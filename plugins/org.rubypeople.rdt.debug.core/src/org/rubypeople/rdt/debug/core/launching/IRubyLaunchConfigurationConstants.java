/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Bjorn Freeman-Benson - initial API and implementation
 *******************************************************************************/
package org.rubypeople.rdt.debug.core.launching;

import org.rubypeople.rdt.debug.core.RdtDebugCorePlugin;

/**
 * Constants for the PDA debugger.
 */
public interface IRubyLaunchConfigurationConstants {
	
	/**
	 * Unique identifier for the Ruby debug model (value 
	 * <code>org.rubypeople.rdt.debug</code>).
	 */
	public static final String ID_RUBY_DEBUG_MODEL = RdtDebugCorePlugin.MODEL_IDENTIFIER;
	
	/**
	 * Name of the string substitution variable that resolves to the
	 * location of a local Ruby executable (value <code>rubyExecutable</code>).
	 */
	public static final String ID_RUBY_EXECUTABLE = "rubyExecutable";
	/**
	 * Launch configuration key. Value is a path to a ruby
	 * program. The path is a string representing a full path
	 * to a ruby program in the workspace. 
	 */
	public static final String ATTR_FILE_NAME = ID_RUBY_DEBUG_MODEL + ".ATTR_FILE_NAME";

	public static final String ID_RUBY_APPLICATION = "org.rubypeople.rdt.debug.core.launchConfigurationType1";
	
	public static final String ID_RUBY_SOURCE_LOCATOR = "org.rubypeople.rdt.debug.core.sourceLocator1";
}
