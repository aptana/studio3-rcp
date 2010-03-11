package com.aptana.ruby.internal.debug.core.parsing;

import com.aptana.ruby.debug.core.RubyDebugCorePlugin;
import org.xmlpull.v1.XmlPullParser;

public class ErrorReader extends XmlStreamReader {

	public ErrorReader(XmlPullParser xpp) {
		super(xpp);
	}

	public ErrorReader(AbstractReadStrategy readStrategy) {
		super(readStrategy);
	}

	@Override
	protected boolean processStartElement(XmlPullParser xpp)
			throws XmlStreamReaderException {
		return xpp.getName().equals("error") || xpp.getName().equals("message") ;
	}

	@Override
	public void processContent(String text) {
		RubyDebugCorePlugin.log(text,null) ;
	}
	@Override
	protected boolean processEndElement(XmlPullParser xpp) {
		return xpp.getName().equals("error")|| xpp.getName().equals("message") ;
	}
	
	

}
