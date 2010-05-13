package com.aptana.deploy.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.tm.internal.terminal.control.ITerminalListener;
import org.eclipse.tm.internal.terminal.provisional.api.ITerminalConnector;
import org.eclipse.tm.internal.terminal.provisional.api.TerminalConnectorExtension;
import org.eclipse.tm.internal.terminal.provisional.api.TerminalState;

import com.aptana.core.util.ExecutableUtil;
import com.aptana.terminal.connector.LocalTerminalConnector;
import com.aptana.terminal.internal.emulator.VT100TerminalControl;

@SuppressWarnings("restriction")
public class InstallCapistranoGemPage extends WizardPage
{

	static final String NAME = "InstallCapistrano"; //$NON-NLS-1$
	private VT100TerminalControl fCtlTerminal;

	protected InstallCapistranoGemPage()
	{
		super(NAME, Messages.InstallCapistranoGemPage_Title, null);
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
		Label label = new Label(composite, SWT.WRAP);
		label.setText(Messages.InstallCapistranoGemPage_Description);
		label.setLayoutData(new GridData(500, SWT.DEFAULT));

		Button generateButton = new Button(composite, SWT.PUSH);
		generateButton.setText(Messages.InstallCapistranoGemPage_InstallGemLabel);
		generateButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		generateButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				if (!fCtlTerminal.isEmpty())
				{
					fCtlTerminal.clearTerminal();
				}

				// install gem
				// TODO Determine if we need to run via sudo or not!
				fCtlTerminal.pasteString("gem install capistrano\n"); //$NON-NLS-1$
			}
		});

		// Terminal
		Composite terminal = new Composite(composite, SWT.NONE);
		terminal.setLayout(new FillLayout());
		terminal.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// TODO Can we prevent user input to this terminal?
		fCtlTerminal = new VT100TerminalControl(new ITerminalListener()
		{

			@Override
			public void setState(TerminalState state)
			{
				// do nothing
			}

			@Override
			public void setTerminalTitle(String title)
			{
				// do nothing
			}

		}, terminal, getTerminalConnectors());
		fCtlTerminal.setConnector(fCtlTerminal.getConnectors()[0]);
		fCtlTerminal.connectTerminal();

		// TODO Need to listen to console/process/poll to determine when it's done and check if gem was installed!

		Dialog.applyDialogFont(composite);
	}

	@Override
	public void dispose()
	{
		if (fCtlTerminal != null)
		{
			fCtlTerminal.disposeTerminal();
		}
		super.dispose();
	}

	private ITerminalConnector[] getTerminalConnectors()
	{
		ITerminalConnector connector = TerminalConnectorExtension.makeTerminalConnector(LocalTerminalConnector.ID);
		if (connector != null)
		{
			connector.getInitializationErrorMessage();
		}
		return new ITerminalConnector[] { connector };
	}

	@Override
	public IWizardPage getNextPage()
	{
		IWizardPage nextPage = new CapifyProjectPage();
		nextPage.setWizard(getWizard());
		return nextPage;
	}

	@Override
	public boolean isPageComplete()
	{
		return isCapistranoGemInstalled();
	}

	static boolean isCapistranoGemInstalled()
	{
		// Determine if capistrano is installed
		IPath path = ExecutableUtil.find("capify", true, null); //$NON-NLS-1$
		if (path != null && path.toFile().exists())
			return true;
		return false;
	}

	protected IProject getProject()
	{
		return ((DeployWizard) getWizard()).getProject();
	}
}
