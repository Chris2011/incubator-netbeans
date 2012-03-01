/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.search.project;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.netbeans.api.project.Project;
import org.netbeans.api.search.SearchScopeOptions;
import org.netbeans.api.search.provider.SearchInfo;
import org.netbeans.api.search.provider.SearchListener;
import org.netbeans.junit.NbTestCase;
import org.netbeans.spi.search.SearchFilterDefinition;
import org.netbeans.spi.search.SubTreeSearchOptions;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author jhavlin
 */
public class CompatibilityUtilsTest extends NbTestCase {

    private FileObject projectDir;

    public CompatibilityUtilsTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        projectDir = FileUtil.createMemoryFileSystem().getRoot();
        projectDir.createData("valid.file");
        projectDir.createData("filtered.file");
        FileObject inner = projectDir.createFolder("inner");
        inner.createData("filteredInner.file");
        inner.createData("validInner.file");
    }

    public void testUseSubTreeSearchOptions() {
        Node n = new MockProjectNode(projectDir, Lookups.singleton(new MockProject()));
        assertEquals(3, n.getChildren().getNodes(true).length);
        traverseNodes(n);
    }

    private class MockProjectNode extends AbstractNode {

        FileObject fileObject;

        public MockProjectNode(FileObject fileObject, Lookup lookup) {
            super(new MockChildren(fileObject), new ProxyLookup(lookup));
            this.fileObject = fileObject;
        }

        @Override
        public String getDisplayName() {
            return fileObject.getName() + " (" + fileObject.getPath() + ")";
        }
    }

    private class MockChildren extends Children.Keys<FileObject> {

        private FileObject root;

        public MockChildren(FileObject root) {
            this.root = root;
        }

        @Override
        protected Node[] createNodes(FileObject key) {
            try {
                return new Node[]{new MockProjectNode(key, Lookups.singleton(DataObject.find(key)))};
            } catch (DataObjectNotFoundException ex) {
                fail("Cannot find data object " + key.getPath());
                return new Node[0];
            }
        }

        @Override
        protected void addNotify() {
            setKeys(root.getChildren());
        }
    }

    private class MockProject implements Project {

        @Override
        public FileObject getProjectDirectory() {
            return projectDir;
        }

        @Override
        public Lookup getLookup() {
            InstanceContent ic = new InstanceContent();
            ic.add(new MockSearchOptions());
            return new AbstractLookup(ic);
        }
    }

    private class MockSearchOptions extends SubTreeSearchOptions {

        @Override
        public List<SearchFilterDefinition> getFilters() {
            return Collections.<SearchFilterDefinition>singletonList(
                    new MockFilter());
        }
    }

    private class MockFilter extends SearchFilterDefinition {

        @Override
        public boolean searchFile(FileObject file) throws IllegalArgumentException {
            return !file.getName().startsWith("filtered");
        }

        @Override
        public FolderResult traverseFolder(FileObject folder) throws IllegalArgumentException {
            return folder.getName().startsWith("filtered")
                    ? FolderResult.DO_NOT_TRAVERSE
                    : FolderResult.TRAVERSE;
        }
    }

    private void traverseNodes(Node node) {
        System.out.println("Finding under " + node.getDisplayName());
        testFilters(node);
        for (Node n : node.getChildren().getNodes()) {
            traverseNodes(n);
        }
    }

    private void testFilters(Node n) {
        SearchInfo si = CompatibilityUtils.getSearchInfoForNode(n);
        for (FileObject fo : si.iterateFilesToSearch(
                SearchScopeOptions.create("*", false),
                new SearchListener() {
                }, new AtomicBoolean(false))) {
            if (fo.getName().startsWith("filtered")
                    && !n.getDisplayName().startsWith("filtered")) {
                fail(fo.getPath() + " should be filtered when searching under "
                        + n.getDisplayName());
            }
        }
    }
}
