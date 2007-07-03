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

package org.netbeans.modules.ruby.rubyproject.ui.wizards;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import org.netbeans.modules.ruby.rubyproject.ui.FoldersListSettings;
import org.netbeans.modules.ruby.spi.project.support.rake.PropertyUtils;
import org.netbeans.spi.project.ui.support.ProjectChooser;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 *
 * @author  Petr Hrebejk
 */

public class PanelProjectLocationVisual extends SettingsPanel implements DocumentListener {
    
    public static final String PROP_PROJECT_NAME = "projectName";      //NOI18N
    
    private PanelConfigureProject panel;
    private int type;
        
    /** Creates new form PanelProjectLocationVisual */
    public PanelProjectLocationVisual( PanelConfigureProject panel, int type ) {
        initComponents();
        this.panel = panel;
        this.type = type;
        // Register listener on the textFields to make the automatic updates
        projectNameTextField.getDocument().addDocumentListener( this );
        projectLocationTextField.getDocument().addDocumentListener( this );        
    }
    
    
    public String getProjectName () {
        return this.projectNameTextField.getText ();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        projectNameLabel = new javax.swing.JLabel();
        projectNameTextField = new javax.swing.JTextField();
        projectLocationLabel = new javax.swing.JLabel();
        projectLocationTextField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        createdFolderLabel = new javax.swing.JLabel();
        createdFolderTextField = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "ACSN_PanelProjectLocationVisual"));
        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "ACSD_PanelProjectLocationVisual"));
        projectNameLabel.setLabelFor(projectNameTextField);
        org.openide.awt.Mnemonics.setLocalizedText(projectNameLabel, org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "LBL_NWP1_ProjectName_Label"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        add(projectNameLabel, gridBagConstraints);
        projectNameLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getBundle(PanelProjectLocationVisual.class).getString("ACSN_projectNameLabel"));
        projectNameLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getBundle(PanelProjectLocationVisual.class).getString("ACSD_projectNameLabel"));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 12, 0);
        add(projectNameTextField, gridBagConstraints);

        projectLocationLabel.setLabelFor(projectLocationTextField);
        org.openide.awt.Mnemonics.setLocalizedText(projectLocationLabel, org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "LBL_NWP1_ProjectLocation_Label"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(projectLocationLabel, gridBagConstraints);
        projectLocationLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getBundle(PanelProjectLocationVisual.class).getString("ACSN_projectLocationLabel"));
        projectLocationLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getBundle(PanelProjectLocationVisual.class).getString("ACSD_projectLocationLabel"));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 5, 0);
        add(projectLocationTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(browseButton, org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "LBL_NWP1_BrowseLocation_Button"));
        browseButton.setActionCommand("BROWSE");
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
        browseButton.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getBundle(PanelProjectLocationVisual.class).getString("ACSN_browseButton"));
        browseButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getBundle(PanelProjectLocationVisual.class).getString("ACSD_browseButton"));

        createdFolderLabel.setLabelFor(createdFolderTextField);
        org.openide.awt.Mnemonics.setLocalizedText(createdFolderLabel, org.openide.util.NbBundle.getMessage(PanelProjectLocationVisual.class, "LBL_NWP1_CreatedProjectFolder_Lablel"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(createdFolderLabel, gridBagConstraints);
        createdFolderLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getBundle(PanelProjectLocationVisual.class).getString("ACSN_createdFolderLabel"));
        createdFolderLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getBundle(PanelProjectLocationVisual.class).getString("ACSD_createdFolderLabel"));

        createdFolderTextField.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
        add(createdFolderTextField, gridBagConstraints);

    }//GEN-END:initComponents

    private void browseLocationAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseLocationAction
        String command = evt.getActionCommand();        
        if ( "BROWSE".equals( command ) ) { // NOI18N                
            JFileChooser chooser = new JFileChooser ();
            FileUtil.preventFileChooserSymlinkTraversal(chooser, null);
            chooser.setDialogTitle(NbBundle.getMessage(PanelSourceFolders.class,"LBL_NWP1_SelectProjectLocation"));
            chooser.setFileSelectionMode (JFileChooser.DIRECTORIES_ONLY);
            String path = this.projectLocationTextField.getText();
            if (path.length() > 0) {
                File f = new File (path);
                if (f.exists ()) {
                    chooser.setSelectedFile(f);
                }
            }
            if ( JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) { //NOI18N
                File projectDir = chooser.getSelectedFile();
                projectLocationTextField.setText( FileUtil.normalizeFile(projectDir).getAbsolutePath() );
            }            
            panel.fireChangeEvent();
        }
    }//GEN-LAST:event_browseLocationAction
    
    
    public void addNotify() {
        super.addNotify();
        //same problem as in 31086, initial focus on Cancel button
        projectNameTextField.requestFocus();
    }
    
    boolean valid( WizardDescriptor wizardDescriptor ) {
        
        if ( projectNameTextField.getText().length() == 0 
            || projectNameTextField.getText().indexOf('/')  > 0         //NOI18N
            || projectNameTextField.getText().indexOf('\\') > 0         //NOI18N
            || projectNameTextField.getText().indexOf(':')  > 0) {      //NOI18N
            wizardDescriptor.putProperty( "WizardPanel_errorMessage", // NOI18N
            NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_IllegalProjectName"));
            return false; // Display name not specified
        }
        File f = new File (projectLocationTextField.getText()).getAbsoluteFile();
        if (getCanonicalFile (f)==null) {
            String message = NbBundle.getMessage (PanelProjectLocationVisual.class,"MSG_IllegalProjectLocation");
            wizardDescriptor.putProperty("WizardPanel_errorMessage", message);
            return false;
        }
        // not allow to create project on unix root folder, see #82339
        File cfl = getCanonicalFile(new File(createdFolderTextField.getText()));
        if (Utilities.isUnix() && cfl != null && cfl.getParentFile().getParent() == null) {
            String message = NbBundle.getMessage (PanelProjectLocationVisual.class,"MSG_ProjectInRootNotSupported");
            wizardDescriptor.putProperty("WizardPanel_errorMessage", message);
            return false;
        }
        
        final File destFolder = new File( createdFolderTextField.getText() ).getAbsoluteFile();
        if (getCanonicalFile (destFolder) == null) {
            String message = NbBundle.getMessage (PanelProjectLocationVisual.class,"MSG_IllegalProjectLocation");
            wizardDescriptor.putProperty("WizardPanel_errorMessage", message);
            return false;
        }

        File projLoc = FileUtil.normalizeFile(destFolder);
        while (projLoc != null && !projLoc.exists()) {
            projLoc = projLoc.getParentFile();
        }
        if (projLoc == null || !projLoc.canWrite()) {
            wizardDescriptor.putProperty( "WizardPanel_errorMessage", // NOI18N
            NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_ProjectFolderReadOnly"));
            return false;
        }
        
        if (FileUtil.toFileObject(projLoc) == null) {
            String message = NbBundle.getMessage (PanelProjectLocationVisual.class,"MSG_IllegalProjectLocation");
            wizardDescriptor.putProperty("WizardPanel_errorMessage", message);
            return false;
        }
        
        File[] kids = destFolder.listFiles();
        if ( destFolder.exists() && kids != null && kids.length > 0) {
            // Folder exists and is not empty
            wizardDescriptor.putProperty( "WizardPanel_errorMessage", // NOI18N
            NbBundle.getMessage(PanelProjectLocationVisual.class,"MSG_ProjectFolderExists"));
            return false;
        }                
        return true;
    }
    
    void store( WizardDescriptor d ) {        
        
        String name = projectNameTextField.getText().trim();
        String location = projectLocationTextField.getText().trim();
        String folder = createdFolderTextField.getText().trim();
        
        d.putProperty( /*XXX Define somewhere */ "projdir", new File( folder )); // NOI18N
        d.putProperty( /*XXX Define somewhere */ "name", name ); // NOI18N
    }
    
    void read (WizardDescriptor settings) {
        File projectLocation = (File) settings.getProperty ("projdir");  //NOI18N
        if (projectLocation == null || projectLocation.getParentFile() == null || !projectLocation.getParentFile().isDirectory ()) {
            projectLocation = ProjectChooser.getProjectsFolder();
        }
        else {
            projectLocation = projectLocation.getParentFile();
        }
        this.projectLocationTextField.setText (projectLocation.getAbsolutePath());
        
        String projectName = (String) settings.getProperty ("name"); //NOI18N
        if (projectName == null) {
            if (this.type == NewRubyProjectWizardIterator.TYPE_APP) {
                int baseCount = FoldersListSettings.getDefault().getNewApplicationCount() + 1;
                String formater = NbBundle.getMessage(PanelSourceFolders.class,"TXT_JavaApplication");
                while ((projectName=validFreeProjectName(projectLocation, formater, baseCount))==null)
                    baseCount++;                
                settings.putProperty (NewRubyProjectWizardIterator.PROP_NAME_INDEX, new Integer(baseCount));
            }
//            else {                
//                int baseCount = FoldersListSettings.getDefault().getNewLibraryCount() + 1;
//                String formater = NbBundle.getMessage(PanelSourceFolders.class,"TXT_JavaLibrary");
//                while ((projectName=validFreeProjectName(projectLocation, formater, baseCount))==null)
//                    baseCount++;                
//                settings.putProperty (NewRubyProjectWizardIterator.PROP_NAME_INDEX, new Integer(baseCount));
//            }            
        }
        this.projectNameTextField.setText (projectName);                
        this.projectNameTextField.selectAll();
    }
        
    void validate (WizardDescriptor d) throws WizardValidationException {
        // nothing to validate
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel createdFolderLabel;
    private javax.swing.JTextField createdFolderTextField;
    private javax.swing.JLabel projectLocationLabel;
    private javax.swing.JTextField projectLocationTextField;
    private javax.swing.JLabel projectNameLabel;
    private javax.swing.JTextField projectNameTextField;
    // End of variables declaration//GEN-END:variables
    
    
    // Private methods ---------------------------------------------------------------------
    
    private static JFileChooser createChooser() {
        JFileChooser chooser = new JFileChooser();
        FileUtil.preventFileChooserSymlinkTraversal(chooser, null);
        chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        chooser.setAcceptAllFileFilterUsed( false );
        chooser.setName( "Select Project Directory" ); // XXX // NOI18N
        return chooser;
    }
    
    private String validFreeProjectName (final File parentFolder, final String formater, final int index) {
        String name = MessageFormat.format (formater, new Object[]{new Integer (index)});                
        File file = new File (parentFolder, name);
        return file.exists() ? null : name;
    }

    // Implementation of DocumentListener --------------------------------------------------
    
    public void changedUpdate( DocumentEvent e ) {
        updateTexts( e );
        if (this.projectNameTextField.getDocument() == e.getDocument()) {
            firePropertyChange (PROP_PROJECT_NAME,null,this.projectNameTextField.getText());
        }
    }
    
    public void insertUpdate( DocumentEvent e ) {
        updateTexts( e );
        if (this.projectNameTextField.getDocument() == e.getDocument()) {
            firePropertyChange (PROP_PROJECT_NAME,null,this.projectNameTextField.getText());
        }
    }
    
    public void removeUpdate( DocumentEvent e ) {
        updateTexts( e );
        if (this.projectNameTextField.getDocument() == e.getDocument()) {
            firePropertyChange (PROP_PROJECT_NAME,null,this.projectNameTextField.getText());
        }
    }
    
    
    /** Handles changes in the Project name and project directory
     */
    private void updateTexts( DocumentEvent e ) {
        Document doc = e.getDocument();
        if ( doc == projectNameTextField.getDocument() || doc == projectLocationTextField.getDocument() ) {
            // Change in the project name
            String projectName = projectNameTextField.getText();
            String projectFolder = projectLocationTextField.getText();
            String projFolderPath = getCanonicalPath(new File(projectFolder));
            if (projFolderPath.endsWith(File.separator)) {
                createdFolderTextField.setText(projFolderPath + projectName);
            } else {
                createdFolderTextField.setText(projFolderPath + File.separator + projectName);
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
    
    static String getCanonicalPath(File f) {
        try {
            return f.getCanonicalPath();
        } catch (IOException e) {
            return "";
        }
    }

}
