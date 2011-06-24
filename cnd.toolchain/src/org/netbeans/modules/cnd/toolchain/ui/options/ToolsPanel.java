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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
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
package org.netbeans.modules.cnd.toolchain.ui.options;

import java.io.IOException;
import org.netbeans.modules.cnd.api.toolchain.ui.ToolsPanelGlobalCustomizer;
import org.netbeans.modules.cnd.api.toolchain.ui.ServerListUIEx;
import org.netbeans.modules.cnd.api.toolchain.ui.ToolsPanelModel;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.netbeans.modules.cnd.api.toolchain.CompilerSet;
import org.netbeans.modules.cnd.api.toolchain.CompilerSetManager;
import org.netbeans.modules.cnd.api.remote.ServerList;
import org.netbeans.modules.cnd.api.remote.ServerRecord;
import org.netbeans.modules.cnd.toolchain.compilerset.CompilerSetImpl;
import org.netbeans.modules.cnd.toolchain.compilerset.CompilerSetManagerImpl;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironmentFactory;
import org.netbeans.modules.cnd.api.toolchain.ui.ToolsPanelSupport;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironment;
import org.netbeans.modules.nativeexecution.api.util.ConnectionManager;
import org.netbeans.modules.nativeexecution.api.util.ConnectionManager.CancellationException;
import org.netbeans.modules.nativeexecution.api.util.HostInfoUtils;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;

/** Display the "Tools Default" panel */
public final class ToolsPanel extends JPanel implements ActionListener,
        ListSelectionListener, ItemListener {

    private static enum ValidState {
        VALID,
        INVALID,
        UNKNOWN
    }

    // The following are constants so I can do == rather than "equals"
    public static final String PROP_VALID = "valid"; // NOI18N
    private static final String PROP_COMPILER_SET_NAME = "compilerSetName"; // NOI18N
    private boolean initialized = false;
    private boolean changed;
    private boolean changingCompilerSet;
    private boolean updating;
    private ValidState valid = ValidState.UNKNOWN;
    private ToolsPanelModel model = null;
    private boolean customizeDebugger;
    private ExecutionEnvironment execEnv;
    private CompilerSetManagerImpl csm;
    private CompilerSet currentCompilerSet;
    private ToolsCacheManagerImpl tcm = (ToolsCacheManagerImpl) ToolsPanelSupport.getToolsCacheManager();
    private static final Logger log = Logger.getLogger("cnd.remote.logger"); // NOI18N
    private static final RequestProcessor RP = new RequestProcessor(ToolsPanel.class.getName(), 1);

    /** Creates new form ToolsPanel */
    public ToolsPanel(String helpContext) {
        initComponents();
        setName("TAB_ToolsTab"); // NOI18N (used as a pattern...)
        changed = false;
        currentCompilerSet = null;
        execEnv = ServerList.getDefaultRecord().getExecutionEnvironment();

        lstDirlist.setCellRenderer(new MyCellRenderer());
        cbDevHost.setRenderer(new MyDevHostListCellRenderer());

        if ("Windows".equals(UIManager.getLookAndFeel().getID())) { //NOI18N
            setOpaque(false);
        }
        // clean up previous caches
        tcm.clear();
        HelpCtx.setHelpIDString(ToolsPanel.this, helpContext); // NOI18N
    }

    public ToolsPanel(ToolsPanelModel model, String heplContext) {
        this(heplContext);
        this.model = model;
        ExecutionEnvironment env = model.getSelectedDevelopmentHost();
        if (env != null) {
            execEnv = env;
        }
    }

    private void initializeLong() {
        csm = (CompilerSetManagerImpl) tcm.getCompilerSetManagerCopy(execEnv, true);
    }

    ToolsPanelModel getModel(){
        return model;
    }

    ExecutionEnvironment getExecutionEnvironment(){
        return execEnv;
    }

    private void showHideToolchainInitialization(boolean show) {
        if (show) {
            this.cbDevHost.setEnabled(model.getEnableDevelopmentHostChange());
        } else {
            this.cbDevHost.setEnabled(false);
        }
        btEditDevHost.setEnabled(show);
        buttomPanel.setVisible(show);
        buttonPanel.setVisible(show);
        toolCollectionPanel.setVisible(show);
        loadingToolCollectionPanel.setVisible(!show);
    }
    
    private void initializeUI() {
        changingCompilerSet = true;
        if (model == null) {
            model = new GlobalToolsPanelModel();
        }
        showHideToolchainInitialization(true);
        cbDevHost.removeItemListener(this);

        ExecutionEnvironment selectedEnv = model.getSelectedDevelopmentHost();
        ServerRecord selectedRec = null;

        Collection<? extends ServerRecord> hostList = tcm.getHosts();
        if (hostList != null) {
            cbDevHost.removeAllItems();
            for (ServerRecord rec : hostList) {
                if (rec.getExecutionEnvironment().equals(selectedEnv)) {
                    selectedRec = rec;
                }
                cbDevHost.addItem(rec);
            }
        } else {
            cbDevHost.addItem(ServerList.get(ExecutionEnvironmentFactory.getLocal()));
        }

        if (selectedRec == null) {
            selectedRec = tcm.getDefaultHostRecord();
        }
        if (selectedRec == null) {
            selectedRec = ServerList.get(ExecutionEnvironmentFactory.getLocal());
        }
        cbDevHost.setSelectedItem(selectedRec);

        cbDevHost.addItemListener(this);
        cbDevHost.setEnabled(model.getEnableDevelopmentHostChange());
        btEditDevHost.setEnabled(model.getEnableDevelopmentHostChange());
        execEnv = getSelectedRecord().getExecutionEnvironment();
        btVersions.setEnabled(false);
        initCustomizableDebugger();

        getToolCollectionPanel().initializeUI();
    }

    private ToolCollectionPanel getToolCollectionPanel(){
        return (ToolCollectionPanel) toolCollectionPanel;
    }

    private void addCompilerSet() {
        if (csm == null) {
            // Compiler set manager is not initialized yet
            // (initializeLong still running). Stop here to avoid NPEs.
            return;
        }

        boolean oldHostValid = ToolsUtils.isDevHostValid(execEnv);
        final CompilerSet cs = AddCompilerSetPanel.invokeMe(csm);
        if (cs == null) {
            boolean newHostValid = ToolsUtils.isDevHostValid(execEnv);
            if (oldHostValid != newHostValid) {
                // we didn't add the collection, but host changed its valid state
                dataValid();
            }
            return;
        }

        updating = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        RP.post(new Runnable(){
            @Override
            public void run() {
                csm.add(cs);
                changed = true;
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        update(false, cs, null);
                    }
                });
            }
        });
    }

    private void duplicateCompilerSet() {
        final CompilerSet selectedCompilerSet = (CompilerSet) lstDirlist.getSelectedValue();
        DuplicateCompilerSetPanel panel = new DuplicateCompilerSetPanel(csm, selectedCompilerSet);
        DialogDescriptor dialogDescriptor = new DialogDescriptor(panel, getString("COPY_TOOL_SET_TITLE"));
        panel.setDialogDescriptor(dialogDescriptor);
        DialogDisplayer.getDefault().notify(dialogDescriptor);
        if (dialogDescriptor.getValue() != DialogDescriptor.OK_OPTION) {
            return;
        }
        final String compilerSetName = panel.getCompilerSetName().trim();

        updating = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        RP.post(new Runnable(){
            @Override
            public void run() {
                final CompilerSetImpl cs = ((CompilerSetImpl) selectedCompilerSet).createCopy(
                        selectedCompilerSet.getCompilerFlavor(), selectedCompilerSet.getDirectory(), compilerSetName, false, selectedCompilerSet.getEncoding(), true);
                csm.add(cs);
                changed = true;
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        update(false, cs, null);
                    }
                });
            }
        });
    }

    private void onCompilerSetChanged() {
        boolean cbRemoveEnabled;
        if (model.showRequiredTools()) {
            cbRemoveEnabled = lstDirlist.getSelectedIndex() >= 0;
        } else {
            cbRemoveEnabled = csm.getCompilerSets().size() > 1 && lstDirlist.getSelectedIndex() >= 0;
        }
        changeCompilerSet((CompilerSet) lstDirlist.getSelectedValue());
        btAdd.setEnabled(isHostValidForEditing());
        btRemove.setEnabled(cbRemoveEnabled && isHostValidForEditing());
        btDuplicate.setEnabled(lstDirlist.getSelectedIndex() >= 0 && isHostValidForEditing());
        btDefault.setEnabled(lstDirlist.getSelectedIndex() >= 0 && !((CompilerSetImpl) lstDirlist.getSelectedValue()).isDefault());
    }

    private void onNewDevHostSelected() {
        if (!execEnv.equals(getSelectedRecord().getExecutionEnvironment())) {
            log.fine("TP.itemStateChanged: About to update");
            changed = true;
            if (!tcm.hasCache()) {
                List<ServerRecord> nulist = new ArrayList<ServerRecord>(cbDevHost.getItemCount());
                for (int i = 0; i < cbDevHost.getItemCount(); i++) {
                    nulist.add((ServerRecord) cbDevHost.getItemAt(i));
                }
                tcm.setHosts(nulist);
            }
            tcm.setDefaultRecord((ServerRecord) cbDevHost.getSelectedItem());
            execEnv = getSelectedRecord().getExecutionEnvironment();
            model.setSelectedDevelopmentHost(execEnv);
            update(true);
        } else {
            update(false);
        }
    }

    private void removeCompilerSet() {
        CompilerSetImpl cs = (CompilerSetImpl) lstDirlist.getSelectedValue();
        if (cs != null) {
            int index = csm.getCompilerSets().indexOf(cs);
            boolean wasDefault = csm.isDefaultCompilerSet(cs);
            csm.remove(cs);
            if (wasDefault) {
                if (csm.getCompilerSets().size() > 0) {
                    csm.setDefault(csm.getCompilerSet(0));
                }
            }
            if (index >= 0 && index < csm.getCompilerSets().size()) {
                update(false, csm.getCompilerSets().get(index), null);
            } else if (index > 0) {
                update(false, csm.getCompilerSets().get(index - 1), null);
            } else {
                getToolCollectionPanel().removeCompilerSet();
                update(false);
            }
            changed = true;
        }
    }

    private void setSelectedAsDefault() {
        CompilerSet cs = (CompilerSet) lstDirlist.getSelectedValue();
        csm.setDefault(cs);
        changed = true;
        update(false);

    }

    /** Update the display */
    public void update() {
        tcm.clear();
        update(true, null, null);
    }

    /** Update the display  and show initial warnings */
    public void update(List<String> errs) {
        update(true, null, errs);
    }

    private void update(boolean doInitialize) {
        update(doInitialize, null, null);
    }

    /** Update the display */
    private void update(final boolean doInitialize, final CompilerSet selectedCS, final List<String> errs) {
        updating = true;
        if (!initialized || doInitialize) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            showHideToolchainInitialization(false);
            RP.post(new Runnable(){
                @Override
                public void run() {
                    try {
                     initializeLong();
                    } finally {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                initializeUI();
                                updateUI(doInitialize, selectedCS, errs);
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            }
                        });
                    }
                }
            });
        } else {
            updateUI(doInitialize, selectedCS, errs);
        }
    }

    private void updateUI(boolean doInitialize, CompilerSet selectedCS, List<String> errs){
        getToolCollectionPanel().updateUI(doInitialize, selectedCS);

        if (doInitialize) {
            // Set Default
            if (!csm.getCompilerSets().isEmpty()) {
                if (csm.getDefaultCompilerSet() == null) {
                    String name = getCompilerSetName(); // the default set
                    if (name.length() == 0 || csm.getCompilerSet(name) == null) {
                        csm.setDefault(csm.getCompilerSet(0));
                    } else {
                        csm.setDefault(csm.getCompilerSet(name));
                    }
                }
                String selectedName = model.getSelectedCompilerSetName(); // The selected set
                if (selectedName != null) {
                    selectedCS = csm.getCompilerSet(selectedName);

                }
                if (selectedCS == null) {
                    selectedCS = csm.getDefaultCompilerSet();
                }
            }
        }

        if (selectedCS == null) {
            selectedCS = (CompilerSet) lstDirlist.getSelectedValue();
        }
        lstDirlist.setListData(csm.getCompilerSets().toArray());
        if (selectedCS != null) {
            lstDirlist.setSelectedValue(selectedCS, true); // FIXUP: should use name
        }
        if (lstDirlist.getSelectedIndex() < 0) {
            lstDirlist.setSelectedIndex(0);
        }
        lstDirlist.invalidate();
        lstDirlist.repaint();
        onCompilerSetChanged();
        updating = false;
        dataValid(errs);
        initialized = true;
    }

    private String getCompilerSetName() {
        String name = NbPreferences.forModule(ToolsPanel.class).get(PROP_COMPILER_SET_NAME, null);
        if (name == null) {
            return "";
        } else {
            return name;
        }
    }

    private void setCompilerSetName(String name) {
        String n = getCompilerSetName();
        if (n == null || !n.equals(name)) {
            NbPreferences.forModule(ToolsPanel.class).put(PROP_COMPILER_SET_NAME, name);
            firePropertyChange(PROP_COMPILER_SET_NAME, n, name);
        }
    }

    boolean isRemoteHostSelected() {
        return ((ServerRecord) cbDevHost.getSelectedItem()).isRemote();
    }

    private ServerRecord getSelectedRecord() {
        return (ServerRecord) cbDevHost.getSelectedItem();
    }

    private boolean isHostValidForEditing() {
        return true; //serverList == null ? true : serverList.get((String)cbDevHost.getSelectedItem()).isOnline();
    }

    private void changeCompilerSet(CompilerSet cs) {
        getToolCollectionPanel().preChangeCompilerSet(cs);
        if (cs == null) {
            String errorMsg = "";
            if (!ToolsUtils.isDevHostValid(execEnv)) {
                String key;
                if (model.getEnableDevelopmentHostChange()) {
                    key = "TP_ErrorMessage_BadDevHost"; // NOI18N
                } else {
                    key = "TP_ErrorMessage_BadDevHostReadOnly"; // NOI18N
                }
                errorMsg = NbBundle.getMessage(ToolsPanel.class, key, execEnv.toString());
            }
            lblErrors.setText("<html>" + errorMsg + "</html>"); //NOI18N
            updateToolsControls(false, false, true);
            return;
        }
        if (currentCompilerSet != null && currentCompilerSet != cs) {
            getToolCollectionPanel().updateCompilerSet(currentCompilerSet, false);
        }

        changingCompilerSet = true;
        getToolCollectionPanel().changeCompilerSet(cs);
        changingCompilerSet = false;
        currentCompilerSet = cs;
        fireCompilerSetChange();
        dataValid();
    }

    public String getSelectedToolchain() {
        return currentCompilerSet != null ? currentCompilerSet.getName() : "";
    }

    public void applyChanges(boolean force) {
        changed = force;
        applyChanges();
    }

    /** Apply changes */
    public void applyChanges() {
        boolean changedInOtherPanels = ToolsPanelSupport.isChangedInOtherPanels();
        if (changed || changedInOtherPanels) {
            List<Runnable> fireChanges = null;
            if (changedInOtherPanels) {
                fireChanges = ToolsPanelSupport.saveChangesInOtherPanels();
            }

            CompilerSet cs = (CompilerSet) lstDirlist.getSelectedValue();
            changed = false;
            if (cs != null) {
                getToolCollectionPanel().updateCompilerSet(cs, true);
                model.setCompilerSetName(csm.getDefaultCompilerSet().getName());
                model.setSelectedCompilerSetName(cs.getName());
            }
            currentCompilerSet = cs;
            tcm.applyChanges((ServerRecord) cbDevHost.getSelectedItem());
            if (fireChanges != null) {
                for(Runnable fire : fireChanges) {
                    fire.run();
                }
            }
        }
        getToolCollectionPanel().applyChanges();
    }

    /** What to do if user cancels the dialog (nothing) */
    public void cancel() {
        tcm.cancel();
        changed = false;
    }

    public CompilerSet getCurrentCompilerSet() {
        return currentCompilerSet;
    }

    CompilerSetManager getCompilerSetManager(){
        return csm;
    }

    boolean isUpdatindOrChangingCompilerSet(){
        return updating || changingCompilerSet;
    }

    /**
     * Lets NB know if the data in the panel is valid and OK should be enabled
     *
     * @return Returns true if all data is valid
     */
    public boolean dataValid() {
        return dataValid(null);
    }

    private boolean dataValid(List<String> errs) {
        if (csm.getCompilerSets().isEmpty()) {
            if (valid != ValidState.INVALID) {
                valid = ValidState.INVALID;
                firePropertyChange(PROP_VALID, true, false);
            }
            return false;
        }
        if (updating || changingCompilerSet) {
            return true;
        } else {
            boolean csmValid = csm.getCompilerSets().size() > 0;
            boolean isToolsValid = getToolCollectionPanel().isToolsValid();
            boolean devhostValid = ToolsUtils.isDevHostValid(execEnv);

            if (csmValid && isToolsValid && devhostValid) {
                if (valid != ValidState.VALID) {
                    valid = ValidState.VALID;
                    firePropertyChange(PROP_VALID, false, true);
                }
            } else {
                if (valid != ValidState.INVALID) {
                    valid = ValidState.INVALID;
                    firePropertyChange(PROP_VALID, true, false);
                }
            }

            // post errors in error text area
            if (valid == ValidState.INVALID || (errs != null && errs.size()>0)) {
                lblErrors.setText("<html>"); // NOI18N
                ArrayList<String> errors = new ArrayList<String>();
                if (errs != null) {
                    errors.addAll(errs);
                }
                if (!devhostValid) {
                    String key;
                    if (model.getEnableDevelopmentHostChange()) {
                        key = "TP_ErrorMessage_BadDevHost"; // NOI18N
                    } else {
                        key = "TP_ErrorMessage_BadDevHostReadOnly"; // NOI18N
                    }
                    errors.add(NbBundle.getMessage(ToolsPanel.class, key, execEnv.toString()));
                }
                getToolCollectionPanel().getErrors(errors);
                StringBuilder errorString = new StringBuilder();
                for (int i = 0; i < errors.size(); i++) {
                    errorString.append(errors.get(i));
                    if (i < errors.size() - 1) {
                        errorString.append("<br>"); // NOI18N
                    }
                }
                lblErrors.setText("<html>" + errorString.toString() + "</html>"); //NOI18N

                validate();
                repaint();
            } else {
                lblErrors.setText("");
            }

            boolean baseDirValid = getToolCollectionPanel().isBaseDirValid();
            boolean enableText = baseDirValid || (isRemoteHostSelected() && isHostValidForEditing());
            boolean enableVersions = (baseDirValid || isRemoteHostSelected()) && isHostValidForEditing();
            updateToolsControls(enableText, enableVersions, false);

            return valid == ValidState.VALID;
        }
    }

    private void updateToolsControls(boolean enableText, boolean enableVersions, boolean cleanText) {
        btVersions.setEnabled(enableVersions);
        getToolCollectionPanel().updateToolsControls(enableText, enableVersions, cleanText);
    }

    /**
     * Lets caller know if any data has been changed.
     *
     * @return True if anything has been changed
     */
    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void fireCompilerSetChange() {
        ToolsPanelSupport.fireCompilerSetChange(currentCompilerSet);
    }

    public void fireCompilerSetModified() {
        ToolsPanelSupport.fireCompilerSetModified(currentCompilerSet);
    }

    // implement ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o instanceof JButton) {
            if (o == btAdd) {
                addCompilerSet();
            } else if (o == btRemove) {
                removeCompilerSet();
            } else if (o == btDuplicate) {
                duplicateCompilerSet();
            } else if (o == btEditDevHost) {
                editDevHosts();
            } else if (o == btDefault) {
                setSelectedAsDefault();
            }
        }
    }

    // implemet ItemListener
    @Override
    public void itemStateChanged(ItemEvent ev) {
        Object o = ev.getSource();
        if (!updating) {
            if (o == cbDevHost && ev.getStateChange() == ItemEvent.SELECTED) {
                onNewDevHostSelected();
            }
        }
    }

    // Implement List SelectionListener
    @Override
    public void valueChanged(ListSelectionEvent ev) {

        if (!ev.getValueIsAdjusting() && !updating) { // we don't want the event until its finished
            if (ev.getSource() == lstDirlist) {
                onCompilerSetChanged();
            }
        }
    }

    /**
     * Show the Development Host Manager. Note that we assume serverList is non-null as the Edit
     * button should <b>never</b> be enabled if its null.
     */
    private void editDevHosts() {
        // Show the Dev Host Manager dialog
        if (ServerListUIEx.showServerListDialog(tcm, null)) {
            changed = true;
            cbDevHost.removeItemListener(this);
            log.fine("TP.editDevHosts: Removing all items from cbDevHost");
            cbDevHost.removeAllItems();
            log.log(Level.FINE, "TP.editDevHosts: Adding {0} items to cbDevHost", tcm.getHosts().size());
            for (ServerRecord rec : tcm.getHosts()) {
                log.log(Level.FINE, "    Adding {0}", rec);
                cbDevHost.addItem(rec);
            }
            log.log(Level.FINE, "TP.editDevHosts: cbDevHost has {0} items", cbDevHost.getItemCount());
            log.log(Level.FINE, "TP.editDevHosts: getDefaultHostRecord returns {0}", tcm.getDefaultHostRecord());
            cbDevHost.setSelectedItem(tcm.getDefaultHostRecord());
            ToolsUtils.ensureHostSetup(getSelectedRecord().getExecutionEnvironment());
            cbDevHost.addItemListener(this);
            onNewDevHostSelected();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttomPanel = new javax.swing.JPanel();
        lblErrors = new javax.swing.JLabel();
        btVersions = new javax.swing.JButton();
        btRestore = new javax.swing.JButton();
        ToolSetPanel = new javax.swing.JPanel();
        spDirlist = new JScrollPane(lstDirlist);
        lstDirlist = new javax.swing.JList();
        lbToolCollections = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        btAdd = new javax.swing.JButton();
        btAdd.addActionListener(this);
        btRemove = new javax.swing.JButton();
        btRemove.addActionListener(this);
        btDuplicate = new javax.swing.JButton();
        btDuplicate.addActionListener(this);
        btDefault = new javax.swing.JButton();
        btDefault.addActionListener(this);
        lbDevHost = new javax.swing.JLabel();
        cbDevHost = new javax.swing.JComboBox();
        cbDevHost.addItemListener(this);
        btEditDevHost = new javax.swing.JButton();
        btEditDevHost.addActionListener(this);
        toolCollectionPanel = new ToolCollectionPanel(this);
        loadingToolCollectionPanel = new javax.swing.JPanel();
        lblLoadToolsProgress = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(600, 420));
        setPreferredSize(new java.awt.Dimension(600, 420));
        setLayout(new java.awt.GridBagLayout());

        buttomPanel.setMinimumSize(new java.awt.Dimension(150, 26));
        buttomPanel.setOpaque(false);
        buttomPanel.setPreferredSize(new java.awt.Dimension(150, 26));
        buttomPanel.setLayout(new java.awt.GridBagLayout());

        lblErrors.setForeground(new java.awt.Color(255, 51, 51));
        lblErrors.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblErrors.setText(org.openide.util.NbBundle.getMessage(ToolsPanel.class, "ToolsPanel.lblErrors.text")); // NOI18N
        lblErrors.setEnabled(false);
        lblErrors.setFocusable(false);
        lblErrors.setPreferredSize(new java.awt.Dimension(0, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        buttomPanel.add(lblErrors, gridBagConstraints);
        lblErrors.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(ToolsPanel.class, "ToolsPanel.lblErrors.AccessibleContext.accessibleName")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btVersions, org.openide.util.NbBundle.getMessage(ToolsPanel.class, "ToolsPanel.btVersions.text")); // NOI18N
        btVersions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVersionsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        buttomPanel.add(btVersions, gridBagConstraints);
        btVersions.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ToolsPanel.class, "ToolsPanel.btVersions.AccessibleContext.accessibleDescription")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btRestore, org.openide.util.NbBundle.getMessage(ToolsPanel.class, "ToolsPanel.btRestore.text")); // NOI18N
        btRestore.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRestoreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        buttomPanel.add(btRestore, gridBagConstraints);
        btRestore.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ToolsPanel.class, "ToolsPanel.btRestore.AccessibleContext.accessibleDescription")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        add(buttomPanel, gridBagConstraints);

        ToolSetPanel.setOpaque(false);
        ToolSetPanel.setLayout(new java.awt.GridBagLayout());

        spDirlist.setMinimumSize(new java.awt.Dimension(180, 20));
        spDirlist.setPreferredSize(new java.awt.Dimension(180, 20));

        lstDirlist.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstDirlist.setAutoscrolls(false);
        lstDirlist.addListSelectionListener(this);
        spDirlist.setViewportView(lstDirlist);
        lstDirlist.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(ToolsPanel.class, "ToolsPanel.lstDirlist.AccessibleContext.accessibleName")); // NOI18N
        lstDirlist.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ToolsPanel.class, "ToolsPanel.lstDirlist.AccessibleContext.accessibleDescription")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        ToolSetPanel.add(spDirlist, gridBagConstraints);

        lbToolCollections.setLabelFor(spDirlist);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/toolchain/ui/options/Bundle"); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(lbToolCollections, bundle.getString("LBL_DirlistLabel")); // NOI18N
        lbToolCollections.setToolTipText(bundle.getString("HINT_DirListLabel")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 4);
        ToolSetPanel.add(lbToolCollections, gridBagConstraints);
        lbToolCollections.getAccessibleContext().setAccessibleName(bundle.getString("ACSN_DirlistLabel")); // NOI18N
        lbToolCollections.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_DirlistLabel")); // NOI18N

        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(btAdd, bundle.getString("LBL_AddButton")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        buttonPanel.add(btAdd, gridBagConstraints);
        btAdd.getAccessibleContext().setAccessibleName(bundle.getString("ACSN_AddButton")); // NOI18N
        btAdd.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_AddButton")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btRemove, bundle.getString("LBL_RemoveButton")); // NOI18N
        btRemove.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        buttonPanel.add(btRemove, gridBagConstraints);
        btRemove.getAccessibleContext().setAccessibleName(bundle.getString("ACSN_RemoveButton")); // NOI18N
        btRemove.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_RemoveButton")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btDuplicate, bundle.getString("LBL_UpButton")); // NOI18N
        btDuplicate.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        buttonPanel.add(btDuplicate, gridBagConstraints);
        btDuplicate.getAccessibleContext().setAccessibleName(bundle.getString("ACSN_UpButton")); // NOI18N
        btDuplicate.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_UpButton")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btDefault, bundle.getString("LBL_DownButton")); // NOI18N
        btDefault.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        buttonPanel.add(btDefault, gridBagConstraints);
        btDefault.getAccessibleContext().setAccessibleName(bundle.getString("ACSN_DownButton")); // NOI18N
        btDefault.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_DownButton")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        ToolSetPanel.add(buttonPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        add(ToolSetPanel, gridBagConstraints);

        lbDevHost.setLabelFor(cbDevHost);
        org.openide.awt.Mnemonics.setLocalizedText(lbDevHost, org.openide.util.NbBundle.getMessage(ToolsPanel.class, "LBL_DevelopmentHosts")); // NOI18N
        lbDevHost.setToolTipText(org.openide.util.NbBundle.getMessage(ToolsPanel.class, "HINT_DevelopmentHosts")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        add(lbDevHost, gridBagConstraints);

        cbDevHost.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        add(cbDevHost, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(btEditDevHost, org.openide.util.NbBundle.getMessage(ToolsPanel.class, "Lbl_AddDevHost")); // NOI18N
        btEditDevHost.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
        add(btEditDevHost, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        add(toolCollectionPanel, gridBagConstraints);

        loadingToolCollectionPanel.setEnabled(false);
        loadingToolCollectionPanel.setFocusable(false);
        loadingToolCollectionPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        loadingToolCollectionPanel.setRequestFocusEnabled(false);
        loadingToolCollectionPanel.setVerifyInputWhenFocusTarget(false);
        loadingToolCollectionPanel.setLayout(new java.awt.BorderLayout());

        lblLoadToolsProgress.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLoadToolsProgress.setText(org.openide.util.NbBundle.getMessage(ToolsPanel.class, "ToolsPanel.lblLoadToolsProgress.text")); // NOI18N
        loadingToolCollectionPanel.add(lblLoadToolsProgress, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(loadingToolCollectionPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

private void btVersionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVersionsActionPerformed
    btVersions.setEnabled(false);
    final CompilerSet set = currentCompilerSet;
    if (set == null) {
        return;
    }

    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    RP.post(new Runnable() {

            @Override
        public void run() {
            if (!ConnectionManager.getInstance().isConnectedTo(getSelectedRecord().getExecutionEnvironment())) {
                try {
                    ConnectionManager.getInstance().connectTo(getSelectedRecord().getExecutionEnvironment());
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (CancellationException ex) {
                    // don't report CancellationException
                }
            }
            String versions = null;
            if (ConnectionManager.getInstance().isConnectedTo(getSelectedRecord().getExecutionEnvironment())) {
                versions = getToolCollectionPanel().getVersion(set);
            }
            SwingUtilities.invokeLater(new Runnable() {
                    @Override
                public void run() {
                    btVersions.setEnabled(true);
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });
            if (versions != null) {
                NotifyDescriptor nd = new NotifyDescriptor.Message(versions);
                nd.setTitle(getString("LBL_VersionInfo_Title")); // NOI18N
                DialogDisplayer.getDefault().notify(nd);
            }
        }
    });
}//GEN-LAST:event_btVersionsActionPerformed

    private void initCustomizableDebugger() {
        ToolsPanelGlobalCustomizer customizer = Lookup.getDefault().lookup(ToolsPanelGlobalCustomizer.class);
        customizeDebugger = customizer == null ? true : customizer.isDebuggerCustomizable();
    }

    boolean isCustomizableDebugger() {
        return customizeDebugger;
    }

    private static class MyCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            CompilerSetImpl cs = (CompilerSetImpl) value;
            if (cs != null && cs.isDefault()) {
                comp.setFont(comp.getFont().deriveFont(Font.BOLD));
            }
            return comp;
        }
    }

    private class MyDevHostListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            ServerRecord rec = (ServerRecord) value;
            if (rec != null) {
                label.setText(rec.getDisplayName());
                if (rec.equals(tcm.getDefaultHostRecord())) {
                    label.setFont(label.getFont().deriveFont(Font.BOLD));
                }
            }
            return label;
        }
    }

private void btRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRestoreActionPerformed
    if (csm == null) {
        // restore is available after long initialization
        return;
    }

    NotifyDescriptor nd = new NotifyDescriptor.Confirmation(
            getString("RESTORE_TXT"), // NOI18N
            getString("RESTORE_TITLE"), // NOI18N
            NotifyDescriptor.OK_CANCEL_OPTION);
    if (DialogDisplayer.getDefault().notify(nd) != NotifyDescriptor.OK_OPTION) {
        return;
    }

    final CompilerSet selected = (CompilerSet) lstDirlist.getSelectedValue();
    final String selectedName = selected == null? null : selected.getName();
    final AtomicBoolean newCsmCreated = new AtomicBoolean();

    final Runnable postWork = new Runnable() {
        @Override
        public void run() {
            showHideToolchainInitialization(true);
            if (newCsmCreated.get()) {
                changed = true;
                CompilerSet selected = csm.getCompilerSet(selectedName);
                if (selected != null) {
                    update(false, selected, null);
                } else {
                    update(false);
                }
            }
        }
    };

    Runnable longWork = new Runnable() {
        @Override
        public void run() {
            CompilerSetManagerImpl newCsm = null;
            try {
                HostInfoUtils.updateHostInfo(execEnv);
                newCsm = tcm.restoreCompilerSets(csm);
                if (newCsm != null) {
                    csm = newCsm;
                    newCsmCreated.set(true);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                // don't report InterruptedException
            } finally {
                SwingUtilities.invokeLater(postWork);
            }
        }
    };

    showHideToolchainInitialization(false);
    RP.post(longWork);
}//GEN-LAST:event_btRestoreActionPerformed

    static String getString(String key) {
        return NbBundle.getMessage(ToolsPanel.class, key);
    }

    static String getString(String key, Object param) {
        return NbBundle.getMessage(ToolsPanel.class, key, param);
    }

    static String getString(String key, Object param1, Object param2) {
        return NbBundle.getMessage(ToolsPanel.class, key, param1, param2);
    }

    static String getString(String key, Object param1, Object param2, Object param3) {
        return NbBundle.getMessage(ToolsPanel.class, key, param1, param2, param3);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ToolSetPanel;
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btDefault;
    private javax.swing.JButton btDuplicate;
    private javax.swing.JButton btEditDevHost;
    private javax.swing.JButton btRemove;
    private javax.swing.JButton btRestore;
    private javax.swing.JButton btVersions;
    private javax.swing.JPanel buttomPanel;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JComboBox cbDevHost;
    private javax.swing.JLabel lbDevHost;
    private javax.swing.JLabel lbToolCollections;
    private javax.swing.JLabel lblErrors;
    private javax.swing.JLabel lblLoadToolsProgress;
    private javax.swing.JPanel loadingToolCollectionPanel;
    private javax.swing.JList lstDirlist;
    private javax.swing.JScrollPane spDirlist;
    private javax.swing.JPanel toolCollectionPanel;
    // End of variables declaration//GEN-END:variables
}
