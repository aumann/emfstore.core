package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.Date;
import java.util.List;

import org.eclipse.swt.graphics.Color;

public interface IPlotCommit {

	public PlotLane getLane();

	public void setLane(PlotLane lane);

	public void addPassingLane(PlotLane c);

	public PlotLane[] getPassingLanes();

	public void setParents(List<IPlotCommit> parents);

	public int getParentCount();

	public IPlotCommit getParent(int i);

	public void addChild(IPlotCommit child);

	public IPlotCommit getChild(int childId);

	public int getChildCount();

	public boolean isChild(IPlotCommit commit);

	public boolean isLocalHistoryOnly();

	public String getShortMessage();

	public void setIsRealCommit(boolean isReal);

	public boolean isRealCommit();

	public String getBranch();

	public String getCommitterName();

	public String getId();

	public Date getCommitDate();

	public void dispose();

	public void setColor(Color color);

	public Color getColor();

	public void setLightColor(Color color);

	public Color getLightColor();
}
