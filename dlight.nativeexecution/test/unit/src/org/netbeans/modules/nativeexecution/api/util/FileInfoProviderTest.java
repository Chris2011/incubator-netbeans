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

package org.netbeans.modules.nativeexecution.api.util;

import java.io.File;
import java.util.Date;
import java.util.concurrent.Future;
import junit.framework.Test;
import org.netbeans.modules.dlight.libs.common.PathUtilities;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironment;
import org.netbeans.modules.nativeexecution.api.util.FileInfoProvider.StatInfo;
import org.netbeans.modules.nativeexecution.test.ForAllEnvironments;
import org.netbeans.modules.nativeexecution.test.NativeExecutionBaseTestCase;
import org.netbeans.modules.nativeexecution.test.NativeExecutionBaseTestSuite;
import org.openide.util.Exceptions;

/**
 *
 * @author Vladimir Kvashin
 */
public class FileInfoProviderTest extends NativeExecutionBaseTestCase {

    private String remoteTmpDir;
    private String remoteFile;
    private String remoteLink;
    private String remoteSubdir;
    private String remoteSubdirLink;
    private Date creationDate;
    
    public FileInfoProviderTest(String name, ExecutionEnvironment testExecutionEnvironment) {
        super(name, testExecutionEnvironment);
    }

    @SuppressWarnings("unchecked")
    public static Test suite() {
        return new NativeExecutionBaseTestSuite(FileInfoProviderTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ExecutionEnvironment env = getTestExecutionEnvironment();
        ConnectionManager.getInstance().connectTo(env);
        clearRemoteTmpDir();
        remoteTmpDir = createRemoteTmpDir();
        File localFile = File.createTempFile("test_stat_", ".dat");
        localFile.delete();
        remoteFile = remoteTmpDir + "/" + localFile.getName();
        remoteLink = remoteFile + ".link";
        remoteSubdir = remoteFile + ".subdir";
        remoteSubdirLink = remoteFile + ".subdir.link";                
        String script = 
                "echo 123 > " + remoteFile + ";" +
                "ln -s " + remoteFile + ' ' + remoteLink + ";"  +
                " mkdir -p " + remoteSubdir + ";" + 
                "ln -s " + remoteSubdir + ' ' + remoteSubdirLink;
        runScript(script);
        creationDate = new Date();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        clearRemoteTmpDir();
    }
        
    @ForAllEnvironments(section = "remote.platforms")
    public void testStat() throws Exception {
        StatInfo statInfo;

        statInfo = getStatInfo(remoteFile);
        System.err.printf("Stat for %s: %s\n", remoteFile, statInfo);
        assertExpected(statInfo, remoteFile, false, null);
        
        statInfo = getStatInfo(remoteLink);
        System.err.printf("Stat for %s: %s\n", remoteLink, statInfo);
        assertExpected(statInfo, remoteLink, false, remoteFile);

        statInfo = getStatInfo(remoteSubdir);
        System.err.printf("Stat for %s: %s\n", remoteSubdir, statInfo);
        assertExpected(statInfo, remoteSubdir, true, null);
        
        statInfo = getStatInfo(remoteSubdirLink);
        System.err.printf("Stat for %s: %s\n", remoteSubdirLink, statInfo);
        assertExpected(statInfo, remoteSubdirLink, false, remoteSubdir);
    }
    
    @ForAllEnvironments(section = "remote.platforms")
    public void testAccessMode() throws Exception {
        ExecutionEnvironment env = getTestExecutionEnvironment();
        
        checkAccess(remoteFile, "700", env, true, true, true);
        checkAccess(remoteFile, "400", env, true, false, false);
        checkAccess(remoteFile, "200", env, false, true, false);
        checkAccess(remoteFile, "100", env, false, false, true);
        
        checkAccess(remoteFile, "070", env, true, true, true);
        checkAccess(remoteFile, "040", env, true, false, false);
        checkAccess(remoteFile, "020", env, false, true, false);
        checkAccess(remoteFile, "010", env, false, false, true);
        
        checkAccess(remoteFile, "007", env, true, true, true);
        checkAccess(remoteFile, "004", env, true, false, false);
        checkAccess(remoteFile, "002", env, false, true, false);
        checkAccess(remoteFile, "001", env, false, false, true);
        
        // TODO: test (other) groups
    }
    
    @ForAllEnvironments(section = "remote.platforms")
    public void testExternalForm() throws Exception {
        ExecutionEnvironment env = getTestExecutionEnvironment();
        StatInfo statInfo1 = getStatInfo(remoteLink);
        String extForm = statInfo1.toExternalForm();
        StatInfo statInfo2 = StatInfo.fromExternalForm(extForm);
        assertEquals("getName()", statInfo1.getName(), statInfo2.getName());
        assertEquals("getAccess()", statInfo1.getAccess(), statInfo2.getAccess());
        assertEquals("getGropupId()", statInfo1.getGropupId(), statInfo2.getGropupId());
        assertEquals("getLastModified()", statInfo1.getLastModified(), statInfo2.getLastModified());
        assertEquals("getLinkTarget()", statInfo1.getLinkTarget(), statInfo2.getLinkTarget());
        assertEquals("getUserId()", statInfo1.getUserId(), statInfo2.getUserId());        
        assertEquals("isDirectory()", statInfo1.isDirectory(), statInfo2.isDirectory());
        assertEquals("isLink()", statInfo1.isLink(), statInfo2.isLink());        
        assertEquals("canRead()", statInfo1.canRead(env), statInfo2.canRead(env));
        assertEquals("canWrite()", statInfo1.canWrite(env), statInfo2.canWrite(env));
        assertEquals("canExecute()", statInfo1.canExecute(env), statInfo2.canExecute(env));
    }
    
    private void checkAccess(String path, String chmod, ExecutionEnvironment env, boolean read, boolean write, boolean execute) throws Exception {
        runScript("chmod " + chmod + ' ' + path);
        StatInfo statInfo = getStatInfo(path);
        checkAccess("canRead", path, env, read, statInfo.canRead(env));        
        checkAccess("canWrite", path, env, write, statInfo.canWrite(env));        
        checkAccess("canExecute", path, env, execute, statInfo.canExecute(env));
    }
    
    private void checkAccess(String prefix, String path, ExecutionEnvironment env, boolean expected, boolean actual) throws Exception {
        if (expected != actual) {
            StringBuilder sb = new StringBuilder(prefix).append(" differs for ").append(path).append(": ");
            sb.append("expected ").append(expected).append(" but was ").append(actual).append('\n');
            try {
                sb.append(runScript(env, "id; ls -ld " + path));
            } catch (Throwable ex) {
                Exceptions.printStackTrace(ex);
            }
            assertTrue(sb.toString(), false);
        }
    }            

    @ForAllEnvironments(section = "remote.platforms")
    public void testLs() throws Exception {
        StatInfo[] res = getLs(remoteTmpDir);
        System.err.printf("LS for %s\n", remoteTmpDir);
        for (StatInfo info : res) {                        
            System.err.printf("\t%s\n", info);
        }
        StatInfo statInfo;        
        statInfo = find(res, PathUtilities.getBaseName(remoteFile));
        assertExpected(statInfo, remoteFile, false, null);
        
        statInfo = getStatInfo(remoteLink);
        statInfo = find(res, PathUtilities.getBaseName(remoteLink));
        assertExpected(statInfo, remoteLink, false, remoteFile);

        statInfo = getStatInfo(remoteSubdir);
        statInfo = find(res, PathUtilities.getBaseName(remoteSubdir));
        assertExpected(statInfo, remoteSubdir, true, null);
        
        statInfo = getStatInfo(remoteSubdirLink);
        statInfo = find(res, PathUtilities.getBaseName(remoteSubdirLink));
        assertExpected(statInfo, remoteSubdirLink, false, remoteSubdir);
    }
    
    private StatInfo find(StatInfo[] infList, String name) throws Exception {
        for (StatInfo info : infList) {
            if (info.getName().equals(name)) {
                return info;
            }
        }
        assertTrue("can not found in ls info: " + name, false);
        return null;
    }
    
    private StatInfo[] getLs(String path) throws Exception {
        Future<StatInfo[]> res = FileInfoProvider.ls(getTestExecutionEnvironment(), path);
        assertNotNull(res);
        StatInfo[] info = res.get();
        assertNotNull("ls returned null", info);
        assertTrue("ls returned empty array", info.length > 0);
        return info;
        
    }

    private StatInfo getStatInfo(String path) throws Exception {
        Future<StatInfo> res = FileInfoProvider.stat(getTestExecutionEnvironment(), path);
        assertNotNull(res);
        StatInfo statInfo = res.get();
        assertNotNull("stat returned null", statInfo);
        return statInfo;
    }
        
    private void assertExpected(StatInfo statInfo, String path, boolean dir, String link) throws Exception {
        int slashPos = path.lastIndexOf('/');
        String name = slashPos < 0 ? path : path.substring(slashPos + 1);
        assertEquals("name for " + path, name, statInfo.getName());
        assertEquals("isLink() for " + path, (link != null), statInfo.isLink());
        assertEquals("isDirectory() for " + path, dir, statInfo.isDirectory());
        if (link != null) {
            assertEquals("link target for " + path, link, statInfo.getLinkTarget());
        }
        long skew = HostInfoUtils.getHostInfo(getTestExecutionEnvironment()).getClockSkew();
        if (Math.abs(creationDate.getTime() - statInfo.getLastModified().getTime()) > skew + 1000*60*5) {
            assertTrue("last modified differs too much: " + creationDate +  " vs " + statInfo.getLastModified(), false);
        }
    }
}
