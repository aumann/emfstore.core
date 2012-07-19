package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.RangeQuery;
import org.eclipse.emf.emfstore.server.model.versioning.util.HistoryQueryBuilder;

/**
 * @author Aumann
 * 
 */
public class PaginationManager {
	/**
	 * The version around which history queries are created. At initialization time this is the base version. It gets
	 * change if the user clicks on 'show next x elements'
	 */
	private PrimaryVersionSpec currentCenterVersionShown;

	private int aboveCenterCount, belowCenterCount;

	private List<HistoryInfo> currentlyPresentedInfos = new ArrayList<HistoryInfo>();

	private ProjectSpace projectSpace;

	private boolean nextPage = false;

	private boolean prevPage = false;

	/**
	 * Creates a new PaginationManager with given page range around the central version. The central version is
	 * initialized to be the base version.
	 * 
	 * Note that the real number of versions shown might be smaller if there are not enough versions above (e.g. base
	 * version == head version) or below the
	 * center version (e.g. only x revisions yet, but below is larger).
	 * 
	 * @param projectSpace The project space to operate on.
	 * @param aboveCenterCount The number of versions shown above the central version.
	 * @param belowCenterCount The number of versions shown below the central version.
	 */
	public PaginationManager(ProjectSpace projectSpace, int aboveCenterCount, int belowCenterCount) {
		this.aboveCenterCount = aboveCenterCount;
		this.belowCenterCount = belowCenterCount;
		this.projectSpace = projectSpace;
	}

	/**
	 * @return The history info objects to be displayed on the current page.
	 * @throws EmfStoreException If an exception gets thrown contacting the server.
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
			afterCurrent = currentlyPresentedInfos.size() - beforeCurrent - 1; // 1 == currentCenter
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

			setCorrectCenterVersionAndHistory(historyInfos);

			currentCenterVersionShown = newCenterVersion;
		}

		currentlyPresentedInfos = historyInfos;
		prevPage = nextPage = false;
		return historyInfos;
	}

	/**
	 * Set correct center version and displayed History infos.
	 * 1) prev page: check if there are enough previous versions
	 * if not: set centerVersion further down to retrieve more lower versions
	 * 2) next page : similiar to prev page
	 * 
	 * @param historyInfos
	 */
	private void setCorrectCenterVersionAndHistory(List<HistoryInfo> newQueryHistoryInfos,
		int positionOfOldCenterInCurrentDisplay) {
		int idOfCurrentVersionShown = currentlyPresentedInfos.get(positionOfOldCenterInCurrentDisplay).getPrimerySpec()
			.getIdentifier();
		int olderVersions = 0, newerVersions = 0;
		int currentCenterVersionPos = -1;
		for (int i = 0; i < newQueryHistoryInfos.size(); i++) {
			int idOfI = newQueryHistoryInfos.get(i).getPrimerySpec().getIdentifier();
			if (idOfI > idOfCurrentVersionShown) {
				++newerVersions;
			} else if (idOfI < idOfCurrentVersionShown) {
				++olderVersions;
			} else if (idOfI == idOfCurrentVersionShown) {
				assert currentCenterVersionPos == -1 : "Should not be in there twice.";
				currentCenterVersionPos = i;
			}
		}
		PrimaryVersionSpec newCenterVersion;

		if (prevPage) {
			if (newerVersions < aboveCenterCount) {
				List<HistoryInfo> mergedInfos = mergeHistoryInfoLists(newQueryHistoryInfos, currentlyPresentedInfos);
				int oldCenterPos = findPositionOfId(currentCenterVersionShown.getIdentifier(), mergedInfos);

				// not enough versions
				// go further down
				newCenterVersion = newQueryHistoryInfos.get(Math.min(olderVersions, newQueryHistoryInfos.size() - 1))
					.getPrimerySpec();
				if (newCenterVersion.getIdentifier() != currentCenterVersionShown.getIdentifier()) {
					currentCenterVersionShown = newCenterVersion;
					query = getQuery(newCenterVersion); // reretrieve
					newQueryHistoryInfos = projectSpace.getHistoryInfo(query);
				}
			}

		} else {
			assert nextPage;
			if (olderVersions < belowCenterCount) {
				int size = newQueryHistoryInfos.size();
				newCenterVersion = newQueryHistoryInfos.get(Math.max(size - olderVersions, 0)).getPrimerySpec();
				if (newCenterVersion.getIdentifier() != currentCenterVersionShown.getIdentifier()) {
					query = getQuery(newCenterVersion); // reretrieve
					newQueryHistoryInfos = projectSpace.getHistoryInfo(query);
				}
			}

		}

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

		// now start merging from the overlap version on
		// actually merging should be unnecessary as all versions should be contained from the overlap on
		// but merging is more robust if paging ever gets mixed with new calls
		int currentPosNewer = overLapVersionPosInNewerVersions;
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
			if (idOfLastVersionMerged != getId(nextMergeCandidate)) { // avoid duplicates
				mergedInfos.add(nextMergeCandidate);
				idOfLastVersionMerged = getId(nextMergeCandidate);
			}
		}
		// add rest of the list that is not at the end
		for (int i = currentPosNewer; i < newerVersions.size(); i++) {
			HistoryInfo nextMergeCandidate = newerVersions.get(i);
			if (idOfLastVersionMerged != getId(nextMergeCandidate)) { // avoid duplicates
				mergedInfos.add(nextMergeCandidate);
				idOfLastVersionMerged = getId(nextMergeCandidate);
			}
		}

		for (int i = currentPosOlder; i < olderVersions.size(); i++) {
			HistoryInfo nextMergeCandidate = olderVersions.get(i);
			if (idOfLastVersionMerged != getId(nextMergeCandidate)) { // avoid duplicates
				mergedInfos.add(nextMergeCandidate);
				idOfLastVersionMerged = getId(nextMergeCandidate);
			}
		}
		return mergedInfos;
	}

	private int getId(HistoryInfo info) {
		return info.getPrimerySpec().getIdentifier();
	}

	private HistoryQuery getQuery(PrimaryVersionSpec centerVersion) {

		boolean allVersions = true;
		PrimaryVersionSpec version;
		if (centerVersion != null) {
			version = centerVersion;
		} else {
			version = projectSpace.getBaseVersion();
			currentCenterVersionShown = version;
		}
		RangeQuery query = HistoryQueryBuilder.rangeQuery(version, aboveCenterCount, belowCenterCount, allVersions,
			false, false, true);

		return query;
	}

	/**
	 * Swaps to the next page. Call {@link #retrieveHistoryInfos()} to retrieve the new page.
	 */
	public void nextPage() {
		nextPage = true;
	}

	/**
	 * Swaps to the previous page. Call {@link #retrieveHistoryInfos()} to retrieve the new page.
	 */
	public void previousPage() {
		prevPage = true;
	}

}
