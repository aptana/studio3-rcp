/**
 * Aptana Studio
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the GNU Public License (GPL) v3 (with exceptions).
 * Please see the license.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.rcp.tests.all;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.aptana.core.logging.IdeLog;
import com.aptana.core.logging.IdeLog.StatusLevel;
import com.aptana.core.tests.StdErrLoggingSuite;
import com.aptana.core.util.EclipseUtil;

@RunWith(StdErrLoggingSuite.class)
// @formatter:off
@Suite.SuiteClasses({ 
	com.aptana.editor.coffee.tests.IntegrationTests.class,
})
// @formatter:on
public class UIIntegrationTests
{
	/**
	 * We turn logging level to INFO and turn on debugging for everything (basically setting logging to TRACE)
	 */
	@BeforeClass
	public static void turnUpLogging()
	{
		System.err.println("Turning logging to INFO");
		IdeLog.setCurrentSeverity(StatusLevel.INFO);
		System.err.println("Turning on all debug options");
		String[] currentOptions = EclipseUtil.getCurrentDebuggableComponents();
		EclipseUtil.setBundleDebugOptions(currentOptions, true);
		System.err.println("Turning on platform debugging flag");
		EclipseUtil.setPlatformDebugging(true);
	}
}
