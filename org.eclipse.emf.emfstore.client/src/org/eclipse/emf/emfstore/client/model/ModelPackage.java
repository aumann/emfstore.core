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
package org.eclipse.emf.emfstore.client.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.emfstore.client.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://eclipse.org/emf/emfstore/client/model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emfstore.client.model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	ModelPackage eINSTANCE = org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.client.model.impl.WorkspaceImpl <em>Workspace</em>}'
	 * class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.impl.WorkspaceImpl
	 * @see org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl#getWorkspace()
	 * @generated
	 */
	int WORKSPACE = 0;

	/**
	 * The feature id for the '<em><b>Project Spaces</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WORKSPACE__PROJECT_SPACES = 0;

	/**
	 * The feature id for the '<em><b>Server Infos</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WORKSPACE__SERVER_INFOS = 1;

	/**
	 * The feature id for the '<em><b>Usersessions</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WORKSPACE__USERSESSIONS = 2;

	/**
	 * The number of structural features of the '<em>Workspace</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WORKSPACE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.client.model.impl.ServerInfoImpl
	 * <em>Server Info</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.impl.ServerInfoImpl
	 * @see org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl#getServerInfo()
	 * @generated
	 */
	int SERVER_INFO = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVER_INFO__NAME = 0;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVER_INFO__URL = 1;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVER_INFO__PORT = 2;

	/**
	 * The feature id for the '<em><b>Project Infos</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVER_INFO__PROJECT_INFOS = 3;

	/**
	 * The feature id for the '<em><b>Last Usersession</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVER_INFO__LAST_USERSESSION = 4;

	/**
	 * The feature id for the '<em><b>Certificate Alias</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVER_INFO__CERTIFICATE_ALIAS = 5;

	/**
	 * The number of structural features of the '<em>Server Info</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVER_INFO_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.client.model.impl.UsersessionImpl
	 * <em>Usersession</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.impl.UsersessionImpl
	 * @see org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl#getUsersession()
	 * @generated
	 */
	int USERSESSION = 2;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USERSESSION__USERNAME = 0;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USERSESSION__PASSWORD = 1;

	/**
	 * The feature id for the '<em><b>Session Id</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USERSESSION__SESSION_ID = 2;

	/**
	 * The feature id for the '<em><b>Persistent Password</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USERSESSION__PERSISTENT_PASSWORD = 3;

	/**
	 * The feature id for the '<em><b>Server Info</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USERSESSION__SERVER_INFO = 4;

	/**
	 * The feature id for the '<em><b>Save Password</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USERSESSION__SAVE_PASSWORD = 5;

	/**
	 * The feature id for the '<em><b>AC User</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USERSESSION__AC_USER = 6;

	/**
	 * The feature id for the '<em><b>Changed Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USERSESSION__CHANGED_PROPERTIES = 7;

	/**
	 * The number of structural features of the '<em>Usersession</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USERSESSION_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl
	 * <em>Project Space</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl
	 * @see org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl#getProjectSpace()
	 * @generated
	 */
	int PROJECT_SPACE = 3;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__IDENTIFIER = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Project</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__PROJECT = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Project Id</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__PROJECT_ID = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__PROJECT_NAME = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Project Description</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__PROJECT_DESCRIPTION = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Usersession</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__USERSESSION = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Last Updated</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__LAST_UPDATED = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Base Version</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__BASE_VERSION = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Resource Count</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__RESOURCE_COUNT = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Dirty</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__DIRTY = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Old Log Messages</b></em>' attribute list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__OLD_LOG_MESSAGES = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Local Operations</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__LOCAL_OPERATIONS = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Waiting Uploads</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__WAITING_UPLOADS = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__PROPERTIES = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Changed Shared Properties</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__CHANGED_SHARED_PROPERTIES = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Workspace</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__WORKSPACE = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Local Change Package</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__LOCAL_CHANGE_PACKAGE = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Merged Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE__MERGED_VERSION = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 16;

	/**
	 * The number of structural features of the '<em>Project Space</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_SPACE_FEATURE_COUNT = org.eclipse.emf.emfstore.common.model.ModelPackage.IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 17;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.client.model.impl.OperationCompositeImpl
	 * <em>Operation Composite</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.impl.OperationCompositeImpl
	 * @see org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl#getOperationComposite()
	 * @generated
	 */
	int OPERATION_COMPOSITE = 4;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OPERATION_COMPOSITE__OPERATIONS = 0;

	/**
	 * The number of structural features of the '<em>Operation Composite</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OPERATION_COMPOSITE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.client.model.impl.PendingFileTransferImpl
	 * <em>Pending File Transfer</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.impl.PendingFileTransferImpl
	 * @see org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl#getPendingFileTransfer()
	 * @generated
	 */
	int PENDING_FILE_TRANSFER = 5;

	/**
	 * The feature id for the '<em><b>Attachment Id</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PENDING_FILE_TRANSFER__ATTACHMENT_ID = 0;

	/**
	 * The feature id for the '<em><b>File Version</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PENDING_FILE_TRANSFER__FILE_VERSION = 1;

	/**
	 * The feature id for the '<em><b>Chunk Number</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PENDING_FILE_TRANSFER__CHUNK_NUMBER = 2;

	/**
	 * The feature id for the '<em><b>Upload</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PENDING_FILE_TRANSFER__UPLOAD = 3;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PENDING_FILE_TRANSFER__FILE_NAME = 4;

	/**
	 * The feature id for the '<em><b>Preliminary File Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PENDING_FILE_TRANSFER__PRELIMINARY_FILE_NAME = 5;

	/**
	 * The number of structural features of the '<em>Pending File Transfer</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PENDING_FILE_TRANSFER_FEATURE_COUNT = 6;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.client.model.Workspace <em>Workspace</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Workspace</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Workspace
	 * @generated
	 */
	EClass getWorkspace();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.client.model.Workspace#getProjectSpaces <em>Project Spaces</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Project Spaces</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#getProjectSpaces()
	 * @see #getWorkspace()
	 * @generated
	 */
	EReference getWorkspace_ProjectSpaces();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.client.model.Workspace#getServerInfos <em>Server Infos</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Server Infos</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#getServerInfos()
	 * @see #getWorkspace()
	 * @generated
	 */
	EReference getWorkspace_ServerInfos();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.client.model.Workspace#getUsersessions <em>Usersessions</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Usersessions</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#getUsersessions()
	 * @see #getWorkspace()
	 * @generated
	 */
	EReference getWorkspace_Usersessions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.client.model.ServerInfo <em>Server Info</em>}
	 * '.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Server Info</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ServerInfo
	 * @generated
	 */
	EClass getServerInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.client.model.ServerInfo#getName
	 * <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ServerInfo#getName()
	 * @see #getServerInfo()
	 * @generated
	 */
	EAttribute getServerInfo_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.client.model.ServerInfo#getUrl
	 * <em>Url</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ServerInfo#getUrl()
	 * @see #getServerInfo()
	 * @generated
	 */
	EAttribute getServerInfo_Url();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.client.model.ServerInfo#getPort
	 * <em>Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Port</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ServerInfo#getPort()
	 * @see #getServerInfo()
	 * @generated
	 */
	EAttribute getServerInfo_Port();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.client.model.ServerInfo#getProjectInfos <em>Project Infos</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Project Infos</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ServerInfo#getProjectInfos()
	 * @see #getServerInfo()
	 * @generated
	 */
	EReference getServerInfo_ProjectInfos();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emf.emfstore.client.model.ServerInfo#getLastUsersession <em>Last Usersession</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Last Usersession</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ServerInfo#getLastUsersession()
	 * @see #getServerInfo()
	 * @generated
	 */
	EReference getServerInfo_LastUsersession();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.model.ServerInfo#getCertificateAlias <em>Certificate Alias</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Certificate Alias</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ServerInfo#getCertificateAlias()
	 * @see #getServerInfo()
	 * @generated
	 */
	EAttribute getServerInfo_CertificateAlias();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.client.model.Usersession <em>Usersession</em>}
	 * '.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Usersession</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Usersession
	 * @generated
	 */
	EClass getUsersession();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.client.model.Usersession#getUsername
	 * <em>Username</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Username</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Usersession#getUsername()
	 * @see #getUsersession()
	 * @generated
	 */
	EAttribute getUsersession_Username();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.client.model.Usersession#getPassword
	 * <em>Password</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Usersession#getPassword()
	 * @see #getUsersession()
	 * @generated
	 */
	EAttribute getUsersession_Password();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.emfstore.client.model.Usersession#getSessionId
	 * <em>Session Id</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Session Id</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Usersession#getSessionId()
	 * @see #getUsersession()
	 * @generated
	 */
	EReference getUsersession_SessionId();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.model.Usersession#getPersistentPassword <em>Persistent Password</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Persistent Password</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Usersession#getPersistentPassword()
	 * @see #getUsersession()
	 * @generated
	 */
	EAttribute getUsersession_PersistentPassword();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emf.emfstore.client.model.Usersession#getServerInfo <em>Server Info</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Server Info</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Usersession#getServerInfo()
	 * @see #getUsersession()
	 * @generated
	 */
	EReference getUsersession_ServerInfo();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.model.Usersession#isSavePassword <em>Save Password</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Save Password</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Usersession#isSavePassword()
	 * @see #getUsersession()
	 * @generated
	 */
	EAttribute getUsersession_SavePassword();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.client.model.Usersession#getACUser <em>AC User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>AC User</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Usersession#getACUser()
	 * @see #getUsersession()
	 * @generated
	 */
	EReference getUsersession_ACUser();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.client.model.Usersession#getChangedProperties <em>Changed Properties</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Changed Properties</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.Usersession#getChangedProperties()
	 * @see #getUsersession()
	 * @generated
	 */
	EReference getUsersession_ChangedProperties();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.client.model.ProjectSpace
	 * <em>Project Space</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Project Space</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace
	 * @generated
	 */
	EClass getProjectSpace();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Project</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getProject()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EReference getProjectSpace_Project();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getProjectId <em>Project Id</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Project Id</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getProjectId()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EReference getProjectSpace_ProjectId();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getProjectName <em>Project Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Project Name</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getProjectName()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EAttribute getProjectSpace_ProjectName();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getProjectDescription <em>Project Description</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Project Description</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getProjectDescription()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EAttribute getProjectSpace_ProjectDescription();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getUsersession <em>Usersession</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Usersession</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getUsersession()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EReference getProjectSpace_Usersession();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getLastUpdated <em>Last Updated</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Last Updated</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getLastUpdated()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EAttribute getProjectSpace_LastUpdated();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getBaseVersion <em>Base Version</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Base Version</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getBaseVersion()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EReference getProjectSpace_BaseVersion();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getResourceCount <em>Resource Count</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Resource Count</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getResourceCount()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EAttribute getProjectSpace_ResourceCount();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.client.model.ProjectSpace#isDirty
	 * <em>Dirty</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Dirty</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#isDirty()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EAttribute getProjectSpace_Dirty();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getOldLogMessages <em>Old Log Messages</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Old Log Messages</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getOldLogMessages()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EAttribute getProjectSpace_OldLogMessages();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getLocalOperations <em>Local Operations</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Local Operations</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getLocalOperations()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EReference getProjectSpace_LocalOperations();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getWaitingUploads <em>Waiting Uploads</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Waiting Uploads</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getWaitingUploads()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EReference getProjectSpace_WaitingUploads();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getProperties()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EReference getProjectSpace_Properties();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getChangedSharedProperties
	 * <em>Changed Shared Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Changed Shared Properties</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getChangedSharedProperties()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EReference getProjectSpace_ChangedSharedProperties();

	/**
	 * Returns the meta object for the container reference '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getWorkspace <em>Workspace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Workspace</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getWorkspace()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EReference getProjectSpace_Workspace();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getLocalChangePackage <em>Local Change Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Local Change Package</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getLocalChangePackage()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EReference getProjectSpace_LocalChangePackage();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getMergedVersion <em>Merged Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Merged Version</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getMergedVersion()
	 * @see #getProjectSpace()
	 * @generated
	 */
	EReference getProjectSpace_MergedVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.client.model.OperationComposite
	 * <em>Operation Composite</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Operation Composite</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.OperationComposite
	 * @generated
	 */
	EClass getOperationComposite();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.client.model.OperationComposite#getOperations <em>Operations</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Operations</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.OperationComposite#getOperations()
	 * @see #getOperationComposite()
	 * @generated
	 */
	EReference getOperationComposite_Operations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.client.model.PendingFileTransfer
	 * <em>Pending File Transfer</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Pending File Transfer</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.PendingFileTransfer
	 * @generated
	 */
	EClass getPendingFileTransfer();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.client.model.PendingFileTransfer#getAttachmentId <em>Attachment Id</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Attachment Id</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.PendingFileTransfer#getAttachmentId()
	 * @see #getPendingFileTransfer()
	 * @generated
	 */
	EReference getPendingFileTransfer_AttachmentId();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.model.PendingFileTransfer#getFileVersion <em>File Version</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>File Version</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.PendingFileTransfer#getFileVersion()
	 * @see #getPendingFileTransfer()
	 * @generated
	 */
	EAttribute getPendingFileTransfer_FileVersion();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.model.PendingFileTransfer#getChunkNumber <em>Chunk Number</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Chunk Number</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.PendingFileTransfer#getChunkNumber()
	 * @see #getPendingFileTransfer()
	 * @generated
	 */
	EAttribute getPendingFileTransfer_ChunkNumber();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.model.PendingFileTransfer#isUpload <em>Upload</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Upload</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.PendingFileTransfer#isUpload()
	 * @see #getPendingFileTransfer()
	 * @generated
	 */
	EAttribute getPendingFileTransfer_Upload();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.model.PendingFileTransfer#getFileName <em>File Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>File Name</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.PendingFileTransfer#getFileName()
	 * @see #getPendingFileTransfer()
	 * @generated
	 */
	EAttribute getPendingFileTransfer_FileName();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.client.model.PendingFileTransfer#getPreliminaryFileName
	 * <em>Preliminary File Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Preliminary File Name</em>'.
	 * @see org.eclipse.emf.emfstore.client.model.PendingFileTransfer#getPreliminaryFileName()
	 * @see #getPendingFileTransfer()
	 * @generated
	 */
	EAttribute getPendingFileTransfer_PreliminaryFileName();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.client.model.impl.WorkspaceImpl
		 * <em>Workspace</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.client.model.impl.WorkspaceImpl
		 * @see org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl#getWorkspace()
		 * @generated
		 */
		EClass WORKSPACE = eINSTANCE.getWorkspace();

		/**
		 * The meta object literal for the '<em><b>Project Spaces</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference WORKSPACE__PROJECT_SPACES = eINSTANCE.getWorkspace_ProjectSpaces();

		/**
		 * The meta object literal for the '<em><b>Server Infos</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference WORKSPACE__SERVER_INFOS = eINSTANCE.getWorkspace_ServerInfos();

		/**
		 * The meta object literal for the '<em><b>Usersessions</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference WORKSPACE__USERSESSIONS = eINSTANCE.getWorkspace_Usersessions();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.client.model.impl.ServerInfoImpl
		 * <em>Server Info</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.client.model.impl.ServerInfoImpl
		 * @see org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl#getServerInfo()
		 * @generated
		 */
		EClass SERVER_INFO = eINSTANCE.getServerInfo();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SERVER_INFO__NAME = eINSTANCE.getServerInfo_Name();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SERVER_INFO__URL = eINSTANCE.getServerInfo_Url();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SERVER_INFO__PORT = eINSTANCE.getServerInfo_Port();

		/**
		 * The meta object literal for the '<em><b>Project Infos</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SERVER_INFO__PROJECT_INFOS = eINSTANCE.getServerInfo_ProjectInfos();

		/**
		 * The meta object literal for the '<em><b>Last Usersession</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SERVER_INFO__LAST_USERSESSION = eINSTANCE.getServerInfo_LastUsersession();

		/**
		 * The meta object literal for the '<em><b>Certificate Alias</b></em>' attribute feature.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SERVER_INFO__CERTIFICATE_ALIAS = eINSTANCE.getServerInfo_CertificateAlias();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.client.model.impl.UsersessionImpl
		 * <em>Usersession</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.client.model.impl.UsersessionImpl
		 * @see org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl#getUsersession()
		 * @generated
		 */
		EClass USERSESSION = eINSTANCE.getUsersession();

		/**
		 * The meta object literal for the '<em><b>Username</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute USERSESSION__USERNAME = eINSTANCE.getUsersession_Username();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute USERSESSION__PASSWORD = eINSTANCE.getUsersession_Password();

		/**
		 * The meta object literal for the '<em><b>Session Id</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference USERSESSION__SESSION_ID = eINSTANCE.getUsersession_SessionId();

		/**
		 * The meta object literal for the '<em><b>Persistent Password</b></em>' attribute feature.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute USERSESSION__PERSISTENT_PASSWORD = eINSTANCE.getUsersession_PersistentPassword();

		/**
		 * The meta object literal for the '<em><b>Server Info</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference USERSESSION__SERVER_INFO = eINSTANCE.getUsersession_ServerInfo();

		/**
		 * The meta object literal for the '<em><b>Save Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute USERSESSION__SAVE_PASSWORD = eINSTANCE.getUsersession_SavePassword();

		/**
		 * The meta object literal for the '<em><b>AC User</b></em>' containment reference feature.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference USERSESSION__AC_USER = eINSTANCE.getUsersession_ACUser();

		/**
		 * The meta object literal for the '<em><b>Changed Properties</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference USERSESSION__CHANGED_PROPERTIES = eINSTANCE.getUsersession_ChangedProperties();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl
		 * <em>Project Space</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl
		 * @see org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl#getProjectSpace()
		 * @generated
		 */
		EClass PROJECT_SPACE = eINSTANCE.getProjectSpace();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' containment reference feature.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT_SPACE__PROJECT = eINSTANCE.getProjectSpace_Project();

		/**
		 * The meta object literal for the '<em><b>Project Id</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT_SPACE__PROJECT_ID = eINSTANCE.getProjectSpace_ProjectId();

		/**
		 * The meta object literal for the '<em><b>Project Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROJECT_SPACE__PROJECT_NAME = eINSTANCE.getProjectSpace_ProjectName();

		/**
		 * The meta object literal for the '<em><b>Project Description</b></em>' attribute feature.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROJECT_SPACE__PROJECT_DESCRIPTION = eINSTANCE.getProjectSpace_ProjectDescription();

		/**
		 * The meta object literal for the '<em><b>Usersession</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT_SPACE__USERSESSION = eINSTANCE.getProjectSpace_Usersession();

		/**
		 * The meta object literal for the '<em><b>Last Updated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROJECT_SPACE__LAST_UPDATED = eINSTANCE.getProjectSpace_LastUpdated();

		/**
		 * The meta object literal for the '<em><b>Base Version</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT_SPACE__BASE_VERSION = eINSTANCE.getProjectSpace_BaseVersion();

		/**
		 * The meta object literal for the '<em><b>Resource Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROJECT_SPACE__RESOURCE_COUNT = eINSTANCE.getProjectSpace_ResourceCount();

		/**
		 * The meta object literal for the '<em><b>Dirty</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROJECT_SPACE__DIRTY = eINSTANCE.getProjectSpace_Dirty();

		/**
		 * The meta object literal for the '<em><b>Old Log Messages</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROJECT_SPACE__OLD_LOG_MESSAGES = eINSTANCE.getProjectSpace_OldLogMessages();

		/**
		 * The meta object literal for the '<em><b>Local Operations</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT_SPACE__LOCAL_OPERATIONS = eINSTANCE.getProjectSpace_LocalOperations();

		/**
		 * The meta object literal for the '<em><b>Waiting Uploads</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT_SPACE__WAITING_UPLOADS = eINSTANCE.getProjectSpace_WaitingUploads();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT_SPACE__PROPERTIES = eINSTANCE.getProjectSpace_Properties();

		/**
		 * The meta object literal for the '<em><b>Changed Shared Properties</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT_SPACE__CHANGED_SHARED_PROPERTIES = eINSTANCE.getProjectSpace_ChangedSharedProperties();

		/**
		 * The meta object literal for the '<em><b>Workspace</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT_SPACE__WORKSPACE = eINSTANCE.getProjectSpace_Workspace();

		/**
		 * The meta object literal for the '<em><b>Local Change Package</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT_SPACE__LOCAL_CHANGE_PACKAGE = eINSTANCE.getProjectSpace_LocalChangePackage();

		/**
		 * The meta object literal for the '<em><b>Merged Version</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT_SPACE__MERGED_VERSION = eINSTANCE.getProjectSpace_MergedVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.client.model.impl.OperationCompositeImpl
		 * <em>Operation Composite</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.client.model.impl.OperationCompositeImpl
		 * @see org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl#getOperationComposite()
		 * @generated
		 */
		EClass OPERATION_COMPOSITE = eINSTANCE.getOperationComposite();

		/**
		 * The meta object literal for the '<em><b>Operations</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference OPERATION_COMPOSITE__OPERATIONS = eINSTANCE.getOperationComposite_Operations();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.client.model.impl.PendingFileTransferImpl
		 * <em>Pending File Transfer</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.client.model.impl.PendingFileTransferImpl
		 * @see org.eclipse.emf.emfstore.client.model.impl.ModelPackageImpl#getPendingFileTransfer()
		 * @generated
		 */
		EClass PENDING_FILE_TRANSFER = eINSTANCE.getPendingFileTransfer();

		/**
		 * The meta object literal for the '<em><b>Attachment Id</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PENDING_FILE_TRANSFER__ATTACHMENT_ID = eINSTANCE.getPendingFileTransfer_AttachmentId();

		/**
		 * The meta object literal for the '<em><b>File Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PENDING_FILE_TRANSFER__FILE_VERSION = eINSTANCE.getPendingFileTransfer_FileVersion();

		/**
		 * The meta object literal for the '<em><b>Chunk Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PENDING_FILE_TRANSFER__CHUNK_NUMBER = eINSTANCE.getPendingFileTransfer_ChunkNumber();

		/**
		 * The meta object literal for the '<em><b>Upload</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PENDING_FILE_TRANSFER__UPLOAD = eINSTANCE.getPendingFileTransfer_Upload();

		/**
		 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PENDING_FILE_TRANSFER__FILE_NAME = eINSTANCE.getPendingFileTransfer_FileName();

		/**
		 * The meta object literal for the '<em><b>Preliminary File Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PENDING_FILE_TRANSFER__PRELIMINARY_FILE_NAME = eINSTANCE
			.getPendingFileTransfer_PreliminaryFileName();

	}

} // ModelPackage
