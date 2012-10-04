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
package de.extrastandard.persistence.model;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Transient;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import de.extrastandard.api.model.execution.IExecution;
import de.extrastandard.api.model.execution.IExecutionPersistence;
import de.extrastandard.api.model.execution.IInputData;
import de.extrastandard.api.model.execution.IProcedure;
import de.extrastandard.api.model.execution.IProcedureType;
import de.extrastandard.api.model.execution.IStatus;
import de.extrastandard.api.model.execution.PhaseQualifier;
import de.extrastandard.persistence.repository.InputDataRepository;
import de.extrastandard.persistence.repository.ProcedureRepository;

/**
 * Einstiegsklasse zum Management von Executions.
 * 
 * @author Thorsten Vogel
 * @version $Id: ExecutionPersistence.java 508 2012-09-04 09:35:41Z
 *          thorstenvogel@gmail.com $
 */
@Named("executionPersistenceJPA")
public class ExecutionPersistence implements IExecutionPersistence {

	@Transient
	@Inject
	@Named("procedureRepository")
	private transient ProcedureRepository procedureRepository;

	@Transient
	@Inject
	@Named("inputDataRepository")
	private transient InputDataRepository inputDataRepository;

	/**
	 * @see de.extrastandard.api.model.execution.IExecutionPersistence#startExecution(java.lang.String)
	 */
	@Override
	public IExecution startExecution(final IProcedure procedure, final String parameters) {
		return new Execution(procedure, parameters);
	}

	/**
	 * @see de.extrastandard.api.model.execution.IExecutionPersistence#startExecution(java.lang.String)
	 */
	@Override
	@Transactional
	public IExecution startExecution(final String procedureName, final String parameters) {
		Assert.notNull(procedureName, "ProcedureName is null");
		final IProcedure procedure = procedureRepository.findByName(procedureName);
		Assert.notNull(procedure, "Procedure not found. Name : " + procedureName);
		return startExecution(procedure, parameters);
	}

	@Override
	public boolean isProcedureStartPhase(final String procedureName, final String phase) {
		Assert.notNull(procedureName, "ProcedureName is null");
		Assert.notNull(phase, "Phase is null");
		final IProcedure procedure = procedureRepository.findByName(procedureName);
		Assert.notNull(procedure, "Procedure is null for Name: " + procedureName);
		final IProcedureType procedureType = procedure.getProcedureType();
		return procedureType.isProcedureStartPhase(phase);
	}

	@Override
	public IInputData findInputDataByRequestId(final String requestId) {
		return inputDataRepository.findByRequestId(requestId);
	}

	/**
	 * Seeks InputData for further Procesierung depending on the ExecutePhase
	 * 
	 * @param phaseQualifier
	 * @return
	 */
	@Override
	public List<IInputData> findInputDataForExecution(final String procedureName, final PhaseQualifier phaseQualifier) {
		Assert.notNull(procedureName, "ProcedureName is null");
		Assert.notNull(phaseQualifier, "Phase is null");
		final IProcedure procedure = procedureRepository.findByName(procedureName);
		return findInputDataForExecution(procedure, phaseQualifier);
	}

	/**
	 * Seeks InputData for further Procesierung depending on the ExecutePhase
	 * 
	 * @param phaseQualifier
	 * @return
	 */
	public List<IInputData> findInputDataForExecution(final IProcedure procedure, final PhaseQualifier phaseQualifier) {
		Assert.notNull(procedure, "Procedure is null");
		Assert.notNull(phaseQualifier, "Phase is null");
		final IProcedureType procedureType = procedure.getProcedureType();
		final IStatus phaseStartStatus = procedureType.getPhaseStartStatus(phaseQualifier);
		Assert.notNull(phaseStartStatus, "PhaseStartStatus is not found for Procedure: " + procedure.getName()
				+ " und Phase: " + phaseQualifier.getName());
		final List<IInputData> inputDateList = inputDataRepository
				.findByProcedureAndStatus(procedure, phaseStartStatus);
		return inputDateList;
	}

}