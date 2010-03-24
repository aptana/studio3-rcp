/*******************************************************************************
 * Copyright (c) 2009, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.aptana.ruby.internal.debug.ui.breakpoints;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.ui.IDetailPane;
import org.eclipse.debug.ui.IDetailPaneFactory;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.aptana.ruby.internal.debug.core.breakpoints.RubyLineBreakpoint;

/**
 * Detail pane factory for Ruby breakpoints.
 * 
 * @since 1.0
 */
public class BreakpointDetailPaneFactory implements IDetailPaneFactory
{

	/**
	 * Maps pane IDs to names
	 */
	private Map<String, String> fNameMap;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.IDetailPaneFactory#getDetailPaneTypes(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@SuppressWarnings("unchecked")
	public Set getDetailPaneTypes(IStructuredSelection selection)
	{
		HashSet<String> set = new HashSet<String>();
		if (selection.size() == 1)
		{
			IBreakpoint b = (IBreakpoint) selection.getFirstElement();
			try
			{
				String type = b.getMarker().getType();
				if (RubyLineBreakpoint.RUBY_LINE_BREAKPOINT.equals(type))
				{
					set.add(LineBreakpointDetailPane.DETAIL_PANE_LINE_BREAKPOINT);
				}
				else
				{
					set.add(StandardBreakpointDetailPane.DETAIL_PANE_STANDARD);
				}
			}
			catch (CoreException e)
			{
			}
		}
		return set;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.IDetailPaneFactory#getDefaultDetailPane(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public String getDefaultDetailPane(IStructuredSelection selection)
	{
		if (selection.size() == 1)
		{
			IBreakpoint b = (IBreakpoint) selection.getFirstElement();
			try
			{
				String type = b.getMarker().getType();
				if (RubyLineBreakpoint.RUBY_LINE_BREAKPOINT.equals(type))
				{
					return LineBreakpointDetailPane.DETAIL_PANE_LINE_BREAKPOINT;
				}
				return StandardBreakpointDetailPane.DETAIL_PANE_STANDARD;
			}
			catch (CoreException e)
			{
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.IDetailPaneFactory#createDetailPane(java.lang.String)
	 */
	public IDetailPane createDetailPane(String paneID)
	{
		if (LineBreakpointDetailPane.DETAIL_PANE_LINE_BREAKPOINT.equals(paneID))
		{
			return new LineBreakpointDetailPane();
		}
		if (StandardBreakpointDetailPane.DETAIL_PANE_STANDARD.equals(paneID))
		{
			return new StandardBreakpointDetailPane();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.IDetailPaneFactory#getDetailPaneName(java.lang.String)
	 */
	public String getDetailPaneName(String paneID)
	{
		return getNameMap().get(paneID);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.IDetailPaneFactory#getDetailPaneDescription(java.lang.String)
	 */
	public String getDetailPaneDescription(String paneID)
	{
		return getNameMap().get(paneID);
	}

	private Map<String, String> getNameMap()
	{
		if (fNameMap == null)
		{
			fNameMap = new HashMap<String, String>();
			fNameMap.put(LineBreakpointDetailPane.DETAIL_PANE_LINE_BREAKPOINT,
					BreakpointMessages.BreakpointDetailPaneFactory_0);
			fNameMap.put(StandardBreakpointDetailPane.DETAIL_PANE_STANDARD,
					BreakpointMessages.StandardBreakpointDetailPane_0);
		}
		return fNameMap;
	}

}
