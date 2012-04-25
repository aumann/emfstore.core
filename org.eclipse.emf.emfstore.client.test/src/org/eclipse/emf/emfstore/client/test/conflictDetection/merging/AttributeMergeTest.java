/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering, Technische Universitaet Muenchen. All rights
 * reserved. This program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.conflictDetection.merging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.client.test.model.task.ActionItem;
import org.eclipse.emf.emfstore.client.test.model.task.TaskFactory;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.DecisionManager;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.conflict.Conflict;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.conflict.conflicts.AttributeConflict;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AttributeOperation;
import org.junit.Test;

/**
 * AttributeOperation merge tests.
 * 
 * @author wesendon
 */
public class AttributeMergeTest extends MergeTest {

	/**
	 * Test without the convenience methods.
	 */
	@Test
	public void oldAttributeCollision() {
		final ActionItem item = new EMFStoreCommandWithResult<ActionItem>() {
			@Override
			protected ActionItem doRun() {
				Project myProject = getProject();
				ActionItem item = TaskFactory.eINSTANCE.createActionItem();
				myProject.addModelElement(item);
				return item;
			}
		}.run(false);

		clearOperations();

		ProjectSpace projectSpace2 = cloneProjectSpace(getProjectSpace());
		Project project2 = projectSpace2.getProject();
		final ActionItem theirItem = (ActionItem) project2.getModelElement(getProject().getModelElementId(item));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				item.setName("Otto");
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				theirItem.setName("Max");
			}
		}.run(false);

		PrimaryVersionSpec spec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
		spec.setIdentifier(2);

		DecisionManager manager = new DecisionManager(getProject(), getProjectSpace().getLocalChangePackage(true),
			Arrays.asList(projectSpace2.getLocalChangePackage(true)), spec, spec);

		ArrayList<Conflict> conflicts = manager.getConflicts();

		assertEquals(conflicts.size(), 1);
		assertTrue(conflicts.get(0) instanceof AttributeConflict);
		Conflict conflict = conflicts.get(0);
		assertTrue(conflict.getMyOperations().size() == 1);
		assertTrue(conflict.getMyOperation() instanceof AttributeOperation);
		assertTrue(conflict.getTheirOperations().size() == 1);
		assertTrue(conflict.getTheirOperation() instanceof AttributeOperation);
	}

	/**
	 * Simple attribute conflict on name attribute.
	 */
	@Test
	public void simpleAttributeCollision() {
		final MergeCase mergeCase = newMergeCase();

		final ActionItem item = TaskFactory.eINSTANCE.createActionItem();
		mergeCase.add(item);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(item).setName("Otto");
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(item).setName("Max");
			}
		}.run(false);

		ArrayList<Conflict> conflicts = mergeCase.execute().getConflicts();

		assertEquals(conflicts.size(), 1);
		assertTrue(conflicts.get(0) instanceof AttributeConflict);
		Conflict conflict = conflicts.get(0);
		assertTrue(conflict.getMyOperations().size() == 1);
		assertTrue(conflict.getMyOperation() instanceof AttributeOperation);
		assertTrue(conflict.getTheirOperations().size() == 1);
		assertTrue(conflict.getTheirOperation() instanceof AttributeOperation);
	}

	/**
	 * Simple attribute conflict on name attribute with uninvoled change before.
	 */
	@Test
	public void simpleAttributeCollisionWithUninvolvedBefore() {
		final MergeCase mergeCase = newMergeCase();

		final ActionItem item = TaskFactory.eINSTANCE.createActionItem();
		mergeCase.add(item);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(item).setDescription("some random description");
				mergeCase.getMyItem(item).setName("Otto");
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(item).setName("Max");
			}
		}.run(false);

		ArrayList<Conflict> conflicts = mergeCase.execute().getConflicts();

		assertEquals(conflicts.size(), 1);
		assertTrue(conflicts.get(0) instanceof AttributeConflict);
		Conflict conflict = conflicts.get(0);
		assertTrue(conflict.getMyOperations().size() == 1);
		assertTrue(conflict.getMyOperation() instanceof AttributeOperation);
		assertTrue(conflict.getTheirOperations().size() == 1);
		assertTrue(conflict.getTheirOperation() instanceof AttributeOperation);
	}

	/**
	 * Simple attribute conflict on name attribute with uninvoled change after.
	 */
	@Test
	public void simpleAttributeCollisionWithUninvolvedAfter() {
		final MergeCase mergeCase = newMergeCase();

		final ActionItem item = TaskFactory.eINSTANCE.createActionItem();
		mergeCase.add(item);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(item).setName("Otto");
				mergeCase.getMyItem(item).setDescription("some random description");
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(item).setName("Max");
			}
		}.run(false);

		ArrayList<Conflict> conflicts = mergeCase.execute().getConflicts();

		assertEquals(conflicts.size(), 1);
		assertTrue(conflicts.get(0) instanceof AttributeConflict);
		Conflict conflict = conflicts.get(0);
		assertTrue(conflict.getMyOperations().size() == 1);
		assertTrue(conflict.getMyOperation() instanceof AttributeOperation);
		assertTrue(conflict.getTheirOperations().size() == 1);
		assertTrue(conflict.getTheirOperation() instanceof AttributeOperation);
	}

	/**
	 * Simple attribute conflict on name attribute with uninvoled change before on their side.
	 */
	@Test
	public void simpleAttributeCollisionWithUninvolvedBeforeTheir() {
		final MergeCase mergeCase = newMergeCase();

		final ActionItem item = TaskFactory.eINSTANCE.createActionItem();
		mergeCase.add(item);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(item).setName("Otto");
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(item).setDescription("some random description");
				mergeCase.getTheirItem(item).setName("Max");
			}
		}.run(false);

		ArrayList<Conflict> conflicts = mergeCase.execute().getConflicts();

		assertEquals(conflicts.size(), 1);
		assertTrue(conflicts.get(0) instanceof AttributeConflict);
		Conflict conflict = conflicts.get(0);
		assertTrue(conflict.getMyOperations().size() == 1);
		assertTrue(conflict.getMyOperation() instanceof AttributeOperation);
		assertTrue(conflict.getTheirOperations().size() == 1);
		assertTrue(conflict.getTheirOperation() instanceof AttributeOperation);
	}
}
