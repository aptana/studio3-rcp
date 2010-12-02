package org.radrails.rails.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.aptana.core.util.ResourceUtil;

public class RailsProjectNature implements IProjectNature
{

	public static final String ID = RailsCorePlugin.PLUGIN_ID + ".railsnature"; //$NON-NLS-1$

	private IProject project;

	public void configure() throws CoreException
	{
		// FIXME Move the id to some interface/constant in core!
		ResourceUtil.addBuilder(getProject(), "com.aptana.ide.core.unifiedBuilder");
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
		String[] oldNatures = description.getNatureIds();
		String[] newNatures = new String[oldNatures.length + 1];
		System.arraycopy(oldNatures, 0, newNatures, 0, oldNatures.length);
		newNatures[oldNatures.length] = RailsProjectNature.ID;
		description.setNatureIds(newNatures);
		project.setDescription(description, monitor);
	}

}
