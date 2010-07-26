package com.aptana.ruby.debug.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.Position;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import com.aptana.ruby.internal.debug.core.breakpoints.RubyLineBreakpoint;

/**
 * A marker annotation model whose underlying source of markers is the workspace root. This uses our hack of a special
 * attribute name to point to the real absolute filepath of the file we want the marker for.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class ExternalRubyFileAnnotationModel extends AbstractMarkerAnnotationModel
{

	/**
	 * Internal resource change listener.
	 */
	class ResourceChangeListener implements IResourceChangeListener
	{
		/*
		 * @see IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
		 */
		public void resourceChanged(IResourceChangeEvent e)
		{
			IResourceDelta delta = e.getDelta();
			if (delta != null && fResource != null)
			{
				IResourceDelta child = delta.findMember(fResource.getFullPath());
				if (child != null)
					update(child.getMarkerDeltas());
			}
		}
	}

	/** The workspace. */
	private IWorkspace fWorkspace;
	/** The resource. */
	private IResource fResource;
	/** The resource change listener. */
	private IResourceChangeListener fResourceChangeListener = new ResourceChangeListener();
	private IPath fPath;

	/**
	 * Creates a marker annotation model with the given resource as the source of the markers.
	 * 
	 * @param resource
	 *            the absolute path of a file external to the workspace whose breakpoints we want to listen for and
	 *            display in the ruler
	 */
	public ExternalRubyFileAnnotationModel(IPath resource)
	{
		Assert.isNotNull(resource);
		fPath = resource;
		fResource = ResourcesPlugin.getWorkspace().getRoot();
		fWorkspace = ResourcesPlugin.getWorkspace();
		listenToMarkerChanges(true);
	}

	/*
	 * @see AbstractMarkerAnnotationModel#isAcceptable(IMarker)
	 */
	protected boolean isAcceptable(IMarker marker)
	{
		return marker != null && fResource.equals(marker.getResource()) && sameFile(marker);
	}

	private boolean sameFile(IMarker marker)
	{
		String filename = marker.getAttribute(RubyLineBreakpoint.EXTERNAL_FILENAME, (String) null);
		if (filename == null)
			return false;
		return fPath.equals(Path.fromPortableString(filename));
	}

	/**
	 * Updates this model to the given marker deltas.
	 * 
	 * @param markerDeltas
	 *            the array of marker deltas
	 */
	protected void update(IMarkerDelta[] markerDeltas)
	{

		if (markerDeltas.length == 0)
			return;

		if (markerDeltas.length == 1)
		{
			IMarkerDelta delta = markerDeltas[0];
			switch (delta.getKind())
			{
				case IResourceDelta.ADDED:
					addMarkerAnnotation(delta.getMarker());
					break;
				case IResourceDelta.REMOVED:
					removeMarkerAnnotation(delta.getMarker());
					break;
				case IResourceDelta.CHANGED:
					modifyMarkerAnnotation(delta.getMarker());
					break;
			}
		}
		else
			batchedUpdate(markerDeltas);

		fireModelChanged();
	}

	/**
	 * Updates this model to the given marker deltas.
	 * 
	 * @param markerDeltas
	 *            the array of marker deltas
	 */
	private void batchedUpdate(IMarkerDelta[] markerDeltas)
	{
		HashSet removedMarkers = new HashSet(markerDeltas.length);
		HashSet modifiedMarkers = new HashSet(markerDeltas.length);

		for (int i = 0; i < markerDeltas.length; i++)
		{
			IMarkerDelta delta = markerDeltas[i];
			switch (delta.getKind())
			{
				case IResourceDelta.ADDED:
					addMarkerAnnotation(delta.getMarker());
					break;
				case IResourceDelta.REMOVED:
					removedMarkers.add(delta.getMarker());
					break;
				case IResourceDelta.CHANGED:
					modifiedMarkers.add(delta.getMarker());
					break;
			}
		}

		if (modifiedMarkers.isEmpty() && removedMarkers.isEmpty())
			return;

		Iterator e = getAnnotationIterator(false);
		while (e.hasNext())
		{
			Object o = e.next();
			if (o instanceof MarkerAnnotation)
			{
				MarkerAnnotation a = (MarkerAnnotation) o;
				IMarker marker = a.getMarker();

				if (removedMarkers.remove(marker))
					removeAnnotation(a, false);

				if (modifiedMarkers.remove(marker))
				{
					Position p = createPositionFromMarker(marker);
					if (p != null)
					{
						a.update();
						modifyAnnotationPosition(a, p, false);
					}
				}

				if (modifiedMarkers.isEmpty() && removedMarkers.isEmpty())
					return;

			}
		}

		Iterator iter = modifiedMarkers.iterator();
		while (iter.hasNext())
			addMarkerAnnotation((IMarker) iter.next());
	}

	/*
	 * @see AbstractMarkerAnnotationModel#listenToMarkerChanges(boolean)
	 */
	protected void listenToMarkerChanges(boolean listen)
	{
		if (listen)
			fWorkspace.addResourceChangeListener(fResourceChangeListener);
		else
			fWorkspace.removeResourceChangeListener(fResourceChangeListener);
	}

	/*
	 * @see AbstractMarkerAnnotationModel#deleteMarkers(IMarker[])
	 */
	protected void deleteMarkers(final IMarker[] markers) throws CoreException
	{
		fWorkspace.run(new IWorkspaceRunnable()
		{
			public void run(IProgressMonitor monitor) throws CoreException
			{
				for (int i = 0; i < markers.length; ++i)
				{
					markers[i].delete();
				}
			}
		}, null, IWorkspace.AVOID_UPDATE, null);
	}

	/*
	 * @see AbstractMarkerAnnotationModel#retrieveMarkers()
	 */
	protected IMarker[] retrieveMarkers() throws CoreException
	{
		// Limit to those markers with the same filename!
		List<IMarker> filtered = new ArrayList<IMarker>();
		IMarker[] markers = fResource.findMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);
		for (IMarker marker : markers)
		{
			if (!sameFile(marker))
			{
				continue;
			}
			filtered.add(marker);
		}
		return filtered.toArray(new IMarker[filtered.size()]);
	}
}