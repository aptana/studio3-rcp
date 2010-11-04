/**
 * This file Copyright (c) 2005-2010 Aptana, Inc. This program is
 * dual-licensed under both the Aptana Public License and the GNU General
 * Public license. You may elect to use one or the other of these licenses.
 * 
 * This program is distributed in the hope that it will be useful, but
 * AS-IS and WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, TITLE, or
 * NONINFRINGEMENT. Redistribution, except as permitted by whichever of
 * the GPL or APL you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or modify this
 * program under the terms of the GNU General Public License,
 * Version 3, as published by the Free Software Foundation.  You should
 * have received a copy of the GNU General Public License, Version 3 along
 * with this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Aptana provides a special exception to allow redistribution of this file
 * with certain other free and open source software ("FOSS") code and certain additional terms
 * pursuant to Section 7 of the GPL. You may view the exception and these
 * terms on the web at http://www.aptana.com/legal/gpl/.
 * 
 * 2. For the Aptana Public License (APL), this program and the
 * accompanying materials are made available under the terms of the APL
 * v1.0 which accompanies this distribution, and is available at
 * http://www.aptana.com/legal/apl/.
 * 
 * You may view the GPL, Aptana's exception and additional terms, and the
 * APL in the file titled license.html at the root of the corresponding
 * plugin containing this source file.
 * 
 * Any modifications to this file must keep this entire header intact.
 */
package org.radrails.rails.internal.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.radrails.rails.ui.RailsUIPlugin;

import com.aptana.console.process.ConsoleProcessFactory;
import com.aptana.ruby.debug.core.launching.IRubyLaunchConfigurationConstants;

public class DebugScriptServerAction extends RunScriptServerAction
{

	@Override
	public void run()
	{
		IProject railsProject = getSelectedRailsProject();
		if (railsProject == null)
			return;

		try
		{
			ILaunchConfiguration config = findOrCreateLaunchConfiguration(railsProject);
			if (config != null)
			{
				DebugUITools.launch(config, ILaunchManager.DEBUG_MODE);
			}
		}
		catch (CoreException e)
		{
			RailsUIPlugin.logError(e);
		}
	}

	@SuppressWarnings("nls")
	protected ILaunchConfiguration findOrCreateLaunchConfiguration(IProject railsProject) throws CoreException
	{
		String arguments = "";
		String filename = "";
		if (scriptServerExists(railsProject))
		{
			IFile file = railsProject.getFile(new Path("script").append("server"));
			filename = file.getLocation().toOSString();
			arguments = "";
		}
		else
		{
			IFile file = railsProject.getFile(new Path("script").append("rails"));
			filename = file.getLocation().toOSString();
			arguments = "server";
		}

		ILaunchConfigurationType configType = getRubyLaunchConfigType();
		ILaunchConfiguration[] configs = getLaunchManager().getLaunchConfigurations(configType);
		List<ILaunchConfiguration> candidateConfigs = new ArrayList<ILaunchConfiguration>(configs.length);
		for (ILaunchConfiguration config : configs)
		{
			boolean absoluteFilenamesMatch = config.getAttribute(IRubyLaunchConfigurationConstants.ATTR_FILE_NAME, "") //$NON-NLS-1$
					.equals(filename);
			boolean argsMatch = config.getAttribute(IRubyLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, "") //$NON-NLS-1$
					.equals(arguments);
			if (absoluteFilenamesMatch && argsMatch)
			{
				candidateConfigs.add(config);
			}
		}

		switch (candidateConfigs.size())
		{
			case 0:
				return createConfiguration(railsProject, filename, arguments);
			case 1:
				return candidateConfigs.get(0);
			default:
				Status status = new Status(Status.WARNING, RailsUIPlugin.getPluginIdentifier(), 0,
						"Multiple configurations match", null); //$NON-NLS-1$
				throw new CoreException(status);
		}
	}

	@SuppressWarnings("deprecation")
	protected ILaunchConfiguration createConfiguration(IProject project, String rubyFile, String args)
			throws CoreException
	{
		// TODO Combine with RubyApplicationShortcut?
		ILaunchConfigurationType configType = getRubyLaunchConfigType();
		ILaunchConfigurationWorkingCopy wc = configType.newInstance(null, getLaunchManager()
				.generateUniqueLaunchConfigurationNameFrom(project.getName()));
		wc.setAttribute(IRubyLaunchConfigurationConstants.ATTR_FILE_NAME, rubyFile);
		wc.setAttribute(IRubyLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, project.getLocation().toOSString());
		wc.setAttribute(IRubyLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, args);
		wc.setAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_ID,
				IRubyLaunchConfigurationConstants.ID_RUBY_SOURCE_LOCATOR);
		wc.setAttribute(DebugPlugin.ATTR_PROCESS_FACTORY_ID, ConsoleProcessFactory.ID);
		return wc.doSave();
	}

	protected ILaunchConfigurationType getRubyLaunchConfigType()
	{
		return getLaunchManager().getLaunchConfigurationType(IRubyLaunchConfigurationConstants.ID_RUBY_APPLICATION);
	}

	protected ILaunchManager getLaunchManager()
	{
		return DebugPlugin.getDefault().getLaunchManager();
	}

}
