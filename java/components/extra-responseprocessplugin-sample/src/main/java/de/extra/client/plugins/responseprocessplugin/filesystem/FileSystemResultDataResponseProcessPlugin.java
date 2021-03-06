/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package de.extra.client.plugins.responseprocessplugin.filesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.activation.DataHandler;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.XmlMappingException;

import de.drv.dsrv.extra.marshaller.IExtraMarschaller;
import de.drv.dsrv.extra.marshaller.IExtraUnmarschaller;
import de.drv.dsrv.extrastandard.namespace.components.ReportType;
import de.drv.dsrv.extrastandard.namespace.components.RequestDetailsType;
import de.drv.dsrv.extrastandard.namespace.components.ResponseDetailsType;
import de.drv.dsrv.extrastandard.namespace.response.ResponseMessage;
import de.drv.dsrv.extrastandard.namespace.response.ResponsePackage;
import de.drv.dsrv.extrastandard.namespace.response.ResponseTransport;
import de.drv.dsrv.extrastandard.namespace.response.ResponseTransportBody;
import de.drv.dsrv.extrastandard.namespace.response.ResponseTransportHeader;
import de.extra.client.core.annotation.PluginConfigType;
import de.extra.client.core.annotation.PluginConfiguration;
import de.extra.client.core.annotation.PluginValue;
import de.extra.client.core.observer.impl.TransportInfoBuilder;
import de.extra.client.core.responce.impl.ResponseData;
import de.extra.client.core.responce.impl.SingleReportData;
import de.extra.client.core.responce.impl.SingleResponseData;
import de.extrastandard.api.exception.ExceptionCode;
import de.extrastandard.api.exception.ExtraResponseProcessPluginRuntimeException;
import de.extrastandard.api.model.content.IResponseData;
import de.extrastandard.api.model.content.ISingleResponseData;
import de.extrastandard.api.model.execution.PersistentStatus;
import de.extrastandard.api.observer.ITransportInfo;
import de.extrastandard.api.observer.ITransportObserver;
import de.extrastandard.api.plugin.IResponseProcessPlugin;
import de.extrastandard.api.util.IExtraReturnCodeAnalyser;

/**
 * 
 * Speichert Verarbeitungsergebnisse des Fachverfahren in dem Filesystem. Hier
 * wird initial eine einfache Verarbeitung vorrausgesetzt. Die Daten werden in
 * dem MessageBodybereich in dem Data-Fragment erwartet.
 * 
 * @author DPRS
 * @version $Id$
 */
@Named("fileSystemResultDataResponseProcessPlugin")
@PluginConfiguration(pluginBeanName = "fileSystemResultDataResponseProcessPlugin", pluginType = PluginConfigType.ResponseProcessPlugins)
public class FileSystemResultDataResponseProcessPlugin implements
		IResponseProcessPlugin {

	private static final Logger LOG = LoggerFactory
			.getLogger(FileSystemResultDataResponseProcessPlugin.class);

	@Inject
	@Named("extraMarschaller")
	private IExtraMarschaller marshaller;

	@Inject
	@Named("extraUnmarschaller")
	private IExtraUnmarschaller extraUnmarschaller;

	@PluginValue(key = "eingangOrdner")
	private File eingangOrdner;

	@Inject
	@Named("transportObserver")
	private ITransportObserver transportObserver;

	@Inject
	@Named("transportInfoBuilder")
	private TransportInfoBuilder transportInfoBuilder;

	@Inject
	@Named("extraMessageReturnDataExtractor")
	private ExtraMessageReturnDataExtractor returnCodeExtractor;

	@Inject
	@Named("extraReturnCodeAnalyser")
	private IExtraReturnCodeAnalyser extraReturnCodeAnalyser;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.extra.client.core.plugin.IResponsePlugin#processResponse(de.drv.dsrv
	 * .extrastandard.namespace.response.XMLTransport)
	 */
	@Override
	public IResponseData processResponse(final ResponseTransport extraResponse) {
		final IResponseData responseData = new ResponseData();
		try {
			// Ausgabe der Response im log
			ExtraMessageReturnDataExtractor.printResult(marshaller,
					extraResponse);

			final ResponseTransportHeader transportHeader = extraResponse
					.getTransportHeader();
			final ITransportInfo transportInfo = transportInfoBuilder
					.createTransportInfo(transportHeader);
			transportObserver.responseFilled(transportInfo);

			final ResponseDetailsType responseDetails = transportHeader
					.getResponseDetails();
			final RequestDetailsType requestDetails = transportHeader
					.getRequestDetails();
			if (isBodyEmpty(extraResponse.getTransportBody())) {
				throw new ExtraResponseProcessPluginRuntimeException(
						ExceptionCode.UNEXPECTED_INTERNAL_EXCEPTION,
						"Keine Daten vorhanden. Body Element ist leer");
			}
			final DataHandler transportBodyDataHandler = extraResponse
					.getTransportBody().getData().getBase64CharSequence()
					.getValue();
			final String responseId = responseDetails.getResponseID()
					.getValue();

			saveBodyToFilesystem(responseId, transportBodyDataHandler);

			final ReportType report = responseDetails.getReport();
			final SingleReportData reportData = returnCodeExtractor
					.extractReportData(report);
			final String returnCode = reportData.getReturnCode();
			final boolean returnCodeSuccessful = extraReturnCodeAnalyser
					.isReturnCodeSuccessful(returnCode);
			// Status (DONE oder FAIL)
			final PersistentStatus persistentStatus = returnCodeSuccessful ? PersistentStatus.DONE
					: PersistentStatus.FAIL;

			// (17.12.12) Ergebnis-Dateiname als OutputIdentifier
			final String outputIdentifier = buildFilename(responseId);

			final ISingleResponseData singleResponseData = new SingleResponseData(
					requestDetails.getRequestID().getValue(), returnCode,
					reportData.getReturnText(), responseId,
					returnCodeSuccessful, persistentStatus, outputIdentifier);
			responseData.addSingleResponse(singleResponseData);

		} catch (final XmlMappingException xmlMappingException) {
			throw new ExtraResponseProcessPluginRuntimeException(
					xmlMappingException);
		}
		return responseData;
	}

	private static boolean isBodyEmpty(final ResponseTransportBody transportBody) {
		boolean isEmpty = false;

		if (transportBody == null) {
			isEmpty = true;
		} else {
			if (transportBody.getData() == null
					&& transportBody.getEncryptedData() == null) {

				isEmpty = true;
			}

			final List<ResponsePackage> packageList = transportBody
					.getPackage();
			final List<ResponseMessage> messageList = transportBody
					.getMessage();
			if (messageList.size() == 0 && packageList.size() == 0 && isEmpty) {
				isEmpty = true;
			} else {
				isEmpty = false;
			}
		}

		return isEmpty;
	}

	/**
	 * @param responseId
	 * @param responseBody
	 * @return
	 */
	private void saveBodyToFilesystem(final String responseId,
			final DataHandler dataHandler) {
		try {

			final String dateiName = buildFilename(responseId);

			final File responseFile = new File(eingangOrdner, dateiName);

			final FileOutputStream fileOutputStream = new FileOutputStream(
					responseFile);
			IOUtils.copyLarge(dataHandler.getInputStream(), fileOutputStream);

			transportObserver.responseDataForwarded(
					responseFile.getAbsolutePath(), responseFile.length());

			LOG.info("Response gespeichert in File: '" + dateiName + "'");

		} catch (final IOException ioException) {
			throw new ExtraResponseProcessPluginRuntimeException(
					ExceptionCode.UNEXPECTED_INTERNAL_EXCEPTION,
					"Fehler beim schreiben der Antwort", ioException);
		}
	}

	/**
	 * Erzeugt einen eindeitigen Filenamen mit milissekunden und ResponseID
	 * 
	 * @param responseId
	 * @return
	 */
	private String buildFilename(final String responseId) {
		final StringBuilder fileName = new StringBuilder();
		fileName.append("RESPONSE_").append(responseId);
		fileName.append("_").append(System.currentTimeMillis());
		return fileName.toString();
	}

	/**
	 * @return the eingangOrdner
	 */
	public File getEingangOrdner() {
		return eingangOrdner;
	}

	/**
	 * @param eingangOrdner
	 *            the eingangOrdner to set
	 */
	public void setEingangOrdner(final File eingangOrdner) {
		this.eingangOrdner = eingangOrdner;
	}

}
