package org.rubypeople.rdt.internal.debug.core;

public abstract class SuspensionPoint
{
	private String file;
	private String line;
	private int threadId;

	public SuspensionPoint()
	{
	}

	public String getFile()
	{
		return file;
	}

	public String getLine()
	{
		return line;
	}

	public void setFile(String file)
	{
		this.file = file;
	}

	public void setLine(String line)
	{
		this.line = line;
	}

	public String getPosition()
	{
		return this.getFile() + ":" + this.getLine();
	}

	public abstract String toString();

	public abstract boolean isException();

	public abstract boolean isStep();

	public abstract boolean isBreakpoint();

	public int getThreadId()
	{
		return threadId;
	}

	public void setThreadId(int threadId)
	{
		this.threadId = threadId;
	}

}
