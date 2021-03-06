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
package org.lgna.croquet.imp.frame;

/**
 * @author Dennis Cosgrove
 */
public final class LazyIsFrameShowingState<C extends org.lgna.croquet.FrameComposite<?>> extends AbstractIsFrameShowingState {
	public static <C extends org.lgna.croquet.FrameComposite<?>> org.lgna.croquet.BooleanState createInstance( org.lgna.croquet.Group group, Class<C> cls, edu.cmu.cs.dennisc.pattern.Lazy<C> lazy ) {
		return new LazyIsFrameShowingState<C>( group, cls, lazy );
	}

	public static <C extends org.lgna.croquet.FrameComposite<?>> org.lgna.croquet.BooleanState createNoArgumentConstructorInstance( org.lgna.croquet.Group group, Class<C> cls ) {
		try {
			final java.lang.reflect.Constructor<C> jConstructor = cls.getConstructor();
			assert java.lang.reflect.Modifier.isPublic( jConstructor.getModifiers() ) : jConstructor;
			return createInstance( group, cls, new edu.cmu.cs.dennisc.pattern.Lazy<C>() {
				@Override
				protected C create() {
					try {
						return jConstructor.newInstance();
					} catch( java.lang.reflect.InvocationTargetException ite ) {
						throw new RuntimeException( jConstructor.toString(), ite );
					} catch( IllegalAccessException iae ) {
						throw new RuntimeException( jConstructor.toString(), iae );
					} catch( InstantiationException ie ) {
						throw new RuntimeException( jConstructor.toString(), ie );
					}
				}
			} );
		} catch( NoSuchMethodException nsme ) {
			throw new RuntimeException( nsme );
		}
	}

	private LazyIsFrameShowingState( org.lgna.croquet.Group group, Class<C> cls, edu.cmu.cs.dennisc.pattern.Lazy<C> lazy ) {
		super( group, java.util.UUID.fromString( "e6efce56-7da5-4798-9ec3-6fcaab3962b5" ) );
		this.cls = cls;
		this.lazy = lazy;
	}

	@Override
	protected Class<? extends org.lgna.croquet.Element> getClassUsedForLocalization() {
		return this.cls;
	}

	@Override
	public C getFrameComposite() {
		return this.lazy.get();
	}

	private final Class<C> cls;
	private final edu.cmu.cs.dennisc.pattern.Lazy<C> lazy;
}
