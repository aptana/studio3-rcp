/*******************************************************************************
 * Copyright (c) 2005-2010 Aptana.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.radrails.rails.internal.ui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;
import org.eclipse.ui.console.IConsoleConstants;

import com.aptana.terminal.views.TerminalView;

/**
 * Factory for the Rails perspective.
 * 
 * @author mkent
 * @author cwilliams
 * @author schitale
 */
public class RailsPerspectiveFactory implements IPerspectiveFactory
{
	/**
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
	public void createInitialLayout(IPageLayout layout)
	{
		// Get the editor area
		String editorArea = layout.getEditorArea();

		// Left
		layout.createPlaceholderFolder("left", IPageLayout.LEFT, 0.25f, editorArea); //$NON-NLS-1$

		// Bottom right: Console. Had to leave this programmatic to get the Console appear in bottom right
        IPlaceholderFolderLayout consoleArea = layout.createPlaceholderFolder("consoleArea", IPageLayout.BOTTOM, 0.75f, //$NON-NLS-1$
				editorArea);
		consoleArea.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);

		// We have to do this programmatically so that we can use place holder which allows wildcard in view id so
		// that views with secondary ids can be placed correctly.
		consoleArea.addPlaceholder(TerminalView.ID+":*"); //$NON-NLS-1$

		// We don't want to show the LAUNCH_ACTION_SET for now
//		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
		layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);
	}
}
