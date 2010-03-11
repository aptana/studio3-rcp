package org.rubypeople.rdt.debug.core.model;

import org.eclipse.debug.core.model.IVariable;

public interface IRubyVariable extends IVariable
{

	boolean isHashValue();

	boolean isConstant();

	boolean isStatic();

	String getQualifiedName();

	IRubyVariable getParent();

	String getObjectId();

	IRubyStackFrame getStackFrame();

}
