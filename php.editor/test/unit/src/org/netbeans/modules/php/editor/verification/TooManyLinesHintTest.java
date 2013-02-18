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
package org.netbeans.modules.php.editor.verification;

import java.util.prefs.Preferences;
import org.netbeans.modules.php.editor.verification.TooManyLinesHint.ClassLinesHint;
import org.netbeans.modules.php.editor.verification.TooManyLinesHint.FunctionLinesHint;
import org.netbeans.modules.php.editor.verification.TooManyLinesHint.InterfaceLinesHint;
import org.netbeans.modules.php.editor.verification.TooManyLinesHint.TraitLinesHint;

/**
 *
 * @author Ondrej Brejla <obrejla@netbeans.org>
 */
public class TooManyLinesHintTest extends PHPHintsTestBase {

    public TooManyLinesHintTest(String testName) {
        super(testName);
    }

    public void testFunctionsLinesFail() throws Exception {
        checkHintsInStartEndFile(new FunctionLinesHintStub(5), "testFunctionsLines.php");
    }

    public void testFunctionsLinesOk() throws Exception {
        checkHintsInStartEndFile(new FunctionLinesHintStub(10), "testFunctionsLines.php");
    }

    public void testClassesLinesFail() throws Exception {
        checkHintsInStartEndFile(new ClassLinesHintStub(5), "testClassesLines.php");
    }

    public void testClassesLinesOk() throws Exception {
        checkHintsInStartEndFile(new ClassLinesHintStub(10), "testClassesLines.php");
    }

    public void testInterfacesLinesFail() throws Exception {
        checkHintsInStartEndFile(new InterfaceLinesHintStub(5), "testInterfacesLines.php");
    }

    public void testInterfacesLinesOk() throws Exception {
        checkHintsInStartEndFile(new InterfaceLinesHintStub(10), "testInterfacesLines.php");
    }

    public void testTraitsLinesFail() throws Exception {
        checkHintsInStartEndFile(new TraitLinesHintStub(5), "testTraitsLines.php");
    }

    public void testTraitsLinesOk() throws Exception {
        checkHintsInStartEndFile(new TraitLinesHintStub(10), "testTraitsLines.php");
    }

    private static final class FunctionLinesHintStub extends FunctionLinesHint {
        private final int maxAllowedLines;

        public FunctionLinesHintStub(int maxAllowedLines) {
            this.maxAllowedLines = maxAllowedLines;
        }

        @Override
        public int getMaxAllowedLines(Preferences preferences) {
            return maxAllowedLines;
        }

    }

    private static final class ClassLinesHintStub extends ClassLinesHint {
        private final int maxAllowedLines;

        public ClassLinesHintStub(int maxAllowedLines) {
            this.maxAllowedLines = maxAllowedLines;
        }

        @Override
        public int getMaxAllowedLines(Preferences preferences) {
            return maxAllowedLines;
        }

    }

    private static final class InterfaceLinesHintStub extends InterfaceLinesHint {
        private final int maxAllowedLines;

        public InterfaceLinesHintStub(int maxAllowedLines) {
            this.maxAllowedLines = maxAllowedLines;
        }

        @Override
        public int getMaxAllowedLines(Preferences preferences) {
            return maxAllowedLines;
        }

    }

    private static final class TraitLinesHintStub extends TraitLinesHint {
        private final int maxAllowedLines;

        public TraitLinesHintStub(int maxAllowedLines) {
            this.maxAllowedLines = maxAllowedLines;
        }

        @Override
        public int getMaxAllowedLines(Preferences preferences) {
            return maxAllowedLines;
        }

    }

}
