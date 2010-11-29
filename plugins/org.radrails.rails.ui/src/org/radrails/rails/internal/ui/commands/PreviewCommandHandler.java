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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.radrails.rails.ui.RailsUIPlugin;

import com.aptana.editor.common.CommonEditorPlugin;
import com.aptana.editor.common.scripting.commands.TextEditorUtils;
import com.aptana.scripting.model.AbstractElement;
import com.aptana.scripting.model.BundleManager;
import com.aptana.scripting.model.CommandElement;
import com.aptana.scripting.model.filters.AndFilter;
import com.aptana.scripting.model.filters.IModelFilter;
import com.aptana.scripting.model.filters.IsExecutableCommandFilter;
import com.aptana.scripting.model.filters.ScopeFilter;

/**
 * @author cwilliams
 */
public class PreviewCommandHandler extends AbstractRailsHandler
{
	// TODO Use our new preview API!

	private static final String PREVIEW_COMMAND_NAME = "Preview"; //$NON-NLS-1$
	private static final String RAILS_SCOPE = "source.ruby.rails"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		List<IModelFilter> filters = new ArrayList<IModelFilter>();
		filters.add(new IsExecutableCommandFilter()
		{
			@Override
			public boolean include(AbstractElement element)
			{
				if (!super.include(element))
				{
					return false;
				}
				if (!(element instanceof CommandElement))
				{
					return false;
				}
				CommandElement node = (CommandElement) element;
				return node.getDisplayName().equals(PREVIEW_COMMAND_NAME);
			}
		});

		IEditorPart textEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (textEditor instanceof ITextEditor)
		{
			try
			{
				ITextEditor abstractThemeableEditor = (ITextEditor) textEditor;
				ISourceViewer viewer = TextEditorUtils.getSourceViewer(abstractThemeableEditor);

				IDocument document = abstractThemeableEditor.getDocumentProvider().getDocument(
						abstractThemeableEditor.getEditorInput());
				int caretOffset = TextEditorUtils.getCaretOffset(abstractThemeableEditor);
				// Get the scope at caret offset
				String contentTypeAtOffset = null;
				if (viewer == null)
				{

					contentTypeAtOffset = CommonEditorPlugin.getDefault().getDocumentScopeManager()
							.getScopeAtOffset(document, caretOffset);
				}
				else
				{
					contentTypeAtOffset = CommonEditorPlugin.getDefault().getDocumentScopeManager()
							.getScopeAtOffset(viewer, caretOffset);
				}
				filters.add(new ScopeFilter(contentTypeAtOffset));
			}
			catch (BadLocationException e1)
			{
				RailsUIPlugin.logError(e1);
			}
		}
		else
		{
			// If no active editor, "use project root". Assume rails scope.
			filters.add(new ScopeFilter(RAILS_SCOPE));
		}
		List<CommandElement> commands = BundleManager.getInstance().getCommands(
				new AndFilter(filters.toArray(new IModelFilter[filters.size()])));
		if (commands != null && !commands.isEmpty())
		{
			commands.get(0).execute();
		}
		return null;
	}
}
