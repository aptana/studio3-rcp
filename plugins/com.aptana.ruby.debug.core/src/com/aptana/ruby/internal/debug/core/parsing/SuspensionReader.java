package com.aptana.ruby.internal.debug.core.parsing;

import java.io.IOException;
import java.text.MessageFormat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.aptana.ruby.debug.core.RubyDebugCorePlugin;
import com.aptana.ruby.internal.debug.core.BreakpointSuspensionPoint;
import com.aptana.ruby.internal.debug.core.ExceptionSuspensionPoint;
import com.aptana.ruby.internal.debug.core.StepSuspensionPoint;
import com.aptana.ruby.internal.debug.core.SuspensionPoint;

public class SuspensionReader extends XmlStreamReader
{
	private SuspensionPoint suspensionPoint;

	public SuspensionReader(XmlPullParser xpp)
	{
		super(xpp);
	}

	public SuspensionReader(AbstractReadStrategy readStrategy)
	{
		super(readStrategy);
	}

	public SuspensionPoint readSuspension() throws XmlPullParserException, IOException, XmlStreamReaderException
	{
		this.read();
		return suspensionPoint;
	}

	@SuppressWarnings("nls")
	protected boolean processStartElement(XmlPullParser xpp) throws XmlStreamReaderException
	{
		String name = xpp.getName();
		if (name.equals("breakpoint"))
		{
			suspensionPoint = new BreakpointSuspensionPoint();
		}
		else if (name.equals("exception"))
		{
			ExceptionSuspensionPoint exceptionPoint = new ExceptionSuspensionPoint();
			exceptionPoint.setExceptionMessage(xpp.getAttributeValue("", "message"));
			exceptionPoint.setExceptionType(xpp.getAttributeValue("", "type"));
			suspensionPoint = exceptionPoint;
		}
		else if (name.equals("suspended"))
		{
			StepSuspensionPoint stepPoint = new StepSuspensionPoint();
			String frameNoAttribute = xpp.getAttributeValue("", "frames");
			try
			{
				stepPoint.setFramesNumber(Integer.parseInt(frameNoAttribute));
				suspensionPoint = stepPoint;
			}
			catch (NumberFormatException nfe)
			{
				RubyDebugCorePlugin.debug(MessageFormat.format("Could not parse: {0}, {1}", frameNoAttribute, xpp
						.getText()));
				return false;
			}
		}
		else
		{
			return false;
		}
		int line = 0;
		try
		{
			line = Integer.parseInt(xpp.getAttributeValue("", "line"));
		}
		catch (NumberFormatException e)
		{
			RubyDebugCorePlugin.log(e);
		}
		suspensionPoint.setLine(line);
		suspensionPoint.setFile(xpp.getAttributeValue("", "file"));
		suspensionPoint.setThreadId(Integer.parseInt(xpp.getAttributeValue("", "threadId")));
		return true;
	}

}
