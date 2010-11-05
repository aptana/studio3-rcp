/**
 * This file Copyright (c) 2005-2010 Aptana, Inc. This program is
 * dual-licensed under both the Aptana Public License and the GNU General
 * Public license. You may elect to use one or the other of these licenses.
 * 
 * This program is distributed in the hope that it will be useful, but
 * AS-IS and WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, TITLE, or
 * NONINFRINGEMENT. Redistribution, except as permitted by whichever of
 * the GPL or APL you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or modify this
 * program under the terms of the GNU General Public License,
 * Version 3, as published by the Free Software Foundation.  You should
 * have received a copy of the GNU General Public License, Version 3 along
 * with this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Aptana provides a special exception to allow redistribution of this file
 * with certain other free and open source software ("FOSS") code and certain additional terms
 * pursuant to Section 7 of the GPL. You may view the exception and these
 * terms on the web at http://www.aptana.com/legal/gpl/.
 * 
 * 2. For the Aptana Public License (APL), this program and the
 * accompanying materials are made available under the terms of the APL
 * v1.0 which accompanies this distribution, and is available at
 * http://www.aptana.com/legal/apl/.
 * 
 * You may view the GPL, Aptana's exception and additional terms, and the
 * APL in the file titled license.html at the root of the corresponding
 * plugin containing this source file.
 * 
 * Any modifications to this file must keep this entire header intact.
 */
package org.radrails.rails.internal.ui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.radrails.rails.ui.RailsUIPlugin;

import com.aptana.ruby.ui.wizards.WizardNewRubyProjectCreationPage;

public class WizardNewRailsProjectCreationPage extends WizardNewRubyProjectCreationPage
{

	private static final String ICON_WARNING = "icons/warning_48.png"; //$NON-NLS-1$

	// widgets
	private Button runGenerator;
	private CLabel projectGenerationErrorLabel;
	private boolean hasRailsAppFiles;

	private Composite projectGenerationControls;

	/**
	 * Creates a new project creation wizard page.
	 * 
	 * @param pageName
	 *            the name of this page
	 */
	public WizardNewRailsProjectCreationPage(String pageName)
	{
		super(pageName);
	}

	@Override
	protected Composite createGenerateGroup(Composite parent)
	{
		Composite projectGenerationGroup = super.createGenerateGroup(parent);

		// Create an error label that we'll display in a case where the project
		// is created in a location that contains a Rails project files.
		projectGenerationErrorLabel = new CLabel(projectGenerationGroup, SWT.WRAP);
		projectGenerationErrorLabel.setText(Messages.WizardNewRailsProjectCreationPage_cannotCreateProjectMessage);
		projectGenerationErrorLabel.setImage(RailsUIPlugin.getImage(ICON_WARNING));

		return projectGenerationGroup;
	}

	protected void createGenerationOptions(Composite projectGenerationControls)
	{
		runGenerator = new Button(projectGenerationControls, SWT.RADIO);
		runGenerator.setText(Messages.WizardNewRailsProjectCreationPage_StandardGeneratorText);

		super.createGenerationOptions(projectGenerationControls);

		noGenerator.setSelection(false);
		runGenerator.setSelection(true);

		this.projectGenerationControls = projectGenerationControls;
	}

	/**
	 * Returns whether this page's controls currently all contain valid values.
	 * 
	 * @return <code>true</code> if all controls are valid, and <code>false</code> if at least one is invalid
	 */
	protected boolean validatePage()
	{
		// Validate that there is no Rails project already existing in the
		// new project location
		hasRailsAppFiles = hasRailsApp(getLocationText());
		if (hasRailsAppFiles)
		{
			setTopControl(projectGenerationErrorLabel);
		}
		else
		{
			setTopControl(projectGenerationControls);
		}
		return super.validatePage();
	}

	private boolean hasRailsApp(String path)
	{
		File projectFile = new File(path);
		File env = new File(projectFile, "config" + File.separator + "environment.rb"); //$NON-NLS-1$ //$NON-NLS-2$
		return env.exists();
	}

	protected boolean runGenerator()
	{
		return !hasRailsAppFiles && runGenerator.getSelection();
	}
}
