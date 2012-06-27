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
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiAttributeOperation;

public class MultiAttributeConflict extends Conflict {

	public MultiAttributeConflict(List<AbstractOperation> opsA, List<AbstractOperation> opsB,
		DecisionManager decisionManager, boolean myAdd) {
		super(opsA, opsB, decisionManager, myAdd, true);
	}

	/**
	 * LEFT: ADDING RIGHT: REMOVING
	 */

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.dialogs.merge.conflict.Conflict#initConflictDescription()
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {
		if (isLeftMy()) {
			description.setDescription(DecisionUtil.getDescription("multiattributeconflict.my", getDecisionManager()
				.isBranchMerge()));
		} else {
			description.setDescription(DecisionUtil.getDescription("multiattributeconflict.their", getDecisionManager()
				.isBranchMerge()));
		}
		description.setImage("attribute.gif");
		return description;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.dialogs.merge.conflict.Conflict#initConflictOptions(java.util.List)
	 */
	@Override
	protected void initConflictOptions(List<ConflictOption> options) {
		ConflictOption my = new ConflictOption(getLabel(true) + " "
			+ getMyOperation(MultiAttributeOperation.class).getReferencedValues().get(0), OptionType.MyOperation);
		my.addOperations(getMyOperations());

		ConflictOption their = new ConflictOption(getLabel(false) + " "
			+ getTheirOperation(MultiAttributeOperation.class).getReferencedValues().get(0), OptionType.TheirOperation);
		their.addOperations(getTheirOperations());

		options.add(my);
		options.add(their);
	}

	/**
	 * TODO adjust lable
	 * 
	 * @param you
	 * @return
	 */
	private String getLabel(boolean you) {
		return ((isLeftMy() && you || (!isLeftMy() && !you)) ? "Add" : "Remove") + " ";
	}
}
