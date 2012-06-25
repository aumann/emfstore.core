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
import java.util.List;
import java.util.Random;

/**
 * Class for creating random Integer values.
 * 
 * @author Eugen Neufeld
 * @author Stephan K?hler
 * @author Philip Achenbach
 * 
 * @see AttributeSetter
 */
public class AttributeSetterEInt extends AttributeSetter<Integer> {

	/**
	 * Creates a new AttributeSetter for Integer attributes.
	 * 
	 * @param random
	 *            Random object used to create attribute values
	 */
	public AttributeSetterEInt(Random random) {
		super(random);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer createNewAttribute() {
		return getRandom().nextInt();
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Integer> createNewAttributes(int maxAmount) {
		List<Integer> result = new ArrayList<Integer>(maxAmount);
		for (int i = 0; i < maxAmount; i++) {
			result.add(createNewAttribute());
		}
		return result;
	}

}