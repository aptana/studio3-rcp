/*******************************************************************************
 *  Copyright (c) 2010 IBM Corporation and others.
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
import com.aptana.ruby.debug.ui.breakpoints.RubyBreakpointConditionEditor;

/**
 * Detail pane for editing a line breakpoint.
 *
 * @since 1.0
 */
public class LineBreakpointDetailPane extends AbstractDetailPane {
	
	/**
	 * Identifier for this detail pane editor
	 */
	public static final String DETAIL_PANE_LINE_BREAKPOINT = RubyDebugUIPlugin.getUniqueIdentifier() + ".DETAIL_PANE_LINE_BREAKPOINT"; //$NON-NLS-1$
	
	public LineBreakpointDetailPane() {
		super(BreakpointMessages.BreakpointConditionDetailPane_0, BreakpointMessages.BreakpointConditionDetailPane_0, DETAIL_PANE_LINE_BREAKPOINT); 
		addAutosaveProperties(new int[]{
				RubyBreakpointConditionEditor.PROP_CONDITION_ENABLED,
				StandardRubyBreakpointEditor.PROP_HIT_COUNT_ENABLED});
	}
	
	protected AbstractRubyBreakpointEditor createEditor(Composite parent) {
		return new CompositeBreakpointEditor(
			new AbstractRubyBreakpointEditor[] {new StandardRubyBreakpointEditor(), new RubyBreakpointConditionEditor()}); 
	}

}
