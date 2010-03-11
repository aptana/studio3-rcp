package com.aptana.ruby.debug.ui.launcher;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{

	private static final String BUNDLE_NAME = "com.aptana.radrails.debug.ui.launcher.messages"; //$NON-NLS-1$

	public static String RubyArgumentsTab_working_dir;
	public static String RubyArgumentsTab_working_dir_browser_message;
	public static String RubyArgumentsTab_interpreter_args_box_title;
	public static String RubyArgumentsTab_program_args_box_title;
	public static String RubyArgumentsTab_name;
	public static String RubyArgumentsTab_working_dir_error_message;

	static
	{
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
