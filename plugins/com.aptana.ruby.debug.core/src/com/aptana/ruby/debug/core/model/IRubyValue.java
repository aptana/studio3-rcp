package org.rubypeople.rdt.debug.core.model;

import org.eclipse.debug.core.model.IValue;

public interface IRubyValue extends IValue
{

	IRubyVariable getOwner();

}
