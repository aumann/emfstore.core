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
package org.eclipse.emf.emfstore.client.ui.util;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.model.PostWorkspaceInitiator;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.observers.LoginObserver;
import org.eclipse.emf.emfstore.client.model.observers.LogoutObserver;
import org.eclipse.emf.emfstore.client.model.observers.ShareObserver;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

/**
 * This class is responsible for keeping the workspace's project infos update to date.
 * 
 */
public class ProjectListUpdater implements PostWorkspaceInitiator, ShareObserver, LoginObserver, LogoutObserver {

	private Workspace workspace;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.PostWorkspaceInitiator#workspaceInitComplete(org.eclipse.emf.emfstore.client.model.Workspace)
	 */
	public void workspaceInitComplete(Workspace currentWorkspace) {
		this.workspace = currentWorkspace;
		WorkspaceManager.getObserverBus().register(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observers.LoginObserver#loginCompleted(org.eclipse.emf.emfstore.client.model.Usersession)
	 */
	public void loginCompleted(Usersession session) {
		try {
			update(session);
		} catch (EmfStoreException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't project infos upon loginCompleted.", e);
		}
		updateACUser(session);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observers.ShareObserver#shareDone(org.eclipse.emf.emfstore.client.model.ProjectSpace)
	 */
	public void shareDone(ProjectSpace projectSpace) {
		try {
			update(projectSpace.getUsersession());
		} catch (EmfStoreException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't project infos upon shareDone.", e);
		}
	}

	private void updateACUser(Usersession session) {
		try {
			workspace.updateACUser(session);
		} catch (EmfStoreException e) {
			// fail silently
			WorkspaceUtil.logException("Couldn't update ACUser.", e);
		}
	}

	private void update(final Usersession session) throws EmfStoreException {
		RunInUI.WithException.run(new Callable<Void>() {
			public Void call() throws Exception {
				workspace.updateProjectInfos(session);
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observers.LogoutObserver#logoutCompleted(org.eclipse.emf.emfstore.client.model.Usersession)
	 */
	public void logoutCompleted(Usersession session) {
		ServerInfo serverInfo = session.getServerInfo();
		if (serverInfo != null) {
			serverInfo.getProjectInfos().clear();
		}
	}

}
