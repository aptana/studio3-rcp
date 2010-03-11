package org.rubypeople.rdt.debug.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.rubypeople.rdt.debug.core.launching.IRubyLaunchConfigurationConstants;
import org.rubypeople.rdt.debug.core.model.IRubyExceptionBreakpoint;
import org.rubypeople.rdt.internal.debug.core.breakpoints.RubyExceptionBreakpoint;
import org.rubypeople.rdt.internal.debug.core.breakpoints.RubyLineBreakpoint;
import org.rubypeople.rdt.internal.debug.core.breakpoints.RubyMethodBreakpoint;

public class RubyDebugModel
{

	/**
	 * Creates and returns an exception breakpoint for an exception with the given name. The marker associated with the
	 * breakpoint will be created on the specified resource. Caught and uncaught specify where the exception should
	 * cause thread suspensions - that is, in caught and/or uncaught locations. Checked indicates if the given exception
	 * is a checked exception.
	 * 
	 * @param resource
	 *            the resource on which to create the associated breakpoint marker
	 * @param exceptionName
	 *            the fully qualified name of the exception for which to create the breakpoint
	 * @param caught
	 *            whether to suspend in caught locations
	 * @param uncaught
	 *            whether to suspend in uncaught locations
	 * @param checked
	 *            whether the exception is a checked exception (i.e. compiler detected)
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @param attributes
	 *            a map of client defined attributes that should be assigned to the underlying breakpoint marker on
	 *            creation or <code>null</code> if none.
	 * @return an exception breakpoint
	 * @exception CoreException
	 *                If this method fails. Reasons include:
	 *                <ul>
	 *                <li>Failure creating underlying marker. The exception's status contains the underlying exception
	 *                responsible for the failure.</li>
	 *                </ul>
	 */
	public static IRubyExceptionBreakpoint createExceptionBreakpoint(IResource resource, String exceptionName,
			boolean register, Map<String, Object> attributes) throws CoreException
	{
		if (attributes == null)
		{
			attributes = new HashMap<String, Object>(10);
		}
		return new RubyExceptionBreakpoint(resource, exceptionName, register, attributes);
	}

	/**
	 * Creates and returns a line breakpoint in the type with the given name, at the given line number. The marker
	 * associated with the breakpoint will be created on the specified resource. If a character range within the line is
	 * known, it may be specified by charStart/charEnd. If hitCount is > 0, the breakpoint will suspend execution when
	 * it is "hit" the specified number of times.
	 * 
	 * @param resource
	 *            the resource on which to create the associated breakpoint marker
	 * @param typeName
	 *            the fully qualified name of the type the breakpoint is to be installed in. If the breakpoint is to be
	 *            installed in an inner type, it is sufficient to provide the name of the top level enclosing type. If
	 *            an inner class name is specified, it should be formatted as the associated class file name (i.e. with
	 *            <code>$</code>). For example, <code>example.SomeClass$InnerType</code>, could be specified, but
	 *            <code>example.SomeClass</code> is sufficient.
	 * @param lineNumber
	 *            the lineNumber on which the breakpoint is set - line numbers are 1 based, associated with the source
	 *            file in which the breakpoint is set
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @param attributes
	 *            a map of client defined attributes that should be assigned to the underlying breakpoint marker on
	 *            creation, or <code>null</code> if none.
	 * @return a line breakpoint
	 * @exception CoreException
	 *                If this method fails. Reasons include:
	 *                <ul>
	 *                <li>Failure creating underlying marker. The exception's status contains the underlying exception
	 *                responsible for the failure.</li>
	 *                </ul>
	 */
	public static IRubyLineBreakpoint createLineBreakpoint(IResource resource, String fileName, String typeName,
			int lineNumber, boolean register, Map<String, Object> attributes) throws CoreException
	{
		if (attributes == null)
		{
			attributes = new HashMap<String, Object>(10);
		}
		return new RubyLineBreakpoint(resource, fileName, typeName, lineNumber, -1, -1, 0, register, attributes);
	}

	/**
	 * Returns a Ruby line breakpoint that is already registered with the breakpoint manager for a type with the given
	 * name at the given line number in the given resource.
	 * 
	 * @param resource
	 *            the resource
	 * @param typeName
	 *            fully qualified type name
	 * @param lineNumber
	 *            line number
	 * @return a Ruby line breakpoint that is already registered with the breakpoint manager for a type with the given
	 *         name at the given line number or <code>null</code> if no such breakpoint is registered
	 * @exception CoreException
	 *                if unable to retrieve the associated marker attributes (line number).
	 */
	public static IRubyLineBreakpoint lineBreakpointExists(IResource resource, String typeName, int lineNumber)
			throws CoreException
	{
		if (resource == null)
			return null;
		String modelId = getModelIdentifier();
		String markerType = RubyLineBreakpoint.getMarkerType();
		IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint[] breakpoints = manager.getBreakpoints(modelId);
		for (int i = 0; i < breakpoints.length; i++)
		{
			if (!(breakpoints[i] instanceof IRubyLineBreakpoint))
			{
				continue;
			}
			IRubyLineBreakpoint breakpoint = (IRubyLineBreakpoint) breakpoints[i];
			if (breakpoint == null)
				continue;
			IMarker marker = breakpoint.getMarker();
			if (marker != null && marker.exists() && marker.getType().equals(markerType))
			{
				String breakpointTypeName = breakpoint.getTypeName();
				if (equals(breakpointTypeName, typeName) && breakpoint.getLineNumber() == lineNumber
						&& resource.equals(marker.getResource()))
				{
					return breakpoint;
				}
			}
		}
		return null;
	}

	private static boolean equals(String breakpointTypeName, String typeName)
	{
		if (breakpointTypeName == null)
			return typeName == null;
		return breakpointTypeName.equals(typeName);
	}

	/**
	 * Returns the identifier for the Ruby debug model plug-in
	 * 
	 * @return plug-in identifier
	 */
	public static String getModelIdentifier()
	{
		return IRubyLaunchConfigurationConstants.ID_RUBY_DEBUG_MODEL;
	}

	/**
	 * Creates and returns a method breakpoint with the specified criteria.
	 * 
	 * @param resource
	 *            the resource on which to create the associated breakpoint marker
	 * @param typePattern
	 *            the pattern specifying the fully qualified name of type(s) this breakpoint suspends execution in.
	 *            Patterns are limited to exact matches and patterns that begin or end with '*'.
	 * @param methodName
	 *            the name of the method(s) this breakpoint suspends execution in, or <code>null</code> if this
	 *            breakpoint does not suspend execution based on method name
	 * @param entry
	 *            whether this breakpoint causes execution to suspend on entry of methods
	 * @param exit
	 *            whether this breakpoint causes execution to suspend on exit of methods
	 * @param lineNumber
	 *            the lineNumber on which the breakpoint is set - line numbers are 1 based, associated with the source
	 *            file in which the breakpoint is set
	 * @param charStart
	 *            the first character index associated with the breakpoint, or -1 if unspecified, in the source file in
	 *            which the breakpoint is set
	 * @param charEnd
	 *            the last character index associated with the breakpoint, or -1 if unspecified, in the source file in
	 *            which the breakpoint is set
	 * @param hitCount
	 *            the number of times the breakpoint will be hit before suspending execution - 0 if it should always
	 *            suspend
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @param attributes
	 *            a map of client defined attributes that should be assigned to the underlying breakpoint marker on
	 *            creation, or <code>null</code> if none.
	 * @return a method breakpoint
	 * @exception CoreException
	 *                If this method fails. Reasons include:
	 *                <ul>
	 *                <li>Failure creating underlying marker. The exception's status contains the underlying exception
	 *                responsible for the failure.</li>
	 *                </ul>
	 */
	public static IRubyMethodBreakpoint createMethodBreakpoint(IResource resource, String typePattern,
			String methodName, boolean entry, boolean exit, int lineNumber, int charStart, int charEnd, int hitCount,
			boolean register, Map<String, Object> attributes) throws CoreException
	{
		if (attributes == null)
		{
			attributes = new HashMap<String, Object>(10);
		}
		return new RubyMethodBreakpoint(resource, typePattern, methodName, entry, exit, lineNumber, charStart, charEnd,
				hitCount, register, attributes);
	}
}
