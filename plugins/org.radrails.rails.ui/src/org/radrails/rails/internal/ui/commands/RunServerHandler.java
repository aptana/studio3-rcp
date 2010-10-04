package org.radrails.rails.internal.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.radrails.rails.internal.ui.actions.RunScriptServerAction;

public class RunServerHandler extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		new RunScriptServerAction().run();
		return null;
	}

}
