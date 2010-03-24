/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPartConstants;

/**
 * Common function for Breakpoint editor controls.
 * 
 * @since 1.0
 */
public abstract class AbstractRubyBreakpointEditor {
	
    private ListenerList fListeners = new ListenerList();
    private boolean fDirty = false;
    private boolean fMnemonics = true;
	
	/**
	 * Adds the given property listener to this editor. Property changes
	 * are reported on the breakpoint being edited. Property identifiers
	 * are breakpoint attribute keys.
	 * 
	 * @param listener listener
	 */
	public void addPropertyListener(IPropertyListener listener) {
		fListeners.add(listener);
	}
	
	/**
	 * Sets whether mnemonics should be displayed in editor controls.
	 * Only has an effect if set before {@link #createControl(Composite)}
	 * is called. By default, mnemonics are displayed.
	 * 
	 * @param mnemonics whether to display mnemonics
	 */
	public void setMnemonics(boolean mnemonics) {
		fMnemonics = mnemonics;
	}
	
	/**
	 * Returns whether mnemonics should be displayed in editor controls.
	 * 
	 * @return whether mnemonics should be displayed in editor controls
	 */
	protected boolean isMnemonics() {
		return fMnemonics;
	}
	
	/**
	 * Returns text with mnemonics in tact or removed based on {@link #isMnemonics()}.
	 * 
	 * @param text string to process 
	 * @return text with mnemonics in tact or removed based on {@link #isMnemonics()}
	 */
	protected String processMnemonics(String text) {
		if (isMnemonics()) {
			return text;
		}
		return LegacyActionTools.removeMnemonics(text);
	}
	
	/**
	 * Removes the property listener from this editor.
	 * 
	 * @param listener listener
	 */
	public void removePropertyListener(IPropertyListener listener) {
		fListeners.remove(listener);
	}
	
	/**
	 * Creates the condition editor widgets and returns the top level
	 * control.
	 * 
	 * @param parent composite to embed the editor controls in
	 * @return top level control
	 */
	public abstract Control createControl(Composite parent);
	
	/**
	 * Disposes this editor and its controls. Once disposed, the editor can no
	 * longer be used.
	 */
	protected void dispose() {
		fListeners.clear();
	}
	
	/**
	 * Gives focus to an appropriate control in the editor.
	 */
	public abstract void setFocus();
	
	/**
	 * Saves current settings to the breakpoint being edited. Has no
	 * effect if a breakpoint is not currently being edited or if this
	 * editor is not dirty.
	 * 
	 * @exception CoreException if unable to update the breakpoint.
	 */
	public abstract void doSave() throws CoreException;
	
	/**
	 * Returns a status describing whether the condition editor is in
	 * a valid state. Returns an OK status when all is good. For example, an error
	 * status is returned when the conditional expression is empty but enabled.
	 * 
	 * @return editor status.
	 */
	public abstract IStatus getStatus();
		
	/**
	 * Returns whether the editor needs saving.
	 *  
	 * @return whether the editor needs saving
	 */
	public boolean isDirty() {
		return fDirty;
	}
	
	/**
	 * Sets the dirty flag based on the given property change
	 * 
	 * @param propId the property that changed
	 */
	protected void setDirty(int propId) {
		fDirty = true;
		firePropertyChange(propId);
	}
	
	/**
	 * Sets the dirty flag and fires a dirty property change if required.
	 * 
	 * @param dirty whether dirty
	 */
	protected void setDirty(boolean dirty) {
		if (fDirty != dirty) {
			fDirty = dirty;
			firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
		}
	}
	
	/**
	 * Fires a property change event to all listeners.
	 * 
	 * @param source
	 * @param propId
	 * @param value
	 */
	protected void firePropertyChange(int propId) {
		Object[] listeners = fListeners.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			IPropertyListener listener = (IPropertyListener) listeners[i];
			listener.propertyChanged(this, propId);
		}
	}
	
	/**
	 * Returns the breakpoint being displayed or <code>null</code> if none.
	 * 
	 * @return breakpoint or <code>null</code>
	 */
	public abstract Object getInput();
	
	/**
	 * Sets the breakpoint to display and edit or <code>null</code> if none.
	 * 
	 * @param breakpoint breakpoint or <code>null</code>
	 * @exception CoreException if unable to set the input
	 */
	public abstract void setInput(Object breakpoint) throws CoreException;
	
}
