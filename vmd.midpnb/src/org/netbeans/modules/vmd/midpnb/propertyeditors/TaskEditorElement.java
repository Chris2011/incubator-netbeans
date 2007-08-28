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
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */

package org.netbeans.modules.vmd.midpnb.propertyeditors;

import org.netbeans.modules.vmd.api.model.DesignComponent;
import org.netbeans.modules.vmd.api.model.TypeID;
import org.netbeans.modules.vmd.midp.actions.GoToSourceSupport;
import org.netbeans.modules.vmd.midp.propertyeditors.api.resource.element.PropertyEditorResourceElement;
import org.netbeans.modules.vmd.midpnb.components.resources.SimpleCancellableTaskCD;

import javax.swing.*;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Anton Chechel
 */
public class TaskEditorElement extends PropertyEditorResourceElement {

    private WeakReference<DesignComponent> component;

    public TaskEditorElement() {
        initComponents();
    }

    public JComponent getJComponent() {
        return this;
    }

    public TypeID getTypeID() {
        return SimpleCancellableTaskCD.TYPEID;
    }

    public List<String> getPropertyValueNames() {
        return Arrays.asList(SimpleCancellableTaskCD.PROP_CODE);
    }
    
    @Override
    public String getResourceNameSuggestion() {
        return "task"; // NOI18N
    }
    
    public void setDesignComponentWrapper(final DesignComponentWrapper wrapper) {
        boolean enableGoTo = true;
        if (wrapper != null) {
            DesignComponent _component = wrapper.getComponent();
            if (enableGoTo = _component != null) {
                component = new WeakReference<DesignComponent>(_component);
            }
        } else {
            component = null;
        }

        // UI stuff
        taskLabel.setEnabled(wrapper != null);
        gotoButton.setEnabled(wrapper != null && enableGoTo);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        taskLabel = new javax.swing.JLabel();
        gotoButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        taskLabel.setLabelFor(gotoButton);
        org.openide.awt.Mnemonics.setLocalizedText(taskLabel, org.openide.util.NbBundle.getMessage(TaskEditorElement.class, "TaskEditorElement.taskLabel.text")); // NOI18N
        taskLabel.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(gotoButton, org.openide.util.NbBundle.getMessage(TaskEditorElement.class, "TaskEditorElement.gotoButton.text")); // NOI18N
        gotoButton.setEnabled(false);
        gotoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gotoButtonActionPerformed(evt);
            }
        });

        jTextArea1.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jTextArea1.setText(org.openide.util.NbBundle.getMessage(TaskEditorElement.class, "TaskEditorElement.jTextArea1.text")); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(taskLabel)
                    .add(gotoButton))
                .add(43, 43, 43))
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(taskLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gotoButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void gotoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gotoButtonActionPerformed
        if (component != null) {
            DesignComponent _component = component.get();
            if (_component != null) {
                GoToSourceSupport.goToSourceOfComponent(_component);
            }
        }
}//GEN-LAST:event_gotoButtonActionPerformed
    // Variables declaration - do not modify
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton gotoButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel taskLabel;
    // End of variables declaration//GEN-END:variables
}
