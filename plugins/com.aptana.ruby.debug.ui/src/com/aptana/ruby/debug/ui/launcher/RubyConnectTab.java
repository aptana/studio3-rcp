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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.aptana.ruby.debug.core.launching.IRubyLaunchConfigurationConstants;

/**
 * Tab to specify the Ruby program to run/debug.
 */
public class RubyConnectTab extends AbstractLaunchConfigurationTab
{

	private Text fHostText;
	private Text fPortText;

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

		Label hostlabel = new Label(comp, SWT.NONE);
		hostlabel.setText(Messages.RubyConnectTab_HostLabel);
		GridData gd = new GridData(GridData.BEGINNING);
		hostlabel.setLayoutData(gd);
		hostlabel.setFont(font);

		fHostText = new Text(comp, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fHostText.setLayoutData(gd);
		fHostText.setFont(font);
		fHostText.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				updateLaunchConfigurationDialog();
			}
		});

		Label portLabel = new Label(comp, SWT.NONE);
		portLabel.setText(Messages.RubyConnectTab_PortLabel);
		gd = new GridData(GridData.BEGINNING);
		portLabel.setLayoutData(gd);
		portLabel.setFont(font);

		fPortText = new Text(comp, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fPortText.setLayoutData(gd);
		fPortText.setFont(font);
		fPortText.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				updateLaunchConfigurationDialog();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
	{
		configuration.setAttribute(IRubyLaunchConfigurationConstants.ATTR_REMOTE_HOST,
				IRubyLaunchConfigurationConstants.DEFAULT_REMOTE_HOST);
		configuration.setAttribute(IRubyLaunchConfigurationConstants.ATTR_REMOTE_PORT,
				IRubyLaunchConfigurationConstants.DEFAULT_REMOTE_PORT);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration)
	{
		try
		{
			String port = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_REMOTE_PORT,
					IRubyLaunchConfigurationConstants.DEFAULT_REMOTE_PORT);
			if (port != null)
			{
				fPortText.setText(port);
			}

			String host = configuration.getAttribute(IRubyLaunchConfigurationConstants.ATTR_REMOTE_HOST,
					IRubyLaunchConfigurationConstants.DEFAULT_REMOTE_HOST);
			if (host != null)
			{
				fHostText.setText(host);
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
		String host = fHostText.getText().trim();
		if (host.length() == 0)
		{
			host = null;
		}
		configuration.setAttribute(IRubyLaunchConfigurationConstants.ATTR_REMOTE_HOST, host);

		String port = fPortText.getText().trim();
		if (port.length() == 0)
		{
			port = null;
		}
		configuration.setAttribute(IRubyLaunchConfigurationConstants.ATTR_REMOTE_PORT, port);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName()
	{
		return Messages.RubyConnectTab_Name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public boolean isValid(ILaunchConfiguration launchConfig)
	{
		String host = fHostText.getText();
		if (host.length() == 0)
		{
			setMessage(Messages.RubyConnectTab_EmptyHostError);
		}

		String port = fPortText.getText();
		if (port.length() == 0)
		{
			setMessage(Messages.RubyConnectTab_EmptyPortError);
		}
		try
		{
			int portValue = Integer.parseInt(port);
			if (portValue < 1)
			{
				setMessage(Messages.RubyConnectTab_NegativePortError);
			}
		}
		catch (NumberFormatException e)
		{
			setMessage(Messages.RubyConnectTab_NonIntegerPortError);
		}
		return super.isValid(launchConfig);
	}
}
