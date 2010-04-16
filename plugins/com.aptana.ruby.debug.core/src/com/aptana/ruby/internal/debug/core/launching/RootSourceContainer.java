package com.aptana.ruby.internal.debug.core.launching;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.AbstractSourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;

public class RootSourceContainer extends AbstractSourceContainer
{

	public Object[] findSourceElements(String name) throws CoreException
	{
		List<Object> sources = new ArrayList<Object>();
		File file = new File(name);
		if (file.exists() && file.isFile())
		{
			sources.add(new LocalFileStorage(file));
		}

		if (sources.isEmpty())
			return EMPTY;
		return sources.toArray();
	}

	public String getName()
	{
		return "root"; //$NON-NLS-1$
	}

	public ISourceContainerType getType()
	{
		return null;
	}

}
