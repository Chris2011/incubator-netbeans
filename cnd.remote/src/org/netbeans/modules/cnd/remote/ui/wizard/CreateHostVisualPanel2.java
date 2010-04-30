/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */
package org.netbeans.modules.cnd.remote.ui.wizard;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.modules.cnd.api.remote.ServerList;
import org.netbeans.modules.cnd.api.remote.ServerRecord;
import org.netbeans.modules.cnd.spi.remote.setup.support.TextComponentWriter;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironmentFactory;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironment;
import org.netbeans.modules.nativeexecution.api.util.PasswordManager;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/*package*/ final class CreateHostVisualPanel2 extends JPanel {

    private final ChangeListener wizardListener;
    private final CreateHostData data;

    public CreateHostVisualPanel2(CreateHostData data, ChangeListener listener) {
        this.data = data;
        wizardListener = listener;
        initComponents();

        textLoginName.setText(System.getProperty("user.name"));

//        if (Boolean.getBoolean("cnd.remote.keep.pwd")) {
//            // default password to the last entered one
//            ExecutionEnvironment lastEnv = CreateHostData.getLastExecutionEnvironment();
//            if (lastEnv != null) {
//                char[] passwd = PasswordManager.getInstance().get(lastEnv);
//                if (passwd != null) {
//                    textPassword.setText(new String(passwd));
//                }
//            }
//        } else {
//            textPassword.setText("");
//        }

        DocumentListener dl = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                fireChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fireChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fireChange();
            }
        };

        textLoginName.getDocument().addDocumentListener(dl);
//        textPassword.getDocument().addDocumentListener(dl);
    }

    private void fireChange() {
        hostFound = null;
        wizardListener.stateChanged(null);
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(getClass(), "CreateHostVisualPanel2.Title");
    }

    void init() {
    }

    private String getLoginName() {
        return textLoginName.getText();
    }

//    char[] getPassword() {
//        return textPassword.getPassword();
//    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        textLoginName = new javax.swing.JTextField();
        pbarStatusPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpOutput = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(534, 409));
        setRequestFocusEnabled(false);

        jLabel1.setLabelFor(textLoginName);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CreateHostVisualPanel2.class, "CreateHostVisualPanel2.jLabel1.text")); // NOI18N

        textLoginName.setText(org.openide.util.NbBundle.getMessage(CreateHostVisualPanel2.class, "CreateHostVisualPanel2.textLoginName.text")); // NOI18N

        pbarStatusPanel.setMaximumSize(new java.awt.Dimension(2147483647, 10));
        pbarStatusPanel.setMinimumSize(new java.awt.Dimension(100, 10));
        pbarStatusPanel.setLayout(new java.awt.BorderLayout());

        tpOutput.setEditable(false);
        tpOutput.setText(org.openide.util.NbBundle.getMessage(CreateHostVisualPanel2.class, "CreateHostVisualPanel2.tpOutput.text")); // NOI18N
        tpOutput.setOpaque(false);
        jScrollPane1.setViewportView(tpOutput);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(CreateHostVisualPanel2.class, "CreateHostVisualPanel2.jLabel3.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textLoginName, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
            .addComponent(pbarStatusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textLoginName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbarStatusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private ProgressHandle phandle;

    /* package-local */ ExecutionEnvironment getHost() {
        return hostFound;
    }

    /* package-local */ Runnable getRunOnFinish() {
        return runOnFinish;
    }
    private ExecutionEnvironment hostFound = null;
    private Runnable runOnFinish = null;

    public void enableControls(boolean enable) {
//        textPassword.setEnabled(enable);
        textLoginName.setEnabled(enable);
//        cbSavePassword.setEnabled(enable);
    }

    public boolean canValidateHost() {
        List<ServerRecord> records = new ArrayList<ServerRecord>();
        if (data.getCacheManager().getServerUpdateCache() != null && data.getCacheManager().getServerUpdateCache().getHosts() != null) {
            records.addAll(data.getCacheManager().getServerUpdateCache().getHosts());
        } else {
            records = new ArrayList<ServerRecord>(ServerList.getRecords());
        }
        for (ServerRecord record : records) {
            if (record.isRemote()) {
                if (record.getServerName().equals(data.getHostName())
                        && record.getExecutionEnvironment().getSSHPort() == data.getPort()
                        && record.getUserName().equals(textLoginName.getText())) {
                    return false;
                }
            }
        }
        return true;
    }

    public Future<Boolean> validateHost() {
        FutureTask<Boolean> validationTask = new FutureTask<Boolean>(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
//                final char[] password = getPassword();
//                final boolean rememberPassword = cbSavePassword.isSelected();
                final ExecutionEnvironment env = ExecutionEnvironmentFactory.createNew(getLoginName(), data.getHostName(), data.getPort());

                tpOutput.setText("");

                phandle = ProgressHandleFactory.createHandle(""); ////NOI18N
                pbarStatusPanel.removeAll();
                pbarStatusPanel.add(ProgressHandleFactory.createProgressComponent(phandle), BorderLayout.CENTER);
                pbarStatusPanel.validate();
                phandle.start();

                try {
                    HostValidatorImpl hostValidator = new HostValidatorImpl(data.getCacheManager());
                    if (hostValidator.validate(env, /*password, rememberPassword, */new TextComponentWriter(tpOutput))) {
                        hostFound = env;
                        runOnFinish = hostValidator.getRunOnFinish();
                        try { // let user see the log ;-)
                            Thread.sleep(1500);
                        } catch (InterruptedException ex) {
                            // nothing
                        }
                    }
                } finally {
                    phandle.finish();
                    wizardListener.stateChanged(null);
                    pbarStatusPanel.setVisible(false);
                }

                return true;
            }
        });

        RequestProcessor.getDefault().post(validationTask);

        return validationTask;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pbarStatusPanel;
    private javax.swing.JTextField textLoginName;
    private javax.swing.JTextPane tpOutput;
    // End of variables declaration//GEN-END:variables
}

