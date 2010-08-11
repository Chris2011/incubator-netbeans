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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */
package org.netbeans.modules.maven;

import java.awt.event.ActionEvent;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import org.apache.maven.artifact.Artifact;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.modules.maven.indexer.api.RepositoryIndexer;
import org.netbeans.modules.maven.indexer.api.RepositoryInfo;
import org.netbeans.modules.maven.indexer.api.RepositoryPreferences;
import org.netbeans.modules.maven.api.NbMavenProject;
import org.netbeans.modules.maven.execute.ActionToGoalUtils;
import org.netbeans.modules.maven.execute.ModelRunConfig;
import org.netbeans.modules.maven.api.execute.PrerequisitesChecker;
import org.netbeans.modules.maven.api.execute.RunConfig;
import org.netbeans.modules.maven.api.execute.RunUtils;
import org.netbeans.modules.maven.configurations.M2Configuration;
import org.netbeans.modules.maven.customizer.CustomizerProviderImpl;
import org.netbeans.modules.maven.execute.BeanRunConfig;
import org.netbeans.modules.maven.execute.MavenExecutor;
import org.netbeans.modules.maven.execute.ui.RunGoalsPanel;
import org.netbeans.modules.maven.options.MavenSettings;
import org.netbeans.modules.maven.configurations.M2ConfigProvider;
import org.netbeans.modules.maven.execute.model.ActionToGoalMapping;
import org.netbeans.modules.maven.execute.model.NetbeansActionMapping;
import org.netbeans.modules.maven.execute.model.io.xpp3.NetbeansBuildActionXpp3Reader;
import org.netbeans.modules.maven.nodes.MavenProjectNode;
import org.netbeans.modules.maven.operations.Operations;
import org.netbeans.modules.maven.problems.ProblemReporterImpl;
import org.netbeans.modules.maven.problems.ProblemsPanel;
import org.netbeans.modules.maven.spi.actions.AbstractMavenActionsProvider;
import org.netbeans.modules.maven.spi.actions.ActionConvertor;
import org.netbeans.modules.maven.spi.actions.MavenActionsProvider;
import org.netbeans.modules.maven.spi.actions.ReplaceTokenProvider;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.SingleMethod;
import org.netbeans.spi.project.SubprojectProvider;
import org.netbeans.spi.project.ui.support.DefaultProjectOperations;
import org.netbeans.spi.project.ui.support.ProjectSensitiveActions;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.awt.DynamicMenuContent;
import org.openide.execution.ExecutorTask;
import org.openide.loaders.DataObject;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.Task;
import org.openide.util.TaskListener;
import org.openide.util.actions.Presenter;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author  Milos Kleint
 */
public class ActionProviderImpl implements ActionProvider {

    public static final String BUILD_WITH_DEPENDENCIES = "build-with-dependencies"; // NOI18N

    private final NbMavenProjectImpl project;
    private static String[] supported = new String[]{
        COMMAND_BUILD,
        BUILD_WITH_DEPENDENCIES,
        COMMAND_CLEAN,
        COMMAND_REBUILD,
        "javadoc", //NOI18N
        COMMAND_TEST,
        COMMAND_TEST_SINGLE,
        COMMAND_RUN,
        COMMAND_RUN_SINGLE,
        COMMAND_DEBUG,
        COMMAND_DEBUG_SINGLE,
        COMMAND_DEBUG_TEST_SINGLE,
        "debug.fix", //NOI18N

        //operations
        COMMAND_DELETE,
        COMMAND_RENAME,
        COMMAND_MOVE,
        COMMAND_COPY
    };
    
    Lookup.Result<? extends MavenActionsProvider> result;

    /** Creates a new instance of ActionProviderImpl */
    public ActionProviderImpl(NbMavenProjectImpl proj) {
        project = proj;
        result = Lookup.getDefault().lookupResult(MavenActionsProvider.class);
    }

    @Override
    public String[] getSupportedActions() {
        Set<String> supp = new HashSet<String>();
        supp.addAll( Arrays.asList( supported));
        for (MavenActionsProvider add : result.allInstances()) {
            Set<String> added = add.getSupportedDefaultActions();
            if (added != null) {
                supp.addAll( added);
            }
        }
        if (RunUtils.hasTestCompileOnSaveEnabled(project)) {
            supp.add(SingleMethod.COMMAND_RUN_SINGLE_METHOD);
            supp.add(SingleMethod.COMMAND_DEBUG_SINGLE_METHOD);
        }
        return supp.toArray(new String[0]);
    }

    @Override
    public void invokeAction(final String action, final Lookup lookup) {
        if (COMMAND_DELETE.equals(action)) {
            DefaultProjectOperations.performDefaultDeleteOperation(project);
            return;
        }
        if (COMMAND_COPY.equals(action)) {
            DefaultProjectOperations.performDefaultCopyOperation(project);
            return;
        }
        if (COMMAND_MOVE.equals(action)) {
            DefaultProjectOperations.performDefaultMoveOperation(project);
            return;
        }

        if (COMMAND_RENAME.equals(action)) {
            Operations.renameProject(project);
            return;
        }

        if (SwingUtilities.isEventDispatchThread()) {
            RequestProcessor.getDefault().post(new Runnable() {
                @Override
                public void run() {
                    ActionProviderImpl.this.invokeAction(action, lookup);
                }

            });
            return;
        }
        //TODO if order is important, use the lookupmerger
        Collection<? extends ActionConvertor> convertors = project.getLookup().lookupAll(ActionConvertor.class);
        String convertedAction = null;
        for (ActionConvertor convertor : convertors) {
            convertedAction = convertor.convert(action, lookup);
            if (convertedAction != null) {
                break;
            }
        }
        if (convertedAction == null) {
            convertedAction = action;
        }

        Lookup enhanced = new ProxyLookup(lookup, Lookups.fixed(replacements(convertedAction, lookup)));
        
        RunConfig rc = ActionToGoalUtils.createRunConfig(convertedAction, project, enhanced);
        if (rc == null) {
            Logger.getLogger(ActionProviderImpl.class.getName()).log(Level.INFO, "No handling for action: {0}. Ignoring.", action); //NOI18N

        } else {
            if (rc instanceof BeanRunConfig) {
                BeanRunConfig brc = (BeanRunConfig)rc;
                if (brc.getPreExecutionActionName() != null) {
                    RunConfig rc2 = ActionToGoalUtils.createRunConfig(brc.getPreExecutionActionName(), project, enhanced);
                    if (rc2 != null) {
                        brc.setPreExecution(rc2);
                    }
                }
            }
            setupTaskName(action, rc, lookup);
            runGoal(rc, true);
        }
    }

    private Map<String,String> replacements(String action, Lookup lookup) {
        Map<String,String> replacements = new HashMap<String,String>();
        for (ReplaceTokenProvider prov : project.getLookup().lookupAll(ReplaceTokenProvider.class)) {
            replacements.putAll(prov.createReplacements(action, lookup));
        }
        return replacements;
    }

    private void runGoal(RunConfig config, boolean checkShowDialog) {
        // save all edited files.. maybe finetune for project's files only, however that would fail for multiprojects..
        LifecycleManager.getDefault().saveAll();

        // check the prerequisites
        Lookup.Result<PrerequisitesChecker> res = config.getProject().getLookup().lookup(new Lookup.Template<PrerequisitesChecker>(PrerequisitesChecker.class));
        for (PrerequisitesChecker elem : res.allInstances()) {
            if (!elem.checkRunConfig(config)) {
                return;
            }
            if (config.getPreExecution() != null) {
                if (!elem.checkRunConfig(config.getPreExecution())) {
                    return;
                }
            }
        }



        if (checkShowDialog && MavenSettings.getDefault().isShowRunDialog()) {
            RunGoalsPanel pnl = new RunGoalsPanel();
            DialogDescriptor dd = new DialogDescriptor(pnl, org.openide.util.NbBundle.getMessage(MavenExecutor.class, "TIT_Run_maven"));
            pnl.readConfig(config);
            Object retValue = DialogDisplayer.getDefault().notify(dd);
            if (retValue == DialogDescriptor.OK_OPTION) {
                BeanRunConfig newConfig = new BeanRunConfig();
                newConfig.setExecutionDirectory(config.getExecutionDirectory());
                newConfig.setExecutionName(config.getExecutionName());
                newConfig.setTaskDisplayName(config.getTaskDisplayName());
                newConfig.setProject(config.getProject());
                pnl.applyValues(newConfig);
                config = newConfig;
            } else {
                return;
            }
        }
        // setup executor now..   
        ExecutorTask task = RunUtils.executeMaven(config);

        // fire project change on when finishing maven execution, to update the classpath etc. -MEVENIDE-83
        task.addTaskListener(new TaskListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void taskFinished(Task task2) {
//reload is done in executors
//                NbMavenProject.fireMavenProjectReload(project);
                RepositoryInfo info = RepositoryPreferences.getInstance().getRepositoryInfoById(RepositoryPreferences.LOCAL_REPO_ID);
                if (info != null) {
                    List<Artifact> arts = new ArrayList<Artifact>();
                    Artifact prjArt = project.getOriginalMavenProject().getArtifact();
                    if (prjArt != null) {
                        arts.add(prjArt);
                    }
                    //#157572
                    Set depArts = project.getOriginalMavenProject().getDependencyArtifacts();
                    if (depArts != null) {
                        arts.addAll(depArts);
                    }
                    RepositoryIndexer.updateIndexWithArtifacts(info, arts);
                }
            }
        });
    }

    private void setupTaskName(String action, RunConfig config, Lookup lkp) {
        assert config instanceof BeanRunConfig;
        BeanRunConfig bc = (BeanRunConfig) config;
        String title;
        DataObject dobj = lkp.lookup(DataObject.class);
        NbMavenProject prj = bc.getProject().getLookup().lookup(NbMavenProject.class);
        //#118926 prevent NPE, how come the dobj is null?
        String dobjName = dobj != null ? dobj.getName() : ""; //NOI18N

        if (ActionProvider.COMMAND_RUN.equals(action)) {
            title = NbBundle.getMessage(ActionProviderImpl.class, "TXT_Run", prj.getMavenProject().getArtifactId());
        } else if (ActionProvider.COMMAND_DEBUG.equals(action)) {
            title = NbBundle.getMessage(ActionProviderImpl.class, "TXT_Debug", prj.getMavenProject().getArtifactId());
        } else if (ActionProvider.COMMAND_TEST.equals(action)) {
            title = NbBundle.getMessage(ActionProviderImpl.class, "TXT_Test", prj.getMavenProject().getArtifactId());
        } else if (action.startsWith(ActionProvider.COMMAND_RUN_SINGLE)) {
            title = NbBundle.getMessage(ActionProviderImpl.class, "TXT_Run", dobjName);
        } else if (action.startsWith(ActionProvider.COMMAND_DEBUG_SINGLE) || ActionProvider.COMMAND_DEBUG_TEST_SINGLE.equals(action)) {
            title = NbBundle.getMessage(ActionProviderImpl.class, "TXT_Debug", dobjName);
        } else if (ActionProvider.COMMAND_TEST_SINGLE.equals(action)) {
            title = NbBundle.getMessage(ActionProviderImpl.class, "TXT_Test", dobjName);
        } else {
            title = NbBundle.getMessage(ActionProviderImpl.class, "TXT_Build", prj.getMavenProject().getArtifactId());
        }
        bc.setTaskDisplayName(title);
    }

    @Override
    public boolean isActionEnabled(String action, Lookup lookup) {
        if (COMMAND_DELETE.equals(action) ||
                COMMAND_RENAME.equals(action) ||
                COMMAND_COPY.equals(action) ||
                COMMAND_MOVE.equals(action)) {
            return true;
        }
        //TODO if order is important, use the lookupmerger
        Collection<? extends ActionConvertor> convertors = project.getLookup().lookupAll(ActionConvertor.class);
        String convertedAction = null;
        for (ActionConvertor convertor : convertors) {
            convertedAction = convertor.convert(action, lookup);
            if (convertedAction != null) {
                break;
            }
        }
        if (convertedAction == null) {
            convertedAction = action;
        }
        return ActionToGoalUtils.isActionEnable(convertedAction, project, lookup);
    }

    public Action createCustomMavenAction(String name, NetbeansActionMapping mapping, boolean showUI) {
        return new CustomAction(name, mapping, showUI);
    }

    /*
    private final static class BasicAction extends AbstractAction implements ContextAwareAction {

        private String actionid;
        private Lookup context;
        private ActionProviderImpl provider;

        private BasicAction(String name, String act) {
            actionid = act;
            putValue(Action.NAME, name);
        }

        private BasicAction(String name, String act, Lookup cntxt) {
            this(name, act);
            Lookup.Result<Project> res = cntxt.lookup(new Lookup.Template<Project>(Project.class));
            if (res.allItems().size() == 1) {
                Project project = cntxt.lookup(Project.class);
                this.context = project.getLookup();
                provider = this.context.lookup(ActionProviderImpl.class);
            }
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (provider != null) {
                provider.invokeAction(actionid, context);
            }
        }

        @Override
        public boolean isEnabled() {
            if (provider != null) {
                return provider.isActionEnabled(actionid, provider.project.getLookup());
            }
            return false;
        }

        @Override
        public Action createContextAwareInstance(Lookup actionContext) {
            return new BasicAction((String) getValue(Action.NAME), actionid, actionContext);
        }
    }
     */

    private final class CustomAction extends AbstractAction {

        private NetbeansActionMapping mapping;
        private boolean showUI;

        private CustomAction(String name, NetbeansActionMapping mapp, boolean showUI) {
            mapping = mapp;
            putValue(Action.NAME, name);
            this.showUI = showUI;
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            Map<String,String> replacements = replacements((String) getValue(Action.NAME), /* is there ever a context? */Lookup.EMPTY);
            Properties mappingProperties = mapping.getProperties();
            for (Map.Entry<Object,Object> entry : mappingProperties.entrySet()) {
                mappingProperties.put(entry.getKey(), AbstractMavenActionsProvider.dynamicSubstitutions(replacements, (String) entry.getValue()));
            }

            if (!showUI) {
                ModelRunConfig rc = new ModelRunConfig(project, mapping, mapping.getActionName(), null);
                rc.setShowDebug(MavenSettings.getDefault().isShowDebug());
                rc.setTaskDisplayName(NbBundle.getMessage(ActionProviderImpl.class, "TXT_Build"));

                setupTaskName("custom", rc, Lookup.EMPTY); //NOI18N
                runGoal(rc, true); //NOI18N

                return;
            }
            RunGoalsPanel pnl = new RunGoalsPanel();
            DialogDescriptor dd = new DialogDescriptor(pnl, NbBundle.getMessage(ActionProviderImpl.class, "TIT_Run_Maven"));
            ActionToGoalMapping maps = ActionToGoalUtils.readMappingsFromFileAttributes(project.getProjectDirectory());
            pnl.readMapping(mapping, project, maps);
            pnl.setShowDebug(MavenSettings.getDefault().isShowDebug());
            pnl.setOffline(MavenSettings.getDefault().isOffline() != null ? MavenSettings.getDefault().isOffline() : false);
            pnl.setRecursive(true);
            Object retValue = DialogDisplayer.getDefault().notify(dd);
            if (retValue == DialogDescriptor.OK_OPTION) {
                pnl.applyValues(mapping);
                if (maps.getActions().size() > 10) {
                    maps.getActions().remove(0);
                }
                maps.getActions().add(mapping);
                ActionToGoalUtils.writeMappingsToFileAttributes(project.getProjectDirectory(), maps);
                if (pnl.isRememberedAs() != null) {
                    try {
                        M2ConfigProvider conf = project.getLookup().lookup(M2ConfigProvider.class);
                        ActionToGoalMapping mappings = new NetbeansBuildActionXpp3Reader().read(new StringReader(conf.getDefaultConfig().getRawMappingsAsString()));
                        String tit = "CUSTOM-" + pnl.isRememberedAs(); //NOI18N

                        mapping.setActionName(tit);
                        NetbeansActionMapping exist = null;
                        for (NetbeansActionMapping m : mappings.getActions()) {
                            if (tit.equals(m.getActionName())) {
                                exist = m;
                                break;
                            }
                        }
                        if (exist != null) {
                            mappings.getActions().set(mappings.getActions().indexOf(exist), mapping);
                        } else {
                            mappings.addAction(mapping);
                        }
                        mapping.setDisplayName(pnl.isRememberedAs());
                        //TODO shall we write to configuration based files or not?
                        CustomizerProviderImpl.writeNbActionsModel(project, mappings, M2Configuration.getFileNameExt(M2Configuration.DEFAULT));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                ModelRunConfig rc = new ModelRunConfig(project, mapping, mapping.getActionName(), null);
                rc.setOffline(Boolean.valueOf(pnl.isOffline()));
                rc.setShowDebug(pnl.isShowDebug());
                rc.setRecursive(pnl.isRecursive());
                rc.setUpdateSnapshots(pnl.isUpdateSnapshots());
                rc.setTaskDisplayName(NbBundle.getMessage(ActionProviderImpl.class, "TXT_Build"));

                setupTaskName("custom", rc, Lookup.EMPTY); //NOI18N
                runGoal(rc, false); //NOI18N

            }
        }
    }

    // XXX should this be an API somewhere?
    private static abstract class ConditionallyShownAction extends AbstractAction implements ContextAwareAction {

        protected ConditionallyShownAction() {
            setEnabled(false);
            putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        }

        public final @Override void actionPerformed(ActionEvent e) {
            assert false;
        }

        protected abstract Action forProject(Project p);

        public final @Override Action createContextAwareInstance(Lookup actionContext) {
            Collection<? extends Project> projects = actionContext.lookupAll(Project.class);
            if (projects.size() != 1) {
                return this;
            }
            Action a = forProject(projects.iterator().next());
            return a != null ? a : this;
        }

    }

    public static Action customPopupActions() {
        return new ConditionallyShownAction() {
            protected @Override Action forProject(Project p) {
                ActionProviderImpl ap = p.getLookup().lookup(ActionProviderImpl.class);
                return ap != null ? ap.new CustomPopupActions() : null;
            }
        };
    }
    private final class CustomPopupActions extends AbstractAction implements Presenter.Popup {

        private CustomPopupActions() {
            putValue(Action.NAME, NbBundle.getMessage(ActionProviderImpl.class, "LBL_Custom_Run"));
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
        }

        @Override
        public JMenuItem getPopupPresenter() {

            final JMenu menu = new JMenu(NbBundle.getMessage(ActionProviderImpl.class, "LBL_Custom_Run"));
            final JMenuItem loading = new JMenuItem(NbBundle.getMessage(ActionProviderImpl.class, "LBL_Loading", new Object[]{}));

            menu.add(loading);
            /*using lazy construction strategy*/
            RequestProcessor.getDefault().post(new Runnable() {

                @Override
                public void run() {
                    NetbeansActionMapping[] maps = ActionToGoalUtils.getActiveCustomMappings(project);
                    for (int i = 0; i < maps.length; i++) {
                        NetbeansActionMapping mapp = maps[i];
                        Action act = createCustomMavenAction(mapp.getActionName(), mapp, false);
                        JMenuItem item = new JMenuItem(act);
                        item.setText(mapp.getDisplayName() == null ? mapp.getActionName() : mapp.getDisplayName());
                        menu.add(item);
                    }
                    menu.add(new JMenuItem(createCustomMavenAction(NbBundle.getMessage(ActionProviderImpl.class, "LBL_Custom_run_goals"), new NetbeansActionMapping(), true)));
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            boolean selected = menu.isSelected();
                            menu.remove(loading);
                            menu.getPopupMenu().pack();
                            menu.repaint();
                            menu.updateUI();
                            menu.setSelected(selected);
                        }
                    });
                }
            }, 100);
            return menu;
        }
    }

    public static Action closeSubprojectsAction() {
        return new ConditionallyShownAction() {
            protected @Override Action forProject(Project p) {
                NbMavenProjectImpl project = p.getLookup().lookup(NbMavenProjectImpl.class);
                if (project != null && NbMavenProject.TYPE_POM.equalsIgnoreCase(project.getProjectWatcher().getPackagingType())) {
                    return new CloseSubprojectsAction(project);
                } else {
                    return null;
                }
            }
        };
    }
    private static class CloseSubprojectsAction extends AbstractAction {
        private final NbMavenProjectImpl project;
        public CloseSubprojectsAction(NbMavenProjectImpl project) {
            this.project = project;
            putValue(Action.NAME, NbBundle.getMessage(MavenProjectNode.class, "ACT_CloseRequired"));
        }
        public @Override void actionPerformed(ActionEvent e) {
            SubprojectProvider subs = project.getLookup().lookup(SubprojectProvider.class);
            Set<? extends Project> lst = subs.getSubprojects();
            Project[] arr = lst.toArray(new Project[lst.size()]);
            OpenProjects.getDefault().close(arr);
        }
    }

    public static Action showProblemsAction() {
        return new ConditionallyShownAction() {
            protected @Override Action forProject(Project p) {
                ProblemReporterImpl reporter = p.getLookup().lookup(ProblemReporterImpl.class);
                return reporter != null && reporter.getReports().size() > 0 ? new ShowProblemsAction(reporter) : null;
            }
        };
    }
    private static class ShowProblemsAction extends AbstractAction {
        private final ProblemReporterImpl reporter;
        public ShowProblemsAction(ProblemReporterImpl reporter) {
            this.reporter = reporter;
            putValue(Action.NAME, NbBundle.getMessage(MavenProjectNode.class, "ACT_ShowProblems"));
        }
        public @Override void actionPerformed(ActionEvent arg0) {
            JButton butt = new JButton();
            ProblemsPanel panel = new ProblemsPanel(reporter);
            panel.setActionButton(butt);
            JButton close = new JButton();
            panel.setCloseButton(close);
            close.setText(NbBundle.getMessage(MavenProjectNode.class, "BTN_Close"));
            DialogDescriptor dd = new DialogDescriptor(panel, NbBundle.getMessage(MavenProjectNode.class, "TIT_Show_Problems"));
            dd.setOptions(new Object[] { butt,  close});
            dd.setClosingOptions(new Object[] { close });
            dd.setModal(false);
            DialogDisplayer.getDefault().notify(dd);
        }
    }

    public static Action buildWithDependenciesAction() {
        return ProjectSensitiveActions.projectCommandAction(BUILD_WITH_DEPENDENCIES, NbBundle.getMessage(MavenProjectNode.class, "ACT_Build_Deps"), null);
    }

    /*
    private final class ProfilesPopupActions extends AbstractAction implements Presenter.Popup {

        private ProfilesPopupActions() {
            putValue(Action.NAME, NbBundle.getMessage(ActionProviderImpl.class, "LBL_Profiles"));
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
        }

        @Override
        public JMenuItem getPopupPresenter() {

            final JMenu menu = new JMenu(NbBundle.getMessage(ActionProviderImpl.class, "LBL_Profiles"));
            final JMenuItem loading = new JMenuItem(NbBundle.getMessage(ActionProviderImpl.class, "LBL_Loading", new Object[]{}));

            menu.add(loading);
            // using lazy construction strategy
            RequestProcessor.getDefault().post(new Runnable() {
                @Override
                public void run() {
                    final ProjectProfileHandler profileHandler = project.getLookup().lookup(ProjectProfileHandler.class);
                    List<String> retrieveAllProfiles = profileHandler.getAllProfiles();
                    
                    List<String> mergedActiveProfiles = profileHandler.getMergedActiveProfiles(false);
                    List<String> customActiveProfiles = profileHandler.getActiveProfiles(false);
                    List<String> activeProfiles = new ArrayList<String>(mergedActiveProfiles);
                    activeProfiles.removeAll(customActiveProfiles);
                    for (final String profile : retrieveAllProfiles) {
                        final boolean activeByDefault = activeProfiles.contains(profile);
                        final JCheckBoxMenuItem item = new JCheckBoxMenuItem(profile, mergedActiveProfiles.contains(profile));
                        menu.add(item);
                        item.setAction(new AbstractAction(profile) {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (item.isSelected()) {
                                    profileHandler.enableProfile( profile, false);
                                } else {
                                    profileHandler.disableProfile( profile, false);
                                }
                                NbMavenProject.fireMavenProjectReload(project);
                            }
                            @Override
                            public boolean isEnabled() {
                                return !activeByDefault;
                            }
                        });
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            boolean selected = menu.isSelected();
                            menu.remove(loading);
                            menu.getPopupMenu().pack();
                            menu.repaint();
                            menu.updateUI();
                            menu.setSelected(selected);
                        }
                    });
                }
            }, 100);
            return menu;
        }
    }
    */
            
}
