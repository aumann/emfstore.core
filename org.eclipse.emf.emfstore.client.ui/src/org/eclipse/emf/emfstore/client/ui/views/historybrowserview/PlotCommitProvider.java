package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;

public class PlotCommitProvider implements IMockCommitProvider {

	private IMockCommit[] commits;
	private final TreeSet<Integer> freePositions;
	private final HashSet<PlotLane> activeLanes;
	private int positionsAllocated;
	private Map<HistoryInfo, IMockCommit> commitForHistory = new HashMap<HistoryInfo, IMockCommit>();

	public PlotCommitProvider(List<HistoryInfo> historyInfo) {
		this.commits = new PlotCommit[historyInfo.size()];
		this.freePositions = new TreeSet<Integer>();
		this.activeLanes = new HashSet<PlotLane>(32);
		this.positionsAllocated = 0;
		this.commitForHistory = new HashMap<HistoryInfo, IMockCommit>();

		for (int i = 0; i < historyInfo.size(); i++) {
			commits[i] = new PlotCommit(historyInfo.get(i));
			commitForHistory.put(historyInfo.get(i), commits[i]);
		}

		setupParents(historyInfo);

		for (int i = 0; i < commits.length; i++) {
			initCommit(i, commits[i]);
		}
	}

	private void setupParents(List<HistoryInfo> historyInfos) {
		int identifierOffset = historyInfos.size() - 1;
		for (int i = 0; i < historyInfos.size(); i++) {
			HistoryInfo currInfo = historyInfos.get(i);

			// check if this historyinfo element is a merge
			EList<PrimaryVersionSpec> mergedFrom = currInfo.getMergedFrom();
			ArrayList<IMockCommit> parents = new ArrayList<IMockCommit>();
			if (mergedFrom != null && mergedFrom.size() >= 1) {
				for (PrimaryVersionSpec mergeParent : mergedFrom) {
					parents.add(commits[identifierOffset - mergeParent.getIdentifier()]);
					System.out.println(identifierOffset - mergeParent.getIdentifier() + " is parent of " + i);
				}
				commits[i].setParents(parents);

			}
			// we only have one parent or none
			PrimaryVersionSpec parentSpec = currInfo.getPreviousSpec();
			if (parentSpec != null) {
				parents.add(commits[identifierOffset - parentSpec.getIdentifier()]);
				System.out.println(identifierOffset - parentSpec.getIdentifier() + " is parent of " + i);
			}
			if (!parents.isEmpty()) {
				commits[i].setParents(parents);
			}
		}
	}

	// private void setupParents(List<HistoryInfo> historyInfos) {
	// for (int i = 0; i < historyInfos.size(); i++) {
	// HistoryInfo info = historyInfos.get(i);
	// PlotCommit commit = (PlotCommit) commits[i];
	// // TODO is this really the parent relation?
	// List<PrimaryVersionSpec> versionSpecs = info.getMergedFrom();
	// if (versionSpecs != null && versionSpecs.size() > 1) {
	// // this is a merge
	// ArrayList<IMockCommit> parents = new ArrayList<IMockCommit>(versionSpecs.size());
	// for (int j = 0; j < versionSpecs.size(); j++) {
	// parents.set(j, commitForHistory.get(versionSpecs.get(j).eContainer()));
	// }
	// commit.setParents(parents);
	// } else {
	// ArrayList<IMockCommit> parents = new ArrayList<IMockCommit>();
	// IMockCommit c = commitForHistory.get(info);
	// if (c != null) {
	// parents.add(c);
	// commit.setParents(parents);
	// }
	// }
	// }
	// }

	private void initCommit(int index, IMockCommit currCommit) {
		setupChildren(currCommit);

		final int nChildren = currCommit.getChildCount();
		if (nChildren == 0)
			return;

		if (nChildren == 1 && currCommit.getChild(0).getParentCount() < 2) {
			// Only one child, child has only us as their parent.
			// Stay in the same lane as the child.
			//
			final IMockCommit c = currCommit.getChild(0);
			if (c.getLane() == null) {
				// Hmmph. This child must be the first along this lane.
				//
				c.setLane(nextFreeLane());
				activeLanes.add(c.getLane());
			}
			for (int r = index - 1; r >= 0; r--) {
				final IMockCommit rObj = commits[r];
				if (rObj == c)
					break;
				rObj.addPassingLane(c.getLane());
			}

			currCommit.setLane(c.getLane());
			handleBlockedLanes(index, currCommit, nChildren);
		} else {
			// More than one child, or our child is a merge.
			// Use a different lane.
			//

			// Process all our children. Especially important when there is more
			// than one child (e.g. a commit is processed where other branches
			// fork out). For each child the following is done
			// 1. If no lane was assigned to the child a new lane is created and
			// assigned
			// 2. The lane of the child is closed. If this frees a position,
			// this position will be added freePositions list.
			// If we have multiple children which where previously not on a lane
			// each such child will get his own new lane but all those new lanes
			// will be on the same position. We have to take care that not
			// multiple newly created (in step 1) lanes occupy that position on
			// which the
			// parent's lane will be on. Therefore we delay closing the lane
			// with the parents position until all children are processed.

			// The lane on that position the current commit will be on
			PlotLane reservedLane = null;

			for (int i = 0; i < nChildren; i++) {
				final IMockCommit c = currCommit.getChild(i);
				// don't forget to position all of your children if they are
				// not already positioned.
				if (c.getLane() == null) {
					c.setLane(nextFreeLane());
					activeLanes.add(c.getLane());
					if (reservedLane != null)
						closeLane(c.getLane());
					else
						reservedLane = c.getLane();
				} else if (reservedLane == null && activeLanes.contains(c.getLane()))
					reservedLane = c.getLane();
				else
					closeLane(c.getLane());
			}

			// finally all children are processed. We can close the lane on that
			// position our current commit will be on.
			if (reservedLane != null)
				closeLane(reservedLane);

			currCommit.setLane(nextFreeLane());
			activeLanes.add(currCommit.getLane());

			handleBlockedLanes(index, currCommit, nChildren);
		}
	}

	private void setupChildren(IMockCommit currCommit) {
		int nParents = currCommit.getParentCount();
		for (int i = 0; i < nParents; i++) {
			currCommit.getParent(i).addChild(currCommit);
		}
	}

	private PlotLane nextFreeLane() {
		final PlotLane p = new PlotLane();
		if (freePositions.isEmpty()) {
			p.position = positionsAllocated++;
		} else {
			final Integer min = freePositions.first();
			p.position = min.intValue();
			freePositions.remove(min);
		}
		return p;
	}

	private void handleBlockedLanes(final int index, final IMockCommit commit, final int nChildren) {
		// take care:
		int remaining = nChildren;
		BitSet blockedPositions = new BitSet();
		for (int r = index - 1; r >= 0; r--) {
			final IMockCommit rObj = commits[r];
			if (commit.isChild(rObj)) {
				if (--remaining == 0)
					break;
			}
			if (rObj != null) {
				PlotLane lane = rObj.getLane();
				if (lane != null)
					blockedPositions.set(lane.getPosition());
				rObj.addPassingLane(commit.getLane());
			}
		}
		// Now let's check whether we have to reposition the lane
		if (blockedPositions.get(commit.getLane().getPosition())) {
			int newPos = -1;
			for (Integer pos : freePositions)
				if (!blockedPositions.get(pos.intValue())) {
					newPos = pos.intValue();
					break;
				}
			if (newPos == -1)
				newPos = positionsAllocated++;
			freePositions.add(Integer.valueOf(commit.getLane().getPosition()));
			activeLanes.remove(commit.getLane());
			commit.getLane().position = newPos;
			activeLanes.add(commit.getLane());
		}
	}

	private void closeLane(PlotLane lane) {
		if (activeLanes.remove(lane)) {
			freePositions.add(Integer.valueOf(lane.getPosition()));
		}
	}

	public IMockCommit[] getCommits() {
		return commits;
	}

	public IMockCommit getCommitFor(HistoryInfo info, boolean onlyAChildRequest) {
		IMockCommit comForInfo = commitForHistory.get(info);
		comForInfo.setIsRealCommit(!onlyAChildRequest);
		return comForInfo;
	}

}
