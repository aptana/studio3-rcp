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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate;
import org.eclipse.debug.core.sourcelookup.containers.DirectorySourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.FolderSourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.ProjectSourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.WorkspaceSourceContainer;
import org.rubypeople.rdt.debug.core.launching.IRubyLaunchConfigurationConstants;

/**
 * Computes the default source lookup path for a Ruby launch configuration. The default source lookup path is the folder
 * or project containing the Ruby program being launched. If the program is not specified, the workspace is searched by
 * default.
 */
public class RubySourcePathComputerDelegate implements ISourcePathComputerDelegate
{

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.debug.internal.core.sourcelookup.ISourcePathComputerDelegate#computeSourceContainers(org.eclipse.
	 * debug.core.ILaunchConfiguration, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ISourceContainer[] computeSourceContainers(ILaunchConfiguration configuration, IProgressMonitor monitor)
			throws CoreException
	{
		List<ISourceContainer> sourceContainers = new ArrayList<ISourceContainer>();
		// TODO Eval the loadpath in the current debug target for launch, and then add DirectorySourceContainers for
		// each entry!

		// Grab the working directory from the configuration and add containers for it...
		String workingDir = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY,
				(String) null);
		sourceContainers.addAll(addPath(workingDir));
		// if it doesn't exist, assume working dir is parent of filename
		if (sourceContainers.isEmpty())
		{
			String path = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_FILE_NAME, (String) null);
			sourceContainers.addAll(addParent(path));
		}

		sourceContainers.add(new WorkspaceSourceContainer());
		if (File.listRoots() != null && File.listRoots().length > 0)
			sourceContainers.add(new DirectorySourceContainer(File.listRoots()[0], false));

		return sourceContainers.toArray(new ISourceContainer[sourceContainers.size()]);
	}

	private Collection<? extends ISourceContainer> addParent(String path)
	{
		if (path != null)
		{
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(path));
			if (resource != null)
			{
				IContainer container = resource.getParent();
				return addPath(container.getLocation().toPortableString());
			}
		}
		return Collections.emptyList();
	}

	private Collection<? extends ISourceContainer> addPath(String path)
	{
		List<ISourceContainer> sourceContainers = new ArrayList<ISourceContainer>();
		if (path != null)
		{
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().getContainerForLocation(new Path(path));
			if (resource != null && resource instanceof IContainer && resource.exists())
			{
				IContainer container = (IContainer) resource;
				if (container.getType() == IResource.FOLDER)
				{
					sourceContainers.add(new FolderSourceContainer(container, false));
				}
				else if (container.getType() == IResource.PROJECT)
				{
					sourceContainers.add(new ProjectSourceContainer(resource.getProject(), false));
				}
			}
			else
			{
				sourceContainers.add(new DirectorySourceContainer(new File(path), false));
			}
		}
		return sourceContainers;
	}
}
