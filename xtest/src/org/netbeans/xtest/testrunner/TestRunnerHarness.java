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

package org.netbeans.xtest.testrunner;

import java.io.*;

/**
 * Class which controls the execution from outer space - it runs
 * in the same VM as harness and is reponsible for launching tested products
 *
 * @author  mb115822
 */
public class TestRunnerHarness {

    // whole testbag is run in a single VM instance
    public static final String TESTRUN_MODE_TESTBAG="testbag";
    // each test class (testsuite) is run in a single VM instance
    public static final String TESTRUN_MODE_TESTSUITE="testsuite";
    // all tests are run internally (in the same VM as the class which starts the test run)
    public static final String TESTRUN_MODE_CURRENT_VM="currentVM";
    
    public static final String TESTLIST_FILENAME = "testrunnerharness.testlist";
    
    private static final boolean DEBUG = false;
    
    private JUnitTestRunnerProperties testList;
    private TestBoardLauncher launcher;
    private File workDir;
    private String testMode;
    
    /** Creates a new instance of TestRunnerHarness */
    public TestRunnerHarness(TestBoardLauncher boardLauncher, File workDir, String testMode) throws IOException {
        this.launcher = boardLauncher;
        this.workDir = workDir;
        this.testList = loadJUnitTestRunnerProperties(workDir);
        this.testMode = testMode;
    }
    
    
    // loads test runner properties from file available at ${xtest.work}/
    public static JUnitTestRunnerProperties loadJUnitTestRunnerProperties(File workDir) throws IOException {
        File testRunnerPropertyFile = new File(workDir, TESTLIST_FILENAME);
        return JUnitTestRunnerProperties.load(testRunnerPropertyFile);
    }
       
    
    public void runTests() {
        if (DEBUG) System.out.println("TestRunnerHarness : "+this);
        if (TESTRUN_MODE_TESTBAG.equalsIgnoreCase(testMode)) {
            // testbag mode
            launchTests(testList);
        } else if (TESTRUN_MODE_TESTSUITE.equalsIgnoreCase(testMode)) {
            // testsuite mode
            JUnitTestRunnerProperties[] dividedTestLists = testList.divideByTests();
            if (DEBUG) System.out.println("Divided to "+dividedTestLists.length+" lists");
            for (int i = 0; i < dividedTestLists.length; i++) {                
                if (DEBUG) System.out.println("List "+i+":"+dividedTestLists[i]);
                if (DEBUG) dividedTestLists[i].list(System.out);
                launchTests(dividedTestLists[i]);
            }
            if(dividedTestLists.length == 0) {
                // run empty list just to report it as error in JUnitTestRunner.getTestSuites().
                launchTests(testList);
            }
        } else if (TESTRUN_MODE_CURRENT_VM.equalsIgnoreCase(testMode)) {
            // same vm mode - not supported right now
            throw new RuntimeException("Current VM mode not yet supported by TestRunner");
        } else {            
            // unupported mode            
            throw new RuntimeException("Unkonwn test run mode :"+testList.getTestRunType());
        }
    }
    
    public void launchTests(JUnitTestRunnerProperties testList) {
        try {
            launcher.launchTestBoard(testList);
        } catch (TestBoardLauncherException tble) {
            System.err.println("Cannot start tests. Reson: "+tble.getMessage());
        }
    }
    
}
