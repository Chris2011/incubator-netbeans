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

package org.netbeans.libs.git.remote.jgit.commands;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.NotTreeFilter;
import org.eclipse.jgit.treewalk.filter.OrTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.netbeans.api.extexecution.ProcessBuilder;
import org.netbeans.libs.git.remote.GitException;
import org.netbeans.libs.git.remote.GitStatus;
import org.netbeans.libs.git.remote.jgit.GitClassFactory;
import org.netbeans.libs.git.remote.jgit.JGitRepository;
import org.netbeans.libs.git.remote.jgit.Utils;
import org.netbeans.libs.git.remote.progress.ProgressMonitor;
import org.netbeans.libs.git.remote.progress.StatusListener;
import org.netbeans.modules.remotefs.versioning.api.ProcessUtils;
import org.netbeans.modules.remotefs.versioning.api.VCSFileProxySupport;
import org.netbeans.modules.versioning.core.api.VCSFileProxy;
import org.netbeans.modules.versioning.core.api.VersioningSupport;

/**
 *
 * @author ondra
 */
public class StatusCommand extends StatusCommandBase {
    private final VCSFileProxy[] roots;
    private final ProgressMonitor monitor;
    private final String revision;
    private static final Logger LOG = Logger.getLogger(StatusCommand.class.getName());
    private static final Set<VCSFileProxy> logged = new HashSet<>();
    private final boolean isRevision;

    public StatusCommand (JGitRepository repository, String revision, VCSFileProxy[] roots, GitClassFactory gitFactory,
            ProgressMonitor monitor, StatusListener listener) {
        super(repository, revision, roots, gitFactory, monitor, listener);
        this.roots = roots;
        this.monitor = monitor;
        this.revision = revision;
        isRevision = !Constants.HEAD.equals(revision);
    }
    
    @Override
    protected void prepare() throws GitException {
        if (isRevision) {
            setCommandsNumber(4);
        } else {
            setCommandsNumber(3);
        }
        super.prepare();
        if (isRevision) {
            addArgument(0, "diff"); //NOI18N
            addArgument(0, "--cached"); //NOI18N
            addArgument(0, "--raw"); //NOI18N
            addArgument(0, "--name-status"); //NOI18N
            addArgument(0, revision);
            addArgument(0, "--"); //NOI18N
            addFiles(0, roots);
            addArgument(1, "diff"); //NOI18N
            addArgument(1, "--raw"); //NOI18N
            addArgument(1, "--name-status"); //NOI18N
            addArgument(1, revision);
            addArgument(1, "--"); //NOI18N
            addFiles(1, roots);
            addArgument(2, "status"); //NOI18N
            addArgument(2, "--short"); //NOI18N
            addArgument(2, "--ignored"); //NOI18N
            addArgument(2, "--untracked-files=normal"); //NOI18N
            addArgument(2, "--"); //NOI18N
            addFiles(2, roots);
            addArgument(3, "ls-files"); //NOI18N
            addArgument(3, "--"); //NOI18N
            addFiles(3, roots);
        } else {
            addArgument(0, "status"); //NOI18N
            addArgument(0, "--short"); //NOI18N
            addArgument(0, "--ignored"); //NOI18N
            addArgument(0, "--untracked-files=normal"); //NOI18N
            addArgument(0, "--"); //NOI18N
            addFiles(0, roots);
            addArgument(1, "diff"); //NOI18N
            addArgument(1, "--raw"); //NOI18N
            addArgument(1, "--name-status"); //NOI18N
            addArgument(1, "HEAD");
            addArgument(1, "--"); //NOI18N
            addFiles(1, roots);
            addArgument(2, "ls-files"); //NOI18N
            addArgument(2, "--"); //NOI18N
            addFiles(2, roots);
        }
    }

    @Override
    protected boolean prepareCommand () throws GitException {
        final boolean exists = getRepository().getMetadataLocation().exists();
        if (exists) {
            prepare();
        }
        return exists;
    }

    @Override
    protected void run () throws GitException {
        if (false) {
            runKit();
        } else {
            runCLI();
        }
    }

    private void runCLI () throws GitException {
        ProcessUtils.Canceler canceled = new ProcessUtils.Canceler();
        if (monitor != null) {
            monitor.setCancelDelegate(canceled);
        }
        String cmd = getCommandLine();
        try {
            if (isRevision) {
                LinkedHashMap<String, StatusLine> list = new LinkedHashMap<>();
                {
                    ProcessBuilder processBuilder = VersioningSupport.createProcessBuilder(getRepository().getLocation());
                    String executable = getExecutable();
                    String[] args = getCliArguments(0);
                    ProcessUtils.ExitStatus exitStatus = ProcessUtils.executeInDir(getRepository().getLocation().getPath(), null, false, canceled, processBuilder, executable, args); //NOI18N
                    if(canceled.canceled()) {
                        return;
                    }
                    if (exitStatus.output!= null) {
                        parseDiffOutput(exitStatus.output, 1, list);
                    }
                    if (exitStatus.error != null && !exitStatus.error.isEmpty()) {
                        for(String line : exitStatus.error.split("\n")) { //NOI18N
                            if (!line.isEmpty()) {
                                //command.errorText(line);
                            }
                        }
                    }
                }
                {
                    ProcessBuilder processBuilder = VersioningSupport.createProcessBuilder(getRepository().getLocation());
                    String executable = getExecutable();
                    String[] args = getCliArguments(1);
                    ProcessUtils.ExitStatus exitStatus = ProcessUtils.executeInDir(getRepository().getLocation().getPath(), null, false, canceled, processBuilder, executable, args); //NOI18N
                    if(canceled.canceled()) {
                        return;
                    }
                    if (exitStatus.output!= null) {
                        parseDiffOutput(exitStatus.output, 3, list);
                    }
                    if (exitStatus.error != null && !exitStatus.error.isEmpty()) {
                        for(String line : exitStatus.error.split("\n")) { //NOI18N
                            if (!line.isEmpty()) {
                                //command.errorText(line);
                            }
                        }
                    }
                }
                {
                    ProcessBuilder processBuilder = VersioningSupport.createProcessBuilder(getRepository().getLocation());
                    String executable = getExecutable();
                    String[] args = getCliArguments(2);
                    ProcessUtils.ExitStatus exitStatus = ProcessUtils.executeInDir(getRepository().getLocation().getPath(), null, false, canceled, processBuilder, executable, args); //NOI18N
                    if (canceled.canceled()) {
                        return;
                    }
                    if (exitStatus.output != null) {
                        parseStatusOutput(exitStatus.output, list, true);
                    }
                    if (exitStatus.error != null && !exitStatus.error.isEmpty()) {
                        for (String line : exitStatus.error.split("\n")) { //NOI18N
                            if (!line.isEmpty()) {
                                //command.errorText(line);
                            }
                        }
                    }
                }
                {
                    ProcessBuilder processBuilder = VersioningSupport.createProcessBuilder(getRepository().getLocation());
                    String executable = getExecutable();
                    String[] args = getCliArguments(3);
                    ProcessUtils.ExitStatus exitStatus = ProcessUtils.executeInDir(getRepository().getLocation().getPath(), null, false, canceled, processBuilder, executable, args); //NOI18N
                    if(canceled.canceled()) {
                        return;
                    }
                    if (exitStatus.output!= null) {
                        parseLsOutput(exitStatus.output, list);
                    }
                    if (exitStatus.error != null && !exitStatus.error.isEmpty()) {
                        for(String line : exitStatus.error.split("\n")) { //NOI18N
                            if (!line.isEmpty()) {
                                //command.errorText(line);
                            }
                        }
                    }
                }
                processOutput(list, canceled);
            } else {
                LinkedHashMap<String, StatusLine> list = new LinkedHashMap<>();
                {
                    ProcessBuilder processBuilder = VersioningSupport.createProcessBuilder(getRepository().getLocation());
                    String executable = getExecutable();
                    String[] args = getCliArguments(0);
                    ProcessUtils.ExitStatus exitStatus = ProcessUtils.executeInDir(getRepository().getLocation().getPath(), null, false, canceled, processBuilder, executable, args); //NOI18N
                    if(canceled.canceled()) {
                        return;
                    }
                    if (exitStatus.output!= null) {
                        parseStatusOutput(exitStatus.output, list, false);
                    }
                    if (exitStatus.error != null && !exitStatus.error.isEmpty()) {
                        for(String line : exitStatus.error.split("\n")) { //NOI18N
                            if (!line.isEmpty()) {
                                //command.errorText(line);
                            }
                        }
                    }
                }
                {
                    ProcessBuilder processBuilder = VersioningSupport.createProcessBuilder(getRepository().getLocation());
                    String executable = getExecutable();
                    String[] args = getCliArguments(1);
                    ProcessUtils.ExitStatus exitStatus = ProcessUtils.executeInDir(getRepository().getLocation().getPath(), null, false, canceled, processBuilder, executable, args); //NOI18N
                    if(canceled.canceled()) {
                        return;
                    }
                    if (exitStatus.output!= null && exitStatus.isOK()) {
                        parseDiffOutput(exitStatus.output, 3, list);
                    }
                    if (!exitStatus.isOK()) {
                        if (exitStatus.error != null && !exitStatus.error.isEmpty()) {
                            for(String line : exitStatus.error.split("\n")) { //NOI18N
                                if (!line.isEmpty()) {
                                    //fatal: bad revision 'HEAD'
                                    //command.errorText(line);
                                }
                            }
                            if (exitStatus.error.contains("fatal: bad revision 'HEAD'")) {
                                for (Map.Entry<String, StatusLine> e : list.entrySet()) {
                                    final char first = e.getValue().first;
                                    if (first != '?' && first != '!') {
                                        e.getValue().third = first;
                                    }
                                }
                            }
                        }
                        
                    }
                }
                {
                    ProcessBuilder processBuilder = VersioningSupport.createProcessBuilder(getRepository().getLocation());
                    String executable = getExecutable();
                    String[] args = getCliArguments(2);
                    ProcessUtils.ExitStatus exitStatus = ProcessUtils.executeInDir(getRepository().getLocation().getPath(), null, false, canceled, processBuilder, executable, args); //NOI18N
                    if(canceled.canceled()) {
                        return;
                    }
                    if (exitStatus.output!= null) {
                        parseLsOutput(exitStatus.output, list);
                    }
                    if (exitStatus.error != null && !exitStatus.error.isEmpty()) {
                        for(String line : exitStatus.error.split("\n")) { //NOI18N
                            if (!line.isEmpty()) {
                                //command.errorText(line);
                            }
                        }
                    }
                }
                processOutput(list, canceled);
            }
            //command.commandCompleted(exitStatus.exitCode);
        } catch (Throwable t) {
            if(canceled.canceled()) {
            } else {
                throw new GitException(t);
            }
        } finally {
            //command.commandFinished();
        }        
    }

    private void parseStatusOutput(String output, Map<String, StatusLine> list, boolean onlyIndexWC) {
        for (String line : output.split("\n")) { //NOI18N
            if (line.length() > 3) {
                char first = line.charAt(0);
                char second = line.charAt(1);
                String file;
                String renamed = null;
                int i = line.indexOf("->");
                if (i > 0) {
                    file = line.substring(2, i).trim();
                    renamed = line.substring(i + 1).trim();
                } else {
                    file = line.substring(2).trim();
                }
                StatusLine status = list.get(file);
                if (status == null) {
                    status = new StatusLine();
                    if (onlyIndexWC) {
                        if (first == '?' || first == '!') {
                            status.first = first;
                            status.second = second;
                        } else {
                            status.second = second;
                        }
                    } else {
                        status.first = first;
                        status.second = second;
                        status.to = renamed;
                    }
                    list.put(file, status);
                } else {
                    if (onlyIndexWC) {
                        if (first == '?' || first == '!') {
                            status.untracked = first;
                        } else {
                            status.second = second;
                        }
                    } else {
                        status.untracked = first;
                    }
                }
            }
        }
    }

    private void parseDiffOutput(String output, int n, Map<String, StatusLine> list) {
        for (String line : output.split("\n")) { //NOI18N
            if (line.length() > 2) {
                char c = line.charAt(0);
                String file = line.substring(2).trim();
                StatusLine status = list.get(file);
                if (status == null) {
                    status = new StatusLine();
                    if (n == 1) {
                        status.first = c;
                    }
                    if (n == 2) {
                        status.second = c;
                    }
                    if (n == 3) {
                        status.third = c;
                    }
                    list.put(file, status);
                } else {
                    if (n == 1) {
                        status.first = c;
                    }
                    if (n == 2) {
                        status.second = c;
                    }
                    if (n == 3) {
                        status.third = c;
                    }
                }
            }
        }
    }

    private void parseLsOutput(String output, Map<String, StatusLine> list) {
        for (String line : output.split("\n")) { //NOI18N
            if (line.length() > 0) {
                String file = line.trim();
                StatusLine status = list.get(file);
                if (status == null) {
                    status = new StatusLine();
                    list.put(file, status);
                }
            }
        }
    }
    
    private void processOutput(LinkedHashMap<String, StatusLine> parseOutput, ProcessUtils.Canceler canceled) {
        for(Map.Entry<String, StatusLine> entry : parseOutput.entrySet()) {
            String file = entry.getKey();
            StatusLine v = entry.getValue();
            char first = v.first;
            char second = v.second;
            char third = v.third;
            char untracked = v.untracked;
            String renamed = v.to;
            
            boolean tracked = !(first == '?' || first == '!' );
            GitStatus.Status statusHeadIndex = GitStatus.Status.STATUS_IGNORED;
            switch (first) {
                case 'A':
                    statusHeadIndex = GitStatus.Status.STATUS_ADDED;
                    break;
                case 'C':
                    statusHeadIndex = GitStatus.Status.STATUS_ADDED;
                    break;
                case 'R':
                case 'D':
                    statusHeadIndex = GitStatus.Status.STATUS_REMOVED;
                    break;
                case 'M':
                case 'U':
                    statusHeadIndex = GitStatus.Status.STATUS_MODIFIED;
                    break;
                case ' ':
                    statusHeadIndex = GitStatus.Status.STATUS_NORMAL;
                    break;
                case '?':
                case '!':
                    statusHeadIndex = GitStatus.Status.STATUS_NORMAL;
                    break;
            }
            GitStatus.Status statusIndexWC = GitStatus.Status.STATUS_IGNORED;
            switch (second) {
                case 'A':
                    statusIndexWC = GitStatus.Status.STATUS_ADDED;
                    break;
                case 'D':
                    statusIndexWC = GitStatus.Status.STATUS_REMOVED;
                    break;
                case 'M':
                case 'U':
                    statusIndexWC = GitStatus.Status.STATUS_MODIFIED;
                    break;
                case ' ':
                    if (untracked == '?') {
                        statusIndexWC = GitStatus.Status.STATUS_ADDED;
                    } else {
                        statusIndexWC = GitStatus.Status.STATUS_NORMAL;
                    }
                    break;
                case '?':
                    statusIndexWC = GitStatus.Status.STATUS_ADDED;
                    break;
                case '!':
                    statusIndexWC = GitStatus.Status.STATUS_IGNORED;
                    break;
            }
            GitStatus.Status statusHeadWC = GitStatus.Status.STATUS_IGNORED;
            switch (third) {
                case 'A':
                    statusHeadWC = GitStatus.Status.STATUS_ADDED;
                    break;
                case 'D':
                    if (untracked == '?') {
                        statusHeadWC = GitStatus.Status.STATUS_MODIFIED;
                    } else {
                        statusHeadWC = GitStatus.Status.STATUS_REMOVED;
                    }
                    break;
                case 'M':
                case 'U':
                    statusHeadWC = GitStatus.Status.STATUS_MODIFIED;
                    break;
                case ' ':
                    if (first == '?' || first == '!') {
                        statusHeadWC = GitStatus.Status.STATUS_ADDED;
                    } else {
                        statusHeadWC = GitStatus.Status.STATUS_NORMAL;
                    }
                    break;
                case '?':
                    statusHeadWC = GitStatus.Status.STATUS_ADDED;
                    break;
                case '!':
                    statusHeadWC = GitStatus.Status.STATUS_IGNORED;
                    break;
            }
            boolean isFolder = false;
            if (file.endsWith("/")) {
                file = file.substring(0, file.length()-1);
                isFolder = true;
            }
            if (!tracked) {
                if (statusIndexWC == GitStatus.Status.STATUS_IGNORED && isFolder) {
                    statusHeadWC = statusIndexWC;
                } else {
                    statusHeadWC = GitStatus.Status.STATUS_ADDED;
                }
            }
            VCSFileProxy vcsFile = VCSFileProxy.createFileProxy(getRepository().getLocation(), file);
            long indexTimestamp = -1;
            GitStatus status = getClassFactory().createStatus(tracked, file, getRepository().getLocation().getPath()+"/"+file, vcsFile,
                    statusHeadIndex, statusIndexWC, statusHeadWC,
                    null, isFolder, null/*renamed*/, indexTimestamp);
            addStatus(vcsFile, status);
            //command.outputText(line);
        }
    }

    private void runKit () throws GitException {
        Repository repository = getRepository().getRepository();
        try {
            DirCache cache = repository.readDirCache();
            ObjectReader od = repository.newObjectReader();
            try {
                String workTreePath = repository.getWorkTree().getAbsolutePath();
                Collection<PathFilter> pathFilters = Utils.getPathFilters(getRepository().getLocation(), roots);
                ObjectId commitId = Utils.parseObjectId(repository, revision);
                Map<String, DiffEntry> renames = detectRenames(repository, cache, commitId);
                TreeWalk treeWalk = new TreeWalk(repository);
                if (!pathFilters.isEmpty()) {
                    treeWalk.setFilter(PathFilterGroup.create(pathFilters));
                }
                treeWalk.setRecursive(false);
                treeWalk.reset();
                if (commitId != null) {
                    treeWalk.addTree(new RevWalk(repository).parseTree(commitId));
                } else {
                    treeWalk.addTree(new EmptyTreeIterator());
                }
                // Index
                treeWalk.addTree(new DirCacheIterator(cache));
                // Working directory
                treeWalk.addTree(new FileTreeIterator(repository));
                final int T_COMMIT = 0;
                final int T_INDEX = 1;
                final int T_WORKSPACE = 2;
                String lastPath = null;
                GitStatus[] conflicts = new GitStatus[3];
                List<GitStatus> symLinks = new LinkedList<GitStatus>();
                boolean checkExecutable = Utils.checkExecutable(repository);
                while (treeWalk.next() && !monitor.isCanceled()) {
                    String path = treeWalk.getPathString();
                    boolean symlink = false;
                    VCSFileProxy file = VCSFileProxy.createFileProxy(getRepository().getLocation(), path);
                    if (path.equals(lastPath)) {
                        symlink = isKnownSymlink(symLinks, path);
                    } else {
                        try {
                            symlink = VCSFileProxySupport.isSymlink(file);
                        } catch (InvalidPathException ex) {
                            if (logged.add(file)) {
                                LOG.log(Level.FINE, null, ex);
                            }
                        }
                        handleConflict(conflicts, workTreePath);
                        handleSymlink(symLinks, workTreePath);
                    }
                    lastPath = path;
                    Logger.getLogger(StatusCommand.class.getName()).log(Level.FINE, "Inspecting file {0} ---- {1}", //NOI18N
                            new Object[] { path, file.getPath() });
                    int mHead = treeWalk.getRawMode(T_COMMIT);
                    int mIndex = treeWalk.getRawMode(T_INDEX);
                    int mWorking = treeWalk.getRawMode(T_WORKSPACE);
                    GitStatus.Status statusHeadIndex;
                    GitStatus.Status statusIndexWC;
                    GitStatus.Status statusHeadWC;
                    boolean tracked = mWorking != FileMode.TREE.getBits() && (mHead != FileMode.MISSING.getBits() || mIndex != FileMode.MISSING.getBits()); // is new and is not a folder
                    if (mHead == FileMode.MISSING.getBits() && mIndex != FileMode.MISSING.getBits()) {
                        statusHeadIndex = GitStatus.Status.STATUS_ADDED;
                    } else if (mIndex == FileMode.MISSING.getBits() && mHead != FileMode.MISSING.getBits()) {
                        statusHeadIndex = GitStatus.Status.STATUS_REMOVED;
                    } else if (mHead != mIndex || (mIndex != FileMode.TREE.getBits() && !treeWalk.idEqual(T_COMMIT, T_INDEX))) {
                        statusHeadIndex = GitStatus.Status.STATUS_MODIFIED;
                    } else {
                        statusHeadIndex = GitStatus.Status.STATUS_NORMAL;
                    }
                    FileTreeIterator fti = treeWalk.getTree(T_WORKSPACE, FileTreeIterator.class);
                    DirCacheIterator indexIterator = treeWalk.getTree(T_INDEX, DirCacheIterator.class);
                    DirCacheEntry indexEntry = indexIterator != null ? indexIterator.getDirCacheEntry() : null;
                    boolean isFolder = false;
                    if (!symlink && treeWalk.isSubtree()) {
                        if (mWorking == FileMode.TREE.getBits() && fti.isEntryIgnored()) {
                            Collection<TreeFilter> subTreeFilters = getSubtreeFilters(pathFilters, path);
                            if (!subTreeFilters.isEmpty()) {
                                // caller requested a status for a file under an ignored folder
                                treeWalk.setFilter(AndTreeFilter.create(treeWalk.getFilter(), OrTreeFilter.create(NotTreeFilter.create(PathFilter.create(path)), 
                                        subTreeFilters.size() > 1 ? OrTreeFilter.create(subTreeFilters) : subTreeFilters.iterator().next())));
                                treeWalk.enterSubtree();
                            }
                            if (includes(pathFilters, treeWalk)) {
                                // ignored folder statu is requested by a caller
                                statusIndexWC = statusHeadWC = GitStatus.Status.STATUS_IGNORED;
                                isFolder = true;
                            } else {
                                continue;
                            }
                        } else {
                            treeWalk.enterSubtree();
                            continue;
                        }
                    } else {
                        if (mWorking == FileMode.TYPE_GITLINK || mHead == FileMode.TYPE_GITLINK || mIndex == FileMode.TYPE_GITLINK) {
                            isFolder = file.isDirectory();
                        }
                        if (mWorking == FileMode.MISSING.getBits() && mIndex != FileMode.MISSING.getBits()) {
                            statusIndexWC = GitStatus.Status.STATUS_REMOVED;
                        } else if (mIndex == FileMode.MISSING.getBits() && mWorking != FileMode.MISSING.getBits()) {
                            if (fti.isEntryIgnored()) {
                                statusIndexWC = GitStatus.Status.STATUS_IGNORED;
                            } else {
                                statusIndexWC = GitStatus.Status.STATUS_ADDED;
                            }
                        } else if (!isExistingSymlink(mIndex, mWorking) && (differ(mIndex, mWorking, checkExecutable) 
                                || (mWorking != 0 && mWorking != FileMode.TREE.getBits() && fti.isModified(indexEntry, true, od)))
                                || GitStatus.Status.STATUS_MODIFIED == getGitlinkStatus(
                                        mWorking, treeWalk.getObjectId(T_WORKSPACE),
                                        mIndex, treeWalk.getObjectId(T_INDEX))) {
                            statusIndexWC = GitStatus.Status.STATUS_MODIFIED;
                        } else {
                            statusIndexWC = GitStatus.Status.STATUS_NORMAL;
                        }
                        if (mWorking == FileMode.MISSING.getBits() && mHead != FileMode.MISSING.getBits()) {
                            statusHeadWC = GitStatus.Status.STATUS_REMOVED;
                        } else if (mHead == FileMode.MISSING.getBits() && mWorking != FileMode.MISSING.getBits()) {
                            statusHeadWC = GitStatus.Status.STATUS_ADDED;
                        } else if (!isExistingSymlink(mIndex, mWorking) && (differ(mHead, mWorking, checkExecutable) 
                                || (mWorking != 0 && mWorking != FileMode.TREE.getBits() 
                                    && (indexEntry == null || !indexEntry.isAssumeValid()) //no update-index --assume-unchanged
                                    // head vs wt can be modified only when head vs index or index vs wt are modified, otherwise it's probably line-endings issue
                                    && (statusIndexWC != GitStatus.Status.STATUS_NORMAL || statusHeadIndex != GitStatus.Status.STATUS_NORMAL)
                                    && !treeWalk.getObjectId(T_COMMIT).equals(fti.getEntryObjectId())))
                                || GitStatus.Status.STATUS_MODIFIED == getGitlinkStatus(
                                        mHead, treeWalk.getObjectId(T_WORKSPACE),
                                        mHead, treeWalk.getObjectId(T_COMMIT))) {
                            statusHeadWC = GitStatus.Status.STATUS_MODIFIED;
                        } else {
                            statusHeadWC = GitStatus.Status.STATUS_NORMAL;
                        }
                    }

                    int stage = indexEntry == null ? 0 : indexEntry.getStage();
                    long indexTimestamp = indexEntry == null ? -1 : indexEntry.getLastModified();

                    GitStatus status = getClassFactory().createStatus(tracked, path, workTreePath, file,
                            statusHeadIndex, statusIndexWC, statusHeadWC,
                            null, isFolder, renames.get(path), indexTimestamp);
                    if (stage == 0) {
                        if (isSymlinkFolder(mHead, symlink)) {
                            symLinks.add(status);
                        } else {
                            addStatus(file, status);
                        }
                    } else {
                        conflicts[stage - 1] = status;
                    }
                }
                handleConflict(conflicts, workTreePath);
                handleSymlink(symLinks, workTreePath);
            } finally {
                od.release();
                cache.unlock();
            }
        } catch (CorruptObjectException ex) {
            throw new GitException(ex);
        } catch (IOException ex) {
            throw new GitException(ex);
        }
    }

    private Map<String, DiffEntry> detectRenames (Repository repository, DirCache cache, ObjectId commitId) {
        List<DiffEntry> entries;
        TreeWalk treeWalk = new TreeWalk(repository);
        try {
            treeWalk.setRecursive(true);
            treeWalk.reset();
            if (commitId != null) {
                treeWalk.addTree(new RevWalk(repository).parseTree(commitId));
            } else {
                treeWalk.addTree(new EmptyTreeIterator());
            }
            // Index
            treeWalk.addTree(new DirCacheIterator(cache));
            treeWalk.setFilter(TreeFilter.ANY_DIFF);
            entries = DiffEntry.scan(treeWalk);
            RenameDetector d = new RenameDetector(repository);
            d.addAll(entries);
            entries = d.compute();
        } catch (IOException ex) {
            entries = Collections.<DiffEntry>emptyList();
        } finally {
            treeWalk.release();
        }
        Map<String, DiffEntry> renames = new HashMap<String, DiffEntry>();
        for (DiffEntry e : entries) {
            if (e.getChangeType().equals(DiffEntry.ChangeType.COPY) || e.getChangeType().equals(DiffEntry.ChangeType.RENAME)) {
                renames.put(e.getNewPath(), e);
            }
        }
        return renames;
    }

    /**
     * Any filter includes this path but only by denoting any of it's ancestors or the path itself
     * Any filter that applies to a file/folder under the given path will not be taken into account
     * @param filters
     * @param treeWalk
     * @return 
     */
    public static boolean includes (Collection<PathFilter> filters, TreeWalk treeWalk) {
        boolean retval = filters.isEmpty();
        for (PathFilter filter : filters) {
            if (filter.include(treeWalk) && treeWalk.getPathString().length() >= filter.getPath().length()) {
                retval = true;
                break;
            }
        }
        return retval;
    }

    private static Collection<TreeFilter> getSubtreeFilters(Collection<PathFilter> filters, String path) {
        List<TreeFilter> subtreeFilters = new LinkedList<TreeFilter>();
        for (PathFilter filter : filters) {
            if (filter.getPath().startsWith(path + "/")) { //NOI18N
                subtreeFilters.add(filter);
            }
        }
        return subtreeFilters;
    }

    private boolean differ (int fileMode1, int fileModeWorking, boolean checkFileMode) {
        boolean differ;
        if (isExistingSymlink(fileMode1, fileModeWorking)) {
            differ = false;
        } else {
            int difference = fileMode1 ^ fileModeWorking;
            if (checkFileMode) {
                differ = difference != 0;
            } else {
                differ = (difference & ~0111) != 0;
            }
        }
        return differ;
    }

    private boolean isExistingSymlink (int fileMode1, int fileModeWorking) {
        return (fileModeWorking & FileMode.TYPE_FILE) == FileMode.TYPE_FILE && (fileMode1 & FileMode.TYPE_SYMLINK) == FileMode.TYPE_SYMLINK;
    }

    private boolean isKnownSymlink (List<GitStatus> symLinks, String path) {
        return !symLinks.isEmpty() && path.equals(symLinks.get(0).getRelativePath());
    }

    private boolean isSymlinkFolder (int mHead, boolean isSymlink) {
        // it seems symlink to a folder comes as two separate tree entries, 
        // first has always mWorking set to 0 and is a symlink in index and HEAD
        // the other is identified as a new tree
        return isSymlink || (mHead & FileMode.TYPE_SYMLINK) == FileMode.TYPE_SYMLINK;
    }

    private void handleSymlink (List<GitStatus> symLinks, String workTreePath) {
        if (!symLinks.isEmpty()) {
            GitStatus status = symLinks.get(0);
            GitStatus.Status statusIndexWC;
            GitStatus.Status statusHeadWC;
            GitStatus.Status statusHeadIndex;
            if (symLinks.size() == 1) {
                statusIndexWC = status.getStatusIndexWC();
                if (status.isTracked()) {
                    statusHeadIndex = status.getStatusHeadIndex();
                    statusHeadWC = status.getStatusHeadWC();
                } else {
                    statusHeadIndex = GitStatus.Status.STATUS_NORMAL;
                    statusHeadWC = GitStatus.Status.STATUS_ADDED;
                }
            } else {
                statusHeadIndex = status.getStatusHeadIndex();
                switch (statusHeadIndex) {
                    case STATUS_ADDED:
                        statusIndexWC = GitStatus.Status.STATUS_NORMAL;
                        statusHeadWC = GitStatus.Status.STATUS_ADDED;
                        break;
                    case STATUS_REMOVED:
                        statusIndexWC = GitStatus.Status.STATUS_ADDED;
                        statusHeadWC = GitStatus.Status.STATUS_NORMAL;
                        break;
                    default:
                        statusIndexWC = GitStatus.Status.STATUS_NORMAL;
                        statusHeadWC = GitStatus.Status.STATUS_NORMAL;
                }
            }
            status = getClassFactory().createStatus(status.isTracked(), status.getRelativePath(), workTreePath, status.getFile(),
                    statusHeadIndex, statusIndexWC, statusHeadWC, null, status.isFolder(), null, status.getIndexEntryModificationDate());
            addStatus(status.getFile(), status);
            symLinks.clear();
        }
    }

    private GitStatus.Status getGitlinkStatus (int mode1, ObjectId id1, int mode2, ObjectId id2) {
        if (mode1 == FileMode.TYPE_GITLINK || mode2 == FileMode.TYPE_GITLINK) {
            if (mode1 == FileMode.TYPE_MISSING) {
                return GitStatus.Status.STATUS_REMOVED;
            } else if (mode2 == FileMode.TYPE_MISSING) {
                return GitStatus.Status.STATUS_ADDED;
            } else if (!id1.equals(id2)) {
                return GitStatus.Status.STATUS_MODIFIED;
            }
        }
        return GitStatus.Status.STATUS_NORMAL;
    }

    private static final class StatusLine {
        char first = ' ';
        char second = ' ';
        char third = ' ';
        char untracked = ' ';
        String to;

        public StatusLine() {
        }

        @Override
        public String toString() {
            return ""+first+second+third+untracked;
        }
    }
}
