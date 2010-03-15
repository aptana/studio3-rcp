package com.aptana.ruby.internal.debug.core.commands;

import java.io.IOException;

import com.aptana.ruby.internal.debug.core.DebuggerNotFoundException;
import com.aptana.ruby.internal.debug.core.parsing.AbstractReadStrategy;
import com.aptana.ruby.internal.debug.core.parsing.ExceptionBreakpointModificationReader;
import com.aptana.ruby.internal.debug.core.parsing.XmlStreamReader;

public class ExceptionBreakpointCommand extends BreakpointCommand
{

	public ExceptionBreakpointCommand(String command)
	{
		super(command);
	}

	@Override
	protected XmlStreamReader createResultReader(AbstractReadStrategy readStrategy)
	{
		return new ExceptionBreakpointModificationReader(readStrategy);
	}

	public int executeWithResult(AbstractDebuggerConnection connection) throws DebuggerNotFoundException, IOException
	{
		execute(connection);
		((ExceptionBreakpointModificationReader) getResultReader()).readExceptionSet();
		return -1;
	}
}
