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
public class ParametersPane extends org.alice.ide.croquet.components.AbstractListPropertyPane<org.lgna.project.ast.NodeListProperty<org.lgna.project.ast.UserParameter>, org.lgna.project.ast.UserParameter> {
	public ParametersPane( org.alice.ide.x.AstI18nFactory factory, org.lgna.project.ast.UserCode code ) {
		super( factory, code.getRequiredParamtersProperty(), javax.swing.BoxLayout.LINE_AXIS );
		if( this.getProperty().size() > 0 ) {
			setBorder( javax.swing.BorderFactory.createEmptyBorder( 4, 4, 4, 4 ) );
		}
	}

	protected org.alice.ide.IDE getIDE() {
		return org.alice.ide.IDE.getActiveInstance();
	}

	private org.lgna.project.ast.UserCode getCode() {
		return (org.lgna.project.ast.UserCode)getProperty().getOwner();
	}

	@Override
	protected org.lgna.croquet.views.AwtComponentView<?> createComponent( org.lgna.project.ast.UserParameter parameter ) {
		return new TypedParameterPane( getProperty(), parameter );
	}

	//<lg>
	@Override
	protected javax.swing.JPanel createJPanel() {
		javax.swing.JPanel rv = new DefaultJPanel() {

			@Override
			protected void paintComponent( java.awt.Graphics g ) {
				int w = this.getWidth();
				int h = this.getHeight();
				java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
				g2.setRenderingHint( java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON );
				g2.setClip( -2, -2, w + 4, w + 4 );

				java.awt.Color backgroundColor = this.getParent().getBackground();
				java.awt.Color fillColor = edu.cmu.cs.dennisc.java.awt.ColorUtilities.scaleHSB( backgroundColor, 1.0, 0.9, 0.85 );
				java.awt.Color lineColor = edu.cmu.cs.dennisc.java.awt.ColorUtilities.scaleHSB( backgroundColor, 1.0, 0.9, 0.75 );

				g2.setPaint( fillColor );
				g2.fillRoundRect( 0, 0, w, h, 10, 10 );
				g2.setStroke( new java.awt.BasicStroke( 1.35f ) );
				g2.setPaint( lineColor );
				g2.drawRoundRect( 0, 0, w, h, 10, 10 );
			}
		};
		return rv;
	}

	//</lg>

	@Override
	protected void addPrefixComponents() {
		//super.addPrefixComponents();
		if( org.alice.ide.croquet.models.ui.formatter.FormatterState.isJava() ) {
			this.addComponent( new org.lgna.croquet.views.Label( "( " ) );
		} else {
			int n = this.getProperty().size();
			String text;
			switch( n ) {
			case 0:
				text = null;
				break;
			case 1:
				text = " with parameter: ";
				break;
			default:
				text = " with parameters: ";
			}
			if( text != null ) {
				org.lgna.croquet.views.Label prefixLabel = new org.lgna.croquet.views.Label( text, edu.cmu.cs.dennisc.java.awt.font.TextWeight.LIGHT );
				java.awt.Color backgroundColor = this.getParent().getBackgroundColor();
				java.awt.Color foregroundColor = edu.cmu.cs.dennisc.java.awt.ColorUtilities.scaleHSB( backgroundColor, 1.0, 1.0, 0.25 );
				prefixLabel.setForegroundColor( foregroundColor );
				this.addComponent( prefixLabel );
			}
		}
	}

	@Override
	protected org.lgna.croquet.views.AwtComponentView<?> createInterstitial( int i, int N ) {
		if( i < ( N - 1 ) ) {
			return new org.lgna.croquet.views.Label( ", " );
		} else {
			return org.lgna.croquet.views.BoxUtilities.createHorizontalSliver( 4 );
		}
	}

	@Override
	protected void addPostfixComponents() {
		super.addPostfixComponents();
		org.alice.ide.x.AstI18nFactory factory = this.getFactory();
		if( factory.isSignatureLocked( this.getCode() ) ) {
			//pass
		} else {
			this.addComponent( org.alice.ide.ast.declaration.AddParameterComposite.getInstance( this.getCode() ).getLaunchOperation().createButton() );
		}
		if( org.alice.ide.croquet.models.ui.formatter.FormatterState.isJava() ) {
			this.addComponent( new org.lgna.croquet.views.Label( " )" ) );
		}
	}
}
