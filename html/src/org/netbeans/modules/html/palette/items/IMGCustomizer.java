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

package org.netbeans.modules.html.palette.items;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.modules.html.palette.HTMLPaletteUtilities;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.netbeans.modules.html.palette.BrowseFolders;
import org.openide.util.NbBundle;




/**
 *
 * @author  Libor Kotouc
 */
public class IMGCustomizer extends javax.swing.JPanel {
    
    private Dialog dialog = null;
    private DialogDescriptor descriptor = null;
    private boolean dialogOK = false;

    IMG img;
    JTextComponent target;
            
    public IMGCustomizer(IMG img, JTextComponent target) {
        this.img = img;
        this.target = target;
        
        initComponents();

        jFileChooser1.setAcceptAllFileFilterUsed(false);
        jFileChooser1.addChoosableFileFilter(
            new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.isDirectory() || FileUtil.toFileObject(pathname).getMIMEType().startsWith("image/"); // NOI18N
                }
                public String getDescription() {
                    String desc = NbBundle.getMessage(IMGCustomizer.class, "LBL_IMG_FileChooserDesc");
                    return desc;
                }
            }
        );
                
    }
    
    public boolean showDialog() {
        
        dialogOK = false;
        
        String displayName = "";
        try {
            displayName = NbBundle.getBundle("org.netbeans.modules.html.palette.items.resources.Bundle").getString("NAME_html-IMG"); // NOI18N
        }
        catch (Exception e) {}
        
        descriptor = new DialogDescriptor
                (this, NbBundle.getMessage(IMGCustomizer.class, "LBL_Customizer_InsertPrefix") + " " + displayName, true,
                 DialogDescriptor.OK_CANCEL_OPTION, DialogDescriptor.OK_OPTION,
                 new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                        if (descriptor.getValue().equals(DialogDescriptor.OK_OPTION)) {
                            evaluateInput();
                            dialogOK = true;
                        }
                        dialog.dispose();
		     }
		 } 
                );
        
        dialog = DialogDisplayer.getDefault().createDialog(descriptor);
        dialog.setVisible(true);
        repaint();
        
        return dialogOK;
    }
    
    private void evaluateInput() {
        
        String loc = jTextField1.getText();
        img.setLocation(loc);
        
        String width = jTextField2.getText();
        img.setWidth(width);
        
        String height = jTextField3.getText();
        img.setHeight(height);
        
        String alt = jTextField4.getText();
        img.setAlttext(alt);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jFileChooser1 = new javax.swing.JFileChooser();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();

        jFileChooser1.setCurrentDirectory(null);

        setLayout(new java.awt.GridBagLayout());

        jLabel4.setLabelFor(jTextField4);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "LBL_IMG_Alt"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 0);
        add(jLabel4, gridBagConstraints);
        jLabel4.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "ACSN_IMG_Alt"));
        jLabel4.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "ACSD_IMG_Alt"));

        jTextField1.setColumns(30);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        add(jTextField1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "LBL_IMG_Browse"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 12);
        add(jButton1, gridBagConstraints);
        jButton1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "ACSN_IMG_Browse"));
        jButton1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "ACSD_IMG_Browse"));

        jTextField2.setColumns(10);
        jTextField2.setMinimumSize(new java.awt.Dimension(100, 19));
        jTextField2.setPreferredSize(new java.awt.Dimension(100, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        add(jTextField2, gridBagConstraints);

        jTextField3.setColumns(10);
        jTextField3.setMinimumSize(new java.awt.Dimension(100, 19));
        jTextField3.setPreferredSize(new java.awt.Dimension(100, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 0);
        add(jTextField3, gridBagConstraints);

        jLabel1.setLabelFor(jTextField1);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "LBL_IMG_Location"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        add(jLabel1, gridBagConstraints);
        jLabel1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "ACSN_IMG_Location"));
        jLabel1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "ACSD_IMG_Location"));

        jLabel2.setLabelFor(jTextField2);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "LBL_IMG_Width"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        add(jLabel2, gridBagConstraints);
        jLabel2.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "ACSN_IMG_Width"));
        jLabel2.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "ACSD_IMG_Width"));

        jLabel3.setLabelFor(jTextField3);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "LBL_IMG_Height"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 0);
        add(jLabel3, gridBagConstraints);
        jLabel3.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "ACSN_IMG_Height"));
        jLabel3.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(IMGCustomizer.class, "ACSD_IMG_Height"));

        jTextField4.setColumns(30);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 0);
        add(jTextField4, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Document targetDoc = target.getDocument();
        FileObject targetDocFO = NbEditorUtilities.getFileObject(targetDoc);
        SourceGroup[] sg = HTMLPaletteUtilities.getSourceGroups(targetDocFO);
        
        File file = null;
        if (sg.length > 0) {
            FileObject fo = BrowseFolders.showDialog(sg, BrowseFolders.imageFileFilter);
            if (fo != null)
                file = FileUtil.toFile(fo);
        }
        else {
            jFileChooser1.setCurrentDirectory(FileUtil.toFile(targetDocFO.getParent()));
            int returnVal = jFileChooser1.showOpenDialog(this);

            if (returnVal == jFileChooser1.APPROVE_OPTION)
                file = jFileChooser1.getSelectedFile();
        }
        
        if (file != null) {
            String path = file.getAbsolutePath();
            FileObject imgFO = FileUtil.toFileObject(file);
            try {
                String relPathToImg = HTMLPaletteUtilities.getRelativePath(targetDocFO, imgFO);
                if (relPathToImg.length() > 0)
                    path = relPathToImg;
            }
            catch (Exception e) {
                //eventual exceptions imply the absolute path to be used
            }
            
            jTextField1.setText(path);
            
            try {
                Image img = new ImageIcon(file.getAbsolutePath()).getImage();
                int width = img.getWidth(null);
                jTextField2.setText(width != -1 ? new Integer(width).toString() : "");
                int height = img.getHeight(null);
                jTextField3.setText(height != -1 ? new Integer(height).toString() : "");
                jTextField4.setText(imgFO.getName());
            }
            catch (Exception ex) {
                Logger.getLogger("global").log(Level.INFO, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
    
}
