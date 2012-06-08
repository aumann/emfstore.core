package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UICreateBranchController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class CreateBranchHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UICreateBranchController(getShell()).commitToBranch(requireSelection(ProjectSpace.class), null);
	}

}
