package com.aptana.ruby.debug.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import com.aptana.ruby.debug.core.RubyDebugModel;
import com.aptana.ruby.debug.core.launching.IRubyLaunchConfigurationConstants;

/**
 * Adapter to create breakpoints in Ruby files.
 */
public class RubyLineBreakpointAdapter implements IToggleBreakpointsTarget
{
	private static final String RUBY_CONTENT_TYPE_ID = "com.aptana.contenttype.ruby"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#toggleLineBreakpoints(org.eclipse.ui.IWorkbenchPart,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	public void toggleLineBreakpoints(IWorkbenchPart part, ISelection selection) throws CoreException
	{
		ITextEditor textEditor = getEditor(part);
		if (textEditor == null)
			return;

		IResource resource = (IResource) textEditor.getEditorInput().getAdapter(IResource.class);
		ITextSelection textSelection = (ITextSelection) selection;
		int lineNumber = textSelection.getStartLine();
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(
				IRubyLaunchConfigurationConstants.ID_RUBY_DEBUG_MODEL);
		for (int i = 0; i < breakpoints.length; i++)
		{
			IBreakpoint breakpoint = breakpoints[i];
			if (resource.equals(breakpoint.getMarker().getResource()))
			{
				if (((ILineBreakpoint) breakpoint).getLineNumber() == (lineNumber + 1))
				{
					// remove
					breakpoint.delete();
					return;
				}
			}
		}
		// create line breakpoint (doc line numbers start at 0)
		RubyDebugModel.createLineBreakpoint(resource, resource.getName(), "", //$NON-NLS-1$
				++lineNumber, true, null);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#canToggleLineBreakpoints(org.eclipse.ui.IWorkbenchPart,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	public boolean canToggleLineBreakpoints(IWorkbenchPart part, ISelection selection)
	{
		return getEditor(part) != null;
	}

	/**
	 * Returns the editor being used to edit a Ruby file, associated with the given part, or <code>null</code> if none.
	 * 
	 * @param part
	 *            workbench part
	 * @return the editor being used to edit a Ruby file, associated with the given part, or <code>null</code> if none
	 */
	ITextEditor getEditor(IWorkbenchPart part)
	{
		if (!(part instanceof ITextEditor))
			return null;

		ITextEditor editorPart = (ITextEditor) part;
		IResource resource = (IResource) editorPart.getEditorInput().getAdapter(IResource.class);
		if (resource == null || !(resource instanceof IFile))
			return null;

		IContentType rubyType = Platform.getContentTypeManager().getContentType(RUBY_CONTENT_TYPE_ID);
		IFile file = (IFile) resource;
		IContentType type = IDE.getContentType(file);
		if (type != null && type.isKindOf(rubyType))
		{
			return editorPart;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#toggleMethodBreakpoints(org.eclipse.ui.IWorkbenchPart,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	public void toggleMethodBreakpoints(IWorkbenchPart part, ISelection selection) throws CoreException
	{
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#canToggleMethodBreakpoints(org.eclipse.ui.IWorkbenchPart,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	public boolean canToggleMethodBreakpoints(IWorkbenchPart part, ISelection selection)
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#toggleWatchpoints(org.eclipse.ui.IWorkbenchPart,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	public void toggleWatchpoints(IWorkbenchPart part, ISelection selection) throws CoreException
	{
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#canToggleWatchpoints(org.eclipse.ui.IWorkbenchPart,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	public boolean canToggleWatchpoints(IWorkbenchPart part, ISelection selection)
	{
		return false;
	}
}
