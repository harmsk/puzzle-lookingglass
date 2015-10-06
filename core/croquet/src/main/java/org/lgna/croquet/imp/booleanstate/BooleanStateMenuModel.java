/*******************************************************************************
 * Copyright (c) 2006, 2015, Carnegie Mellon University. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Products derived from the software may not be called "Alice", nor may
 *    "Alice" appear in their name, without prior written permission of
 *    Carnegie Mellon University.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software
 *    developed by Carnegie Mellon University"
 *
 * 5. The gallery of art assets and animations provided with this software is
 *    contributed by Electronic Arts Inc. and may be used for personal,
 *    non-commercial, and academic use only. Redistributions of any program
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in
 *    The Alice 3.0 Art Gallery License.
 *
 * DISCLAIMER:
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
 * ANY AND ALL EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT
 * SHALL THE AUTHORS, COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package org.lgna.croquet.imp.booleanstate;

import org.lgna.croquet.BooleanState;
import org.lgna.croquet.MenuModel;
import org.lgna.croquet.Operation;

/**
 * @author Dennis Cosgrove
 */
/*package-private*/class BooleanStateMenuModel extends MenuModel {
	public BooleanStateMenuModel( BooleanState state ) {
		super( java.util.UUID.fromString( "89447818-3c15-4707-9464-79c3f0283262" ), state.getClass() );
		this.state = state;
	}

	@Override
	protected void initialize() {
		this.state.initializeIfNecessary();
		super.initialize();
	}

	@Override
	protected String getSubKeyForLocalization() {
		return "menu";
	}

	public BooleanState getBooleanState() {
		return this.state;
	}

	@Override
	public boolean isEnabled() {
		return this.state.isEnabled();
	}

	@Override
	public void setEnabled( boolean isEnabled ) {
		this.state.setEnabled( isEnabled );
	}

	@Override
	protected void handleShowing( org.lgna.croquet.views.MenuItemContainer menuItemContainer, javax.swing.event.PopupMenuEvent e ) {
		edu.cmu.cs.dennisc.java.util.logging.Logger.todo( menuItemContainer, e );
		super.handleShowing( menuItemContainer, e );
		javax.swing.ButtonGroup buttonGroup = new javax.swing.ButtonGroup();
		for( boolean isTrue : new boolean[] { true, false } ) {
			Operation operation = isTrue ? this.state.getSetToTrueOperation() : this.state.getSetToFalseOperation();
			operation.initializeIfNecessary();
			javax.swing.Action action = operation.getImp().getSwingModel().getAction();
			javax.swing.JCheckBoxMenuItem jMenuItem = new javax.swing.JCheckBoxMenuItem( action );
			buttonGroup.add( jMenuItem );
			jMenuItem.setSelected( this.state.getValue() == isTrue );
			menuItemContainer.getViewController().getAwtComponent().add( jMenuItem );
		}
	}

	@Override
	protected void handleHiding( org.lgna.croquet.views.MenuItemContainer menuItemContainer, javax.swing.event.PopupMenuEvent e ) {
		menuItemContainer.forgetAndRemoveAllMenuItems();
		super.handleHiding( menuItemContainer, e );
	}

	private final BooleanState state;
}
