package com.aptana.ruby.debug.ui;

import org.eclipse.core.filebuffers.IAnnotationModelFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;

public class ExternalRubyFileAnnotationModelFactory implements IAnnotationModelFactory
{

	public IAnnotationModel createAnnotationModel(IPath location)
	{
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(location);
		if (file != null && file.exists())
		{
			return new ResourceMarkerAnnotationModel(file);
		}
		return new ExternalRubyFileAnnotationModel(location);
	}

}
