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
package org.eclipse.emf.emfstore.server.model.accesscontrol.roles;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.emfstore.server.model.accesscontrol.roles.RolesPackage
 * @generated
 */
public interface RolesFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	RolesFactory eINSTANCE = org.eclipse.emf.emfstore.server.model.accesscontrol.roles.impl.RolesFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Reader Role</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Reader Role</em>'.
	 * @generated
	 */
	ReaderRole createReaderRole();

	/**
	 * Returns a new object of class '<em>Writer Role</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Writer Role</em>'.
	 * @generated
	 */
	WriterRole createWriterRole();

	/**
	 * Returns a new object of class '<em>Project Admin Role</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Project Admin Role</em>'.
	 * @generated
	 */
	ProjectAdminRole createProjectAdminRole();

	/**
	 * Returns a new object of class '<em>Server Admin</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Server Admin</em>'.
	 * @generated
	 */
	ServerAdmin createServerAdmin();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	RolesPackage getRolesPackage();

} // RolesFactory
