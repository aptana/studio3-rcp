/**
 * Aptana Studio
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the GNU Public License (GPL) v3 (with exceptions).
 * Please see the license.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.rcp.tests.all;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ com.aptana.editor.coffee.tests.AllTests.class, com.aptana.editor.idl.AllTests.class,
		com.aptana.editor.markdown.tests.AllTests.class, com.aptana.editor.yaml.tests.AllTests.class, })
public class UITests
{

}
