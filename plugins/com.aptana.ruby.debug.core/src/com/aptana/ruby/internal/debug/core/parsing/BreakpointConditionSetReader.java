package com.aptana.ruby.internal.debug.core.parsing;

import org.xmlpull.v1.XmlPullParser;

import com.aptana.ruby.debug.core.RubyDebugCorePlugin;

@SuppressWarnings("nls")
public class BreakpointConditionSetReader extends XmlStreamReader
{

	private String index;

	public BreakpointConditionSetReader(XmlPullParser xpp)
	{
		super(xpp);
	}

	public BreakpointConditionSetReader(AbstractReadStrategy readStrategy)
	{
		super(readStrategy);
	}

	public int readExceptionSet() throws NumberFormatException
	{
		try
		{
			this.read();
			return Integer.parseInt(index);
		}
		catch (Exception ex)
		{
			RubyDebugCorePlugin.log(ex);
		}
		return -1;
	}

	@Override
	protected boolean processStartElement(XmlPullParser xpp) throws XmlStreamReaderException
	{
		boolean result = false;
		if (xpp.getName().equals("conditionSet"))
		{
			index = xpp.getAttributeValue("", "bp_id");
			result = true;
		}
		else if (xpp.getName().equals("error"))
		{
			index = "-1";
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
		return xpp.getName().equals("conditionSet") || xpp.getName().equals("error");
	}

}
