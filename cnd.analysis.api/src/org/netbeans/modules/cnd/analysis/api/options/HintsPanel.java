/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
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

package org.netbeans.modules.cnd.analysis.api.options;

import org.netbeans.modules.cnd.analysis.api.AbstractHintsPanel;
import org.netbeans.modules.cnd.analysis.api.AuditPreferences;
import org.netbeans.modules.cnd.analysis.api.CodeAudit;
import org.netbeans.modules.cnd.analysis.api.CodeAuditProvider;
import org.netbeans.modules.cnd.analysis.api.CodeAuditProviderImpl;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.netbeans.modules.cnd.utils.ui.NamedOption;
import org.openide.util.lookup.Lookups;


public class HintsPanel extends AbstractHintsPanel implements TreeCellRenderer  {
    
    private final DefaultTreeCellRenderer dr = new DefaultTreeCellRenderer();
    private final JCheckBox renderer = new JCheckBox();
    private HintsPanelLogic logic;
    private Preferences preferences;

    public HintsPanel(CodeAuditProvider selection) {        
        initComponents();
        if (selection != null) {
            this.preferences = selection.getPreferences().getPreferences();
        } else {
            this.preferences = AuditPreferences.AUDIT_PREFERENCES_ROOT;
        }
        descriptionTextArea.setContentType("text/html"); // NOI18N
        errorTree.setCellRenderer( this );
        errorTree.setRootVisible( false );
        errorTree.setShowsRootHandles( true );
        errorTree.getSelectionModel().setSelectionMode( TreeSelectionModel.SINGLE_TREE_SELECTION );
        
        update();

        errorTree.setModel(new ExtendedModel(selection));
    }
    
    @Override
    public void setSettings(Preferences settings) {
        if (preferences == null) {
            preferences = settings;
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

        jSplitPane1 = new javax.swing.JSplitPane();
        treePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        errorTree = new javax.swing.JTree();
        detailsPanel = new javax.swing.JPanel();
        optionsPanel = new javax.swing.JPanel();
        severityLabel = new javax.swing.JLabel();
        severityComboBox = new javax.swing.JComboBox();
        customizerPanel = new javax.swing.JPanel();
        descriptionPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JEditorPane();
        descriptionLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setPreferredSize(new java.awt.Dimension(400, 400));
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(400, 400));

        treePanel.setOpaque(false);
        treePanel.setPreferredSize(new java.awt.Dimension(200, 400));
        treePanel.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 200));
        jScrollPane1.setViewportView(errorTree);
        errorTree.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(HintsPanel.class, "HintsPanel.errorTree.AccessibleContext.accessibleName")); // NOI18N
        errorTree.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(HintsPanel.class, "HintsPanel.errorTree.AccessibleContext.accessibleDescription")); // NOI18N

        treePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(treePanel);

        detailsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 6, 0, 0));
        detailsPanel.setOpaque(false);
        detailsPanel.setPreferredSize(new java.awt.Dimension(200, 400));
        detailsPanel.setLayout(new java.awt.BorderLayout());

        optionsPanel.setOpaque(false);
        optionsPanel.setPreferredSize(new java.awt.Dimension(200, 200));

        severityLabel.setLabelFor(severityComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(severityLabel, org.openide.util.NbBundle.getMessage(HintsPanel.class, "CTL_ShowAs_Label")); // NOI18N

        customizerPanel.setOpaque(false);
        customizerPanel.setLayout(new java.awt.BorderLayout());

        org.jdesktop.layout.GroupLayout optionsPanelLayout = new org.jdesktop.layout.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(optionsPanelLayout.createSequentialGroup()
                .add(0, 0, 0)
                .add(optionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(optionsPanelLayout.createSequentialGroup()
                        .add(customizerPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(optionsPanelLayout.createSequentialGroup()
                        .add(severityLabel)
                        .add(18, 18, 18)
                        .add(severityComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 249, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(103, Short.MAX_VALUE))))
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(optionsPanelLayout.createSequentialGroup()
                .add(4, 4, 4)
                .add(optionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(severityLabel)
                    .add(severityComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(8, 8, 8)
                .add(customizerPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );

        severityLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(HintsPanel.class, "HintsPanel.severityLabel.AccessibleContext.accessibleDescription")); // NOI18N
        severityComboBox.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(HintsPanel.class, "AN_Show_As_Combo")); // NOI18N
        severityComboBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(HintsPanel.class, "AD_Show_As_Combo")); // NOI18N

        detailsPanel.add(optionsPanel, java.awt.BorderLayout.CENTER);

        descriptionPanel.setOpaque(false);
        descriptionPanel.setPreferredSize(new java.awt.Dimension(200, 200));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        descriptionTextArea.setEditable(false);
        descriptionTextArea.setPreferredSize(new java.awt.Dimension(100, 50));
        jScrollPane2.setViewportView(descriptionTextArea);

        descriptionLabel.setLabelFor(descriptionTextArea);
        org.openide.awt.Mnemonics.setLocalizedText(descriptionLabel, org.openide.util.NbBundle.getMessage(HintsPanel.class, "CTL_Description_Border")); // NOI18N

        org.jdesktop.layout.GroupLayout descriptionPanelLayout = new org.jdesktop.layout.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(descriptionPanelLayout.createSequentialGroup()
                .add(descriptionLabel)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(descriptionPanelLayout.createSequentialGroup()
                .add(descriptionLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
        );

        jScrollPane2.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(HintsPanel.class, "HintsPanel.jScrollPane2.AccessibleContext.accessibleName")); // NOI18N

        detailsPanel.add(descriptionPanel, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(detailsPanel);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(HintsPanel.class, "HintsPanel.AccessibleContext.accessibleName")); // NOI18N
        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(HintsPanel.class, "HintsPanel.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    
        
    synchronized void update() {
        if ( logic != null ) {
            logic.disconnect();
        }
        logic = new HintsPanelLogic();
        logic.connect(errorTree, severityLabel, severityComboBox,
                customizerPanel, descriptionTextArea,
                preferences);
    }
    
    void cancel() {
        logic.disconnect();
        logic = null;
    }
    
    boolean isChanged() {
        return logic != null ? logic.isChanged() : false;
    }
    
    void applyChanges() {
        logic.applyChanges();
        logic.disconnect();
        logic = null;
    }
           
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        
        renderer.setBackground( selected ? dr.getBackgroundSelectionColor() : dr.getBackgroundNonSelectionColor() );
        renderer.setForeground( selected ? dr.getTextSelectionColor() : dr.getTextNonSelectionColor() );
        renderer.setEnabled( true );
        if (value instanceof DefaultMutableTreeNode) {
            Object data = ((DefaultMutableTreeNode)value).getUserObject();
            if ( data instanceof CodeAudit ) {
                CodeAudit audit = (CodeAudit)data;
                if (audit.getName().equals(audit.getDescription())) {
                    renderer.setText(audit.getName());
                } else {
                    renderer.setText( audit.getName()+ ": " + audit.getDescription()); // NOI18N
                }
                renderer.setSelected(audit.isEnabled());
            } else if (data instanceof CodeAuditProvider) {
                CodeAuditProvider provider = (CodeAuditProvider)data;
                renderer.setText( provider.getDisplayName());
                boolean hasEnabled = false;
                boolean hasDisabled = false;
                for(int i = 0; i < ((DefaultMutableTreeNode)value).getChildCount(); i++) {
                    DefaultMutableTreeNode childAt = (DefaultMutableTreeNode) ((DefaultMutableTreeNode)value).getChildAt(i);
                    CodeAudit audit = (CodeAudit) childAt.getUserObject();
                    if (audit.isEnabled()) {
                        hasEnabled = true;
                    } else {
                        hasDisabled = true;
                    }
                }
                if (hasEnabled) {
                    if (hasDisabled) {
                        //TODO make partly selected state
                        renderer.setSelected(true);
                    } else {
                        renderer.setSelected(true);
                    }
                } else {
                    renderer.setSelected(false);
                }
            } else if (data instanceof NamedOption) {
                NamedOption option = (NamedOption)data;
                renderer.setText( option.getDisplayName());
                renderer.setSelected(NamedOption.getAccessor().getBoolean(option.getName()));
            }
        }

        return renderer;
    }
        
    // Variables declaration - do not modify                     
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel customizerPanel;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JEditorPane descriptionTextArea;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JTree errorTree;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JComboBox severityComboBox;
    private javax.swing.JLabel severityLabel;
    private javax.swing.JPanel treePanel;
    // End of variables declaration//GEN-END:variables

    static class ExtendedModel implements TreeModel {
        private final List<DefaultMutableTreeNode> audits;
        private ExtendedModel(CodeAuditProvider selection){
            audits = new ArrayList<DefaultMutableTreeNode>();
            if (selection == null) {
                for(CodeAuditProvider provider : CodeAuditProviderImpl.getDefault()) {
                    DefaultMutableTreeNode providerRoot = new DefaultMutableTreeNode(provider);
                    audits.add(providerRoot);
                    for(CodeAudit audit : provider.getAudits()) {
                        providerRoot.add(new DefaultMutableTreeNode(audit));
                    }
                }
                for (NamedOption option : Lookups.forPath(NamedOption.HINTS_CATEGORY).lookupAll(NamedOption.class)) {
                    if (option.isVisible()) {
                        audits.add(new DefaultMutableTreeNode(option));
                    }
                }
            } else {
                DefaultMutableTreeNode providerRoot = new DefaultMutableTreeNode(selection);
                audits.add(providerRoot);
                for(CodeAudit audit : selection.getAudits()) {
                    providerRoot.add(new DefaultMutableTreeNode(audit));
                }
            }
        }

        @Override
        public Object getRoot() {
            return "Root"; //NOI18N
        }

        @Override
        public Object getChild(Object parent, int index) {
            if (parent == getRoot()) {
                return audits.get(index);
            } else if (parent instanceof DefaultMutableTreeNode) {
                return ((DefaultMutableTreeNode)parent).getChildAt(index);
            }
            return null;
        }

        @Override
        public int getChildCount(Object parent) {
            if (parent == getRoot()) {
                return audits.size();
            } else if (parent instanceof DefaultMutableTreeNode) {
                return ((DefaultMutableTreeNode)parent).getChildCount();
            }
            return 0;
        }

        @Override
        public boolean isLeaf(Object node) {
            if (node == getRoot()) {
                return false;
            } else if (node instanceof DefaultMutableTreeNode) {
                return ((DefaultMutableTreeNode)node).isLeaf();
            }
            return true;
        }

        @Override
        public void valueForPathChanged(TreePath path, Object newValue) {
        }

        @Override
        public int getIndexOfChild(Object parent, Object child) {
            if (parent == getRoot()) {
                return audits.indexOf(child);
            } else if (parent instanceof DefaultMutableTreeNode) {
                return ((DefaultMutableTreeNode)parent).getIndex((DefaultMutableTreeNode)child);
            }
            return 0;
        }

        @Override
        public void addTreeModelListener(TreeModelListener l) {
        }

        @Override
        public void removeTreeModelListener(TreeModelListener l) {
        }

        public void nodeChanged(TreeNode node) {
            if (node instanceof DefaultMutableTreeNode) {
                Object data = ((DefaultMutableTreeNode)node).getUserObject();
                if ( data instanceof CodeAudit ) {
                    CodeAudit rule = (CodeAudit)data;
                } else if (data instanceof CodeAuditProvider) {
                    CodeAuditProvider provider = (CodeAuditProvider)data;
                } else if (data instanceof NamedOption) {
                    NamedOption option = (NamedOption)data;
                }
            }
        }
    }
}

