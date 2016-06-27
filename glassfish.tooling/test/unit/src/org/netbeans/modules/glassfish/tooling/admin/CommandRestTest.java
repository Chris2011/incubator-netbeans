/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
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
 */
package org.netbeans.modules.glassfish.tooling.admin;

import java.io.File;
import org.netbeans.modules.glassfish.tooling.TaskState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import org.netbeans.modules.glassfish.tooling.CommonTest;
import static org.netbeans.modules.glassfish.tooling.CommonTest.readProperties;
import org.netbeans.modules.glassfish.tooling.GlassFishIdeException;
import org.netbeans.modules.glassfish.tooling.GlassFishStatus;
import org.netbeans.modules.glassfish.tooling.data.GlassFishServer;
import org.netbeans.modules.glassfish.tooling.data.StartupArgsEntity;
import org.netbeans.modules.glassfish.tooling.logging.Logger;
import org.netbeans.modules.glassfish.tooling.server.FetchLog;
import org.netbeans.modules.glassfish.tooling.server.FetchLogSimple;
import org.netbeans.modules.glassfish.tooling.server.ServerTasks;
import org.netbeans.modules.glassfish.tooling.utils.StreamLinesList;
import static org.testng.Assert.*;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Common GlassFish REST command execution test.
 * <p>
 * @author Tomas Kraus, Peter Benedikovic
 */
public abstract class CommandRestTest extends CommandTest {

    ////////////////////////////////////////////////////////////////////////////
    // Class attributes                                                       //
    ////////////////////////////////////////////////////////////////////////////

    /** Logger instance for this class. */
    private static final Logger LOGGER = new Logger(CommandRestTest.class);

    /** GlassFish test server property file. */
    private static final String GLASSFISH_PROPERTES
            = "src/test/java/org/netbeans/modules/glassfish/tooling/GlassFishRest.properties";

    /** GlassFish test server properties. */
    private static volatile Properties glassfishProperties;

    /** GlassFish test server object. */
    private static volatile GlassFishServer glassfishServer;
    
    /** GlassFish test server stdout lines stored in linked list. */
    protected static volatile StreamLinesList gfStdOut = null;

    /** GlassFish test server stderr lines stored in linked list. */
    protected static volatile StreamLinesList gfStdErr = null;

    ////////////////////////////////////////////////////////////////////////////
    // Static methods                                                         //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Get GlassFish test server properties.
     * <p>
     * @return GlassFish test server properties.
     */
    @BeforeSuite
    @Parameters({ "rest-properties" })
    public static Properties glassfishProperties(@Optional String propertyFile) {
        if (glassfishProperties != null) {
            return glassfishProperties;
        }
        else {
            synchronized(CommandRestTest.class) {
                if (glassfishProperties == null) {
                    if (propertyFile == null)
                        propertyFile = GLASSFISH_PROPERTES;
                    glassfishProperties = readProperties(propertyFile);
                }
            }
            return glassfishProperties;
        }
    }
    
    public static Properties glassfishProperties() {
        return glassfishProperties(GLASSFISH_PROPERTES);
    }

    /**
     * Get GlassFish test server properties for HTTP tests.
     * <p/>
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns
     * <code>null</code> if the property is not found.
     * <p/>
     * @param key Property key.
     * @return Value in GlassFish test server properties property list with
     *         the specified key value.
     */
    public static String getGlassFishProperty(String key) {
//        String value = glassfishProperties().getProperty(key);
//        if (value == null) {
//            return CommonTest.glassfishProperties().getProperty(key);
//        } else {
//            return value;
//        }
        return glassfishProperties().getProperty(key);
    }

    /**
     * Constructs <code>GlassFishServer</code> object using GlassFish
     * test server properties
     * <p/>
     * @param properties
     * @return <code>GlassFishServer</code> object initialized with GlassFish
     *         test server properties values.
     */
    public static GlassFishServer createGlassfishServer() {
        return CommonTest.createGlassfishServer(CommandRestTest.class);
    }

    /**
     * Get GlassFish test server object with REST test specific values.
     * <p>
     * @return GlassFish test server object with REST test specific values.
     */
    public static GlassFishServer glassFishServer() {
        if (glassfishServer != null) {
            return glassfishServer;
        }
        else {
            synchronized(CommandRestTest.class) {
                if (glassfishServer == null) {
                    glassfishServer = createGlassfishServer();
                    //GlassFishStatus.add(glassfishServer);
                }
            }
            return glassfishServer;
        }
    }

    /**
     * GlassFish startup.
     * <p/>
     * Starts GlassFish server for tests.
     */
    @SuppressWarnings("SleepWhileInLoop")
    @BeforeGroups(groups = {"rest-commands"})
    public static void startGlassFish() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(CommandRestTest.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        final String METHOD = "startGlassFish";
        LOGGER.log(Level.INFO, METHOD, "startFrame");
        LOGGER.log(Level.INFO, METHOD, "startText");
        LOGGER.log(Level.INFO, METHOD, "startFrame");
        String javaArgsProperty = getGlassFishProperty(GFPROP_JAVA_ARGS);
        String glassFishArgsProperty = getGlassFishProperty(
                GFPROP_GLASSFISH_ARGS);
        List<String> javaArgs = javaArgsProperty != null
                ? Arrays.asList(javaArgsProperty.split(" +"))
                : new ArrayList<String>();
        List<String> glassFishArgs = glassFishArgsProperty != null
                ? Arrays.asList(glassFishArgsProperty.split(" +"))
                : new ArrayList<String>();
        StartupArgsEntity startupArgs = new StartupArgsEntity(
                glassFishArgs,
                javaArgs,
                new HashMap<String, String>(),
                getJdkProperty(JDKPROP_HOME));
        GlassFishServer server = glassFishServer();
        // restore domain before starting server
        restoreDomain(server, getGlassFishProperty(BACKUP_DOMAIN));
        ResultProcess result = ServerTasks.startServer(server, startupArgs);
        ValueProcess process = result.getValue();
        FetchLog stdOutLog = new FetchLogSimple(process.getProcess()
                .getInputStream());
        FetchLog stdErrLog = new FetchLogSimple(process.getProcess()
                .getErrorStream());
        gfStdOut = new StreamLinesList(stdOutLog);
        gfStdErr = new StreamLinesList(stdErrLog);
        boolean exit = false;
        boolean started = false;
        boolean shutdownOnError = false;
        int emptyCycles = 0;
        while (!exit && emptyCycles++ < 10) {
            String line;                    
            while ((line = gfStdOut.getNext()) != null) {
                Matcher startedMessageMatcher
                        = STARTED_MESSAGE_PATTERN.matcher(line);
                Matcher shutdownMessageMatcher
                        = SHUTDOWN_MESSAGE_PATTERN.matcher(line);
                if (startedMessageMatcher.matches()) {
                    started = true;
                    exit = true;
                }
                if (shutdownMessageMatcher.matches()) {
                    shutdownOnError = true;
                }
                LOGGER.log(Level.INFO, METHOD, "stdout", line);
                emptyCycles = 0;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                fail("FetchLogData command execution failed: "
                        + ie.getMessage());
            }
        }
        assertNotNull(result.getValue());
        assertTrue(started);
        if (shutdownOnError) {
            LOGGER.log(Level.SEVERE, METHOD, "failed");
        }
    }

    /**
     * Test cleanup
     * 
     */
    @AfterGroups(groups = {"rest-commands"}, alwaysRun = true)
    //@Test(alwaysRun = true)
    public static void stopGlassFish() {
        final String METHOD = "stopGlassFish";
        LOGGER.log(Level.INFO, METHOD, "stopFrame");
        LOGGER.log(Level.INFO, METHOD, "stopText");
        LOGGER.log(Level.INFO, METHOD, "stopFrame");
        GlassFishServer server = glassFishServer();
        try {
            ResultString result = CommandStopDAS.stopDAS(server);
            gfStdOut.close();
            gfStdErr.close();
            assertEquals(result.getState(), TaskState.COMPLETED);

        } catch (GlassFishIdeException gfie) {
            fail("Version command execution failed: " + gfie.getMessage());
        }
    }

}
