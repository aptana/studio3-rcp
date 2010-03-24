package com.aptana.ruby.debug.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IBreakpoint;

public interface IRubyBreakpoint extends IBreakpoint
{
	/**
	 * Returns the fully qualified name of the type this breakpoint is located in, or <code>null</code> if this
	 * breakpoint is not located in a specific type - for example, a pattern breakpoint.
	 * 
	 * @return the fully qualified name of the type this breakpoint is located in, or <code>null</code>
	 * @exception CoreException
	 *                if unable to access the property from this breakpoint's underlying marker
	 */
	public String getTypeName() throws CoreException;

	/**
	 * Returns whether this breakpoint is installed in at least one debug target.
	 * 
	 * @return whether this breakpoint is installed
	 * @exception CoreException
	 *                if unable to access the property on this breakpoint's underlying marker
	 */
	public boolean isInstalled() throws CoreException;

	/**
	 * Returns this breakpoint's hit count or, -1 if this
	 * breakpoint does not have a hit count.
	 * 
	 * @return this breakpoint's hit count, or -1
	 * @exception CoreException if unable to access the property
	 *  from this breakpoint's underlying marker
	 */
	public int getHitCount() throws CoreException;
	
	/**
	 * Sets the hit count attribute of this breakpoint.
	 * If this breakpoint is currently disabled and the hit count
	 * is set greater than -1, this breakpoint is automatically enabled.
	 * 
	 * @param count the new hit count
	 * @exception CoreException if unable to set the property
	 * 	on this breakpoint's underlying marker
	 */
	public void setHitCount(int count) throws CoreException;	

}
