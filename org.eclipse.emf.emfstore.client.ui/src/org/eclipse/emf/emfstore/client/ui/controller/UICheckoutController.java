package org.eclipse.emf.emfstore.client.ui.controller;

import java.util.List;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.impl.WorkspaceImpl;
import org.eclipse.emf.emfstore.client.ui.dialogs.BranchSelectionDialog;
import org.eclipse.emf.emfstore.client.ui.dialogs.BranchSelectionDialog.CheckoutSelection;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

public class UICheckoutController extends AbstractEMFStoreUIController {

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 */
	public UICheckoutController(Shell shell) {
		super(shell);
	}

	public void checkout(ServerInfo serverInfo, ProjectInfo projectInfo) throws EmfStoreException {
		checkout(serverInfo, projectInfo, null);
	}

	public void checkout(ServerInfo serverInfo, final ProjectInfo projectInfo, final PrimaryVersionSpec versionSpec)
		throws EmfStoreException {
		new ServerCall<Void>(serverInfo) {
			@Override
			protected Void run() throws EmfStoreException {
				if (versionSpec == null) {
					WorkspaceManager.getInstance().getCurrentWorkspace().checkout(getUsersession(), projectInfo);
				} else {
					WorkspaceManager.getInstance().getCurrentWorkspace()
						.checkout(getUsersession(), projectInfo, versionSpec);
				}
				return null;
			}
		}.execute();
	}

	public void checkoutBranch(ServerInfo serverInfo, ProjectInfo projectInfo) throws EmfStoreException {
		checkout(serverInfo, projectInfo, branchSelection(serverInfo, projectInfo));
	}

	private PrimaryVersionSpec branchSelection(ServerInfo serverInfo, ProjectInfo projectInfo) throws EmfStoreException {

		List<BranchInfo> branches = ((WorkspaceImpl) WorkspaceManager.getInstance().getCurrentWorkspace()).getBranches(
			serverInfo, projectInfo.getProjectId());

		CheckoutSelection dialog = new BranchSelectionDialog.CheckoutSelection(getShell(), branches);
		dialog.setBlockOnOpen(true);

		if (dialog.open() != Dialog.OK || dialog.getResult() == null) {
			return null;
		}
		return dialog.getResult().getHead();
	}
}
