/*
 * RenameClassForm.java
 *
 * Created on January 18, 2005, 9:18 PM
 */

package org.netbeans.modules.j2ee.ddloaders.multiview.ui;

import javax.swing.*;

/**
 * @author pf154013
 */
public class RenameClassForm extends javax.swing.JPanel {

    /**
     * Creates new form RenameClassForm
     */
    public RenameClassForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        changeNameTextField = new javax.swing.JTextField();
        renameCommentsCheckBox = new javax.swing.JCheckBox();
        spacerLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(org.openide.util.NbBundle.getMessage(RenameClassForm.class, "LBL_NewName"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(changeNameTextField, gridBagConstraints);

        renameCommentsCheckBox.setText(org.openide.util.NbBundle.getMessage(RenameClassForm.class, "LBL_RenameComments"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(renameCommentsCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 1.0;
        add(spacerLabel, gridBagConstraints);

    }//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField changeNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox renameCommentsCheckBox;
    private javax.swing.JLabel spacerLabel;
    // End of variables declaration//GEN-END:variables

    public JTextField getChangeNameTextField() {
        return changeNameTextField;
    }

    public JCheckBox getRenameCommentsCheckBox() {
        return renameCommentsCheckBox;
    }
}
