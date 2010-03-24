/*******************************************************************************
 *  Copyright (c) 2009 IBM Corporation and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.aptana.ruby.internal.debug.ui.breakpoints;

import org.eclipse.swt.widgets.Composite;

import com.aptana.ruby.debug.ui.RubyDebugUIPlugin;

/**
 * Suspend policy and hit count detail pane.
 * 
 * @since 1.0
 */
public class StandardBreakpointDetailPane extends AbstractDetailPane
{

	/**
	 * Identifier for this detail pane editor
	 */
	public static final String DETAIL_PANE_STANDARD = RubyDebugUIPlugin.getUniqueIdentifier() + ".DETAIL_PANE_STANDARD"; //$NON-NLS-1$

	public StandardBreakpointDetailPane()
	{
		super(BreakpointMessages.StandardBreakpointDetailPane_0, BreakpointMessages.StandardBreakpointDetailPane_0,
				DETAIL_PANE_STANDARD);
		addAutosaveProperties(new int[] { StandardRubyBreakpointEditor.PROP_HIT_COUNT_ENABLED });
	}

	protected AbstractRubyBreakpointEditor createEditor(Composite parent)
	{
		return new StandardRubyBreakpointEditor();
	}

}
