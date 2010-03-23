/**
 * Copyright (c) 2005-2009 Aptana, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html. If redistributing this code,
 * this entire header must remain intact.
 */
package com.aptana.radrails.rcp;

import org.eclipse.osgi.util.NLS;

/**
 * Messages
 * 
 * @author Ingo Muschenetz
 */
public final class Messages extends NLS {

	private static final String BUNDLE_NAME = "com.aptana.radrails.rcp.messages"; //$NON-NLS-1$

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

	public static String IDEWorkbenchErrorHandler_KeybindingConflict;
}
