package org.radrails.rails.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.aptana.core.build.UnifiedBuilder;
import com.aptana.core.util.ResourceUtil;

public class RailsProjectNature implements IProjectNature
{

	public static final String ID = RailsCorePlugin.PLUGIN_ID + ".railsnature"; //$NON-NLS-1$

	private IProject project;

	public void configure() throws CoreException
	{
		ResourceUtil.addBuilder(getProject(), UnifiedBuilder.ID);
	}

	public void deconfigure() throws CoreException
	{
	}

	public IProject getProject()
	{
		return project;
	}

	public void setProject(IProject project)
	{
		this.project = project;
	}

	public static void add(IProject project, IProgressMonitor monitor) throws CoreException
	{
		IProjectDescription description = project.getDescription();
		boolean addedNature = ResourceUtil.addNature(description, ID);
		boolean addedBuilder = ResourceUtil.addBuilder(description, UnifiedBuilder.ID);
		if (addedNature || addedBuilder)
		{
			project.setDescription(description, monitor);
		}
	}

}
