package com.aptana.ruby.internal.debug.core.parsing;

import java.io.IOException;

import com.aptana.ruby.debug.core.RubyDebugCorePlugin;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SingleReaderStrategy extends AbstractReadStrategy {

	public SingleReaderStrategy(XmlPullParser xpp) {
		super(xpp);
	}

	public void readElement(XmlStreamReader streamReader) throws XmlPullParserException, IOException, XmlStreamReaderException  {
	
		int eventType = xpp.getEventType();
		do {
			if (eventType == XmlPullParser.START_DOCUMENT) {
				RubyDebugCorePlugin.debug("Start document");
			} else if (eventType == XmlPullParser.END_DOCUMENT) {
				RubyDebugCorePlugin.debug("End document");
				break ;
			} else if (eventType == XmlPullParser.START_TAG) {
				streamReader.processStartElement(xpp);
			} else if (eventType == XmlPullParser.END_TAG) {
				streamReader.processEndElement(xpp);
				if (xpp.getDepth() == 1) {
					break ;	
				}
			} else if (eventType == XmlPullParser.TEXT) {
				//processText(xpp);
			}
			eventType = xpp.next();
		} while (true);
	}

	@Override
	public void readElement(XmlStreamReader streamReader, long maxWaitTime) throws XmlPullParserException, IOException, XmlStreamReaderException {
		readElement(streamReader) ;
	}

	@Override
	public boolean isConnected() {
		return true;
	}

}
