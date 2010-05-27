package org.radrails.rails.internal.ui.actions;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;

import com.aptana.terminal.views.TerminalView;

public class ScriptConsoleAction extends RailsScriptAction
{

	@Override
	public void run()
	{
		IProject railsProject = getSelectedRailsProject();
		if (railsProject == null)
			return;
		// now determine which version so we can tell what to run...
		String viewId = MessageFormat.format("{0} script/rails console", railsProject //$NON-NLS-1$
				.getName());
		String command = "script/rails console"; //$NON-NLS-1$
		if (scriptConsoleExists(railsProject))
		{
			viewId = MessageFormat.format("{0} script/console", railsProject //$NON-NLS-1$
					.getName());
			command = "script/console"; //$NON-NLS-1$
		}
		// Now do the launch in terminal
		TerminalView term = TerminalView.openView(viewId, viewId, railsProject.getLocation());
		if (term != null)
		{
			term.sendInput(command + '\n');
		}
	}

	protected boolean scriptConsoleExists(IProject railsProject)
	{
		IFile scriptConsole = railsProject.getFile(new Path("script").append("console")); //$NON-NLS-1$ //$NON-NLS-2$
		return scriptConsole != null && scriptConsole.exists();
	}

}
