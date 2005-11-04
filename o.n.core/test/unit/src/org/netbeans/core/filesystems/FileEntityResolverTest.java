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

package org.netbeans.core.filesystems;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.jar.Attributes;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.core.LoaderPoolNode;
import org.netbeans.core.startup.MainLookup;
import org.netbeans.core.startup.ManifestSection;
import org.netbeans.junit.NbTestCase;
import org.openide.ErrorManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.LocalFileSystem;
import org.openide.filesystems.MIMEResolver;
import org.openide.filesystems.Repository;
import org.openide.loaders.DataLoaderPool;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.Lookup.Template;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/** Checking the behaviour of entity resolvers.
 *
 * @author Jaroslav Tulach
 */
public class FileEntityResolverTest extends org.netbeans.core.LoggingTestCaseHid 
implements LookupListener, ChangeListener {
    private FileObject fo;
    private Lenka loader;
    private ManifestSection.LoaderSection ls;
    private Lookup.Result mimeResolvers;
    private int change;
    private int poolChange;
    private static ErrorManager err;
    
    public FileEntityResolverTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        clearWorkDir();
        
        err = ErrorManager.getDefault().getInstance("TEST-" + getName());
        
        DataLoaderPool.getDefault().addChangeListener(this);
        
        org.netbeans.core.startup.Main.getModuleSystem();
        
        Thread.sleep(2000);
        
        assertEquals("No change in pool during initialization of module system", 0, poolChange);
        
        LocalFileSystem lfs = new LocalFileSystem();
        lfs.setRootDirectory(getWorkDir());
        
        fo = FileUtil.createData(lfs.getRoot(), "X.lenka");
        
        Attributes at = new Attributes();
        at.putValue("OpenIDE-Module-Class", "Loader");
        String name = Lenka.class.getName().replace('.', '/') + ".class";
        ls = (ManifestSection.LoaderSection)ManifestSection.create(name, at, null);
        LoaderPoolNode.add(ls);
        
        loader = (Lenka) Lenka.getLoader(Lenka.class);
        
        mimeResolvers = Lookup.getDefault().lookup(new Lookup.Template(MIMEResolver.class));
        mimeResolvers.addLookupListener(this);
    }

    protected void tearDown() throws Exception {
        LoaderPoolNode.remove(loader);
    }

    public void testNewResolverShallInfluenceExistingDataObjects() throws Exception {
        DataObject old = DataObject.find(fo);
        if (old.getLoader() == loader) {
            fail("The should be taken be default loader: " + old);
        }
        if (old.getClass() == MultiDataObject.class) {
            fail("The should be taken be default loader: " + old);
        }
        
        assertEquals("No changes in lookup yet", 0, change);
        
        err.log("starting to create the resolver");
        FileObject res = FileUtil.createData(
            Repository.getDefault().getDefaultFileSystem().getRoot(), 
            "Services/MIMEResolver/Lenkaresolver.xml"
        );
        err.log("file created: " + res);
        org.openide.filesystems.FileLock l = res.lock();
        OutputStream os = res.getOutputStream(l);
        err.log("stream opened");
        PrintStream ps = new PrintStream(os);
        
        ps.println("<?xml version='1.0' encoding='UTF-8'?>");
        ps.println("<!DOCTYPE MIME-resolver PUBLIC '-//NetBeans//DTD MIME Resolver 1.0//EN' 'http://www.netbeans.org/dtds/mime-resolver-1_0.dtd'>");
        ps.println("<MIME-resolver>");
        ps.println("    <file>");
        ps.println("        <ext name='lenka'/>");
        ps.println("        <resolver mime='hodna/lenka'/>");
        ps.println("    </file>");
        ps.println("</MIME-resolver>");

        err.log("Content written");
        os.close();
        err.log("Stream closed");
        l.releaseLock();
        err.log("releaseLock");
        
        err.log("Let's query the resolvers");
        Collection isthere = mimeResolvers.allInstances();
        err.log("What is the result: " + isthere);
        assertEquals("resolver found", 1, change);
        
        err.log("Waiting till finished");
        LoaderPoolNode.waitFinished();
        err.log("Waiting done, querying the data object");
        
        err.log("Clear the mime type cache in org.openide.filesystems.MIMESupport: " + fo.getFileSystem().getRoot().getMIMEType());
        
        DataObject now = DataObject.find(fo);
        
        err.log("Object is here: " + now);
        assertEquals("Loader updated to lenka", loader, now.getLoader());fail("Ok");
    }

    public void resultChanged(LookupEvent ev) {
        err.notify(err.INFORMATIONAL, new Exception("change in lookup"));
        change++;
    }

    public void stateChanged(ChangeEvent e) {
        poolChange++;
    }
    
    public static final class Lenka extends UniFileLoader {
        public Lenka() {
            super(MultiDataObject.class.getName());
        }
        
        protected MultiDataObject createMultiObject(FileObject primaryFile) throws DataObjectExistsException, IOException {
            return new MultiDataObject(primaryFile, this);
        }

        protected void initialize() {
            getExtensions().addMimeType("hodna/lenka");
            super.initialize();
        }

        protected FileObject findPrimaryFile(FileObject fo) {
            err.log("findPrimaryFile: " + fo + " with mime: " + fo.getMIMEType());
            FileObject retValue;
            retValue = super.findPrimaryFile(fo);
            err.log("findPrimaryFile result: " + retValue);
            return retValue;
        }
        
    }
}
