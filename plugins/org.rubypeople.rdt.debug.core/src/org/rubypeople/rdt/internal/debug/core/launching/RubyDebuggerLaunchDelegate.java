/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Bjorn Freeman-Benson - initial API and implementation
 *******************************************************************************/
package org.rubypeople.rdt.internal.debug.core.launching;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.rubypeople.rdt.debug.core.RubyDebugCorePlugin;
import org.rubypeople.rdt.debug.core.launching.IRubyLaunchConfigurationConstants;
import org.rubypeople.rdt.internal.debug.core.RubyDebuggerProxy;
import org.rubypeople.rdt.internal.debug.core.model.RubyDebugTarget;
import org.rubypeople.rdt.internal.debug.core.model.RubyProcessingException;

/**
 * Launches Ruby program on a Ruby interpreter
 */
public class RubyDebuggerLaunchDelegate extends LaunchConfigurationDelegate
{
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration,
	 * java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException
	{
		List<String> commandList = new ArrayList<String>();

		// Ruby executable
		// FIXME This needs to search for ruby binary!
		String path = "/usr/bin/ruby";
		// if (path == null)
		// {
		// abort("Ruby executable location unspecified. Check value of ${rubyExecutable}.", null);
		// }
		File exe = new File(path);
		if (!exe.exists())
		{
			abort(MessageFormat.format(
					"Specified Ruby executable {0} does not exist. Check value of ${rubyExecutable}.", path), null);
		}
		commandList.add(path);

		// TODO VM Args go here...

		commandList.add("--"); //$NON-NLS-1$

		int port = -1;
		if (mode.equals(ILaunchManager.DEBUG_MODE))
		{
			port = findFreePort();
			if (port == -1)
			{
				abort("Unable to find free port", null);
			}
			// RDebug-ide
			// FIXME Grab location of bin script by searching or from pref value!
			commandList.add("/Users/cwilliams/.gem/ruby/1.8/bin/rdebug-ide");
			commandList.add("--port"); //$NON-NLS-1$
			commandList.add(Integer.toString(port));
			commandList.add("--"); //$NON-NLS-1$
		}

		// file we're debugging/running
		String program = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_FILE_NAME, (String) null);
		if (program == null)
		{
			abort("Ruby program unspecified.", null);
		}
		File file = new File(program);
		if (!file.exists())
		{
			abort(MessageFormat.format("Ruby program {0} does not exist.", program), null);
		}
		commandList.add(program);

		String host = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_FILE_NAME,
				IRubyLaunchConfigurationConstants.DEFAULT_REMOTE_HOST);

		// Now actually launch the process!
		Process process = DebugPlugin.exec(commandList.toArray(new String[commandList.size()]),
				getWorkingDirectory(configuration), getEnvironment(configuration));
		IProcess p = DebugPlugin.newProcess(launch, process, path);
		if (mode.equals(ILaunchManager.DEBUG_MODE))
		{
			RubyDebugTarget target = new RubyDebugTarget(launch, host, port);
			target.setProcess(p);
			RubyDebuggerProxy proxy = new RubyDebuggerProxy(target, true);
			try
			{
				proxy.start();
				launch.addDebugTarget(target);
			}
			catch (RubyProcessingException e)
			{
				RubyDebugCorePlugin.log(e);
				target.terminate();
			}
			catch (IOException e)
			{
				RubyDebugCorePlugin.log(e);
				target.terminate();
			}
		}
	}

	private String[] getEnvironment(ILaunchConfiguration configuration) throws CoreException
	{
		return DebugPlugin.getDefault().getLaunchManager().getEnvironment(configuration);
	}

	/**
	 * Return a File pointing at the working directory for the launch. Return null if no value specified, or specified
	 * location does not exist or is not a directory.
	 * 
	 * @param configuration
	 * @return
	 * @throws CoreException
	 */
	protected File getWorkingDirectory(ILaunchConfiguration configuration) throws CoreException
	{
		String workingDirVal = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY,
				(String) null);
		if (workingDirVal == null)
			return null;
		File workingDirectory = new File(workingDirVal);
		if (!workingDirectory.isDirectory())
			return null;
		return workingDirectory;
	}

	/**
	 * Throws an exception with a new status containing the given message and optional exception.
	 * 
	 * @param message
	 *            error message
	 * @param e
	 *            underlying exception
	 * @throws CoreException
	 */
	private void abort(String message, Throwable e) throws CoreException
	{
		throw new CoreException(new Status(IStatus.ERROR, IRubyLaunchConfigurationConstants.ID_RUBY_DEBUG_MODEL, 0,
				message, e));
	}

	/**
	 * Returns a free port number on localhost, or -1 if unable to find a free port.
	 * 
	 * @return a free port number on localhost, or -1 if unable to find a free port
	 */
	public static int findFreePort()
	{
		ServerSocket socket = null;
		try
		{
			socket = new ServerSocket(0);
			return socket.getLocalPort();
		}
		catch (IOException e)
		{
		}
		finally
		{
			if (socket != null)
			{
				try
				{
					socket.close();
				}
				catch (IOException e)
				{
				}
			}
		}
		return -1;
	}
}
