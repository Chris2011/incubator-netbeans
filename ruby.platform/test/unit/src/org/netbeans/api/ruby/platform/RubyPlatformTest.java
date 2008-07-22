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

package org.netbeans.api.ruby.platform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import org.netbeans.modules.ruby.platform.gems.GemManager;
import org.openide.filesystems.FileUtil;

public class RubyPlatformTest extends RubyTestBase {

    public RubyPlatformTest(String testName) {
        super(testName);
    }

    public void testPlatformBasics() {
        RubyPlatform jruby = RubyPlatformManager.getDefaultPlatform();
        assertNotNull("has bundled JRuby", jruby);
        assertTrue("is JRuby", jruby.isJRuby());
        assertNotNull("has label", jruby.getLabel());
        assertTrue("is valid", jruby.isValid());
        assertTrue("is default", jruby.isDefault());
        assertEquals("right version", "1.8.6", jruby.getVersion());
        assertEquals("right ruby home", TestUtil.getXTestJRubyHome(), jruby.getHome());
        assertEquals("right ruby home", new File(jruby.getHome(), "lib").getAbsolutePath(), jruby.getLibDir());
        assertEquals("right ruby lib", new File(jruby.getHome(), "lib/ruby/1.8").getAbsolutePath(), jruby.getVersionLibDir());
    }

    public void testHasRubyGemsInstalled() throws Exception {
        assertTrue(RubyPlatformManager.getDefaultPlatform().hasRubyGemsInstalled());
        RubyPlatform ruby = RubyPlatformManager.addPlatform(setUpRuby());
        assertFalse(ruby.hasRubyGemsInstalled());
    }

    public void testFindGemExecutableInRubyBin() throws Exception {
        RubyPlatform platform = RubyPlatformManager.addPlatform(setUpRubyWithGems());
        touch(platform.getBinDir(), "rdebug-ide");
        assertNotNull(platform.findExecutable("rdebug-ide"));
    }

    public void testFindGemExecutableInGemRepo() throws Exception {
        RubyPlatform platform = RubyPlatformManager.addPlatform(setUpRubyWithGems());
        GemManager gemManager = platform.getGemManager();
        touch(new File(gemManager.getGemHome(), "bin").getPath(), "rdebug-ide");
        assertNotNull(platform.findExecutable("rdebug-ide"));
    }

    public void testFindRDoc() throws Exception {
        RubyPlatform platform = RubyPlatformManager.addPlatform(setUpRubyWithGems());
        assertNotNull("rdoc found", platform.getRDoc());
    }

    public void testFindRDocWithSuffix() throws Exception {
        RubyPlatform platform = RubyPlatformManager.addPlatform(setUpRuby(false, "1.8.6-p110"));
        assertNotNull("rdoc found", platform.getRDoc());
    }

    public void testFindIRB() throws Exception {
        RubyPlatform platform = RubyPlatformManager.addPlatform(setUpRubyWithGems());
        assertNotNull("irb found", platform.getIRB());
    }

    public void testFindJIRB() throws Exception {
        assertNotNull("jirb found", RubyPlatformManager.getDefaultPlatform().getIRB());
    }

    public void testFindIRBWithSuffix() throws Exception {
        RubyPlatform platform = RubyPlatformManager.addPlatform(setUpRuby(false, "1.8.6-p110"));
        assertNotNull("irb found", platform.getIRB());
    }

    public void testLongDescription() throws Exception {
        RubyPlatform jruby = RubyPlatformManager.getDefaultPlatform();
        assertEquals("right long description", "JRuby 1.8.6 (2008-07-19 patchlevel 114) [java]", jruby.getInfo().getLongDescription());
        RubyPlatform ruby = RubyPlatformManager.addPlatform(setUpRuby());
        assertEquals("right long description without patchlevel", "Ruby 0.1 (2000-01-01) [abcd]", ruby.getInfo().getLongDescription());
    }

    public void testLabel() throws Exception {
        RubyPlatform jruby = RubyPlatformManager.getDefaultPlatform();
        assertEquals("right label for build-in JRuby", "Built-in JRuby 1.1.3", jruby.getLabel());
        RubyPlatform ruby = RubyPlatformManager.addPlatform(setUpRuby());
        assertEquals("right label for Ruby", "Ruby 0.1", ruby.getLabel());
    }

    public void testHasFastDebuggerInstalled() throws IOException {
        RubyPlatform jruby = getSafeJRuby();
        assertFalse("does not have fast debugger", jruby.hasFastDebuggerInstalled());
        installFakeFastRubyDebugger(jruby);
        assertTrue("does have fast debugger", jruby.hasFastDebuggerInstalled());
    }

    public void testHasFastDebuggerInstalledExactness() throws IOException {
        RubyPlatform jruby = getSafeJRuby();
        installFakeGem(RubyPlatform.RUBY_DEBUG_IDE_NAME, "9.9.9", "java", jruby);
        assertFalse("does have fast debugger in exact version", jruby.hasFastDebuggerInstalled());
    }

    public void testHasFastDebuggerInstalledPattern() throws IOException {
        RubyPlatform jruby = getSafeJRuby();
        String rdebugIDE = RubyPlatform.RUBY_DEBUG_IDE_NAME;
        installFakeGem(rdebugIDE, "0.2.1", jruby);
        assertTrue("0.2.1 matches", jruby.hasFastDebuggerInstalled());
        uninstallFakeGem(rdebugIDE, "0.2.1", jruby);

        installFakeGem(rdebugIDE, "0.3.1", jruby);
        assertFalse("0.3.1 does not match", jruby.hasFastDebuggerInstalled());
    }

    public void testFireGemsChanged() throws Exception {
        RubyPlatform jruby = getSafeJRuby();

        final boolean[] gotEvent = new boolean[1];
        jruby.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ("gems".equals(evt.getPropertyName())) {
                    gotEvent[0] = true;
                }
            }
        });

        installFakeGem("jalokivi", "9.9", jruby);
        assertTrue(gotEvent[0]);
    }

    public void testDefaultPlatformInfo() {
        RubyPlatform platform = RubyPlatformManager.getDefaultPlatform();
        RubyPlatform.Info info = platform.getInfo();
        RubyPlatform.Info computed = RubyPlatformManager.computeInfo(platform.getInterpreterFile());
        assertEquals("correct info for bundled JRuby", computed, info);
        assertEquals("correct info for bundled JRuby", computed.getJVersion(), info.getJVersion());
        assertEquals("correct info for bundled JRuby", computed.getLibDir(), info.getLibDir());
    }

    public void testRubinius() throws IOException {
        RubyPlatform rubinius = RubyPlatformManager.addPlatform(setUpRubinius());
        assertNotNull("rubinius supported", rubinius);
        assertTrue("is Rubinius", rubinius.isRubinius());
        assertNotNull("has label", rubinius.getLabel());
        assertTrue("is valid", rubinius.isValid());
        assertFalse("is default", rubinius.isDefault());
        assertEquals("right version", "0.1", rubinius.getVersion());
        assertEquals("right ruby lib", new File(rubinius.getHome(), "lib").getAbsolutePath(), rubinius.getLibDir());
        assertNull("does not throw AssertionError", rubinius.getSystemRoot(FileUtil.toFileObject(new File(rubinius.getHome(), "lib"))));
    }

}
