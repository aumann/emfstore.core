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
package org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.dialogs.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.emfstore.client.model.AdminBroker;
import org.eclipse.emf.emfstore.client.ui.Activator;
import org.eclipse.emf.emfstore.client.ui.dialogs.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.dialogs.admin.acimport.wizard.AcUserImportAction;
import org.eclipse.emf.emfstore.server.ServerConfiguration;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACOrgUnit;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.server.model.accesscontrol.roles.Role;
import org.eclipse.emf.emfstore.server.model.accesscontrol.roles.ServerAdmin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * @author gurcankarakoc, deser
 */
public class UserTabContent extends TabContent implements IPropertyChangeListener {

	/**
	 * Action to delete a user.
	 * 
	 * @author koegel
	 */
	private final class DeleteUserAction extends Action {
		private DeleteUserAction(String text) {
			super(text);
		}

		@Override
		public void run() {
			IStructuredSelection selection = (IStructuredSelection) getTableViewer().getSelection();
			Iterator<?> iterator = selection.iterator();

			while (iterator.hasNext()) {
				ACUser ou = (ACUser) iterator.next();
				if (ou == null) {
					return;
				}

				try {
					String superUser = ServerConfiguration.getProperties().getProperty(ServerConfiguration.SUPER_USER,
						ServerConfiguration.SUPER_USER_DEFAULT);
					boolean isAdmin = false;
					for (Iterator<Role> it = ou.getRoles().iterator(); it.hasNext();) {
						Role userRole = it.next();
						if ((ou.getName().compareTo(superUser) == 0) && (userRole instanceof ServerAdmin)) {
							isAdmin = true;
							break;
						}
					}
					if (isAdmin) {
						Display display = Display.getCurrent();
						MessageDialog.openInformation(display.getActiveShell(), "Illegal deletion attempt",
							"It is not allowed to delete the super user!");
					} else {
						getAdminBroker().deleteUser(ou.getId());
					}
				} catch (EmfStoreException e) {
					EMFStoreMessageDialog.showExceptionDialog(e);
				}

				if (getForm().getCurrentInput() instanceof ACOrgUnit && getForm().getCurrentInput().equals(ou)) {
					getForm().setInput(null);
				}
			}
			getTableViewer().refresh();
		}
	}

	/**
	 * @param string the name of tab.
	 * @param adminBroker AdminBroker is needed to communicate with server.
	 * @param frm used to set input to properties form and update its table viewer upon. deletion of OrgUnits.
	 */
	public UserTabContent(String string, AdminBroker adminBroker, PropertiesForm frm) {
		super(string, adminBroker, frm);
		this.setTab(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.dialogs.admin.TabContent#initActions()
	 */
	@Override
	protected List<Action> initActions() {
		Action createNewUser = new Action("Create new user") {
			@Override
			public void run() {
				try {
					getAdminBroker().createUser("New User");
				} catch (EmfStoreException e) {
					EMFStoreMessageDialog.showExceptionDialog(e);
				}
				getTableViewer().refresh();
				getForm().getTableViewer().refresh();

			}
		};
		createNewUser.setImageDescriptor(Activator.getImageDescriptor("icons/user.png"));
		createNewUser.setToolTipText("Create new user");

		Action deleteUser = new DeleteUserAction("Delete user");
		deleteUser.setImageDescriptor(Activator.getImageDescriptor("icons/delete.gif"));
		deleteUser.setToolTipText("Delete user");

		Action importOrgUnit = new AcUserImportAction(getAdminBroker());
		importOrgUnit.addPropertyChangeListener(this);

		return Arrays.asList(createNewUser, deleteUser, importOrgUnit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ITableLabelProvider getLabelProvider() {
		return new ITableLabelProvider() {

			public void addListener(ILabelProviderListener listener) {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public void removeListener(ILabelProviderListener listener) {
			}

			public void dispose() {
			}

			public Image getColumnImage(Object element, int columnIndex) {
				return Activator.getImageDescriptor("icons/user.png").createImage();
			}

			public String getColumnText(Object element, int columnIndex) {
				return ((ACUser) element).getName();
			}

		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IStructuredContentProvider getContentProvider() {
		return new IStructuredContentProvider() {

			public Object[] getElements(Object inputElement) {
				// return a list of Users in project space
				List<ACUser> users = new ArrayList<ACUser>();
				try {
					users.addAll(getAdminBroker().getUsers());
				} catch (EmfStoreException e) {
					EMFStoreMessageDialog.showExceptionDialog(e);
				}
				return users.toArray(new ACUser[users.size()]);
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		};
	}

	/**
	 * Refresh the tableViewer after a property change. (Used e.g. after importing users via e.g. CSV.)
	 * 
	 * @param event The event to deal with.
	 */
	public void propertyChange(PropertyChangeEvent event) {
		getTableViewer().refresh();
	}

}
