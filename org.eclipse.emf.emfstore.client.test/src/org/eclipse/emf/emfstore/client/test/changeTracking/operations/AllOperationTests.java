/**
 * <copyright> Copyright (c) 2008-2009 Jonas Helming, Maximilian Koegel. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html </copyright>
 */
package org.eclipse.emf.emfstore.client.test.changeTracking.operations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite for running all tests of workspace.
 * 
 * @author koegel
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AttributeOperationTest.class, SingleReferenceOperationTest.class,
	MultiReferenceOperationTest.class, CreateDeleteOperationTest.class, MultiReferenceMoveOperationTest.class,
	CompositeOperationTest.class, MultiAttributeMoveOperationTest.class, MultiAttributeSetTest.class,
	MultiAttributeTest.class, MultiReferenceSetOperationTest.class })
public class AllOperationTests {

}
