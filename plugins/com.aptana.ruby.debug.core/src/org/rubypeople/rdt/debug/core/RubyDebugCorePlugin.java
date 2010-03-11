package org.rubypeople.rdt.debug.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public class RubyDebugCorePlugin extends Plugin
{

	public static final String PLUGIN_ID = "com.aptana.ruby.debug.core"; //$NON-NLS-1$

	protected static RubyDebugCorePlugin plugin;

	public RubyDebugCorePlugin()
	{
		super();
	}

	public static Plugin getDefault()
	{
		return plugin;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception
	{
		plugin = null;
		super.stop(context);
	}

	public static void log(int severity, String message)
	{
		log(new Status(severity, PLUGIN_ID, IStatus.OK, message, null));
	}

	public static void log(String message, Throwable e)
	{
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, message, e));
	}

	private static void log(IStatus status)
	{
		if (RubyDebugCorePlugin.getDefault() != null)
		{
			getDefault().getLog().log(status);
		}
		else
		{
			System.out.println("Error: "); //$NON-NLS-1$
			System.out.println(status.getMessage());
		}
	}

	public static void log(Throwable e)
	{
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, e.getMessage(), e));
	}

	public static void debug(Object message)
	{
		if (RubyDebugCorePlugin.getDefault() != null && RubyDebugCorePlugin.getDefault().isDebugging())
		{
			System.out.println(message.toString());
		}
		else
		{
			// Called from Unit-Test, Plugin not initialized
			System.out.println(message.toString());
		}
	}

	public static void debug(String message, Throwable e)
	{
		if (RubyDebugCorePlugin.getDefault() != null && RubyDebugCorePlugin.getDefault().isDebugging())
		{
			System.out.println(message + ", Exception: " + e.getMessage()); //$NON-NLS-1$
			RubyDebugCorePlugin.log(e);
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
		String rubyDebuggerVerboseOption = Platform.getDebugOption(RubyDebugCorePlugin.PLUGIN_ID
				+ "/rubyDebuggerVerbose"); //$NON-NLS-1$
		return rubyDebuggerVerboseOption == null ? false : rubyDebuggerVerboseOption.equalsIgnoreCase(Boolean.TRUE
				.toString());
	}

	public static String getPluginIdentifier()
	{
		return PLUGIN_ID;
	}
}