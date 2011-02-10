/**
 * Aptana Studio
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Eclipse Public License (EPL).
 * Please see the license-epl.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.rcp;

import org.eclipse.osgi.util.NLS;

/**
 * Messages
 * 
 * @author Ingo Muschenetz
 */
public final class Messages extends NLS {

	private static final String BUNDLE_NAME = "com.aptana.rcp.messages"; //$NON-NLS-1$

	private Messages() {
	}

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	/**
	 * ApplicationWorkbenchAdvisor_ErrorGettingCurrentEditorReferences
	 */
	public static String ApplicationWorkbenchAdvisor_ErrorGettingCurrentEditorReferences;

	public static String InstallCommandLineExecutableJob_JobName;

	public static String PreferenceInitializer_Cannot_Set_Default_Encoding;
}
