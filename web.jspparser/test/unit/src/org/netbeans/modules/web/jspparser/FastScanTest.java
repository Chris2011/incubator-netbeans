/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2003 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.web.jspparser;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;

import junit.framework.*;
import org.netbeans.junit.*;
import org.netbeans.modules.web.jsps.parserapi.JspParserAPI;
import org.netbeans.modules.web.jsps.parserapi.JspParserFactory;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;

/** JUnit test suite with Jemmy support
 *
 * @author pj97932
 * @version 1.0
 */
public class FastScanTest extends NbTestCase {
    
    /** constructor required by JUnit
     * @param testName method name to be used as testcase
     */
    public FastScanTest(String testName) {
        super(testName);
    }
    
    /** method used for explicit testsuite definition
     */
    public static junit.framework.Test suite() {
        TestSuite suite = new NbTestSuite(FastScanTest.class);
        return suite;
    }

    public void testPage1() throws IOException {
        doFastScanTest("subdir/Page1.jsp");
    }
    
    public void doFastScanTest(String path) throws IOException {
        try{
            File f = new File(Manager.getWorkDirPath(), "jspparser-data");
            FileObject fo = TestUtil.mountRoot(f, this);
            FileObject wmRoot = fo.getFileObject("wmroot");
            StringTokenizer st = new StringTokenizer(path, "/");
            FileObject tempFile = wmRoot;
            while (st.hasMoreTokens()) {
                tempFile = tempFile.getFileObject(st.nextToken());
            }
            parseIt(wmRoot, tempFile);
        }catch(RuntimeException e){
            e.printStackTrace();
            e.printStackTrace(getRef());
            fail("Initialization of test failed! ->" + e);
        }
    }
    
    private void parseIt(FileObject root, FileObject jspFile) {
        log("calling parseIt, root: " + root + "  file: " + jspFile);
        JspParserAPI api = JspParserFactory.getJspParser();
        JspParserAPI.JspOpenInfo info = api.getJspOpenInfo(root, jspFile, TestUtil.getWebModule(jspFile));
        log("file: " + jspFile + "   enc: " + info.getEncoding() + "   isXML: " + info.isXmlSyntax());
        assertEquals(new JspParserAPI.JspOpenInfo(false, "ISO-8859-1"), info);
    }
    
    /** method called before each testcase
     */
    protected void setUp() throws IOException {
    }
    
    /** method called after each testcase<br>
     * resets Jemmy WaitComponentTimeout
     */
    protected void tearDown() {
    }
    
    /** Use for internal test execution inside IDE
     * @param args command line arguments
     */
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
