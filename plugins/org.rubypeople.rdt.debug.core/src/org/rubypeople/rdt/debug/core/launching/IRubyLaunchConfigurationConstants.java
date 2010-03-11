package org.rubypeople.rdt.debug.core.launching;

/**
 * Constants for the Ruby debugger.
 */
@SuppressWarnings("nls")
public interface IRubyLaunchConfigurationConstants
{

	/**
	 * Unique identifier for the Ruby debug model (value <code>org.rubypeople.rdt.debug</code>).
	 */
	public static final String ID_RUBY_DEBUG_MODEL = "org.rubypeople.rdt.debug";

	/**
	 * Name of the string substitution variable that resolves to the location of a local Ruby executable (value
	 * <code>rubyExecutable</code>).
	 */
	public static final String ID_RUBY_EXECUTABLE = "rubyExecutable";

	public static final String ID_RUBY_APPLICATION = "org.rubypeople.rdt.debug.core.launchConfigurationType";

	public static final String ID_RUBY_SOURCE_LOCATOR = "org.rubypeople.rdt.debug.core.sourceLocator";

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
}
