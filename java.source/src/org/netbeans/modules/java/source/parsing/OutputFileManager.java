/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.java.source.parsing;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.JavaFileObject;
import org.netbeans.api.java.classpath.ClassPath;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author Tomas Zezula
 */
public class OutputFileManager extends CachingFileManager {

    private static boolean debug = Boolean.getBoolean("org.netbeans.modules.java.source.parsing.OutputFileManager.debug");      //NOI18N

    private FileObject[] sourceRoots;
    
    /** Creates a new instance of CachingFileManager */
    public OutputFileManager(CachingArchiveProvider provider, final ClassPath outputClassPath, final ClassPath sourcePath) {
        super (provider, outputClassPath, false);
	assert sourcePath != null && outputClassPath != null;
	this.sourceRoots = sourcePath.getRoots();
    }
    
    public @Override JavaFileObject getJavaFileForOutput( Location l, String className, JavaFileObject.Kind kind, javax.tools.FileObject sibling ) 
        throws IOException, UnsupportedOperationException, IllegalArgumentException {
        
        
        if (kind != JavaFileObject.Kind.CLASS) {
            throw new IllegalArgumentException ();
        }
        else {	                            
            int index;
            if (sibling != null) {
                index = getActiveRoot (sibling);
            }
            else {
                index = getActiveRoot (FileObjects.convertPackage2Folder(className));
            }
            assert index >= 0 && index < this.files.length;
            File activeRoot = this.files[index];
            String baseName = className.replace('.', File.separatorChar);       //NOI18N
            String nameStr = baseName + '.' + FileObjects.SIG;
            int nameComponentIndex = nameStr.lastIndexOf(File.separatorChar);            
            if (nameComponentIndex != -1) {
                String pathComponent = nameStr.substring(0, nameComponentIndex);
                new File (activeRoot, pathComponent).mkdirs();
            }
            else {
                activeRoot.mkdirs();
            }                                                            
            File f = FileUtil.normalizeFile(new File (activeRoot, nameStr));
            return OutputFileObject.create (activeRoot, f);
        }
    }    
        
    public @Override javax.tools.FileObject getFileForOutput( Location l, String pkgName, String relativeName, javax.tools.FileObject sibling )
        throws IOException, UnsupportedOperationException, IllegalArgumentException {
        assert pkgName != null;
        assert relativeName != null;
        if (sibling == null) {
            throw new IllegalArgumentException ("sibling == null");
        }        
        final int index = getActiveRoot (sibling);
        assert index >= 0 && index < this.files.length;
        File folder;
        if (pkgName.length() == 0) {
            folder = this.files[index];
        }
        else {
            folder = new File (this.files[index],FileObjects.convertPackage2Folder(pkgName));
        }
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                throw new IOException ();
            }
        }
        File file = new File (folder,relativeName);
        return OutputFileObject.create (this.files[index],file);
    }
        
        
    
    private int getActiveRoot (final javax.tools.FileObject file) throws IOException {
        if (this.sourceRoots.length == 1) {
            return 0;
        }
        for (int i = 0; i< sourceRoots.length; i++) {
            if (isParentOf(sourceRoots[i], file.toUri().toURL())) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean isParentOf (FileObject folder, final URL file) throws IOException {
        assert folder != null && file != null;
        URL folderUrl = folder.getURL();
        return file.toExternalForm().startsWith(folderUrl.toExternalForm());
    }
    
    private int getActiveRoot (String baseName) {
        if (sourceRoots.length == 1) {
            return 0;
        }
        String name, parent = null;
	int index = baseName.lastIndexOf('/');              //NOI18N        
	if (index<0) {
            name = baseName;	    
	}
	else {
            parent = baseName.substring(0, index);
            name = baseName.substring(index+1);
	}
        index = name.indexOf('$');                          //NOI18N
	if (index > 0) {
	    name = name.substring(0,index);
	}
        
        for (int i=0; i<this.sourceRoots.length; i++) {
            FileObject parentFile = sourceRoots[i].getFileObject(parent);
            if (parentFile != null) {
                if (parentFile.getFileObject(name, FileObjects.JAVA) != null) {
                    return i;
                }
            }
        }        
	return -1;
    }
    
    private static boolean debug (String message) {
        if (debug) {
            Logger.getLogger("global").log(Level.INFO, message);
        }
        return true;
    }
    
}
