package com.aptana.deploy.wizard;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.aptana.deploy.Activator;
import com.aptana.ide.ui.ftp.internal.FTPConnectionPropertyComposite;
import com.aptana.ide.ui.secureftp.internal.CommonFTPConnectionPropertyComposite;

@SuppressWarnings("restriction")
public class FTPDeployWizardPage extends WizardPage implements FTPConnectionPropertyComposite.Listener
{

	static final String NAME = "FTPDeployment"; //$NON-NLS-1$
	private static final String ICON_PATH = "icons/ftp.png"; //$NON-NLS-1$

	private FTPConnectionPropertyComposite ftpConnectionComposite;

	protected FTPDeployWizardPage()
	{
		super(NAME, Messages.FTPDeployWizardPage_Title, Activator.getImageDescriptor(ICON_PATH));
	}

	@Override
	public void createControl(Composite parent)
	{
		ftpConnectionComposite = new CommonFTPConnectionPropertyComposite(parent, SWT.NONE, null, this);
		ftpConnectionComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		setControl(ftpConnectionComposite);

		initializeDialogUnits(parent);
		Dialog.applyDialogFont(ftpConnectionComposite);

		ftpConnectionComposite.validate();
	}

	@Override
	public IWizardPage getNextPage()
	{
		return null;
	}

	@Override
	public boolean close()
	{
		return false;
	}

	@Override
	public void error(String message)
	{
		if (message == null)
		{
			setErrorMessage(null);
			setMessage(null);
		}
		else
		{
			setErrorMessage(message);
		}
	}

	@Override
	public void layoutShell()
	{
	}

	@Override
	public void lockUI(boolean lock)
	{
	}

	@Override
	public void setValid(boolean valid)
	{
	}

}
