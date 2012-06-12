/*******************************************************************************
 * Copyright (C) 2006, Robin Rosenberg <robin.rosenberg@dewire.com>
 * Copyright (C) 2008, Shawn O. Pearce <spearce@spearce.org>
 * Copyright (C) 2011, Matthias Sohn <matthias.sohn@sap.com>
 * Copyright (C) 2011, IBM Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

class GraphLabelProvider extends BaseLabelProvider implements ITableLabelProvider {

	private boolean showEmail;

	private IMockCommit lastCommit;

	private String lastCommitter;

	GraphLabelProvider() {
	}

	public String getColumnText(final Object element, final int columnIndex) {
		final IMockCommit c = (IMockCommit) element;
		// try {
		// c.parseBody();
		// } catch (IOException e) {
		//			Activator.error("Error parsing body", e); //$NON-NLS-1$
		//			return ""; //$NON-NLS-1$
		// }
		if (columnIndex == 0)
			return c.getShortMessage();
		if (columnIndex == 1)
			return c.getId();
		if (columnIndex == 2 || columnIndex == 3) {
			final String author = committerOf(c);
			if (author != null)
				switch (columnIndex) {
				case 2:
					return author;
				case 3:
					return c.getCommitDate().toString();
				}
		}

		return ""; //$NON-NLS-1$
	}

	private String committerOf(final IMockCommit c) {
		if (lastCommit != c) {
			lastCommit = c;
			lastCommitter = c.getCommitterName();
		}
		return lastCommitter;
	}

	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	/**
	 * @param showEmail true to show e-mail addresses, false otherwise
	 */
	public void setShowEmailAddresses(boolean showEmail) {
		this.showEmail = showEmail;
	}
}
