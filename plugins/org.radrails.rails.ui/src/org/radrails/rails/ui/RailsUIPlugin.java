/*******************************************************************************
 * Copyright (c) 2005-2010 Aptana
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.radrails.rails.ui;

import java.util.Hashtable;

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
 * @author mkent
 */
public class RailsUIPlugin extends AbstractUIPlugin
{
	private static RailsUIPlugin instance;

	private static final String PLUGIN_ID = "org.radrails.rails.ui"; //$NON-NLS-1$

	private static Hashtable<String, Image> images = new Hashtable<String, Image>();

	/**
	 * Default constructor.
	 */
	public RailsUIPlugin()
	{
		super();
		instance = this;
	}

	/**
	 * getImage
	 * 
	 * @param path
	 * @return Image
	 */
	public static Image getImage(String path)
	{
		if (images.get(path) == null)
		{
			ImageDescriptor id = getImageDescriptor(path);
			if (id == null)
			{
				return null;
			}
			Image i = id.createImage();
			images.put(path, i);
			return i;
		}
		return images.get(path);
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
	 * Returns an image descriptor for the image file at the given plug-in relative path.
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path)
	{
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(final BundleContext context) throws Exception
	{
		super.start(context);
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

	public static void logError(Exception e)
	{
		if (e instanceof CoreException)
			logError((CoreException) e);
		else
			getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
	}

	public static void logError(CoreException e)
	{
		getDefault().getLog().log(e.getStatus());
	}
}
