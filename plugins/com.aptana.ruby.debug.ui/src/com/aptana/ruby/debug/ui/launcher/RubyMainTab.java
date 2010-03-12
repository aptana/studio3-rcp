/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Bjorn Freeman-Benson - initial API and implementation
 *******************************************************************************/
package com.aptana.ruby.debug.ui.launcher;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;

import com.aptana.ruby.debug.core.launching.IRubyLaunchConfigurationConstants;

/**
 * Tab to specify the Ruby program to run/debug.
 */
public class RubyMainTab extends AbstractLaunchConfigurationTab
{

	private Text fProgramText;
	private Button fProgramButton;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent)
	{
		Font font = parent.getFont();

		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		GridLayout topLayout = new GridLayout();
		topLayout.verticalSpacing = 0;
		topLayout.numColumns = 3;
		comp.setLayout(topLayout);
		comp.setFont(font);

		createVerticalSpacer(comp, 3);

		Label programLabel = new Label(comp, SWT.NONE);
		programLabel.setText(Messages.RubyMainTab_FileLabel);
		GridData gd = new GridData(GridData.BEGINNING);
		programLabel.setLayoutData(gd);
		programLabel.setFont(font);

		fProgramText = new Text(comp, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fProgramText.setLayoutData(gd);
		fProgramText.setFont(font);
		fProgramText.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				updateLaunchConfigurationDialog();
			}
		});

		fProgramButton = createPushButton(comp, "&Browse...", null); //$NON-NLS-1$
		fProgramButton.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				browseRubyFiles();
			}
		});
	}

	/**
	 * Open a resource chooser to select a Ruby program
	 */
	protected void browseRubyFiles()
	{
		// FIXME Use a file selector dialog!
		ResourceListSelectionDialog dialog = new ResourceListSelectionDialog(getShell(), ResourcesPlugin.getWorkspace()
				.getRoot(), IResource.FILE);
		dialog.setTitle(Messages.RubyMainTab_OpenFileDialogTitle);
		dialog.setMessage(Messages.RubyMainTab_OpenFileDialogMessage);
		// TODO: single select
		if (dialog.open() == Window.OK)
		{
			Object[] files = dialog.getResult();
			IFile file = (IFile) files[0];
			fProgramText.setText(file.getFullPath().toString());
		}

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
	{
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration)
	{
		try
		{
			String program = configuration
					.getAttribute(IRubyLaunchConfigurationConstants.ATTR_FILE_NAME, (String) null);
			if (program != null)
			{
				fProgramText.setText(program);
			}
		}
		catch (CoreException e)
		{
			setErrorMessage(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration)
	{
		String program = fProgramText.getText().trim();
		if (program.length() == 0)
		{
			program = null;
		}
		configuration.setAttribute(IRubyLaunchConfigurationConstants.ATTR_FILE_NAME, program);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName()
	{
		return Messages.RubyMainTab_Name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public boolean isValid(ILaunchConfiguration launchConfig)
	{
		String text = fProgramText.getText();
		if (text.length() > 0)
		{
			IPath path = new Path(text);
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(path);
			if (file == null || !file.exists())
			{
				setErrorMessage(Messages.RubyMainTab_FileDoesntExistError);
				return false;
			}
		}
		else
		{
			setMessage(Messages.RubyMainTab_EmptyFileError);
		}
		return super.isValid(launchConfig);
	}
}
