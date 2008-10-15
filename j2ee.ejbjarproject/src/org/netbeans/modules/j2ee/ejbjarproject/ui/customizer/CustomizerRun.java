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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
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

package org.netbeans.modules.j2ee.ejbjarproject.ui.customizer;

import java.io.IOException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.netbeans.api.project.libraries.Library;
import org.netbeans.modules.j2ee.common.SharabilityUtility;
import org.netbeans.modules.java.api.common.project.ui.ClassPathUiSupport;
import org.netbeans.modules.j2ee.common.project.ui.J2eePlatformUiSupport;
import org.netbeans.modules.j2ee.common.project.ui.MessageUtils;
import org.netbeans.modules.j2ee.deployment.devmodules.api.Deployment;
import org.netbeans.modules.j2ee.deployment.devmodules.api.InstanceRemovedException;
import org.netbeans.modules.j2ee.deployment.devmodules.api.J2eeModule;
import org.netbeans.modules.j2ee.deployment.devmodules.api.ServerInstance;
import org.netbeans.modules.java.api.common.ant.UpdateHelper;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 *
 * @author Andrei Badea
 */
public class CustomizerRun extends javax.swing.JPanel implements HelpCtx.Provider {

    private Object initialJ2eeSpecVersion;

    private final EjbJarProjectProperties uiProperties;
    
    private final String oldServerInstanceId;

    /** Creates new form CustomizerRun */
    public CustomizerRun( EjbJarProjectProperties uiProperties ) {
        initComponents();

        this.uiProperties = uiProperties;
        
        this.oldServerInstanceId = uiProperties.J2EE_SERVER_INSTANCE_MODEL.getSelectedItem() != null
                ? J2eePlatformUiSupport.getServerInstanceID(uiProperties.J2EE_SERVER_INSTANCE_MODEL.getSelectedItem())
                : null;
        
        uiProperties.JAVAC_CLASSPATH_MODEL.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                setMessages();
            }
        });
        
        jComboBoxJ2eePlatform.setModel (uiProperties.J2EE_SERVER_INSTANCE_MODEL );
        jComboBoxJ2eeSpecVersion.setModel (uiProperties.J2EE_PLATFORM_MODEL );
        jCheckBoxDeployOnSave.setModel(uiProperties.DEPLOY_ON_SAVE_MODEL);
        vmOptions.setDocument(uiProperties.RUNMAIN_JVM_MODEL);
        
        initialJ2eeSpecVersion = uiProperties.J2EE_PLATFORM_MODEL.getSelectedItem();

        setDeployOnSaveState();
    }
    
    public HelpCtx getHelpCtx() {
        return new HelpCtx(CustomizerRun.class);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabelJ2eePlatform = new javax.swing.JLabel();
        jComboBoxJ2eePlatform = new javax.swing.JComboBox();
        jLabelJ2eeVersion = new javax.swing.JLabel();
        jComboBoxJ2eeSpecVersion = new javax.swing.JComboBox();
        errorLabel = new javax.swing.JLabel();
        jCheckBoxDeployOnSave = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        vmOptions = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jLabelJ2eePlatform.setLabelFor(jComboBoxJ2eePlatform);
        org.openide.awt.Mnemonics.setLocalizedText(jLabelJ2eePlatform, org.openide.util.NbBundle.getBundle(CustomizerRun.class).getString("LBL_CustomizeRun_Run_Server_JLabel")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 11);
        add(jLabelJ2eePlatform, gridBagConstraints);

        jComboBoxJ2eePlatform.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxJ2eePlatformItemStateChanged(evt);
            }
        });
        jComboBoxJ2eePlatform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxJ2eePlatformActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(jComboBoxJ2eePlatform, gridBagConstraints);
        jComboBoxJ2eePlatform.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getBundle(CustomizerRun.class).getString("AD_jComboBoxServer")); // NOI18N

        jLabelJ2eeVersion.setLabelFor(jComboBoxJ2eeSpecVersion);
        org.openide.awt.Mnemonics.setLocalizedText(jLabelJ2eeVersion, org.openide.util.NbBundle.getBundle(CustomizerRun.class).getString("LBL_CustomizeRun_Run_J2EEVersion_JLabel")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 0, 11);
        add(jLabelJ2eeVersion, gridBagConstraints);

        jComboBoxJ2eeSpecVersion.setEnabled(false);
        jComboBoxJ2eeSpecVersion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxJ2eeSpecVersionItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 0, 0);
        add(jComboBoxJ2eeSpecVersion, gridBagConstraints);

        errorLabel.setForeground(new java.awt.Color(89, 71, 191));
        org.openide.awt.Mnemonics.setLocalizedText(errorLabel, " ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 0, 0);
        add(errorLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxDeployOnSave, org.openide.util.NbBundle.getMessage(CustomizerRun.class, "LBL_CustomizeRun_DeployOnSave_JCheckBox")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 0, 0);
        add(jCheckBoxDeployOnSave, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 11, 0);
        add(jSeparator1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CustomizerRun.class, "Label_JVM_Argument")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 5, 0);
        add(vmOptions, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(CustomizerRun.class, "Label_VM_Hint")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
        add(jLabel2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxJ2eeSpecVersionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxJ2eeSpecVersionItemStateChanged
        setMessages();//GEN-LAST:event_jComboBoxJ2eeSpecVersionItemStateChanged
    }                                                         

private void jComboBoxJ2eePlatformItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxJ2eePlatformItemStateChanged
        // TODO add your handling code here://GEN-LAST:event_jComboBoxJ2eePlatformItemStateChanged
        setMessages();
}                                                      

private void jComboBoxJ2eePlatformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxJ2eePlatformActionPerformed
    setDeployOnSaveState();
}//GEN-LAST:event_jComboBoxJ2eePlatformActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel errorLabel;
    private javax.swing.JCheckBox jCheckBoxDeployOnSave;
    private javax.swing.JComboBox jComboBoxJ2eePlatform;
    private javax.swing.JComboBox jComboBoxJ2eeSpecVersion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelJ2eePlatform;
    private javax.swing.JLabel jLabelJ2eeVersion;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField vmOptions;
    // End of variables declaration//GEN-END:variables

    private void setDeployOnSaveState() {
        if (uiProperties.J2EE_SERVER_INSTANCE_MODEL.getSelectedItem() != null) {
            String serverInstanceID = J2eePlatformUiSupport.getServerInstanceID(
                    uiProperties.J2EE_SERVER_INSTANCE_MODEL.getSelectedItem());

            J2eeModule module = uiProperties.getProject().getEjbModule().getJ2eeModule();
            ServerInstance instance = Deployment.getDefault().getServerInstance(serverInstanceID);

            try {
                jCheckBoxDeployOnSave.setEnabled(instance.isDeployOnSaveSupported(module));
            } catch (InstanceRemovedException ex) {
                jCheckBoxDeployOnSave.setEnabled(false);
            }
        } else {
            jCheckBoxDeployOnSave.setEnabled(false);
        }
    }

    private void setMessages() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>"); // NOI18N

        boolean display = false;
        if (uiProperties.J2EE_SERVER_INSTANCE_MODEL.getSelectedItem() != null) {
            if (isServerLibraryMessageNeeded(J2eePlatformUiSupport.getServerInstanceID(
                    uiProperties.J2EE_SERVER_INSTANCE_MODEL.getSelectedItem()), uiProperties)) {
                sb.append(NbBundle.getMessage(CustomizerRun.class, "MSG_CREATING_LIBRARY"));
                display = true;
            }
        }

        boolean changed = !jComboBoxJ2eeSpecVersion.getSelectedItem().equals(initialJ2eeSpecVersion);
        if (changed) {
            if (display) {
                sb.append("<p>"); // NOI18N
            }
            sb.append(NbBundle.getMessage(CustomizerRun.class, "LBL_CustomizeRun_Run_WardDdChange_JLabel"));
            display = true;
        }

        if (display) {
            sb.append("</html>"); // NOI18N
            MessageUtils.setMessage(errorLabel, MessageUtils.MessageType.WARNING, sb.toString());
        } else {
            MessageUtils.clear(errorLabel);
        }
    }

    private boolean isServerLibraryMessageNeeded(String serverInstanceId, EjbJarProjectProperties uiProperties) {
        UpdateHelper helper = uiProperties.getProject().getUpdateHelper();

        try {
            if (SharabilityUtility.isLibrarySwitchIntended(serverInstanceId,
                    oldServerInstanceId, ClassPathUiSupport.getList(uiProperties.JAVAC_CLASSPATH_MODEL.getDefaultListModel()), helper)) {

                    AntProjectHelper antHelper = helper.getAntProjectHelper();
                    Library[] libs = SharabilityUtility.getLibraries(antHelper.resolveFile(antHelper.getLibrariesLocation()), serverInstanceId);
                    return libs.length <= 0;
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return false;
    }    
}
