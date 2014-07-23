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

package org.netbeans.modules.uihandler;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.openide.util.Lookup;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Jaroslav Tulach
 */
@org.openide.util.lookup.ServiceProviders({@org.openide.util.lookup.ServiceProvider(service=java.util.logging.Handler.class), @org.openide.util.lookup.ServiceProvider(service=org.netbeans.modules.uihandler.EarlyHandler.class)})
public final class EarlyHandler extends Handler {
    
    final Queue<LogRecord> earlyRecords = new ArrayDeque<>();
    private final Runnable installerRestore = new InstallerRestore();
    private volatile boolean isOn = true;
    private Installer installerHandle;
    
    public EarlyHandler() {
        setLevel(Level.ALL);
    }
    
    public static void disable() {
        EarlyHandler eh = Lookup.getDefault().lookup(EarlyHandler.class);
        eh.setLevel(Level.OFF);
        eh.isOn = false;
    }

    @Override
    public void publish(LogRecord record) {
        if (isOn && record.getLoggerName() != null) {
            synchronized (earlyRecords) {
                if (earlyRecords.isEmpty()) {
                    restoreLoggerInstaller();
                }
                earlyRecords.add(record);
            }
        }
    }
    
    static void forgetInstallerHandle() {
        EarlyHandler eh = Lookup.getDefault().lookup(EarlyHandler.class);
        eh.installerHandle = null;
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
    
    private void restoreLoggerInstaller() {
        new RequestProcessor(EarlyHandler.class).post(installerRestore);
    }
    
    private class InstallerRestore implements Runnable {
        
        @Override
        public void run() {
            Installer installer = Installer.findObject(Installer.class, true);
            installer.restored(earlyRecords);
            // We have decided to restore the installer. We must hold it's instance,
            // so that it's not GC'ed. If it is GC'ed, it'd be restored again,
            // after the module loads,
            // which would register the log handlers for the second time.
            installerHandle = installer;
        }
    }
}
