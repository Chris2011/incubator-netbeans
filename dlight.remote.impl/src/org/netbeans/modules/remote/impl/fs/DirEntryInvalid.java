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

package org.netbeans.modules.remote.impl.fs;

import java.util.Date;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironment;
import org.netbeans.modules.remote.impl.RemoteLogger;

/**
 *
 * @author vk155633
 */
public final class DirEntryInvalid implements DirEntry {

    private final String name;

    public DirEntryInvalid(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public long getSize() {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return 0;
    }

    public boolean canExecute(ExecutionEnvironment execEnv) {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return false;
    }

    public boolean canRead(ExecutionEnvironment execEnv) {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return false;
    }

    public boolean canWrite(ExecutionEnvironment execEnv) {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return false;
    }

    public String getAccessAsString() {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return "---------"; //NOI18N
    }

    public Date getLastModified() {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return new Date();
    }

    public boolean isLink() {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return false;
    }

    public boolean isDirectory() {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return false;
    }

    public boolean isPlainFile() {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return true;
    }

    public boolean isSameLastModified(DirEntry other) {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return false;
    }

    public boolean isSameType(DirEntry other) {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return false;
    }

    public FileType getFileType() {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return FileType.File;
    }

    public boolean isSameUser(DirEntry other) {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return false;
    }

    public boolean isSameGroup(DirEntry other) {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return false;
    }

    public String getLinkTarget() {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return null;
    }

    public String getCache() {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
        return "";
    }

    public void setCache(String cache) {
        RemoteLogger.assertTrueInConsole(false, "unsupported operation for " + name); //NOI18N
    }

    public String toExternalForm() {
        return name; //TODO: escape '\n'
    }

    static DirEntry fromExternalForm(String line) {
        return new DirEntryInvalid(line); //TODO: unescape '\n'
    }
    
    public boolean isValid() {
        return false;
    }

    @Override
    public String toString() {
        return "DirEntryInvalid {" + name + '}'; //NOI18N
    }
}
