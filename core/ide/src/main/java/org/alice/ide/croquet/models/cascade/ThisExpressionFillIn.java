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
package org.alice.ide.croquet.models.cascade;


/**
 * @author Dennis Cosgrove
 */
public class ThisExpressionFillIn extends org.alice.ide.croquet.models.cascade.ExpressionFillInWithoutBlanks<org.lgna.project.ast.ThisExpression> {
	private static class SingletonHolder {
		private static ThisExpressionFillIn instance = new ThisExpressionFillIn( new org.lgna.project.ast.ThisExpression() );
	}

	public static ThisExpressionFillIn getInstance() {
		return SingletonHolder.instance;
	}

	public static ThisExpressionFillIn createInstanceWithExpression( org.lgna.project.ast.ThisExpression thisExpression ) {
		return new ThisExpressionFillIn( thisExpression );
	}

	private final org.lgna.project.ast.ThisExpression transientValue;

	private ThisExpressionFillIn( org.lgna.project.ast.ThisExpression transientValue ) {
		super( java.util.UUID.fromString( "9183fbda-6571-4421-9336-dde1ec9188de" ) );
		this.transientValue = transientValue;
	}

	@Override
	public org.lgna.project.ast.ThisExpression getTransientValue( org.lgna.croquet.imp.cascade.ItemNode<? super org.lgna.project.ast.ThisExpression, Void> node ) {
		return this.transientValue;
	}

	@Override
	public org.lgna.project.ast.ThisExpression createValue( org.lgna.croquet.imp.cascade.ItemNode<? super org.lgna.project.ast.ThisExpression, Void> node, org.lgna.croquet.history.TransactionHistory transactionHistory ) {
		return new org.lgna.project.ast.ThisExpression();
	}

	@Override
	protected javax.swing.Icon getLeadingIcon( org.lgna.croquet.imp.cascade.ItemNode<? super org.lgna.project.ast.ThisExpression, java.lang.Void> step ) {
		java.awt.Dimension size = new java.awt.Dimension( 18, 18 );
		org.lgna.croquet.icon.IconFactory iconFactory = org.alice.stageide.icons.IconFactoryManager.getIconFactoryForType( transientValue.getType() );

		if( iconFactory != null ) {
			return iconFactory.getIcon( size );
		}

		return org.lgna.croquet.icon.EmptyIconFactory.getInstance().getIcon( size );
	}
}
