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
package edu.wustl.lookingglass.lgpedia.components;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * @author Michael Pogran
 */
public class LgpediaIcon implements Icon {
	private int size;
	private String name;

	public LgpediaIcon( int size, String name ) {
		this.size = size;
		this.name = name;
	}

	@Override
	public void paintIcon( Component c, Graphics g, int x, int y ) {

		java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
		g2.setRenderingHint( java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON );

		java.awt.Color lineColor = edu.cmu.cs.dennisc.java.awt.ColorUtilities.createGray( 165 );
		java.awt.Color startColor = edu.cmu.cs.dennisc.java.awt.ColorUtilities.createGray( 220 );
		java.awt.Color endColor = edu.cmu.cs.dennisc.java.awt.ColorUtilities.createGray( 190 );
		java.awt.GradientPaint paint = new java.awt.GradientPaint( x, y, startColor, x, this.size, endColor );

		g2.setPaint( paint );
		g2.fillRoundRect( x - 8, y + 1, c.getWidth() - 10, this.size - 2, 5, 5 );

		g2.setPaint( lineColor );
		g2.drawRoundRect( x - 8, y + 1, c.getWidth() - 10, this.size - 2, 5, 5 );

		g2.setFont( c.getFont() );
		g2.setPaint( c.getForeground() );
		java.awt.Rectangle rect = new java.awt.Rectangle( x - 10, y + 1, c.getWidth() - 10, this.size - 2 );
		edu.cmu.cs.dennisc.java.awt.GraphicsUtilities.drawCenteredText( g2, this.name, rect );
	}

	@Override
	public int getIconWidth() {
		return 0;//this.size;
	}

	@Override
	public int getIconHeight() {
		return this.size;
	}

}
