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
package org.radrails.rails.internal.ui;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.undo.CreateProjectOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.statushandlers.IStatusAdapterConstants;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.radrails.rails.core.RailsProjectNature;
import org.radrails.rails.ui.RailsUIPlugin;

import com.aptana.git.ui.CloneJob;

/**
 * TODO Extract common code between this and our web wizard!
 * 
 * @author cwilliams
 */
public class NewRubyProjectWizard extends BasicNewResourceWizard implements IExecutableExtension
{

	private WizardNewRubyProjectCreationPage mainPage;

	// cache of newly-created project
	private IProject newProject;

	/**
	 * The config element which declares this wizard.
	 */
	private IConfigurationElement configElement;

	public NewRubyProjectWizard()
	{
		IDialogSettings workbenchSettings = RailsUIPlugin.getDefault().getDialogSettings();
		IDialogSettings section = workbenchSettings.getSection("BasicNewProjectResourceWizard");//$NON-NLS-1$
		if (section == null)
		{
			section = workbenchSettings.addNewSection("BasicNewProjectResourceWizard");//$NON-NLS-1$
		}
		setDialogSettings(section);
	}

	protected IProject getProject()
	{
		return newProject;
	}

	@Override
	public void addPages()
	{
		super.addPages();

		mainPage = createMainPage();
		this.addPage(mainPage);
	}

	protected WizardNewRubyProjectCreationPage createMainPage()
	{
		WizardNewRubyProjectCreationPage mainPage = new WizardNewRubyProjectCreationPage("basicNewProjectPage"); //$NON-NLS-1$
		mainPage.setTitle(Messages.NewProject_title);
		mainPage.setDescription(Messages.NewRubyProject_description);
		return mainPage;
	}

	/*
	 * (non-Javadoc) Method declared on BasicNewResourceWizard.
	 */
	protected void initializeDefaultPageImageDescriptor()
	{
		ImageDescriptor desc = RailsUIPlugin.imageDescriptorFromPlugin(RailsUIPlugin.getPluginIdentifier(),
				"icons/newproj_wiz.png"); //$NON-NLS-1$
		setDefaultPageImageDescriptor(desc);
	}

	/*
	 * (non-Javadoc) Method declared on IWorkbenchWizard.
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection)
	{
		super.init(workbench, currentSelection);
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.NewRubyProject_windowTitle);
	}

	@Override
	public boolean performFinish()
	{
		createNewProject();

		if (newProject == null)
		{
			return false;
		}

		updatePerspective();
		selectAndReveal(newProject);

		return true;
	}

	/**
	 * Stores the configuration element for the wizard. The config element will be used in <code>performFinish</code> to
	 * set the result perspective.
	 */
	public void setInitializationData(IConfigurationElement cfig, String propertyName, Object data)
	{
		configElement = cfig;
	}

	/**
	 * Creates a new project resource with the selected name.
	 * <p>
	 * In normal usage, this method is invoked after the user has pressed Finish on the wizard; the enablement of the
	 * Finish button implies that all controls on the pages currently contain valid values.
	 * </p>
	 * <p>
	 * Note that this wizard caches the new project once it has been successfully created; subsequent invocations of
	 * this method will answer the same project resource without attempting to create it again.
	 * </p>
	 * 
	 * @return the created project resource, or <code>null</code> if the project was not created
	 */
	private IProject createNewProject()
	{
		// HACK I have to query for this here, because otherwise when we generate the project somehow the fields get
		// focus and that auto changes the radio selection value for generation
		boolean doGitClone = mainPage.cloneFromGit();
		if (newProject != null)
		{
			return newProject;
		}

		// get a project handle
		final IProject newProjectHandle = mainPage.getProjectHandle();

		// get a project descriptor
		URI location = null;
		if (!mainPage.locationIsDefault())
		{
			location = mainPage.getLocationURI();
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProjectDescription description = workspace.newProjectDescription(newProjectHandle.getName());
		description.setLocationURI(location);
		description.setNatureIds(getNatureIds());

		try
		{
			if (doGitClone)
			{
				doGitClone(description);
			}
			else
			{
				doBasicCreateProject(newProjectHandle, description);
			}
		}
		catch (CoreException e)
		{
			return null;
		}
		newProject = newProjectHandle;

		return newProject;
	}

	protected String[] getNatureIds()
	{
		// FIXME Use a ruby nature!
		return new String[] { RailsProjectNature.ID };
	}

	private void doGitClone(final IProjectDescription overridingDescription)
	{
		Job job = new CloneJob(mainPage.gitCloneURI(), mainPage.getLocationPath().toOSString(), true)
		{
			@Override
			protected void doCreateProject(IProject project, IProjectDescription desc, IProgressMonitor monitor)
					throws CoreException
			{
				super.doCreateProject(project, overridingDescription, monitor);
			}
		};
		job.schedule();
	}

	private void doBasicCreateProject(final IProject newProjectHandle, final IProjectDescription description)
			throws CoreException
	{
		// create the new project operation
		IRunnableWithProgress op = new IRunnableWithProgress()
		{
			public void run(IProgressMonitor monitor) throws InvocationTargetException
			{
				CreateProjectOperation op = new CreateProjectOperation(description, Messages.NewProject_jobTitle);
				try
				{
					// see bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=219901
					// directly execute the operation so that the undo state is
					// not preserved. Making this undoable resulted in too many
					// accidental file deletions.
					op.execute(monitor, WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
				}
				catch (ExecutionException e)
				{
					throw new InvocationTargetException(e);
				}
			}
		};

		// run the new project creation operation
		try
		{
			getContainer().run(true, true, op);
		}
		catch (InterruptedException e)
		{
			throw new CoreException(new Status(IStatus.ERROR, RailsUIPlugin.getPluginIdentifier(), e.getMessage(), e));
		}
		catch (InvocationTargetException e)
		{
			Throwable t = e.getTargetException();
			if (t instanceof ExecutionException && t.getCause() instanceof CoreException)
			{
				CoreException cause = (CoreException) t.getCause();
				StatusAdapter status;
				if (cause.getStatus().getCode() == IResourceStatus.CASE_VARIANT_EXISTS)
				{
					status = new StatusAdapter(new Status(IStatus.WARNING, RailsUIPlugin.getPluginIdentifier(),
							NLS.bind(Messages.NewProject_caseVariantExistsError, newProjectHandle.getName()), cause));
				}
				else
				{
					status = new StatusAdapter(new Status(cause.getStatus().getSeverity(),
							RailsUIPlugin.getPluginIdentifier(), Messages.NewProject_errorMessage, cause));
				}
				status.setProperty(IStatusAdapterConstants.TITLE_PROPERTY, Messages.NewProject_errorMessage);
				StatusManager.getManager().handle(status, StatusManager.BLOCK);
			}
			else
			{
				StatusAdapter status = new StatusAdapter(new Status(IStatus.WARNING,
						RailsUIPlugin.getPluginIdentifier(), 0, NLS.bind(Messages.NewProject_internalError,
								t.getMessage()), t));
				status.setProperty(IStatusAdapterConstants.TITLE_PROPERTY, Messages.NewProject_errorMessage);
				StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.BLOCK);
			}
			throw new CoreException(new Status(IStatus.ERROR, RailsUIPlugin.getPluginIdentifier(), e.getMessage(), e));
		}
	}

	/**
	 * Updates the perspective for the active page within the window.
	 */
	protected void updatePerspective()
	{
		BasicNewProjectResourceWizard.updatePerspective(configElement);
	}
}
