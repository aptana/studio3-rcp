/***********************************************************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others. All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/cpl-v10.html Contributors: IBM Corporation - initial API and implementation
 **********************************************************************************************************************/
package com.aptana.radrails.rcp;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.cheatsheets.ICheatSheetViewer;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.intro.config.IStandbyContentPart;

/**
 * CheatSheetStandbyContent
 */
public final class CheatSheetStandbyContent implements IStandbyContentPart
{

	private static String MEMENTO_CHEATSHEET_ID_ATT = "cheatsheetId"; //$NON-NLS-1$

	// private IIntroPart introPart;
	private ICheatSheetViewer viewer;
	private Composite container;
	private String input;

	/**
	 * @see org.eclipse.ui.intro.config.IStandbyContentPart#init(org.eclipse.ui.intro.IIntroPart,
	 *      org.eclipse.ui.IMemento)
	 */
	public void init(IIntroPart introPart, IMemento memento)
	{
		// this.introPart = introPart;
		// try to restore last state.
		input = getCachedInput(memento);
	}

	/**
	 * @see org.eclipse.ui.intro.config.IStandbyContentPart#createPartControl(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.forms.widgets.FormToolkit)
	 */
	public void createPartControl(Composite parent, FormToolkit toolkit)
	{
		container = toolkit.createComposite(parent);
		FillLayout layout = new FillLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		container.setLayout(layout);

		viewer = new AptanaCheatSheetViewer(); // CheatSheetViewerFactory.createCheatSheetView();
		viewer.createPartControl(container);
	}

	/**
	 * @see org.eclipse.ui.intro.config.IStandbyContentPart#getControl()
	 */
	public Control getControl()
	{
		return container;
	}

	/**
	 * @see org.eclipse.ui.intro.config.IStandbyContentPart#setInput(java.lang.Object)
	 */
	public void setInput(Object input)
	{
		// if the new input is null, use cached input from momento.
		if (input != null)
		{
			this.input = (String) input;

			try
			{
				viewer.setInput(null, null, new URL(this.input));
			}
			catch (MalformedURLException e)
			{
				// TODO logging
				IdePlugin.getDefault().getLog().log(
						new Status(IStatus.ERROR,
								IdePlugin.getDefault().getBundle().getSymbolicName(),
								IStatus.OK,
								MessageFormat.format(Messages.CheatSheetStandbyContent_URLIsMalformed, this.input),
								e)
						);
			}
		}
		else
		{
			try
			{
				viewer.setInput(null, null, new URL(this.input));
			}
			catch (MalformedURLException e)
			{
				// TODO logging
				IdePlugin.getDefault().getLog().log(
						new Status(IStatus.ERROR,
								IdePlugin.getDefault().getBundle().getSymbolicName(),
								IStatus.OK,
								MessageFormat.format(Messages.CheatSheetStandbyContent_URLIsMalformed, this.input),
								e)
						);
			}
		}
	}

	/**
	 * @see org.eclipse.ui.intro.config.IStandbyContentPart#setFocus()
	 */
	public void setFocus()
	{
		viewer.setFocus();
	}

	/**
	 * @see org.eclipse.ui.intro.config.IStandbyContentPart#dispose()
	 */
	public void dispose()
	{

	}

	/**
	 * @see org.eclipse.ui.intro.config.IStandbyContentPart#saveState(org.eclipse.ui.IMemento)
	 */
	public void saveState(IMemento memento)
	{
		if (this.input != null)
		{
			memento.putString(MEMENTO_CHEATSHEET_ID_ATT, this.input);
		}
	}

	/**
	 * Tries to create the last content part viewed, based on content part id..
	 * 
	 * @param memento
	 * @return String
	 */
	private String getCachedInput(IMemento memento)
	{
		if (memento == null)
		{
			return null;
		}

		return memento.getString(MEMENTO_CHEATSHEET_ID_ATT);

	}

}
