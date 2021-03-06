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

package org.alice.stageide.perspectives.code;

import org.alice.stageide.run.RunComposite;

/**
 * @author Dennis Cosgrove
 */
public class CodePerspectiveComposite extends org.lgna.croquet.LazyImmutableSplitComposite<CodeContextSplitComposite, org.lgna.croquet.Composite<?>> {
	public CodePerspectiveComposite( org.alice.ide.ProjectDocumentFrame projectDocumentFrame ) {
		super( java.util.UUID.fromString( "55b694a1-da0e-4820-b138-6cf285be4ed3" ) );
		this.projectDocumentFrame = projectDocumentFrame;
	}

	@Override
	protected org.alice.stageide.perspectives.code.CodeContextSplitComposite createLeadingComposite() {
		return new CodeContextSplitComposite( this );
	}

	@Override
	protected org.lgna.croquet.Composite<?> createTrailingComposite() {
		return this.projectDocumentFrame.getDeclarationsEditorComposite();
	}

	public void incrementIgnoreDividerLocationChangeCount() {
		this.ignoreDividerChangeCount++;
	}

	public void decrementIgnoreDividerLocationChangeCount() {
		this.ignoreDividerChangeCount--;
	}

	@Override
	protected org.lgna.croquet.views.SplitPane createView() {
		org.lgna.croquet.views.SplitPane rv = this.createHorizontalSplitPane();
		rv.addDividerLocationChangeListener( this.dividerLocationListener );
		// TODO: we really should be to set resize weight and this work so we get better layouts on wide screen monitors. but whatevs.
		rv.setResizeWeight( 0.0 );
		//todo
		rv.setDividerLocation( 400 );
		return rv;
	}

	private int ignoreDividerChangeCount = 0;
	private final org.alice.ide.ProjectDocumentFrame projectDocumentFrame;
	private final java.beans.PropertyChangeListener dividerLocationListener = new java.beans.PropertyChangeListener() {
		@Override
		public void propertyChange( java.beans.PropertyChangeEvent e ) {
			if( ignoreDividerChangeCount > 0 ) {
				// pass
			} else {
				CodeContextSplitComposite otherComposite = getLeadingComposite();
				org.lgna.croquet.views.SplitPane otherSplitPane = otherComposite.getView();
				int prevValue = otherSplitPane.getDividerLocation();
				int nextValue = (int)( (Integer)e.getNewValue() / org.alice.stageide.run.RunComposite.WIDTH_TO_HEIGHT_RATIO );
				if( prevValue != nextValue ) {
					otherComposite.incrementIgnoreDividerLocationChangeCount();
					try {
						otherSplitPane.setDividerLocation( nextValue );
					} finally {
						otherComposite
								.decrementIgnoreDividerLocationChangeCount();
					}
				}
				// edu.cmu.cs.dennisc.java.util.logging.Logger.outln( "outer:",
				// e.getOldValue(), e.getNewValue() );
			}
		}
	};

	@Override
	public void handlePostDeactivation() {
		super.handlePostDeactivation();
		if( RunComposite.getInstance().getCurrentFrame() != null ) {
			RunComposite.getInstance().getCurrentFrame().close();
		}
	};
}
