/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.netbeans.modules.xml.tax.beans.customizer;

import java.beans.PropertyChangeEvent;

import org.netbeans.tax.TreeDTD;
import org.netbeans.tax.TreeException;

import org.netbeans.modules.xml.tax.beans.editor.VersionEditor;
import org.netbeans.modules.xml.tax.beans.editor.EncodingEditor;
import org.netbeans.modules.xml.tax.util.TAXUtil;

/**
 *
 * @author  Libor Kramolis
 * @version 0.1
 */
public class TreeDTDCustomizer extends AbstractTreeCustomizer {

    /** Serial Version UID */
    private static final long serialVersionUID = -6359067211795222437L;
    
    
    //
    // init
    //
    
    /** */
    public TreeDTDCustomizer () {
        super ();
        
        initComponents ();
        versionLabel.setDisplayedMnemonic (Util.THIS.getChar ("MNE_dtd_version")); // NOI18N
        encodingLabel.setDisplayedMnemonic (Util.THIS.getChar ("MNE_dtd_encoding")); // NOI18N
        
        initAccessibility ();
    }
    
    
    //
    // itself
    //
    
    /**
     */
    protected final TreeDTD getDTD () {
        return (TreeDTD)getTreeObject ();
    }
    
    /**
     */
    protected final void safePropertyChange (PropertyChangeEvent pche) {
        super.safePropertyChange (pche);
        
        if (pche.getPropertyName ().equals (TreeDTD.PROP_VERSION)) {
            updateVersionComponent ();
        } else if (pche.getPropertyName ().equals (TreeDTD.PROP_ENCODING)) {
            updateEncodingComponent ();
        }
    }
    
    /**
     */
    protected final void updateDTDVersion () {
        if ( cbVersion.getSelectedItem () == null ) {
            return;
        }
        
        try {
            getDTD ().setVersion (text2null ((String) cbVersion.getSelectedItem ()));
        } catch (TreeException exc) {
            updateVersionComponent ();
            TAXUtil.notifyTreeException (exc);
        }
    }
    
    /**
     */
    protected final void updateVersionComponent () {
        cbVersion.setSelectedItem (null2text (getDTD ().getVersion ()));
    }
    
    /**
     */
    protected final void updateDTDEncoding () {
        if ( cbEncoding.getSelectedItem () == null ) {
            return;
        }
        
        try {
            getDTD ().setEncoding (text2null ((String) cbEncoding.getSelectedItem ()));
        } catch (TreeException exc) {
            updateEncodingComponent ();
            TAXUtil.notifyTreeException (exc);
        }
    }
    
    /**
     */
    protected final void updateEncodingComponent () {
        cbEncoding.setSelectedItem (null2text (getDTD ().getEncoding ()));
    }
    
    /**
     */
    protected void initComponentValues () {
        updateVersionComponent ();
        updateEncodingComponent ();
    }
    
    
    /**
     */
    protected void updateReadOnlyStatus (boolean editable) {
        cbVersion.setEnabled (editable);
        cbEncoding.setEnabled (editable);
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        versionLabel = new javax.swing.JLabel();
        cbVersion = new javax.swing.JComboBox();
        encodingLabel = new javax.swing.JLabel();
        cbEncoding = new javax.swing.JComboBox();
        fillPanel = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        versionLabel.setText(Util.THIS.getString ("PROP_dtd_version"));
        versionLabel.setLabelFor(cbVersion);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(versionLabel, gridBagConstraints);

        cbVersion.setModel(new javax.swing.DefaultComboBoxModel(VersionEditor.getItems()));
        cbVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbVersionActionPerformed(evt);
            }
        });

        cbVersion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbVersionFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(cbVersion, gridBagConstraints);

        encodingLabel.setText(Util.THIS.getString ("PROP_dtd_encoding"));
        encodingLabel.setLabelFor(cbEncoding);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(encodingLabel, gridBagConstraints);

        cbEncoding.setModel(new javax.swing.DefaultComboBoxModel(EncodingEditor.getItems()));
        cbEncoding.setEditable(true);
        cbEncoding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEncodingActionPerformed(evt);
            }
        });

        cbEncoding.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbEncodingFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(cbEncoding, gridBagConstraints);

        fillPanel.setPreferredSize(new java.awt.Dimension(0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(fillPanel, gridBagConstraints);

    }//GEN-END:initComponents
    
    private void cbEncodingFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbEncodingFocusLost
        // Add your handling code here:
        updateDTDEncoding ();
    }//GEN-LAST:event_cbEncodingFocusLost
    
    private void cbEncodingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEncodingActionPerformed
        // Add your handling code here:
        updateDTDEncoding ();
    }//GEN-LAST:event_cbEncodingActionPerformed
    
    private void cbVersionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbVersionFocusLost
        // Add your handling code here:
        updateDTDVersion ();
    }//GEN-LAST:event_cbVersionFocusLost
    
    private void cbVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbVersionActionPerformed
        // Add your handling code here:
        updateDTDVersion ();
    }//GEN-LAST:event_cbVersionActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbVersion;
    private javax.swing.JLabel versionLabel;
    private javax.swing.JComboBox cbEncoding;
    private javax.swing.JLabel encodingLabel;
    private javax.swing.JPanel fillPanel;
    // End of variables declaration//GEN-END:variables
    
    /** Initialize accesibility
     */
    public void initAccessibility (){
        
        cbVersion.getAccessibleContext ().setAccessibleDescription (Util.THIS.getString ("ACSD_cbVersion"));
        cbEncoding.getAccessibleContext ().setAccessibleDescription (Util.THIS.getString ("ACSD_cbEncoding"));
        
        this.getAccessibleContext ().setAccessibleDescription (Util.THIS.getString ("ACSD_TreeDTDCustomizer"));
    }
}
