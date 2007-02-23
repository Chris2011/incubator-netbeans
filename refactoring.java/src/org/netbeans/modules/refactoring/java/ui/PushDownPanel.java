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
package org.netbeans.modules.refactoring.java.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import org.netbeans.api.java.source.TreePathHandle;
import org.netbeans.modules.refactoring.java.api.PushDownRefactoring;
import org.netbeans.modules.refactoring.spi.ui.CustomRefactoringPanel;
import org.openide.util.NbBundle;


/**
 *
 * @author  Pavel Flaska
 */
public class PushDownPanel extends JPanel implements CustomRefactoringPanel {
    
    // helper constants describing columns in the table of members
    private static final String[] COLUMN_NAMES = {"LBL_PullUp_Selected", "LBL_PullUp_Member", "LBL_PushDown_KeepAbstract"}; // NOI18N
    private static final Class[] COLUMN_CLASSES = {Boolean.class, TreePathHandle.class, Boolean.class};
    
    // refactoring this panel provides parameters for
    private final PushDownRefactoring refactoring;
    // table model for the table of members
    private final TableModel tableModel;
    // pre-selected members (comes from the refactoring action - the elements
    // that should be pre-selected in the table of members)
    private Set selectedMembers;
    // target type to move the members to
    private TreePathHandle originalType;
    // data for the members table (first dimension - rows, second dimension - columns)
    // the columns are: 0 = Selected (true/false), 1 = Member (Java element), 2 = Make Abstract (true/false)
    private Object[][] members = new Object[0][0];
    
    /** Creates new form PushDownPanel
     * @param refactoring The refactoring this panel provides parameters for.
     * @param selectedMembers Members that should be pre-selected in the panel
     *      (determined by which nodes the action was invoked on - e.g. if it was
     *      invoked on a method, the method will be pre-selected to be pulled up)
     */
    public PushDownPanel(PushDownRefactoring refactoring, Set selectedMembers) {
        this.refactoring = refactoring;
        this.tableModel = new TableModel();
        this.selectedMembers = selectedMembers;
        initComponents();
        setPreferredSize(new Dimension(420, 380));
    }
    
    public void initialize() {
        // *** initialize table
//        List l = new ArrayList();
//        l.addAll(refactoring.getSourceType().getFeatures());
//        l.addAll(refactoring.getSourceType().getInterfaceNames());
//        Object[][] allMembers = new Object[l.size()][3];
//        int i = 0;
//        for (Iterator it = l.iterator(); it.hasNext(); ) {
//            Object o = it.next();
//            if (o instanceof JavaClass || o instanceof Method || o instanceof Field || o instanceof MultipartId) {
//                allMembers[i][0] = selectedMembers.contains(o) ? Boolean.TRUE : Boolean.FALSE;
//                allMembers[i][1] = o;
//                allMembers[i][2] = o instanceof Method ? Boolean.FALSE : null;
//                i++;
//            }
//        }
//        members = new Object[i][3];
//        if (i > 0)
//            System.arraycopy(allMembers, 0, members, 0, i);
//        
//        // set renderer for the second column ("Member") do display name of the feature
//        membersTable.setDefaultRenderer(COLUMN_CLASSES[1], new UIUtilities.JavaElementTableCellRenderer() {
//            // override the extractText method to add "implements " prefix to the text
//            // in case the value is instance of MultipartId (i.e. it represents an interface
//            // name from implements clause)
//            protected String extractText(Object value) {
//                String displayValue = super.extractText(value);
//                if (value instanceof MultipartId) {
//                    displayValue = "implements " + displayValue; // NOI18N
//                }
//                return displayValue;
//            }
//        });
//        // send renderer for the third column ("Make Abstract") to make the checkbox:
//        // 1. hidden for elements that are not methods
//        // 2. be disabled for static methods
//        // 3. be disabled and checked for methods if the target type is an interface
//        membersTable.getColumnModel().getColumn(2).setCellRenderer(new UIUtilities.BooleanTableCellRenderer() {
//            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                // make the checkbox checked (even if "Make Abstract" is not set)
//                // for non-static methods if the target type is an interface
//                Object object = table.getModel().getValueAt(row, 1);
//                if (object instanceof Method) {
//                    if (refactoring.getSourceType().isInterface() && !Modifier.isStatic(((Method) object).getModifiers())) {
//                        value = Boolean.TRUE;
//                    }
//                }
//                // the super method automatically makes sure the checkbox is not visible if the
//                // "Make Abstract" value is null (which holds for non-methods)
//                // and that the checkbox is disabled if the cell is not editable (which holds for
//                // static methods all the time and for all methods in case the target type is an interface
//                // - see the table model)
//                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            }
//        });
//        // set background color of the scroll pane to be the same as the background
//        // of the table
//        membersScrollPane.setBackground(membersTable.getBackground());
//        membersScrollPane.getViewport().setBackground(membersTable.getBackground());
//        // set default row height
//        membersTable.setRowHeight(18);
//        // set grid color to be consistent with other netbeans tables
//        if (UIManager.getColor("control") != null) { // NOI18N
//            membersTable.setGridColor(UIManager.getColor("control")); // NOI18N
//        }
//        // compute and set the preferred width for the first and the third column
//        UIUtilities.initColumnWidth(membersTable, 0, Boolean.TRUE, 4);
//        UIUtilities.initColumnWidth(membersTable, 2, Boolean.TRUE, 4);
    }
    
    public PushDownRefactoring.MemberInfo[] getMembers() {
//        List list = new ArrayList();
//        // go through all rows of a table and collect selected members
//        for (int i = 0; i < members.length; i++) {
//            // if the current row is selected, create MemberInfo for it and
//            // add it to the list of selected members
//            if (members[i][0].equals(Boolean.TRUE)) {
//                Object element = members[i][1];
//                Object member;
//                if (element instanceof Field) {
//                    member = new PushDownRefactoring.MemberInfo((Field) element);
//                } else if (element instanceof JavaClass) {
//                    member = new PushDownRefactoring.MemberInfo((JavaClass) element);
//                } else if (element instanceof MultipartId) {
//                    member = new PushDownRefactoring.MemberInfo((MultipartId) element);
//                } else {
//                    // for methods the makeAbstract is always set to true if the
//                    // target type is an interface
//                    member = new PushDownRefactoring.MemberInfo((Method) element, ((Boolean) members[i][2]).booleanValue());
//                }
//                list.add(member);
//            }
//        }
//        // return the array of selected members
//        return (PushDownRefactoring.MemberInfo[]) list.toArray(new PushDownRefactoring.MemberInfo[list.size()]);
        return new PushDownRefactoring.MemberInfo[0];
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        membersScrollPane = new javax.swing.JScrollPane();
        membersTable = new javax.swing.JTable();
        chooseLabel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        setName(org.openide.util.NbBundle.getMessage(PushDownPanel.class, "LBL_PushDown_Header", new Object[] {UIUtilities.getDisplayText(refactoring.getSourceType())}) /* NOI18N */);
        membersScrollPane.setToolTipText("");
        membersTable.setModel(tableModel);
        membersScrollPane.setViewportView(membersTable);
        membersTable.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(PushDownPanel.class, "ACSD_MembersToPushDown"));
        membersTable.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(PushDownPanel.class, "ACSD_MembersToPushDownDescription"));

        add(membersScrollPane, java.awt.BorderLayout.CENTER);

        chooseLabel.setLabelFor(membersTable);
        org.openide.awt.Mnemonics.setLocalizedText(chooseLabel, org.openide.util.NbBundle.getMessage(PushDownPanel.class, "LBL_PushDownLabel"));
        add(chooseLabel, java.awt.BorderLayout.NORTH);

    }
    // </editor-fold>//GEN-END:initComponents
//GEN-FIRST:event_jComboBox1ActionPerformed
//GEN-LAST:event_jComboBox1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel chooseLabel;
    private javax.swing.JScrollPane membersScrollPane;
    private javax.swing.JTable membersTable;
    // End of variables declaration//GEN-END:variables
    private class TableModel extends AbstractTableModel {
        
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        public String getColumnName(int column) {
            return UIUtilities.getColumnName(NbBundle.getMessage(PushDownPanel.class, COLUMN_NAMES[column]));
        }

        public Class getColumnClass(int columnIndex) {
            return COLUMN_CLASSES[columnIndex];
        }

        public int getRowCount() {
            return members.length;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return members[rowIndex][columnIndex];
        }

        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            members[rowIndex][columnIndex] = value;
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex == 2) {
                // column 2 is editable only in case of non-static methods
                // if the target type is not an interface
                if (members[rowIndex][2] == null) {
                    return false;
                }
                Object element = members[rowIndex][1];
                //TODO:
                //                return !refactoring.getSourceType().isInterface() && !Modifier.isStatic(((Method) element).getModifiers());
                return false;
            } else {
                // column 0 is always editable, column 1 is never editable
                return columnIndex == 0;
            }
        }
    }

    public Component getComponent() {
        return this;
    }
}
