/*
 * Author: Markus Barchfeld
 * 
 * Copyright (c) 2005 RubyPeople.
 * 
 * This file is part of the Ruby Development Tools (RDT) plugin for eclipse. RDT is
 * subject to the "Common Public License (CPL) v 1.0". You may not use RDT except in 
 * compliance with the License. For further information see org.rubypeople.rdt/rdt.license.
 */

package org.rubypeople.rdt.internal.debug.core.breakpoints;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.model.IBreakpoint;
import org.rubypeople.rdt.debug.core.model.IRubyExceptionBreakpoint;

public class RubyExceptionBreakpoint extends RubyBreakpoint implements IRubyExceptionBreakpoint
{
	// TODO Move this constant to some public interface...
	private static final String RUBY_EXCEPTION_BREAKPOINT = "org.rubypeople.rdt.debug.core.rubyExceptionBreakpointMarker"; //$NON-NLS-1$

	public RubyExceptionBreakpoint(final IResource resource, final String exception, final boolean add,
			final Map<String, Object> attributes) throws CoreException
	{
		// we need to have a resource, because the marker needs it (BTW: why the hell do
		// we need a marker for?) Possible Answer: so that changes can be detected and
		// propagated to the DebugTarget in the same manner as for Line Breakpoints?
		// The workspace root is chosen because JavaExceptionsBreakpoints do so as well
		IWorkspaceRunnable wr = new IWorkspaceRunnable()
		{

			public void run(IProgressMonitor monitor) throws CoreException
			{
				// create the marker
				setMarker(resource.createMarker(RUBY_EXCEPTION_BREAKPOINT));

				// add attributes
				attributes.put(IBreakpoint.ID, getModelIdentifier());
				attributes.put(TYPE_NAME, exception);
				attributes.put(ENABLED, Boolean.TRUE);
				// attributes.put(CAUGHT, Boolean.valueOf(caught));
				// attributes.put(UNCAUGHT, Boolean.valueOf(uncaught));
				// attributes.put(CHECKED, Boolean.valueOf(checked));
				// attributes.put(SUSPEND_POLICY, new Integer(getDefaultSuspendPolicy()));

				ensureMarker().setAttributes(attributes);

				register(add);
			}

		};
		run(getMarkerRule(resource), wr);
	}
}
