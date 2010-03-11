package com.aptana.ruby.internal.debug.core.parsing;

import java.io.IOException;

import com.aptana.ruby.debug.core.RubyDebugCorePlugin;
import com.aptana.ruby.internal.debug.core.BreakpointSuspensionPoint;
import com.aptana.ruby.internal.debug.core.ExceptionSuspensionPoint;
import com.aptana.ruby.internal.debug.core.StepSuspensionPoint;
import com.aptana.ruby.internal.debug.core.SuspensionPoint;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SuspensionReader extends XmlStreamReader {
	private SuspensionPoint suspensionPoint;

	public SuspensionReader(XmlPullParser xpp) {
		super(xpp);
	}

	public SuspensionReader(AbstractReadStrategy readStrategy) {
		super(readStrategy);
	}

	public SuspensionPoint readSuspension() throws XmlPullParserException,
			IOException, XmlStreamReaderException {
		this.read();
		return suspensionPoint;
	}

	protected boolean processStartElement(XmlPullParser xpp)
			throws XmlStreamReaderException {
		String name = xpp.getName();
		if (name.equals("breakpoint")) {
			suspensionPoint = new BreakpointSuspensionPoint();
		} else if (name.equals("exception")) {
			ExceptionSuspensionPoint exceptionPoint = new ExceptionSuspensionPoint();
			exceptionPoint.setExceptionMessage(xpp.getAttributeValue("",
					"message"));
			exceptionPoint.setExceptionType(xpp.getAttributeValue("", "type"));
			suspensionPoint = exceptionPoint;
		} else if (name.equals("suspended")) {
			StepSuspensionPoint stepPoint = new StepSuspensionPoint();
			String frameNoAttribute = xpp.getAttributeValue("", "frames");
			try {
				stepPoint.setFramesNumber(Integer.parseInt(frameNoAttribute));
				suspensionPoint = stepPoint;
			} catch (NumberFormatException nfe) {
				String message = "Could not parse: " + frameNoAttribute + ", "
						+ xpp.getText();
				RubyDebugCorePlugin.debug(message);
				return false;
			}
		} else {
			return false;
		}
		suspensionPoint.setLine(xpp.getAttributeValue("",
				"line"));
		suspensionPoint.setFile(xpp.getAttributeValue("", "file"));
		suspensionPoint.setThreadId(Integer.parseInt(xpp.getAttributeValue("",
				"threadId")));
		return true;
	}

}
