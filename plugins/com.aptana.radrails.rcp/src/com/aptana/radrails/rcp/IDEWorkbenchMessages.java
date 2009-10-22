/**
 * Copyright (c) 2005-2008 Aptana, Inc.
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
 * 
 * @author Ingo Muschenetz
 *
 */
public final class IDEWorkbenchMessages extends NLS
{
	private static final String BUNDLE_NAME = "com.aptana.radrails.rcp.messages"; //$NON-NLS-1$

	private IDEWorkbenchMessages()
	{
	}

	static
	{
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, IDEWorkbenchMessages.class);
	}
	
	/**
	 * AbstractIDEApplication_CouldNotObtainWorkspaceLock
	 */
	public static String AbstractIDEApplication_CouldNotObtainWorkspaceLock;
	
	/**
	 * AbstractIDEApplication_IncompatibleJVM
	 */
	public static String AbstractIDEApplication_IncompatibleJVM;
	
	/**
	 * AbstractIDEApplication_JVMVersionIsIncompatible
	 */
	public static String AbstractIDEApplication_JVMVersionIsIncompatible;

	public static String IDEApplication_Unknown_Run_State;

	public static String IDEApplication_versionMessage;

	public static String IDEApplication_versionTitle;

	public static String IDEApplication_workspaceCannotBeSetMessage;

	public static String IDEApplication_workspaceCannotBeSetTitle;

	public static String IDEApplication_workspaceCannotLockMessage;

	public static String IDEApplication_workspaceCannotLockTitle;

	public static String IDEApplication_workspaceEmptyMessage;

	public static String IDEApplication_workspaceEmptyTitle;

	public static String IDEApplication_workspaceInUseMessage;

	public static String IDEApplication_workspaceInUseTitle;

	public static String IDEApplication_workspaceInvalidMessage;

	public static String IDEApplication_workspaceInvalidTitle;

	public static String IDEApplication_workspaceMandatoryMessage;

	public static String IDEApplication_workspaceMandatoryTitle;

	public static String InternalError;
}
