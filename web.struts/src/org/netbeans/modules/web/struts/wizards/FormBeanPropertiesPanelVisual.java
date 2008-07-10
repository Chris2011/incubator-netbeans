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

package org.netbeans.modules.web.struts.wizards;

import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.openide.WizardDescriptor;

import org.openide.util.NbBundle;

public class FormBeanPropertiesPanelVisual extends javax.swing.JPanel implements WizardDescriptor.Panel {

    private PropertiesTableModel model;

    /**
     * Creates new form PropertiesPanelVisual
     */
    public FormBeanPropertiesPanelVisual() {
        initComponents();
        initTable();
        
//        this.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(FormBeanNewPanelVisual.class, "ACS_BeanFormProperties"));  // NOI18N
    }
    
    private void initTable() {
        model = new PropertiesTableModel();
        jTableProperties.setModel(model);
        jTableProperties.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        PropertiesTableCellRenderer renderer = new PropertiesTableCellRenderer();
//        jTableProperties.setDefaultRenderer(String.class, renderer);

        jTableProperties.setRowHeight(jTableProperties.getFontMetrics(jTableProperties.getFont()).getHeight() + 4);        
        jTableProperties.getParent().setBackground(jTableProperties.getBackground());
    }
    
    public DefaultListModel getTableModel() {
        return model.getDefaultListModel();
    }

    public void removeChangeListener(javax.swing.event.ChangeListener l) {
    }

    public void addChangeListener(javax.swing.event.ChangeListener l) {
    }

    public void storeSettings(Object settings) {
    }

    public void readSettings(Object settings) {
    }

    public org.openide.util.HelpCtx getHelp() {
        return null;
    }

    public java.awt.Component getComponent() {
        return this;
    }
    
//    public java.awt.Dimension getPreferredSize() {
//        return new java.awt.Dimension(560,350);
//    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProperties = new javax.swing.JTable();
        jButtonAdd = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jTableProperties.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableProperties);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);

        jButtonAdd.setMnemonic(org.openide.util.NbBundle.getMessage(FormBeanPropertiesPanelVisual.class, "LBL_AddButton_LabelMnemonic").charAt(0));
        jButtonAdd.setText(org.openide.util.NbBundle.getMessage(FormBeanPropertiesPanelVisual.class, "LBL_AddButton"));
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 5, 0);
        add(jButtonAdd, gridBagConstraints);

        jButtonRemove.setMnemonic(org.openide.util.NbBundle.getMessage(FormBeanPropertiesPanelVisual.class, "LBL_RemoveButton_LabelMnemonic").charAt(0));
        jButtonRemove.setText(org.openide.util.NbBundle.getMessage(FormBeanPropertiesPanelVisual.class, "LBL_RemoveButton"));
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
        add(jButtonRemove, gridBagConstraints);

        jLabel1.setLabelFor(jTableProperties);
        jLabel1.setText(org.openide.util.NbBundle.getMessage(FormBeanPropertiesPanelVisual.class, "LBL_Properties"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(jLabel1, gridBagConstraints);

    }
    // </editor-fold>//GEN-END:initComponents

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
        int index = jTableProperties.getSelectedRow();
        if (index != -1)
            model.getDefaultListModel().remove(index);
    }//GEN-LAST:event_jButtonRemoveActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        model.addItem(new FormBeanProperty());
    }//GEN-LAST:event_jButtonAddActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProperties;
    // End of variables declaration//GEN-END:variables
    
//    boolean valid(WizardDescriptor wizardDescriptor) {
//        wizardDescriptor.putProperty( WizardDescriptor.PROP_ERROR_MESSAGE, null); //NOI18N
//        
//        for (int i = 0; i < model.getRowCount(); i++)
//            if (model.getItem(i) == null || (model.getItem(i).getProperty()).trim().equals("")) //NOI18N
//                return false;
//
//        return true;
//    }
//
//    void read (WizardDescriptor settings) {
//    }
//
//    void store(WizardDescriptor settings) {
//        settings.putProperty(WizardProperties.FORMBEAN_NEPROPERTIES, model.getDefaultListModel().elements());
//    }

//    public static class PropertiesTableCellRenderer extends DefaultTableCellRenderer {
//        private TableCellRenderer booleanRenderer;
//        
//        public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
//            if (value instanceof Boolean && booleanRenderer != null)
//                return booleanRenderer.getTableCellRendererComponent(table, value, isSelected, false, row, column);
//            else
//                return super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
//        }
//        
//        public void setBooleanRenderer(TableCellRenderer booleanRenderer) {
//            this.booleanRenderer = booleanRenderer;
//        }
//    }

    /** 
     * Implements a TableModel.
     */
    public static final class PropertiesTableModel extends AbstractTableModel implements ListDataListener {
        
        private DefaultListModel model;
        
        public PropertiesTableModel() {
            model = new DefaultListModel();
            model.addListDataListener(this);
        }
        
        public DefaultListModel getDefaultListModel() {
            return model;
        }

        public int getColumnCount() {
            return 2;
        }
        
        public int getRowCount() {
            return model.size();
        }
        
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return NbBundle.getMessage(FormBeanPropertiesPanelVisual.class, "LBL_PropertiesTableHeader_Property");//NOI18N
                case 1:
                    return NbBundle.getMessage(FormBeanPropertiesPanelVisual.class, "LBL_PropertiesTableHeader_Value");//NOI18N    
            }
            return "";  //NOI18N
        }

        public Class getColumnClass(int columnIndex) {
            if (columnIndex == 2)
                return Boolean.class;
            else
                return String.class;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
        
        public Object getValueAt(int row, int column) {
            FormBeanProperty item = getItem(row);
            switch (column) {
                case 0: return item.getProperty();
                case 1: return item.getType();
            }
            return "";
        }
        
        public void setValueAt(Object value, int row, int column) {
            FormBeanProperty item = getItem(row);
            switch (column) {
                case 0: item.setProperty((String) value);break;
                case 1: item.setType((String) value);
            }            
        }

        public void clear(){
            model.clear();
        }
        
        public void contentsChanged(ListDataEvent e) {
            fireTableRowsUpdated(e.getIndex0(), e.getIndex1());
        }
        
        public void intervalAdded(ListDataEvent e) {
            fireTableRowsInserted(e.getIndex0(), e.getIndex1());
        }
        
        public void intervalRemoved(ListDataEvent e) {
            fireTableRowsDeleted(e.getIndex0(), e.getIndex1());
        }

        private FormBeanProperty getItem(int index) {
            return (FormBeanProperty) model.get(index);
        }
        
        public void addItem(FormBeanProperty item){
            model.addElement(item);
        }
    }
}
