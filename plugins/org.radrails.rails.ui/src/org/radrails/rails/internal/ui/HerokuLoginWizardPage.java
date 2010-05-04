package org.radrails.rails.internal.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.radrails.rails.ui.RailsUIPlugin;

public class HerokuLoginWizardPage extends WizardPage
{

	private Text userId;
	private Text password;

	protected HerokuLoginWizardPage()
	{
		super("HerokuLogin", "Deploy to Heroku", RailsUIPlugin.getImageDescriptor("icons/heroku.png"));
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
		label.setText("Please enter your Heroku credentials:");

		Composite credentials = new Composite(composite, SWT.NONE);
		credentials.setLayout(new GridLayout(2, false));

		Label userIdLabel = new Label(credentials, SWT.NONE);
		userIdLabel.setText("User ID: ");
		userId = new Text(credentials, SWT.SINGLE | SWT.BORDER);

		Label passwordLabel = new Label(credentials, SWT.NONE);
		passwordLabel.setText("Password: ");
		password = new Text(credentials, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);

		// Signup link
		Link link = new Link(composite, SWT.NONE);
		link.setText("<a>Don't have an account? click here to sign up</a>");
		link.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				// TODO open new wizard page with just email address and submit button
				super.widgetSelected(e);
			}
		});

		Dialog.applyDialogFont(composite);
	}

	@Override
	public IWizardPage getNextPage()
	{
		IWizardPage nextPage = new HerokuDeployWizardPage();
		nextPage.setWizard(getWizard());
		return nextPage;
	}

	@Override
	public boolean isPageComplete()
	{
		String userId = this.userId.getText();
		if (userId == null || userId.trim().length() < 1)
		{
			setErrorMessage("Must provide a User ID.");
			return false;
		}

		String password = this.password.getText();
		if (password == null || password.trim().length() < 1)
		{
			setErrorMessage("Must provide a password.");
			return false;
		}

		// TODO When do we check that the credentials actually authenticate? Probably here!

		setErrorMessage(null);
		return true;
	}

}
