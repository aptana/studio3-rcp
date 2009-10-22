/*******************************************************************************
 * Copyright (c) 2005 RadRails.org and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.radrails.rails.ui;

import java.util.Hashtable;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Plugin class for the rails.ui plugin.
 * 
 * @author mkent
 */
public class RailsUIPlugin extends AbstractUIPlugin
{

	/**
	 * Whether or not to show jobs in progress view/bar. If false, jobs will be set to "System" so they won't get shown.
	 */
	private static final boolean DISPLAY_JOBS = true;

	private static RailsUIPlugin instance;

	private static final String PLUGIN_ID = "org.radrails.rails.ui";

	private static Hashtable<String, Image> images = new Hashtable<String, Image>();

	// TODO When/if the RailsDBConnector is working properly then uncomment this.
	// private RailsDBConnector connector;
	private ServiceTracker gemManagerTracker;

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
		else
		{
			return (Image) images.get(path);
		}
	}

	/**
	 * Returns the singleton instance of the plugin.
	 * 
	 * @return - the plugin
	 */
	public static RailsUIPlugin getInstance()
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

}
