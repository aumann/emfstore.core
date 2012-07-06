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
package org.eclipse.emf.emfstore.client.ui.dialogs.merge;

import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.AbstractConflictResolver;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.ConflictResolver;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.MergeLabelProvider;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.util.DefaultMergeLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

/**
 * This is an alternative merge handler, using the new merge wizard.
 * 
 * @author wesendon
 */
public class MergeProjectHandler extends AbstractConflictResolver implements ConflictResolver {

	private DefaultMergeLabelProvider labelProvider;

	public MergeProjectHandler(boolean isBranchMerge) {
		super(isBranchMerge);
	}

	public MergeProjectHandler() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void preDecisionManagerHook() {
		labelProvider = new DefaultMergeLabelProvider();
		WorkspaceManager.getObserverBus().register(labelProvider, MergeLabelProvider.class);
	}

	@Override
	protected boolean controlDecisionManager(DecisionManager decisionManager) {
		MergeWizard wizard = new MergeWizard(decisionManager);
		WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
		dialog.setPageSize(1000, 500);
		dialog.setBlockOnOpen(true);
		dialog.create();

		int open = dialog.open();

		labelProvider.dispose();
		return (open == Dialog.OK);
	}
}
