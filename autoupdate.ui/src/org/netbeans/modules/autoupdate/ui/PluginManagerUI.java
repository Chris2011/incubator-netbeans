/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2012 Oracle and/or its affiliates. All rights reserved.
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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2008 Sun
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

package org.netbeans.modules.autoupdate.ui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Window;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.autoupdate.UpdateManager;
import org.netbeans.api.autoupdate.UpdateUnit;
import org.netbeans.modules.autoupdate.ui.actions.AutoupdateCheckScheduler;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.Task;
import org.openide.util.TaskListener;

/**
 *
 * @author  Jiri Rechtacek, Radek Matous
 */
public class PluginManagerUI extends javax.swing.JPanel  {
    private List<UpdateUnit> units = Collections.emptyList ();
    private UnitTable installedTable;
    private UnitTable availableTable;
    private UnitTable updateTable;
    private UnitTable localTable;
    private JButton closeButton;
    public final RequestProcessor.Task initTask;
    private final Object initLock = new Object();
    private Object helpInstance = null;
    
    private static RequestProcessor.Task runningTask;
    private static Collection<Runnable> runOnCancel = null;
    private boolean wasSettings = false;
    public static final int INDEX_OF_UPDATES_TAB = 0;
    public static final int INDEX_OF_AVAILABLE_TAB = 1;
    public static final int INDEX_OF_DOWNLOAD_TAB = 2;
    public static final int INDEX_OF_INSTALLED_TAB = 3;
    public static final int INDEX_OF_SETTINGS_TAB = 4;

    public static final String[] TAB_NAMES = { "update", "available", "local", "installed" }; //NOI18N
    private int initialTabToSelect;
    private boolean detailView;
    public static final String DETAIL_VIEW_SELECTED_PROP = "plugin.manager.detail.view.selected";//NOI18N
    
    public PluginManagerUI (JButton closeButton ) {
        this(closeButton, null, true);
    }

    public PluginManagerUI (JButton closeButton, Object initialTab) {
        this(closeButton, initialTab, Boolean.getBoolean(DETAIL_VIEW_SELECTED_PROP));
    }
    
    /** Creates new form PluginManagerUI */
    public PluginManagerUI (JButton closeButton, Object initialTab, boolean detailView) {
        this.detailView = detailView;
        this.closeButton = closeButton;
        int selIndex = -1;
        for( int i=0; i<TAB_NAMES.length; i++ ) {
            if( TAB_NAMES[i].equals(initialTab) ) {
                selIndex = i;
                break;
            }
        }
        if( selIndex < 0 && null != initialTab ) {
            throw new IllegalArgumentException("Invalid tab name: " + initialTab); //NOI18N
        }
        initialTabToSelect = selIndex;
        initComponents ();
        //start initialize method as soon as possible
        initTask = Utilities.startAsWorkerThread (new Runnable () {
            @Override
            public void run () {
                postInitComponents ();
                initialize ();
            }
        });
    }

    boolean isDetailView() {
        return detailView;
    }

    void setDetailView(boolean detailView) {
        this.detailView = detailView;
    }

    private Window findWindowParent () {
        Component c = this;
        while(c != null) {
            c = c.getParent ();
            if (c instanceof Window) {
                return (Window)c;
            }
        }
        return null;
    }
    
    void setWaitingState (boolean waitingState) {
        boolean enabled = !waitingState;

        for (Component c : tpTabs.getComponents()) {
            if (c instanceof UnitTab) {
                ((UnitTab) c).setWaitingState (waitingState);
            }            
        }               
        
        // the Close & Help buttons are always enabled
        bClose.setEnabled (true);
        
        Component parent = getParent ();
        Component rootPane = getRootPane ();
        if (parent != null) {
            parent.setEnabled (enabled);
        }
        if (rootPane != null) {
            if (enabled) {
                rootPane.setCursor (null);
            } else {
                rootPane.setCursor (Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR));
            }
        }
    }

    @Override
    public void addNotify () {
        super.addNotify ();
        //show progress for initialize method
        final Window w = findWindowParent ();
        if (w != null) {
            w.addWindowListener (new WindowAdapter (){
                @Override
                public void windowOpened (WindowEvent e) {
                    final WindowAdapter waa = this;
                    setWaitingState (true);
                    Utilities.startAsWorkerThread (PluginManagerUI.this,
                            new Runnable () {
                                @Override
                                public void run () {
                                    try {
                                        initTask.waitFinished ();
                                        w.removeWindowListener (waa);
                                    } finally {
                                        setWaitingState (false);
                                    }
                                }
                            },
                            NbBundle.getMessage (PluginManagerUI.class, "UnitTab_InitAndCheckingForUpdates"),
                            Utilities.getTimeOfInitialization ());
                }
            });
        }
        HelpCtx.setHelpIDString (this, PluginManagerUI.class.getName ());
        tpTabs.addChangeListener (new ChangeListener () {
            @Override
            public void stateChanged (ChangeEvent evt) {
                HelpCtx.setHelpIDString (PluginManagerUI.this, getHelpCtx ().getHelpID ());
            }
        });
    }
    
    
    @Override
    public void removeNotify () {
        super.removeNotify ();
        unitilialize ();
    }
    
    public void close () {
        bClose.doClick ();
    }
    
    private void initialize () {
        try {
            final List<UpdateUnit> uu = UpdateManager.getDefault().getUpdateUnits(Utilities.getUnitTypes ());
            List<UnitCategory> precompute1 = Utilities.makeUpdateCategories (uu, false);
            List<UnitCategory> precompute2 = Utilities.makeUpdateCategories (uu, true);
            // postpone later
            // getLocalDownloadSupport().getUpdateUnits();
            SwingUtilities.invokeAndWait (new Runnable () {
                @Override
                public void run () {
                    refreshUnits(uu);
                    setSelectedTab ();
                }
            });
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace (ex);
        } catch (InvocationTargetException ex) {
            Exceptions.printStackTrace (ex);
        }
    }
    
    //workaround of #96282 - Memory leak in org.netbeans.core.windows.services.NbPresenter
    private void unitilialize () {
        Utilities.startAsWorkerThread (new Runnable () {
            @Override
            public void run () {
                //ensures that uninitialization runs after initialization
                initTask.waitFinished ();
                AutoupdateCheckScheduler.runCheckAvailableUpdates (0);
                //ensure exclusivity between this uninitialization code and refreshUnits (which can run even after this dialog is disposed)
                synchronized(initLock) {
                    units = null;
                    installedTable = null;
                    availableTable = null;
                    updateTable = null;
                    localTable = null;
                }
            }
        }, 10000);
    }
    
    void setProgressComponent (final JLabel detail, final JComponent progressComponent) {
        if (SwingUtilities.isEventDispatchThread ()) {
            setProgressComponentInAwt (detail, progressComponent);
        } else {
            SwingUtilities.invokeLater (new Runnable () {
                @Override
                public void run () {
                    setProgressComponentInAwt (detail, progressComponent);
                }
            });
        }
    }

    private UnitTable createTabForModel(UnitCategoryTableModel model) {
        UnitTable table = new UnitTable(model);
        selectFirstRow(table);
        
        final UnitTab tab = new UnitTab(table, new UnitDetails(), this);
        tpTabs.add(tab, model.getTabIndex());
        if (initTask != null) {
            tab.setWaitingState(! initTask.isFinished());
            initTask.addTaskListener(new TaskListener() {

                @Override
                public void taskFinished(Task task) {
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            tab.setWaitingState(false);
                        }
                    });
                }
            });
        }
        decorateTabTitle(table);
        return table;
    }
    
    private void setProgressComponentInAwt (JLabel detail, JComponent progressComponent) {
        assert pProgress != null;
        assert SwingUtilities.isEventDispatchThread () : "Must be called in EQ.";
        
        progressComponent.setMinimumSize (progressComponent.getPreferredSize ());
        
        pProgress.setVisible (true);

        java.awt.GridBagConstraints gridBagConstraints;
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 12);
        pProgress.add(progressComponent, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        pProgress.add(detail, gridBagConstraints);

        revalidate ();
    }
    
    void unsetProgressComponent (final JLabel detail, final JComponent progressComponent) {
        if (SwingUtilities.isEventDispatchThread ()) {
            unsetProgressComponentInAwt (detail, progressComponent);
        } else {
            SwingUtilities.invokeLater (new Runnable () {
                @Override
                public void run () {
                    unsetProgressComponentInAwt (detail, progressComponent);
                }
            });
        }
    }

    private void setSelectedTab() {
        if( initialTabToSelect >= 0 && initialTabToSelect != tpTabs.getSelectedIndex()
                && initialTabToSelect < tpTabs.getComponentCount() ) {
            Component c = tpTabs.getComponentAt(initialTabToSelect);
            if (c instanceof UnitTab) {
                UnitTab unitTab = (UnitTab) c;
                if (unitTab.getModel().isTabEnabled() && unitTab.getModel().canBePrimaryTab() ) {
                    tpTabs.setSelectedIndex(initialTabToSelect);
                    initialTabToSelect = -1;
                    return;
                }
            }
        }
        initialTabToSelect = -1;
        Component component = tpTabs.getSelectedComponent();
        if (component instanceof UnitTab) {
            UnitTab unitTab = (UnitTab) component;
            if (!unitTab.getModel().isTabEnabled()) {
                for (int i = 0; i < tpTabs.getComponentCount(); i++) {
                    component = tpTabs.getComponentAt(i);
                    if (component instanceof UnitTab) {
                        unitTab = (UnitTab) component;
                        if (unitTab.getModel().isTabEnabled() && unitTab.getModel().canBePrimaryTab()) {
                            tpTabs.setSelectedIndex(i);
                            break;
                        }
                    } else {
                        tpTabs.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }
    
    private void unsetProgressComponentInAwt (JLabel detail, JComponent progressComponent) {
        assert pProgress != null;
        assert SwingUtilities.isEventDispatchThread () : "Must be called in EQ.";
        pProgress.remove (detail);
        pProgress.remove (progressComponent);
        pProgress.setVisible (false);
        revalidate ();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tpTabs = new javax.swing.JTabbedPane();
        pProgress = new javax.swing.JPanel();
        bClose = closeButton;
        bHelp = new javax.swing.JButton();

        tpTabs.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tpTabsStateChanged(evt);
            }
        });

        pProgress.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(bClose, org.openide.util.NbBundle.getMessage(PluginManagerUI.class, "UnitTab_bClose_Text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(bHelp, org.openide.util.NbBundle.getMessage(PluginManagerUI.class, "PluginManagerUI.bHelp.text")); // NOI18N
        bHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHelpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                        .addComponent(bClose)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bHelp))
                    .addComponent(tpTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tpTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bHelp)
                        .addComponent(bClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tpTabs.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(PluginManagerUI.class, "ACN_Tabs")); // NOI18N
        tpTabs.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(PluginManagerUI.class, "ACD_Tabs")); // NOI18N

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(PluginManagerUI.class, "ACN_PluginManagerUI")); // NOI18N
        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(PluginManagerUI.class, "ACD_PluginManagerUI")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    
private void tpTabsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tpTabsStateChanged
    Component component = ((JTabbedPane) evt.getSource ()).getSelectedComponent ();
    if (component instanceof SettingsTab) {
        ((SettingsTab)component).getSettingsTableModel ().refreshModel ();
        wasSettings = true;
    } else {
        if (wasSettings) {
            final UnitCategoryTableModel availableModel = (UnitCategoryTableModel) (availableTable).getModel ();
            final Map<String, Boolean> availableState = UnitCategoryTableModel.captureState (availableModel.getUnits ());
            ((SettingsTab) tpTabs.getComponentAt (INDEX_OF_SETTINGS_TAB)).doLazyRefresh (new Runnable () { // get SettingsTab
                @Override
                public void run () {
                    UnitCategoryTableModel.restoreState (availableModel.getUnits (), availableState, false);
                }
            });
        }
        wasSettings = false;
    }
}//GEN-LAST:event_tpTabsStateChanged

private void bHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHelpActionPerformed
    getHelpCtx().display();
}//GEN-LAST:event_bHelpActionPerformed

    private HelpCtx getHelpCtx() {
        String id = PluginManagerUI.class.getName ();
        Component c = tpTabs.getSelectedComponent ();
        if (c instanceof UnitTab) {
            id = ((UnitTab) c).getHelpId ();
        } else if (c instanceof SettingsTab) {
            id = SettingsTab.class.getName ();
        }
        return new HelpCtx (id);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bClose;
    private javax.swing.JButton bHelp;
    private javax.swing.JPanel pProgress;
    private javax.swing.JTabbedPane tpTabs;
    // End of variables declaration//GEN-END:variables
    
    private void postInitComponents () {
        Containers.initNotify ();
        updateTable = createTabForModel(new UpdateTableModel(units));
        availableTable = createTabForModel(new AvailableTableModel (units));
        final LocalDownloadSupport localDownloadSupport = new LocalDownloadSupport();
        final LocallyDownloadedTableModel localTM = new LocallyDownloadedTableModel(localDownloadSupport);
        localTable = createTabForModel(localTM);
        installedTable = createTabForModel(new InstalledTableModel(units));

        DropTargetListener l = new LocallDownloadDnD(localDownloadSupport, localTM, this);
        final DropTarget dt = new DropTarget(null, l);
        dt.setActive(true);
        this.setDropTarget(dt);


        SettingsTab tab = new SettingsTab (this);
        tpTabs.add (tab, INDEX_OF_SETTINGS_TAB);
        tpTabs.setTitleAt(INDEX_OF_SETTINGS_TAB, tab.getDisplayName());
    }
    
    void decorateTabTitle (UnitTable table) {
        UnitCategoryTableModel model = (UnitCategoryTableModel)table.getModel();
        int index = model.getTabIndex();
        tpTabs.setTitleAt (index, model.getDecoratedTabTitle());
        tpTabs.setEnabledAt(index, model.isTabEnabled());
        tpTabs.setToolTipTextAt(index, model.getTabTooltipText()); // NOI18N
    }
    
    void undecorateTabTitles () {
        tpTabs.setTitleAt (INDEX_OF_UPDATES_TAB, NbBundle.getMessage (PluginManagerUI.class, "PluginManagerUI_UnitTab_Update_Title")); // NOI18N // Updates tab
        tpTabs.setTitleAt (INDEX_OF_AVAILABLE_TAB, NbBundle.getMessage (PluginManagerUI.class, "PluginManagerUI_UnitTab_Available_Title")); // NOI18N // Available tab
    }
    
    private int findRowWithFirstUnit (UnitCategoryTableModel model) {
        for (int row = 0; row <= model.getRowCount (); row++) {
            if (model.getUnitAtRow (row) != null) {
                return row;
            }
        }
        return -1;
    }
    
    private void selectFirstRow (UnitTable table) {
        if (table.getSelectedRow () == -1) {
            UnitCategoryTableModel model = (UnitCategoryTableModel)table.getModel ();
            int row = findRowWithFirstUnit (model);
            if (row != -1) {
                table.getSelectionModel ().setSelectionInterval (row, row);
            }
        }
    }
    
    private void refreshUnits(List<UpdateUnit> newUnits) {
        //ensure exclusivity between this refreshUnits code(which can run even after this dialog is disposed) and uninitialization code
        synchronized(initLock) {
            //return immediatelly if uninialization(after removeNotify) was alredy called
            if (units == null) return;
            //TODO: REVIEW THIS CODE - problem is that is called from called from AWT thread
            //UpdateManager.getDefault().getUpdateUnits() should never be called fromn AWT because it may cause
            //long terming starvation because in fact impl. of this method calls AutoUpdateCatalogCache.getCatalogURL
            //which is synchronized and may wait until cache is created
            //even more AutoUpdateCatalog.getUpdateItems () can at first start call refresh and thus writeToCache again
            units = newUnits;
            InstalledTableModel installTableModel = (InstalledTableModel)installedTable.getModel();
            UnitCategoryTableModel updateTableModel = ((UnitCategoryTableModel)updateTable.getModel());
            UnitCategoryTableModel availableTableModel = ((UnitCategoryTableModel)availableTable.getModel());
            LocallyDownloadedTableModel localTableModel = ((LocallyDownloadedTableModel)localTable.getModel());
            
            updateTableModel.setUnits(units);
            List<UpdateUnit> features = UpdateManager.getDefault().getUpdateUnits(UpdateManager.TYPE.FEATURE);
            if (isDetailView() && !features.isEmpty()) {
                installTableModel.setUnits(units);
            } else {
                installTableModel.setUnits(units, features);
            }
            availableTableModel.setUnits(units);
            localTableModel.setUnits(units);
            selectFirstRow(installedTable);
            selectFirstRow(updateTable);
            selectFirstRow(availableTable);
            selectFirstRow(localTable);
            decorateTabTitle(updateTable);
            decorateTabTitle(availableTable);
            decorateTabTitle(localTable);
            decorateTabTitle(installedTable);
            Component[] components = tpTabs.getComponents();
            for (Component component : components) {
                if (component instanceof UnitTab) {
                    UnitTab tab = (UnitTab)component;
                    tab.refreshState();
                }
            }
            setSelectedTab();
        }        
    }
        
    static boolean canContinue (String message) {
        return NotifyDescriptor.YES_OPTION.equals (DialogDisplayer.getDefault ().notify (new NotifyDescriptor.Confirmation (message)));
    }

    public static void registerRunningTask (RequestProcessor.Task it) {
        assert runningTask == null || runningTask.isFinished () : "Only once task can be running. Already running : " + runningTask;
        runningTask = it;
    }
    
    public static void unregisterRunningTask () {
        runningTask = null;
        runOnCancel = null;
    }
    
    public static RequestProcessor.Task getRunningTask (Runnable onCancel) {
        if (runningTask != null) {
            if (runOnCancel == null) {
                runOnCancel = new HashSet<Runnable>();
            }
            runOnCancel.add(onCancel);
        }
        return runningTask;
    }
    
    public static RequestProcessor.Task getRunningTask () {
        return runningTask;
    }
    
    public static void cancelRunningTask() {
        if (runningTask != null && runOnCancel != null) {
            runningTask = null;
            for (Runnable run : runOnCancel) {
                run.run();
            }
            runOnCancel = null;
        } else {
            runningTask = null;
        }
    }
    
    //TODO: all the request for refresh should be cancelled if there is already one such running refresh task
    public void updateUnitsChanged () {
        refreshUnits (UpdateManager.getDefault().getUpdateUnits(Utilities.getUnitTypes()));
    }
    
    public void tableStructureChanged () {
        installedTable.resortByDefault ();
        ((UnitCategoryTableModel) installedTable.getModel ()).fireTableStructureChanged ();
        installedTable.setColumnsSize ();
        installedTable.resetEnableRenderer ();
        updateTable.resortByDefault ();
        ((UnitCategoryTableModel) updateTable.getModel ()).fireTableStructureChanged ();
        updateTable.setColumnsSize ();
        availableTable.resortByDefault ();
        ((UnitCategoryTableModel) availableTable.getModel ()).fireTableStructureChanged ();
        availableTable.setColumnsSize ();
    }
    
    public void buttonsChanged () {
        Component c = tpTabs.getSelectedComponent ();
        if (c instanceof UnitTab) {
            ((UnitTab) c).refreshState ();
        }
    }
    
    final void setSelectedTab(UnitTab tab) {
        tpTabs.setSelectedComponent(tab);
    }

    final UnitTab findTabForModel(UnitCategoryTableModel model) {
        for (Component c : tpTabs.getComponents()) {
            if (c instanceof UnitTab) {
                UnitTab ut = (UnitTab) c;
                if (ut.getModel() == model) {
                    return ut;
                }
            }
        }
        return null;
    }
    
    final String getSelectedTabName() {
        int index = initialTabToSelect;
        if (index == -1) {
            index = tpTabs.getSelectedIndex();
        }
        return TAB_NAMES[index];
    }
}
