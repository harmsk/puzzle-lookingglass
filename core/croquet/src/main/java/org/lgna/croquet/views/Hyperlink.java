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

package org.lgna.croquet.views;

/**
 * @author Dennis Cosgrove
 */
public class Hyperlink extends OperationButton<javax.swing.JButton, org.lgna.croquet.Operation> {
	public Hyperlink( org.lgna.croquet.Operation model ) {
		super( model );
	}

	public Hyperlink( org.lgna.croquet.Operation model, float fontScalar, edu.cmu.cs.dennisc.java.awt.font.TextAttribute<?>... textAttributes ) {
		this( model );
		this.scaleFont( fontScalar );
		this.changeFont( textAttributes );
	}

	public Hyperlink( org.lgna.croquet.Operation model, edu.cmu.cs.dennisc.java.awt.font.TextAttribute<?>... textAttributes ) {
		this( model, 1.0f, textAttributes );
	}

	public boolean isUnderlinedOnlyWhenRolledOver() {
		edu.cmu.cs.dennisc.javax.swing.plaf.HyperlinkUI ui = (edu.cmu.cs.dennisc.javax.swing.plaf.HyperlinkUI)this.getAwtComponent().getUI();
		return ui.isUnderlinedOnlyWhenRolledOver();
	}

	public void setUnderlinedOnlyWhenRolledOver( boolean isUnderlinedOnlyWhenRolledOver ) {
		edu.cmu.cs.dennisc.javax.swing.plaf.HyperlinkUI ui = (edu.cmu.cs.dennisc.javax.swing.plaf.HyperlinkUI)this.getAwtComponent().getUI();
		ui.setUnderlinedOnlyWhenRolledOver( isUnderlinedOnlyWhenRolledOver );
	}

	@Override
	protected final javax.swing.JButton createAwtComponent() {
		javax.swing.JButton rv = new javax.swing.JButton() {
			@Override
			public String getText() {
				if( isTextClobbered() ) {
					return getClobberText();
				} else {
					return super.getText();
				}
			}

			@Override
			public void updateUI() {
				this.setUI( edu.cmu.cs.dennisc.javax.swing.plaf.HyperlinkUI.createUI( this ) );
			}
		};

		// <lg/> easier to see with Looking Glass dark colors
		rv.setForeground( new java.awt.Color( 0, 31, 183 ) );
		rv.setBackground( javax.swing.UIManager.getColor( "control" ) );
		rv.setRolloverEnabled( true );
		rv.setHorizontalAlignment( javax.swing.SwingConstants.LEADING );
		rv.setBorder( javax.swing.BorderFactory.createEmptyBorder() );
		rv.setOpaque( false );
		rv.setCursor( java.awt.Cursor.getPredefinedCursor( java.awt.Cursor.HAND_CURSOR ) );
		return rv;
	}
}
