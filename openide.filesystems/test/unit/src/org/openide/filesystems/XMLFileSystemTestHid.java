/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2005 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.openide.filesystems;

import java.io.IOException;
import java.io.File;

public class XMLFileSystemTestHid extends TestBaseHid {
    private String[] resources = new String[] {"a/b/c"};
    XMLFileSystem xfs = null;
    
    public XMLFileSystemTestHid(String testName) {
        super(testName);
    }
    
    protected String[] getResources (String testName) {        
        return resources;
    }    
    
   
    public void testReset () throws Exception {        
        FileObject a = xfs.findResource("a");
        assertNotNull(a);
        

        FileChangeAdapter fcl = new FileChangeAdapter();
        a.addFileChangeListener(fcl);
        
        resources = new String[] {"a/b/c","a/b1/c"};        
        xfs.setXmlUrl(createXMLLayer().toURL());
        
        FileObject b1 = xfs.findResource("a/b1");
        assertNotNull(b1);                
        assertTrue(b1.isFolder());        
    }
    
    protected void setUp() throws Exception {
        super.setUp();
        
        File f = createXMLLayer();
        xfs = new XMLFileSystem ();
        xfs.setXmlUrl(f.toURL());
        this.testedFS = xfs;
    }

    private File createXMLLayer() throws IOException {
        String testName = getName();
        File f = TestUtilHid.createXMLLayer(testName, getResources(testName));
        return f;
    }

    public void testChangesAreFiredOnSetXMLUrlsIssue59160() throws Exception {
        File f = writeFile ("layer1.xml", 
"<filesystem>\n" +
    "<folder name='TestModule'>\n" +
        "<file name='sample.txt' >Ahoj</file>\n" +
    "</folder>\n" +
"</filesystem>\n"
        );
        
        File f2 = writeFile ("layer2.xml", 
"<filesystem>\n" +
    "<folder name='TestModule'>\n" +
        "<file name='sample.txt' >Hello!</file>\n" +
    "</folder>\n" +
"</filesystem>\n"
        );

        
        
        
        xfs = new XMLFileSystem (f.toURL());
        
        FileObject fo = xfs.findResource ("TestModule/sample.txt");
        assertEquals ("Four bytes there", 4, fo.getSize ());
        registerDefaultListener (fo);
        
        xfs.setXmlUrl (f2.toURL ());
        
        assertEquals ("Six bytes there", 6, fo.getSize ());
        
        fileChangedAssert ("Change in the content", 1);
    }

    public void testChangesAreFiredOnSetXMLUrlsWithURLsIssue59160() throws Exception {
        File u1 = writeFile("u1.txt", "Ahoj");
        File u2 = writeFile("u2.txt", "Hello!");
        
        File f = writeFile ("layer1.xml", 
"<filesystem>\n" +
    "<folder name='TestModule'>\n" +
        "<file name='sample.txt' url='u1.txt' />\n" +
    "</folder>\n" +
"</filesystem>\n"
        );
        
        File f2 = writeFile ("layer2.xml", 
"<filesystem>\n" +
    "<folder name='TestModule'>\n" +
        "<file name='sample.txt' url='u2.txt' />\n" +
    "</folder>\n" +
"</filesystem>\n"
        );

        
        
        
        xfs = new XMLFileSystem (f.toURL());
        
        FileObject fo = xfs.findResource ("TestModule/sample.txt");
        assertEquals ("Four bytes there", 4, fo.getSize ());
        registerDefaultListener (fo);
        
        xfs.setXmlUrl (f2.toURL ());
        
        assertEquals ("Six bytes there", 6, fo.getSize ());
        
        fileChangedAssert ("Change in the content", 1);
    }
    
    public void testChangesAreFiredOnSetXMLUrlsWithAttributesIssue21204() throws Exception {
        File f = writeFile ("layer1.xml", 
"<filesystem>\n" +
    "<folder name='TestModule'>\n" +
        "<file name='sample.txt' >\n" +
        "  <attr name='value' stringvalue='old' />\n" +
        "</file>\n" +
    "</folder>\n" +
"</filesystem>\n"
        );
        
        File f2 = writeFile ("layer2.xml", 
"<filesystem>\n" +
    "<folder name='TestModule'>\n" +
        "<file name='sample.txt' >\n" +
        "  <attr name='value' stringvalue='new' />\n" +
        "</file>\n" +
    "</folder>\n" +
"</filesystem>\n"
        );

        
        
        
        xfs = new XMLFileSystem (f.toURL());
        
        FileObject fo = xfs.findResource ("TestModule/sample.txt");
        assertEquals("Old value is in the attribute", "old", fo.getAttribute("value"));
        registerDefaultListener (fo);
        
        xfs.setXmlUrl (f2.toURL ());

        assertEquals("New value is in the attribute", "new", fo.getAttribute("value"));
        fileAttributeChangedAssert("Change in the content", 1);
    }
    
    public void testChangesAreFiredOnSetXMLUrlsEvenWhenRemoved() throws Exception {
        File u1 = writeFile("u1.txt", "Ahoj");
        
        File f = writeFile("layer1.xml",
                "<filesystem>\n" +
                "<folder name='TestModule'>\n" +
                "<file name='sample.txt' url='u1.txt' />\n" +
                "</folder>\n" +
                "</filesystem>\n"
                );
        
        File f2 = writeFile("layer2.xml",
                "<filesystem>\n" +
                "</filesystem>\n"
                );
        
        
        
        
        xfs = new XMLFileSystem(f.toURL());
        
        FileObject fo = xfs.findResource("TestModule/sample.txt");
        assertEquals("Four bytes there", 4, fo.getSize());
        registerDefaultListener(fo);
        
        xfs.setXmlUrl(f2.toURL());
        
        assertFalse("Valid no more", fo.isValid());
        assertEquals("Empty now", 0, fo.getSize());
        
        fileDeletedAssert("Change in the content", 1);
    }
    
    public void testIssue62570() throws Exception {
        File f = writeFile("layer3.xml",
                "<filesystem>\n" +
                "<folder name='TestModule'>\n" +
                "<file name='sample.txt' >Ahoj</file>\n" +
                "<file name='sample2.txt' url='sample2.txt'/>\n" +
                "</folder>\n" +
                "</filesystem>\n"
                );
              
        File f2 = new File(f.getParentFile(), "sample2.txt");        
        if (!f2.exists()) {
            Thread.sleep(3000);
            assertTrue(f2.createNewFile());
        }
        xfs = new XMLFileSystem(f.toURL());
        FileObject fo = xfs.findResource ("TestModule/sample.txt");
        assertNotNull(fo);
        assertEquals(fo.lastModified().getTime(), f.lastModified());        
        
        FileObject fo2 = xfs.findResource ("TestModule/sample2.txt");
        assertNotNull(fo2);
        assertEquals(fo2.lastModified().getTime(), f2.lastModified());        
        assertFalse(fo2.lastModified().equals(fo.lastModified()));        
        
    }
    
    private File writeFile(String name, String content) throws IOException {
        File f = new File (getWorkDir (), name);
        java.io.FileWriter w = new java.io.FileWriter (f);
        w.write(content);
        w.close();
        return f;
    }
}
