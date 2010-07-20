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

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
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
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

import com.aptana.ruby.debug.core.IRubyLineBreakpoint;
import com.aptana.ruby.internal.debug.ui.StorageEditorInput;

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
		if (element instanceof IRubyLineBreakpoint)
		{
			try
			{
				IRubyLineBreakpoint rlbp = (IRubyLineBreakpoint) element;
				return rlbp.getFileName() + ":" + rlbp.getLineNumber(); //$NON-NLS-1$
			}
			catch (CoreException e)
			{
				// ignore
			}
		}
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
		if (element instanceof IStorage)
		{
			return new StorageEditorInput((IStorage) element);
		}
		IFileStore store = getFileStore(element);
		if (store != null)
		{
			return new FileStoreEditorInput(store);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ISourcePresentation#getEditorId(org.eclipse.ui.IEditorInput, java.lang.Object)
	 */
	public String getEditorId(IEditorInput input, Object element)
	{
		IEditorDescriptor desc = null;
		IFile file = getFile(element);
		if (file != null)
		{
			desc = IDE.getDefaultEditor(file);
		}
		else if (element instanceof IStorage)
		{
			IStorage storage = (IStorage) element;
			IContentType contentType = Platform.getContentTypeManager().findContentTypeFor(storage.getName());
			IEditorRegistry editorReg = PlatformUI.getWorkbench().getEditorRegistry();
			desc = editorReg.getDefaultEditor(storage.getName(), contentType);
		}
		if (desc != null)
		{
			return desc.getId();
		}
		return null;
	}

	private IFile getFile(Object element)
	{
		if (element instanceof IFile)
			return (IFile) element;
		if (element instanceof ILineBreakpoint)
		{
			IResource resource = ((ILineBreakpoint) element).getMarker().getResource();
			if (resource instanceof IFile)
			{
				return (IFile) resource;
			}
		}
		if (element instanceof LocalFileStorage)
		{
			File file = ((LocalFileStorage) element).getFile();
			IResource resource = ResourcesPlugin.getWorkspace().getRoot()
					.getFileForLocation(new Path(file.getAbsolutePath()));
			if (resource != null)
				return (IFile) resource;
		}

		return null;
	}

	private IFileStore getFileStore(Object element)
	{
		if (element instanceof IRubyLineBreakpoint)
		{
			try
			{
				String fileName = ((IRubyLineBreakpoint) element).getFileName();
				return EFS.getStore(Path.fromPortableString(fileName).toFile().toURI());
			}
			catch (CoreException e)
			{
				// ignore
			}
		}
		return null;
	}
}
