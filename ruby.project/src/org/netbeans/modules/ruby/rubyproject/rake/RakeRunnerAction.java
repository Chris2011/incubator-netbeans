/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2008 Sun Microsystems, Inc. All rights reserved.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2008 Sun
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
package org.netbeans.modules.ruby.rubyproject.rake;

import org.netbeans.modules.ruby.rubyproject.*;
import java.io.File;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.ruby.platform.RubyPlatform;
import org.netbeans.modules.ruby.platform.execution.FileLocator;
import org.netbeans.modules.ruby.rubyproject.rake.RakeTaskChooser.TaskDescriptor;
import org.openide.LifecycleManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;

/**
 * Shows convenient runner for running or debugging Rake tasks, similar to e.g.
 * Go To File dialog.
 */
public final class RakeRunnerAction extends NodeAction {

    @Override
    public String getName() {
        return NbBundle.getMessage(RakeRunnerAction.class, "RakeRunnerAction.RunDebugRakeTask");
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected void performAction(Node[] activatedNodes) {
        Lookup lookup = activatedNodes[0].getLookup();
        Project p = lookup.lookup(Project.class);

        if (!RubyPlatform.platformFor(p).showWarningIfInvalid()) {
            return;
        }

        RubyBaseProject project = (RubyBaseProject) p;

        TaskDescriptor taskDesc = RakeTaskChooser.select(project);
        runTask(project, taskDesc);

    }

    private void runTask(final RubyBaseProject project, final TaskDescriptor taskDesc) {
        if (!RubyPlatform.platformFor(project).showWarningIfInvalid()) {
            return;
        }
        
        if (!RubyPlatform.hasValidRake(project, true)) {
            return;
        }

        // Save all files first
        LifecycleManager.getDefault().saveAll();


        // EMPTY CONTEXT??
        FileLocator fileLocator = new RubyFileLocator(Lookup.EMPTY, project);
        String displayName = NbBundle.getMessage(RakeRunnerAction.class, "RakeRunnerAction.Rake");

        ProjectInformation info = ProjectUtils.getInformation(project);

        File pwd = null;

        FileObject rakeFile = RakeSupport.findRakeFile(project);
        if (rakeFile == null) {
            pwd = FileUtil.toFile(project.getProjectDirectory());
        }

        RakeSupport rake = new RakeSupport(project);

        final RakeTask task = taskDesc.getRakeTask();
        String taskName = task.getTask();

        if (taskName != null && (taskName.equals("test") || taskName.startsWith("test:"))) { // NOI18N
            rake.setTest(true);
        }

        if (info != null) {
            displayName = info.getDisplayName();
        }

        displayName += " (" + taskName + ')';

        rake.runRake(pwd, rakeFile, displayName, fileLocator, true, taskDesc.isDebug(), taskName);

//        // Update recent tasks list: add or move to end
//        if (rakeFile != null) {
//            List<RakeTask> recent = recentTasks.get(rakeFile);
//            if (recent == null) {
//                recent = new ArrayList<RakeTask>();
//                recenttasks.put(rakeFile, recent);
//            }
//            recent.remove(task);
//            recent.add(task);
//        }
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
        if ((activatedNodes == null) || (activatedNodes.length != 1)) {
            return false;
        }

        Lookup lookup = activatedNodes[0].getLookup();
        Project project = lookup.lookup(Project.class);

        return project instanceof RubyBaseProject;
    }

    @Override
    protected boolean asynchronous() {
        return true;
    }
}
