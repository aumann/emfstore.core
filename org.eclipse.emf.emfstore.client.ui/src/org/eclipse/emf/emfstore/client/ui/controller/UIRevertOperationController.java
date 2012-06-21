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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for reverting any changes upon a {@link ProjectSpace}.
 * 
 * @author emueller
 * 
 */
public class UIRevertOperationController extends AbstractEMFStoreUIController<Void> {

	private final ProjectSpace projectSpace;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} to be used during the revert of the operations
	 * @param projectSpace
	 *            the {@link ProjectSpace} upon which to revert operations
	 */
	public UIRevertOperationController(Shell shell, ProjectSpace projectSpace) {
		super(shell);
		this.projectSpace = projectSpace;
	}

	@Override
	public Void doRun(IProgressMonitor progressMonitor) throws EmfStoreException {

		String message = "Do you really want to revert all your changes on project " + projectSpace.getProjectName()
			+ "?";

		if (confirm("Confirmation", message)) {
			progressMonitor.beginTask("Revert project...", 100);
			progressMonitor.worked(10);
			projectSpace.revert();
			MessageDialog.openInformation(shell, "Revert", "Reverted project ");
		}

		return null;
	}
}
