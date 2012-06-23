package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.Date;
import java.util.List;

import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.swt.widgets.Widget;

public class PlotCommit implements IMockCommit {

	private HistoryInfo historyInfo;
	private PlotLane lane;
	private PlotLane[] passingLanes;
	private List<IMockCommit> parents;
	private List<IMockCommit> children;
	private Widget widget;
	private boolean isRealCommit;

	public PlotCommit(HistoryInfo historyInfo) {
		this.historyInfo = historyInfo;
		this.lane = null;
		this.passingLanes = null;
	}

	public void setLane(PlotLane lane) {
		this.lane = lane;
	}

	public PlotLane getLane() {
		return lane;
	}

	public void addPassingLane(PlotLane c) {
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

	public PlotLane[] getPassingLanes() {
		return passingLanes;
	}

	public void setParents(List<IMockCommit> parents) {
		this.parents = parents;
	}

	public int getParentCount() {
		return parents.size();
	}

	public IMockCommit getParent(int i) {
		return parents.get(i);
	}

	public void addChild(IMockCommit child) {
		children.add(child);
	}

	public IMockCommit getChild(int child) {
		return children.get(child);
	}

	public int getChildCount() {
		return children.size();
	}

	public boolean isChild(IMockCommit commit) {
		for (IMockCommit mc : children) {
			if (mc == commit)
				return true;
		}
		return false;
	}

	public int getRefsLength() {
		// TODO What to do here?
		return 0;
	}

	public String getShortMessage() {
		return historyInfo.getLogMessage().getMessage();
	}

	public void setIsRealCommit(boolean isReal) {
		isRealCommit = isReal;
	}

	public boolean isRealCommit() {
		return isRealCommit;
	}

	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}

	public String getCommitterName() {
		return historyInfo.getLogMessage().getAuthor();
	}

	public String getId() {
		// TODO what to do here
		return "was soll hier stehen? ^^";
	}

	public Date getCommitDate() {
		return historyInfo.getLogMessage().getDate();
	}

	public void dispose() {
		if (widget != null) {
			widget.dispose();
		}
	}

}
