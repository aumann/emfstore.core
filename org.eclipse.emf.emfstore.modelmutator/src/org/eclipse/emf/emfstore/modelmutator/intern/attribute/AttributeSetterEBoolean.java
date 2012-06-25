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
 * Class for creating random Boolean values.
 * 
 * @author Eugen Neufeld
 * @author Stephan K?hler
 * @author Philip Achenbach
 * 
 * @see AttributeSetter
 */
public class AttributeSetterEBoolean extends AttributeSetter<Boolean> {

	/**
	 * Creates a new AttributeSetter for Boolean attributes.
	 * 
	 * @param random
	 *            Random object used to create attribute values
	 */
	public AttributeSetterEBoolean(Random random) {
		super(random);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean createNewAttribute() {
		return getRandom().nextBoolean();
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Boolean> createNewAttributes(int maxAmount) {
		List<Boolean> result = new ArrayList<Boolean>(maxAmount);
		for (int i = 0; i < maxAmount; i++) {
			result.add(createNewAttribute());
		}
		return result;
	}

}