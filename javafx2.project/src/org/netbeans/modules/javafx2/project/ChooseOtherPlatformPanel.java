/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.javafx2.project;

import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.java.platform.JavaPlatform;
import org.netbeans.api.java.platform.JavaPlatformManager;
import org.netbeans.api.java.platform.PlatformsCustomizer;
import org.netbeans.api.java.platform.Specification;
import org.netbeans.modules.javafx2.platform.api.JavaFXPlatformUtils;
import org.openide.util.Parameters;

/**
 *
 * @author Tomas Zezula, Petr Somol
 */
public final class ChooseOtherPlatformPanel extends javax.swing.JPanel {

    public ChooseOtherPlatformPanel(@NonNull final String type) {
        Parameters.notNull("type", type);
        initComponents();
        postInit(type);
    }

    final JavaPlatform getSelectedPlatform() {
        return (JavaPlatform) platforms.getSelectedItem();
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
        platforms = new javax.swing.JComboBox();
        managePlatforms = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setLabelFor(platforms);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ChooseOtherPlatformPanel.class, "ChooseOtherPlatformPanel.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        add(jLabel1, gridBagConstraints);

        platforms.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        add(platforms, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(managePlatforms, org.openide.util.NbBundle.getMessage(ChooseOtherPlatformPanel.class, "ChooseOtherPlatformPanel.managePlatforms.text")); // NOI18N
        managePlatforms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                managePlatforms(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        add(managePlatforms, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void managePlatforms(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managePlatforms
        PlatformsCustomizer.showCustomizer(null);
        ((PlatformModel)platforms.getModel()).refresh();
    }//GEN-LAST:event_managePlatforms

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton managePlatforms;
    private javax.swing.JComboBox platforms;
    // End of variables declaration//GEN-END:variables

    private void postInit(@NonNull String type) {
        platforms.setModel(new PlatformModel(type));
        platforms.setRenderer(new PlatformRenderer());
    }

    private static final class PlatformModel extends DefaultComboBoxModel {

        private final String type;

        PlatformModel(@NonNull final String type) {
            this.type = type;
            refresh();
        }

        void refresh() {
            removeAllElements();
            final JavaPlatform[] platforms = JavaPlatformManager.getDefault().getPlatforms(
                    null,
                    new Specification(type, null));
            for (JavaPlatform jp : platforms) {
                if (JavaFXPlatformUtils.isJavaFXEnabled(jp)) {
                    addElement(jp);
                }
            }
        }
    }

    private static class PlatformRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                final JList list,
                Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            if (value instanceof JavaPlatform) {
                value = ((JavaPlatform)value).getDisplayName();
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }

    }
}

