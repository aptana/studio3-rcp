/*******************************************************************************
 * Copyright (c) 2005 RadRails.org and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.radrails.rails.internal.ui;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;
import org.eclipse.ui.console.IConsoleConstants;

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

		// Top left: Ruby Explorer
		IPlaceholderFolderLayout topLeft = layout.createPlaceholderFolder("topLeft", IPageLayout.LEFT, 0.2f, editorArea);
		
		IPlaceholderFolderLayout left = layout.createPlaceholderFolder (
                "left", IPageLayout.BOTTOM, 0.5f, "leftTop"); //$NON-NLS-1$ //$NON-NLS-2$
        left.addPlaceholder(IPageLayout.ID_OUTLINE);

		// Bottom right: Console, Servers, RubyGems, Rake, Problems, Tasks, Generators, Rails Plugins view
		IPlaceholderFolderLayout consoleArea = layout.createPlaceholderFolder("consoleArea", IPageLayout.BOTTOM, 0.75f,
				editorArea);
		consoleArea.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);
		consoleArea.addPlaceholder(IPageLayout.ID_PROBLEM_VIEW);
		consoleArea.addPlaceholder(IPageLayout.ID_TASK_LIST);

		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
		layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);
	}

}
