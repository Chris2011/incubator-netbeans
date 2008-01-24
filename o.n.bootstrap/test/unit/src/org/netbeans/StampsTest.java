/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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

package org.netbeans;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import junit.framework.TestCase;
import org.netbeans.junit.Log;
import org.netbeans.junit.NbTestCase;

/**
 *
 * @author Jaroslav Tulach <jaroslav.tulach@netbeans.org>
 */
public class StampsTest extends NbTestCase {
    private File userdir;
    private File ide;
    private File platform;
    private File install;
    
    public StampsTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        clearWorkDir();
        
        install = new File(getWorkDir(), "install");
        platform = new File(install, "platform7");
        ide = new File(install, "ide8");
        userdir = new File(getWorkDir(), "tmp");
        
        System.setProperty("netbeans.home", platform.getPath());
        System.setProperty("netbeans.dirs", ide.getPath());
        System.setProperty("netbeans.user", userdir.getPath());
        
        createModule("org.openide.awt", platform, 50000L);
        createModule("org.openide.nodes", platform, 60000L);
        createModule("org.netbeans.api.languages", ide, 90000L);
        createModule("org.netbeans.modules.logmanagement", userdir, 10000L);
        
        Stamps.main("reset");
        
        Thread.sleep(100);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGenerateTimeStamps() {
        long stamp = Stamps.moduleJARs();
        assertEquals("Timestamp is taken from api.languages module", 90000L, stamp);
        
        assertStamp(60000L, platform, false, true);
        assertStamp(90000L, ide, false, true);
        assertStamp(-1L, userdir, false, false);
        
        Stamps.main("reset");
        
        CountingSecurityManager.initialize(install.getPath());

        long newStamp = Stamps.moduleJARs();
        
        CountingSecurityManager.assertCounts("Just two accesses installation", 2);
        assertEquals("Stamps are the same", stamp, newStamp);
        
        
        assertStamp(60000L, platform, false, true);
        assertStamp(90000L, ide, false, true);
        assertStamp(-1L, userdir, false, false);

        Stamps.main("reset");
        CountingSecurityManager.initialize(new File(userdir, "var").getPath());
        long newStamp2 = Stamps.moduleJARs();
        
        CountingSecurityManager.assertCounts("Just two accesses to cache", 2);
        assertEquals("Stamps are the same", stamp, newStamp2);
        
    }

    public void testWriteToCache() throws Exception {
        Stamps s = Stamps.getModulesJARs();
        
        assertNull(s.asByteBuffer("mycache.dat"));
        assertNull(s.asStream("mycache.dat"));

        class Up implements Stamps.Updater {

            public void flushCaches(DataOutputStream os) throws IOException {
                os.writeInt(1);
                os.writeInt(2);
                os.writeShort(2);
            }
            
        }
        Up updater = new Up();
        
        s.scheduleSave(updater, "mycache.dat", false);
        
        assertNull(s.asByteBuffer("mycache.dat"));
        assertNull(s.asStream("mycache.dat"));
        
        s.flush(true);
        
        ByteBuffer bb;
        InputStream is;
        assertNotNull(bb = s.asByteBuffer("mycache.dat"));
        assertNotNull(is = s.asStream("mycache.dat"));
        
        assertEquals("10 bytes", 10, bb.remaining());
        assertEquals("10 bytes stream", 10, is.available());
        is.close();
        bb.clear();
    }
    
    public void testWriteToCacheWithError() {
        Stamps s = Stamps.getModulesJARs();
        
        assertNull(s.asByteBuffer("mycache.dat"));
        assertNull(s.asStream("mycache.dat"));

        class Up implements Stamps.Updater {

            public void flushCaches(DataOutputStream os) throws IOException {
                throw new IOException("Not supported yet.");
            }
            
        }
        Up updater = new Up();
        
        s.scheduleSave(updater, "mycache.dat", false);
        
        assertNull(s.asByteBuffer("mycache.dat"));
        assertNull(s.asStream("mycache.dat"));
        
        CharSequence log = Log.enable("org.netbeans", Level.WARNING);
        s.flush(true);
        
        assertNull(s.asByteBuffer("mycache.dat"));
        assertNull(s.asStream("mycache.dat"));
        
        if (log.length() < 10) {
            fail("There should be a warning written to log:\n" + log);
        }
    }
    
    
    
    
    
    
    
    
    
    private static void assertStamp(long expectedValue, File cluster, boolean global, boolean local) {
        File globalStamp = new File(cluster, ".lastModified");

        File userDir = new File(System.getProperty("netbeans.user"));
        File localStamp = new File(new File(new File(new File(userDir, "var"), "cache"), "lastModified"), cluster.getName());
        
        if (global) {
            assertTrue("File shall exist: " + globalStamp, globalStamp.exists());
            assertEquals("Modification time is good " + globalStamp, expectedValue, globalStamp.lastModified());
        } else {
            assertFalse("File shall not exist: " + globalStamp, globalStamp.exists());
        }

        if (local) {
            assertTrue("File shall exist: " + localStamp, localStamp.exists());
            assertEquals("Modification time is good " + localStamp, expectedValue, localStamp.lastModified());
        } else {
            assertFalse("File shall not exist: " + localStamp, localStamp.exists());
        }
        
    }

    private void createModule(String cnb, File cluster, long accesTime) throws IOException {
        String dashes = cnb.replace('.', '-');
        
        File config = new File(new File(new File(cluster, "config"), "Modules"), dashes + ".xml");
        File jar = new File(new File(cluster, "modules"), dashes + ".jar");
        
        config.getParentFile().mkdirs();
        jar.getParentFile().mkdirs();
        
        config.createNewFile();
        jar.createNewFile();
        config.setLastModified(accesTime);
        jar.setLastModified(accesTime);
    }

}
