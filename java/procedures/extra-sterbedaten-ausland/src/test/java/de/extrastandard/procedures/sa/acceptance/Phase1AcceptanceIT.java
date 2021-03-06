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
package de.extrastandard.procedures.sa.acceptance;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import de.extra.client.starter.ExtraClient;
import de.extra.client.starter.ExtraClientTestBasic;
import de.extrastandard.api.model.execution.ICommunicationProtocol;
import de.extrastandard.api.model.execution.PersistentStatus;
import de.extrastandard.persistence.model.Execution;
import de.extrastandard.persistence.model.ExecutionPersistenceJpa;
import de.extrastandard.persistence.model.ProcessTransition;
import de.extrastandard.persistence.repository.ExecutionRepository;

/**
 * <pre>
 * Acceptance Test für die Fachverfahren Sterbedaten Phase 1.
 * Test setzt eine Oracle Datenbankschema vorraus.
 * Das eXTra Schema wird vor jedem Test neu angelegt und mit der Testdaten initial gefüllt.
 * </pre>
 *
 * @author Leonid Potap
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-persistence-jpa.xml",
		"/conf/acceptance/phase1/property-placeholder-acceptance-phase1.xml",
		"/conf/acceptance/spring-acceptance-flyway.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class Phase1AcceptanceIT {

	private static final Logger logger = LoggerFactory
			.getLogger(Phase1AcceptanceIT.class);

    private static final String DRV = "DRV";

    private static final String GLOBAL_CONFIG_PATH = "/conf/testglobalconfig";

    private static final String CONFIG_PATH = "/conf/phase1";

    private static final String LOG_DIR = "/logs";

	private final ExtraClientTestBasic extraClientTestBasic = new ExtraClientTestBasic();

	@Inject
	@Named("executionPersistenceJpa")
	private ExecutionPersistenceJpa executionPersistenceJpa;

	@Inject
	@Named("executionRepository")
	private ExecutionRepository executionRepository;

	@Before
	public void setUp() throws Exception {
		ExtraClient extraClient;
		extraClient = extraClientTestBasic.createExtraClient(DRV, GLOBAL_CONFIG_PATH, CONFIG_PATH, LOG_DIR);
		extraClientTestBasic.testExecute(extraClient);
	}

	@Test
	public void checkDBResults() {
		logger.info("CheckDBResults started");
		final int expectedExecutionSize = 3;
		final String expectedPhase = "PHASE1";
		final String expectedParametersSuffix = "phase1";
		final String expectedReturnCode = "C00";

		final List<Execution> allExecutions = executionRepository.findAll();
		Assert.assertEquals("Unexpected Execution Size", expectedExecutionSize,
				allExecutions.size());
		for (final Execution execution : allExecutions) {
			Assert.assertNull("ErrorCode is not null", execution.getErrorCode());
			Assert.assertNull("ErrorMessage is not null",
					execution.getErrorMessage());
			Assert.assertEquals("Unexpected Phase", expectedPhase,
					execution.getPhase());
			Assert.assertNotNull("Parameters ist null",
					execution.getParameters());
			Assert.assertTrue(
					"Unexpected Parameters: " + execution.getParameters(),
					execution.getParameters()
							.endsWith(expectedParametersSuffix));
			final ProcessTransition lastTransition = execution
					.getLastTransition();
			Assert.assertNotNull("LastTransition ist null", lastTransition);
			final String statusName = lastTransition.getCurrentStatus()
					.getName();
			Assert.assertEquals("Unexpected Phase",
					PersistentStatus.DONE.name(), statusName);
			final Set<ICommunicationProtocol> communicationProtocols = execution
					.getCommunicationProtocols();
			Assert.assertEquals("Unexpected Count of CommunicationProtokols",
					1, communicationProtocols.size());
			for (final ICommunicationProtocol communicationProtocol : communicationProtocols) {
				Assert.assertEquals("Unexpected ReturnCode",
						expectedReturnCode,
						communicationProtocol.getReturnCode());
				Assert.assertNull("Unexpected nextPhaseConnection",
						communicationProtocol.getNextPhaseConnection());
			}
		}
		logger.info("CheckDBResults successfully completed");
	}
}
