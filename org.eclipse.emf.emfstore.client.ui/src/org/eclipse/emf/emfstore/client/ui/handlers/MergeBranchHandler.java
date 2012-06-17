package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIMergeController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class MergeBranchHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIMergeController(getShell()).merge(requireSelection(ProjectSpace.class));
	}

}