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
package edu.cmu.cs.dennisc.property;

/**
 * @author Dennis Cosgrove
 */
public class ListProperty<E> extends InstanceProperty<java.util.ArrayList<E>>implements Iterable<E> {
	public ListProperty( InstancePropertyOwner owner ) {
		super( owner, new java.util.ArrayList<E>() );
	}

	public void addListPropertyListener( edu.cmu.cs.dennisc.property.event.ListPropertyListener<E> l ) {
		if( this.listPropertyListeners != null ) {
			//pass
		} else {
			this.listPropertyListeners = edu.cmu.cs.dennisc.java.util.Lists.newCopyOnWriteArrayList();
		}
		this.listPropertyListeners.add( l );
	}

	public void removeListPropertyListener( edu.cmu.cs.dennisc.property.event.ListPropertyListener<E> l ) {
		assert this.listPropertyListeners != null : this;
		this.listPropertyListeners.remove( l );
	}

	private void fireAdding( edu.cmu.cs.dennisc.property.event.AddListPropertyEvent<E> e ) {
		getOwner().fireAdding( e );
		if( this.listPropertyListeners != null ) {
			for( edu.cmu.cs.dennisc.property.event.ListPropertyListener<E> l : this.listPropertyListeners ) {
				l.adding( e );
			}
		}
	}

	private void fireAdded( edu.cmu.cs.dennisc.property.event.AddListPropertyEvent<E> e ) {
		getOwner().fireAdded( e );
		if( this.listPropertyListeners != null ) {
			for( edu.cmu.cs.dennisc.property.event.ListPropertyListener<E> l : this.listPropertyListeners ) {
				l.added( e );
			}
		}
	}

	private void fireClearing( edu.cmu.cs.dennisc.property.event.ClearListPropertyEvent<E> e ) {
		getOwner().fireClearing( e );
		if( this.listPropertyListeners != null ) {
			for( edu.cmu.cs.dennisc.property.event.ListPropertyListener<E> l : this.listPropertyListeners ) {
				l.clearing( e );
			}
		}
	}

	private void fireCleared( edu.cmu.cs.dennisc.property.event.ClearListPropertyEvent<E> e ) {
		getOwner().fireCleared( e );
		if( this.listPropertyListeners != null ) {
			for( edu.cmu.cs.dennisc.property.event.ListPropertyListener<E> l : this.listPropertyListeners ) {
				l.cleared( e );
			}
		}
	}

	private void fireRemoving( edu.cmu.cs.dennisc.property.event.RemoveListPropertyEvent<E> e ) {
		getOwner().fireRemoving( e );
		if( this.listPropertyListeners != null ) {
			for( edu.cmu.cs.dennisc.property.event.ListPropertyListener<E> l : this.listPropertyListeners ) {
				l.removing( e );
			}
		}
	}

	private void fireRemoved( edu.cmu.cs.dennisc.property.event.RemoveListPropertyEvent<E> e ) {
		getOwner().fireRemoved( e );
		if( this.listPropertyListeners != null ) {
			for( edu.cmu.cs.dennisc.property.event.ListPropertyListener<E> l : this.listPropertyListeners ) {
				l.removed( e );
			}
		}
	}

	private void fireSetting( edu.cmu.cs.dennisc.property.event.SetListPropertyEvent<E> e ) {
		getOwner().fireSetting( e );
		if( this.listPropertyListeners != null ) {
			for( edu.cmu.cs.dennisc.property.event.ListPropertyListener<E> l : this.listPropertyListeners ) {
				l.setting( e );
			}
		}
	}

	private void fireSet( edu.cmu.cs.dennisc.property.event.SetListPropertyEvent<E> e ) {
		getOwner().fireSet( e );
		if( this.listPropertyListeners != null ) {
			for( edu.cmu.cs.dennisc.property.event.ListPropertyListener<E> l : this.listPropertyListeners ) {
				l.set( e );
			}
		}
	}

	public Object[] toArray() {
		return getValue().toArray();
	}

	public <T> T[] toArray( T[] a ) {
		return getValue().toArray( a );
	}

	public <T> T[] toArray( Class<T> componentType ) {
		return this.toArray( (T[])java.lang.reflect.Array.newInstance( componentType, this.size() ) );
	}

	public boolean isEmpty() {
		return getValue().isEmpty();
	}

	public boolean contains( Object o ) {
		return getValue().contains( o );
	}

	public boolean containsAll( java.util.Collection<?> c ) {
		return getValue().containsAll( c );
	}

	public int size() {
		return getValue().size();
	}

	public java.util.List<E> subList( int fromIndex, int upToButExcludingIndex ) {
		return getValue().subList( fromIndex, upToButExcludingIndex );
	}

	public java.util.List<E> subListCopy( int fromIndex, int upToButExcludingIndex ) {
		java.util.ArrayList<E> rv = edu.cmu.cs.dennisc.java.util.Lists.newArrayList();
		rv.ensureCapacity( ( ( upToButExcludingIndex - fromIndex ) + 1 ) - 1 );
		rv.addAll( this.subList( fromIndex, upToButExcludingIndex ) );
		return rv;
	}

	public int indexOf( Object element ) {
		return getValue().indexOf( element );
	}

	public int lastIndexOf( Object element ) {
		return getValue().lastIndexOf( element );
	}

	public E get( int index ) {
		return getValue().get( index );
	}

	@Override
	public java.util.Iterator<E> iterator() {
		return getValue().iterator();
	}

	public void add( int index, E... elements ) {
		edu.cmu.cs.dennisc.property.event.AddListPropertyEvent<E> e = new edu.cmu.cs.dennisc.property.event.AddListPropertyEvent<E>( this, index, elements );
		fireAdding( e );
		getValue().ensureCapacity( size() + elements.length );
		int i = index;
		for( E element : elements ) {
			getValue().add( i++, element );
		}
		fireAdded( e );
	}

	public void add( E... elements ) {
		add( size(), elements );
	}

	public boolean addAll( int index, java.util.Collection<? extends E> collection ) {
		edu.cmu.cs.dennisc.property.event.AddListPropertyEvent<E> e = new edu.cmu.cs.dennisc.property.event.AddListPropertyEvent<E>( this, index, collection );
		fireAdding( e );
		boolean rv = getValue().addAll( index, collection );
		fireAdded( e );
		return rv;
	}

	public boolean addAll( java.util.Collection<? extends E> c ) {
		return addAll( size(), c );
	}

	public void clear() {
		//assert isLocked() == false;
		edu.cmu.cs.dennisc.property.event.ClearListPropertyEvent<E> e = new edu.cmu.cs.dennisc.property.event.ClearListPropertyEvent<E>( this );
		fireClearing( e );
		getValue().clear();
		fireCleared( e );
	}

	public void removeExclusive( int fromIndex, int upToButExcludingIndex ) {
		//assert isLocked() == false;
		edu.cmu.cs.dennisc.property.event.RemoveListPropertyEvent<E> e = new edu.cmu.cs.dennisc.property.event.RemoveListPropertyEvent<E>( this, fromIndex, this.subListCopy( fromIndex, upToButExcludingIndex ) );
		fireRemoving( e );
		for( int i = fromIndex; i < upToButExcludingIndex; i++ ) {
			getValue().remove( fromIndex );
		}
		fireRemoved( e );
	}

	public void removeInclusive( int fromIndex, int upToAndIncludingIndex ) {
		this.removeExclusive( fromIndex, upToAndIncludingIndex + 1 );
	}

	public E remove( int index ) {
		E rv = get( index );
		removeInclusive( index, index );
		return rv;
	}

	public E remove( E element ) {
		int index = indexOf( element );
		if( index < 0 ) {
			return null;
		} else {
			return remove( index );
		}
	}

	public void set( int index, E... elements ) {
		edu.cmu.cs.dennisc.property.event.SetListPropertyEvent<E> e = new edu.cmu.cs.dennisc.property.event.SetListPropertyEvent<E>( this, index, elements );
		fireSetting( e );
		for( int i = 0; i < elements.length; i++ ) {
			getValue().set( index + i, elements[ i ] );
		}
		fireSet( e );
	}

	public void set( int index, java.util.List<E> elements ) {
		edu.cmu.cs.dennisc.property.event.SetListPropertyEvent<E> e = new edu.cmu.cs.dennisc.property.event.SetListPropertyEvent<E>( this, index, elements );
		fireSetting( e );
		for( int i = 0; i < elements.size(); i++ ) {
			getValue().set( index + i, elements.get( i ) );
		}
		fireSet( e );
	}

	public void swap( int indexA, int indexB ) {
		if( indexA != indexB ) {
			//todo: test
			int indexMin = Math.min( indexA, indexB );
			int indexMax = Math.max( indexA, indexB );
			java.util.List<E> subList = this.subList( indexMin, indexMax + 1 );
			final int N = subList.size();
			E eMin = subList.get( 0 );
			E eMax = subList.get( N - 1 );
			subList.set( 0, eMax );
			subList.set( N - 1, eMin );
			this.set( indexMin, subList );
		}
	}

	public void slide( int prevIndex, int nextIndex ) {
		if( prevIndex != nextIndex ) {

			final int ONE_TO_EXCLUDE = 1;
			//todo: test
			E element = this.getValue().get( prevIndex );
			if( prevIndex < nextIndex ) {
				java.util.List<E> subList = this.subListCopy( prevIndex + 1, nextIndex + ONE_TO_EXCLUDE );
				subList.add( element );
				this.set( prevIndex, subList );
			} else {
				java.util.List<E> subList = this.subListCopy( nextIndex, ( prevIndex - 1 ) + ONE_TO_EXCLUDE );
				subList.add( 0, element );
				this.set( nextIndex, subList );
			}
		}
	}

	@Override
	public void setValue( java.util.ArrayList<E> value ) {

		//todo?

		edu.cmu.cs.dennisc.property.event.ClearListPropertyEvent<E> eClear = new edu.cmu.cs.dennisc.property.event.ClearListPropertyEvent<E>( this );
		edu.cmu.cs.dennisc.property.event.AddListPropertyEvent<E> eAdd = new edu.cmu.cs.dennisc.property.event.AddListPropertyEvent<E>( this, 0, value );
		fireClearing( eClear );
		fireAdding( eAdd );
		super.setValue( value );
		fireCleared( eClear );
		fireAdded( eAdd );
	}

	private java.util.List<edu.cmu.cs.dennisc.property.event.ListPropertyListener<E>> listPropertyListeners = null;
}
