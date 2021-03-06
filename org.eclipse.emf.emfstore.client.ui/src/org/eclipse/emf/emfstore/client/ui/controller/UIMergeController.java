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
package org.eclipse.emf.emfstore.client.ui.controller;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.ui.dialogs.BranchSelectionDialog;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.MergeProjectHandler;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UIController used to merge other branches into the current projectspace.
 * 
 * @author wesendon
 */
public class UIMergeController extends AbstractEMFStoreUIController<Void> {

	private final ProjectSpace projectSpace;

	/**
	 * Default constructor.
	 * 
	 * @param shell
	 *            active shell
	 * @param projectSpace
	 *            projectspace
	 */
	public UIMergeController(Shell shell, ProjectSpace projectSpace) {
		super(shell);
		this.projectSpace = projectSpace;
	}

	@Override
	public Void doRun(IProgressMonitor monitor) throws EmfStoreException {
		if (!projectSpace.getOperations().isEmpty()) {
			MessageDialog
					.openError(
							getShell(),
							"Merge not possible",
							"There are pending changes. Please revert or commit first. Merging with local changes is currently not supported.");
			return null;
		}
		PrimaryVersionSpec selectedSource = branchSelection(projectSpace);
		if (selectedSource != null) {
			((ProjectSpaceBase) projectSpace).mergeBranch(selectedSource,
					new MergeProjectHandler(true));
		}
		return null;
	}

	private PrimaryVersionSpec branchSelection(ProjectSpace projectSpace)
			throws EmfStoreException {

		List<BranchInfo> branches = ((ProjectSpaceBase) projectSpace)
				.getBranches();
		ListIterator<BranchInfo> iterator = branches.listIterator();
		while (iterator.hasNext()) {
			BranchInfo current = iterator.next();
			if (current.getName().equals(
					projectSpace.getBaseVersion().getBranch())) {
				iterator.remove();
			}
		}
		BranchSelectionDialog dialog = new BranchSelectionDialog(getShell(),
				projectSpace.getBaseVersion(), branches);
		dialog.setBlockOnOpen(true);

		if (dialog.open() != Dialog.OK || dialog.getResult() == null) {
			// throw new EmfStoreException("No Branch specified");
			return null;
		}
		return dialog.getResult().getHead();
	}
}
