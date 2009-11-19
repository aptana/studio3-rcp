/**
 * Copyright (c) 2005-2009 Aptana, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html. If redistributing this code,
 * this entire header must remain intact.
 */
package com.aptana.radrails.rcp.main;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class MainPlugin extends AbstractUIPlugin {

    public final static String PLUGIN_ID = "com.aptana.radrails.rcp.main"; //$NON-NLS-1$

    // The shared instance.
    private static MainPlugin plugin;

    /**
     * The constructor.
     */
    public MainPlugin() {
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
     * @return MainPlugin
     */
    public static MainPlugin getDefault() {
        return plugin;
    }
}
