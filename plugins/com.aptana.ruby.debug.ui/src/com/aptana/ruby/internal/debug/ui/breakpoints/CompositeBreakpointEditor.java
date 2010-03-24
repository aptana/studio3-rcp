/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.aptana.ruby.internal.debug.ui.breakpoints;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPropertyListener;

/**
 * Combines editors.
 * 
 * @since 1.0
 */
public class CompositeBreakpointEditor extends AbstractRubyBreakpointEditor {
	
	private AbstractRubyBreakpointEditor[] fEditors;
	
	public CompositeBreakpointEditor(AbstractRubyBreakpointEditor[] editors) {
		fEditors = editors;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.ui.breakpoints.AbstractRubyBreakpointEditor#addPropertyListener(org.eclipse.ui.IPropertyListener)
	 */
	public void addPropertyListener(IPropertyListener listener) {
		for (int i = 0; i < fEditors.length; i++) {
			fEditors[i].addPropertyListener(listener);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.ui.breakpoints.AbstractRubyBreakpointEditor#removePropertyListener(org.eclipse.ui.IPropertyListener)
	 */
	public void removePropertyListener(IPropertyListener listener) {
		for (int i = 0; i < fEditors.length; i++) {
			fEditors[i].removePropertyListener(listener);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.ui.breakpoints.AbstractRubyBreakpointEditor#dispose()
	 */
	protected void dispose() {
		for (int i = 0; i < fEditors.length; i++) {
			fEditors[i].dispose();
		}
		fEditors = null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.ui.breakpoints.AbstractRubyBreakpointEditor#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public Control createControl(Composite parent) {
		Composite comp = SWTFactory.createComposite(parent, parent.getFont(), 1, 1, GridData.FILL_BOTH, 0, 0);
		for (int i = 0; i < fEditors.length; i++) {
			fEditors[i].createControl(comp);
		}
		return comp;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.ui.breakpoints.AbstractRubyBreakpointEditor#setFocus()
	 */
	public void setFocus() {
		fEditors[0].setFocus();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.ui.breakpoints.AbstractRubyBreakpointEditor#doSave()
	 */
	public void doSave() throws CoreException {
		for (int i = 0; i < fEditors.length; i++) {
			fEditors[i].doSave();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.ui.breakpoints.AbstractRubyBreakpointEditor#isDirty()
	 */
	public boolean isDirty() {
		for (int i = 0; i < fEditors.length; i++) {
			if (fEditors[i].isDirty()) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.ui.breakpoints.AbstractRubyBreakpointEditor#getStatus()
	 */
	public IStatus getStatus() {
		for (int i = 0; i < fEditors.length; i++) {
			IStatus status = fEditors[i].getStatus();
			if (!status.isOK()) {
				return status;
			}
		}
		return Status.OK_STATUS;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.ui.breakpoints.AbstractRubyBreakpointEditor#getInput()
	 */
	public Object getInput() {
		return fEditors[0].getInput();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.ui.breakpoints.AbstractRubyBreakpointEditor#setInput(java.lang.Object)
	 */
	public void setInput(Object breakpoint) throws CoreException {
		for (int i = 0; i < fEditors.length; i++) {
			fEditors[i].setInput(breakpoint);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.ui.breakpoints.AbstractRubyBreakpointEditor#setMnemonics(boolean)
	 */
	public void setMnemonics(boolean mnemonics) {
		super.setMnemonics(mnemonics);
		for (int i = 0; i < fEditors.length; i++) {
			fEditors[i].setMnemonics(mnemonics);
		}
	}

}
