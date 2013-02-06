/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */

package org.netbeans.modules.php.phpunit.run;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.annotations.common.NullAllowed;
import org.netbeans.api.extexecution.input.LineProcessors;
import org.netbeans.api.extexecution.print.LineConvertor;
import org.netbeans.api.extexecution.print.LineConvertors;
import org.netbeans.modules.php.phpunit.commands.PhpUnit;
import org.netbeans.modules.php.spi.testing.coverage.Coverage;
import org.netbeans.modules.php.spi.testing.run.OutputLineHandler;
import org.netbeans.modules.php.spi.testing.run.TestSession;
import org.netbeans.modules.php.spi.testing.run.TestSuite;
import org.openide.util.NbBundle;
import org.openide.windows.OutputWriter;

public final class TestSessionImpl implements TestSession {

    private final List<TestSuite> testSuites = new ArrayList<TestSuite>();
    private final String customSuitePath;

    private long time = -1;
    private int tests = -1;
    private Coverage coverage;


    public TestSessionImpl(@NullAllowed String customSuitePath) {
        this.customSuitePath = customSuitePath;
    }

    public void addTestSuite(TestSuite testSuite) {
        testSuites.add(testSuite);
    }

    @Override
    public List<TestSuite> getTestSuites() {
        return testSuites;
    }

    public int getTests() {
        return tests;
    }

    public void setTests(int tests) {
        this.tests = tests;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @NbBundle.Messages({
        "# {0} - suite name",
        "TestSessionImpl.msg.customSuite=Using custom test suite {0}."
    })
    @Override
    public String getInitMessage() {
        if (customSuitePath != null) {
            return Bundle.TestSessionImpl_msg_customSuite(customSuitePath);
        }
        return null;
    }

    @NbBundle.Messages("TestSessionImpl.msg.output=Full output can be found in Output window.")
    @Override
    public String getFinishMessage() {
        return Bundle.TestSessionImpl_msg_output();
    }

    @Override
    public String toString() {
        return String.format("TestSessionImpl{time: %d, tests: %d, suites: %d}", time, tests, testSuites.size());
    }

    @Override
    public OutputLineHandler getOutputLineHandler() {
        return new PhpOutputLineHandler();
    }

    @Override
    public Coverage getCoverage() {
        return coverage;
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    //~ Inner classes

    private static final class PhpOutputLineHandler implements OutputLineHandler {

        private static final LineConvertor CONVERTOR = LineConvertors.filePattern(null, PhpUnit.LINE_PATTERN, null, 1, 2);


        @Override
        public void handleLine(OutputWriter out, String text) {
            LineProcessors.printing(out, CONVERTOR, true).processLine(text);
        }
    }

}
