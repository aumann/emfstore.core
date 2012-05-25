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
package org.eclipse.emf.emfstore.client.model.controller;

import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.model.observers.CommitObserver;
import org.eclipse.emf.emfstore.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;

/**
 * The controller responsible for performing a commit.
 * 
 * @author wesendon
 */
public class CommitController extends ServerCall<PrimaryVersionSpec> {

	private LogMessage logMessage;
	private CommitCallback callback;
	private BranchVersionSpec branch;

	/**
	 * Constructor.
	 * 
	 * @param projectSpace
	 *            the project space whose pending changes should be commited
	 * @param logMessage
	 *            a log message documenting the commit
	 * @param callback
	 *            an callback that will be called during and at the end of the commit.
	 *            May be <code>null</code>.
	 * @param monitor
	 *            an {@link IProgressMonitor} that will be used to inform clients about the commit progress.
	 *            May be <code>null</code>.
	 */
	public CommitController(ProjectSpaceBase projectSpace, LogMessage logMessage, CommitCallback callback,
		IProgressMonitor monitor) {
		this(projectSpace, null, logMessage, callback, monitor);
	}

	/**
	 * Branching Constructor.
	 * 
	 * @param projectSpace
	 *            the project space whose pending changes should be commited
	 * @param branch Specification of the branch to which the changes should be commited.
	 * @param logMessage
	 *            a log message documenting the commit
	 * @param callback
	 *            an callback that will be called during and at the end of the commit.
	 *            May be <code>null</code>.
	 * @param monitor
	 *            an {@link IProgressMonitor} that will be used to inform clients about the commit progress.
	 *            May be <code>null</code>.
	 */
	public CommitController(ProjectSpaceBase projectSpace, BranchVersionSpec branch, LogMessage logMessage,
		CommitCallback callback, IProgressMonitor monitor) {
		super(projectSpace);
		this.branch = branch;
		this.logMessage = (logMessage == null) ? createLogMessage() : logMessage;
		this.callback = callback == null ? CommitCallback.NOCALLBACK : callback;
		setProgressMonitor(monitor);
	}

	@Override
	protected PrimaryVersionSpec run() throws EmfStoreException {
		return commit(this.logMessage, this.branch);
	}

	private PrimaryVersionSpec commit(LogMessage logMessage, BranchVersionSpec branch) throws EmfStoreException {
		getProgressMonitor().beginTask("Commiting Changes", 100);
		getProgressMonitor().worked(1);

		getProgressMonitor().subTask("Checking changes");
		// check if there are any changes
		if (!getProjectSpace().isDirty()) {
			callback.noLocalChanges(getProjectSpace());
			return getProjectSpace().getBaseVersion();
		}
		getProjectSpace().cleanCutElements();

		getProgressMonitor().subTask("Resolving new version");

		if (branch != null) {
			// TODO BRANCH
			PrimaryVersionSpec resolvedVersion = getProjectSpace().resolveVersionSpec(branch);
			if (resolvedVersion != null) {
				// TODO BRANCH merge should have own controller
				throw new EmfStoreException("Branch already exists. You need to merge.");
			}
		} else {
			// check if we need to update first
			PrimaryVersionSpec resolvedVersion = getProjectSpace().resolveVersionSpec(
				Versions.HEAD_VERSION(getProjectSpace().getBaseVersion()));
			if (!getProjectSpace().getBaseVersion().equals(resolvedVersion)) {
				if (!callback.baseVersionOutOfDate(getProjectSpace())) {
					throw new BaseVersionOutdatedException();
				}
			}
		}

		getProgressMonitor().worked(10);
		getProgressMonitor().subTask("Gathering changes");
		ChangePackage changePackage = getProjectSpace().getLocalChangePackage();
		changePackage.setLogMessage(logMessage);
		WorkspaceManager.getObserverBus().notify(CommitObserver.class).inspectChanges(getProjectSpace(), changePackage);

		getProgressMonitor().subTask("Presenting Changes");
		if (!callback.inspectChanges(getProjectSpace(), changePackage) || getProgressMonitor().isCanceled()) {
			return getProjectSpace().getBaseVersion();
		}

		getProgressMonitor().subTask("Sending files to server");
		// TODO reimplement with ObserverBus and think about subtasks for commit
		getProjectSpace().getFileTransferManager().uploadQueuedFiles(getProgressMonitor());
		getProgressMonitor().worked(30);

		getProgressMonitor().subTask("Sending changes to server");

		PrimaryVersionSpec newBaseVersion = null;

		// TODO BRANCH
		// Branching case: branch specifier added
		newBaseVersion = getConnectionManager().createVersion(getUsersession().getSessionId(),
			getProjectSpace().getProjectId(), getProjectSpace().getBaseVersion(), changePackage, branch, null,
			changePackage.getLogMessage());

		getProgressMonitor().worked(35);

		getProgressMonitor().subTask("Finalizing commit");
		getProjectSpace().setBaseVersion(newBaseVersion);
		getProjectSpace().getOperations().clear();

		getProjectSpace().saveProjectSpaceOnly();

		WorkspaceManager.getObserverBus().notify(CommitObserver.class)
			.commitCompleted(getProjectSpace(), newBaseVersion);

		getProjectSpace().updateDirtyState();
		return newBaseVersion;
	}

	private LogMessage createLogMessage() {
		LogMessage logMessage = VersioningFactory.eINSTANCE.createLogMessage();
		String commiter = "UNKOWN";
		if (getProjectSpace().getUsersession() != null && getProjectSpace().getUsersession().getACUser() != null
			&& getProjectSpace().getUsersession().getACUser().getName() != null) {
			commiter = getProjectSpace().getUsersession().getACUser().getName();
		}
		logMessage.setAuthor(commiter);
		logMessage.setClientDate(new Date());
		logMessage.setMessage("");
		return logMessage;
	}

}
