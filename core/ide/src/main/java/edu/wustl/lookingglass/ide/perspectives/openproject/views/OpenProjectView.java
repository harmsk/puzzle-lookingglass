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
package edu.wustl.lookingglass.ide.perspectives.openproject.views;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.lgna.croquet.views.MigPanel;

import edu.wustl.lookingglass.ide.perspectives.openproject.OpenProjectComposite;

/**
 * @author Michael Pogran
 */
public class OpenProjectView extends MigPanel {

	private final SidePanelTabbedPane sidePanelTabbedPane;

	private final OuterPanel panel;

	public OpenProjectView( OpenProjectComposite openProjectComposite ) {
		super( openProjectComposite, "fill", "", "" );
		this.panel = new OuterPanel();
		this.addComponent( panel, "center, grow" );
		this.setBackgroundColor( new Color( 121, 116, 145 ) );
		this.sidePanelTabbedPane = new SidePanelTabbedPane( openProjectComposite.getTabState(), openProjectComposite.getReturnToPreviousProjectOperation() );

		panel.addComponent( this.sidePanelTabbedPane, "cell 0 0, wmin 664" );
		this.addDetailsPane();
	}

	public SidePanelTabbedPane getSidePanelTabbedPane() {
		return this.sidePanelTabbedPane;
	}

	public void addDetailsPane() {
		synchronized( this.getTreeLock() ) {
			panel.addComponent( this.getComposite().getProjectDetailsComposite().getView(), "cell 1 0, wmin 450, wmax 450, alignx r" );
			this.revalidateAndRepaint();
		}
	}

	public void removeDetailsPane() {
		synchronized( this.getTreeLock() ) {
			panel.removeComponent( this.getComposite().getProjectDetailsComposite().getView() );
			this.revalidateAndRepaint();
		}
	}

	@Override
	public OpenProjectComposite getComposite() {
		return (OpenProjectComposite)super.getComposite();
	}

	private class OuterPanel extends MigPanel {

		public OuterPanel() {
			super( null, "fill", "[fill, grow]5[fill]", "[fill]" );
		}

		@Override
		protected javax.swing.JPanel createJPanel() {
			javax.swing.JPanel rv = new DefaultJPanel() {

				@Override
				public void paint( java.awt.Graphics g ) {
					int w = this.getWidth();
					int h = this.getHeight();
					Graphics2D g2 = (Graphics2D)g.create();
					g2.setClip( -2, -2, w + 4, h + 4 );
					g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

					g2.setColor( new Color( 216, 219, 238 ) );
					g2.fillRoundRect( -1, -1, w, h, 10, 10 );

					g2.setColor( new Color( 56, 61, 81 ) );
					g2.fillRoundRect( 0, 0, w + 1, h + 1, 10, 10 );

					g2.setColor( new Color( 151, 160, 217 ) );
					g2.fillRoundRect( 0, 0, w, h, 10, 10 );

					super.paint( g );
				}
			};
			return rv;
		}
	}
}
