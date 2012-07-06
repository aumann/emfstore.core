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
package org.eclipse.emf.emfstore.modelmutator.intern.attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Class for creating random Byte[] values.
 * 
 * @author Eugen Neufeld
 * @author Stephan K�hler
 * @author Philip Achenbach
 * 
 * @see AttributeSetter
 */
public class AttributeSetterEByteArray extends AttributeSetter<byte[]> {

	/**
	 * Length of the Byte-array that is randomly filled.
	 */
	private int bytesize;


	/**
	 * Creates a new AttributeSetter for Byte[] attributes.
	 * 
	 * @param random
	 *            Random object used to create attribute values
	 * @param bytesize
	 *            length of the Byte-array
	 */
	public AttributeSetterEByteArray(Random random, int bytesize) {
		super(random);
		this.bytesize = bytesize;
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] createNewAttribute() {
		byte[] bytes = new byte[bytesize];
		getRandom().nextBytes(bytes);
		return bytes;
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<byte[]> createNewAttributes(int maxAmount) {
		List<byte[]> result = new ArrayList<byte[]>(maxAmount);
		for (int i = 0; i < maxAmount; i++) {
			result.add(createNewAttribute());
		}
		return result;
	}

}
