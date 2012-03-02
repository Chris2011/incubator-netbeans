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
package org.netbeans.modules.javascript2.editor.formatter;

import org.netbeans.modules.csl.api.test.CslTestBase.IndentPrefs;
import org.netbeans.modules.javascript2.editor.JsTestBase;

/**
 *
 * @author Petr Hejl
 */
public class JsFormatterTest extends JsTestBase {

    public JsFormatterTest(String testName) {
        super(testName);
    }
    
    public void testSimple() throws Exception {
        reformatFileContents("testfiles/simple.js",new IndentPrefs(4, 4));
    }

    public void testTrailingSpaces1() throws Exception {
        format("var a = 1;   \nvar b = 3;                   \n",
                "var a = 1;\nvar b = 3;\n", new IndentPrefs(4, 4));
    }

    public void testTrailingSpaces2() throws Exception {
        format("var a = 1;   \nvar b = 3;                   \n         \n",
                "var a = 1;\nvar b = 3;\n\n", new IndentPrefs(4, 4));
    }

    public void testIndentation1() throws Exception {
        format("\n var a = 1;   \n        var b = 3;                   \n",
                "\nvar a = 1;\nvar b = 3;\n", new IndentPrefs(4, 4));
    }

    public void testIndentation2() throws Exception {
        format(" var a = 1;   \n        var b = 3;                   \n",
                "var a = 1;\nvar b = 3;\n", new IndentPrefs(4, 4));
    }

    public void testFunctions1() throws Exception {
        reformatFileContents("testfiles/formatter/functions1.js",new IndentPrefs(4, 4));
    }

    public void testFunctions2() throws Exception {
        reformatFileContents("testfiles/formatter/functions2.js",new IndentPrefs(4, 4));
    }

    public void testFunctions3() throws Exception {
        reformatFileContents("testfiles/formatter/functions3.js",new IndentPrefs(4, 4));
    }

    public void testFunctions4() throws Exception {
        reformatFileContents("testfiles/formatter/functions4.js",new IndentPrefs(4, 4));
    }

    public void testFunctions5() throws Exception {
        reformatFileContents("testfiles/formatter/functions5.js",new IndentPrefs(4, 4));
    }

    public void testComments1() throws Exception {
        reformatFileContents("testfiles/formatter/comments1.js",new IndentPrefs(4, 4));
    }

    public void testObjects1() throws Exception {
        reformatFileContents("testfiles/formatter/objects1.js",new IndentPrefs(4, 4));
    }

    public void testSwitch1() throws Exception {
        reformatFileContents("testfiles/formatter/switch1.js",new IndentPrefs(4, 4));
    }

    public void testSwitch2() throws Exception {
        reformatFileContents("testfiles/formatter/switch2.js",new IndentPrefs(4, 4));
    }

    public void testIf1() throws Exception {
        reformatFileContents("testfiles/formatter/if1.js",new IndentPrefs(4, 4));
    }

    public void testDoWhile1() throws Exception {
        reformatFileContents("testfiles/formatter/dowhile1.js",new IndentPrefs(4, 4));
    }

    public void testFor1() throws Exception {
        reformatFileContents("testfiles/formatter/for1.js",new IndentPrefs(4, 4));
    }

    public void testFor2() throws Exception {
        reformatFileContents("testfiles/formatter/for2.js",new IndentPrefs(4, 4));
    }

    public void testWhile1() throws Exception {
        reformatFileContents("testfiles/formatter/while1.js",new IndentPrefs(4, 4));
    }

    public void testWith1() throws Exception {
        reformatFileContents("testfiles/formatter/with1.js",new IndentPrefs(4, 4));
    }

    public void testFormatting1() throws Exception {
        reformatFileContents("testfiles/formatter/formatting1.js",new IndentPrefs(4, 4));
    }

    public void testFormatting2() throws Exception {
        reformatFileContents("testfiles/formatter/formatting2.js",new IndentPrefs(4, 4));
    }

    public void testCommas1() throws Exception {
        reformatFileContents("testfiles/formatter/commas1.js",new IndentPrefs(4, 4));
    }

    public void testPrototype() throws Exception {
        reformatFileContents("testfiles/formatter/prototype.js",new IndentPrefs(4, 4));
    }
}
