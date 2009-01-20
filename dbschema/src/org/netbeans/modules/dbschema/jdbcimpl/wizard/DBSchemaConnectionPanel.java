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

package org.netbeans.modules.dbschema.jdbcimpl.wizard;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.event.*;
import org.netbeans.api.db.explorer.ConnectionManager;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.netbeans.api.db.explorer.support.DatabaseExplorerUIs;

import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;

public class DBSchemaConnectionPanel extends JPanel implements ListDataListener {

    static final long serialVersionUID = 5364628520334696421L;

    private ArrayList list;
    private DBSchemaWizardData data;
    private Node dbNode;
    private Node[] drvNodes;

    /** Creates new form DBSchemaConnectionpanel */
    public DBSchemaConnectionPanel(DBSchemaWizardData data, ArrayList list) {
        this.list = list;
        this.data = data;

        putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, new Integer(1)); //NOI18N
        setName(bundle.getString("ConnectionChooser")); //NOI18N

        initComponents ();
        initAccessibility();

        FileObject fo = FileUtil.getConfigFile("UI/Runtime"); //NOI18N
        DataFolder df;
        try {
            df = (DataFolder) DataObject.find(fo);
        } catch (DataObjectNotFoundException exc) {
            return;
        }
        dbNode = df.getNodeDelegate().getChildren().findChild("Databases"); //NOI18N
        DatabaseExplorerUIs.connect(existingConnComboBox, ConnectionManager.getDefault());
        existingConnComboBox.getModel().addListDataListener(this);
    }
    
    private void initAccessibility() {
        this.getAccessibleContext().setAccessibleDescription(bundle.getString("ACS_ConnectionPanelA11yDesc"));  // NOI18N
        descriptionTextArea.getAccessibleContext().setAccessibleName(bundle.getString("ACS_DescriptionA11yName"));  // NOI18N
        descriptionTextArea.getAccessibleContext().setAccessibleDescription(bundle.getString("ACS_DescriptionA11yDesc"));  // NOI18N
        existingConnComboBox.getAccessibleContext().setAccessibleName(bundle.getString("ACS_ExistingConnectionA11yName"));  // NOI18N
        existingConnComboBox.getAccessibleContext().setAccessibleDescription(bundle.getString("ACS_ExistingConnectionA11yDesc"));  // NOI18N
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        descriptionTextArea = new javax.swing.JTextArea();
        existingConnComboBox = new javax.swing.JComboBox();

        setLayout(new java.awt.GridBagLayout());

        descriptionTextArea.setEditable(false);
        descriptionTextArea.setFont(javax.swing.UIManager.getFont("Label.font"));
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setText(bundle.getString("Description"));
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setDisabledTextColor(javax.swing.UIManager.getColor("Label.foreground"));
        descriptionTextArea.setEnabled(false);
        descriptionTextArea.setOpaque(false);
        descriptionTextArea.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        add(descriptionTextArea, gridBagConstraints);

        existingConnComboBox.setToolTipText(bundle.getString("ACS_ExistingConnectionComboBoxA11yDesc"));
        existingConnComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingConnComboBoxActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 5);
        add(existingConnComboBox, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void existingConnComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingConnComboBoxActionPerformed
        Object selectedItem = existingConnComboBox.getSelectedItem();
        if (selectedItem instanceof DatabaseConnection) {
            data.setDatabaseConnection((DatabaseConnection)selectedItem);
        }
        fireChange(this);
    }//GEN-LAST:event_existingConnComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JComboBox existingConnComboBox;
    // End of variables declaration//GEN-END:variables

    private final ResourceBundle bundle = NbBundle.getBundle("org.netbeans.modules.dbschema.jdbcimpl.resources.Bundle"); //NOI18N

    public boolean isValid() {
        return existingConnComboBox.getSelectedItem() instanceof DatabaseConnection;
    }

    public void intervalAdded(final javax.swing.event.ListDataEvent p1) {
        fireChange(this);
    }

    public void intervalRemoved(final javax.swing.event.ListDataEvent p1) {
        fireChange(this);
    }

    public void contentsChanged(final javax.swing.event.ListDataEvent p1) {
        fireChange(this);
    }

    public void initData() {
        data.setExistingConn(true);
        Object selectedItem = existingConnComboBox.getSelectedItem();
        if (selectedItem instanceof DatabaseConnection) {
            data.setDatabaseConnection((DatabaseConnection)selectedItem);
        }
    }

    public void fireChange (Object source) {
        ArrayList lst;

        synchronized (this) {
            lst = (ArrayList) this.list.clone();
        }

        ChangeEvent event = new ChangeEvent(source);
        for (int i=0; i< lst.size(); i++){
            ChangeListener listener = (ChangeListener) lst.get(i);
            listener.stateChanged(event);
        }
    }
}
