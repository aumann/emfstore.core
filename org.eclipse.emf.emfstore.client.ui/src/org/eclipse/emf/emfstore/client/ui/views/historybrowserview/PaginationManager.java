/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Aumann
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.util.HistoryQueryBuilder;

/**
 * Class handling pagination. See constructor {@link #PaginationManager(ProjectSpace, int, int)}
 * 
 * @author Aumann
 * 
 */
public class PaginationManager {
	/**
	 * The version around which history queries are created. At initialization
	 * time this is the base version. It gets change if the user clicks on 'show
	 * next x elements'
	 */
	private PrimaryVersionSpec currentCenterVersionShown;

	private int aboveCenterCount, belowCenterCount;

	private List<HistoryInfo> currentlyPresentedInfos = new ArrayList<HistoryInfo>();

	private ProjectSpace projectSpace;

	private boolean nextPage;

	private boolean prevPage;

	private boolean showAllVersions;

	private EObject modelElement;

	/**
	 * Creates a new PaginationManager with given page range around the central
	 * version. The central version is initialized to be the base version.
	 * 
	 * Note that the real number of versions shown might be smaller if there are
	 * not enough versions above (e.g. base version == head version) or below
	 * the center version (e.g. only x revisions yet, but below is larger).
	 * 
	 * @param projectSpace
	 *            The project space to operate on.
	 * @param modelElement An optional modelElement to show the history for. <code>null</code> to show the history for
	 *            the project space.
	 * @param aboveCenterCount
	 *            The number of versions shown above the central version.
	 * @param belowCenterCount
	 *            The number of versions shown below the central version.
	 */
	public PaginationManager(ProjectSpace projectSpace, EObject modelElement, int aboveCenterCount, int belowCenterCount) {
		this.aboveCenterCount = aboveCenterCount;
		this.belowCenterCount = belowCenterCount;
		this.projectSpace = projectSpace;
		this.modelElement = modelElement;
	}

	/**
	 * @return The history info objects to be displayed on the current page.
	 * @throws EmfStoreException
	 *             If an exception gets thrown contacting the server.
	 */
	public List<HistoryInfo> retrieveHistoryInfos() throws EmfStoreException {
		PrimaryVersionSpec newCenterVersion;
		int beforeCurrent = -1, afterCurrent = -1;
		if ((prevPage || nextPage) && currentCenterVersionShown != null && !currentlyPresentedInfos.isEmpty()) {
			for (int i = 0; i < currentlyPresentedInfos.size(); i++) {
				if (currentlyPresentedInfos.get(i).getPrimerySpec().getIdentifier() == currentCenterVersionShown
					.getIdentifier()) {
					beforeCurrent = i;
					break;
				}
			}
			assert beforeCurrent != -1 : "The currently shown center version should be contained in the currently shown history infos, why has it vanished?";
			afterCurrent = currentlyPresentedInfos.size() - beforeCurrent - 1; // 1
																				// ==
																				// currentCenter
			if (prevPage && beforeCurrent >= aboveCenterCount) {
				// there might be more versions, so swap page if there are
				newCenterVersion = currentlyPresentedInfos.get(0).getPrimerySpec();
			} else if (nextPage && afterCurrent >= belowCenterCount) {
				newCenterVersion = currentlyPresentedInfos.get(currentlyPresentedInfos.size() - 1).getPrimerySpec();
			} else {
				newCenterVersion = currentCenterVersionShown;
			}
		} else {
			newCenterVersion = currentCenterVersionShown;
		}
		HistoryQuery query = getQuery(newCenterVersion);
		List<HistoryInfo> historyInfos = projectSpace.getHistoryInfo(query);

		if (newCenterVersion != null && !currentCenterVersionShown.equals(newCenterVersion)) {
			setCorrectCenterVersionAndHistory(historyInfos, newCenterVersion.getIdentifier(), beforeCurrent);
		} else {
			currentlyPresentedInfos = historyInfos;
		}
		prevPage = false;
		nextPage = false;
		return currentlyPresentedInfos;
	}

	/**
	 * Set correct center version and displayed History infos. 1) prev page:
	 * check if there are enough previous versions if not: set centerVersion
	 * further down to retrieve more lower versions 2) next page : similiar to
	 * prev page
	 * 
	 * @param historyInfos
	 */
	private void setCorrectCenterVersionAndHistory(List<HistoryInfo> newQueryHistoryInfos, int newCenterVersionId,
		int positionOfOldCenterInCurrentDisplay) {
		int idOfCurrentVersionShown = currentlyPresentedInfos.get(positionOfOldCenterInCurrentDisplay).getPrimerySpec()
			.getIdentifier();
		int olderVersions = 0, newerVersions = 0;
		int newCenterVersionPos = -1;
		int oldCenterVersionPos = -1;
		for (int i = 0; i < newQueryHistoryInfos.size(); i++) {
			int idOfI = newQueryHistoryInfos.get(i).getPrimerySpec().getIdentifier();
			if (idOfI > newCenterVersionId) {
				++newerVersions;
			} else if (idOfI < newCenterVersionId) {
				++olderVersions;
			} else if (idOfI == newCenterVersionId) {
				assert newCenterVersionPos == -1 : "Should not be in there twice.";
				newCenterVersionPos = i;
			} else if (idOfI == idOfCurrentVersionShown) {
				assert oldCenterVersionPos == -1 : "Should not be in there twice.";
				oldCenterVersionPos = i;
			}
		}
		assert newCenterVersionPos != -1 : "The query is based around this version. So it must be there.";
		PrimaryVersionSpec newCenterVersion = newQueryHistoryInfos.get(newCenterVersionPos).getPrimerySpec();

		assert prevPage ^ nextPage;

		if (prevPage && newerVersions < aboveCenterCount) {
			List<HistoryInfo> mergedInfos = mergeHistoryInfoLists(newQueryHistoryInfos, currentlyPresentedInfos);
			int oldCenterPos = findPositionOfId(currentCenterVersionShown.getIdentifier(), mergedInfos);

			// not enough versions: go further down, but never further than the old center as this is supposed to be
			// prevPage and thus at the very least not nextPage
			int newCenterPos = Math.min(Math.min(aboveCenterCount, oldCenterPos), newQueryHistoryInfos.size() - 1);
			newCenterVersion = mergedInfos.get(newCenterPos).getPrimerySpec();
			currentlyPresentedInfos = cutInfos(mergedInfos, newCenterPos);

		} else if (nextPage && olderVersions < belowCenterCount) {
			List<HistoryInfo> mergedInfos = mergeHistoryInfoLists(newQueryHistoryInfos, currentlyPresentedInfos);
			int oldCenterPos = findPositionOfId(currentCenterVersionShown.getIdentifier(), mergedInfos);

			// not enough versions: go further up, but never further than the old center as this is supposed to be
			// nextPage and thus at the very least not prevPage
			int newCenterPos = Math.max(Math.max(mergedInfos.size() - 1 - belowCenterCount, oldCenterPos), 0);
			newCenterVersion = mergedInfos.get(newCenterPos).getPrimerySpec();
			currentlyPresentedInfos = cutInfos(mergedInfos, newCenterPos);

		} else {
			currentlyPresentedInfos = newQueryHistoryInfos;
		}
		currentCenterVersionShown = newCenterVersion;

	}

	private List<HistoryInfo> cutInfos(List<HistoryInfo> mergedInfos, int newCenterPos) {
		int smallestIndexIn = Math.max(0, newCenterPos - aboveCenterCount);
		int largestIndexIn = Math.min(mergedInfos.size() - 1, newCenterPos + belowCenterCount);
		List<HistoryInfo> cut = new ArrayList<HistoryInfo>();
		for (int i = smallestIndexIn; i <= largestIndexIn; i++) {
			cut.add(mergedInfos.get(i));
		}
		return cut;
	}

	private int findPositionOfId(int identifier, List<HistoryInfo> mergedInfos) {
		for (int i = 0; i < mergedInfos.size(); i++) {
			if (getId(mergedInfos.get(i)) <= identifier) {
				return i;
			}
		}
		assert false : "Unexpected.";
		return mergedInfos.size() - 1;
	}

	private List<HistoryInfo> mergeHistoryInfoLists(List<HistoryInfo> newQueryHistoryInfos,
		List<HistoryInfo> oldPresentedHistoryInfos) {
		List<HistoryInfo> newerVersions;
		List<HistoryInfo> olderVersions;
		if (prevPage) {
			newerVersions = newQueryHistoryInfos;
			olderVersions = oldPresentedHistoryInfos;
		} else {
			assert nextPage;
			newerVersions = oldPresentedHistoryInfos;
			olderVersions = newQueryHistoryInfos;
		}

		int highestVersionInOlderVersions = olderVersions.get(0).getPrimerySpec().getIdentifier();
		int overLapVersionPosInNewerVersions = -1;
		List<HistoryInfo> mergedInfos = new ArrayList<HistoryInfo>();
		int idOfLastVersionMerged = -1;
		for (int i = 0; i < newerVersions.size(); i++) {
			HistoryInfo currentNewerVersion = newerVersions.get(i);
			int currentVersionInNewerVersions = getId(currentNewerVersion);
			if (currentVersionInNewerVersions == highestVersionInOlderVersions) {
				overLapVersionPosInNewerVersions = i;
				break;
			} else if (currentVersionInNewerVersions < highestVersionInOlderVersions) {
				assert i > 0 : "There must at least be one not older version in newerVersions";
				overLapVersionPosInNewerVersions = i - 1;
			} else {
				mergedInfos.add(currentNewerVersion);
				idOfLastVersionMerged = getId(currentNewerVersion);
			}
		}
		assert overLapVersionPosInNewerVersions != -1 : "As the new query is based around the first/last version of the previous query there must be at least one overlapping version.";

		mergedInfos = performMerge(mergedInfos, newerVersions, olderVersions, overLapVersionPosInNewerVersions,
			idOfLastVersionMerged);

		return mergedInfos;
	}

	private List<HistoryInfo> performMerge(List<HistoryInfo> mergedInfos, List<HistoryInfo> newerVersions,
		List<HistoryInfo> olderVersions, int overLapVersionPosInNewerVersion, int idOfLastVersionMerged) {
		// now start merging from the overlap version on
		// actually merging should be unnecessary as all versions should be
		// contained from the overlap on
		// but this way merging is more robust if paging ever gets mixed with new calls
		int currentPosNewer = overLapVersionPosInNewerVersion;
		int currentPosOlder = 0;
		while (currentPosNewer != newerVersions.size() && currentPosOlder != olderVersions.size()) {
			HistoryInfo nextMergeCandidate;
			if (getId(newerVersions.get(currentPosNewer)) >= getId(olderVersions.get(currentPosOlder))) {
				nextMergeCandidate = newerVersions.get(currentPosNewer);
				++currentPosNewer;
			} else {
				nextMergeCandidate = olderVersions.get(currentPosOlder);
				++currentPosOlder;
			}
			if (idOfLastVersionMerged != getId(nextMergeCandidate)) { // avoid
																		// duplicates
				mergedInfos.add(nextMergeCandidate);
				idOfLastVersionMerged = getId(nextMergeCandidate);
			}
		}
		// add rest of the list that is not at the end
		for (int i = currentPosNewer; i < newerVersions.size(); i++) {
			HistoryInfo nextMergeCandidate = newerVersions.get(i);
			if (idOfLastVersionMerged != getId(nextMergeCandidate)) { // avoid
																		// duplicates
				mergedInfos.add(nextMergeCandidate);
				idOfLastVersionMerged = getId(nextMergeCandidate);
			}
		}

		for (int i = currentPosOlder; i < olderVersions.size(); i++) {
			HistoryInfo nextMergeCandidate = olderVersions.get(i);
			if (idOfLastVersionMerged != getId(nextMergeCandidate)) { // avoid
																		// duplicates
				mergedInfos.add(nextMergeCandidate);
				idOfLastVersionMerged = getId(nextMergeCandidate);
			}
		}
		return mergedInfos;
	}

	private int getId(HistoryInfo info) {
		return info.getPrimerySpec().getIdentifier();
	}

	/**
	 * 
	 * @param centerVersion The query center version.
	 * @return
	 */
	private HistoryQuery getQuery(PrimaryVersionSpec centerVersion) {
		PrimaryVersionSpec version;
		if (centerVersion != null) {
			version = centerVersion;
		} else {
			version = projectSpace.getBaseVersion();
			currentCenterVersionShown = version;
		}

		HistoryQuery query;
		if (modelElement != null && !(modelElement instanceof ProjectSpace)
			&& projectSpace.getProject().containsInstance(modelElement)) {
			query = HistoryQueryBuilder.modelelementQuery(version,
				projectSpace.getProject().getModelElementId(modelElement), aboveCenterCount, belowCenterCount,
				showAllVersions, true);
		} else {
			query = HistoryQueryBuilder.rangeQuery(version, aboveCenterCount, belowCenterCount, showAllVersions,
				!showAllVersions, !showAllVersions, true);
		}

		return query;
	}

	/**
	 * Allows to switch between showing all history info items (across all branches) or just those relevant to the
	 * current project branch.
	 * 
	 * @param allVersions if true versions across all branches are shown, otherwise only versions for the current branch
	 *            including ancestor versions
	 */
	public void setShowAllVersions(boolean allVersions) {
		showAllVersions = allVersions;
	}

	/**
	 * Swaps to the next page. Call {@link #retrieveHistoryInfos()} to retrieve
	 * the new page.
	 */
	public void nextPage() {
		nextPage = true;
		prevPage = false;
	}

	/**
	 * Swaps to the previous page. Call {@link #retrieveHistoryInfos()} to
	 * retrieve the new page.
	 */
	public void previousPage() {
		prevPage = true;
		nextPage = false;
	}

	/**
	 * Swaps to a page containing the specified version. Call {@link #retrieveHistoryInfos()} to
	 * retrieve the new page.
	 * 
	 * @param id The identifier of the version to display.
	 * @throws EmfStoreException When an error occurs while retrieving versions from the server.
	 * 
	 * @return true if a version range surrounding the id has been found, false otherwise. Note that the range does not
	 *         necessarily contain the id, for example if only versions for a certain branch are shown.
	 */
	public boolean setVersion(int id) throws EmfStoreException {
		prevPage = false;
		nextPage = false;
		if (currentlyPresentedInfos.isEmpty() || currentCenterVersionShown == null) {
			return false;
		}

		int newestVersion = getId(currentlyPresentedInfos.get(0));
		int oldestVersion = getId(currentlyPresentedInfos.get(currentlyPresentedInfos.size() - 1));

		List<HistoryInfo> currentHistoryInfosBU = currentlyPresentedInfos;
		PrimaryVersionSpec currentCenterBU = currentCenterVersionShown;

		if (newestVersion >= id && id >= oldestVersion) {
			return true; // already there
		}

		List<HistoryInfo> historyInfos = new ArrayList<HistoryInfo>();
		while (!containsId(historyInfos, id)) {
			if (id > newestVersion) {
				// retrieve newer versions until the desired id is in range (i.e. either found or not found)
				prevPage = true;
			} else {
				nextPage = true;
			}
			List<HistoryInfo> result = retrieveHistoryInfos();
			if (getId(result.get(0)) == newestVersion || getId(result.get(result.size() - 1)) == oldestVersion) {
				// could not find the desired version range
				assert !containsId(result, id);
				currentlyPresentedInfos = currentHistoryInfosBU;
				currentCenterVersionShown = currentCenterBU;
				return false;
			}
			newestVersion = getId(result.get(0));
			oldestVersion = getId(result.get(result.size() - 1));
		}
		return true;
	}

	private boolean containsId(List<HistoryInfo> infos, int id) {
		int newestVersion = getId(infos.get(0));
		int oldestVersion = getId(infos.get(infos.size() - 1));

		if (newestVersion >= id && id >= oldestVersion) {
			return true;
		}
		return false;
	}
}
