/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering, Technische Universitaet Muenchen. All rights
 * reserved. This program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.conflictDetection.merging;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.test.conflictDetection.ConflictDetectionTest;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.DecisionManager;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;

/**
 * Helper super class for merge tests.
 * 
 * @author wesendon
 */
public class MergeTest extends ConflictDetectionTest {

	/**
	 * Default Constructor.
	 * 
	 * @return case helper
	 */
	public MergeCase newMergeCase() {
		return new MergeCase();
	}

	/**
	 * Helper class for merge tests. It manages the two projectspaces and offers covenience methods.
	 * 
	 * @author wesendon
	 */
	public class MergeCase {

		private ProjectSpace theirProjectSpace;

		public void add(EObject... objs) {
			for (EObject obj : objs) {
				getProject().addModelElement(obj);
			}
		}

		@SuppressWarnings("unchecked")
		public <T extends EObject> T getMyItem(T id) {
			ensureCopy();
			return (T) getProject().getModelElement(byId(id));
		}

		@SuppressWarnings("unchecked")
		public <T extends EObject> T getTheirItem(T id) {
			ensureCopy();
			return (T) getTheirProject().getModelElement(byId(id));
		}

		private ModelElementId byId(EObject id) {
			return getProject().getModelElementId(id);
		}

		private void ensureCopy() {
			if (theirProjectSpace == null) {
				clearOperations();
				this.theirProjectSpace = cloneProjectSpace(getProjectSpace());
			}
		}

		public Project getTheirProject() {
			ensureCopy();
			return this.theirProjectSpace.getProject();
		}

		public ProjectSpace getTheirProjectSpace() {
			ensureCopy();
			return this.theirProjectSpace;
		}

		public DecisionManager execute() {
			ensureCopy();
			PrimaryVersionSpec spec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
			spec.setIdentifier(23);

			DecisionManager manager = new DecisionManager(getProject(), getProjectSpace().getLocalChangePackage(true),
				Arrays.asList(getTheirProjectSpace().getLocalChangePackage(true)), spec, spec);

			return manager;
		}
	}
}
