/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */
package org.netbeans.modules.nativeexecution;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.text.ParseException;
import java.util.Map;
import java.util.regex.Pattern;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironment;
import org.netbeans.modules.nativeexecution.api.util.HostInfoUtils;
import org.netbeans.modules.nativeexecution.support.Logger;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.Utilities;

public final class LocalNativeProcess extends AbstractNativeProcess {

    private final static java.util.logging.Logger log = Logger.getInstance();
    private final static String shell;
    private final InputStream processOutput;
    private final InputStream processError;
    private final OutputStream processInput;
    private final Process process;

    static {
        String sh = null;

        try {
            sh = HostInfoUtils.getShell(new ExecutionEnvironment());
        } catch (ConnectException ex) {
        }

        shell = sh;
    }

    // TODO: For now cygwin is the ONLY tested environment on Windows!
    public LocalNativeProcess(NativeProcessInfo info) throws IOException {
        super(info);

        if (Utilities.isWindows() && shell == null) {
            throw new IOException("CYGWIN currently is the ONLY supported env on Windows."); // NOI18N
        }

        final String workingDirectory = info.getWorkingDirectory(true);
        final File wdir =
                workingDirectory == null ? null : new File(workingDirectory);

        final ProcessBuilder pb;
        final Map<String, String> env = info.getEnvVariables();

        pb = new ProcessBuilder(shell, "-s");// NOI18N

        if (info.isUnbuffer()) {
            String unbufferPath = null; // NOI18N
            String unbufferLib = null; // NOI18N

            try {
                unbufferPath = info.macroExpander.expandPredefinedMacros(
                        "bin/nativeexecution/$osname-$platform"); // NOI18N
                unbufferLib = info.macroExpander.expandPredefinedMacros(
                        "unbuffer.$soext"); // NOI18N
            } catch (ParseException ex) {
            }

            if (unbufferLib != null && unbufferPath != null) {
                InstalledFileLocator fl = InstalledFileLocator.getDefault();
                File file = fl.locate(unbufferPath + "/" + unbufferLib, null, false); // NOI18N

                if (file != null && file.exists()) {
                    unbufferPath = file.getParentFile().getAbsolutePath();
                    String ldPreload = env.get("LD_PRELOAD"); // NOI18N
                    ldPreload = ((ldPreload == null) ? "" : (ldPreload + ":")) + // NOI18N
                            unbufferLib; // NOI18N
                    env.put("LD_PRELOAD", ldPreload); // NOI18N

                    String ldLibPath = env.get("LD_LIBRARY_PATH"); // NOI18N
                    ldLibPath = ((ldLibPath == null) ? "" : (ldLibPath + ":")) + // NOI18N
                            unbufferPath + ":" + unbufferPath + "_64"; // NOI18N
                    env.put("LD_LIBRARY_PATH", ldLibPath); // NOI18N
                }
            }
        }

        Process pr = null;

        try {
            pr = pb.start();
        } catch (IOException ex) {
            Logger.getInstance().warning(ex.getMessage());
            throw ex;
        }

        process = pr;

        processOutput = process.getInputStream();
        processError = process.getErrorStream();
        processInput = process.getOutputStream();

        processInput.write("/bin/echo $$\n".getBytes()); // NOI18N
        processInput.flush();

        if (!env.isEmpty()) {
            String val = null;
            // Very simple sanity check of vars...
            Pattern pattern = Pattern.compile("[a-zA-Z_]+.*"); // NOI18N
            
            for (String var : env.keySet()) {
                if (!pattern.matcher(var).matches()) {
                    continue;
                }

                val = env.get(var);

                if (val != null) {
                    log.fine(var + "=\"" + env.get(var) + // NOI18N
                            "\" && export " + var); // NOI18N
                    processInput.write((var + "=\"" + env.get(var) + // NOI18N
                            "\" && export " + var + "\n").getBytes()); // NOI18N
                    processInput.flush();
                }
            }
        }

        if (wdir != null) {
            processInput.write(("cd \"" + wdir + "\"\n").getBytes()); // NOI18N
        }

        String cmd = "exec " + info.getCommandLine() + "\n"; // NOI18N
        processInput.write(cmd.getBytes());
        processInput.write("exit $?\n".getBytes()); // NOI18N
        processInput.flush();

        readPID(processOutput);
    }

    @Override
    public OutputStream getOutputStream() {
        return processInput;
    }

    @Override
    public InputStream getInputStream() {
        return processOutput;
    }

    @Override
    public InputStream getErrorStream() {
        return processError;
    }

    @Override
    public final int waitResult() throws InterruptedException {
        return process.waitFor();
    }

    @Override
    public void cancel() {
        process.destroy();
    }
}
