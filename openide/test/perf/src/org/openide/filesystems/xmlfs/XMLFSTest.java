/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2000 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.openide.filesystems.xmlfs;

import java.io.*;

import org.openide.*;
import org.openide.loaders.*;
import org.openide.filesystems.*;
import org.openide.filesystems.localfs.LocalFSTest;
import org.openide.filesystems.data.SerialData;

/**
 * Base class for testing XMLFileSystem.
 */
public class XMLFSTest extends ReadOnlyFSTest {
    
    public static final String PACKAGE = "org/openide/filesystems/data/";
    private static final String MF_NAME = "mf-layer";
    private static final String HEADER = "<?xml version=\"1.0\"?>";//\n<!DOCTYPE filesystem PUBLIC \"-//NetBeans//DTD Filesystem 1.0//EN\" \"http://www.netbeans.org/dtds/filesystem-1_0.dtd\">";
    private static final String FOLDER_START = "  <folder name=\"org\">\n    <folder name=\"openide\">\n      <folder name=\"filesystems\">\n        <folder name=\"data\">";
    private static final String FOLDER_END = "        </folder>\n      </folder>\n    </folder>\n  </folder>\n";
    private static final int FOLDER_INDENT  = FOLDER_END.indexOf('<') / 2;
    private static final String INDENT_STEP  = "  ";
    
    /** Root folder for this test */
    protected File tmp;
    /** Working folder */
    protected File destFolder;
    /** Tested XMLFS */
    protected XMLFileSystem xmlfs;
    
    /** Creates new XMLFSGenerator */
    public XMLFSTest(String name) {
        super(name);
    }

    /** Set up given number of FileObjects */
    protected FileObject[] setUpFileObjects(int foCount) throws Exception {
        tmp = createTempFolder();
        destFolder = LocalFSTest.createFiles(foCount, 0, tmp);
        File xmlbase = generateXMLFile(destFolder, foCount, 0, LocalFSTest.RES_EXT);
        xmlfs = new XMLFileSystem();
        xmlfs.setXmlUrl(xmlbase.toURL(), false);
        
        FileObject pkg = xmlfs.findResource(PACKAGE);
        return pkg.getChildren();
    }
    
    /** Disposes given FileObjects */
    protected void tearDownFileObjects(FileObject[] fos) throws Exception {
        destFolder = null;
        delete(tmp);
        tmp = null;
    }
    
    /** Generates an XML file that describes a filesystem structure.
     * @param folder - where to place the file
     * @param fileno - how many files should be in that filesystem
     * @param base
     */
    public static final File generateXMLFile(File folder, int fileNo, int base, String resExt) throws Exception {
        String name = MF_NAME + '-' + String.valueOf(base);
        File dest = new File(folder, name.concat(".xml"));
        
        OutputStream os = new FileOutputStream(dest);
        Writer writer = new OutputStreamWriter(os);
        writer.write(generate(fileNo, base, resExt));
        writer.close();
        os.close();
        
        return dest;
    }
    
    /** Generates an XML file that describes a filesystem structure.
     * @param fileno - how many files should be in that filesystem
     * @param base
     * @return a String that is an xml document describing a filesystem
     */
    public static String generate(int fileNo, int base, String resExt) throws Exception {
        StringBuffer buffer = new StringBuffer(50000);
        buffer.append(HEADER).append('\n');
        buffer.append("<filesystem>").append('\n');
        generateFolder(buffer, fileNo, base, resExt);
        buffer.append("</filesystem>").append('\n');
        
        return buffer.toString();
    }
    
    /** Generates an XML description of a folder inside a filesystem structure.
     * @param fileno - how many files should be in that filesystem
     * @param buffer - where to place the description
     * @param base
     */
    private static final void generateFolder(StringBuffer buffer, int fileNo, int base, String resExt) throws Exception {
        buffer.append(FOLDER_START);
        generateFiles(buffer, fileNo, base, resExt);
        buffer.append(FOLDER_END);
    }

    /** Generates an XML description of files inside a folder structure.
     * @param fileno - how many files should be in that filesystem
     * @param buffer - where to place the description
     * @param base
     */
    private static final void generateFiles(StringBuffer buffer, final int fileNo, final int base, String resExt) throws Exception {
        int paddingSize = Utilities.expPaddingSize(fileNo);
        for (int i = 0; i < fileNo; i++) {
            generateOneFile(buffer, paddingSize, base + i, resExt);
        }
    }
    
    /** Generates an XML description of a file inside a folder structure.
     * @param buffer - where to place the description
     * @param paddingSize - number of digits used for this file, i.e.
     * base is 793 and paddingSize is 5 so the generated file name ends with 00793.
     * @param base
     */
    private static void generateOneFile(StringBuffer buffer, int paddingSize, int fileBase, String resExt) throws Exception {
        buffer.append('\n');
        String fname = generateOneFileString(paddingSize, fileBase, resExt);
        addFileHeader(buffer, fname);
        generateAttributes(buffer, paddingSize);
        addFileEnd(buffer);
    }
    
    /** Generates an XML description of attributes inside a file description.
     * @param buffer - where to place the description
     */
    private static void generateAttributes(StringBuffer buffer, int paddingSize) throws Exception {
        generateSerialAttr(buffer);
        for (int i = 0; i < 5; i++) {
            generateStringAttr(buffer, i, paddingSize);
        }
    }
    
    /** Generates a serial attribute inside a file description.
     * @param buffer - where to place the description
     */
    private static void generateSerialAttr(StringBuffer buffer) throws Exception {
        addIndent(buffer, FOLDER_INDENT + 2);
        buffer.append("<attr name=\"NetBeansAttrSerial\" serialvalue=\"").append(SerialData.getSerialDataString()).append("\"/>");
        buffer.append('\n');
    }
    
    /** Generates i-th String attribute inside a file description.
     * @param buffer - where to place the description
     */
    private static void generateStringAttr(StringBuffer buffer, int i, int paddingSize) {
        addIndent(buffer, FOLDER_INDENT + 2);
        buffer.append("<attr name=\"key_");
        Utilities.appendNDigits(i, paddingSize, buffer);
        buffer.append("\" stringvalue=\"val_");
        Utilities.appendNDigits(i, paddingSize, buffer);
        buffer.append("\"/>");
        buffer.append('\n');
    }
    
    /** Generates file end inside a folder description.
     * @param buffer - where to place the description
     */
    private static void addFileEnd(StringBuffer buffer) {
        addIndent(buffer, FOLDER_INDENT + 1);
        buffer.append("</file>");
        buffer.append('\n');
    }
    
    /** Generates file start inside a folder description.
     * @param buffer - where to place the description
     */
    private static void addFileHeader(StringBuffer buffer, String fname) {
        addIndent(buffer, FOLDER_INDENT + 1);
        buffer.append("<file name=\"").append(fname).append("\" url=\"").append(fname).append("\">");
        buffer.append('\n');
    }
    
    /** Adds indent
     * @param buffer - where to place the description
     */
    private static void addIndent(StringBuffer buffer, int howMuch) {
        for (int i = 0; i < howMuch; i++) {
            buffer.append(INDENT_STEP);
        }
    }
    
    /** Generates string that describes one file via XML */
    private static String generateOneFileString(int paddingSize, int base, String resExt) {
        StringBuffer sbuffer = new StringBuffer(20);
        sbuffer.append(LocalFSTest.RES_NAME);
        Utilities.appendNDigits(base, paddingSize, sbuffer);
        sbuffer.append(resExt);
        return sbuffer.toString();
    }

    /*
     public static void main(String[] args) throws Exception {
        XMLFSTest xmlfstest = new XMLFSTest("first test");
        xmlfstest.setUpFileObjects(2500);
    }
     */    
}
