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
package org.eclipse.emf.emfstore.server.model.provider;

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
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.emfstore.server.model.ModelFactory;
import org.eclipse.emf.emfstore.server.model.ModelPackage;
import org.eclipse.emf.emfstore.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.emfstore.server.model.ProjectHistory} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ProjectHistoryItemProvider extends ItemProviderAdapter implements
		IEditingDomainItemProvider, IStructuredItemContentProvider,
		ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ProjectHistoryItemProvider(AdapterFactory adapterFactory) {
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

			addProjectNamePropertyDescriptor(object);
			addProjectDescriptionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Project Name feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addProjectNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ProjectHistory_projectName_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ProjectHistory_projectName_feature",
						"_UI_ProjectHistory_type"),
				ModelPackage.Literals.PROJECT_HISTORY__PROJECT_NAME, true,
				false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null,
				null));
	}

	/**
	 * This adds a property descriptor for the Project Description feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addProjectDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ProjectHistory_projectDescription_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ProjectHistory_projectDescription_feature",
						"_UI_ProjectHistory_type"),
				ModelPackage.Literals.PROJECT_HISTORY__PROJECT_DESCRIPTION,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null, null));
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
					.add(ModelPackage.Literals.PROJECT_HISTORY__PROJECT_ID);
			childrenFeatures
					.add(ModelPackage.Literals.PROJECT_HISTORY__VERSIONS);
			childrenFeatures
					.add(ModelPackage.Literals.PROJECT_HISTORY__SHARED_PROPERTIES);
			childrenFeatures
					.add(ModelPackage.Literals.PROJECT_HISTORY__BRANCHES);
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

	/**
	 * This returns ProjectHistory.gif. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object,
				getResourceLocator().getImage("full/obj16/ProjectHistory"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ProjectHistory) object).getProjectName();
		return label == null || label.length() == 0 ? getString("_UI_ProjectHistory_type")
				: getString("_UI_ProjectHistory_type") + " " + label;
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

		switch (notification.getFeatureID(ProjectHistory.class)) {
		case ModelPackage.PROJECT_HISTORY__PROJECT_NAME:
		case ModelPackage.PROJECT_HISTORY__PROJECT_DESCRIPTION:
			fireNotifyChanged(new ViewerNotification(notification,
					notification.getNotifier(), false, true));
			return;
		case ModelPackage.PROJECT_HISTORY__PROJECT_ID:
		case ModelPackage.PROJECT_HISTORY__VERSIONS:
		case ModelPackage.PROJECT_HISTORY__SHARED_PROPERTIES:
		case ModelPackage.PROJECT_HISTORY__BRANCHES:
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
				ModelPackage.Literals.PROJECT_HISTORY__PROJECT_ID,
				ModelFactory.eINSTANCE.createProjectId()));

		newChildDescriptors.add(createChildParameter(
				ModelPackage.Literals.PROJECT_HISTORY__VERSIONS,
				VersioningFactory.eINSTANCE.createVersion()));

		newChildDescriptors.add(createChildParameter(
				ModelPackage.Literals.PROJECT_HISTORY__SHARED_PROPERTIES,
				org.eclipse.emf.emfstore.common.model.ModelFactory.eINSTANCE
						.createEMFStoreProperty()));

		newChildDescriptors.add(createChildParameter(
				ModelPackage.Literals.PROJECT_HISTORY__BRANCHES,
				VersioningFactory.eINSTANCE.createBranchInfo()));
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
