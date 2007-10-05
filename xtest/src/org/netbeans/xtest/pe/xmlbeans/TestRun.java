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

/*
 * XTestTestRun.java
 *
 * Created on November 1, 2001, 6:09 PM
 */

package org.netbeans.xtest.pe.xmlbeans;

import java.io.*;
import org.netbeans.xtest.pe.xmlbeans.ModuleError;

/**
 *
 * @author  mb115822
 */
public class TestRun extends XMLBean {

    /** Creates new XTestTestRun */
    public TestRun() {
    }

    /** Getter for property timeStamp.
     * @return Value of property timeStamp.
     */
    public java.sql.Timestamp getTimeStamp() {
        return xmlat_timeStamp;
    }

    /** Setter for property timeStamp.
     * @param timeStamp New value of property timeStamp.
     */
    public void setTimeStamp(java.sql.Timestamp timeStamp) {
        xmlat_timeStamp = timeStamp;
    }
    
    /** Getter for property time.
     * @return Value of property time.
     */
    public long getTime() {
        return xmlat_time;
    }
    
    /** Setter for property time.
     * @param time New value of property time.
     */
    public void setTime(long time) {
        xmlat_time = time;
    }
    
    /** Getter for property config.
     * @return Value of property config.
     */
    public String getConfig() {
        return xmlat_config;
    }
    
    /** Setter for property config.
     * @param config New value of property config.
     */
    public void setConfig(String config) {
        xmlat_config = config;
    }
    
    /** Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return xmlat_name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        xmlat_name = name;
    }
    
    /** Getter for property attributes.
     * @return Value of property attributes.
     */
    public String getAttributes() {
        return xmlat_attributes;
    }
    
    /** Setter for property attributes.
     * @param attributes New value of property attributes.
     */
    public void setAttributes(String attributes) {
        xmlat_attributes = attributes;
    }
    
    /** Getter for property testsError.
     * @return Value of property testsError.
     */
    public long getTestsError() {
        return xmlat_testsError;
    }
    
    /** Setter for property testsError.
     * @param testsError New value of property testsError.
     */
    public void setTestsError(long testsError) {
        xmlat_testsError = testsError;
    }
    
    /** Getter for property testsFail.
     * @return Value of property testsFail.
     */
    public long getTestsFail() {
        return xmlat_testsFail;
    }
    
    /** Setter for property testsFail.
     * @param testsFail New value of property testsFail.
     */
    public void setTestsFail(long testsFail) {
        xmlat_testsFail = testsFail;
    }
    
    /** Getter for property testsPass.
     * @return Value of property testsPass.
     */
    public long getTestsPass() {
        return xmlat_testsPass;
    }

    /** Setter for property testsPass.
     * @param testsPass New value of property testsPass.
     */
    public void setTestsPass(long testsPass) {
        xmlat_testsPass = testsPass;
    }

    /** Getter for property testsUnexpectedPass.
     * @return Value of property testsUnexpectedPass.
     */
    public long getTestsUnexpectedPass() {
        return xmlat_testsUnexpectedPass;
    }
    
    /** Setter for property testsUnexpectedPass.
     * @param testsPass New value of property testsUnexpectedPass.
     */
    public void setTestsUnexpectedPass(long testsUnexpectedPass) {
        xmlat_testsUnexpectedPass = testsUnexpectedPass;
    }

    /** Getter for property testsExpectedFail.
     * @return Value of property testsExpectedFail.
     */
    public long getTestsExpectedFail() {
        return xmlat_testsExpectedFail;
    }
    
    /** Setter for property testsExpectedFail.
     * @param testsExpectedFail New value of property testsExpectedFail.
     */
    public void setTestsExpectedFail(long testsExpectedFail) {
        xmlat_testsExpectedFail = testsExpectedFail;
    }

    /** Getter for property testsTotal.
     * @return Value of property testsTotal.
     */
    public long getTestsTotal() {
        return xmlat_testsTotal;
    }
    
    /** Setter for property testsTotal.
     * @param testsTotal New value of property testsTotal.
     */
    public void setTestsTotal(long testsTotal) {
        xmlat_testsTotal = testsTotal;
    }
    
    /** Getter for property runID.
     * @return Value of property runID.
     */
    public String getRunID() {
        return xmlat_runID;
    }
    
    /** Setter for property runID.
     * @param runID New value of property runID.
     */
    public void setRunID(String runID) {
        xmlat_runID = runID;
    }
    
    /** Getter for property XTestResultsReport_id.
     * @return Value of property XTestResultsReport_id.
     */
    public long getXTestResultsReport_id() {
        return this.XTestResultsReport_id;
    }
    
    /** Setter for property XTestResultsReport_id.
     * @param XTestResultsReport_id New value of property XTestResultsReport_id.
     */
    public void setXTestResultsReport_id(long XTestResultsReport_id) {
        this.XTestResultsReport_id = XTestResultsReport_id;
    }
    
    /** Setter for property moduleErrors.
     * @param moduleErrors New value of property moduleErrors.
     */
    public void addModuleError(ModuleError moduleError) {
        //xml_cdata = moduleErrors;
        if (xmlel_ModuleError == null) {
            xmlel_ModuleError = new ModuleError[] {moduleError};
        } else {
            ModuleError new_ModuleError[] = new ModuleError[xmlel_ModuleError.length+1];
            for (int i=0; i<xmlel_ModuleError.length; i++)
                new_ModuleError[i] = xmlel_ModuleError[i];
            new_ModuleError[xmlel_ModuleError.length] = moduleError;
            xmlel_ModuleError = new_ModuleError;
        }
    }
    
    // XML attributes
    public java.sql.Timestamp      xmlat_timeStamp;
    public long     xmlat_time;
    public String   xmlat_config;
    public String   xmlat_name;
    public String   xmlat_attributes;
    public long     xmlat_testsTotal;
    public long     xmlat_testsPass;
    public long     xmlat_testsFail;
    public long     xmlat_testsUnexpectedPass;
    public long     xmlat_testsExpectedFail;
    public long     xmlat_testsError;
    public String   xmlat_runID;
    
    // flag indicating whether this testreport includes
    // logs from ant running tests
    public boolean  xmlat_antLogs=false;
    
    // child elements
    public TestBag[]    xmlel_TestBag;
    public ModuleError[] xmlel_ModuleError;

    /** Holds value of property XTestResultsReport_id. */
    private long XTestResultsReport_id;    
    
 
    // load TestRun from a file
    public static TestRun loadFromFile(File aFile) throws IOException, ClassNotFoundException {
        XMLBean xmlBean = XMLBean.loadXMLBean(aFile);
        if (!(xmlBean instanceof TestRun)) {
            throw new ClassNotFoundException("Loaded file "+aFile+" does not contain TestRun");
        }
        return (TestRun)xmlBean;
    }

    
}
