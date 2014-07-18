/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.netbeans.modules.ko4j.debugging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.netbeans.modules.web.browser.api.PageInspector;
import org.netbeans.modules.web.webkit.debugging.api.WebKitDebugging;
import org.netbeans.modules.web.webkit.debugging.spi.Factory;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 * 
 * @author Jan Stola
 */
final class Server {
    private static final Server INSTANCE = new Server();
    private ServerSocket socket;

    public static Server getInstance() {
        return INSTANCE;
    }
    
    public int acceptClient() {
        ensureStarted();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket client = socket.accept();
                    MessageDispatcherImpl dispatcher = new MessageDispatcherImpl();
                    Transport transport = new Transport(client, dispatcher);
                    WebKitDebugging webKit = Factory.createWebKitDebugging(transport);
                    Lookup context = Lookups.fixed(transport, webKit, dispatcher);
                    PageInspector.getDefault().inspectPage(context);
                } catch (IOException ioex) {
                    Exceptions.printStackTrace(ioex);
                }
            }
        });
        t.start();
        return socket.getLocalPort();
    }

    private synchronized void ensureStarted() {
        if (socket != null) {
            return;
        }
        try {
            socket = new ServerSocket();
            socket.bind(null);
        } catch (IOException ioex) {
            Exceptions.printStackTrace(ioex);
        }
    }

}
