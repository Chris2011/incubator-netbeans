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

package org.netbeans.libs.git.jgit.commands;

import java.io.File;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.netbeans.libs.git.GitBlameResult;
import org.netbeans.libs.git.GitException;
import org.netbeans.libs.git.jgit.GitClassFactory;
import org.netbeans.libs.git.jgit.Utils;
import org.netbeans.libs.git.progress.ProgressMonitor;

/**
 *
 * @author ondra
 */
public class BlameCommand extends GitCommand {

    private final String revision;
    private final File file;
    private final ProgressMonitor monitor;
    private GitBlameResult result;

    public BlameCommand (Repository repository, GitClassFactory gitFactory, File file, String revision, ProgressMonitor monitor) {
        super(repository, gitFactory, monitor);
        this.file = file;
        this.revision = revision;
        this.monitor = monitor;
    }

    @Override
    protected void run () throws GitException {
        Repository repository = getRepository();
        org.eclipse.jgit.api.BlameCommand cmd = new Git(repository).blame();
        cmd.setFilePath(Utils.getRelativePath(getRepository().getWorkTree(), file));
        if (revision != null) {
            cmd.setStartCommit(Utils.findCommit(repository, revision));
        } else if (repository.getConfig().get(WorkingTreeOptions.KEY).getAutoCRLF() != CoreConfig.AutoCRLF.FALSE) {
            // work-around for autocrlf
            cmd.setTextComparator(new WAComparator());
        }
        cmd.setFollowFileRenames(true);
        BlameResult cmdResult = cmd.call();
        if (cmdResult != null) {
            result = getClassFactory().createBlameResult(cmdResult, repository);
        }
    }

    @Override
    protected String getCommandDescription () {
        StringBuilder sb = new StringBuilder("git blame -l -f "); //NOI18N
        if (revision != null) {
            sb.append(revision).append(" "); //NOI18N
        }
        sb.append(file);
        return sb.toString();
    }

    public GitBlameResult getResult () {
        return result;
    }

    private static class WAComparator extends RawTextComparator {

        @Override
        public boolean equals (RawText a, int ai, RawText b, int bi) {
            String line1 = a.getString(ai);
            String line2 = b.getString(bi);
            line1 = trimTrailingEoL(line1);
            line2 = trimTrailingEoL(line2);

            return line1.equals(line2);
        }

        @Override
        protected int hashRegion (final byte[] raw, int ptr, int end) {
            int hash = 5381;
            end = trimTrailingEoL(raw, ptr, end);
            for (; ptr < end; ptr++) {
                hash = ((hash << 5) + hash) + (raw[ptr] & 0xff);
            }
            return hash;
        }
        
        private static String trimTrailingEoL (String line) {
            int end = line.length() - 1;
            while (end >= 0 && isNewLine(line.charAt(end))) {
                --end;
            }
            return line.substring(0, end + 1);
	}
        
        private static int trimTrailingEoL(byte[] raw, int start, int end) {
            int ptr = end - 1;
            while (start <= ptr && (raw[ptr] == '\r' || raw[ptr] == '\n')) {
                ptr--;
            }

            return ptr + 1;
	}

        private static boolean isNewLine (char ch) {
            return ch == '\n' || ch == '\r';
        }
    }

}
