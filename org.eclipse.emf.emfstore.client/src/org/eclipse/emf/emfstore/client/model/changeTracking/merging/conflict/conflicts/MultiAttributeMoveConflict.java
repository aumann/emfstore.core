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

// BEGIN COMPLEX CODE
//
// WORK IN PROGRESS !
//

import java.util.List;

import org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

public class MultiAttributeMoveConflict extends Conflict {

	public MultiAttributeMoveConflict(List<AbstractOperation> opsA, List<AbstractOperation> opsB,
		DecisionManager decisionManager, boolean isMyAdd) {
		super(opsA, opsB, decisionManager, isMyAdd, true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.dialogs.merge.conflict.Conflict#initConflictDescription()
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {
		if (isLeftMy()) {
			description.setDescription(DecisionUtil.getDescription("multiattributemoveconflict.my",
				getDecisionManager().isBranchMerge()));

		} else {
			description.setDescription(DecisionUtil.getDescription("multiattributemoveconflict.their",
				getDecisionManager().isBranchMerge()));
		}

		return description;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.dialogs.merge.conflict.Conflict#initConflictOptions(java.util.List)
	 */
	@Override
	protected void initConflictOptions(List<ConflictOption> options) {
		ConflictOption myOption = new ConflictOption("", OptionType.MyOperation);
		myOption.addOperations(getMyOperations());
		ConflictOption theirOption = new ConflictOption("", OptionType.TheirOperation);
		theirOption.addOperations(getTheirOperations());

		if (isLeftMy()) {
			myOption.setOptionLabel("Add element");
			theirOption.setOptionLabel("Move element");
		} else {
			myOption.setOptionLabel("Move element");
			theirOption.setOptionLabel("Add element");
		}

		options.add(myOption);
		options.add(theirOption);
	}
}
