/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2004 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.java.j2seproject.ui;

import java.awt.Component;
import java.awt.Image;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.File;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.openide.ErrorManager;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.HelpCtx;
import org.openide.util.lookup.Lookups;
import org.openide.util.actions.SystemAction;
import org.openide.util.actions.NodeAction;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ant.AntArtifact;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.api.java.queries.JavadocForBinaryQuery;
import org.netbeans.spi.project.ant.AntArtifactProvider;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.spi.project.support.ant.PropertyUtils;
import org.netbeans.spi.project.support.ant.ReferenceHelper;
import org.netbeans.modules.java.j2seproject.ui.customizer.J2SEProjectProperties;
import org.netbeans.modules.java.j2seproject.UpdateHelper;



/**
 * ProjectNode represnts a dependent project under the Libraries Node
 * @author Tomas Zezula
 */
class ProjectNode extends AbstractNode {

    private static final String PROJECT_ICON = "org/netbeans/modules/java/j2seproject/ui/resources/projectDependencies.gif";    //NOI18N
    private static final Component CONVERTOR_COMPONENT = new Panel();

    private final Project project;
    private Image cachedIcon;

    ProjectNode (Project project, UpdateHelper helper,ReferenceHelper refHelper, String classPathId, String entryId) {
        super (Children.LEAF, Lookups.fixed(new Object[] {
            project,
            new JavadocProvider(project),
            new Removable(helper,refHelper, classPathId, entryId)}));
        this.project = project;
    }

    public String getDisplayName () {
        ProjectInformation info = (ProjectInformation) this.project.getLookup().lookup(ProjectInformation.class);
        if (info != null) {
            return info.getDisplayName();
        }
        else {
            return NbBundle.getMessage (ProjectNode.class,"TXT_UnknownProjectName");
        }
    }

    public String getName () {
        return this.getDisplayName();
    }

    public Image getIcon(int type) {
        if (cachedIcon == null) {
            ProjectInformation info = (ProjectInformation) this.project.getLookup().lookup(ProjectInformation.class);
            if (info != null) {
                Icon icon = info.getIcon();
                if (icon instanceof ImageIcon) {
                    cachedIcon = ((ImageIcon)icon).getImage();
                }
                else {
                    int height = icon.getIconHeight();
                    int width = icon.getIconWidth();
                    cachedIcon = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
                    icon.paintIcon( CONVERTOR_COMPONENT, cachedIcon.getGraphics(), 0, 0 );
                }
            }
            else {
                cachedIcon = Utilities.loadImage(PROJECT_ICON);
            }
        }
        return cachedIcon;
    }

    public Image getOpenedIcon(int type) {
        return this.getIcon(type);
    }

    public boolean canCopy() {
        return false;
    }

    public Action[] getActions(boolean context) {
        if (!context) {
            return new Action[] {
                SystemAction.get (OpenProjectAction.class),
                SystemAction.get (ShowJavadocAction.class),
                SystemAction.get (RemoveClassPathRootAction.class),
            };
        }
        else {
            return new Action[0];
        }
    }

    public Action getPreferredAction () {
        return getActions(false)[0];
    }

    private static class JavadocProvider implements ShowJavadocAction.JavadocProvider {

        private final Project project;

        JavadocProvider (Project project) {
            this.project = project;
        }


        public boolean hasJavadoc() {
            try {
                AntArtifactProvider provider = (AntArtifactProvider) project.getLookup().lookup(AntArtifactProvider.class);
                if (provider == null) {
                    return false;
                }
                AntArtifact[] artifacts = provider.getBuildArtifacts();
                if (artifacts.length==0) {
                    return false;
                }
                URI artifactLocation = artifacts[0].getArtifactLocation();
                File scriptLocation = artifacts[0].getScriptLocation();
                URL artifactURL = scriptLocation.toURI().resolve(artifactLocation).normalize().toURL();
                if (FileUtil.isArchiveFile(artifactURL)) {
                    artifactURL = FileUtil.getArchiveRoot(artifactURL);
                }
                URL[] urls = JavadocForBinaryQuery.findJavadoc(artifactURL).getRoots();
                return urls.length>0;
            } catch (MalformedURLException mue) {
                return false;
            }
        }

        public void showJavadoc() {
            try {
                AntArtifactProvider provider = (AntArtifactProvider) project.getLookup().lookup(AntArtifactProvider.class);
                AntArtifact[] artifacts = provider.getBuildArtifacts();
                //XXX: J2SEProject has a single artifact, if changed must return FileObject[]
                URI artifactLocation = artifacts[0].getArtifactLocation();
                File scriptLocation = artifacts[0].getScriptLocation();
                URL artifactURL = scriptLocation.toURI().resolve(artifactLocation).normalize().toURL();
                if (FileUtil.isArchiveFile(artifactURL)) {
                    artifactURL = FileUtil.getArchiveRoot(artifactURL);
                }
                URL[] urls = JavadocForBinaryQuery.findJavadoc(artifactURL).getRoots();
                ProjectInformation info = (ProjectInformation) project.getLookup().lookup(ProjectInformation.class);
                ShowJavadocAction.showJavaDoc (ShowJavadocAction.findJavadoc("index.html",urls),info == null ?
                    NbBundle.getMessage (ProjectNode.class,"TXT_UnknownProjectName") : info.getDisplayName());
            } catch (MalformedURLException mue) {
                ErrorManager.getDefault().notify (mue);
            }
        }

    }

    private static class OpenProjectAction extends NodeAction {

        protected void performAction(Node[] activatedNodes) {
            Project[] projects = new Project[activatedNodes.length];
            for (int i=0; i<projects.length;i++) {
                projects[i] = (Project) activatedNodes[i].getLookup().lookup(Project.class);
            }
            OpenProjects.getDefault().open(projects, false);
        }

        protected boolean enable(Node[] activatedNodes) {
            for (int i=0; i<activatedNodes.length; i++) {
                if (activatedNodes[i].getLookup().lookup(Project.class) == null) {
                    return false;
                }
            }
            return true;
        }

        public String getName() {
            return NbBundle.getMessage (ProjectNode.class,"CTL_OpenProject");
        }

        public HelpCtx getHelpCtx() {
            return new HelpCtx (OpenProjectAction.class);
        }

        protected boolean asynchronous() {
            return false;
        }
    }

    private static class Removable implements RemoveClassPathRootAction.Removable {

        private final UpdateHelper helper;
        private final ReferenceHelper refHelper;
        private final String classPathId;
        private final String entryId;

        Removable (UpdateHelper helper, ReferenceHelper refHelper, String classPathId, String entryId) {
            this.helper = helper;
            this.refHelper = refHelper;
            this.classPathId = classPathId;
            this.entryId = entryId;
        }

        public boolean canRemove () {
            //Allow to remove only entries from PROJECT_PROPERTIES, same behaviour as the project customizer
            EditableProperties props = helper.getProperties(AntProjectHelper.PROJECT_PROPERTIES_PATH);
            return props.getProperty (classPathId) != null;
        }

        public void remove() {
            ProjectManager.mutex().writeAccess ( new Runnable () {
               public void run() {
                   EditableProperties props = helper.getProperties(AntProjectHelper.PROJECT_PROPERTIES_PATH);
                   String cp = props.getProperty (classPathId);
                   if (cp != null) {
                       String[] entries = PropertyUtils.tokenizePath(cp);
                       StringBuffer result = new StringBuffer();
                       for (int i=0; i<entries.length; i++) {
                           if (!entryId.equals(J2SEProjectProperties.getAntPropertyName(entries[i]))) {
                               if (result.length()>0) {
                                   result.append(File.pathSeparatorChar);
                               }
                               result.append (entries[i]);
                           }
                       }
                       props.put (classPathId, result.toString());
                       helper.putProperties(AntProjectHelper.PROJECT_PROPERTIES_PATH,props);
                       String ref = "${"+entryId+"}";  //NOI18N
                       if (!RemoveClassPathRootAction.isReferenced (new EditableProperties[] {
                           props,
                           helper.getProperties(AntProjectHelper.PRIVATE_PROPERTIES_PATH)}, ref)) {
                           refHelper.destroyForeignFileReference (ref);
                       }
                   }
               }
           });
        }
    }
}
