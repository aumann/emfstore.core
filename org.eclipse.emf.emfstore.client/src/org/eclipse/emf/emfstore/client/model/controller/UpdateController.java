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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.model.observers.UpdateObserver;
import org.eclipse.emf.emfstore.server.conflictDetection.ConflictDetector;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;

public class UpdateController extends ServerCall<PrimaryVersionSpec> {

	private VersionSpec version;
	private UpdateCallback callback;

	public UpdateController(ProjectSpaceBase projectSpace, VersionSpec version, UpdateCallback callback,
		IProgressMonitor progress) {
		super(projectSpace);
		/**
		 * SANITY CHECKS
		 */
		if (version == null) {
			version = Versions.HEAD_VERSION(projectSpace.getBaseVersion());
		}
		if (callback == null) {
			callback = UpdateCallback.NOCALLBACK;
		}
		this.version = version;
		this.callback = callback;
		setProgressMonitor(progress);
	}

	@Override
	protected PrimaryVersionSpec run() throws EmfStoreException {
		return doUpdate(version);
	}

	private PrimaryVersionSpec doUpdate(VersionSpec version) throws EmfStoreException {
		getProgressMonitor().beginTask("Updating Project", 100);
		getProgressMonitor().worked(1);
		getProgressMonitor().subTask("Resolving new version");
		final PrimaryVersionSpec resolvedVersion = getProjectSpace().resolveVersionSpec(version);
		if (resolvedVersion.compareTo(getProjectSpace().getBaseVersion()) == 0) {
			return resolvedVersion;
		}
		getProgressMonitor().worked(5);

		if (getProgressMonitor().isCanceled()) {
			return getProjectSpace().getBaseVersion();
		}

		getProgressMonitor().subTask("Fetching changes from server");
		List<ChangePackage> changes = new ArrayList<ChangePackage>();
		changes = getConnectionManager().getChanges(getSessionId(), getProjectSpace().getProjectId(),
			getProjectSpace().getBaseVersion(), resolvedVersion);
		ChangePackage localchanges = getProjectSpace().getLocalChangePackage(false);
		getProgressMonitor().worked(65);

		if (getProgressMonitor().isCanceled()) {
			return getProjectSpace().getBaseVersion();
		}

		getProgressMonitor().subTask("Checking for conflicts");

		ConflictDetector conflictDetector = new ConflictDetector();
		for (ChangePackage change : changes) {
			if (conflictDetector.doConflict(change, localchanges)) {
				if (callback
					.conflictOccurred(new ChangeConflictException(changes, getProjectSpace(), conflictDetector))) {
					return getProjectSpace().getBaseVersion();
				} else {
					throw new ChangeConflictException(changes, getProjectSpace(), conflictDetector);
				}
			}
		}

		getProgressMonitor().worked(15);
		// TODO ASYNC review this cancel
		if (getProgressMonitor().isCanceled() || !callback.inspectChanges(getProjectSpace(), changes)) {
			return resolvedVersion;
			// updateDone(getProjectSpace().getBaseVersion(), null);
		}

		WorkspaceManager.getObserverBus().notify(UpdateObserver.class).inspectChanges(getProjectSpace(), changes);

		getProgressMonitor().subTask("Applying changes");

		getProjectSpace().applyChanges(resolvedVersion, changes, localchanges);

		WorkspaceManager.getObserverBus().notify(UpdateObserver.class).updateCompleted(getProjectSpace());

		return getProjectSpace().getBaseVersion();
	}
}
