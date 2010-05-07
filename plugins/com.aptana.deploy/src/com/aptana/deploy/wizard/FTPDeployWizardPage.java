package com.aptana.deploy.wizard;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.aptana.deploy.Activator;
import com.aptana.deploy.Messages;

public class FTPDeployWizardPage extends WizardPage
{

	static final String NAME = "FTPDeployment"; //$NON-NLS-1$
	private static final String ICON_PATH = "icons/ftp.png"; //$NON-NLS-1$

	private Combo protocolCombo;
	private Text siteName;

	protected FTPDeployWizardPage()
	{
		super(NAME, Messages.FTPDeployWizardPage_Title, Activator.getImageDescriptor(ICON_PATH));
	}

	@Override
	public void createControl(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		setControl(composite);

		initializeDialogUnits(parent);

		// Actual contents
		Label siteNameLabel = new Label(composite, SWT.NONE);
		siteNameLabel.setText(Messages.FTPDeployWizardPage_SiteNameLabel);

		siteName = new Text(composite, SWT.SINGLE | SWT.BORDER);

		Label protocolLabel = new Label(composite, SWT.NONE);
		protocolLabel.setText(Messages.FTPDeployWizardPage_ProtocolLabel);

		protocolCombo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		protocolCombo.add("SFTP"); // TODO Add the available protocols in a more programmatic way? //$NON-NLS-1$
		protocolCombo.add("FTP"); //$NON-NLS-1$
		protocolCombo.add("FTPS"); //$NON-NLS-1$
		protocolCombo.setText("SFTP"); //$NON-NLS-1$

		Group remoteInfoGroup = new Group(composite, SWT.NONE);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		remoteInfoGroup.setLayoutData(gd);
		remoteInfoGroup.setText(Messages.FTPDeployWizardPage_RemoteInfoLabel);

		// TODO Add server hostname, login, etc.

		Dialog.applyDialogFont(composite);
	}

	@Override
	public IWizardPage getNextPage()
	{
		return null;
	}

}
