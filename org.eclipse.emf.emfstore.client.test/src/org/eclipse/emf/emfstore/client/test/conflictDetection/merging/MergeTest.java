package org.eclipse.emf.emfstore.client.test.conflictDetection.merging;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.test.conflictDetection.ConflictDetectionTest;

public class MergeTest extends ConflictDetectionTest {

	public MergeCase newMergeCase() {
		return new MergeCase(this);
	}

	public class MergeCase {
		private final MergeTest testSuite;

		public MergeCase(MergeTest test) {
			this.testSuite = test;

		}

		public void add(EObject obj) {
			// TODO Auto-generated method stub

		}

		public <T extends EObject> T getMyItem(Class<T> clazz) {
			return null;
		}

		public <T extends EObject> T getTheirItem(Class<T> clazz) {
			return null;
		}

		public void execute() {

		}
	}
}
