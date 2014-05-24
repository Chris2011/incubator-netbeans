/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 */
package org.netbeans.modules.javascript2.requirejs.ui;

import org.netbeans.modules.javascript2.requirejs.StringUtils;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.netbeans.api.project.Project;
import org.netbeans.modules.javascript2.requirejs.RequireJsPreferences;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 *
 * @author Petr Pisl
 */
final class RequireJsPanel extends JPanel implements HelpCtx.Provider {

    static final String DEFAULT_LOCAL_PATH = ""; // NOI18N
    static final int COLUMN_MAPPING_PATH = 0;
    static final int COLUMN_LOCAL_PATH = 1;

    private final Project project;
    private final ProjectCustomizer.Category category;
    private final PathMappingTableModel pathMappingTableModel;

    /**
     * Creates new form RequireJsPanel
     */
    public RequireJsPanel(ProjectCustomizer.Category category, Project project) {
        this.project = project;
        this.category = category;

        this.category.setStoreListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        initComponents();
        String[] columnNames = {
            NbBundle.getMessage(RequireJsPanel.class, "LBL_Mapping"),
            NbBundle.getMessage(RequireJsPanel.class, "LBL_LocalPath"),};
        pathMappingTableModel = new PathMappingTableModel(columnNames, getPathMappingsData());    
        init();
    }

    public boolean isSupportEnabled() {
        return enabledCheckBox.isSelected();
    }

    public void setSupportEnabled(boolean enabled) {
        enabledCheckBox.setSelected(enabled);
    }

    private void init() {
        setSupportEnabled(RequireJsPreferences.getBoolean(project, RequireJsPreferences.ENABLED));

        pathMappingTable.setModel(pathMappingTableModel);
        pathMappingTable.setDefaultRenderer(LocalPathCell.class, new LocalPathCellRenderer());
        pathMappingTable.addMouseListener(new LocalPathCellMouseListener(pathMappingTable));
        pathMappingTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                handleButtonStates();
//                validateFields();
            }
        });
        pathMappingTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                handleButtonStates();
            }
        });
    }

    private void saveData() {
        RequireJsPreferences.putBoolean(project, RequireJsPreferences.ENABLED, isSupportEnabled());
        RequireJsPreferences.storeMappings(project, getPathMappings());
    }

    void handleButtonStates() {
        removePathMappingButton.setEnabled(isTableRowSelected());
        newPathMappingButton.setEnabled(pathMappingTableModel.isLastServerPathFilled());
    }
    
    private boolean isTableRowSelected() {
        return getTableSelectedRow() != -1;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        enabledCheckBox = new javax.swing.JCheckBox();
        enabledInfoLabel = new javax.swing.JLabel();
        pathMappingLabel = new javax.swing.JLabel();
        pathMappingScrollPane = new javax.swing.JScrollPane();
        pathMappingTable = new javax.swing.JTable();
        newPathMappingButton = new javax.swing.JButton();
        removePathMappingButton = new javax.swing.JButton();
        pathMappingInfoLabel = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(enabledCheckBox, org.openide.util.NbBundle.getMessage(RequireJsPanel.class, "RequireJsPanel.enabledCheckBox.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(enabledInfoLabel, org.openide.util.NbBundle.getMessage(RequireJsPanel.class, "RequireJsPanel.enabledInfoLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(pathMappingLabel, org.openide.util.NbBundle.getMessage(RequireJsPanel.class, "RequireJsPanel.pathMappingLabel.text")); // NOI18N

        pathMappingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        pathMappingScrollPane.setViewportView(pathMappingTable);

        org.openide.awt.Mnemonics.setLocalizedText(newPathMappingButton, org.openide.util.NbBundle.getMessage(RequireJsPanel.class, "RequireJsPanel.newPathMappingButton.text")); // NOI18N
        newPathMappingButton.setEnabled(false);
        newPathMappingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPathMappingButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(removePathMappingButton, org.openide.util.NbBundle.getMessage(RequireJsPanel.class, "RequireJsPanel.removePathMappingButton.text")); // NOI18N
        removePathMappingButton.setEnabled(false);
        removePathMappingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removePathMappingButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(pathMappingInfoLabel, org.openide.util.NbBundle.getMessage(RequireJsPanel.class, "RequireJsPanel.pathMappingInfoLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(enabledCheckBox)
                    .addComponent(enabledInfoLabel)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pathMappingScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(removePathMappingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(newPathMappingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(pathMappingLabel)
                    .addComponent(pathMappingInfoLabel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(enabledCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enabledInfoLabel)
                .addGap(18, 18, 18)
                .addComponent(pathMappingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(newPathMappingButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removePathMappingButton)
                        .addGap(0, 167, Short.MAX_VALUE))
                    .addComponent(pathMappingScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pathMappingInfoLabel)
                .addGap(155, 155, 155))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void newPathMappingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPathMappingButtonActionPerformed
        pathMappingTableModel.addRow(new Object[]{null, new LocalPathCell(DEFAULT_LOCAL_PATH)});
    }//GEN-LAST:event_newPathMappingButtonActionPerformed

    private void removePathMappingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removePathMappingButtonActionPerformed
        assert getTableSelectedRow() != -1 : "A table row must be selected";
        while (getTableSelectedRow() != -1) {
            pathMappingTableModel.removeRow(getTableSelectedRow());
        }
        if (pathMappingTableModel.getRowCount() == 0) {
            newPathMappingButtonActionPerformed(null);
        }
    }//GEN-LAST:event_removePathMappingButtonActionPerformed

    private int getTableSelectedRow() {
        return pathMappingTable.getSelectedRow();
    }

    private Object[][] getPathMappingsData() {
        Map<String, String> mappings = RequireJsPreferences.getMappings(project);
        int mappingSize = mappings.size();
        Object[][] paths = new Object[mappingSize + 1][2];
        int index = 0;
        for (Map.Entry<String, String> mapping : mappings.entrySet()) {
            paths[index][COLUMN_MAPPING_PATH] = mapping.getKey();
            paths[index][COLUMN_LOCAL_PATH] = new LocalPathCell(mapping.getValue());
            index++;
        }
        paths[mappingSize][COLUMN_MAPPING_PATH] = null;
        paths[mappingSize][COLUMN_LOCAL_PATH] = new LocalPathCell(DEFAULT_LOCAL_PATH);
        return paths;
    }
    
    private Map<String, String> getPathMappings() {
        Map<String, String> mappings = new HashMap(pathMappingTableModel.getRowCount());
        for (int i = 0; i < pathMappingTableModel.getRowCount() - 1; ++i) {
            String mapping = (String) pathMappingTableModel.getValueAt(i, COLUMN_MAPPING_PATH);
            String localPath = ((LocalPathCell) pathMappingTableModel.getValueAt(i, COLUMN_LOCAL_PATH)).getPath();
            mappings.put(mapping, localPath);
        }
        return mappings;
    }

    private static final class PathMappingTableModel extends DefaultTableModel {

        private static final long serialVersionUID = 169356031075115831L;

        public PathMappingTableModel(String[] columnNames, Object[][] data) {
            super(data, columnNames);
        }

        public boolean isLastServerPathFilled() {
            int rowCount = getRowCount();
            if (rowCount == 0) {
                return true;
            }
            return StringUtils.hasText((String) getValueAt(rowCount - 1, COLUMN_MAPPING_PATH));
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == COLUMN_LOCAL_PATH) {
                return LocalPathCell.class;
            } else if (columnIndex == COLUMN_MAPPING_PATH) {
                return String.class;
            }
            throw new IllegalStateException("Unhandled column index: " + columnIndex);
        }
    }

    private static final class LocalPathCellRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            LocalPathCell localPathCell = (LocalPathCell) value;
            // #164688 - sorry, no idea how this can happen
            if (localPathCell == null) {
                localPathCell = new LocalPathCell(DEFAULT_LOCAL_PATH);
            }
            if (isSelected) {
                localPathCell.setBgColor(table.getSelectionBackground());
                localPathCell.setFgColor(table.getSelectionForeground());
            } else {
                localPathCell.setBgColor(table.getBackground());
                localPathCell.setFgColor(table.getForeground());
            }

            return localPathCell;
        }
    }
    
    private String findRelativePath(FileObject from, FileObject to) {
        String path = FileUtil.getRelativePath(from, to);
        StringBuilder result = new StringBuilder();
        FileObject parent = from.getParent();
        while (path == null && parent != null) {
            result.append("../");
            path = FileUtil.getRelativePath(parent, to);
            parent = parent.getParent();
        }
        if (path != null) {
            result.append(path);
        } else {
            result.append(to.getPath());
        }
        return result.toString();
    }

    private final class LocalPathCellMouseListener extends MouseAdapter {

        private final JTable table;

        public LocalPathCellMouseListener(JTable table) {
            this.table = table;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Point p = new Point(e.getX(), e.getY());
            int row = table.rowAtPoint(p);
            int col = table.columnAtPoint(p);
            Object value = table.getValueAt(row, col);
            if (value instanceof LocalPathCell) {
                Rectangle cellRect = table.getCellRect(row, col, false);
                LocalPathCell localPathCell = (LocalPathCell) value;
                JButton button = localPathCell.getButton();
                if (e.getX() > (cellRect.x + cellRect.width - button.getWidth())) {
                    //inside changeButton
                    FileObject projectDirectory = project.getProjectDirectory();
                    File newLocation = browseAction(".",
                            "Select somethings", false, FileUtil.toFile(projectDirectory));
                    if (newLocation != null) {
                        localPathCell.setPath(findRelativePath(projectDirectory, FileUtil.toFileObject(newLocation)));
                    }
//                    validateFields();
                }
            }
        }

        private File browseAction(String dirKey, String title, boolean filesOnly, File workDir) {
            FileChooserBuilder builder = new FileChooserBuilder(dirKey)
                    .setTitle(title);
            if (workDir != null) {
                builder.setDefaultWorkingDirectory(workDir)
                        .forceUseOfDefaultWorkingDirectory(true);
            }
            if (filesOnly) {
                builder.setFilesOnly(true);
            } 
            File selectedFile = builder.showOpenDialog();
            if (selectedFile != null) {
                return FileUtil.normalizeFile(selectedFile);
            }
            return null;
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox enabledCheckBox;
    private javax.swing.JLabel enabledInfoLabel;
    private javax.swing.JButton newPathMappingButton;
    private javax.swing.JLabel pathMappingInfoLabel;
    private javax.swing.JLabel pathMappingLabel;
    private javax.swing.JScrollPane pathMappingScrollPane;
    private javax.swing.JTable pathMappingTable;
    private javax.swing.JButton removePathMappingButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }
}
