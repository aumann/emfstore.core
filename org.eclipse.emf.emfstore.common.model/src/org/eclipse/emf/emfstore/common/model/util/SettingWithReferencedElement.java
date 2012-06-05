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
package org.eclipse.emf.emfstore.common.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;

/**
 * @author Edgar
 * 
 */
public class SettingWithReferencedElement {

	private Setting setting;
	private EObject referencedElement;

	public SettingWithReferencedElement(Setting setting, EObject referencedElement) {
		this.referencedElement = referencedElement;
		this.setting = setting;
	}

	public EObject getReferencedElement() {
		return referencedElement;
	}

	public Setting getSetting() {
		return setting;
	}
}
