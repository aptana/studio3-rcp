package com.aptana.ruby.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class RubyCorePlugin extends Plugin
{

	// The plug-in ID
	public static final String PLUGIN_ID = "com.aptana.ruby.core"; //$NON-NLS-1$

	// The shared instance
	private static RubyCorePlugin plugin;

	/**
	 * The constructor
	 */
	public RubyCorePlugin()
	{
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception
	{
		try
		{
			// clean up
		}
		finally
		{
			plugin = null;
			super.stop(context);
		}
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static RubyCorePlugin getDefault()
	{
		return plugin;
	}

}
