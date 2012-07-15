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
import java.util.List;

import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Widget;

/**
 * @author Aumann, Faltermeier
 * 
 */
public class PlotCommit implements IPlotCommit {

	private PlotLane lane;
	private PlotLane[] passingLanes;
	private List<IPlotCommit> parents;
	private List<IPlotCommit> children;
	private Widget widget;
	private boolean isRealCommit;
	private String branchName;
	private String idString;
	private boolean localHistoryOnly;
	private Color color;
	private Color lightColor;

	/**
	 * A PlotCommit object holds graphical information for a certain history object which is used for drawing in the
	 * HistoryBrowserView, or rather the SWTPlotRenderer used by this view.
	 * 
	 * @param historyInfo The historyInfo that is represented by this PlotCommit
	 */
	public PlotCommit(HistoryInfo historyInfo) {
		this.lane = null;
		this.passingLanes = new PlotLane[0];
		this.parents = new ArrayList<IPlotCommit>();
		this.children = new ArrayList<IPlotCommit>();
		this.widget = null;
		this.isRealCommit = true;

		if (historyInfo.getPrimerySpec().getIdentifier() < 0) {
			localHistoryOnly = true;
			this.branchName = "local";
		} else if (historyInfo.getPrimerySpec() != null) {
			this.branchName = historyInfo.getPrimerySpec().getBranch();
			this.idString = String.valueOf(historyInfo.getPrimerySpec().getIdentifier());
		} else {
			this.branchName = "Strange";
			this.idString = "Strange ID";
			localHistoryOnly = true;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#setLane(org.eclipse.emf.emfstore.client.ui.views.historybrowserview.PlotLane)
	 */
	public void setLane(PlotLane lane) {
		this.lane = lane;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#getLane()
	 */
	public PlotLane getLane() {
		return lane;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#addPassingLane(org.eclipse.emf.emfstore.client.ui.views.historybrowserview.PlotLane)
	 */
	public void addPassingLane(PlotLane c) {
		final int cnt = passingLanes.length;
		if (cnt == 0) {
			passingLanes = new PlotLane[] { c };
		} else if (cnt == 1) {
			passingLanes = new PlotLane[] { passingLanes[0], c };
		} else {
			final PlotLane[] n = new PlotLane[cnt + 1];
			System.arraycopy(passingLanes, 0, n, 0, cnt);
			n[cnt] = c;
			passingLanes = n;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#getPassingLanes()
	 */
	public PlotLane[] getPassingLanes() {
		return passingLanes;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#setParents(java.util.List)
	 */
	public void setParents(List<IPlotCommit> parents) {
		this.parents = parents;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#getParentCount()
	 */
	public int getParentCount() {
		return parents.size();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#getParent(int)
	 */
	public IPlotCommit getParent(int i) {
		return parents.get(i);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#addChild(org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit)
	 */
	public void addChild(IPlotCommit child) {
		children.add(child);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#getChild(int)
	 */
	public IPlotCommit getChild(int child) {
		return children.get(child);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#getChildCount()
	 */
	public int getChildCount() {
		return children.size();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#isChild(org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit)
	 */
	public boolean isChild(IPlotCommit commit) {
		for (IPlotCommit mc : children) {
			if (mc == commit) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#setIsRealCommit(boolean)
	 */
	public void setIsRealCommit(boolean isReal) {
		isRealCommit = isReal;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#isRealCommit()
	 */
	public boolean isRealCommit() {
		return isRealCommit;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#getBranch()
	 */
	public String getBranch() {
		return branchName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#getId()
	 */
	public String getId() {
		return idString;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#dispose()
	 */
	public void dispose() {
		if (widget != null) {
			widget.dispose();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#isLocalHistoryOnly()
	 */
	public boolean isLocalHistoryOnly() {
		return localHistoryOnly;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#setColor(org.eclipse.swt.graphics.Color)
	 */
	public void setColor(Color color) {
		this.color = color;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#getColor()
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#setLightColor(org.eclipse.swt.graphics.Color)
	 */
	public void setLightColor(Color color) {
		this.lightColor = color;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.IPlotCommit#getLightColor()
	 */
	public Color getLightColor() {
		return lightColor;
	}

}
