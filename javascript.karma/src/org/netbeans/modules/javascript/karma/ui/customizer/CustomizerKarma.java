/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */

package org.netbeans.modules.javascript.karma.ui.customizer;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.project.Project;
import org.netbeans.modules.javascript.karma.preferences.KarmaPreferences;
import org.netbeans.modules.javascript.karma.preferences.KarmaPreferencesValidator;
import org.netbeans.modules.javascript.karma.util.KarmaUtils;
import org.netbeans.modules.javascript.karma.util.ValidationResult;
import org.netbeans.modules.web.browser.api.BrowserUISupport;
import org.netbeans.modules.web.browser.api.WebBrowser;
import org.openide.awt.HtmlBrowser;
import org.openide.awt.Mnemonics;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.filesystems.FileUtil;
import org.openide.util.ChangeSupport;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

public class CustomizerKarma extends JPanel {

    private final Project project;
    private final BrowserUISupport.BrowserComboBoxModel browserModel;
    private final ChangeSupport changeSupport = new ChangeSupport(this);

    private volatile String karma;
    private volatile String config;
    private volatile boolean autowatch;
    private volatile boolean debug;
    private volatile String selectedBrowserId;

    // @GuardedBy("EDT")
    private ValidationResult validationResult;


    public CustomizerKarma(Project project) {
        assert EventQueue.isDispatchThread();
        assert project != null;

        this.project = project;

        browserModel = BrowserUISupport.createBrowserModel(KarmaPreferences.getDebugBrowserId(project), KarmaUtils.getDebugBrowsers());

        initComponents();
        init();
    }

    public void addChangeListener(ChangeListener listener) {
        changeSupport.addChangeListener(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        changeSupport.removeChangeListener(listener);
    }

    public String getKarma() {
        return karma;
    }

    public String getConfig() {
        return config;
    }

    public boolean isAutowatch() {
        return autowatch;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getSelectedBrowserId() {
        return selectedBrowserId;
    }

    public String getWarningMessage() {
        assert EventQueue.isDispatchThread();
        for (ValidationResult.Message message : validationResult.getWarnings()) {
            return message.getMessage();
        }
        return null;
    }

    public String getErrorMessage() {
        assert EventQueue.isDispatchThread();
        for (ValidationResult.Message message : validationResult.getErrors()) {
            return message.getMessage();
        }
        return null;
    }

    private void init() {
        assert EventQueue.isDispatchThread();
        // data
        karmaTextField.setText(KarmaPreferences.getKarma(project));
        configTextField.setText(KarmaPreferences.getConfig(project));
        autowatchCheckBox.setSelected(KarmaPreferences.isAutowatch(project));
        debugCheckBox.setSelected(KarmaPreferences.isDebug(project));
        debugBrowserIdComboBox.setModel(browserModel);
        debugBrowserIdComboBox.setRenderer(BrowserUISupport.createBrowserRenderer());
        // enabled
        enableDebugBrowserComboBox(debugCheckBox.isSelected());
        // listeners
        addListeners();
        // initial validation
        validateData();
    }

    private void addListeners() {
        DocumentListener defaultDocumentListener = new DefaultDocumentListener();
        ItemListener defaultItemListener = new DefaultItemListener();
        ActionListener defaultActionListener = new DefaultActionListener();
        karmaTextField.getDocument().addDocumentListener(defaultDocumentListener);
        configTextField.getDocument().addDocumentListener(defaultDocumentListener);
        autowatchCheckBox.addItemListener(defaultItemListener);
        debugCheckBox.addItemListener(new DebugItemListener());
        debugBrowserIdComboBox.addActionListener(defaultActionListener);
    }

    void validateData() {
        assert EventQueue.isDispatchThread();
        karma = karmaTextField.getText();
        config = configTextField.getText();
        debug = debugCheckBox.isSelected();
        autowatch = autowatchCheckBox.isSelected();
        selectedBrowserId = browserModel.getSelectedBrowserId();
        validationResult = new KarmaPreferencesValidator()
                .validateKarma(karma)
                .validateConfig(config)
                .validateDebug(debug, selectedBrowserId)
                .getResult();
        changeSupport.fireChange();
    }

    void enableDebugBrowserComboBox(boolean enabled) {
        debugBrowserIdComboBox.setEnabled(enabled);
    }

    private File getProjectDirectory() {
        return FileUtil.toFile(project.getProjectDirectory());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        karmaLabel = new JLabel();
        karmaTextField = new JTextField();
        karmaBrowseButton = new JButton();
        karmaSearchButton = new JButton();
        configLabel = new JLabel();
        configTextField = new JTextField();
        configBrowseButton = new JButton();
        configSearchButton = new JButton();
        autowatchCheckBox = new JCheckBox();
        debugLabel = new JLabel();
        debugCheckBox = new JCheckBox();
        debugBrowserIdLabel = new JLabel();
        debugBrowserIdComboBox = new JComboBox<WebBrowser>();
        coverageLabel = new JLabel();
        coverageInfoLabel = new JLabel();
        coverageLearnMoreLabel = new JLabel();
        coverageIstanbulInfoLabel = new JLabel();
        coverageConfigLabel = new JLabel();
        coverageDebugLabel = new JLabel();

        karmaLabel.setLabelFor(karmaTextField);
        Mnemonics.setLocalizedText(karmaLabel, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.karmaLabel.text")); // NOI18N

        karmaTextField.setColumns(30);

        Mnemonics.setLocalizedText(karmaBrowseButton, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.karmaBrowseButton.text")); // NOI18N
        karmaBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                karmaBrowseButtonActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(karmaSearchButton, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.karmaSearchButton.text")); // NOI18N
        karmaSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                karmaSearchButtonActionPerformed(evt);
            }
        });

        configLabel.setLabelFor(configTextField);
        Mnemonics.setLocalizedText(configLabel, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.configLabel.text")); // NOI18N

        configTextField.setColumns(30);

        Mnemonics.setLocalizedText(configBrowseButton, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.configBrowseButton.text")); // NOI18N
        configBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                configBrowseButtonActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(configSearchButton, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.configSearchButton.text")); // NOI18N
        configSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                configSearchButtonActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(autowatchCheckBox, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.autowatchCheckBox.text")); // NOI18N

        Mnemonics.setLocalizedText(debugLabel, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.debugLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(debugCheckBox, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.debugCheckBox.text")); // NOI18N

        debugBrowserIdLabel.setLabelFor(debugBrowserIdComboBox);
        Mnemonics.setLocalizedText(debugBrowserIdLabel, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.debugBrowserIdLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(coverageLabel, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.coverageLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(coverageInfoLabel, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.coverageInfoLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(coverageLearnMoreLabel, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.coverageLearnMoreLabel.text")); // NOI18N
        coverageLearnMoreLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                coverageLearnMoreLabelMouseEntered(evt);
            }
            public void mousePressed(MouseEvent evt) {
                coverageLearnMoreLabelMousePressed(evt);
            }
        });

        Mnemonics.setLocalizedText(coverageIstanbulInfoLabel, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.coverageIstanbulInfoLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(coverageConfigLabel, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.coverageConfigLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(coverageDebugLabel, NbBundle.getMessage(CustomizerKarma.class, "CustomizerKarma.coverageDebugLabel.text")); // NOI18N

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(configLabel)
                    .addComponent(karmaLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(karmaTextField, GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(karmaBrowseButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(karmaSearchButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(configTextField, GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(configBrowseButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(configSearchButton))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(autowatchCheckBox)
                    .addComponent(coverageLabel)
                    .addComponent(debugLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(debugCheckBox)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(coverageInfoLabel)
                        .addGap(18, 18, 18)
                        .addComponent(coverageLearnMoreLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(coverageIstanbulInfoLabel)
                    .addComponent(coverageConfigLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(coverageDebugLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(debugBrowserIdLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(debugBrowserIdComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {configBrowseButton, karmaBrowseButton});

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {configSearchButton, karmaSearchButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(karmaLabel)
                    .addComponent(karmaTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(karmaBrowseButton)
                    .addComponent(karmaSearchButton))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(configLabel)
                    .addComponent(configTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(configBrowseButton)
                    .addComponent(configSearchButton))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(autowatchCheckBox)
                .addGap(18, 18, 18)
                .addComponent(debugLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(debugCheckBox)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(debugBrowserIdLabel)
                    .addComponent(debugBrowserIdComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(coverageLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(coverageInfoLabel)
                    .addComponent(coverageLearnMoreLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coverageIstanbulInfoLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coverageConfigLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coverageDebugLabel))
        );
    }// </editor-fold>//GEN-END:initComponents

    @NbBundle.Messages("CustomizerKarma.chooser.karma=Select Karma file")
    private void karmaBrowseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_karmaBrowseButtonActionPerformed
        assert EventQueue.isDispatchThread();
        File file = new FileChooserBuilder(CustomizerKarma.class)
                .setTitle(Bundle.CustomizerKarma_chooser_karma())
                .setFilesOnly(true)
                .setDefaultWorkingDirectory(getProjectDirectory())
                .forceUseOfDefaultWorkingDirectory(true)
                .showOpenDialog();
        if (file != null) {
            karmaTextField.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_karmaBrowseButtonActionPerformed

    @NbBundle.Messages("CustomizerKarma.chooser.config=Select Karma configuration file")
    private void configBrowseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_configBrowseButtonActionPerformed
        assert EventQueue.isDispatchThread();
        File file = new FileChooserBuilder(CustomizerKarma.class)
                .setTitle(Bundle.CustomizerKarma_chooser_config())
                .setFilesOnly(true)
                .setDefaultWorkingDirectory(KarmaUtils.getKarmaConfigDir(project))
                .forceUseOfDefaultWorkingDirectory(true)
                .showOpenDialog();
        if (file != null) {
            configTextField.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_configBrowseButtonActionPerformed

    @NbBundle.Messages("CustomizerKarma.karma.none=No Karma executable was found.")
    private void karmaSearchButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_karmaSearchButtonActionPerformed
        assert EventQueue.isDispatchThread();
        File karmaExecutable = KarmaUtils.findKarma(project);
        if (karmaExecutable != null) {
            karmaTextField.setText(karmaExecutable.getAbsolutePath());
            return;
        }
        // no karma found
        StatusDisplayer.getDefault().setStatusText(Bundle.CustomizerKarma_karma_none());
    }//GEN-LAST:event_karmaSearchButtonActionPerformed

    @NbBundle.Messages("CustomizerKarma.config.none=No Karma configuration was found.")
    private void configSearchButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_configSearchButtonActionPerformed
        assert EventQueue.isDispatchThread();
        File karmaConfig = KarmaUtils.findKarmaConfig(KarmaUtils.getKarmaConfigDir(project));
        if (karmaConfig != null) {
            configTextField.setText(karmaConfig.getAbsolutePath());
            return;
        }
        // no config found
        StatusDisplayer.getDefault().setStatusText(Bundle.CustomizerKarma_config_none());
    }//GEN-LAST:event_configSearchButtonActionPerformed

    private void coverageLearnMoreLabelMouseEntered(MouseEvent evt) {//GEN-FIRST:event_coverageLearnMoreLabelMouseEntered
        assert EventQueue.isDispatchThread();
        evt.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_coverageLearnMoreLabelMouseEntered

    private void coverageLearnMoreLabelMousePressed(MouseEvent evt) {//GEN-FIRST:event_coverageLearnMoreLabelMousePressed
        assert EventQueue.isDispatchThread();
        try {
            URL url = new URL("https://github.com/karma-runner/karma-coverage"); // NOI18N
            HtmlBrowser.URLDisplayer.getDefault().showURL(url);
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_coverageLearnMoreLabelMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JCheckBox autowatchCheckBox;
    private JButton configBrowseButton;
    private JLabel configLabel;
    private JButton configSearchButton;
    private JTextField configTextField;
    private JLabel coverageConfigLabel;
    private JLabel coverageDebugLabel;
    private JLabel coverageInfoLabel;
    private JLabel coverageIstanbulInfoLabel;
    private JLabel coverageLabel;
    private JLabel coverageLearnMoreLabel;
    private JComboBox<WebBrowser> debugBrowserIdComboBox;
    private JLabel debugBrowserIdLabel;
    private JCheckBox debugCheckBox;
    private JLabel debugLabel;
    private JButton karmaBrowseButton;
    private JLabel karmaLabel;
    private JButton karmaSearchButton;
    private JTextField karmaTextField;
    // End of variables declaration//GEN-END:variables

    //~ Inner classes

    private final class DefaultDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            processChange();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            processChange();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            processChange();
        }

        private void processChange() {
            validateData();
        }

    }

    private final class DefaultItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            validateData();
        }

    }

    private final class DefaultActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            validateData();
        }

    }

    private final class DebugItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            enableDebugBrowserComboBox(e.getStateChange() == ItemEvent.SELECTED);
            validateData();
        }

    }

}
