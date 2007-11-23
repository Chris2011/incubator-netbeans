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
 * Contributor(s):
 *
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */

package org.netbeans.modules.groovy.grails;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import org.openide.execution.NbProcessDescriptor;
import org.openide.util.Exceptions;
import org.netbeans.modules.groovy.grails.settings.Settings;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.concurrent.CountDownLatch;
import org.openide.util.Utilities;

/**
 *
 * @author schmidtm
 */
public class GrailsServerRunnable implements Runnable {
    PrintWriter writer = null;
    String grailsExecutable;
    Settings settings;
    String cwdName;
    String cmd;
    private BufferedReader procOutput;
    private Process process;
    CountDownLatch outputReady = null;
    
    private  final Logger LOG = Logger.getLogger(GrailsServerRunnable.class.getName());
    
    public GrailsServerRunnable(CountDownLatch outputReady, String cwdName, String cmd){

        this.settings = Settings.getInstance();
        this.cwdName = cwdName; 
        this.cmd = cmd;
        this.outputReady = outputReady;
        
        this.grailsExecutable = settings.getGrailsBase() + ( Utilities.isWindows() ? "\\bin\\grails.bat" : "/bin/grails" ); // NOI18N
        }
    
    public void run() {
        if (new File(grailsExecutable).exists()) {
            try {
                NbProcessDescriptor grailsProcessDesc = new NbProcessDescriptor(grailsExecutable, cmd);

                File cwd = new File(cwdName);

                process = grailsProcessDesc.exec(null, null, cwd);

                // procOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                outputReady.countDown();

                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                    LOG.log(Level.WARNING, "Problem creating Process");
                    }                
           
        } else {
            LOG.log(Level.WARNING, "Executable doesn't exist...");
            }
    }

//    public BufferedReader getProcOutput() {
//        return procOutput;
//    }
    
    public Process getProcess() {
        return process;
    } 
    
    
}
