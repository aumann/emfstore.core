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
package org.eclipse.emf.emfstore.server.model.versioning;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.model.Project;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Version</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getProjectState
 * <em>Project State</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getPrimarySpec
 * <em>Primary Spec</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getTagSpecs
 * <em>Tag Specs</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getNextVersion
 * <em>Next Version</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getPreviousVersion
 * <em>Previous Version</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getChanges
 * <em>Changes</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getLogMessage
 * <em>Log Message</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getAncestorVersion
 * <em>Ancestor Version</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getBranchedVersions
 * <em>Branched Versions</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getMergedToVersion
 * <em>Merged To Version</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getMergedFromVersion
 * <em>Merged From Version</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getVersion()
 * @model
 * @generated
 */
public interface Version extends EObject {
	/**
	 * Returns the value of the '<em><b>Project State</b></em>' containment
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Project State</em>' containment reference.
	 * @see #setProjectState(Project)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getVersion_ProjectState()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	Project getProjectState();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getProjectState
	 * <em>Project State</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Project State</em>' containment
	 *            reference.
	 * @see #getProjectState()
	 * @generated
	 */
	void setProjectState(Project value);

	/**
	 * Returns the value of the '<em><b>Primary Spec</b></em>' containment
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Spec</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Primary Spec</em>' containment reference.
	 * @see #setPrimarySpec(PrimaryVersionSpec)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getVersion_PrimarySpec()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	PrimaryVersionSpec getPrimarySpec();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getPrimarySpec
	 * <em>Primary Spec</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Primary Spec</em>' containment
	 *            reference.
	 * @see #getPrimarySpec()
	 * @generated
	 */
	void setPrimarySpec(PrimaryVersionSpec value);

	/**
	 * Returns the value of the '<em><b>Tag Specs</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tag Specs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Tag Specs</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getVersion_TagSpecs()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<TagVersionSpec> getTagSpecs();

	/**
	 * Returns the value of the '<em><b>Next Version</b></em>' reference. It is
	 * bidirectional and its opposite is '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getPreviousVersion
	 * <em>Previous Version</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Next Version</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Next Version</em>' reference.
	 * @see #setNextVersion(Version)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getVersion_NextVersion()
	 * @see org.eclipse.emf.emfstore.server.model.versioning.Version#getPreviousVersion
	 * @model opposite="previousVersion"
	 * @generated
	 */
	Version getNextVersion();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getNextVersion
	 * <em>Next Version</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Next Version</em>' reference.
	 * @see #getNextVersion()
	 * @generated
	 */
	void setNextVersion(Version value);

	/**
	 * Returns the value of the '<em><b>Previous Version</b></em>' reference. It
	 * is bidirectional and its opposite is '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getNextVersion
	 * <em>Next Version</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Previous Version</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Previous Version</em>' reference.
	 * @see #setPreviousVersion(Version)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getVersion_PreviousVersion()
	 * @see org.eclipse.emf.emfstore.server.model.versioning.Version#getNextVersion
	 * @model opposite="nextVersion"
	 * @generated
	 */
	Version getPreviousVersion();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getPreviousVersion
	 * <em>Previous Version</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Previous Version</em>' reference.
	 * @see #getPreviousVersion()
	 * @generated
	 */
	void setPreviousVersion(Version value);

	/**
	 * Returns the value of the '<em><b>Changes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Changes</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Changes</em>' containment reference.
	 * @see #setChanges(ChangePackage)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getVersion_Changes()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	ChangePackage getChanges();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getChanges
	 * <em>Changes</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Changes</em>' containment reference.
	 * @see #getChanges()
	 * @generated
	 */
	void setChanges(ChangePackage value);

	/**
	 * Returns the value of the '<em><b>Log Message</b></em>' containment
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Log Message</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Log Message</em>' containment reference.
	 * @see #setLogMessage(LogMessage)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getVersion_LogMessage()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	LogMessage getLogMessage();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getLogMessage
	 * <em>Log Message</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Log Message</em>' containment
	 *            reference.
	 * @see #getLogMessage()
	 * @generated
	 */
	void setLogMessage(LogMessage value);

	/**
	 * Returns the value of the '<em><b>Ancestor Version</b></em>' reference. It
	 * is bidirectional and its opposite is '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getBranchedVersions
	 * <em>Branched Versions</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ancestor Version</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ancestor Version</em>' reference.
	 * @see #setAncestorVersion(Version)
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getVersion_AncestorVersion()
	 * @see org.eclipse.emf.emfstore.server.model.versioning.Version#getBranchedVersions
	 * @model opposite="branchedVersions"
	 * @generated
	 */
	Version getAncestorVersion();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getAncestorVersion
	 * <em>Ancestor Version</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Ancestor Version</em>' reference.
	 * @see #getAncestorVersion()
	 * @generated
	 */
	void setAncestorVersion(Version value);

	/**
	 * Returns the value of the '<em><b>Branched Versions</b></em>' reference
	 * list. The list contents are of type
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version}. It is
	 * bidirectional and its opposite is '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getAncestorVersion
	 * <em>Ancestor Version</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branched Versions</em>' reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Branched Versions</em>' reference list.
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getVersion_BranchedVersions()
	 * @see org.eclipse.emf.emfstore.server.model.versioning.Version#getAncestorVersion
	 * @model opposite="ancestorVersion"
	 * @generated
	 */
	EList<Version> getBranchedVersions();

	/**
	 * Returns the value of the '<em><b>Merged To Version</b></em>' reference
	 * list. The list contents are of type
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version}. It is
	 * bidirectional and its opposite is '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getMergedFromVersion
	 * <em>Merged From Version</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Merged To Version</em>' reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Merged To Version</em>' reference list.
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getVersion_MergedToVersion()
	 * @see org.eclipse.emf.emfstore.server.model.versioning.Version#getMergedFromVersion
	 * @model opposite="mergedFromVersion"
	 * @generated
	 */
	EList<Version> getMergedToVersion();

	/**
	 * Returns the value of the '<em><b>Merged From Version</b></em>' reference
	 * list. The list contents are of type
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version}. It is
	 * bidirectional and its opposite is '
	 * {@link org.eclipse.emf.emfstore.server.model.versioning.Version#getMergedToVersion
	 * <em>Merged To Version</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Merged From Version</em>' reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Merged From Version</em>' reference list.
	 * @see org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage#getVersion_MergedFromVersion()
	 * @see org.eclipse.emf.emfstore.server.model.versioning.Version#getMergedToVersion
	 * @model opposite="mergedToVersion"
	 * @generated
	 */
	EList<Version> getMergedFromVersion();

} // Version
