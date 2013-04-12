/**
 * Aptana Studio
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the GNU Public License (GPL) v3 (with exceptions).
 * Please see the license.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.rcp.tests.all;

import junit.framework.Test;
import junit.framework.TestSuite;

public class UITests
{

	public static Test suite()
	{
		TestSuite suite = new TestSuite(UITests.class.getName());
		// $JUnit-BEGIN$
		suite.addTest(com.aptana.editor.coffee.tests.AllTests.suite());
		suite.addTest(com.aptana.editor.idl.AllTests.suite());
		suite.addTest(com.aptana.editor.markdown.tests.AllTests.suite());
		suite.addTest(com.aptana.editor.yaml.tests.AllTests.suite());
		// $JUnit-END$
		return suite;
	}

}
