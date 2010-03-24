package com.aptana.ruby.internal.debug.core.breakpoints;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.Breakpoint;
import com.aptana.ruby.debug.core.RubyDebugModel;

public abstract class RubyBreakpoint extends Breakpoint
{
	/**
	 * Breakpoint attribute storing the expired value (value <code>"com.aptana.ruby.debug.core.expired"</code>). This
	 * attribute is stored as a <code>boolean</code>. Once a hit count has been reached, a breakpoint is considered to
	 * be "expired".
	 */
	protected static final String EXPIRED = "com.aptana.ruby.debug.core.expired"; //$NON-NLS-1$
	/**
	 * Breakpoint attribute storing a breakpoint's hit count value (value
	 * <code>"com.aptana.ruby.debug.core.hitCount"</code>). This attribute is stored as an <code>int</code>.
	 */
	protected static final String HIT_COUNT = "com.aptana.ruby.debug.core.hitCount"; //$NON-NLS-1$
	/**
	 * Breakpoint attribute storing the fully qualified name of the type this breakpoint is located in. (value
	 * <code>"com.aptana.ruby.debug.core.typeName"</code>). This attribute is a <code>String</code>.
	 */
	protected static final String TYPE_NAME = "com.aptana.ruby.debug.core.typeName"; //$NON-NLS-1$		

	/**
	 * Breakpoint attribute storing the number of debug targets a breakpoint is installed in (value
	 * <code>"com.aptana.ruby.debug.core.installCount"</code>). This attribute is a <code>int</code>.
	 */
	protected static final String INSTALL_COUNT = "com.aptana.ruby.debug.core.installCount"; //$NON-NLS-1$	

	/**
	 * Stores the type name that this breakpoint was last installed in. When a breakpoint is created, the TYPE_NAME
	 * attribute assigned to it is that of its top level enclosing type. When installed, the type may actually be an
	 * inner type. We need to keep track of the type type the breakpoint was installed in, in case we need to re-install
	 * the breakpoint for HCR (i.e. in case an inner type is HCR'd).
	 */
	protected String fInstalledTypeName = null;

	/**
	 * Sets the type name in which to install this breakpoint.
	 */
	protected void setTypeName(String typeName) throws CoreException
	{
		setAttribute(TYPE_NAME, typeName);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaBreakpoint#getTypeName()
	 */
	public String getTypeName() throws CoreException
	{
		if (fInstalledTypeName == null)
		{
			return ensureMarker().getAttribute(TYPE_NAME, null);
		}
		return fInstalledTypeName;
	}

	public String getModelIdentifier()
	{
		return RubyDebugModel.getModelIdentifier();
	}

	/**
	 * Add this breakpoint to the breakpoint manager, or sets it as unregistered.
	 */
	protected void register(boolean register) throws CoreException
	{
		DebugPlugin plugin = DebugPlugin.getDefault();
		if (plugin != null && register)
		{
			plugin.getBreakpointManager().addBreakpoint(this);
		}
		else
		{
			setRegistered(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.aptana.ruby.debug.core.IRubyBreakpoint#isInstalled()
	 */
	public boolean isInstalled() throws CoreException
	{
		return ensureMarker().getAttribute(INSTALL_COUNT, 0) > 0;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaBreakpoint#getHitCount()
	 */
	public int getHitCount() throws CoreException {
		return ensureMarker().getAttribute(HIT_COUNT, -1);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaBreakpoint#setHitCount(int)
	 */
	public void setHitCount(int count) throws CoreException {	
		if (getHitCount() != count) {
			if (!isEnabled() && count > -1) {
				setAttributes(new String []{ENABLED, HIT_COUNT, EXPIRED},
					new Object[]{Boolean.TRUE, new Integer(count), Boolean.FALSE});
			} else {
				setAttributes(new String[]{HIT_COUNT, EXPIRED},
					new Object[]{new Integer(count), Boolean.FALSE});
			}
			recreate();
		}
	}

	protected void recreate()
	{
		// TODO Remove and re-add to debug target?
//		DebugPlugin plugin = DebugPlugin.getDefault();
//		if (plugin != null)
//		{
//			plugin.getBreakpointManager().fireBreakpointChanged(this);
//		}
	}
}
