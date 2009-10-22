/**
 * Copyright (c) 2005-2006 Aptana, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html. If redistributing this code,
 * this entire header must remain intact.
 */
package com.aptana.radrails.rcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.cheatsheets.ICheatSheetViewer;
import org.eclipse.ui.internal.cheatsheets.CheatSheetPlugin;
import org.eclipse.ui.internal.cheatsheets.data.CheatSheetParser;
import org.eclipse.ui.internal.cheatsheets.registry.CheatSheetElement;
import org.eclipse.ui.internal.cheatsheets.registry.CheatSheetRegistryReader;
import org.osgi.framework.Bundle;

/**
 * @author Paul Colton
 */
public class AptanaCheatSheetViewer implements ICheatSheetViewer
{
	Browser browser;
	URL contentURL;
	CheatSheetElement contentElement;
	CheatSheetParser parser;
	String cheatSheet;
	DocumentBuilder documentBuilder;

	/**
	 * AptanaCheatSheetViewer
	 */
	public AptanaCheatSheetViewer()
	{
		documentBuilder = CheatSheetPlugin.getPlugin().getDocumentBuilder();
	}

	/**
	 * @see org.eclipse.ui.cheatsheets.ICheatSheetViewer#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPartControl(Composite parent)
	{
		browser = new Browser(parent, SWT.NONE);

		browser.addDisposeListener(new DisposeListener()
		{
			public void widgetDisposed(DisposeEvent e)
			{
				dispose();
			}
		});
	}

	/**
	 * @see org.eclipse.ui.cheatsheets.ICheatSheetViewer#getControl()
	 */
	public Control getControl()
	{
		return browser;
	}

	/**
	 * @see org.eclipse.ui.cheatsheets.ICheatSheetViewer#getCheatSheetID()
	 */
	public String getCheatSheetID()
	{
		if (getContent() != null)
		{
			return getContent().getID();
		}

		return null;
	}

	private CheatSheetElement getContent()
	{
		return contentElement;
	}

	/**
	 * @see org.eclipse.ui.cheatsheets.ICheatSheetViewer#setFocus()
	 */
	public void setFocus()
	{
		if (browser != null)
		{
			browser.setFocus();
		}
	}

	/**
	 * @see org.eclipse.ui.cheatsheets.ICheatSheetViewer#setInput(java.lang.String)
	 */
	public void setInput(String id)
	{
		if (id != null)
		{
			CheatSheetElement element = null;
			element = CheatSheetRegistryReader.getInstance().findCheatSheet(id);
			browser.setText(getContent(element));
		}
	}

	/**
	 * @see org.eclipse.ui.cheatsheets.ICheatSheetViewer#setInput(java.lang.String, java.lang.String, java.net.URL)
	 */
	public void setInput(String id, String name, URL url)
	{
		if (url != null)
		{
			browser.setUrl(url.toString());
			browser.update();
		}
		else
		{
			setInput(id);
		}
	}

	private String getContent(CheatSheetElement element)
	{
		// Set the current content to new content
		contentElement = element;
		contentURL = null;

		if (element != null)
		{

			Bundle bundle = null;
			if (element != null && element.getConfigurationElement() != null)
			{
				try
				{
					String pluginId = element.getConfigurationElement().getContributor().getName();
					bundle = Platform.getBundle(pluginId);
				}
				catch (Exception e)
				{
					// do nothing
				}
			}
			if (bundle != null)
			{
				contentURL = FileLocator.find(bundle, new Path(element.getContentFile()), null);
			}

			if (contentURL == null)
			{
				URL checker;
				try
				{
					checker = new URL(element.getContentFile());
					if (checker.getProtocol().equalsIgnoreCase("http")) //$NON-NLS-1$
					{
						contentURL = checker;
					}
				}
				catch (MalformedURLException mue)
				{
				}
			}
		}

		return readFile();
	}

	private void dispose()
	{
	}

	private String readFile()
	{
		InputStream is = null;

		try
		{
			is = contentURL.openStream();

		}
		catch (Exception e)
		{
			// TODO logging
			IdePlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR,
							IdePlugin.getDefault().getBundle().getSymbolicName(),
							IStatus.OK,
							 MessageFormat.format(Messages.AptanaCheatSheetViewer_ErrorOpeningFile, contentURL.getFile()),
							e)
					);
			return null;
		}

		StringBuffer sb = new StringBuffer();

		int c = -1;

		try
		{
			while ((c = is.read()) != -1)
			{
				sb.append((char) c);
			}
		}
		catch (IOException e)
		{
			// TODO logging
			IdePlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR,
							IdePlugin.getDefault().getBundle().getSymbolicName(),
							IStatus.OK,
							 MessageFormat.format(Messages.AptanaCheatSheetViewer_UnableToReadFile, contentURL.getFile()),
							e)
					);
		}

		return sb.toString();
	}

	/**
	 * reset
	 * 
	 * @param cheatSheetData
	 */
	public void reset(Map cheatSheetData)
	{
		// TODO Auto-generated method stub
	}
}
