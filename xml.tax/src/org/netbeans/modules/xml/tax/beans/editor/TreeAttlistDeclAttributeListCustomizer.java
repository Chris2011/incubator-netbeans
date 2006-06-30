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
package org.netbeans.modules.xml.tax.beans.editor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.AbstractTableModel;
import javax.swing.JTextField;
import javax.swing.DefaultCellEditor;
import java.beans.Customizer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.netbeans.tax.TreeNamedObjectMap;
import org.netbeans.tax.TreeException;
import org.netbeans.tax.TreeAttlistDeclAttributeDef;

import org.netbeans.modules.xml.tax.util.TAXUtil;

/**
 * 
 * @author  Petr Kuzel
 * @author  Vladimir Zboril
 * @version 1.0
 */
public class TreeAttlistDeclAttributeListCustomizer extends JPanel implements Customizer, PropertyChangeListener {

    /** Serial Version UID */
    private static final long serialVersionUID =3524220594991141235L;

    /** */
    private static final String headerTitles[] = {
	Util.THIS.getString ("TEXT_attlistdecl_attributelist_header_Name"),
	Util.THIS.getString ("TEXT_attlistdecl_attributelist_header_Type"),
	Util.THIS.getString ("TEXT_attlistdecl_attributelist_header_Enumerated"),
	Util.THIS.getString ("TEXT_attlistdecl_attributelist_header_Default"),
	Util.THIS.getString ("TEXT_attlistdecl_attributelist_header_Value")
    };
    
    private static final int COL_NAME          = 0;
    private static final int COL_TYPE          = 1;
    private static final int COL_ENUM_TYPE     = 2; 
    private static final int COL_DEFAULT_TYPE  = 3;
    private static final int COL_DEFAULT_VALUE = 4;

    private static final int COL_COUNT         = 5;


    //
    // init
    //

    public TreeAttlistDeclAttributeListCustomizer() {
        initComponents ();
        attrTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Cells should become editable on single mouse click
        final JTextField editorComponent = new JTextField();
        editorComponent.getCaret().setVisible(true);
        final DefaultCellEditor singleClickEditor = new DefaultCellEditor(editorComponent);
        singleClickEditor.setClickCountToStart(1);
        attrTable.setDefaultEditor(String.class, singleClickEditor);
        
        
        initAccessibility();
    }

    
    //
    // itself
    //

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        tableScrollPane = new javax.swing.JScrollPane();
        attrTable = new javax.swing.JTable();

        setLayout(new java.awt.GridBagLayout());

        attrTable.setPreferredScrollableViewportSize(new java.awt.Dimension(250, 150));
        tableScrollPane.setViewportView(attrTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 11);
        add(tableScrollPane, gridBagConstraints);

    }//GEN-END:initComponents
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable attrTable;
    private javax.swing.JScrollPane tableScrollPane;
    // End of variables declaration//GEN-END:variables


    private TreeNamedObjectMap peer;
    private AbstractTableModel tableModel;


    /**
     */
    public void setObject (Object obj) {
        peer = (TreeNamedObjectMap) obj;        
        tableModel = new AttlistTableModel(/*peer*/);
        attrTable.setModel(tableModel);
        //          attrTable.addKeyListener(new RowKeyListener(attrTable)); // DTD is read only now --> it will be ready in future

        /** First table column is row selector. */
        TableColumn column = null;
        for (int i = 0; i < COL_COUNT; i++) {
            column = attrTable.getColumnModel().getColumn (i);
	    column.setPreferredWidth (50);
        }

        updateView (peer);
        peer.addPropertyChangeListener (org.openide.util.WeakListeners.propertyChange (this, peer));
    }


    /** Udate atate accordingly*/
    public void propertyChange(final PropertyChangeEvent e) {
        if (e.getSource() == null)
	    return;
        if (e.getSource() != peer)
	    return;

        updateView ((TreeNamedObjectMap) e.getSource());        
    }

    /** Update visualization accordingly. */
    private void updateView (TreeNamedObjectMap model) {
        tableModel.fireTableDataChanged();
    }


    //
    // class RowKeyListener
    //

    /** deletes whole row by pressing DELETE on row column. */
    private class RowKeyListener extends KeyAdapter {

	/** */
        private JTable table;

	
	//
	// init
	//

        public RowKeyListener (JTable table) {
            this.table = table;
        }

	
	//
	// itself
	//

	/**
	 */
        public void keyReleased (KeyEvent e) {
            //if ( Util.THIS.isLoggable() ) /* then */ Util.THIS.debug("Event: " + e); // NOI18N
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                peer.remove (peer.get (table.getSelectedRow()));
                tableModel.fireTableDataChanged();
            }
        }
    }


    //
    // class AttlistTableModel
    //

    /**
     *
     */
    private class AttlistTableModel extends AbstractTableModel {

        /** Serial Version UID */
        private static final long serialVersionUID =2920999049977694084L;
        
	//
	// init
	//
        
        /** Create a data node for a given data object.
         * The provided children object will be used to hold all child nodes.
         * @param obj object to work with
         * @param ch children container for the node
         */
        public AttlistTableModel () {
            super();
        }

        /** Returns the number of rows in the model */
        public int getRowCount () {
            return peer.size();
        }

        /** Returns the number of columns in the model */
        public int getColumnCount () {
            return COL_COUNT;
        }

        /** Returns the class for a model. */
        public Class getColumnClass (int index) {
            return String.class;
        }

	/**
	 */
        public Object getValueAt (int row, int column) {
            TreeAttlistDeclAttributeDef attr = (TreeAttlistDeclAttributeDef) peer.get(row);
            switch (column) {
            case COL_NAME:
                return attr.getName();
            case COL_TYPE:
                return attr.getTypeName();
            case COL_ENUM_TYPE: 
                return ( (attr.getEnumeratedTypeString() == null) ? "" : attr.getEnumeratedTypeString() ); // NOI18N
            case COL_DEFAULT_TYPE:
                return attr.getDefaultTypeName();
            case COL_DEFAULT_VALUE:
                String defVal = attr.getDefaultValue();
                if ( defVal == null ) {
                    defVal = ""; // NOI18N
                }
                return defVal;
            default:
                return null;
            }
        }

	/**
	 */
        public void setValueAt (Object val, int row, int column) {
            TreeAttlistDeclAttributeDef attr = (TreeAttlistDeclAttributeDef) peer.get (row);
            try {
                if (column == COL_NAME) {
                    attr.setName ((String) val);
                }
            } catch (TreeException exc) {
                TAXUtil.notifyTreeException (exc);
            }
        }

	/**
	 */
        public String getColumnName (int column) {
            return column < getColumnCount() ? headerTitles[column] : "" ; // NOI18N
        }

        /** Returns true for all cells which are editable. For a 
         * a new cell is editable only name field.
         */
        public boolean isCellEditable (int rowIndex, int columnIndex) {
            return false;
        }

    } // end: class AttlistTableModel
    
    
   /** Initialize accesibility
     */
    public void initAccessibility(){
        
       this.getAccessibleContext().setAccessibleDescription(Util.THIS.getString("ACSD_TreeAttlistDeclAttributeListCustomizer"));
       attrTable.getAccessibleContext().setAccessibleDescription(Util.THIS.getString("ACSD_attrTable1")); 
       attrTable.getAccessibleContext().setAccessibleName(Util.THIS.getString("ACSN_attrTable1")); 
    }

}
