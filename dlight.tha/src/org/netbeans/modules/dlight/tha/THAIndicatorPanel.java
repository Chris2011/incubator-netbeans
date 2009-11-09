/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009 Sun Microsystems, Inc. All rights reserved.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */
package org.netbeans.modules.dlight.tha;

import java.awt.Cursor;
import javax.swing.Action;
import org.openide.util.NbBundle;

/**
 *
 * @author mt154047
 */
public class THAIndicatorPanel extends javax.swing.JPanel {

    private final Action deadlocksAction;
    private final Action racesAction;

    /** Creates new form THAIndicatorPanel */
    public THAIndicatorPanel(Action deadlocksAction, Action racesAction) {
        this.deadlocksAction = deadlocksAction;
        this.racesAction = racesAction;
        initComponents();

        // not everything could be set in the form editor
        deadlocksDetails.setVisible(false);
        deadlocksDetails.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        racesDetails.setVisible(false);
        racesDetails.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deadlocksLabel = new javax.swing.JLabel();
        racesLabel = new javax.swing.JLabel();
        deadlocksDetails = new javax.swing.JButton();
        racesDetails = new javax.swing.JButton();

        setOpaque(false);

        deadlocksLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/dlight/tha/resources/deadlock_active16.png"))); // NOI18N
        deadlocksLabel.setText(org.openide.util.NbBundle.getMessage(THAIndicatorPanel.class, "THAControlPanel.deadlocksLabel.nodeadlocks")); // NOI18N
        deadlocksLabel.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/dlight/tha/resources/deadlock_inactive16.png"))); // NOI18N
        deadlocksLabel.setEnabled(false);

        racesLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/dlight/tha/resources/races_active16.png"))); // NOI18N
        racesLabel.setText(org.openide.util.NbBundle.getMessage(THAIndicatorPanel.class, "THAControlPanel.dataracesLabel.nodataraces")); // NOI18N
        racesLabel.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/dlight/tha/resources/races_inactive16.png"))); // NOI18N
        racesLabel.setEnabled(false);

        deadlocksDetails.setAction(deadlocksAction);
        deadlocksDetails.setForeground(java.awt.Color.blue);
        deadlocksDetails.setText(org.openide.util.NbBundle.getMessage(THAIndicatorPanel.class, "THAIndicatorPanel.deadlocksDetails.text")); // NOI18N
        deadlocksDetails.setBorderPainted(false);
        deadlocksDetails.setContentAreaFilled(false);
        deadlocksDetails.setName("deadlocksDetails"); // NOI18N

        racesDetails.setAction(racesAction);
        racesDetails.setForeground(java.awt.Color.blue);
        racesDetails.setText(org.openide.util.NbBundle.getMessage(THAIndicatorPanel.class, "THAIndicatorPanel.racesDetails.text")); // NOI18N
        racesDetails.setBorderPainted(false);
        racesDetails.setContentAreaFilled(false);
        racesDetails.setName("racesDetails"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(racesLabel)
                    .add(deadlocksLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(deadlocksDetails)
                    .add(racesDetails))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(deadlocksLabel)
                    .add(deadlocksDetails))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(racesLabel)
                    .add(racesDetails))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deadlocksDetails;
    private javax.swing.JLabel deadlocksLabel;
    private javax.swing.JButton racesDetails;
    private javax.swing.JLabel racesLabel;
    // End of variables declaration//GEN-END:variables

    void setDeadlocks(int deadlocks) {
        if (0 < deadlocks) {
            deadlocksLabel.setText(deadlocks == 1 ?
                getMessage("THAControlPanel.deadlocksLabel.deadlock", deadlocks) ://NOI18N
                getMessage("THAControlPanel.deadlocksLabel.deadlocks", deadlocks));//NOI18N
            deadlocksLabel.setEnabled(true);
            deadlocksDetails.setVisible(true);
        } else {
            deadlocksLabel.setText(getMessage("THAControlPanel.deadlocksLabel.nodeadlocks"));//NOI18N
            deadlocksLabel.setEnabled(false);
            deadlocksDetails.setVisible(false);
        }
    }

    void setDataRaces(int dataraces) {
        if (0 < dataraces) {
            racesLabel.setText(dataraces == 1 ?
                getMessage("THAControlPanel.dataracesLabel.datarace", dataraces) ://NOI18N
                getMessage("THAControlPanel.dataracesLabel.dataraces", dataraces));//NOI18N
            racesLabel.setEnabled(true);
            racesDetails.setVisible(true);
        } else {
            racesLabel.setText(getMessage("THAControlPanel.dataracesLabel.nodataraces"));//NOI18N
            racesLabel.setEnabled(false);
            racesDetails.setVisible(false);
        }
    }

    void reset() {
        // throw new UnsupportedOperationException("Not yet implemented");
    }

    private static String getMessage(String name, Object... args) {
        return NbBundle.getMessage(THAIndicatorPanel.class, name, args);
    }
}
