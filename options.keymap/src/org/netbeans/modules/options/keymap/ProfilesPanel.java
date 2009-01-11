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

package org.netbeans.modules.options.keymap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;
import javax.swing.AbstractListModel;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import org.netbeans.core.options.keymap.api.ShortcutAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.NotifyDescriptor.InputLine;
import org.openide.NotifyDescriptor.Message;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.xml.EntityCatalog;
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Panel for managing keymap profiles
 * @author Max Sauer
 */
public class ProfilesPanel extends javax.swing.JPanel {

    private ProfileListModel model;

    /** Creates new form ProfilesPanel */
    public ProfilesPanel() {
        model = new ProfileListModel();
        initComponents();
        model.setData(KeymapPanel.getModel().getProfiles());
        profilesList.setSelectedValue(KeymapPanel.getModel().getCurrentProfile(), true);
    }

    ProfileListModel getModel() {
        return model;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        profilesList = new javax.swing.JList();
        duplicateButton = new javax.swing.JButton();
        restoreButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        exportButton = new javax.swing.JButton();
        importButton = new javax.swing.JButton();

        profilesList.setModel(model);
        profilesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        profilesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                profilesListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(profilesList);
        profilesList.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.profilesList.AccessibleContext.accessibleName")); // NOI18N
        profilesList.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.profilesList.AccessibleContext.accessibleDescription")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(duplicateButton, org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.duplicateButton.text")); // NOI18N
        duplicateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                duplicateButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(restoreButton, org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.restoreButton.text")); // NOI18N
        restoreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restoreButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(deleteButton, org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.deleteButton.text")); // NOI18N
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(exportButton, org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.exportButton.text")); // NOI18N
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(importButton, org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.importButton.text")); // NOI18N
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, duplicateButton)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, restoreButton)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, deleteButton)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, exportButton)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, importButton))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {deleteButton, duplicateButton, exportButton, importButton, restoreButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(duplicateButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(restoreButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(deleteButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(exportButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(importButton)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.jScrollPane1.AccessibleContext.accessibleName")); // NOI18N
        jScrollPane1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.jScrollPane1.AccessibleContext.accessibleDescription")); // NOI18N
        duplicateButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.duplicateButton.AccessibleContext.accessibleDescription")); // NOI18N
        restoreButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.restoreButton.AccessibleContext.accessibleDescription")); // NOI18N
        deleteButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.deleteButton.AccessibleContext.accessibleDescription")); // NOI18N
        exportButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.exportButton.AccessibleContext.accessibleDescription")); // NOI18N
        importButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.importButton.AccessibleContext.accessibleDescription")); // NOI18N

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.AccessibleContext.accessibleName")); // NOI18N
        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(ProfilesPanel.class, "ProfilesPanel.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void restoreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restoreButtonActionPerformed
        deleteOrRestoreSelectedProfile();
    }//GEN-LAST:event_restoreButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        deleteOrRestoreSelectedProfile();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void profilesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_profilesListValueChanged
        String profile = (String) profilesList.getSelectedValue();
        if (profile == null) {
            deleteButton.setEnabled(false);
            duplicateButton.setEnabled(false);
            exportButton.setEnabled(false);
            restoreButton.setEnabled(false);
            return ;
        }
        
        duplicateButton.setEnabled(true);
        exportButton.setEnabled(true);
        
        if(KeymapPanel.getModel().isCustomProfile(profile)) {
            deleteButton.setEnabled(true);
            restoreButton.setEnabled(false);
        } else {
            deleteButton.setEnabled(false);
            restoreButton.setEnabled(true);
        }
    }//GEN-LAST:event_profilesListValueChanged

    private void duplicateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_duplicateButtonActionPerformed
        duplicateProfile();
    }//GEN-LAST:event_duplicateButtonActionPerformed

    private String duplicateProfile() {
        String newName = null;
        InputLine il = new InputLine(
                KeymapPanel.loc("CTL_Create_New_Profile_Message"), // NOI18N
                KeymapPanel.loc("CTL_Create_New_Profile_Title") // NOI18N
                );
        il.setInputText((String) profilesList.getSelectedValue());
        DialogDisplayer.getDefault().notify(il);
        if (il.getValue() == NotifyDescriptor.OK_OPTION) {
            newName = il.getInputText();
            for (String s : KeymapPanel.getModel().getProfiles()) {
                if (newName.equals(s)) {
                    Message md = new Message(
                            KeymapPanel.loc("CTL_Duplicate_Profile_Name"), // NOI18N
                            Message.ERROR_MESSAGE);
                    DialogDisplayer.getDefault().notify(md);
                    return null;
                }
            }
            KeymapPanel.getModel().cloneProfile(newName);
            model.addItem(il.getInputText());
            profilesList.setSelectedValue(il.getInputText(), true);
        }
        return newName;
    }

    private static final String PUBLIC_ID = "-//NetBeans//DTD Keymap Preferences 1.0//EN"; //NOI18N
    private static final String SYSTEM_ID = "http://www.netbeans.org/dtds/KeymapPreferences-1_0.dtd"; //NOI18N
    private static final String ATTR_ACTION_ID = "id"; //NOI18N
    private static final String ELEM_SHORTCUT = "shortcut"; //NOI18N
    private static final String ELEM_XML_ROOT = "keymap-preferences"; //NOI18N
    private static final String ATTR_SHORTCUT_STRING = "shortcut_string"; //NOI18N
    private static final String ELEM_ACTION = "action"; //NOI18N

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed

        // show filechooser
        final JFileChooser chooser = getFileChooser();

        chooser.setSelectedFile(new File("exported-" + profilesList.getSelectedValue() + "-profile.xml")); //NOI18N

        int ret = chooser.showSaveDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            Document doc = XMLUtil.createDocument(ELEM_XML_ROOT, null, PUBLIC_ID, SYSTEM_ID);
            Node root = doc.getElementsByTagName(ELEM_XML_ROOT).item(0);

            KeymapViewModel kmodel = KeymapPanel.getModel();
            for (String category : kmodel.getCategories().get("")) {
                for (Object o : kmodel.getItems(category)) {
                    if (o instanceof ShortcutAction) {
                        ShortcutAction sca = (ShortcutAction) o;
                        String[] shortcuts = kmodel.getShortcuts(sca);
                        if (shortcuts.length > 0) { //export only actions with at least one SC
                            String id = sca.getId();
                            Element actionElement = doc.createElement(ELEM_ACTION);
                            actionElement.setAttribute(ATTR_ACTION_ID, id);
                            for (int i = 0; i < shortcuts.length; i++) {
                                Element shortcutElement = doc.createElement(ELEM_SHORTCUT);
                                //get portable representation of the ahortcut
                                String shortcutToStore = shortcutToPortableRepresentation(shortcuts[i]);
                                shortcutElement.setAttribute(ATTR_SHORTCUT_STRING, shortcutToStore);
                                actionElement.appendChild(shortcutElement);
                            }
                            root.appendChild(actionElement);
                        }
                    }
                }
            }

            File f = chooser.getSelectedFile();

            FileOutputStream fos;
            try {
                fos = new FileOutputStream(f);
                XMLUtil.write(doc, fos, "UTF-8"); //NOI18N
                fos.close();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

    }//GEN-LAST:event_exportButtonActionPerformed

    private static String shortcutToPortableRepresentation(String key) {
        assert key != null : "The parameter key must not be null"; //NOI18N

        StringBuilder buf = new StringBuilder();
        String delimiter = " "; //NOI18N

        for(StringTokenizer st = new StringTokenizer(key, delimiter); st.hasMoreTokens();) { //NOI18N
            String ks = st.nextToken().trim();

            KeyStroke keyStroke = Utils.getKeyStroke(ks);

            if (keyStroke != null) {
                buf.append(Utilities.keyToString(keyStroke, true));
                if (st.hasMoreTokens())
                    buf.append(' ');
            } else {
                return null;
            }
        }

        return buf.toString();
    }

    private static String portableRepresentationToShortcut(String portable) {
        assert portable != null : "The parameter must not be null"; //NOI18N

        StringBuilder buf = new StringBuilder();
        String delimiter = " "; //NOI18N

        for(StringTokenizer st = new StringTokenizer(portable, delimiter); st.hasMoreTokens();) { //NOI18N
            String ks = st.nextToken().trim();

            KeyStroke keyStroke = Utilities.stringToKey(ks);

            if (keyStroke != null) {
                buf.append(Utils.getKeyStrokeAsText(keyStroke));
                if (st.hasMoreTokens())
                    buf.append(' ');
            } else {
                return null;
            }
        }

        return buf.toString();
    }

    private static JFileChooser getFileChooser() {
        final JFileChooser chooser = new JFileChooser();
        XMLFileFilter filter = new XMLFileFilter();

        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(filter);
        chooser.setFileFilter(filter);

        return chooser;
    }

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        JFileChooser chooser = getFileChooser();
        int ret = chooser.showOpenDialog(this);
        
        if(ret == JFileChooser.APPROVE_OPTION) {
            try {
                InputSource is = new InputSource(new FileInputStream(chooser.getSelectedFile()));
                KeymapViewModel kmodel = KeymapPanel.getModel();
                String newProfile = duplicateProfile();
                if (newProfile == null) return; //invalid (duplicate) profile name
                kmodel.setCurrentProfile(newProfile);

                Document doc = XMLUtil.parse(is, false, true, null, EntityCatalog.getDefault());
                Node root = doc.getElementsByTagName(ELEM_XML_ROOT).item(0);

                NodeList nl = root.getChildNodes();
                for (int i = 0; i < nl.getLength(); i++) {//iterate stored actions
                    Node action = nl.item(i);
                    final NamedNodeMap attributes = action.getAttributes();
                    if (attributes == null) continue;
                    String id = attributes.item(0).getNodeValue();
                    ShortcutAction sca = kmodel.findActionForId(id);
                    NodeList childList = action.getChildNodes();
                    int childCount = childList.getLength();
                    Set<String> shortcuts = new LinkedHashSet<String>(childCount);
                    for (int j = 0; j < childCount; j++) {//iterate shortcuts
                        //iterate shortcuts
                        NamedNodeMap attrs = childList.item(j).getAttributes();
                        if (attrs != null) {
                            String sc = attrs.item(0).getNodeValue();
                            shortcuts.add(portableRepresentationToShortcut(sc));
                        }
                    }
                    kmodel.setShortcuts(sca, shortcuts);
                }

            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } catch (SAXException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

    }//GEN-LAST:event_importButtonActionPerformed

    private static class XMLFileFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File file) {
            if (file.isDirectory())
                return false;

            if(file.getAbsolutePath().endsWith(".xml")) {
                return true;
            } else {
                return false;
            }
        }
        
        @Override
        public String getDescription() {
            return "XML " + NbBundle.getMessage(ProfilesPanel.class, "CTL_Files") + "(*.xml)"; //NOI18N
        }
    }


    private void deleteOrRestoreSelectedProfile() {
        String currentProfile = (String) profilesList.getSelectedValue();
        final KeymapViewModel keymapModel = KeymapPanel.getModel();
        keymapModel.deleteOrRestoreProfile(currentProfile);
        if (keymapModel.isCustomProfile (currentProfile)) {
            model.removeItem(profilesList.getSelectedIndex());
            profilesList.setSelectedIndex(0);
        }
    }

    public String getSelectedProfile() {
        return (String) profilesList.getSelectedValue();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton duplicateButton;
    private javax.swing.JButton exportButton;
    private javax.swing.JButton importButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList profilesList;
    private javax.swing.JButton restoreButton;
    // End of variables declaration//GEN-END:variables


    private class ProfileListModel extends AbstractListModel {

        private ArrayList<String> delegate = new ArrayList<String>();

        public int getSize() {
            return delegate.size();
        }

        public Object getElementAt(int index) {
            return delegate.get(index);
        }

        public void setData(Collection<String> c) {
            delegate.clear();
            delegate.addAll(c);
            fireContentsChanged(this, 0, c.size());
        }

        private void addItem(String inputText) {
            delegate.add(inputText);
            int size = delegate.size();
            fireContentsChanged(this, size, size);
        }

        private void removeItem(int index) {
            delegate.remove(index);
            fireContentsChanged(this, index, index);
        }
    }

}
