/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
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
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 */
package org.netbeans.modules.javascript2.nodejs.editor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.project.Project;
import org.netbeans.modules.javascript2.editor.JsCodeCompletionBase;
import static org.netbeans.modules.javascript2.editor.JsTestBase.JS_SOURCE_ID;
import org.netbeans.modules.javascript2.nodejs.TestProjectSupport;
import org.netbeans.spi.java.classpath.support.ClassPathSupport;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.modules.Places;
import org.openide.util.test.MockLookup;

/**
 *
 * @author Petr Pisl
 */
public class NodeJSCodeCompletionTest extends JsCodeCompletionBase {

    public NodeJSCodeCompletionTest(String testName) throws IOException {
        super(testName);
    }

    static private boolean isSetup = false;

    @Override
    protected void setUp() throws Exception {
//        super.setUp(); //To change body of generated methods, choose Tools | Templates.
        if (!isSetup) {
            // only for the first run index all sources
            super.setUp();
            isSetup = true;
        }
        
        File cached = Places.getCacheSubfile(NodeJsDataProvider.BACKUP_API_FILE);
        if (!cached.exists()) {
            FileObject apiFile = getTestFile("NodeJsRuntime/all.json");
            File parent = cached.getParentFile();
            FileUtil.copyFile(apiFile, FileUtil.toFileObject(parent), apiFile.getName());
        }
        
        FileObject folder = getTestFile("TestNavigation");
            Project testProject = new TestProjectSupport.TestProject(folder, null);
            List lookupAll = new ArrayList();
            lookupAll.addAll(MockLookup.getDefault().lookupAll(Object.class));
            lookupAll.add(new TestProjectSupport.FileOwnerQueryImpl(testProject));
            MockLookup.setInstances(lookupAll.toArray());

    }

    public void testBasicExport01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/foo2.js", "+ circle.^area(4));", false);
    }

    public void testBasicExport02() throws Exception {
        checkCompletion("TestNavigation/public_html/js/testAddress.js", "as.^print();", false);
    }
    
    public void testExport01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/testcc01.js", "var mess = require('./simpleModule').m^essage;", false);
    }
    
    public void testExport02() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/testcc01.js", "simple.m^essage.setCode(23);", false);
    }
    
    public void testExport03() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/testcc01.js", "simple.message.se^tCode(23);", false);
    }
    
    public void testExport04() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/testcc01.js", "mess.set^Code(25);", false);
    }
    
    public void testExport05() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/testcc01.js", "mOut.o^utput = 'bug';", false);
    }
    
    public void testIssue249436_01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/issue249436.js", "t^ //cc here", false);
    }
    
    public void testIssue249632() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/issue249632.js", "rnewe.rgetAttempt().a^a;", false);
    }
    
    public void testIssue249628() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/issue249629.js", "instRef.rprops.^b;", false);
    }
    
    public void testIssue249500_01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/issue249500.js", "a^;", false);
    }
    
    public void testIssue249500_02() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/issue249500.js", "k^;", false);
    }
    
    public void testIssue249500_03() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/issue249500.js", "trip^; // test 1", false);
    }
    
    public void testIssue249500_04() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/issue249500.js", "trip^; // test 2", false);
    }
    
    public void testIssue249626_01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/issue249626.js", "require(\"./complexModule\").^literalRef.propX.iprop;", false);
    }
    
    public void testIssue249626_02() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/issue249626.js", "require(\"./complexModule\").literalRef.^propX.iprop;", false);
    }
    
    public void testIssue249626_03() throws Exception {
        checkCompletion("TestNavigation/public_html/js/cc01/issue249626.js", "require(\"./complexModule\").literalRef.propX.^iprop;", false);
    }
    
    public void testIssue250346_01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250346/modex/inFunction01.js", "paaa.^", false);
    }
    
    public void testIssue250346_02() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250346/modex/inFunction02.js", "simpleLit.^", false);
    }
    
    public void testIssue250346_03() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250346/modex/inFunction03.js", "simpleRef.^", false);
    }
    
    public void testIssue250346_04() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250346/modex/inFunction04.js", "modFu.^", false);
    }
     
    public void testIssue250333_01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250333/test_01.js", "aaa.^", false);
    }
    
    public void testIssue250333_02() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250333/test_02.js", "aaa.^", false);
    }
    
    public void testIssue250329_01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250329/s1.js", "ut.^errorRespond();", false);
    }
    
    public void testIssue250329_02() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250329/s1.js", "ut2.^endsWith2();", false);
    }
    
    public void testIssue250329_03() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250329/s2.js", "ut.^errorRespond();", false);
    }
    
    public void testIssue250329_04() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250329/s2.js", "ut2.^endsWith2();", false);
    }
    
    public void testIssue250329_05() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250329/s3.js", "ut.^errorRespond();", false);
    }
    
    public void testIssue250329_06() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250329/s3.js", "ut2.^endsWith2();", false);
    }
    
    public void testIssue250329_07() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250329/s4.js", "ut.^errorRespond();", false);
    }
    
    public void testIssue250329_08() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250329/s4.js", "ut2.^endsWith2();", false);
    }
    
    public void testIssue250298_01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250298/api/test250298.js", "mi.^listFiles(); // test1", false);
    }
    
    public void testIssue250298_02() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250298/api/test250298.js", "mi.^listFiles(); // test2", false);
    }
    
    public void testIssue250298_03() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250298/api/test250298.js", "mi.^listFiles(); // test3", false);
    }
    
    public void testIssue250298_04() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250298/api/test250298.js", "mi.^listFiles(); // test4", false);
    }
    
    public void testIssue250298_05() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250298/api/test250298.js", "mi.^listFiles(); // test5", false);
    }
    
    public void testIssue250011_01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250011.js", "msg^26;", false);
    }
    
    public void testIssue250200_01() throws Exception {
        checkCompletionDocumentation("TestNavigation/public_html/js/documentation/issue250200.js", "ba.r^esolve();", false, "relative");
    }
    
    public void testIssue250200_02() throws Exception {
        checkCompletionDocumentation("TestNavigation/public_html/js/documentation/issue250200.js", "ba.r^esolve();", false, "resolve");
    }
    
    public void testIssue249439_01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue249439/test249439.js", "weNeed.^create();", false);
    }
    
    public void testIssue249439_02() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue249439/test249439.js", "wn.^create();", false);
    }
    
    public void testIssue250603_01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250603/issue250603.js", "if (this.^)", false);
    }
    
    public void testIssue250338_01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250338/issue250338_01.js", "require('../cc01/complexModule').^", false);
    }
    
    public void testIssue250338_02() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250338/issue250338_02.js", "require('../cc01/complexModule').^", false);
    }
    
    public void testIssue250338_03() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250338/issue250338_03.js", "require('../cc01/complexModule').^", false);
    }
    
    public void testIssue250322_01() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250322.js", "ht.^; // cc here", false);
    }
    
    public void testIssue250322_02() throws Exception {
        checkCompletion("TestNavigation/public_html/js/issue250322.js", "fs.^; // cc here", false);
    }
    
    @Override
    protected Map<String, ClassPath> createClassPathsForTest() {
        List<FileObject> cpRoots = new LinkedList<FileObject>();

        cpRoots.add(FileUtil.toFileObject(new File(getDataDir(), "/TestNavigation/public_html/")));
        cpRoots.add(FileUtil.toFileObject(new File(getDataDir(), "/NodeJsRuntime/")));
        return Collections.singletonMap(
                JS_SOURCE_ID,
                ClassPathSupport.createClassPath(cpRoots.toArray(new FileObject[cpRoots.size()]))
        );
    }

    @Override
    protected boolean classPathContainsBinaries() {
        return true;
    }

    @Override
    protected boolean cleanCacheDir() {
        return false;
    }
}
