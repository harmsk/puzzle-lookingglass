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
package edu.wustl.lookingglass.ide.puzzle.editor;

import java.util.UUID;

import javax.swing.SwingUtilities;

import org.alice.ide.project.ProjectChangeOfInterestManager;
import org.lgna.croquet.Application;
import org.lgna.croquet.BooleanState;
import org.lgna.croquet.event.ValueEvent;
import org.lgna.project.ast.Statement;

import edu.wustl.lookingglass.ide.LookingGlassIDE;
import edu.wustl.lookingglass.puzzle.PuzzleProjectProperties;

/**
 * @author Kyle J. Harms
 */
public class IsStaticPuzzleStatementState extends BooleanState {

	private final Statement statement;
	private final PuzzleStatementStates states;

	public IsStaticPuzzleStatementState( Statement statement, PuzzleStatementStates states ) {
		super( Application.PROJECT_GROUP, UUID.fromString( "7bafb443-1e61-4127-b94d-769e19792737" ), LookingGlassIDE.getActiveInstance().getPuzzleProjectProperties().containsStaticStatement( statement ) );
		this.statement = statement;
		this.states = states;
		this.states.setIsStaticPuzzleStatementState( this );

		this.addNewSchoolValueListener( new org.lgna.croquet.event.ValueListener<Boolean>() {
			@Override
			public void valueChanged( ValueEvent<Boolean> e ) {
				PuzzleProjectProperties properties = LookingGlassIDE.getActiveInstance().getPuzzleProjectProperties();
				if( e.getNextValue() ) {
					properties.addStaticStatementId( IsStaticPuzzleStatementState.this.statement.getId() );
				} else {
					properties.removeStaticStatementId( IsStaticPuzzleStatementState.this.statement.getId() );
				}

				ProjectChangeOfInterestManager.SINGLETON.fireProjectChangeOfInterestListeners();
				IsStaticPuzzleStatementState.this.states.update();

				SwingUtilities.invokeLater( () -> {
					LookingGlassIDE.getActiveInstance().getDocumentFrame().getDeclarationsEditorComposite().getView().repaint();
				} );
			}
		} );
	}
}
