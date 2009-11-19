package org.radrails.rails.internal.ui;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.radrails.rails.core.RailsProjectNature;
import org.radrails.rails.ui.RailsUIPlugin;

import com.aptana.util.ProcessUtil;

public class NewProjectWizard extends BasicNewProjectResourceWizard
{

	@Override
	public void addPages()
	{
		super.addPages();
		getPage("basicNewProjectPage").setImageDescriptor( //$NON-NLS-1$
				RailsUIPlugin.imageDescriptorFromPlugin(RailsUIPlugin.getPluginIdentifier(), "icons/newproj_wiz.gif")); //$NON-NLS-1$
	}

	@Override
	public boolean performFinish()
	{
		boolean valid = super.performFinish();
		if (!valid)
			return valid;

		final IProject project = getNewProject();
		Job job = new Job(Messages.NewProjectWizard_JobTitle)
		{
			@Override
			protected IStatus run(IProgressMonitor monitor)
			{
				SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
				String absolutePath = project.getLocation().toOSString();
				if (subMonitor.isCanceled())
					return Status.CANCEL_STATUS;
				// Now launch the rails command!
				Map<Integer, String> result = ProcessUtil.runInBackground("rails", absolutePath, new String[] { "." }); //$NON-NLS-1$ //$NON-NLS-2$
				if (result.keySet().iterator().next() != 0)
					return new Status(IStatus.ERROR, RailsUIPlugin.getPluginIdentifier(),
							Messages.NewProjectWizard_RailsCommandFailedMessage);
				subMonitor.worked(80);
				try
				{
					RailsProjectNature.add(project, subMonitor.newChild(5));
					project.refreshLocal(IResource.DEPTH_ONE, subMonitor.newChild(15));
				}
				catch (CoreException e)
				{
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setPriority(Job.SHORT);
		job.schedule();
		return true;
	}

}
