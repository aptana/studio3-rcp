package com.aptana.radrails.rcp.preferences;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.IPreferenceStore;

import com.aptana.radrails.rcp.IdePlugin;

public class PreferenceInitializer extends AbstractPreferenceInitializer
{

	@Override
	public void initializeDefaultPreferences()
	{
		// Force "auto-refresh" pref to true by default for RadRails
		IEclipsePreferences prefs = new DefaultScope().getNode(ResourcesPlugin.PI_RESOURCES);
		prefs.putBoolean(ResourcesPlugin.PREF_AUTO_REFRESH, true);

		IPreferenceStore store = IdePlugin.getDefault().getPreferenceStore();
		if (!store.getBoolean(IPreferenceConstants.WORKSPACE_ENCODING_SET))
		{
			try
			{
				ResourcesPlugin.getWorkspace().getRoot().setDefaultCharset("UTF-8", null); //$NON-NLS-1$
			}
			catch (CoreException e)
			{
				IdePlugin.getDefault().getLog().log(
						new Status(IStatus.ERROR, IdePlugin.getDefault().getBundle().getSymbolicName(), IStatus.OK,
								Messages.PreferenceInitializer_Cannot_Set_Default_Encoding, e));
			}
			store.setValue(IPreferenceConstants.WORKSPACE_ENCODING_SET, true);
		}
	}
}
