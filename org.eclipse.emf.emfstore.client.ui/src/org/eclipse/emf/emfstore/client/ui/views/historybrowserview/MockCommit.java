package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Widget;

public class MockCommit implements IPlotCommit {

	PlotLane lane;

	PlotLane[] passingLanes;

	ArrayList<IPlotCommit> parents;

	ArrayList<IPlotCommit> children;

	String shortMsg;

	private Widget widget;

	private boolean isRealCommit;

	public MockCommit() {
		this.lane = null;
		this.passingLanes = new PlotLane[0];
		this.parents = new ArrayList<IPlotCommit>();
		this.children = new ArrayList<IPlotCommit>();
		this.shortMsg = "foo";
	}

	public PlotLane getLane() {
		return lane;
	}

	public PlotLane[] getPassingLanes() {
		return passingLanes;
	}

	public int getParentCount() {
		return parents.size();
	}

	public IPlotCommit getParent(int i) {
		return parents.get(i);
	}

	public int getChildCount() {
		return children.size();
	}

	public int getRefsLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getShortMessage() {
		return shortMsg;
	}

	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;

	}

	public boolean isChild(IPlotCommit c) {
		for (IPlotCommit mc : children) {
			if (mc == c)
				return true;
		}
		return false;
	}

	public String getCommitterName() {
		return "Pot Committed";
	}

	public String getId() {
		return "co_38423";
	}

	public Date getCommitDate() {
		return new Date(2008, 11, 12);
	}

	public void dispose() {
		if (widget != null)
			widget.dispose();
	}

	public void addPassingLane(final PlotLane c) {
		final int cnt = passingLanes.length;
		if (cnt == 0)
			passingLanes = new PlotLane[] { c };
		else if (cnt == 1)
			passingLanes = new PlotLane[] { passingLanes[0], c };
		else {
			final PlotLane[] n = new PlotLane[cnt + 1];
			System.arraycopy(passingLanes, 0, n, 0, cnt);
			n[cnt] = c;
			passingLanes = n;
		}
	}

	public void setIsRealCommit(boolean isReal) {
		isRealCommit = isReal;
	}

	public boolean isRealCommit() {
		return isRealCommit;
	}

	public void setLane(PlotLane lane) {
		// TODO Auto-generated method stub

	}

	public void setParents(List<IPlotCommit> parents) {
		// TODO Auto-generated method stub

	}

	public void addChild(IPlotCommit child) {
		// TODO Auto-generated method stub

	}

	public IPlotCommit getChild(int childId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getBranch() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isLocalHistoryOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setColor(Color color) {
		// TODO Auto-generated method stub

	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLightColor(Color color) {
		// TODO Auto-generated method stub

	}

	public Color getLightColor() {
		// TODO Auto-generated method stub
		return null;
	}
}
