/*
 * RegenerateXMLTask.java
 *
 * Created on November 13, 2001, 6:48 PM
 */

package org.netbeans.xtest.pe;

import org.apache.tools.ant.*;
import java.io.*;
import org.netbeans.xtest.pe.xmlbeans.*;
import java.util.*;
import org.w3c.dom.*;

// move ant task - i'm lazy to write my own :-)))
import org.apache.tools.ant.taskdefs.Move;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.DirectoryScanner;

/**
 *
 * @author  mb115822
 * @version 
 */
public class RegenerateXMLTask extends Task{


    
    // debugging flag - should be set to false :-)
    private static final boolean DEBUG = false;
    private static final void debugInfo(String message) {
        if (DEBUG) System.out.println("RegenerateXMLTask."+message);
    }
    
    
    /** Creates new AggregatorTask */
    public RegenerateXMLTask() {
    }
    
    
    
    private File outputDir;
    
    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }
    
    private File inputDir;
    
    public void setInputDir(File inputDir) {
        this.inputDir = inputDir;
    }
    
    public static Document getDOMDocFromFile(File file) throws IOException {
        //return SerializeDOM.parseFile(file);
        return SerializeDOM.parseFile(file);
    }
    
    public TestBag getTestBag() throws Exception {
        return getTestBag(new File(inputDir,PEConstants.TESTBAG_XML_FILE));
    }
    
    
    public static TestBag getTestBag(File testBag) throws Exception {
        Document doc = getDOMDocFromFile(testBag);
        XMLBean xmlBean = XMLBean.getXMLBean(doc);    
        if (xmlBean instanceof TestBag) {
            return (TestBag)xmlBean;
        } else {
            return new TestBag();
        }
    }
    
    public SystemInfo getSystemInfo() {
        try {
            Document doc = getDOMDocFromFile(new File(inputDir,"systeminfo.xml"));
            XMLBean xmlBean = XMLBean.getXMLBean(doc);    
            if (xmlBean instanceof SystemInfo) {
                return (SystemInfo)xmlBean;
            } else {
                return new SystemInfo();
            }
        } catch (Exception e) {
            return new SystemInfo();
        }
    }
    
    public TestRun getTestRun() {
       return getTestRun(new File(inputDir,PEConstants.TESTRUN_XML_FILE));
    }
    
    public static TestRun getTestRun(File testRunFile) {
        try {
            Document doc = getDOMDocFromFile(testRunFile);
            XMLBean xmlBean = XMLBean.getXMLBean(doc);    
            if (xmlBean instanceof TestRun) {
                return (TestRun)xmlBean;
            } else {
                return new TestRun();
            }
        } catch (Exception e) {
            return new TestRun();
        }
    }
    
    public XTestResultsReport getXTestResultsReport()    {
        return getXTestResultsReport(new File(inputDir,PEConstants.TESTREPORT_XML_FILE));
    }
    
    public static XTestResultsReport getXTestResultsReport(File reportFile)    {
        try {
            Document doc = getDOMDocFromFile(reportFile);
            XMLBean xmlBean = XMLBean.getXMLBean(doc);    
            if (xmlBean instanceof XTestResultsReport) {
                return (XTestResultsReport)xmlBean;
            } else {
                return new XTestResultsReport();
            }
        } catch (Exception e) {
            return new XTestResultsReport();
        }
    }
    
    
    public static UnitTestSuite getUnitTestSuite(File suiteFile) throws Exception {
        Document doc = getDOMDocFromFile(suiteFile);
        XMLBean xmlBean = XMLBean.getXMLBean(doc);    
        if (xmlBean instanceof UnitTestSuite) {
            return (UnitTestSuite)xmlBean;
        } else {
            System.out.println("getUnitTestSuite():xmlBean:"+xmlBean);
            return null;
        }
    }
    
    
    public UnitTestSuite[] getUnitTestSuites() throws Exception {
         File suiteDir = new File(inputDir,"suites");
         return getUnitTestSuites(suiteDir);
    }
    
    public static UnitTestSuite[] getUnitTestSuites(File suiteDir) throws Exception {       
        //File suiteDir = inputDir;
        // scan directory
        File[] suiteFiles = suiteDir.listFiles();
        debugInfo("getUnitTestSuites(File):"+suiteFiles);
        ArrayList suiteList = new ArrayList();
        for (int i=0; i< suiteFiles.length; i++) {
            try {
                suiteList.add(getUnitTestSuite(suiteFiles[i]));
            } catch (Exception e) {
                // exception !!!
            }
        }
        // now convert the arraylist into plain array
        return (UnitTestSuite[])(suiteList.toArray(new UnitTestSuite[0]));
    }
    
    public void execute () throws BuildException {
        try {
                        
            Project antProject = getProject();
            String fullrun = antProject.getProperty("xtest.fullrun");
         
            // lets regenerate 
            int inputDirType = ResultsUtils.resolveResultsDir(inputDir);
            switch (inputDirType) {
                case ResultsUtils.TESTBAG_DIR:
                case ResultsUtils.TESTRUN_DIR:
                case ResultsUtils.TESTREPORT_DIR:
                    regenerateXMLs(inputDir,false,false);
                    break;
                case ResultsUtils.UNKNOWN_DIR:
                    regenerateTestReport(inputDir,false,false);
                    break;
                default:
                    System.out.println("RegenerateXMLTask: cannot regenerate (not a suitable input dir)");
            }
            
        } catch (Exception e) {
            System.err.println("Exception in RegenerateXMLTask");
            e.printStackTrace(System.err);
        }
    }
    
   
    public static void regenerateXMLs(File rootDir, boolean fullRegenerate, boolean produceBigReportOnly) throws Exception {
         debugInfo("regenerateXMLs(rootDir="+rootDir+", fullRegenerate="+fullRegenerate+
                ", produceBigReportOnly="+produceBigReportOnly+")");
         int rootDirType = ResultsUtils.resolveResultsDir(rootDir);
            switch (rootDirType) {
                case ResultsUtils.TESTBAG_DIR:
                    regenerateTestBag(rootDir,fullRegenerate,produceBigReportOnly);
                    break;
                case ResultsUtils.TESTRUN_DIR:
                    regenerateTestRun(rootDir,fullRegenerate,produceBigReportOnly);
                    break;
                case ResultsUtils.TESTREPORT_DIR:
                    regenerateTestReport(rootDir,fullRegenerate,produceBigReportOnly);
                    break;
                default:
                    throw new IOException("regenerateXMLs: specified directory is not recognized: "+rootDir);                    
            }
            
    }
    
    
    
    // regenerates testbag.xml if out of sync with stored suites in suites subdir
    public static TestBag regenerateTestBag(File testBagRoot, boolean fullRegenerate, boolean produceBigReportOnly) throws Exception {
        debugInfo("regenerateTestBag(testBagRoot="+testBagRoot+", fullRegenerate="+fullRegenerate+
            ", produceBigReportOnly="+produceBigReportOnly+")");
        File testBagResultDir = new File(testBagRoot,PEConstants.XMLRESULTS_DIR);
        File testBagFile = new File(testBagResultDir,PEConstants.TESTBAG_XML_FILE);
        File testBagFailuresFile = new File(testBagResultDir,PEConstants.TESTBAG_FAILURES_XML_FILE);
        TestBag testBag = (TestBag)getTestBag(testBagFile);
        debugInfo("regenerateTestBag(): got testBag from testbag.xml");
        // now scan the directory and get a list of test suites available in suite directory
        if ((!fullRegenerate)&(!produceBigReportOnly)) {
            // check whether suite files and suites contained in testbag match
            String[] suitesInFiles = ResultsUtils.listSuites(new File(testBagResultDir,PEConstants.TESTSUITES_SUBDIR));
            debugInfo("regenerateTestBag(): checking whether testbag is out of sync with suites dir");
            if (testBag.xmlel_UnitTestSuite!=null) {
                if (suitesInFiles.length!=testBag.xmlel_UnitTestSuite.length) {
                    debugInfo("regenerateTestBag(): both suites differ in length");
                    boolean suitesDiffer = false;
                    for (int i=0;i<suitesInFiles.length;i++) {
                        boolean found = false;
                        for (int j=0;(j<testBag.xmlel_UnitTestSuite.length)|(found);j++) {
                            if (testBag.xmlel_UnitTestSuite[j].xmlat_name.equals(suitesInFiles[i])) {
                                found = true;
                            }
                        }                        
                        if (!found) {
                            suitesDiffer = true;
                            debugInfo("regenerateTestBag(): have to regenerate, suite "+
                                        suitesInFiles[i]+ " does not exist in XML");
                            break;
                        }
                    }
                    if (!suitesDiffer) {
                        debugInfo("regenerateTestBag(): suites are in sync - don't regenerate");
                        return testBag;
                    }
                } else {
                    debugInfo("regenerateTestBag(): lenghts of suitesInFiles and testBag.xmlel_UnitTestSuite differs - have to regenerate");
                }
            } else {
                debugInfo("regenerateTestBag(): testBag does not contain any suite, have to regenerate");
            }
        }
        
        debugInfo("regenerateTestBag(): regenerating testbag.xml");
        UnitTestSuite[] testSuites = getUnitTestSuites(new File(testBagResultDir,PEConstants.TESTSUITES_SUBDIR)); 
        UnitTestSuite[] failingTestSuites = new UnitTestSuite[testSuites.length];
        // clean old values
        testBag.xmlat_testsPass = 0;
        testBag.xmlat_testsFail = 0;
        testBag.xmlat_testsError = 0;
        testBag.xmlat_testsTotal = 0;
        testBag.xmlat_testsTotal = 0;
        testBag.xmlat_time = 0;
        // remove testcases from suites - we don't need them in testbag.xml and
        // count new values
        for (int i=0;i<testSuites.length;i++) {            
            // compute new values            
            testBag.xmlat_testsPass+=testSuites[i].xmlat_testsPass;
            testBag.xmlat_testsFail+=testSuites[i].xmlat_testsFail;
            testBag.xmlat_testsError+=testSuites[i].xmlat_testsError;
            testBag.xmlat_testsTotal+=testSuites[i].xmlat_testsTotal;
            testBag.xmlat_time+=testSuites[i].xmlat_time;
            // if we generate also failures
            if (!produceBigReportOnly) {                
                failingTestSuites[i] = testSuites[i];
            }
        }
        // assign id to this testbag
        testBag.xmlat_bagID = testBagRoot.getName();
        //
        // now serialize new regenerated testbag (only if not producing big report !!!)
        if (!produceBigReportOnly) {                                
            debugInfo("regenerateTestBag(): serializing new testbag with failures");            
            // delete all passing testcases!!!
            for (int i=0;i<failingTestSuites.length;i++) {
                UnitTestCase[] testCases = testSuites[i].xmlel_UnitTestCase;
                if (failingTestSuites[i].xmlat_testsPass != failingTestSuites[i].xmlat_testsTotal) {
                    for (int j=0;j<testCases.length;j++) {
                        if (testCases[j].xmlat_result.equals("pass")) {
                            testCases[j] = null;
                        } else {
                            testCases[j].xml_pcdata = null;
                            testCases[j].xml_cdata = null;
                        }
                    }
                } else {
                    failingTestSuites[i] = null;
                }
            }                    
            testBag.xmlel_UnitTestSuite = failingTestSuites;
            SerializeDOM.serializeToFile(testBag.toDocument(),testBagFailuresFile);
            
            debugInfo("regenerateTestBag(): serializing new testbag");
            // delete the rest of testcases
            for (int i=0;i<testSuites.length;i++) {
                testSuites[i].xmlel_UnitTestCase = null;
            }
            testBag.xmlel_UnitTestSuite = testSuites;
            SerializeDOM.serializeToFile(testBag.toDocument(),testBagFile);
        }
        
        testBag.xmlel_UnitTestSuite = testSuites;
        return testBag;
    }     
    
    
    
    
    // regenerate testrun
    public static TestRun regenerateTestRun(File testRunRoot, boolean fullRegenerate, boolean produceBigReportOnly) throws Exception {
        debugInfo("regenerateTestRun(testRunRoot="+testRunRoot+", fullRegenerate="+fullRegenerate+
            ", produceBigReportOnly="+produceBigReportOnly+")");
        File testRunResultsDir = new File(testRunRoot,PEConstants.XMLRESULTS_DIR);
        if (!testRunResultsDir.exists()) {
            if (!testRunResultsDir.mkdirs()) {
                throw new IOException("Cannot create directory:"+testRunResultsDir);
            }
        }
        File testRunFile = new File(testRunResultsDir,PEConstants.TESTRUN_XML_FILE);
        File testRunFailuresFile = new File(testRunResultsDir,PEConstants.TESTRUN_FAILURES_XML_FILE);
        TestRun testRun = (TestRun)getTestRun(testRunFile);
        debugInfo("regenerateTestRun(): regenerating testrun.xml");
        File[] testBagsDirs = ResultsUtils.listTestBags(testRunRoot);
        TestBag[] testBags = new TestBag[testBagsDirs.length];       
        // now try to regenerate testbags first, then regenerate testRun;       
        debugInfo("regenerateTestRun(): regenerating child TestBags");
        for (int i=0;i<testBagsDirs.length;i++) {
            testBags[i] = regenerateTestBag(testBagsDirs[i],fullRegenerate,produceBigReportOnly);            
            debugInfo("regenerateTestRun(): succesfully regenerated testBag "+testBagsDirs[i].getName());
        }
        // now regenerate our stuff
        // because we actually have our testBags already - we don't have to
        // perfrom any check against the testRun - we just
        // replace testBags in TestRun object (sure, we will take care of includeChildren stuff)
     
        testRun.xmlat_testsPass = 0;
        testRun.xmlat_testsFail = 0;
        testRun.xmlat_testsError = 0;
        testRun.xmlat_testsTotal = 0;
        testRun.xmlat_time = 0;
        // count new values
        for (int i=0;i<testBags.length;i++) {
            // compute new values
            testRun.xmlat_testsPass+=testBags[i].xmlat_testsPass;
            testRun.xmlat_testsFail+=testBags[i].xmlat_testsFail;
            testRun.xmlat_testsError+=testBags[i].xmlat_testsError;
            testRun.xmlat_testsTotal+=testBags[i].xmlat_testsTotal;
            testRun.xmlat_time+=testBags[i].xmlat_time;
        }
        testRun.xmlel_TestBag = testBags;
        // give the run ID
        testRun.xmlat_runID = testRunRoot.getName();
        // now serialize the new test run
        if (!produceBigReportOnly) {
            TestBag[] testBagsWithFailures = new TestBag[testBagsDirs.length];
            debugInfo("regenerateTestRun(): loading testbags with failures");
            for (int i=0;i<testRun.xmlel_TestBag.length;i++) {
                TestBag aTestBag = testRun.xmlel_TestBag[i];
                if (aTestBag.xmlat_testsTotal != aTestBag.xmlat_testsPass) {
                   testBagsWithFailures[i] = getTestBag(new File(testBagsDirs[i],PEConstants.XMLRESULTS_DIR+File.separator+PEConstants.TESTBAG_FAILURES_XML_FILE));
                }                
            }
            debugInfo("regenerateTestRun(): serializing new testrun with failures");
            testRun.xmlel_TestBag = testBagsWithFailures;
            SerializeDOM.serializeToFile(testRun.toDocument(),testRunFailuresFile);             
                        
            // here delete all suites - no longer needed
            for (int i=0; i<testBags.length;i++) {
                testBags[i].xmlel_UnitTestSuite = null;
            }
            debugInfo("regenerateTestRun(): serializing new testrun");
            testRun.xmlel_TestBag = testBags;
            SerializeDOM.serializeToFile(testRun.toDocument(),testRunFile);        
        }               
        return testRun;
    }
    
    
    
    
    // regenerate testreport
    public static XTestResultsReport regenerateTestReport(File testReportRoot, boolean fullRegenerate, boolean produceBigReportOnly) throws Exception {
        debugInfo("regenerateTestReport(testReportRoot="+testReportRoot+", fullRegenerate="+fullRegenerate+
            ", produceBigReportOnly="+produceBigReportOnly+")");
        File testReportResultsDir = new File(testReportRoot,PEConstants.XMLRESULTS_DIR);
        if (!testReportResultsDir.exists()) {
            if (!testReportResultsDir.mkdirs()) {
                throw new IOException("Cannot create directory:"+testReportResultsDir);
            }
        }
        File testReportFile = new File(testReportResultsDir,PEConstants.TESTREPORT_XML_FILE);
        XTestResultsReport testReport = (XTestResultsReport)getXTestResultsReport(testReportFile);
        debugInfo("regenerateTestReport(): regenerating testreport.xml");
        File[] testRunsDirs = ResultsUtils.listTestRuns(testReportRoot);
        TestRun[] testRuns = new TestRun[testRunsDirs.length];
        // now try to regenerate testbags first, then regenerate testRun;       
        debugInfo("regenerateTestReport(): regenerating child TestRuns");
        for (int i=0;i<testRunsDirs.length;i++) {
            testRuns[i] = regenerateTestRun(testRunsDirs[i],fullRegenerate,produceBigReportOnly);
            debugInfo("regenerateTestReport(): succesfully regenerated testRun "+testRunsDirs[i].getName());
        }
        // now regenerate our stuff
        // because we actually have our testRuns ready - we don't have to
        // perfrom any check against the testReport - we just
        // replace testRuns in TestReport object (sure, we will take care of includeChildren stuff)
     
        testReport.xmlat_testsPass = 0;
        testReport.xmlat_testsFail = 0;
        testReport.xmlat_testsError = 0;
        testReport.xmlat_testsTotal = 0;
        testReport.xmlat_time = 0;
        testReport.xmlat_fullReport = produceBigReportOnly;
        // count new values
        for (int i=0;i<testRuns.length;i++) {            
            // compute new values
            testReport.xmlat_testsPass+=testRuns[i].xmlat_testsPass;
            testReport.xmlat_testsFail+=testRuns[i].xmlat_testsFail;
            testReport.xmlat_testsError+=testRuns[i].xmlat_testsError;
            testReport.xmlat_testsTotal+=testRuns[i].xmlat_testsTotal;
            testReport.xmlat_time+=testRuns[i].xmlat_time;
        }
        testReport.xmlel_TestRun = testRuns;
        // try to set the correct date
        if (testReport.xmlat_timeStamp==null) {
            testReport.xmlat_timeStamp = new java.sql.Timestamp(System.currentTimeMillis());
        }
        // if not any system info was generated we have to do it ourselves
        if (testReport.xmlel_SystemInfo==null) {
            testReport.xmlel_SystemInfo = new SystemInfo[] {new SystemInfo()};
        }
        // do we generate also failures file (so we don't generate full report)
        if (!produceBigReportOnly) {
            File testReportFailuresFile = new File(testReportResultsDir,PEConstants.TESTREPORT_FAILURES_XML_FILE);
            TestRun[] testRunsWithFailures = new TestRun[testRunsDirs.length];
            debugInfo("regenerateTestReport(): loading testruns with failures");
            for (int i=0;i<testReport.xmlel_TestRun.length;i++) {
                TestRun aTestRun = testReport.xmlel_TestRun[i];
                if (aTestRun.xmlat_testsTotal != aTestRun.xmlat_testsPass) {
                   testRunsWithFailures[i] = getTestRun(new File(testRunsDirs[i],PEConstants.XMLRESULTS_DIR+File.separator+PEConstants.TESTRUN_FAILURES_XML_FILE));
                }                
            }
            debugInfo("regenerateTestReport(): serializing new testrun with failures");
            testReport.xmlel_TestRun = testRunsWithFailures;
            SystemInfo[] si = testReport.xmlel_SystemInfo;
            testReport.xmlel_SystemInfo = null;
            SerializeDOM.serializeToFile(testReport.toDocument(),testReportFailuresFile);          
            testReport.xmlel_SystemInfo = si;
        }
        
        // now serialize the new test run
        debugInfo("regenerateTestReport(): serializing new testreport");
        testReport.xmlel_TestRun = testRuns;
        SerializeDOM.serializeToFile(testReport.toDocument(),testReportFile);        
        return testReport;
    }
    
}


