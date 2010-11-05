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

import java.io.File;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.progress.UIJob;
import org.radrails.rails.core.RailsCorePlugin;
import org.radrails.rails.core.RailsProjectNature;

import com.aptana.core.ShellExecutable;
import com.aptana.ruby.core.RubyProjectNature;
import com.aptana.ruby.ui.wizards.NewRubyProjectWizard;
import com.aptana.ruby.ui.wizards.WizardNewRubyProjectCreationPage;
import com.aptana.terminal.views.TerminalView;

public class NewRailsProjectWizard extends NewRubyProjectWizard
{

	public NewRailsProjectWizard()
	{
		super();
	}

	@Override
	protected WizardNewRubyProjectCreationPage createMainPage()
	{
		WizardNewRailsProjectCreationPage mainPage = new WizardNewRailsProjectCreationPage("basicNewProjectPage"); //$NON-NLS-1$
		mainPage.setTitle(Messages.NewProject_title);
		mainPage.setDescription(Messages.NewRailsProject_description);
		return mainPage;
	}

	/*
	 * (non-Javadoc) Method declared on IWorkbenchWizard.
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection)
	{
		super.init(workbench, currentSelection);
		setWindowTitle(Messages.NewRailsProject_windowTitle);
	}
	
	@Override
	protected String[] getNatureIds()
	{
		return new String[] { RailsProjectNature.ID, RubyProjectNature.ID };
	}

	@Override
	public boolean performFinish()
	{
		// HACK I have to query for this here, because otherwise when we generate the project somehow the fields get
		// focus and that auto changes the radio selection value for generation
		IWizardPage page = getStartingPage();
		boolean runGenerator = false;
		if (page instanceof WizardNewRailsProjectCreationPage)
		{
			WizardNewRailsProjectCreationPage railsPage = (WizardNewRailsProjectCreationPage) page;
			runGenerator = railsPage.runGenerator();
		}

		if (!super.performFinish())
		{
			return false;
		}

		if (runGenerator)
			runGenerator();

		return true;
	}

	private void runGenerator()
	{
		// Pop open a confirmation dialog if the project already has a config/environment.rb file!
		File projectFile = getProject().getLocation().toFile();
		File env = new File(projectFile, "config" + File.separator + "environment.rb"); //$NON-NLS-1$ //$NON-NLS-2$
		if (env.exists())
		{
			if (!MessageDialog.openConfirm(getShell(), Messages.NewProjectWizard_ContentsAlreadyExist_Title,
					Messages.NewProjectWizard_ContentsAlreadyExist_Msg))
				return;
		}
		final IProject project = getProject();
		Job job = new UIJob(Messages.NewProjectWizard_JobTitle)
		{
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor)
			{
				SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
				if (subMonitor.isCanceled())
					return Status.CANCEL_STATUS;

				// Now launch the rails command in a terminal!
				TerminalView terminal = TerminalView.openView(project.getName(), project.getName(),
						project.getLocation());
				String input = "rails .\n"; //$NON-NLS-1$
				if (requiresNewArgToGenerateApp(project))
				{
					input = "rails new .\n"; //$NON-NLS-1$
				}
				terminal.sendInput(input);

				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setPriority(Job.SHORT);
		job.schedule();
	}

	/**
	 * As of Rails 3 beta4, to generate an app, you need to use 'rails new APP_NAME'
	 * 
	 * @param project
	 * @return
	 */
	@SuppressWarnings("nls")
	protected boolean requiresNewArgToGenerateApp(IProject project)
	{
		Map<Integer, String> result = RailsCorePlugin.runRailsInBackground(project.getLocation(),
				ShellExecutable.getEnvironment(), "-v");
		String version = null;
		if (result != null && result.values().size() > 0)
		{
			version = result.values().iterator().next();
		}
		if (version == null)
		{
			return false;
		}
		String[] parts = version.split("\\s");
		String lastPart = parts[parts.length - 1];
		if (lastPart.startsWith("1") || lastPart.startsWith("2"))
		{
			return false;
		}
		if (lastPart.startsWith("3.0.0beta"))
		{
			return lastPart.endsWith("beta4");
		}
		return true;
	}
}
