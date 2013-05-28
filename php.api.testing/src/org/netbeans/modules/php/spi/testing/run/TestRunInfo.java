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
package org.netbeans.modules.php.spi.testing.run;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.annotations.common.NullAllowed;
import org.openide.filesystems.FileObject;
import org.openide.util.Parameters;

/**
 * Class holding information about a test run.
 * <p>
 * This class is thread-safe.
 */
public final class TestRunInfo {

    /**
     * Session type.
     */
    public static enum SessionType {
        /**
         * Normal run.
         */
        TEST,
        /**
         * Run under debugger.
         */
        DEBUG,
    }

    private final SessionType sessionType;
    private final FileObject workingDirectory;
    private final FileObject startFile;
    private final String suiteName;
    private final boolean coverageEnabled;
    private final List<TestInfo> customTests = new CopyOnWriteArrayList<>();
    private final Map<String, Object> parameters = new ConcurrentHashMap<>();

    private volatile boolean rerun = false;


    /**
     * Create new info about test run.
     * @param sessionType run or debug
     * @param workingDirectory working directory
     * @param startFile start file (can be directory)
     * @param suiteName test name, can be {@code null}
     * @param coverageEnabled {@code true} if the coverage is enabled and should be collected
     */
    private TestRunInfo(Builder builder) {
        assert builder != null;
        assert builder.sessionType != null;
        assert builder.workingDirectory != null;
        assert builder.startFile != null;

        this.sessionType = builder.sessionType;
        this.workingDirectory = builder.workingDirectory;
        this.startFile = builder.startFile;
        this.suiteName = builder.suiteName;
        this.coverageEnabled = builder.coverageEnabled;
    }

    /**
     * Get session type.
     * @return session type
     */
    public SessionType getSessionType() {
        return sessionType;
    }

    /**
     * Get initial file or directory of test run.
     * @return initial file or directory of test run
     */
    public FileObject getStartFile() {
        return startFile;
    }

    /**
     * Get name of the test to be run, can be {@code null}.
     * @return name of the test to be run, can be {@code null}
     * @since 0.3
     */
    @CheckForNull
    public String getSuiteName() {
        return suiteName;
    }

    /**
     * Return {@code true} if all tests are to be run.
     * @return {@code true} if all tests are to be run
     */
    public boolean allTests() {
        return suiteName == null;
    }

    /**
     * Get working directory of tests.
     * @return working directory of tests
     */
    public FileObject getWorkingDirectory() {
        return workingDirectory;
    }

    /**
     * Return {@code true} if code coverage should be collected (if supported).
     * @return {@code true} if code coverage should be collected (if supported)
     */
    public boolean isCoverageEnabled() {
        return coverageEnabled;
    }

    /**
     * Get custom tests to be run or empty list, never {@code null}.
     * @return custom tests to be run or empty list, never {@code null}
     */
    public List<TestInfo> getCustomTests() {
        return new ArrayList<>(customTests);
    }

    /**
     * Reset custom tests.
     * @see #getCustomTests()
     * @see #setCustomTests(java.util.Collection)
     */
    public void resetCustomTests() {
        customTests.clear();
    }

    /**
     * Set custom tests to be run.
     * @param tests custom tests to be run or empty list, never {@code null}
     */
    public void setCustomTests(Collection<TestInfo> tests) {
        Parameters.notNull("tests", tests); // NOI18N
        resetCustomTests();
        customTests.addAll(tests);
    }

    /**
     * Check whether this test run is rerun.
     * @return {@code true} if this test run is rerun
     */
    public boolean isRerun() {
        return rerun;
    }

    /**
     * Set whether this test run is rerun.
     * @param rerun {@code true} for rerun, {@code false} otherwise
     */
    public void setRerun(boolean rerun) {
        this.rerun = rerun;
    }

    /**
     * Get custom parameter previously stored using {@link #setParameter(String, Object) store} method.
     * @param <T> type of the parameter
     * @param key key of the parameter
     * @param type type of the parameter
     * @return parameter value or {@code null} if not found
     */
    public <T> T getParameter(String key, Class<T> type) {
        Parameters.notEmpty("key", key); // NOI18N
        Parameters.notNull("type", type); // NOI18N
        Object param = parameters.get(key);
        if (param == null) {
            return null;
        }
        return type.cast(param);
    }

    /**
     * Set custom parameter.
     * @param key key of the parameter
     * @param value value of the parameter
     */
    public void setParameter(String key, Object value) {
        Parameters.notEmpty("key", key); // NOI18N
        Parameters.notNull("value", value); // NOI18N
        parameters.put(key, value);
    }

    /**
     * Remove custom parameter.
     * @param key key of the parameter
     */
    public void removeParameter(String key) {
        Parameters.notEmpty("key", key); // NOI18N
        parameters.remove(key);
    }

    //~ Inner classes

    /**
     * Builder for {@link TestRunInfo}.
     * @since 0.3
     */
    public static final class Builder {

        SessionType sessionType;
        FileObject workingDirectory;
        FileObject startFile;
        String suiteName;
        boolean coverageEnabled;


        /**
         * Set session type.
         * @param sessionType session type
         * @return this instance
         */
        public Builder setSessionType(@NonNull SessionType sessionType) {
            this.sessionType = sessionType;
            return this;
        }

        /**
         * Set working directory.
         * @param workingDirectory working directory
         * @return this instance
         */
        public Builder setWorkingDirectory(@NonNull FileObject workingDirectory) {
            this.workingDirectory = workingDirectory;
            return this;
        }

        /**
         * Set start file (can be directory).
         * @param startFile start file (can be directory)
         * @return this instance
         */
        public Builder setStartFile(@NonNull FileObject startFile) {
            this.startFile = startFile;
            return this;
        }

        /**
         * Set test suite name, can be {@code null}.
         * @param suiteName test suite name, can be {@code null}
         * @return this instance
         */
        public Builder setSuiteName(@NullAllowed String suiteName) {
            this.suiteName = suiteName;
            return this;
        }

        /**
         * Set {@code true} if the coverage is enabled and should be collected.
         * @param coverageEnabled {@code true} if the coverage is enabled and should be collected
         * @return this instance
         */
        public Builder setCoverageEnabled(boolean coverageEnabled) {
            this.coverageEnabled = coverageEnabled;
            return this;
        }

        /**
         * Create {@link TestRunInfo}.
         * @return {@link TestRunInfo} instance
         */
        public TestRunInfo build() {
            Parameters.notNull("sessionType", sessionType); // NOI18N
            Parameters.notNull("workingDirectory", workingDirectory); // NOI18N
            Parameters.notNull("startFile", startFile); // NOI18N
            return new TestRunInfo(this);
        }

    }

    /**
     * Class representing information about a test.
     */
    public static final class TestInfo {

        /**
         * Unknown test type.
         * @since 0.3
         */
        public static final String UNKNOWN_TYPE = "UNKNOWN_TYPE"; // NOI18N

        private final String type;
        private final String name;
        private final String className;
        private final String location;


        /**
         * Create new information about a test.
         * @param type type of the test, typically an identifier of the testing provider, can be {@see #UNKNOWN_TYPE unknown}
         * @param name name of the test
         * @param className class name, can be {@code null}
         * @param location location, can be {@code null}
         */
        public TestInfo(String type, String name, @NullAllowed String className, @NullAllowed String location) {
            Parameters.notEmpty("type", name);
            Parameters.notEmpty("name", name);

            this.type = type;
            this.name = name;
            this.className = className;
            this.location = location;
        }

        /**
         * Get the type of the test, typically an identifier of the testing provider (can be {@see #UNKNOWN_TYPE unknown}).
         * @return the type of the test, typically an identifier of the testing provider (can be {@see #UNKNOWN_TYPE unknown})
         */
        public String getType() {
            return type;
        }

        /**
         * Get the name of the test.
         * @return name of the test
         */
        public String getName() {
            return name;
        }

        /**
         * Get the class name, can be {@code null}.
         * @return class name, can be {@code null}
         */
        @CheckForNull
        public String getClassName() {
            return className;
        }

        /**
         * Get the location, can be {@code null}.
         * @return location, can be {@code null}
         */
        @CheckForNull
        public String getLocation() {
            return location;
        }

        @Override
        public String toString() {
            return "TestInfo{" + "type=" + type + ", name=" + name + ", className=" + className + ", location=" + location + '}'; // NOI18N
        }

    }

}
