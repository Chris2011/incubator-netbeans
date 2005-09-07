/*
 *                 Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2005 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.project.uiapi;
import java.awt.CardLayout;
import javax.swing.event.ChangeListener;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.modules.project.uiapi.DefaultProjectOperationsImplementation.InvalidablePanel;
import org.openide.util.NbBundle;

/**
 *
 * @author Jan Lahoda
 */
final class DefaultProjectDeletePanel extends javax.swing.JPanel implements InvalidablePanel {
    
    private String projectDisplaName;
    private String projectFolder;
    private boolean enableCheckbox;
    private ProgressHandle handle;
    
    /**
     * Creates new form DefaultProjectDeletePanel
     */
    public DefaultProjectDeletePanel(ProgressHandle handle, String projectDisplaName, String projectFolder, boolean enableCheckbox) {
        this.projectDisplaName = projectDisplaName;
        this.projectFolder = projectFolder;
        this.enableCheckbox = enableCheckbox;
        this.handle = handle;
        initComponents();
        
        if (Boolean.getBoolean("org.netbeans.modules.project.uiapi.DefaultProjectOperations.showProgress")) {
            ((CardLayout) progress.getLayout()).show(progress, "progress");
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        warningText = new javax.swing.JTextArea();
        deleteSourcesCheckBox = new javax.swing.JCheckBox();
        progress = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        progressImpl = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(DefaultProjectDeletePanel.class, "ACSD_Delete_Panel", new Object[] {}));
        warningText.setEditable(false);
        warningText.setFont(javax.swing.UIManager.getFont("Label.font"));
        warningText.setText(org.openide.util.NbBundle.getMessage(DefaultProjectDeletePanel.class, "LBL_Pre_Delete_Warning", new Object[] {projectDisplaName}));
        warningText.setDisabledTextColor(javax.swing.UIManager.getColor("Label.foreground"));
        warningText.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 12);
        add(warningText, gridBagConstraints);
        warningText.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(DefaultProjectDeletePanel.class, "ASCN_Pre_Delete_Warning", new Object[] {}));
        warningText.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(DefaultProjectDeletePanel.class, "ACSD_Pre_Delete_Warning", new Object[] {projectDisplaName}));

        org.openide.awt.Mnemonics.setLocalizedText(deleteSourcesCheckBox, org.openide.util.NbBundle.getMessage(DefaultProjectDeletePanel.class, "LBL_Delete_Also_Sources", new Object[] {projectFolder}));
        deleteSourcesCheckBox.setEnabled(enableCheckbox);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 12);
        add(deleteSourcesCheckBox, gridBagConstraints);
        deleteSourcesCheckBox.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(DefaultProjectDeletePanel.class, "ACSN_Delete_Also_Sources", new Object[] {projectFolder}));
        deleteSourcesCheckBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(DefaultProjectDeletePanel.class, "ACSD_Delete_Also_Sources", new Object[] {}));

        progress.setLayout(new java.awt.CardLayout());

        progress.add(jPanel4, "not-progress");

        progressImpl.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(DefaultProjectDeletePanel.class, "LBL_Deleting_Project", new Object[] {}));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        progressImpl.add(jLabel5, gridBagConstraints);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel3.add(ProgressHandleFactory.createProgressComponent(handle));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        progressImpl.add(jPanel3, gridBagConstraints);

        progress.add(progressImpl, "progress");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 0);
        add(progress, gridBagConstraints);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox deleteSourcesCheckBox;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel progress;
    private javax.swing.JPanel progressImpl;
    private javax.swing.JTextArea warningText;
    // End of variables declaration//GEN-END:variables
    
    public boolean isDeleteSources() {
        return deleteSourcesCheckBox.isSelected();
    }

    void setDeleteSources(boolean value) {
        deleteSourcesCheckBox.setSelected(value);
    }
    
    private String getCheckboxText() {
        return NbBundle.getMessage(DefaultProjectDeletePanel.class, "LBL_Delete_Also_Sources", new Object[] {projectFolder});
    }

    public void addChangeListener(ChangeListener l) {
        //no changes.
    }

    public void removeChangeListener(ChangeListener l) {
        //no changes.
    }

    public void showProgress() {
        deleteSourcesCheckBox.setEnabled(false);
        
        ((CardLayout) progress.getLayout()).last(progress);
    }

    public boolean isPanelValid() {
        return true;
    }
    
}
