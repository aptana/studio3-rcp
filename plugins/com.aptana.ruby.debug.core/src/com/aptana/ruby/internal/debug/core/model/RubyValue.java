package com.aptana.ruby.internal.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import com.aptana.ruby.debug.core.model.IRubyValue;

/**
 * @author Administrator
 */

public class RubyValue extends RubyDebugElement implements IRubyValue
{

	private String valueString;
	private String referenceTypeName;
	private boolean hasChildren;
	private RubyVariable owner;
	private RubyVariable[] variables;

	public RubyValue(RubyVariable owner)
	{
		this(owner, "nil", null, false); //$NON-NLS-1$
	}

	public RubyValue(RubyVariable owner, String valueString, String type, boolean hasChildren)
	{
		super(owner.getDebugTarget());
		this.valueString = valueString;
		if (type != null && type.equals("String")) //$NON-NLS-1$
		{
			this.valueString = '"' + this.valueString + '"';
		}
		else if (this.valueString.startsWith("Empty ")) //$NON-NLS-1$
		{
			this.valueString = this.valueString.substring(6) + "[0]"; //$NON-NLS-1$
		}
		else if (this.valueString.endsWith("element(s))")) //$NON-NLS-1$
		{
			int index = this.valueString.substring(0, this.valueString.length() - 11).lastIndexOf("("); //$NON-NLS-1$
			this.valueString = this.valueString.substring(0, index).trim() + "[" //$NON-NLS-1$
					+ this.valueString.substring(index + 1, this.valueString.length() - 11).trim() + "]"; //$NON-NLS-1$
		}
		else if (type != null && type.equals("Symbol")) //$NON-NLS-1$
		{
			this.valueString = ':' + this.valueString;
		}
		this.owner = owner;
		this.hasChildren = hasChildren;
		this.referenceTypeName = type;
	}

	/**
	 * @see org.eclipse.debug.core.model.IValue#getReferenceTypeName()
	 */
	public String getReferenceTypeName()
	{
		return this.referenceTypeName;
	}

	/**
	 * @see org.eclipse.debug.core.model.IValue#getValueString()
	 */
	public String getValueString()
	{
		return valueString;
	}

	/**
	 * @see org.eclipse.debug.core.model.IValue#isAllocated()
	 */
	public boolean isAllocated() throws DebugException
	{
		return false;
	}

	/**
	 * @see org.eclipse.debug.core.model.IValue#getVariables()
	 */
	public IVariable[] getVariables() throws DebugException
	{
		if (!hasChildren)
		{
			return new RubyVariable[0];
		}
		if (variables == null)
		{
			variables = ((RubyDebugTarget) this.getDebugTarget()).getRubyDebuggerProxy().readInstanceVariables(owner);
		}
		return variables;
	}

	/**
	 * @see org.eclipse.debug.core.model.IValue#hasVariables()
	 */
	public boolean hasVariables() throws DebugException
	{
		return hasChildren;
	}

	public String toString()
	{
		if (this.getReferenceTypeName() == null)
		{
			return this.getValueString();
		}
		return this.getValueString();
	}

	public RubyVariable getOwner()
	{
		return owner;
	}

}
