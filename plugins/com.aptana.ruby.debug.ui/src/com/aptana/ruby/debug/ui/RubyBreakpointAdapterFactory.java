package com.aptana.ruby.debug.ui;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Creates a toggle breakpoint adapter
 */
@SuppressWarnings("rawtypes")
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
			if (adapter.getFileStore((IWorkbenchPart) adaptableObject) != null)
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
