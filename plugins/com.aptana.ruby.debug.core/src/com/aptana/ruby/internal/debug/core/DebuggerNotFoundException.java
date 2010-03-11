package com.aptana.ruby.internal.debug.core;

public class DebuggerNotFoundException extends RuntimeException
{

	private static final long serialVersionUID = -7048656572730937337L;

	public DebuggerNotFoundException(String message)
	{
		super(message);
	}

	public DebuggerNotFoundException()
	{
		super("Could not connect to debugger.");
	}
}
