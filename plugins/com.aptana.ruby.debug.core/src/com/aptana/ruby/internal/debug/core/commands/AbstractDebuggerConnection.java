package com.aptana.ruby.internal.debug.core.commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.aptana.ruby.debug.core.RubyDebugCorePlugin;
import com.aptana.ruby.debug.core.launching.IRubyLaunchConfigurationConstants;
import com.aptana.ruby.internal.debug.core.DebuggerNotFoundException;
import com.aptana.ruby.internal.debug.core.parsing.AbstractReadStrategy;
import com.aptana.ruby.internal.debug.core.parsing.MultiReaderStrategy;
import com.aptana.ruby.internal.debug.core.parsing.SuspensionReader;

public abstract class AbstractDebuggerConnection
{

	private static final String PARSER_CLASSNAMES = "org.kxml2.io.KXmlParser,org.kxml2.io.KXmlSerializer"; //$NON-NLS-1$
	private static final String ENCODING = "UTF-8"; //$NON-NLS-1$

	private int commandPort;
	private Socket commandSocket;
	private PrintWriter writer;
	private AbstractReadStrategy commandReadStrategy;
	private String host;

	public AbstractDebuggerConnection(int port)
	{
		this(IRubyLaunchConfigurationConstants.DEFAULT_REMOTE_HOST, port);
	}

	public AbstractDebuggerConnection(String host, int port)
	{
		super();
		this.host = host;
		this.commandPort = port;
	}

	/*
	 * connect to the debugger. After the connection is established the debugger listens for control commands
	 */
	public abstract void connect() throws DebuggerNotFoundException, IOException;

	/*
	 * start the debugger. Must be connected to start.
	 */
	public abstract SuspensionReader start() throws DebuggerNotFoundException, IOException;

	public abstract boolean isStarted();

	/*
	 * always call via Command.execute
	 */
	protected AbstractReadStrategy sendCommand(AbstractCommand command) throws DebuggerNotFoundException, IOException
	{
		if (!isCommandPortConnected())
		{
			throw new IllegalStateException(command + " could not be sent since command socket is not open"); //$NON-NLS-1$
		}
		RubyDebugCorePlugin.debug("Sending command: " + command.getCommand()); //$NON-NLS-1$
		getWriter().println(command.getCommand());
		return getCommandReadStrategy();
	}

	public AbstractReadStrategy getCommandReadStrategy()
	{
		return commandReadStrategy;
	}

	protected void createCommandConnection() throws DebuggerNotFoundException, IOException
	{
		getSocket();
		XmlPullParser xpp = createXpp(commandSocket);
		commandReadStrategy = new MultiReaderStrategy(xpp);
	}

	public boolean isCommandPortConnected()
	{
		return commandSocket != null;
	}

	protected Socket getSocket() throws IOException, DebuggerNotFoundException
	{

		if (commandSocket == null)
		{
			commandSocket = acquireSocket(host, commandPort);
			if (commandSocket == null)
			{
				throw new DebuggerNotFoundException("Could not connect to debugger on port " + commandPort); //$NON-NLS-1$
			}
		}
		return commandSocket;
	}

	protected static Socket acquireSocket(String host, int port) throws IOException
	{
		Socket socket = null;
		int tryCount = 50;
		for (int i = 0; i < tryCount; i++)
		{
			try
			{
				socket = new Socket(host, port);
				break;
			}
			catch (IOException e)
			{
				try
				{
					Thread.sleep(500);
				}
				catch (InterruptedException e1)
				{
				}
			}
		}
		return socket;

	}

	private PrintWriter getWriter() throws IOException, DebuggerNotFoundException
	{
		if (writer == null)
		{
			writer = new PrintWriter(commandSocket.getOutputStream(), true);
		}
		return writer;
	}

	protected static XmlPullParser createXpp(Socket socket)
	{
		XmlPullParser xpp = null;
		try
		{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance(PARSER_CLASSNAMES, null);
			xpp = factory.newPullParser();
			xpp.setInput(socket.getInputStream(), ENCODING);
		}
		catch (XmlPullParserException e)
		{
			// TODO: log
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return xpp;
	}

	public int getCommandPort()
	{
		return commandPort;
	}

	public void exit() throws IOException
	{
		if (commandSocket != null)
		{
			commandSocket.close();
		}
	}

}
