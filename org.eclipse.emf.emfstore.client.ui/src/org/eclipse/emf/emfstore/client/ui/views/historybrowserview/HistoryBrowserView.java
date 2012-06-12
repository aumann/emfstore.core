package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class HistoryBrowserView extends ViewPart {

	@Override
	public void createPartControl(Composite parent) {
		CommitGraphTable tab = new CommitGraphTable(parent, null);
		MockCommitProvider provider = new MockCommitProvider();
		List<IMockCommit> commits = new ArrayList<IMockCommit>();

		IMockCommit[] commArr = provider.getCommits();
		for (IMockCommit comm : commArr) {
			commits.add(comm);
		}

		tab.setInput(commits, commArr, true);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public ProjectSpace getProjectSpace() {
		return null;
	}

	public void refresh() {

	}

	public void setInput(ProjectSpace space, EObject obj) {

	}

}