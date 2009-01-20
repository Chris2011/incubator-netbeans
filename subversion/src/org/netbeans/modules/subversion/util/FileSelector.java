/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

/*
 * RootSelectorPanel.java
 *
 * Created on Dec 1, 2008, 2:41:58 PM
 */

package org.netbeans.modules.subversion.util;

import java.awt.Dialog;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.netbeans.modules.subversion.Subversion;
import org.netbeans.modules.subversion.SvnModuleConfig;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 *
 * @author Tomas Stuka
 */
public class FileSelector extends javax.swing.JPanel implements ListSelectionListener {
    private File[] files;
    private DialogDescriptor dialogDescriptor;
    private JButton okButton;
    private JButton cancelButton;

    /** Creates new form RootSelectorPanel */
    public FileSelector() {
        initComponents();
        filesList.addListSelectionListener(this);

        dialogDescriptor = new DialogDescriptor(this, org.openide.util.NbBundle.getMessage(FileSelector.class, "LBL_FileSelector_Title"));

        okButton = new JButton(org.openide.util.NbBundle.getMessage(FileSelector.class, "CTL_FileSelector_Select"));
        okButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FileSelector.class, "CTL_FileSelector_Select"));
        okButton.setEnabled(false);
        cancelButton = new JButton(org.openide.util.NbBundle.getMessage(FileSelector.class, "CTL_FileSelector_Cancel"));                                      // NOI18N
        cancelButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FileSelector.class, "CTL_FileSelector_Cancel"));    // NOI18N
        dialogDescriptor.setOptions(new Object[] {okButton, cancelButton});

        dialogDescriptor.setModal(true);
        dialogDescriptor.setHelpCtx(new HelpCtx(this.getClass()));
        dialogDescriptor.setValid(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(FileSelector.class, "FileSelector.jLabel1.text")); // NOI18N

        filesList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        filesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(filesList);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .add(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    final javax.swing.JList filesList = new javax.swing.JList();
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public boolean show(File[] files) {
        Arrays.sort(files);
        this.files = files;
        DefaultListModel m = new DefaultListModel();
        for (File file : files) {
            m.addElement(file);
        }
        filesList.setModel(m);
        preselectFile(files);

        Dialog dialog = DialogDisplayer.getDefault().createDialog(dialogDescriptor);
        dialog.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(FileSelector.class, "LBL_FileSelector_Title"));                     // NOI18N

        dialog.setVisible(true);
        dialogDescriptor.setHelpCtx(new HelpCtx("org.netbeans.modules.subversion.FileSelector"));
        boolean ret = dialogDescriptor.getValue() == okButton;
        if(ret) {
            saveSelectedFile(files);
        }
        return ret;
    }

    public File getSelectedFile() {
        return (File) filesList.getSelectedValue();
    }

    public void valueChanged(ListSelectionEvent e) {
        boolean enabled = filesList.getSelectedValue() != null;
        dialogDescriptor.setValid(enabled);
        okButton.setEnabled(enabled);
    }

    private void preselectFile(File[] files) {
        String hash = getHash(files);
        if(hash == null || hash.trim().equals("")) {
            return;
        }
        String path = SvnModuleConfig.getDefault().getFileSelectorPreset(hash);
        if(path != null && !path.trim().equals("")) {
            File f = new File(path);
            filesList.setSelectedValue(f, true);
        }
    }

    private void saveSelectedFile(File[] files) {
        String hash = getHash(files);
        if(hash == null || hash.trim().equals("")) {
            return;
        }
        File file = getSelectedFile();
        if(file != null) {
            SvnModuleConfig.getDefault().setFileSelectorPreset(hash, file.getAbsolutePath());
        }
    }

    private String getHash(File[] files) {
        Arrays.sort(files);
        StringBuffer sb = new StringBuffer();
        for (File file : files) {
            sb.append(file.getAbsolutePath());
        }
        String hash = null;
        try {
            hash = SvnUtils.getHash("MD5", sb.toString().getBytes());
        } catch (NoSuchAlgorithmException ex) {
            Subversion.LOG.log(Level.SEVERE, null, ex); // should not happen
        }
        return hash;
    }

}
