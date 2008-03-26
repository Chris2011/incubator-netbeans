/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2008 Sun Microsystems, Inc. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2008 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 */
package org.netbeans.modules.ruby.debugger.breakpoints.ui;

import java.awt.Dialog;
import java.beans.Customizer;
import java.io.File;
import javax.swing.JPanel;
import org.netbeans.modules.ruby.debugger.EditorUtil;
import org.netbeans.modules.ruby.debugger.breakpoints.RubyBreakpoint;
import org.netbeans.modules.ruby.platform.Util;
import org.netbeans.spi.debugger.ui.Controller;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;

public final class BreakpointCustomizer extends JPanel implements Customizer, Controller {

    private RubyBreakpoint bp;
    
    public BreakpointCustomizer() {
        initComponents();
    }

    public static void customize(RubyBreakpoint bp) {
        BreakpointCustomizer customizer = new BreakpointCustomizer();
        customizer.setObject(bp);
        DialogDescriptor descriptor = new DialogDescriptor(customizer,
                NbBundle.getMessage(BreakpointCustomizer.class, "BreakpointCustomizer.title"));
        Dialog d = DialogDisplayer.getDefault().createDialog(descriptor);
        d.setVisible(true);
        if (descriptor.getValue() == NotifyDescriptor.OK_OPTION) {
            try {
                int line = Integer.valueOf(customizer.lineValue.getText()) - 1; // need 0-based
                String file = customizer.fileValue.getText();
                if (!new File(file).isFile()) {
                    Util.notifyLocalized(BreakpointCustomizer.class, "BreakpointCustomizer.file.not.found", file);
                } else {
                    bp.setLine(EditorUtil.getLine(file, line));
                }
            } catch (NumberFormatException nfe) {
                Util.notifyLocalized(BreakpointCustomizer.class, "BreakpointCustomizer.invalid.number", customizer.lineValue.getText());
            }
        }
    }

    public void setObject(Object bean) {
        if (!(bean instanceof RubyBreakpoint)) {
            throw new IllegalArgumentException(bean.toString());
        }
        RubyBreakpoint bp = (RubyBreakpoint) bean;
        this.bp = bp;
        fileValue.setText(bp.getFilePath());
        lineValue.setText("" + bp.getLineNumber());
    }

    public boolean ok() {
        try {
            int line = Integer.valueOf(lineValue.getText()) - 1; // need 0-based
            String file = fileValue.getText();
            if (!new File(file).isFile()) {
                Util.notifyLocalized(BreakpointCustomizer.class, "BreakpointCustomizer.file.not.found", file);
                return false;
            } else {
                bp.setLine(EditorUtil.getLine(file, line));
                return true;
            }
        } catch (NumberFormatException nfe) {
            Util.notifyLocalized(BreakpointCustomizer.class, "BreakpointCustomizer.invalid.number", lineValue.getText());
            return false;
        }
    }

    public boolean cancel() {
        return true;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileLbl = new javax.swing.JLabel();
        lineLbl = new javax.swing.JLabel();
        fileValue = new javax.swing.JTextField();
        lineValue = new javax.swing.JTextField();

        org.openide.awt.Mnemonics.setLocalizedText(fileLbl, org.openide.util.NbBundle.getMessage(BreakpointCustomizer.class, "BreakpointCustomizer.fileLbl.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lineLbl, org.openide.util.NbBundle.getMessage(BreakpointCustomizer.class, "BreakpointCustomizer.lineLbl.text")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(fileLbl)
                    .add(lineLbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lineValue, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                    .add(fileValue, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fileLbl)
                    .add(fileValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lineLbl)
                    .add(lineValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fileLbl;
    private javax.swing.JTextField fileValue;
    private javax.swing.JLabel lineLbl;
    private javax.swing.JTextField lineValue;
    // End of variables declaration//GEN-END:variables
}
