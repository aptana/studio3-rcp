/**
 * Copyright (c) 2005-2009 Aptana, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html. If redistributing this code,
 * this entire header must remain intact.
 */
package com.aptana.radrails.rcp.main.preferences;

import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.help.internal.base.HelpBasePlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.internal.browser.BrowserManager;
import org.eclipse.ui.internal.browser.IBrowserDescriptor;
import org.osgi.framework.Version;
import org.osgi.service.prefs.BackingStoreException;

import com.aptana.radrails.rcp.main.MainPlugin;

/**
 * Class used to initialize default preference values.
 */
@SuppressWarnings("restriction")
public class PreferenceInitializer extends AbstractPreferenceInitializer
{

	/**
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences()
	{
		IEclipsePreferences prefs = new DefaultScope().getNode(HelpBasePlugin.PLUGIN_ID);
		prefs.putBoolean(getOpenInEditorKey(), true);
		try
		{
			prefs.flush();
		}
		catch (BackingStoreException e1)
		{
			MainPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, MainPlugin.PLUGIN_ID, -1, e1.getMessage(), e1));
		}

		// Force "auto-refresh" pref to true by default for RadRails
		prefs = new DefaultScope().getNode(ResourcesPlugin.PI_RESOURCES);
		prefs.putBoolean(ResourcesPlugin.PREF_AUTO_REFRESH, true);
		try
		{
			prefs.flush();
		}
		catch (BackingStoreException e1)
		{
			MainPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, MainPlugin.PLUGIN_ID, -1, e1.getMessage(), e1));
		}

		IPreferenceStore store = MainPlugin.getDefault().getPreferenceStore();
		store.setDefault(IPreferenceConstants.REOPEN_EDITORS_ON_STARTUP, true);

		if (store.getBoolean(IPreferenceConstants.WORKSPACE_ENCODING_SET) == false)
		{
			try
			{
				ResourcesPlugin.getWorkspace().getRoot().setDefaultCharset("UTF-8", null); //$NON-NLS-1$
			}
			catch (CoreException e)
			{
				// TODO logging
				MainPlugin.getDefault().getLog().log(
						new Status(IStatus.ERROR, MainPlugin.getDefault().getBundle().getSymbolicName(), IStatus.OK,
								Messages.PreferenceInitializer_Cannot_Set_Default_Encoding, e));
			}
			store.setValue(IPreferenceConstants.WORKSPACE_ENCODING_SET, true);
		}

		// Attempt to set the default browser to Firefox so that external links
		// in portal pages will work out of the box
		if (Platform.OS_LINUX.equals(Platform.getOS()))
		{
			try
			{
				BrowserManager browserManager = BrowserManager.getInstance();
				List<?> webBrowsers = browserManager.getWebBrowsers();
				for (Object browserObject : webBrowsers)
				{
					if (browserObject instanceof IBrowserDescriptor)
					{
						IBrowserDescriptor browserDescriptor = (IBrowserDescriptor) browserObject;
						if ("Firefox".equals(browserDescriptor.getName())) { //$NON-NLS-1$
							browserManager.setCurrentWebBrowser(browserDescriptor);
							break;
						}
					}
				}
			}
			catch (Throwable t)
			{
				// Ignore
			}
		}
	}

	private String getOpenInEditorKey()
	{
		String rawVersion = (String) Platform.getBundle("org.eclipse.core.runtime").getHeaders().get("Bundle-Version"); //$NON-NLS-1$ //$NON-NLS-2$
		Version v = new Version(rawVersion);
		if (v.compareTo(new Version("3.5.0")) >= 0) { //$NON-NLS-1$
			return "in editor"; // 3.5 //$NON-NLS-1$
		}
		return "open_in_browser"; //$NON-NLS-1$
	}
}
