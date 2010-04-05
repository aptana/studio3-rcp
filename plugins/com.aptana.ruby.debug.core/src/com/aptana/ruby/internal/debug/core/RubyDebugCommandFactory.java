package com.aptana.ruby.internal.debug.core;

import org.eclipse.core.runtime.CoreException;

import com.aptana.ruby.debug.core.IRubyLineBreakpoint;
import com.aptana.ruby.debug.core.model.IRubyExceptionBreakpoint;
import com.aptana.ruby.debug.core.model.IRubyStackFrame;
import com.aptana.ruby.internal.debug.core.model.RubyStackFrame;
import com.aptana.ruby.internal.debug.core.model.RubyThread;
import com.aptana.ruby.internal.debug.core.model.RubyVariable;

@SuppressWarnings("nls")
public class RubyDebugCommandFactory implements ICommandFactory
{

	public String createReadFrames(RubyThread thread)
	{
		return "w";
	}

	public String createReadLocalVariables(RubyStackFrame frame)
	{
		return "frame " + frame.getIndex() + "; v l";
	}

	public String createReadGlobalVariables()
	{
		return "v g";
	}

	public String createReadInstanceVariable(RubyVariable variable)
	{
		StringBuilder command = new StringBuilder();
		// FIXME Uncomment!
		// if (!variable.isGlobal()) {
		// command.append("frame " + variable.getFrame().getIndex() + "; ");
		// }
		return command.append("v i " + variable.getObjectId()).toString();
	}

	public String createStepOver(RubyStackFrame frame)
	{
		return "frame " + frame.getIndex() + "; next";
	}

	public String createForcedStepOver(RubyStackFrame frame)
	{
		return "frame " + frame.getIndex() + "; next+";
	}

	public String createStepReturn(RubyStackFrame frame)
	{
		return "frame " + frame.getIndex() + "; finish";
	}

	public String createStepInto(RubyStackFrame frame)
	{
		return "frame " + frame.getIndex() + "; step";
	}

	public String createForcedStepInto(RubyStackFrame frame)
	{
		return "frame " + frame.getIndex() + "; step+";
	}

	public String createReadThreads()
	{
		return "th l";
	}

	public String createLoad(String filename)
	{
		return "load " + filename;
	}

	public String createInspect(IRubyStackFrame frame, String expression)
	{
		return "frame " + frame.getIndex() + "; v inspect " + expression.replaceAll(";", "\\;");
	}

	public String createResume(RubyThread thread)
	{
		return "cont";
	}

	public String createAddBreakpoint(String file, int line)
	{
		StringBuffer setBreakPointCommand = new StringBuffer();
		setBreakPointCommand.append("b ");
		setBreakPointCommand.append(file);
		setBreakPointCommand.append(":");
		setBreakPointCommand.append(line);
		return setBreakPointCommand.toString();
	}

	public String createAddMethodBreakpoint(String file, String type, String method, int line)
	{
		StringBuffer setBreakPointCommand = new StringBuffer();
		setBreakPointCommand.append("b ");
		setBreakPointCommand.append(type);
		setBreakPointCommand.append(".");
		setBreakPointCommand.append(method);
		return setBreakPointCommand.toString();
	}

	public String createRemoveBreakpoint(int index)
	{
		return "delete " + index;
	}

	public String createCatchOff(IRubyExceptionBreakpoint breakpoint) throws CoreException
	{
		return "catch " + breakpoint.getTypeName() + " off";
	}

	public String createCatchOn(IRubyExceptionBreakpoint breakpoint) throws CoreException
	{
		return "catch " + breakpoint.getTypeName();
	}

	public String createThreadStop(RubyThread thread)
	{
		return "thread stop " + thread.getId();
	}

	public String createSetCondition(IRubyLineBreakpoint lineBreakpoint) throws CoreException
	{
		return "condition " + lineBreakpoint.getIndex() + " " + lineBreakpoint.getCondition();
	}
}
