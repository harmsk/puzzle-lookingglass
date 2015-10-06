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
package org.alice.ide;

import org.alice.nonfree.NebulousIde;

/**
 * @author Dennis Cosgrove
 */
public enum IdeApp {
	INSTANCE;

	public org.lgna.croquet.BooleanState getMemoryUsageFrameIsShowingState() {
		return this.memoryUsageFrameIsShowingState;
	}

	public org.lgna.croquet.BooleanState getLocalizeReviewIsShowingState() {
		return this.localizeReviewIsShowingState;
	}

	public org.lgna.croquet.Operation getIsRecursionAllowedPreferenceDialogLaunchOperation() {
		return this.isRecursionAllowedPreferenceDialogLaunchOperation;
	}

	public org.lgna.croquet.MenuModel getContributorMenuModel() {
		return this.contributorMenuModel;
	}

	public org.lgna.croquet.Operation getHelpDialogLaunchOperation() {
		//<lg/>
		return this.helpOperation;
	}

	public org.lgna.croquet.Operation getGraphicsHelpDialogLaunchOperation() {
		return this.graphicsHelpDialogLaunchOperation;
	}

	public org.lgna.croquet.Operation getWarningDialogLaunchOperation() {
		return this.warningDialogLaunchOperation;
	}

	public org.alice.ide.croquet.models.help.ShowClassPathPropertyComposite getShowClassPathPropertyComposite() {
		return this.showClassPathPropertyComposite;
	}

	public org.alice.ide.croquet.models.help.ShowLibraryPathPropertyComposite getShowLibraryPathPropertyComposite() {
		return this.showLibraryPathPropertyComposite;
	}

	public org.alice.ide.croquet.models.help.ShowAllSystemPropertiesComposite getShowAllSystemPropertiesComposite() {
		return this.showAllSystemPropertiesComposite;
	}

	public org.lgna.croquet.Operation getShowSystemPropertiesDialogLaunchOperation() {
		return this.showSystemPropertiesDialogLaunchOperation;
	}

	public org.lgna.croquet.Operation getSystemEulaDialogLaunchOperation() {
		return this.systemEulaDialogLaunchOperation;
	}

	public org.lgna.croquet.Operation getSimsArtEulaDialogLaunchOperation() {
		return this.simsArtEulaDialogLaunchOperation;
	}

	public org.lgna.croquet.Operation getCreditsDialogLaunchOperation() {
		return this.creditsDialogLaunchOperation;
	}

	public org.lgna.croquet.Operation getAboutDialogLaunchOperation() {
		// <lg/>
		return this.aboutOperation;
	}

	public org.alice.ide.croquet.models.menubar.HelpMenuModel getHelpMenu() {
		return this.helpMenu;
	}

	private final org.lgna.croquet.BooleanState memoryUsageFrameIsShowingState = org.lgna.croquet.imp.frame.LazyIsFrameShowingState.createNoArgumentConstructorInstance(
			org.lgna.croquet.Application.INFORMATION_GROUP,
			org.alice.ide.croquet.models.ui.MemoryUsageComposite.class );

	private final org.lgna.croquet.BooleanState localizeReviewIsShowingState = org.lgna.croquet.imp.frame.LazyIsFrameShowingState.createNoArgumentConstructorInstance(
			org.lgna.croquet.Application.INFORMATION_GROUP,
			org.alice.ide.localize.review.croquet.LocalizeReviewFrame.class );

	private final org.lgna.croquet.Operation isRecursionAllowedPreferenceDialogLaunchOperation = org.lgna.croquet.imp.launch.LazySimpleLaunchOperationFactory.createNoArgumentConstructorInstance(
			org.alice.ide.preferences.recursion.IsRecursionAllowedPreferenceDialogComposite.class,
			org.lgna.croquet.Application.APPLICATION_UI_GROUP ).getLaunchOperation();

	private final org.lgna.croquet.MenuModel contributorMenuModel = new org.alice.ide.croquet.models.menubar.ContributorMenuModel( localizeReviewIsShowingState );

	//<lg/>
	private final org.lgna.croquet.Operation helpOperation = new edu.wustl.lookingglass.ide.croquet.models.help.HelpOperation();
	//private final org.lgna.croquet.Operation helpDialogLaunchOperation = org.lgna.croquet.imp.launch.LazySimpleLaunchOperationFactory.createNoArgumentConstructorInstance(
	//		org.alice.ide.help.HelpComposite.class,
	//		org.lgna.croquet.Application.INFORMATION_GROUP ).getLaunchOperation();

	private final org.lgna.croquet.Operation graphicsHelpDialogLaunchOperation = org.lgna.croquet.imp.launch.LazySimpleLaunchOperationFactory.createNoArgumentConstructorInstance(
			org.alice.ide.croquet.models.help.GraphicsHelpComposite.class,
			org.lgna.croquet.Application.INFORMATION_GROUP ).getLaunchOperation();

	private final org.lgna.croquet.Operation warningDialogLaunchOperation = org.lgna.croquet.imp.launch.LazySimpleLaunchOperationFactory.createNoArgumentConstructorInstance(
			org.alice.ide.warning.WarningDialogComposite.class,
			org.lgna.croquet.Application.INFORMATION_GROUP ).getLaunchOperation();

	private final org.alice.ide.croquet.models.help.ShowClassPathPropertyComposite showClassPathPropertyComposite = new org.alice.ide.croquet.models.help.ShowClassPathPropertyComposite();

	private final org.alice.ide.croquet.models.help.ShowLibraryPathPropertyComposite showLibraryPathPropertyComposite = new org.alice.ide.croquet.models.help.ShowLibraryPathPropertyComposite();

	private final org.alice.ide.croquet.models.help.ShowAllSystemPropertiesComposite showAllSystemPropertiesComposite = new org.alice.ide.croquet.models.help.ShowAllSystemPropertiesComposite();

	private final org.lgna.croquet.Operation showSystemPropertiesDialogLaunchOperation = org.lgna.croquet.imp.launch.LazySimpleLaunchOperationFactory.createNoArgumentConstructorInstance(
			org.alice.ide.croquet.models.help.ShowSystemPropertiesComposite.class,
			org.lgna.croquet.Application.INFORMATION_GROUP ).getLaunchOperation();

	private final org.lgna.croquet.Operation systemEulaDialogLaunchOperation = org.lgna.croquet.imp.launch.LazySimpleLaunchOperationFactory.createNoArgumentConstructorInstance(
			org.alice.stageide.about.SystemEulaComposite.class,
			org.lgna.croquet.Application.INFORMATION_GROUP ).getLaunchOperation();

	private final org.lgna.croquet.Operation simsArtEulaDialogLaunchOperation = NebulousIde.nonfree.newSimsArtEulaDialogLaunchOperation();

	private final org.lgna.croquet.Operation creditsDialogLaunchOperation = org.lgna.croquet.imp.launch.LazySimpleLaunchOperationFactory.createNoArgumentConstructorInstance(
			org.alice.stageide.about.CreditsComposite.class,
			org.lgna.croquet.Application.INFORMATION_GROUP ).getLaunchOperation();

	// <lg/>
	private final org.lgna.croquet.Operation aboutOperation = new edu.wustl.lookingglass.ide.croquet.models.help.AboutOperation();
	//	private final org.lgna.croquet.Operation aboutDialogLaunchOperation = org.lgna.croquet.imp.launch.LazySimpleLaunchOperationFactory.createNoArgumentConstructorInstance(
	//			org.alice.stageide.about.AboutComposite.class,
	//			org.lgna.croquet.Application.INFORMATION_GROUP ).getLaunchOperation();

	private final org.alice.ide.croquet.models.menubar.HelpMenuModel helpMenu = new org.alice.ide.croquet.models.menubar.HelpMenuModel( this );
}
