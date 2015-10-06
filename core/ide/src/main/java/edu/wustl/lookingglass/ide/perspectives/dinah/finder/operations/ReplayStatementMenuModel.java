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
package edu.wustl.lookingglass.ide.perspectives.dinah.finder.operations;

import javax.swing.Icon;

import org.lgna.croquet.StandardMenuItemPrepModel;
import org.lgna.project.ast.Comment;
import org.lgna.project.ast.LocalDeclarationStatement;
import org.lgna.project.ast.ReturnStatement;
import org.lgna.project.ast.Statement;

import edu.wustl.lookingglass.ide.croquet.models.menu.AbstractStatementMenuModel;
import edu.wustl.lookingglass.ide.perspectives.dinah.DinahProgramManager;
import edu.wustl.lookingglass.virtualmachine.eventtracing.AbstractEventNode;
import edu.wustl.lookingglass.virtualmachine.eventtracing.AbstractLoopEventNode;
import edu.wustl.lookingglass.virtualmachine.eventtracing.ConditionalStatementEventNode;
import edu.wustl.lookingglass.virtualmachine.eventtracing.ContainerEventNode;
import edu.wustl.lookingglass.virtualmachine.eventtracing.EachInArrayTogetherEventNode;
import edu.wustl.lookingglass.virtualmachine.eventtracing.ExpressionStatementEventNode;

public class ReplayStatementMenuModel extends AbstractStatementMenuModel {

	public ReplayStatementMenuModel( org.lgna.project.ast.Statement statement, DinahProgramManager programManager ) {
		super( java.util.UUID.fromString( "21bb2b65-defe-4ba5-94e7-ec6e95f2fadb" ), statement, programManager );
	}

	@Override
	protected boolean isEventNodeAcceptable( AbstractEventNode<?> eventNode ) {
		return ( eventNode instanceof ConditionalStatementEventNode ) || ( eventNode instanceof ContainerEventNode ) || ( eventNode instanceof ExpressionStatementEventNode );
	}

	@Override
	protected String getStatusKey( Statement statement ) {
		if( getStatement() instanceof Comment ) {
			return "comment";
		}
		else if( ( getStatement() instanceof LocalDeclarationStatement ) || ( getStatement() instanceof ReturnStatement ) ) {
			return "invalidStatement";
		}
		return "notRun";
	}

	@Override
	protected java.lang.String findDefaultLocalizedText() {
		String rv = null;
		if( getStatement() != null ) {
			rv = findLocalizedText( getStatement().getClass().getSimpleName() );
		}

		if( rv == null ) {
			rv = super.findDefaultLocalizedText();
		}

		return rv;
	}

	@Override
	protected StandardMenuItemPrepModel getModelForEventNode( AbstractEventNode<?> eventNode ) {
		if( ( eventNode instanceof AbstractLoopEventNode ) || ( eventNode instanceof EachInArrayTogetherEventNode ) ) {
			return new ReplayContainerStatementOperation( eventNode, getProgramManager() ).getMenuItemPrepModel();
		} else {
			return new ReplayStatementOperation( eventNode, getProgramManager() ).getMenuItemPrepModel();
		}
	}

	@Override
	protected Icon getMenuIcon() {
		return edu.wustl.lookingglass.ide.LookingGlassTheme.getIcon( "world-play", org.lgna.croquet.icon.IconSize.EXTRA_SMALL );
	}
}
