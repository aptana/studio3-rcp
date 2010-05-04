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

	public static String DeployWizardPage_CapistranoLabel;

	public static String DeployWizardPage_FTPLabel;

	public static String DeployWizardPage_OtherDeploymentOptionsLabel;

	public static String DeployWizardPage_ProvidersLabel;

	public static String DeployWizardPage_Title;

	public static String FTPDeployWizardPage_ProtocolLabel;

	public static String FTPDeployWizardPage_RemoteInfoLabel;

	public static String FTPDeployWizardPage_SiteNameLabel;

	public static String FTPDeployWizardPage_Title;

	public static String HerokuAPI_AuthConnectionFailed_Error;

	public static String HerokuAPI_AuthFailed_Error;

	public static String HerokuDeployWizardPage_ApplicationNameLabel;

	public static String HerokuDeployWizardPage_EmotyApplicationNameError;

	public static String HerokuDeployWizardPage_NoGitRepoNote;

	public static String HerokuDeployWizardPage_PublishApplicationLabel;

	public static String HerokuDeployWizardPage_Title;

	public static String HerokuLoginWizardPage_EmptyPasswordError;

	public static String HerokuLoginWizardPage_EmptyUserIDError;

	public static String HerokuLoginWizardPage_EnterCredentialsLabel;

	public static String HerokuLoginWizardPage_PasswordExample;

	public static String HerokuLoginWizardPage_PasswordLabel;

	public static String HerokuLoginWizardPage_SignupLink;

	public static String HerokuLoginWizardPage_SubmitButtonLabel;

	public static String HerokuLoginWizardPage_Title;

	public static String HerokuLoginWizardPage_UserIDExample;

	public static String HerokuLoginWizardPage_UserIDLabel;

	public static String HerokuSignupPage_SignupButtonLabel;

	public static String HerokuSignupPage_SignupNote;

	public static String HerokuSignupPage_Title;

	static
	{
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages()
	{
	}
}
