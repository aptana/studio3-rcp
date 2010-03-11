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
		// TODO Take a look at the working directory, loadpaths
		String path = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_FILE_NAME, (String) null);
		if (path != null)
		{
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(path));
			if (resource != null)
			{
				IContainer container = resource.getParent();
				if (container.getType() == IResource.FOLDER)
				{
					sourceContainers.add(new FolderSourceContainer(container, false));
				}
				sourceContainers.add(new ProjectSourceContainer(resource.getProject(), false));
			}
		}
		sourceContainers.add(new WorkspaceSourceContainer());
		if (File.listRoots() != null && File.listRoots().length > 0)
			sourceContainers.add(new DirectorySourceContainer(File.listRoots()[0], false));

		return sourceContainers.toArray(new ISourceContainer[sourceContainers.size()]);
	}
}
