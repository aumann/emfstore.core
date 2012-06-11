package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

public interface IMockCommit {

	public PlotLane getLane();

	public PlotLane[] getPassingLanes();

	public int getParentCount();

	public IMockCommit getParent(int i);

	public int getChildCount();

	public int getRefsLength();

	public String getShortMessage();

}
