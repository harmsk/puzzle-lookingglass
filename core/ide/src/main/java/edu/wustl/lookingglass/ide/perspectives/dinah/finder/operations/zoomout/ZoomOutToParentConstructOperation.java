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
package edu.wustl.lookingglass.ide.perspectives.dinah.finder.operations.zoomout;

import edu.wustl.lookingglass.ide.perspectives.dinah.DinahProgramManager;
import edu.wustl.lookingglass.virtualmachine.eventtracing.AbstractEventNode;
import edu.wustl.lookingglass.virtualmachine.eventtracing.ExpressionStatementEventNode;

public class ZoomOutToParentConstructOperation extends ZoomOutToOperation {

	public ZoomOutToParentConstructOperation( AbstractEventNode<?> eventNode, DinahProgramManager programManager ) {
		super( java.util.UUID.fromString( "d3d6cf10-2aad-45a4-ba0f-9d86e1f5331f" ), getParentEventNode( eventNode, programManager ), programManager );
	}

	@Override
	protected java.lang.String getLocalizedName() {
		String rv;

		if( getEventNode() != null ) {
			rv = findLocalizedText( getEventNode().getAstNode().getClass().getSimpleName() );

			if( rv == null ) {
				rv = findLocalizedText( "unknown" );
			}
		} else {
			rv = findLocalizedText( "noInstance" );
		}

		return rv;
	}

	private static AbstractEventNode<?> getParentEventNode( AbstractEventNode<?> eventNode, DinahProgramManager programManager ) {
		AbstractEventNode<?> rv = null;
		if( eventNode != null ) {
			rv = eventNode.getParent();

			if( programManager.getProgramImp().getExecutionObserver().isRootEventNode( rv ) ) {
				return null;
			} else if( ( rv != null ) && ( rv.getParent() instanceof ExpressionStatementEventNode ) ) {
				return null;
			}
		}
		return rv;
	}
}
