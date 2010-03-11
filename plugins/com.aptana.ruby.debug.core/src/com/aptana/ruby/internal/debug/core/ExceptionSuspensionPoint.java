package com.aptana.ruby.internal.debug.core;

public class ExceptionSuspensionPoint extends SuspensionPoint
{
	private String exceptionMessage;
	private String exceptionType;

	public boolean isBreakpoint()
	{
		return false;
	}

	public boolean isException()
	{
		return true;
	}

	public boolean isStep()
	{
		return false;
	}

	public String toString()
	{
		return this.getExceptionType() + " occurred :" + this.getExceptionMessage();
	}

	public String getExceptionMessage()
	{
		return exceptionMessage;
	}

	public String getExceptionType()
	{
		return exceptionType;
	}

	public void setExceptionMessage(String exceptionMessage)
	{
		this.exceptionMessage = exceptionMessage;
	}

	public void setExceptionType(String exceptionType)
	{
		this.exceptionType = exceptionType;
	}

}
