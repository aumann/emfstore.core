package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.Date;

import org.eclipse.swt.widgets.Widget;

public interface IMockCommit {

	public PlotLane getLane();

	public PlotLane[] getPassingLanes();

	public int getParentCount();

	public IMockCommit getParent(int i);

	public int getChildCount();

	public int getRefsLength();

	public String getShortMessage();

	public void setIsRealCommit(boolean isReal);

	public boolean isRealCommit();

	public Widget getWidget();

	public void setWidget(Widget widget);

	public String getCommitterName();

	public String getId();

	public Date getCommitDate();

	public void dispose();

}
