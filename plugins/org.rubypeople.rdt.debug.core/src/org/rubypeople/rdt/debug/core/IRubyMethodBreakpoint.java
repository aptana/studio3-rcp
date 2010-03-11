package org.rubypeople.rdt.debug.core;

import org.eclipse.core.runtime.CoreException;

public interface IRubyMethodBreakpoint extends IRubyLineBreakpoint
{
	/**
	 * Returns the name of the method(s) this breakpoint suspends execution in, or <code>null</code> if this breakpoint
	 * does not suspend execution based on method name.
	 * 
	 * @return the name of the method(s) this breakpoint suspends execution in, or <code>null</code> if this breakpoint
	 *         does not suspend execution based on method name
	 * @exception CoreException
	 *                if unable to access the property from this breakpoint's underlying marker
	 */
	public String getMethodName() throws CoreException;

	/**
	 * Returns the pattern specifying the fully qualified name of type(s) this breakpoint suspends execution in.
	 * Patterns are limited to exact matches and patterns that begin or end with '*'.
	 * 
	 * @return the pattern specifying the fully qualified name of type(s) this breakpoint suspends execution in
	 * @exception CoreException
	 *                if unable to access the property from this breakpoint's underlying marker
	 * @see IRubyBreakpoint#getTypeName()
	 */
	public String getTypeName() throws CoreException;
}
