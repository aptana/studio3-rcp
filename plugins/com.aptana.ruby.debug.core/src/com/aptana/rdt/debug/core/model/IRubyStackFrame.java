package org.rubypeople.rdt.debug.core.model;

import org.eclipse.debug.core.model.IStackFrame;
import org.rubypeople.rdt.internal.debug.core.RubyDebuggerProxy;

public interface IRubyStackFrame extends IStackFrame
{

	String getFileName();

	IEvaluationResult evaluate(String expressionText);

	/**
	 * @deprecated Will be removed once code is cleaned up!
	 * @return
	 */
	RubyDebuggerProxy getRubyDebuggerProxy();

	int getIndex();

}
