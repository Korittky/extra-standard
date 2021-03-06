package de.extrastandard.persistence.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Transient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import de.extra.client.core.model.inputdata.impl.SingleStringInputData;
import de.extra.client.core.responce.impl.ResponseData;
import de.extra.client.core.responce.impl.SingleResponseData;
import de.extrastandard.api.model.content.IResponseData;
import de.extrastandard.api.model.content.ISingleContentInputData;
import de.extrastandard.api.model.content.ISingleResponseData;
import de.extrastandard.api.model.execution.IExecution;
import de.extrastandard.api.model.execution.IExecutionPersistence;
import de.extrastandard.api.model.execution.ICommunicationProtocol;
import de.extrastandard.api.model.execution.IProcessTransition;
import de.extrastandard.api.model.execution.IStatus;
import de.extrastandard.api.model.execution.PersistentStatus;
import de.extrastandard.api.model.execution.PhaseQualifier;
import de.extrastandard.persistence.repository.CommunicationProtocolRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-persistence-jpa.xml",
		"/spring-ittest.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class PersistenceSterbedatenBroken {
	@Inject
	@Named("persistenceSterbedatenTestSetup")
	private PersistenceSterbedatenTestSetup persistenceSterbedatenTestSetup;

	@Transient
	@Inject
	@Named("communicationProtocolRepository")
	private transient CommunicationProtocolRepository communicationProtocolRepository;

	@Inject
	@Named("executionPersistenceJpa")
	private IExecutionPersistence executionPersistence;

	@Before
	public void before() throws Exception {
	}

	@Test
	public void testExecutionConstruction() throws Exception {
		assertNotNull(executionPersistence);

		final String parameter = "-c d:/extras/configdir";
		// erzeugt eine 'Execution' und eine zugeordnete 'ProcessTransition'
		final IExecution execution = executionPersistence.startExecution(
				PersistenceSterbedatenTestSetup.PROC_STERBEMELDUNG_NAME,
				parameter, PhaseQualifier.PHASE1);

		// Execution vorhanden?
		assertNotNull(execution.getParameters());
		assertNotNull(execution.getId());
		assertNotNull(execution.getStartTime());
		assertEquals(parameter, execution.getParameters());
		assertEquals(PhaseQualifier.PHASE1.getName(), execution.getPhase());

		// Transition vorhanden?
		final IProcessTransition lastTransitionInitial = execution
				.getLastTransition();
		assertNotNull(lastTransitionInitial);
		// Transition mit Status?
		final IStatus currentStatusInitial = lastTransitionInitial
				.getCurrentStatus();
		assertNotNull(currentStatusInitial);
		assertEquals(PersistentStatus.INITIAL.getId(),
				currentStatusInitial.getId());
		// Kein Vorgänger-Status?
		final IStatus previousStatus = lastTransitionInitial
				.getPreviousStatus();
		assertNull(previousStatus);

		// Execution in nächste Stufe setzen: ENVELOPED
		execution.updateProgress(PersistentStatus.ENVELOPED);
		// Zustandswechsel in neuer 'ProcessTransition' abgespeichert?
		final IProcessTransition lastTransitionEnveloped = execution
				.getLastTransition();
		assertNotNull(lastTransitionEnveloped);
		final IStatus currentStatusEnveloped = lastTransitionEnveloped
				.getCurrentStatus();
		assertNotNull(currentStatusEnveloped);
		assertEquals(PersistentStatus.ENVELOPED.getId(),
				currentStatusEnveloped.getId());
		final IStatus previousStatusInitial = lastTransitionEnveloped
				.getPreviousStatus();
		assertNotNull(previousStatusInitial);
		assertEquals(PersistentStatus.INITIAL.getId(),
				previousStatusInitial.getId());

		// Execution in nächste Stufe setzen: Transmitted
		execution.updateProgress(PersistentStatus.TRANSMITTED);
		// Zustandswechsel in neuer 'ProcessTransition' abgespeichert?
		final IProcessTransition lastTransitionTransmitted = execution
				.getLastTransition();
		assertNotNull(lastTransitionTransmitted);
		final IStatus currentStatusTransmitted = lastTransitionTransmitted
				.getCurrentStatus();
		assertNotNull(currentStatusTransmitted);
		assertEquals(PersistentStatus.TRANSMITTED.getId(),
				currentStatusTransmitted.getId());
		final IStatus previousStatusEnveloped = lastTransitionTransmitted
				.getPreviousStatus();
		assertNotNull(previousStatusEnveloped);
		assertEquals(PersistentStatus.ENVELOPED.getId(),
				previousStatusEnveloped.getId());

		final ISingleContentInputData singleContentInputData = new SingleStringInputData(
				"test data");
		final ICommunicationProtocol inputData = execution
				.startInputData(singleContentInputData);
		assertNotNull(inputData);
		assertEquals(singleContentInputData.getHashCode(),
				inputData.getHashcode());
		assertEquals(singleContentInputData.getInputIdentifier(),
				inputData.getInputIdentifier());

		final String requestId = inputData.calculateRequestId();
		singleContentInputData.setRequestId(requestId);
		inputData.setRequestId(requestId);
		assertEquals(singleContentInputData.getRequestId(),
				inputData.getRequestId());

		final IResponseData responceData = new ResponseData();
		final Boolean successful = true;
		final ISingleResponseData singleResponseData = new SingleResponseData(
				requestId, "ReturnCode", "ReturnText", "RESPONSE_ID", false, PersistentStatus.DONE, "Output-ID");
		responceData.addSingleResponse(singleResponseData);
		execution.endExecution(responceData);
		final IProcessTransition lastTransitionDone = execution
				.getLastTransition();
		assertNotNull(lastTransitionDone);
		final IStatus currentStatusDone = lastTransitionDone.getCurrentStatus();
		assertNotNull(currentStatusDone);
		assertEquals(PersistentStatus.DONE.getId(), currentStatusDone.getId());
		final IStatus previousStatusTransmitted = lastTransitionDone
				.getPreviousStatus();
		assertNotNull(previousStatusTransmitted);
		assertEquals(PersistentStatus.TRANSMITTED.getId(),
				previousStatusTransmitted.getId());

		Thread.sleep(500);

	}

}
