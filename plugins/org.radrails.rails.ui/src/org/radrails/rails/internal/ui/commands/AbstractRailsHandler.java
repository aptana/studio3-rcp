package org.radrails.rails.internal.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.navigator.CommonNavigator;

import com.aptana.explorer.IExplorerUIConstants;

abstract class AbstractRailsHandler extends AbstractHandler
{

	protected IProject getProject(ExecutionEvent event)
	{
		IResource resource = null;
		IEditorPart part = HandlerUtil.getActiveEditor(event);
		if (part != null)
		{
			IEditorInput editorInput = part.getEditorInput();
			if (editorInput != null)
			{
				resource = (IResource) editorInput.getAdapter(IResource.class);
			}
		}
		if (resource == null)
		{
			ISelection selection = HandlerUtil.getCurrentSelection(event);
			if (selection instanceof IStructuredSelection)
			{
				IStructuredSelection struct = (IStructuredSelection) selection;
				Object element = struct.getFirstElement();
				if (element instanceof IResource)
				{
					resource = (IResource) element;
				}
				else if (element instanceof IAdaptable)
				{
					IAdaptable adapt = (IAdaptable) element;
					resource = (IResource) adapt.getAdapter(IResource.class);
				}
			}
		}
		if (resource == null)
		{
			// checks the active project in App Explorer
			CommonNavigator navigator = getAppExplorer();
			if (navigator != null)
			{
				Object input = navigator.getCommonViewer().getInput();
				if (input instanceof IProject)
				{
					resource = (IProject) input;
				}
			}
		}

		if (resource == null)
		{
			return null;
		}

		return resource.getProject();
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
