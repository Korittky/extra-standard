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
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.XmlMappingException;

import com.sun.istack.ByteArrayDataSource;

import de.drv.dsrv.extra.marshaller.IExtraMarschaller;
import de.drv.dsrv.extra.marshaller.IExtraUnmarschaller;
import de.drv.dsrv.extrastandard.namespace.components.DataType;
import de.drv.dsrv.extrastandard.namespace.components.FlagType;
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
import de.extrastandard.api.exception.ExtraResponseProcessPluginRuntimeException;
import de.extrastandard.api.model.content.IResponseData;
import de.extrastandard.api.model.content.ISingleResponseData;
import de.extrastandard.api.model.execution.PersistentStatus;
import de.extrastandard.api.observer.ITransportInfo;
import de.extrastandard.api.observer.ITransportObserver;
import de.extrastandard.api.plugin.IResponseProcessPlugin;
import de.extrastandard.api.util.IExtraReturnCodeAnalyser;

@Named("fileSystemResponseProcessPlugin")
@PluginConfiguration(pluginBeanName = "fileSystemResponseProcessPlugin", pluginType = PluginConfigType.ResponseProcessPlugins)
public class FileSystemResponseProcessPlugin implements IResponseProcessPlugin {

	private static final Logger LOG = LoggerFactory
			.getLogger(FileSystemResponseProcessPlugin.class);

	@Inject
	@Named("extraMarschaller")
	private IExtraMarschaller marshaller;

	@Inject
	@Named("extraUnmarschaller")
	private IExtraUnmarschaller extraUnmarschaller;

	@PluginValue(key = "eingangOrdner")
	private File eingangOrdner;

	@PluginValue(key = "reportOrdner")
	private File reportOrdner;

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

			pruefeVerzeichnis();

			final ResponseTransportHeader transportHeader = extraResponse
					.getTransportHeader();
			final ITransportInfo transportInfo = transportInfoBuilder
					.createTransportInfo(transportHeader);
			transportObserver.responseFilled(transportInfo);

			final ResponseDetailsType responseDetails = transportHeader
					.getResponseDetails();
			final RequestDetailsType requestDetails = transportHeader
					.getRequestDetails();
			if (!isBodyEmpty(extraResponse.getTransportBody())) {

				final List<ResponsePackage> packageList = extraResponse
						.getTransportBody().getPackage();
				if (packageList == null || packageList.size() == 0) {
					final String responseId = responseDetails.getResponseID()
							.getValue();
					LOG.debug("Keine Pakete vorhanden");
					final DataHandler dataHandler = extraResponse
							.getTransportBody().getData()
							.getBase64CharSequence().getValue();

					if (saveBodyToFilesystem(responseId, dataHandler)) {
						LOG.debug("Speicheren des Body auf Filesystem erfolgreich");
					}

					final ReportType report = responseDetails.getReport();
					final SingleReportData reportData = returnCodeExtractor
							.extractReportData(report);
					final String returnCode = reportData.getReturnCode();
					final boolean returnCodeSuccessful = extraReturnCodeAnalyser
							.isReturnCodeSuccessful(returnCode);
					// Status (DONE oder FAIL)
					final PersistentStatus persistentStatus = returnCodeSuccessful ? PersistentStatus.DONE
							: PersistentStatus.FAIL;

					final String outputIdentifier = baueDateiname();

					final ISingleResponseData singleResponseData = new SingleResponseData(
							requestDetails.getRequestID().getValue(),
							returnCode, reportData.getReturnText(), responseId,
							returnCodeSuccessful, persistentStatus,
							outputIdentifier);
					responseData.addSingleResponse(singleResponseData);

				} else {
					for (final Iterator<ResponsePackage> iter = packageList
							.iterator(); iter.hasNext();) {
						final ResponsePackage extraPackage = iter.next();

						final String responseId = extraPackage
								.getPackageHeader().getResponseDetails()
								.getResponseID().getValue();
						DataType data = new DataType();
						data = extraPackage.getPackageBody().getData();
						DataHandler packageBodyDataHandler = null;

						if (data.getBase64CharSequence() != null) {

							packageBodyDataHandler = data
									.getBase64CharSequence().getValue();

						} else {
							if (data.getCharSequence() != null) {
								final byte[] packageBody = data
										.getCharSequence().getValue()
										.getBytes();
								final DataSource source = new ByteArrayDataSource(
										packageBody, "application/octet-stream");
								packageBodyDataHandler = new DataHandler(source);
							}
						}

						if (packageBodyDataHandler != null) {
							if (saveBodyToFilesystem(responseId,
									packageBodyDataHandler)) {
								if (LOG.isDebugEnabled()) {
									LOG.debug("Speichern für RespId "
											+ responseId + " erfolgreich");
								}
							}
						} else {
							LOG.error("RequestPackageBody nicht gefüllt");

						}
					}
				}
			} else {

				final ReportType report = responseDetails.getReport();
				final String requestId = requestDetails.getRequestID()
						.getValue();
				final String responseId = responseDetails.getResponseID()
						.getValue();

				saveReportToFilesystem(report, responseId, requestId);

				final ISingleResponseData singleResponseData = new SingleResponseData(
						requestId, "C00", "RETURNTEXT", responseId, true,
						PersistentStatus.DONE, "OUTPUT-ID");
				responseData.addSingleResponse(singleResponseData);
				LOG.info("Body leer");
			}

		} catch (final XmlMappingException xmlMappingException) {
			throw new ExtraResponseProcessPluginRuntimeException(
					xmlMappingException);
		} catch (final IOException ioException) {
			throw new ExtraResponseProcessPluginRuntimeException(ioException);
		}
		return responseData;
	}

	private static boolean isBodyEmpty(final ResponseTransportBody transportBody) {
		boolean isEmpty = false;

		if (transportBody == null) {
			isEmpty = true;
		} else {
			if (transportBody.getData() == null
					|| transportBody.getEncryptedData() == null) {

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

	private boolean saveBodyToFilesystem(final String responseId,
			final DataHandler dataHandler) throws IOException {
		final boolean erfolgreichGespeichert = false;

		final StringBuffer dateiName = new StringBuffer();

		dateiName.append(baueDateiname());
		dateiName.append("-");
		dateiName.append(responseId);

		final File responseFile = new File(eingangOrdner, dateiName.toString());

		final FileOutputStream fileOutputStream = new FileOutputStream(
				responseFile);

		IOUtils.copyLarge(dataHandler.getInputStream(), fileOutputStream);

		transportObserver.responseDataForwarded(responseFile.getAbsolutePath(),
				responseFile.length());

		if (LOG.isTraceEnabled()) {
			LOG.trace("Dateiname: '" + dateiName + "'");
		}

		return erfolgreichGespeichert;
	}

	private boolean saveReportToFilesystem(final ReportType report,
			final String responseId, final String requestId) {
		final boolean erfolgreichGespeichert = false;

		final StringBuffer dateiName = new StringBuffer();

		dateiName.append(baueDateiname());
		dateiName.append(".rep");

		final File reportFile = new File(reportOrdner, dateiName.toString());

		final List<FlagType> flagList = report.getFlag();
		FlagType flagItem = null;

		if (flagList.size() >= 1) {
			flagItem = flagList.get(0);
		}

		FileWriter fw = null;
		StringBuffer sb;

		try {
			fw = new FileWriter(reportFile);

			sb = new StringBuffer();
			sb.append("Die Meldung mit ID ");
			sb.append(requestId);
			sb.append(" erhielt folgende Antwort:");
			sb.append("\r\n");
			sb.append("ResponseID: ");
			sb.append(responseId);
			sb.append("\r\n");
			sb.append("ReturnCode:");
			sb.append(flagItem.getCode().getValue());
			sb.append("\r\n");
			if (flagItem.getText() != null) {
				sb.append(flagItem.getText().getValue());
			}

			fw.write(sb.toString());
			transportObserver.responseDataForwarded(
					reportFile.getAbsolutePath(), 0);

		} catch (final IOException e) {
			LOG.error("Fehler beim Schreiben des Reports", e);
		} finally {
			try {
				fw.close();
			} catch (final IOException e) {
				LOG.error("Fehler beim schließen das FileWriters");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Report: '" + reportFile.getAbsolutePath() + "'");
		}

		return erfolgreichGespeichert;
	}

	private String baueDateiname() {
		final Date now = Calendar.getInstance().getTime();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmm");
		sdf.format(now);

		return sdf.format(now);
	}

	private void pruefeVerzeichnis() {
		if (!eingangOrdner.exists()) {
			LOG.debug("Eingangsordner anlegen");

			eingangOrdner.mkdir();
		}
		if (!reportOrdner.exists()) {
			LOG.debug("Reportordner anlegen");

			reportOrdner.mkdir();
		}
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

	/**
	 * @return the reportOrdner
	 */
	public File getReportOrdner() {
		return reportOrdner;
	}

	/**
	 * @param reportOrdner
	 *            the reportOrdner to set
	 */
	public void setReportOrdner(final File reportOrdner) {
		this.reportOrdner = reportOrdner;
	}

}
