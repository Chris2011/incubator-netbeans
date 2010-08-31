/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2010 Sun Microsystems, Inc.
 */

package org.netbeans.modules.cnd.discovery.projectimport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.project.Project;
import org.netbeans.modules.cnd.api.model.CsmDeclaration;
import org.netbeans.modules.cnd.api.model.CsmListeners;
import org.netbeans.modules.cnd.api.model.CsmModel;
import org.netbeans.modules.cnd.api.model.CsmModelAccessor;
import org.netbeans.modules.cnd.api.model.CsmOffsetableDeclaration;
import org.netbeans.modules.cnd.api.model.CsmProgressAdapter;
import org.netbeans.modules.cnd.api.model.CsmProgressListener;
import org.netbeans.modules.cnd.api.model.CsmProject;
import org.netbeans.modules.cnd.api.project.NativeProject;
import org.netbeans.modules.cnd.api.remote.HostInfoProvider;
import org.netbeans.modules.cnd.api.toolchain.CompilerSet;
import org.netbeans.modules.cnd.api.toolchain.CompilerSetManager;
import org.netbeans.modules.cnd.discovery.api.DiscoveryExtensionInterface.Applicable;
import org.netbeans.modules.cnd.discovery.api.DiscoveryExtensionInterface.Position;
import org.netbeans.modules.cnd.discovery.wizard.DiscoveryExtension;
import org.netbeans.modules.cnd.discovery.wizard.DiscoveryWizardDescriptor;
import org.netbeans.modules.cnd.makeproject.api.configurations.ConfigurationDescriptorProvider;
import org.netbeans.modules.cnd.makeproject.api.configurations.MakeConfiguration;
import org.netbeans.modules.cnd.makeproject.api.configurations.MakeConfigurationDescriptor;
import org.netbeans.modules.cnd.modelimpl.csm.core.ModelImpl;
import org.netbeans.modules.cnd.modelimpl.csm.core.ProjectBase;
import org.netbeans.modules.cnd.modelimpl.csm.core.Utils;
import org.netbeans.modules.cnd.modelutil.CsmUtilities;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironment;
import org.netbeans.modules.nativeexecution.api.util.HostInfoUtils;
import org.netbeans.modules.nativeexecution.api.util.MacroExpanderFactory;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Alexander Simon
 */
public class ImportExecutable {
    private static final boolean CREATE_DEPENDENCIES = true;
    private static final boolean IMPORT_DEPENDENCIES = true;
    private static final boolean DLL_FILE_SEARCH = false;
    private static final RequestProcessor RP = new RequestProcessor(ImportExecutable.class.getName(), 2);
    private final Map<String, Object> map;
    private final Project lastSelectedProject;
    private final String functionToOpen;
    private CreateDependencies cd;

    public ImportExecutable(Map<String, Object> map, Project lastSelectedProject, String functionToOpen) {
        this.map = map;
        this.lastSelectedProject = lastSelectedProject;
        this.functionToOpen = functionToOpen;
    }

    public void process(final DiscoveryExtension extension){
        switchModel(false, lastSelectedProject);
        RP.post(new Runnable() {

            @Override
            public void run() {
                ProgressHandle progress = ProgressHandleFactory.createHandle(NbBundle.getBundle(ImportExecutable.class).getString("ImportExecutable.Progress")); // NOI18N
                progress.start();
                Applicable applicable = null;
                try {
                    ConfigurationDescriptorProvider provider = lastSelectedProject.getLookup().lookup(ConfigurationDescriptorProvider.class);
                    MakeConfigurationDescriptor configurationDescriptor = provider.getConfigurationDescriptor(true);
                    applicable = extension.isApplicable(map, lastSelectedProject);
                    if (applicable.isApplicable()) {
                        resetCompilerSet(configurationDescriptor.getActiveConfiguration(), applicable);
                        String additionalDependencies = null;
                        if (IMPORT_DEPENDENCIES) {
                            additionalDependencies = additionalDependencies(applicable, configurationDescriptor.getActiveConfiguration());
                            if (additionalDependencies != null && !additionalDependencies.isEmpty()) {
                                map.put("DW:libraries", additionalDependencies); // NOI18N
                            }
                        }
                        if (extension.canApply(map, lastSelectedProject)) {
                            try {
                                extension.apply(map, lastSelectedProject);
                                saveMakeConfigurationDescriptor(lastSelectedProject);
                                if (CREATE_DEPENDENCIES && (additionalDependencies == null || additionalDependencies.isEmpty())) {
                                    cd = new CreateDependencies(lastSelectedProject, DiscoveryWizardDescriptor.adaptee(map).getDependencies());
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } finally {
                    progress.finish();
                }
                Position mainFunction = applicable.getMainFunction();
                boolean open = true;
                if (mainFunction != null && "main".equals(functionToOpen)) { // NOI18N
                    FileObject toFileObject = FileUtil.toFileObject(new File(mainFunction.getFilePath()));
                    if (toFileObject != null) {
                        if (CsmUtilities.openSource(toFileObject, mainFunction.getLine(), 0)) {
                            open = false;
                        }
                    }

                }
                switchModel(true, lastSelectedProject);
                if (functionToOpen != null && open) {
                    openFunction(functionToOpen, lastSelectedProject);
                }
            }
        });
    }

    private String additionalDependencies(Applicable applicable, MakeConfiguration activeConfiguration) {
        if (applicable.getSourceRoot() == null || applicable.getSourceRoot().length() == 0) {
            return null;
        }
        if (applicable.getDependencies() == null || applicable.getDependencies().isEmpty()) {
            return null;
        }
        Map<String,String> dllPaths = new HashMap<String, String>();
        String root = applicable.getSourceRoot();
        String ldLibPath = getLdLibraryPath(activeConfiguration);
        boolean search = false;
        for(String dll : applicable.getDependencies()) {
            String p = findLocation(dll, ldLibPath, root);
            if (p != null) {
                dllPaths.put(dll, p);
            } else {
                search = true;
                dllPaths.put(dll, null);
            }
        }
        if (search) {
            gatherSubFolders(new File(root), new HashSet<String>(), dllPaths);
        }
        StringBuilder buf = new StringBuilder();
        for(Map.Entry<String, String> entry : dllPaths.entrySet()) {
            if (entry.getValue() != null) {
                if (isMyDll(entry.getValue(), root)) {
                    if (buf.length() > 0) {
                        buf.append(';');
                    }
                    buf.append(entry.getValue());
                }
            }
        }
        return buf.toString();
    }

    static String getLdLibraryPath(MakeConfiguration activeConfiguration) {
        String ldLibPath = activeConfiguration.getProfile().getEnvironment().getenv("LD_LIBRARY_PATH"); // NOI18N
        ExecutionEnvironment eenv = activeConfiguration.getDevelopmentHost().getExecutionEnvironment();
        if (ldLibPath != null) {
            try {
                ldLibPath = MacroExpanderFactory.getExpander(eenv).expandMacros(ldLibPath, HostInfoUtils.getHostInfo(eenv).getEnvironment());
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        if (ldLibPath == null) {
            ldLibPath = HostInfoProvider.getEnv(eenv).get("LD_LIBRARY_PATH"); // NOI18N
        }
        return ldLibPath;
    }

    private static final List<CsmProgressListener> listeners = new ArrayList<CsmProgressListener>(1);

    private void openFunction(final String functionName, Project makeProject) {
        if (makeProject != null) {
            final NativeProject np = makeProject.getLookup().lookup(NativeProject.class);
            CsmProgressListener listener = new CsmProgressAdapter() {

                @Override
                public void projectParsingFinished(CsmProject project) {
                    if (project.getPlatformProject().equals(np)) {
                        CsmListeners.getDefault().removeProgressListener(this);
                        listeners.remove(this);
                        if (project instanceof ProjectBase) {
                            String from = Utils.getCsmDeclarationKindkey(CsmDeclaration.Kind.FUNCTION_DEFINITION) + ':' + functionName + '('; // NOI18N
                            Collection<CsmOffsetableDeclaration> decls = ((ProjectBase)project).findDeclarationsByPrefix(from);
                            for(CsmOffsetableDeclaration decl : decls){
                                CsmUtilities.openSource(decl);
                                break;
                            }
                        }
                        if (cd != null) {
                            cd.create();
                        }
                    }
                }
            };
            listeners.add(listener);
            CsmListeners.getDefault().addProgressListener(listener);
        }
    }

    static void switchModel(boolean state, Project makeProject) {
        CsmModel model = CsmModelAccessor.getModel();
        if (model instanceof ModelImpl && makeProject != null) {
            NativeProject np = makeProject.getLookup().lookup(NativeProject.class);
            if (state) {
                ((ModelImpl) model).enableProject(np);
            } else {
                ((ModelImpl) model).disableProject(np);
            }
        }
    }

    static void resetCompilerSet(MakeConfiguration configuration, Applicable applicable){
        if (configuration != null) {
            CompilerSetManager manager = CompilerSetManager.get(configuration.getDevelopmentHost().getExecutionEnvironment());
            if (applicable.isSunStudio()) {
                CompilerSet def = manager.getDefaultCompilerSet();
                if (def != null && def.getCompilerFlavor().isSunStudioCompiler()) {
                    return;
                }
                List<CompilerSet> compilerSets = CompilerSetManager.get(configuration.getDevelopmentHost().getExecutionEnvironment()).getCompilerSets();
                def = null;
                for(CompilerSet set : compilerSets) {
                    if (set.getCompilerFlavor().isSunStudioCompiler()) {
                        if ("OracleSolarisStudio".equals(set.getName())) { // NOI18N
                            def = set;
                        }
                        if (def == null) {
                            def = set;
                        }
                    }
                }
                if (def != null) {
                    configuration.getCompilerSet().setValue(def.getName());
                }
            } else {
                // It seems GNU compiler. applicable.getCompilerName() contains a producer information.
                // Unfortunately producer field does not give information about flavor, only compiler version
                CompilerSet def = manager.getDefaultCompilerSet();
                if (def != null && !def.getCompilerFlavor().isSunStudioCompiler()) {
                    return;
                }
                List<CompilerSet> compilerSets = CompilerSetManager.get(configuration.getDevelopmentHost().getExecutionEnvironment()).getCompilerSets();
                def = null;
                for(CompilerSet set : compilerSets) {
                    if (!set.getCompilerFlavor().isSunStudioCompiler()) {
                        if (def == null) {
                            def = set;
                        }
                    }
                }
                if (def != null) {
                    configuration.getCompilerSet().setValue(def.getName());
                }
            }
        }
    }

    static void saveMakeConfigurationDescriptor(Project lastSelectedProject) {
        ConfigurationDescriptorProvider pdp = lastSelectedProject.getLookup().lookup(ConfigurationDescriptorProvider.class);
        final MakeConfigurationDescriptor makeConfigurationDescriptor = pdp.getConfigurationDescriptor();
        if (makeConfigurationDescriptor != null) {
            makeConfigurationDescriptor.setModified();
            makeConfigurationDescriptor.save();
            makeConfigurationDescriptor.checkForChangedItems(lastSelectedProject, null, null);
        }
    }

    static boolean isMyDll(String path, String root) {
        if (path.startsWith(root)) {
            return true;
        } else {
            String[] p1 = path.split("/");  // NOI18N
            String[] p2 = root.split("/");  // NOI18N
            for(int i = 0; i < Math.min(p1.length - 1, p2.length); i++) {
                if (!p1[i].equals(p2[i])) {
                    if (i > 2) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    static String findLocation(String dll, String ldPath, String root){
        if (ldPath != null) {
            for(String search : ldPath.split(":")) {  // NOI18N
                File file = new File(search, dll);
                if (file.isFile() && file.exists()) {
                    String path = file.getAbsolutePath();
                    return path.replace('\\', '/');
                }
            }
        }
        return null;
    }

    static void gatherSubFolders(File d, HashSet<String> set, Map<String,String> result){
        if (!DLL_FILE_SEARCH) {
            return;
        }
        if (d.exists() && d.isDirectory() && d.canRead()){
            String path = d.getAbsolutePath();
            path = path.replace('\\', '/'); // NOI18N
            if (!set.contains(path)){
                set.add(path);
                File[] ff = d.listFiles();
                for (int i = 0; i < ff.length; i++) {
                    try {
                        String canPath = ff[i].getCanonicalPath();
                        String absPath = ff[i].getAbsolutePath();
                        if (!absPath.equals(canPath) && absPath.startsWith(canPath)) {
                            continue;
                        }
                    } catch (IOException ex) {
                        //Exceptions.printStackTrace(ex);
                    }
                    String name = ff[i].getName();
                    if (result.containsKey(name)) {
                       result.put(name, ff[i].getAbsolutePath());
                    }
                    gatherSubFolders(ff[i], set, result);
                }
            }
        }
    }
}
