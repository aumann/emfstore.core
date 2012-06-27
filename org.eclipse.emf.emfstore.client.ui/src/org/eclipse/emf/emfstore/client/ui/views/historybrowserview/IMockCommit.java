package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.Date;
import java.util.List;

import org.eclipse.swt.widgets.Widget;

public interface IMockCommit {

	public PlotLane getLane();

	public void setLane(PlotLane lane);

	public void addPassingLane(PlotLane c);

	public PlotLane[] getPassingLanes();

	public void setParents(List<IMockCommit> parents);

	public int getParentCount();

	public IMockCommit getParent(int i);

	public void addChild(IMockCommit child);

	public IMockCommit getChild(int childId);

	public int getChildCount();

	public boolean isChild(IMockCommit commit);

	public int getRefsLength();

	public String getShortMessage();

	public void setIsRealCommit(boolean isReal);

	public boolean isRealCommit();

	public Widget getWidget();

	public void setWidget(Widget widget);

	public String getBranch();

	public String getCommitterName();

	public String getId();

	public Date getCommitDate();

	public void dispose();
}
