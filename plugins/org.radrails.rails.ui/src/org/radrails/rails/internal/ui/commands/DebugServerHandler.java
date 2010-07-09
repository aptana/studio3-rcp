package org.radrails.rails.internal.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.radrails.rails.internal.ui.actions.DebugScriptServerAction;

public class DebugServerHandler extends AbstractHandler
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		new DebugScriptServerAction().run();
		return null;
	}

}
