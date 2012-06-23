/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH.
 * 
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
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.impl.WorkspaceImpl;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThreadWithResult;
import org.eclipse.emf.emfstore.client.ui.dialogs.BranchSelectionDialog;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for checking out a project.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class UICheckoutController extends AbstractEMFStoreUIController<ProjectSpace> {

	private ServerInfo serverInfo;
	private ProjectInfo projectInfo;
	private PrimaryVersionSpec versionSpec;
	private boolean askForBranch;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} that will be used during checkout
	 * @param serverInfo
	 *            the {@link ServerInfo} that is used to determine the server where
	 *            the project that should get checked out, is located on
	 * @param projectInfo
	 *            the {@link ProjectInfo} that is used to determine the project that should get
	 *            checked out
	 */
	public UICheckoutController(Shell shell, ServerInfo serverInfo, ProjectInfo projectInfo) {
		super(shell, true, true);
		this.serverInfo = serverInfo;
		this.projectInfo = projectInfo;
		this.askForBranch = false;
		versionSpec = null;
	}

	// TODO BRANCH quick hack, ask eddy how to solve correctly
	public UICheckoutController(Shell shell, ServerInfo serverInfo, ProjectInfo projectInfo, boolean askForBranch) {
		super(shell, true, true);
		this.serverInfo = serverInfo;
		this.projectInfo = projectInfo;
		this.askForBranch = askForBranch;
		versionSpec = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} that will be used during checkout
	 * @param serverInfo
	 *            the {@link ServerInfo} that is used to determine the server where
	 *            the project that should get checked out, is located on
	 * @param projectInfo
	 *            the {@link ProjectInfo} that is used to determine the project that should get
	 *            checked out
	 * @param versionSpec
	 *            the specific version of the project that should get checked out
	 */
	public UICheckoutController(Shell shell, ServerInfo serverInfo, ProjectInfo projectInfo,
		PrimaryVersionSpec versionSpec) {
		super(shell);
		this.serverInfo = serverInfo;
		this.projectInfo = projectInfo;
		this.versionSpec = versionSpec;
		this.askForBranch = false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public ProjectSpace doRun(IProgressMonitor progressMonitor) throws EmfStoreException {
		if (askForBranch) {
			if (versionSpec == null) {
				versionSpec = branchSelection(serverInfo, projectInfo);
			}
		}
		return new ServerCall<ProjectSpace>(serverInfo, progressMonitor) {
			@Override
			protected ProjectSpace run() throws EmfStoreException {

				if (versionSpec == null) {
					return WorkspaceManager.getInstance().getCurrentWorkspace()
						.checkout(getUsersession(), projectInfo, getProgressMonitor());
				}

				return WorkspaceManager.getInstance().getCurrentWorkspace()
					.checkout(getUsersession(), projectInfo, versionSpec, getProgressMonitor());
			}
		}.execute();
	}

	private PrimaryVersionSpec branchSelection(ServerInfo serverInfo, ProjectInfo projectInfo) throws EmfStoreException {
		final List<BranchInfo> branches = ((WorkspaceImpl) WorkspaceManager.getInstance().getCurrentWorkspace())
			.getBranches(serverInfo, projectInfo.getProjectId());

		BranchInfo result = new RunInUIThreadWithResult<BranchInfo>(getShell()) {
			@Override
			public BranchInfo doRun(Shell shell) {
				BranchSelectionDialog.CheckoutSelection dialog = new BranchSelectionDialog.CheckoutSelection(
					getShell(), branches);
				dialog.setBlockOnOpen(true);

				if (dialog.open() != Dialog.OK || dialog.getResult() == null) {
					// TODO BRANCH ask eddy
					// throw new EmfStoreException("No Branch specified");
					return null;
				}
				return dialog.getResult();
			}
		}.execute();

		if (result == null) {
			return null;
		}
		return result.getHead();
	}
}
