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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package gui.window;

import org.netbeans.jellytools.NbDialogOperator;
import org.netbeans.jellytools.ProjectsTabOperator;
import org.netbeans.jellytools.nodes.Node;

import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.JButtonOperator;

/**
 *
 * @author mkhramov@netbeans.org, mmirilovic@netbeans.org
 */
public class ManageComponentLibrariesDialog  extends org.netbeans.performance.test.utilities.PerformanceTestCase {
    
    private NbDialogOperator parent, dialog;
    private JButtonOperator manageComponentLibsButton;
    
    /** Creates a new instance of ManageComponentLibrariesDialog */
    public ManageComponentLibrariesDialog(String testName) {
        super(testName);
        expectedTime = WINDOW_OPEN;
        WAIT_AFTER_OPEN=3000;            
    }
    
    /** Creates a new instance of ManageComponentLibrariesDialog */
    public ManageComponentLibrariesDialog(String testName, String performanceDataName) {
        super(testName,performanceDataName);
        expectedTime = WINDOW_OPEN;
        WAIT_AFTER_OPEN=3000;        
    }
    
    protected void initialize() {
        log("::initialize");
        Node openNode = new Node(new ProjectsTabOperator().getProjectRootNode("VisualWebProject"),org.netbeans.jellytools.Bundle.getString("org.netbeans.modules.visualweb.complib.ui.Bundle", "ComplibsRootNode.displayName"));
        openNode.callPopup().pushMenu(org.netbeans.jellytools.Bundle.getString("org.netbeans.modules.visualweb.complib.ui.Bundle", "ComplibsRootNode.addComplibAction"));
        parent = new NbDialogOperator(org.netbeans.jellytools.Bundle.getString("org.netbeans.modules.visualweb.complib.ui.Bundle", "ComplibsRootNode.addComplib"));
        
        
    }
    public void prepare() {
        log("::prepare");
        manageComponentLibsButton = new JButtonOperator(parent,"Manage Component Libraries..."); // NOI18N
    }

    public ComponentOperator open() {
        log(":: open");
        manageComponentLibsButton.pushNoBlock();
        dialog = new NbDialogOperator("Component Library Manager"); // NOI18N
        return dialog;
    }

    @Override
    public void close() {
        log("::close");
        dialog.close();
    }

    protected void shutdown() {
        log(":: shutdown");
        super.shutdown();
        parent.close();
    }
    
    public static void main(String[] args) {
        junit.textui.TestRunner.run(new ManageComponentLibrariesDialog("measureTime","Measure Manage Component Libraries Dialog open time"));
    }

}
