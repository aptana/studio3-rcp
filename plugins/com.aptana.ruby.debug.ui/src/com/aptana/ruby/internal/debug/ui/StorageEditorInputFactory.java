package com.aptana.ruby.internal.debug.ui;

import java.io.File;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

public class StorageEditorInputFactory implements IElementFactory
{

	/**
	 * Factory id. The workbench plug-in registers a factory by this name with the "org.eclipse.ui.elementFactories"
	 * extension point.
	 */
	private static final String ID_FACTORY = "com.aptana.ui.part.StorageEditorInputFactory"; //$NON-NLS-1$

	/**
	 * Tag for the IFile.fullPath of the file resource.
	 */
	private static final String TAG_PATH = "path"; //$NON-NLS-1$

	/*
	 * (non-Javadoc) Method declared on IElementFactory.
	 */
	public IAdaptable createElement(IMemento memento)
	{
		// Get the file name.
		String fileName = memento.getString(TAG_PATH);
		if (fileName == null)
		{
			return null;
		}

		File file = new File(fileName);
		return new StorageEditorInput(new LocalFileStorage(file));
	}

	/**
	 * Returns the element factory id for this class.
	 * 
	 * @return the element factory id
	 */
	public static String getFactoryId()
	{
		return ID_FACTORY;
	}

	public static void saveState(IMemento memento, IStorage storage)
	{
		memento.putString(TAG_PATH, storage.getFullPath().toString());
	}

}
