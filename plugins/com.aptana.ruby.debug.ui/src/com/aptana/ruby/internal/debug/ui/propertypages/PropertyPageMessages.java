/*******************************************************************************
 * Copyright (c) 2003, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM - Initial API and implementation
 *******************************************************************************/
package com.aptana.ruby.internal.debug.ui.propertypages;

import org.eclipse.osgi.util.NLS;

public class PropertyPageMessages extends NLS
{
	private static final String BUNDLE_NAME = PropertyPageMessages.class.getName();

	public static String RubyBreakpointConditionEditor_0;

	public static String BreakpointConditionEditor_1;

	public static String RubyBreakpointPage_0;
	public static String RubyBreakpointPage_4;

	static
	{
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, PropertyPageMessages.class);
	}

}
