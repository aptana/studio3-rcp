package org.radrails.rails.internal.ui.hyperlink;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.tm.terminal.model.IHyperlinkDetector;

import com.aptana.editor.common.text.hyperlink.EditorLineHyperlink;

/**
 * Detects references to views that get rendered, resolves them relative to rails app/views
 * 
 * @author cwilliams
 */
public class RenderPathHyperlinkDetector implements IHyperlinkDetector
{
	private static Pattern RENDERED_VIEW_PATTERN = Pattern.compile("^Rendered\\s+(\\S.+?)\\s+"); //$NON-NLS-1$

	public IHyperlink[] detectHyperlinks(String contents)
	{
		Matcher m = RENDERED_VIEW_PATTERN.matcher(contents);
		if (m.find())
		{
			String filepath = m.group(1);
			int start = m.start(1);
			int length = m.end(1) - start;
			if (!filepath.startsWith("/")) //$NON-NLS-1$
			{
				filepath = "app/views/" + filepath; //$NON-NLS-1$
			}
			return new IHyperlink[] { new EditorLineHyperlink(new Region(start, length), filepath, 0) };
		}
		return new IHyperlink[0];
	}
}
