package com.aptana.ruby.debug.ui.launcher;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{

	private static final String BUNDLE_NAME = "com.aptana.radrails.debug.ui.launcher.messages"; //$NON-NLS-1$

	public static String LaunchConfigurationTab_RubyArguments_working_dir;
	public static String LaunchConfigurationTab_RubyArguments_working_dir_browser_message;
	public static String LaunchConfigurationTab_RubyArguments_working_dir_use_default_message;
	public static String LaunchConfigurationTab_RubyArguments_interpreter_args_box_title;
	public static String LaunchConfigurationTab_RubyArguments_program_args_box_title;
	public static String LaunchConfigurationTab_RubyArguments_name;
	public static String LaunchConfigurationTab_RubyArguments_working_dir_error_message;

	static
	{
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
