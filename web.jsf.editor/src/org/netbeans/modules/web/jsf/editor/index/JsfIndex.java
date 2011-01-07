/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */
package org.netbeans.modules.web.jsf.editor.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.modules.parsing.spi.indexing.support.IndexResult;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

/**
 * Generic index mining data from both embedded and binary indexes
 *
 * @author marekfukala
 */
public class JsfIndex {

    public static JsfIndex create(WebModule wm) {
        return new JsfIndex(wm);
    }
    private final FileObject[] sourceRoots;
    private final FileObject[] binaryRoots;
    private final FileObject[] customRoots;
    private final FileObject base;

    /** Creates a new instance of JsfIndex */
    private JsfIndex(WebModule wm) {
        this.base = wm.getDocumentBase();
        
        //#179930 - merge compile and execute classpath, remove once #180183 resolved
        Collection<FileObject> roots = new HashSet<FileObject>();
        roots.addAll(Arrays.asList(ClassPath.getClassPath(wm.getDocumentBase(), ClassPath.COMPILE).getRoots()));
        roots.addAll(Arrays.asList(ClassPath.getClassPath(wm.getDocumentBase(), ClassPath.EXECUTE).getRoots()));
        binaryRoots = roots.toArray(new FileObject[]{});

        Collection<FileObject> croots = QuerySupport.findRoots(base, null, null, null);
        sourceRoots = customRoots = croots.toArray(new FileObject[]{});
    }

    private QuerySupport createEmbeddingIndex() throws IOException {
        return QuerySupport.forRoots(JsfIndexer.Factory.NAME, JsfIndexer.Factory.VERSION, sourceRoots);
    }

    private QuerySupport createBinaryIndex() throws IOException {
        return QuerySupport.forRoots(JsfBinaryIndexer.INDEXER_NAME, JsfBinaryIndexer.INDEX_VERSION, binaryRoots);
    }

    private QuerySupport createCustomIndex() throws IOException {
        return QuerySupport.forRoots(JsfCustomIndexer.INDEXER_NAME, JsfCustomIndexer.INDEXER_VERSION, customRoots);
    }

    // --------------- BOTH EMBEDDING && BINARY INDEXES ------------------
    public Collection<String> getAllCompositeLibraryNames() {
        Collection<String> col = new ArrayList<String>();
        try {
            //aggregate data from both indexes
            col.addAll(getAllCompositeLibraryNames(createBinaryIndex()));
            col.addAll(getAllCompositeLibraryNames(createCustomIndex()));
            col.addAll(getAllCompositeLibraryNames(createEmbeddingIndex()));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return col;
    }

    private Collection<String> getAllCompositeLibraryNames(QuerySupport index) {
        Collection<String> libNames = new ArrayList<String>();
        try {
            Collection<? extends IndexResult> results = index.query(CompositeComponentModel.LIBRARY_NAME_KEY, "", QuerySupport.Kind.PREFIX, CompositeComponentModel.LIBRARY_NAME_KEY);
            for (IndexResult result : results) {
                String libraryName = result.getValue(CompositeComponentModel.LIBRARY_NAME_KEY);
                if (libraryName != null) {
                    libNames.add(libraryName);
                }
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return libNames;
    }

    public Collection<String> getCompositeLibraryComponents(String libraryName) {
        Collection<String> col = new ArrayList<String>();
        try {
            //aggregate data from both indexes
            col.addAll(getCompositeLibraryComponents(createBinaryIndex(), libraryName));
            col.addAll(getCompositeLibraryComponents(createEmbeddingIndex(), libraryName));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return col;
    }

    private Collection<String> getCompositeLibraryComponents(QuerySupport index, String libraryName) {
        Collection<String> components = new ArrayList<String>();
        try {
            Collection<? extends IndexResult> results = index.query(CompositeComponentModel.LIBRARY_NAME_KEY, libraryName, QuerySupport.Kind.EXACT, CompositeComponentModel.LIBRARY_NAME_KEY);
            for (IndexResult result : results) {
                FileObject file = result.getFile();
                if (file != null) {
                    components.add(file.getName());
                }
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return components;
    }

    public CompositeComponentModel getCompositeComponentModel(String libraryName, String componentName) {
        //try both indexes, the embedding one first
        try {
            CompositeComponentModel model = getCompositeComponentModel(createEmbeddingIndex(), libraryName, componentName);
            return model != null ? model : getCompositeComponentModel(createBinaryIndex(), libraryName, componentName);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }

    private CompositeComponentModel getCompositeComponentModel(QuerySupport index, String libraryName, String componentName) {
        try {
            Collection<? extends IndexResult> results = index.query(CompositeComponentModel.LIBRARY_NAME_KEY, libraryName, QuerySupport.Kind.EXACT,
                    CompositeComponentModel.LIBRARY_NAME_KEY,
                    CompositeComponentModel.INTERFACE_ATTRIBUTES_KEY,
                    CompositeComponentModel.HAS_IMPLEMENTATION_KEY);
            for (IndexResult result : results) {
                FileObject file = result.getFile(); //expensive? use result.getRelativePath?
                if (file != null) {
                    String fileName = file.getName();
                    //the filename w/o extenstion is the component name
                    if (fileName.equals(componentName)) {
                        return (CompositeComponentModel) JsfPageModelFactory.getFactory(CompositeComponentModel.Factory.class).loadFromIndex(result);
                    }
                }

            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    public Collection<IndexedFile> getAllFaceletsLibraryDescriptors() {
        Collection<IndexedFile> files = new ArrayList<IndexedFile>();
        try {
            //order of the following queries DOES matter! read comment #3 in FaceletsLibrarySupport.parseLibraries()

            //query binary index
            Collection<? extends IndexResult> binResults = createBinaryIndex().query(
                    JsfBinaryIndexer.FACELETS_LIBRARY_MARK_KEY,
                    "true", //NOI18N
                    QuerySupport.Kind.EXACT,
                    JsfBinaryIndexer.FACELETS_LIBRARY_MARK_KEY,
                    JsfIndexSupport.TIMESTAMP_KEY);

            for (IndexResult result : binResults) {
                FileObject file = result.getFile();
                if (file != null) {
                    long timestamp = Long.parseLong(result.getValue(JsfIndexSupport.TIMESTAMP_KEY));
                    files.add(new IndexedFile(timestamp, file));
                }

            }

            //query custom (sources) index
            Collection<? extends IndexResult> eiResults = createCustomIndex().query(
                    JsfBinaryIndexer.FACELETS_LIBRARY_MARK_KEY,
                    "true", //NOI18N
                    QuerySupport.Kind.EXACT,
                    JsfBinaryIndexer.FACELETS_LIBRARY_MARK_KEY,
                    JsfIndexSupport.TIMESTAMP_KEY);

            for (IndexResult result : eiResults) {
                FileObject file = result.getFile();
                if (file != null) {
                    long timestamp = Long.parseLong(result.getValue(JsfIndexSupport.TIMESTAMP_KEY));
                    files.add(new IndexedFile(timestamp, file));
                }

            }

        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return files;
    }

}
