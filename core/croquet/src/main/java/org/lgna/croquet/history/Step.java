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
package org.lgna.croquet.history;

import java.lang.ref.WeakReference;

/**
 * @author Dennis Cosgrove
 */
public abstract class Step<M extends org.lgna.croquet.Model> extends TransactionNode<Transaction> {

	private final java.util.List<org.lgna.croquet.Context> contexts;
	private final org.lgna.croquet.triggers.Trigger trigger;
	private final java.util.UUID id;

	// This is not how this should be coded. This should not be a weak reference, but croquet
	// decides that it should never let anything garbage collect and so this will be stored in the
	// transaction history forever.
	private final WeakReference<M> model;

	public Step( Transaction parent, M model, org.lgna.croquet.triggers.Trigger trigger ) {
		super( parent );
		this.model = new WeakReference<>( model );
		if( trigger != null ) {
			this.trigger = trigger;
		} else {
			//todo?
			this.trigger = org.lgna.croquet.triggers.NullTrigger.createUserInstance();
		}
		this.id = java.util.UUID.randomUUID();

		java.util.List<org.lgna.croquet.Context> contexts = edu.cmu.cs.dennisc.java.util.Lists.newLinkedList();
		if( model != null ) {
			for( org.lgna.croquet.ContextFactory<?> contextFactory : model.getContextFactories() ) {
				//edu.cmu.cs.dennisc.java.util.logging.Logger.errln( model );
				contexts.add( contextFactory.createContext() );
			}
		}
		this.contexts = java.util.Collections.unmodifiableList( contexts );
	}

	public static class Key<T> {
		public static <T> Key<T> createInstance( String repr ) {
			return new Key<T>( repr );
		}

		private final String repr;

		private Key( String repr ) {
			this.repr = repr;
		}

		@Override
		public String toString() {
			return this.repr;
		}
	}

	private final java.util.Map/* < Key<T>, T > */dataMap = edu.cmu.cs.dennisc.java.util.Maps.newHashMap();

	public <T> boolean containsEphemeralDataFor( Key<T> key ) {
		return this.dataMap.containsKey( key );
	}

	public <T> T getEphemeralDataFor( Key<T> key ) {
		return (T)this.dataMap.get( key );
	}

	public <T> void putEphemeralDataFor( Key<T> key, T value ) {
		this.dataMap.put( key, value );
	}

	public <T> void removeEphemeralDataFor( Key<T> key ) {
		this.dataMap.remove( key );
	}

	@Override
	protected void appendContexts( java.util.List<org.lgna.croquet.Context> out ) {
		out.addAll( this.contexts );
	}

	/* package-private */Iterable<org.lgna.croquet.Context> getContexts() {
		return this.contexts;
	}

	public <C extends org.lgna.croquet.Context> C findFirstContext( Class<C> cls ) {
		if( this.getOwnerTransaction() != null ) {
			return this.getOwnerTransaction().findFirstContext( this, cls );
		} else {
			edu.cmu.cs.dennisc.java.util.logging.Logger.severe( cls );
			return null;
		}
	}

	public org.lgna.croquet.triggers.Trigger getTrigger() {
		return this.trigger;
	}

	public java.util.UUID getId() {
		return this.id;
	}

	public Step<?> getPreviousStep() {
		int index = this.getOwnerTransaction().getIndexOfChildStep( this );
		if( index > 0 ) {
			return this.getOwnerTransaction().getChildStepAt( index - 1 );
		} else {
			return null;
		}
	}

	protected org.lgna.croquet.views.ViewController<?, ?> getViewController() {
		return this.trigger != null ? this.trigger.getViewController() : null;
	}

	public M getModel() {
		return this.model.get();
	}

	/* This is here to clarify the structure of the transaction history tree */
	public Transaction getOwnerTransaction() {
		return this.getOwner();
	}

	protected StringBuilder updateRepr( StringBuilder rv ) {
		org.lgna.croquet.Model model = this.getModel();
		if( model != null ) {
			rv.append( "model=" );
			rv.append( model );
			rv.append( ";trigger=" );
			org.lgna.croquet.triggers.Trigger trigger = this.getTrigger();
			if( trigger != null ) {
				trigger.appendRepr( rv );
			}
			rv.append( ";text=" );
			model.appendUserRepr( rv );
			rv.append( ";" );
		}
		return rv;
	}

	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( this.getClass().getSimpleName() );
		sb.append( "[" );
		updateRepr( sb );
		sb.append( "]" );
		return sb.toString();
	}
}
