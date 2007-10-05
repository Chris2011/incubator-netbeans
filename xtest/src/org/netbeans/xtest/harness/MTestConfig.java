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
 * MTestConfig.java
 *
 * Created on March 25, 2003, 2:36 PM
 */

package org.netbeans.xtest.harness;

import java.io.*;
import java.util.*;

import org.netbeans.xtest.xmlserializer.*;
import org.netbeans.xtest.util.SerializeDOM;
import org.netbeans.xtest.XTestEntityResolver;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author  mb115822
 */
public class MTestConfig implements XMLSerializable {

    private String name;
    private Testbag testbags[];
    private AntExecType executors[];
    private AntExecType compilers[];
    private AntExecType resultsprocessors[];
    
    private AntExecType defaultExecutor;
    private AntExecType defaultCompiler;
    private AntExecType defaultResultsprocessor;
    
    private Hashtable executors_table = new Hashtable();
    private Hashtable compilers_table = new Hashtable();
    private Hashtable resultsprocessors_table = new Hashtable();
    
    private Testbag filtered_testbags[];
    
    private String additionalIncludes[];
    private String additionalExcludes[];
    
    private String testtype;
    
    
    static ClassMappingRegistry classMappingRegistry = new ClassMappingRegistry(MTestConfig.class);
    static {
        try {
            // load global registry
            GlobalMappingRegistry.registerClassForElementName("mconfig", MTestConfig.class);
            // register this classMTestConfig
            classMappingRegistry.registerSimpleField("name",ClassMappingRegistry.ATTRIBUTE,"name");
            classMappingRegistry.registerContainerField("testbags","testbag",ClassMappingRegistry.DIRECT);
            classMappingRegistry.registerContainerField("executors","executor",ClassMappingRegistry.DIRECT);
            classMappingRegistry.registerContainerField("compilers","compiler",ClassMappingRegistry.DIRECT);
            classMappingRegistry.registerContainerField("resultsprocessors","resultsprocessor",ClassMappingRegistry.DIRECT);
        } catch (MappingException me) {
            me.printStackTrace();
            classMappingRegistry = null;
        }
    }
    
    public ClassMappingRegistry registerXMLMapping() {
        return classMappingRegistry;
    }        
    
    // empty constructor - required by XMLSerializer
    public MTestConfig() {}
    
    public String getName() {
        return name;
    }
    
    public void setTesttype(String testtype) {
        this.testtype = testtype;
    }
    
    public String getTesttype () {
        return testtype;
    }
    
    public void setAdditionalIncludes(String in[]) {
        additionalIncludes = in;
    }

    public void setAdditionalExcludes(String ex[]) {
        additionalExcludes = ex;
    }
    
    public String[] getAdditionalIncludes() {
        return additionalIncludes;
    }

    public String[] getAdditionalExcludes() {
        return additionalExcludes;
    }
    
    public void setTestbags(Testbag[] testbags) {
        this.testbags = testbags;
    }
    
    public Testbag[] getTestbags() {
        return testbags;
    }
    
    public Testbag[] getFilteredTestbags() {
        return filtered_testbags;
    }
    
    public Testbag createSingleTestbag(String testset_dir, Vector test_includes) throws XMLSerializeException {
        System.out.println("Preparing single testbag with default executor/compiler/result processor");
        if (defaultExecutor == null)
            throw new XMLSerializeException("You have to set default executor when executing single test.");
        if (defaultCompiler == null)
            throw new XMLSerializeException("You have to set default compiler when executing single test.");
        // results processor settings are no longer used - always a corresponding result processor for executor is used
        /*
        if (defaultResultsprocessor == null)
            throw new XMLSerializeException("You have to set default resultsprocessor when executes single test.");
         */
        Testbag testbag = new Testbag();
        testbag.setName("Autogenerated single testbag");
        testbag.setAntExecutor(defaultExecutor);
        testbag.setAntCompiler(defaultCompiler);
        //testbag.setAntResultsprocessor(defaultResultsprocessor);
        
        Testbag.Testset testset = new Testbag.Testset();
        testset.setDir(testset_dir);
        Testbag.Patternset patternset = new Testbag.Patternset();

        Testbag.InExclude includes[] = new Testbag.InExclude[test_includes.size()];
        for (int i=0; i<test_includes.size(); i++) {
            includes[i] = new Testbag.InExclude();
            includes[i].setName((String)test_includes.get(i));
        }
        patternset.setIncludes(includes);
        testset.setPatternsets(new Testbag.Patternset[] { patternset });
        testset.filterPatternsets(null);
        testbag.setTestsets(new Testbag.Testset[] { testset });
        // set parents 
        testset.setParent(testbag);
        testbag.setParent(this);
        return testbag;
    }
    
    public static MTestConfig loadConfig(File configFile) throws XMLSerializeException {    
        return loadConfig(configFile, null, null, null, null);
    }
    
    public static MTestConfig loadConfig(File configFile, HashSet passed_patternattribs, HashSet passed_testattribs, 
                                         HashSet passed_executors, HashSet passed_testbags) throws XMLSerializeException {  
        System.out.println("Loading config...");                                         
        if (!configFile.isFile()) {
            throw new XMLSerializeException("Cannot load config from file "+configFile.getPath()+", file does not exist");
        }
        try {
            DocumentBuilder db = SerializeDOM.getDocumentBuilder();
            db.setEntityResolver(new XTestEntityResolver());
            Document doc = db.parse(configFile);
            
            XMLSerializable xmlObject = XMLSerializer.getXMLSerializable(doc);
            if (xmlObject instanceof MTestConfig) {
                MTestConfig config = (MTestConfig)xmlObject;
                config.validateAndSetup(passed_patternattribs);
                config.sortTestbagsByPriority();
                if (passed_testattribs != null || passed_executors != null || passed_testbags != null)
                    config.filterTestbag(passed_testattribs, passed_executors, passed_testbags);
                return config;
            }
        }
        catch (SAXException saxe) {
            throw new XMLSerializeException("SAXException thrown when loading config file "+configFile.getPath()+": "+saxe.getMessage(),saxe);
        }
        catch (IOException ioe) {
            throw new XMLSerializeException("IOException thrown when loading config file "+configFile.getPath()+": "+ioe.getMessage(),ioe);
        }
        // xmlobject is not of required type
        throw new XMLSerializeException("Loaded xml document is not MTestConfig");
    }
    
    private void filterTestbag(HashSet passed_testattribs, HashSet passed_executors, HashSet passed_testbags) {
        Vector v = new Vector();
        for (int i=0; i<testbags.length; i++) {
            // compare passed attributes with attribs from this testbag,            
            AttribParser ap = new AttribParser( testbags[i].getTestattribs(), passed_testattribs );

            // test if tbg_exec is in passed_executors too
            if ( (!passed_testbags.isEmpty() && passed_testbags.contains(testbags[i].getName())) ||
                 (passed_testbags.isEmpty() && ap.parse() && (passed_executors.contains(testbags[i].getExecutor().getName()) || passed_executors.isEmpty()))) {
                     v.add(testbags[i]);
            }
        }
        filtered_testbags = (Testbag[])v.toArray(new Testbag[0]);
    }
    
    private void sortTestbagsByPriority() {
        Arrays.sort(testbags, new Comparator() {
             public int compare(Object o1, Object o2) {
                 if (((Testbag)o1).getPrio() == null && ((Testbag)o2).getPrio() == null)
                     return 0;
                 if (((Testbag)o1).getPrio() == null)
                     return +1;
                 if (((Testbag)o2).getPrio() == null)
                     return -1;
                 if (((Testbag)o1).getPrio().intValue() < ((Testbag)o2).getPrio().intValue())
                     return -1;
                 if (((Testbag)o1).getPrio().intValue() > ((Testbag)o2).getPrio().intValue())
                     return +1;
                 return 0;
             }
        });
        
        
    }
    
    private void validateAndSetup(HashSet passed_patternset) throws XMLSerializeException {
        if (getName() == null)
            throw new XMLSerializeException("Attribute name is required for root element mconfig.");

        // This is not required when plugin are used !!! -- TBD !!!
        
        if (executors == null || executors.length == 0)
            throw new XMLSerializeException("At least one element executor is required.");
        if (compilers == null || compilers.length == 0) 
            throw new XMLSerializeException("At least one element compiler is required.");
        // result processos is no longer required
        if (resultsprocessors != null) {
            System.out.println("!!! Element resultprocessor is no longer used. Please delete it.");
        }
        /*
        if (resultsprocessors == null || resultsprocessors.length == 0)
            throw new XMLSerializeException("At least one element resultsprocessor is required.");
         */
        
        if (testbags == null || testbags.length == 0)
            throw new XMLSerializeException("At least one element testbag is required.");
        
        
        defaultExecutor = processAntExecTypes(executors, executors_table, "executor");
        defaultCompiler = processAntExecTypes(compilers, compilers_table, "compiler");
        //defaultResultsprocessor = processAntExecTypes(resultsprocessors, resultsprocessors_table, "resultsprocessor");

        for (int i=0; i<testbags.length; i++) {
            testbags[i].setParent(this);
            testbags[i].setAntExecutor(getTestbagAntExecType(testbags[i].getExecutorName(), defaultExecutor, executors_table, "executor",testbags[i].getPluginName()));
            testbags[i].setAntCompiler(getTestbagAntExecType(testbags[i].getCompilerName(), defaultCompiler, compilers_table, "compiler",testbags[i].getPluginName()));            
            //testbags[i].setAntResultsprocessor(getTestbagAntExecType(testbags[i].getResultsprocessorName(), defaultResultsprocessor, resultsprocessors_table, "resultsprocessor",testbags[i].getPluginName()));
            testbags[i].validate(passed_patternset);
        }
        
    }
 
    /** Validate all AntExecTypes, find ones with duplicate names and create hastable where key is name.
     */
    private AntExecType processAntExecTypes(AntExecType types[], Hashtable types_table, String name) throws XMLSerializeException {
        if (types == null) 
            return null;
        AntExecType defaultType = null;
        for (int i=0; i<types.length; i++) {
            types[i].validate();
            AntExecType duplicate = (AntExecType)types_table.get(types[i].getName());
            if (duplicate != null) 
                throw new XMLSerializeException("Name of "+name+" must be unique. Found duplicate name "+types[i].getName()+".");
            types_table.put(types[i].getName(), types[i]);
            if (types[i].isDefault()) {
                if (defaultType != null)
                    throw new XMLSerializeException("Only one "+name+" can be set as default.");
                defaultType = types[i];
            }
        }
        return defaultType;
    }
    
    /** Find AntExecType for given type_name or return default i type_name is null.
     */
    private AntExecType getTestbagAntExecType(String type_name, AntExecType defaultType, Hashtable types_table, String name, String pluginName) throws XMLSerializeException {
        if (type_name == null) {
            if (defaultType == null) {
                if (name.equals("compiler")) {
                    return null;
                } else {
                    throw new XMLSerializeException("No default "+name+" was found.");
                }
            } else {
                if (pluginName == null) {
                    // if plugin is not defined - return default
                    return defaultType;
                } else {
                    // else each plugin have to contain at least one default action
                    return null;
                }
            }
        } else {
            AntExecType type = (AntExecType)types_table.get(type_name);
            if (type == null) {
                throw new XMLSerializeException("No "+name+" with name "+type_name+" was found.");    
            } else {
                return type;
            }
        }
    }
    
    // public inner classes    
    
    public static class AntExecType implements XMLSerializable {
        private String name;
        private String antfile;
        private String dir;
        private String target;
        private String def;

        static ClassMappingRegistry classMappingRegistry = new ClassMappingRegistry(MTestConfig.AntExecType.class);
        static {
            try {
                // register this class
                classMappingRegistry.registerSimpleField("name",ClassMappingRegistry.ATTRIBUTE,"name");
                classMappingRegistry.registerSimpleField("antfile",ClassMappingRegistry.ATTRIBUTE,"antfile");
                classMappingRegistry.registerSimpleField("dir",ClassMappingRegistry.ATTRIBUTE,"dir");
                classMappingRegistry.registerSimpleField("target",ClassMappingRegistry.ATTRIBUTE,"target");
                classMappingRegistry.registerSimpleField("def",ClassMappingRegistry.ATTRIBUTE,"default");
            } catch (MappingException me) {
                me.printStackTrace();
                classMappingRegistry = null;
            }
        }

        public ClassMappingRegistry registerXMLMapping() {
            return classMappingRegistry;
        }
        
        public String getName() {
            return name;
        }
        
        public String getAntFile() {
            return antfile;
        }
        
        public String getTarget() {
            return target;
        }
        
        public String getDir() {
            return dir;
        }
        
        protected void validate() throws XMLSerializeException {
            if (name == null)
                throw new XMLSerializeException("Attribute name is required for element executor/compiler.");
            if (def != null && !(def.equalsIgnoreCase("false") || def.equalsIgnoreCase("no") || def.equalsIgnoreCase("0") ||
                                 def.equalsIgnoreCase("true") || def.equalsIgnoreCase("yes") || def.equalsIgnoreCase("1")))
                throw new XMLSerializeException("Invalid value "+def+" in attribute default in element executor/compiler. Valid values are true, false, yes, no, 0 or 1.");
        }
        
        public boolean isDefault() {
            if (def != null && (def.equalsIgnoreCase("true") || def.equalsIgnoreCase("yes") || def.equalsIgnoreCase("1")))
                return true;
            return false;
        }
        
    }
    
    public void dump() {
        System.out.println("DUMP:");
        System.out.println("Name="+getName());
        Testbag testbags[] = getTestbags();
        if (testbags != null)
            for (int i=0; i<testbags.length;i++) {
                System.out.println("Testbags["+i+"].name="+testbags[i].getName());
                System.out.println("Testbags["+i+"].prio="+testbags[i].getPrio());
                Testbag.Testset testsets[] = testbags[i].getTestsets();
                for (int j=0; j<testsets.length; j++) {
                    System.out.println("  Testset["+j+"].dir="+testsets[j].getDir());
                    Testbag.Patternset patternsets[] = testsets[j].getPatternset();
                    if (patternsets != null) 
                      for (int k=0; k<patternsets.length; k++) {
                          System.out.println("    Patternset["+k+"]");
                          System.out.println("    Patternset["+k+"].patternattribs="+patternsets[k].getPatternattribs());
                    }
                }
            }
        if (executors != null)
            for (int i=0; i<executors.length;i++)
                System.out.println("Executors["+i+"].name="+executors[i].getName());        
        if (compilers != null)
            for (int i=0; i<compilers.length;i++)
                System.out.println("Compilers["+i+"].name="+compilers[i].getName());        
        /*
        if (resultsprocessors != null)
            for (int i=0; i<resultsprocessors.length;i++)
                System.out.println("Resultsprocessors["+i+"].name="+resultsprocessors[i].getName());        
         */
        
        
    }
}
