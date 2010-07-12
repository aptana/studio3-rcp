/**
 * Copyright (c) 2005-2010 Aptana, Inc. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html. If redistributing this code, this entire header must remain intact.
 */
package com.aptana.rcp;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class IdePlugin extends AbstractUIPlugin {

    public static String PLUGIN_ID = "com.aptana.rcp"; //$NON-NLS-1$

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
}
