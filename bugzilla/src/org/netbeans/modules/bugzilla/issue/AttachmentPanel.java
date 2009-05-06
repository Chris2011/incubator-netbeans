/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009 Sun Microsystems, Inc. All rights reserved.
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
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */
package org.netbeans.modules.bugzilla.issue;

import java.io.File;
import java.util.ResourceBundle;
import javax.swing.DefaultComboBoxModel;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;

/**
 *
 * @author Jan Stola
 */
public class AttachmentPanel extends javax.swing.JPanel {
    static final String PROP_DELETED = "attachmentDeleted"; // NOI18N

    public AttachmentPanel() {
        initComponents();
        initFileTypeCombo();
    }

    private void initFileTypeCombo() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        ResourceBundle bundle = NbBundle .getBundle(AttachmentPanel.class);
        model.addElement(new FileType(null, bundle.getString("AttachmentPanel.fileType.automatic"))); // NOI18N
        model.addElement(new FileType("text/plain", bundle.getString("AttachmentPanel.fileType.textPlain"))); // NOI18N
        model.addElement(new FileType("text/html", bundle.getString("AttachmentPanel.fileType.textHTML"))); // NOI18N
        model.addElement(new FileType("application/xml", bundle.getString("AttachmentPanel.fileType.applicationXML"))); // NOI18N
        model.addElement(new FileType("image/gif", bundle.getString("AttachmentPanel.fileType.imageGIF"))); // NOI18N
        model.addElement(new FileType("image/jpeg", bundle.getString("AttachmentPanel.fileType.imageJPEG"))); // NOI18N
        model.addElement(new FileType("image/png", bundle.getString("AttachmentPanel.fileType.imagePNG"))); // NOI18N
        model.addElement(new FileType("application/octet-stream", bundle.getString("AttachmentPanel.fileType.binary"))); // NOI18N
        fileTypeCombo.setModel(model);
    }

    public File getFile() {
        File file = null;
        if (!isDeleted()) {
            file = new File(fileField.getText());
        }
        return file;
    }

    public String getDescription() {
        return descriptionField.getText();
    }

    public String getContentType() {
        String contentType = null;
        Object value = fileTypeCombo.getSelectedItem();
        if (value instanceof FileType) {
            contentType = ((FileType)value).getContentType();
        } else {
            contentType = value.toString();
        }
        return contentType;
    }

    public boolean isPatch() {
        return patchChoice.isSelected();
    }

    public boolean isDeleted() {
        return !isVisible();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        deleteButton = new org.netbeans.modules.bugtracking.util.LinkButton();
        descriptionLabel = new javax.swing.JLabel();
        descriptionField = new javax.swing.JTextField();
        fileTypeLabel = new javax.swing.JLabel();
        fileTypeCombo = new javax.swing.JComboBox();
        patchLabel = new javax.swing.JLabel();
        patchChoice = new javax.swing.JCheckBox();

        fileField.setColumns(30);

        browseButton.setText(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.browseButton.text")); // NOI18N
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        deleteButton.setText(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.deleteButton.text")); // NOI18N
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        descriptionLabel.setLabelFor(descriptionField);
        descriptionLabel.setText(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.descriptionLabel.text")); // NOI18N

        fileTypeLabel.setLabelFor(fileTypeCombo);
        fileTypeLabel.setText(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.fileTypeLabel.text")); // NOI18N

        fileTypeCombo.setEditable(true);

        patchLabel.setLabelFor(patchChoice);
        patchLabel.setText(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.patchLabel.text")); // NOI18N

        patchChoice.setBorder(null);
        patchChoice.setMargin(new java.awt.Insets(0, 0, 0, 0));
        patchChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patchChoiceActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(fileField)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(browseButton))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(fileTypeLabel)
                            .add(descriptionLabel)
                            .add(patchLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(fileTypeCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(descriptionField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                            .add(patchChoice))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fileField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(browseButton)
                    .add(deleteButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(descriptionLabel)
                    .add(descriptionField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fileTypeLabel)
                    .add(fileTypeCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(patchLabel)
                    .add(patchChoice)))
        );

        fileField.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.fileField.AccessibleContext.accessibleName")); // NOI18N
        fileField.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.fileField.AccessibleContext.accessibleDescription")); // NOI18N
        browseButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.browseButton.AccessibleContext.accessibleDescription")); // NOI18N
        deleteButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.deleteButton.AccessibleContext.accessibleDescription")); // NOI18N
        descriptionField.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.descriptionField.AccessibleContext.accessibleDescription")); // NOI18N
        fileTypeCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.fileTypeCombo.AccessibleContext.accessibleDescription")); // NOI18N
        patchChoice.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.patchChoice.AccessibleContext.accessibleName")); // NOI18N
        patchChoice.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AttachmentPanel.class, "AttachmentPanel.patchChoice.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        setVisible(false);
        firePropertyChange(PROP_DELETED, null, null);
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        File attachment = new FileChooserBuilder(AttachmentPanel.class).showOpenDialog();
        if (attachment != null) {
            attachment = FileUtil.normalizeFile(attachment);
            fileField.setText(attachment.getAbsolutePath());
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    private void patchChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patchChoiceActionPerformed
        fileTypeCombo.setEnabled(!patchChoice.isSelected());
        if (patchChoice.isSelected()) {
            // Select text/plain
            fileTypeCombo.setSelectedIndex(1);
        }
    }//GEN-LAST:event_patchChoiceActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private org.netbeans.modules.bugtracking.util.LinkButton deleteButton;
    private javax.swing.JTextField descriptionField;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField fileField;
    private javax.swing.JComboBox fileTypeCombo;
    private javax.swing.JLabel fileTypeLabel;
    private javax.swing.JCheckBox patchChoice;
    private javax.swing.JLabel patchLabel;
    // End of variables declaration//GEN-END:variables

    static class FileType {
        private String contentType;
        private String displayName;

        FileType(String contentType, String displayName) {
            this.contentType = contentType;
            this.displayName = displayName;
        }

        public String getContentType() {
            return contentType;
        }

        @Override
        public String toString() {
            return displayName + ((contentType == null) ? "" : " (" + contentType + ')'); // NOI18N
        }
    }

}
