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
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.text.SimpleDateFormat;

import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.views.scm.SCMLabelProvider;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;

public class HistorySCMLabelProvider extends SCMLabelProvider {

	public HistorySCMLabelProvider(Project project) {
		super(project);
	}

	@Override
	protected String getText(HistoryInfo historyInfo) {
		if (historyInfo.getPrimerySpec() != null && historyInfo.getPrimerySpec().getIdentifier() == -1) {
			return LOCAL_REVISION;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
		String baseVersion = "";
		if (historyInfo.getPrimerySpec().getIdentifier() == WorkspaceManager.getProjectSpace(getProject())
			.getBaseVersion().getIdentifier()) {
			baseVersion = "*";
		}
		StringBuilder builder = new StringBuilder();

		if (!historyInfo.getTagSpecs().isEmpty()) {
			builder.append("[");
			for (TagVersionSpec versionSpec : historyInfo.getTagSpecs()) {
				builder.append(versionSpec.getName());
				builder.append(",");
			}
			builder.replace(builder.length() - 1, builder.length(), "] ");
		}

		builder.append(baseVersion);
		builder.append("Version ");
		builder.append(historyInfo.getPrimerySpec().getIdentifier());
		// LogMessage logMessage = null;

		// if (historyInfo.getLogMessage() != null) {
		// logMessage = historyInfo.getLogMessage();
		// } else if (historyInfo.getChangePackage() != null && historyInfo.getChangePackage().getLogMessage() != null)
		// {
		// logMessage = historyInfo.getChangePackage().getLogMessage();
		// }
		// if (logMessage != null) {
		// builder.append(" [");
		// builder.append(logMessage.getAuthor());
		// Date clientDate = logMessage.getClientDate();
		// if (clientDate != null) {
		// builder.append(" @ ");
		// builder.append(dateFormat.format(clientDate));
		// }
		// builder.append("] ");
		// builder.append(logMessage.getMessage());
		// }
		return builder.toString();
	}

}
