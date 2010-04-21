package com.aptana.ruby.internal.debug.ui;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.editors.text.ILocationProvider;

import com.aptana.ruby.debug.ui.RubyDebugUIPlugin;

/**
 * 
 */
public class StorageEditorInput implements IStorageEditorInput, ILocationProvider, IPersistableElement
{

	private IStorage storage;

	public StorageEditorInput(IStorage storage)
	{
		this.storage = storage;
	}

	public boolean exists()
	{
		return storage.getFullPath().toFile().exists();
	}

	public ImageDescriptor getImageDescriptor()
	{
		return null;
	}

	public String getName()
	{
		return storage.getName();
	}

	public IStorage getStorage()
	{
		return storage;
	}

	/*
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable()
	{
		return this;
	}

	/*
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText()
	{
		return storage.getFullPath().toPortableString();
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter)
	{
		if (ILocationProvider.class.equals(adapter))
			return this;
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	/*
	 * @see org.eclipse.ui.editors.text.ILocationProvider#getPath(java.lang.Object)
	 */
	public IPath getPath(Object element)
	{
		if (element instanceof StorageEditorInput)
		{
			StorageEditorInput input = (StorageEditorInput) element;
			return input.storage.getFullPath();
		}
		return null;
	}

	public boolean equals(Object object)
	{
		try
		{
			return object instanceof IStorageEditorInput
					&& getStorage().equals(((IStorageEditorInput) object).getStorage());
		}
		catch (CoreException e)
		{
			RubyDebugUIPlugin.logError(e);
		}
		return false;
	}

	public int hashCode()
	{
		return getStorage().hashCode();
	}

	@Override
	public void saveState(IMemento memento)
	{
		StorageEditorInputFactory.saveState(memento, storage);
	}

	@Override
	public String getFactoryId()
	{
		return StorageEditorInputFactory.getFactoryId();
	}

}