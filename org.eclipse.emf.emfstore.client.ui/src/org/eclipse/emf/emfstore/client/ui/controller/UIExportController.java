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

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.importexport.ExportImportControllerFactory;
import org.eclipse.emf.emfstore.client.ui.util.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for exporting
 * <ul>
 * <li>
 * projects</li>
 * <li>
 * project spaces</li>
 * <li>
 * local changes</li>
 * <li>
 * project history of a project</li>
 * <li>
 * the whole workspace.</li>
 * </ul>
 * 
 * @author emueller
 * 
 */
public class UIExportController {

	private Shell shell;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} to be used during an export
	 */
	public UIExportController(Shell shell) {
		this.shell = shell;
	}

	private Shell getShell() {
		return shell;
	}

	/**
	 * Exports the history of a project.
	 * 
	 * @param projectInfo
	 *            the {@link ProjectInfo} that contains the information about the project whose history should get
	 *            exported
	 */
	public void exportProjectHistory(ProjectInfo projectInfo) {
		try {
			new UIGenericExportImportController(getShell(),
				ExportImportControllerFactory.Export.getExportProjectHistoryController(projectInfo)).execute(false,
				false);
		} catch (EmfStoreException e) {
			EMFStoreMessageDialog.showExceptionDialog(getShell(), "Could not export project history", e);
		}
	}

	/**
	 * Exports the history of a project.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} that contains the local changes that should get exported
	 */
	public void exportChanges(ProjectSpace projectSpace) {
		try {
			new UIGenericExportImportController(getShell(),
				ExportImportControllerFactory.Export.getExportChangesController(projectSpace)).execute(false, false);
		} catch (EmfStoreException e) {
			EMFStoreMessageDialog.showExceptionDialog(getShell(), "Could not export changes", e);
		}
	}

	/**
	 * Exports a project.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} that contains the project that should get exported
	 */
	public void exportProject(ProjectSpace projectSpace) {
		try {
			new UIGenericExportImportController(getShell(),
				ExportImportControllerFactory.Export.getExportProjectController(projectSpace)).execute(false, false);
		} catch (EmfStoreException e) {
			EMFStoreMessageDialog.showExceptionDialog(getShell(), "Could not project", e);
		}
	}

	/**
	 * Exports the whole workspace.
	 * 
	 */
	public void exportWorkspace() {
		try {
			new UIGenericExportImportController(getShell(),
				ExportImportControllerFactory.Export.getExportWorkspaceController()).execute(false, false);
		} catch (EmfStoreException e) {
			EMFStoreMessageDialog.showExceptionDialog(getShell(), "Could not export workspace", e);
		}
	}

	/**
	 * Exports a {@link ProjectSpace}.
	 * 
	 * @param projectSpace
	 *            the project space that should get exported
	 */
	public void exportProjectSpace(ProjectSpace projectSpace) {
		try {
			new UIGenericExportImportController(getShell(),
				ExportImportControllerFactory.Export.getExportProjectSpaceController(projectSpace)).execute(false,
				false);
		} catch (EmfStoreException e) {
			EMFStoreMessageDialog.showExceptionDialog(getShell(), "Could not export project space", e);
		}
	}
}
