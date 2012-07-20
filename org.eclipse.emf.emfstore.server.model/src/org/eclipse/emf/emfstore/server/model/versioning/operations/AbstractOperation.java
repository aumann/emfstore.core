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
package org.eclipse.emf.emfstore.server.model.versioning.operations;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.common.model.IdentifiableElement;
import org.eclipse.emf.emfstore.common.model.ModelElementId;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Abstract Operation</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation#getModelElementId
 * <em>Model Element Id</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation#isAccepted
 * <em>Accepted</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation#getClientDate
 * <em>Client Date</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.server.model.versioning.operations.OperationsPackage#getAbstractOperation()
 * @model abstract="true"
 * @generated
 */
public interface AbstractOperation extends IdentifiableElement {
	/**
	 * Returns the value of the '<em><b>Model Element Id</b></em>' containment
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Element Id</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Model Element Id</em>' containment
	 *         reference.
	 * @see #setModelElementId(ModelElementId)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.operations.OperationsPackage#getAbstractOperation_ModelElementId()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	ModelElementId getModelElementId();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation#getModelElementId
	 * <em>Model Element Id</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Model Element Id</em>' containment
	 *            reference.
	 * @see #getModelElementId()
	 * @generated
	 */
	void setModelElementId(ModelElementId value);

	/**
	 * Returns the value of the '<em><b>Accepted</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Accepted</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Accepted</em>' attribute.
	 * @see #setAccepted(boolean)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.operations.OperationsPackage#getAbstractOperation_Accepted()
	 * @model transient="true"
	 * @generated
	 */
	boolean isAccepted();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation#isAccepted
	 * <em>Accepted</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Accepted</em>' attribute.
	 * @see #isAccepted()
	 * @generated
	 */
	void setAccepted(boolean value);

	/**
	 * Returns the value of the '<em><b>Client Date</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Client Date</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Client Date</em>' attribute.
	 * @see #setClientDate(Date)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.operations.OperationsPackage#getAbstractOperation_ClientDate()
	 * @model
	 * @generated
	 */
	Date getClientDate();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation#getClientDate
	 * <em>Client Date</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Client Date</em>' attribute.
	 * @see #getClientDate()
	 * @generated
	 */
	void setClientDate(Date value);

	/**
	 * Apply an operation to the given project if it can be applied. Apply will
	 * silently fail if the operation can not be applied because the given
	 * project can not support the operation (e.g. element is missing). To make
	 * sure an operation can be applied you can use canApply().
	 * 
	 * @param project
	 *            the project
	 */
	void apply(IdEObjectCollection project);

	/**
	 * Reverse the operation. The reversed operation is build such that applying
	 * this operation and then the reversed operation to a project does not
	 * change the project in total effect.
	 * 
	 * @return the reversed operation
	 */
	AbstractOperation reverse();

	/**
	 * Get the operations id.
	 * 
	 * @return the id
	 */
	OperationId getOperationId();

	/**
	 * Get all model elements that are involved in the operation.
	 * 
	 * @return a set of model element ids
	 */
	Set<ModelElementId> getAllInvolvedModelElements();

	/**
	 * Get all model elements that are involved in the operation other than the
	 * element the operation is directly operating on.
	 * 
	 * @return a set of model element ids
	 */
	Set<ModelElementId> getOtherInvolvedModelElements();

	/**
	 * Get all operations that are a leaf operation. In case this operation is
	 * not containing any other operations it will return itself.
	 * 
	 * @return a list of leaf operations
	 */
	List<AbstractOperation> getLeafOperations();

} // AbstractOperation
