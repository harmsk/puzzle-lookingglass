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

package org.alice.ide.member.views;

/**
 * @author Dennis Cosgrove
 */
public class MethodsSubView<C extends org.alice.ide.member.MethodsSubComposite> extends org.lgna.croquet.views.PageAxisPanel {
	public MethodsSubView( org.alice.ide.member.MethodsSubComposite composite ) {
		super( composite );
		this.setMaximumSizeClampedToPreferredSize( true );
		this.setBorder( javax.swing.BorderFactory.createEmptyBorder( 0, 8, 4, 0 ) ); // <lg/> smaller border shows more statements to kids.
	}

	@Override
	public C getComposite() {
		return (C)super.getComposite();
	}

	@Override
	protected void internalRefresh() {
		super.internalRefresh();
		C composite = this.getComposite();
		this.removeAllComponents();

		composite.updateTabTitle();

		for( org.lgna.project.ast.AbstractMethod method : composite.getMethods() ) {
			org.lgna.croquet.views.DragComponent<?> dragComponent = org.alice.ide.members.components.templates.TemplateFactory.getMethodInvocationTemplate( method );
			org.lgna.croquet.views.SwingComponentView<?> component;
			if( ( method instanceof org.lgna.project.ast.UserMethod ) && !method.getName().toLowerCase().matches( "set\\w*resource" ) ) {
				org.lgna.project.ast.UserMethod userMethod = (org.lgna.project.ast.UserMethod)method;
				org.alice.ide.declarationseditor.DeclarationTabState tabState = org.alice.ide.IDE.getActiveInstance().getDocumentFrame().getDeclarationsEditorComposite().getTabState();
				org.lgna.croquet.Operation operation = tabState.getItemSelectionOperationForMethod( method );
				org.lgna.croquet.views.Hyperlink hyperlink = operation.createHyperlink();
				hyperlink.setClobberText( "edit" );
				component = new org.lgna.croquet.views.LineAxisPanel( hyperlink, org.lgna.croquet.views.BoxUtilities.createHorizontalSliver( 8 ), dragComponent );
			} else {
				if( method.isProcedure() ) {
					org.lgna.croquet.views.Button helpButton = new edu.wustl.lookingglass.lgpedia.LgpediaOperation( method, false ).createButton();
					helpButton.tightenUpMargin( new java.awt.Insets( -2, -4, -4, 20 ) );
					helpButton.getAwtComponent().addMouseListener( new java.awt.event.MouseAdapter() {
						@Override
						public void mouseEntered( java.awt.event.MouseEvent e ) {
							super.mouseEntered( e );
							for( java.awt.event.MouseListener listener : dragComponent.getAwtComponent().getMouseListeners() ) {
								listener.mouseEntered( new java.awt.event.MouseEvent( dragComponent.getAwtComponent(), e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), false, e.getButton() ) );
							}
						}

						@Override
						public void mouseExited( java.awt.event.MouseEvent e ) {
							for( java.awt.event.MouseListener listener : dragComponent.getAwtComponent().getMouseListeners() ) {
								listener.mouseExited( new java.awt.event.MouseEvent( dragComponent.getAwtComponent(), e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), false, e.getButton() ) );
							}
						}
					} );

					component = new org.lgna.croquet.views.LineAxisPanel( helpButton, dragComponent );
				} else {
					//					org.lgna.croquet.views.Button helpButton = new edu.wustl.lookingglass.lgpedia.LgpediaOperation( method, false ).createButton();
					//					component = new org.lgna.croquet.views.LineAxisPanel( helpButton,
					//							org.lgna.croquet.views.BoxUtilities.createHorizontalSliver( 5 ),
					//							dragComponent,
					//							org.lgna.croquet.views.BoxUtilities.createHorizontalGlue() );
					component = dragComponent;
				}
			}
			this.addComponent( component );

			// <lg/> kids had a hard time scanning... see if this helps.
			if( method.isFunction() ) {
				// pass; there is plenty of space for function tiles
			} else {
				this.addComponent( org.lgna.croquet.views.BoxUtilities.createVerticalStrut( 2 ) );
			}
		}

		// <lg/> Remove the last strut, it was added by mistake
		if( this.getComponentCount() > 0 ) {
			org.lgna.croquet.views.AwtComponentView<?> lastComponent = this.getComponent( this.getComponentCount() - 1 );
			if( lastComponent.getAwtComponent() instanceof javax.swing.Box.Filler ) {
				this.removeComponent( lastComponent );
			}
		}
	}
}
