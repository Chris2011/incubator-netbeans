/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
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

package org.netbeans.modules.cnd.remote.mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.modules.cnd.api.compilers.PlatformTypes;
import org.netbeans.modules.cnd.api.utils.PlatformInfo;
import org.netbeans.modules.cnd.api.utils.RemoteUtils;
import org.openide.util.Exceptions;

/**
 *
 * @author Sergey Grinev
 */
public class HostMappingProviderWindows implements HostMappingProvider {

    public Map<String, String> findMappings(String hkey) {
        Map<String, String> mappings = null;
        try {
            Process process = Runtime.getRuntime().exec("net use");
            InputStream output = process.getInputStream();
            process.waitFor();
            mappings = parseNetUseOutput(RemoteUtils.getHostName(hkey), new InputStreamReader(output));
            return mappings;
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return mappings != null ? mappings : Collections.<String, String>emptyMap();
    }

    public boolean isApplicable(PlatformInfo hostPlatform, PlatformInfo otherPlatform) {
        return PlatformTypes.PLATFORM_WINDOWS == hostPlatform.getPlatform()
                && hostPlatform.isLocalhost(); // Windows is only supported as client platform
    }

    @SuppressWarnings("empty-statement")
    /* package */ static Map<String, String> parseNetUseOutput(String hostName, Reader outputReader) throws IOException {
        Map<String, String> mappings = new HashMap<String, String>();
        BufferedReader reader = new BufferedReader(outputReader);
        String line;
        // TODO: all those words are localized in non-English Windows versions
        for( line = reader.readLine(); line != null && !line.contains("Status"); line = reader.readLine()); //NOI18N
        if (line != null) {
            int nLocal = line.indexOf("Local"); //NOI18N
            int nRemote = line.indexOf("Remote"); //NOI18N
            int nNetwork = line.indexOf("Network"); //NOI18N
            for( line = reader.readLine(); line != null && !line.startsWith("----"); line = reader.readLine()) ; //NOI18N
            if (line != null) {
                for( line = reader.readLine(); line != null && line.indexOf(':')!=-1; line = reader.readLine() ) {  //NOI18N
                    String local = line.substring(nLocal, nRemote -1).trim(); // something like X:
                    String remote = line.substring(nRemote, nNetwork -1).trim(); // something like \\hostname\foldername
                    String[] arRemote = remote.substring(2).split("\\\\"); //NOI18N
                    if (arRemote.length >=2) {
                        String host = arRemote[0];
                        String folder = arRemote[1];

                        if (hostName.equals(host)) {
                            mappings.put(folder, local.toLowerCase());
                        }
                    }
                }
            }
        }

        return mappings;
    }
}
