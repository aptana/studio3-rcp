package com.aptana.ruby.internal.debug.core.commands;

import java.io.IOException;

import com.aptana.ruby.internal.debug.core.DebuggerNotFoundException;
import com.aptana.ruby.internal.debug.core.parsing.AbstractReadStrategy;
import com.aptana.ruby.internal.debug.core.parsing.SuspensionReader;

public class RubyDebugConnection extends AbstractDebuggerConnection
{

	private boolean isStarted;

	public RubyDebugConnection(String host, int port)
	{
		super(host, port);
	}

	@Override
	public void connect() throws DebuggerNotFoundException, IOException
	{
		createCommandConnection();
	}

	@Override
	public SuspensionReader start() throws DebuggerNotFoundException, IOException
	{
		AbstractReadStrategy strategy = sendControlCommand(new GenericCommand("start", true)); //$NON-NLS-1$
		isStarted = true;
		return new SuspensionReader(strategy);
	}

	private AbstractReadStrategy sendControlCommand(AbstractCommand command) throws IOException
	{
		return sendCommand(command);
	}

	@Override
	public void exit() throws IOException
	{
		GenericCommand command = new GenericCommand("exit", true); //$NON-NLS-1$
		command.execute(this);
	}

	@Override
	public boolean isStarted()
	{
		return isStarted;
	}

}
