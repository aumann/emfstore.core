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
package org.eclipse.emf.emfstore.server.model.versioning.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.emfstore.common.model.ModelFactory;
import org.eclipse.emf.emfstore.server.model.provider.ServerEditPlugin;
import org.eclipse.emf.emfstore.server.model.versioning.Version;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.emfstore.server.model.versioning.Version} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class VersionItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
	IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VersionItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addNextVersionPropertyDescriptor(object);
			addPreviousVersionPropertyDescriptor(object);
			addAncestorVersionPropertyDescriptor(object);
			addBranchedVersionsPropertyDescriptor(object);
			addMergedToVersionPropertyDescriptor(object);
			addMergedFromVersionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Next Version feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addNextVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_Version_nextVersion_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_Version_nextVersion_feature", "_UI_Version_type"),
			VersioningPackage.Literals.VERSION__NEXT_VERSION, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Previous Version feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addPreviousVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_Version_previousVersion_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_Version_previousVersion_feature", "_UI_Version_type"),
			VersioningPackage.Literals.VERSION__PREVIOUS_VERSION, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Ancestor Version feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addAncestorVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_Version_ancestorVersion_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_Version_ancestorVersion_feature", "_UI_Version_type"),
			VersioningPackage.Literals.VERSION__ANCESTOR_VERSION, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Branched Versions feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addBranchedVersionsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Version_branchedVersions_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Version_branchedVersions_feature",
					"_UI_Version_type"), VersioningPackage.Literals.VERSION__BRANCHED_VERSIONS, true, false, true,
				null, null, null));
	}

	/**
	 * This adds a property descriptor for the Merged To Version feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMergedToVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_Version_mergedToVersion_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_Version_mergedToVersion_feature", "_UI_Version_type"),
			VersioningPackage.Literals.VERSION__MERGED_TO_VERSION, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Merged From Version feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMergedFromVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Version_mergedFromVersion_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Version_mergedFromVersion_feature",
					"_UI_Version_type"), VersioningPackage.Literals.VERSION__MERGED_FROM_VERSION, true, false, true,
				null, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(VersioningPackage.Literals.VERSION__PROJECT_STATE);
			childrenFeatures.add(VersioningPackage.Literals.VERSION__PRIMARY_SPEC);
			childrenFeatures.add(VersioningPackage.Literals.VERSION__TAG_SPECS);
			childrenFeatures.add(VersioningPackage.Literals.VERSION__CHANGES);
			childrenFeatures.add(VersioningPackage.Literals.VERSION__LOG_MESSAGE);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns Version.gif.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Version"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_Version_type");
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached children and by creating
	 * a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Version.class)) {
		case VersioningPackage.VERSION__PROJECT_STATE:
		case VersioningPackage.VERSION__PRIMARY_SPEC:
		case VersioningPackage.VERSION__TAG_SPECS:
		case VersioningPackage.VERSION__CHANGES:
		case VersioningPackage.VERSION__LOG_MESSAGE:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(VersioningPackage.Literals.VERSION__PROJECT_STATE,
			ModelFactory.eINSTANCE.createProject()));

		newChildDescriptors.add(createChildParameter(VersioningPackage.Literals.VERSION__PRIMARY_SPEC,
			VersioningFactory.eINSTANCE.createPrimaryVersionSpec()));

		newChildDescriptors.add(createChildParameter(VersioningPackage.Literals.VERSION__TAG_SPECS,
			VersioningFactory.eINSTANCE.createTagVersionSpec()));

		newChildDescriptors.add(createChildParameter(VersioningPackage.Literals.VERSION__CHANGES,
			VersioningFactory.eINSTANCE.createChangePackage()));

		newChildDescriptors.add(createChildParameter(VersioningPackage.Literals.VERSION__LOG_MESSAGE,
			VersioningFactory.eINSTANCE.createLogMessage()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ServerEditPlugin.INSTANCE;
	}

}
