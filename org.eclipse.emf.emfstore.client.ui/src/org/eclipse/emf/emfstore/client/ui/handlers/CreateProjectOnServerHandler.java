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
package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UICreateRemoteProjectController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

/**
 * Creates an empty project on a server.
 * 
 * @author emueller
 * 
 */
public class CreateProjectOnServerHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {

		ServerInfo serverInfo = requireSelection(ServerInfo.class);

		if (serverInfo == null || serverInfo.getLastUsersession() == null) {
			return;
		}

		new UICreateRemoteProjectController(getShell(), serverInfo.getLastUsersession()).execute();
	}
}
