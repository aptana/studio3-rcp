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
	/**
	 * Launch configuration key. Value is a path to a ruby program. The path is a string representing a full path to a
	 * ruby program in the workspace.
	 */
	public static final String ATTR_FILE_NAME = ID_RUBY_DEBUG_MODEL + ".ATTR_FILE_NAME";

	public static final String ID_RUBY_APPLICATION = "org.rubypeople.rdt.debug.core.launchConfigurationType";

	public static final String ID_RUBY_SOURCE_LOCATOR = "org.rubypeople.rdt.debug.core.sourceLocator";
}
