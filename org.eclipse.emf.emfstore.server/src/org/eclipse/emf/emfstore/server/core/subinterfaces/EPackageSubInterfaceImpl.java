/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Tobias Verhoeven
 * Maximilian Koegel
 ******************************************************************************/

package org.eclipse.emf.emfstore.server.core.subinterfaces;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.DanglingHREFException;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.ServerConfiguration;
import org.eclipse.emf.emfstore.server.core.AbstractEmfstoreInterface;
import org.eclipse.emf.emfstore.server.core.AbstractSubEmfstoreInterface;
import org.eclipse.emf.emfstore.server.core.MonitorProvider;
import org.eclipse.emf.emfstore.server.core.helper.EPackageHelper;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.FatalEmfStoreException;

/**
 * Subinterface for EPackage registration.
 * 
 * @author mkoegel
 * 
 */
public class EPackageSubInterfaceImpl extends AbstractSubEmfstoreInterface {

	private static final String E_PACKAGE_REGISTRATION = "EPackage_Registration";

	/**
	 * Constructor.
	 * 
	 * @param parentInterface the parent interface
	 * @throws FatalEmfStoreException if init fails
	 */
	public EPackageSubInterfaceImpl(AbstractEmfstoreInterface parentInterface) throws FatalEmfStoreException {
		super(parentInterface);
	}

	/**
	 * Register and store the given EPackage.
	 * 
	 * @param ePackage the package
	 * @throws EmfStoreException if registration storage fails
	 */
	public void registerEPackage(EPackage ePackage) throws EmfStoreException {
		synchronized (MonitorProvider.getInstance().getMonitor(E_PACKAGE_REGISTRATION)) {
			List<EPackage> packages = EPackageHelper.getAllSubPackages(ePackage);
			Set<EPackage> rmPackages = new HashSet<EPackage>();
			packages.add(ePackage);

			// check for subpackages that are already registered
			for (EPackage subPkg : packages) {
				if (EPackage.Registry.INSTANCE.getEPackage(subPkg.getNsURI()) != null) {
					rmPackages.add(subPkg);
				}
			}
			packages.removeAll(rmPackages);

			// remove subpackages that are already registered from
			// input-EPackage, the diff-package is registered and saved.
			EPackageHelper.removeSubPackages(ePackage, rmPackages);

			if (packages.isEmpty()) {
				throw new EmfStoreException(
					"Registration failed: Package(s) with supplied NsUris(s) is/are already registred!");

			}

			// Save the EPackages to disc as an ".ecore"-file
			String uriFileName = null;
			try {
				uriFileName = URLEncoder.encode(ePackage.getNsURI(), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				throw new EmfStoreException("Registration failed: Could not convert NsUri to filename!");
			}
			URI fileUri = URI.createFileURI(ServerConfiguration.getServerHome() + "dynamic-models/" + uriFileName
				+ (uriFileName.endsWith(".ecore") ? "" : ".ecore"));

			// create a resource to save the file to disc
			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("ecore", new EcoreResourceFactoryImpl());
			Resource resource = resourceSet.createResource(fileUri);
			resource.getContents().add(ePackage);
			try {
				resource.save(null);
			} catch (IOException e) {
				if (e.getCause() instanceof DanglingHREFException) {
					// Ignore, as the referenced elements were either stored earlier or can still be
					// stored later.
				} else {
					throw new EmfStoreException("Registration failed: Could not persist .ecore!", e);
				}
			}
			// Finally register EPackages in global EPackage-registry.
			for (EPackage registerPackage : packages) {
				EPackage.Registry.INSTANCE.put(registerPackage.getNsURI(), registerPackage);
			}
			ModelUtil.logInfo("EPackage \"" + ePackage.getNsURI() + "\" registered and saved.");

		}
	}
}
