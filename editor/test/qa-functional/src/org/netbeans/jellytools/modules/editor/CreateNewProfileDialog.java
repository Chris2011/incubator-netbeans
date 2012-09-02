/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package org.netbeans.jellytools.modules.editor;

import org.netbeans.jemmy.operators.*;
import org.netbeans.jemmy.util.NameComponentChooser;

/** Class implementing all necessary methods for handling "Create New Profile Dialog" NbPresenter.
 *
 * @author jp159440
 * @version 1.0
 */
public class CreateNewProfileDialog extends JDialogOperator {

    /** Creates new CreateNewProfileDialog that can handle it.
     */
    public CreateNewProfileDialog() {
        super("Create New Profile Dialog");
    }

    private JLabelOperator _lblProfileName;
    private JTextFieldOperator _txtProfileName;
    private JButtonOperator _btOK;
    private JButtonOperator _btCancel;


    //******************************
    // Subcomponents definition part
    //******************************

    /** Tries to find "Profile Name:" JLabel in this dialog.
     * @return JLabelOperator
     */
    public JLabelOperator lblProfileName() {
        if (_lblProfileName==null) {
            _lblProfileName = new JLabelOperator(this, "Profile Name:");
        }
        return _lblProfileName;
    }

    /** Tries to find null JTextField in this dialog.
     * @return JTextFieldOperator
     */
    public JTextFieldOperator txtProfileName() {
        if (_txtProfileName==null) {
            _txtProfileName = new JTextFieldOperator(this);
        }
        return _txtProfileName;
    }

    /** Tries to find "OK" JButton in this dialog.
     * @return JButtonOperator
     */
    public JButtonOperator btOK() {
        if (_btOK==null) {
            _btOK = new JButtonOperator(this, "OK");
        }
        return _btOK;
    }

    /** Tries to find "Cancel" JButton in this dialog.
     * @return JButtonOperator
     */
    public JButtonOperator btCancel() {
        if (_btCancel==null) {
            _btCancel = new JButtonOperator(this, "Cancel");
        }
        return _btCancel;
    }


    //****************************************
    // Low-level functionality definition part
    //****************************************

    /** gets text for txtProfileName
     * @return String text
     */
    public String getProfileName() {
        return txtProfileName().getText();
    }

    /** sets text for txtProfileName
     * @param text String text
     */
    public void setProfileName(String text) {
        txtProfileName().setText(text);
    }

    /** types text for txtProfileName
     * @param text String text
     */
    public void typeProfileName(String text) {
        txtProfileName().typeText(text);
    }

    /** clicks on "OK" JButton
     */
    public void ok() {
        btOK().push();
    }

    /** clicks on "Cancel" JButton
     */
    public void cancel() {
        btCancel().push();
    }


    //*****************************************
    // High-level functionality definition part
    //*****************************************

    /** Performs verification of CreateNewProfileDialog by accessing all its components.
     */
    public void verify() {
        lblProfileName();
        txtProfileName();
        btOK();
        btCancel();
    }

    /** Performs simple test of CreateNewProfileDialog
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        new CreateNewProfileDialog().verify();
        System.out.println("CreateNewProfileDialog verification finished.");
    }
}

