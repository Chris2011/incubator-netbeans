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

package org.netbeans.modules.web.project.ui.wizards;

import org.netbeans.api.java.platform.JavaPlatform;
import org.netbeans.api.java.platform.JavaPlatformManager;
import org.netbeans.api.java.platform.Specification;
import org.netbeans.modules.web.project.ui.FoldersListSettings;
import org.openide.modules.SpecificationVersion;
import org.openide.util.NbBundle;

/**
 * Displays a warning that the project's Java platform will be set to JDK 1.4 or
 * the source level will be set to 1.4. See issue #55797.
 *
 * @author Andrei Badea
 */
public final class J2eeVersionWarningPanel extends javax.swing.JPanel {
    
    /**
     * Display a warning that the target platform will be downgraded to JDK 1.4
     */
    public final static String WARN_SET_JDK_14 = "warnSetJdk14"; // NOI18N
    
    /**
     * Display a warning that the source level will be downgraded to 1.4
     */
    public final static String WARN_SET_SOURCE_LEVEL_14 = "warnSetSourceLevel14"; // NOI18N
    
    private String warningType;
    private String java14PlatformName;
    
    public J2eeVersionWarningPanel(String warningType) {
        initComponents();
        setWarningType(warningType);
    }
    
    public String getWarningType() {
        return warningType;
    }
    
    public void setWarningType(String warningType) {
        String warningText = null;
        String checkBoxText = null;
        boolean setJdk14 = false;
        
        this.warningType = warningType;
        
        if (WARN_SET_JDK_14.equals(warningType)) {
            setJdk14 = true;
            downgradeJdk14CheckBox.setSelected(FoldersListSettings.getDefault().isAgreedSetJdk14());
        } else if (WARN_SET_SOURCE_LEVEL_14.equals(warningType)) {
            setJdk14 = false;
            downgradeSourceLevel14CheckBox.setSelected(FoldersListSettings.getDefault().isAgreedSetSourceLevel14());
        } else {
            assert false : "Send WARN_SET_JDK_14 or WARN_SET_SOURCE_LEVEL_14"; // NOI18N
        }
        
        setJdk14Panel.setVisible(setJdk14);
        setSourceLevel14Panel.setVisible(!setJdk14);
        
    }
    
    public boolean getDowngradeAllowed() {
        if (WARN_SET_JDK_14.equals(warningType)) {
            return downgradeJdk14CheckBox.isSelected();
        } else if (WARN_SET_SOURCE_LEVEL_14.equals(warningType)) {
            return downgradeSourceLevel14CheckBox.isSelected();
        } else return false;
    }
    
    public String getJava14PlatformName() {
        if (java14PlatformName == null && WARN_SET_JDK_14.equals(warningType)) {
            JavaPlatform[] java14Platforms = getJava14Platforms();
            java14PlatformName = java14Platforms[0].getDisplayName();
        }
        return java14PlatformName;
    }
    
    public static String findWarningType() {
        JavaPlatform defaultPlatform = JavaPlatformManager.getDefault().getDefaultPlatform();
        SpecificationVersion version = defaultPlatform.getSpecification().getVersion();
        
        // no warning if 1.4 is the default
        if (new SpecificationVersion("1.4").equals(version)) // NOI18N
            return null;
        
        // now we know the default platform is 1.5 or higher
        JavaPlatform[] java14Platforms = getJava14Platforms();
        if (java14Platforms.length > 0) {
            // the user has JDK 1.4, so we warn we'll downgrade to 1.4
            return WARN_SET_JDK_14;
        } else {
            // no JDK 1.4, the best we can do is downgrade the source level to 1.4
            return WARN_SET_SOURCE_LEVEL_14;
        }
    }
    
    private static JavaPlatform[] getJava14Platforms() {
        return JavaPlatformManager.getDefault().getPlatforms(null, new Specification("J2SE", new SpecificationVersion("1.4"))); // NOI18N
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        setJdk14Panel = new javax.swing.JPanel();
        warningJdk14TextArea = new javax.swing.JTextArea();
        downgradeJdk14CheckBox = new javax.swing.JCheckBox();
        setSourceLevel14Panel = new javax.swing.JPanel();
        warningSourceLevel14TextArea1 = new javax.swing.JTextArea();
        downgradeSourceLevel14CheckBox = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridBagLayout());

        setPreferredSize(new java.awt.Dimension(100, 70));
        setJdk14Panel.setLayout(new java.awt.GridBagLayout());

        warningJdk14TextArea.setEditable(false);
        warningJdk14TextArea.setLineWrap(true);
        warningJdk14TextArea.setText(org.openide.util.NbBundle.getBundle(J2eeVersionWarningPanel.class).getString("MSG_RecommendationSetJdk14"));
        warningJdk14TextArea.setWrapStyleWord(true);
        warningJdk14TextArea.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        setJdk14Panel.add(warningJdk14TextArea, gridBagConstraints);

        downgradeJdk14CheckBox.setMnemonic(org.openide.util.NbBundle.getMessage(J2eeVersionWarningPanel.class, "MNE_AgreeSetJdk14").charAt(0));
        downgradeJdk14CheckBox.setText(org.openide.util.NbBundle.getMessage(J2eeVersionWarningPanel.class, "CTL_AgreeSetJdk14"));
        downgradeJdk14CheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        downgradeJdk14CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downgradeJdk14CheckBoxActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        setJdk14Panel.add(downgradeJdk14CheckBox, gridBagConstraints);
        downgradeJdk14CheckBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getBundle(J2eeVersionWarningPanel.class).getString("ACS_AgreeSetJdk14"));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(setJdk14Panel, gridBagConstraints);

        setSourceLevel14Panel.setLayout(new java.awt.GridBagLayout());

        warningSourceLevel14TextArea1.setEditable(false);
        warningSourceLevel14TextArea1.setLineWrap(true);
        warningSourceLevel14TextArea1.setText(org.openide.util.NbBundle.getBundle(J2eeVersionWarningPanel.class).getString("MSG_RecommendationSetSourceLevel14"));
        warningSourceLevel14TextArea1.setWrapStyleWord(true);
        warningSourceLevel14TextArea1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        setSourceLevel14Panel.add(warningSourceLevel14TextArea1, gridBagConstraints);

        downgradeSourceLevel14CheckBox.setMnemonic(org.openide.util.NbBundle.getMessage(J2eeVersionWarningPanel.class, "MNE_AgreeSetSourceLevel14").charAt(0));
        downgradeSourceLevel14CheckBox.setText(org.openide.util.NbBundle.getMessage(J2eeVersionWarningPanel.class, "CTL_AgreeSetSourceLevel14"));
        downgradeSourceLevel14CheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        downgradeSourceLevel14CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downgradeSourceLevel14CheckBoxActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        setSourceLevel14Panel.add(downgradeSourceLevel14CheckBox, gridBagConstraints);
        downgradeSourceLevel14CheckBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getBundle(J2eeVersionWarningPanel.class).getString("ACS_AgreeSetSourceLevel14"));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(setSourceLevel14Panel, gridBagConstraints);

    }
    // </editor-fold>//GEN-END:initComponents

    private void downgradeSourceLevel14CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downgradeSourceLevel14CheckBoxActionPerformed
        FoldersListSettings.getDefault().setAgreedSetSourceLevel14(downgradeSourceLevel14CheckBox.isSelected());
    }//GEN-LAST:event_downgradeSourceLevel14CheckBoxActionPerformed

    private void downgradeJdk14CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downgradeJdk14CheckBoxActionPerformed
        FoldersListSettings.getDefault().setAgreedSetJdk14(downgradeJdk14CheckBox.isSelected());
    }//GEN-LAST:event_downgradeJdk14CheckBoxActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox downgradeJdk14CheckBox;
    private javax.swing.JCheckBox downgradeSourceLevel14CheckBox;
    private javax.swing.JPanel setJdk14Panel;
    private javax.swing.JPanel setSourceLevel14Panel;
    private javax.swing.JTextArea warningJdk14TextArea;
    private javax.swing.JTextArea warningSourceLevel14TextArea1;
    // End of variables declaration//GEN-END:variables
    
}

