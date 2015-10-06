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
package edu.wustl.lookingglass.ide.community.connection;

import org.lgna.croquet.BooleanState;
import org.lgna.croquet.Operation;
import org.lgna.croquet.SimpleComposite;
import org.lgna.croquet.StringState;

import edu.wustl.lookingglass.ide.croquet.models.community.CommunityLoginOperation;
import edu.wustl.lookingglass.ide.croquet.models.community.browseroperation.CreateAccountBrowseOperation;
import edu.wustl.lookingglass.ide.croquet.models.community.browseroperation.ResetPasswordBrowseOperation;
import edu.wustl.lookingglass.ide.croquet.models.community.views.CommunityLoginView;
import edu.wustl.lookingglass.ide.croquet.preferences.CommunityPasswordState;
import edu.wustl.lookingglass.ide.croquet.preferences.CommunityUsernameState;
import edu.wustl.lookingglass.ide.croquet.preferences.PersistentCommunityCredentialsState;

/**
 * @author Caitlin Kelleher
 */
public class CommunityLoginComposite extends SimpleComposite<CommunityLoginView> {

	private final StringState usernameState = CommunityUsernameState.getInstance();
	private final StringState passwordState = CommunityPasswordState.getInstance();
	private final BooleanState isRememberedState = PersistentCommunityCredentialsState.getInstance();
	private final Operation signUpOperation = new CreateAccountBrowseOperation();
	private final Operation resetPasswordOperation = new ResetPasswordBrowseOperation();
	private final Operation logInOperation = CommunityLoginOperation.getInstance();

	public CommunityLoginComposite() {
		super( java.util.UUID.fromString( "b859b893-7348-45aa-99cc-73574f4bbb75" ) );
	}

	public StringState getUsernameState() {
		return this.usernameState;
	}

	public StringState getPasswordState() {
		return this.passwordState;
	}

	public BooleanState getIsRememberedState() {
		return this.isRememberedState;
	}

	public Operation getSignUpOperation() {
		return this.signUpOperation;
	}

	public Operation getResetPasswordOperation() {
		return this.resetPasswordOperation;
	}

	public Operation getLogInOperation() {
		return this.logInOperation;
	}

	@Override
	protected CommunityLoginView createView() {
		return new CommunityLoginView( this );
	}
}