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
package org.netbeans.modules.j2ee.websphere6.dd.loaders.ui;

import  org.netbeans.modules.j2ee.websphere6.dd.beans.DDXmiConstants;
/**
 *
 * @author  dlm198383
 */
public class LocalTransactionPanel extends javax.swing.JPanel {

    /**
     * Creates new form LocalTransactionPanel
     */
    public LocalTransactionPanel() {
        initComponents();
        boundaryComboBox.setModel(new javax.swing.DefaultComboBoxModel(DDXmiConstants.LOCAL_TRANSACTION_BOUNDARY_TYPES));
        resolverComboBox.setModel(new javax.swing.DefaultComboBoxModel(DDXmiConstants.LOCAL_TRANSACTION_RESOLVER_TYPES));
        unresolvedActionComboBox.setModel(new javax.swing.DefaultComboBoxModel(DDXmiConstants.LOCAL_TRANSACTION_UNRESOLVED_ACTION_TYPES));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        localTransactionCheckBox = new javax.swing.JCheckBox();
        nameLabel = new javax.swing.JLabel();
        transactionNameField = new javax.swing.JTextField();
        unresolvedLabel = new javax.swing.JLabel();
        unresolvedActionComboBox = new javax.swing.JComboBox();
        resolverCheckBox = new javax.swing.JCheckBox();
        resolverComboBox = new javax.swing.JComboBox();
        boundaryCheckBox = new javax.swing.JCheckBox();
        boundaryComboBox = new javax.swing.JComboBox();

        setLayout(new java.awt.GridBagLayout());

        setPreferredSize(new java.awt.Dimension(150, 22));
        localTransactionCheckBox.setText(java.util.ResourceBundle.getBundle("org/netbeans/modules/j2ee/websphere6/dd/loaders/ui/Bundle").getString("LBL_LocalTransaction"));
        localTransactionCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        localTransactionCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        localTransactionCheckBox.setMinimumSize(new java.awt.Dimension(40, 15));
        localTransactionCheckBox.setPreferredSize(new java.awt.Dimension(150, 15));
        localTransactionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localTransactionCheckBoxActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 26);
        add(localTransactionCheckBox, gridBagConstraints);

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nameLabel.setText(java.util.ResourceBundle.getBundle("org/netbeans/modules/j2ee/websphere6/dd/loaders/ui/Bundle").getString("LBL_Name"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 14, 0, 6);
        add(nameLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 10);
        add(transactionNameField, gridBagConstraints);

        unresolvedLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        unresolvedLabel.setText(java.util.ResourceBundle.getBundle("org/netbeans/modules/j2ee/websphere6/dd/loaders/ui/Bundle").getString("LBL_UnresolvedAction"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 14, 0, 6);
        add(unresolvedLabel, gridBagConstraints);

        unresolvedActionComboBox.setPreferredSize(new java.awt.Dimension(150, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 10);
        add(unresolvedActionComboBox, gridBagConstraints);

        resolverCheckBox.setText(java.util.ResourceBundle.getBundle("org/netbeans/modules/j2ee/websphere6/dd/loaders/ui/Bundle").getString("LBL_Resolver"));
        resolverCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        resolverCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        resolverCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 14, 0, 6);
        add(resolverCheckBox, gridBagConstraints);

        resolverComboBox.setPreferredSize(new java.awt.Dimension(150, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 10);
        add(resolverComboBox, gridBagConstraints);

        boundaryCheckBox.setText(java.util.ResourceBundle.getBundle("org/netbeans/modules/j2ee/websphere6/dd/loaders/ui/Bundle").getString("LBL_Boundary"));
        boundaryCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        boundaryCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 14, 0, 6);
        add(boundaryCheckBox, gridBagConstraints);

        boundaryComboBox.setPreferredSize(new java.awt.Dimension(150, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 10);
        add(boundaryComboBox, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void localTransactionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localTransactionCheckBoxActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_localTransactionCheckBoxActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox boundaryCheckBox;
    private javax.swing.JComboBox boundaryComboBox;
    private javax.swing.JCheckBox localTransactionCheckBox;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JCheckBox resolverCheckBox;
    private javax.swing.JComboBox resolverComboBox;
    private javax.swing.JTextField transactionNameField;
    private javax.swing.JComboBox unresolvedActionComboBox;
    private javax.swing.JLabel unresolvedLabel;
    // End of variables declaration//GEN-END:variables
    
    public void setEnabledComponents() {
        boolean state=localTransactionCheckBox.isSelected();
        nameLabel.setEnabled(state);
        unresolvedLabel.setEnabled(state);
        transactionNameField.setEnabled(state);
        unresolvedActionComboBox.setEnabled(state);
        resolverCheckBox.setEnabled(state);
        boundaryCheckBox.setEnabled(state);
        boundaryComboBox.setEnabled(state?boundaryCheckBox.isSelected():false);
        resolverComboBox.setEnabled(state?resolverCheckBox.isSelected():false);
        if(state && (transactionNameField.getText().trim().equals(""))){
            long timeStamp=java.lang.System.currentTimeMillis();
            transactionNameField.setText(DDXmiConstants.LOCAL_TRANSACTION+"_"+timeStamp);//NOI18N
            
        }
    }
    public javax.swing.JCheckBox getBoundaryCheckBox(){
        return boundaryCheckBox;
    }
    public javax.swing.JCheckBox getResolverCheckBox(){
        return resolverCheckBox;
    }
    public javax.swing.JCheckBox getLocalTransactionCheckBox(){
        return localTransactionCheckBox;
    }
    public javax.swing.JComboBox getBoundaryComboBox() {
        return boundaryComboBox;
    }
    public javax.swing.JComboBox getResolverComboBox() {
        return resolverComboBox;
    }
    public javax.swing.JComboBox getUnresolvedActionComboBox() {
        return unresolvedActionComboBox;
    }
    public javax.swing.JTextField getTransactionNameField() {
        return transactionNameField; 
    }
    public javax.swing.JLabel getNameLabel() {
        return nameLabel; 
    }
    public javax.swing.JLabel getUnresolvedActionLable() {
        return unresolvedLabel; 
    }
    public void setComponentsBackground(java.awt.Color c) {
        nameLabel.setBackground(c);
        unresolvedLabel.setBackground(c);
        resolverCheckBox.setBackground(c);
        boundaryCheckBox.setBackground(c);
        localTransactionCheckBox.setBackground(c);        
        setBackground(c);
    }
}

