/*
 *                 Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2004 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.web.project.ui.customizer;

import javax.swing.JPanel;

import org.openide.util.NbBundle;

public class CustomizerRun extends JPanel implements WebCustomizer.Panel {
    
    // Helper for storing properties
    private WebProjectProperties webProperties;
    
    /** Creates new form CustomizerCompile */
    public CustomizerRun() {
        initComponents();                
    }
    
    public void initValues(WebProjectProperties webProperties) {
        this.webProperties = webProperties;
        
        VisualPropertySupport vps = new VisualPropertySupport(webProperties);
        
        vps.register(jTextFieldContextPath, WebProjectProperties.CONTEXT_PATH);
        vps.register(jCheckBoxDisplayBrowser, WebProjectProperties.DISPLAY_BROWSER);
        vps.register(jTextFieldLaunchURL, WebProjectProperties.LAUNCH_URL);
        
        jTextFieldLaunchURL.setEnabled(jCheckBoxDisplayBrowser.isSelected());
    } 
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jLabelContextPath = new javax.swing.JLabel();
        jTextFieldContextPath = new javax.swing.JTextField();
        jCheckBoxDisplayBrowser = new javax.swing.JCheckBox();
        jLabelLaunchURL = new javax.swing.JLabel();
        jTextFieldLaunchURL = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        setBorder(new javax.swing.border.EtchedBorder());
        jLabelContextPath.setLabelFor(jTextFieldContextPath);
        jLabelContextPath.setText(NbBundle.getMessage(CustomizerRun.class, "LBL_CustomizeRun_ContextPath_JLabel"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 0);
        add(jLabelContextPath, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 11, 11, 11);
        add(jTextFieldContextPath, gridBagConstraints);

        jCheckBoxDisplayBrowser.setSelected(true);
        jCheckBoxDisplayBrowser.setText(NbBundle.getMessage(CustomizerRun.class, "LBL_CustomizeRun_DisplayBrowser_JCheckBox"));
        jCheckBoxDisplayBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxDisplayBrowserActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 11, 11);
        add(jCheckBoxDisplayBrowser, gridBagConstraints);

        jLabelLaunchURL.setLabelFor(jTextFieldLaunchURL);
        jLabelLaunchURL.setText(NbBundle.getMessage(CustomizerRun.class, "LBL_CustomizeRun_LaunchURL_JLabel"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        add(jLabelLaunchURL, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 11);
        add(jTextFieldLaunchURL, gridBagConstraints);

    }//GEN-END:initComponents

    private void jCheckBoxDisplayBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxDisplayBrowserActionPerformed
        jTextFieldLaunchURL.setEnabled(jCheckBoxDisplayBrowser.isSelected());
    }//GEN-LAST:event_jCheckBoxDisplayBrowserActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxDisplayBrowser;
    private javax.swing.JLabel jLabelContextPath;
    private javax.swing.JLabel jLabelLaunchURL;
    private javax.swing.JTextField jTextFieldContextPath;
    private javax.swing.JTextField jTextFieldLaunchURL;
    // End of variables declaration//GEN-END:variables
    
}
