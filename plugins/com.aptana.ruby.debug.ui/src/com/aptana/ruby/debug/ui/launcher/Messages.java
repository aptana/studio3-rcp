package com.aptana.ruby.debug.ui.launcher;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{

	private static final String BUNDLE_NAME = Messages.class.getPackage().getName() + ".messages"; //$NON-NLS-1$

	public static String ResourceSelector_BrowseButtonLabel;

	public static String RubyArgumentsTab_working_dir;
	public static String RubyArgumentsTab_working_dir_browser_message;
	public static String RubyArgumentsTab_interpreter_args_box_title;
	public static String RubyArgumentsTab_program_args_box_title;
	public static String RubyArgumentsTab_name;
	public static String RubyArgumentsTab_working_dir_error_message;
	public static String RubyConnectTab_EmptyHostError;
	public static String RubyConnectTab_EmptyPortError;
	public static String RubyConnectTab_HostLabel;
	public static String RubyConnectTab_Name;
	public static String RubyConnectTab_NegativePortError;
	public static String RubyConnectTab_NonIntegerPortError;
	public static String RubyConnectTab_PortLabel;
	public static String RubyMainTab_EmptyFileError;
	public static String RubyMainTab_FileDoesntExistError;
	public static String RubyMainTab_FileLabel;
	public static String RubyMainTab_Name;
	public static String RubyMainTab_OpenFileDialogMessage;
	public static String RubyMainTab_OpenFileDialogTitle;

	static
	{
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
