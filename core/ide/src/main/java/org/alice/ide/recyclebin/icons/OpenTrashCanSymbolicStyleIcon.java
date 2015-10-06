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
package org.alice.ide.recyclebin.icons;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

/**
 * This class has been automatically generated using svg2java
 * 
 */
public class OpenTrashCanSymbolicStyleIcon implements javax.swing.Icon {

	private float origAlpha = 1.0f;

	/**
	 * Paints the transcoded SVG image on the specified graphics context. You
	 * can install a custom transformation on the graphics context to scale the
	 * image.
	 * 
	 * @param g Graphics context.
	 */
	public void paint( Graphics2D g ) {
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		origAlpha = 1.0f;
		Composite origComposite = g.getComposite();
		if( origComposite instanceof AlphaComposite ) {
			AlphaComposite origAlphaComposite =
					(AlphaComposite)origComposite;
			if( origAlphaComposite.getRule() == AlphaComposite.SRC_OVER ) {
				origAlpha = origAlphaComposite.getAlpha();
			}
		}

		// _0
		AffineTransform trans_0 = g.getTransform();
		paintRootGraphicsNode_0( g );
		g.setTransform( trans_0 );

	}

	private void paintCompositeGraphicsNode_0_0_0( Graphics2D g ) {
	}

	private void paintCompositeGraphicsNode_0_0_1( Graphics2D g ) {
	}

	private void paintCompositeGraphicsNode_0_0_2( Graphics2D g ) {
	}

	private void paintShapeNode_0_0_3_0( Graphics2D g ) {
		GeneralPath shape0 = new GeneralPath();
		shape0.moveTo( 504.1351, 172.64601 );
		shape0.curveTo( 503.64478, 172.64601, 503.33902, 172.87447, 503.10385, 173.11476 );
		shape0.curveTo( 502.86868, 173.35506, 502.66635, 173.67328, 502.66635, 174.14601 );
		shape0.lineTo( 502.66635, 174.61476 );
		shape0.lineTo( 500.66635, 174.61476 );
		shape0.lineTo( 500.66635, 176.61476 );
		shape0.lineTo( 515.6664, 176.61476 );
		shape0.lineTo( 515.6664, 174.61476 );
		shape0.lineTo( 513.6664, 174.61476 );
		shape0.lineTo( 513.6664, 174.27101 );
		shape0.lineTo( 513.6664, 174.2398 );
		shape0.curveTo( 513.64246, 173.8092, 513.49615, 173.48752, 513.26013, 173.20856 );
		shape0.curveTo( 513.0364, 172.94412, 512.6334, 172.67924, 512.1039, 172.6773 );
		shape0.lineTo( 512.1039, 172.6461 );
		shape0.lineTo( 512.0414, 172.6461 );
		shape0.lineTo( 504.13513, 172.6461 );
		shape0.closePath();
		shape0.moveTo( 504.66635, 173.64601 );
		shape0.lineTo( 511.66635, 173.64601 );
		shape0.lineTo( 511.66635, 174.61476 );
		shape0.lineTo( 504.66635, 174.61476 );
		shape0.lineTo( 504.66635, 173.64601 );
		shape0.closePath();
		g.setPaint( this.fillColor );
		g.fill( shape0 );
	}

	private void paintShapeNode_0_0_3_1( Graphics2D g ) {
		GeneralPath shape1 = new GeneralPath();
		shape1.moveTo( 512.6664, 179.64601 );
		shape1.lineTo( 514.6664, 179.64601 );
		shape1.lineTo( 514.6664, 188.42726 );
		shape1.curveTo( 514.6664, 189.62807, 513.684, 190.64601, 512.4789, 190.64601 );
		shape1.lineTo( 503.85388, 190.64601 );
		shape1.curveTo( 502.64877, 190.64601, 501.66638, 189.62807, 501.66638, 188.42726 );
		shape1.lineTo( 501.66638, 179.64601 );
		shape1.lineTo( 503.66638, 179.64601 );
		shape1.lineTo( 503.66638, 188.64601 );
		shape1.lineTo( 512.6664, 188.64601 );
		shape1.closePath();
		g.fill( shape1 );
	}

	private void paintShapeNode_0_0_3_2( Graphics2D g ) {
		GeneralPath shape2 = new GeneralPath();
		shape2.moveTo( 507.6976, 180.64601 );
		shape2.lineTo( 507.6976, 187.64601 );
		shape2.lineTo( 508.66635, 187.64601 );
		shape2.lineTo( 508.66635, 180.64601 );
		shape2.closePath();
		g.fill( shape2 );
	}

	private void paintShapeNode_0_0_3_3( Graphics2D g ) {
		GeneralPath shape3 = new GeneralPath();
		shape3.moveTo( 509.66635, 180.64601 );
		shape3.lineTo( 509.66635, 187.64601 );
		shape3.lineTo( 510.66635, 187.64601 );
		shape3.lineTo( 510.66635, 180.64601 );
		shape3.closePath();
		g.fill( shape3 );
	}

	private void paintShapeNode_0_0_3_4( Graphics2D g ) {
		GeneralPath shape4 = new GeneralPath();
		shape4.moveTo( 505.6976, 180.64601 );
		shape4.lineTo( 505.6976, 187.64601 );
		shape4.lineTo( 506.66635, 187.64601 );
		shape4.lineTo( 506.66635, 180.64601 );
		shape4.closePath();
		g.fill( shape4 );
	}

	private void paintCompositeGraphicsNode_0_0_3( Graphics2D g ) {
		// _0_0_3_0
		AffineTransform trans_0_0_3_0 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f ) );
		paintShapeNode_0_0_3_0( g );
		g.setTransform( trans_0_0_3_0 );
		// _0_0_3_1
		AffineTransform trans_0_0_3_1 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f ) );
		paintShapeNode_0_0_3_1( g );
		g.setTransform( trans_0_0_3_1 );
		// _0_0_3_2
		AffineTransform trans_0_0_3_2 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f ) );
		paintShapeNode_0_0_3_2( g );
		g.setTransform( trans_0_0_3_2 );
		// _0_0_3_3
		AffineTransform trans_0_0_3_3 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f ) );
		paintShapeNode_0_0_3_3( g );
		g.setTransform( trans_0_0_3_3 );
		// _0_0_3_4
		AffineTransform trans_0_0_3_4 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f ) );
		paintShapeNode_0_0_3_4( g );
		g.setTransform( trans_0_0_3_4 );
	}

	private void paintCompositeGraphicsNode_0_0_4( Graphics2D g ) {
	}

	private void paintCompositeGraphicsNode_0_0_5( Graphics2D g ) {
	}

	private void paintCompositeGraphicsNode_0_0_6( Graphics2D g ) {
	}

	private void paintCompositeGraphicsNode_0_0_7( Graphics2D g ) {
	}

	private void paintCompositeGraphicsNode_0_0_8( Graphics2D g ) {
	}

	private void paintCanvasGraphicsNode_0_0( Graphics2D g ) {
		// _0_0_0
		AffineTransform trans_0_0_0 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, -500.6663513183594f, -172.64601135253906f ) );
		paintCompositeGraphicsNode_0_0_0( g );
		g.setTransform( trans_0_0_0 );
		// _0_0_1
		AffineTransform trans_0_0_1 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, -500.6663513183594f, -172.64601135253906f ) );
		paintCompositeGraphicsNode_0_0_1( g );
		g.setTransform( trans_0_0_1 );
		// _0_0_2
		AffineTransform trans_0_0_2 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, -500.6663513183594f, -172.64601135253906f ) );
		paintCompositeGraphicsNode_0_0_2( g );
		g.setTransform( trans_0_0_2 );
		// _0_0_3
		AffineTransform trans_0_0_3 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, -500.6663513183594f, -172.64601135253906f ) );
		paintCompositeGraphicsNode_0_0_3( g );
		g.setTransform( trans_0_0_3 );
		// _0_0_4
		AffineTransform trans_0_0_4 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, -500.6663513183594f, -172.64601135253906f ) );
		paintCompositeGraphicsNode_0_0_4( g );
		g.setTransform( trans_0_0_4 );
		// _0_0_5
		AffineTransform trans_0_0_5 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, -500.6663513183594f, -172.64601135253906f ) );
		paintCompositeGraphicsNode_0_0_5( g );
		g.setTransform( trans_0_0_5 );
		// _0_0_6
		AffineTransform trans_0_0_6 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, -500.6663513183594f, -172.64601135253906f ) );
		paintCompositeGraphicsNode_0_0_6( g );
		g.setTransform( trans_0_0_6 );
		// _0_0_7
		AffineTransform trans_0_0_7 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, -500.6663513183594f, -172.64601135253906f ) );
		paintCompositeGraphicsNode_0_0_7( g );
		g.setTransform( trans_0_0_7 );
		// _0_0_8
		AffineTransform trans_0_0_8 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, -500.6663513183594f, -172.64601135253906f ) );
		paintCompositeGraphicsNode_0_0_8( g );
		g.setTransform( trans_0_0_8 );
	}

	private void paintRootGraphicsNode_0( Graphics2D g ) {
		// _0_0
		g.setComposite( AlphaComposite.getInstance( 3, 1.0f * origAlpha ) );
		AffineTransform trans_0_0 = g.getTransform();
		g.transform( new AffineTransform( 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f ) );
		paintCanvasGraphicsNode_0_0( g );
		g.setTransform( trans_0_0 );
	}

	/**
	 * Returns the X of the bounding box of the original SVG image.
	 * 
	 * @return The X of the bounding box of the original SVG image.
	 */
	public int getOrigX() {
		return 0;
	}

	/**
	 * Returns the Y of the bounding box of the original SVG image.
	 * 
	 * @return The Y of the bounding box of the original SVG image.
	 */
	public int getOrigY() {
		return 0;
	}

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public int getOrigWidth() {
		return 15;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public int getOrigHeight() {
		return 18;
	}

	/**
	 * The current width of this resizable icon.
	 */
	private int width;

	/**
	 * The current height of this resizable icon.
	 */
	private int height;

	private Color fillColor = new Color( 190, 190, 190, 255 );

	/**
	 * Creates a new transcoded SVG image.
	 */
	public OpenTrashCanSymbolicStyleIcon( int width, int height, Color fillColor ) {
		this( width, height );
		this.fillColor = fillColor;
	}

	public OpenTrashCanSymbolicStyleIcon( int width, int height ) {
		this.width = width;
		this.height = height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconHeight()
	 */
	@Override
	public int getIconHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconWidth()
	 */
	@Override
	public int getIconWidth() {
		return width;
	}

	/*
	 * Set the dimension of the icon.
	 */
	public void setDimension( Dimension newDimension ) {
		this.width = newDimension.width;
		this.height = newDimension.height;
	}

	public Color getFillColor() {
		return this.fillColor;
	}

	public void setFillColor( Color fillColor ) {
		this.fillColor = fillColor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
	 */
	@Override
	public void paintIcon( Component c, Graphics g, int x, int y ) {
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.translate( x, y );

		double coef1 = (double)this.width / (double)getOrigWidth();
		double coef2 = (double)this.height / (double)getOrigHeight();
		double coef = Math.min( coef1, coef2 );
		g2d.translate( 0.0, (double)this.height - ( (double)this.getOrigHeight() * coef ) );
		g2d.scale( coef, coef );
		paint( g2d );
		g2d.dispose();
	}
}
