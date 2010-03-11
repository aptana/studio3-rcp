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
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
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

import com.aptana.util.ExecutableUtil;

/**
 * Launches Ruby program on a Ruby interpreter
 */
public class RubyDebuggerLaunchDelegate extends LaunchConfigurationDelegate
{

	private static final String DEBUGGER_PORT_SWITCH = "--port"; //$NON-NLS-1$
	/**
	 * Switch/arguments that tells ruby/debugger that we're done passing switches/arguments to it.
	 */
	private static final String END_OF_ARGUMENTS_DELIMETER = "--"; //$NON-NLS-1$
	private static final String RUBYW = "rubyw"; //$NON-NLS-1$
	private static final String RUBY = "ruby"; //$NON-NLS-1$

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
		// Ruby binary
		String exe = rubyExecutable();
		commandList.add(exe);
		// Arguments to ruby
		commandList.addAll(interpreterArguments(configuration));

		// Set up debugger
		String host = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_REMOTE_HOST,
				IRubyLaunchConfigurationConstants.DEFAULT_REMOTE_HOST);
		int port = -1;
		if (mode.equals(ILaunchManager.DEBUG_MODE))
		{
			// TODO Grab port from configuration?
			port = findFreePort();
			if (port == -1)
			{
				abort("Unable to find free port", null);
			}
			commandList.addAll(debugArguments(exe, host, port, configuration));
		}
		// File to run
		commandList.add(fileToLaunch(configuration));
		// Args to file
		commandList.addAll(programArguments(configuration));

		// Now actually launch the process!
		Process process = DebugPlugin.exec(commandList.toArray(new String[commandList.size()]),
				getWorkingDirectory(configuration), getEnvironment(configuration));

		// FIXME Build a label from args?
		String label = commandList.get(0);

		IProcess p = DebugPlugin.newProcess(launch, process, label);
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

	private Collection<? extends String> programArguments(ILaunchConfiguration configuration) throws CoreException
	{
		List<String> commandList = new ArrayList<String>();
		String programArgs = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS,
				(String) null);
		if (programArgs != null)
		{
			for (String arg : DebugPlugin.parseArguments(programArgs))
			{
				commandList.add(arg);
			}
		}
		return commandList;
	}

	private String fileToLaunch(ILaunchConfiguration configuration) throws CoreException
	{
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
		return program;
	}

	private Collection<? extends String> debugArguments(String exe, String host, int port,
			ILaunchConfiguration configuration) throws CoreException
	{
		List<String> commandList = new ArrayList<String>();
		String rdebug = ExecutableUtil.find("rdebug-ide", false, null, getRDebugIDELocations(exe)); //$NON-NLS-1$
		if (rdebug == null)
		{
			abort("Unable to find 'rdebug-ide' binary script. May need to install 'ruby-debug-ide' gem.", null);
		}
		commandList.add(rdebug);
		commandList.add(DEBUGGER_PORT_SWITCH);
		commandList.add(Integer.toString(port));
		commandList.add(END_OF_ARGUMENTS_DELIMETER);
		return commandList;
	}

	@SuppressWarnings("nls")
	private List<String> getRDebugIDELocations(String rubyExe)
	{
		List<String> locations = new ArrayList<String>();
		// TODO What are the common places this could be?
		// check in bin dir alongside where our ruby exe is!
		locations.add(new File(rubyExe).getParent() + File.separator + "rdebug-ide");
		locations.add(System.getProperty("user.home") + "/.gem/ruby/1.8/bin/rdebug-ide");
		locations.add(System.getProperty("user.home") + "/.gem/ruby/1.9/bin/rdebug-ide");
		locations.add("/opt/local/bin/rdebug-ide");
		locations.add("/usr/local/bin/rdebug-ide");
		locations.add("/usr/bin/rdebug-ide");
		locations.add("/bin/rdebug-ide");
		return locations;
	}

	private Collection<? extends String> interpreterArguments(ILaunchConfiguration configuration) throws CoreException
	{
		List<String> arguments = new ArrayList<String>();
		String interpreterArgs = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_VM_ARGUMENTS,
				(String) null);
		if (interpreterArgs != null)
		{
			String[] raw = DebugPlugin.parseArguments(interpreterArgs);
			for (int i = 0; i < raw.length; i++)
			{
				String arg = raw[i];
				if ((arg.equals("-e") || arg.equals("-X") || arg.equals("-F")) && (raw.length > (i + 1))) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				{
					arguments.add(arg + " " + raw[i + 1]); //$NON-NLS-1$
					i++;
				}
				else
				{
					arguments.add(arg);
				}
			}
		}
		arguments.add(END_OF_ARGUMENTS_DELIMETER);
		return arguments;
	}

	protected String rubyExecutable() throws CoreException
	{
		// Ruby executable, look for rubyw, then ruby
		// TODO check TM_RUBY env value?
		String path = ExecutableUtil.find(RUBYW, true, null, getCommonRubyBinaryLocations(RUBYW));
		if (path == null)
		{
			path = ExecutableUtil.find(RUBY, true, null, getCommonRubyBinaryLocations(RUBY));
		}
		if (path == null)
		{
			abort("Unable to find a Ruby executable.", null);
		}
		File exe = new File(path);
		if (!exe.exists())
		{
			abort(MessageFormat.format("Specified Ruby executable {0} does not exist.", path), null);
		}
		return path;
	}

	/**
	 * Return an ordered list of common locations that you'd find a ruby binary.
	 * 
	 * @return
	 */
	@SuppressWarnings("nls")
	protected List<String> getCommonRubyBinaryLocations(String binaryName)
	{
		List<String> locations = new ArrayList<String>();
		if (Platform.getOS().equals(Platform.OS_WIN32))
		{
			locations.add("C:\\ruby\\bin\\" + binaryName + ".exe");
		}
		else
		{
			locations.add("/opt/local/bin/" + binaryName);
			locations.add("/usr/local/bin/" + binaryName);
			locations.add("/usr/bin/" + binaryName);
		}
		if (Platform.getOS().equals(Platform.OS_MACOSX))
		{
			locations.add("/System/Library/Frameworks/Ruby.framework/Versions/Current/usr/bin/" + binaryName);
		}
		return locations;
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
