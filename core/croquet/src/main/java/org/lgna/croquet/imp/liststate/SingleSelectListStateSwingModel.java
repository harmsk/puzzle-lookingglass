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
package org.lgna.croquet.imp.liststate;

/**
 * @author Dennis Cosgrove
 */
public class SingleSelectListStateSwingModel {
	public SingleSelectListStateSwingModel( javax.swing.ComboBoxModel comboBoxModel ) {
		this.comboBoxModel = comboBoxModel;
		this.listSelectionModel = new javax.swing.DefaultListSelectionModel();
		this.listSelectionModel.setSelectionMode( javax.swing.ListSelectionModel.SINGLE_SELECTION );
	}

	public javax.swing.ComboBoxModel getComboBoxModel() {
		return this.comboBoxModel;
	}

	public javax.swing.ListSelectionModel getListSelectionModel() {
		return this.listSelectionModel;
	}

	//<lg> want to be able to set list selection model
	public void setListSelectionModel( javax.swing.DefaultListSelectionModel listSelectionModel ) {
		this.listSelectionModel = listSelectionModel;
		this.listSelectionModel.setSelectionMode( javax.swing.ListSelectionModel.SINGLE_SELECTION );
	}

	//</lg>

	public int getSelectionIndex() {
		if( this.listSelectionModel.isSelectionEmpty() ) {
			return -1;
		} else {
			return this.listSelectionModel.getLeadSelectionIndex();
		}
	}

	public void setSelectionIndex( int index ) {
		if( index != -1 ) {
			this.listSelectionModel.setSelectionInterval( index, index );
		} else {
			this.listSelectionModel.clearSelection();
		}
	}

	public void fireListSelectionChanged( int firstIndex, int lastIndex, boolean isAdjusting ) {
		javax.swing.event.ListSelectionEvent e = new javax.swing.event.ListSelectionEvent( this, firstIndex, lastIndex, isAdjusting );
		for( javax.swing.event.ListSelectionListener listener : this.listSelectionModel.getListSelectionListeners() ) {
			listener.valueChanged( e );
		}
	}

	@Deprecated
	public void ACCESS_fireContentsChanged( Object source, int index0, int index1 ) {
		// <lg/> edu.cmu.cs.dennisc.java.util.logging.Logger.errln( "todo: fireContentsChanged", source, index0, index1 );
	}

	@Deprecated
	public void ACCESS_fireIntervalAdded( Object source, int index0, int index1 ) {
		// <lg/> edu.cmu.cs.dennisc.java.util.logging.Logger.errln( "todo: fireIntervalAdded", source, index0, index1 );
	}

	@Deprecated
	public void ACCESS_fireIntervalRemoved( Object source, int index0, int index1 ) {
		// <lg/> edu.cmu.cs.dennisc.java.util.logging.Logger.errln( "todo: fireIntervalRemoved", source, index0, index1 );
	}

	private final javax.swing.ComboBoxModel comboBoxModel;
	private/*final*/javax.swing.DefaultListSelectionModel listSelectionModel;
}
