/*******************************************************************************
 * Copyright (c) 2008, 2015, Washington University in St. Louis.
 * All rights reserved.
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
 * 3. Products derived from the software may not be called "Looking Glass", nor
 *    may "Looking Glass" appear in their name, without prior written permission
 *    of Washington University in St. Louis.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software
 *    developed by Washington University in St. Louis"
 *
 * 5. The gallery of art assets and animations provided with this software is
 *    contributed by Electronic Arts Inc. and may be used for personal,
 *    non-commercial, and academic use only. Redistributions of any program
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in
 *    The Alice 3.0 Art Gallery License.
 *
 * DISCLAIMER:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.  ANY AND ALL
 * EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A PARTICULAR PURPOSE,
 * TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHORS,
 * COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package org.lgna.story.implementation.tempwalkandtouch;

import org.lgna.story.ArmSwing;
import org.lgna.story.Bounce;
import org.lgna.story.EmployeesOnly;
import org.lgna.story.Move;
import org.lgna.story.OrientToUpright;
import org.lgna.story.SBiped;
import org.lgna.story.SCamera;
import org.lgna.story.StrideLength;
import org.lgna.story.implementation.BipedImp;
import org.lgna.story.implementation.CameraImp;
import org.lgna.story.implementation.SymmetricPerspectiveCameraImp;

import edu.cmu.cs.dennisc.animation.AnimationObserver;
import edu.cmu.cs.dennisc.math.Angle;
import edu.cmu.cs.dennisc.math.AxisAlignedBox;
import edu.cmu.cs.dennisc.math.ForwardAndUpGuide;
import edu.cmu.cs.dennisc.math.OrthogonalMatrix3x3;
import edu.cmu.cs.dennisc.math.UnitQuaternion;

public class WalkOffscreenAnimation extends AbstractWalkAnimation {
	double stepLength = -1.0;
	double numberOfSteps = -1.0;
	double turnLength = 0.25;

	private boolean firstOver1 = true;
	double timePerStep = -1.0;
	double currentPos = 0.0;

	UnitQuaternion m_quat0;
	UnitQuaternion m_quat1;

	protected double distanceToMove = 0.0;
	protected SCamera camera = null;
	protected ExitDirection exitDirection = ExitDirection.RIGHT;

	public WalkOffscreenAnimation( SBiped subject, ExitDirection exitDirection, double stepSpeed, StrideLength strideLength, Bounce bounce, ArmSwing armSwing ) {
		super( subject, stepSpeed, strideLength, bounce, armSwing );
		this.exitDirection = exitDirection;
	}

	@Override
	public void prologue() {
		super.prologue();
		findCamera();

		stepLength = this.getStepLength();

		BipedImp subjectImp = EmployeesOnly.getImplementation( subject );
		CameraImp cameraImp = EmployeesOnly.getImplementation( camera );

		m_quat0 = subjectImp.getTransformation( cameraImp ).orientation.createUnitQuaternion();
		//				subjectImp.getOrientationAsAxes(camera).createUnitQuaternion();
		m_quat1 = OrthogonalMatrix3x3.createIdentity().createUnitQuaternion();

		if( exitDirection.equals( ExitDirection.LEFT ) ) {
			m_quat1.setValue( new ForwardAndUpGuide( new edu.cmu.cs.dennisc.math.Vector3( -1, 0, 0 ), new edu.cmu.cs.dennisc.math.Vector3( 0, 1, 0 ) ) );
		} else {
			m_quat1.setValue( new ForwardAndUpGuide( new edu.cmu.cs.dennisc.math.Vector3( 1, 0, 0 ), new edu.cmu.cs.dennisc.math.Vector3( 0, 1, 0 ) ) );
		}

	}

	@Override
	protected double update( double deltaSincePrologue, double deltaSinceLastUpdate, AnimationObserver observer ) {
		double timeRemaining = this.getTimeRemaining( deltaSincePrologue );

		BipedImp subjectImp = EmployeesOnly.getImplementation( subject );
		CameraImp cameraImp = EmployeesOnly.getImplementation( camera );

		if( deltaSincePrologue <= turnLength ) {
			double portion = deltaSincePrologue / turnLength;

			UnitQuaternion quatPortion = UnitQuaternion.createInterpolation( m_quat0, m_quat1, portion );

			subjectImp.setOrientationOnly( cameraImp, quatPortion.createOrthogonalMatrix3x3() );
			//			subject.setOrientationRightNow( quatPortion.createOrthogonalMatrix3x3(), camera );
			//			subject.orientToUprightRightNow(AsSeenBy.SCENE);
			subject.orientToUpright( OrientToUpright.duration( 0 ) );

		} else {
			if( firstOver1 ) {

				UnitQuaternion quatPortion = UnitQuaternion.createInterpolation( m_quat0, m_quat1, 1.0 );

				subjectImp.setOrientationOnly( cameraImp, quatPortion.createOrthogonalMatrix3x3() );
				//				subject.setOrientationRightNow( quatPortion.createOrthogonalMatrix3x3(), camera );
				subject.orientToUpright( OrientToUpright.duration( 0 ) );
				//				subject.orientToUprightRightNow(AsSeenBy.SCENE);
				firstOver1 = false;
			}

			timePerStep = 1.0 / stepSpeed;

			int stepNumber = (int)java.lang.Math.ceil( ( deltaSincePrologue - turnLength ) * ( 1.0 / timePerStep ) ) - 1;
			if( stepNumber == -1 ) {
				stepNumber = 0;
			}
			if( stepNumber == numberOfSteps ) {
				stepNumber -= 1;
			}
			double portionOfStep = ( deltaSincePrologue - turnLength - ( stepNumber * timePerStep ) ) / timePerStep;

			if( portionOfStep > 1.0 ) {
				portionOfStep = 1.0;
			}

			boolean lastStep = false;
			if( stepNumber == ( numberOfSteps - 1 ) ) {
				lastStep = true;
			}

			if( ( stepNumber % 2 ) == 0 ) {
				this.stepRight( portionOfStep, lastStep );
			} else {
				this.stepLeft( portionOfStep, lastStep );
			}

			double targetDistance = stepLength * portionOfStep;

			subject.move( org.lgna.story.MoveDirection.FORWARD, ( targetDistance + ( ( stepNumber ) * stepLength ) ) - currentPos, Move.duration( 0 ) );
			currentPos += ( ( targetDistance + ( ( stepNumber ) * stepLength ) ) - currentPos );

		}

		return timeRemaining;

	}

	protected void findCamera() {
		if( camera == null ) {
			BipedImp subjectImp = EmployeesOnly.getImplementation( subject );
			camera = (SCamera)subjectImp.getScene().findFirstCamera().getAbstraction();
			//			subjectImp.getScene().findFirstMatch( org.alice.apis.moveandturn.SymmetricPerspectiveCamera.class );
		}
	}

	protected boolean cameraSeesPoint( double x, double z, double horizAngle ) {
		double pointAngleToCamera = Math.abs( Math.asin( x / -z ) );

		if( pointAngleToCamera > ( horizAngle / 2.0 ) ) {
			return false;
		} else {
			return true;
		}
	}

	public double getTimeRemaining( double deltaSincePrologue ) {

		BipedImp subjectImp = EmployeesOnly.getImplementation( subject );
		SymmetricPerspectiveCameraImp cameraImp = EmployeesOnly.getImplementation( camera );

		Angle horizViewAngle = cameraImp.getScene().getProgram().getOnscreenRenderTarget().getActualHorizontalViewingAngle( cameraImp.getSgCamera() );

		AxisAlignedBox subjectBoundingBox = EmployeesOnly.getImplementation( subject ).getAxisAlignedMinimumBoundingBox( EmployeesOnly.getImplementation( camera ) );

		//		PrintUtilities.println();

		// check xMin, zMin
		if( cameraSeesPoint( subjectBoundingBox.getXMinimum(), subjectBoundingBox.getZMinimum(), horizViewAngle.getAsRadians() ) ) {
			return ( 1.0 / stepSpeed ); // we need at least one more step
		}
		if( cameraSeesPoint( subjectBoundingBox.getXMinimum(), subjectBoundingBox.getZMaximum(), horizViewAngle.getAsRadians() ) ) {
			return ( 1.0 / stepSpeed ); // we need at least one more step
		}
		if( cameraSeesPoint( subjectBoundingBox.getXMaximum(), subjectBoundingBox.getZMinimum(), horizViewAngle.getAsRadians() ) ) {
			return ( 1.0 / stepSpeed ); // we need at least one more step
		}
		if( cameraSeesPoint( subjectBoundingBox.getXMaximum(), subjectBoundingBox.getZMaximum(), horizViewAngle.getAsRadians() ) ) {
			return ( 1.0 / stepSpeed ); // we need at least one more step
		}

		return 0.0;

	}
}
