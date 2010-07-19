package com.aptana.rcp;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.osgi.service.datalocation.Location;

/**
 * This copies the "studio3" executable script from our install directory to /usr/local/bin so that it's on the user's
 * path. This script allows us to open the IDE from the command line ala "mate".
 * 
 * @author cwilliams
 */
class InstallCommandLineExecutableJob extends Job
{

	private static final String EXECUTABLE_NAME = "studio3"; //$NON-NLS-1$
	private static final String INSTALL_PATH = "/usr/local/bin"; //$NON-NLS-1$

	public InstallCommandLineExecutableJob()
	{
		super("Installing studio3 executable");
	}

	@Override
	protected IStatus run(IProgressMonitor monitor)
	{
		SubMonitor sub = SubMonitor.convert(monitor, 10);
		// Get our install location...
		try
		{
			Location loc = Platform.getInstallLocation();
			URL url = loc.getURL();
			File installFolder = new File(url.toURI());
			sub.worked(1);
			File executable = new File(installFolder, EXECUTABLE_NAME);
			if (!executable.exists())
			{
				return Status.CANCEL_STATUS;
			}
			File dest = new File(INSTALL_PATH, EXECUTABLE_NAME);
			if (dest.exists())
			{
				return Status.CANCEL_STATUS;
			}
			// Kind of ridiculous to do this, but use EFS to copy...
			IFileStore store = EFS.getStore(executable.toURI());
			store.copy(EFS.getStore(dest.toURI()), EFS.NONE, sub.newChild(9));
		}
		catch (URISyntaxException e)
		{
			IdePlugin.logError(e);
			return new Status(IStatus.ERROR, IdePlugin.PLUGIN_ID, e.getMessage(), e);
		}
		catch (CoreException e)
		{
			IdePlugin.logError(e);
			return e.getStatus();
		}
		finally
		{
			sub.done();
		}
		return Status.OK_STATUS;
	}

	@Override
	public boolean shouldSchedule()
	{
		// Install on any non-win machine
		return !Platform.getOS().equals(Platform.OS_WIN32);
	}

	@Override
	public boolean shouldRun()
	{
		return shouldSchedule();
	}

}
