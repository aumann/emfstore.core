package org.eclipse.emf.emfstore.client.test.server.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.server.model.versioning.Version;
import org.junit.Test;

public class SampleAPITest extends CoreServerTest {

	@Test
	public void createProject() throws EmfStoreException {
		final ProjectSpace ps = getProjectSpace();
		ps.init();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				ps.getProject().addModelElement(createTestElement("Horst"));

				try {
					ps.shareProject();
				} catch (EmfStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		assertEquals(1, getServerSpace().getProjects().size());
		ProjectHistory projectHistory = getServerSpace().getProjects().get(0);

		Version version = projectHistory.getVersions().get(projectHistory.getVersions().size() - 1);
		Project projectState = version.getProjectState();
		assertEquals(1, project.getModelElements().size());
		assertTrue("Horst".equals(((TestElement) project.getModelElements().get(0)).getName()));

	}
}
