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

package org.alice.stageide.sceneeditor.interact;

import java.awt.event.KeyEvent;

import javax.swing.SwingUtilities;

import org.alice.interact.InteractionGroup;
import org.alice.interact.ModifierMask;
import org.alice.interact.ModifierMask.ModifierKey;
import org.alice.interact.MovementDirection;
import org.alice.interact.MovementKey;
import org.alice.interact.MovementType;
import org.alice.interact.PickHint;
import org.alice.interact.condition.AndInputCondition;
import org.alice.interact.condition.DoubleClickedObjectCondition;
import org.alice.interact.condition.DragAndDropCondition;
import org.alice.interact.condition.InputCondition;
import org.alice.interact.condition.InvertedSelectedObjectCondition;
import org.alice.interact.condition.KeyPressCondition;
import org.alice.interact.condition.ManipulatorConditionSet;
import org.alice.interact.condition.MouseCondition;
import org.alice.interact.condition.MouseDragCondition;
import org.alice.interact.condition.MousePressCondition;
import org.alice.interact.condition.MouseWheelCondition;
import org.alice.interact.condition.MovementDescription;
import org.alice.interact.condition.PickCondition;
import org.alice.interact.condition.SelectedObjectCondition;
import org.alice.interact.event.ManipulationEvent;
import org.alice.interact.event.ManipulationEventCriteria;
import org.alice.interact.handle.HandleSet;
import org.alice.interact.handle.HandleStyle;
import org.alice.interact.handle.JointRotationRingHandle;
import org.alice.interact.handle.LinearScaleHandle;
import org.alice.interact.handle.LinearTranslateHandle;
import org.alice.interact.handle.ManipulationAxes;
import org.alice.interact.handle.RotationRingHandle;
import org.alice.interact.handle.StoodUpRotationRingHandle;
import org.alice.interact.manipulator.CameraMoveDragManipulator;
import org.alice.interact.manipulator.CameraOrbitDragManipulator;
import org.alice.interact.manipulator.CameraPanDragManipulator;
import org.alice.interact.manipulator.CameraRotateKeyManipulator;
import org.alice.interact.manipulator.CameraTiltDragManipulator;
import org.alice.interact.manipulator.CameraTranslateKeyManipulator;
import org.alice.interact.manipulator.ClickAdapterManipulator;
import org.alice.interact.manipulator.HandlelessObjectRotateDragManipulator;
import org.alice.interact.manipulator.LinearDragManipulator;
import org.alice.interact.manipulator.ManipulatorClickAdapter;
import org.alice.interact.manipulator.ObjectGlobalHandleDragManipulator;
import org.alice.interact.manipulator.ObjectRotateDragManipulator;
import org.alice.interact.manipulator.ObjectTranslateKeyManipulator;
import org.alice.interact.manipulator.ObjectUpDownDragManipulator;
import org.alice.interact.manipulator.OmniDirectionalDragManipulator;
import org.alice.interact.manipulator.SelectObjectDragManipulator;
import org.alice.interact.manipulator.TargetManipulator;
import org.alice.stageide.sceneeditor.interact.croquet.AbstractPredeterminedSetLocalTransformationActionOperation;
import org.alice.stageide.sceneeditor.interact.croquet.PredeterminedSetLocalTransformationActionOperation;
import org.alice.stageide.sceneeditor.interact.manipulators.CameraZoomMouseWheelManipulator;
import org.alice.stageide.sceneeditor.interact.manipulators.CopyObjectDragManipulator;
import org.alice.stageide.sceneeditor.interact.manipulators.GetAGoodLookAtManipulator;
import org.alice.stageide.sceneeditor.interact.manipulators.OmniDirectionalBoundingBoxManipulator;
import org.alice.stageide.sceneeditor.interact.manipulators.ResizeDragManipulator;
import org.alice.stageide.sceneeditor.interact.manipulators.ScaleDragManipulator;

import edu.cmu.cs.dennisc.color.Color4f;
import edu.cmu.cs.dennisc.math.AffineMatrix4x4;

/**
 * @author David Culyba
 */
public class GlobalDragAdapter extends org.alice.stageide.sceneeditor.interact.CroquetSupportingDragAdapter {

	//Used to lock down the scene editor so only selection is available as an interaction (moving objects, moving the camera and whatnot are all disabled)
	private boolean enableSelectionOnlyMode = true;

	TargetManipulator dropTargetManipulator;

	private final org.alice.stageide.sceneeditor.StorytellingSceneEditor sceneEditor;

	public GlobalDragAdapter( org.alice.stageide.sceneeditor.StorytellingSceneEditor sceneEditor ) {
		this.sceneEditor = sceneEditor;
		this.setUpControls();
	}

	@Override
	public boolean hasSceneEditor() {
		return this.sceneEditor != null;
	}

	//<lg>
	public void setSelectionOnlyMode( boolean enableSelectionOnlyMode ) {
		this.enableSelectionOnlyMode = enableSelectionOnlyMode;

		if( this.enableSelectionOnlyMode ) {
			getHandleStyleState().setValueTransactionlessly( HandleStyle.SELECT );
			for( ManipulatorConditionSet manipulator : this.manipulators ) {
				if( !manipulator.getName().contentEquals( "Select Object" ) ) {
					manipulator.setEnabled( false );
				}
			}
		} else {
			for( ManipulatorConditionSet manipulator : this.manipulators ) {
				manipulator.setEnabled( true );
			}
			for( InteractionGroup interactionGroups : this.mapHandleStyleToInteractionGroup.values() ) {
				interactionGroups.enabledManipulators( false );
			}
			getHandleStyleState().setValueTransactionlessly( HandleStyle.DEFAULT );
		}
	}

	public boolean isSelectionOnlyMode() {
		return this.enableSelectionOnlyMode;
	}

	//</lg>

	private void setUpControls() {
		MovementKey[] movementKeys = {
				//Forward
				new MovementKey( KeyEvent.VK_UP, new MovementDescription( MovementDirection.FORWARD ) ),
				//				new MovementKey(KeyEvent.VK_NUMPAD8, new MovementDescription(MovementDirection.FORWARD)),
				//				new MovementKey(KeyEvent.VK_W, new MovementDescription(MovementDirection.FORWARD)),
				//Backward
				new MovementKey( KeyEvent.VK_DOWN, new MovementDescription( MovementDirection.BACKWARD ) ),
				//				new MovementKey(KeyEvent.VK_NUMPAD2, new MovementDescription(MovementDirection.BACKWARD)),
				//				new MovementKey(KeyEvent.VK_S, new MovementDescription(MovementDirection.BACKWARD)),
				//Left
				new MovementKey( KeyEvent.VK_LEFT, new MovementDescription( MovementDirection.LEFT ) ),
				//				new MovementKey(KeyEvent.VK_NUMPAD4, new MovementDescription(MovementDirection.LEFT)),
				//				new MovementKey(KeyEvent.VK_A, new MovementDescription(MovementDirection.LEFT)),
				//Right
				new MovementKey( KeyEvent.VK_RIGHT, new MovementDescription( MovementDirection.RIGHT ) ),
				//				new MovementKey(KeyEvent.VK_NUMPAD6, new MovementDescription(MovementDirection.RIGHT)),
				//				new MovementKey(KeyEvent.VK_D,  new MovementDescription(MovementDirection.RIGHT)),
				//Up
				new MovementKey( KeyEvent.VK_PAGE_UP, new MovementDescription( MovementDirection.UP, MovementType.LOCAL ), .5d ),
				//Down
				new MovementKey( KeyEvent.VK_PAGE_DOWN, new MovementDescription( MovementDirection.DOWN, MovementType.LOCAL ), .5d ),
				//Up Left
				//				new MovementKey(KeyEvent.VK_NUMPAD7, new Point3(-1, 0, -1)),
				//				//Up Right
				//				new MovementKey(KeyEvent.VK_NUMPAD9, new Point3(1, 0, -1)),
				//				//Back Left
				//				new MovementKey(KeyEvent.VK_NUMPAD1, new Point3(-1, 0, 1)),
				//				//Back Right
				//				new MovementKey(KeyEvent.VK_NUMPAD3, new Point3(1, 0, 1)),
		};

		MovementKey[] zoomKeys = {
				//Zoom out
				new MovementKey( KeyEvent.VK_MINUS, new MovementDescription( MovementDirection.BACKWARD, MovementType.LOCAL ) ),
				new MovementKey( KeyEvent.VK_SUBTRACT, new MovementDescription( MovementDirection.BACKWARD, MovementType.LOCAL ) ),
				//Zoom in
				new MovementKey( KeyEvent.VK_EQUALS, new MovementDescription( MovementDirection.FORWARD, MovementType.LOCAL ) ),
				new MovementKey( KeyEvent.VK_ADD, new MovementDescription( MovementDirection.FORWARD, MovementType.LOCAL ) ),
		};

		MovementKey[] turnKeys = {
				//Left
				new MovementKey( KeyEvent.VK_OPEN_BRACKET, new MovementDescription( MovementDirection.LEFT, MovementType.LOCAL ), 2.0d ),
				//Right
				new MovementKey( KeyEvent.VK_CLOSE_BRACKET, new MovementDescription( MovementDirection.RIGHT, MovementType.LOCAL ), -2.0d ),
		};

		ModifierMask noModifiers = new ModifierMask( ModifierMask.NO_MODIFIERS_DOWN );

		CameraTranslateKeyManipulator cameraTranslateManip = new CameraTranslateKeyManipulator( movementKeys );
		cameraTranslateManip.addKeys( zoomKeys );
		ManipulatorConditionSet cameraTranslate = new ManipulatorConditionSet( cameraTranslateManip );
		for( MovementKey movementKey : movementKeys ) {
			AndInputCondition keyAndNotSelected = new AndInputCondition( new KeyPressCondition( movementKey.keyValue ), new SelectedObjectCondition( PickHint.getNonInteractiveHint(), InvertedSelectedObjectCondition.ObjectSwitchBehavior.IGNORE_SWITCH ) );
			cameraTranslate.addCondition( keyAndNotSelected );
		}
		for( MovementKey zoomKey : zoomKeys ) {
			AndInputCondition keyAndNotSelected = new AndInputCondition( new KeyPressCondition( zoomKey.keyValue, noModifiers ), new SelectedObjectCondition( PickHint.getNonInteractiveHint(), InvertedSelectedObjectCondition.ObjectSwitchBehavior.IGNORE_SWITCH ) );
			cameraTranslate.addCondition( keyAndNotSelected );
		}
		//	this.addManipulator( cameraTranslate );

		ManipulatorConditionSet objectTranslate = new ManipulatorConditionSet( new ObjectTranslateKeyManipulator( movementKeys ) );
		for( MovementKey movementKey : movementKeys ) {
			AndInputCondition keyAndSelected = new AndInputCondition( new KeyPressCondition( movementKey.keyValue ), new SelectedObjectCondition( PickHint.PickType.MOVEABLE.pickHint() ) );
			objectTranslate.addCondition( keyAndSelected );
		}
		this.addManipulatorConditionSet( objectTranslate );

		ManipulatorConditionSet cameraRotate = new ManipulatorConditionSet( new CameraRotateKeyManipulator( turnKeys ) );
		for( MovementKey turnKey : turnKeys ) {
			AndInputCondition keyAndNotSelected = new AndInputCondition( new KeyPressCondition( turnKey.keyValue ), new SelectedObjectCondition( PickHint.getNonInteractiveHint(), InvertedSelectedObjectCondition.ObjectSwitchBehavior.IGNORE_SWITCH ) );
			cameraRotate.addCondition( keyAndNotSelected );
		}
		this.addManipulatorConditionSet( cameraRotate );

		//Camera mouse control
		MouseDragCondition leftAndNoModifiers = new MouseDragCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.getNonInteractiveHint() ), new ModifierMask( ModifierMask.NO_MODIFIERS_DOWN ) );
		MouseDragCondition leftAndShift = new MouseDragCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.getNonInteractiveHint() ), new ModifierMask( ModifierMask.JUST_SHIFT ) );
		MouseDragCondition leftAndControl = new MouseDragCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.getNonInteractiveHint() ), new ModifierMask( ModifierMask.JUST_CONTROL ) );
		MouseDragCondition middleMouseAndAnything = new MouseDragCondition( java.awt.event.MouseEvent.BUTTON2, new PickCondition( PickHint.getAnythingHint() ) );
		MouseDragCondition rightMouseAndNonInteractive = new MouseDragCondition( java.awt.event.MouseEvent.BUTTON3, new PickCondition( PickHint.getNonInteractiveHint() ) );

		ManipulatorConditionSet cameraOrbit = new ManipulatorConditionSet( new CameraOrbitDragManipulator() );
		//		cameraOrbit.addCondition(rightMouseAndNonInteractive);
		cameraOrbit.addCondition( middleMouseAndAnything );
		this.addManipulatorConditionSet( cameraOrbit );

		ManipulatorConditionSet cameraTilt = new ManipulatorConditionSet( new CameraTiltDragManipulator() );
		cameraTilt.addCondition( rightMouseAndNonInteractive );
		cameraTilt.addCondition( leftAndControl );
		this.addManipulatorConditionSet( cameraTilt );

		ManipulatorConditionSet cameraMouseTranslate = new ManipulatorConditionSet( new CameraMoveDragManipulator() );
		cameraMouseTranslate.addCondition( leftAndNoModifiers );
		this.addManipulatorConditionSet( cameraMouseTranslate );

		ManipulatorConditionSet cameraMousePan = new ManipulatorConditionSet( new CameraPanDragManipulator() );
		cameraMousePan.addCondition( leftAndShift );
		this.addManipulatorConditionSet( cameraMousePan );

		//Object Manipulation
		OmniDirectionalBoundingBoxManipulator boundingBoxManipulator = new OmniDirectionalBoundingBoxManipulator();
		this.dropTargetManipulator = boundingBoxManipulator;
		ManipulatorConditionSet dragFromGallery = new ManipulatorConditionSet( boundingBoxManipulator, "Bounding Box Translate" );
		dragFromGallery.addCondition( new DragAndDropCondition() );
		this.addManipulatorConditionSet( dragFromGallery );

		MouseDragCondition leftClickMoveableObjects = new MouseDragCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.PickType.MOVEABLE.pickHint() ), new ModifierMask( ModifierMask.NO_MODIFIERS_DOWN ) );
		MouseDragCondition leftClickTurnableObjects = new MouseDragCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.PickType.TURNABLE.pickHint() ), new ModifierMask( ModifierMask.NO_MODIFIERS_DOWN ) );
		MouseDragCondition leftClickResizableObjects = new MouseDragCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.PickType.RESIZABLE.pickHint() ), new ModifierMask( ModifierMask.NO_MODIFIERS_DOWN ) );

		ManipulatorConditionSet leftClickMouseTranslateObject = new ManipulatorConditionSet( new OmniDirectionalDragManipulator(), "Mouse Translate" );
		leftClickMouseTranslateObject.addCondition( leftClickMoveableObjects );
		this.addManipulatorConditionSet( leftClickMouseTranslateObject );

		ManipulatorConditionSet leftClickMouseRotateObjectLeftRight = new ManipulatorConditionSet( new HandlelessObjectRotateDragManipulator( MovementDirection.UP ) );
		leftClickMouseRotateObjectLeftRight.addCondition( leftClickTurnableObjects );
		//This manipulation is used only when the "rotation" interaction group is selected. Disabled by default.
		leftClickMouseRotateObjectLeftRight.setEnabled( false );
		this.addManipulatorConditionSet( leftClickMouseRotateObjectLeftRight );

		ManipulatorConditionSet leftClickMouseResizeObject = new ManipulatorConditionSet( new ResizeDragManipulator( edu.cmu.cs.dennisc.scenegraph.scale.Resizer.UNIFORM, edu.cmu.cs.dennisc.scenegraph.scale.Resizer.XY_PLANE, edu.cmu.cs.dennisc.scenegraph.scale.Resizer.XZ_PLANE, edu.cmu.cs.dennisc.scenegraph.scale.Resizer.YZ_PLANE ) );
		leftClickMouseResizeObject.addCondition( leftClickResizableObjects );
		//This manipulation is used only when the "resize" interaction group is selected. Disabled by default.
		leftClickMouseResizeObject.setEnabled( false );
		this.addManipulatorConditionSet( leftClickMouseResizeObject );

		ManipulatorConditionSet mouseUpDownTranslateObject = new ManipulatorConditionSet( new ObjectUpDownDragManipulator() );
		MouseDragCondition moveableObjectWithShift = new MouseDragCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.PickType.MOVEABLE.pickHint() ), new ModifierMask( ModifierKey.SHIFT ) );
		mouseUpDownTranslateObject.addCondition( moveableObjectWithShift );
		this.addManipulatorConditionSet( mouseUpDownTranslateObject );

		ManipulatorConditionSet mouseRotateObjectLeftRight = new ManipulatorConditionSet( new HandlelessObjectRotateDragManipulator( MovementDirection.UP ) );
		MouseDragCondition moveableObjectWithCtrl = new MouseDragCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.PickType.TURNABLE.pickHint() ), new ModifierMask( ModifierKey.CONTROL ) );
		mouseRotateObjectLeftRight.addCondition( moveableObjectWithCtrl );
		this.addManipulatorConditionSet( mouseRotateObjectLeftRight );

		ManipulatorConditionSet mouseCopyAndMoveObject = new ManipulatorConditionSet( new CopyObjectDragManipulator() );
		MouseDragCondition copyObjectWithAlt = new MouseDragCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.PickType.MOVEABLE.pickHint() ), new ModifierMask( ModifierKey.ALT ) );
		mouseCopyAndMoveObject.addCondition( copyObjectWithAlt );
		this.addManipulatorConditionSet( mouseCopyAndMoveObject );

		ManipulatorConditionSet mouseHandleDrag = new ManipulatorConditionSet( new ObjectGlobalHandleDragManipulator() );
		MouseDragCondition handleObjectCondition = new MouseDragCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.PickType.THREE_D_HANDLE.pickHint() ), new ModifierMask( ModifierMask.NO_MODIFIERS_DOWN ) );
		MouseCondition handleObjectClickCondition = new MouseCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.PickType.TWO_D_HANDLE.pickHint() ), new ModifierMask( ModifierMask.NO_MODIFIERS_DOWN ) );
		mouseHandleDrag.addCondition( handleObjectCondition );
		mouseHandleDrag.addCondition( handleObjectClickCondition );
		this.addManipulatorConditionSet( mouseHandleDrag );

		//		ManipulatorConditionSet mouseHandleClick = new ManipulatorConditionSet( new ObjectGlobalHandleDragManipulator() );
		//		MousePressCondition handleObjectClickCondition = new MousePressCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.TWO_D_HANDLES));
		//		System.out.println("Looking for condition: "+handleObjectCondition.hashCode()+", in set "+mouseHandleClick.hashCode());
		//		mouseHandleClick.addCondition( handleObjectClickCondition );
		//		this.addManipulator( mouseHandleClick );

		ManipulatorConditionSet selectObject = new ManipulatorConditionSet( new SelectObjectDragManipulator( this ), "Select Object" );
		selectObject.addCondition( new MousePressCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.PickType.SELECTABLE.pickHint() ) ) );
		this.addManipulatorConditionSet( selectObject );

		ManipulatorConditionSet getAGoodLookAtObject = new ManipulatorConditionSet( new GetAGoodLookAtManipulator() );
		getAGoodLookAtObject.addCondition( new DoubleClickedObjectCondition( java.awt.event.MouseEvent.BUTTON1, new PickCondition( PickHint.PickType.VIEWABLE.pickHint() ), new ModifierMask( ModifierMask.JUST_CONTROL ) ) );
		this.addManipulatorConditionSet( getAGoodLookAtObject );

		ManipulatorConditionSet mouseWheelCameraZoom = new ManipulatorConditionSet( new CameraZoomMouseWheelManipulator() );
		MouseWheelCondition mouseWheelCondition = new MouseWheelCondition( new ModifierMask( ModifierMask.NO_MODIFIERS_DOWN ) );
		mouseWheelCameraZoom.addCondition( mouseWheelCondition );
		this.addManipulatorConditionSet( mouseWheelCameraZoom );

		//todo: move down?
		for( ManipulatorConditionSet manipulatorConditionSet : this.getManipulatorConditionSets() ) {
			manipulatorConditionSet.getManipulator().setDragAdapter( this );
		}

		ManipulationAxes handleAxis = new org.alice.interact.handle.ManipulationAxes();

		handleAxis.addToGroup( HandleSet.HandleGroup.VISUALIZATION );

		//		handleAxis.addToSet( HandleSet.DEFAULT_INTERACTION );
		//		handleAxis.addToSet( HandleSet.ROTATION_INTERACTION );
		//		handleAxis.addToSet( HandleSet.JOINT_ROTATION_INTERACTION );
		//		handleAxis.addToSet( HandleSet.TRANSLATION_INTERACTION );

		handleAxis.addCondition( new ManipulationEventCriteria( ManipulationEvent.EventType.Rotate, null, PickHint.getAnythingHint() ) );
		handleAxis.addCondition( new ManipulationEventCriteria( ManipulationEvent.EventType.Translate, null, PickHint.getAnythingHint() ) );
		this.addManipulationListener( handleAxis );
		handleAxis.setDragAdapterAndAddHandle( this );
		handleAxis.setName( "handleAxis" );

		StoodUpRotationRingHandle rotateAboutYAxisStoodUp = new StoodUpRotationRingHandle( MovementDirection.UP, RotationRingHandle.HandlePosition.BOTTOM );
		rotateAboutYAxisStoodUp.setManipulation( new ObjectRotateDragManipulator() {
			@Override
			protected HandleSet getHandleSetToEnable() {
				return new HandleSet( HandleSet.HandleGroup.Y_AXIS, HandleSet.HandleGroup.VISUALIZATION, HandleSet.HandleGroup.STOOD_UP_ROTATION );
			}
		} );
		//			rotateAboutYAxisStoodUp.addToSet( HandleSet.ROTATION_INTERACTION );
		rotateAboutYAxisStoodUp.addToSet( HandleSet.DEFAULT_INTERACTION );
		rotateAboutYAxisStoodUp.addToGroups( HandleSet.HandleGroup.DEFAULT, HandleSet.HandleGroup.Y_AXIS, HandleSet.HandleGroup.VISUALIZATION, HandleSet.HandleGroup.STOOD_UP_ROTATION );
		rotateAboutYAxisStoodUp.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Rotate,
				new MovementDescription( MovementDirection.UP, MovementType.STOOD_UP ),
				PickHint.PickType.TURNABLE.pickHint() ) );
		rotateAboutYAxisStoodUp.addCondition(
				new ManipulationEventCriteria( ManipulationEvent.EventType.Rotate,
						new MovementDescription( MovementDirection.DOWN, MovementType.STOOD_UP ),
						PickHint.PickType.TURNABLE.pickHint() ) );
		this.addManipulationListener( rotateAboutYAxisStoodUp );
		rotateAboutYAxisStoodUp.setDragAdapterAndAddHandle( this );
		rotateAboutYAxisStoodUp.setName( "rotateAboutYAxisStoodUp" );

		RotationRingHandle rotateAboutYAxis = new RotationRingHandle( MovementDirection.UP, Color4f.GREEN );
		rotateAboutYAxis.setManipulation( new ObjectRotateDragManipulator() );
		rotateAboutYAxis.addToSet( HandleSet.ROTATION_INTERACTION );
		rotateAboutYAxis.addToGroups( HandleSet.HandleGroup.Y_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		rotateAboutYAxis.setDragAdapterAndAddHandle( this );
		rotateAboutYAxis.setName( "rotateAboutYAxis" );

		RotationRingHandle rotateAboutXAxis = new RotationRingHandle( MovementDirection.LEFT, Color4f.RED );
		rotateAboutXAxis.setManipulation( new ObjectRotateDragManipulator() );
		rotateAboutXAxis.addToSet( HandleSet.ROTATION_INTERACTION );
		rotateAboutXAxis.addToGroups( HandleSet.HandleGroup.X_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		rotateAboutXAxis.setDragAdapterAndAddHandle( this );
		rotateAboutXAxis.setName( "rotateAboutXAxis" );

		RotationRingHandle rotateAboutZAxis = new RotationRingHandle( MovementDirection.BACKWARD, Color4f.BLUE );
		rotateAboutZAxis.setManipulation( new ObjectRotateDragManipulator() );
		rotateAboutZAxis.addToSet( HandleSet.ROTATION_INTERACTION );
		rotateAboutZAxis.addToGroups( HandleSet.HandleGroup.Z_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		rotateAboutZAxis.setDragAdapterAndAddHandle( this );
		rotateAboutZAxis.setName( "rotateAboutZAxis" );

		JointRotationRingHandle rotateJointAboutZAxis = new org.alice.interact.handle.JointRotationRingHandle( MovementDirection.BACKWARD, Color4f.BLUE );
		rotateJointAboutZAxis.setManipulation( new ObjectRotateDragManipulator() );
		rotateJointAboutZAxis.addToSet( HandleSet.JOINT_ROTATION_INTERACTION );
		rotateJointAboutZAxis.addToGroups( HandleSet.HandleGroup.Z_AXIS, HandleSet.HandleGroup.VISUALIZATION, HandleSet.HandleGroup.JOINT );
		rotateJointAboutZAxis.setDragAdapterAndAddHandle( this );
		rotateJointAboutZAxis.setName( "rotateJointAboutZAxis" );

		JointRotationRingHandle rotateJointAboutYAxis = new org.alice.interact.handle.JointRotationRingHandle( MovementDirection.UP, Color4f.GREEN );
		rotateJointAboutYAxis.setManipulation( new ObjectRotateDragManipulator() );
		rotateJointAboutYAxis.addToSet( HandleSet.JOINT_ROTATION_INTERACTION );
		rotateJointAboutYAxis.addToGroups( HandleSet.HandleGroup.Y_AXIS, HandleSet.HandleGroup.VISUALIZATION, HandleSet.HandleGroup.JOINT );
		rotateJointAboutYAxis.setDragAdapterAndAddHandle( this );
		rotateJointAboutYAxis.setName( "rotateJointAboutYAxis" );

		JointRotationRingHandle rotateJointAboutXAxis = new org.alice.interact.handle.JointRotationRingHandle( MovementDirection.LEFT, Color4f.RED );
		rotateJointAboutXAxis.setManipulation( new ObjectRotateDragManipulator() );
		rotateJointAboutXAxis.addToSet( HandleSet.JOINT_ROTATION_INTERACTION );
		rotateJointAboutXAxis.addToGroups( HandleSet.HandleGroup.X_AXIS, HandleSet.HandleGroup.VISUALIZATION, HandleSet.HandleGroup.JOINT );
		rotateJointAboutXAxis.setDragAdapterAndAddHandle( this );
		rotateJointAboutXAxis.setName( "rotateJointAboutXAxis" );

		LinearTranslateHandle translateUp = new LinearTranslateHandle( new MovementDescription( MovementDirection.UP, MovementType.ABSOLUTE ), Color4f.YELLOW );
		LinearTranslateHandle translateDown = new LinearTranslateHandle( new MovementDescription( MovementDirection.DOWN, MovementType.ABSOLUTE ), Color4f.YELLOW );
		translateUp.setManipulation( new LinearDragManipulator() );
		translateUp.addToGroups( HandleSet.HandleGroup.ABSOLUTE_TRANSLATION, HandleSet.HandleGroup.Y_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		translateDown.addToGroups( HandleSet.HandleGroup.ABSOLUTE_TRANSLATION, HandleSet.HandleGroup.Y_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		translateUp.addToGroup( HandleSet.HandleGroup.INTERACTION );
		translateUp.addToGroup( HandleSet.HandleGroup.VISUALIZATION );
		translateDown.addToGroup( HandleSet.HandleGroup.VISUALIZATION );
		//			translateDown.addCondition( new ManipulationEventCriteria(
		//					ManipulationEvent.EventType.Translate,
		//					new MovementDescription( MovementDirection.DOWN, MovementType.STOOD_UP ),
		//					PickHint.PickType.MOVEABLE.pickHint() ) );
		//			translateUp.addCondition( new ManipulationEventCriteria(
		//					ManipulationEvent.EventType.Translate,
		//					new MovementDescription( MovementDirection.UP, MovementType.STOOD_UP ),
		//					PickHint.PickType.MOVEABLE.pickHint() ) );
		translateDown.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Translate,
				new MovementDescription( MovementDirection.DOWN, MovementType.ABSOLUTE ),
				PickHint.PickType.MOVEABLE.pickHint() ) );
		translateUp.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Translate,
				new MovementDescription( MovementDirection.UP, MovementType.ABSOLUTE ),
				PickHint.PickType.MOVEABLE.pickHint() ) );
		this.addManipulationListener( translateUp );
		this.addManipulationListener( translateDown );
		translateDown.setDragAdapterAndAddHandle( this );
		translateUp.setDragAdapterAndAddHandle( this );
		translateDown.setName( "translateDown" );
		translateUp.setName( "translateUp" );

		LinearTranslateHandle translateXAxisRight = new LinearTranslateHandle( new MovementDescription( MovementDirection.RIGHT, MovementType.ABSOLUTE ), Color4f.YELLOW );
		LinearTranslateHandle translateXAxisLeft = new LinearTranslateHandle( new MovementDescription( MovementDirection.LEFT, MovementType.ABSOLUTE ), Color4f.YELLOW );
		translateXAxisLeft.setManipulation( new LinearDragManipulator() );
		//Add the left handle to the group to be shown by the system
		translateXAxisLeft.addToGroups( HandleSet.HandleGroup.ABSOLUTE_TRANSLATION, HandleSet.HandleGroup.X_AXIS, HandleSet.HandleGroup.X_AND_Z_AXIS );
		translateXAxisRight.addToGroups( HandleSet.HandleGroup.ABSOLUTE_TRANSLATION, HandleSet.HandleGroup.X_AXIS, HandleSet.HandleGroup.X_AND_Z_AXIS );
		translateXAxisLeft.addToGroup( HandleSet.HandleGroup.INTERACTION );
		translateXAxisLeft.addToGroup( HandleSet.HandleGroup.VISUALIZATION );
		translateXAxisRight.addToGroup( HandleSet.HandleGroup.VISUALIZATION );
		//			translateXAxisLeft.addCondition( new ManipulationEventCriteria(
		//					ManipulationEvent.EventType.Translate,
		//					new MovementDescription( MovementDirection.LEFT, MovementType.STOOD_UP ),
		//					PickHint.PickType.MOVEABLE.pickHint() ) );
		//			translateXAxisRight.addCondition( new ManipulationEventCriteria(
		//					ManipulationEvent.EventType.Translate,
		//					new MovementDescription( MovementDirection.RIGHT, MovementType.STOOD_UP ),
		//					PickHint.PickType.MOVEABLE.pickHint() ) );
		translateXAxisLeft.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Translate,
				new MovementDescription( MovementDirection.LEFT, MovementType.ABSOLUTE ),
				PickHint.PickType.MOVEABLE.pickHint() ) );
		translateXAxisRight.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Translate,
				new MovementDescription( MovementDirection.RIGHT, MovementType.ABSOLUTE ),
				PickHint.PickType.MOVEABLE.pickHint() ) );
		this.addManipulationListener( translateXAxisRight );
		this.addManipulationListener( translateXAxisLeft );
		translateXAxisRight.setDragAdapterAndAddHandle( this );
		translateXAxisLeft.setDragAdapterAndAddHandle( this );
		translateXAxisRight.setName( "translateXAxisRight" );
		translateXAxisLeft.setName( "translateXAxisLeft" );

		LinearTranslateHandle translateForward = new LinearTranslateHandle( new MovementDescription( MovementDirection.FORWARD, MovementType.ABSOLUTE ), Color4f.YELLOW );
		LinearTranslateHandle translateBackward = new LinearTranslateHandle( new MovementDescription( MovementDirection.BACKWARD, MovementType.ABSOLUTE ), Color4f.YELLOW );
		translateForward.setManipulation( new LinearDragManipulator() );
		translateForward.addToGroups( HandleSet.HandleGroup.ABSOLUTE_TRANSLATION, HandleSet.HandleGroup.Z_AXIS, HandleSet.HandleGroup.X_AND_Z_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		translateBackward.addToGroups( HandleSet.HandleGroup.ABSOLUTE_TRANSLATION, HandleSet.HandleGroup.Z_AXIS, HandleSet.HandleGroup.X_AND_Z_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		translateForward.addToGroup( HandleSet.HandleGroup.INTERACTION );
		translateForward.addToGroup( HandleSet.HandleGroup.VISUALIZATION );
		translateBackward.addToGroup( HandleSet.HandleGroup.VISUALIZATION );
		//			translateBackward.addCondition( new ManipulationEventCriteria(
		//					ManipulationEvent.EventType.Translate,
		//					new MovementDescription( MovementDirection.BACKWARD, MovementType.STOOD_UP ),
		//					PickHint.PickType.MOVEABLE.pickHint() ) );
		//			translateForward.addCondition( new ManipulationEventCriteria(
		//					ManipulationEvent.EventType.Translate,
		//					new MovementDescription( MovementDirection.FORWARD, MovementType.STOOD_UP ),
		//					PickHint.PickType.MOVEABLE.pickHint() ) );
		translateBackward.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Translate,
				new MovementDescription( MovementDirection.BACKWARD, MovementType.ABSOLUTE ),
				PickHint.PickType.MOVEABLE.pickHint() ) );
		translateForward.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Translate,
				new MovementDescription( MovementDirection.FORWARD, MovementType.ABSOLUTE ),
				PickHint.PickType.MOVEABLE.pickHint() ) );
		this.addManipulationListener( translateForward );
		this.addManipulationListener( translateBackward );
		translateForward.setDragAdapterAndAddHandle( this );
		translateBackward.setDragAdapterAndAddHandle( this );
		translateForward.setName( "translateForward" );
		translateBackward.setName( "translateBackward" );

		LinearScaleHandle scaleAxisUniform = LinearScaleHandle.createFromResizer( edu.cmu.cs.dennisc.scenegraph.scale.Resizer.UNIFORM );
		scaleAxisUniform.setManipulation( new ScaleDragManipulator() );
		scaleAxisUniform.addToSet( HandleSet.RESIZE_INTERACTION );
		scaleAxisUniform.addToGroups( HandleSet.HandleGroup.RESIZE_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		scaleAxisUniform.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Scale,
				scaleAxisUniform.getMovementDescription(),
				PickHint.PickType.RESIZABLE.pickHint() ) );
		scaleAxisUniform.setDragAdapterAndAddHandle( this );
		scaleAxisUniform.setName( "scaleAxisUniform" );

		LinearScaleHandle scaleAxisX = LinearScaleHandle.createFromResizer( edu.cmu.cs.dennisc.scenegraph.scale.Resizer.X_AXIS );
		scaleAxisX.setManipulation( new ScaleDragManipulator() );
		scaleAxisX.addToSet( HandleSet.RESIZE_INTERACTION );
		scaleAxisX.addToGroups( HandleSet.HandleGroup.X_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		scaleAxisX.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Scale,
				scaleAxisX.getMovementDescription(),
				PickHint.PickType.RESIZABLE.pickHint() ) );
		scaleAxisX.setDragAdapterAndAddHandle( this );
		scaleAxisX.setName( "scaleAxisX" );

		LinearScaleHandle scaleAxisY = LinearScaleHandle.createFromResizer( edu.cmu.cs.dennisc.scenegraph.scale.Resizer.Y_AXIS );
		scaleAxisY.setManipulation( new ScaleDragManipulator() );
		scaleAxisY.addToSet( HandleSet.RESIZE_INTERACTION );
		scaleAxisY.addToGroups( HandleSet.HandleGroup.Y_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		scaleAxisY.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Scale,
				scaleAxisY.getMovementDescription(),
				PickHint.PickType.RESIZABLE.pickHint() ) );
		scaleAxisY.setDragAdapterAndAddHandle( this );
		scaleAxisY.setName( "scaleAxisY" );

		LinearScaleHandle scaleAxisZ = LinearScaleHandle.createFromResizer( edu.cmu.cs.dennisc.scenegraph.scale.Resizer.Z_AXIS );
		scaleAxisZ.setManipulation( new ScaleDragManipulator() );
		scaleAxisZ.addToSet( HandleSet.RESIZE_INTERACTION );
		scaleAxisZ.addToGroups( HandleSet.HandleGroup.Z_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		scaleAxisZ.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Scale,
				scaleAxisZ.getMovementDescription(),
				PickHint.PickType.RESIZABLE.pickHint() ) );
		scaleAxisZ.setDragAdapterAndAddHandle( this );
		scaleAxisZ.setName( "scaleAxisZ" );

		LinearScaleHandle scaleAxisXY = LinearScaleHandle.createFromResizer( edu.cmu.cs.dennisc.scenegraph.scale.Resizer.XY_PLANE );
		scaleAxisXY.setManipulation( new ScaleDragManipulator() );
		scaleAxisXY.addToSet( HandleSet.RESIZE_INTERACTION );
		scaleAxisXY.addToGroups( HandleSet.HandleGroup.X_AND_Y_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		scaleAxisXY.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Scale,
				scaleAxisXY.getMovementDescription(),
				PickHint.PickType.RESIZABLE.pickHint() ) );
		scaleAxisXY.setDragAdapterAndAddHandle( this );
		scaleAxisXY.setName( "scaleAxisXY" );

		LinearScaleHandle scaleAxisXZ = LinearScaleHandle.createFromResizer( edu.cmu.cs.dennisc.scenegraph.scale.Resizer.XZ_PLANE );
		scaleAxisXZ.setManipulation( new ScaleDragManipulator() );
		scaleAxisXZ.addToSet( HandleSet.RESIZE_INTERACTION );
		scaleAxisXZ.addToGroups( HandleSet.HandleGroup.X_AND_Z_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		scaleAxisXZ.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Scale,
				scaleAxisXZ.getMovementDescription(),
				PickHint.PickType.RESIZABLE.pickHint() ) );
		scaleAxisXZ.setDragAdapterAndAddHandle( this );
		scaleAxisXZ.setName( "scaleAxisXZ" );

		LinearScaleHandle scaleAxisYZ = LinearScaleHandle.createFromResizer( edu.cmu.cs.dennisc.scenegraph.scale.Resizer.YZ_PLANE );
		scaleAxisYZ.setManipulation( new ScaleDragManipulator() );
		scaleAxisYZ.addToSet( HandleSet.RESIZE_INTERACTION );
		scaleAxisYZ.addToGroups( HandleSet.HandleGroup.Y_AND_Z_AXIS, HandleSet.HandleGroup.VISUALIZATION );
		scaleAxisYZ.addCondition( new ManipulationEventCriteria(
				ManipulationEvent.EventType.Scale,
				scaleAxisYZ.getMovementDescription(),
				PickHint.PickType.RESIZABLE.pickHint() ) );
		scaleAxisYZ.setDragAdapterAndAddHandle( this );
		scaleAxisYZ.setName( "scaleAxisYZ" );

		// <lg>
		//Selection visual handle
		org.alice.interact.handle.SelectionIndicator selectionIndicator = new org.alice.interact.handle.SelectionIndicator();
		selectionIndicator.addToGroup( HandleSet.HandleGroup.SELECTION );
		this.addManipulationListener( selectionIndicator );
		selectionIndicator.setDragAdapterAndAddHandle( this );

		InteractionGroup selectionOnly = new InteractionGroup( HandleSet.SELECTION_ONLY, null );
		this.mapHandleStyleToInteractionGroup.put( org.alice.interact.handle.HandleStyle.SELECT, selectionOnly );
		// </lg>

		if( this.sceneEditor != null ) {
			InteractionGroup defaultInteraction = new InteractionGroup( HandleSet.DEFAULT_INTERACTION, leftClickMouseTranslateObject, org.alice.interact.PickHint.PickType.MOVEABLE );
			InteractionGroup rotationInteraction = new InteractionGroup( HandleSet.ROTATION_INTERACTION, leftClickMouseRotateObjectLeftRight, org.alice.interact.PickHint.PickType.TURNABLE );
			InteractionGroup translationInteraction = new InteractionGroup( HandleSet.ABSOLUTE_TRANSLATION_INTERACTION, leftClickMouseTranslateObject, org.alice.interact.PickHint.PickType.MOVEABLE );
			InteractionGroup resizeInteraction = new InteractionGroup( HandleSet.RESIZE_INTERACTION, leftClickMouseResizeObject, org.alice.interact.PickHint.PickType.RESIZABLE );

			this.mapHandleStyleToInteractionGroup.put( org.alice.interact.handle.HandleStyle.DEFAULT, defaultInteraction );
			this.mapHandleStyleToInteractionGroup.put( org.alice.interact.handle.HandleStyle.ROTATION, rotationInteraction );
			this.mapHandleStyleToInteractionGroup.put( org.alice.interact.handle.HandleStyle.TRANSLATION, translationInteraction );
			this.mapHandleStyleToInteractionGroup.put( org.alice.interact.handle.HandleStyle.RESIZE, resizeInteraction );
			//			this.interactionSelectionState.addItem(defaultInteraction);
			//			this.interactionSelectionState.addItem(rotationInteraction);
			//			this.interactionSelectionState.addItem(translationInteraction);
			//			this.interactionSelectionState.addItem(resizeInteraction);
			//
			//			this.interactionSelectionState.setSelectedItem(defaultInteraction);

			getHandleStyleState().addAndInvokeNewSchoolValueListener( this.handleStyleListener );

			// <lg/> HACK! selection only mode doesn't seem to get initialized correctly
			// unless it's setup later. Somehow if you set this here, now (instead of later)
			// the initialization sequence doesn't quite finish correctly. This appears to be
			// due to how the observers get setup later and don't pick up this change. But
			// I don't have time to fix it. So this hack works for now.
			SwingUtilities.invokeLater( () -> {
				setSelectionOnlyMode( true );
			} );
		}

		edu.cmu.cs.dennisc.render.RenderCapabilities renderCapabilities = this.sceneEditor.getOnscreenRenderTarget().getActualCapabilities();
		if( renderCapabilities.getStencilBits() > 0 ) {
			edu.cmu.cs.dennisc.scenegraph.Silhouette sgSilhouette = new edu.cmu.cs.dennisc.scenegraph.Silhouette();
			//sgSilhouette.color.setValue( Color4f.YELLOW );
			//sgSilhouette.width.setValue( 1.5f );
			this.setSgSilhouette( sgSilhouette );
		}
	}

	public void addClickAdapter( ManipulatorClickAdapter clickAdapter, InputCondition... conditions ) {
		ManipulatorConditionSet conditionSet = new ManipulatorConditionSet( new ClickAdapterManipulator( clickAdapter ) );
		for( InputCondition condition : conditions ) {
			conditionSet.addCondition( condition );
		}
		this.addManipulatorConditionSet( conditionSet );
	}

	@Override
	protected org.lgna.croquet.ImmutableDataSingleSelectListState<org.alice.interact.handle.HandleStyle> getHandleStyleState() {
		return org.alice.stageide.sceneeditor.side.SideComposite.getInstance().getHandleStyleState();
	}

	public AffineMatrix4x4 getDropTargetTransformation() {
		return this.dropTargetManipulator.getTargetTransformation();
	}

	private final org.lgna.croquet.event.ValueListener<org.alice.interact.handle.HandleStyle> handleStyleListener = new org.lgna.croquet.event.ValueListener<org.alice.interact.handle.HandleStyle>() {
		@Override
		public void valueChanged( org.lgna.croquet.event.ValueEvent<org.alice.interact.handle.HandleStyle> e ) {
			setInteractionState( e.getNextValue() );
		}
	};

	@Override
	public boolean shouldSnapToRotation() {
		return org.alice.stageide.sceneeditor.snap.SnapState.getInstance().shouldSnapToRotation();
	}

	@Override
	public boolean shouldSnapToGround() {
		return org.alice.stageide.sceneeditor.snap.SnapState.getInstance().shouldSnapToGround();
	}

	@Override
	public boolean shouldSnapToGrid() {
		return org.alice.stageide.sceneeditor.snap.SnapState.getInstance().shouldSnapToGrid();
	}

	@Override
	public double getGridSpacing() {
		return org.alice.stageide.sceneeditor.snap.SnapState.getInstance().getGridSpacing();
	}

	@Override
	public edu.cmu.cs.dennisc.math.Angle getRotationSnapAngle() {
		return org.alice.stageide.sceneeditor.snap.SnapState.getInstance().getRotationSnapAngle();
	}

	@Override
	public void undoRedoEndManipulation( org.alice.interact.manipulator.AbstractManipulator manipulator, AffineMatrix4x4 originalTransformation ) {
		edu.cmu.cs.dennisc.scenegraph.AbstractTransformable sgManipulatedTransformable = manipulator.getManipulatedTransformable();
		if( sgManipulatedTransformable != null ) {
			AffineMatrix4x4 newTransformation = sgManipulatedTransformable.getLocalTransformation();

			if( newTransformation.equals( originalTransformation ) ) {
				edu.cmu.cs.dennisc.java.util.logging.Logger.warning( "Adding an undoable action for a manipulation that didn't actually change the transformation." );
			}
			if( originalTransformation == null ) {
				edu.cmu.cs.dennisc.java.util.logging.Logger.severe( "Ending manipulation where the original transformaion is null." );
			}

			org.lgna.story.SThing aliceThing = org.lgna.story.implementation.EntityImp.getAbstractionFromSgElement( sgManipulatedTransformable );
			if( aliceThing != null ) {
				AbstractPredeterminedSetLocalTransformationActionOperation undoOperation;
				if( aliceThing instanceof org.lgna.story.SJoint ) {
					org.lgna.story.implementation.JointImp jointImp = (org.lgna.story.implementation.JointImp)org.lgna.story.implementation.EntityImp.getInstance( sgManipulatedTransformable );
					org.lgna.story.SThing jointedModelThing = jointImp.getJointedModelParent().getAbstraction();
					org.lgna.project.ast.UserField manipulatedField = org.alice.stageide.sceneeditor.StorytellingSceneEditor.getInstance().getFieldForInstanceInJavaVM( jointedModelThing );
					undoOperation = new org.alice.stageide.sceneeditor.interact.croquet.PredeterminedSetLocalJointTransformationActionOperation( org.lgna.croquet.Application.PROJECT_GROUP, false, this.getAnimator(), manipulatedField, jointImp.getJointId(), originalTransformation, newTransformation, manipulator.getUndoRedoDescription() );
				} else {
					org.lgna.project.ast.UserField manipulatedField = org.alice.stageide.sceneeditor.StorytellingSceneEditor.getInstance().getFieldForInstanceInJavaVM( aliceThing );
					undoOperation = new PredeterminedSetLocalTransformationActionOperation( org.lgna.croquet.Application.PROJECT_GROUP, false, this.getAnimator(), manipulatedField, originalTransformation, newTransformation, manipulator.getUndoRedoDescription() );
				}
				// <lg> ensure we're on the event dispatch thread
				javax.swing.SwingUtilities.invokeLater( () -> {
					undoOperation.fire();
				} );
				// </lg>
			} else {
				//note: currently this condition can occur for manipulations of the scene editor's orthographic camera views
			}
		}
	}
}
