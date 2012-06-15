/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.c2c.tasks;

import com.tasktop.c2c.internal.client.tasks.core.CfcRepositoryConnector;
import com.tasktop.c2c.internal.client.tasks.core.client.CfcClientData;
import com.tasktop.c2c.internal.client.tasks.core.client.ICfcClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.mylyn.commons.net.AuthenticationCredentials;
import org.eclipse.mylyn.commons.net.AuthenticationType;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.TaskRepositoryLocationFactory;
import org.openide.util.Exceptions;

/**
 *
 * @author tomas
 */
public class DummyUtils {
    /********************************************************************************
     * Dummies
     ********************************************************************************/
    
    private static boolean firstRun = true;
    private static String uname;
    private static String passw;
    private static String proxyHost;
    private static String proxyPort;
   
    static TaskRepository repository;
    static CfcRepositoryConnector rc;
    static {
        rc = new CfcRepositoryConnector();
        rc.getClientManager().setTaskRepositoryLocationFactory(new TaskRepositoryLocationFactory());
        repository = new TaskRepository(rc.getConnectorKind(), "https://q.tasktop.com/alm/s/anagramgame/tasks");
        setup(repository);
    }
    
    public static CfcClientData getClientData(TaskRepository taskRepository) {
        ICfcClient client = rc.getClientManager().getClient(taskRepository);
        CfcClientData clientData = client.getCalmClientData();       
        
        if(!clientData.isInitialized()) {
            try {
                client.updateRepositoryConfiguration(new NullProgressMonitor());
            } catch (CoreException ex) {
                // XXX
                Exceptions.printStackTrace(ex);
            }
        }
        return client.getCalmClientData();
    }
    
    public static void setup(TaskRepository repository) {
        setupCredentials(repository);
    }
    
    private static void setupCredentials(TaskRepository repository) {
        if(firstRun) {
            if (uname == null) {
                uname = System.getProperty("team.user.login");
                passw = System.getProperty("team.user.password");
            }
            if (uname == null) { 
                try {
                        // if it is still null, check the file in ~
                    BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.home"), ".test-team")));
                    uname = br.readLine();
                    passw = br.readLine();

                    proxyHost = br.readLine();
                    proxyPort = br.readLine();

                    br.close();  
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            if (firstRun) {
                firstRun = false;
            }
        }
        
        AuthenticationCredentials authenticationCredentials = new AuthenticationCredentials(uname, passw);
        repository.setCredentials(AuthenticationType.REPOSITORY, authenticationCredentials, false);
        
        if(proxyHost != null && !proxyHost.isEmpty() && !proxyHost.trim().startsWith("#")) {
            repository.setProperty(TaskRepository.PROXY_HOSTNAME, proxyHost);
        }
        if(proxyPort != null && !proxyPort.isEmpty() && !proxyPort.trim().startsWith("#")) {
            repository.setProperty(TaskRepository.PROXY_PORT, proxyPort);
        }
        
    }
}
