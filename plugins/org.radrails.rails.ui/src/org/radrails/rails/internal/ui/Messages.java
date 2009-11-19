package org.radrails.rails.internal.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{
	private static final String BUNDLE_NAME = "org.radrails.rails.internal.ui.messages"; //$NON-NLS-1$
	public static String NewProjectWizard_JobTitle;
	public static String NewProjectWizard_RailsCommandFailedMessage;
	static
	{
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages()
	{
	}
}
