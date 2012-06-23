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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThread;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThreadWithResult;
import org.eclipse.emf.emfstore.client.ui.dialogs.BranchSelectionDialog;
import org.eclipse.emf.emfstore.client.ui.dialogs.CommitDialog;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI-dependent commit controller for committing pending changes on a {@link ProjectSpace}.<br/>
 * The controller presents the user a dialog will all changes made before he is able to confirm the commit.
 * If no changes have been made by the user a information dialog is presented that states that there are no
 * pending changes to be committed.
 * 
 * @author ovonwesen
 * @author emueller
 * 
 */
public class UICreateBranchController extends AbstractEMFStoreUIController<PrimaryVersionSpec> implements
	CommitCallback {

	private final ProjectSpace projectSpace;
	private LogMessage logMessage;
	private int dialogReturnValue;
	private BranchVersionSpec branch;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent shell that will be used during commit
	 * @param projectSpace
	 *            the {@link ProjectSpace} that contains the pending changes that should get committed
	 */
	public UICreateBranchController(Shell shell, ProjectSpace projectSpace) {
		super(shell, true, true);
		this.projectSpace = projectSpace;
		this.branch = null;
	}

	public UICreateBranchController(Shell shell, ProjectSpace projectSpace, BranchVersionSpec branch) {
		super(shell, true, true);
		this.projectSpace = projectSpace;
		this.branch = branch;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback#noLocalChanges(org.eclipse.emf.emfstore.client.model.ProjectSpace)
	 */
	public void noLocalChanges(ProjectSpace projectSpace) {
		new RunInUIThread(getShell()) {
			@Override
			public Void doRun(Shell shell) {
				MessageDialog.openInformation(shell, null, "No local changes in your project. No need to commit.");
				return null;
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback#baseVersionOutOfDate(org.eclipse.emf.emfstore.client.model.ProjectSpace)
	 */
	public boolean baseVersionOutOfDate(final ProjectSpace projectSpace) {

		final String message = "Your project is outdated, you need to update before commit. Do you want to update now?";

		return new RunInUIThreadWithResult<Boolean>(getShell()) {
			@Override
			public Boolean doRun(Shell shell) {
				boolean shouldUpdate = MessageDialog.openConfirm(shell, "Confirmation", message);

				if (shouldUpdate) {
					try {
						new UIUpdateProjectController(getShell(), projectSpace).execute();
					} catch (EmfStoreException e) {
						handleException(e);
					}
				}

				return shouldUpdate;
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback#inspectChanges(org.eclipse.emf.emfstore.client.model.ProjectSpace,
	 *      org.eclipse.emf.emfstore.server.model.versioning.ChangePackage)
	 */
	public boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage) {

		final CommitDialog commitDialog = new CommitDialog(getShell(), changePackage, projectSpace);

		dialogReturnValue = new RunInUIThreadWithResult<Integer>(getShell()) {
			@Override
			public Integer doRun(Shell shell) {
				return commitDialog.open();
			}
		}.execute();

		if (dialogReturnValue == Dialog.OK) {
			changePackage.getLogMessage().setMessage(commitDialog.getLogText());
			return true;
		}

		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public PrimaryVersionSpec doRun(final IProgressMonitor progressMonitor) throws EmfStoreException {

		if (branch == null) {
			branch = branchSelection(projectSpace);
		}
		return projectSpace.commitToBranch(branch, logMessage, UICreateBranchController.this, progressMonitor);
	}

	private BranchVersionSpec branchSelection(final ProjectSpace projectSpace) throws EmfStoreException {
		final List<BranchInfo> branches = ((ProjectSpaceBase) projectSpace).getBranches();

		String branch = new RunInUIThreadWithResult<String>(getShell()) {
			@Override
			public String doRun(Shell shell) {
				BranchSelectionDialog.Creation dialog = new BranchSelectionDialog.Creation(getShell(),
					projectSpace.getBaseVersion(), branches);
				dialog.setBlockOnOpen(true);

				if (dialog.open() != Dialog.OK || dialog.getNewBranch() == null) {
					// TODO BRANCH ask eddy
					// throw new EmfStoreException("No Branch specified");
					return null;
				}
				return dialog.getNewBranch();
			}
		}.execute();

		if (branch == null) {
			throw new EmfStoreException("No Branch specified");

		}

		return Versions.BRANCH(branch);
	}
}
