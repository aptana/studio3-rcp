package com.aptana.ruby.internal.debug.core.breakpoints;

import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import com.aptana.ruby.debug.core.IRubyMethodBreakpoint;

public class RubyMethodBreakpoint extends RubyLineBreakpoint implements IRubyMethodBreakpoint
{
	// TODO Move this constant to some public interface...
	private static final String RUBY_METHOD_BREAKPOINT = "com.aptana.ruby.debug.rubyMethodBreakpointMarker"; //$NON-NLS-1$

	/**
	 * Breakpoint attribute storing the name of the method in which a breakpoint is contained. (value
	 * <code>"com.aptana.ruby.debug.core.methodName"</code>). This attribute is a <code>String</code>.
	 */
	private static final String METHOD_NAME = "com.aptana.ruby.debug.core.methodName"; //$NON-NLS-1$	

	/**
	 * Breakpoint attribute storing whether this breakpoint is an entry breakpoint. (value
	 * <code>"com.aptana.ruby.debug.core.entry"</code>). This attribute is a <code>boolean</code>.
	 */
	private static final String ENTRY = "com.aptana.ruby.debug.core.entry"; //$NON-NLS-1$	

	/**
	 * Breakpoint attribute storing whether this breakpoint is an exit breakpoint. (value
	 * <code>"com.aptana.ruby.debug.core.exit"</code>). This attribute is a <code>boolean</code>.
	 */
	private static final String EXIT = "com.aptana.ruby.debug.core.exit"; //$NON-NLS-1$	

	/**
	 * Cache of method name attribute
	 */
	private String fMethodName = null;

	/**
	 * Used to match type names
	 */
	private Pattern fPattern;

	/**
	 * Constructs a new unconfigured method breakpoint
	 */
	public RubyMethodBreakpoint()
	{
	}

	public RubyMethodBreakpoint(final IResource resource, final String typePattern, final String methodName,
			final boolean entry, final boolean exit, final int lineNumber, final int charStart, final int charEnd,
			final int hitCount, final boolean register, final Map<String, Object> attributes) throws CoreException
	{
		IWorkspaceRunnable wr = new IWorkspaceRunnable()
		{
			public void run(IProgressMonitor monitor) throws CoreException
			{
				// create the marker
				setMarker(resource.createMarker(RUBY_METHOD_BREAKPOINT));

				// add attributes
				addLineBreakpointAttributes(attributes, getModelIdentifier(), true, lineNumber, charStart, charEnd);
				addMethodNameAndSignature(attributes, methodName, null);
				addTypeNameAndHitCount(attributes, typePattern, hitCount);
				attributes.put(ENTRY, Boolean.valueOf(entry));
				attributes.put(EXIT, Boolean.valueOf(exit));
				// attributes.put(SUSPEND_POLICY, new Integer(getDefaultSuspendPolicy()));
				// set attributes
				ensureMarker().setAttributes(attributes);
				register(register);
			}

		};
		run(getMarkerRule(resource), wr);
		String type = convertToRegularExpression(typePattern);
		fPattern = Pattern.compile(type);
	}

	/**
	 * Adds the method name and signature attributes to the given attribute map, and initializes the local cache of
	 * method name and signature.
	 */
	private void addMethodNameAndSignature(Map<String, Object> attributes, String methodName, String methodSignature)
	{
		if (methodName != null)
		{
			attributes.put(METHOD_NAME, methodName);
		}
		// if (methodSignature != null) {
		// attributes.put(METHOD_SIGNATURE, methodSignature);
		// }
		fMethodName = methodName;
		// fMethodSignature= methodSignature;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaMethodEntryBreakpoint#getMethodName()
	 */
	public String getMethodName()
	{
		return fMethodName;
	}

	/**
	 * Initialize cache of attributes
	 * 
	 * @see org.eclipse.debug.core.model.IBreakpoint#setMarker(IMarker)
	 */
	public void setMarker(IMarker marker) throws CoreException
	{
		super.setMarker(marker);
		fMethodName = marker.getAttribute(METHOD_NAME, null);
		// fMethodSignature = marker.getAttribute(METHOD_SIGNATURE, null);
		String typePattern = marker.getAttribute(TYPE_NAME, ""); //$NON-NLS-1$
		if (typePattern != null)
		{
			fPattern = Pattern.compile(convertToRegularExpression(typePattern));
		}
	}

	/**
	 * converts the specified string to one which has been formated to our needs
	 * 
	 * @param stringMatcherPattern
	 *            the initial pattern
	 * @return the modified pattern
	 */
	private String convertToRegularExpression(String stringMatcherPattern)
	{
		String regex = stringMatcherPattern.replaceAll("\\.", "\\\\."); //$NON-NLS-1$//$NON-NLS-2$
		regex = regex.replaceAll("\\*", "\\.\\*"); //$NON-NLS-1$//$NON-NLS-2$
		regex = regex.replaceAll("\\$", "\\\\\\$"); //$NON-NLS-1$ //$NON-NLS-2$
		return regex;
	}
}
