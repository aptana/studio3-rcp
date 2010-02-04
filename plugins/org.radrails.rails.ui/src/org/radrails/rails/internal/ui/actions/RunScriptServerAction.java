package org.radrails.rails.internal.ui.actions;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.radrails.rails.core.RailsProjectNature;
import org.radrails.rails.ui.RailsUIPlugin;

import com.aptana.terminal.server.TerminalServer;
import com.aptana.terminal.server.ProcessWrapper;
import com.aptana.terminal.views.TerminalView;

public class RunScriptServerAction extends Action implements IObjectActionDelegate, IWorkbenchWindowActionDelegate
{
	// TODO Combine common code form GitAction and this into a base class for actions we want to run off editor or
	// resource selection
	private ISelection selection;
	private IWorkbenchPart targetPart;
	private IWorkbenchWindow window;

	private ISelectionListener selectionListener = new ISelectionListener()
	{
		public void selectionChanged(IWorkbenchPart part, ISelection selection)
		{
			if (selection instanceof IStructuredSelection)
				RunScriptServerAction.this.selection = selection;
		}
	};

	private IPartListener2 targetPartListener = new IPartListener2()
	{
		public void partActivated(IWorkbenchPartReference partRef)
		{
		}

		public void partBroughtToTop(IWorkbenchPartReference partRef)
		{
		}

		public void partClosed(IWorkbenchPartReference partRef)
		{
			if (targetPart == partRef.getPart(false))
			{
				targetPart = null;
			}
		}

		public void partDeactivated(IWorkbenchPartReference partRef)
		{
		}

		public void partHidden(IWorkbenchPartReference partRef)
		{
		}

		public void partInputChanged(IWorkbenchPartReference partRef)
		{
		}

		public void partOpened(IWorkbenchPartReference partRef)
		{
		}

		public void partVisible(IWorkbenchPartReference partRef)
		{
		}
	};

	public void run(IAction action)
	{
		run();
	}

	public void selectionChanged(IAction action, ISelection selection)
	{
		if (selection instanceof IStructuredSelection)
		{
			this.selection = selection;
		}
	}

	public void init(IWorkbenchWindow window)
	{
		this.window = window;
		window.getSelectionService().addPostSelectionListener(selectionListener);
		window.getActivePage().addPartListener(targetPartListener);
	}

	public void dispose()
	{
		if (window != null)
		{
			window.getSelectionService().removePostSelectionListener(selectionListener);
			if (window.getActivePage() != null)
			{
				window.getActivePage().removePartListener(targetPartListener);
			}
			targetPartListener = null;
		}
		// Don't hold on to anything when we are disposed to prevent memory leaks (see bug 195521)
		selection = null;
		window = null;
		targetPart = null;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart)
	{
		if (targetPart != null)
		{
			this.targetPart = targetPart;
		}
	}

	protected IResource[] getSelectedResources()
	{
		if (this.selection == null || !(this.selection instanceof IStructuredSelection))
		{

			final IResource[] editorResource = new IResource[1];
			Display.getDefault().syncExec(new Runnable()
			{

				public void run()
				{
					try
					{
						IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
								.getActiveEditor();
						IEditorInput input = part.getEditorInput();
						if (input == null)
							return;
						editorResource[0] = (IResource) input.getAdapter(IResource.class);
					}
					catch (Exception e)
					{
						// ignore
					}
				}
			});
			if (editorResource[0] != null)
				return editorResource;
			return new IResource[0];
		}

		Set<IResource> resources = new HashSet<IResource>();
		IStructuredSelection structured = (IStructuredSelection) this.selection;
		for (Object element : structured.toList())
		{
			if (element == null)
				continue;

			if (element instanceof IResource)
				resources.add((IResource) element);

			if (element instanceof IAdaptable)
			{
				IAdaptable adapt = (IAdaptable) element;
				IResource resource = (IResource) adapt.getAdapter(IResource.class);
				if (resource != null)
					resources.add(resource);
			}
		}
		return resources.toArray(new IResource[resources.size()]);
	}

	@Override
	public boolean isEnabled()
	{
		return getSelectedRailsProject() != null;
	}

	private IProject getSelectedRailsProject()
	{
		for (IResource resource : getSelectedResources())
		{
			IProject project = resource.getProject();
			try
			{
				if (project.hasNature(RailsProjectNature.ID))
					return project;
			}
			catch (CoreException e)
			{
				RailsUIPlugin.logError(e);
			}
		}
		return null;
	}

	@Override
	public void run()
	{
		IProject railsProject = getSelectedRailsProject();
		if (railsProject == null)
			return;
		String viewId = MessageFormat.format("{0} script/server", railsProject //$NON-NLS-1$
				.getName());
		TerminalView term = TerminalView.open(viewId, "script/server", railsProject.getLocation().toOSString()); //$NON-NLS-1$
		if (term == null)
			return;
		ProcessWrapper wrapper = TerminalServer.getInstance().getProcess(term.getId());
		wrapper.sendText("script/server\n"); //$NON-NLS-1$
	}

}
