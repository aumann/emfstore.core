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
package org.eclipse.emf.emfstore.server.model.versioning.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.impl.ProjectImpl;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Version;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Version</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.server.model.versioning.impl.VersionImpl#getProjectState <em>Project State</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.server.model.versioning.impl.VersionImpl#getPrimarySpec <em>Primary Spec</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.server.model.versioning.impl.VersionImpl#getTagSpecs <em>Tag Specs</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.server.model.versioning.impl.VersionImpl#getNextVersion <em>Next Version</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.server.model.versioning.impl.VersionImpl#getPreviousVersion <em>Previous Version
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.server.model.versioning.impl.VersionImpl#getChanges <em>Changes</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.server.model.versioning.impl.VersionImpl#getLogMessage <em>Log Message</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.server.model.versioning.impl.VersionImpl#getAncestorVersion <em>Ancestor Version
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.server.model.versioning.impl.VersionImpl#getBranchedVersions <em>Branched
 * Versions</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class VersionImpl extends EObjectImpl implements Version {
	/**
	 * The cached value of the '{@link #getProjectState() <em>Project State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getProjectState()
	 * @generated
	 * @ordered
	 */
	protected Project projectState;

	/**
	 * The cached value of the '{@link #getPrimarySpec() <em>Primary Spec</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPrimarySpec()
	 * @generated
	 * @ordered
	 */
	protected PrimaryVersionSpec primarySpec;

	/**
	 * The cached value of the '{@link #getTagSpecs() <em>Tag Specs</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTagSpecs()
	 * @generated
	 * @ordered
	 */
	protected EList<TagVersionSpec> tagSpecs;

	/**
	 * The cached value of the '{@link #getNextVersion() <em>Next Version</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNextVersion()
	 * @generated
	 * @ordered
	 */
	protected Version nextVersion;

	/**
	 * The cached value of the '{@link #getPreviousVersion() <em>Previous Version</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getPreviousVersion()
	 * @generated
	 * @ordered
	 */
	protected Version previousVersion;

	/**
	 * The cached value of the '{@link #getChanges() <em>Changes</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getChanges()
	 * @generated
	 * @ordered
	 */
	protected ChangePackage changes;

	/**
	 * The cached value of the '{@link #getLogMessage() <em>Log Message</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLogMessage()
	 * @generated
	 * @ordered
	 */
	protected LogMessage logMessage;

	/**
	 * The cached value of the '{@link #getAncestorVersion() <em>Ancestor Version</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getAncestorVersion()
	 * @generated
	 * @ordered
	 */
	protected Version ancestorVersion;

	/**
	 * The cached value of the '{@link #getBranchedVersions() <em>Branched Versions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getBranchedVersions()
	 * @generated
	 * @ordered
	 */
	protected EList<Version> branchedVersions;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VersionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VersioningPackage.Literals.VERSION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Project getProjectStateGen() {
		if (projectState != null && projectState.eIsProxy()) {
			InternalEObject oldProjectState = (InternalEObject) projectState;
			projectState = (Project) eResolveProxy(oldProjectState);
			if (projectState != oldProjectState) {
				InternalEObject newProjectState = (InternalEObject) projectState;
				NotificationChain msgs = oldProjectState.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.VERSION__PROJECT_STATE, null, null);
				if (newProjectState.eInternalContainer() == null) {
					msgs = newProjectState.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- VersioningPackage.VERSION__PROJECT_STATE, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VersioningPackage.VERSION__PROJECT_STATE,
						oldProjectState, projectState));
			}
		}
		return projectState;
	}

	// begin of custom code
	/**
	 * Retrieve the corresponding project state and init its ids. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the project representing this version or null if there is no full
	 *         representation at this version (will not recalculate the state
	 *         from previous versions)
	 * @generated NOT
	 */
	public Project getProjectState() {
		if ((projectState != null && projectState.eIsProxy())) {
			ProjectImpl project = (ProjectImpl) getProjectStateGen();

			Resource resource = project.eResource();
			if (resource instanceof XMIResource) {
				Set<EObject> allContainedModelElements = ModelUtil.getAllContainedModelElements(project, false);
				EMap<EObject, String> eObjectToIdMap = loadIdsFromResourceForEObjects(allContainedModelElements,
					(XMIResource) resource);

				// create reverse mapping
				Map<String, EObject> idToEObjectMap = new HashMap<String, EObject>(eObjectToIdMap.size());

				for (Map.Entry<EObject, String> entry : eObjectToIdMap.entrySet()) {
					idToEObjectMap.put(entry.getValue(), entry.getKey());
				}

				project.initCaches(eObjectToIdMap.map(), idToEObjectMap);
			}

			return project;
		}

		return getProjectStateGen();
	}

	// end of custom code

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Project basicGetProjectState() {
		return projectState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetProjectState(Project newProjectState, NotificationChain msgs) {
		Project oldProjectState = projectState;
		projectState = newProjectState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VersioningPackage.VERSION__PROJECT_STATE, oldProjectState, newProjectState);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setProjectState(Project newProjectState) {
		if (newProjectState != projectState) {
			NotificationChain msgs = null;
			if (projectState != null)
				msgs = ((InternalEObject) projectState).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.VERSION__PROJECT_STATE, null, msgs);
			if (newProjectState != null)
				msgs = ((InternalEObject) newProjectState).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.VERSION__PROJECT_STATE, null, msgs);
			msgs = basicSetProjectState(newProjectState, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VersioningPackage.VERSION__PROJECT_STATE,
				newProjectState, newProjectState));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PrimaryVersionSpec getPrimarySpec() {
		if (primarySpec != null && primarySpec.eIsProxy()) {
			InternalEObject oldPrimarySpec = (InternalEObject) primarySpec;
			primarySpec = (PrimaryVersionSpec) eResolveProxy(oldPrimarySpec);
			if (primarySpec != oldPrimarySpec) {
				InternalEObject newPrimarySpec = (InternalEObject) primarySpec;
				NotificationChain msgs = oldPrimarySpec.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.VERSION__PRIMARY_SPEC, null, null);
				if (newPrimarySpec.eInternalContainer() == null) {
					msgs = newPrimarySpec.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- VersioningPackage.VERSION__PRIMARY_SPEC, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VersioningPackage.VERSION__PRIMARY_SPEC,
						oldPrimarySpec, primarySpec));
			}
		}
		return primarySpec;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PrimaryVersionSpec basicGetPrimarySpec() {
		return primarySpec;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetPrimarySpec(PrimaryVersionSpec newPrimarySpec, NotificationChain msgs) {
		PrimaryVersionSpec oldPrimarySpec = primarySpec;
		primarySpec = newPrimarySpec;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VersioningPackage.VERSION__PRIMARY_SPEC, oldPrimarySpec, newPrimarySpec);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPrimarySpec(PrimaryVersionSpec newPrimarySpec) {
		if (newPrimarySpec != primarySpec) {
			NotificationChain msgs = null;
			if (primarySpec != null)
				msgs = ((InternalEObject) primarySpec).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.VERSION__PRIMARY_SPEC, null, msgs);
			if (newPrimarySpec != null)
				msgs = ((InternalEObject) newPrimarySpec).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.VERSION__PRIMARY_SPEC, null, msgs);
			msgs = basicSetPrimarySpec(newPrimarySpec, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VersioningPackage.VERSION__PRIMARY_SPEC,
				newPrimarySpec, newPrimarySpec));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TagVersionSpec> getTagSpecs() {
		if (tagSpecs == null) {
			tagSpecs = new EObjectContainmentEList.Resolving<TagVersionSpec>(TagVersionSpec.class, this,
				VersioningPackage.VERSION__TAG_SPECS);
		}
		return tagSpecs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Version getNextVersion() {
		if (nextVersion != null && nextVersion.eIsProxy()) {
			InternalEObject oldNextVersion = (InternalEObject) nextVersion;
			nextVersion = (Version) eResolveProxy(oldNextVersion);
			if (nextVersion != oldNextVersion) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VersioningPackage.VERSION__NEXT_VERSION,
						oldNextVersion, nextVersion));
			}
		}
		return nextVersion;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Version basicGetNextVersion() {
		return nextVersion;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetNextVersion(Version newNextVersion, NotificationChain msgs) {
		Version oldNextVersion = nextVersion;
		nextVersion = newNextVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VersioningPackage.VERSION__NEXT_VERSION, oldNextVersion, newNextVersion);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setNextVersion(Version newNextVersion) {
		if (newNextVersion != nextVersion) {
			NotificationChain msgs = null;
			if (nextVersion != null)
				msgs = ((InternalEObject) nextVersion).eInverseRemove(this,
					VersioningPackage.VERSION__PREVIOUS_VERSION, Version.class, msgs);
			if (newNextVersion != null)
				msgs = ((InternalEObject) newNextVersion).eInverseAdd(this,
					VersioningPackage.VERSION__PREVIOUS_VERSION, Version.class, msgs);
			msgs = basicSetNextVersion(newNextVersion, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VersioningPackage.VERSION__NEXT_VERSION,
				newNextVersion, newNextVersion));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Version getPreviousVersion() {
		if (previousVersion != null && previousVersion.eIsProxy()) {
			InternalEObject oldPreviousVersion = (InternalEObject) previousVersion;
			previousVersion = (Version) eResolveProxy(oldPreviousVersion);
			if (previousVersion != oldPreviousVersion) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						VersioningPackage.VERSION__PREVIOUS_VERSION, oldPreviousVersion, previousVersion));
			}
		}
		return previousVersion;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Version basicGetPreviousVersion() {
		return previousVersion;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetPreviousVersion(Version newPreviousVersion, NotificationChain msgs) {
		Version oldPreviousVersion = previousVersion;
		previousVersion = newPreviousVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VersioningPackage.VERSION__PREVIOUS_VERSION, oldPreviousVersion, newPreviousVersion);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPreviousVersion(Version newPreviousVersion) {
		if (newPreviousVersion != previousVersion) {
			NotificationChain msgs = null;
			if (previousVersion != null)
				msgs = ((InternalEObject) previousVersion).eInverseRemove(this,
					VersioningPackage.VERSION__NEXT_VERSION, Version.class, msgs);
			if (newPreviousVersion != null)
				msgs = ((InternalEObject) newPreviousVersion).eInverseAdd(this,
					VersioningPackage.VERSION__NEXT_VERSION, Version.class, msgs);
			msgs = basicSetPreviousVersion(newPreviousVersion, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VersioningPackage.VERSION__PREVIOUS_VERSION,
				newPreviousVersion, newPreviousVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ChangePackage getChanges() {
		if (changes != null && changes.eIsProxy()) {
			InternalEObject oldChanges = (InternalEObject) changes;
			changes = (ChangePackage) eResolveProxy(oldChanges);
			if (changes != oldChanges) {
				InternalEObject newChanges = (InternalEObject) changes;
				NotificationChain msgs = oldChanges.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.VERSION__CHANGES, null, null);
				if (newChanges.eInternalContainer() == null) {
					msgs = newChanges.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VersioningPackage.VERSION__CHANGES,
						null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VersioningPackage.VERSION__CHANGES,
						oldChanges, changes));
			}
		}
		return changes;
	}

	// begin of custom code
	/**
	 * Loads the XMI IDs from the given resource and returns them in a map
	 * together with the object each ID belongs to.
	 * 
	 * @param resource
	 *            the resource from which to load the ID mappings
	 * @return a map consisting of object/id mappings, if the resource doesn't
	 *         contain an eobject/id mapping null will be returned
	 */
	private EMap<EObject, String> loadIdsFromResourceForEObjects(Set<EObject> modelElements, XMIResource xmiResource) {

		EMap<EObject, String> eObjectToIdMap;

		if (xmiResource != null) {
			// guess a rough initial size by looking at the size of the contents
			eObjectToIdMap = new BasicEMap<EObject, String>(xmiResource.getContents().size());

			for (EObject eObject : modelElements) {
				String objId = xmiResource.getID(eObject);
				if (objId != null) {
					eObjectToIdMap.put(eObject, objId);
				}
			}

			return eObjectToIdMap;
		}

		return null;
	}

	// end of custom code

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ChangePackage basicGetChanges() {
		return changes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetChanges(ChangePackage newChanges, NotificationChain msgs) {
		ChangePackage oldChanges = changes;
		changes = newChanges;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VersioningPackage.VERSION__CHANGES, oldChanges, newChanges);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setChanges(ChangePackage newChanges) {
		if (newChanges != changes) {
			NotificationChain msgs = null;
			if (changes != null)
				msgs = ((InternalEObject) changes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.VERSION__CHANGES, null, msgs);
			if (newChanges != null)
				msgs = ((InternalEObject) newChanges).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.VERSION__CHANGES, null, msgs);
			msgs = basicSetChanges(newChanges, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VersioningPackage.VERSION__CHANGES, newChanges,
				newChanges));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LogMessage getLogMessage() {
		if (logMessage != null && logMessage.eIsProxy()) {
			InternalEObject oldLogMessage = (InternalEObject) logMessage;
			logMessage = (LogMessage) eResolveProxy(oldLogMessage);
			if (logMessage != oldLogMessage) {
				InternalEObject newLogMessage = (InternalEObject) logMessage;
				NotificationChain msgs = oldLogMessage.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.VERSION__LOG_MESSAGE, null, null);
				if (newLogMessage.eInternalContainer() == null) {
					msgs = newLogMessage.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- VersioningPackage.VERSION__LOG_MESSAGE, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VersioningPackage.VERSION__LOG_MESSAGE,
						oldLogMessage, logMessage));
			}
		}
		return logMessage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LogMessage basicGetLogMessage() {
		return logMessage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetLogMessage(LogMessage newLogMessage, NotificationChain msgs) {
		LogMessage oldLogMessage = logMessage;
		logMessage = newLogMessage;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VersioningPackage.VERSION__LOG_MESSAGE, oldLogMessage, newLogMessage);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLogMessage(LogMessage newLogMessage) {
		if (newLogMessage != logMessage) {
			NotificationChain msgs = null;
			if (logMessage != null)
				msgs = ((InternalEObject) logMessage).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.VERSION__LOG_MESSAGE, null, msgs);
			if (newLogMessage != null)
				msgs = ((InternalEObject) newLogMessage).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VersioningPackage.VERSION__LOG_MESSAGE, null, msgs);
			msgs = basicSetLogMessage(newLogMessage, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VersioningPackage.VERSION__LOG_MESSAGE,
				newLogMessage, newLogMessage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Version getAncestorVersion() {
		if (ancestorVersion != null && ancestorVersion.eIsProxy()) {
			InternalEObject oldAncestorVersion = (InternalEObject) ancestorVersion;
			ancestorVersion = (Version) eResolveProxy(oldAncestorVersion);
			if (ancestorVersion != oldAncestorVersion) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						VersioningPackage.VERSION__ANCESTOR_VERSION, oldAncestorVersion, ancestorVersion));
			}
		}
		return ancestorVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Version basicGetAncestorVersion() {
		return ancestorVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetAncestorVersion(Version newAncestorVersion, NotificationChain msgs) {
		Version oldAncestorVersion = ancestorVersion;
		ancestorVersion = newAncestorVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VersioningPackage.VERSION__ANCESTOR_VERSION, oldAncestorVersion, newAncestorVersion);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setAncestorVersion(Version newAncestorVersion) {
		if (newAncestorVersion != ancestorVersion) {
			NotificationChain msgs = null;
			if (ancestorVersion != null)
				msgs = ((InternalEObject) ancestorVersion).eInverseRemove(this,
					VersioningPackage.VERSION__BRANCHED_VERSIONS, Version.class, msgs);
			if (newAncestorVersion != null)
				msgs = ((InternalEObject) newAncestorVersion).eInverseAdd(this,
					VersioningPackage.VERSION__BRANCHED_VERSIONS, Version.class, msgs);
			msgs = basicSetAncestorVersion(newAncestorVersion, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VersioningPackage.VERSION__ANCESTOR_VERSION,
				newAncestorVersion, newAncestorVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Version> getBranchedVersions() {
		if (branchedVersions == null) {
			branchedVersions = new EObjectWithInverseResolvingEList<Version>(Version.class, this,
				VersioningPackage.VERSION__BRANCHED_VERSIONS, VersioningPackage.VERSION__ANCESTOR_VERSION);
		}
		return branchedVersions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VersioningPackage.VERSION__NEXT_VERSION:
			if (nextVersion != null)
				msgs = ((InternalEObject) nextVersion).eInverseRemove(this,
					VersioningPackage.VERSION__PREVIOUS_VERSION, Version.class, msgs);
			return basicSetNextVersion((Version) otherEnd, msgs);
		case VersioningPackage.VERSION__PREVIOUS_VERSION:
			if (previousVersion != null)
				msgs = ((InternalEObject) previousVersion).eInverseRemove(this,
					VersioningPackage.VERSION__NEXT_VERSION, Version.class, msgs);
			return basicSetPreviousVersion((Version) otherEnd, msgs);
		case VersioningPackage.VERSION__ANCESTOR_VERSION:
			if (ancestorVersion != null)
				msgs = ((InternalEObject) ancestorVersion).eInverseRemove(this,
					VersioningPackage.VERSION__BRANCHED_VERSIONS, Version.class, msgs);
			return basicSetAncestorVersion((Version) otherEnd, msgs);
		case VersioningPackage.VERSION__BRANCHED_VERSIONS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getBranchedVersions()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VersioningPackage.VERSION__PROJECT_STATE:
			return basicSetProjectState(null, msgs);
		case VersioningPackage.VERSION__PRIMARY_SPEC:
			return basicSetPrimarySpec(null, msgs);
		case VersioningPackage.VERSION__TAG_SPECS:
			return ((InternalEList<?>) getTagSpecs()).basicRemove(otherEnd, msgs);
		case VersioningPackage.VERSION__NEXT_VERSION:
			return basicSetNextVersion(null, msgs);
		case VersioningPackage.VERSION__PREVIOUS_VERSION:
			return basicSetPreviousVersion(null, msgs);
		case VersioningPackage.VERSION__CHANGES:
			return basicSetChanges(null, msgs);
		case VersioningPackage.VERSION__LOG_MESSAGE:
			return basicSetLogMessage(null, msgs);
		case VersioningPackage.VERSION__ANCESTOR_VERSION:
			return basicSetAncestorVersion(null, msgs);
		case VersioningPackage.VERSION__BRANCHED_VERSIONS:
			return ((InternalEList<?>) getBranchedVersions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case VersioningPackage.VERSION__PROJECT_STATE:
			if (resolve)
				return getProjectState();
			return basicGetProjectState();
		case VersioningPackage.VERSION__PRIMARY_SPEC:
			if (resolve)
				return getPrimarySpec();
			return basicGetPrimarySpec();
		case VersioningPackage.VERSION__TAG_SPECS:
			return getTagSpecs();
		case VersioningPackage.VERSION__NEXT_VERSION:
			if (resolve)
				return getNextVersion();
			return basicGetNextVersion();
		case VersioningPackage.VERSION__PREVIOUS_VERSION:
			if (resolve)
				return getPreviousVersion();
			return basicGetPreviousVersion();
		case VersioningPackage.VERSION__CHANGES:
			if (resolve)
				return getChanges();
			return basicGetChanges();
		case VersioningPackage.VERSION__LOG_MESSAGE:
			if (resolve)
				return getLogMessage();
			return basicGetLogMessage();
		case VersioningPackage.VERSION__ANCESTOR_VERSION:
			if (resolve)
				return getAncestorVersion();
			return basicGetAncestorVersion();
		case VersioningPackage.VERSION__BRANCHED_VERSIONS:
			return getBranchedVersions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case VersioningPackage.VERSION__PROJECT_STATE:
			setProjectState((Project) newValue);
			return;
		case VersioningPackage.VERSION__PRIMARY_SPEC:
			setPrimarySpec((PrimaryVersionSpec) newValue);
			return;
		case VersioningPackage.VERSION__TAG_SPECS:
			getTagSpecs().clear();
			getTagSpecs().addAll((Collection<? extends TagVersionSpec>) newValue);
			return;
		case VersioningPackage.VERSION__NEXT_VERSION:
			setNextVersion((Version) newValue);
			return;
		case VersioningPackage.VERSION__PREVIOUS_VERSION:
			setPreviousVersion((Version) newValue);
			return;
		case VersioningPackage.VERSION__CHANGES:
			setChanges((ChangePackage) newValue);
			return;
		case VersioningPackage.VERSION__LOG_MESSAGE:
			setLogMessage((LogMessage) newValue);
			return;
		case VersioningPackage.VERSION__ANCESTOR_VERSION:
			setAncestorVersion((Version) newValue);
			return;
		case VersioningPackage.VERSION__BRANCHED_VERSIONS:
			getBranchedVersions().clear();
			getBranchedVersions().addAll((Collection<? extends Version>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case VersioningPackage.VERSION__PROJECT_STATE:
			setProjectState((Project) null);
			return;
		case VersioningPackage.VERSION__PRIMARY_SPEC:
			setPrimarySpec((PrimaryVersionSpec) null);
			return;
		case VersioningPackage.VERSION__TAG_SPECS:
			getTagSpecs().clear();
			return;
		case VersioningPackage.VERSION__NEXT_VERSION:
			setNextVersion((Version) null);
			return;
		case VersioningPackage.VERSION__PREVIOUS_VERSION:
			setPreviousVersion((Version) null);
			return;
		case VersioningPackage.VERSION__CHANGES:
			setChanges((ChangePackage) null);
			return;
		case VersioningPackage.VERSION__LOG_MESSAGE:
			setLogMessage((LogMessage) null);
			return;
		case VersioningPackage.VERSION__ANCESTOR_VERSION:
			setAncestorVersion((Version) null);
			return;
		case VersioningPackage.VERSION__BRANCHED_VERSIONS:
			getBranchedVersions().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case VersioningPackage.VERSION__PROJECT_STATE:
			return projectState != null;
		case VersioningPackage.VERSION__PRIMARY_SPEC:
			return primarySpec != null;
		case VersioningPackage.VERSION__TAG_SPECS:
			return tagSpecs != null && !tagSpecs.isEmpty();
		case VersioningPackage.VERSION__NEXT_VERSION:
			return nextVersion != null;
		case VersioningPackage.VERSION__PREVIOUS_VERSION:
			return previousVersion != null;
		case VersioningPackage.VERSION__CHANGES:
			return changes != null;
		case VersioningPackage.VERSION__LOG_MESSAGE:
			return logMessage != null;
		case VersioningPackage.VERSION__ANCESTOR_VERSION:
			return ancestorVersion != null;
		case VersioningPackage.VERSION__BRANCHED_VERSIONS:
			return branchedVersions != null && !branchedVersions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // VersionImpl
