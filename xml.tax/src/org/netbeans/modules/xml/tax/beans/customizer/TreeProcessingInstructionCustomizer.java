/*
 *                 Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2001 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.netbeans.modules.xml.tax.beans.customizer;

import java.beans.PropertyChangeEvent;

import org.netbeans.tax.TreeProcessingInstruction;
import org.netbeans.tax.TreeException;

/**
 *
 * @author  Libor Kramolis
 * @version 0.1
 */
public class TreeProcessingInstructionCustomizer extends AbstractTreeCustomizer {
    
    /** Serial Version UID */
    private static final long serialVersionUID = 1151841201717291218L;
    
    
    //
    // init
    //
    
    /** */
    public TreeProcessingInstructionCustomizer () {
        super ();
        
        initComponents ();
        targetLabel.setDisplayedMnemonic (Util.getChar ("MNE_xmlTarget")); // NOI18N
        
        initAccessibility ();
    }
    
    
    //
    // itself
    //
    
    /**
     */
    protected final TreeProcessingInstruction getProcessingInstruction () {
        return (TreeProcessingInstruction)getTreeObject ();
    }
    
    /**
     */
    protected final void safePropertyChange (PropertyChangeEvent pche) {
        super.safePropertyChange (pche);
        
        if (pche.getPropertyName ().equals (TreeProcessingInstruction.PROP_TARGET)) {
            updateTargetComponent ();
        } else if (pche.getPropertyName ().equals (TreeProcessingInstruction.PROP_DATA)) {
            updateDataComponent ();
        }
    }
    
    /**
     */
    protected final void updateProcessingInstructionTarget () {
        try {
            getProcessingInstruction ().setTarget (targetField.getText ());
        } catch (TreeException exc) {
            updateTargetComponent ();
            Util.notifyTreeException (exc);
        }
    }
    
    /**
     */
    protected final void updateTargetComponent () {
        targetField.setText (getProcessingInstruction ().getTarget ());
    }
    
    /**
     */
    protected final void updateProcessingInstructionData () {
        try {
            getProcessingInstruction ().setData (dataPane.getText ());
        } catch (TreeException exc) {
            updateDataComponent ();
            Util.notifyTreeException (exc);
        }
    }
    
    /**
     */
    protected final void updateDataComponent () {
        dataPane.setText (getProcessingInstruction ().getData ());
    }
    
    /**
     */
    protected final void initComponentValues () {
        updateTargetComponent ();
        updateDataComponent ();
    }
    
    
    /**
     */
    protected void updateReadOnlyStatus (boolean editable) {
        targetField.setEditable (editable);
        dataPane.setEditable (editable);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        targetLabel = new javax.swing.JLabel();
        targetField = new javax.swing.JTextField();
        dataPanel = new javax.swing.JPanel();
        dataScroll = new javax.swing.JScrollPane();
        dataPane = new javax.swing.JEditorPane();

        setLayout(new java.awt.GridBagLayout());

        setPreferredSize(new java.awt.Dimension(380, 250));
        setMinimumSize(new java.awt.Dimension(380, 250));
        targetLabel.setText(Util.getString ("PROP_xmlTarget"));
        targetLabel.setLabelFor(targetField);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        add(targetLabel, gridBagConstraints);

        targetField.setColumns(23);
        targetField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetFieldActionPerformed(evt);
            }
        });

        targetField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                targetFieldFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        add(targetField, gridBagConstraints);

        dataPanel.setLayout(new java.awt.BorderLayout());

        dataPane.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dataPaneFocusLost(evt);
            }
        });

        dataPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dataPaneKeyPressed(evt);
            }
        });

        dataScroll.setViewportView(dataPane);

        dataPanel.add(dataScroll, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        add(dataPanel, gridBagConstraints);

    }//GEN-END:initComponents
    
    private void dataPaneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataPaneFocusLost
        // Add your handling code here:
        updateProcessingInstructionData ();
    }//GEN-LAST:event_dataPaneFocusLost
    
    /**
     */
    private void targetFieldFocusLost (java.awt.event.FocusEvent evt) {//GEN-FIRST:event_targetFieldFocusLost
        // Add your handling code here:
        updateProcessingInstructionTarget ();
    }//GEN-LAST:event_targetFieldFocusLost
    
    
    /**
     */
    private void targetFieldActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetFieldActionPerformed
        // Add your handling code here:
        updateProcessingInstructionTarget ();
    }//GEN-LAST:event_targetFieldActionPerformed
    
    
    private void dataPaneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dataPaneKeyPressed
        // Add your handling code here:
        if ( applyKeyPressed (evt) ) {
            updateProcessingInstructionData ();
        }
    }//GEN-LAST:event_dataPaneKeyPressed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel targetLabel;
    private javax.swing.JPanel dataPanel;
    private javax.swing.JTextField targetField;
    private javax.swing.JEditorPane dataPane;
    private javax.swing.JScrollPane dataScroll;
    // End of variables declaration//GEN-END:variables
    
    /** Initialize accesibility
     */
    public void initAccessibility (){
        
        this.getAccessibleContext ().setAccessibleDescription (Util.getString ("ACSD_TreeProcessingInstructionCustomizer"));
        targetField.getAccessibleContext ().setAccessibleDescription (Util.getString ("ACSD_targetField"));
        dataPane.getAccessibleContext ().setAccessibleDescription (Util.getString ("ACSD_dataPanel1"));
        dataPane.getAccessibleContext ().setAccessibleName (Util.getString ("ACSN_dataPanel1"));
    }
}
