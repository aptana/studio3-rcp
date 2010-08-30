package org.radrails.rails.internal.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
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
public class PreviewCommandAction extends RailsScriptAction
{

	private static final String PREVIEW_COMMAND_NAME = "Preview"; //$NON-NLS-1$
	private static final String RAILS_SCOPE = "source.ruby.rails"; //$NON-NLS-1$

	@Override
	public void run()
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
				IDocument document = abstractThemeableEditor.getDocumentProvider().getDocument(
						abstractThemeableEditor.getEditorInput());
				int caretOffset = TextEditorUtils.getCaretOffset(abstractThemeableEditor);
				// Get the scope at caret offset
				String contentTypeAtOffset = CommonEditorPlugin.getDefault().getDocumentScopeManager()
						.getScopeAtOffset(document, caretOffset);
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
		CommandElement[] commands = BundleManager.getInstance().getCommands(
				new AndFilter(filters.toArray(new IModelFilter[filters.size()])));
		if (commands != null && commands.length > 0)
		{
			commands[0].execute();
		}
	}
}
