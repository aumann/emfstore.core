package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;

public interface ICommitProvider {
	public IPlotCommit getCommitFor(HistoryInfo info, boolean onlyAChildRequest);

}
