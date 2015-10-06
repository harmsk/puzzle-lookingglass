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
package edu.wustl.lookingglass.ide.perspectives.dinah.finder.rightnowtree;

import edu.wustl.lookingglass.ide.perspectives.dinah.DinahProgramManager;
import edu.wustl.lookingglass.ide.perspectives.dinah.finder.operations.ShowStatementLocationOperation;
import edu.wustl.lookingglass.virtualmachine.eventtracing.ExpressionStatementEventNode;

public class ExecutionTraceTree extends javax.swing.JTree {
	private final DinahProgramManager programManager;

	public ExecutionTraceTree( ExecutionTraceTreeModel model, DinahProgramManager programManager ) {
		this.setModel( model );
		this.setCellRenderer( new ExecutionTraceTreeRenderer() );
		this.setRootVisible( false );
		this.setVisibleRowCount( 8 );
		this.setRowHeight( 0 );
		this.setUI( new javax.swing.plaf.basic.BasicTreeUI() );

		this.programManager = programManager;

		ShowStatementMenuTreeMouseListener listener = new ShowStatementMenuTreeMouseListener();
		this.addMouseListener( listener );
		this.addMouseMotionListener( listener );
	}

	@Override
	public ExecutionTraceTreeModel getModel() {
		return (ExecutionTraceTreeModel)super.getModel();
	}

	public void addEventNode( ExpressionStatementEventNode eventNode ) {
		if( ( eventNode.getCallerField() != null ) && ( eventNode.isUserMethod() == false ) ) {
			getModel().addObject( eventNode, this );
		}
	}

	public void removeEventNode( ExpressionStatementEventNode eventNode ) {
		if( ( eventNode.getCallerField() != null ) && ( eventNode.isUserMethod() == false ) ) {
			getModel().removeObject( eventNode );
		}
	}

	private class ShowStatementMenuTreeMouseListener extends java.awt.event.MouseAdapter {

		private StatementTreeNode getNodeForEvent( java.awt.event.MouseEvent e ) {
			ExecutionTraceTree tree = (ExecutionTraceTree)e.getSource();
			javax.swing.tree.TreePath path = tree.getPathForLocation( e.getX(), e.getY() );
			if( path != null ) {
				tree.setSelectionPath( path );
				javax.swing.tree.DefaultMutableTreeNode treeNode = (javax.swing.tree.DefaultMutableTreeNode)path.getLastPathComponent();
				if( treeNode instanceof StatementTreeNode ) {
					return (StatementTreeNode)treeNode;
				}
			}
			return null;
		}

		@Override
		public void mouseReleased( java.awt.event.MouseEvent e ) {
			StatementTreeNode treeNode = getNodeForEvent( e );
			if( treeNode != null ) {
				ExpressionStatementEventNode eventNode = treeNode.getEventNode();
				// fire the location operation on click
				ShowStatementLocationOperation showStatementLocationOperation = new ShowStatementLocationOperation( eventNode, programManager );
				showStatementLocationOperation.fire();
			}
		}

		@Override
		public void mouseExited( java.awt.event.MouseEvent e ) {
			ExecutionTraceTree tree = (ExecutionTraceTree)e.getSource();
			tree.clearSelection();
		}

		@Override
		public void mouseMoved( java.awt.event.MouseEvent e ) {
			ExecutionTraceTree tree = (ExecutionTraceTree)e.getSource();
			int row = tree.getRowForLocation( e.getX(), e.getY() );
			tree.setSelectionRow( row );

			if( ( tree.getSelectionPath() != null ) && ( tree.getSelectionPath().getLastPathComponent() instanceof StatementTreeNode ) ) {
				tree.setCursor( java.awt.Cursor.getPredefinedCursor( java.awt.Cursor.HAND_CURSOR ) );
			} else {
				tree.setCursor( java.awt.Cursor.getDefaultCursor() );
			}
		}
	}
}
