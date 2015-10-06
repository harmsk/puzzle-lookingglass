/*******************************************************************************
 * Copyright (c) 2008, 2015, Washington University in St. Louis.
 * All rights reserved.
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
 * 3. Products derived from the software may not be called "Looking Glass", nor
 *    may "Looking Glass" appear in their name, without prior written permission
 *    of Washington University in St. Louis.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software
 *    developed by Washington University in St. Louis"
 *
 * 5. The gallery of art assets and animations provided with this software is
 *    contributed by Electronic Arts Inc. and may be used for personal,
 *    non-commercial, and academic use only. Redistributions of any program
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in
 *    The Alice 3.0 Art Gallery License.
 *
 * DISCLAIMER:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.  ANY AND ALL
 * EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A PARTICULAR PURPOSE,
 * TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHORS,
 * COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package edu.wustl.lookingglass.ide.croquet.components;

import org.alice.ide.browser.BrowserOperation;
import org.lgna.croquet.history.Transaction;
import org.lgna.croquet.triggers.Trigger;
import org.lgna.croquet.views.Dialog;

import edu.cmu.cs.dennisc.java.awt.WindowUtilities;

/**
 * @author Michael Pogran
 */
public class ErrorDialog extends Dialog {

	public static class Builder {
		public Builder( String message ) {
			this.message = message;
		}

		public Builder owner( java.awt.Component parentComponent ) {
			this.owner = parentComponent;
			return this;
		}

		public Builder title( String title ) {
			this.title = title;
			return this;
		}

		public Builder helpText( String helpText ) {
			this.helpText = helpText;
			return this;
		}

		public Builder icon( javax.swing.Icon icon ) {
			this.icon = icon;
			return this;
		}

		public Builder action( BrowserOperation action ) {
			this.action = action;
			return this;
		}

		private ErrorDialog build() {
			return new ErrorDialog( this );
		}

		public void buildAndShow() {
			this.build().show();
		}

		private java.awt.Component owner;
		private String message;
		private String title;
		private String helpText;
		private javax.swing.Icon icon;
		private BrowserOperation action;
	}

	private java.awt.Component owner;
	private Object message;
	private String helpText;
	private String title;
	private javax.swing.Icon icon;
	private BrowserOperation action;

	public ErrorDialog( Builder builder ) {
		super( null, true );
		this.owner = builder.owner;
		this.message = builder.message;
		this.helpText = builder.helpText;
		this.title = builder.title;
		this.icon = builder.icon == null ? edu.wustl.lookingglass.ide.LookingGlassTheme.getIcon( "logo-128x128", org.lgna.croquet.icon.IconSize.FIXED ) : builder.icon;
		this.action = builder.action;

		setTitle( this.title );

		org.lgna.croquet.Operation closeOperation = new org.lgna.croquet.Operation( org.lgna.croquet.Application.APPLICATION_UI_GROUP, java.util.UUID.randomUUID() ) {

			@Override
			protected void perform( Transaction transaction, Trigger trigger ) {
				setVisible( false );
			}

			@Override
			protected void localize() {
				setName( "Okay" );
			}
		};
		org.lgna.croquet.views.MigPanel panel = new org.lgna.croquet.views.MigPanel( null, "fill", "[]10[]", "[]5[]5[][]5[]" );
		StringBuilder sb = new StringBuilder();
		sb.append( "<html>" );
		sb.append( "<p style='text-align:left; font-family:sans-serif; font-size:12px'>" );
		sb.append( message );
		sb.append( "</p>" );
		sb.append( "</html>" );
		panel.addComponent( new org.lgna.croquet.views.Label( this.icon ), "cell 0 0, spany 2" );
		panel.addComponent( new org.lgna.croquet.views.Label( sb.toString() ), "cell 1 0, growx" );

		if( this.helpText != null ) {
			panel.addComponent( new org.lgna.croquet.views.PlainMultiLineLabel( this.helpText ), "cell 1 1, grow, push" );
		}
		if( this.action != null ) {
			panel.addComponent( action.createExternalHyperlink(), "cell 1 2, right" );
		}
		panel.addComponent( org.lgna.croquet.views.Separator.createInstanceSeparatingTopFromBottom(), "cell 0 3, span 2, growx" );
		panel.addComponent( closeOperation.createButton(), "cell 0 4, span 2, right" );

		getContentPane().addCenterComponent( panel );
		pack();
	}

	public void show() {
		WindowUtilities.setLocationOnScreenToCenteredWithin( getAwtComponent(), this.owner );
		this.setVisible( true );
	}
}
