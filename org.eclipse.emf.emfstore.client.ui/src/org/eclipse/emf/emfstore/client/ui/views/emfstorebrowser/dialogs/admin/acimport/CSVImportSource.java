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
package org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.dialogs.admin.acimport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.client.ui.dialogs.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.client.ui.util.EMFStorePreferenceHelper;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACGroup;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.server.model.accesscontrol.AccesscontrolFactory;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * @author gurcankarakoc, deser
 */

public class CSVImportSource extends ImportSource {

	private static final String CSV_IMPORT_SOURCE_PATH = "org.eclipse.emf.emfstore.client.ui.CSVImportSourcePath";

	private HashMap<String, ImportItemWrapper> groupMap = new HashMap<String, ImportItemWrapper>();

	private ArrayList<ImportItemWrapper> groups;
	private ArrayList<ImportItemWrapper> users;

	private String absFileName;

	/**
	 * Constructor.
	 */
	public CSVImportSource() {
	}

	/**
	 * @see org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.dialogs.admin.acimport.ImportSource#getChildren(java.lang.Object)
	 * @param ob
	 *            the object to get the children from
	 * @return the children of the given object
	 */
	@Override
	public Object[] getChildren(Object ob) {
		ImportItemWrapper importWrapper = (ImportItemWrapper) ob;
		if (importWrapper != null && importWrapper.getChildOrgUnits() != null) {
			return importWrapper.getChildOrgUnits().toArray();
		}
		return null;
	}

	/**
	 * @param ob
	 *            The object to get the root elements from
	 * @return The list of groups, which were read from the specified file.
	 * @see org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.dialogs.admin.acimport.ImportSource#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object ob) {
		return this.groups.toArray();
	}

	/**
	 * @see org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.dialogs.admin.acimport.ImportSource#getLabel()
	 * @return String label.
	 */
	@Override
	public String getLabel() {
		return "import from CSV file";
	}

	/**
	 * @see org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.dialogs.admin.acimport.ImportSource#init()
	 * @param shell
	 *            the shell, which holds the dialog for file selection
	 * @return if a file was selected and successfully handled
	 */
	@Override
	public boolean init(Shell shell) {
		// clear old data
		groups = new ArrayList<ImportItemWrapper>();
		users = new ArrayList<ImportItemWrapper>();

		FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.OPEN);
		dialog.setText("Choose import file");
		String initialPath = EMFStorePreferenceHelper.getPreference(CSV_IMPORT_SOURCE_PATH,
			System.getProperty("user.home"));
		dialog.setFilterPath(initialPath);
		String fn = dialog.open();
		if (fn == null) {
			return false;
		}

		String fileName = dialog.getFileName();
		String filterPath = dialog.getFilterPath();
		if (fileName == null) {
			return false;
		}

		this.absFileName = filterPath + File.separatorChar + fileName;
		final File file = new File(absFileName);
		EMFStorePreferenceHelper.setPreference(CSV_IMPORT_SOURCE_PATH, filterPath);
		BufferedReader bufferedReader = null;
		InputStreamReader isr = null;

		try {
			isr = new InputStreamReader(new FileInputStream(file)); // "8859_1","ASCII"
			bufferedReader = new BufferedReader(isr);
			String line = bufferedReader.readLine();

			int indexUserName = 0;
			int indexForGroup = 1;

			while ((line = bufferedReader.readLine()) != null) {
				// Get the user information from the next line
				String[] title = line.split(",");

				String userName = title[indexUserName];
				String groupName = title[indexForGroup];

				ImportItemWrapper importWrapper = null;
				ArrayList<ImportItemWrapper> childOrgUnits;
				if (groupMap.get(groupName) == null) {
					ACGroup group = AccesscontrolFactory.eINSTANCE.createACGroup();
					importWrapper = new ImportItemWrapper(null, group);

					group.setName(groupName);
					groups.add(importWrapper);
					groupMap.put(groupName, importWrapper);
					childOrgUnits = new ArrayList<ImportItemWrapper>();
				} else {
					importWrapper = groupMap.get(groupName);
					childOrgUnits = importWrapper.getChildOrgUnits();
				}

				ACUser user = AccesscontrolFactory.eINSTANCE.createACUser();
				user.setName(userName);
				ImportItemWrapper userImportWrapper = new ImportItemWrapper(null, user, importWrapper);
				users.add(userImportWrapper);

				childOrgUnits.add(userImportWrapper);
				importWrapper.setChildOrgUnits(childOrgUnits);

			}

			bufferedReader.close();
			isr.close();

		} catch (FileNotFoundException e) {
			// TODO: sensible error messages
			WorkspaceUtil.logWarning(e.getMessage(), e);
			EMFStoreMessageDialog.showExceptionDialog("File not found", e);
			return false;
		} catch (IOException e) {
			WorkspaceUtil.logWarning(e.getMessage(), e);
			EMFStoreMessageDialog.showExceptionDialog("An I/O-exception occured", e);
			return false;
		} catch (ArrayIndexOutOfBoundsException e) {
			WorkspaceUtil.logWarning(e.getMessage(), e);
			EMFStoreMessageDialog.showExceptionDialog("ArrayIndexOutOfBoundsException", e);
			return false;
		}

		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.dialogs.admin.acimport.ImportSource#getMessage()
	 */
	@Override
	public String getMessage() {
		return "Importing from file: " + this.absFileName;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		// Nothing to dispose
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object,
	 *      java.lang.Object)
	 */
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// Nothing to change
	}
}
