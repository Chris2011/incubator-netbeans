/*
 *                 Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2005 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.web.project.ui.customizer;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.BeanInfo;
import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ButtonModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;
import org.openide.loaders.DataFolder;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.support.ant.PropertyEvaluator;

import org.netbeans.modules.web.project.classpath.ClassPathSupport;
import org.netbeans.modules.web.project.ui.FoldersListSettings;

/** Classes containing code speciic for handling UI of J2SE project classpath 
 *
 * @author Petr Hrebejk, Radko Najman
 */
public class WebClassPathUi {
    
    private WebClassPathUi() {}
           
    // Innerclasses ------------------------------------------------------------
            
    /** Renderer which can be used to render the classpath in lists
     */    
    public static class ClassPathListCellRenderer extends DefaultListCellRenderer {
        
        private static String RESOURCE_ICON_JAR = "org/netbeans/modules/web/project/ui/resources/jar.gif"; //NOI18N
        private static String RESOURCE_ICON_LIBRARY = "org/netbeans/modules/web/project/ui/resources/libraries.gif"; //NOI18N
        private static String RESOURCE_ICON_ARTIFACT = "org/netbeans/modules/web/project/ui/resources/projectDependencies.gif"; //NOI18N
        private static String RESOURCE_ICON_CLASSPATH = "org/netbeans/modules/web/project/ui/resources/referencedClasspath.gif"; //NOI18N
        private static String RESOURCE_ICON_BROKEN_BADGE = "org/netbeans/modules/web/project/ui/resources/brokenProjectBadge.gif"; //NOI18N
        
        
        private static ImageIcon ICON_JAR = new ImageIcon( Utilities.loadImage( RESOURCE_ICON_JAR ) );
        private static ImageIcon ICON_FOLDER = null; 
        private static ImageIcon ICON_LIBRARY = new ImageIcon( Utilities.loadImage( RESOURCE_ICON_LIBRARY ) );
        private static ImageIcon ICON_ARTIFACT  = new ImageIcon( Utilities.loadImage( RESOURCE_ICON_ARTIFACT ) );
        private static ImageIcon ICON_CLASSPATH  = new ImageIcon( Utilities.loadImage( RESOURCE_ICON_CLASSPATH ) );
        private static ImageIcon ICON_BROKEN_BADGE  = new ImageIcon( Utilities.loadImage( RESOURCE_ICON_BROKEN_BADGE ) );
        
        private static ImageIcon ICON_BROKEN_JAR;
        private static ImageIcon ICON_BROKEN_LIBRARY;
        private static ImageIcon ICON_BROKEN_ARTIFACT;
                
        private PropertyEvaluator evaluator;
        
        // Contains well known paths in the WebProject
        private static final Map WELL_KNOWN_PATHS_NAMES = new HashMap();
        static {
            WELL_KNOWN_PATHS_NAMES.put( WebProjectProperties.JAVAC_CLASSPATH, NbBundle.getMessage( WebProjectProperties.class, "LBL_JavacClasspath_DisplayName" ) );
            WELL_KNOWN_PATHS_NAMES.put( WebProjectProperties.JAVAC_TEST_CLASSPATH, NbBundle.getMessage( WebProjectProperties.class,"LBL_JavacTestClasspath_DisplayName") );
            WELL_KNOWN_PATHS_NAMES.put( WebProjectProperties.RUN_TEST_CLASSPATH, NbBundle.getMessage( WebProjectProperties.class, "LBL_RunTestClasspath_DisplayName" ) );
            WELL_KNOWN_PATHS_NAMES.put( WebProjectProperties.BUILD_CLASSES_DIR, NbBundle.getMessage( WebProjectProperties.class, "LBL_BuildClassesDir_DisplayName" ) );            
            WELL_KNOWN_PATHS_NAMES.put( WebProjectProperties.BUILD_TEST_CLASSES_DIR, NbBundle.getMessage (WebProjectProperties.class,"LBL_BuildTestClassesDir_DisplayName") );
        };
                
        public ClassPathListCellRenderer( PropertyEvaluator evaluator ) {
            super();
            this.evaluator = evaluator;
        }
        
        public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            
            ClassPathSupport.Item item = (ClassPathSupport.Item)value;
            
            super.getListCellRendererComponent( list, getDisplayName( item ), index, isSelected, cellHasFocus );
            setIcon( getIcon( item ) );
            setToolTipText( getToolTipText( item ) );
            
            return this;
        }
        
        
        private String getDisplayName( ClassPathSupport.Item item ) {
            
            switch ( item.getType() ) {
                
                case ClassPathSupport.Item.TYPE_LIBRARY:
                    if ( item.isBroken() ) {
                        return NbBundle.getMessage( WebClassPathUi.class, "LBL_MISSING_LIBRARY", getLibraryName( item ) );
                    }
                    else { 
                        return item.getLibrary().getDisplayName();
                    }
                case ClassPathSupport.Item.TYPE_CLASSPATH:
                    String name = (String)WELL_KNOWN_PATHS_NAMES.get( WebProjectProperties.getAntPropertyName( item.getReference() ) );
                    return name == null ? item.getReference() : name;
                case ClassPathSupport.Item.TYPE_ARTIFACT:
                    if ( item.isBroken() ) {
                        return NbBundle.getMessage( WebClassPathUi.class, "LBL_MISSING_PROJECT", getProjectName( item ) );
                    } else {
                        Project p = item.getArtifact().getProject();
                        String projectName;
                        ProjectInformation pi = (ProjectInformation) p.getLookup().lookup(ProjectInformation.class);
                        if (pi != null) {
                            projectName = pi.getDisplayName();
                        } else {
                            projectName = "???";    //NOI18N
                        }
                        return MessageFormat.format(NbBundle.getMessage(WebClassPathUi.class,"MSG_ProjectArtifactFormat"), new Object[] {
                            projectName,
                                    item.getArtifactURI().toString()
                        });
                    }
               case ClassPathSupport.Item.TYPE_JAR:
                    if ( item.isBroken() ) {
                        return NbBundle.getMessage( WebClassPathUi.class, "LBL_MISSING_FILE", getFileRefName( item ) );
                    }
                    else {
                        return item.getFile().getPath();
                    }
            }
            
            return item.getReference(); // XXX            
        }
        
        static Icon getIcon( ClassPathSupport.Item item ) {
            
            switch ( item.getType() ) {
                
                case ClassPathSupport.Item.TYPE_LIBRARY:
                    if ( item.isBroken() ) {
                        if ( ICON_BROKEN_LIBRARY == null ) {
                            ICON_BROKEN_LIBRARY = new ImageIcon( Utilities.mergeImages( ICON_LIBRARY.getImage(), ICON_BROKEN_BADGE.getImage(), 7, 7 ) );
                        }
                        return ICON_BROKEN_LIBRARY;
                    }
                    else {
                        return ICON_LIBRARY;
                    }
                case ClassPathSupport.Item.TYPE_ARTIFACT:
                    if ( item.isBroken() ) {
                        if ( ICON_BROKEN_ARTIFACT == null ) {
                            ICON_BROKEN_ARTIFACT = new ImageIcon( Utilities.mergeImages( ICON_ARTIFACT.getImage(), ICON_BROKEN_BADGE.getImage(), 7, 7 ) );
                        }
                        return ICON_BROKEN_ARTIFACT;
                    }
                    else {
                        return ICON_ARTIFACT;
                    }
                case ClassPathSupport.Item.TYPE_JAR:
                    if ( item.isBroken() ) {
                        if ( ICON_BROKEN_JAR == null ) {
                            ICON_BROKEN_JAR = new ImageIcon( Utilities.mergeImages( ICON_JAR.getImage(), ICON_BROKEN_BADGE.getImage(), 7, 7 ) );
                        }
                        return ICON_BROKEN_JAR;
                    }
                    else {
                        File file = item.getFile();
                        return file.isDirectory() ? getFolderIcon() : ICON_JAR;
                    }
                case ClassPathSupport.Item.TYPE_CLASSPATH:
                    return ICON_CLASSPATH;
                
            }
            
            return null; // XXX 
        }
        
        private String getToolTipText( ClassPathSupport.Item item ) {
            if ( item.isBroken() && 
                 ( item.getType() == ClassPathSupport.Item.TYPE_JAR || 
                   item.getType() == ClassPathSupport.Item.TYPE_ARTIFACT )  ) {
                return evaluator.evaluate( item.getReference() );
            }
            
            return getDisplayName( item ); // XXX
        }
        
        private static ImageIcon getFolderIcon() {
        
            if ( ICON_FOLDER == null ) {
                FileObject root = Repository.getDefault().getDefaultFileSystem().getRoot();
                DataFolder dataFolder = DataFolder.findFolder( root );
                ICON_FOLDER = new ImageIcon( dataFolder.getNodeDelegate().getIcon( BeanInfo.ICON_COLOR_16x16 ) );            
            }

            return ICON_FOLDER;   
        }
        
        private String getProjectName( ClassPathSupport.Item item ) {
            String ID = item.getReference();
            // something in the form of "${reference.project-name.id}"
            return ID.substring(12, ID.indexOf(".", 12)); // NOI18N
        }

        private String getLibraryName( ClassPathSupport.Item item ) {
            String ID = item.getReference();
            // something in the form of "${libs.junit.classpath}"
            return ID.substring(7, ID.indexOf(".classpath")); // NOI18N
        }

        private String getFileRefName( ClassPathSupport.Item item ) {
            String ID = item.getReference();        
            // something in the form of "${file.reference.smth.jar}"
            return ID.substring(17, ID.length()-1);
        }


        
    }
    
    public static class ClassPathTableCellItemRenderer extends DefaultTableCellRenderer {
        
        private ClassPathListCellRenderer renderer;
        private TableCellRenderer booleanRenderer;
        
        public ClassPathTableCellItemRenderer(PropertyEvaluator evaluator) {
            renderer = new ClassPathListCellRenderer(evaluator);
        }
        
        public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
            
            if (value instanceof ClassPathSupport.Item) {
                ClassPathSupport.Item item = (ClassPathSupport.Item)value;
                setIcon( renderer.getIcon( item ) );
                setToolTipText( renderer.getToolTipText( item ) );
                return super.getTableCellRendererComponent(table, renderer.getDisplayName( item ), isSelected, false, row, column);
            } else {
                if (value instanceof Boolean && booleanRenderer != null)
                    return booleanRenderer.getTableCellRendererComponent( table, value, isSelected, false, row, column );
                else {
                    setIcon( null );
                    return super.getTableCellRendererComponent( table, value, isSelected, false, row, column );
                }
            }
        }
        
        public void setBooleanRenderer(TableCellRenderer booleanRenderer) {
            this.booleanRenderer = booleanRenderer;
        }
    }

    public static class EditMediator implements ActionListener, ListSelectionListener {
                
        private final Project project;
        private final ListComponent list;
        private final DefaultListModel listModel;
        private final ListSelectionModel selectionModel;
        private final ButtonModel addJar;
        private final ButtonModel addLibrary;
        private final ButtonModel addAntArtifact;
        private final ButtonModel remove;
        private final ButtonModel moveUp;
        private final ButtonModel moveDown;
        private final boolean includeNewFilesInDeployment;
                    
        public EditMediator( Project project,
                             ListComponent list,
                             ButtonModel addJar,
                             ButtonModel addLibrary, 
                             ButtonModel addAntArtifact,
                             ButtonModel remove, 
                             ButtonModel moveUp,
                             ButtonModel moveDown, 
                             boolean includeNewFilesInDeployment) {
                             
            // Remember all buttons
            
            this.list = list;
            
            if ( !( list.getModel() instanceof DefaultListModel ) ) {
                throw new IllegalArgumentException( "The list's model has to be of class DefaultListModel" ); // NOI18N
            }
            
            this.listModel = (DefaultListModel)list.getModel();
            this.selectionModel = list.getSelectionModel();
            
            this.addJar = addJar;
            this.addLibrary = addLibrary;
            this.addAntArtifact = addAntArtifact;
            this.remove = remove;
            this.moveUp = moveUp;
            this.moveDown = moveDown;

            this.project = project;
            this.includeNewFilesInDeployment = includeNewFilesInDeployment;
        }

        public static void register(Project project,
                                    ListComponent list,
                                    ButtonModel addJar,
                                    ButtonModel addLibrary, 
                                    ButtonModel addAntArtifact,
                                    ButtonModel remove, 
                                    ButtonModel moveUp,
                                    ButtonModel moveDown, 
                                    boolean includeNewFilesInDeployment) {    
            
            EditMediator em = new EditMediator( project, 
                                                list,
                                                addJar,
                                                addLibrary, 
                                                addAntArtifact,
                                                remove,    
                                                moveUp,
                                                moveDown,
                                                includeNewFilesInDeployment );
            
            // Register the listener on all buttons
            addJar.addActionListener( em ); 
            addLibrary.addActionListener( em );
            addAntArtifact.addActionListener( em );
            remove.addActionListener( em );
            moveUp.addActionListener( em );
            moveDown.addActionListener( em );
            // On list selection
            em.selectionModel.addListSelectionListener( em );
            // Set the initial state of the buttons
            em.valueChanged( null );
        }
    
        // Implementation of ActionListener ------------------------------------
        
        /** Handles button events
         */        
        public void actionPerformed( ActionEvent e ) {
            
            Object source = e.getSource();
            
            if ( source == addJar ) { 
                // Let user search for the Jar file
                JFileChooser chooser = new JFileChooser();
                FileUtil.preventFileChooserSymlinkTraversal(chooser, null);
                chooser.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
                chooser.setMultiSelectionEnabled( true );
                chooser.setDialogTitle( NbBundle.getMessage( WebClassPathUi.class, "LBL_AddJar_DialogTitle" ) ); // NOI18N
                chooser.setFileFilter( new SimpleFileFilter( 
                    NbBundle.getMessage( WebClassPathUi.class, "LBL_ZipJarFolderFilter" ),                  // NOI18N
                    new String[] {"ZIP","JAR"} ) );                                                                 // NOI18N 
                chooser.setAcceptAllFileFilterUsed( false );
                File curDir = FoldersListSettings.getDefault().getLastUsedClassPathFolder(); 
                chooser.setCurrentDirectory (curDir);
                int option = chooser.showOpenDialog( SwingUtilities.getWindowAncestor( list.getComponent() ) ); // Show the chooser
                
                if ( option == JFileChooser.APPROVE_OPTION ) {
                    
                    File files[] = chooser.getSelectedFiles();
                    int[] newSelection = ClassPathUiSupport.addJarFiles( listModel, list.getSelectedIndices(), files);
                    list.setSelectedIndices( newSelection );
                    curDir = FileUtil.normalizeFile(chooser.getCurrentDirectory());
                    FoldersListSettings.getDefault().setLastUsedClassPathFolder(curDir);
                }
            }
            else if ( source == addLibrary ) {
                Set/*<Library>*/includedLibraries = new HashSet ();
                for (int i=0; i< listModel.getSize(); i++) {
                    ClassPathSupport.Item item = (ClassPathSupport.Item) listModel.get(i);
                    if (item.getType() == ClassPathSupport.Item.TYPE_LIBRARY) {
                        includedLibraries.add( item.getLibrary() );
                    }
                }
                Object[] options = new Object[] {
                    new JButton (NbBundle.getMessage (WebClassPathUi.class,"LBL_AddLibrary")),
                    DialogDescriptor.CANCEL_OPTION
                };
                ((JButton)options[0]).setEnabled(false);
                ((JButton)options[0]).getAccessibleContext().setAccessibleDescription (NbBundle.getMessage (WebClassPathUi.class,"AD_AddLibrary"));
                LibrariesChooser panel = new LibrariesChooser ((JButton)options[0], includedLibraries);
                DialogDescriptor desc = new DialogDescriptor(panel,NbBundle.getMessage( WebClassPathUi.class, "LBL_CustomizeCompile_Classpath_AddLibrary" ),
                    true, options, options[0], DialogDescriptor.DEFAULT_ALIGN,null,null);
                Dialog dlg = DialogDisplayer.getDefault().createDialog(desc);
                dlg.setVisible(true);
                if (desc.getValue() == options[0]) {
                   int[] newSelection = ClassPathUiSupport.addLibraries( listModel, list.getSelectedIndices(), panel.getSelectedLibraries(), includedLibraries);
                   list.setSelectedIndices( newSelection );
                }
                dlg.dispose();
            }
            else if ( source == addAntArtifact ) { 
                AntArtifactChooser.ArtifactItem artifactItems[] = AntArtifactChooser.showDialog(JavaProjectConstants.ARTIFACT_TYPE_JAR, project, list.getComponent().getParent());
                if (artifactItems != null) {
                    int[] newSelection = ClassPathUiSupport.addArtifacts( listModel, list.getSelectedIndices(), artifactItems);
                    list.setSelectedIndices( newSelection );
                }
            }
            else if ( source == remove ) { 
                int[] newSelection = ClassPathUiSupport.remove( listModel, list.getSelectedIndices() );
                list.setSelectedIndices( newSelection );
            }
            else if ( source == moveUp ) {
                int[] newSelection = ClassPathUiSupport.moveUp( listModel, list.getSelectedIndices() );
                list.setSelectedIndices( newSelection );
            }
            else if ( source == moveDown ) {
                int[] newSelection = ClassPathUiSupport.moveDown( listModel, list.getSelectedIndices() );
                list.setSelectedIndices( newSelection );
            }
        }    
        
        
        /** Handles changes in the selection
         */        
        public void valueChanged( ListSelectionEvent e ) {
            
            // remove enabled only if selection is not empty
            boolean canRemove = false;
            // and when the selection does not contain unremovable item
            if ( selectionModel.getMinSelectionIndex() != -1 ) {
                canRemove = true;
                int iMin = selectionModel.getMinSelectionIndex();
                int iMax = selectionModel.getMinSelectionIndex();
                for ( int i = iMin; i <= iMax; i++ ) {
                    
                    if ( selectionModel.isSelectedIndex( i ) ) {
                        ClassPathSupport.Item item = (ClassPathSupport.Item)listModel.get( i );
                        if ( item.getType() == ClassPathSupport.Item.TYPE_CLASSPATH ) {
                            canRemove = false;
                            break;
                        }
                    }
                }
            }
            
            // addJar allways enabled            
            // addLibrary allways enabled            
            // addArtifact allways enabled            
            // editButton.setEnabled( edit );            
            remove.setEnabled( canRemove );
            moveUp.setEnabled( ClassPathUiSupport.canMoveUp( selectionModel ) );
            moveDown.setEnabled( ClassPathUiSupport.canMoveDown( selectionModel, listModel.getSize() ) );       
            
        }
        
        public interface ListComponent {
            public Component getComponent();
            public int[] getSelectedIndices();
            public void setSelectedIndices(int[] indices);
            public DefaultListModel getModel();
            public ListSelectionModel getSelectionModel();
        }
        
        private static final class JListListComponent implements ListComponent {
            private JList list;
            private DefaultListModel model;
            
            public JListListComponent(JList list) {
                this.list = list;
                this.model = model;
            }
            
            public Component getComponent() {
                return list;
            }
            
            public int[] getSelectedIndices() {
                return list.getSelectedIndices();
            }
            
            public void setSelectedIndices(int[] indices) {
                list.setSelectedIndices(indices);
            }
            
            public DefaultListModel getModel() {
                return (DefaultListModel)list.getModel();
            }
            
            public ListSelectionModel getSelectionModel() {
                return list.getSelectionModel();
            }
        }
        
        private static final class JTableListComponent implements ListComponent {
            private JTable table;
            private DefaultListModel model;
            
            public JTableListComponent(JTable table, DefaultListModel model) {
                this.table = table;
                this.model = model;
            }
            
            public Component getComponent() {
                return table;
            }
            
            public int[] getSelectedIndices() {
                return table.getSelectedRows();
            }
            
            public void setSelectedIndices(int[] indices) {
                table.clearSelection();
                for (int i = 0; i < indices.length; i++) {
                    table.addRowSelectionInterval(indices[i], indices[i]);
                }
            }
            
            public DefaultListModel getModel() {
                return model;
            }
            
            public ListSelectionModel getSelectionModel() {
                return table.getSelectionModel();
            }
        }
        
        public static ListComponent createListComponent(JList list) {
            return new JListListComponent(list);
        }
        
        public static ListComponent createListComponent(JTable table, DefaultListModel model) {
            return new JTableListComponent(table, model);
        }
    }
    
    private static class SimpleFileFilter extends FileFilter {

        private String description;
        private Collection extensions;


        public SimpleFileFilter (String description, String[] extensions) {
            this.description = description;
            this.extensions = Arrays.asList(extensions);
        }

        public boolean accept(File f) {
            if (f.isDirectory())
                return true;
            String name = f.getName();
            int index = name.lastIndexOf('.');   //NOI18N
            if (index <= 0 || index==name.length()-1)
                return false;
            String extension = name.substring (index+1).toUpperCase();
            return this.extensions.contains(extension);
        }

        public String getDescription() {
            return this.description;
        }
    }
        
}
