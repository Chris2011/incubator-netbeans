/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009 Sun Microsystems, Inc. All rights reserved.
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

package org.netbeans.modules.java.source.indexing;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.netbeans.modules.java.source.usages.BuildArtifactMapperImpl;
import org.netbeans.modules.parsing.impl.indexing.IndexerCache;
import org.netbeans.modules.parsing.impl.indexing.IndexerCache.IndexerInfo;
import org.netbeans.modules.parsing.spi.indexing.Context;
import org.netbeans.modules.parsing.spi.indexing.CustomIndexer;
import org.netbeans.modules.parsing.spi.indexing.CustomIndexerFactory;
import org.netbeans.modules.parsing.spi.indexing.Indexable;
import org.openide.util.Exceptions;

/**
 *
 * @author lahvac
 */
public class COSSynchronizingIndexer extends CustomIndexer {

    @Override
    protected void index(Iterable<? extends Indexable> files, Context context) {
        if (!BuildArtifactMapperImpl.isUpdateResources(BuildArtifactMapperImpl.getTargetFolder(context.getRootURI()))) {
            return ;
        }

        Set<String> javaMimeTypes = gatherJavaMimeTypes();
        List<File> updated = new LinkedList<File>();

        for (Indexable i : files) {
            if (javaMimeTypes.contains(i.getMimeType()))
                continue;
            
            try {
                updated.add(new File(i.getURL().toURI()));
            } catch (URISyntaxException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        try {
            File sourceRootFile = new File(context.getRootURI().toURI());

            BuildArtifactMapperImpl.classCacheUpdated(context.getRootURI(), sourceRootFile, Collections.<File>emptyList(), updated, true);
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static Set<String> gatherJavaMimeTypes() {
        Set<String> mimeTypes = new HashSet<String>();

        for (IndexerInfo<CustomIndexerFactory> i : IndexerCache.getCifCache().getIndexersByName(JavaIndex.NAME)) {
            mimeTypes.addAll(i.getMimeTypes());
        }

        return mimeTypes;
    }

    public static final class Factory extends CustomIndexerFactory {

        @Override
        public CustomIndexer createIndexer() {
            return new COSSynchronizingIndexer();
        }

        @Override
        public boolean supportsEmbeddedIndexers() {
            return true;
        }

        @Override
        public void filesDeleted(Iterable<? extends Indexable> deleted, Context context) {
            if (BuildArtifactMapperImpl.getTargetFolder(context.getRootURI()) == null) {
                return ;
            }

            List<File> deletedFiles = new LinkedList<File>();

            for (Indexable d : deleted) {
                try {
                    deletedFiles.add(new File(d.getURL().toURI()));
                } catch (URISyntaxException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

            try {
                File sourceRootFile = new File(context.getRootURI().toURI());

                BuildArtifactMapperImpl.classCacheUpdated(context.getRootURI(), sourceRootFile, deletedFiles, Collections.<File>emptyList(), true);
            } catch (URISyntaxException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        @Override
        public void filesDirty(Iterable<? extends Indexable> dirty, Context context) {}

        @Override
        public String getIndexerName() {
            return COSSynchronizingIndexer.class.getName();
        }

        @Override
        public int getIndexVersion() {
            return 1;
        }

    }

}
