/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.core.subinterfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.impl.ProjectImpl;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.EmfStoreController;
import org.eclipse.emf.emfstore.server.ServerConfiguration;
import org.eclipse.emf.emfstore.server.core.AbstractEmfstoreInterface;
import org.eclipse.emf.emfstore.server.core.AbstractSubEmfstoreInterface;
import org.eclipse.emf.emfstore.server.core.helper.HistoryCache;
import org.eclipse.emf.emfstore.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.FatalEmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.exceptions.StorageException;
import org.eclipse.emf.emfstore.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.DateVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.HeadVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Version;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;

/**
 * This subinterfaces implements all version related functionality for the
 * {@link org.eclipse.emf.emfstore.server.core.EmfStoreImpl} interface.
 * 
 * @author wesendon
 */
public class VersionSubInterfaceImpl extends AbstractSubEmfstoreInterface {

	private HistoryCache historyCache;

	/**
	 * Default constructor.
	 * 
	 * @param parentInterface
	 *            parent interface
	 * @throws FatalEmfStoreException
	 *             in case of failure
	 */
	public VersionSubInterfaceImpl(AbstractEmfstoreInterface parentInterface) throws FatalEmfStoreException {
		super(parentInterface);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws FatalEmfStoreException
	 *             in case of failure
	 * @see org.eclipse.emf.emfstore.server.core.AbstractSubEmfstoreInterface#initSubInterface()
	 */
	@Override
	public void initSubInterface() throws FatalEmfStoreException {
		super.initSubInterface();
		historyCache = EmfStoreController.getInstance().getHistoryCache();
	}

	/**
	 * Resolves a versionSpec and delivers the corresponding primary
	 * versionSpec.
	 * 
	 * @param projectId
	 *            project id
	 * @param versionSpec
	 *            versionSpec
	 * @return primary versionSpec
	 * @throws EmfStoreException
	 *             if versionSpec can't be resolved or other failure
	 */
	public PrimaryVersionSpec resolveVersionSpec(ProjectId projectId, VersionSpec versionSpec) throws EmfStoreException {
		synchronized (getMonitor()) {
			ProjectHistory projectHistory = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId);
			// PrimaryVersionSpec
			if (versionSpec instanceof PrimaryVersionSpec && 0 <= ((PrimaryVersionSpec) versionSpec).getIdentifier()
				&& ((PrimaryVersionSpec) versionSpec).getIdentifier() < projectHistory.getVersions().size()) {
				return ((PrimaryVersionSpec) versionSpec);
				// HeadVersionSpec
			} else if (versionSpec instanceof HeadVersionSpec) {
				return ModelUtil.clone(getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId)
					.getLastVersion().getPrimarySpec());
				// DateVersionSpec
			} else if (versionSpec instanceof DateVersionSpec) {
				for (Version version : projectHistory.getVersions()) {
					LogMessage logMessage = version.getLogMessage();
					if (logMessage == null || logMessage.getDate() == null) {
						continue;
					}
					if (((DateVersionSpec) versionSpec).getDate().before(logMessage.getDate())) {
						Version previousVersion = version.getPreviousVersion();
						if (previousVersion == null) {
							return VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
						}
						return previousVersion.getPrimarySpec();
					}
				}
				return projectHistory.getLastVersion().getPrimarySpec();
				// TagVersionSpec
			} else if (versionSpec instanceof TagVersionSpec) {
				for (Version version : projectHistory.getVersions()) {
					for (TagVersionSpec tag : version.getTagSpecs()) {
						if (((TagVersionSpec) versionSpec).equals(tag)) {
							return ModelUtil.clone(version.getPrimarySpec());
						}
					}
				}
				throw new InvalidVersionSpecException();
			} else if (versionSpec instanceof BranchVersionSpec) {
				// TODO BRANCH
				return null;
			} else {

				throw new InvalidVersionSpecException();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param user
	 */
	public PrimaryVersionSpec createVersion(ProjectId projectId, PrimaryVersionSpec baseVersionSpec,
		ChangePackage changePackage, BranchVersionSpec targetBranch, PrimaryVersionSpec sourceVersion,
		LogMessage logMessage, ACUser user) throws EmfStoreException {
		synchronized (getMonitor()) {
			long currentTimeMillis = System.currentTimeMillis();
			ProjectHistory projectHistory = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId);

			// Find branch
			BranchInfo baseBranch = getBranchInfo(projectHistory, baseVersionSpec);
			Version baseVersion = getVersion(projectHistory, baseVersionSpec);
			// TODO BRANCH
			if (baseVersion == null || baseBranch == null) {
				// TODO BRANCH custom exception
				throw new EmfStoreException("Branch doesn't exist.");
			}

			// defined here fore scoping reasons
			Version newVersion = null;

			if (targetBranch == null || (baseVersion.getPrimarySpec().getBranch().equals(targetBranch.getBranch()))) {

				// If branch is null or branch equals base branch, create new version for specific branch
				if (!baseVersionSpec.equals(isHeadOfBranch(projectHistory, baseVersion.getPrimarySpec()))) {
					throw new BaseVersionOutdatedException();
				}
				newVersion = createVersion(projectHistory, changePackage, logMessage, user, baseVersion);
				newVersion.setPreviousVersion(baseVersion);
				baseBranch.setHead(EcoreUtil.copy(newVersion.getPrimarySpec()));

			} else if (getBranchInfo(projectHistory, targetBranch) == null) {

				// after check whether branch does NOT exist, create branch
				newVersion = createVersion(projectHistory, changePackage, logMessage, user, baseVersion);
				createNewBranch(projectHistory, baseVersion.getPrimarySpec(), newVersion.getPrimarySpec(), targetBranch);
				newVersion.setAncestorVersion(baseVersion);

			} else {
				// TODO BRANCH custom exception
				throw new EmfStoreException("invalid.");
			}
			if (sourceVersion != null) {
				// TODO BRANCH add sources
			}

			// TODO BRANCH fix in memory first, then persistence
			// try to save
			try {
				try {
					getResourceHelper().createResourceForProject(newVersion.getProjectState(),
						newVersion.getPrimarySpec(), projectHistory.getProjectId());
					getResourceHelper().createResourceForChangePackage(changePackage, newVersion.getPrimarySpec(),
						projectId);
					getResourceHelper().createResourceForVersion(newVersion, projectHistory.getProjectId());
				} catch (FatalEmfStoreException e) {
					// try to roll back
					baseVersion.setNextVersion(null);
					projectHistory.getVersions().remove(newVersion);
					// TODO: OW: why do we need to save here, can we remove? do
					// test!!
					save(baseVersion);
					save(projectHistory);
					throw new StorageException(StorageException.NOSAVE, e);
				}

				// delete projectstate from last revision depending on
				// persistence
				// policy
				handleOldProjectState(projectId, baseVersion);

				save(baseVersion);
				save(projectHistory);

				// update history cache
				// TODO BRANCH fix historyCache
				// historyCache.addVersionToCache(projectId, newVersion);
			} catch (FatalEmfStoreException e) {
				// roll back failed
				EmfStoreController.getInstance().shutdown(e);
				throw new EmfStoreException("Shutting down server.");
			}

			ModelUtil.logInfo("Total time for commit: " + (System.currentTimeMillis() - currentTimeMillis));
			return newVersion.getPrimarySpec();
		}
	}

	private void createNewBranch(ProjectHistory projectHistory, PrimaryVersionSpec baseSpec,
		PrimaryVersionSpec primarySpec, BranchVersionSpec branch) {
		primarySpec.setBranch(branch.getBranch());

		// TODO BRANCH make sure branch name is not null
		BranchInfo branchInfo = VersioningFactory.eINSTANCE.createBranchInfo();
		branchInfo.setName(branch.getBranch());
		branchInfo.setSource(EcoreUtil.copy(baseSpec));
		branchInfo.setHead(EcoreUtil.copy(primarySpec));

		projectHistory.getBranches().add(branchInfo);

	}

	private Version createVersion(ProjectHistory projectHistory, ChangePackage changePackage, LogMessage logMessage,
		ACUser user, Version previousHeadVersion) {
		Version newVersion = VersioningFactory.eINSTANCE.createVersion();

		// copy project and apply changes
		Project newProjectState = ((ProjectImpl) previousHeadVersion.getProjectState()).copy();
		changePackage.apply(newProjectState);
		newVersion.setProjectState(newProjectState);
		newVersion.setChanges(changePackage);

		logMessage.setDate(new Date());
		logMessage.setAuthor(user.getName());
		newVersion.setLogMessage(logMessage);

		// latest version == getVersion.size() (version start with index 0 as the list), IMPORTANT: branch has to be set
		// outside this method
		PrimaryVersionSpec newVersionSpec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
		newVersionSpec.setIdentifier(projectHistory.getVersions().size());
		newVersion.setPrimarySpec(newVersionSpec);
		newVersion.setNextVersion(null);

		projectHistory.getVersions().add(newVersion);
		return newVersion;
	}

	private Version getVersion(ProjectHistory projectHistory, PrimaryVersionSpec baseVersionSpec) {
		Version version = projectHistory.getVersions().get(baseVersionSpec.getIdentifier());
		if (version == null || !version.getPrimarySpec().equals(baseVersionSpec)) {
			return null;
		}
		return version;
	}

	private PrimaryVersionSpec isHeadOfBranch(ProjectHistory projectHistory, PrimaryVersionSpec versionSpec) {
		BranchInfo branchInfo = getBranchInfo(projectHistory, versionSpec);
		if (branchInfo != null && branchInfo.getHead().equals(versionSpec)) {
			return branchInfo.getHead();
		}
		return null;
	}

	private BranchInfo getBranchInfo(ProjectHistory projectHistory, VersionSpec versionSpec) {
		for (BranchInfo branchInfo : projectHistory.getBranches()) {
			if (branchInfo.getName().equals(versionSpec.getBranch())) {
				return branchInfo;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	// TODO what's the purpose of this task
	@Deprecated
	public PrimaryVersionSpec createVersionForProject(ProjectId projectId, PrimaryVersionSpec baseVersionSpec,
		ChangePackage changePackage, LogMessage logMessage) throws EmfStoreException {
		synchronized (getMonitor()) {

			long currentTimeMillis = System.currentTimeMillis();

			ProjectHistory projectHistory = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId);
			List<Version> versions = projectHistory.getVersions();

			// OW: check here if base version is valid at all

			if (versions.size() - 1 != baseVersionSpec.getIdentifier()) {
				throw new BaseVersionOutdatedException();
			}

			PrimaryVersionSpec newVersionSpec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
			newVersionSpec.setIdentifier(baseVersionSpec.getIdentifier() + 1);

			Version newVersion = VersioningFactory.eINSTANCE.createVersion();

			Version previousHeadVersion = versions.get(versions.size() - 1);

			Project newProjectState = ModelUtil.clone(previousHeadVersion.getProjectState());

			changePackage.apply(newProjectState);

			newVersion.setProjectState(newProjectState);
			newVersion.setChanges(changePackage);
			logMessage.setDate(new Date());
			newVersion.setLogMessage(logMessage);
			newVersion.setPrimarySpec(newVersionSpec);
			newVersion.setNextVersion(null);
			newVersion.setPreviousVersion(previousHeadVersion);

			versions.add(newVersion);

			// TODO BRANCH fix persistence
			// try to save
			try {
				try {
					getResourceHelper().createResourceForProject(newProjectState, newVersion.getPrimarySpec(),
						projectHistory.getProjectId());
					getResourceHelper().createResourceForChangePackage(changePackage, newVersion.getPrimarySpec(),
						projectId);
					getResourceHelper().createResourceForVersion(newVersion, projectHistory.getProjectId());
				} catch (FatalEmfStoreException e) {
					// try to roll back
					previousHeadVersion.setNextVersion(null);
					versions.remove(newVersion);
					// OW: why do we need to save here, can we remove? do test!!
					save(previousHeadVersion);
					save(projectHistory);
					throw new StorageException(StorageException.NOSAVE);
				}

				// delete projectstate from last revision depending on
				// persistence
				// policy
				handleOldProjectState(projectId, previousHeadVersion);

				save(previousHeadVersion);
				save(projectHistory);

				// update history cache
				historyCache.addVersionToCache(projectId, newVersion);
			} catch (FatalEmfStoreException e) {
				// roll back failed
				EmfStoreController.getInstance().shutdown(e);
				throw new EmfStoreException("Shutting down server.");
			}

			ModelUtil.logInfo("Total time for commit: " + (System.currentTimeMillis() - currentTimeMillis));
			return newVersionSpec;
		}
	}

	/**
	 * Deletes projectstate from last revision depending on persistence policy.
	 * 
	 * @param projectId
	 *            project id
	 * @param previousHeadVersion
	 *            last head version
	 */
	private void handleOldProjectState(ProjectId projectId, Version previousHeadVersion) {
		String property = ServerConfiguration.getProperties().getProperty(
			ServerConfiguration.PROJECTSTATE_VERSION_PERSISTENCE,
			ServerConfiguration.PROJECTSPACE_VERSION_PERSISTENCE_DEFAULT);

		if (property.equals(ServerConfiguration.PROJECTSTATE_VERSION_PERSISTENCE_EVERYXVERSIONS)) {

			int x = getResourceHelper().getXFromPolicy(
				ServerConfiguration.PROJECTSTATE_VERSION_PERSISTENCE_EVERYXVERSIONS_X,
				ServerConfiguration.PROJECTSTATE_VERSION_PERSISTENCE_EVERYXVERSIONS_X_DEFAULT, false);

			// always save projecstate of first version
			int lastVersion = previousHeadVersion.getPrimarySpec().getIdentifier();
			if (lastVersion != 0 && lastVersion % x != 0) {
				getResourceHelper().deleteProjectState(previousHeadVersion, projectId);
			}

		} else {
			getResourceHelper().deleteProjectState(previousHeadVersion, projectId);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ChangePackage> getChanges(ProjectId projectId, VersionSpec source, VersionSpec target)
		throws EmfStoreException {
		synchronized (getMonitor()) {
			PrimaryVersionSpec resolvedSource = resolveVersionSpec(projectId, source);
			PrimaryVersionSpec resolvedTarget = resolveVersionSpec(projectId, target);

			// if target and source are equal return empty list
			if (resolvedSource.getIdentifier() == resolvedTarget.getIdentifier()) {
				return new ArrayList<ChangePackage>();
			}
			boolean updateForward = resolvedTarget.getIdentifier() > resolvedSource.getIdentifier();

			// Example: if you want the changes to get from version 5 to 7, you
			// need the changes contained in version 6
			// and 7. The reason is that each version holds the changes which
			// occurred from the predecessor to the
			// version itself. Version 5 holds the changes to get from version 4
			// to 5 and therefore is irrelevant.
			// So the lower bound (source and target can be inverse too) has to
			// be counted up by one.
			if (resolvedSource.getIdentifier() < resolvedTarget.getIdentifier()) {
				resolvedSource.setIdentifier(resolvedSource.getIdentifier() + 1);
			} else {
				resolvedTarget.setIdentifier(resolvedTarget.getIdentifier() + 1);
			}

			List<ChangePackage> result = new ArrayList<ChangePackage>();
			for (Version version : getVersions(projectId, resolvedSource, resolvedTarget)) {
				ChangePackage changes = version.getChanges();
				changes.setLogMessage(ModelUtil.clone(version.getLogMessage()));
				result.add(changes);
			}

			// if source is after target in time
			if (!updateForward) {
				// reverse list and change packages
				List<ChangePackage> resultReverse = new ArrayList<ChangePackage>();
				for (ChangePackage changePackage : result) {
					ChangePackage changePackageReverse = changePackage.reverse();
					// copy again log message
					// reverse() created a new change package without copying
					// existent attributes
					changePackageReverse.setLogMessage(ModelUtil.clone(changePackage.getLogMessage()));
					resultReverse.add(changePackageReverse);
				}

				Collections.reverse(resultReverse);
				result = resultReverse;
			}

			return result;
		}
	}

	/**
	 * Returns the specified version of a project.
	 * 
	 * @param projectId
	 *            project id
	 * @param versionSpec
	 *            versionSpec
	 * @return the version
	 * @throws EmfStoreException
	 *             if version couldn't be found
	 */
	protected Version getVersion(ProjectId projectId, PrimaryVersionSpec versionSpec) throws EmfStoreException {
		EList<Version> versions = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId).getVersions();
		if (versionSpec.getIdentifier() < 0 || versionSpec.getIdentifier() > versions.size() - 1) {
			throw new InvalidVersionSpecException();
		}
		return versions.get(versionSpec.getIdentifier());
	}

	/**
	 * Returns a list of versions starting from source and ending with target.
	 * This method returns the version always in an ascanding order. So if you
	 * need it ordered differently you have to reverse the list.
	 * 
	 * @param projectId
	 *            project id
	 * @param source
	 *            source
	 * @param target
	 *            target
	 * @return list of versions
	 * @throws EmfStoreException
	 *             if source or target are out of range or any other problem
	 *             occurs
	 */
	protected List<Version> getVersions(ProjectId projectId, PrimaryVersionSpec source, PrimaryVersionSpec target)
		throws EmfStoreException {
		if (source.compareTo(target) < 1) {
			EList<Version> versions = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId)
				.getVersions();
			if (source.getIdentifier() < 0 || source.getIdentifier() > versions.size() - 1
				|| target.getIdentifier() < 0 || target.getIdentifier() > versions.size() - 1) {
				throw new InvalidVersionSpecException();
			}
			List<Version> result = new ArrayList<Version>();
			Iterator<Version> iter = versions.listIterator(source.getIdentifier());
			int steps = target.getIdentifier() - source.getIdentifier();
			while (iter.hasNext() && steps-- >= 0) {
				result.add(iter.next());
			}
			return result;
		} else {
			return getVersions(projectId, target, source);
		}
	}
}
