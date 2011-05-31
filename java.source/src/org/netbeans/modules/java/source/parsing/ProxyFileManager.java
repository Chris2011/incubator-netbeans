/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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

package org.netbeans.modules.java.source.parsing;

import com.sun.tools.javac.api.ClientCodeWrapper.Trusted;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.modules.java.source.util.Iterators;

/**
 *
 * @author Tomas Zezula
 */
@Trusted
public class ProxyFileManager implements JavaFileManager {


    private static final Location ALL = new Location () {
        public String getName() { return "ALL";}   //NOI18N

        public boolean isOutputLocation() { return false; }
    };

    /**
     * Workaround to allow Filer ask for getFileForOutput for StandardLocation.SOURCE_PATH
     * which is not allowed but Filer does not allow write anyway => safe to do it.
     */
    private static final Location SOURCE_PATH_WRITE = new Location () {
        @Override
        public String getName() { return "SOURCE_PATH_WRITE"; }  //NOI18N
        @Override
        public boolean isOutputLocation() { return false;}
    };

    private final JavaFileManager bootPath;
    private final JavaFileManager classPath;
    private final JavaFileManager sourcePath;
    private final JavaFileManager aptSources;
    private final MemoryFileManager memoryFileManager;
    private final JavaFileManager outputhPath;
    private final GeneratedFileMarker marker;
    private final SiblingSource siblings;
    private JavaFileObject lastInfered;
    private String lastInferedResult;
    

    private static final Logger LOG = Logger.getLogger(ProxyFileManager.class.getName());


    /** Creates a new instance of ProxyFileManager */
    public ProxyFileManager(final JavaFileManager bootPath,
            final JavaFileManager classPath,
            final JavaFileManager sourcePath,
            final JavaFileManager aptSources,
            final JavaFileManager outputhPath,
            final MemoryFileManager memoryFileManager,
            final GeneratedFileMarker marker,
            final SiblingSource siblings) {
        assert bootPath != null;
        assert classPath != null;
        assert memoryFileManager == null || sourcePath != null;
        assert marker != null;
        assert siblings != null;
        this.bootPath = bootPath;
        this.classPath = classPath;
        this.sourcePath = sourcePath;
        this.aptSources = aptSources;
        this.memoryFileManager = memoryFileManager;
        this.outputhPath = outputhPath;
        this.marker = marker;
        this.siblings = siblings;
    }

    private JavaFileManager[] getFileManager (final Location location) {
        if (location == StandardLocation.CLASS_PATH) {
            return this.outputhPath == null ?
                new JavaFileManager[] {this.classPath} :
                new JavaFileManager[] {this.classPath, this.outputhPath};
        }
        else if (location == StandardLocation.PLATFORM_CLASS_PATH) {
            return new JavaFileManager[] {this.bootPath};
        }
        else if (location == StandardLocation.SOURCE_PATH && this.sourcePath != null) {
            if (this.memoryFileManager != null) {
                return new JavaFileManager[] {
                    this.sourcePath,
                    this.memoryFileManager
                };
            }
            else {
                return new JavaFileManager[] {this.sourcePath};
            }
        }
        else if (location == StandardLocation.CLASS_OUTPUT && this.outputhPath != null) {
            return new JavaFileManager[] {this.outputhPath};
        }
        else if (location == StandardLocation.SOURCE_OUTPUT && this.aptSources != null) {
            return new JavaFileManager[] {this.aptSources};
        }
        else if (location == SOURCE_PATH_WRITE) {
            return new JavaFileManager[] {this.sourcePath};
        }
        else if (location == ALL) {
            return getAllFileManagers();
        }
        return new JavaFileManager[0];
    }

    private JavaFileManager[] getAllFileManagers () {
        List<JavaFileManager> result = new ArrayList<JavaFileManager> (4);
        if (this.sourcePath!=null) {
            result.add (this.sourcePath);
        }
        if (this.aptSources != null) {
            result.add (this.aptSources);
        }
        if (this.memoryFileManager != null) {
            result.add(this.memoryFileManager);
        }
        result.add(this.bootPath);
        result.add (this.classPath);
        if (this.outputhPath!=null) {
            result.add (this.outputhPath);
        }
        return result.toArray(new JavaFileManager[result.size()]);
    }

    public Iterable<JavaFileObject> list(Location l, String packageName, Set<JavaFileObject.Kind> kinds, boolean recurse) throws IOException {
        List<Iterable<JavaFileObject>> iterables = new LinkedList<Iterable<JavaFileObject>>();
        JavaFileManager[] fms = getFileManager (l);
        for (JavaFileManager fm : fms) {
            iterables.add( fm.list(l, packageName, kinds, recurse));
        }
        final Iterable<JavaFileObject> result = Iterators.chained(iterables);
        if (LOG.isLoggable(Level.FINER)) {
            final StringBuilder urls = new StringBuilder ();
            for (JavaFileObject jfo : result ) {
                urls.append(jfo.toUri().toString());
                urls.append(", ");  //NOI18N
            }
            LOG.finer(String.format("list %s package: %s type: %s found files: [%s]", l.toString(), packageName, kinds.toString(), urls.toString())); //NOI18N
        }
        return result;
    }

    public FileObject getFileForInput(Location l, String packageName, String relativeName) throws IOException {
        JavaFileManager[] fms = getFileManager(l);
        for (JavaFileManager fm : fms) {
            FileObject result = fm.getFileForInput(l, packageName, relativeName);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public FileObject getFileForOutput(Location l, String packageName, String relativeName, FileObject sibling) 
        throws IOException, UnsupportedOperationException, IllegalArgumentException {
        JavaFileManager[] fms = getFileManager(
                l == StandardLocation.SOURCE_PATH ?
                    SOURCE_PATH_WRITE : l);
        assert fms.length <=1;
        if (fms.length == 0) {
            return null;
        }
        else {
            return mark(fms[0].getFileForOutput(l, packageName, relativeName, sibling), l);
        }
    }

    public ClassLoader getClassLoader (Location l) {
        return null;
    }

    public void flush() throws IOException {
        JavaFileManager[] fms = getAllFileManagers ();
        for (JavaFileManager fm : fms) {
            fm.flush();
        }
    }

    public void close() throws IOException {
        JavaFileManager[] fms = getAllFileManagers ();
        for (JavaFileManager fm : fms) {
            fm.close();
        }
    }

    public int isSupportedOption(String string) {
        return -1;
    }

    public boolean handleOption (String current, Iterator<String> remains) {
        boolean isSourceElement;
        final Collection<String> defensiveCopy = copy(remains);
        if (AptSourceFileManager.ORIGIN_FILE.equals(current)) {
            final Iterator<String> it = defensiveCopy.iterator();
            if (!it.hasNext()) {
                throw new IllegalArgumentException("The apt-source-root requires folder.");    //NOI18N
            }
            final String sib = it.next();
            if(sib.length() != 0) {                
                siblings.push(asURL(sib));                
            } else {
                try {
                    markerFinished();
                } finally {
                    siblings.pop();
                }
            }
        } else if ((isSourceElement=AptSourceFileManager.ORIGIN_SOURCE_ELEMENT_URL.equals(current)) || 
                   AptSourceFileManager.ORIGIN_RESOURCE_ELEMENT_URL.equals(current)) {
            if (!defensiveCopy.isEmpty()) {
                URL bestSoFar = siblings.getProvider().getSibling();
                for  (String surl : defensiveCopy) {
                    if (FileObjects.JAVA.equals(FileObjects.getExtension(surl))) {
                        bestSoFar = asURL(surl);
                        break;
                    }
                }
                if (LOG.isLoggable(Level.INFO) && isSourceElement && defensiveCopy.size() > 1) {
                    final StringBuilder sb = new StringBuilder();
                    for (String surl : defensiveCopy) {
                        if (sb.length() > 0) {
                            sb.append(", ");    //NOI18N
                        }
                        sb.append(surl);
                    }
                    LOG.log(Level.FINE, "Multiple source files passed as ORIGIN_SOURCE_ELEMENT_URL: {0}; using: {1}",  //NOI18N
                            new Object[]{sb.toString(), bestSoFar});
                }
                siblings.push(bestSoFar);
            } else {
                try {
                    markerFinished();
                } finally {
                    siblings.pop();
                }
            }
        }
        for (JavaFileManager m : getFileManager(ALL)) {
            if (m.handleOption(current, defensiveCopy.iterator())) {
                return true;
            }
        }
        return false;
    }
    
    private static URL asURL(final String url) throws IllegalArgumentException {
        try {
            return new URL(url);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException("Invalid path argument: " + url);    //NOI18N
        }
    }

    private static Collection<String> copy(final Iterator<String> from) {
        if (!from.hasNext()) {
            return Collections.<String>emptyList();
        } else {
            final LinkedList<String> result = new LinkedList<String>();
            while (from.hasNext()) {
                result.add(from.next());
            }
            return result;
        }
    }

    public boolean hasLocation(JavaFileManager.Location location) {
        return location == StandardLocation.CLASS_PATH ||
               location == StandardLocation.PLATFORM_CLASS_PATH ||
               location == StandardLocation.SOURCE_PATH ||
               location == StandardLocation.CLASS_OUTPUT;
    }

    public JavaFileObject getJavaFileForInput (Location l, String className, JavaFileObject.Kind kind) throws IOException {
        JavaFileManager[] fms = getFileManager (l);
        for (JavaFileManager fm : fms) {
            JavaFileObject result = fm.getJavaFileForInput(l,className,kind);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public JavaFileObject getJavaFileForOutput(Location l, String className, JavaFileObject.Kind kind, FileObject sibling) 
        throws IOException, UnsupportedOperationException, IllegalArgumentException {
        JavaFileManager[] fms = getFileManager (l);
        assert fms.length <=1;
        if (fms.length == 0) {
            return null;
        }
        else {
            final JavaFileObject result = fms[0].getJavaFileForOutput (l, className, kind, sibling);
            return mark (result,l);
        }
    }


    public String inferBinaryName(JavaFileManager.Location location, JavaFileObject javaFileObject) {
        assert javaFileObject != null;
        //If cached return it dirrectly
        if (javaFileObject == lastInfered) {
            return lastInferedResult;
        }
        String result;
        //If instanceof FileObject.Base no need to delegate it
        if (javaFileObject instanceof InferableJavaFileObject) {
            final InferableJavaFileObject ifo = (InferableJavaFileObject) javaFileObject;
            result = ifo.inferBinaryName();
            if (result != null) {
                this.lastInfered = javaFileObject;
                this.lastInferedResult = result;
                return result;
            }
        }
        //Ask delegates to infer the binary name
        JavaFileManager[] fms = getFileManager (location);
        for (JavaFileManager fm : fms) {
            result = fm.inferBinaryName (location, javaFileObject);
            if (result != null && result.length() > 0) {
                this.lastInfered = javaFileObject;
                this.lastInferedResult = result;
                return result;
            }
        }
        return null;
    }

    public boolean isSameFile(FileObject fileObject, FileObject fileObject0) {
        final JavaFileManager[] fms = getFileManager(ALL);
        for (JavaFileManager fm : fms) {
            if (fm.isSameFile(fileObject, fileObject0)) {
                return true;
            }
        }
        return fileObject.toUri().equals (fileObject0.toUri());
    }

    @SuppressWarnings("unchecked")
    private <T extends javax.tools.FileObject> T mark(final T result, JavaFileManager.Location l) throws MalformedURLException {
        GeneratedFileMarker.Type type = null;
        if (l == StandardLocation.CLASS_OUTPUT) {
            type = GeneratedFileMarker.Type.RESOURCE;
        } else if (l == StandardLocation.SOURCE_OUTPUT) {
            type = GeneratedFileMarker.Type.SOURCE;
        }
        final boolean hasSibling = siblings.getProvider().hasSibling();
        final boolean write = marker.allowsWrite() || !hasSibling;
        if (result != null && hasSibling) {
            if (type == GeneratedFileMarker.Type.SOURCE) {
                marker.mark(result.toUri().toURL(), type);
            } else if (type == GeneratedFileMarker.Type.RESOURCE) {
                try {
                    result.openInputStream().close();
                } catch (IOException ioe) {
                    //Marking only created files
                    marker.mark(result.toUri().toURL(), type);
                }
            }
        }
        return write ? result : (T) new NullFileObject((InferableJavaFileObject)result);    //safe - NullFileObject subclass of both JFO and FO.
    }

    private void markerFinished() {
        if (siblings.getProvider().hasSibling()) {
            marker.finished(siblings.getProvider().getSibling());
        }
    }

    @Trusted
    private static final class NullFileObject extends ForwardingInferableJavaFileObject {
        private NullFileObject (@NonNull final InferableJavaFileObject delegate) {
            super (delegate);
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return new NullOutputStream();
        }

        @Override
        public Writer openWriter() throws IOException {
            return new OutputStreamWriter(openOutputStream());
        }
    }


    private static class NullOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
            //pass
        }
    }

}
