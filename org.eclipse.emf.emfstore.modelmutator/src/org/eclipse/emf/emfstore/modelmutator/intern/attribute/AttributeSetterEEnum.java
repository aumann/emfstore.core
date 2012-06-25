/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH,
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.modelmutator.intern.attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;

/**
 * Class for creating random Enumerator values.
 * 
 * @author Eugen Neufeld
 * @author Stephan K?hler
 * @author Philip Achenbach
 * 
 * @see AttributeSetter
 */
public class AttributeSetterEEnum extends AttributeSetter<Enumerator> {

	/**
	 * The EEnum for which the Enumerators shall be created.
	 */
	private EEnum eEnum;


	/**
	 * Creates a new AttributeSetter for Enumerator attributes.
	 * 
	 * @param eEnum
	 *            the EEnum this attribute setter will create Enumerators for
	 * @param random
	 *            Random object used to create attribute values
	 */
	public AttributeSetterEEnum(EEnum eEnum, Random random) {
		super(random);
		this.eEnum = eEnum;
	}

	/**
	 * {@inheritDoc}
	 */
	public Enumerator createNewAttribute() {
		List<EEnumLiteral> literals = new ArrayList<EEnumLiteral>(eEnum.getELiterals());
		if (literals.isEmpty()) {
			return null;
		}
		Collections.shuffle(literals, getRandom());
		return literals.get(0).getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Enumerator> createNewAttributes(int maxAmount) {
		List<Enumerator> result = new ArrayList<Enumerator>(maxAmount);
		// add instances of all possible literals
		for (EEnumLiteral literal : eEnum.getELiterals()) {
			result.add(literal.getInstance());
		}
		Collections.shuffle(result, getRandom());
		// remove random Enumerators until at most maxObjects are returned
		while (result.size() > maxAmount) {
			result.remove(0);
		}
		return result;
	}

}