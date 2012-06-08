package org.eclipse.emf.emfstore.client.ui.controller;

import java.util.List;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.ui.dialogs.BranchSelectionDialog;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.MergeProjectHandler;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

public class UIMergeController extends AbstractEMFStoreUIController {

	public UIMergeController(Shell shell) {
		super(shell);
	}

	public void merge(ProjectSpace projectSpace) throws EmfStoreException {
		// TODO BRANCH
		try {
			PrimaryVersionSpec selectedSource = branchSelection(projectSpace);
			openProgress();
			// TODO BRANCH
			((ProjectSpaceBase) projectSpace).mergeBranch(selectedSource, new MergeProjectHandler());
		} finally {
			closeProgress();
		}
	}

	private PrimaryVersionSpec branchSelection(ProjectSpace projectSpace) throws EmfStoreException {

		List<BranchInfo> branches = ((ProjectSpaceBase) projectSpace).getBranches();
		BranchSelectionDialog dialog = new BranchSelectionDialog(getShell(), projectSpace.getBaseVersion(), branches);
		dialog.setBlockOnOpen(true);

		if (dialog.open() != Dialog.OK || dialog.getResult() == null) {
			throw new EmfStoreException("No Branch specified");
		}
		return dialog.getResult().getHead();
	}
}
