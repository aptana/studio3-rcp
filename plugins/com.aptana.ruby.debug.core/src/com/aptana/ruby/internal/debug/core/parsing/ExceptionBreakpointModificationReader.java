package com.aptana.ruby.internal.debug.core.parsing;

import org.xmlpull.v1.XmlPullParser;

import com.aptana.ruby.debug.core.RubyDebugCorePlugin;

@SuppressWarnings("nls")
public class ExceptionBreakpointModificationReader extends XmlStreamReader
{

	private String exception;

	public ExceptionBreakpointModificationReader(XmlPullParser xpp)
	{
		super(xpp);
	}

	public ExceptionBreakpointModificationReader(AbstractReadStrategy readStrategy)
	{
		super(readStrategy);
	}

	public String readExceptionSet() throws NumberFormatException
	{
		try
		{
			this.read();
		}
		catch (Exception ex)
		{
			RubyDebugCorePlugin.log(ex);
			return "nil";
		}
		return exception;
	}

	@Override
	protected boolean processStartElement(XmlPullParser xpp) throws XmlStreamReaderException
	{
		boolean result = false;
		if (xpp.getName().equals("catchpointSet"))
		{
			exception = xpp.getAttributeValue("", "exception");
			result = true;
		}
		else if (xpp.getName().equals("error"))
		{
			exception = "nil";
			result = true;
		}
		return result;
	}

	@Override
	public void processContent(String text)
	{
	}

	@Override
	protected boolean processEndElement(XmlPullParser xpp)
	{
		return xpp.getName().equals("catchpointSet")
				|| xpp.getName().equals("error");
	}

}
