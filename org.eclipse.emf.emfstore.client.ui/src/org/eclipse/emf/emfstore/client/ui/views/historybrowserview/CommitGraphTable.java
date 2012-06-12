/*******************************************************************************
 * Copyright (C) 2007, Dave Watson <dwatson@mimvista.com>
 * Copyright (C) 2007, Robin Rosenberg <robin.rosenberg@dewire.com>
 * Copyright (C) 2008, Roger C. Soares <rogersoares@intelinet.com.br>
 * Copyright (C) 2008, Shawn O. Pearce <spearce@spearce.org>
 * Copyright (C) 2011-2012, Mathias Kinzler <mathias.kinzler@sap.com>
 * Copyright (C) 2011-2012, Matthias Sohn <matthias.sohn@sap.com>
 * Copyright (C) 2012, Robin Stocker <robin@nibor.org>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IPageSite;

class CommitGraphTable {
	// static Font highlightFont() {
	// final Font n, h;
	//
	// n = UIUtils.getFont(UIPreferences.THEME_CommitGraphNormalFont);
	// h = UIUtils.getFont(UIPreferences.THEME_CommitGraphHighlightFont);
	//
	// final FontData[] nData = n.getFontData();
	// final FontData[] hData = h.getFontData();
	// if (nData.length != hData.length)
	// return h;
	// for (int i = 0; i < nData.length; i++)
	// if (!nData[i].equals(hData[i]))
	// return h;
	//
	// return UIUtils.getBoldFont(UIPreferences.THEME_CommitGraphNormalFont);
	// }

	private static final String LINESEP = System.getProperty("line.separator"); //$NON-NLS-1$

	private final TableViewer table;

	private Clipboard clipboard;

	private final SWTPlotRenderer renderer;

	private final Font nFont;

	private List<IMockCommit> allCommits;

	private int allCommitsLength = 0;

	// used for resolving PlotCommit objects by ids
	private HashMap<String, IMockCommit> commitsMap = null;

	// private HistoryPageInput input;

	IAction copy;

	private IMockCommit commitToShow;

	private GraphLabelProvider graphLabelProvider;

	private final TableLoader tableLoader;

	CommitGraphTable(Composite parent, final TableLoader loader) {
		nFont = PlatformUI.getWorkbench().getDisplay().getSystemFont();
		tableLoader = loader;

		final Table rawTable = new Table(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER
			| SWT.FULL_SELECTION | SWT.VIRTUAL);
		rawTable.setHeaderVisible(true);
		rawTable.setLinesVisible(false);
		rawTable.setFont(nFont);
		rawTable.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event event) {
				if (tableLoader != null) {
					TableItem item = (TableItem) event.item;
					int index = rawTable.indexOf(item);
					tableLoader.loadItem(index);
				}
			}
		});

		final TableLayout layout = new TableLayout();
		rawTable.setLayout(layout);

		createColumns(rawTable, layout);
		createPaintListener(rawTable);

		table = new TableViewer(rawTable) {
			@Override
			protected Widget doFindItem(final Object element) {
				return element != null ? ((IMockCommit) element).getWidget() : null;
			}

			@Override
			protected void mapElement(final Object element, final Widget item) {
				((IMockCommit) element).setWidget(item);
			}
		};

		graphLabelProvider = new GraphLabelProvider();

		table.setLabelProvider(graphLabelProvider);
		table.setContentProvider(new GraphContentProvider());
		renderer = new SWTPlotRenderer(rawTable.getDisplay());

		clipboard = new Clipboard(rawTable.getDisplay());
		rawTable.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(final DisposeEvent e) {
				clipboard.dispose();
			}
		});

		table.setUseHashlookup(true);

		table.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection s = event.getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
				final IStructuredSelection iss = (IStructuredSelection) s;
				commitToShow = (IMockCommit) iss.getFirstElement();

				copy.setEnabled(canDoCopy());
			}
		});

		table.getTable().addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				if (allCommits != null) {
					for (IMockCommit commit : allCommits) {
						commit.dispose();

					}
				}
				if (renderer != null)
					renderer.dispose();
			}
		});

		Transfer[] transferTypes = new Transfer[] { TextTransfer.getInstance(), FileTransfer.getInstance() };
	}

	CommitGraphTable(final Composite parent, final IPageSite site, final MenuManager menuMgr, final TableLoader loader) {
		this(parent, loader);

		// getTableView().addOpenListener(new IOpenListener() {
		// public void open(OpenEvent event) {
		// if (input == null || !input.isSingleFile())
		// return;
		//
		// ICommandService srv = (ICommandService) site.getService(ICommandService.class);
		// IHandlerService hsrv = (IHandlerService) site.getService(IHandlerService.class);
		// Command cmd = srv.getCommand(HistoryViewCommands.SHOWVERSIONS);
		// Parameterization[] parms;
		// if (Activator.getDefault().getPreferenceStore().getBoolean(UIPreferences.RESOURCEHISTORY_COMPARE_MODE))
		// try {
		// IParameter parm = cmd.getParameter(HistoryViewCommands.COMPARE_MODE_PARAM);
		// parms = new Parameterization[] { new Parameterization(parm, Boolean.TRUE.toString()) };
		// } catch (NotDefinedException e) {
		// Activator.handleError(e.getMessage(), e, true);
		// parms = null;
		// }
		// else
		// parms = null;
		// ParameterizedCommand pcmd = new ParameterizedCommand(cmd, parms);
		// try {
		// hsrv.executeCommandInContext(pcmd, null, hsrv.getCurrentState());
		// } catch (Exception e) {
		// Activator.handleError(e.getMessage(), e, true);
		// }
		// }
		// });

		Control c = getControl();
		menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		c.setMenu(menuMgr.createContextMenu(c));
	}

	Control getControl() {
		return table.getControl();
	}

	void selectCommitStored(final IMockCommit c) {
		commitToShow = c;
		selectCommit(c);
	}

	void selectCommit(final IMockCommit c) {
		table.setSelection(new StructuredSelection(c), true);
	}

	void addSelectionChangedListener(final ISelectionChangedListener l) {
		table.addPostSelectionChangedListener(l);
	}

	void removeSelectionChangedListener(final ISelectionChangedListener l) {
		table.removePostSelectionChangedListener(l);
	}

	private boolean canDoCopy() {
		return !table.getSelection().isEmpty();
	}

	@SuppressWarnings("unchecked")
	private void doCopy() {
		final ISelection s = table.getSelection();
		if (s.isEmpty() || !(s instanceof IStructuredSelection))
			return;
		final IStructuredSelection iss = (IStructuredSelection) s;
		final Iterator<IMockCommit> itr = iss.iterator();
		final StringBuilder r = new StringBuilder();
		while (itr.hasNext()) {
			final IMockCommit d = itr.next();
			if (r.length() > 0)
				r.append(LINESEP);
			r.append(d.getId());
		}

		if (clipboard == null || clipboard.isDisposed())
			return;
		clipboard.setContents(new Object[] { r.toString() }, new Transfer[] { TextTransfer.getInstance() },
			DND.CLIPBOARD);
	}

	void setInput(final List<IMockCommit> list, final IMockCommit[] asArray, boolean keepPosition) {
		int topIndex = -1;
		if (keepPosition)
			topIndex = table.getTable().getTopIndex();
		// setHistoryPageInput(input);
		final List<IMockCommit> oldList = allCommits;
		if (oldList != null && oldList != list) {
			for (IMockCommit c : oldList)
				c.dispose();
		}
		allCommits = list;
		int newAllCommitsLength = allCommits.size();
		table.setInput(asArray);
		if (asArray != null && asArray.length > 0) {
			if (oldList != list || allCommitsLength < newAllCommitsLength)
				initCommitsMap();
		} else
			table.getTable().deselectAll();
		allCommitsLength = newAllCommitsLength;
		if (commitToShow != null)
			selectCommit(commitToShow);
		if (keepPosition)
			table.getTable().setTopIndex(topIndex);
	}

	// void setHistoryPageInput(HistoryPageInput input) {
	// this.input = input;
	// if (menuListener != null)
	// menuListener.setInput(input);
	// }

	private void initCommitsMap() {
		commitsMap = new HashMap<String, IMockCommit>();
		// ensure that filling (GenerateHistoryJob) and reading (here)
		// the commit list is thread safe
		synchronized (allCommits) {
			for (IMockCommit commit : allCommits)
				if (commit != null)
					commitsMap.put(commit.getId(), commit);
		}
	}

	private void createColumns(final Table rawTable, final TableLayout layout) {

		final TableColumn graph = new TableColumn(rawTable, SWT.NONE);
		graph.setResizable(true);
		graph.setText("Message");
		graph.setWidth(250);
		layout.addColumnData(new ColumnWeightData(20, true));

		final TableColumn commitId = new TableColumn(rawTable, SWT.NONE);
		commitId.setResizable(true);
		commitId.setText("Commit ID");
		int minWidth;
		GC gc = new GC(rawTable.getDisplay());
		try {
			gc.setFont(rawTable.getFont());
			minWidth = gc.stringExtent("0000000").x + 5; //$NON-NLS-1$
		} finally {
			gc.dispose();
		}
		layout.addColumnData(new ColumnWeightData(3, minWidth, true));

		final TableColumn committer = new TableColumn(rawTable, SWT.NONE);
		committer.setResizable(true);
		committer.setText("Committer");
		committer.setWidth(100);
		layout.addColumnData(new ColumnWeightData(5, true));

		final TableColumn date = new TableColumn(rawTable, SWT.NONE);
		date.setResizable(true);
		date.setText("Date");
		date.setWidth(250);
		layout.addColumnData(new ColumnWeightData(5, true));
	}

	private void createPaintListener(final Table rawTable) {
		// Tell SWT we will completely handle painting for some columns.
		//
		rawTable.addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(final Event event) {
				if (0 <= event.index && event.index <= 5)
					event.detail &= ~SWT.FOREGROUND;
			}
		});

		rawTable.addListener(SWT.PaintItem, new Listener() {
			public void handleEvent(final Event event) {
				doPaint(event);
			}
		});
	}

	void doPaint(final Event event) {
		final IMockCommit c = (IMockCommit) ((TableItem) event.item).getData();
		final PlotLane lane = c.getLane();
		if (lane != null && lane.color.isDisposed())
			return;
		// if (highlight != null && c.has(highlight))
		// event.gc.setFont(hFont);
		// else
		event.gc.setFont(nFont);

		if (event.index == 1) {
			// renderer.paint(event, input == null ? null : input.getHead());
			renderer.paint(event, null);
			return;
		}

		final ITableLabelProvider lbl;
		final String txt;

		lbl = (ITableLabelProvider) table.getLabelProvider();
		txt = lbl.getColumnText(c, event.index);

		final Point textsz = event.gc.textExtent(txt);
		final int texty = (event.height - textsz.y) / 2;
		event.gc.drawString(txt, event.x, event.y + texty, true);
	}

	/**
	 * Returns the SWT TableView of this CommitGraphTable.
	 * 
	 * @return Table the SWT Table
	 */
	public TableViewer getTableView() {
		return table;
	}

}
