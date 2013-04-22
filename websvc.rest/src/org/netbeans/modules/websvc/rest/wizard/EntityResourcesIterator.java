/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
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

package org.netbeans.modules.websvc.rest.wizard;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.project.Project;
import org.netbeans.modules.j2ee.deployment.devmodules.api.J2eeModule;
import org.netbeans.modules.websvc.api.support.LogUtils;
import org.netbeans.modules.websvc.rest.RestUtils;
import org.netbeans.modules.websvc.rest.codegen.EntityResourcesGenerator;
import org.netbeans.modules.websvc.rest.codegen.EntityResourcesGeneratorFactory;
import org.netbeans.modules.websvc.rest.codegen.model.EntityResourceBeanModel;
import org.netbeans.modules.websvc.rest.codegen.model.EntityResourceModelBuilder;
import org.netbeans.modules.websvc.rest.spi.RestSupport;
import org.netbeans.modules.websvc.rest.spi.WebRestSupport;
import org.netbeans.modules.websvc.rest.support.PersistenceHelper.PersistenceUnit;
import org.netbeans.modules.websvc.rest.support.SourceGroupSupport;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Peter Liu
 * @author ads
 */
public class EntityResourcesIterator implements WizardDescriptor.ProgressInstantiatingIterator<WizardDescriptor> {
    
    private static final long serialVersionUID = -1555851385128542149L;
    private int index;
    private transient WizardDescriptor.Panel<?>[] panels;
    private transient RequestProcessor.Task transformTask;
    private WizardDescriptor wizard;
    
    @Override
    public Set instantiate() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set instantiate(ProgressHandle pHandle) throws IOException {
        final Project project = Templates.getProject(wizard);

        String restAppPackage = null;
        String restAppClass = null;
        
        final RestSupport restSupport = project.getLookup().lookup(RestSupport.class);
        if( restSupport instanceof WebRestSupport) {
            Object useJersey = wizard.getProperty(WizardProperties.USE_JERSEY);
            if ( useJersey != null && useJersey.toString().equals("true")){     // NOI18N 
                ((WebRestSupport)restSupport).enableRestSupport( WebRestSupport.RestConfig.DD);
            }
            else {
                restAppPackage = (String) wizard
                        .getProperty(WizardProperties.APPLICATION_PACKAGE);
                restAppClass = (String) wizard
                        .getProperty(WizardProperties.APPLICATION_CLASS);
                if (restAppPackage != null && restAppClass != null) {
                    ((WebRestSupport) restSupport)
                            .enableRestSupport(WebRestSupport.RestConfig.IDE);
                }
            }
        }
        if ( restSupport!= null ){
            restSupport.ensureRestDevelopmentReady();
        }

        FileObject targetFolder = Templates.getTargetFolder(wizard);
        FileObject wizardSrcRoot = (FileObject)wizard.getProperty(
                WizardProperties.TARGET_SRC_ROOT);
        /*
         *  Visual panel is used from several wizards. One of them
         *  has several options for target source roots ( different for 
         *  entities, generation classes ). 
         *  There is special property WizardProperties.TARGET_SRC_ROOT
         *  which is set up by wizard panel. This property should be used
         *  as target source root folder. 
         */
        if ( wizardSrcRoot != null ){
            targetFolder  = wizardSrcRoot;
        }
        
        String targetPackage = SourceGroupSupport.packageForFolder(targetFolder);
        final String resourcePackage = (String) wizard.getProperty(WizardProperties.RESOURCE_PACKAGE);
        String controllerPackage = (String) wizard.getProperty(WizardProperties.CONTROLLER_PACKAGE);
        List<String> entities = (List<String>) wizard.
            getProperty(org.netbeans.modules.j2ee.persistence.wizard.WizardProperties.ENTITY_CLASS);
        final PersistenceUnit pu = (PersistenceUnit) wizard.getProperty(WizardProperties.PERSISTENCE_UNIT);
    
        /* 
         * There should be ALL found entities but they needed to compute closure. 
         * Persistence wizard already has computed closure. So there is no need 
         * in all other entities.
         * Current CTOR of builder and method <code>build</code> is not changed 
         * for now but should be changed later after  review of its usage.
         */
        EntityResourceModelBuilder builder = new EntityResourceModelBuilder(
                project, entities );
        EntityResourceBeanModel model = builder.build();
        final EntityResourcesGenerator generator = EntityResourcesGeneratorFactory.newInstance(project);
        generator.initialize(model, project, targetFolder, targetPackage, 
                resourcePackage, controllerPackage, pu);
        
        // create application config class if required
        final FileObject restAppPack = restAppPackage == null ? null :  
            FileUtil.createFolder(targetFolder, restAppPackage.replace('.', '/'));
        final String appClassName = restAppClass;
        try {
            if ( restAppPack != null && appClassName!= null ){
                RestUtils.createApplicationConfigClass( restAppPack, appClassName);
            }
            RestUtils.disableRestServicesChangeListner(project);
            generator.generate(pHandle);
            restSupport.configure(resourcePackage);
        } catch(Exception iox) {
            Exceptions.printStackTrace(iox);
        } finally {
            RestUtils.enableRestServicesChangeListner(project);
        }

        // logging usage of wizard
        Object[] params = new Object[5];
        params[0] = LogUtils.WS_STACK_JAXRS;
        params[1] = project.getClass().getName();
        J2eeModule j2eeModule = RestUtils.getJ2eeModule(project);
        params[2] = j2eeModule == null ? null : j2eeModule.getModuleVersion()+"(WAR)"; //NOI18N
        params[3] = "REST FROM ENTITY"; //NOI18N
        LogUtils.logWsWizard(params);
        
        return Collections.<DataObject>singleton(DataFolder.findFolder(targetFolder));
    }
    
 
    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
        index = 0;
        WizardDescriptor.Panel<?> secondPanel = new EntitySelectionPanel(
                NbBundle.getMessage(EntityResourcesIterator.class, 
                        "LBL_EntityClasses"), wizard);      // NOI18N
        WizardDescriptor.Panel<?> thirdPanel =new EntityResourcesSetupPanel(
                NbBundle.getMessage(EntityResourcesIterator.class,
                "LBL_RestResourcesAndClasses"), wizard,         // NOI18N
                RestUtils.isJavaEE6AndHigher(Templates.getProject(wizard)) ||
                        RestUtils.hasSpringSupport(Templates.getProject(wizard)));
        panels = new WizardDescriptor.Panel[] { secondPanel, thirdPanel };
        String names[] = new String[] {
            NbBundle.getMessage(EntityResourcesIterator.class, 
                    "LBL_EntityClasses"),                       // NOI18N
            NbBundle.getMessage(EntityResourcesIterator.class, 
                    "LBL_RestResourcesAndClasses")              // NOI18N    
        };
        wizard.putProperty("NewFileWizard_Title",
                NbBundle.getMessage(EntityResourcesIterator.class, 
                        "Templates/WebServices/RestServicesFromEntities"));// NOI18N
        Util.mergeSteps(wizard, panels, names);
    }
    
    public void uninitialize(WizardDescriptor wizard) {
        panels = null;
    }
    
    public WizardDescriptor.Panel current() {
        return panels[index];
    }
    
    public String name() {
        return NbBundle.getMessage(EntityResourcesIterator.class, 
                "LBL_WizardTitle_FromEntity");          // NOI18N
    }
    
    public boolean hasNext() {
        return index < panels.length - 1;
    }
    
    public boolean hasPrevious() {
        return index > 0;
    }
    
    public void nextPanel() {
        if (! hasNext()) throw new NoSuchElementException();
        index++;
    }
    
    public void previousPanel() {
        if (! hasPrevious()) throw new NoSuchElementException();
        index--;
    }
    
    public void addChangeListener(ChangeListener l) {
    }
    
    public void removeChangeListener(ChangeListener l) {
    }
}
