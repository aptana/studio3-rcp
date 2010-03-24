package com.aptana.ruby.internal.debug.core.commands;

import com.aptana.ruby.internal.debug.core.parsing.AbstractReadStrategy;
import com.aptana.ruby.internal.debug.core.parsing.BreakpointConditionSetReader;
import com.aptana.ruby.internal.debug.core.parsing.XmlStreamReader;

public class BreakpointConditionSetCommand extends AbstractCommand
{

	public BreakpointConditionSetCommand(String command)
	{
		super(command, true);
	}

	@Override
	protected XmlStreamReader createResultReader(AbstractReadStrategy readStrategy)
	{
		return new BreakpointConditionSetReader(readStrategy);
	}

}
