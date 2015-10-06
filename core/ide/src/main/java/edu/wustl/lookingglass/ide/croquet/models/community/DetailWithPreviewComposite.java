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
package edu.wustl.lookingglass.ide.croquet.models.community;

import org.alice.ide.uricontent.UriProjectLoader;

import edu.wustl.lookingglass.ide.croquet.models.community.views.PreviewWithTitleDescriptionPanel;
import edu.wustl.lookingglass.ide.croquet.models.preview.PreviewProjectComposite;

public final class DetailWithPreviewComposite extends org.lgna.croquet.SimpleComposite<PreviewWithTitleDescriptionPanel> {

	private final PreviewProjectComposite previewProjectComposite;

	public DetailWithPreviewComposite( PreviewProjectComposite previewProjectComposite ) {
		super( java.util.UUID.fromString( "f3e0ae4e-4a9e-4524-919a-f58b5afd51b7" ) );
		this.previewProjectComposite = previewProjectComposite;
		this.registerSubComposite( this.previewProjectComposite );
	}

	public void update( UriProjectLoader uriProjectLoader ) {
		PreviewWithTitleDescriptionPanel previewWorldPanel = this.getView();
		previewWorldPanel.setSelectedUriContentLoader( uriProjectLoader );

		// the preview composite needs to start the download because it's giving the user feedback
		DetailWithPreviewComposite.this.previewProjectComposite.loadProject( uriProjectLoader );
	}

	@Override
	protected PreviewWithTitleDescriptionPanel createView() {
		return new PreviewWithTitleDescriptionPanel( this.previewProjectComposite.getView() );
	}
}
