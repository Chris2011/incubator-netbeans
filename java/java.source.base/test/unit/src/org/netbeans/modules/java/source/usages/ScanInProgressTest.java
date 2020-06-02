/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.java.source.usages;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.junit.MockServices;
import org.netbeans.junit.NbTestCase;
import org.netbeans.spi.project.ui.ProjectOpenedHook;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.URLMapper;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;

import java.awt.EventQueue;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.classpath.GlobalPathRegistry;
import org.netbeans.api.java.source.SourceUtils;
import org.netbeans.api.project.Project;
import org.netbeans.modules.parsing.api.indexing.IndexingManager;
import org.netbeans.modules.project.ui.ExtIcon;
import org.netbeans.modules.project.ui.OpenProjectListSettings;
import org.netbeans.modules.project.ui.ProjectsRootNode;
import org.netbeans.spi.java.classpath.support.ClassPathSupport;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class ScanInProgressTest extends NbTestCase implements PropertyChangeListener {
    CountDownLatch first;
    CountDownLatch middle;
    CountDownLatch rest;
    private int events;
    private static boolean result = true;
    
    public ScanInProgressTest(String testName) {
        super(testName);
    }

    Lookup createLookup(TestSupport.TestProject project, Object instance) {
        return Lookups.singleton(instance);
    }

    @Override
    protected void setUp() throws Exception {
        //ensure ProjectsRootNode children are processed synchronously:
        System.setProperty("test.projectnode.sync", "true");

        clearWorkDir();

        MockServices.setServices(TestSupport.TestProjectFactory.class);

        FileObject workDir = FileUtil.toFileObject(getWorkDir());
        assertNotNull(workDir);

        first = new CountDownLatch(1);
        middle = new CountDownLatch(1);
        rest = new CountDownLatch(2);

        List<URL> list = new ArrayList<URL>();
        List<ExtIcon> icons = new ArrayList<ExtIcon>();
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            FileObject prj = TestSupport.createTestProject(workDir, "prj" + i);
            URL url = URLMapper.findURL(prj, URLMapper.EXTERNAL);
            list.add(url);
            names.add(url.toExternalForm());
            icons.add(new ExtIcon());
            TestSupport.TestProject tmp = (TestSupport.TestProject)ProjectManager.getDefault ().findProject (prj);
            assertNotNull("Project found", tmp);
            CountDownLatch down = i == 0 ? first : (i == 5 ? middle : rest);
            tmp.setLookup(createLookup(tmp, new TestProjectOpenedHookImpl(down, prj)));
        }

        OpenProjectListSettings.getInstance().setOpenProjectsURLs(list);
        OpenProjectListSettings.getInstance().setOpenProjectsDisplayNames(names);
        OpenProjectListSettings.getInstance().setOpenProjectsIcons(icons);

        OpenProjects.getDefault().addPropertyChangeListener(this);
    }

    public void testScanInProgressWhenOpeningProject() throws InterruptedException {
        assertEquals("No events in API", 0, events);

        Node logicalView = new ProjectsRootNode(1 /*ProjectsRootNode.LOGICAL_VIEW*/);
        L listener = new L();
        logicalView.addNodeListener(listener);

        assertEquals("No events in API", 0, events);
        assertEquals("10 children", 10, logicalView.getChildren().getNodesCount());
        listener.assertEvents("None", 0);
        assertEquals("No project opened yet", 0, TestProjectOpenedHookImpl.opened);
        assertEquals("No events in API", 0, events);

        for (Node n : logicalView.getChildren().getNodes()) {
            TestSupport.TestProject p = n.getLookup().lookup(TestSupport.TestProject.class);
            assertNull("No project of this type, yet", p);
        }
        assertEquals("No events in API", 0, events);

        Node midNode = logicalView.getChildren().getNodes()[5];
        {
            TestSupport.TestProject p = midNode.getLookup().lookup(TestSupport.TestProject.class);
            assertNull("No project of this type, yet", p);
        }
        Project lazyP = midNode.getLookup().lookup(Project.class);
        assertNotNull("Some project is found", lazyP);
//        assertEquals("It is lazy project", LazyProject.class, lazyP.getClass());
        assertEquals("No events in API", 0, events);

        middle.countDown();
        // not necessary, but to ensure middle really does not run
        Thread.sleep(300);
        assertEquals("Still no processing", 0, TestProjectOpenedHookImpl.opened);
        // trigger initialization of the node, shall trigger OpenProjectList.preferredProject(lazyP);
        midNode.getChildren().getNodes();
        first.countDown();

        TestProjectOpenedHookImpl.toOpen.await();

        {
            TestSupport.TestProject p = null;
            for (int i = 0; i < 10; i++) {
                Node midNode2 = logicalView.getChildren().getNodes()[5];
                p = midNode.getLookup().lookup(TestSupport.TestProject.class);
                if (p != null) {
                    break;
                }
                Thread.sleep(100);
            }
            assertNotNull("The right project opened", p);
        }
        assertEquals("No events in API", 0, events);

        {
            int cnt = 0;
            for (int i = 0; i < 10; i++) {
                Node n = logicalView.getChildren().getNodes()[i];
                TestSupport.TestProject p = null;
                p = n.getLookup().lookup(TestSupport.TestProject.class);
                if (p != null) {
                    cnt++;
                }
            }
            assertEquals("First and fifth projects are open, nobody else is", 2, cnt);
        }

        assertEquals("No events in API", 0, events);
        rest.countDown();
        rest.countDown();

        //OpenProjectList.waitProjectsFullyOpen();
        //assertEquals("Finally notified in API", 1, events);
        SourceUtils.waitScanFinished();
        assertEquals("All projects opened", 10, TestProjectOpenedHookImpl.opened);


        for (Node n : logicalView.getChildren().getNodes()) {
            TestSupport.TestProject p = n.getLookup().lookup(TestSupport.TestProject.class);
            assertNotNull("Nodes have correct project of this type", p);
        }
        assertFalse(IndexingManager.getDefault().isIndexing());
        assertTrue(result);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (OpenProjects.PROPERTY_OPEN_PROJECTS.equals(evt.getPropertyName())) {
            events++;
        }
    }

    private static class L implements NodeListener, PropertyChangeListener {
        public List<EventObject> events = new ArrayList<EventObject>();

        public void childrenAdded(NodeMemberEvent ev) {
            assertFalse("No event in AWT thread", EventQueue.isDispatchThread());
            events.add(ev);
        }

        public void childrenRemoved(NodeMemberEvent ev) {
            assertFalse("No event in AWT thread", EventQueue.isDispatchThread());
            events.add(ev);
        }

        public void childrenReordered(NodeReorderEvent ev) {
            assertFalse("No event in AWT thread", EventQueue.isDispatchThread());
            events.add(ev);
        }

        public void nodeDestroyed(NodeEvent ev) {
            assertFalse("No event in AWT thread", EventQueue.isDispatchThread());
            events.add(ev);
        }

        public void propertyChange(PropertyChangeEvent evt) {
            assertFalse("No event in AWT thread", EventQueue.isDispatchThread());
            events.add(evt);
        }

        final void assertEvents(String string, int i) {
            assertEquals(string + events, i, events.size());
            events.clear();
        }

    }

    private static class TestProjectOpenedHookImpl extends ProjectOpenedHook {

        public static CountDownLatch toOpen = new CountDownLatch(2);
        public static int opened = 0;
        public static int closed = 0;


        private CountDownLatch toWaitOn;
        private FileObject prj;

        public TestProjectOpenedHookImpl(CountDownLatch toWaitOn, FileObject prj) {
            this.toWaitOn = toWaitOn;
            this.prj = prj;
        }

        protected void projectClosed() {
            closed++;
        }

        protected void projectOpened() {
            ClassPath cp = ClassPathSupport.createClassPath(prj);
            GlobalPathRegistry.getDefault().register(ClassPath.SOURCE, new ClassPath[] { cp });

            if (toWaitOn != null) {
                try {
                    toWaitOn.await();
                } catch (InterruptedException ex) {
                    throw new IllegalStateException(ex);
                }
            }
            opened++;
            toOpen.countDown();
            result &= IndexingManager.getDefault().isIndexing();
            assertTrue(result);
        }

    }
}

