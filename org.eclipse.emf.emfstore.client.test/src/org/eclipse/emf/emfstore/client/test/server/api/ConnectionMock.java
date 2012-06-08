package org.eclipse.emf.emfstore.client.test.server.api;

import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.common.model.EMFStoreProperty;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.server.core.EmfStoreImpl;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.filetransfer.FileChunk;
import org.eclipse.emf.emfstore.server.filetransfer.FileTransferInformation;
import org.eclipse.emf.emfstore.server.model.ClientVersionInfo;
import org.eclipse.emf.emfstore.server.model.ModelFactory;
import org.eclipse.emf.emfstore.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.SessionId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.server.model.accesscontrol.OrgUnitProperty;
import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;

public class ConnectionMock implements ConnectionManager {

	private final EmfStoreImpl emfStore;
	private final AuthControlMock authMock;
	private HashSet<SessionId> sessions;

	public ConnectionMock(EmfStoreImpl emfStore, AuthControlMock authMock) {
		this.emfStore = emfStore;
		this.authMock = authMock;
		sessions = new HashSet<SessionId>();
	}

	public SessionId logIn(String username, String password, ServerInfo severInfo, ClientVersionInfo clientVersionInfo)
		throws EmfStoreException {
		SessionId sessionId = ModelFactory.eINSTANCE.createSessionId();
		sessions.add(sessionId);
		return sessionId;
	}

	public void logout(SessionId sessionId) throws EmfStoreException {
		sessions.remove(sessionId);
	}

	public boolean isLoggedIn(SessionId id) {
		return sessions.contains(id);
	}

	public void checkSessionId(SessionId sessionId) throws EmfStoreException {
		if (!isLoggedIn(sessionId))
			throw new AccessControlException();
	}

	public List<ProjectInfo> getProjectList(SessionId sessionId) throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.getProjectList(sessionId);
	}

	public Project getProject(SessionId sessionId, ProjectId projectId, VersionSpec versionSpec)
		throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.getProject(sessionId, projectId, versionSpec);
	}

	public PrimaryVersionSpec createVersion(SessionId sessionId, ProjectId projectId,
		PrimaryVersionSpec baseVersionSpec, ChangePackage changePackage, BranchVersionSpec targetBranch,
		PrimaryVersionSpec sourceVersion, LogMessage logMessage) throws EmfStoreException, InvalidVersionSpecException {
		checkSessionId(sessionId);
		return emfStore.createVersion(sessionId, projectId, baseVersionSpec, changePackage, targetBranch,
			sourceVersion, logMessage);
	}

	public PrimaryVersionSpec resolveVersionSpec(SessionId sessionId, ProjectId projectId, VersionSpec versionSpec)
		throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.resolveVersionSpec(sessionId, projectId, versionSpec);
	}

	public List<ChangePackage> getChanges(SessionId sessionId, ProjectId projectId, VersionSpec source,
		VersionSpec target) throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.getChanges(sessionId, projectId, source, target);
	}

	public List<BranchInfo> getBranches(SessionId sessionId, ProjectId projectId) throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.getBranches(sessionId, projectId);
	}

	public List<HistoryInfo> getHistoryInfo(SessionId sessionId, ProjectId projectId, HistoryQuery historyQuery)
		throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.getHistoryInfo(sessionId, projectId, historyQuery);
	}

	public void addTag(SessionId sessionId, ProjectId projectId, PrimaryVersionSpec versionSpec, TagVersionSpec tag)
		throws EmfStoreException {
		checkSessionId(sessionId);
		emfStore.addTag(sessionId, projectId, versionSpec, tag);
	}

	public void removeTag(SessionId sessionId, ProjectId projectId, PrimaryVersionSpec versionSpec, TagVersionSpec tag)
		throws EmfStoreException {
		checkSessionId(sessionId);
		emfStore.removeTag(sessionId, projectId, versionSpec, tag);
	}

	public ProjectInfo createEmptyProject(SessionId sessionId, String name, String description, LogMessage logMessage)
		throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.createEmptyProject(sessionId, name, description, logMessage);
	}

	public ProjectInfo createProject(SessionId sessionId, String name, String description, LogMessage logMessage,
		Project project) throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.createProject(sessionId, name, description, logMessage, EcoreUtil.copy(project));
	}

	public void deleteProject(SessionId sessionId, ProjectId projectId, boolean deleteFiles) throws EmfStoreException {
		checkSessionId(sessionId);
		emfStore.deleteProject(sessionId, projectId, deleteFiles);
	}

	public ACUser resolveUser(SessionId sessionId, ACOrgUnitId id) throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.resolveUser(sessionId, id);
	}

	public ProjectId importProjectHistoryToServer(SessionId sessionId, ProjectHistory projectHistory)
		throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.importProjectHistoryToServer(sessionId, projectHistory);
	}

	public ProjectHistory exportProjectHistoryFromServer(SessionId sessionId, ProjectId projectId)
		throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.exportProjectHistoryFromServer(sessionId, projectId);
	}

	public FileTransferInformation uploadFileChunk(SessionId sessionId, ProjectId projectId, FileChunk fileChunk)
		throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.uploadFileChunk(sessionId, projectId, fileChunk);
	}

	public FileChunk downloadFileChunk(SessionId sessionId, ProjectId projectId, FileTransferInformation fileInformation)
		throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.downloadFileChunk(sessionId, projectId, fileInformation);
	}

	public void transmitProperty(SessionId sessionId, OrgUnitProperty changedProperty, ACUser user, ProjectId projectId)
		throws EmfStoreException {
		checkSessionId(sessionId);
		emfStore.transmitProperty(sessionId, changedProperty, user, projectId);
	}

	public List<EMFStoreProperty> setEMFProperties(SessionId sessionId, List<EMFStoreProperty> property,
		ProjectId projectId) throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.setEMFProperties(sessionId, property, projectId);
	}

	public List<EMFStoreProperty> getEMFProperties(SessionId sessionId, ProjectId projectId) throws EmfStoreException {
		checkSessionId(sessionId);
		return emfStore.getEMFProperties(sessionId, projectId);
	}
}
