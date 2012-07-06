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
package org.eclipse.emf.emfstore.common.model.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * Helps with validation of modelelements.
 * 
 * @author naughton
 */
public final class ValidationConstraintHelper {

	private ValidationConstraintHelper() {

	}

	/**
	 * Returns the structural feature specified by the feature name belonging to
	 * the model element.
	 * 
	 * @param modelElement
	 *            the modelElement
	 * @param featureName
	 *            the featureName
	 * @return the structuralFeature
	 */
	public static EStructuralFeature getErrorFeatureForModelElement(EObject modelElement, String featureName) {
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		AdapterFactoryItemDelegator adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(adapterFactory);
		IItemPropertyDescriptor itemPropertyDescriptor = adapterFactoryItemDelegator.getPropertyDescriptor(
			modelElement, featureName);
		EStructuralFeature errorFeature = (EStructuralFeature) itemPropertyDescriptor.getFeature(modelElement);
		adapterFactory.dispose();
		return errorFeature;
	}
}
