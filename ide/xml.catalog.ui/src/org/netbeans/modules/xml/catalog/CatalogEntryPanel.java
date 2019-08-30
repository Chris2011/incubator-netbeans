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

package org.netbeans.modules.xml.catalog;

import org.openide.util.NbBundle;
import org.openide.DialogDescriptor;


/** Panel for Add Catalog Entry action
 *
 * Created on May 31, 2005
 * @author  mkuchtiak
 */
public class CatalogEntryPanel extends javax.swing.JPanel {

    private org.openide.DialogDescriptor enclosingDesc;
    /** Creates new form CatalogEntryPanel */
    public CatalogEntryPanel() {
        initComponents();
        DocListener docListener = new DocListener(this);
        publicTF.getDocument().addDocumentListener(docListener);
        systemTF.getDocument().addDocumentListener(docListener);
        uriTF.getDocument().addDocumentListener(docListener);
        getAccessibleContext().setAccessibleName(titleLabel.getText());
        getAccessibleContext().setAccessibleDescription(descLabel.getText());
    }
    
    void setEnclosingDesc(DialogDescriptor enclosingDesc) {
        this.enclosingDesc=enclosingDesc;
    }
    
    public String getPublicId() {
        return publicTF.getText();
    }
    
    public boolean isPublic() {
        return publicRB.isSelected();
    }
    
    public String getSystemId() {
        return systemTF.getText();
    }
    
    public String getUri() {
        return uriTF.getText();
    }
    
    
    private void checkValues() {
        if (enclosingDesc==null) return;
        if (getUri().length()==0) enclosingDesc.setValid(false);
        else if (isPublic() && getPublicId().length()==0) enclosingDesc.setValid(false);
        else if (!isPublic() && getSystemId().length()==0) enclosingDesc.setValid(false);
        else enclosingDesc.setValid(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        publicRB = new javax.swing.JRadioButton();
        systemRB = new javax.swing.JRadioButton();
        publicTF = new javax.swing.JTextField();
        systemTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        uriLabel = new javax.swing.JLabel();
        uriTF = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        descLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(publicRB);
        publicRB.setMnemonic(org.openide.util.NbBundle.getMessage(CatalogEntryPanel.class, "RADIO_publicId_mnem").charAt(0));
        publicRB.setSelected(true);
        publicRB.setText(org.openide.util.NbBundle.getMessage(CatalogEntryPanel.class, "RADIO_publicId")); // NOI18N
        publicRB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                publicRBItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(publicRB, gridBagConstraints);

        buttonGroup1.add(systemRB);
        systemRB.setMnemonic(org.openide.util.NbBundle.getMessage(CatalogEntryPanel.class, "RADIO_systemId_mnem").charAt(0));
        systemRB.setText(org.openide.util.NbBundle.getMessage(CatalogEntryPanel.class, "RADIO_systemId")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        add(systemRB, gridBagConstraints);

        publicTF.setColumns(40);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(publicTF, gridBagConstraints);

        systemTF.setColumns(25);
        systemTF.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        add(systemTF, gridBagConstraints);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(CatalogEntryPanel.class, "HINT_publicId")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        add(jLabel1, gridBagConstraints);

        jLabel2.setText(org.openide.util.NbBundle.getMessage(CatalogEntryPanel.class, "HINT_systemId")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        add(jLabel2, gridBagConstraints);

        uriLabel.setDisplayedMnemonic(org.openide.util.NbBundle.getMessage(CatalogEntryPanel.class, "LBL_uri_mnem").charAt(0));
        uriLabel.setLabelFor(uriTF);
        uriLabel.setText(org.openide.util.NbBundle.getMessage(CatalogEntryPanel.class, "LBL_uri")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 0);
        add(uriLabel, gridBagConstraints);

        uriTF.setColumns(35);
        uriTF.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 0);
        add(uriTF, gridBagConstraints);

        browseButton.setMnemonic(org.openide.util.NbBundle.getMessage(CatalogEntryPanel.class, "LBL_browse_mnem").charAt(0));
        browseButton.setText(org.openide.util.NbBundle.getMessage(CatalogEntryPanel.class, "LBL_browse")); // NOI18N
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        add(browseButton, gridBagConstraints);

        titleLabel.setText(org.openide.util.NbBundle.getMessage(CatalogEntryPanel.class, "HINT_panel")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 10, 5);
        add(titleLabel, gridBagConstraints);

        descLabel.setText(org.openide.util.NbBundle.getMessage(CatalogEntryPanel.class, "LBL_catalogEntryDesc")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        add(descLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void publicRBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_publicRBItemStateChanged
// TODO add your handling code here:
        if (publicRB.isSelected()) {
            publicTF.setEditable(true);
            systemTF.setEditable(false);
        } else {
            publicTF.setEditable(false);
            systemTF.setEditable(true);
        }
        checkValues();
    }//GEN-LAST:event_publicRBItemStateChanged

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
// TODO add your handling code here:
        String dialogTitle = NbBundle.getMessage(CatalogEntryPanel.class,"TITLE_SelectDTDorSchema");
        String maskTitle = NbBundle.getMessage(CatalogEntryPanel.class,"TXT_DTDorSchema");
        java.io.File f = org.netbeans.modules.xml.catalog.lib.Util.selectFile("dtd xsd DTD XSD", dialogTitle, maskTitle); // NOI18N
        if (f == null) return;
        try {
            String location = f.toURI().toURL().toExternalForm();
            uriTF.setText(location);
        } catch (java.net.MalformedURLException ex) {
            // ignore
        }
    }//GEN-LAST:event_browseButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel descLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton publicRB;
    private javax.swing.JTextField publicTF;
    private javax.swing.JRadioButton systemRB;
    private javax.swing.JTextField systemTF;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel uriLabel;
    private javax.swing.JTextField uriTF;
    // End of variables declaration//GEN-END:variables
    
    /** Useful DocumentListener class that can be added to the panel's text compoents */
    private class DocListener implements javax.swing.event.DocumentListener {
        CatalogEntryPanel panel;
        
        public DocListener(CatalogEntryPanel panel) {
            this.panel=panel;
        }
        /**
         * Method from DocumentListener
         */
        public void changedUpdate(javax.swing.event.DocumentEvent evt) {
            panel.checkValues();
        }

        /**
         * Method from DocumentListener
         */
        public void insertUpdate(javax.swing.event.DocumentEvent evt) {
            panel.checkValues();
        }

        /**
         * Method from DocumentListener
         */
        public void removeUpdate(javax.swing.event.DocumentEvent evt) {
            panel.checkValues();
        }
    }
}
