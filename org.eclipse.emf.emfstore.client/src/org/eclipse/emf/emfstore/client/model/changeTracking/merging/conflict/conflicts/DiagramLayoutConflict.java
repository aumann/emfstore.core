/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts;

import java.util.List;

import org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

/**
 * Conflict between two {@link org.eclipse.emf.emfstore.server.model.versioning.operations.DiagramLayoutOperation} .
 * Special case of {@link AttributeConflict}.
 * 
 * @author wesendon
 */
public class DiagramLayoutConflict extends AttributeConflict {

	/**
	 * Default constructor.
	 * 
	 * @param myOperations list of my operations
	 * @param theirOperations list of their operations
	 * @param decisionManager decisionmanager
	 */
	public DiagramLayoutConflict(List<AbstractOperation> myOperations, List<AbstractOperation> theirOperations,
		DecisionManager decisionManager) {
		super(myOperations, theirOperations, decisionManager);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {
		description = super.initConflictDescription(description);
		description
			.setDescription(DecisionUtil.getDescription("diagramconflict", getDecisionManager().isBranchMerge()));
		return description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initConflictOptions(List<ConflictOption> options) {
		super.initOptionsWithOutMerge(options, false);
		for (ConflictOption op : options) {
			if (op.getType().equals(OptionType.MyOperation)) {
				op.setOptionLabel("Retain your Layout");
			} else if (op.getType().equals(OptionType.TheirOperation)) {
				op.setOptionLabel("Drop your Layout");
			}
		}
	}

}
