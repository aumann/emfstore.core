package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.ArrayList;

public class MockCommit implements IMockCommit {

	private PlotLane lane;

	ArrayList<PlotLane> passingLines;

	ArrayList<IMockCommit> parents;

	ArrayList<IMockCommit> children;

	private String shortMsg;

	public MockCommit(PlotLane lane, String shortMsg) {
		this.lane = lane;
		this.passingLines = new ArrayList<PlotLane>();
		this.parents = new ArrayList<IMockCommit>();
		this.children = new ArrayList<IMockCommit>();
		this.shortMsg = shortMsg;
	}

	public PlotLane getLane() {
		return lane;
	}

	public PlotLane[] getPassingLanes() {
		return passingLines.toArray();
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

}
