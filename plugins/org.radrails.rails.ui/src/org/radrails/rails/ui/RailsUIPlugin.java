/*******************************************************************************
 * Copyright (c) 2005-2010 Aptana
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.radrails.rails.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * Plugin class for the rails.ui plugin.
 * 
 * @author cwilliams
 * @author mkent
 */
public class RailsUIPlugin extends AbstractUIPlugin
{
	private static RailsUIPlugin instance;

	private static final String PLUGIN_ID = "org.radrails.rails.ui"; //$NON-NLS-1$

	public static Image getImage(String string)
	{
		if (getDefault().getImageRegistry().get(string) == null)
		{
			ImageDescriptor id = imageDescriptorFromPlugin(PLUGIN_ID, string);
			if (id != null)
			{
				getDefault().getImageRegistry().put(string, id);
			}
		}
		return getDefault().getImageRegistry().get(string);
	}

	/**
	 * Returns the singleton instance of the plugin.
	 * 
	 * @return - the plugin
	 */
	public static RailsUIPlugin getDefault()
	{
		return instance;
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(final BundleContext context) throws Exception
	{
		super.start(context);
		instance = this;
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception
	{
		super.stop(context);
	}

	/**
	 * Get plugin identifier
	 * 
	 * @return - plugin id
	 */
	public static String getPluginIdentifier()
	{
		return PLUGIN_ID;
	}

	public static void logError(String message, Exception e)
	{
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, message, e));
	}

	public static void logError(Exception e)
	{
		if (e instanceof CoreException)
		{
			logError((CoreException) e);
		}
		else
		{
			logError(e.getMessage(), e);
		}
	}

	public static void logError(CoreException e)
	{
		getDefault().getLog().log(e.getStatus());
	}
}
