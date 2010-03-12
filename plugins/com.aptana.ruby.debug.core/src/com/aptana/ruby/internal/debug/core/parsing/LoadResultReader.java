package com.aptana.ruby.internal.debug.core.parsing;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.xmlpull.v1.XmlPullParser;

import com.aptana.ruby.debug.core.RubyDebugCorePlugin;
import com.aptana.ruby.internal.debug.core.model.RubyProcessingException;

@SuppressWarnings("nls")
public class LoadResultReader extends XmlStreamReader
{

	private LoadResult loadResult;

	public LoadResultReader(XmlPullParser xpp)
	{
		super(xpp);
	}

	public LoadResultReader(AbstractReadStrategy readStrategy)
	{
		super(readStrategy);
	}

	public IStatus readLoadResult() throws RubyProcessingException
	{
		this.loadResult = new LoadResult();
		try
		{
			this.read();
		}
		catch (Exception ex)
		{
			RubyDebugCorePlugin.log(ex);
		}
		int code = IStatus.ERROR;
		if (loadResult.isOk())
			code = IStatus.OK;
		StringBuilder builder = new StringBuilder();
		if (loadResult.exceptionType != null)
			builder.append(loadResult.exceptionType).append(": ");
		builder.append(loadResult.exceptionMessage);
		return new Status(code, RubyDebugCorePlugin.PLUGIN_ID, -1, builder.toString(), null);
	}

	protected boolean processStartElement(XmlPullParser xpp)
	{
		String name = xpp.getName();
		if (name.equals("loadResult"))
		{
			this.loadResult.setFileName(xpp.getAttributeValue("", "fileName"));
			this.loadResult.setExceptionType(xpp.getAttributeValue("", "exceptionType"));
			this.loadResult.setExceptionMessage(xpp.getAttributeValue("", "exceptionMessage"));
			return true;
		}
		return false;
	}

	public class LoadResult
	{
		private String fileName;
		private String exceptionMessage;
		private String exceptionType;

		public String getExceptionMessage()
		{
			return exceptionMessage;
		}

		public void setExceptionMessage(String exceptionMessage)
		{
			this.exceptionMessage = exceptionMessage;
		}

		public String getExceptionType()
		{
			return exceptionType;
		}

		public void setExceptionType(String exceptionType)
		{
			this.exceptionType = exceptionType;
		}

		public String getFileName()
		{
			return fileName;
		}

		public void setFileName(String fileName)
		{
			this.fileName = fileName;
		}

		public boolean isOk()
		{
			return exceptionType == null;
		}
	}

}
