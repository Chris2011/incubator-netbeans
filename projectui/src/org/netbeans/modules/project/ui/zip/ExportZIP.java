/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */

package org.netbeans.modules.project.ui.zip;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.api.queries.SharabilityQuery;
import org.netbeans.api.queries.VisibilityQuery;
import org.netbeans.modules.project.ui.ProjectCellRenderer;
import static org.netbeans.modules.project.ui.zip.Bundle.*;
import org.openide.DialogDisplayer;
import org.openide.NotificationLineSupport;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileUtil;
import org.openide.util.Cancellable;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;

public class ExportZIP extends JPanel {

    private static final RequestProcessor RP = new RequestProcessor(ExportZIP.class);
    private static final Logger LOG = Logger.getLogger(ExportZIP.class.getName());

    @ActionID(category="Project", id="org.netbeans.modules.project.ui.zip.export")
    @ActionRegistration(iconInMenu=false, displayName="#CTL_ExportZIPAction")
    @ActionReference(path="Menu/File/Export", position=500)
    @Messages("CTL_ExportZIPAction=To &ZIP...")
    public static final class ExportZIPAction implements ActionListener {
        @Messages({
            "LBL_export=Export",
            "TITLE_export=Export Project(s) to ZIP",
            "# {0} - ZIP file", "MSG_created=Created {0}"
        })
        @Override public void actionPerformed(ActionEvent e) {
            final ExportZIP panel = new ExportZIP();
            final JButton ok = new JButton(LBL_export());
            NotifyDescriptor d = new NotifyDescriptor(panel, TITLE_export(), NotifyDescriptor.OK_CANCEL_OPTION, NotifyDescriptor.PLAIN_MESSAGE, new Object[] {ok, NotifyDescriptor.CANCEL_OPTION}, null);
            final NotificationLineSupport notifications = d.createNotificationLineSupport();
            panel.addPropertyChangeListener(new PropertyChangeListener() {
                @Override public void propertyChange(PropertyChangeEvent evt) {
                    ok.setEnabled(panel.check(notifications));
                }
            });
            panel.projectComboActionPerformed(null);
            if (DialogDisplayer.getDefault().notify(d) == ok) {
                final File root = panel.root();
                final File zip = new File(panel.zipField.getText());
                RP.post(new Runnable() {
                    @Override public void run() {
                        try {
                            if (!build(root, zip)) {
                                zip.delete();
                                return;
                            }
                        } catch (IOException x) {
                            LOG.log(Level.WARNING, null, x);
                            return;
                        }
                        StatusDisplayer.getDefault().setStatusText(MSG_created(zip));
                        try {
                            Desktop.getDesktop().open(zip);
                        } catch (Exception x) {
                            LOG.log(Level.FINE, null, x);
                        }
                    }
                });
            }
        }
    }

    @org.netbeans.api.annotations.common.SuppressWarnings("OS_OPEN_STREAM")
    @Messages({
        "# {0} - ZIP file", "MSG_building=Building {0}",
        "# {0} - ZIP entry name", "MSG_packed=Packed: {0}"
    })
    private static boolean build(File root, File zip) throws IOException {
        final AtomicBoolean canceled = new AtomicBoolean();
        ProgressHandle handle = ProgressHandleFactory.createHandle(MSG_building(zip.getName()), new Cancellable() {
            @Override public boolean cancel() {
                return canceled.compareAndSet(false, true);
            }
        });
        handle.start();
        try {
            List<String> files = new ArrayList<String>();
            scanForFiles(root, files, "", handle, canceled, true);
            if (canceled.get()) {
                return false;
            }
            handle.switchToDeterminate(files.size());
            OutputStream os = new FileOutputStream(zip);
            try {
                ZipOutputStream zos = new ZipOutputStream(os);
                Set<String> written = new HashSet<String>();
                String prefix = root.getName() + '/';
                for (int i = 0; i < files.size(); i++) {
                    if (canceled.get()) {
                        return false;
                    }
                    String name = files.get(i);
                    writeEntry(prefix + name, written, zos, new File(root, name));
                    handle.progress(MSG_packed(name), i);
                }
                zos.finish();
                zos.close();
            } finally {
                os.close();
            }
        } finally {
            handle.finish();
        }
        return true;
    }

    @Messages({"# {0} - subdirectory", "MSG_searching=Searching in {0}"})
    private static void scanForFiles(File root, List<String> files, String prefix, ProgressHandle handle, AtomicBoolean canceled, boolean mixedSharability) throws IOException {
        File[] kids = root.listFiles();
        if (kids == null) {
            throw new IOException("could not list " + root);
        }
        Arrays.sort(kids);
        for (File kid : kids) {
            if (canceled.get()) {
                return;
            }
            if (!VisibilityQuery.getDefault().isVisible(kid)) {
                continue;
            }
            boolean kidMixed;
            if (mixedSharability) {
                switch (SharabilityQuery.getSharability(kid)) {
                case SharabilityQuery.SHARABLE:
                    kidMixed = false;
                    break;
                case SharabilityQuery.NOT_SHARABLE:
                    continue;
                default:
                    kidMixed = true;
                }
            } else {
                kidMixed = false;
            }
            String n = kid.getName();
            String prefixN = prefix + n;
            if (kid.isFile()) {
                files.add(prefixN);
            } else if (kid.isDirectory()) {
                handle.progress(MSG_searching(prefixN));
                scanForFiles(kid, files, prefixN + '/', handle, canceled, kidMixed);
            } // else symlink etc.?
        }
    }

    private static void writeEntry(String name, Set<String> written, ZipOutputStream zos, File f) throws IOException, FileNotFoundException {
        if (!written.add(name)) {
            return;
        }
        int idx = name.lastIndexOf('/', name.length() - 2);
        if (idx != -1) {
            writeEntry(name.substring(0, idx + 1), written, zos, f.getParentFile());
        }
        ZipEntry ze = new ZipEntry(name);
        ze.setTime(f.lastModified());
        if (name.endsWith("/")) {
            ze.setMethod(ZipEntry.STORED);
            ze.setSize(0);
            ze.setCrc(0);
            zos.putNextEntry(ze);
        } else {
            ByteArrayOutputStream content = new ByteArrayOutputStream((int) f.length());
            InputStream is = new FileInputStream(f);
            try {
                FileUtil.copy(is, content);
            } finally {
                is.close();
            }
            ze.setMethod(ZipEntry.DEFLATED);
            ze.setSize(f.length());
            CRC32 crc = new CRC32();
            byte[] data = content.toByteArray();
            crc.update(data);
            ze.setCrc(crc.getValue());
            zos.putNextEntry(ze);
            zos.write(data);
        }
    }

    @Messages({
        "ERR_no_proj=No project selected.",
        "ERR_no_root=Must select a root directory to package.",
        "# {0} - directory", "ERR_no_dir={0} does not exist.",
        "ERR_no_zip=Must select a ZIP to export to.",
        "# {0} - file", "ERR_exists={0} already exists.",
        "ERR_hg=If using Mercurial, consider instead: hg bundle --all ..."
    })
    private boolean check(NotificationLineSupport notifications) {
        notifications.clearMessages();
        if (projectRadio.isSelected() && projectCombo.getSelectedIndex() == -1) {
            notifications.setInformationMessage(ERR_no_proj());
            return false;
        } else if (otherRadio.isSelected()) {
            String t = otherField.getText();
            if (t.isEmpty()) {
                notifications.setInformationMessage(ERR_no_root());
                return false;
            } else if (!new File(t).isDirectory()) {
                notifications.setErrorMessage(ERR_no_dir(t));
                return false;
            }
        }
        String t = zipField.getText();
        if (t.isEmpty()) {
            notifications.setInformationMessage(ERR_no_zip());
            return false;
        } else if (new File(t).exists()) {
            notifications.setErrorMessage(ERR_exists(t));
            return false;
        } else if (new File(t).getParentFile() == null) {
            notifications.setErrorMessage(ERR_no_dir(new File(t)));
            return false;
        } else if (!new File(t).getParentFile().isDirectory()) {
            notifications.setErrorMessage(ERR_no_dir(new File(t).getParent()));
            return false;
        }
        if (new File(root(), ".hg/store").isDirectory()) {
            notifications.setInformationMessage(ERR_hg());
        }
        return true;
    }

    private boolean zipFieldDefault = true;

    private ExportZIP() {
        initComponents();
        projectCombo.setRenderer(new ProjectCellRenderer());
        projectCombo.setModel(new DefaultComboBoxModel(OpenProjects.getDefault().getOpenProjects()));
        zipField.getDocument().addDocumentListener(new DocumentListener() {
            private void edited() {
                firePropertyChange("validity", null, null);
                zipFieldDefault = false;
            }
            @Override public void insertUpdate(DocumentEvent e) {
                edited();
            }
            @Override public void removeUpdate(DocumentEvent e) {
                edited();
            }
            @Override public void changedUpdate(DocumentEvent e) {}
        });
    }
    
    private File root() {
        if (projectRadio.isSelected()) {
            Project p = (Project) projectCombo.getSelectedItem();
            if (p != null) {
                return FileUtil.toFile(p.getProjectDirectory());
            } else {
                return null;
            }
        } else {
            return new File(otherField.getText());
        }
    }

    private void defaultZipField() {
        if (zipFieldDefault) {
            File root = root();
            if (root != null) {
                zipField.setText(FileUtil.normalizeFile(new File(System.getProperty("java.io.tmpdir"), root.getName() + ".zip")).getAbsolutePath());
                zipFieldDefault = true;
            }
        }
        firePropertyChange("validity", null, null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootGroup = new javax.swing.ButtonGroup();
        projectRadio = new javax.swing.JRadioButton();
        projectCombo = new javax.swing.JComboBox();
        otherRadio = new javax.swing.JRadioButton();
        otherField = new javax.swing.JTextField();
        otherButton = new javax.swing.JButton();
        zipLabel = new javax.swing.JLabel();
        zipField = new javax.swing.JTextField();
        zipButton = new javax.swing.JButton();

        rootGroup.add(projectRadio);
        projectRadio.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(projectRadio, org.openide.util.NbBundle.getMessage(ExportZIP.class, "ExportZIP.projectRadio.text")); // NOI18N
        projectRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectRadioActionPerformed(evt);
            }
        });

        projectCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectComboActionPerformed(evt);
            }
        });

        rootGroup.add(otherRadio);
        org.openide.awt.Mnemonics.setLocalizedText(otherRadio, org.openide.util.NbBundle.getMessage(ExportZIP.class, "ExportZIP.otherRadio.text")); // NOI18N
        otherRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherRadioActionPerformed(evt);
            }
        });

        otherField.setEditable(false);

        org.openide.awt.Mnemonics.setLocalizedText(otherButton, org.openide.util.NbBundle.getMessage(ExportZIP.class, "ExportZIP.otherButton.text")); // NOI18N
        otherButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherButtonActionPerformed(evt);
            }
        });

        zipLabel.setLabelFor(zipField);
        org.openide.awt.Mnemonics.setLocalizedText(zipLabel, org.openide.util.NbBundle.getMessage(ExportZIP.class, "ExportZIP.zipLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(zipButton, org.openide.util.NbBundle.getMessage(ExportZIP.class, "ExportZIP.zipButton.text")); // NOI18N
        zipButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zipButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(zipLabel)
                    .addComponent(projectRadio)
                    .addComponent(otherRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(otherField, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(otherButton))
                    .addComponent(projectCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(zipField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zipButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectRadio)
                    .addComponent(projectCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otherRadio)
                    .addComponent(otherField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(otherButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(zipLabel)
                    .addComponent(zipField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zipButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void zipButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zipButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            zipField.setText(fc.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_zipButtonActionPerformed

    private void otherButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            otherField.setText(fc.getSelectedFile().getAbsolutePath());
            otherRadio.setSelected(true);
            defaultZipField();
        }
    }//GEN-LAST:event_otherButtonActionPerformed

    private void projectComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectComboActionPerformed
        projectRadio.setSelected(true);
        defaultZipField();
    }//GEN-LAST:event_projectComboActionPerformed

    private void projectRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectRadioActionPerformed
        defaultZipField();
    }//GEN-LAST:event_projectRadioActionPerformed

    private void otherRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherRadioActionPerformed
        defaultZipField();
    }//GEN-LAST:event_otherRadioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton otherButton;
    private javax.swing.JTextField otherField;
    private javax.swing.JRadioButton otherRadio;
    private javax.swing.JComboBox projectCombo;
    private javax.swing.JRadioButton projectRadio;
    private javax.swing.ButtonGroup rootGroup;
    private javax.swing.JButton zipButton;
    private javax.swing.JTextField zipField;
    private javax.swing.JLabel zipLabel;
    // End of variables declaration//GEN-END:variables

}
