/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH,
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.modelmutator.test;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.emfstore.client.model.Configuration;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.AdminConnectionManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.client.test.SetupHelper;
import org.eclipse.emf.emfstore.common.CommonUtil;
import org.eclipse.emf.emfstore.common.model.ModelFactory;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.server.ServerConfiguration;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.InvalidInputException;
import org.eclipse.emf.emfstore.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.SessionId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.server.model.accesscontrol.AccesscontrolFactory;
import org.eclipse.emf.emfstore.server.model.accesscontrol.roles.RolesPackage;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PathQuery;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Superclass for server tests.
 * 
 * @author wesendon
 */

public class ModelMutatorServerSetup {

	/**
	 * @return the sessionId
	 */
	public static SessionId getSessionId() {
		return authenticationInfo.getSessionId();
	}

	/**
	 * @return the connectionManager
	 */
	public static ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	/**
	 * @return the generatedProject
	 */
	public static Project getGeneratedProject() {
		return generatedProject;
	}

	/**
	 * @return the generatedProjectId
	 */
	public static ProjectId getGeneratedProjectId() {
		return generatedProjectId;
	}

	/**
	 * @return the ProjectInfo
	 */
	public static ProjectInfo getProjectInfo() {
		return projectInfo;
	}

	/**
	 * @return the projectsOnServerBeforeTest
	 */
	public static int getProjectsOnServerBeforeTest() {
		return projectsOnServerBeforeTest;
	}

	/**
	 * @return the generatedProjectVersion
	 */
	public static PrimaryVersionSpec getGeneratedProjectVersion() {
		return generatedProjectVersion;
	}

	private static AuthenticationInformation authenticationInfo;
	private static ConnectionManager connectionManager;
	private static Project generatedProject;
	private static ProjectId generatedProjectId;
	private static ProjectInfo projectInfo;
	private static int projectsOnServerBeforeTest;
	private static PrimaryVersionSpec generatedProjectVersion;
	private static HashMap<Class<?>, Object> arguments;

	/**
	 * Start server and gain sessionid.
	 * 
	 * @throws EmfStoreException in case of failure
	 * @throws IOException
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws EmfStoreException, IOException {
		ServerConfiguration.setTesting(true);
		CommonUtil.setTesting(true);

		SetupHelper.addUserFileToServer(false);

		SetupHelper.startSever();
		connectionManager = WorkspaceManager.getInstance().getConnectionManager();
		login(SetupHelper.getServerInfo());
		// FIXME: re-add when new project generator is available
		generatedProject = ModelFactory.eINSTANCE.createProject();
		projectsOnServerBeforeTest = 1;
		initArguments();
	}

	/**
	 * sets users on server.
	 * 
	 * @throws EmfStoreException in case of failure
	 */

	public static void setupUsers() throws EmfStoreException {
		try {
			AdminConnectionManager adminConnectionManager = WorkspaceManager.getInstance().getAdminConnectionManager();
			for (ACUser user : adminConnectionManager.getUsers(authenticationInfo.getSessionId())) {
				if (user.getName().equals("reader") || user.getName().equals("writer1")
					|| user.getName().equals("writer2") || user.getName().equals("projectadmin")) {
					throw new InvalidInputException("User already exists");
				}
			}

			ACOrgUnitId orgUnitId = SetupHelper.createUserOnServer("reader");
			SetupHelper.setUsersRole(orgUnitId, RolesPackage.eINSTANCE.getReaderRole(), getGeneratedProjectId());

			orgUnitId = SetupHelper.createUserOnServer("writer1");
			SetupHelper.setUsersRole(orgUnitId, RolesPackage.eINSTANCE.getWriterRole(), getGeneratedProjectId());

			orgUnitId = SetupHelper.createUserOnServer("writer2");
			SetupHelper.setUsersRole(orgUnitId, RolesPackage.eINSTANCE.getWriterRole(), getGeneratedProjectId());

			orgUnitId = SetupHelper.createUserOnServer("projectadmin");
			SetupHelper.setUsersRole(orgUnitId, RolesPackage.eINSTANCE.getProjectAdminRole(), getGeneratedProjectId());
		} catch (InvalidInputException e) {
			// do nothing, user already exists.
		}
	}

	/**
	 * Sets the connection manager.
	 * 
	 * @param cm connection manager
	 */
	protected static void setConnectionManager(ConnectionManager cm) {
		connectionManager = cm;
	}

	/**
	 * @param serverInfo serverinfo
	 * @throws EmfStoreException in case of failure
	 */
	protected static void login(ServerInfo serverInfo) throws EmfStoreException {
		authenticationInfo = login(serverInfo, "super", "super");
		WorkspaceManager.getInstance().getAdminConnectionManager()
			.initConnection(serverInfo, authenticationInfo.getSessionId());
	}

	/**
	 * @param serverInfo serverinfo
	 * @param username username
	 * @param password password
	 * @return sessionId
	 * @throws EmfStoreException in case of failure
	 */
	protected static AuthenticationInformation login(ServerInfo serverInfo, String username, String password)
		throws EmfStoreException {
		return connectionManager.logIn(username, KeyStoreManager.getInstance().encrypt(password, serverInfo),
			serverInfo, Configuration.getClientVersion());
	}

	/**
	 * Shuts down server after testing.
	 */
	@AfterClass
	public static void tearDownAfterClass() {
		SetupHelper.stopServer();
		// SetupHelper.cleanupServer();
	}

	/**
	 * Compares two projects.
	 * 
	 * @param expected expected
	 * @param compare to be compared
	 */
	public static void assertEqual(Project expected, Project compare) {
		try {
			if (!ModelUtil.eObjectToString(expected).equals(ModelUtil.eObjectToString(compare))) {
				throw new AssertionError("Projects are not equal.");
			}
		} catch (SerializationException e) {
			throw new AssertionError("Couldn't compare projects.");
		}
	}

	/**
	 * Adds a project to the server before test.
	 * 
	 * @throws EmfStoreException in case of failure
	 */
	@Before
	public void beforeTest() throws EmfStoreException {
		projectInfo = connectionManager.createProject(authenticationInfo.getSessionId(), "initialProject",
			"TestProject", SetupHelper.createLogMessage("super", "a logmessage"), generatedProject);
		generatedProjectId = projectInfo.getProjectId();
		generatedProjectVersion = projectInfo.getVersion();
		setupUsers();
	}

	/**
	 * Removes all projects from server after test.
	 * 
	 * @throws EmfStoreException in case of failure
	 */
	@After
	public void afterTest() throws EmfStoreException {
		for (ProjectInfo info : connectionManager.getProjectList(authenticationInfo.getSessionId())) {
			connectionManager.deleteProject(authenticationInfo.getSessionId(), info.getProjectId(), true);
		}
	}

	/**
	 * Creates a historyquery.
	 * 
	 * @param ver1 source
	 * @param ver2 target
	 * @return historyquery
	 */
	public static HistoryQuery createHistoryQuery(PrimaryVersionSpec ver1, PrimaryVersionSpec ver2) {
		PathQuery historyQuery = VersioningFactory.eINSTANCE.createPathQuery();
		historyQuery.setSource((PrimaryVersionSpec) EcoreUtil.copy(ver1));
		historyQuery.setTarget((PrimaryVersionSpec) EcoreUtil.copy(ver2));
		return historyQuery;
	}

	private static void initArguments() {
		arguments = new HashMap<Class<?>, Object>();
		arguments.put(boolean.class, false);
		arguments.put(String.class, new String());
		arguments.put(SessionId.class, EcoreUtil.copy(getSessionId()));
		arguments.put(ProjectId.class, org.eclipse.emf.emfstore.server.model.ModelFactory.eINSTANCE.createProjectId());
		arguments.put(PrimaryVersionSpec.class, VersioningFactory.eINSTANCE.createPrimaryVersionSpec());
		arguments.put(VersionSpec.class, VersioningFactory.eINSTANCE.createPrimaryVersionSpec());
		arguments.put(TagVersionSpec.class, VersioningFactory.eINSTANCE.createTagVersionSpec());
		arguments.put(LogMessage.class, VersioningFactory.eINSTANCE.createLogMessage());
		arguments.put(Project.class, ModelFactory.eINSTANCE.createProject());
		arguments.put(ChangePackage.class, VersioningFactory.eINSTANCE.createChangePackage());
		arguments.put(HistoryQuery.class, VersioningFactory.eINSTANCE.createPathQuery());
		arguments.put(ChangePackage.class, VersioningFactory.eINSTANCE.createChangePackage());
		arguments.put(ACOrgUnitId.class, AccesscontrolFactory.eINSTANCE.createACOrgUnitId());
	}

	/**
	 * Get a default Parameter.
	 * 
	 * @param clazz parameter type
	 * @param b if false, null is returned
	 * @return parameter
	 */
	protected static Object getParameter(Class<?> clazz, boolean b) {
		if (clazz.equals(boolean.class)) {
			return false;
		}
		return (b) ? arguments.get(clazz) : null;
	}
}