package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UICommitProjectToBranchController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class CommitProjectToBranchHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UICommitProjectToBranchController(getShell()).commit(requireSelection(ProjectSpace.class), null);
	}

}
