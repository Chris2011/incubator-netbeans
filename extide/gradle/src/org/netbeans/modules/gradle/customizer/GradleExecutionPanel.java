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
package org.netbeans.modules.gradle.customizer;

import org.netbeans.api.project.Project;
import org.netbeans.modules.gradle.ProjectTrust;
import org.netbeans.modules.gradle.api.GradleBaseProject;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author lkishalmi
 */
@Messages({
    "GRADLE_TRUST_MSG=<html><p>Executing Gradle can be potentially un-safe as it "
            + "allows arbitrary code execution.</p><p></p>"
            + "<p>By trusting this project, and with that all its subprojects, "
            + "you entitle NetBeans to invoke Gradle to load project details "
            + "without further confirmation.</p><p></p>"
            + "<p>Invoking any build related actions, would mark this project "
            + "automatically trusted.</p>",
})
public class GradleExecutionPanel extends javax.swing.JPanel {

    Project project;

    /**
     * Creates new form GradleExecutionPanel
     */
    public GradleExecutionPanel() {
        initComponents();
        lbTrustTerms.setText(Bundle.GRADLE_TRUST_MSG());
    }

    public GradleExecutionPanel(Project project) {
        this();
        this.project = project;
        GradleBaseProject gbp = GradleBaseProject.get(project);
        if (gbp != null) {
            lbReadOnly.setVisible(!gbp.isRoot());
            cbTrustProject.setEnabled(gbp.isRoot());
            lbTrustTerms.setEnabled(gbp.isRoot());
            cbTrustProject.setSelected(ProjectTrust.getDefault().isTrusted(project));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbTrustProject = new javax.swing.JCheckBox();
        lbTrustTerms = new javax.swing.JLabel();
        lbReadOnly = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(cbTrustProject, org.openide.util.NbBundle.getMessage(GradleExecutionPanel.class, "GradleExecutionPanel.cbTrustProject.text")); // NOI18N

        lbTrustTerms.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lbReadOnly.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/gradle/resources/info.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(lbReadOnly, org.openide.util.NbBundle.getMessage(GradleExecutionPanel.class, "GradleExecutionPanel.lbReadOnly.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbReadOnly)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbTrustProject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lbTrustTerms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbTrustProject)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTrustTerms, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(lbReadOnly)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbTrustProject;
    private javax.swing.JLabel lbReadOnly;
    private javax.swing.JLabel lbTrustTerms;
    // End of variables declaration//GEN-END:variables

    void save() {
        if (project != null) {
            if (cbTrustProject.isSelected()) {
                ProjectTrust.getDefault().trustProject(project);
            } else {
                ProjectTrust.getDefault().distrustProject(project);
            }
        }
    }
}
