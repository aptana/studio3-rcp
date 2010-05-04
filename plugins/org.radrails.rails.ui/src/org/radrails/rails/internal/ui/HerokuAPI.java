package org.radrails.rails.internal.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.core.internal.preferences.Base64;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.radrails.rails.ui.RailsUIPlugin;

public class HerokuAPI
{

	private String userId;
	private String password;

	public HerokuAPI(String userId, String password)
	{
		this.userId = userId;
		this.password = password;
	}

	public static File getCredentialsFile()
	{
		String userHome = System.getProperty("user.home");
		if (userHome == null || userHome.trim().length() == 0)
		{
			userHome = ""; // FIXME What should we use if we can't resolve user home???? //$NON-NLS-1$
		}
		File herokuDir = new File(userHome, ".heroku");
		return new File(herokuDir, "credentials");
	}

	public IStatus authenticate()
	{
		try
		{
			URL url = new URL("https://api.heroku.com/apps"); // FIXME This isn't working. I always get a 200!
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			String usernamePassword = userId + ":" + password;
			connection.setRequestProperty("Authorization", "Basic " + Base64.encode(usernamePassword.getBytes()));
			int code = connection.getResponseCode();
			if (code != 200)
			{
				if (code == 401)
				{
					return new Status(IStatus.ERROR, RailsUIPlugin.getPluginIdentifier(), "Authentication failed!");
				}

				return new Status(IStatus.ERROR, RailsUIPlugin.getPluginIdentifier(), "Unable to verify credentials");
			}
			return Status.OK_STATUS;
		}
		catch (Exception e)
		{
			return new Status(IStatus.ERROR, RailsUIPlugin.getPluginIdentifier(), e.getMessage(), e);
		}
	}

	public boolean writeCredentials()
	{
		File credentials = getCredentialsFile();
		credentials.getParentFile().mkdirs();
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new FileWriter(credentials));
			writer.write(userId);
			writer.newLine();
			writer.write(password);
			return true;
		}
		catch (IOException e)
		{
			RailsUIPlugin.logError(e);
		}
		finally
		{
			try
			{
				if (writer != null)
					writer.close();
			}
			catch (IOException e)
			{
				// ignore
			}
		}
		return false;
	}
}
