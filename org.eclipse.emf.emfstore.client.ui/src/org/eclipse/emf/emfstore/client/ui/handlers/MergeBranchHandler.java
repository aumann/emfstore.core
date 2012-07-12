/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIMergeController;

/**
 * Triggers a branch merge.
 * 
 * @author wesendon
 * 
 */
public class MergeBranchHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIMergeController(getShell(), requireSelection(ProjectSpace.class))
				.execute();
	}

}