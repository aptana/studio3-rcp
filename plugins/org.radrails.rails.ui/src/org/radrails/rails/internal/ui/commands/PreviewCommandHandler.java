/**
 * 
 */
package org.radrails.rails.internal.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.radrails.rails.internal.ui.actions.PreviewCommandAction;

/**
 * @author cwilliams
 */
public class PreviewCommandHandler extends AbstractHandler
{

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		(new PreviewCommandAction()).run();
		return null;
	}
}
