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
package org.eclipse.emf.emfstore.server.model.accesscontrol;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.server.model.ProjectId;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Org Unit Properties</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.accesscontrol.OrgUnitProperty#getName
 * <em>Name</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.accesscontrol.OrgUnitProperty#getValue
 * <em>Value</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.accesscontrol.OrgUnitProperty#getProject
 * <em>Project</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.server.model.accesscontrol.AccesscontrolPackage#getOrgUnitProperty()
 * @model
 * @generated
 */
public interface OrgUnitProperty extends EObject {

	public final static String ARRAY_SEPARATOR = "%%";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.emfstore.server.model.accesscontrol.AccesscontrolPackage#getOrgUnitProperty_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.accesscontrol.OrgUnitProperty#getName
	 * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.emf.emfstore.server.model.accesscontrol.AccesscontrolPackage#getOrgUnitProperty_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.accesscontrol.OrgUnitProperty#getValue
	 * <em>Value</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Project</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Project</em>' containment reference.
	 * @see #setProject(ProjectId)
	 * @see org.eclipse.emf.emfstore.server.model.accesscontrol.AccesscontrolPackage#getOrgUnitProperty_Project()
	 * @model containment="true" resolveProxies="true" keys="id"
	 * @generated
	 */
	ProjectId getProject();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.accesscontrol.OrgUnitProperty#getProject
	 * <em>Project</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Project</em>' containment reference.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(ProjectId value);

	/**
	 * Sets a new boolean property.
	 * 
	 * @param name
	 *            the name of the property
	 * @param value
	 *            the new value
	 * @generated NOT
	 */
	void setValue(boolean value);

	/**
	 * Sets a new int property.
	 * 
	 * @param value
	 *            the new value
	 * @generated NOT
	 */
	void setValue(int value);

	/**
	 * Sets a new String[] property.
	 * 
	 * @param value
	 *            the new value
	 * @generated NOT
	 */
	void setValue(String[] value);

	/**
	 * Sets a new EObject[] property.
	 * 
	 * @param value
	 *            the new EObject[] value
	 * @generated NOT
	 */
	void setValue(EObject[] value);

	/**
	 * @return the boolean value of the property or null if it doesn't exist
	 */
	Boolean getBooleanProperty();

	/**
	 * @return the int value of the property or null if it doesn't exist
	 */
	Integer getIntegerProperty();

	/**
	 * @return the array value of the property or null if it doesn't exist
	 */
	String[] getStringArrayProperty();

} // OrgUnitProperties
