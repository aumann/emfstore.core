package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback;
import org.eclipse.emf.emfstore.client.ui.dialogs.CommitDialog;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class UICreateBranchController extends AbstractEMFStoreUIController implements CommitCallback {

	public UICreateBranchController(Shell shell) {
		super(shell);
	}

	public PrimaryVersionSpec commit(ProjectSpace projectSpace, BranchVersionSpec branch) throws EmfStoreException {
		return commit(projectSpace, branch, null);
	}

	public PrimaryVersionSpec commit(ProjectSpace projectSpace, BranchVersionSpec branch, LogMessage logMessage)
		throws EmfStoreException {
		if (branch == null) {
			branch = branchSelection(projectSpace);
		}
		openProgress();
		PrimaryVersionSpec commit = projectSpace.commitToBranch(branch, logMessage, this, getProgressMonitor());
		closeProgress();
		return commit;
	}

	private BranchVersionSpec branchSelection(ProjectSpace projectSpace) throws EmfStoreException {

		InputDialog inputDialog = new InputDialog(getShell(), "Branch Selection", "Please enter the branch's name.",
			"", null);
		if (inputDialog.open() != Dialog.OK) {
			throw new EmfStoreException("No Branch specified");
		}
		return Versions.BRANCH(inputDialog.getValue());
	}

	public void noLocalChanges(ProjectSpace projectSpace) {
		MessageDialog.openInformation(getShell(), null, "No local changes in your project. No need to commit.");
		closeProgress();
	}

	public boolean baseVersionOutOfDate(ProjectSpace projectSpace) {
		String message = "Your project is outdated, you need to update before commit. Do you want to update now?";
		if (confirmationDialog(message)) {
			// TODO results?
			try {
				new UIUpdateProjectController(getShell()).update(projectSpace);
			} catch (EmfStoreException e) {
				handleException(e);
			}
		}
		closeProgress();
		return true;
	}

	public boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage) {
		if (changePackage.getOperations().isEmpty()) {
			MessageDialog.openInformation(getShell(), "No local changes",
				"Your local changes were mutually exclusive.\nThey are no changes pending for commit.");
			return false;
		}
		CommitDialog commitDialog = new CommitDialog(getShell(), changePackage, projectSpace);
		if (commitDialog.open() == Dialog.OK) {
			changePackage.getLogMessage().setMessage(commitDialog.getLogText());
			return true;
		}
		return false;
	}
}
