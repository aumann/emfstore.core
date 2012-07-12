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
import org.eclipse.emf.emfstore.client.ui.controller.UICreateBranchController;

/**
 * Triggers Branch creation.
 * 
 * @author wesendon
 */
public class CreateBranchHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UICreateBranchController(getShell(),
				requireSelection(ProjectSpace.class)).execute();
	}

}
