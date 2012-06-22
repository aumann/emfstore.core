/**
 * <copyright> Copyright (c) 2008-2009 Jonas Helming, Maximilian Koegel. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html </copyright>
 */

package org.eclipse.emf.emfstore.client.test;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.emf.emfstore.client.model.Configuration;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.common.model.Project;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

/**
 * Abstract Superclass for Workspace Tests. Provides Setup and Tear-down.
 * 
 * @author koegel
 */
public abstract class WorkspaceTest {
	protected Project project;
	protected ProjectSpace projectSpace;
	protected static Workspace workspace;

	/**
	 * Setup a dummy project for testing.
	 */
	@Before
	public void setupProjectSpace() {
		Configuration.setTesting(true);
		final Workspace workspace = WorkspaceManager.getInstance().getCurrentWorkspace();
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				ProjectSpace localProject = workspace.createLocalProject("testProject", "test Project");
				setProjectSpace(localProject);
				setProject(getProjectSpace().getProject());
			}
		}.run(false);

	}

	/**
	 * Clean workspace.
	 */
	@After
	public void cleanProjectSpace() {
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				try {
					WorkspaceManager.getInstance().getCurrentWorkspace().deleteProjectSpace(getProjectSpace());
				} catch (IOException e) {
					fail();
				}
			}
		}.run(false);
	}

	/**
	 * Delete all persisted data.
	 */
	@AfterClass
	public static void deleteData() {
		SetupHelper.cleanupWorkspace();
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param projectSpace the projectSpace to set
	 */
	private void setProjectSpace(ProjectSpace projectSpace) {
		this.projectSpace = projectSpace;
	}

	/**
	 * @return the projectSpace
	 */
	public ProjectSpace getProjectSpace() {
		return projectSpace;
	}

	/**
	 * Clear all operations from project space.
	 */
	protected void clearOperations() {
		getProjectSpace().getOperations().clear();
		getProjectSpace().getOperationManager().clearOperations();
	}

	/**
	 * Creates an test element.
	 * 
	 * @return test element
	 */
	protected TestElement createTestElementWithoutTransaction() {
		TestElement element = TestmodelFactory.eINSTANCE.createTestElement();
		getProject().getModelElements().add(element);
		return element;
	}

	/**
	 * Creates an test element.
	 * 
	 * @return test element
	 */
	protected TestElement getTestElement(String name) {
		TestElement element = TestmodelFactory.eINSTANCE.createTestElement();
		element.setName(name);
		return element;
	}

	public TestElement getTestElement() {
		return getTestElement("");
	}

	public TestElement createTestElement() {
		return new EMFStoreCommandWithResult<TestElement>() {
			@Override
			protected TestElement doRun() {
				return createTestElementWithoutTransaction();
			}
		}.run(false);
	}

	public TestElement createFilledTestElement(final int count) {
		final TestElement testElement = createTestElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = 0; i < count; i++) {
					testElement.getStrings().add("value" + i);
				}
			}
		}.run(false);

		return testElement;
	}
}
