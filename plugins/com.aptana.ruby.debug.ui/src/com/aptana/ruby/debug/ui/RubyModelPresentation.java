/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Bjorn Freeman-Benson - initial API and implementation
 *******************************************************************************/
package com.aptana.ruby.debug.ui;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Renders Ruby debug elements
 */
public class RubyModelPresentation extends LabelProvider implements IDebugModelPresentation
{
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.IDebugModelPresentation#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String attribute, Object value)
	{
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element)
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element)
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.IDebugModelPresentation#computeDetail(org.eclipse.debug.core.model.IValue,
	 * org.eclipse.debug.ui.IValueDetailListener)
	 */
	public void computeDetail(IValue value, IValueDetailListener listener)
	{
		String detail = ""; //$NON-NLS-1$
		try
		{
			detail = value.getValueString();
		}
		catch (DebugException e)
		{
		}
		listener.detailComputed(value, detail);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ISourcePresentation#getEditorInput(java.lang.Object)
	 */
	public IEditorInput getEditorInput(Object element)
	{
		IFile file = getFile(element);
		if (file != null)
		{
			return new FileEditorInput(file);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ISourcePresentation#getEditorId(org.eclipse.ui.IEditorInput, java.lang.Object)
	 */
	public String getEditorId(IEditorInput input, Object element)
	{
		IFile file = getFile(element);
		if (file != null)
		{
			IEditorDescriptor desc = IDE.getDefaultEditor(file);
			if (desc != null)
			{
				return desc.getId();
			}
		}
		return null;
	}

	private IFile getFile(Object element)
	{
		if (element instanceof IFile)
			return (IFile) element;
		if (element instanceof ILineBreakpoint)
		{
			return (IFile) ((ILineBreakpoint) element).getMarker().getResource();
		}
		if (element instanceof LocalFileStorage)
		{
			File file = ((LocalFileStorage) element).getFile();
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
					new Path(file.getAbsolutePath()));
			// TODO What about when it's not related to anything in the workspace at all (i.e. std lib!)
			if (resource != null)
				return (IFile) resource;
		}

		return null;
	}
}
