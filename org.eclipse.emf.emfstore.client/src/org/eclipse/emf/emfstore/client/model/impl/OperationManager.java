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
package org.eclipse.emf.emfstore.client.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.client.model.CompositeOperationHandle;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.observers.OperationObserver;
import org.eclipse.emf.emfstore.common.IDisposable;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.semantic.SemanticCompositeOperation;

public class OperationManager implements OperationRecorderListener, IDisposable {

	private OperationRecorder operationRecorder;
	private List<OperationObserver> operationListeners;

	private ProjectSpace projectSpace;

	public OperationManager(OperationRecorder operationRecorder, ProjectSpace projectSpace) {
		this.operationRecorder = operationRecorder;
		this.projectSpace = projectSpace;
		operationListeners = new ArrayList<OperationObserver>();
	}

	/**
	 * Undo the last operation of the projectSpace.
	 */
	public void undoLastOperation() {
		if (!projectSpace.getOperations().isEmpty()) {
			List<AbstractOperation> operations = projectSpace.getOperations();
			AbstractOperation lastOperation = operations.get(operations.size() - 1);
			operationRecorder.stopChangeRecording();
			try {
				lastOperation.reverse().apply(operationRecorder.getCollection());
				notifyOperationUndone(lastOperation);
			} finally {
				operationRecorder.startChangeRecording();
			}
			operations.remove(lastOperation);
		}
	}

	/**
	 * 
	 * @param operationListener
	 */
	public void addOperationListener(OperationObserver operationListener) {
		operationListeners.add(operationListener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param operationListner
	 */
	public void removeOperationListener(OperationObserver operationListner) {
		operationListeners.remove(operationListner);

	}

	// TODO: EM, changed to public
	public void notifyOperationUndone(AbstractOperation operation) {
		for (OperationObserver operationListener : operationListeners) {
			operationListener.operationUnDone(operation);
		}
	}

	/**
	 * Notify the operation observer that an operation has just completed.
	 * 
	 * @param operation
	 *            the operation
	 */
	void notifyOperationExecuted(AbstractOperation operation) {

		// do not notify on composite start, wait until completion
		if (operation instanceof CompositeOperation) {
			// check of automatic composite, if yes then continue
			if (((CompositeOperation) operation).getMainOperation() == null) {
				return;
			}
		}

		for (OperationObserver operationListener : operationListeners) {
			operationListener.operationExecuted(operation);
		}
	}

	// public CompositeOperationHandle beginCompositeOperation() {
	// // notificationRecorder.newRecording();
	// if (this.compositeOperation != null) {
	// throw new IllegalStateException(
	// "Can only have one composite at once!");
	// }
	// this.compositeOperation = OperationsFactory.eINSTANCE
	// .createCompositeOperation();
	// operationRecorder.addOperation(this.compositeOperation);
	// CompositeOperationHandle handle = new CompositeOperationHandle(this,
	// compositeOperation);
	// return handle;
	// }

	/**
	 * Aborts the current composite operation.
	 */
	public void abortCompositeOperation() {
		undoLastOperation();
		operationRecorder.abortCompositeOperation();
	}

	/**
	 * Complete the current composite operation.
	 */
	public void endCompositeOperation() {
		notifyOperationExecuted(operationRecorder.getCompositeOperation());
		operationRecorder.endCompositeOperation();
	}

	/**
	 * Replace and complete the current composite operation.
	 * 
	 * @param semanticCompositeOperation
	 *            the semantic operation that replaces the composite operation
	 */
	public void endCompositeOperation(SemanticCompositeOperation semanticCompositeOperation) {
		List<AbstractOperation> operations = projectSpace.getOperations();
		operations.remove(operations.size() - 1);
		operations.add(semanticCompositeOperation);
		endCompositeOperation();
	}

	public CompositeOperationHandle beginCompositeOperation() {
		return operationRecorder.beginCompositeOperation();
	}

	public void operationsRecorded(List<? extends AbstractOperation> operations) {
		projectSpace.addOperations(operations);
	}

	public void clearOperations() {
		operationRecorder.clearOperations();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.IDisposable#dispose()
	 */
	public void dispose() {
		operationRecorder.removeOperationRecorderListener(this);
	}
}
