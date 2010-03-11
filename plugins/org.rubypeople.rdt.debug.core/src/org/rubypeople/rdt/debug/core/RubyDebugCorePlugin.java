package org.rubypeople.rdt.debug.core;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public class RubyDebugCorePlugin extends Plugin
{

	public static final String PLUGIN_ID = "org.rubypeople.rdt.debug.core"; //$NON-NLS-1$

	/**
	 * Status code indicating an unexpected internal error.
	 */
	public static final int INTERNAL_ERROR = 120;

	private static boolean isRubyDebuggerVerbose = false;

	protected static RubyDebugCorePlugin plugin;

	public RubyDebugCorePlugin()
	{
		super();
	}

	public static Plugin getDefault()
	{
		return plugin;
	}

	public static IWorkspace getWorkspace()
	{
		return ResourcesPlugin.getWorkspace();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception
	{
		plugin = this;
		super.start(context);
		String rubyDebuggerVerboseOption = Platform.getDebugOption(RubyDebugCorePlugin.PLUGIN_ID
				+ "/rubyDebuggerVerbose");
		isRubyDebuggerVerbose = rubyDebuggerVerboseOption == null ? false : rubyDebuggerVerboseOption
				.equalsIgnoreCase("true");
	}

	public static void log(int severity, String message)
	{
		Status status = new Status(severity, PLUGIN_ID, IStatus.OK, message, null);
		RubyDebugCorePlugin.log(status);
	}

	public static void log(String message, Throwable e)
	{
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, message, e));
	}

	public static void log(IStatus status)
	{
		if (RubyDebugCorePlugin.getDefault() != null)
		{
			getDefault().getLog().log(status);
		}
		else
		{
			System.out.println("Error: ");
			System.out.println(status.getMessage());
		}
	}

	public static void log(Throwable e)
	{
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, "RdtLaunchingPlugin.internalErrorOccurred", e)); //$NON-NLS-1$
	}

	public static void debug(Object message)
	{
		if (RubyDebugCorePlugin.getDefault() != null)
		{
			if (RubyDebugCorePlugin.getDefault().isDebugging())
			{
				System.out.println(message.toString());
			}

		}
		else
		{
			// Called from Unit-Test, Plugin not initialized
			System.out.println(message.toString());
		}
	}

	public static void debug(String message, Throwable e)
	{
		if (RubyDebugCorePlugin.getDefault() != null)
		{
			if (RubyDebugCorePlugin.getDefault().isDebugging())
			{
				System.out.println(message + ", Exception: " + e.getMessage());
				RubyDebugCorePlugin.log(e);
			}

		}
		else
		{
			// Called from Unit-Test, Plugin not initialized
			System.out.println(message);
			e.printStackTrace();
		}

	}

	public static boolean isRubyDebuggerVerbose()
	{
		return isRubyDebuggerVerbose;
	}

	public static String getPluginIdentifier()
	{
		return PLUGIN_ID;
	}
}