/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.mercurial.ui.commit;

import org.netbeans.modules.mercurial.HgModuleConfig;
import org.netbeans.modules.versioning.util.ListenersSupport;
import org.netbeans.modules.versioning.util.VersioningListener;
import org.netbeans.modules.versioning.util.Utils;
import org.netbeans.modules.versioning.util.StringSelector;
import org.openide.util.NbBundle;

import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

/**
 *
 * @author  pk97937
 */
public class CommitPanel extends javax.swing.JPanel implements PreferenceChangeListener, TableModelListener {

    static final Object EVENT_SETTINGS_CHANGED = new Object();

    private CommitTable commitTable;
    
    /** Creates new form CommitPanel */
    public CommitPanel() {
        initComponents();
    }

    void setCommitTable(CommitTable commitTable) {
        this.commitTable = commitTable;
    }
    
    void setErrorLabel(String htmlErrorLabel) {
        jLabel2.setText(htmlErrorLabel);
    }    

    public void addNotify() {
        super.addNotify();
        HgModuleConfig.getDefault().getPreferences().addPreferenceChangeListener(this);
        commitTable.getTableModel().addTableModelListener(this);
        listenerSupport.fireVersioningEvent(EVENT_SETTINGS_CHANGED);

        recentLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        recentLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                onBrowseRecentMessages();
            }
        });
        
        List<String> messages = Utils.getStringList(HgModuleConfig.getDefault().getPreferences(), CommitAction.RECENT_COMMIT_MESSAGES);
        if (messages.size() > 0) {
            messageTextArea.setText(messages.get(0));
        }
        messageTextArea.selectAll();
    }

    public void removeNotify() {
        commitTable.getTableModel().removeTableModelListener(this);
        HgModuleConfig.getDefault().getPreferences().removePreferenceChangeListener(this);
        super.removeNotify();
    }
    
    private void onBrowseRecentMessages() {
        String message = StringSelector.select(NbBundle.getMessage(CommitPanel.class, "CTL_CommitForm_RecentTitle"), 
                                               NbBundle.getMessage(CommitPanel.class, "CTL_CommitForm_RecentPrompt"), 
            Utils.getStringList(HgModuleConfig.getDefault().getPreferences(), CommitAction.RECENT_COMMIT_MESSAGES));
        if (message != null) {
            messageTextArea.replaceSelection(message);
        }
    }
    
    public void preferenceChange(PreferenceChangeEvent evt) {
        if (evt.getKey().startsWith(HgModuleConfig.PROP_COMMIT_EXCLUSIONS)) {
            commitTable.dataChanged();
            listenerSupport.fireVersioningEvent(EVENT_SETTINGS_CHANGED);
        }
    }

    public void tableChanged(TableModelEvent e) {
        listenerSupport.fireVersioningEvent(EVENT_SETTINGS_CHANGED);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMinimumSize(new java.awt.Dimension(400, 300));
        setPreferredSize(new java.awt.Dimension(650, 400));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CommitPanel.class, "CTL_CommitForm_Message")); // NOI18N

        messageTextArea.setColumns(30);
        messageTextArea.setLineWrap(true);
        messageTextArea.setRows(6);
        messageTextArea.setTabSize(4);
        messageTextArea.setWrapStyleWord(true);
        messageTextArea.setMinimumSize(new java.awt.Dimension(100, 18));
        jScrollPane1.setViewportView(messageTextArea);

        org.openide.awt.Mnemonics.setLocalizedText(filesLabel, org.openide.util.NbBundle.getMessage(CommitPanel.class, "CTL_CommitForm_FilesToCommit")); // NOI18N

        filesPanel.setPreferredSize(new java.awt.Dimension(240, 108));

        org.jdesktop.layout.GroupLayout filesPanelLayout = new org.jdesktop.layout.GroupLayout(filesPanel);
        filesPanel.setLayout(filesPanelLayout);
        filesPanelLayout.setHorizontalGroup(
            filesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 630, Short.MAX_VALUE)
        );
        filesPanelLayout.setVerticalGroup(
            filesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 187, Short.MAX_VALUE)
        );

        recentLink.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/mercurial/resources/icons/recent_messages.png")));
        recentLink.setToolTipText(org.openide.util.NbBundle.getMessage(CommitPanel.class, "CTL_CommitForm_RecentMessages")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, filesPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 530, Short.MAX_VALUE)
                        .add(recentLink))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, filesLabel)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(recentLink))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(15, 15, 15)
                .add(filesLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(filesPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel2)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    ListenersSupport listenerSupport = new ListenersSupport(this);
    public void addVersioningListener(VersioningListener listener) {
        listenerSupport.addListener(listener);
    }

    public void removeVersioningListener(VersioningListener listener) {
        listenerSupport.removeListener(listener);
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    final javax.swing.JLabel filesLabel = new javax.swing.JLabel();
    final javax.swing.JPanel filesPanel = new javax.swing.JPanel();
    final javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
    final javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
    final javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
    final javax.swing.JTextArea messageTextArea = new javax.swing.JTextArea();
    final javax.swing.JLabel recentLink = new javax.swing.JLabel();
    // End of variables declaration//GEN-END:variables
    
}
