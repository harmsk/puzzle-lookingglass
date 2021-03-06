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

package org.alice.ide.croquet.models.ast.keyed;

/**
 * @author Dennis Cosgrove
 */
public class KeyedBlank extends org.lgna.croquet.CascadeBlank<org.lgna.project.ast.JavaKeyedArgument> {
	private static java.util.Map<org.lgna.project.ast.ArgumentListProperty<org.lgna.project.ast.JavaKeyedArgument>, KeyedBlank> map = edu.cmu.cs.dennisc.java.util.Maps.newHashMap();

	public static synchronized KeyedBlank getInstance( org.lgna.project.ast.ArgumentListProperty<org.lgna.project.ast.JavaKeyedArgument> argumentListProperty ) {
		KeyedBlank rv = map.get( argumentListProperty );
		if( rv != null ) {
			//pass
		} else {
			rv = new KeyedBlank( argumentListProperty );
			map.put( argumentListProperty, rv );
		}
		return rv;
	}

	private static boolean isValidMethod( java.lang.reflect.Method mthd, org.lgna.project.ast.AbstractType<?, ?, ?> valueType ) {
		int modifiers = mthd.getModifiers();
		if( java.lang.reflect.Modifier.isPublic( modifiers ) && java.lang.reflect.Modifier.isStatic( modifiers ) ) {
			return valueType.isAssignableFrom( mthd.getReturnType() );
		} else {
			return false;
		}
	}

	private final org.lgna.project.ast.ArgumentListProperty<org.lgna.project.ast.JavaKeyedArgument> argumentListProperty;

	private KeyedBlank( org.lgna.project.ast.ArgumentListProperty<org.lgna.project.ast.JavaKeyedArgument> argumentListProperty ) {
		super( java.util.UUID.fromString( "c9b684e5-9e91-4c38-8cdf-ffce14de6a18" ) );
		this.argumentListProperty = argumentListProperty;
	}

	@Override
	protected void updateChildren( java.util.List<org.lgna.croquet.CascadeBlankChild> children, org.lgna.croquet.imp.cascade.BlankNode<org.lgna.project.ast.JavaKeyedArgument> blankNode ) {
		org.lgna.project.ast.AbstractType<?, ?, ?> valueType = this.argumentListProperty.getOwner().getParameterOwnerProperty().getValue().getKeyedParameter().getValueType().getComponentType();
		org.lgna.project.ast.AbstractType<?, ?, ?> keywordFactoryType = valueType.getKeywordFactoryType();
		if( keywordFactoryType != null ) {
			Class<?> cls = ( (org.lgna.project.ast.JavaType)keywordFactoryType ).getClassReflectionProxy().getReification();
			for( java.lang.reflect.Method mthd : cls.getMethods() ) {
				if( isValidMethod( mthd, valueType ) ) {
					org.lgna.project.ast.JavaMethod keyMethod = org.lgna.project.ast.JavaMethod.getInstance( mthd );
					boolean isAlreadyFilledIn = false;
					for( org.lgna.project.ast.JavaKeyedArgument keyedArgument : this.argumentListProperty ) {
						if( keyedArgument.getKeyMethod() == keyMethod ) {
							isAlreadyFilledIn = true;
							break;
						}
					}
					if( isAlreadyFilledIn ) {
						//pass
					} else {
						children.add( JavaKeyedArgumentFillIn.getInstance( org.lgna.project.ast.JavaMethod.getInstance( mthd ) ) );
					}
				}
			}
			if( this.argumentListProperty.getOwner() instanceof org.lgna.project.ast.MethodInvocation ) {
				org.lgna.project.ast.MethodInvocation invocation = (org.lgna.project.ast.MethodInvocation)this.argumentListProperty.getOwner();
				org.lgna.project.ast.AbstractMethod method = invocation.method.getValue();
				edu.wustl.lookingglass.lgpedia.LgpediaOperation operation = new edu.wustl.lookingglass.lgpedia.LgpediaOperation( method, true );
				children.add( org.lgna.croquet.CascadeLineSeparator.getInstance() );
				children.add( operation.getFauxCascadeItem() );
			}
		}
	}
}
