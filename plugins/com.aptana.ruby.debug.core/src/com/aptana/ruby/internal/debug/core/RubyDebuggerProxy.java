package com.aptana.ruby.internal.debug.core;

import java.io.IOException;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;

import com.aptana.ruby.debug.core.IRubyLineBreakpoint;
import com.aptana.ruby.debug.core.IRubyMethodBreakpoint;
import com.aptana.ruby.debug.core.RubyDebugCorePlugin;
import com.aptana.ruby.debug.core.RubyDebugModel;
import com.aptana.ruby.debug.core.model.IEvaluationResult;
import com.aptana.ruby.debug.core.model.IRubyExceptionBreakpoint;
import com.aptana.ruby.debug.core.model.IRubyStackFrame;
import com.aptana.ruby.internal.debug.core.commands.AbstractDebuggerConnection;
import com.aptana.ruby.internal.debug.core.commands.BreakpointCommand;
import com.aptana.ruby.internal.debug.core.commands.ClassicDebuggerConnection;
import com.aptana.ruby.internal.debug.core.commands.ExceptionBreakpointCommand;
import com.aptana.ruby.internal.debug.core.commands.GenericCommand;
import com.aptana.ruby.internal.debug.core.commands.RubyDebugConnection;
import com.aptana.ruby.internal.debug.core.model.IRubyDebugTarget;
import com.aptana.ruby.internal.debug.core.model.RubyDebugTarget;
import com.aptana.ruby.internal.debug.core.model.RubyEvaluationResult;
import com.aptana.ruby.internal.debug.core.model.RubyProcessingException;
import com.aptana.ruby.internal.debug.core.model.RubyStackFrame;
import com.aptana.ruby.internal.debug.core.model.RubyThread;
import com.aptana.ruby.internal.debug.core.model.RubyVariable;
import com.aptana.ruby.internal.debug.core.model.ThreadInfo;
import com.aptana.ruby.internal.debug.core.parsing.AbstractReadStrategy;
import com.aptana.ruby.internal.debug.core.parsing.ErrorReader;
import com.aptana.ruby.internal.debug.core.parsing.FramesReader;
import com.aptana.ruby.internal.debug.core.parsing.LoadResultReader;
import com.aptana.ruby.internal.debug.core.parsing.SuspensionReader;
import com.aptana.ruby.internal.debug.core.parsing.ThreadInfoReader;
import com.aptana.ruby.internal.debug.core.parsing.VariableReader;

@SuppressWarnings("nls")
public class RubyDebuggerProxy
{

	// TODO What the heck is this "key" for?
	public final static String DEBUGGER_ACTIVE_KEY = "com.aptana.ruby.debug.ui.debuggerActive"; //$NON-NLS-1$

	private AbstractDebuggerConnection debuggerConnection;
	private IRubyDebugTarget debugTarget;
	private RubyLoop rubyLoop;
	private ICommandFactory commandFactory;
	private Thread threadUpdater;
	private Thread errorReader;
//	private boolean isLoopFinished;

	public RubyDebuggerProxy(IRubyDebugTarget debugTarget, boolean isRubyDebug)
	{
		this.debugTarget = debugTarget;
		debugTarget.setRubyDebuggerProxy(this);
		commandFactory = isRubyDebug ? new RubyDebugCommandFactory() : new ClassicDebuggerCommandFactory();
		debuggerConnection = isRubyDebug ? new RubyDebugConnection(debugTarget.getHost(), debugTarget.getPort())
				: new ClassicDebuggerConnection(debugTarget.getPort());
	}

	public boolean checkConnection()
	{
		return debuggerConnection.isCommandPortConnected();
	}

	public void start() throws RubyProcessingException, IOException
	{
//		isLoopFinished = false;
		debuggerConnection.connect();
		this.setBreakPoints();
		this.startRubyLoop();
	}

	public void stop() throws IOException
	{
		if (rubyLoop == null)
		{
			// only in tests, where no real connection is established
			return;
		}
		rubyLoop.setShouldStop();
		rubyLoop.interrupt();
		closeConnection();
	}

	protected void setBreakPoints() throws IOException
	{
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(
				RubyDebugModel.getModelIdentifier());
		for (int i = 0; i < breakpoints.length; i++)
		{
			this.addBreakpoint(breakpoints[i]);
		}
	}

	public void addBreakpoint(IBreakpoint breakpoint)
	{
		try
		{
			if (breakpoint.isEnabled())
			{
				if (breakpoint instanceof IRubyExceptionBreakpoint)
				{
					String command = commandFactory.createCatchOn((IRubyExceptionBreakpoint) breakpoint);
					new ExceptionBreakpointCommand(command).executeWithResult(debuggerConnection);
				}
				else if (breakpoint instanceof IRubyMethodBreakpoint)
				{
					IRubyMethodBreakpoint rubymethodBreakpoint = (IRubyMethodBreakpoint) breakpoint;
					String command = commandFactory.createAddMethodBreakpoint(rubymethodBreakpoint.getFileName(),
							rubymethodBreakpoint.getTypeName(), rubymethodBreakpoint.getMethodName(),
							rubymethodBreakpoint.getLineNumber());
					int index = new BreakpointCommand(command).executeWithResult(debuggerConnection);
					rubymethodBreakpoint.setIndex(index);
				}
				else if (breakpoint instanceof IRubyLineBreakpoint)
				{
					IRubyLineBreakpoint rubyLineBreakpoint = (IRubyLineBreakpoint) breakpoint;
					String command = commandFactory.createAddBreakpoint(rubyLineBreakpoint.getFileName(),
							rubyLineBreakpoint.getLineNumber());
					int index = new BreakpointCommand(command).executeWithResult(debuggerConnection);
					rubyLineBreakpoint.setIndex(index);
				}
			}
		}
		catch (IOException e)
		{
			RubyDebugCorePlugin.log(e);
		}
		catch (CoreException e)
		{
			RubyDebugCorePlugin.log(e);
		}
	}

	public void removeBreakpoint(IBreakpoint breakpoint)
	{
		try
		{
			if (breakpoint instanceof IRubyExceptionBreakpoint)
			{
				String command = commandFactory.createCatchOff((IRubyExceptionBreakpoint) breakpoint);
				if (command != null)
					new BreakpointCommand(command).execute(debuggerConnection);
			}
			else if (breakpoint instanceof IRubyLineBreakpoint)
			{
				IRubyLineBreakpoint rubyLineBreakpoint = (IRubyLineBreakpoint) breakpoint;
				if (rubyLineBreakpoint.getIndex() != -1)
				{
					String command = commandFactory.createRemoveBreakpoint(rubyLineBreakpoint.getIndex());
					// TODO: check for errors
					new BreakpointCommand(command).executeWithResult(debuggerConnection);
					rubyLineBreakpoint.setIndex(-1);
				}
			}
		}
		catch (IOException e)
		{
			RubyDebugCorePlugin.log(e);
		}
		catch (CoreException e)
		{
			RubyDebugCorePlugin.log(e);
		}

	}

	public void updateBreakpoint(IBreakpoint breakpoint, IMarkerDelta markerDelta)
	{
		this.removeBreakpoint(breakpoint);
		this.addBreakpoint(breakpoint);
	}

	public void startRubyLoop() throws DebuggerNotFoundException, IOException
	{
		debuggerConnection.start();
		rubyLoop = new RubyLoop();
		rubyLoop.start();
		Runnable runnable = new Runnable()
		{
			public void run()
			{
				try
				{
					RubyDebugCorePlugin.debug("Command Connection error handler started.");
					while (debuggerConnection.getCommandReadStrategy().isConnected())
					{
						// The read strategy resumes read() after the connection to the debugger
						// has been dropped
						new ErrorReader(debuggerConnection.getCommandReadStrategy()).read();
					}
				}
				catch (Exception e)
				{
					RubyDebugCorePlugin.log(e);
				}
				finally
				{
					RubyDebugCorePlugin.debug("Command Connection error handler finished.");
				}
			};
		};
		errorReader = new Thread(runnable, "Error Reader");
		errorReader.start();
		// TODO: Check if it would not be better if the ruby part created the threadinfos
		// only after a change to the thread status has occurred
		Runnable threadListener = new Runnable()
		{
			public void run()
			{
				try
				{
					RubyDebugCorePlugin.debug("Thread updater started.");
					Thread.sleep(2000);
					GenericCommand cmd = null;
					while (cmd == null || cmd.getReadStrategy().isConnected())
					{
						if (!getDebugTarget().isSuspended())
						{
							String command = commandFactory.createReadThreads();
							cmd = new GenericCommand(command, true /* isControl */);
							cmd.execute(debuggerConnection);
							ThreadInfo[] threadInfos = new ThreadInfoReader(cmd.getReadStrategy()).readThreads();
							((RubyDebugTarget) getDebugTarget()).updateThreads(threadInfos);
						}
						Thread.sleep(2000);
					}
				}
				catch (Exception e)
				{
					RubyDebugCorePlugin.log(e);
				}
				finally
				{
					RubyDebugCorePlugin.debug("Thread updater finished.");
				}
			};
		};
		threadUpdater = new Thread(threadListener, "Ruby Thread Updater");
		threadUpdater.start();
	}

	public void resume(RubyThread thread)
	{
		try
		{
			println(commandFactory.createResume(thread));
		}
		catch (IOException e)
		{
			// terminate ?
		}
	}

	protected void println(String s) throws IOException
	{
		try
		{
			// TOOD: GenericCommand is only temporary solution
			new GenericCommand(s, false /* isControl */).execute(debuggerConnection);
		}
		catch (IOException e)
		{
			RubyDebugCorePlugin.debug("Could not send to debugger. Exception occured.", e);
			throw e;
		}
	}

	protected IRubyDebugTarget getDebugTarget()
	{
		return debugTarget;
	}

	public RubyVariable[] readVariables(RubyStackFrame frame) throws DebugException
	{
		try
		{
			this.println(commandFactory.createReadLocalVariables(frame));
			return new VariableReader(getMultiReaderStrategy()).readVariables(frame);
		}
		catch (Exception e)
		{
			throw new DebugException(new Status(IStatus.ERROR, RubyDebugCorePlugin.getPluginIdentifier(), -1, e
					.getMessage(), e));
		}
	}

	public RubyVariable[] readInstanceVariables(RubyVariable variable)
	{
		try
		{
			this.println(commandFactory.createReadInstanceVariable(variable));
			return new VariableReader(getMultiReaderStrategy()).readVariables(variable);
		}
		catch (Exception ioex)
		{
			ioex.printStackTrace();
			throw new RuntimeException(ioex.getMessage());
		}
	}

	public RubyVariable readInspectExpression(IRubyStackFrame frame, String expression) throws RubyProcessingException
	{
		try
		{
			expression = expression.replaceAll("\\n", "\\\\n");
			RubyEvaluationResult result = new RubyEvaluationResult(expression, frame.getThread());
			this.println(commandFactory.createInspect(frame, expression));
			RubyVariable[] variables = new VariableReader(getMultiReaderStrategy()).readVariables(frame);
			if (variables.length == 0)
			{
				return null;
			}
			result.setValue(variables[0].getValue());
			return variables[0];
		}
		catch (IOException ioex)
		{
			ioex.printStackTrace();
			throw new RuntimeException(ioex.getMessage());
		}
	}

	public IEvaluationResult evaluate(RubyStackFrame frame, String expression)
	{
		expression = expression.replaceAll("\\r\\n", "\n");
		expression = expression.replaceAll("\\n", "; ");
		expression = expression.trim();
		RubyEvaluationResult result = new RubyEvaluationResult(expression, frame.getThread());
		try
		{
			this.println(commandFactory.createInspect(frame, expression));
			RubyVariable[] variables = new VariableReader(getMultiReaderStrategy()).readVariables(frame);
			if (variables.length > 0)
			{
				result.setValue(variables[0].getValue());
			}
		}
		catch (IOException ioex)
		{
			DebugException ex = new DebugException(new Status(IStatus.ERROR, RubyDebugCorePlugin.PLUGIN_ID,
					DebugException.INTERNAL_ERROR, ioex.getMessage(), ioex));
			result.setException(ex);
		}
		catch (RubyProcessingException e)
		{
			DebugException ex = new DebugException(new Status(IStatus.ERROR, RubyDebugCorePlugin.PLUGIN_ID,
					DebugException.TARGET_REQUEST_FAILED, e.getMessage(), e));
			result.setException(ex);
		}
		return result;
	}

	public void sendStepOverEnd(RubyStackFrame stackFrame)
	{
		try
		{
			this.println(commandFactory.createStepOver(stackFrame));
		}
		catch (Exception e)
		{
			RubyDebugCorePlugin.log(e);

		}
	}

	public void sendStepReturnEnd(RubyStackFrame stackFrame)
	{
		try
		{
			this.println(commandFactory.createStepReturn(stackFrame));
		}
		catch (Exception e)
		{
			RubyDebugCorePlugin.log(e);
		}
	}

	public void sendStepIntoEnd(RubyStackFrame stackFrame)
	{
		try
		{
			this.println(commandFactory.createStepInto(stackFrame));
		}
		catch (Exception e)
		{
			RubyDebugCorePlugin.log(e);
		}
	}

	public void sendThreadStop(RubyThread thread)
	{
		try
		{
			String command = commandFactory.createThreadStop(thread);
			new GenericCommand(command, true /* isControl */).execute(debuggerConnection);
		}
		catch (Exception e)
		{
			RubyDebugCorePlugin.log(e);
		}
	}

	public RubyStackFrame[] readFrames(RubyThread thread)
	{
		try
		{
			this.println(commandFactory.createReadFrames(thread));
			return new FramesReader(getMultiReaderStrategy()).readFrames(thread);
		}
		catch (IOException e)
		{
			RubyDebugCorePlugin.log(e);
			return null;
		}

	}

	public ThreadInfo[] readThreads()
	{
		try
		{
			String command = commandFactory.createReadThreads();
			new GenericCommand(command, true /* isControl */).execute(debuggerConnection);
			return new ThreadInfoReader(getMultiReaderStrategy()).readThreads();
		}
		catch (Exception e)
		{
			RubyDebugCorePlugin.log(e);
			return null;
		}
	}

	public IStatus readLoadResult(String filename)
	{
		try
		{
			this.println(commandFactory.createLoad(filename));
			return new LoadResultReader(getMultiReaderStrategy()).readLoadResult();
		}
		catch (Exception e)
		{
			return new Status(IStatus.ERROR, RubyDebugCorePlugin.getPluginIdentifier(), -1, e.getMessage(), e);
		}
	}

	public void closeConnection() throws IOException
	{
		debuggerConnection.exit();
	}

	private AbstractReadStrategy getMultiReaderStrategy()
	{
		return debuggerConnection.getCommandReadStrategy();
	}

	class RubyLoop extends Thread
	{

		public RubyLoop()
		{
			this.setName("RubyDebuggerLoop");
		}

		public void setShouldStop()
		{
		}

		public void run()
		{
			try
			{
				System.setProperty(DEBUGGER_ACTIVE_KEY, "true");

				RubyDebugCorePlugin.debug("Waiting for breakpoints.");
				while (true)
				{
					final SuspensionPoint hit = new SuspensionReader(getMultiReaderStrategy()).readSuspension();
					if (hit == null)
					{
						break;
					}
					RubyDebugCorePlugin.debug(hit);
					// TODO: should this be using the JOB API?
					new Thread()
					{

						public void run()
						{
							getDebugTarget().suspensionOccurred(hit);
						}
					}.start();
				}
			}
			catch (DebuggerNotFoundException ex)
			{
				throw ex;
			}
			catch (Exception ex)
			{
				RubyDebugCorePlugin.debug("Exception in socket reader loop.", ex);
			}
			finally
			{
				System.setProperty(DEBUGGER_ACTIVE_KEY, "false");
				try
				{
					getDebugTarget().terminate();
					closeConnection();
				}
				catch (Exception e)
				{
					RubyDebugCorePlugin.log(e);
				}
				RubyDebugCorePlugin.debug("Socket reader loop finished.");
			}
		}
	}

}
