package org.eclipse.emf.emfstore.client.test.conflictDetection.merging;

import java.util.Arrays;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.client.test.model.task.ActionItem;
import org.eclipse.emf.emfstore.client.test.model.task.TaskFactory;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.DecisionManager;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.junit.Test;

public class AttributeMergeTest extends MergeTest {

	@Test
	public void attributeCollision() {
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
		Project project = projectSpace2.getProject();
		final ActionItem theirItem = (ActionItem) project.getModelElement(project.getModelElementId(item));

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

		manager.calcResult();

		manager.getAcceptedMine();
		manager.getRejectedTheirs();
	}

	@Test
	public void betterAttributeCollision() {
		final MergeCase mergeCase = newMergeCase();

		ActionItem item = TaskFactory.eINSTANCE.createActionItem();
		mergeCase.add(item);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(ActionItem.class).setName("Otto");
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(ActionItem.class).setName("Max");
			}
		}.run(false);

		mergeCase.execute();
	}

}
