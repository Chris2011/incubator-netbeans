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
package org.netbeans.modules.lsp.client.options;

import java.io.File;
import java.util.Locale;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.netbeans.modules.lsp.client.options.LanguageStorage.LanguageDescription;
import org.openide.util.NbBundle.Messages;

@Messages("BTN_Select=Select")
public class LanguageDescriptionPanel extends javax.swing.JPanel {

    private final String id;

    public LanguageDescriptionPanel(LanguageDescription desc, Set<String> usedIds) {
        initComponents();
        if (desc == null) {
            String id;
            String base = "";
            OUTER: while (true) {
                for (char c = 'a'; c <= 'z'; c++) {
                    if (usedIds.add(base + c)) {
                        id = base + c;
                        break OUTER;
                    }
                }
                base = "a";
            }
            this.id = id;
        } else {
            this.id = desc.id;
            this.extensions.setText(desc.extensions);
            this.syntax.setText(desc.syntaxGrammar);
            this.server.setText(desc.languageServer);
            this.name.setText(desc.name);
            this.icon.setText(desc.icon);
        }
    }

    public LanguageDescription getDescription() {
        return new LanguageDescription(id, this.extensions.getText(), this.syntax.getText(), this.server.getText(), this.name.getText(), this.icon.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        extensions = new javax.swing.JTextField();
        syntax = new javax.swing.JTextField();
        server = new javax.swing.JTextField();
        browseGrammar = new javax.swing.JButton();
        browseServer = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        icon = new javax.swing.JTextField();
        browseIcon = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 81;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 12, 0, 0);
        add(jLabel1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 12, 0, 0);
        add(jLabel2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.jLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 12, 0, 0);
        add(jLabel3, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.jLabel4.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 12, 0, 0);
        add(jLabel4, gridBagConstraints);

        name.setText(org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.name.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 166;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        add(name, gridBagConstraints);

        extensions.setText(org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.extensions.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 172;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        add(extensions, gridBagConstraints);

        syntax.setText(org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.syntax.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 172;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 12, 0, 0);
        add(syntax, gridBagConstraints);

        server.setText(org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.server.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 172;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 12, 0, 0);
        add(server, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(browseGrammar, org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.browseGrammar.text")); // NOI18N
        browseGrammar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseGrammarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 12);
        add(browseGrammar, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(browseServer, org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.browseServer.text")); // NOI18N
        browseServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseServerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 12);
        add(browseServer, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 308;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 12, 0, 12);
        add(jSeparator1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.jLabel5.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 12, 0, 0);
        add(jLabel5, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.jLabel6.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 92;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 12, 0, 0);
        add(jLabel6, gridBagConstraints);

        icon.setText(org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.icon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 166;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 12, 0, 0);
        add(icon, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(browseIcon, org.openide.util.NbBundle.getMessage(LanguageDescriptionPanel.class, "LanguageDescriptionPanel.browseIcon.text")); // NOI18N
        browseIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseIconActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 94, 12);
        add(browseIcon, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    @Messages("DESC_JSONFilter=Grammars (.json, .xml, .tmLanguage)")
    private void browseGrammarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseGrammarActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() ||
                       f.getName().toLowerCase(Locale.ENGLISH).endsWith(".json") ||
                       f.getName().toLowerCase(Locale.ENGLISH).endsWith(".xml") ||
                       f.getName().toLowerCase(Locale.ENGLISH).endsWith(".tmlanguage");
            }

            @Override
            public String getDescription() {
                return Bundle.DESC_JSONFilter();
            }
        });
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setSelectedFile(new File(syntax.getText()));
        if (chooser.showDialog(null, Bundle.BTN_Select()) == JFileChooser.APPROVE_OPTION) {
            syntax.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_browseGrammarActionPerformed

    @Messages("DESC_IconFilter=Icons (.png, .jpg, .gif)")
    private void browseIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseIconActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() ||
                       f.getName().toLowerCase(Locale.ENGLISH).endsWith(".png") ||
                       f.getName().toLowerCase(Locale.ENGLISH).endsWith(".jpg") ||
                       f.getName().toLowerCase(Locale.ENGLISH).endsWith(".gif");
            }

            @Override
            public String getDescription() {
                return Bundle.DESC_IconFilter();
            }
        });
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setSelectedFile(new File(icon.getText()));
        if (chooser.showDialog(null, Bundle.BTN_Select()) == JFileChooser.APPROVE_OPTION) {
            icon.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_browseIconActionPerformed

    private void browseServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseServerActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setSelectedFile(new File(server.getText()));
        if (chooser.showDialog(null, Bundle.BTN_Select()) == JFileChooser.APPROVE_OPTION) {
            server.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_browseServerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseGrammar;
    private javax.swing.JButton browseIcon;
    private javax.swing.JButton browseServer;
    private javax.swing.JTextField extensions;
    private javax.swing.JTextField icon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField name;
    private javax.swing.JTextField server;
    private javax.swing.JTextField syntax;
    // End of variables declaration//GEN-END:variables
}
