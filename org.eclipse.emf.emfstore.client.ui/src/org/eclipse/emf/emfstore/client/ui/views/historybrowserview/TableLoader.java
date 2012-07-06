/*******************************************************************************
 * Copyright (C) 2012, Matthias Sohn <matthias.sohn@sap.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

/**
 * Callback interface for incrementally loading table items
 */
public interface TableLoader {
	/**
	 * @param index hint for index of table item to be loaded
	 */
	void loadItem(int index);

	/**
	 * @param c commit to be loaded
	 */
	void loadCommit(IPlotCommit c);
}
