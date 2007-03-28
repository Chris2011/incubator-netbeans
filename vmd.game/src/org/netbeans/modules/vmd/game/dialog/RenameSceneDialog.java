/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.netbeans.modules.vmd.game.dialog;

import org.netbeans.modules.vmd.game.model.Scene;

public class RenameSceneDialog extends AbstractNameValidationDialog {

	private Scene scene;
	
	public RenameSceneDialog(Scene scene) {
		super(scene.getName());
		this.scene = scene;
	}
	
	protected String getInitialStateDescriptionText() {
		return "Enter scene name.";
	}
	
	protected String getNameLabelText() {
		return "Scene name:";
	}
	
	protected String getDialogNameText() {
		return "Rename scene";
	}
	
	protected String getCurrentStateErrorText() {
		String errMsg = null; 
		String name = this.fieldName.getText();

		if (name.equals("")) {
			return this.getInitialStateDescriptionText();
		}
		
		if (!scene.getGameDesign().isComponentNameAvailable(name)) {
			errMsg = "Component name already exists. Choose a different name.";
		}		
		return errMsg;
	}
	
	protected void handleOKButton() {
		this.scene.setName(this.fieldName.getText());
	}

}
