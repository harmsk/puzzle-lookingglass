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
package org.alice.ide.member;

/**
 * @author Dennis Cosgrove
 */
public abstract class AddMethodMenuModel extends org.lgna.croquet.MenuModel {
	public AddMethodMenuModel( java.util.UUID migrationId ) {
		super( migrationId );
	}

	private org.lgna.project.ast.NamedUserType getInstanceFactoryNamedUserType() {
		org.alice.ide.instancefactory.InstanceFactory instanceFactory = org.alice.ide.IDE.getActiveInstance().getDocumentFrame().getInstanceFactoryState().getValue();
		if( instanceFactory != null ) {
			org.lgna.project.ast.AbstractType<?, ?, ?> type = instanceFactory.getValueType();
			if( type instanceof org.lgna.project.ast.NamedUserType ) {
				return (org.lgna.project.ast.NamedUserType)type;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public boolean isRelevant() {
		return this.getInstanceFactoryNamedUserType() != null;
	}

	protected abstract org.alice.ide.ast.declaration.AddMethodComposite getAddMethodComposite( org.lgna.project.ast.NamedUserType declaringType );

	private void appendMenuItemPrepModelsForType( java.util.List<org.lgna.croquet.StandardMenuItemPrepModel> models, org.lgna.project.ast.AbstractType<?, ?, ?> type ) {
		if( type instanceof org.lgna.project.ast.NamedUserType ) {
			org.lgna.project.ast.NamedUserType namedUserType = (org.lgna.project.ast.NamedUserType)type;
			models.add( this.getAddMethodComposite( namedUserType ).getLaunchOperation().getMenuItemPrepModel() );
			appendMenuItemPrepModelsForType( models, namedUserType.superType.getValue() );
		}
	}

	@Override
	public final void handlePopupMenuPrologue( org.lgna.croquet.views.PopupMenu popupMenu, org.lgna.croquet.history.PopupPrepStep context ) {
		super.handlePopupMenuPrologue( popupMenu, context );
		java.util.List<org.lgna.croquet.StandardMenuItemPrepModel> models = edu.cmu.cs.dennisc.java.util.Lists.newLinkedList();
		this.appendMenuItemPrepModelsForType( models, this.getInstanceFactoryNamedUserType() );
		org.lgna.croquet.views.MenuItemContainerUtilities.setMenuElements( popupMenu, models );
	}
}
