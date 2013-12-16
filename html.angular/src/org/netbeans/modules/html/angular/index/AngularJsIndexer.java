/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.netbeans.modules.html.angular.index;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.modules.javascript2.editor.api.lexer.JsTokenId;
import org.netbeans.modules.javascript2.editor.index.JsIndexer;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.indexing.Context;
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexer;
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexerFactory;
import org.netbeans.modules.parsing.spi.indexing.Indexable;
import org.netbeans.modules.parsing.spi.indexing.support.IndexDocument;
import org.netbeans.modules.parsing.spi.indexing.support.IndexingSupport;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Parameters;
import org.openide.util.Utilities;

/**
 *
 * @author Petr Pisl
 */
public class AngularJsIndexer extends EmbeddingIndexer{
    
    public static final String FIELD_CONTROLLER = "cont"; //NOI18N
    public static final String FIELD_TEMPLATE_CONTROLLER = "tc"; //NOI18N
    
    private static final Logger LOG = Logger.getLogger(AngularJsIndexer.class.getName());
    
    private static final ThreadLocal<Map<URI, Collection<AngularJsController>>> controllers = new ThreadLocal<>();
    private static final ThreadLocal<Map<URI, Map<String, String>>>templateControllers = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> addedToJsIndexPost = new ThreadLocal<>();
    
    public static void addController(@NonNull final URI uri, @NonNull final AngularJsController controller) {
        final Map<URI, Collection<AngularJsController>> map = controllers.get();
        
        if (map == null) {
            throw new IllegalStateException("AngularJsIndexer.addControllers can be called only from scanner thread.");  //NOI18N
        }
        Collection<AngularJsController> cons = map.get(uri);
        if (cons == null) {
            cons = new ArrayList<>();
            cons.add(controller);
            map.put(uri, cons);
        } else {
            cons.add(controller);
        }
        
    }
    
    public static void addTemplateController(@NonNull final URI uri, @NonNull final String template, @NonNull final String controller) {
        final Map<URI, Map<String, String>> map = templateControllers.get();
        
        if (map == null) {
            throw new IllegalStateException("AngularJsIndexer.addTemplateControllers can be called only from scanner thread.");  //NOI18N
        }
        
        Map<String, String> templates = map.get(uri);
        if(templates == null) {
            templates = new HashMap<>();
            templates.put(template, controller);
            map.put(uri, templates);
        } else {
            templates.put(template, controller);
        }
    }

    private static void removeControllers(@NonNull final URI uri) {
        final Map<URI, Collection<AngularJsController>> map = controllers.get();
        
        if (map == null) {
            throw new IllegalStateException("AngularJsIndexer.addControllers can be called only from scanner thread.");  //NOI18N
        }
        map.remove(uri);
    }
    
    private static void removeTemplateControllers(@NonNull final URI uri) {
        final Map<URI, Map<String, String>> map = templateControllers.get();
        
        if (map == null) {
            throw new IllegalStateException("AngularJsIndexer.addControllers can be called only from scanner thread.");  //NOI18N
        }
        map.remove(uri);
    }
    
    private static Collection<AngularJsController> getControllers(@NonNull final URI uri) {
        final Map<URI, Collection<AngularJsController>> map = controllers.get();
        if (map == null) {
            throw new IllegalStateException("AngularJsIndexer.getControllers can be called only from scanner thread.");  //NOI18N
        }
        return map.get(uri);
    }
    
    private static Map<String, String> getTemplateControllers(@NonNull final URI uri) {
        final Map<URI, Map<String, String>> map = templateControllers.get();
        if (map == null) {
            throw new IllegalStateException("AngularJsIndexer.getControllers can be called only from scanner thread.");  //NOI18N
        }
        return map.get(uri);
    }
    
    public static boolean isScannerThread() {
        return controllers.get() != null;
    }
    
    @Override
    protected void index(Indexable indexable, Parser.Result parserResult, Context context) {
        // TODO this is a basically hack, because the indexes are not called in layer order
        // so we need to be sure, that the JsIndex has to be called before saving the angulra stuff. 
        if (!addedToJsIndexPost.get().booleanValue()) {
            addedToJsIndexPost.set(Boolean.TRUE);
            JsIndexer.Factory.addPostScanTask(new SaveToIndex(context));
        }
//        Collection<AngularJsController> cons = null;
//        Map<String, String>templates = null;
//        URI uri = null;
//        try {
//            URL url = indexable.getURL();
//            if ( url != null) {
//                uri = url.toURI();
//            }
//        } catch (URISyntaxException ex) {
//            LOG.log(Level.WARNING, null, ex);
//        }
//        if (uri != null) {
//            cons = getControllers(uri);
//            templates = getTemplateControllers(uri);
//            if (cons != null || templates != null) {
//                IndexingSupport support;
//                try {
//                    support = IndexingSupport.getInstance(context);
//                } catch (IOException ioe) {
//                    LOG.log(Level.WARNING, null, ioe);
//                    return;
//                }
//                IndexDocument elementDocument = support.createDocument(indexable);
//                if (cons != null) {
//                    for (AngularJsController controller : cons) {
//                        StringBuilder sb = new StringBuilder();
//                        sb.append(controller.getName()).append(":");
//                        sb.append(controller.getFqn()).append(":");
//                        sb.append(controller.getOffset());
//                        elementDocument.addPair(FIELD_CONTROLLER, sb.toString() , true, true);
//                    }
//                }
//                if (templates != null) {
//                    for(String template : templates.keySet()) {
//                        String controller = templates.get(template);
//                        StringBuilder sb = new StringBuilder();
//                        sb.append(template).append(":").append(controller);
//                        elementDocument.addPair(FIELD_TEMPLATE_CONTROLLER, sb.toString(), true, true);
//                    }
//                }
//                support.addDocument(elementDocument);
//                // remove the caches
//                removeControllers(uri);
//                removeTemplateControllers(uri);
//            }
//        }
    }
    
    public static final class Factory extends EmbeddingIndexerFactory {
        public static final String NAME = "angular"; // NOI18N
        public static final int VERSION = 3;
        
        private static final ThreadLocal<Collection<Runnable>> postScanTasks = new ThreadLocal<Collection<Runnable>>();
        
        @Override
        public EmbeddingIndexer createIndexer(Indexable indexable, Snapshot snapshot) {
             if (isIndexable(indexable, snapshot)) {
                return new AngularJsIndexer();
            } else {
                return null;
            }
        }

        @Override
        public void filesDeleted(Iterable<? extends Indexable> deleted, Context context) {
            try {
                IndexingSupport is = IndexingSupport.getInstance(context);
                for (Indexable i : deleted) {
                    is.removeDocuments(i);
                }
            } catch (IOException ioe) {
                LOG.log(Level.WARNING, null, ioe);
            }
        }

        @Override
        public void filesDirty(Iterable<? extends Indexable> dirty, Context context) {

        }

        @Override
        public String getIndexerName() {
            return NAME;
        }

        @Override
        public int getIndexVersion() {
            return VERSION;
        }
        
        private boolean isIndexable(Indexable indexable, Snapshot snapshot) {
            return JsTokenId.JAVASCRIPT_MIME_TYPE.equals(snapshot.getMimeType());
        }
        
        @Override
        public boolean scanStarted(Context context) {
            postScanTasks.set(new LinkedList<Runnable>());
            controllers.set(new HashMap<URI, Collection<AngularJsController>>());
            templateControllers.set(new HashMap<URI, Map<String, String>>());
            addedToJsIndexPost.set(Boolean.FALSE);
            return super.scanStarted(context);
        }
        
         @Override
        public void scanFinished(Context context) {
            try {
                for (Runnable task : postScanTasks.get()) {            
                    task.run();
                }
            } finally {
                postScanTasks.remove();
                super.scanFinished(context);
            }
        }

        public static boolean isScannerThread() {
            return postScanTasks.get() != null;
        }
        
        public static void addPostScanTask(@NonNull final Runnable task) {
            Parameters.notNull("task", task);   //NOI18N
            final Collection<Runnable> tasks = postScanTasks.get();
            if (tasks == null) {
                throw new IllegalStateException("JsIndexer.postScanTask can be called only from scanner thread.");  //NOI18N
            }                        
            tasks.add(task);
        }
    }
    
    private static class SaveToIndex implements Runnable {

        private final Context context;

        public SaveToIndex(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            Map<URI, Map<String, String>> templates = templateControllers.get();
            Map<URI, Collection<AngularJsController>> controls = controllers.get();
            if ((templates != null && !templates.isEmpty()) || (controls != null && !controls.isEmpty())) {
                IndexingSupport support;
                try {
                    support = IndexingSupport.getInstance(context);
                } catch (IOException ioe) {
                    LOG.log(Level.WARNING, null, ioe);
                    return;
                }
                if (templates != null && !templates.isEmpty()) {
                    for (Map.Entry<URI, Map<String, String>> entry : templates.entrySet()) {
                        URI uri = entry.getKey();
                        Map<String, String> map = entry.getValue();
                        File file = Utilities.toFile(uri);
                        FileObject fo = FileUtil.toFileObject(file);

                        IndexDocument elementDocument = support.createDocument(fo);
                        for (String template : map.keySet()) {
                            String controller = map.get(template);
                            StringBuilder sb = new StringBuilder();
                            sb.append(template).append(":").append(controller); //NOI18N
                            elementDocument.addPair(FIELD_TEMPLATE_CONTROLLER, sb.toString(), true, true);
                        }
                        if (controls != null) {
                            Collection<AngularJsController> cons = controls.get(uri);
                            if (cons != null) {
                                for (AngularJsController controller : cons) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(controller.getName()).append(":");    //NOI18N
                                    sb.append(controller.getFqn()).append(":");     //NOI18N
                                    sb.append(controller.getOffset());
                                    elementDocument.addPair(FIELD_CONTROLLER, sb.toString(), true, true);
                                }
                                controls.remove(uri);
                            }
                        }
                        support.addDocument(elementDocument);
                    }
                }
                if (controls != null && !controls.isEmpty()) {
                    for (Map.Entry<URI, Collection<AngularJsController>> entry : controls.entrySet()) {
                        URI uri = entry.getKey();
                        Collection<AngularJsController> collection = entry.getValue();

                        File file = Utilities.toFile(uri);
                        FileObject fo = FileUtil.toFileObject(file);

                        IndexDocument elementDocument = support.createDocument(fo);
                        for (AngularJsController controller : collection) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(controller.getName()).append(":");    //NOI18N
                            sb.append(controller.getFqn()).append(":");     //NOI18N
                            sb.append(controller.getOffset());
                            elementDocument.addPair(FIELD_CONTROLLER, sb.toString(), true, true);
                        }
                        support.addDocument(elementDocument);
                    }
                }
            }
        }
    }
}