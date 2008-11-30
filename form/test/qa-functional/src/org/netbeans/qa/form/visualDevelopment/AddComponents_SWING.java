/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
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
package org.netbeans.qa.form.visualDevelopment;

import org.netbeans.jellytools.*;
import org.netbeans.jellytools.modules.form.*;
import org.netbeans.jellytools.nodes.*;
import org.netbeans.jellytools.actions.*;

import java.util.*;
import org.netbeans.junit.ide.ProjectSupport;
import org.netbeans.qa.form.*;
import java.io.*;
import junit.framework.Test;
import org.netbeans.junit.NbModuleSuite;

/**
 *<P>
 *<B><BR> Test create frame.</B>
 *
 *<BR><BR><B>What it tests:</B><BR>
 *  Frame containing all components from Component Palette SWING category try compile.
 *<BR><BR><B>How it works:</B><BR>
 *  Find tested form file, add all components from SWING category and compile created frame (check compile resolution).
 *
 *<BR><BR><B>Settings:</B><BR>
 *  Jemmy/Jelly classes, VisualDevelopmentSupport class in the classpath.
 *
 *<BR><BR><B>Resources:</B><BR>
 *  File (Resources.) clear_Frame(java/form) generated by NBr32(37).
 *
 *<BR><B>Possible reasons of failure</B>
 * <BR><U>jelly didn't find menu or popup menu</U>
 * <BR><U>is impossible add component or components in SWING category is another as in NB r3.2 (37)</U>
 * <BR><U>component was't add correctly or generated source code is wrong</U>
 *
 * @author  Jana.Maleckova@czech.sun.com
 * @version
 */
public class AddComponents_SWING extends ExtJellyTestCase {

    public String FILE_NAME = "clear_JFrame";
    public String PACKAGE_NAME = "data";
    public String DATA_PROJECT_NAME = "SampleProject";
    public String FRAME_ROOT = "[JFrame]";
    FormDesignerOperator formDesigner;
    public MainWindowOperator mainWindow;
    public ProjectsTabOperator pto;
    public Node formnode;

    public AddComponents_SWING(String testName) {
        super(testName);
    }

    /** Run test.
     */
    public void setUp() throws IOException {
        openDataProjects(_testProjectName);
    }

    /** Run test.
     */
    public void testCloseDataProject() {
//        closeDataProject();
        EditorWindowOperator ewo = new EditorWindowOperator();
        ewo.closeDiscard();
    }

    /** Run test.
     */
    public void testAddAndCompile() {
        String categoryName = "Swing Controls";

        pto = new ProjectsTabOperator();
        ProjectRootNode prn = pto.getProjectRootNode(DATA_PROJECT_NAME);
        prn.select();
        formnode = new Node(prn, "Source Packages|" + PACKAGE_NAME + "|" + FILE_NAME);
        formnode.select();
        log("Form node selected.");

        EditAction editAction = new EditAction();
        editAction.perform(formnode);
        log("Source Editor window opened.");

        OpenAction openAction = new OpenAction();
        openAction.perform(formnode);
        log("Form Editor window opened.");

        // store all component names from the category in the Vector
        Vector componentNames = new Vector();
        ComponentPaletteOperator palette = new ComponentPaletteOperator();
        palette.collapseBeans();
        palette.collapseSwingContainers();
        palette.collapseSwingMenus();
        palette.collapseSwingWindows();
        palette.collapseAWT();
        palette.expandSwingControls();
        //Read all simple swing componet
        String[] componentList = {"Label", "Button", "Toggle Button", "Check Box", "Radio Button", "Combo Box", "Text Field", "Scroll Bar", "Slider", "Progress Bar", "Password Field", "Spinner", "Separator"};
        //

        ComponentInspectorOperator cio = new ComponentInspectorOperator();
        Node inspectorRootNode = new Node(cio.treeComponents(), FRAME_ROOT);
        inspectorRootNode.select();
        inspectorRootNode.expand();

        // Add all beans from Swing Palette Category to form
        Action popupAddFromPaletteAction;
        for (int i = 0; i < componentList.length; i++) {
            popupAddFromPaletteAction = new Action(null, "Add From Palette|Swing Controls|" + componentList[i]);
            popupAddFromPaletteAction.perform(inspectorRootNode);
            String componentName = componentList[i].toString().replace(" ", "");
            System.out.println("What is searched: " + "private javax.swing.J" + componentName + " j" + componentName + "1");

            //Check if code was generated properly for component
            assertTrue("Check if " + componentName + " is correctly declared", checkEditor("private javax.swing.J" + componentName + " j" + componentName + "1"));
            assertTrue("Check if " + componentName + " is added to layout", checkEditor("getContentPane().add(j" + componentName + "1)"));
        }

        //Add the rest of swing components which inserted together with another component into layout
        String[] componentList2 = {"Button Group", "List", "Tree", "Table", "Text Area", "Text Pane", "Editor Pane", "Formatted Field"};

        for (int i = 0; i < componentList2.length; i++) {
            popupAddFromPaletteAction = new Action(null, "Add From Palette|Swing Controls|" + componentList2[i]);
            popupAddFromPaletteAction.perform(inspectorRootNode);
            String componentName = componentList2[i].toString().replace(" ", "");
            switch (i) {
                case 0:
                    System.out.println("private javax.swing." + componentName + " j" + componentName + "1");
                    assertTrue("Check if " + componentName + " is correctly declared", checkEditor("private javax.swing." + componentName + " buttonGroup1"));
                    break;
                case 7:
                    assertTrue("Check if " + componentName + " is correctly declared", checkEditor("private javax.swing.JFormattedTextField" + " jFormattedTextField1"));
                    assertTrue("Check if " + componentName + " is added to layout", checkEditor("getContentPane().add(jFormattedTextField1)"));
                    System.out.println("private javax.swing.J" + componentName + " j" + componentName + "1");
                    break;
                default:
                    System.out.println("private javax.swing.J" + componentName + " j" + componentName + "1");
                    assertTrue("Check if " + componentName + " is correctly declared", checkEditor("private javax.swing.J" + componentName + " j" + componentName + "1"));
                    assertTrue("Check if " + componentName + " is added to jScrollPane", checkEditor("jScrollPane" + i + ".setViewportView(j" + componentName + "1)"));
                    assertTrue("Check if jScrollPane" + i + "is added to layout", checkEditor("getContentPane().add(jScrollPane" + i));
                    break;

            }
        }


        log("All components from Swing Controls Palette : " + categoryName + " - were added to " + FILE_NAME);

        log("Try to save the form.");
        new org.netbeans.jemmy.EventTool().waitNoEvent(1000);
        editAction.perform(formnode);
        Action saveAction;
        saveAction = new Action("File|Save", null);
        saveAction.perform();

    }

    boolean checkEditor(String regexp) {

        sleep(300);
//        String editortext = editor.getText();

        formDesigner = new FormDesignerOperator(FILE_NAME);
        String editortext = formDesigner.editor().getText();
        formDesigner.design();

        java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(regexp, ",");
        int pos = -1;
        boolean result = true;
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            pos = editortext.indexOf(token, pos);
            if (pos == -1) {
                result = false;
                break;
            }
            pos += token.length();
        }
        System.out.println("Result: " + result);
        return result;
    }

    public void closeDataProject() {
        ProjectSupport.closeProject(DATA_PROJECT_NAME);
        log("SampleProject closed.");
    }

    void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
    }

    public static Test suite() {
        return NbModuleSuite.create(NbModuleSuite.createConfiguration(AddComponents_SWING.class).addTest("testAddAndCompile").clusters(".*").enableModules(".*").gui(true));

    }
}
