/*
 * VcsAdvancedOptionsPanel.java
 *
 * Created on June 13, 2007, 3:21 PM
 */

package org.netbeans.modules.versioning.util;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;

/**
 *
 * @author  pbuzek
 */
public class VcsAdvancedOptionsPanel extends javax.swing.JPanel {
    
    /** Creates new form VcsAdvancedOptionsPanel */
    public VcsAdvancedOptionsPanel() {
        initComponents();
    }
    
    public void addPanel(String name, JComponent component) {
        ((DefaultListModel)versioningSystemsList.getModel()).addElement(name);
        containerPanel.add(name, component);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        versioningSystemsList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        containerPanel = new javax.swing.JPanel();

        versioningSystemsList.setModel(new DefaultListModel());
        versioningSystemsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        versioningSystemsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                versioningSystemsListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(versioningSystemsList);

        jLabel1.setText(org.openide.util.NbBundle.getBundle(VcsAdvancedOptionsPanel.class).getString("LBL_VersioningSystems")); // NOI18N

        containerPanel.setLayout(new java.awt.CardLayout());

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 104, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(containerPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(containerPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void versioningSystemsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_versioningSystemsListValueChanged
    ((CardLayout) containerPanel.getLayout()).show(
            containerPanel, (String) versioningSystemsList.getSelectedValue());
}//GEN-LAST:event_versioningSystemsListValueChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel containerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList versioningSystemsList;
    // End of variables declaration//GEN-END:variables
    
}
