/**
 * Copyright (c) 2005-2009 Aptana, Inc. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html. If redistributing this code, this entire header must remain intact.
 */
package com.aptana.radrails.rcp;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;

import com.aptana.radrails.rcp.preferences.IPreferenceConstants;

/**
 * The main plugin class to be used in the desktop.
 */
public class IdePlugin extends AbstractUIPlugin {

    public static String PLUGIN_ID = "com.aptana.radrails.rcp"; //$NON-NLS-1$

    // The shared instance.
    private static IdePlugin plugin;

    /**
     * The constructor.
     */
    public IdePlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     * 
     * @param context
     * @throws Exception
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        initPreferences();
    }

    /**
     * This method is called when the plug-in is stopped
     * 
     * @param context
     * @throws Exception
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     * 
     * @return IdePlugin
     */
    public static IdePlugin getDefault() {
        return plugin;
    }

	/**
	 * Log exception as error.
	 *
	 * @param e
	 */
	public static void logError(Exception e)
	{
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
	}

	private void initPreferences()
	{
		// Force "auto-refresh" pref to true by default for RadRails
		IEclipsePreferences prefs = new DefaultScope().getNode(ResourcesPlugin.PI_RESOURCES);
		prefs.putBoolean(ResourcesPlugin.PREF_AUTO_REFRESH, true);

		if (!Platform.getPreferencesService().getBoolean(PLUGIN_ID, IPreferenceConstants.WORKSPACE_ENCODING_SET, false,
				null))
		{
			try
			{
				ResourcesPlugin.getWorkspace().getRoot().setDefaultCharset("UTF-8", null); //$NON-NLS-1$
			}
			catch (CoreException e)
			{
				getDefault().getLog().log(
						new Status(IStatus.ERROR, getDefault().getBundle().getSymbolicName(), IStatus.OK,
								Messages.PreferenceInitializer_Cannot_Set_Default_Encoding, e));
			}
			prefs = (new InstanceScope()).getNode(PLUGIN_ID);
			prefs.putBoolean(IPreferenceConstants.WORKSPACE_ENCODING_SET, true);
			try
			{
				prefs.flush();
			}
			catch (BackingStoreException e)
			{
			}
		}
	}
}
