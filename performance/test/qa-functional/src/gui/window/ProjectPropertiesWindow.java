/*
 *                 Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2004 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package gui.window;

import org.netbeans.jellytools.nodes.Node;
import org.netbeans.jellytools.ProjectsTabOperator;
import org.netbeans.jellytools.NbDialogOperator;


import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Test of Project Properties Window
 *
 * @author  mmirilovic@netbeans.org
 */
public class ProjectPropertiesWindow extends testUtilities.PerformanceTestCase {
    
    private static Node testNode;
    
    /** Creates a new instance of ProjectPropertiesWindow */
    public ProjectPropertiesWindow(String testName) {
        super(testName);
        expectedTime = WINDOW_OPEN;
    }
    
    /** Creates a new instance of ProjectPropertiesWindow */
    public ProjectPropertiesWindow(String testName, String performanceDataName) {
        super(testName,performanceDataName);
        expectedTime = WINDOW_OPEN;
    }
    
    protected void initialize() {
        testNode = (Node) new ProjectsTabOperator().getProjectRootNode("jEdit41");
    }
    
    public void prepare() {
        // do nothing
    }
    
    public ComponentOperator open() {
        // invoke Window / Properties from the main menu
        testNode.performPopupAction("Properties");
        return new NbDialogOperator("Project Properties - jEdit41");
    }
    
}
