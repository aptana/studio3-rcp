package com.aptana.ruby.debug.core.launching;

/**
 * Constants for the Ruby debugger.
 */
@SuppressWarnings("nls")
public interface IRubyLaunchConfigurationConstants
{

	/**
	 * Unique identifier for the Ruby debug model (value <code>com.aptana.ruby.debug</code>).
	 */
	public static final String ID_RUBY_DEBUG_MODEL = "com.aptana.ruby.debug";

	/**
	 * Name of the string substitution variable that resolves to the location of a local Ruby executable (value
	 * <code>rubyExecutable</code>).
	 */
	public static final String ID_RUBY_EXECUTABLE = "rubyExecutable";

	public static final String ID_RUBY_APPLICATION = "com.aptana.ruby.debug.core.launchConfigurationType";

	public static final String ID_RUBY_SOURCE_LOCATOR = "com.aptana.ruby.debug.core.sourceLocator";

	/**
	 * Launch configuration key. Value is a path to a ruby program. The path is a string representing a full path to a
	 * ruby program in the workspace.
	 */
	public static final String ATTR_FILE_NAME = ID_RUBY_DEBUG_MODEL + ".ATTR_FILE_NAME";

	/**
	 * Working directory to use when launching the program.
	 */
	public static final String ATTR_WORKING_DIRECTORY = ID_RUBY_DEBUG_MODEL + ".ATTR_WORKING_DIR";

	/**
	 * Arguments to pass to the Ruby binary.
	 */
	public static final String ATTR_VM_ARGUMENTS = ID_RUBY_DEBUG_MODEL + ".ATTR_VM_ARGUMENTS";

	/**
	 * Arguments to pass to the file we're executing.
	 */
	public static final String ATTR_PROGRAM_ARGUMENTS = ID_RUBY_DEBUG_MODEL + ".ATTR_PROGRAM_ARGUMENTS";

	/**
	 * Hostname of the remote host we're trying to attach the debugger to.
	 */
	public static final String ATTR_REMOTE_HOST = ID_RUBY_DEBUG_MODEL + ".ATTR_REMOTE_HOST";

	/**
	 * Default value for ATTR_REMOTE_HOST: localhost.
	 */
	public static final String DEFAULT_REMOTE_HOST = "localhost";

	/**
	 * Port of the remote debugger we're trying to attach to.
	 */
	public static final String ATTR_REMOTE_PORT = ID_RUBY_DEBUG_MODEL + ".ATTR_REMOTE_PORT";

	/**
	 * Status code indicating a launch configuration does not specify a host name value
	 */
	public static final int ERR_UNSPECIFIED_HOSTNAME = 109;

	/**
	 * Status code indicating a launch configuration does not specify a port number value
	 */
	public static final int ERR_UNSPECIFIED_PORT = 111;

	/**
	 * Status code indicating an attempt to connect to a remote VM has failed or an attempt to listen for a remote VM
	 * connecting has failed.
	 */
	public static final int ERR_REMOTE_VM_CONNECTION_FAILED = 113;

	public static final String DEFAULT_REMOTE_PORT = "1234";
}
