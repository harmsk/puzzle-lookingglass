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

package org.alice.ide.x;

import org.lgna.project.ast.DoInOrder;
import org.lgna.project.ast.DoTogether;

/**
 * @author Dennis Cosgrove
 */
public abstract class I18nFactory {
	protected abstract org.lgna.croquet.views.SwingComponentView<?> createGetsComponent( boolean isTowardLeadingEdge );

	protected abstract org.lgna.croquet.views.SwingComponentView<?> createPropertyComponent( edu.cmu.cs.dennisc.property.InstanceProperty<?> property, int underscoreCount );

	private org.lgna.croquet.views.SwingComponentView<?> createComponent( org.alice.ide.i18n.GetsChunk getsChunk, edu.cmu.cs.dennisc.property.InstancePropertyOwner owner ) {
		return this.createGetsComponent( getsChunk.isTowardLeading() );
	}

	private org.lgna.croquet.views.SwingComponentView<?> createComponent( org.alice.ide.i18n.TextChunk textChunk, edu.cmu.cs.dennisc.property.InstancePropertyOwner owner ) {
		if( textChunk.getText().isEmpty() || textChunk.getText().equals( " " ) ) {
			return null;
		} else {
			org.lgna.croquet.views.Label rv = new org.lgna.croquet.views.Label( textChunk.getText() );

			if( owner instanceof DoTogether ) {
				rv.setText( "<html>Do <i>together</i></html>" );
			} else if( owner instanceof DoInOrder ) {
				rv.setText( "<html>Do <u>in order</u></html>" );
			}

			if( ( owner instanceof org.lgna.project.ast.AbstractStatementWithBody ) || ( owner instanceof org.lgna.project.ast.ConditionalStatement ) ) {
				rv.changeFont( edu.cmu.cs.dennisc.java.awt.font.TextWeight.BOLD );
				rv.setBorder( javax.swing.BorderFactory.createEmptyBorder( 3, 4, 2, 0 ) );
			}
			if( ( owner instanceof org.lgna.project.ast.MethodInvocation ) && org.alice.stageide.ast.JointMethodUtilities.isJointGetter( ( (org.lgna.project.ast.MethodInvocation)owner ).method.getValue() ) ) {
				rv.setBorder( javax.swing.BorderFactory.createEmptyBorder( 0, 5, 0, 8 ) );
			}
			return rv;
		}
	}

	private org.lgna.croquet.views.SwingComponentView<?> createComponent( org.alice.ide.i18n.PropertyChunk propertyChunk, edu.cmu.cs.dennisc.property.InstancePropertyOwner owner ) {
		int underscoreCount = propertyChunk.getUnderscoreCount();
		String propertyName = propertyChunk.getPropertyName();
		edu.cmu.cs.dennisc.property.InstanceProperty<?> property = owner.getPropertyNamed( propertyName );
		if( property != null ) {
			return createPropertyComponent( property, underscoreCount );
		} else {
			edu.cmu.cs.dennisc.java.util.logging.Logger.severe( propertyName, owner );
			org.lgna.croquet.views.Label rv = new org.lgna.croquet.views.Label( "TODO: " + propertyName );
			rv.setBackgroundColor( java.awt.Color.RED );
			return rv;
		}
	}

	protected abstract org.lgna.croquet.views.SwingComponentView<?> createComponent( org.alice.ide.i18n.MethodInvocationChunk methodInvocationChunk, edu.cmu.cs.dennisc.property.InstancePropertyOwner owner );

	private org.lgna.croquet.views.SwingComponentView<?> createComponent( org.alice.ide.i18n.Chunk chunk, edu.cmu.cs.dennisc.property.InstancePropertyOwner owner ) {
		if( chunk instanceof org.alice.ide.i18n.TextChunk ) {
			return createComponent( (org.alice.ide.i18n.TextChunk)chunk, owner );
		} else if( chunk instanceof org.alice.ide.i18n.PropertyChunk ) {
			return createComponent( (org.alice.ide.i18n.PropertyChunk)chunk, owner );
		} else if( chunk instanceof org.alice.ide.i18n.MethodInvocationChunk ) {
			return createComponent( (org.alice.ide.i18n.MethodInvocationChunk)chunk, owner );
		} else if( chunk instanceof org.alice.ide.i18n.GetsChunk ) {
			return createComponent( (org.alice.ide.i18n.GetsChunk)chunk, owner );
		} else {
			return new org.lgna.croquet.views.Label( "unhandled: " + chunk.toString() );
		}
	}

	private int getPixelsPerIndent() {
		return 4;
	}

	private org.lgna.croquet.views.SwingComponentView<?> createComponent( org.alice.ide.i18n.Line line, edu.cmu.cs.dennisc.property.InstancePropertyOwner owner ) {
		int indentCount = line.getIndentCount();
		org.alice.ide.i18n.Chunk[] chunks = line.getChunks();
		assert chunks.length > 0 : owner;
		if( ( indentCount > 0 ) || ( chunks.length > 1 ) ) {
			org.lgna.croquet.views.LineAxisPanel rv = new org.lgna.croquet.views.LineAxisPanel();
			if( indentCount > 0 ) {
				rv.addComponent( org.lgna.croquet.views.BoxUtilities.createHorizontalSliver( indentCount * this.getPixelsPerIndent() ) );
			}
			for( org.alice.ide.i18n.Chunk chunk : chunks ) {
				org.lgna.croquet.views.AwtComponentView<?> component = createComponent( chunk, owner );
				if( component != null ) {
					//assert component != null : chunk.toString();
					//				rv.setAlignmentY( 0.5f );
					rv.addComponent( component );
				}
			}
			return rv;
		} else {
			//edu.cmu.cs.dennisc.print.PrintUtilities.println( "skipping line" );
			org.lgna.croquet.views.SwingComponentView<?> rv = createComponent( chunks[ 0 ], owner );
			assert rv != null : chunks[ 0 ].toString();
			return rv;
		}
	}

	public org.lgna.croquet.views.SwingComponentView<?> createComponent( org.alice.ide.i18n.Page page, edu.cmu.cs.dennisc.property.InstancePropertyOwner owner, org.lgna.croquet.views.SwingComponentView<?> firstLinePrefixComponent ) {
		org.alice.ide.i18n.Line[] lines = page.getLines();
		final int N = lines.length;
		assert N > 0;
		if( N > 1 ) {
			final boolean isLoop = lines[ N - 1 ].isLoop();
			org.lgna.croquet.views.PageAxisPanel pagePane = new org.lgna.croquet.views.PageAxisPanel() {
				@Override
				protected javax.swing.JPanel createJPanel() {
					return new DefaultJPanel() {
						@Override
						protected void paintComponent( java.awt.Graphics g ) {
							java.awt.Color prev = g.getColor();
							if( isLoop ) {
								int n = this.getComponentCount();
								java.awt.Component cFirst = this.getComponent( 0 );
								java.awt.Component cLast = this.getComponent( n - 1 );
								g.setColor( edu.cmu.cs.dennisc.java.awt.ColorUtilities.createGray( 160 ) );
								int xB = I18nFactory.this.getPixelsPerIndent();
								int xA = xB / 2;
								int yTop = cFirst.getY() + cFirst.getHeight();
								int yBottom = cLast.getY() + ( cLast.getHeight() / 2 );
								g.drawLine( xA, yTop, xA, yBottom );
								g.drawLine( xA, yBottom, xB, yBottom );

								int xC = cLast.getX() + cLast.getWidth();
								int xD = xC + I18nFactory.this.getPixelsPerIndent();

								g.drawLine( xC, yBottom, xD, yBottom );
								g.drawLine( xD, yBottom, xD, cLast.getY() );

								final int HALF_TRIANGLE_WIDTH = 3;
								edu.cmu.cs.dennisc.java.awt.GraphicsUtilities.fillTriangle( g, edu.cmu.cs.dennisc.java.awt.GraphicsUtilities.Heading.NORTH, xA - HALF_TRIANGLE_WIDTH, yTop, HALF_TRIANGLE_WIDTH + 1 + HALF_TRIANGLE_WIDTH, 10 );
							}
							g.setColor( prev );
							super.paintComponent( g );
						}
					};
				}
			};
			boolean setFirstLinePrefix = firstLinePrefixComponent != null;
			for( org.alice.ide.i18n.Line line : lines ) {
				if( setFirstLinePrefix ) {
					org.lgna.croquet.views.SwingComponentView<?> temp = createComponent( line, owner );
					pagePane.addComponent(
							new org.lgna.croquet.views.LineAxisPanel(
									firstLinePrefixComponent,
									temp ) );
					setFirstLinePrefix = false;
				} else {
					if( line.getChunks().length > 0 ) {
						pagePane.addComponent( createComponent( line, owner ) );
					} else {
						edu.cmu.cs.dennisc.java.util.logging.Logger.severe( line );
					}
				}
			}
			pagePane.revalidateAndRepaint();
			return pagePane;
		} else {
			//edu.cmu.cs.dennisc.print.PrintUtilities.println( "skipping page" );
			org.lgna.croquet.views.SwingComponentView<?> temp = createComponent( lines[ 0 ], owner );
			if( firstLinePrefixComponent != null ) {
				return new org.lgna.croquet.views.LineAxisPanel(
						firstLinePrefixComponent,
						temp );
			} else {
				return temp;
			}
		}
	}

	public org.lgna.croquet.views.SwingComponentView<?> createComponent( org.alice.ide.i18n.Page page, edu.cmu.cs.dennisc.property.InstancePropertyOwner owner ) {
		return createComponent( page, owner, null );
	}

	public org.lgna.croquet.views.SwingComponentView<?> createComponent( edu.cmu.cs.dennisc.property.InstancePropertyOwner owner, org.lgna.croquet.views.SwingComponentView<?> firstLinePrefixComponent ) {
		org.alice.ide.formatter.Formatter formatter = org.alice.ide.croquet.models.ui.formatter.FormatterState.getInstance().getValue();
		org.lgna.croquet.views.SwingComponentView<?> rv;
		if( owner != null ) {
			String value;
			if( owner instanceof org.lgna.project.ast.MethodInvocation ) {
				org.lgna.project.ast.MethodInvocation methodInvocation = (org.lgna.project.ast.MethodInvocation)owner;
				org.lgna.project.ast.AbstractMethod method = methodInvocation.method.getValue();
				String text = formatter.getNameForDeclaration( method );
				if( text.contains( "</expression/>" ) ) {
					value = text;
				} else {
					value = null;
				}
			} else {
				value = null;
			}
			if( value != null ) {
				//pass
			} else {
				Class<?> cls = owner.getClass();
				value = formatter.getTemplateText( cls );
			}
			org.alice.ide.i18n.Page page = new org.alice.ide.i18n.Page( value );
			rv = createComponent( page, owner, firstLinePrefixComponent );
		} else {
			rv = new org.lgna.croquet.views.Label( formatter.getTextForNull() );
		}
		return rv;
	}

	public org.lgna.croquet.views.SwingComponentView<?> createComponent( edu.cmu.cs.dennisc.property.InstancePropertyOwner owner ) {
		return createComponent( owner, null );
	}
}
