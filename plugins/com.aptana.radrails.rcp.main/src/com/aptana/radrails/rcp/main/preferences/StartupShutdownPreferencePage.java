/**
 * Copyright (c) 2005-2006 Aptana, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html. If redistributing this code,
 * this entire header must remain intact.
 */
package com.aptana.radrails.rcp.main.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.aptana.radrails.rcp.main.MainPlugin;

/**
 * Allows the user to edit the set of user agents
 * 
 * @since 3.1
 */
public final class StartupShutdownPreferencePage extends PreferencePage implements IWorkbenchPreferencePage
{

	private BooleanFieldEditor nameEditor;

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent)
	{

		Composite entryTable = new Composite(parent, SWT.NULL);

		// Create a data that takes up the extra space in the dialog .
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		entryTable.setLayoutData(data);

		GridLayout layout = new GridLayout();
		entryTable.setLayout(layout);

		Composite colorComposite = new Composite(entryTable, SWT.NONE);

		colorComposite.setLayout(new GridLayout());

		// Create a data that takes up the extra space in the dialog.
		colorComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		nameEditor = new BooleanFieldEditor(IPreferenceConstants.REOPEN_EDITORS_ON_STARTUP,
				Messages.StartupShutdownPreferencePage_ReopenExternalFilesOnStartup, colorComposite);

		// Set the editor up to use this page
		nameEditor.setPage(this);
		nameEditor.setPreferenceStore(getPreferenceStore());
		nameEditor.load();

		return entryTable;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench)
	{
		// Initialize the preference store we wish to use
		setPreferenceStore(MainPlugin.getDefault().getPreferenceStore());
	}

	/**
	 * Performs special processing when this page's Restore Defaults button has been pressed. Sets the contents of the
	 * color field to the default value in the preference store.
	 */
	protected void performDefaults()
	{
		nameEditor.loadDefault();
	}

	/**
	 * Method declared on IPreferencePage. Save the color preference to the preference store.
	 * 
	 * @return boolean
	 */
	public boolean performOk()
	{
		nameEditor.store();
		return super.performOk();
	}

}
