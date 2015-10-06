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

package org.alice.interact;

import edu.cmu.cs.dennisc.math.AffineMatrix4x4;
import edu.cmu.cs.dennisc.math.Point3;
import edu.cmu.cs.dennisc.math.UnitQuaternion;

public class QuaternionAndTranslation {
	public QuaternionAndTranslation() {
		this.quaternion = UnitQuaternion.createIdentity();
		this.translation = new Point3();
	}

	public QuaternionAndTranslation( UnitQuaternion quaternion, Point3 translation ) {
		this.quaternion = new UnitQuaternion( quaternion );
		this.translation = new Point3( translation );
	}

	public QuaternionAndTranslation( QuaternionAndTranslation other ) {
		this.quaternion = new UnitQuaternion( other.quaternion );
		this.translation = new Point3( other.translation );
	}

	public QuaternionAndTranslation( AffineMatrix4x4 matrix ) {
		this.translation = new Point3( matrix.translation );
		this.quaternion = matrix.orientation.createUnitQuaternion();
	}

	public void setToInterpolation( QuaternionAndTranslation a, QuaternionAndTranslation b, double portion ) {
		this.quaternion.setToInterpolation( a.quaternion, b.quaternion, portion );
		this.translation.setToInterpolation( a.translation, b.translation, portion );
	}

	public AffineMatrix4x4 getAffineMatrix() {
		AffineMatrix4x4 toReturn = new AffineMatrix4x4();
		toReturn.translation.set( this.translation );
		toReturn.orientation.setValue( this.quaternion );
		toReturn.orientation.normalizeColumns();
		return toReturn;
	}

	public UnitQuaternion getQuaternion() {
		return this.quaternion;
	}

	public Point3 getTranslation() {
		return this.translation;
	}

	private final UnitQuaternion quaternion;
	private final Point3 translation;
}