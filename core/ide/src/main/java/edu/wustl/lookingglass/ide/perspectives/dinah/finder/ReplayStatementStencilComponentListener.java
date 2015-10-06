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
package edu.wustl.lookingglass.ide.perspectives.dinah.finder;

import org.alice.ide.highlight.IdeHighlightStencil;

import edu.wustl.lookingglass.ide.perspectives.dinah.DinahProgramManager;
import edu.wustl.lookingglass.remix.models.EndCaptureState;
import edu.wustl.lookingglass.remix.models.StartCaptureState;
import edu.wustl.lookingglass.virtualmachine.eventtracing.AbstractEventNode;
import edu.wustl.lookingglass.virtualmachine.eventtracing.EventNodeUtilities;

public class ReplayStatementStencilComponentListener extends AbstractReplayStencilComponentListener {

	AbstractEventNode<?> nodeToReplay;

	public ReplayStatementStencilComponentListener( AbstractEventNode<?> eventNode, IdeHighlightStencil stencil, DinahProgramManager programManager ) {
		super( stencil, programManager );
		this.nodeToReplay = eventNode;
	}

	@Override
	protected void startReplayThread() {
		if( this.nodeToReplay == null ) {
			// this is previewing a capture
			AbstractEventNode<?> sharedParent = EventNodeUtilities.getSharedParentNode(
					edu.wustl.lookingglass.remix.models.StartCaptureState.getInstance().getValue(),
					edu.wustl.lookingglass.remix.models.EndCaptureState.getInstance().getValue()
					);
			this.programManager.getProgramImp().continuouslyReplayDependentThreadTimePeriod( StartCaptureState.getInstance().getValue(), EndCaptureState.getInstance().getValue(), sharedParent );
		} else {
			// this is replaying a single eventnode
			this.programManager.getProgramImp().continuouslyReplayEvent( this.nodeToReplay, true );
		}
	}

	@Override
	protected void stopReplayThread() {
		// this is more of this same hack.
		if( this.nodeToReplay != null ) {
			this.programManager.getProgramImp().stopReplayingEventNode( this.nodeToReplay );
		} else {
			this.programManager.getProgramImp().stopReplay();
		}
	}
}
