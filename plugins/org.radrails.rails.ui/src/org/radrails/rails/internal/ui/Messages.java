package org.radrails.rails.internal.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{
	private static final String BUNDLE_NAME = "org.radrails.rails.internal.ui.messages"; //$NON-NLS-1$

	public static String NewProjectWizard_ContentsAlreadyExist_Msg;

	public static String NewProjectWizard_ContentsAlreadyExist_Title;

	public static String NewProjectWizard_JobTitle;
	public static String NewProjectWizard_RailsCommandFailedMessage;

	public static String NewProject_title;
	public static String NewProject_description;
	public static String NewProject_windowTitle;
	public static String NewProject_caseVariantExistsError;
	public static String NewProject_errorMessage;
	public static String NewProject_internalError;

	public static String WizardNewProjectCreationPage_BrowseLabel;

	public static String WizardNewProjectCreationPage_CloneGitRepoLabel;
	public static String WizardNewProjectCreationPage_GenerateAppGroupLabel;
	public static String WizardNewProjectCreationPage_nameLabel;
	public static String WizardNewProjectCreationPage_NoGeneratorText;
	public static String WizardNewProjectCreationPage_projectNameEmpty;
	public static String WizardNewProjectCreationPage_projectExistsMessage;
	public static String WizardNewProjectCreationPage_StandardGeneratorText;
	public static String ProjectLocationSelectionDialog_locationLabel;
	public static String WizardNewProjectCreationPage_projectLocationEmpty;
	public static String WizardNewProjectCreationPage_gitLocationEmpty;
	public static String WizardNewProjectCreationPage_cannotCreateProjectMessage;

	public static String ClearLogConfirmTitle;
	public static String ClearLogConfirmDescription;

	static
	{
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages()
	{
	}
}
