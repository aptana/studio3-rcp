package com.aptana.ruby.internal.debug.core;

import org.eclipse.core.runtime.CoreException;

import com.aptana.ruby.debug.core.IRubyLineBreakpoint;
import com.aptana.ruby.debug.core.model.IRubyExceptionBreakpoint;
import com.aptana.ruby.debug.core.model.IRubyStackFrame;
import com.aptana.ruby.internal.debug.core.model.RubyStackFrame;
import com.aptana.ruby.internal.debug.core.model.RubyThread;
import com.aptana.ruby.internal.debug.core.model.RubyVariable;

public interface ICommandFactory
{

	public String createReadFrames(RubyThread thread);

	public String createReadLocalVariables(RubyStackFrame frame);

	public String createReadInstanceVariable(RubyVariable variable);

	public String createStepOver(RubyStackFrame stackFrame);

	public String createForcedStepOver(RubyStackFrame stackFrame);

	public String createStepReturn(RubyStackFrame stackFrame);

	public String createStepInto(RubyStackFrame stackFrame);

	public String createForcedStepInto(RubyStackFrame stackFrame);

	public String createReadThreads();

	public String createThreadStop(RubyThread thread);

	public String createInspect(IRubyStackFrame frame, String expression);

	public String createResume(RubyThread thread);

	public String createAddBreakpoint(String file, int line);

	public String createRemoveBreakpoint(int index);

	public String createCatchOff(IRubyExceptionBreakpoint rubyExceptionBreakpoint) throws CoreException;

	public String createCatchOn(IRubyExceptionBreakpoint breakpoint) throws CoreException;

	public String createLoad(String filename);

	public String createAddMethodBreakpoint(String fileName, String typeName, String methodName, int line);

	public String createSetCondition(IRubyLineBreakpoint lineBreakpoint) throws CoreException;
}
