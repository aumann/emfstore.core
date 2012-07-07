/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * @author Aumann, Faltermeier
 * 
 */
public class PlotCommitProvider implements ICommitProvider {

	private IPlotCommit[] commits;
	private final TreeSet<Integer> freePositions;
	private final HashSet<PlotLane> activeLanes;
	private int positionsAllocated;
	private Map<HistoryInfo, IPlotCommit> commitForHistory = new HashMap<HistoryInfo, IPlotCommit>();
	private int nextBranchColorIndex;
	private Map<String, Integer> colorForBranch = new HashMap<String, Integer>();
	private static List<Color> createdColors = new LinkedList<Color>();
	private static final Color[] COLORS = new Color[] { Display.getDefault().getSystemColor(SWT.COLOR_BLUE),
		Display.getDefault().getSystemColor(SWT.COLOR_GREEN), Display.getDefault().getSystemColor(SWT.COLOR_RED) };
	private static final Color[] COLORS_LIGHT = new Color[COLORS.length];

	static {
		setUpLightColors();
	}

	/**
	 * Creates a new PlotCommitProvider from a list of {@linkplain HistoryInfo} objects.
	 * 
	 * @param historyInfo The history info for which the plot commits should be created.
	 */
	public PlotCommitProvider(List<HistoryInfo> historyInfo) {
		this.nextBranchColorIndex = 0;
		this.commits = new PlotCommit[historyInfo.size()];
		this.freePositions = new TreeSet<Integer>();
		this.activeLanes = new HashSet<PlotLane>(32);
		this.positionsAllocated = 0;
		this.commitForHistory = new HashMap<HistoryInfo, IPlotCommit>();

		for (int i = 0; i < historyInfo.size(); i++) {
			commits[i] = new PlotCommit(historyInfo.get(i));
			commitForHistory.put(historyInfo.get(i), commits[i]);
			Color[] branchColors = getColorsForBranch(commits[i].getBranch());
			commits[i].setColor(branchColors[0]);
			commits[i].setLightColor(branchColors[1]);
		}

		setupParents(historyInfo);

		for (int i = 0; i < commits.length; i++) {
			initCommit(i, commits[i]);
		}
	}

	private static void setUpLightColors() {
		for (int i = 0; i < COLORS.length; i++) {
			COLORS_LIGHT[i] = createLightColor(COLORS[i]);
		}

	}

	private static Color createLightColor(Color color) {
		float[] hsbColor = java.awt.Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
		hsbColor[1] = hsbColor[1] * 0.2f;
		hsbColor[2] = hsbColor[2];

		int lightColorRGB = java.awt.Color.HSBtoRGB(hsbColor[0], hsbColor[1], hsbColor[2]);

		java.awt.Color lightColor = new java.awt.Color(lightColorRGB);

		Color lightColorSWT = new Color(Display.getDefault(), lightColor.getRed(), lightColor.getGreen(),
			lightColor.getBlue());
		createdColors.add(lightColorSWT);
		return lightColorSWT;
	}

	private Color[] getColorsForBranch(String branch) {
		if ("trunk".equals(branch)) {
			Color[] colors = new Color[] { Display.getDefault().getSystemColor(SWT.COLOR_BLACK),
				Display.getDefault().getSystemColor(SWT.COLOR_GRAY) };
			return colors;
		}

		Integer colorIndex = colorForBranch.get(branch);
		if (colorIndex == null) {
			colorIndex = nextBranchColorIndex;
			colorForBranch.put(branch, colorIndex);
			nextBranchColorIndex = (nextBranchColorIndex + 1) % COLORS.length;
		}
		Color[] colors = new Color[2];
		colors[0] = COLORS[colorIndex];
		colors[1] = COLORS_LIGHT[colorIndex];
		return colors;
	}

	private void setupParents(List<HistoryInfo> historyInfos) {
		int identifierOffset = historyInfos.size() - 1;
		for (int i = 0; i < historyInfos.size(); i++) {
			HistoryInfo currInfo = historyInfos.get(i);

			// check if this historyinfo element is a merge
			EList<PrimaryVersionSpec> mergedFrom = currInfo.getMergedFrom();
			ArrayList<IPlotCommit> parents = new ArrayList<IPlotCommit>();
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

	private void initCommit(int index, IPlotCommit currCommit) {
		setupChildren(currCommit);

		final int nChildren = currCommit.getChildCount();
		if (nChildren == 0) {
			return;
		}

		if (nChildren == 1 && currCommit.getChild(0).getParentCount() < 2) {
			// Only one child, child has only us as their parent.
			// Stay in the same lane as the child.
			//
			final IPlotCommit c = currCommit.getChild(0);
			if (c.getLane() == null) {
				// Hmmph. This child must be the first along this lane.
				//
				PlotLane lane = nextFreeLane();
				lane.setSaturatedColor(c.getColor());
				lane.setLightColor(c.getLightColor());
				c.setLane(lane);
				activeLanes.add(c.getLane());
			}
			for (int r = index - 1; r >= 0; r--) {
				final IPlotCommit rObj = commits[r];
				if (rObj == c) {
					break;
				}
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
				final IPlotCommit c = currCommit.getChild(i);
				// don't forget to position all of your children if they are
				// not already positioned.
				if (c.getLane() == null) {
					PlotLane lane = nextFreeLane();
					lane.setSaturatedColor(c.getColor());
					lane.setLightColor(c.getLightColor());
					c.setLane(lane);
					activeLanes.add(c.getLane());
					if (reservedLane != null) {
						closeLane(c.getLane());
					} else {
						reservedLane = c.getLane();
					}
				} else if (reservedLane == null && activeLanes.contains(c.getLane())) {
					reservedLane = c.getLane();
				} else {
					closeLane(c.getLane());
				}
			}

			// finally all children are processed. We can close the lane on that
			// position our current commit will be on.
			if (reservedLane != null) {
				closeLane(reservedLane);
			}

			PlotLane lane = nextFreeLane();
			lane.setSaturatedColor(currCommit.getColor());
			lane.setLightColor(currCommit.getLightColor());
			currCommit.setLane(lane);
			activeLanes.add(currCommit.getLane());

			handleBlockedLanes(index, currCommit, nChildren);
		}
	}

	private void setupChildren(IPlotCommit currCommit) {
		int nParents = currCommit.getParentCount();
		for (int i = 0; i < nParents; i++) {
			currCommit.getParent(i).addChild(currCommit);
		}
	}

	private PlotLane nextFreeLane() {
		final PlotLane p = new PlotLane();
		if (freePositions.isEmpty()) {
			p.setPosition(positionsAllocated++);
		} else {
			final Integer min = freePositions.first();
			p.setPosition(min.intValue());
			freePositions.remove(min);
		}
		return p;
	}

	private void handleBlockedLanes(final int index, final IPlotCommit commit, final int nChildren) {
		// take care:
		int remaining = nChildren;
		BitSet blockedPositions = new BitSet();
		for (int r = index - 1; r >= 0; r--) {
			final IPlotCommit rObj = commits[r];
			if (commit.isChild(rObj)) {
				if (--remaining == 0) {
					break;
				}
			}
			if (rObj != null) {
				PlotLane lane = rObj.getLane();
				if (lane != null) {
					blockedPositions.set(lane.getPosition());
				}
				rObj.addPassingLane(commit.getLane());
			}
		}
		// Now let's check whether we have to reposition the lane
		if (blockedPositions.get(commit.getLane().getPosition())) {
			int newPos = -1;
			for (Integer pos : freePositions) {
				if (!blockedPositions.get(pos.intValue())) {
					newPos = pos.intValue();
					break;
				}
			}
			if (newPos == -1) {
				newPos = positionsAllocated++;
			}
			freePositions.add(Integer.valueOf(commit.getLane().getPosition()));
			activeLanes.remove(commit.getLane());
			commit.getLane().setPosition(newPos);
			activeLanes.add(commit.getLane());
		}
	}

	private void closeLane(PlotLane lane) {
		if (activeLanes.remove(lane)) {
			freePositions.add(Integer.valueOf(lane.getPosition()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.ICommitProvider#getCommitFor(org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo,
	 *      boolean)
	 */
	public IPlotCommit getCommitFor(HistoryInfo info, boolean onlyAChildRequest) {
		IPlotCommit comForInfo = commitForHistory.get(info);
		comForInfo.setIsRealCommit(!onlyAChildRequest);
		return comForInfo;
	}

}
