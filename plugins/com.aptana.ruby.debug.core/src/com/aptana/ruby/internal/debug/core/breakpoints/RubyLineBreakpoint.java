package com.aptana.ruby.internal.debug.core.breakpoints;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;

import com.aptana.ruby.debug.core.IRubyLineBreakpoint;
import com.aptana.ruby.debug.core.RubyDebugModel;

public class RubyLineBreakpoint extends RubyBreakpoint implements IRubyLineBreakpoint
{
	// TODO Move this constant to some public interface...
	public static final String RUBY_LINE_BREAKPOINT = "com.aptana.ruby.debug.core.rubyLineBreakpointMarker"; //$NON-NLS-1$

	/**
	 * Breakpoint attribute storing a breakpoint's conditional expression (value
	 * <code>"com.aptana.ruby.debug.core.condition"</code>). This attribute is stored as a <code>String</code>.
	 */
	protected static final String CONDITION = "com.aptana.ruby.debug.core.condition"; //$NON-NLS-1$
	/**
	 * Breakpoint attribute storing a breakpoint's condition enabled state (value
	 * <code>"com.aptana.ruby.debug.core.conditionEnabled"</code>). This attribute is stored as an <code>boolean</code>.
	 */
	protected static final String CONDITION_ENABLED = "com.aptana.ruby.debug.core.conditionEnabled"; //$NON-NLS-1$

	/**
	 * Breakpoint attribute storing a breakpoint's condition suspend policy (value
	 * <code>" com.aptana.ruby.debug.core.conditionSuspendOnTrue"
	 * </code>). This attribute is stored as an <code>boolean</code>.
	 */
	protected static final String CONDITION_SUSPEND_ON_TRUE = "com.aptana.ruby.debug.core.conditionSuspendOnTrue"; //$NON-NLS-1$

	public static final String EXTERNAL_FILENAME = "externalFileName"; //$NON-NLS-1$
	private int index = -1; // index of breakpoint on ruby debugger side

	public RubyLineBreakpoint()
	{
	}

	/**
	 * @param typeName
	 * @see RDtDebugModel#createLineBreakpoint(IResource, String, int, int, int, int, boolean, Map)
	 */
	public RubyLineBreakpoint(IResource resource, String fileName, String typeName, int lineNumber, int charStart,
			int charEnd, int hitCount, boolean add, Map<String, Object> attributes) throws DebugException
	{
		this(resource, fileName, typeName, lineNumber, charStart, charEnd, hitCount, add, attributes,
				RUBY_LINE_BREAKPOINT);
	}

	protected RubyLineBreakpoint(final IResource resource, final String fileName, final String typeName,
			final int lineNumber, final int charStart, final int charEnd, final int hitCount, final boolean add,
			final Map<String, Object> attributes, final String markerType) throws DebugException
	{

		IWorkspaceRunnable wr = new IWorkspaceRunnable()
		{
			public void run(IProgressMonitor monitor) throws CoreException
			{
				// create the marker
				setMarker(resource.createMarker(RUBY_LINE_BREAKPOINT));
				if (resource.equals(ResourcesPlugin.getWorkspace().getRoot()))
				{
					attributes.put(EXTERNAL_FILENAME, fileName);
				}
				// add attributes
				addLineBreakpointAttributes(attributes, getModelIdentifier(), true, lineNumber, charStart, charEnd);
				addTypeNameAndHitCount(attributes, typeName, hitCount);
				// set attributes
				// attributes.put(SUSPEND_POLICY, new Integer(getDefaultSuspendPolicy()));
				ensureMarker().setAttributes(attributes);

				// add to breakpoint manager if requested
				register(add);

			}
		};
		run(getMarkerRule(resource), wr);
	}

	/**
	 * Adds the standard attributes of a line breakpoint to the given attribute map. The standard attributes are:
	 * <ol>
	 * <li>IBreakpoint.ID</li>
	 * <li>IBreakpoint.ENABLED</li>
	 * <li>IMarker.LINE_NUMBER</li>
	 * <li>IMarker.CHAR_START</li>
	 * <li>IMarker.CHAR_END</li>
	 * </ol>
	 */
	public void addLineBreakpointAttributes(Map<String, Object> attributes, String modelIdentifier, boolean enabled,
			int lineNumber, int charStart, int charEnd)
	{
		attributes.put(IBreakpoint.ID, modelIdentifier);
		attributes.put(IBreakpoint.ENABLED, Boolean.valueOf(enabled));
		attributes.put(IMarker.LINE_NUMBER, new Integer(lineNumber));
		attributes.put(IMarker.CHAR_START, new Integer(charStart));
		attributes.put(IMarker.CHAR_END, new Integer(charEnd));
	}

	/**
	 * Adds type name and hit count attributes to the given map. If <code>hitCount > 0</code>, adds the
	 * <code>HIT_COUNT</code> attribute to the given breakpoint, and resets the <code>EXPIRED</code> attribute to false
	 * (since, if the hit count is changed, the breakpoint should no longer be expired).
	 */
	public void addTypeNameAndHitCount(Map<String, Object> attributes, String typeName, int hitCount)
	{
		attributes.put(TYPE_NAME, typeName);
		if (hitCount > 0)
		{
			attributes.put(HIT_COUNT, new Integer(hitCount));
			attributes.put(EXPIRED, Boolean.FALSE);
		}
	}

	public String getFileName() throws CoreException
	{
		IResource resource = ensureMarker().getResource();
		if (resource.equals(ResourcesPlugin.getWorkspace().getRoot()))
		{
			return ensureMarker().getAttribute(EXTERNAL_FILENAME, "");
		}
		return resource.getName();
	}

	public int getLineNumber() throws CoreException
	{
		return ensureMarker().getAttribute(IMarker.LINE_NUMBER, -1);
	}

	public int getCharStart() throws CoreException
	{
		return ensureMarker().getAttribute(IMarker.CHAR_START, -1);
	}

	public int getCharEnd() throws CoreException
	{
		return ensureMarker().getAttribute(IMarker.CHAR_END, -1);
	}

	/**
	 * Returns the type of marker associated with this type of breakpoints
	 */
	public static String getMarkerType()
	{
		return RUBY_LINE_BREAKPOINT;
	}

	public String getModelIdentifier()
	{
		return RubyDebugModel.getModelIdentifier();
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public String getCondition() throws CoreException
	{
		return ensureMarker().getAttribute(CONDITION, null);
	}

	public boolean isConditionEnabled() throws CoreException
	{
		return ensureMarker().getAttribute(CONDITION_ENABLED, false);
	}

	public boolean isConditionSuspendOnTrue() throws DebugException
	{
		return ensureMarker().getAttribute(CONDITION_SUSPEND_ON_TRUE, true);
	}

	public void setCondition(String condition) throws CoreException
	{
		if (condition != null && condition.trim().length() == 0)
		{
			condition = null;
		}
		setAttributes(new String[] { CONDITION }, new Object[] { condition });
		recreate();
	}

	public void setConditionEnabled(boolean conditionEnabled) throws CoreException
	{
		setAttributes(new String[] { CONDITION_ENABLED }, new Object[] { Boolean.valueOf(conditionEnabled) });
		recreate();
	}

	public void setConditionSuspendOnTrue(boolean suspendOnTrue) throws CoreException
	{
		if (isConditionSuspendOnTrue() != suspendOnTrue)
		{
			setAttributes(new String[] { CONDITION_SUSPEND_ON_TRUE }, new Object[] { Boolean.valueOf(suspendOnTrue) });
			recreate();
		}
	}

	public boolean supportsCondition()
	{
		return true;
	}

}
