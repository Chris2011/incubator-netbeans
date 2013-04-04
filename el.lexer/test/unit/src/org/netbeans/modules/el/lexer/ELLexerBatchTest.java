/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 */

package org.netbeans.modules.el.lexer;

import junit.framework.TestCase;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.lib.lexer.test.LexerTestUtilities;
import org.netbeans.modules.el.lexer.api.ELTokenId;

/**
 * Tests EL lexer.
 *
 * @author Marek Fukala
 * @author Martin Fousek <marfous@netbeans.org>
 */
public class ELLexerBatchTest extends TestCase {

    public ELLexerBatchTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        // Set-up testing environment
        LexerTestUtilities.setTesting(true);
    }

    public void testIdentifiers01() throws Exception {
        String text = "session";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "session");

        text = "myBean.property";
        ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "myBean");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.DOT, ".");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "property");
    }

    public void testMathExpression01() throws Exception {
        String text = "(6 * 0x5) + 05";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LPAREN, "(");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "6");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.MUL, "*");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.HEX_LITERAL, "0x5");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.RPAREN, ")");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.PLUS, "+");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.OCTAL_LITERAL, "05");
    }

    public void testBooleanLiteral01() throws Exception {
        String text = "property == true";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "property");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EQ_EQ, "==");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.TRUE_KEYWORD, "true");
    }

    public void testBooleanLiteral02() throws Exception {
        String text = "property == false";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "property");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EQ_EQ, "==");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.FALSE_KEYWORD, "false");
    }

    public void testNumberLiteral01() throws Exception {
        String text = "15 + 18";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "15");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.PLUS, "+");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "18");
    }

    public void testNumberLiteral02() throws Exception {
        String text = "15L + 18L";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LONG_LITERAL, "15L");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.PLUS, "+");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LONG_LITERAL, "18L");
    }

    public void testNumberLiteral03() throws Exception {
        String text = "0xF + 0x12";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.HEX_LITERAL, "0xF");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.PLUS, "+");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.HEX_LITERAL, "0x12");
    }

    public void testNumberLiteral04() throws Exception {
        String text = "015 + 017";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.OCTAL_LITERAL, "015");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.PLUS, "+");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.OCTAL_LITERAL, "017");
    }

    public void testNumberLiteral05() throws Exception {
        String text = "1.5e+20f + 1.7e+20f";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.FLOAT_LITERAL, "1.5e+20f");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.PLUS, "+");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.FLOAT_LITERAL, "1.7e+20f");
    }

    public void testNumberLiteral06() throws Exception {
        String text = "1.5e+20 + 1.7e+20D";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.DOUBLE_LITERAL, "1.5e+20");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.PLUS, "+");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.DOUBLE_LITERAL, "1.7e+20D");
    }

    public void testNumberLiteral07() throws Exception {
        String text = "1.5f + 1.7F";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.FLOAT_LITERAL, "1.5f");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.PLUS, "+");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.FLOAT_LITERAL, "1.7F");
    }

    public void testNumberLiteral08() throws Exception {
        String text = "1.5d + 1.7D";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.DOUBLE_LITERAL, "1.5d");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.PLUS, "+");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.DOUBLE_LITERAL, "1.7D");
    }

    public void testStringLiteral01() throws Exception {
        String text = "\"string in quotes\"";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.STRING_LITERAL, "\"string in quotes\"");
    }

    public void testStringLiteral02() throws Exception {
        String text = "'string in apostrophes'";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.STRING_LITERAL, "'string in apostrophes'");
    }

    public void testStringLiteral03() throws Exception {
        String text = "\"string with quote \\\" \"";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.STRING_LITERAL, "\"string with quote \\\" \"");
    }

    public void testStringLiteral04() throws Exception {
        String text = "\"string with slashes \\\\ \"";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.STRING_LITERAL, "\"string with slashes \\\\ \"");
    }

    public void testStringLiteral05() throws Exception {
        String text = "'string with apostrophes \\''";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.STRING_LITERAL, "'string with apostrophes \\''");
    }

    public void testBrackets01() throws Exception {
        String text = "expr[\"identifier\"]";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "expr");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LBRACKET, "[");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.STRING_LITERAL, "\"identifier\"");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.RBRACKET, "]");
    }

    public void testParenthesis01() throws Exception {
        String text = "expr.identifier(params)";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "expr");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.DOT, ".");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "identifier");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LPAREN, "(");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "params");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.RPAREN, ")");
    }

    public void testMathOperator01() throws Exception {
        String text = "1 + 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.PLUS, "+");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testMathOperator02() throws Exception {
        String text = "1 - 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.MINUS, "-");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testMathOperator03() throws Exception {
        String text = "1 * 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.MUL, "*");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testMathOperator04() throws Exception {
        String text = "1 / 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.DIV, "/");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testMathOperator05() throws Exception {
        String text = "1 % 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.MOD, "%");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testRelationalOperator01() throws Exception {
        String text = "1 == 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EQ_EQ, "==");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testRelationalOperator02() throws Exception {
        String text = "1 != 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.NOT_EQ, "!=");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testRelationalOperator03() throws Exception {
        String text = "1 < 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LT, "<");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testRelationalOperator04() throws Exception {
        String text = "1 <= 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LT_EQ, "<=");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testRelationalOperator05() throws Exception {
        String text = "1 > 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.GT, ">");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testRelationalOperator06() throws Exception {
        String text = "1 >= 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.GT_EQ, ">=");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testLogicalOperator01() throws Exception {
        String text = "true && true";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.TRUE_KEYWORD, "true");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.AND_AND, "&&");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.TRUE_KEYWORD, "true");
    }

    public void testLogicalOperator02() throws Exception {
        String text = "true || true";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.TRUE_KEYWORD, "true");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.OR_OR, "||");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.TRUE_KEYWORD, "true");
    }

    public void testLogicalOperator03() throws Exception {
        String text = "!true";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.NOT, "!");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.TRUE_KEYWORD, "true");
    }

    public void testConditionalOperator01() throws Exception {
        String text = "A ? B : C";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "A");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.QUESTION, "?");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "B");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.COLON, ":");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "C");
    }

    public void testEqualOperator01() throws Exception {
        String text = "A ==";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "A");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EQ_EQ, "==");
    }

    public void testEqualOperator02() throws Exception {
        String text = "[A ==";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LBRACKET, "[");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "A");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EQ_EQ, "==");
    }

    public void testAssigmentOperator01() throws Exception {
        String text = "A = B";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "A");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EQ, "=");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "B");
    }

    public void testAssigmentOperator02() throws Exception {
        String text = "A =";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "A");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EQ, "=");
    }

    public void testAssigmentOperator03() throws Exception {
        String text = "[A =";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LBRACKET, "[");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "A");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EQ, "=");
    }

    public void testSemicolonOperator01() throws Exception {
        String text = "A ; B";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "A");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.SEMICOLON, ";");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "B");
    }

    public void testLambdaOperator01() throws Exception {
        String text = "x -> (a=x)";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "x");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LAMBDA, "->");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LPAREN, "(");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "a");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EQ, "=");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "x");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.RPAREN, ")");
    }

    public void testLambdaOperator02() throws Exception {
        String text = "x ->";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "x");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LAMBDA, "->");
    }

    public void testMinusOperator01() throws Exception {
        String text = "x -";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "x");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.MINUS, "-");
    }

    public void testNullKeyword01() throws Exception {
        String text = "property == null";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "property");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EQ_EQ, "==");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.NULL_KEYWORD, "null");
    }

    public void testDivKeyword01() throws Exception {
        String text = "1 div 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.DIV_KEYWORD, "div");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testModKeyword01() throws Exception {
        String text = "1 mod 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.MOD_KEYWORD, "mod");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testAndKeyword01() throws Exception {
        String text = "true and true";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.TRUE_KEYWORD, "true");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.AND_KEYWORD, "and");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.TRUE_KEYWORD, "true");
    }

    public void testOrKeyword01() throws Exception {
        String text = "true or true";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.TRUE_KEYWORD, "true");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.OR_KEYWORD, "or");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.TRUE_KEYWORD, "true");
    }

    public void testNotKeyword01() throws Exception {
        String text = "not true";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.NOT_KEYWORD, "not");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.TRUE_KEYWORD, "true");
    }

    public void testEqKeyword01() throws Exception {
        String text = "1 eq 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EQ_KEYWORD, "eq");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testNeKeyword01() throws Exception {
        String text = "1 ne 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.NE_KEYWORD, "ne");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testLtKeyword01() throws Exception {
        String text = "1 lt 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LT_KEYWORD, "lt");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testTKeyword01() throws Exception {
        String text = "Boolean.TRUE";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "Boolean");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.DOT, ".");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "TRUE");
    }

    public void testTKeyword02() throws Exception {
        String text = "ThisBean == Boolean.TRUE";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "ThisBean");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EQ_EQ, "==");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "Boolean");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.DOT, ".");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "TRUE");
    }

    public void testGtKeyword01() throws Exception {
        String text = "1 gt 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.GT_KEYWORD, "gt");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testLeKeyword01() throws Exception {
        String text = "1 le 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.LE_KEYWORD, "le");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testGeKeyword01() throws Exception {
        String text = "1 ge 1";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.GE_KEYWORD, "ge");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INT_LITERAL, "1");
    }

    public void testInstanceOfKeyword01() throws Exception {
        String text = "bean instanceof Boolean";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "bean");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.INSTANCEOF_KEYWORD, "instanceof");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "Boolean");
    }

    public void testEmptyKeyword01() throws Exception {
        String text = "empty bean.list";
        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.EMPTY_KEYWORD, "empty");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "bean");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.DOT, ".");
        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.IDENTIFIER, "list");
    }

//    public void testCatKeyword01() throws Exception {
//        String text = "'a' cat 'b'";
//        TokenSequence ts = TokenHierarchy.create(text, ELTokenId.language()).tokenSequence();
//        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.STRING_LITERAL, "'a'");
//        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
//        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.CAT_KEYWORD, "cat");
//        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.WHITESPACE, " ");
//        LexerTestUtilities.assertNextTokenEquals(ts, ELTokenId.STRING_LITERAL, "'b'");
//    }

}
