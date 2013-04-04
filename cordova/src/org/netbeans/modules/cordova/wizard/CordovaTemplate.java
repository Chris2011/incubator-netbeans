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
package org.netbeans.modules.cordova.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.prefs.Preferences;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import javax.swing.text.StyledDocument;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.modules.cordova.CordovaPerformer;
import org.netbeans.modules.cordova.CordovaPlatform;
import org.netbeans.modules.cordova.project.CordovaPanel;
import org.netbeans.modules.cordova.updatetask.SourceConfig;
import org.netbeans.modules.web.clientproject.spi.ClientProjectExtender;
import org.netbeans.modules.web.clientproject.spi.SiteTemplateImplementation;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;
import org.openide.cookies.EditorCookie;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.ChangeSupport;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 */
@NbBundle.Messages({"LBL_Name=PhoneGap Sample Project"})
@ServiceProvider(service = SiteTemplateImplementation.class, position = 1000)
public class CordovaTemplate implements SiteTemplateImplementation {

    @Override
    public String getName() {
        return Bundle.LBL_Name();
    }

    @Override
    public void apply(FileObject projectDir, ProjectProperties projectProperties, ProgressHandle handle) throws IOException {
        try {
            FileObject p = FileUtil.createFolder(projectDir, projectProperties.getSiteRootFolder());
            File examplesFolder = new File(CordovaPlatform.getDefault().getSdkLocation() + "/lib/android/example/assets/www");//NOI18N
            FileObject examples = FileUtil.toFileObject(examplesFolder);
            FileObject index = FileUtil.copyFile(examples.getFileObject("index.html"), p, "index");//NOI18N
            FileUtil.copyFile(examples.getFileObject("main.js"), p, "main");//NOI18N
            FileUtil.copyFile(examples.getFileObject("master.css"), p, "master");//NOI18N
            FileUtil.copyFile(examples.getFileObject("js/index.js"), FileUtil.createFolder(p, "js"), "index");//NOI18N
            FileUtil.copyFile(examples.getFileObject("css/index.css"), FileUtil.createFolder(p, "css"), "index");//NOI18N
            FileUtil.copyFile(examples.getFileObject("img/cordova.png"), FileUtil.createFolder(p, "img"), "cordova");//NOI18N
            FileUtil.copyFile(examples.getFileObject("img/logo.png"), FileUtil.createFolder(p, "img"), "logo");//NOI18N
            DataObject find = DataObject.find(index);
            EditorCookie c = find.getLookup().lookup(EditorCookie.class);
            StyledDocument openDocument = c.openDocument();
            String version = CordovaPlatform.getDefault().getVersion().toString();
            final String cordova = "cordova-" + version + ".js";//NOI18N
            int start = openDocument.getText(0, openDocument.getLength()).indexOf(cordova);
            openDocument.remove(start, cordova.length());
            openDocument.insertString(start, "js/libs/Cordova-" + version + "/" + cordova, null);//NOI18N
            find.getCookie(SaveCookie.class).save();

        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (Throwable ex) {
            
        }
    }


    @Override
    public String getDescription() {
        return Bundle.LBL_Name();
    }

    @Override
    public boolean isPrepared() {
        return true;
    }

    @Override
    public void prepare() throws IOException {
    }

    @Override
    public Collection<String> supportedLibraries() {
        return Collections.singletonList("Cordova");//NOI18N
    }

    @Override
    public void configure(ProjectProperties projectProperties) {
        projectProperties.setConfigFolder("config");//NOI18N
        projectProperties.setSiteRootFolder("public_html");//NOI18N
        projectProperties.setTestFolder("test");//NOI18N
    }

    @Override
    public String getId() {
        return "CORDOVA"; // NOI18N
    }

    @ServiceProvider(service=ClientProjectExtender.class)
    public static class CordovaExtender implements ClientProjectExtender {

        private boolean enabled;
        private CordovaWizardPanel panel;

        /**
         * Get the value of enabled
         *
         * @return the value of enabled
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * Set the value of enabled
         *
         * @param enabled new value of enabled
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }


        @Override
        public Panel<WizardDescriptor>[] createWizardPanels() {
            return new Panel[]{panel=new CordovaWizardPanel(this)};
        }

        @Override
        @NbBundle.Messages({
            "LBL_iPhoneSimulator=iPhone Simulator",
            "LBL_AndroidEmulator=Android Emulator",
            "LBL_AndroidDevice=Android Device"
        })
        public void apply(FileObject projectRoot, FileObject siteRoot, String librariesPath) {
            if (!isEnabled()) {
                return;
            }
            try {
                String version = CordovaPlatform.getDefault().getVersion().toString();

                final String sdkLocation = CordovaPlatform.getDefault().getSdkLocation();
                File lib = new File(sdkLocation + "/lib/android/cordova-"+version +".js");//NOI18N
                FileObject libFo = FileUtil.toFileObject(lib);
                FileObject createFolder = FileUtil.createFolder(siteRoot, librariesPath + "/Cordova-" + version);//NOI18N
                FileUtil.copyFile(libFo, createFolder, "cordova-" + version);//NOI18N
                final Project project = FileOwnerQuery.getOwner(projectRoot);
                
                Preferences preferences = ProjectUtils.getPreferences(project, CordovaPlatform.class, true);
                preferences.put("phonegap", "true");
                if (panel != null) {
                    
                    try {
                        final SourceConfig config = CordovaPerformer.getConfig(project);
                        config.setId(panel.getPackageName());
                        config.save();

                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                    CordovaPerformer.getDefault().createPlatforms(project);
                    panel = null;
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } catch (Throwable ex) {
                Exceptions.printStackTrace(ex);
            }
        }

    }

    private static class CordovaWizardPanel implements Panel<WizardDescriptor>, PropertyChangeListener  {

        private CordovaExtender ext;
        private WizardDescriptor wizardDescriptor;
        public CordovaWizardPanel(CordovaExtender ext) {
            CordovaPlatform.getDefault().addPropertyChangeListener(this);
            this.ext = ext;
        }

        private CordovaPanel panel;
        private transient final ChangeSupport changeSupport = new ChangeSupport(this);

        @Override
        public void addChangeListener(ChangeListener listener) {
            changeSupport.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ChangeListener listener) {
            changeSupport.removeChangeListener(listener);
        }

        @Override
        public JComponent getComponent() {
            if (panel == null) {
                panel = new CordovaPanel(ext);
                panel.addPropertyChangeListener(this);
            }
            return panel;
        }


        @Override
        public HelpCtx getHelp() {
            return new HelpCtx("org.netbeans.modules.cordova.template.CordovaTemplate$CordovaWizardPanel");//NOI18N
        }

        @Override
        public void readSettings(WizardDescriptor wizardDescriptor) {
            this.wizardDescriptor = wizardDescriptor;
            SiteTemplateImplementation template = (SiteTemplateImplementation) wizardDescriptor.getProperty("SITE_TEMPLATE");//NOI18N
            panel.setPanelEnabled(template instanceof CordovaTemplate);
        }

        @Override
        public void storeSettings(WizardDescriptor settings) {
        }

        @Override
        @NbBundle.Messages("ERR_MobilePlatforms=Mobile Platforms are not configured")
        public boolean isValid() {
            final String sdkLocation = CordovaPlatform.getDefault().getSdkLocation();
            if (sdkLocation == null && ext.isEnabled()) {
                setErrorMessage(Bundle.ERR_MobilePlatforms());
                return false;
            }
            setErrorMessage("");
            return true;
        }

        private void setErrorMessage(String message) {
            wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, message);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            changeSupport.fireChange();
            panel.update();
        }

        private String getPackageName() {
            return panel.getPackageName();
        }
    }
}
