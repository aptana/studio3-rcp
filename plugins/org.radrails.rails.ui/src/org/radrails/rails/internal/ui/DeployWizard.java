package org.radrails.rails.internal.ui;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class DeployWizard extends Wizard
{

	@Override
	public boolean performFinish()
	{
		IWizardPage currentPage = getContainer().getCurrentPage();
		// check what the user chose, then do the heavy lifting, or tell the page to finish...
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
		return page.getNextPage();
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage page)
	{
		return page.getPreviousPage();
	}

}
