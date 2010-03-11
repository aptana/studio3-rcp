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
package com.aptana.ruby.debug.ui;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Creates a toggle breakpoint adapter
 */
public class RubyBreakpointAdapterFactory implements IAdapterFactory
{
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType)
	{
		RubyLineBreakpointAdapter adapter = new RubyLineBreakpointAdapter();
		if (adaptableObject instanceof IWorkbenchPart)
		{
			if (adapter.getEditor((IWorkbenchPart) adaptableObject) != null)
				return adapter;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList()
	{
		return new Class[] { IToggleBreakpointsTarget.class };
	}
}
