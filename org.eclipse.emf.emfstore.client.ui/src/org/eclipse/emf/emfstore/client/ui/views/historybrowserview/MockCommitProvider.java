package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.BitSet;
import java.util.HashSet;
import java.util.TreeSet;

public class MockCommitProvider implements IMockCommitProvider {

	private MockCommit[] commits;

	private final TreeSet<Integer> freePositions = new TreeSet<Integer>();

	private final HashSet<PlotLane> activeLanes = new HashSet<PlotLane>(32);

	private int positionsAllocated = 0;

	public MockCommitProvider() {
		setup();
	}

	// create the 10 commits. index 0 represents oldest, 9 newest comit
	// TODO it is possible that we need to turn this around, because of the
	// index-parameter in enter-method. time will show
	private void setup() {
		// init commits array
		commits = new MockCommit[10];
		for (int i = 0; i < commits.length; i++) {
			MockCommit mock = new MockCommit();
			mock.shortMsg = "Commit Nr. " + i;
			commits[i] = mock;
		}

		// set parents
		commits[9].parents.add(commits[8]);

		commits[8].parents.add(commits[7]);
		commits[8].parents.add(commits[6]);

		commits[7].parents.add(commits[3]);

		commits[6].parents.add(commits[5]);
		commits[6].parents.add(commits[4]);

		commits[5].parents.add(commits[1]);

		commits[4].parents.add(commits[2]);

		commits[3].parents.add(commits[1]);

		commits[2].parents.add(commits[1]);

		commits[1].parents.add(commits[0]);

		// "enter" commits
		for (int i = 9; i >= 0; i--) {
			enter(10 - i, commits[i]);
		}
	}

	private void enter(int index, MockCommit currCommit) {
		setupChildren(currCommit);

		final int nChildren = currCommit.getChildCount();
		if (nChildren == 0)
			return;

		if (nChildren == 1 && currCommit.children.get(0).getParentCount() < 2) {
			// Only one child, child has only us as their parent.
			// Stay in the same lane as the child.
			//
			final MockCommit c = (MockCommit) currCommit.children.get(0);
			if (c.lane == null) {
				// Hmmph. This child must be the first along this lane.
				//
				c.lane = nextFreeLane();
				activeLanes.add(c.lane);
			}
			for (int r = index - 1; r >= 0; r--) {
				final MockCommit rObj = commits[r]; // TODO stimmt das hier mit
													// r so??
				if (rObj == c)
					break;
				// rObj.addPassingLane(c.lane); //TODO passiert hier was
				// magisches?
				rObj.passingLines.add(c.lane);
			}

			currCommit.lane = c.lane;
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
				final MockCommit c = (MockCommit) currCommit.children.get(i);
				// don't forget to position all of your children if they are
				// not already positioned.
				if (c.lane == null) {
					c.lane = nextFreeLane();
					activeLanes.add(c.lane);
					if (reservedLane != null)
						closeLane(c.lane);
					else
						reservedLane = c.lane;
				} else if (reservedLane == null && activeLanes.contains(c.lane))
					reservedLane = c.lane;
				else
					closeLane(c.lane);
			}

			// finally all children are processed. We can close the lane on that
			// position our current commit will be on.
			if (reservedLane != null)
				closeLane(reservedLane);

			currCommit.lane = nextFreeLane();
			activeLanes.add(currCommit.lane);

			handleBlockedLanes(index, currCommit, nChildren);
		}
	}

	private void setupChildren(MockCommit currCommit) {
		int nParents = currCommit.getParentCount();
		for (int i = 0; i < nParents; i++)
			((MockCommit) currCommit.getParent(i)).children.add(currCommit);
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

	private void handleBlockedLanes(final int index, final MockCommit commit, final int nChildren) {
		// take care:
		int remaining = nChildren;
		BitSet blockedPositions = new BitSet();
		for (int r = index - 1; r >= 0; r--) {
			final MockCommit rObj = commits[r]; // TODO stimmt das so?
			if (commit.isChild(rObj)) {
				if (--remaining == 0)
					break;
			}
			if (rObj != null) {
				PlotLane lane = rObj.getLane();
				if (lane != null)
					blockedPositions.set(lane.getPosition());
				// rObj.addPassingLane(commit.lane); //TODO passiert hier was
				// magisches?
				rObj.passingLines.add(commit.lane);
			}
		}
		// Now let's check whether we have to reposition the lane
		if (blockedPositions.get(commit.lane.getPosition())) {
			int newPos = -1;
			for (Integer pos : freePositions)
				if (!blockedPositions.get(pos.intValue())) {
					newPos = pos.intValue();
					break;
				}
			if (newPos == -1)
				newPos = positionsAllocated++;
			freePositions.add(Integer.valueOf(commit.lane.getPosition()));
			activeLanes.remove(commit.lane);
			commit.lane.position = newPos;
			activeLanes.add(commit.lane);
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
}
