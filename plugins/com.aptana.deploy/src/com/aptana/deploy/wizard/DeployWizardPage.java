package com.aptana.deploy.wizard;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.aptana.deploy.Activator;
import com.aptana.deploy.HerokuAPI;
import com.aptana.deploy.Messages;

public class DeployWizardPage extends WizardPage
{

	static final String NAME = "Deployment"; //$NON-NLS-1$
	private static final String HEROKU_IMG_PATH = "icons/heroku.png"; //$NON-NLS-1$

	private Button deployWithFTP;
	// private Button deployWithCapistrano;
	private Button deployWithHeroku;

	protected DeployWizardPage()
	{
		super(NAME, Messages.DeployWizardPage_Title, Activator.getImageDescriptor(HEROKU_IMG_PATH));
	}

	@Override
	public void createControl(Composite parent)
	{

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		setControl(composite);

		initializeDialogUnits(parent);

		// Actual contents
		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.DeployWizardPage_ProvidersLabel);

		// deploy with Heroku
		deployWithHeroku = new Button(composite, SWT.RADIO);
		deployWithHeroku.setImage(Activator.getImage(HEROKU_IMG_PATH));
		deployWithHeroku.setSelection(true);
		// TODO Add a click listener, if clicked treat it like selecting and clicking Next button!

		label = new Label(composite, SWT.NONE);
		label.setText(Messages.DeployWizardPage_OtherDeploymentOptionsLabel);

		// "Other" Deployment options radio button group
		deployWithFTP = new Button(composite, SWT.RADIO);
		deployWithFTP.setText(Messages.DeployWizardPage_FTPLabel);

		// deployWithCapistrano = new Button(composite, SWT.RADIO);
		// deployWithCapistrano.setText(Messages.DeployWizardPage_CapistranoLabel);

		Dialog.applyDialogFont(composite);
	}

	@Override
	public boolean canFlipToNextPage()
	{
		// user can always move on, and we don't want getNextPage() getting called quickly since it tries to actually
		// auth against Heroku...
		return true;
	}

	@Override
	public IWizardPage getNextPage()
	{
		IWizardPage nextPage = null;
		// Determine what page is next by the user's choice in the radio buttons
		if (deployWithHeroku.getSelection())
		{
			File credentials = HerokuAPI.getCredentialsFile();
			if (credentials.exists() && HerokuAPI.fromCredentials().authenticate().isOK())
				nextPage = new HerokuDeployWizardPage();
			else
				nextPage = new HerokuLoginWizardPage();
		}
		else if (deployWithFTP.getSelection())
		{
			nextPage = new FTPDeployWizardPage();
		}
		// else if (deployWithCapistrano.getSelection())
		// {
		// // TODO CapistranoDeployWizardPage
		// }
		if (nextPage == null)
			nextPage = super.getNextPage();
		if (nextPage != null)
		{
			nextPage.setWizard(getWizard());
		}
		return nextPage;
	}

	@Override
	public IWizardPage getPreviousPage()
	{
		return null;
	}

}
