package com.aptana.ruby.internal.debug.core.model;

import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.debug.core.model.IDebugTarget;

import com.aptana.ruby.debug.core.RubyDebugModel;

public class RubyDebugElement extends DebugElement
{

	public RubyDebugElement(IDebugTarget target)
	{
		super(target);
	}

	public String getModelIdentifier()
	{
		return RubyDebugModel.getModelIdentifier();
	}

}
