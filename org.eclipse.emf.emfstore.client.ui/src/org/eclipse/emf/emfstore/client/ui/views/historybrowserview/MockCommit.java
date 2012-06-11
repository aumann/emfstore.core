package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.widgets.Widget;

public class MockCommit implements IMockCommit {

	PlotLane lane;

	ArrayList<PlotLane> passingLines;

	ArrayList<IMockCommit> parents;

	ArrayList<IMockCommit> children;

	String shortMsg;

	private Widget widget;

	public MockCommit() {
		this.lane = null;
		this.passingLines = new ArrayList<PlotLane>();
		this.parents = new ArrayList<IMockCommit>();
		this.children = new ArrayList<IMockCommit>();
		this.shortMsg = "foo";
	}

	public PlotLane getLane() {
		return lane;
	}

	public PlotLane[] getPassingLanes() {
		return passingLines.toArray(new PlotLane[passingLines.size()]);
	}

	public int getParentCount() {
		return parents.size();
	}

	public IMockCommit getParent(int i) {
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

	public boolean isChild(IMockCommit c) {
		for (IMockCommit mc : children) {
			if (mc == c)
				return true;
		}
		return false;
	}

	public String getCommitterName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getCommitDate() {
		return new Date(2008, 11, 12);
	}

	public void dispose() {
		if (widget != null)
			widget.dispose();
	}
}
