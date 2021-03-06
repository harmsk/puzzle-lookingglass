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

package org.alice.ide.properties.uicontroller;

import org.alice.ide.properties.adapter.AbstractPropertyAdapter;
import org.lgna.croquet.views.GridBagPanel;
import org.lgna.croquet.views.Panel;

public abstract class AbstractAdapterController<P> extends GridBagPanel implements PropertyAdapterController<P>
{
	protected AbstractPropertyAdapter.ValueChangeObserver<P> valueChangeObserver = new AbstractPropertyAdapter.ValueChangeObserver<P>()
	{
		@Override
		public void valueChanged( P newValue )
		{
			AbstractAdapterController.this.setValueOnUI( newValue );
		}
	};

	protected AbstractPropertyAdapter<P, ?> propertyAdapter;

	public AbstractAdapterController( AbstractPropertyAdapter<P, ?> propertyAdapter )
	{
		super();
		this.propertyAdapter = propertyAdapter;
		this.initializeComponents();
		this.setPropertyAdapter( this.propertyAdapter );
	}

	@Override
	public Class<?> getPropertyType()
	{
		return this.propertyAdapter.getPropertyType();
	}

	protected abstract void setValueOnUI( P value );

	protected void setValueOnData( P value )
	{
		this.propertyAdapter.setValue( value );
	}

	@Override
	protected void internalRefresh() {
		super.internalRefresh();
		this.updateUIFromNewAdapter();
	}

	protected void updateUIFromNewAdapter()
	{
		if( this.propertyAdapter != null )
		{
			this.setValueOnUI( this.propertyAdapter.getValue() );
		}
		else
		{
			this.setValueOnUI( null );
		}
	}

	protected void initializeComponents()
	{
	}

	@Override
	public Panel getPanel()
	{
		return this;
	}

	@Override
	public AbstractPropertyAdapter<P, ?> getPropertyAdapter()
	{
		return this.propertyAdapter;
	}

	@Override
	public void setPropertyAdapter( AbstractPropertyAdapter<P, ?> propertyAdapter )
	{
		if( this.propertyAdapter != null )
		{
			this.propertyAdapter.removeValueChangeObserver( this.valueChangeObserver );
		}
		this.propertyAdapter = propertyAdapter;
		this.refreshLater();
		if( this.propertyAdapter != null )
		{
			this.propertyAdapter.addAndInvokeValueChangeObserver( this.valueChangeObserver );
		}
		else
		{
			setValueOnUI( null );
		}

	}

}
