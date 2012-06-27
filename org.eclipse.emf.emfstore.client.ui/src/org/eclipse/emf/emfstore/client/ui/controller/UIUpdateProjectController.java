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
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.client.ui.dialogs.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.client.ui.dialogs.UpdateDialog;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.MergeProjectHandler;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for updating a project.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class UIUpdateProjectController extends AbstractEMFStoreUIController<PrimaryVersionSpec> implements
	UpdateCallback {

	private final ProjectSpace projectSpace;
	private VersionSpec version;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the {@link Shell} that will be used during the update
	 * @param projectSpace
	 *            the {@link ProjectSpace} that should get updated
	 */
	public UIUpdateProjectController(Shell shell, ProjectSpace projectSpace) {
		super(shell, true, true);
		this.projectSpace = projectSpace;
		version = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the {@link Shell} that will be used during the update
	 * @param projectSpace
	 *            the {@link ProjectSpace} that should get updated
	 * @param version
	 *            the version to update to
	 */
	public UIUpdateProjectController(Shell shell, ProjectSpace projectSpace, VersionSpec version) {
		super(shell);
		this.projectSpace = projectSpace;
		this.version = version;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback#noChangesOnServer()
	 */
	public void noChangesOnServer() {
		RunInUI.run(new Callable<Void>() {
			public Void call() throws Exception {
				MessageDialog.openInformation(getShell(), "No need to update",
					"Your project is up to date, you do not need to update.");
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback#conflictOccurred(org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException)
	 */
	public boolean conflictOccurred(final ChangeConflictException conflictException) {
		final ProjectSpace projectSpace = conflictException.getProjectSpace();
		boolean mergeSuccessful = false;
		try {
			final PrimaryVersionSpec targetVersion = projectSpace.resolveVersionSpec(VersionSpec.HEAD_VERSION);
			// merge opens up a dialog
			mergeSuccessful = RunInUI.WithException.runWithResult(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return projectSpace.merge(targetVersion, new MergeProjectHandler());
				}
			});
		} catch (EmfStoreException e) {
			handleMergeException(projectSpace, e);
		}

		return mergeSuccessful;
	}

	private void handleMergeException(final ProjectSpace projectSpace, EmfStoreException e) {
		WorkspaceUtil.logException(
			String.format("Exception while merging the project %s!", projectSpace.getProjectName()), e);
		EMFStoreMessageDialog.showExceptionDialog(getShell(), e);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback#inspectChanges(org.eclipse.emf.emfstore.client.model.ProjectSpace,
	 *      java.util.List)
	 */
	public boolean inspectChanges(final ProjectSpace projectSpace, final List<ChangePackage> changePackages) {
		return RunInUI.runWithResult(new Callable<Boolean>() {
			public Boolean call() throws Exception {
				UpdateDialog updateDialog = new UpdateDialog(getShell(), projectSpace, changePackages);
				if (updateDialog.open() == Window.OK) {
					return true;
				}
				return false;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public PrimaryVersionSpec doRun(final IProgressMonitor monitor) throws EmfStoreException {
		PrimaryVersionSpec oldBaseVersion = projectSpace.getBaseVersion();

		PrimaryVersionSpec resolveVersionSpec = WorkspaceManager.getInstance().getCurrentWorkspace()
			.resolveVersionSpec(projectSpace.getUsersession(), version.HEAD_VERSION, projectSpace.getProjectId());

		if (oldBaseVersion.equals(resolveVersionSpec)) {
			noChangesOnServer();
			return oldBaseVersion;
		}

		PrimaryVersionSpec newBaseVersion = RunInUI.WithException.runWithResult(new Callable<PrimaryVersionSpec>() {
			public PrimaryVersionSpec call() throws Exception {
				return projectSpace.update(version, UIUpdateProjectController.this, monitor);
			}
		});

		return newBaseVersion;
	}
}
