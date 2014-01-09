/**
 * Aptana Studio
 * Copyright (c) 2005-2012 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the GNU Public License (GPL) v3 (with exceptions).
 * Please see the license.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.editor.yaml.outline;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import junit.framework.TestCase;

import com.aptana.editor.yaml.YAMLPlugin;
import com.aptana.editor.yaml.parsing.YAMLParser;
import com.aptana.parsing.ParseState;
import com.aptana.parsing.ast.IParseRootNode;

public class YAMLOutlineProviderTest
{

	private YAMLOutlineContentProvider fContentProvider;
	private YAMLOutlineLabelProvider fLabelProvider;
	private YAMLParser fParser;
	private ParseState fParseState;

//	@Override
	@Before
	public void setUp() throws Exception
	{
		fContentProvider = new YAMLOutlineContentProvider();
		fLabelProvider = new YAMLOutlineLabelProvider();
		fParser = new YAMLParser();
	}

//	@Override
	@After
	public void tearDown() throws Exception
	{
		fContentProvider = null;
		fLabelProvider = null;
		fParser = null;
	}

	@Test
	public void testString() throws Exception
	{
		String source = "string : text";
		fParseState = new ParseState(source);
		IParseRootNode result = fParser.parse(fParseState).getRootNode();

		Object[] children = fContentProvider.getChildren(result);
		assertEquals(1, children.length);
		assertEquals("string", fLabelProvider.getText(children[0]));
		assertEquals(YAMLPlugin.getImage("icons/property.png"), fLabelProvider.getImage(children[0]));

		Object[] grandchildren = fContentProvider.getChildren(children[0]);
		assertEquals(1, grandchildren.length);
		assertEquals("text", fLabelProvider.getText(grandchildren[0]));
		assertEquals(YAMLPlugin.getImage("icons/string.png"), fLabelProvider.getImage(grandchildren[0]));
	}

	@Test
	public void testNumber() throws Exception
	{
		String source = "number : 12345";
		fParseState = new ParseState(source);
		IParseRootNode result = fParser.parse(fParseState).getRootNode();

		Object[] children = fContentProvider.getChildren(result);
		assertEquals(1, children.length);
		assertEquals("number", fLabelProvider.getText(children[0]));
		assertEquals(YAMLPlugin.getImage("icons/property.png"), fLabelProvider.getImage(children[0]));

		Object[] grandchildren = fContentProvider.getChildren(children[0]);
		assertEquals(1, grandchildren.length);
		assertEquals("12345", fLabelProvider.getText(grandchildren[0]));
		assertEquals(YAMLPlugin.getImage("icons/number.png"), fLabelProvider.getImage(grandchildren[0]));
	}
}
