/*
 * TestBagInfoTask.java
 *
 * Created on November 13, 2001, 6:56 PM
 */

package org.netbeans.xtest.pe;


import org.apache.tools.ant.*;
import org.netbeans.xtest.pe.xmlbeans.TestBag;
import java.io.*;

/**
 *
 * @author  mb115822
 * @version 
 */
public class TestBagInfoTask extends Task{

    /** Creates new TestBagInfoTask */
    public TestBagInfoTask() {
        tb = new TestBag();
        tb.xmlat_name = "Unknown Name";
        tb.xmlat_module = "Unknown Module";
        tb.xmlat_testType = "Unknown TestType";
        tb.xmlat_executor = "Unknown Executor";    
    }
    
    private File outfile;
    private TestBag tb;
    
    public void setOutFile(File outfile) {
        this.outfile = outfile;
    }
    
    public void setName(String name) {
        tb.xmlat_name = name;
    }
    
    public void setModule(String module) {
        tb.xmlat_module = module;
    }
    
    public void setTestType(String testType) {
        tb.xmlat_testType = testType;
    }
    
    public void setExecutor(String executor) {
        tb.xmlat_executor = executor;
    }
    
    public void setTestAttribs(String testAttribs) {
        tb.xmlat_testAttribs = testAttribs;
    }

    /*
    public TestBag getTestBag() {
    }
    */
    
    public void execute () throws BuildException {
        tb.xmlat_timeStamp = new java.sql.Timestamp(System.currentTimeMillis());
        //System.err.println("TB:"+tb);
        try {
            FileOutputStream outStream = new FileOutputStream(this.outfile);            
            SerializeDOM.serializeToStream(tb.toDocument(),outStream);
        } catch (IOException ioe) {
            System.err.println("TestBagInfoTask - cannot save systeminfo");
            ioe.printStackTrace(System.err);
        } catch (Exception e) {
            System.err.println("TestBagInfoTask - XMLBean exception ???");
            e.printStackTrace(System.err);           
        }
    }
}
