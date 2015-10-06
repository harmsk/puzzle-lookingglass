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
package org.alice.ide.croquet.models.help;

/**
 * @author Dennis Cosgrove
 */
public class SearchForGraphicsDriversOperation extends org.alice.ide.browser.BrowserOperation {
	private static class SingletonHolder {
		private static SearchForGraphicsDriversOperation instance = new SearchForGraphicsDriversOperation();
	}

	public static SearchForGraphicsDriversOperation getInstance() {
		return SingletonHolder.instance;
	}

	private SearchForGraphicsDriversOperation() {
		super( java.util.UUID.fromString( "c0e0d8bf-3c9d-4b47-aeb0-1623de06a8ea" ) );
	}

	private static String getRendererSearchTerm( String renderer ) {
		if( renderer.toLowerCase( java.util.Locale.ENGLISH ).contains( "geforce" ) ) {
			return "GeForce";
		} else {
			return renderer;
		}
	}

	@Override
	protected java.net.URL getUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append( "http://www.google.com/search?q=+graphics+driver" );
		edu.cmu.cs.dennisc.system.graphics.ConformanceTestResults.SharedDetails sharedDetails = edu.cmu.cs.dennisc.system.graphics.ConformanceTestResults.SINGLETON.getSharedDetails();
		if( sharedDetails != null ) {
			String renderer = sharedDetails.getRenderer();
			if( renderer != null ) {
				renderer = getRendererSearchTerm( renderer );
				sb.append( "+" );
				sb.append( renderer.replaceAll( " ", "+" ) );
			}
		}
		String spec = sb.toString();
		this.setToolTipText( spec );
		try {
			return new java.net.URL( spec );
		} catch( java.net.MalformedURLException murle ) {
			throw new RuntimeException( spec, murle );
		}
	}
}
