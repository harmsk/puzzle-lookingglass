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
package org.alice.ide.codeeditor;

/**
 * @author Dennis Cosgrove
 */
public class MethodHeaderPane extends AbstractCodeHeaderPane {
	private final org.alice.ide.x.AstI18nFactory factory;
	private final org.lgna.project.ast.UserMethod userMethod;

	public MethodHeaderPane( org.alice.ide.x.AstI18nFactory factory, org.lgna.project.ast.UserMethod userMethod, boolean isPreview ) {
		super( isPreview );
		this.factory = factory;
		this.userMethod = userMethod;
	}

	protected org.lgna.croquet.views.SwingComponentView<?> createNameLabel() {
		return this.factory.createNameView( this.userMethod );
	}

	@Override
	protected void internalRefresh() {
		super.internalRefresh();
		this.forgetAndRemoveAllComponents();

		//<lg>
		java.awt.Color backgroundColor = this.getParent().getBackgroundColor();
		java.awt.Color foregroundColor = edu.cmu.cs.dennisc.java.awt.ColorUtilities.scaleHSB( backgroundColor, 1.0, 1.5, 0.35 );

		if( org.alice.ide.croquet.models.ui.formatter.FormatterState.isJava() ) {
			this.addComponent( org.alice.ide.common.TypeComponent.createInstance( userMethod.getReturnType() ) );
			foregroundColor = java.awt.Color.BLACK;
		} else {
			if( userMethod.isFunction() ) {
				org.lgna.croquet.views.Label customLabel = new org.lgna.croquet.views.Label( "custom", edu.cmu.cs.dennisc.java.awt.font.TextWeight.BOLD );
				org.lgna.croquet.views.Label questionLabel = new org.lgna.croquet.views.Label( "question", edu.cmu.cs.dennisc.java.awt.font.TextWeight.BOLD );
				customLabel.setForegroundColor( foregroundColor );
				questionLabel.setForegroundColor( foregroundColor );
				this.addComponent( customLabel );
				if( this.isPreview() ) {
					this.addComponent( org.lgna.croquet.views.BoxUtilities.createRigidArea( 5, 0 ) );
				}
				this.addComponent( org.alice.ide.common.TypeComponent.createInstance( userMethod.getReturnType() ) );
				if( this.isPreview() ) {
					this.addComponent( org.lgna.croquet.views.BoxUtilities.createRigidArea( 5, 0 ) );
				}
				this.addComponent( questionLabel );
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append( "custom " );

				if( userMethod.isStatic() ) {
					sb.append( "static " );
				}
				sb.append( "action" );
				org.lgna.croquet.views.Label procedureLabel = new org.lgna.croquet.views.Label( sb.toString(), edu.cmu.cs.dennisc.java.awt.font.TextWeight.BOLD );
				procedureLabel.setForegroundColor( foregroundColor );
				this.addComponent( procedureLabel );
			}
		}
		if( this.isPreview() ) {
			this.addComponent( org.lgna.croquet.views.BoxUtilities.createRigidArea( 5, 0 ) );
		}

		org.lgna.croquet.views.SwingComponentView<?> nameLabel = this.createNameLabel();
		nameLabel.scaleFont( NAME_SCALE );
		nameLabel.setForegroundColor( foregroundColor );

		if( userMethod.isSignatureLocked.getValue() ) {
			this.addComponent( nameLabel );
		} else {
			class PopupPanel extends org.lgna.croquet.views.ViewController<javax.swing.JPanel, org.lgna.croquet.Model> {
				private org.lgna.croquet.views.AwtComponentView<?> centerComponent;

				public PopupPanel( org.lgna.croquet.views.AwtComponentView<?> centerComponent, org.lgna.croquet.MenuModel.InternalPopupPrepModel popupMenuOperation ) {
					super( null );
					this.centerComponent = centerComponent;
					this.setPopupPrepModel( popupMenuOperation );
				}

				@Override
				protected javax.swing.JPanel createAwtComponent() {
					javax.swing.JPanel rv = new javax.swing.JPanel() {
						@Override
						public java.awt.Dimension getMaximumSize() {
							return this.getPreferredSize();
						}
					};
					rv.setBackground( null );
					rv.setOpaque( false );
					rv.setLayout( new java.awt.BorderLayout() );
					rv.add( centerComponent.getAwtComponent(), java.awt.BorderLayout.CENTER );
					return rv;
				}
			}
			this.addComponent( new PopupPanel( nameLabel, org.alice.ide.croquet.models.ast.MethodHeaderMenuModel.getInstance( userMethod ).getPopupPrepModel() ) );
		}

		if( ( ( this.isPreview() == false ) || ( this.userMethod.requiredParameters.size() > 0 ) ) && !userMethod.isSignatureLocked.getValue() ) {
			this.addComponent( new ParametersPane( this.factory, this.userMethod ) );
		}

		setBorder( javax.swing.BorderFactory.createEmptyBorder( 0, 0, 4, 0 ) );
		//</lg>
	}
}
