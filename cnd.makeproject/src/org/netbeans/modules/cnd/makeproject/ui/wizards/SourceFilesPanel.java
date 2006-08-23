/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.

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

package org.netbeans.modules.cnd.makeproject.ui.wizards;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import org.netbeans.modules.cnd.api.utils.AllSourceFileFilter;
import org.netbeans.modules.cnd.api.utils.CCSourceFileFilter;
import org.netbeans.modules.cnd.api.utils.CSourceFileFilter;
import org.netbeans.modules.cnd.api.utils.FileChooser;
import org.netbeans.modules.cnd.api.utils.FortranSourceFileFilter;
import org.netbeans.modules.cnd.api.utils.HeaderSourceFileFilter;
import org.netbeans.modules.cnd.api.utils.IpeUtils;
import org.netbeans.modules.cnd.makeproject.MakeOptions;

public class SourceFilesPanel extends javax.swing.JPanel {
    private Vector data = new Vector();
    private SourceFileTable sourceFileTable = null;
    
    private String baseDir;
    private String wd;
    
    /** Creates new form SourceFilesPanel */
    public SourceFilesPanel() {
        initComponents();
        scrollPane.getViewport().setBackground(java.awt.Color.WHITE);
        
        // File type filters
        filterComboBox.removeAllItems();
        filterComboBox.addItem(CSourceFileFilter.getInstance());
        filterComboBox.addItem(CCSourceFileFilter.getInstance());
        filterComboBox.addItem(HeaderSourceFileFilter.getInstance());
        if (MakeOptions.getInstance().getFortran())
            filterComboBox.addItem(FortranSourceFileFilter.getInstance());
        filterComboBox.addItem(AllSourceFileFilter.getInstance());
        filterComboBox.setSelectedItem(AllSourceFileFilter.getInstance());
        
        refresh();
    }
    
    public void setSeed(String baseDir, String wd) {
        this.baseDir = baseDir;
        this.wd = wd;
    }
    
    public Vector getListData() {
        FolderEntry.setFileFilter((FileFilter)filterComboBox.getSelectedItem());
        return data;
    }
    
    private class TargetSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting())
                return;
            validateSelection();
        }
    }
    
    private void validateSelection() {
        addButton.setEnabled(true);
        if (data.size() == 0 || sourceFileTable.getSelectedRow() < 0)
            deleteButton.setEnabled(false);
        else
            deleteButton.setEnabled(true);
    }
    
    private void refresh() {
        scrollPane.setViewportView(sourceFileTable = new SourceFileTable()); // FIXUP: how to refresh ??
        sourceFilesLabel.setLabelFor(sourceFileTable);
        validateSelection();
    }
    
    class SourceFileTable extends JTable {
        public SourceFileTable() {
            //setTableHeader(null); // Hides table headers
            setModel(new MyTableModel());
            // Left align table header
            ((DefaultTableCellRenderer)getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
            getColumnModel().getColumn(0).setPreferredWidth(95);
            getColumnModel().getColumn(0).setMaxWidth(95);
            getColumnModel().getColumn(0).setResizable(false);
            
            getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            getSelectionModel().addListSelectionListener(new TargetSelectionListener());
        }
        
        public boolean getShowHorizontalLines() {
            return false;
        }
        
        public boolean getShowVerticalLines() {
            return false;
        }
        
        public TableCellRenderer getCellRenderer(int row, int column) {
            return new MyTableCellRenderer();
        }
        
        public TableCellEditor getCellEditor(int row, int col) {
            JCheckBox checkBox = new JCheckBox();
            return new DefaultCellEditor(checkBox);
        }
        
        public void setValueAt(Object value, int row, int col) {
            if (col == 0) {
                FolderEntry fileEntry = (FolderEntry)data.elementAt(row);
                fileEntry.setAddSubfoldersSelected(!fileEntry.isAddSubfoldersSelected());
            }
        }
    }
    
    class MyTableModel extends DefaultTableModel {
        public String getColumnName(int col) {
            if (col == 0)
                return " Add subfolders";
            else
                return " Source File Folder";
        }
        
        public int getColumnCount() {
            return 2;
        }
        
        public int getRowCount() {
            return data.size();
        }
        
        public Object getValueAt(int row, int col) {
            if (col == 0) {
                return data.elementAt(row);
            } else {
                return ((FolderEntry)data.elementAt(row)).getFolderName();
            }
        }
        
        public boolean isCellEditable(int row, int col) {
            if (col == 0)
                return true;
            else
                return false;
        }
    }
    
    class MyTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int col) {
            if (col == 0) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setBackground(Color.WHITE);
                checkBox.setSelected(((FolderEntry)data.elementAt(row)).isAddSubfoldersSelected());
                //checkBox.setText(((FileEntry)data.elementAt(row)).getFile().getPath());
                return checkBox;
            } else
                return super.getTableCellRendererComponent(table, color, isSelected, hasFocus, row, col);
        }
    }
    
//    public class FolderEntry {
//        private File file;
//        private String folderName;
//        private boolean addSubfolders;
//        private FileFilter fileFilter;
//        
//        public FolderEntry(File file, String folderName) {
//            this.file = file;
//            this.folderName = folderName;
//            addSubfolders = true;
//        }
//        
//        public String getFolderName() {
//            return folderName;
//        }
//        
//        public void setFolderName(String file) {
//            this.folderName = file;
//        }
//        
//        public boolean isAddSubfoldersSelected() {
//            return addSubfolders;
//        }
//        
//        public void setAddSubfoldersSelected(boolean selected) {
//            this.addSubfolders = selected;
//        }
//
//        public File getFile() {
//            return file;
//        }
//
//        public void setFile(File file) {
//            this.file = file;
//        }
//
//        public FileFilter getFileFilter() {
//            return fileFilter;
//        }
//
//        public void setFileFilter(FileFilter fileFilter) {
//            this.fileFilter = fileFilter;
//        }
//    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        sourceFilesLabel = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        list = new javax.swing.JList();
        buttonPanel = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        filterText = new javax.swing.JLabel();
        filterComboBox = new javax.swing.JComboBox();

        setLayout(new java.awt.GridBagLayout());

        sourceFilesLabel.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("SourceFileFoldersMN").charAt(0));
        sourceFilesLabel.setText(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("SourceFileFoldersLbl"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(sourceFilesLabel, gridBagConstraints);

        list.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        scrollPane.setViewportView(list);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(scrollPane, gridBagConstraints);

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        addButton.setMnemonic(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("AddButtonMN").charAt(0));
        addButton.setText(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("AddButtonTxt"));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        buttonPanel.add(addButton, gridBagConstraints);

        deleteButton.setMnemonic(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("DeleteButtonTxt").charAt(0));
        deleteButton.setText(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("DeleteButtonTxt"));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        buttonPanel.add(deleteButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        add(buttonPanel, gridBagConstraints);

        filterText.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("FileTypesFilterMN").charAt(0));
        filterText.setLabelFor(filterComboBox);
        filterText.setText(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("FileTypesFilterLBL"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(filterText, gridBagConstraints);

        filterComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(filterComboBox, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents
    
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int index = sourceFileTable.getSelectedRow();
        if (index < 0 || index >= data.size())
            return;
        data.remove(index);
        refresh();
        if (data.size() > 0) {
            if (data.size() > index)
                sourceFileTable.getSelectionModel().setSelectionInterval(index, index);
            else
                sourceFileTable.getSelectionModel().setSelectionInterval(index-1, index-1);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed
    
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        String seed = null;
        if (FileChooser.getCurrectChooserFile() != null) {
            seed = FileChooser.getCurrectChooserFile().getPath();
        }
        if (seed == null) {
            if (wd != null && wd.length() > 0 && !IpeUtils.isPathAbsolute(wd))
                seed = baseDir + File.separator + wd;
            else if (wd != null)
                seed = wd;
            else
                seed = baseDir;
        }
        FileChooser fileChooser = new FileChooser("Select Source File Folder", "Select", FileChooser.DIRECTORIES_ONLY, null, seed, true);
        int ret = fileChooser.showOpenDialog(this);
        if (ret == FileChooser.CANCEL_OPTION)
            return;
        data.add(new FolderEntry(fileChooser.getSelectedFile(), IpeUtils.toAbsoluteOrRelativePath(baseDir, fileChooser.getSelectedFile().getPath())));
        refresh();
    }//GEN-LAST:event_addButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JComboBox filterComboBox;
    private javax.swing.JLabel filterText;
    private javax.swing.JList list;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JLabel sourceFilesLabel;
    // End of variables declaration//GEN-END:variables
    
}
