/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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

package org.netbeans.modules.vmd.palette;

import java.beans.PropertyVetoException;
import org.netbeans.modules.vmd.api.model.ComponentProducer;
import org.netbeans.modules.vmd.api.model.ComponentSerializationSupport;
import org.netbeans.modules.vmd.api.model.Debug;
import org.netbeans.modules.vmd.api.model.DescriptorRegistry;
import org.netbeans.modules.vmd.api.model.DesignDocument;
import org.netbeans.modules.vmd.api.model.common.DefaultDataFlavor;
import org.netbeans.modules.vmd.api.palette.PaletteProvider;
import org.netbeans.spi.palette.*;
import org.openide.filesystems.*;
import org.openide.filesystems.FileSystem.AtomicAction;
import org.openide.loaders.DataFolder;
import org.openide.util.Lookup;
import org.openide.util.RequestProcessor;
import org.openide.util.datatransfer.ExTransferable;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.*;
import org.openide.loaders.DataObject;

/**
 * @author David Kaspar, Anton Chechel
 */
public class PaletteKit implements Runnable {

    static final String CUSTOM_CATEGORY_NAME = "custom"; // NOI18N
    private static final String PALETTE_FOLDER_NAME = "palette"; // NOI18N
    private WeakReference<DesignDocument> activeDocument;
    private PaletteController paletteController;
    private DNDHandler dndHandler;
    private Map<String, PaletteItemDataNode> nodesMap;
    private boolean isValidationRunning;
    private LinkedList<Lookup> validationQueue;
    private DataFolder rootFolder;
    private FileSystem fs;
    private final Object validationSynch = new Object();

    public PaletteKit(final String projectType) {
        this.fs = Repository.getDefault().getDefaultFileSystem();

        validationQueue = new LinkedList<Lookup>();

        String rootFolderPath = projectType + '/' + PALETTE_FOLDER_NAME; // NOI18N
        nodesMap = new HashMap<String, PaletteItemDataNode>();
        try {
            FileObject rootFolderFO = fs.findResource(rootFolderPath);
            if (rootFolderFO == null) {
                FileObject projectTypeFO = fs.findResource(projectType);
                if (projectTypeFO == null) {
                    projectTypeFO = fs.getRoot().createFolder(projectType);
                }
                rootFolderFO = FileUtil.createFolder(projectTypeFO, PALETTE_FOLDER_NAME);
            }

            rootFolder = DataFolder.findFolder(rootFolderFO);
            rootFolder.getPrimaryFile().setAttribute("itemWidth", "120"); // NOI18N
            dndHandler = new DNDHandler();
            paletteController = PaletteFactory.createPalette(rootFolderPath, new Actions(), new Filter(), dndHandler);
        } catch (IOException ex) {
            throw Debug.error(ex);
        }
    }

    synchronized void clean() {
        if (activeDocument == null || activeDocument.get() == null) {
            return;
        }

        String projectID = activeDocument.get().getDocumentInterface().getProjectID();
        String projectType = activeDocument.get().getDocumentInterface().getProjectType();

        final DescriptorRegistry registry = DescriptorRegistry.getDescriptorRegistry(projectType, projectID);
        registry.readAccess(new Runnable() {

            public void run() {
                List<ComponentProducer> list = registry.getComponentProducers();
                Map<String, ComponentProducer> producers = new HashMap<String, ComponentProducer>(list.size());
                for (ComponentProducer producer : list) {
                    producers.put(producer.getProducerID(), producer);
                }
                cleanCore(producers);
            }
        });
    }

    private void cleanCore(Map<String, ComponentProducer> producers) {
        try {
            for (FileObject catFolder : rootFolder.getPrimaryFile().getChildren()) {
                for (FileObject item : catFolder.getChildren()) {
                    if (CUSTOM_CATEGORY_NAME.equalsIgnoreCase(catFolder.getName())) {
                        item.delete();
                    } else {
                        String producerName = item.getName();
                        ComponentProducer producer = producers.get(producerName);

                        if (producer != null) {
                            String catID = producer.getPaletteDescriptor().getCategoryID();
                            if (!catID.equals(catFolder.getName())) {
                                item.delete();
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Debug.warning(ex);
        }
    }

    synchronized void refreshDescriptorRegistry() {
        if (activeDocument == null || activeDocument.get() == null) {
            return;
        }

        String projectType = activeDocument.get().getDocumentInterface().getProjectType();
        ComponentSerializationSupport.refreshDescriptorRegistry(projectType);
    }

    public PaletteController getPaletteController() {
        return paletteController;
    }

    public DragAndDropHandler getDndHandler() {
        return dndHandler;
    }

    void refreshPaletteController() {
        if (paletteController == null) {
            return;
        }
        paletteController.refresh();
    }

    synchronized void init() {
        if (activeDocument == null || activeDocument.get() == null) {
            return;
        }

        final String projectID = activeDocument.get().getDocumentInterface().getProjectID();
        final String projectType = activeDocument.get().getDocumentInterface().getProjectType();

        final DescriptorRegistry registry = DescriptorRegistry.getDescriptorRegistry(projectType, projectID);
        registry.readAccess(new Runnable() {

            public void run() {
                Collection<? extends PaletteProvider> providers = Lookup.getDefault().lookupAll(PaletteProvider.class);
                for (PaletteProvider provider : providers) {
                    if (provider != null) {
                        provider.initPaletteCategories(projectType);
                        initCore(registry.getComponentProducers());
                    }
                }
            }
        });
    }

    private void initCore(final List<ComponentProducer> producers) {
        FileObject[] children = rootFolder.getPrimaryFile().getChildren();
        Map<String, FileObject> categoryFolders = new HashMap<String, FileObject>(children.length);
        for (FileObject fo : children) {
            categoryFolders.put(fo.getName(), fo);
        }

        // create item files
        for (ComponentProducer producer : producers) {
            if (producer.getPaletteDescriptor() == null) {
                continue;
            }

            String producerID = producer.getProducerID();
            String catID = producer.getPaletteDescriptor().getCategoryID();
            FileObject catFO;
            if (catID != null) {
                catFO = categoryFolders.get(catID);
            } else {
                catFO = categoryFolders.get(CUSTOM_CATEGORY_NAME);
                if (catFO == null) {
                    continue;
                }
            }

            if (catFO == null) {
                // if category folder was not initialized - create folder
                // only creation is not enough, should be set NB attributes, see MidpPaletteProvider for example
                Debug.warning(catID + " should be initialized! See MidpPaletteProvider."); // NOI18N
                try {
                    catFO = DataFolder.create(rootFolder, catID).getPrimaryFile();
                } catch (IOException ex) {
                    Debug.warning("Can't create folder for palette category: " + ex); // NOI18N
                }
            }

            StringBuffer path = new StringBuffer();
            path.append(catFO.getPath());
            path.append('/'); // NOI18N
            path.append(producerID);
            path.append('.'); // NOI18N
            path.append(PaletteItemDataLoader.EXTENSION);
            if (fs.findResource(path.toString()) == null) {
                try {
                    FileObject itemFO = catFO.createData(producerID, PaletteItemDataLoader.EXTENSION);

                    Properties props = new Properties();
                    props.setProperty("producerID", producerID); // NOI18N
                    String displayName = producer.getPaletteDescriptor().getDisplayName();
                    props.setProperty("displayName", displayName != null ? displayName : ""); // NOI18N
                    String toolTip = producer.getPaletteDescriptor().getToolTip();
                    props.setProperty("toolTip", toolTip != null ? toolTip : ""); // NOI18N
                    String icon = producer.getPaletteDescriptor().getSmallIcon();
                    props.setProperty("icon", icon != null ? icon : ""); // NOI18N
                    String largeIcon = producer.getPaletteDescriptor().getLargeIcon();
                    props.setProperty("bigIcon", largeIcon != null ? largeIcon : ""); // NOI18N
                    FileLock lock = itemFO.lock();
                    OutputStream os = null;
                    try {
                        os = itemFO.getOutputStream(lock);
                        props.store(os, "VMD Palette Item"); // NOI18N
                    } finally {
                        if (os != null) {
                            os.close();
                        }
                        lock.releaseLock();
                    }
                } catch (IOException e) {
                    Debug.warning("Can't create file for palette item: " + path + ", " + producerID + ", " + producerID + "." + PaletteItemDataLoader.EXTENSION + ": " + e); // NOI18N
                }
            }
        }
    }

    void checkValidity(final Lookup lookup) {
        PaletteItemDataNode node = lookup.lookup(PaletteItemDataNode.class);
        assert node != null;

        final String producerID = node.getProducerID();
        if (producerID == null) {
            node.setNeedCheck(false);
            node.setValid(false);
            return;
        }

        if (!nodesMap.containsKey(producerID)) {
            nodesMap.put(producerID, node);
        }

        node.setNeedCheck(false);
        scheduleCheckValidityCore(lookup);
    }

    private void scheduleCheckValidityCore(Lookup lookup) {
        validationQueue.add(lookup);
        synchronized (validationSynch) {
            if (isValidationRunning) {
                return;
            }
            isValidationRunning = true;
        }
        RequestProcessor.getDefault().post(this);
    }

    public void run() {
        while (true) {
            synchronized (validationSynch) {
                if (validationQueue.isEmpty()) {
                    isValidationRunning = false;
                    break;
                }
            }
            checkValidityCore(validationQueue.remove());
        }

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                refreshPaletteController();
            }
        });
    }

    private void checkValidityCore(Lookup lookup) {
        if (activeDocument == null || activeDocument.get() == null) {
            return;
        }

        PaletteItemDataNode node = lookup.lookup(PaletteItemDataNode.class);
        if (node == null) {
            return;
        }

        final String producerID = node.getProducerID();
        String projectID = activeDocument.get().getDocumentInterface().getProjectID();
        String projectType = activeDocument.get().getDocumentInterface().getProjectType();

        // check whether producerID is valid
        final ComponentProducer[] result = new ComponentProducer[1];
        final DescriptorRegistry registry = DescriptorRegistry.getDescriptorRegistry(projectType, projectID);
        registry.readAccess(new Runnable() {

            public void run() {
                List<ComponentProducer> producers = registry.getComponentProducers();
                ComponentProducer producer = null;
                for (ComponentProducer p : producers) {
                    if (p.getProducerID().equals(producerID)) {
                        producer = p;
                        break;
                    }
                }
                result[0] = producer;
            }
        });

        boolean isValid = result[0] != null;

        // check component's availability in classpath
        if (isValid) {
            isValid = result[0].checkValidity(activeDocument.get());
        }

        node.setValid(isValid);
    }

    void clearNodesStateCache() {
        for (PaletteItemDataNode node : nodesMap.values()) {
            node.setNeedCheck(true);
            node.setValid(true);
        }
    }

    void setActiveDocument(DesignDocument activeDocument) {
        this.activeDocument = new WeakReference<DesignDocument>(activeDocument);
    }

    private class Actions extends PaletteActions {

        public Action[] getImportActions() {
            if (activeDocument == null || activeDocument.get() == null) {
                return null;
            }

            String projectType = activeDocument.get().getDocumentInterface().getProjectType();

            Collection<? extends PaletteProvider> providers = Lookup.getDefault().lookupAll(PaletteProvider.class);
            ArrayList<Action> actions = new ArrayList<Action>();
            for (PaletteProvider paletteProvider : providers) {
                List<? extends Action> list = paletteProvider.getActions(projectType);
                if (list != null) {
                    actions.addAll(list);
                }
            }
            return actions.toArray(new Action[actions.size()]);
        }

        public Action[] getCustomPaletteActions() {
            return new Action[0];
        }

        public Action[] getCustomCategoryActions(Lookup category) {
            return new Action[0];
        }

        public Action[] getCustomItemActions(Lookup item) {
            return new Action[0];
        }

        public Action getPreferredAction(Lookup item) {
            return null;
        }

        @Override
        public Action getResetAction() {
            return new AbstractAction() {

                public void actionPerformed(ActionEvent evt) {
                    refreshDescriptorRegistry();
                    try {
                        fs.runAtomicAction(new AtomicAction() {

                            public void run() {
                                clean();
                                init();
                            }
                        });
                    } catch (IOException e) {
                        Debug.warning(e);
                    }
                }
            };
        }
    }

    private class Filter extends PaletteFilter {

        public boolean isValidCategory(Lookup lkp) {
            return true;
        }

        public boolean isValidItem(Lookup lkp) {
            PaletteItemDataNode node = lkp.lookup(PaletteItemDataNode.class);
            return node == null || node.isValid();
        }
    }

    private class DNDHandler extends DragAndDropHandler {

        public void customize(final ExTransferable t, Lookup item) {
            if (activeDocument == null || activeDocument.get() == null) {
                return;
            }

            PaletteItemDataObject itemDataObject = item.lookup(PaletteItemDataObject.class);
            if (itemDataObject == null) {
                return;
            }

            final String producerID = itemDataObject.getProducerID();
            String projectID = activeDocument.get().getDocumentInterface().getProjectID();
            String projectType = activeDocument.get().getDocumentInterface().getProjectType();
            final DescriptorRegistry registry = DescriptorRegistry.getDescriptorRegistry(projectType, projectID);

            registry.readAccess(new Runnable() {

                public void run() {
                    List<ComponentProducer> producers = registry.getComponentProducers();
                    final ComponentProducer[] producer = new ComponentProducer[1];
                    for (ComponentProducer p : producers) {
                        if (p.getProducerID().equals(producerID)) {
                            producer[0] = p;
                            break;
                        }
                    }

                    if (producer[0] != null) {
                        DefaultDataFlavor dataFlavor = new DefaultDataFlavor(producer[0]);
                        t.put(new ExTransferable.Single(dataFlavor) {

                            protected Object getData() {
                                return producer[0].getProducerID();
                            }
                        });
                    }
                }
            });
        }
    }
}