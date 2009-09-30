/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
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
package folders.javaFolder1000;

import org.netbeans.jellytools.ProjectsTabOperator;
import org.netbeans.jellytools.nodes.Node;
import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.ComponentOperator;

import org.netbeans.jemmy.operators.JScrollBarOperator;
import org.netbeans.modules.performance.guitracker.ActionTracker;
import org.netbeans.modules.performance.utilities.CommonUtilities;
import org.netbeans.modules.performance.utilities.PerformanceTestCase;
import org.netbeans.modules.project.ui.test.ProjectSupport;

/**
 * Measure UI-RESPONSIVENES and WINDOW_OPENING.
 *
 * @author rashid@netbeans.org
 *
 */
public class ScrollExpandedProject_1 extends PerformanceTestCase {

    private static String testProjectName = "jEdit-Model";
    private JScrollBarOperator projectScroll;
    private ProjectsTabOperator pto;

    /** Creates a new instance of ScrollExpandedProject*/
    public ScrollExpandedProject_1(String testName) {
        super(testName);
        expectedTime = 2000;
        WAIT_AFTER_OPEN = 4000;
    }

    public ScrollExpandedProject_1(String testName, String performanceDataName) {
        super(testName, performanceDataName);
        expectedTime = 2000;
        WAIT_AFTER_OPEN = 4000;
    }

    @Override
    public void initialize() {
        log(":: initialize");

        ProjectSupport.openProject(CommonUtilities.getProjectsDir() + testProjectName);
        Node pNode = new ProjectsTabOperator().getProjectRootNode(testProjectName);
        Node nodeModel = new Node(pNode, "Model");
        Node nodeDiagrams = new Node(pNode, "Diagrams");
        nodeModel.expand();
        nodeDiagrams.select();
        nodeDiagrams.expand();
        new EventTool().waitNoEvent(1000);
        pto = new ProjectsTabOperator();
        projectScroll = new JScrollBarOperator(pto, 0);
        MY_START_EVENT = ActionTracker.TRACK_OPEN_BEFORE_TRACE_MESSAGE;
    }

    public void prepare() {
        log(":: prepare");
        projectScroll.scrollToMinimum();
    }

    public ComponentOperator open() {
        log("::open");
        projectScroll.scrollToMaximum();
        return null;
    }

    @Override
    protected void shutdown() {
        log("::shutdown");
        ProjectSupport.closeProject(testProjectName);
    }

    @Override
    public void close() {
        log("::close");
    }

//    public static void main(java.lang.String[] args) {
//        junit.textui.TestRunner.run(new ScrollExpandedProject("measureTime"));
//    }
}
