/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.

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

package org.netbeans.modules.cnd.makeproject.ui.wizards;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import org.netbeans.modules.cnd.api.utils.IpeUtils;
import org.netbeans.modules.cnd.makeproject.api.configurations.MakeConfiguration;
import org.netbeans.modules.cnd.makeproject.api.configurations.MakeConfigurationDescriptor;
import org.netbeans.spi.project.ui.support.ProjectChooser;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

public class PanelProjectLocationVisual extends SettingsPanel
                implements DocumentListener, HelpCtx.Provider {
    
    public static final String PROP_PROJECT_NAME = "projectName"; // NOI18N
    
    private PanelConfigureProject panel;
    private String templateName;
    private String name;
    private boolean makefileNameChanged = false;
    
    /** Creates new form PanelProjectLocationVisual */
    public PanelProjectLocationVisual( PanelConfigureProject panel, String name, boolean showMakefileTextField ) {
        initComponents();
        this.panel = panel;
        this.name = name;
        this.templateName = name;
        // Register listener on the textFields to make the automatic updates
        projectNameTextField.getDocument().addDocumentListener( this );
        projectLocationTextField.getDocument().addDocumentListener( this );
        if (showMakefileTextField) {
            makefileTextField.getDocument().addDocumentListener( this );
            makefileTextField.getDocument().addDocumentListener( new MakefileDocumentListener());
        }
        else {
            makefileTextField.setVisible(false);
            makefileLabel.setVisible(false);
        }
        
        // Accessibility
        makefileTextField.getAccessibleContext().setAccessibleDescription(getString("AD_MAKEFILE"));
    }
    
    
    public String getProjectName() {
        return this.projectNameTextField.getText();
    }
    
    public HelpCtx getHelpCtx() {
        return new HelpCtx("NewAppWizard"); // NOI18N
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        projectNameLabel = new javax.swing.JLabel();
        projectNameTextField = new javax.swing.JTextField();
        projectLocationLabel = new javax.swing.JLabel();
        projectLocationTextField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        createdFolderLabel = new javax.swing.JLabel();
        createdFolderTextField = new javax.swing.JTextField();
        makefileLabel = new javax.swing.JLabel();
        makefileTextField = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "ACSN_PanelProjectLocationVisual"));
        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "ACSD_PanelProjectLocationVisual"));
        projectNameLabel.setLabelFor(projectNameTextField);
        org.openide.awt.Mnemonics.setLocalizedText(projectNameLabel, org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "LBL_NWP1_ProjectName_Label"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(projectNameLabel, gridBagConstraints);
        projectNameLabel.getAccessibleContext().setAccessibleName(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("ACSN_projectNameLabel"));
        projectNameLabel.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("ACSD_projectNameLabel"));

        projectNameTextField.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 4, 0);
        add(projectNameTextField, gridBagConstraints);

        projectLocationLabel.setLabelFor(projectLocationTextField);
        org.openide.awt.Mnemonics.setLocalizedText(projectLocationLabel, org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "LBL_NWP1_ProjectLocation_Label"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(projectLocationLabel, gridBagConstraints);
        projectLocationLabel.getAccessibleContext().setAccessibleName(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("ACSN_projectLocationLabel"));
        projectLocationLabel.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("ACSD_projectLocationLabel"));

        projectLocationTextField.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 5, 0);
        add(projectLocationTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(browseButton, org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "LBL_NWP1_BrowseLocation_Button"));
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseLocationAction(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 5, 0);
        add(browseButton, gridBagConstraints);
        browseButton.getAccessibleContext().setAccessibleName(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("ACSN_browseButton"));
        browseButton.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("ACSD_browseButton"));

        createdFolderLabel.setLabelFor(createdFolderTextField);
        org.openide.awt.Mnemonics.setLocalizedText(createdFolderLabel, org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "LBL_NWP1_CreatedProjectFolder_Lablel"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(createdFolderLabel, gridBagConstraints);
        createdFolderLabel.getAccessibleContext().setAccessibleName(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("ACSN_createdFolderLabel"));
        createdFolderLabel.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/cnd/makeproject/ui/wizards/Bundle").getString("ACSD_createdFolderLabel"));

        createdFolderTextField.setColumns(20);
        createdFolderTextField.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 5, 0);
        add(createdFolderTextField, gridBagConstraints);

        makefileLabel.setLabelFor(makefileTextField);
        org.openide.awt.Mnemonics.setLocalizedText(makefileLabel, org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "LBL_MAKEFILE"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 12, 0);
        add(makefileLabel, gridBagConstraints);

        makefileTextField.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 12, 12, 0);
        add(makefileTextField, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents
    
    private void browseLocationAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseLocationAction
        JFileChooser chooser = new JFileChooser();
        FileUtil.preventFileChooserSymlinkTraversal(chooser, null);
        chooser.setDialogTitle(NbBundle.getMessage(PanelProjectLocationVisual.class,"LBL_NWP1_SelectProjectLocation")); // NOI18N
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        String path = this.projectLocationTextField.getText();
        if (path.length() > 0) {
            File f = new File(path);
            if (f.exists()) {
                chooser.setSelectedFile(f);
            }
        }
        if ( JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) { //NOI18N
            File projectDir = chooser.getSelectedFile();
            projectLocationTextField.setText( projectDir.getAbsolutePath() );
        }
        panel.fireChangeEvent();
    }//GEN-LAST:event_browseLocationAction
    
    
    public void addNotify() {
        super.addNotify();
        //same problem as in 31086, initial focus on Cancel button
        projectNameTextField.requestFocus();
    }
    
    boolean valid( WizardDescriptor wizardDescriptor ) {
        
        if ( projectNameTextField.getText().length() == 0 ) {
            wizardDescriptor.putProperty( "WizardPanel_errorMessage", // NOI18N
                    NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_IllegalProjectName")); // NOI18N
            return false; // Display name not specified
        }
        File f = new File(projectLocationTextField.getText()).getAbsoluteFile();
        if (getCanonicalFile(f)==null) {
            String message = NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_IllegalProjectLocation"); // NOI18N
            wizardDescriptor.putProperty("WizardPanel_errorMessage", message); // NOI18N
            return false;
        }
        final File destFolder = getCanonicalFile(new File( createdFolderTextField.getText() ).getAbsoluteFile());
        if (destFolder == null) {
            String message = NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_IllegalProjectName"); // NOI18N
            wizardDescriptor.putProperty("WizardPanel_errorMessage", message); // NOI18N
            return false;
        }
        if (makefileTextField.getText().indexOf(" ") >= 0) { // NOI18N
            wizardDescriptor.putProperty( "WizardPanel_errorMessage", NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_SpacesInMakefile")); // NOI18N
            return false;
        }
        if (makefileTextField.getText().indexOf("/") >= 0 || makefileTextField.getText().indexOf("\\") >= 0) { // NOI18N
            wizardDescriptor.putProperty( "WizardPanel_errorMessage", NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_IllegalMakefileName")); // NOI18N
            return false;
        }
        
        File projLoc = destFolder;
        while (projLoc != null && !projLoc.exists()) {
            projLoc = projLoc.getParentFile();
        }
        if (projLoc == null || !projLoc.canWrite()) {
            wizardDescriptor.putProperty( "WizardPanel_errorMessage", // NOI18N
                    NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_ProjectFolderReadOnly")); // NOI18N
            return false;
        }
        File[] kids = destFolder.listFiles();
        if (destFolder.exists()) {
            if (destFolder.isFile()) {
                wizardDescriptor.putProperty( "WizardPanel_errorMessage", NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_NotAFolder", makefileTextField.getText()));  // NOI18N
                return false;
            }
            if (new File(destFolder.getPath() + File.separator + makefileTextField.getText()).exists()) {
                // Folder exists and is not empty
                wizardDescriptor.putProperty( "WizardPanel_errorMessage", NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_MakefileExists", makefileTextField.getText()));  // NOI18N
                return false;
            }
            if (new File(destFolder.getPath() + File.separator + "nbproject").exists() || // NOI18N
                    new File(destFolder.getPath() + File.separator + MakeConfiguration.BUILD_FOLDER).exists() ||
                    new File(destFolder.getPath() + File.separator + MakeConfiguration.DIST_FOLDER).exists()) {
                // Folder exists and is not empty
                wizardDescriptor.putProperty( "WizardPanel_errorMessage", NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_ProjectFolderExists")); // NOI18N
                return false;
            }
        }
        
        /*
        if (destFolder.getPath().indexOf(' ') >= 0) {
            wizardDescriptor.putProperty( "WizardPanel_errorMessage", // NOI18N
                    NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_NoSpaces"));
            return false;
        }
         **/
        
        return true;
    }
    
    void store( WizardDescriptor d ) {
        
        String name = projectNameTextField.getText().trim();
        String location = projectLocationTextField.getText().trim();
        String folder = createdFolderTextField.getText().trim();
        
        d.putProperty( /*XXX Define somewhere */ "projdir", new File(folder)); // NOI18N
        d.putProperty( /*XXX Define somewhere */ "name", name); // NOI18N
        d.putProperty( /*XXX Define somewhere */ "makefilename", makefileTextField.getText()); // NOI18N
        File projectsDir = new File(this.projectLocationTextField.getText());
        if (projectsDir.isDirectory()) {
            ProjectChooser.setProjectsFolder(projectsDir);
        }
    }
    
    void read(WizardDescriptor settings) {
        File projectLocation = (File) settings.getProperty("projdir");  //NOI18N
        if (projectLocation == null) {
            projectLocation = ProjectChooser.getProjectsFolder();
        } else {
            projectLocation = projectLocation.getParentFile();
        }
        this.projectLocationTextField.setText(projectLocation.getAbsolutePath());
        
        String projectName = (String) settings.getProperty("displayName"); //NOI18N
        if (projectName == null) {
            String workingDir = (String) settings.getProperty("buildCommandWorkingDirTextField"); //NOI18N
            if (workingDir != null && workingDir.length() > 0 && templateName.equals(getString("NativeMakefileName")))
                name = IpeUtils.getBaseName(workingDir);
            int baseCount = 1;
            String formater = name + "_{0}"; // NOI18N
            while ((projectName=validFreeProjectName(projectLocation, formater, baseCount))==null)
                baseCount++;
            settings.putProperty(NewMakeProjectWizardIterator.PROP_NAME_INDEX, new Integer(baseCount));
        }
        this.projectNameTextField.setText(projectName);
        this.projectNameTextField.selectAll();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel createdFolderLabel;
    private javax.swing.JTextField createdFolderTextField;
    private javax.swing.JLabel makefileLabel;
    private javax.swing.JTextField makefileTextField;
    private javax.swing.JLabel projectLocationLabel;
    private javax.swing.JTextField projectLocationTextField;
    private javax.swing.JLabel projectNameLabel;
    private javax.swing.JTextField projectNameTextField;
    // End of variables declaration//GEN-END:variables
    
    
    // Private methods ---------------------------------------------------------
    
    private static JFileChooser createChooser() {
        JFileChooser chooser = new JFileChooser();
        FileUtil.preventFileChooserSymlinkTraversal(chooser, null);
        chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        chooser.setAcceptAllFileFilterUsed( false );
        chooser.setName( "Select Project Directory" ); // XXX // NOI18N
        return chooser;
    }
    
    private String validFreeProjectName(final File parentFolder, final String formater, final int index) {
        String name = MessageFormat.format(formater, new Object[]{new Integer(index)});
        File file = new File(parentFolder, name);
        return file.exists() ? null : name;
    }
    
    // Implementation of DocumentListener --------------------------------------
    
    public void changedUpdate( DocumentEvent e ) {
        updateTexts( e );
        if (this.projectNameTextField.getDocument() == e.getDocument()) {
            firePropertyChange(PROP_PROJECT_NAME,null,this.projectNameTextField.getText());
        }
    }
    
    public void insertUpdate( DocumentEvent e ) {
        updateTexts( e );
        if (this.projectNameTextField.getDocument() == e.getDocument()) {
            firePropertyChange(PROP_PROJECT_NAME,null,this.projectNameTextField.getText());
        }
    }
    
    public void removeUpdate( DocumentEvent e ) {
        updateTexts( e );
        if (this.projectNameTextField.getDocument() == e.getDocument()) {
            firePropertyChange(PROP_PROJECT_NAME,null,this.projectNameTextField.getText());
        }
    }
    
    class MakefileDocumentListener implements DocumentListener {
        public void changedUpdate( DocumentEvent e ) {
            makefileNameChanged = true;
        }
        
        public void insertUpdate( DocumentEvent e ) {
            makefileNameChanged = true;
        }
        
        public void removeUpdate( DocumentEvent e ) {
            makefileNameChanged = true;
        }
    }
    
    private String contructProjectMakefileName(int count) {
        String makefileName = projectNameTextField.getText() + "-" + MakeConfigurationDescriptor.DEFAULT_PROJECT_MAKFILE_NAME; // NOI18N
        if (count > 0)
            makefileName += "" + count + ".mk"; // NOI18N
        else
            makefileName += ".mk"; // NOI18N
        return makefileName;
    }
    
    
    /** Handles changes in the Project name and project directory
     */
    private void updateTexts( DocumentEvent e ) {
        
        Document doc = e.getDocument();
        
        if ( doc == projectNameTextField.getDocument() || doc == projectLocationTextField.getDocument() ) {
            String projectName = projectNameTextField.getText();
            String projectFolder = projectLocationTextField.getText();
            createdFolderTextField.setText( projectFolder + File.separatorChar + projectName );
            
            if (!makefileNameChanged) {
                // re-evaluate name of master project file.
                String makefileName;
                if (!templateName.equals(getString("NativeMakefileName"))) // NOI18N
                    makefileName = MakeConfigurationDescriptor.DEFAULT_PROJECT_MAKFILE_NAME;
                else
                    makefileName = contructProjectMakefileName(0);
                
                for (int count = 0;;) {
                String proposedMakefile = createdFolderTextField.getText() + File.separatorChar + makefileName;
                    if (!new File(proposedMakefile).exists() && !new File(proposedMakefile.toLowerCase()).exists() && !new File(proposedMakefile.toUpperCase()).exists())
                        break;
                    makefileName = contructProjectMakefileName(count++);
                }
                makefileTextField.setText(makefileName);
                makefileNameChanged = false;
            }
        }
        panel.fireChangeEvent(); // Notify that the panel changed
    }
    
    static File getCanonicalFile(File file) {
        try {
            return file.getCanonicalFile();
        } catch (IOException e) {
            return null;
        }
    }
    
    /** Look up i18n strings here */
    private static ResourceBundle bundle;
    private static String getString(String s) {
	if (bundle == null) {
	    bundle = NbBundle.getBundle(PanelProjectLocationVisual.class);
}
	return bundle.getString(s);
    }
    
}
