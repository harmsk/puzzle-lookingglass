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
public class ControlFlowTabView extends org.lgna.croquet.views.MigPanel {
	private static final boolean IS_MINI_DESIRED = true;
	private static final int GAP_TOP = 4;

	private final TitlePanel titlePanel;

	private org.lgna.croquet.views.AbstractLabel returnHeader;
	private org.lgna.croquet.views.DragComponent<?> returnDragComponent;

	public ControlFlowTabView( org.alice.ide.member.ControlFlowTabComposite composite, String titleText ) {
		super( composite, "insets 0, fill", "[]", "[]" );

		this.titlePanel = new TitlePanel();
		this.titlePanel.setTitle( titleText );
		this.addComponent( this.titlePanel, "grow, wrap" );

		this.addHeader( composite.getDoInOrderHeader() );
		this.addDragComponent( org.alice.ide.ast.draganddrop.statement.DoInOrderTemplateDragModel.getInstance() );
		this.addHeader( composite.getDoTogetherHeader() );
		this.addDragComponent( org.alice.ide.ast.draganddrop.statement.DoTogetherTemplateDragModel.getInstance() );
		this.addHeader( composite.getLoopHeader() );
		this.addDragComponent( org.alice.ide.ast.draganddrop.statement.CountLoopTemplateDragModel.getInstance() );
		this.addDragComponent( org.alice.ide.ast.draganddrop.statement.WhileLoopTemplateDragModel.getInstance() );
		this.addHeader( composite.getIfThenHeader() );
		this.addDragComponent( org.alice.ide.ast.draganddrop.statement.ConditionalStatementTemplateDragModel.getInstance() );
		this.addHeader( composite.getEachInTogetherHeader() );
		this.addDragComponent( org.alice.ide.ast.draganddrop.statement.ForEachInArrayLoopTemplateDragModel.getInstance() );
		this.addDragComponent( org.alice.ide.ast.draganddrop.statement.EachInArrayTogetherTemplateDragModel.getInstance() );
		this.addHeader( composite.getCommentHeader() );
		this.addDragComponent( org.alice.ide.ast.draganddrop.statement.CommentTemplateDragModel.getInstance() );
		this.addHeader( composite.getLocalHeader() );
		this.addDragComponent( org.alice.ide.ast.draganddrop.statement.DeclareLocalDragModel.getInstance() );
		this.addDragComponent( org.alice.ide.ast.draganddrop.statement.AssignmentTemplateDragModel.getInstance() );
		this.returnHeader = this.createHeader( composite.getReturnHeader() );
		this.returnDragComponent = this.createDragComponent( org.alice.ide.ast.draganddrop.statement.ReturnStatementTemplateDragModel.getInstance( null ) );

		this.setBackgroundColor( edu.wustl.lookingglass.ide.LookingGlassIDE.getActiveInstance().getTheme().getControlFlowColor() );
		this.setBorder( javax.swing.BorderFactory.createMatteBorder( 0, 0, 10, 0, getBackgroundColor() ) );
	}

	@Override
	public java.awt.Color getBackgroundColor() {
		return edu.wustl.lookingglass.ide.LookingGlassIDE.getActiveInstance().getTheme().getControlFlowColor();
	}

	private org.lgna.croquet.views.AbstractLabel createHeader( org.lgna.croquet.PlainStringValue stringValue ) {
		return stringValue.createLabel( edu.cmu.cs.dennisc.java.awt.font.TextPosture.OBLIQUE );
	}

	private void addHeader( org.lgna.croquet.views.AbstractLabel label ) {
		StringBuilder sb = new StringBuilder();
		sb.append( "wrap" );
		if( this.getComponentCount() > 0 ) {
			sb.append( ", gaptop " );
			sb.append( GAP_TOP );
		}
		sb.append( ", gapleft 4" );
		this.addComponent( label, sb.toString() );
	}

	private void addHeader( org.lgna.croquet.PlainStringValue stringValue ) {
		this.addHeader( this.createHeader( stringValue ) );
	}

	private org.lgna.croquet.views.DragComponent<?> createDragComponent( org.alice.ide.ast.draganddrop.statement.StatementTemplateDragModel dragModel ) {
		org.lgna.croquet.views.DragComponent<?> dragComponent;
		if( IS_MINI_DESIRED ) {
			dragComponent = new org.alice.ide.controlflow.components.MiniControlFlowStatementTemplate( dragModel );
		} else {
			dragComponent = new org.alice.ide.controlflow.components.CompleteControlFlowStatementTemplate( dragModel );
		}
		return dragComponent;
	}

	private void addDragComponent( org.lgna.croquet.views.DragComponent<?> dragComponent ) {
		org.lgna.croquet.views.SwingComponentView<?> component;

		//		if( dragComponent.getModel() instanceof org.alice.ide.ast.draganddrop.statement.StatementTemplateDragModel ) {
		//			org.lgna.project.ast.Statement statement = ( (org.alice.ide.ast.draganddrop.statement.StatementTemplateDragModel)dragComponent.getModel() ).getPossiblyIncompleteStatement();
		//
		//			org.lgna.croquet.views.Button helpButton = new edu.wustl.lookingglass.lgpedia.LgpediaOperation( statement, false ).createButton();
		//			component = new org.lgna.croquet.views.LineAxisPanel( helpButton,
		//					org.lgna.croquet.views.BoxUtilities.createHorizontalSliver( 5 ),
		//					dragComponent,
		//					org.lgna.croquet.views.BoxUtilities.createHorizontalGlue() );
		//		} else {
		//			component = dragComponent;
		//		}
		component = dragComponent;

		this.addComponent( dragComponent, "wrap, gapleft 16, hmin 25" );
	}

	private void addDragComponent( org.alice.ide.ast.draganddrop.statement.StatementTemplateDragModel dragModel ) {
		this.addDragComponent( this.createDragComponent( dragModel ) );
	}

	private final org.lgna.croquet.event.ValueListener<org.alice.ide.declarationseditor.DeclarationComposite<?, ?>> declarationTabListener = new org.lgna.croquet.event.ValueListener<org.alice.ide.declarationseditor.DeclarationComposite<?, ?>>() {
		@Override
		public void valueChanged( org.lgna.croquet.event.ValueEvent<org.alice.ide.declarationseditor.DeclarationComposite<?, ?>> e ) {
			updateReturn( e.getNextValue() );
		}
	};

	private void updateReturn( org.alice.ide.declarationseditor.DeclarationComposite declarationComposite ) {
		final boolean isReturnDesired;
		if( declarationComposite instanceof org.alice.ide.declarationseditor.CodeComposite ) {
			org.alice.ide.declarationseditor.CodeComposite codeComposite = (org.alice.ide.declarationseditor.CodeComposite)declarationComposite;
			org.lgna.project.ast.AbstractCode code = codeComposite.getDeclaration();
			if( code instanceof org.lgna.project.ast.AbstractMethod ) {
				org.lgna.project.ast.AbstractMethod method = (org.lgna.project.ast.AbstractMethod)code;
				isReturnDesired = method.isFunction();
			} else {
				isReturnDesired = false;
			}
		} else {
			isReturnDesired = false;
		}
		boolean isReturnShowing = ( this.returnHeader != null ) && this.returnHeader.isShowing();
		if( isReturnDesired != isReturnShowing ) {
			javax.swing.SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					synchronized( getTreeLock() ) {
						if( isReturnDesired ) {
							addHeader( returnHeader );
							addDragComponent( returnDragComponent );
						} else {
							removeComponent( returnDragComponent );
							removeComponent( returnHeader );
						}
					}
					revalidateAndRepaint();
				}
			} );
		}
	}

	@Override
	public void handleCompositePreActivation() {
		super.handleCompositePreActivation();
		org.alice.ide.IDE.getActiveInstance().getDocumentFrame().getDeclarationsEditorComposite().getTabState().addAndInvokeNewSchoolValueListener( this.declarationTabListener );
	}

	@Override
	public void handleCompositePostDeactivation() {
		org.alice.ide.IDE.getActiveInstance().getDocumentFrame().getDeclarationsEditorComposite().getTabState().removeNewSchoolValueListener( this.declarationTabListener );
		super.handleCompositePostDeactivation();
	}

	private class TitlePanel extends org.lgna.croquet.views.MigPanel {
		private final org.lgna.croquet.views.Label title;

		public TitlePanel() {
			super( null, "fill, insets 10 10 10 0" );
			this.title = new org.lgna.croquet.views.Label( "", 1.6f, edu.cmu.cs.dennisc.java.awt.font.TextWeight.BOLD );
			this.title.setHorizontalTextPosition( org.lgna.croquet.views.HorizontalTextPosition.TRAILING );
			this.title.setForegroundColor( edu.cmu.cs.dennisc.java.awt.ColorUtilities.shiftHSB( ControlFlowTabView.this.getBackgroundColor(), 0, 0, -0.75f ) );
			addComponent( this.title );
		}

		public void setTitle( String text ) {
			this.title.setText( text );
		}

		@Override
		protected javax.swing.JPanel createJPanel() {
			return new javax.swing.JPanel() {
				@Override
				protected void paintComponent( java.awt.Graphics g ) {
					super.paintComponent( g );

					java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
					g2.setPaint( new java.awt.Color( 0xC0DAFF ) );
					g2.fillRect( 0, 0, getWidth(), getHeight() );
				}
			};
		}
	}
}
