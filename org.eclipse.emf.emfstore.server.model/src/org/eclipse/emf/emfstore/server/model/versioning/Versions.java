package org.eclipse.emf.emfstore.server.model.versioning;

public class Versions {

	public static HeadVersionSpec HEAD_VERSION() {
		return VersioningFactory.eINSTANCE.createHeadVersionSpec();
	}

	public static HeadVersionSpec HEAD_VERSION(String branch) {
		HeadVersionSpec headVersionSpec = VersioningFactory.eINSTANCE.createHeadVersionSpec();
		headVersionSpec.setBranch(branch);
		return headVersionSpec;
	}

	public static HeadVersionSpec HEAD_VERSION(VersionSpec versionSpec) {
		if (versionSpec == null) {
			return HEAD_VERSION();
		}
		return HEAD_VERSION(versionSpec.getBranch());
	}

	public static PrimaryVersionSpec PRIMARY(String branch, int index) {
		PrimaryVersionSpec spec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
		spec.setIdentifier(index);
		spec.setBranch(branch);
		return spec;
	}

	public static PrimaryVersionSpec PRIMARY(VersionSpec versionSpec, int index) {
		return PRIMARY(versionSpec.getBranch(), index);
	}
}
