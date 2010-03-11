package org.rubypeople.rdt.internal.debug.core;

public class StepSuspensionPoint extends SuspensionPoint
{
	private int framesNumber;

	public boolean isBreakpoint()
	{
		return false;
	}

	public boolean isException()
	{
		return false;
	}

	public boolean isStep()
	{
		return true;
	}

	public String toString()
	{
		return "Step end at " + this.getPosition();
	}

	public int getFramesNumber()
	{
		return framesNumber;
	}

	public void setFramesNumber(int framesNumber)
	{
		this.framesNumber = framesNumber;
	}

}
