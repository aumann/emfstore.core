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
package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.SessionId;

/**
 * This class is intended to wrap all server calls. It may be used either by sub-classing or using anonymous classes.<br/>
 * The {@link SessionManager} ensures there's a valid session before executing the call.<br/>
 * For call of the {@link ConnectionManager}, always use {@link #getSessionId()}, since it is injected by the
 * {@link SessionManager}.<br/>
 * If initialized with an user session, it will be used for the server class when the SessionProvider isn't extended by
 * the user to change this behavior.<br/>
 * Using serverInfo as an input will call the login dialog in the default implementation.
 * Further, in the default implementation, when the {@link org.eclipse.emf.emfstore.client.model.ProjectSpace} is set,
 * it is checked whether it has an user session attached to it.
 * 
 * @author wesendon
 * @author emueller
 * 
 * @param <U> the return type of the wrapped action
 */
public abstract class ServerCall<U> {

	private ProjectSpaceBase projectSpace;
	private Usersession usersession;
	private SessionId sessionId;
	private IProgressMonitor monitor;
	private U ret;
	private ServerInfo serverInfo;

	/**
	 * Default constructor.
	 */
	public ServerCall() {
	}

	/**
	 * Default constructor with user session.
	 * 
	 * @param usersession preselected user session
	 */
	public ServerCall(Usersession usersession) {
		this.usersession = usersession;
		setProgressMonitor(null);
	}

	/**
	 * Default constructor with project space.
	 * 
	 * @param projectSpace
	 *            relevant project space if existent
	 */
	public ServerCall(ProjectSpaceBase projectSpace) {
		this.projectSpace = projectSpace;
		setProgressMonitor(null);
	}

	/**
	 * Default constructor with serverinfo.
	 * 
	 * @param serverInfo a given server
	 */
	public ServerCall(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
		setProgressMonitor(null);
	}

	/**
	 * Default constructor with user session and progress monitor.
	 * 
	 * @param usersession
	 *            preselected user session
	 * @param monitor
	 *            monitor a progress monitor instance that is used during execution of the server call
	 */
	public ServerCall(Usersession usersession, IProgressMonitor monitor) {
		this.usersession = usersession;
		setProgressMonitor(monitor);
	}

	/**
	 * Default constructor with project space and progress monitor.
	 * 
	 * @param projectSpace
	 *            relevant project space if existent
	 * @param monitor
	 *            monitor a progress monitor instance that is used during execution of the server call
	 */
	public ServerCall(ProjectSpaceImpl projectSpace, IProgressMonitor monitor) {
		this.projectSpace = projectSpace;
		setProgressMonitor(monitor);
	}

	/**
	 * Default constructor with server info and progress monitor.
	 * 
	 * @param serverInfo
	 *            a given server info
	 * @param monitor
	 *            monitor a progress monitor instance that is used during execution of the server call
	 */
	public ServerCall(ServerInfo serverInfo, IProgressMonitor monitor) {
		this.serverInfo = serverInfo;
		setProgressMonitor(monitor);
	}

	/**
	 * Returns the server info that is used by this server call, if set.
	 * 
	 * @return the server info that is used by this server call, if set
	 */
	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	/**
	 * Sets the server info that is used by this server call.
	 * 
	 * @param serverInfo
	 *            the server info that should be used by this server call
	 */
	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	/**
	 * Sets the user session to be used by this server call.
	 * 
	 * @param usersession
	 *            the user session to be used by the server call
	 */
	public void setUsersession(Usersession usersession) {
		this.usersession = usersession;
	}

	/**
	 * Returns the user session that is used by this server call.
	 * 
	 * @return the user session in use
	 */
	public Usersession getUsersession() {
		return usersession;
	}

	/**
	 * Returns the project space that will be checked for a valid user session when
	 * executing this server call.
	 * 
	 * @return the project space that will be checked for a valid user session
	 */
	protected ProjectSpaceBase getProjectSpace() {
		return projectSpace;
	}

	/**
	 * Sets the progress monitor instance that may be used during
	 * execution of this server call.
	 * 
	 * @param monitor
	 *            a progress monitor instance that may used during execution
	 *            of this server call
	 */
	public void setProgressMonitor(IProgressMonitor monitor) {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		this.monitor = monitor;
	}

	/**
	 * Returns the progress monitor instance that may be used during
	 * execution of this server call.
	 * 
	 * @return the progress monitor instance that may be used during execution of this server call
	 */
	public IProgressMonitor getProgressMonitor() {
		return this.monitor;
	}

	/**
	 * Returns the connection manager used by this server call.
	 * 
	 * @return the connection manager in use
	 */
	protected ConnectionManager getConnectionManager() {
		return WorkspaceManager.getInstance().getConnectionManager();
	}

	/**
	 * Returns the session ID to be used by this server call.
	 * 
	 * @return the session ID to be used by the server call for authetication against the server
	 */
	protected SessionId getSessionId() {
		return sessionId;
	}

	/**
	 * Sets the session ID to be used by this server call.
	 * 
	 * @param sessionId
	 *            the session ID to be used for authentication against the server
	 */
	public void setSessionId(SessionId sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Runs this call with the given session ID.
	 * 
	 * @param sessionId
	 *            the session ID to be used for authentication against the server
	 * @throws EmfStoreException
	 *             in case any exception occurs during execution of the call
	 */
	public void run(SessionId sessionId) throws EmfStoreException {
		setSessionId(sessionId);
		ret = run();
	}

	/**
	 * Performs the actual behavior of the call. Meant to be implemented by clients.
	 * 
	 * @return a return value of type <code>U</code>
	 * @throws EmfStoreException
	 *             in case any exception occurs during execution of the call
	 */
	protected abstract U run() throws EmfStoreException;

	/**
	 * Executes the server call.
	 * 
	 * @return a return value of type <code>U</code>
	 * @throws EmfStoreException
	 *             in case any exception occurs during execution of the call
	 */
	public U execute() throws EmfStoreException {
		WorkspaceManager.getInstance().getSessionManager().execute(this);
		return ret;
	}
}
