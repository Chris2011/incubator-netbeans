/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2015 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2015 Sun Microsystems, Inc.
 */
package org.netbeans.modules.php.editor.completion;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.modules.php.project.api.PhpSourcePath;
import org.netbeans.spi.java.classpath.support.ClassPathSupport;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

public class PHP70CodeCompletionTest extends PHPCodeCompletionTestBase {

    public PHP70CodeCompletionTest(String testName) {
        super(testName);
    }

    @Override
    protected Map<String, ClassPath> createClassPathsForTest() {
        return Collections.singletonMap(
            PhpSourcePath.SOURCE_CP,
            ClassPathSupport.createClassPath(new FileObject[] {
                FileUtil.toFileObject(new File(getDataDir(), "/testfiles/completion/lib/php70/"))
            })
        );
    }

    public void testIntTypeHint01() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function intTypeHint(^int $arg) {", false);
    }

    public void testIntTypeHint02() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function intTypeHint(in^t $arg) {", false);
    }

    public void testIntTypeHint03() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function __construct(^int $arg) {", false);
    }

    public void testIntTypeHint04() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function __construct(in^t $arg) {", false);
    }

    public void testFloatTypeHint01() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function floatTypeHint(^float $arg) {", false);
    }

    public void testFloatTypeHint02() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function floatTypeHint(flo^at $arg) {", false);
    }

    public void testFloatTypeHint03() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function __construct(^float $arg) {", false);
    }

    public void testFloatTypeHint04() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function __construct(flo^at $arg) {", false);
    }

    public void testStringTypeHint01() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function stringTypeHint(^string $arg) {", false);
    }

    public void testStringTypeHint02() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function stringTypeHint(strin^g $arg) {", false);
    }

    public void testStringTypeHint03() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function __construct(^string $arg) {", false);
    }

    public void testStringTypeHint04() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function __construct(strin^g $arg) {", false);
    }

    public void testBoolTypeHint01() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function boolTypeHint(^bool $arg) {", false);
    }

    public void testBoolTypeHint02() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function boolTypeHint(boo^l $arg) {", false);
    }

    public void testBoolTypeHint03() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function __construct(^bool $arg) {", false);
    }

    public void testBoolTypeHint04() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/scalarTypeHints.php", "function __construct(boo^l $arg) {", false);
    }

    public void testReturnType01() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/returnTypes.php", "function current(): ^Comment;", false);
    }

    public void testReturnType02() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/returnTypes.php", "function current(): Comm^ent;", false);
    }

    public void testReturnType03() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/returnTypes.php", "function &my_array_sort(array &$data): ^array {", false);
    }

    // XXX
    public void testReturnType04() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/returnTypes.php", "function &my_array_sort(array &$data): arr^ay {", false);
    }

    public void testReturnType05() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/returnTypes.php", "function foo(): ^Comment {", false);
    }

    public void testReturnType06() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/returnTypes.php", "function foo(): Comm^ent {", false);
    }

    public void testReturnType07() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/returnTypes.php", "function &bar(): ^\\My\\Firm\\Comment {", false);
    }

    public void testReturnType08() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/returnTypes.php", "function &bar(): \\^My\\Firm\\Comment {", false);
    }

    public void testReturnType09() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/returnTypes.php", "function &bar(): \\My\\Fi^rm\\Comment {", false);
    }

    public void testReturnType10() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/returnTypes.php", "function &bar(): \\My\\Firm\\^Comment {", false);
    }

    public void testReturnType11() throws Exception {
        checkCompletion("testfiles/completion/lib/php70/returnTypes.php", "function &bar(): \\My\\Firm\\Comm^ent {", false);
    }

}
