/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.search;

import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
import org.netbeans.api.search.RegexpUtil;
import org.netbeans.api.search.SearchScopeOptions;
import org.openide.awt.Mnemonics;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;


/**
 *
 * @author jhavlin
 */
public final class IgnoreListPanel extends javax.swing.JPanel {

    static final String SIMPLE_PREFIX = "s: ";                          //NOI18N
    static final String REGEXP_PREFIX = "x: ";                          //NOI18N
    static final String FILE_PREFIX = "f: ";                            //NOI18N
    private IgnoredListModel ignoreListModel;
    private JFileChooser jFileChooser;

    /**
     * Creates new form IgnoreListPanel
     */
    public IgnoreListPanel() {
        ignoreListModel = new IgnoredListModel();
        initComponents();
        setMnemonics();
        updateEnabledButtons();
    }

    private void updateEnabledButtons() {
        int cnt = list.getSelectedIndices().length;
        btnDelete.setEnabled(cnt > 0);

        boolean editable = false;
        if (cnt == 1 && list.getSelectedValue() != null) {
            String value = list.getSelectedValue().toString();
            if (value.startsWith(SIMPLE_PREFIX)
                    || value.startsWith(REGEXP_PREFIX)) {
                editable = true;
            }
        }
        btnEdit.setEnabled(editable);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBrowse = new javax.swing.JButton();
        btnPattern = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        list = new javax.swing.JList();

        btnBrowse.setText("Add Folder...");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        btnPattern.setText("Add Path Pattern....");
        btnPattern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPatternActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        list.setModel(ignoreListModel);
        list.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(list);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPattern, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBrowse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPattern)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(btnClose)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void listValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listValueChanged
        updateEnabledButtons();
    }//GEN-LAST:event_listValueChanged

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        for (Object o : list.getSelectedValues()) {
            ignoreListModel.remove(o);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        Window w = (Window) SwingUtilities.getAncestorOfClass(
                Window.class, this);
        if (w != null) {
            w.dispose();
        }
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        if (jFileChooser == null) {
            jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(
                    JFileChooser.FILES_AND_DIRECTORIES);
            jFileChooser.setMultiSelectionEnabled(true);
        }
        int showOpenDialog = jFileChooser.showOpenDialog(list);
        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
            File[] selected = jFileChooser.getSelectedFiles();
            if (selected != null) {
                for (File f : selected) {
                    ignoreListModel.addFile(f);
                }
            }
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnPatternActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPatternActionPerformed

        PatternSandbox.openDialog(new PatternSandbox.PathPatternComposer(
                "", false) {                                            //NOI18N

            @Override
            protected void onApply(String pattern, boolean regexp) {
                ignoreListModel.addPattern(pattern, regexp);
            }
        }, btnPattern);
    }//GEN-LAST:event_btnPatternActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        final String val = list.getSelectedValue().toString();
        boolean regex = val.startsWith(REGEXP_PREFIX);
        PatternSandbox.openDialog(new PatternSandbox.PathPatternComposer(
                val.substring(REGEXP_PREFIX.length()), regex) {

            @Override
            protected void onApply(String pattern, boolean regexp) {
                ignoreListModel.remove(val);
                ignoreListModel.addPattern(pattern, regexp);
            }
        }, btnEdit);
    }//GEN-LAST:event_btnEditActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPattern;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList list;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        IgnoreListPanel ilp = new IgnoreListPanel();
        jf.add(ilp);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    private void setMnemonics() {
        setMnem(btnBrowse, "IgnoreListPanel.btnBrowse.text");           //NOI18N
        setMnem(btnDelete, "IgnoreListPanel.btnDelete.text");           //NOI18N
        setMnem(btnEdit, "IgnoreListPanel.btnEdit.text");               //NOI18N
        setMnem(btnClose, "IgnoreListPanel.btnClose.text");             //NOI18N
        setMnem(btnPattern, "IgnoreListPanel.btnPattern.text");         //NOI18N
    }

    private static void setMnem(AbstractButton button, String key) {
        Mnemonics.setLocalizedText(button, getText(key));
    }

    private static String getText(String key) {
        return NbBundle.getMessage(IgnoreListPanel.class, key);
    }

    class IgnoredListModel extends AbstractListModel {

        List<String> list;

        public void remove(Object o) {
            int index = list.indexOf(o);
            list.remove(index);
            this.fireIntervalRemoved(this, index, index + 1);
            persist();
        }

        public void addFile(File f) {
            list.add(FILE_PREFIX + f.getAbsolutePath());
            fireIntervalAdded(this, list.size() - 1, list.size());
            persist();
        }

        public void addPattern(String p, boolean regexp) {
            if (regexp) {
                addRegularExpression(p);
            } else {
                addSimplePatter(p);
            }
        }

        public void addSimplePatter(String p) {
            list.add(SIMPLE_PREFIX + p);
            fireIntervalAdded(this, list.size() - 1, list.size());
            persist();
        }

        public void addRegularExpression(String x) {
            list.add(REGEXP_PREFIX + x);
            fireIntervalAdded(this, list.size() - 1, list.size());
            persist();
        }

        public IgnoredListModel() {
            List<String> orig = FindDialogMemory.getDefault().getIgnoreList();
            list = new ArrayList<String>(orig.size());
            list.addAll(orig);
        }

        @Override
        public int getSize() {
            return list.size();
        }

        @Override
        public String getElementAt(int index) {
            return list.get(index);
        }

        public void persist() {
            List<String> copy = new ArrayList<String>(list.size());
            copy.addAll(list);
            FindDialogMemory.getDefault().setIgnoreList(copy);
        }
    }

    public static void openDialog(JComponent baseComponent) {

        JDialog jd = new JDialog(
                (JDialog) SwingUtilities.getAncestorOfClass(
                JDialog.class, baseComponent));

        IgnoreListPanel ilp = new IgnoreListPanel();
        jd.add(ilp);
        jd.setModal(true);
        jd.setLocationRelativeTo(baseComponent);
        jd.getRootPane().setDefaultButton(ilp.btnClose);
        jd.pack();
        jd.setTitle(getText("IgnoreListPanel.title"));                  //NOI18N
        jd.setVisible(true);
    }

    static class IgnoreListManager {

        List<IgnoredItemDefinition> items;

        public IgnoreListManager(List<String> ignoreList) {
            items = new LinkedList<IgnoredItemDefinition>();
            for (String s : ignoreList) {
                try {
                    if (s.startsWith(SIMPLE_PREFIX)) {
                        String p = s.substring(SIMPLE_PREFIX.length());
                        items.add(new IgnoredPatternDefinition(p));
                    } else if (s.startsWith(REGEXP_PREFIX)) {
                        String x = s.substring(REGEXP_PREFIX.length());
                        items.add(new IgnoredRegexpDefinition(x));
                    } else if (s.startsWith(FILE_PREFIX)) {
                        String f = s.substring(FILE_PREFIX.length());
                        items.add(new IgnoredDirDefinition(f));
                    }
                } catch (Exception e) {
                    Exceptions.printStackTrace(e);
                }
            }
        }

        boolean isIgnored(FileObject fo) {
            for (IgnoredItemDefinition iid : items) {
                if (iid.isIgnored(fo)) {
                    return true;
                }
            }
            return false;
        }

        private abstract class IgnoredItemDefinition {

            abstract boolean isIgnored(FileObject obj);
        }

        private class IgnoredPatternDefinition extends IgnoredItemDefinition {

            private Pattern p;

            public IgnoredPatternDefinition(String pattern) {
                p = RegexpUtil.makeFileNamePattern(
                        SearchScopeOptions.create(pattern, false));
            }

            @Override
            boolean isIgnored(FileObject obj) {
                return p.matcher(obj.getNameExt()).matches();
            }
        }

        private class IgnoredRegexpDefinition extends IgnoredItemDefinition {

            private Pattern p;

            public IgnoredRegexpDefinition(String pattern) {
                this.p = Pattern.compile(pattern);
            }

            @Override
            boolean isIgnored(FileObject obj) {
                return p.matcher(obj.getPath()).find();
            }
        }

        private class IgnoredDirDefinition extends IgnoredItemDefinition {

            FileObject dir;

            public IgnoredDirDefinition(String path) {
                dir = FileUtil.toFileObject(new File(path));
            }

            @Override
            boolean isIgnored(FileObject obj) {
                return FileUtil.isParentOf(dir, obj);
            }
        }
    }
}