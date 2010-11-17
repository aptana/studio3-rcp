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
package org.radrails.rails.internal.ui.commands;

import java.text.MessageFormat;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;

import com.aptana.terminal.views.TerminalView;

public class RunServerHandler extends AbstractRailsHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		IProject railsProject = getProject(event);
		if (railsProject == null)
		{
			return null;
		}
		// now determine which version so we can tell what to run...
		String viewId = MessageFormat.format("{0} script/rails server", railsProject //$NON-NLS-1$
				.getName());
		String command = "script/rails server"; //$NON-NLS-1$
		if (scriptServerExists(railsProject))
		{
			viewId = MessageFormat.format("{0} script/server", railsProject //$NON-NLS-1$
					.getName());
			command = "script/server"; //$NON-NLS-1$
		}
		// Now do the launch in terminal
		TerminalView term = TerminalView.openView(viewId, viewId, railsProject.getLocation());
		if (term != null)
		{
			term.sendInput(command + '\n');
		}
		return null;
	}

	protected boolean scriptServerExists(IProject railsProject)
	{
		IFile scriptServer = railsProject.getFile(new Path("script").append("server")); //$NON-NLS-1$ //$NON-NLS-2$
		return scriptServer != null && scriptServer.exists();
	}

}
