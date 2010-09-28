/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.aptana.ruby.internal.debug.ui.breakpoints;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.aptana.ruby.debug.core.RubyDebugModel;
import com.aptana.ruby.debug.core.model.IRubyExceptionBreakpoint;
import com.aptana.ruby.debug.ui.RubyDebugUIPlugin;

/**
 * The workbench menu action for adding an exception breakpoint
 */
public class AddExceptionAction implements IViewActionDelegate, IWorkbenchWindowActionDelegate
{

	/**
	 * constants
	 * 
	 * @since 3.2
	 */
	public static final String DIALOG_SETTINGS = "AddExceptionDialog"; //$NON-NLS-1$

	/**
	 * the current workbench
	 * 
	 * @since 3.2
	 */
	private IWorkbenchWindow fWorkbenchWindow = null;

	/*
	 * (non-Rubydoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action)
	{
		// Open a dialog asking for typename of the exception
		InputDialog dialog = new InputDialog(fWorkbenchWindow.getShell(), "Add Exception breakpoint",
				"Please enter the type name of the exception type you'd like to break on", "StandardError",
				new IInputValidator()
				{

					public String isValid(String newText)
					{
						if (newText == null || newText.trim().length() == 0)
						{
							return "Must be a non-empty name of exception type to catch";
						}
						// TODO No spaces, no -
						return null;
					}
				});
		if (dialog.open() == Window.OK)
		{
			try
			{
				createBreakpoint(dialog.getValue());
			}
			catch (CoreException e)
			{
				RubyDebugUIPlugin.logError(e);
			}
		}
	}

	/**
	 * creates a single breakpoint of the specified type
	 * 
	 * @param type
	 *            the type of the exception
	 */
	private void createBreakpoint(final String type) throws CoreException
	{
		final IResource resource = ResourcesPlugin.getWorkspace().getRoot();
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(
				RubyDebugModel.getModelIdentifier());
		boolean exists = false;
		for (IBreakpoint breakpoint : breakpoints)
		{
			if (!(breakpoint instanceof IRubyExceptionBreakpoint))
				continue;

			IRubyExceptionBreakpoint exceptionBreakpoint = (IRubyExceptionBreakpoint) breakpoint;
			if (exceptionBreakpoint.getTypeName().equals(type))
			{
				exists = true;
				break;
			}
		}
		if (exists) // TODO Pop an error dialog or something?
			return;

		new Job("AddExceptionAction")
		{
			protected IStatus run(IProgressMonitor monitor)
			{
				try
				{
					RubyDebugModel.createExceptionBreakpoint(resource, type, true, null);
					return Status.OK_STATUS;
				}
				catch (CoreException e)
				{
					RubyDebugUIPlugin.logError(e);
					return e.getStatus();
				}
			}

		}.schedule();

	}

	/*
	 * (non-Rubydoc)
	 * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
	 */
	public void init(IViewPart view)
	{
	}

	/*
	 * (non-Rubydoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection)
	{
	}

	/*
	 * (non-Rubydoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose()
	{
		fWorkbenchWindow = null;
	}

	/*
	 * (non-Rubydoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window)
	{
		fWorkbenchWindow = window;
	}
}
