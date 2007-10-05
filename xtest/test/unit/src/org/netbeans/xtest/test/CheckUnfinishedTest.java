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

package org.netbeans.xtest.test;

import java.io.File;
import java.io.IOException;
import org.netbeans.junit.NbTestCase;
import org.netbeans.junit.NbTestSuite;
import org.netbeans.xtest.pe.xmlbeans.UnitTestCase;
import org.netbeans.xtest.pe.xmlbeans.UnitTestSuite;

/** Check test results of UnfinishedTest. At the end delete those results
 * because they contain errors.
 */
public class CheckUnfinishedTest extends NbTestCase {
    
    /** Need to be defined because of JUnit */
    public CheckUnfinishedTest(String name) {
        super(name);
    }
    
    public static NbTestSuite suite() {
        NbTestSuite suite = new NbTestSuite();
        suite.addTest(new CheckUnfinishedTest("testCheckResults"));
        return suite;
    }
    
    private static File suiteResultsFile = new File(
            new File(System.getProperty("xtest.workdir")),
            "xmlresults/suites/TEST-org.netbeans.xtest.test.UnfinishedTest.xml"
            );
    
    /** Setup. */
    public void setUp() {
        System.out.println("########  "+getName()+"  #######");
    }
    
    /** Delete UnfinishedTest results because they contain wanted errors. */
    public void tearDown() {
        assertTrue("Cannot delete suite file "+suiteResultsFile, suiteResultsFile.delete());
    }
    
    /** Check number of tests with proper status. */
    public void testCheckResults() throws IOException, ClassNotFoundException {
        UnitTestSuite unitTestSuite = UnitTestSuite.loadFromFile(suiteResultsFile);
        assertEquals("Wrong number of errors.", 3, unitTestSuite.getTestsError());
        assertEquals("Wrong number of fails.", 0, unitTestSuite.getTestsFail());
        assertEquals("Wrong number of passes.", 1, unitTestSuite.getTestsPass());
        assertEquals("Wrong total number.", 4, unitTestSuite.getTestsTotal());
        UnitTestCase unitTestCase = unitTestSuite.xmlel_UnitTestCase[0];
        assertEquals("Wrong results of test1.", unitTestCase.TEST_PASS, unitTestCase.getResult());
        unitTestCase = unitTestSuite.xmlel_UnitTestCase[1];
        assertEquals("Wrong results of test2.", unitTestCase.TEST_UNKNOWN, unitTestCase.getResult());
        assertEquals("Wrong message of test2.", "Did not finish.", unitTestCase.getMessage());
        unitTestCase = unitTestSuite.xmlel_UnitTestCase[2];
        assertEquals("Wrong results of test3.", unitTestCase.TEST_UNKNOWN, unitTestCase.getResult());
        assertEquals("Wrong message of test3.", "Did not start.", unitTestCase.getMessage());
        unitTestCase = unitTestSuite.xmlel_UnitTestCase[3];
        assertEquals("Wrong results of test4.", unitTestCase.TEST_UNKNOWN, unitTestCase.getResult());
        assertEquals("Wrong message of test4.", "Did not start.", unitTestCase.getMessage());
    }
}