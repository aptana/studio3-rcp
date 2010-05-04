package org.radrails.rails.internal.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.radrails.rails.ui.RailsUIPlugin;

public class HerokuDeployWizardPage extends WizardPage
{

	private Text appName;
	private Button publishButton;

	protected HerokuDeployWizardPage()
	{
		super("HerokuDeploy", "Deploy to Heroku", RailsUIPlugin.getImageDescriptor("icons/heroku.png"));
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
		Composite appSettings = new Composite(composite, SWT.NULL);
		appSettings.setLayout(new GridLayout(2, false));

		Label label = new Label(appSettings, SWT.NONE);
		label.setText("Name your application:");

		appName = new Text(appSettings, SWT.SINGLE | SWT.BORDER);

		publishButton = new Button(composite, SWT.CHECK);
		publishButton.setText("Publish my application immediately");

		// TODO Only show note if project doesn't have git repo!
		Label note = new Label(composite, SWT.WRAP | SWT.ITALIC); // FIXME We need this italic, we may need to set a font explicitly here to get it
		GridData gd = new GridData(400, SWT.DEFAULT);
		note.setLayoutData(gd);
		note.setText("Note: Your project is not currently set up to use Git, and Heroku will not work without a Git repository. As part of the deployment process, we will create a Git repository for your project and perform an initial commit to your local repository.");

		Dialog.applyDialogFont(composite);
	}

	@Override
	public IWizardPage getNextPage()
	{
		return null;
	}
}
