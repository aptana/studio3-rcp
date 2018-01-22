package com.aptana.rcp;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.osgi.service.datalocation.Location;

import com.aptana.core.logging.IdeLog;
import com.aptana.core.util.ProcessUtil;
import com.aptana.core.util.URLEncoder;

/**
 * This copies the "studio3" executable script from our install directory to /usr/local/bin so that it's on the user's
 * path. This script allows us to open the IDE from the command line ala "mate". Right now this won't run/get scheduled
 * again if the destination file already exists (so if we want to force a copy of an updated one we'll need to change
 * this logic).
 * 
 * @author cwilliams
 */
class InstallCommandLineExecutableJob extends Job
{

	private static final String EXECUTABLE_NAME = "studio3"; //$NON-NLS-1$
	private static final String INSTALL_PATH = "/usr/local/bin"; //$NON-NLS-1$

	public InstallCommandLineExecutableJob()
	{
		super(Messages.InstallCommandLineExecutableJob_JobName);
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
			File installFolder = new File(URLEncoder.encode(url).toURI());
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
			// ignore result. 99% of the time it'll fail because /usr/local/bin is not writable
			ProcessUtil.runInBackground("ln", Path.fromOSString(installFolder.getAbsolutePath()), "-s", //$NON-NLS-1$ //$NON-NLS-2$
					executable.getAbsolutePath(), dest.getAbsolutePath());
		}
		catch (Exception e)
		{
			IdeLog.logError(IdePlugin.getDefault(), e);
			return new Status(IStatus.ERROR, IdePlugin.PLUGIN_ID, e.getMessage(), e);
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
		if (Platform.getOS().equals(Platform.OS_WIN32))
		{
			return false;
		}
		File dest = new File(INSTALL_PATH, EXECUTABLE_NAME);
		return !dest.exists();
	}

	@Override
	public boolean shouldRun()
	{
		return shouldSchedule();
	}

}
