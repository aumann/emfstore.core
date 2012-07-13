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
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.emfstore.server.model.provider.ServerEditPlugin;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage;
import org.eclipse.emf.emfstore.server.model.versioning.events.EventsFactory;
import org.eclipse.emf.emfstore.server.model.versioning.events.server.ServerFactory;
import org.eclipse.emf.emfstore.server.model.versioning.operations.OperationsFactory;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.emfstore.server.model.versioning.ChangePackage}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ChangePackageItemProvider extends ItemProviderAdapter implements
		IEditingDomainItemProvider, IStructuredItemContentProvider,
		ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ChangePackageItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to
	 * deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in
	 * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(
			Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures
					.add(VersioningPackage.Literals.CHANGE_PACKAGE__OPERATIONS);
			childrenFeatures
					.add(VersioningPackage.Literals.CHANGE_PACKAGE__EVENTS);
			childrenFeatures
					.add(VersioningPackage.Literals.CHANGE_PACKAGE__LOG_MESSAGE);
			childrenFeatures
					.add(VersioningPackage.Literals.CHANGE_PACKAGE__VERSION_PROPERTIES);
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
		// Check the type of the specified child object and return the proper
		// feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	// begin of custom code
	/**
	 * @param object
	 *            the object
	 * @return This returns the image.
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object,
				getResourceLocator().getImage("full/obj16/ChangePackage.png"));
	}

	// end of custom code

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_ChangePackage_type");
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(ChangePackage.class)) {
		case VersioningPackage.CHANGE_PACKAGE__OPERATIONS:
		case VersioningPackage.CHANGE_PACKAGE__EVENTS:
		case VersioningPackage.CHANGE_PACKAGE__LOG_MESSAGE:
		case VersioningPackage.CHANGE_PACKAGE__VERSION_PROPERTIES:
			fireNotifyChanged(new ViewerNotification(notification,
					notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(
			Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(
				VersioningPackage.Literals.CHANGE_PACKAGE__OPERATIONS,
				OperationsFactory.eINSTANCE.createCompositeOperation()));

		newChildDescriptors.add(createChildParameter(
				VersioningPackage.Literals.CHANGE_PACKAGE__OPERATIONS,
				OperationsFactory.eINSTANCE.createCreateDeleteOperation()));

		newChildDescriptors.add(createChildParameter(
				VersioningPackage.Literals.CHANGE_PACKAGE__OPERATIONS,
				OperationsFactory.eINSTANCE.createAttributeOperation()));

		newChildDescriptors.add(createChildParameter(
				VersioningPackage.Literals.CHANGE_PACKAGE__OPERATIONS,
				OperationsFactory.eINSTANCE.createMultiAttributeOperation()));

		newChildDescriptors
				.add(createChildParameter(
						VersioningPackage.Literals.CHANGE_PACKAGE__OPERATIONS,
						OperationsFactory.eINSTANCE
								.createMultiAttributeSetOperation()));

		newChildDescriptors
				.add(createChildParameter(
						VersioningPackage.Literals.CHANGE_PACKAGE__OPERATIONS,
						OperationsFactory.eINSTANCE
								.createMultiAttributeMoveOperation()));

		newChildDescriptors.add(createChildParameter(
				VersioningPackage.Literals.CHANGE_PACKAGE__OPERATIONS,
				OperationsFactory.eINSTANCE.createSingleReferenceOperation()));

		newChildDescriptors
				.add(createChildParameter(
						VersioningPackage.Literals.CHANGE_PACKAGE__OPERATIONS,
						OperationsFactory.eINSTANCE
								.createMultiReferenceSetOperation()));

		newChildDescriptors.add(createChildParameter(
				VersioningPackage.Literals.CHANGE_PACKAGE__OPERATIONS,
				OperationsFactory.eINSTANCE.createMultiReferenceOperation()));

		newChildDescriptors
				.add(createChildParameter(
						VersioningPackage.Literals.CHANGE_PACKAGE__OPERATIONS,
						OperationsFactory.eINSTANCE
								.createMultiReferenceMoveOperation()));

		newChildDescriptors.add(createChildParameter(
				VersioningPackage.Literals.CHANGE_PACKAGE__EVENTS,
				EventsFactory.eINSTANCE.createEvent()));

		newChildDescriptors.add(createChildParameter(
				VersioningPackage.Literals.CHANGE_PACKAGE__EVENTS,
				ServerFactory.eINSTANCE.createProjectUpdatedEvent()));

		newChildDescriptors.add(createChildParameter(
				VersioningPackage.Literals.CHANGE_PACKAGE__LOG_MESSAGE,
				VersioningFactory.eINSTANCE.createLogMessage()));

		newChildDescriptors.add(createChildParameter(
				VersioningPackage.Literals.CHANGE_PACKAGE__VERSION_PROPERTIES,
				VersioningFactory.eINSTANCE.createVersionProperty()));
	}

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ServerEditPlugin.INSTANCE;
	}

}
