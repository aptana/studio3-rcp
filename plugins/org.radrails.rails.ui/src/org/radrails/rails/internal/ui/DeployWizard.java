package org.radrails.rails.internal.ui;

import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.radrails.rails.ui.RailsUIPlugin;

public class DeployWizard extends Wizard implements IWorkbenchWizard
{

	private IProject project;

	@Override
	public boolean performFinish()
	{
		// check what the user chose, then do the heavy lifting, or tell the page to finish...
		IWizardPage currentPage = getContainer().getCurrentPage();
		if (currentPage.getName().equals(HerokuDeployWizardPage.NAME))
		{
			HerokuDeployWizardPage page = (HerokuDeployWizardPage) currentPage;
			String appName = page.getAppName();
			boolean publishImmediately = page.publishImmediately();
			// TODO Create a job to deploy with these values!
		}
		else if (currentPage.getName().equals(FTPDeployWizardPage.NAME))
		{
			// TODO Set up FTP deployment stuff, run it
		}
		else if (currentPage.getName().equals(HerokuSignupPage.NAME))
		{
			HerokuSignupPage page = (HerokuSignupPage) currentPage;
			String userID = page.getUserID();

			// TODO Send a ping to aptana.com with email address for referral tracking
			// Bring up Heroku signup page, http://api.heroku.com/signup
			try
			{
				IWorkbenchBrowserSupport support = PlatformUI.getWorkbench().getBrowserSupport();
				IWebBrowser browser = support.createBrowser("heroku-signup"); //$NON-NLS-1$
				browser.openURL(new URL("http://api.heroku.com/signup")); //$NON-NLS-1$
				// TODO Inject special JS into it. Need to fill in id of 'invitation_email' with the value!
				// This seems bizzare, can't we somehow send a query param to populate the email address?
			}
			catch (Exception e)
			{
				RailsUIPlugin.logError(e);
			}
		}
		// TODO Add branch for capistrano deployment
		return true;
	}

	@Override
	public void addPages()
	{
		// Add the first basic page where they choose the deployment option
		addPage(new DeployWizardPage());
		setForcePreviousAndNextButtons(true); // we only add one page here, but we calculate the next page
												// dynamically...
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page)
	{
		// delegate to page because we modify the page list dynamically and don't statically add them via addPage
		return page.getNextPage();
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage page)
	{
		// delegate to page because we modify the page list dynamically and don't statically add them via addPage
		return page.getPreviousPage();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection)
	{
		Object element = selection.getFirstElement();
		if (element instanceof IResource)
		{
			IResource resource = (IResource) element;
			this.project = resource.getProject();
		}
	}

	public IProject getProject()
	{
		return project;
	}

	// FIXME Need to override canFinish method....

}
