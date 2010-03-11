package com.aptana.ruby.debug.ui.launcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.aptana.ruby.debug.core.launching.IRubyLaunchConfigurationConstants;
import com.aptana.ruby.debug.ui.RubyDebugUIPlugin;

public class RubyArgumentsTab extends AbstractLaunchConfigurationTab
{

	protected Text interpreterArgsText, programArgsText;
	protected DirectorySelector workingDirectorySelector;

	public RubyArgumentsTab()
	{
		super();
	}

	public void createControl(Composite parent)
	{
		Composite composite = createPageRoot(parent);

		new Label(composite, SWT.NONE)
				.setText(Messages.RubyArgumentsTab_interpreter_args_box_title);
		interpreterArgsText = new Text(composite, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
		interpreterArgsText.setLayoutData(new GridData(GridData.FILL_BOTH));
		interpreterArgsText.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent evt)
			{
				updateLaunchConfigurationDialog();
			}
		});

		new Label(composite, SWT.NONE).setText(Messages.RubyArgumentsTab_program_args_box_title);
		programArgsText = new Text(composite, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
		programArgsText.setLayoutData(new GridData(GridData.FILL_BOTH));
		programArgsText.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent evt)
			{
				updateLaunchConfigurationDialog();
			}
		});

		new Label(composite, SWT.NONE);
		// TODO Use WorkingDirectoryBlock when our minimum Eclipse version is 3.5+!

		new Label(composite, SWT.NONE).setText(Messages.RubyArgumentsTab_working_dir);
		workingDirectorySelector = new DirectorySelector(composite);
		workingDirectorySelector
				.setBrowseDialogMessage(Messages.RubyArgumentsTab_working_dir_browser_message);
		workingDirectorySelector.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		workingDirectorySelector.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				updateLaunchConfigurationDialog();
			}
		});

	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
	{
		// TODO Set default working dir
		configuration.setAttribute(IRubyLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, (String) null);
		configuration.setAttribute(IRubyLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, (String) null);
		// set hidden attribute
		configuration.setAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_ID,
				IRubyLaunchConfigurationConstants.ID_RUBY_SOURCE_LOCATOR);
	}

	public void initializeFrom(ILaunchConfiguration configuration)
	{
		String workingDirectory = "", interpreterArgs = "", programArgs = ""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		try
		{
			workingDirectory = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, ""); //$NON-NLS-1$
			interpreterArgs = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, ""); //$NON-NLS-1$
			programArgs = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, ""); //$NON-NLS-1$
		}
		catch (CoreException e)
		{
			log(e);
		}

		workingDirectorySelector.setSelectionText(workingDirectory);
		interpreterArgsText.setText(interpreterArgs);
		programArgsText.setText(programArgs);
	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration)
	{
		configuration.setAttribute(IRubyLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, workingDirectorySelector
				.getValidatedSelectionText());
		// TODO Set the args to null if the text is empty
		configuration.setAttribute(IRubyLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, interpreterArgsText.getText());
		configuration.setAttribute(IRubyLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, programArgsText.getText());
	}

	protected Composite createPageRoot(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout compositeLayout = new GridLayout();
		compositeLayout.marginWidth = 0;
		compositeLayout.numColumns = 1;
		composite.setLayout(compositeLayout);

		setControl(composite);
		return composite;
	}

	public String getName()
	{
		return Messages.RubyArgumentsTab_name;
	}

	public boolean isValid(ILaunchConfiguration launchConfig)
	{
		try
		{
			String workingDirectory = launchConfig.getAttribute(
					IRubyLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, ""); //$NON-NLS-1$
			if (workingDirectory.length() == 0)
			{
				setErrorMessage(Messages.RubyArgumentsTab_working_dir_error_message);
				return false;
			}
		}
		catch (CoreException e)
		{
			log(e);
		}

		setErrorMessage(null);
		return true;
	}

	protected void log(Throwable t)
	{
		RubyDebugUIPlugin.logError(t.getMessage(), t);
	}

	public Image getImage()
	{
		return RubyDebugUIPlugin.getDefault().getImageRegistry().get(RubyDebugUIPlugin.IMG_EVIEW_ARGUMENTS_TAB);
	}

}