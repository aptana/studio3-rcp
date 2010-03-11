package com.aptana.ruby.internal.debug.core.launching;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;

import com.aptana.ruby.debug.core.launching.IRubyLaunchConfigurationConstants;
import com.aptana.ruby.internal.debug.core.RubyDebuggerProxy;
import com.aptana.ruby.internal.debug.core.model.RubyDebugTarget;
import com.aptana.ruby.internal.debug.core.model.RubyProcessingException;

public class RemoteRubyDebuggerLaunchDelegate extends LaunchConfigurationDelegate
{

	/*
	 * (non-Rubydoc)
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration,
	 * java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException
	{

		if (monitor == null)
		{
			monitor = new NullProgressMonitor();
		}

		// check for cancellation
		if (monitor.isCanceled())
		{
			return;
		}
		try
		{
			// connect to remote VM
			connect(configuration, monitor, launch);

			// check for cancellation
			if (monitor.isCanceled())
			{
				IDebugTarget[] debugTargets = launch.getDebugTargets();
				for (int i = 0; i < debugTargets.length; i++)
				{
					IDebugTarget target = debugTargets[i];
					if (target.canDisconnect())
					{
						target.disconnect();
					}
				}
				return;
			}
		}
		finally
		{
			monitor.done();
		}
	}

	private void connect(ILaunchConfiguration configuration, IProgressMonitor monitor, ILaunch launch)
			throws CoreException
	{
		if (monitor == null)
		{
			monitor = new NullProgressMonitor();
		}

		IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask(Messages.SocketAttachConnector_Connecting____1, 2);
		subMonitor.subTask(Messages.SocketAttachConnector_Configuring_connection____1);

		String portNumberString = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_REMOTE_PORT,
				(String) null);
		if (portNumberString == null)
		{
			abort(Messages.SocketAttachConnector_Port_unspecified_for_remote_connection__2, null,
					IRubyLaunchConfigurationConstants.ERR_UNSPECIFIED_PORT);
		}
		int port = Integer.parseInt(portNumberString);
		String host = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_REMOTE_HOST, (String) null);
		if (host == null)
		{
			abort(Messages.SocketAttachConnector_Hostname_unspecified_for_remote_connection__4, null,
					IRubyLaunchConfigurationConstants.ERR_UNSPECIFIED_HOSTNAME);
		}

		subMonitor.worked(1);
		subMonitor.subTask(Messages.SocketAttachConnector_Establishing_connection____2);

		try
		{
			RubyDebugTarget debugTarget = new RubyDebugTarget(launch, host, port);
			RubyDebuggerProxy proxy = new RubyDebuggerProxy(debugTarget, true);
			proxy.start();
			launch.addDebugTarget(debugTarget);
			subMonitor.worked(1);
			subMonitor.done();
		}
		catch (IOException e)
		{
			abort(Messages.SocketAttachConnector_Failed_to_connect_to_remote_VM_1, e,
					IRubyLaunchConfigurationConstants.ERR_REMOTE_VM_CONNECTION_FAILED);
		}
		catch (RubyProcessingException e)
		{
			abort(Messages.SocketAttachConnector_Failed_to_connect_to_remote_VM_1, e,
					IRubyLaunchConfigurationConstants.ERR_REMOTE_VM_CONNECTION_FAILED);
		}
	}

	/**
	 * Throws an exception with a new status containing the given message and optional exception.
	 * 
	 * @param message
	 *            error message
	 * @param e
	 *            underlying exception
	 * @param errorCode
	 * @throws CoreException
	 */
	private void abort(String message, Throwable e, int errorCode) throws CoreException
	{
		throw new CoreException(new Status(IStatus.ERROR, IRubyLaunchConfigurationConstants.ID_RUBY_DEBUG_MODEL,
				errorCode, message, e));
	}
}
