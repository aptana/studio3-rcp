/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jakub Jurkiewicz <jakub.jurkiewicz@gmail.com> - Fix for Bug 174737
 *     [IDE] New Plug-in Project wizard status handling is inconsistent
 *     Oakland Software Incorporated (Francis Upton) <francisu@ieee.org>
 *		    Bug 224997 [Workbench] Impossible to copy project
 *******************************************************************************/
package com.aptana.ruby.ui.wizards;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;
import org.eclipse.ui.internal.ide.dialogs.IDEResourceInfoUtils;

/**
 * TODO Extract common code between this and our Web project wizard!
 */
@SuppressWarnings("restriction")
public class WizardNewRubyProjectCreationPage extends WizardPage
{

	// initial value stores
	private String initialProjectFieldValue;

	// widgets
	private Text projectNameField;
	private StyledText locationPathField;
	private Button browseButton;
	private Button gitCloneGenerate;
	private StyledText gitLocation;
	private String lastGitDefault;
	private Button gitBrowseButton;

	private Listener nameModifyListener = new Listener()
	{
		public void handleEvent(Event e)
		{
			updateProjectName(getProjectNameFieldValue());
			setPageComplete(validatePage());
		}
	};

	private String lastLocationDefault;
	protected Button noGenerator;
	private Group projectGenerationGroup;
	private StackLayout projectGenerationStackLayout;
	private Composite projectGenerationControls;

	// constants
	private static final int SIZING_TEXT_FIELD_WIDTH = 250;
	private static final String SAVED_LOCATION_ATTR = "OUTSIDE_LOCATION"; //$NON-NLS-1$
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * Creates a new project creation wizard page.
	 * 
	 * @param pageName
	 *            the name of this page
	 */
	public WizardNewRubyProjectCreationPage(String pageName)
	{
		super(pageName);
		setPageComplete(false);
	}

	/**
	 * (non-Javadoc) Method declared on IDialogPage.
	 */
	public void createControl(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NULL);

		initializeDialogUnits(parent);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IIDEHelpContextIds.NEW_PROJECT_WIZARD_PAGE);

		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createProjectNameGroup(composite);

		// create Location section
		createDestinationLocationArea(composite);

		// Add the generate app section
		createGenerateGroup(composite);

		// Scale the button based on the rest of the dialog
		setButtonLayoutData(browseButton);

		setPageComplete(validatePage());
		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
		Dialog.applyDialogFont(composite);
	}

	/**
	 * Create the area for destination location of project.
	 * 
	 * @param composite
	 * @param defaultEnabled
	 */
	private void createDestinationLocationArea(Composite composite)
	{
		// project specification group
		Composite projectGroup = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		projectGroup.setLayout(layout);
		projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// location label
		Label locationLabel = new Label(projectGroup, SWT.NONE);
		locationLabel.setText(Messages.ProjectLocationSelectionDialog_locationLabel);

		// project location entry field
		locationPathField = new SpecialText(projectGroup, SWT.BORDER | SWT.SINGLE)
		{
			@Override
			protected boolean matchesLastDefault()
			{
				return locationMatchesLastDefault();
			}

			protected boolean isDefault()
			{
				return locationIsDefault();
			};
		};
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		data.horizontalSpan = 2;
		locationPathField.setLayoutData(data);

		// browse button
		browseButton = new Button(projectGroup, SWT.PUSH);
		browseButton.setText(Messages.WizardNewProjectCreationPage_BrowseLabel);
		browseButton.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				handleLocationBrowseButtonPressed();
			}
		});

		if (initialProjectFieldValue == null)
		{
			locationPathField.setText(EMPTY_STRING);
		}
		else
		{
			locationPathField.setText(initialProjectFieldValue);
		}

		locationPathField.addModifyListener(new ModifyListener()
		{
			/*
			 * (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e)
			{
				reportError(checkValidLocation(), false);
			}
		});
	}

	/**
	 * Return the path on the location field.
	 * 
	 * @return String
	 */
	private String getPathFromLocationField()
	{
		URI fieldURI;
		try
		{
			fieldURI = new URI(getLocationText());
		}
		catch (URISyntaxException e)
		{
			return locationPathField.getText();
		}
		return fieldURI.getPath();
	}

	protected String getLocationText()
	{
		return locationPathField.getText();
	}

	/**
	 * Open an appropriate directory browser
	 */
	protected void handleLocationBrowseButtonPressed()
	{
		String selectedDirectory = null;
		String dirName = getPathFromLocationField();

		if (!dirName.equals(EMPTY_STRING))
		{
			IFileInfo info = IDEResourceInfoUtils.getFileInfo(dirName);

			if (info == null || !(info.exists()))
				dirName = EMPTY_STRING;
		}
		else
		{
			String value = getDialogSettings().get(SAVED_LOCATION_ATTR);
			if (value != null)
			{
				dirName = value;
			}
		}

		DirectoryDialog dialog = new DirectoryDialog(locationPathField.getShell(), SWT.SHEET);
		dialog.setMessage(EMPTY_STRING);
		dialog.setFilterPath(dirName);
		selectedDirectory = dialog.open();

		if (selectedDirectory != null)
		{
			locationPathField.setText(selectedDirectory);
			getDialogSettings().put(SAVED_LOCATION_ATTR, selectedDirectory);
		}

	}

	/**
	 * Open an appropriate directory browser
	 */
	protected void handleGitLocationBrowseButtonPressed()
	{
		String dirName = gitLocation.getText().trim();

		if (!dirName.equals(EMPTY_STRING))
		{
			IFileInfo info = IDEResourceInfoUtils.getFileInfo(dirName);

			if (info == null || !(info.exists()))
				dirName = EMPTY_STRING;
		}

		DirectoryDialog dialog = new DirectoryDialog(gitLocation.getShell(), SWT.SHEET);
		dialog.setMessage(EMPTY_STRING);
		dialog.setFilterPath(dirName);
		String selectedDirectory = dialog.open();
		if (selectedDirectory != null)
		{
			gitLocation.setText(selectedDirectory);
			selectGitCloneGeneration();
		}
	}

	protected String checkValidLocation()
	{
		String locationFieldContents = locationPathField.getText();
		if (locationFieldContents.length() == 0)
		{
			return Messages.WizardNewProjectCreationPage_projectLocationEmpty;
		}

		return null;
	}

	protected String checkValidGitLocation()
	{
		if (!cloneFromGit())
			return null;
		String locationFieldContents = gitLocation.getText();
		if (locationFieldContents.length() == 0)
		{
			return Messages.WizardNewProjectCreationPage_gitLocationEmpty;
		}

		return null;
	}

	protected void reportError(String errorMessage, boolean infoOnly)
	{
		if (infoOnly)
		{
			setMessage(errorMessage, IStatus.INFO);
			setErrorMessage(null);
		}
		else
			setErrorMessage(errorMessage);
		boolean valid = errorMessage == null;
		if (valid)
		{
			valid = validatePage();
		}

		setPageComplete(valid);
	}

	/**
	 * Creates the project name specification controls.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	private final void createProjectNameGroup(Composite parent)
	{
		// project specification group
		Composite projectGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		projectGroup.setLayout(layout);
		projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// new project label
		Label projectLabel = new Label(projectGroup, SWT.NONE);
		projectLabel.setText(Messages.WizardNewProjectCreationPage_nameLabel);
		projectLabel.setFont(parent.getFont());

		// new project name entry field
		projectNameField = new Text(projectGroup, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		projectNameField.setLayoutData(data);
		projectNameField.setFont(parent.getFont());

		// Set the initial value first before listener
		// to avoid handling an event during the creation.
		if (initialProjectFieldValue != null)
		{
			projectNameField.setText(initialProjectFieldValue);
		}
		projectNameField.addListener(SWT.Modify, nameModifyListener);
	}

	/**
	 * Creates the project generation controls.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected Composite createGenerateGroup(Composite parent)
	{
		// project generation group
		projectGenerationGroup = new Group(parent, SWT.NONE);
		projectGenerationGroup.setText(Messages.WizardNewProjectCreationPage_GenerateAppGroupLabel);
		projectGenerationStackLayout = new StackLayout();
		projectGenerationGroup.setLayout(projectGenerationStackLayout);
		projectGenerationGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		projectGenerationControls = new Composite(projectGenerationGroup, SWT.NONE);
		projectGenerationControls.setLayout(new GridLayout(1, false));
		projectGenerationControls.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createGenerationOptions(projectGenerationControls);

		setTopControl(projectGenerationControls);

		return projectGenerationGroup;
	}

	protected void setTopControl(Control control)
	{
		projectGenerationStackLayout.topControl = control;
		projectGenerationGroup.layout();
	}

	protected void createGenerationOptions(Composite projectGenerationControls)
	{
		gitCloneGenerate = new Button(projectGenerationControls, SWT.RADIO);
		gitCloneGenerate.setText(Messages.WizardNewProjectCreationPage_CloneGitRepoLabel);

		createGitLocationComposite(projectGenerationControls);

		noGenerator = new Button(projectGenerationControls, SWT.RADIO);
		noGenerator.setText(Messages.WizardNewProjectCreationPage_NoGeneratorText);
		
		noGenerator.setSelection(true);
	}

	private void createGitLocationComposite(Composite parent)
	{
		// project specification group
		Composite projectGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		projectGroup.setLayout(layout);
		projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// location label
		Label locationLabel = new Label(projectGroup, SWT.NONE);
		locationLabel.setText(Messages.ProjectLocationSelectionDialog_locationLabel);

		// project location entry field
		gitLocation = new SpecialText(projectGroup, SWT.BORDER | SWT.SINGLE)
		{
			@Override
			protected boolean isDefault()
			{
				return matchesLastDefault();
			}

			@Override
			protected boolean matchesLastDefault()
			{
				return gitLocationIsDefault();
			}
		};
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		data.horizontalSpan = 2;
		gitLocation.setLayoutData(data);
		gitLocation.addModifyListener(new ModifyListener()
		{

			public void modifyText(ModifyEvent e)
			{
				setPageComplete(validatePage());
			}
		});
		gitLocation.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				selectGitCloneGeneration();
			}
		});

		// browse button
		gitBrowseButton = new Button(projectGroup, SWT.PUSH);
		gitBrowseButton.setText(Messages.WizardNewProjectCreationPage_BrowseLabel);
		gitBrowseButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				handleGitLocationBrowseButtonPressed();
			}
		});
	}

	/**
	 * Returns the current project location path as entered by the user, or its anticipated initial value. Note that if
	 * the default has been returned the path in a project description used to create a project should not be set.
	 * 
	 * @return the project location path or its anticipated initial value.
	 */
	public IPath getLocationPath()
	{
		return new Path(locationPathField.getText());
	}

	/**
	 * /** Returns the current project location URI as entered by the user, or <code>null</code> if a valid project
	 * location has not been entered.
	 * 
	 * @return the project location URI, or <code>null</code>
	 * @since 3.2
	 */
	public URI getLocationURI()
	{
		return getLocationPath().toFile().toURI();
	}

	/**
	 * Creates a project resource handle for the current project name field value. The project handle is created
	 * relative to the workspace root.
	 * <p>
	 * This method does not create the project resource; this is the responsibility of <code>IProject::create</code>
	 * invoked by the new project resource wizard.
	 * </p>
	 * 
	 * @return the new project resource handle
	 */
	public IProject getProjectHandle()
	{
		return ResourcesPlugin.getWorkspace().getRoot().getProject(getProjectName());
	}

	/**
	 * Returns the current project name as entered by the user, or its anticipated initial value.
	 * 
	 * @return the project name, its anticipated initial value, or <code>null</code> if no project name is known
	 */
	public String getProjectName()
	{
		if (projectNameField == null)
		{
			return initialProjectFieldValue;
		}

		return getProjectNameFieldValue();
	}

	/**
	 * Returns the value of the project name field with leading and trailing spaces removed.
	 * 
	 * @return the project name in the field
	 */
	private String getProjectNameFieldValue()
	{
		if (projectNameField == null)
		{
			return EMPTY_STRING;
		}

		return projectNameField.getText().trim();
	}

	/**
	 * Sets the initial project name that this page will use when created. The name is ignored if the
	 * createControl(Composite) method has already been called. Leading and trailing spaces in the name are ignored.
	 * Providing the name of an existing project will not necessarily cause the wizard to warn the user. Callers of this
	 * method should first check if the project name passed already exists in the workspace.
	 * 
	 * @param name
	 *            initial project name for this page
	 * @see IWorkspace#validateName(String, int)
	 */
	public void setInitialProjectName(String name)
	{
		if (name == null)
		{
			initialProjectFieldValue = null;
		}
		else
		{
			initialProjectFieldValue = name.trim();
			updateProjectName(name.trim());
		}
	}

	private void updateProjectName(String trim)
	{
		// Update the location field
		// Only update if the location field is empty, or is the "default" value
		if (locationPathField.getText().trim().length() == 0 || locationMatchesLastDefault())
		{
			lastLocationDefault = Platform.getLocation().append(trim).toOSString();
			locationPathField.setText(lastLocationDefault);
		}

		// Update the git location field
		String username = System.getProperty("user.name"); //$NON-NLS-1$
		if (username == null || username.length() == 0)
			username = "user"; //$NON-NLS-1$
		if (gitLocation.getText().trim().length() == 0 || gitLocationIsDefault())
		{
			lastGitDefault = MessageFormat.format("git://github.com/{0}/{1}.git", username, trim); //$NON-NLS-1$
			gitLocation.setText(lastGitDefault);
		}
	}

	private boolean locationMatchesLastDefault()
	{
		return lastLocationDefault != null && locationPathField.getText().trim().equals(lastLocationDefault);
	}

	private boolean gitLocationIsDefault()
	{
		return lastGitDefault != null && gitLocation.getText().trim().equals(lastGitDefault);
	}

	/**
	 * Returns whether this page's controls currently all contain valid values.
	 * 
	 * @return <code>true</code> if all controls are valid, and <code>false</code> if at least one is invalid
	 */
	protected boolean validatePage()
	{
		projectGenerationStackLayout.topControl = projectGenerationControls;
		projectGenerationGroup.layout();

		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		String projectFieldContents = getProjectNameFieldValue();
		if (projectFieldContents.equals(EMPTY_STRING))
		{
			setErrorMessage(null);
			setMessage(Messages.WizardNewProjectCreationPage_projectNameEmpty);
			return false;
		}

		IStatus nameStatus = workspace.validateName(projectFieldContents, IResource.PROJECT);
		if (!nameStatus.isOK())
		{
			setErrorMessage(nameStatus.getMessage());
			return false;
		}

		IProject handle = getProjectHandle();
		if (handle.exists())
		{
			setErrorMessage(Messages.WizardNewProjectCreationPage_projectExistsMessage);
			return false;
		}

		String validLocationMessage = checkValidLocation();
		if (validLocationMessage != null)
		{ // there is no destination location given
			setErrorMessage(validLocationMessage);
			return false;
		}

		validLocationMessage = checkValidGitLocation();
		if (validLocationMessage != null)
		{
			setErrorMessage(validLocationMessage);
			return false;
		}

		setErrorMessage(null);
		setMessage(null);
		return true;
	}

	/*
	 * see @DialogPage.setVisible(boolean)
	 */
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
		if (visible)
		{
			projectNameField.setFocus();
		}
	}

	public boolean cloneFromGit()
	{
		return gitCloneGenerate.getSelection();
	}

	public String gitCloneURI()
	{
		return gitLocation.getText().trim();
	}

	public boolean locationIsDefault()
	{
		String defaultLocation = Platform.getLocation().append(getProjectNameFieldValue()).toOSString();
		return getLocationPath().toOSString().equals(defaultLocation);
	}

	protected void selectGitCloneGeneration()
	{
		noGenerator.setSelection(false);
		gitCloneGenerate.setSelection(true);
	}

	private abstract class SpecialText extends StyledText
	{

		protected boolean justGainedFocus;

		public SpecialText(Composite parent, int style)
		{
			super(parent, style);
			addModifyListener(new ModifyListener()
			{
				/*
				 * (non-Javadoc)
				 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
				 */
				public void modifyText(ModifyEvent e)
				{
					if (!matchesLastDefault())
						setStyleRange(new StyleRange(0, getCharCount(), null, null));
					else
						setStyleRange(new StyleRange(0, getCharCount(), getShell().getDisplay().getSystemColor(
								SWT.COLOR_DARK_GRAY), null, SWT.ITALIC));
				}
			});
			addFocusListener(new FocusAdapter()
			{
				@Override
				public void focusGained(FocusEvent e)
				{
					super.focusGained(e);
					if (isDefault())
					{
						selectAll();
						justGainedFocus = true;
					}
				}
			});
			addMouseListener(new MouseListener()
			{

				public void mouseUp(MouseEvent e)
				{
				}

				public void mouseDown(MouseEvent e)
				{
					if (justGainedFocus)
					{
						justGainedFocus = false;
						// FIXME Don't do this if everything was already selected! I don't know how to see that since at
						// this point the selection has already changed!
						selectAll();
					}
				}

				public void mouseDoubleClick(MouseEvent e)
				{
				}
			});
		}

		abstract protected boolean matchesLastDefault();

		abstract protected boolean isDefault();

	}
}
