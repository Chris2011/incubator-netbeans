/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
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
package org.netbeans.modules.subversion.ui.update;

import org.tigris.subversion.svnclientadapter.SVNRevision;

/**
 *
 * @author  Tomas Stupka
 */
public class RevertModificationsPanel extends javax.swing.JPanel {

    /** Creates new form ReverModificationsPanel */
    public RevertModificationsPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        buttonGroup.add(localChangesRadioButton);
        localChangesRadioButton.setSelected(true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/netbeans/modules/subversion/ui/update/Bundle"); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(localChangesRadioButton, bundle.getString("CTL_RevertPanel_RevertLocal")); // NOI18N

        buttonGroup.add(moreCommitsRadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(moreCommitsRadioButton, bundle.getString("CTL_RevertPanel_RevertMulti")); // NOI18N

        jLabel2.setLabelFor(startRevisionTextField);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, bundle.getString("CTL_RevertPanel_StartingRevision")); // NOI18N

        startRevisionTextField.setText(SVNRevision.HEAD.toString());
        startRevisionTextField.setToolTipText(org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "CTL_RevertPanel_EmptyHint")); // NOI18N
        startRevisionTextField.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(startSearchButton, bundle.getString("CTL_RevertPanel_Search2")); // NOI18N
        startSearchButton.setEnabled(false);

        endRevisionTextField.setText(SVNRevision.HEAD.toString());
        endRevisionTextField.setToolTipText(org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "CTL_RevertPanel_EmptyHint")); // NOI18N
        endRevisionTextField.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(endSearchButton, bundle.getString("CTL_RevertPanel_Search3")); // NOI18N
        endSearchButton.setEnabled(false);

        jLabel3.setLabelFor(endRevisionTextField);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, bundle.getString("CTL_RevertPanel_EndingRevision")); // NOI18N

        buttonGroup.add(oneCommitRadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(oneCommitRadioButton, bundle.getString("CTL_RevertPanel_RevertSingle")); // NOI18N

        jLabel4.setLabelFor(oneRevisionTextField);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, bundle.getString("CTL_RevertPanel_SingleRevision")); // NOI18N

        oneRevisionTextField.setText(SVNRevision.HEAD.toString());
        oneRevisionTextField.setToolTipText(org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "CTL_RevertPanel_EmptyHint")); // NOI18N
        oneRevisionTextField.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(oneRevisionSearchButton, bundle.getString("CTL_RevertPanel_Search1")); // NOI18N
        oneRevisionSearchButton.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(revertNewFilesCheckBox, org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "CTL_RevertPanel_RevertNewFiles")); // NOI18N

        cbRecursiveRevert.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(cbRecursiveRevert, org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "CTL_RevertPanel_Recursive.text")); // NOI18N
        cbRecursiveRevert.setToolTipText(org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "CTL_RevertPanel_Recursive.TTtext")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oneRevisionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oneRevisionSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(oneCommitRadioButton)
                    .addComponent(localChangesRadioButton)
                    .addComponent(moreCommitsRadioButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(revertNewFilesCheckBox)
                            .addComponent(cbRecursiveRevert)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endRevisionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(startRevisionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endSearchButton)
                            .addComponent(startSearchButton))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(localChangesRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbRecursiveRevert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(revertNewFilesCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(oneCommitRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(oneRevisionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oneRevisionSearchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(moreCommitsRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(startRevisionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startSearchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(endRevisionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endSearchButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        localChangesRadioButton.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_RevertPanel_RevertLocal")); // NOI18N
        moreCommitsRadioButton.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_RevertPanel_RevertMulti")); // NOI18N
        jLabel2.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_RevertPanel_StartingRevision")); // NOI18N
        startSearchButton.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_RevertPanel_Search2")); // NOI18N
        endSearchButton.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_RevertPanel_Search3")); // NOI18N
        jLabel3.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_RevertPanel_EndingRevision")); // NOI18N
        oneCommitRadioButton.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_RevertPanel_RevertSingle")); // NOI18N
        jLabel4.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_RevertPanel_SingleRevision")); // NOI18N
        oneRevisionSearchButton.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_RevertPanel_Search1")); // NOI18N
        revertNewFilesCheckBox.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "ASCN_RevertPanel_RevertNewFiles")); // NOI18N
        revertNewFilesCheckBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "ASCD_RevertPanel_RevertNewFiles")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    final javax.swing.ButtonGroup buttonGroup = new javax.swing.ButtonGroup();
    final javax.swing.JCheckBox cbRecursiveRevert = new javax.swing.JCheckBox();
    final javax.swing.JTextField endRevisionTextField = new javax.swing.JTextField();
    final javax.swing.JButton endSearchButton = new javax.swing.JButton();
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    final javax.swing.JRadioButton localChangesRadioButton = new javax.swing.JRadioButton();
    final javax.swing.JRadioButton moreCommitsRadioButton = new javax.swing.JRadioButton();
    final javax.swing.JRadioButton oneCommitRadioButton = new javax.swing.JRadioButton();
    final javax.swing.JButton oneRevisionSearchButton = new javax.swing.JButton();
    final javax.swing.JTextField oneRevisionTextField = new javax.swing.JTextField();
    final javax.swing.JCheckBox revertNewFilesCheckBox = new javax.swing.JCheckBox();
    final javax.swing.JTextField startRevisionTextField = new javax.swing.JTextField();
    final javax.swing.JButton startSearchButton = new javax.swing.JButton();
    // End of variables declaration//GEN-END:variables
    
}
