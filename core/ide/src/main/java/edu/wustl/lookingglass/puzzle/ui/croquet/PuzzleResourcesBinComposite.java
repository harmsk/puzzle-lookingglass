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
package edu.wustl.lookingglass.puzzle.ui.croquet;

import edu.wustl.lookingglass.puzzle.ui.croquet.views.PuzzleResourcesBinView;

public class PuzzleResourcesBinComposite extends org.lgna.croquet.SimpleComposite<PuzzleResourcesBinView> {

	private final edu.wustl.lookingglass.puzzle.CompletionPuzzle puzzle;

	public PuzzleResourcesBinComposite( edu.wustl.lookingglass.puzzle.CompletionPuzzle puzzle ) {
		super( java.util.UUID.fromString( "0ec37f26-e1a7-4a5f-95d6-642a070966e0" ) );
		this.puzzle = puzzle;
	}

	public edu.wustl.lookingglass.puzzle.CompletionPuzzle getPuzzle() {
		return this.puzzle;
	}

	@Override
	protected org.lgna.croquet.views.ScrollPane createScrollPaneIfDesired() {
		return new org.lgna.croquet.views.ScrollPane();
	}

	@Override
	protected PuzzleResourcesBinView createView() {
		return new PuzzleResourcesBinView( this );
	}

	public org.lgna.croquet.DropReceptor getDropReceptor() {
		return this.dropReceptor;
	}

	private final org.lgna.croquet.DropReceptor dropReceptor = new org.lgna.croquet.DropReceptor() {
		@Override
		public org.lgna.croquet.views.TrackableShape getTrackableShape( org.lgna.croquet.DropSite potentialDropSite ) {
			// TODO:
			return getRootComponent();
		}

		@Override
		public boolean isPotentiallyAcceptingOf( org.lgna.croquet.DragModel dragModel ) {
			return true;
		}

		@Override
		public org.lgna.croquet.views.SwingComponentView<?> getViewController() {
			return getRootComponent();
		}

		@Override
		public void dragStarted( org.lgna.croquet.history.DragStep step ) {
		}

		@Override
		public void dragEntered( org.lgna.croquet.history.DragStep step ) {
		}

		@Override
		public org.lgna.croquet.DropSite dragUpdated( org.lgna.croquet.history.DragStep step ) {
			org.lgna.croquet.triggers.DragTrigger dragTrigger = (org.lgna.croquet.triggers.DragTrigger)step.getTrigger();
			java.awt.event.MouseEvent pressedMouseEvent = dragTrigger.getEvent();
			java.awt.event.MouseEvent srcMouseEvent = step.getLatestMouseEvent();
			java.awt.event.MouseEvent dstMouseEvent = javax.swing.SwingUtilities.convertMouseEvent( srcMouseEvent.getComponent(), srcMouseEvent, getView().getStatementsBinView().getAwtComponent() );
			return new PuzzleResourceBinDropSite( this, dstMouseEvent.getX() - pressedMouseEvent.getX(), dstMouseEvent.getY() - pressedMouseEvent.getY() );
		}

		@Override
		public org.lgna.croquet.Model dragDropped( org.lgna.croquet.history.DragStep step ) {
			org.lgna.croquet.Model rv;

			PuzzleResourceBinDropSite dropSite = (PuzzleResourceBinDropSite)step.getCurrentPotentialDropSite();

			org.lgna.croquet.views.DragComponent<?> dragComponent = step.getDragSource();
			org.lgna.croquet.DragModel dragModel = dragComponent.getModel();
			if( dragModel instanceof org.alice.ide.ast.draganddrop.statement.StatementDragModel ) {
				org.alice.ide.ast.draganddrop.statement.StatementDragModel statementDragModel = (org.alice.ide.ast.draganddrop.statement.StatementDragModel)dragModel;
				org.lgna.project.ast.Statement statement = statementDragModel.getStatement();
				org.lgna.project.ast.StatementListProperty unusedStatementsListProperty = puzzle.getBinStatementsListProperty();
				int prevIndex = unusedStatementsListProperty.indexOf( statement );
				if( prevIndex != -1 ) {
					rv = new SetLocationOfUnusedStatementOperation( statement, dragComponent.getLocation(), new java.awt.Point( dropSite.getX(), dropSite.getY() ), prevIndex, unusedStatementsListProperty.size() - 1, unusedStatementsListProperty, getView().getStatementsBinView() );
				} else {
					rv = new ReturnStatementToUnusedBinOperation( statement, unusedStatementsListProperty, getView().getStatementsBinView(), dropSite );
				}
			} else {
				rv = null;
			}
			return rv;
		}

		@Override
		public void dragExited( org.lgna.croquet.history.DragStep step, boolean isDropRecipient ) {
		}

		@Override
		public void dragStopped( org.lgna.croquet.history.DragStep step ) {
		}

		@Override
		public void addDropRejector( org.lgna.croquet.DropRejector dropRejector ) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void removeDropRejector( org.lgna.croquet.DropRejector dropRejector ) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clearDropRejectors() {
		}

		@Override
		public java.util.List<org.lgna.croquet.DropRejector> getDropRejectors() {
			return java.util.Collections.emptyList();
		}
	};
}
