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
package org.eclipse.emf.emfstore.client.ui.dialogs.merge.ui.components;

import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.ui.DecisionBox;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.ui.widgets.MergeTextWidget;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.ui.widgets.OtherInvolvedWidget;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * Uses widgets to display details if needed for the decision box.
 * 
 * @author wesendon
 */
public class DetailsComponent extends Section {

	/**
	 * Default constructor.
	 * 
	 * @param decisionBox
	 *            parent
	 * @param conflict
	 *            conflict
	 */
	public DetailsComponent(final DecisionBox decisionBox, Conflict conflict) {
		super(decisionBox, Section.TWISTIE);
		setText("Details");
		setLayout(new FillLayout());
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.horizontalSpan = 2;
		setLayoutData(layoutData);
		setBackground(decisionBox.getBackground());
		// section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		int columns = 1;

		//
		// for (ConflictOption option : conflict.getOptions()) {
		// if (option.isDetailsProvider()) {
		// columns++;
		// }
		// }

		Composite client = new Composite(this, SWT.NONE);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = columns;
		layout.makeColumnsEqualWidth = true;
		layout.topMargin = 0;
		layout.bottomMargin = 0;
		layout.rightMargin = 0;
		layout.leftMargin = 0;
		client.setLayout(layout);
		client.setBackground(this.getBackground());

		MergeTextWidget multiWidget = null;
		for (ConflictOption option : conflict.getOptions()) {
			if (!option.isDetailsProvider() || option.getDetailProvider() == null) {
				continue;
			}
			// TODO BRANCH hardcoded behavior, maybe generalize in later iteration
			if (option.getDetailProvider().startsWith(DecisionUtil.WIDGET_MULTILINE)) {
				if (multiWidget == null) {
					multiWidget = new MergeTextWidget(decisionBox, this);
				}
				multiWidget.addOption(option);
			} else if (option.getDetailProvider().startsWith(DecisionUtil.WIDGET_OTHERINVOLVED)) {
				new OtherInvolvedWidget(client, decisionBox.getDecisionManager(), option);
			}

			for (ExtensionElement element : new ExtensionPoint("org.eclipse.emf.emfstore.client.ui.merge.detailwidget")
				.getExtensionElements()) {
				if (option.getDetailProvider().equals(element.getAttribute("id"))) {
					DetailsPart detailsPart = element.getClass("class", DetailsPart.class);
					if (detailsPart != null) {
						detailsPart.initialize(decisionBox.getDecisionManager(), option, client);
					}
				}
			}
		}

		if (multiWidget != null) {
			multiWidget.createContent(client);
		}

		setClient(client);
		addExpansionListener(new IExpansionListener() {

			// FIXME: assuming initial size
			private Rectangle bounds = new Rectangle(0, 0, 0, 20);

			public void expansionStateChanged(ExpansionEvent e) {
				int height = bounds.height;
				bounds.height = getBounds().height;
				decisionBox.layoutPage(bounds.height - height);
			}

			public void expansionStateChanging(ExpansionEvent e) {
				// decisionBox.layoutPage();
			}
		});
	}
}
