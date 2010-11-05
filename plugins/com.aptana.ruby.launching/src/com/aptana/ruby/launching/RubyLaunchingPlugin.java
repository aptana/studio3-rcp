/**
 * This file Copyright (c) 2005-2010 Aptana, Inc. This program is
 * dual-licensed under both the Aptana Public License and the GNU General
 * Public license. You may elect to use one or the other of these licenses.
 * 
 * This program is distributed in the hope that it will be useful, but
 * AS-IS and WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, TITLE, or
 * NONINFRINGEMENT. Redistribution, except as permitted by whichever of
 * the GPL or APL you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or modify this
 * program under the terms of the GNU General Public License,
 * Version 3, as published by the Free Software Foundation.  You should
 * have received a copy of the GNU General Public License, Version 3 along
 * with this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Aptana provides a special exception to allow redistribution of this file
 * with certain other free and open source software ("FOSS") code and certain additional terms
 * pursuant to Section 7 of the GPL. You may view the exception and these
 * terms on the web at http://www.aptana.com/legal/gpl/.
 * 
 * 2. For the Aptana Public License (APL), this program and the
 * accompanying materials are made available under the terms of the APL
 * v1.0 which accompanies this distribution, and is available at
 * http://www.aptana.com/legal/apl/.
 * 
 * You may view the GPL, Aptana's exception and additional terms, and the
 * APL in the file titled license.html at the root of the corresponding
 * plugin containing this source file.
 * 
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.ruby.launching;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.aptana.core.util.ExecutableUtil;

public class RubyLaunchingPlugin implements BundleActivator
{

	private static final String RUBYW = "rubyw"; //$NON-NLS-1$
	private static final String RUBY = "ruby"; //$NON-NLS-1$

	private static BundleContext context;

	static BundleContext getContext()
	{
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception
	{
		RubyLaunchingPlugin.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception
	{
		RubyLaunchingPlugin.context = null;
	}

	public static IPath rubyExecutablePath()
	{
		IPath path = null;
		if (Platform.OS_WIN32.equals(Platform.getOS()))
		{
			path = ExecutableUtil.find(RUBYW, true, getCommonRubyBinaryLocations(RUBYW));
		}
		if (path == null)
		{
			path = ExecutableUtil.find(RUBY, true, getCommonRubyBinaryLocations(RUBY));
		}
		// TODO check TM_RUBY env value too?
		return path;
	}

	/**
	 * Return an ordered list of common locations that you'd find a ruby binary.
	 * 
	 * @return
	 */
	private static List<IPath> getCommonRubyBinaryLocations(String binaryName)
	{
		List<IPath> locations = new ArrayList<IPath>();
		if (Platform.OS_WIN32.equals(Platform.getOS()))
		{
			locations.add(Path.fromOSString("C:\\ruby\\bin").append(binaryName).addFileExtension("exe")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
		{
			locations.add(Path.fromOSString("/opt/local/bin/").append(binaryName)); //$NON-NLS-1$
			locations.add(Path.fromOSString("/usr/local/bin/").append(binaryName)); //$NON-NLS-1$
			locations.add(Path.fromOSString("/usr/bin/").append(binaryName)); //$NON-NLS-1$
		}
		if (Platform.OS_MACOSX.equals(Platform.getOS()))
		{
			locations.add(Path.fromOSString("/System/Library/Frameworks/Ruby.framework/Versions/Current/usr/bin/") //$NON-NLS-1$
					.append(binaryName));
		}
		return locations;
	}
}
