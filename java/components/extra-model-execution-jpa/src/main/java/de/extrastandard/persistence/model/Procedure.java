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

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Configurable;

import de.extrastandard.api.model.execution.IMandator;
import de.extrastandard.api.model.execution.IProcedure;
import de.extrastandard.persistence.repository.ProcedureRepository;

/**
 * JPA Implementierung von {@link IProcedure}.
 *
 * @author Thorsten Vogel
 * @version $Id$
 */
@Configurable(preConstruction=true)
@Entity
@Table(name="PROCEDURE")
public class Procedure extends AbstractEntity implements IProcedure {

	private static final long serialVersionUID = 1L;

	@ManyToOne()
	@JoinColumn(name="mandator_id")
	private Mandator mandator;

	@Column(name="name")
	private String name;

	@Transient
	@Inject
	@Named("procedureRepository")
	private transient ProcedureRepository repository;

	/**
	 * @see de.extrastandard.api.model.execution.IProcedure#getIMandator()
	 */
	@Override
	public IMandator getMandator() {
		return mandator;
	}

	/**
	 * @see de.extrastandard.api.model.execution.IProcedure#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	public void setMandator(final IMandator mandator) {
		this.mandator = (Mandator) mandator;
	}

	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @see de.extrastandard.api.model.execution.PersistentEntity#saveOrUpdate()
	 */
	@Override
	public void saveOrUpdate() {
		repository.save(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Procedure [mandator=");
		builder.append(mandator);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}