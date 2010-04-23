package org.radrails.rails.internal.ui.actions;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.radrails.rails.core.RailsProjectNature;
import org.radrails.rails.ui.RailsUIPlugin;

import com.aptana.explorer.IExplorerUIConstants;
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

	protected IProject getSelectedRailsProject()
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
		// checks the active project in App Explorer
		CommonNavigator navigator = getAppExplorer();
		if (navigator != null)
		{
			Object input = navigator.getCommonViewer().getInput();
			if (input instanceof IProject)
			{
				IProject project = (IProject) input;
				try
				{
					if (project.hasNature(RailsProjectNature.ID))
					{
						return project;
					}
				}
				catch (CoreException e)
				{
					RailsUIPlugin.logError(e);
				}
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
		// now determine which version so we can tell what to run...
		String viewId = MessageFormat.format("{0} server", railsProject //$NON-NLS-1$
				.getName());
		String command = "rails server"; //$NON-NLS-1$
		if (scriptServerExists(railsProject))
		{
			viewId = MessageFormat.format("{0} script/server", railsProject //$NON-NLS-1$
					.getName());
			command = "script/server"; //$NON-NLS-1$
		}
		// Now do the launch in terminal
		TerminalView term = TerminalView.openView(viewId, viewId, railsProject.getLocation());
		if (term != null) {
			term.sendInput(command + '\n');
		}
	}

	protected boolean scriptServerExists(IProject railsProject)
	{
		IFile scriptServer = railsProject.getFile(new Path("script").append("server")); //$NON-NLS-1$ //$NON-NLS-2$
		return scriptServer != null && scriptServer.exists();
	}

	private CommonNavigator getAppExplorer()
	{
		IViewReference[] refs = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getViewReferences();
		for (IViewReference ref : refs)
		{
			if (ref.getId().equals(IExplorerUIConstants.VIEW_ID))
			{
				IViewPart part = ref.getView(false);
				if (part instanceof CommonNavigator)
				{
					return (CommonNavigator) part;
				}
			}
		}
		return null;
	}
}
