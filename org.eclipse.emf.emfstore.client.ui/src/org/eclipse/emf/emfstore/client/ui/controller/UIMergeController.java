package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.MergeProjectHandler;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class UIMergeController extends AbstractEMFStoreUIController {

	public UIMergeController(Shell shell) {
		super(shell);
	}

	public void merge(ProjectSpace projectSpace) throws EmfStoreException {
		PrimaryVersionSpec selectedSource = branchSelection(projectSpace);
		MessageDialog.openInformation(getShell(), "", selectedSource.toString());
		try {
			openProgress();
			((ProjectSpaceBase) projectSpace).mergeBranch(selectedSource, new MergeProjectHandler());
		} finally {
			closeProgress();
		}
	}

	private PrimaryVersionSpec branchSelection(ProjectSpace projectSpace) throws EmfStoreException {

		InputDialog inputDialog = new InputDialog(getShell(), "Branch Selection", "Please enter the branch's name.",
			"", null);
		if (inputDialog.open() != Dialog.OK) {
			throw new EmfStoreException("No Branch specified");
		}
		BranchVersionSpec branchSpec = VersioningFactory.eINSTANCE.createBranchVersionSpec();
		branchSpec.setBranch(inputDialog.getValue());
		return projectSpace.resolveVersionSpec(branchSpec);
	}
}
