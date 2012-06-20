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

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.swt.widgets.Shell;

public class UIDeleteProjectController extends AbstractEMFStoreUIController<Void> {

	private final ProjectSpace projectSpace;

	public UIDeleteProjectController(Shell shell, ProjectSpace projectSpace) {
		super(shell);
		this.projectSpace = projectSpace;
	}

	public void deleteProject(final ProjectSpace projectSpace) {
		final Workspace currentWorkspace = WorkspaceManager.getInstance().getCurrentWorkspace();
		try {
			currentWorkspace.deleteProjectSpace(projectSpace);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// Do NOT catch all Exceptions ("catch (Exception e)")
			// Log AND handle Exceptions if possible
			//
			// You can just uncomment one of the lines below to log an exception:
			// logException will show the logged excpetion to the user
			// ModelUtil.logException(e);
			// ModelUtil.logException("YOUR MESSAGE HERE", e);
			// logWarning will only add the message to the error log
			// ModelUtil.logWarning("YOUR MESSAGE HERE", e);
			// ModelUtil.logWarning("YOUR MESSAGE HERE");
			//
			// If handling is not possible declare and rethrow Exception
			e.printStackTrace();
		}
	}

	private boolean confirmation(final ProjectSpace projectSpace) {
		String message = "Do you really want to delete your local copy of project \"" + projectSpace.getProjectName()
			+ "\n";

		if (projectSpace.getBaseVersion() != null) {
			message += " in version " + projectSpace.getBaseVersion().getIdentifier();
		}

		message += " ?";

		return confirm("Confirmation", message);
	}

	@Override
	public Void doRun(IProgressMonitor pm) throws EmfStoreException {

		if (!confirmation(projectSpace)) {
			return null;
		}

		deleteProject(projectSpace);
		return null;
	}

}
