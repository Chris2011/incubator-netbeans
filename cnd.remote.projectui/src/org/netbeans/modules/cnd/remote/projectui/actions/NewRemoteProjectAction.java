/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */

package org.netbeans.modules.cnd.remote.projectui.actions;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.modules.cnd.api.remote.ServerList;
import org.netbeans.modules.cnd.api.remote.ServerRecord;
import org.netbeans.modules.cnd.makeproject.api.wizards.WizardConstants;
import org.netbeans.modules.cnd.remote.actions.base.RemoteOpenActionBase;
import org.netbeans.modules.cnd.remote.projectui.wizard.ide.NewProjectWizard;
import org.netbeans.modules.cnd.remote.projectui.wizard.ide.OpenProjectList;
import org.netbeans.modules.cnd.remote.projectui.wizard.ide.OpenProjectListSettings;
import org.netbeans.modules.cnd.remote.projectui.wizard.ide.ProjectTemplatePanel;
import org.netbeans.modules.cnd.remote.projectui.wizard.ide.ProjectUtilities;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironment;
import org.netbeans.modules.nativeexecution.api.util.ConnectionManager;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.ErrorManager;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;
import org.openide.util.WeakListeners;
import org.openide.windows.WindowManager;


@ActionID(id = "org.netbeans.modules.cnd.remote.actions.NewRemoteProjectAction", category = "Project")
@ActionRegistration(iconInMenu = true, displayName = "#NewRemoteProjectAction.submenu.title")
@ActionReferences({
    //@ActionReference(path = "Menu/File", position = 510),
    @ActionReference(path = "Toolbars/Remote", position = 1000)
})
public class NewRemoteProjectAction extends RemoteOpenActionBase implements PropertyChangeListener {

    private ImageIcon icon;
    private boolean isPreselect = false;
    private RequestProcessor.Task bodyTask;
    private ExecutionEnvironment env;
    private static final boolean ALLOW_LOCAL = false;
    private volatile boolean running = false;
    
    public NewRemoteProjectAction() {
        super(NbBundle.getMessage(NewRemoteProjectAction.class, "NewRemoteProjectAction.submenu.title"), ALLOW_LOCAL);
        icon = ImageUtilities.loadImageIcon("org/netbeans/modules/cnd/remote/resources/newProject.png", false); //NOI18N
        putValue("iconBase","org/netbeans/modules/cnd/remote/resources/newProject.png"); //NOI18N
        bodyTask = new RequestProcessor( "NewProjectBody" ).create( new Runnable () { // NOI18N
            @Override
            public void run () {
                try {
                    doPerform ();
                } finally {
                    running = false;
                }
            }
        });
        if (!ALLOW_LOCAL) {
            ServerList.addPropertyChangeListener(WeakListeners.propertyChange(this, this));
        }
    }

    @Override
    protected Icon getIcon() {
        return icon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand != null && !actionCommand.isEmpty()) {
            super.actionPerformed(e);
        } else {
            ExecutionEnvironment executionEnvironment = ServerList.getDefaultRecord().getExecutionEnvironment();
            if (!ALLOW_LOCAL && executionEnvironment.isLocal()) {
                return;
            }
            actionPerformed(executionEnvironment);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (ServerList.PROP_DEFAULT_RECORD.equals(evt.getPropertyName())){
            setEnabled(!ServerList.getDefaultRecord().getExecutionEnvironment().isLocal());
        }
    }

    @Override
    protected void actionPerformed(ExecutionEnvironment env) {
        this.env = env;
        if (!running) {
            running = true;
            bodyTask.schedule(0);
        }
        
        //if ( "waitFinished".equals( evt.getActionCommand() ) ) {
        //    bodyTask.waitFinished();
        //}
    }

    @Override
    protected String getSubmenuTitle() {
        return NbBundle.getMessage(NewRemoteProjectAction.class, "NewRemoteProjectAction.submenu.title");
    }

    @Override
    protected String getItemTitle(ServerRecord record) {
        return NbBundle.getMessage(NewRemoteProjectAction.class, "NewRemoteProjectAction.item.title", record.getDisplayName());
    }        
        
    NewProjectWizard prepareWizardDescriptor(FileObject fo) {
        NewProjectWizard wizard = new NewProjectWizard(fo, env);
            
        if ( isPreselect ) {
            // XXX make the properties public ?
            wizard.putProperty(ProjectTemplatePanel.PRESELECT_CATEGORY, getValue(ProjectTemplatePanel.PRESELECT_CATEGORY));
            wizard.putProperty(ProjectTemplatePanel.PRESELECT_TEMPLATE, getValue(ProjectTemplatePanel.PRESELECT_TEMPLATE));
        }
        else {
            wizard.putProperty(ProjectTemplatePanel.PRESELECT_CATEGORY, null);
            wizard.putProperty(ProjectTemplatePanel.PRESELECT_TEMPLATE, null);
        }

        wizard.putProperty(ProjectTemplatePanel.PRESELECT_TEMPLATE, null);
        
        wizard.putProperty(WizardConstants.PROPERTY_REMOTE_FILE_SYSTEM_ENV, env);
        
        FileObject folder = (FileObject) getValue(CommonProjectActions.EXISTING_SOURCES_FOLDER);
        if (folder != null) {
            wizard.putProperty(CommonProjectActions.EXISTING_SOURCES_FOLDER, folder);
        }
        return wizard;
    }
    
    private void doPerform () {
        FileObject fo = FileUtil.getConfigFile( "CND/RemoteProject" ); //NOI18N
        final NewProjectWizard wizard = prepareWizardDescriptor(fo);
        
        Runnable createWorker = new Runnable() {
            @Override
            public void run() {
                try {
                    
                    Set newObjects = wizard.instantiate();
                    // #75960 - test if any folder was created during the wizard and if yes and it's empty delete it
                    Preferences prefs = NbPreferences.forModule(OpenProjectListSettings.class);
                    String nbPrjDirPath = prefs.get(OpenProjectListSettings.PROP_CREATED_PROJECTS_FOLDER, null);
                    prefs.remove(OpenProjectListSettings.PROP_CREATED_PROJECTS_FOLDER);
                    if (nbPrjDirPath != null) {
                        File prjDir = new File(nbPrjDirPath);
                        if (prjDir.exists() && prjDir.isDirectory() && prjDir.listFiles() != null && prjDir.listFiles().length == 0) {
                            prjDir.delete();
                        }
                    }
                    
                    //#69618: the non-project cache may contain a project folder listed in newObjects:
                    ProjectManager.getDefault().clearNonProjectCache();
                    ProjectUtilities.WaitCursor.show();
                    
                    if ( newObjects != null && !newObjects.isEmpty() ) {
                        // First. Open all returned projects in the GUI.

                        LinkedList<DataObject> filesToOpen = new LinkedList<DataObject>();
                        List<Project> projectsToOpen = new LinkedList<Project>();

                        for( Iterator it = newObjects.iterator(); it.hasNext(); ) {
                            Object obj = it.next();
                            FileObject newFo;
                            DataObject newDo;
                            if (obj instanceof DataObject) {
                                newDo = (DataObject) obj;
                                newFo = newDo.getPrimaryFile();
                            } else if (obj instanceof FileObject) {
                                newFo = (FileObject) obj;
                                try {
                                    newDo = DataObject.find(newFo);
                                } catch (DataObjectNotFoundException e) {
                                    ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, e);
                                    continue;
                                }
                            } else {
                                ErrorManager.getDefault().log(ErrorManager.WARNING, "Found unrecognized object " + obj + " in result set from instantiate()"); //NOI18N
                                continue;
                            }
                            // check if it's a project directory
                            if (newFo.isFolder()) {
                                try {
                                    Project p = ProjectManager.getDefault().findProject(newFo);
                                    if (p != null) {
                                        // It is a project, so schedule it to open:
                                        projectsToOpen.add(p);
                                    } else {
                                        // Just a folder to expand
                                        filesToOpen.add(newDo);
                                    }
                                } catch (IOException e) {
                                    ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, e);
                                    continue;
                                }
                            } else {
                                filesToOpen.add(newDo);
                            }
                        }
                        
                        Project lastProject = projectsToOpen.size() > 0 ? projectsToOpen.get(0) : null;
                        
                        Project mainProject = null;
                        if (Templates.getDefinesMainProject(wizard) && lastProject != null) {
                            mainProject = lastProject;
                        }
                        
                        OpenProjectList.getDefault().open(projectsToOpen.toArray(new Project[projectsToOpen.size()]), false, true, mainProject);
                        //OpenProjects.getDefault().open(new Project[]{mainProject}, true);
                        
                        // Show the project tab to show the user we did something
                        ProjectUtilities.makeProjectTabVisible();
                        
                        if (lastProject != null) {
                            // Just select and expand the project node
                            ProjectUtilities.selectAndExpandProject(lastProject);
                        }
                        // Second open the files
                        for (DataObject d : filesToOpen) { // Open the files
                            ProjectUtilities.openAndSelectNewObject(d);
                        }
                        
                    }
                    ProjectUtilities.WaitCursor.hide();
                } catch ( IOException e ) {
                    ErrorManager.getDefault().notify( ErrorManager.INFORMATIONAL, e );
                }
            }
        };
        
        // always connect first
        try {
            ConnectionManager.getInstance().connectTo(env);
            SwingUtilities.invokeLater(createWorker);
        } catch (final IOException ex) {
            //LOGGER.log(Level.INFO, "Error connecting " + env, ex);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(WindowManager.getDefault().getMainWindow(), 
                    NbBundle.getMessage(NewRemoteProjectAction.class, "ErrorConnectingHost", env.getDisplayName(), ex.getMessage()));
                }
            });
        } catch (ConnectionManager.CancellationException ex) {
            // don't report CancellationException
        }
    }
}
