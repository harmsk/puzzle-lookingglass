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

import org.lgna.croquet.Operation;
import org.lgna.croquet.OwnedByCompositeOperation;

import edu.cmu.cs.dennisc.codec.BinaryDecoder;
import edu.cmu.cs.dennisc.codec.BinaryEncoder;
import edu.wustl.lookingglass.ide.LookingGlassIDE;
import edu.wustl.lookingglass.ide.croquet.models.community.ShowRemixesOperation;

/**
 * @author Dennis Cosgrove
 */
public final class CodeToolBarComposite extends org.alice.ide.toolbar.croquet.IdeToolBar {
	public CodeToolBarComposite( org.alice.ide.ProjectDocumentFrame projectDocumentFrame ) {
		super( java.util.UUID.fromString( "633d89d9-9ddf-470b-a56b-e0169f3ba1d4" ) );

		java.util.List<org.lgna.croquet.Element> list = edu.cmu.cs.dennisc.java.util.Lists.newLinkedList();

		// <lg> changed order elements are added to toobar
		org.alice.stageide.perspectives.ToolBarUtilities.appendDocumentSubElements( projectDocumentFrame, list );
		org.alice.stageide.perspectives.ToolBarUtilities.appendUndoRedoSubElements( projectDocumentFrame, list );
		org.alice.stageide.perspectives.ToolBarUtilities.appendRunSubElements( projectDocumentFrame, list );

		// Play And Explore
		playAndExploreOperation.setButtonIcon( edu.wustl.lookingglass.ide.LookingGlassTheme.getIcon( "play-and-explore", org.lgna.croquet.icon.IconSize.SMALL ) );
		list.add( playAndExploreOperation );
		list.add( org.lgna.croquet.GapToolBarSeparator.getInstance() );

		// Community Controls
		ShowRemixesOperation remixOperation = edu.wustl.lookingglass.ide.croquet.models.community.ShowRemixesOperation.getInstance();
		remixOperation.setButtonIcon( edu.wustl.lookingglass.ide.LookingGlassTheme.getIcon( "remix", org.lgna.croquet.icon.IconSize.SMALL ) );
		list.add( remixOperation );
		list.add( org.lgna.croquet.PushToolBarSeparator.getInstance() );
		list.add( this.createStringValue( "share_label" ) );

		Operation shareWorldOperation = edu.wustl.lookingglass.remix.share.ShareWorldComposite.getInstance().getLaunchOperation();
		shareWorldOperation.setButtonIcon( edu.wustl.lookingglass.ide.LookingGlassTheme.getIcon( "share-aqua", org.lgna.croquet.icon.IconSize.SMALL ) );
		list.add( shareWorldOperation );

		shareRemixOperation.setButtonIcon( edu.wustl.lookingglass.ide.LookingGlassTheme.getIcon( "share-yellow", org.lgna.croquet.icon.IconSize.SMALL ) );
		list.add( shareRemixOperation );

		Operation shareTemplateOperation = edu.wustl.lookingglass.remix.share.ShareTemplateComposite.getInstance().getLaunchOperation();
		shareTemplateOperation.setButtonIcon( edu.wustl.lookingglass.ide.LookingGlassTheme.getIcon( "share-purple", org.lgna.croquet.icon.IconSize.SMALL ) );
		list.add( shareTemplateOperation );

		list.add( org.lgna.croquet.GapToolBarSeparator.getInstance() );
		// </lg>
		list.add( org.alice.ide.clipboard.Clipboard.SINGLETON.getDragModel() );
		this.subElements = java.util.Collections.unmodifiableList( list );
	}

	public Operation getPlayAndExploreOperation() {
		return this.playAndExploreOperation;
	}

	public org.lgna.croquet.Operation getShareRemixOperation() {
		return this.shareRemixOperation;
	}

	@Override
	public Iterable<? extends org.lgna.croquet.Element> getSubElements() {
		return this.subElements;
	}

	public enum OwnedByCompositeOperationCodec implements org.lgna.croquet.ItemCodec<OwnedByCompositeOperation> {
		SINGLETON;
		@Override
		public Class<OwnedByCompositeOperation> getValueClass() {
			return OwnedByCompositeOperation.class;
		}

		@Override
		public OwnedByCompositeOperation decodeValue( BinaryDecoder binaryDecoder ) {
			return null;
		}

		@Override
		public void encodeValue( BinaryEncoder binaryEncoder, OwnedByCompositeOperation value ) {
		}

		@Override
		public void appendRepresentation( StringBuilder sb, OwnedByCompositeOperation value ) {
		}

	}

	private final Operation playAndExploreOperation = LookingGlassIDE.getActiveInstance().getSetToPlayAndExplorePerspectiveOperation();
	private final org.lgna.croquet.Operation shareRemixOperation = edu.wustl.lookingglass.ide.LookingGlassIDE.getActiveInstance().getSetToLocalRemixPerspectiveOperation();
	private final java.util.List<? extends org.lgna.croquet.Element> subElements;
}
