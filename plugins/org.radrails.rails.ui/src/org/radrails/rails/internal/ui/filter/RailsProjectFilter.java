/**
 * This file Copyright (c) 2005-2010 Aptana, Inc. This program is
 * dual-licensed under both the Aptana Public License and the GNU General
 * Public license. You may elect to use one or the other of these licenses.
 * 
 * This program is distributed in the hope that it will be useful, but
 * AS-IS and WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, TITLE, or
 * NONINFRINGEMENT. Redistribution, except as permitted by whichever of
 * the GPL or APL you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or modify this
 * program under the terms of the GNU General Public License,
 * Version 3, as published by the Free Software Foundation.  You should
 * have received a copy of the GNU General Public License, Version 3 along
 * with this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Aptana provides a special exception to allow redistribution of this file
 * with certain other free and open source software ("FOSS") code and certain additional terms
 * pursuant to Section 7 of the GPL. You may view the exception and these
 * terms on the web at http://www.aptana.com/legal/gpl/.
 * 
 * 2. For the Aptana Public License (APL), this program and the
 * accompanying materials are made available under the terms of the APL
 * v1.0 which accompanies this distribution, and is available at
 * http://www.aptana.com/legal/apl/.
 * 
 * You may view the GPL, Aptana's exception and additional terms, and the
 * APL in the file titled license.html at the root of the corresponding
 * plugin containing this source file.
 * 
 * Any modifications to this file must keep this entire header intact.
 */
package org.radrails.rails.internal.ui.filter;

import java.text.MessageFormat;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.Viewer;

import com.aptana.editor.html.contentassist.index.HTMLIndexConstants;
import com.aptana.editor.ruby.index.IRubyIndexConstants;
import com.aptana.explorer.ui.filter.PathFilter;

public class RailsProjectFilter extends PathFilter
{

	/**
	 * Answers whether the given element in the given viewer matches the filter pattern. This is a default
	 * implementation that will show a leaf element in the tree based on whether the provided filter text matches the
	 * text of the given element's text, or that of it's children (if the element has any). Subclasses may override this
	 * method.
	 * 
	 * @param viewer
	 *            the tree viewer in which the element resides
	 * @param element
	 *            the element in the tree to check for a match
	 * @return true if the element matches the filter pattern
	 */
	protected boolean isElementVisible(Viewer viewer, Object element)
	{
		// HACK Ignore tmp and vendor for Rails projects
		IResource resource = (IResource) element;
		String firstSegment = resource.getProjectRelativePath().segment(0);
		if (firstSegment.equals("tmp") || firstSegment.equals("vendor")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			return false;
		}
		return super.isElementVisible(viewer, element);
	}

	/**
	 * The pattern string for which this filter should select elements in the viewer.
	 * 
	 * @param patternString
	 */
	protected void setPattern(String patternString)
	{
		this.patternString = patternString;
		if (patternString == null || patternString.equals("")) //$NON-NLS-1$
		{
			regexp = null;
		}
		else
		{
			regexp = Pattern.compile(MessageFormat.format(
					"\\b({0}|{1})\\b", patternString, Inflector.pluralize(patternString))); //$NON-NLS-1$
		}
	}

	protected String createPatternFromResource(IResource resource)
	{
		String text = resource.getName();
		// Try and strip filename down to the resource name!
		if (text.endsWith("_controller.rb")) //$NON-NLS-1$
		{
			text = text.substring(0, text.indexOf("_controller")); //$NON-NLS-1$
			text = Inflector.singularize(text);
		}
		else if (text.endsWith("_controller_test.rb")) //$NON-NLS-1$
		{
			text = text.substring(0, text.indexOf("_controller_test.rb")); //$NON-NLS-1$
			text = Inflector.singularize(text);
		}
		else if (text.endsWith("_helper.rb")) //$NON-NLS-1$
		{
			text = text.substring(0, text.indexOf("_helper")); //$NON-NLS-1$
			text = Inflector.singularize(text);
		}
		else if (text.endsWith("_helper_test.rb")) //$NON-NLS-1$
		{
			text = text.substring(0, text.indexOf("_helper_test.rb")); //$NON-NLS-1$
			text = Inflector.singularize(text);
		}
		else if (text.endsWith("_test.rb")) //$NON-NLS-1$
		{
			text = text.substring(0, text.indexOf("_test.rb")); //$NON-NLS-1$
		}
		else if (text.endsWith("_spec.rb")) //$NON-NLS-1$
		{
			text = text.substring(0, text.indexOf("_spec.rb")); //$NON-NLS-1$
		}
		else if (text.endsWith(".yml")) //$NON-NLS-1$
		{
			IPath path = resource.getProjectRelativePath();
			if (path.segmentCount() >= 3 && path.segment(1).equals("fixtures")) //$NON-NLS-1$
			{
				text = text.substring(0, text.indexOf(".yml")); //$NON-NLS-1$
				text = Inflector.singularize(text);
			}
		}
		else if (text.endsWith(".rb")) //$NON-NLS-1$
		{
			text = text.substring(0, text.indexOf(".rb")); //$NON-NLS-1$
		}
		else
		{
			// We need to grab the full path, so we can determine the resource name!

			IPath path = resource.getProjectRelativePath();
			if (path.segmentCount() >= 3 && path.segment(1).equals("views")) //$NON-NLS-1$
			{
				text = path.segment(2);
				text = Inflector.singularize(text);
			}
		}
		return text;
	}

	protected String[] indexCategories()
	{
		return new String[] { HTMLIndexConstants.RESOURCE_CSS, HTMLIndexConstants.RESOURCE_JS,
				IRubyIndexConstants.REQUIRE };
	}

}
