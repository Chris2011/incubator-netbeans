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
package org.netbeans.modules.web.clientproject.ui.customizer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.charset.Charset;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.modules.web.clientproject.ClientSideProject;
import org.netbeans.modules.web.clientproject.api.validation.ValidationResult;
import org.netbeans.modules.web.clientproject.indirect.IndirectServices;
import org.netbeans.modules.web.clientproject.validation.ProjectFoldersValidator;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.openide.awt.Mnemonics;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

public class SourcesPanel extends JPanel implements HelpCtx.Provider {

    private static final long serialVersionUID = -6576834165786545L;

    private final ProjectCustomizer.Category category;
    private final ClientSideProjectProperties uiProperties;
    private final ClientSideProject project;


    public SourcesPanel(ProjectCustomizer.Category category, ClientSideProjectProperties uiProperties) {
        assert category != null;
        assert uiProperties != null;

        this.category = category;
        this.uiProperties = uiProperties;
        project = uiProperties.getProject();

        initComponents();
        init();
        initListeners();
        validateData();
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx("org.netbeans.modules.web.clientproject.ui.customizer.SourcesPanel"); // NOI18N
    }

    private void init() {
        projectFolderTextField.setText(FileUtil.getFileDisplayName(project.getProjectDirectory()));
        setSiteRootFolder(beautifyPath(uiProperties.getSiteRootFolder().get()), false);
        setSourceFolder(beautifyPath(uiProperties.getSourceFolder().get()), false);
        setTestFolder(beautifyPath(uiProperties.getTestFolder().get()), false);
        encodingComboBox.setModel(ProjectCustomizer.encodingModel(uiProperties.getEncoding()));
        encodingComboBox.setRenderer(ProjectCustomizer.encodingRenderer());
    }

    private void initListeners() {
        DocumentListener documentListener = new DefaultDocumentListener();
        siteRootFolderTextField.getDocument().addDocumentListener(documentListener);
        sourceFolderTextField.getDocument().addDocumentListener(documentListener);
        testFolderTextField.getDocument().addDocumentListener(documentListener);
        encodingComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateAndStore();
            }
        });
    }

    void validateAndStore() {
        validateData();
        storeData();
    }

    private void validateData() {
        ValidationResult result = new ProjectFoldersValidator()
                .validate(getSourceFolder(), getSiteRootFolder(), getTestFolder())
                .getResult();
        // errors
        if (result.hasErrors()) {
            category.setErrorMessage(result.getErrors().get(0).getMessage());
            category.setValid(false);
            return;
        }
        // warnings
        if (result.hasWarnings()) {
            category.setErrorMessage(result.getWarnings().get(0).getMessage());
            category.setValid(true);
            return;
        }
        // all ok
        category.setErrorMessage(" "); // NOI18N
        category.setValid(true);
    }

    private void storeData() {
        File siteRootFolder = getSiteRootFolder();
        uiProperties.setSiteRootFolder(siteRootFolder != null ? siteRootFolder.getAbsolutePath() : null);
        File sourceFolder = getSourceFolder();
        uiProperties.setSourceFolder(sourceFolder != null ? sourceFolder.getAbsolutePath() : null);
        File testFolder = getTestFolder();
        uiProperties.setTestFolder(testFolder != null ? testFolder.getAbsolutePath() : null);
        uiProperties.setEncoding(getEncoding().name());
    }

    private void setSiteRootFolder(String siteRoot) {
        setSiteRootFolder(siteRoot, true);
    }

    @NbBundle.Messages({
        "SourcesPanel.siteRoot.info.empty=Empty value means project directory",
        "SourcesPanel.siteRoot.info.none=Site Root folder will not be used",
    })
    private void setSiteRootFolder(String siteRoot, boolean validate) {
        setFolder(siteRoot, siteRootFolderTextField, siteRootFolderRemoveButton, validate);
        siteRootFolderInfoLabel.setText(siteRoot != null ? Bundle.SourcesPanel_siteRoot_info_empty() : Bundle.SourcesPanel_siteRoot_info_none());
    }

    private void setSourceFolder(String sources) {
        setSourceFolder(sources, true);
    }

    @NbBundle.Messages({
        "SourcesPanel.sources.info.empty=Empty value means project directory",
        "SourcesPanel.sources.info.none=Source folder will not be used",
    })
    private void setSourceFolder(String sources, boolean validate) {
        setFolder(sources, sourceFolderTextField, sourceFolderRemoveButton, validate);
        sourceFolderInfoLabel.setText(sources != null ? Bundle.SourcesPanel_sources_info_empty() : Bundle.SourcesPanel_sources_info_none());
    }

    private void setTestFolder(String tests) {
        setTestFolder(tests, true);
    }

    @NbBundle.Messages("SourcesPanel.tests.info=Empty value means no Unit Tests folder")
    private void setTestFolder(String tests, boolean validate) {
        setFolder(tests, testFolderTextField, testFolderRemoveButton, validate);
        testFolderInfoLabel.setText(Bundle.SourcesPanel_tests_info());
    }

    private void setFolder(String folder, JTextField textField, JButton removeButton, boolean validate) {
        textField.setText(folder);
        textField.setEnabled(folder != null);
        removeButton.setEnabled(folder != null);
        if (validate) {
            validateAndStore();
        }
    }


    @CheckForNull
    private File getSiteRootFolder() {
        return getFolder(siteRootFolderTextField);
    }

    @CheckForNull
    private File getSourceFolder() {
        return getFolder(sourceFolderTextField);
    }

    @CheckForNull
    private File getFolder(JTextField textField) {
        if (!textField.isEnabled()) {
            return null;
        }
        File resolved = resolveFile(textField.getText(), true);
        if (resolved != null) {
            return resolved;
        }
        // return project dir
        return FileUtil.toFile(project.getProjectDirectory());
    }

    private File getTestFolder() {
        return resolveFile(testFolderTextField.getText(), false);
    }

    private Charset getEncoding() {
        return (Charset) encodingComboBox.getSelectedItem();
    }

    private File resolveFile(String path, boolean emptyIsProject) {
        if (path == null) {
            return null;
        }
        if (!emptyIsProject
                && path.isEmpty()) {
            return null;
        }
        return FileUtil.normalizeFile(project.getProjectHelper().resolveFile(path));
    }

    private String browseFolder(String title, File currentPath) {
        File workDir = currentPath;
        if (workDir == null || !workDir.exists()) {
            workDir = FileUtil.toFile(project.getProjectDirectory());
        }
        File folder = new FileChooserBuilder(SourcesPanel.class)
                .setTitle(title)
                .setDirectoriesOnly(true)
                .setDefaultWorkingDirectory(workDir)
                .forceUseOfDefaultWorkingDirectory(true)
                .setFileHiding(true)
                .showOpenDialog();
        if (folder == null) {
            return null;
        }
        String filePath = project.is.relativizeFile(FileUtil.toFile(project.getProjectDirectory()), folder);
        if (filePath == null) {
            // path cannot be relativized
            filePath = folder.getAbsolutePath();
        }
        return beautifyPath(filePath);
    }

    private String beautifyPath(String path) {
        if (path == null) {
            return null;
        } else if (path.equals(".")) { // NOI18N
            return ""; // NOI18N
        } else if (path.startsWith("../../")) { // NOI18N
            File resolved = resolveFile(path, false);
            assert resolved != null : path;
            return resolved.getAbsolutePath();
        }
        return path;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        projectFolderLabel = new JLabel();
        projectFolderTextField = new JTextField();
        siteRootLabel = new JLabel();
        siteRootFolderTextField = new JTextField();
        siteRootFolderBrowseButton = new JButton();
        siteRootFolderRemoveButton = new JButton();
        siteRootFolderInfoLabel = new JLabel();
        sourceFolderLabel = new JLabel();
        sourceFolderTextField = new JTextField();
        sourceFolderBrowseButton = new JButton();
        sourceFolderRemoveButton = new JButton();
        sourceFolderInfoLabel = new JLabel();
        testFolderLabel = new JLabel();
        testFolderTextField = new JTextField();
        testFolderBrowseButton = new JButton();
        testFolderInfoLabel = new JLabel();
        testFolderRemoveButton = new JButton();
        encodingLabel = new JLabel();
        encodingComboBox = new JComboBox();

        projectFolderLabel.setLabelFor(projectFolderTextField);
        Mnemonics.setLocalizedText(projectFolderLabel, NbBundle.getMessage(SourcesPanel.class, "SourcesPanel.projectFolderLabel.text")); // NOI18N

        projectFolderTextField.setEditable(false);

        siteRootLabel.setLabelFor(siteRootFolderTextField);
        Mnemonics.setLocalizedText(siteRootLabel, NbBundle.getMessage(SourcesPanel.class, "SourcesPanel.siteRootLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(siteRootFolderBrowseButton, NbBundle.getMessage(SourcesPanel.class, "SourcesPanel.siteRootFolderBrowseButton.text")); // NOI18N
        siteRootFolderBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                siteRootFolderBrowseButtonActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(siteRootFolderRemoveButton, NbBundle.getMessage(SourcesPanel.class, "SourcesPanel.siteRootFolderRemoveButton.text")); // NOI18N
        siteRootFolderRemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                siteRootFolderRemoveButtonActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(siteRootFolderInfoLabel, "HINT"); // NOI18N

        sourceFolderLabel.setLabelFor(sourceFolderTextField);
        Mnemonics.setLocalizedText(sourceFolderLabel, NbBundle.getMessage(SourcesPanel.class, "SourcesPanel.sourceFolderLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(sourceFolderBrowseButton, NbBundle.getMessage(SourcesPanel.class, "SourcesPanel.sourceFolderBrowseButton.text")); // NOI18N
        sourceFolderBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sourceFolderBrowseButtonActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(sourceFolderRemoveButton, NbBundle.getMessage(SourcesPanel.class, "SourcesPanel.sourceFolderRemoveButton.text")); // NOI18N
        sourceFolderRemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sourceFolderRemoveButtonActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(sourceFolderInfoLabel, "HINT"); // NOI18N

        testFolderLabel.setLabelFor(testFolderTextField);
        Mnemonics.setLocalizedText(testFolderLabel, NbBundle.getMessage(SourcesPanel.class, "SourcesPanel.testFolderLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(testFolderBrowseButton, NbBundle.getMessage(SourcesPanel.class, "SourcesPanel.testFolderBrowseButton.text")); // NOI18N
        testFolderBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                testFolderBrowseButtonActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(testFolderInfoLabel, "HINT"); // NOI18N

        Mnemonics.setLocalizedText(testFolderRemoveButton, NbBundle.getMessage(SourcesPanel.class, "SourcesPanel.testFolderRemoveButton.text")); // NOI18N
        testFolderRemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                testFolderRemoveButtonActionPerformed(evt);
            }
        });

        encodingLabel.setLabelFor(encodingComboBox);
        Mnemonics.setLocalizedText(encodingLabel, NbBundle.getMessage(SourcesPanel.class, "SourcesPanel.encodingLabel.text")); // NOI18N

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(siteRootLabel)
                    .addComponent(projectFolderLabel)
                    .addComponent(testFolderLabel)
                    .addComponent(encodingLabel)
                    .addComponent(sourceFolderLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(projectFolderTextField)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(encodingComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(95, 95, 95))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sourceFolderTextField)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sourceFolderBrowseButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sourceFolderRemoveButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(siteRootFolderTextField)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(siteRootFolderBrowseButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(siteRootFolderRemoveButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(sourceFolderInfoLabel)
                            .addComponent(siteRootFolderInfoLabel)
                            .addComponent(testFolderInfoLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(testFolderTextField)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(testFolderBrowseButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(testFolderRemoveButton))))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {siteRootFolderBrowseButton, testFolderBrowseButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(projectFolderLabel)
                    .addComponent(projectFolderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(siteRootLabel)
                    .addComponent(siteRootFolderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(siteRootFolderBrowseButton)
                    .addComponent(siteRootFolderRemoveButton))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(siteRootFolderInfoLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(sourceFolderLabel)
                    .addComponent(sourceFolderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(sourceFolderBrowseButton)
                    .addComponent(sourceFolderRemoveButton))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sourceFolderInfoLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(testFolderLabel)
                    .addComponent(testFolderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(testFolderBrowseButton)
                    .addComponent(testFolderRemoveButton))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(testFolderInfoLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(encodingLabel)
                    .addComponent(encodingComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    @NbBundle.Messages("SourcesPanel.browse.siteRootFolder=Select Site Root")
    private void siteRootFolderBrowseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_siteRootFolderBrowseButtonActionPerformed
        String filePath = browseFolder(Bundle.SourcesPanel_browse_siteRootFolder(), getSiteRootFolder());
        if (filePath != null) {
            setSiteRootFolder(filePath);
        }
    }//GEN-LAST:event_siteRootFolderBrowseButtonActionPerformed

    @NbBundle.Messages("SourcesPanel.browse.testFolder=Select Unit Tests")
    private void testFolderBrowseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_testFolderBrowseButtonActionPerformed
        String filePath = browseFolder(Bundle.SourcesPanel_browse_testFolder(), getTestFolder());
        if (filePath != null) {
            setTestFolder(filePath);
        }
    }//GEN-LAST:event_testFolderBrowseButtonActionPerformed

    private void siteRootFolderRemoveButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_siteRootFolderRemoveButtonActionPerformed
        setSiteRootFolder(null);
    }//GEN-LAST:event_siteRootFolderRemoveButtonActionPerformed

    private void sourceFolderRemoveButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_sourceFolderRemoveButtonActionPerformed
        setSourceFolder(null);
    }//GEN-LAST:event_sourceFolderRemoveButtonActionPerformed

    private void testFolderRemoveButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_testFolderRemoveButtonActionPerformed
        setTestFolder(null);
    }//GEN-LAST:event_testFolderRemoveButtonActionPerformed

    @NbBundle.Messages("SourcesPanel.browse.sourceFolder=Select Sources")
    private void sourceFolderBrowseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_sourceFolderBrowseButtonActionPerformed
        String filePath = browseFolder(Bundle.SourcesPanel_browse_sourceFolder(), getSourceFolder());
        if (filePath != null) {
            setSourceFolder(filePath);
        }
    }//GEN-LAST:event_sourceFolderBrowseButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JComboBox encodingComboBox;
    private JLabel encodingLabel;
    private JLabel projectFolderLabel;
    private JTextField projectFolderTextField;
    private JButton siteRootFolderBrowseButton;
    private JLabel siteRootFolderInfoLabel;
    private JButton siteRootFolderRemoveButton;
    private JTextField siteRootFolderTextField;
    private JLabel siteRootLabel;
    private JButton sourceFolderBrowseButton;
    private JLabel sourceFolderInfoLabel;
    private JLabel sourceFolderLabel;
    private JButton sourceFolderRemoveButton;
    private JTextField sourceFolderTextField;
    private JButton testFolderBrowseButton;
    private JLabel testFolderInfoLabel;
    private JLabel testFolderLabel;
    private JButton testFolderRemoveButton;
    private JTextField testFolderTextField;
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
            validateAndStore();
        }

    }

}
