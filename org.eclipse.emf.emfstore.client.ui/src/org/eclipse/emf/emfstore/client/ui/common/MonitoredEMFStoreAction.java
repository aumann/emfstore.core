/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.common;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

/**
 * A monitored action will be executed using the {@link IProgressService} of
 * Eclipse. Clients may use the passed {@link IProgressMonitor} to update the status of their
 * progress.
 * 
 * @author emueller
 * 
 * @param <T> the return type of the action
 */
public abstract class MonitoredEMFStoreAction<T> {

	private final boolean cancelable;
	private T returnValue;
	private final boolean fork;

	/**
	 * Constructor.
	 * 
	 * @param fork
	 *            whether the {@link IProgressService} should fork the request
	 * @param cancelable
	 *            whether the request is cancelable
	 */
	public MonitoredEMFStoreAction(boolean fork, boolean cancelable) {
		this.fork = fork;
		this.cancelable = cancelable;
	}

	/**
	 * Executes the request using the {@link IProgressService} of Eclipse.
	 * 
	 * @return the return value as determined by {@link #doRun(IProgressMonitor)}
	 * 
	 */
	public final T execute() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IProgressService progressService = workbench.getProgressService();

		try {
			progressService.run(fork, cancelable, new IRunnableWithProgress() {
				public void run(final IProgressMonitor monitor) {
					returnValue = doRun(monitor);
				}
			});
		} catch (InvocationTargetException e) {
			WorkspaceUtil.logException("Error during execution of an EMFStore UI controller: " + e.getMessage(), e);
		} catch (InterruptedException e) {
			WorkspaceUtil.logException("Error during execution of an EMFStore UI controller: " + e.getMessage(), e);
		}

		return returnValue;
	}

	/**
	 * The actual behavior that should be performed when the {@link #execute()} is called.<br/>
	 * Must be implemented by clients.
	 * 
	 * @param monitor
	 *            the {@link IProgressMonitor} that should be used by clients to update the status of their progress
	 * @return an optional return value
	 */
	public abstract T doRun(IProgressMonitor monitor);
}
