package org.radrails.rails.core;

import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.aptana.core.util.ExecutableUtil;
import com.aptana.core.util.ProcessUtil;
import com.aptana.ruby.launching.RubyLaunchingPlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class RailsCorePlugin extends Plugin
{

	/**
	 * The filename of the rails script.
	 */
	private static final String RAILS = "rails"; //$NON-NLS-1$

	// The plug-in ID
	public static final String PLUGIN_ID = "org.radrails.rails.core"; //$NON-NLS-1$

	// The shared instance
	private static RailsCorePlugin plugin;

	/**
	 * The constructor
	 */
	public RailsCorePlugin()
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
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static RailsCorePlugin getDefault()
	{
		return plugin;
	}

	public static Map<Integer, String> runRailsInBackground(IPath workingDirectory, String... args)
	{
		return runRailsInBackground(workingDirectory, null, args);
	}

	public static Map<Integer, String> runRailsInBackground(IPath workingDirectory, Map<String, String> environment,
			String... args)
	{
		IPath rubyExe = RubyLaunchingPlugin.rubyExecutablePath();
		IPath railsPath = ExecutableUtil.find(RAILS, false, null);
		String[] newArgs = new String[args.length + 1];
		newArgs[0] = (railsPath == null) ? RAILS : railsPath.toOSString();
		System.arraycopy(args, 0, newArgs, 1, args.length);
		return ProcessUtil.runInBackground(
				(rubyExe == null) ? "ruby" : rubyExe.toOSString(), workingDirectory, environment, newArgs); //$NON-NLS-1$
	}
}
