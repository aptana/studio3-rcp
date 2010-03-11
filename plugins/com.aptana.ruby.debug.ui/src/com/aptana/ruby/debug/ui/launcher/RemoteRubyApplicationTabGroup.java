package com.aptana.ruby.debug.ui.launcher;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;

/**
 * Tab group for attaching to a remote debuuger session for a Ruby program
 */
public class RemoteRubyApplicationTabGroup extends AbstractLaunchConfigurationTabGroup
{
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTabGroup#createTabs(org.eclipse.debug.ui.ILaunchConfigurationDialog,
	 * java.lang.String)
	 */
	public void createTabs(ILaunchConfigurationDialog dialog, String mode)
	{
		setTabs(new ILaunchConfigurationTab[] { new RubyConnectTab(), new SourceLookupTab(), new CommonTab() });
	}
}
