/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.php.editor.indent;

import java.util.HashMap;

/**
 *
 * @author Ondrej Brejla <obrejla@netbeans.org>
 */
public class PHPFormatterWrappingTest extends PHPFormatterTestBase {

    public PHPFormatterWrappingTest(String testName) {
        super(testName);
    }

    public void testWrapMethodCallArg01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        reformatFileContents("testfiles/formatting/wrapping/methodCallArg01.php", options);
    }

    public void testWrapMethodCallArg02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        reformatFileContents("testfiles/formatting/wrapping/methodCallArg02.php", options);
    }

    public void testWrapMethodCallArg03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        reformatFileContents("testfiles/formatting/wrapping/methodCallArg03.php", options);
    }

    public void testWrapMethodCallArg04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        reformatFileContents("testfiles/formatting/wrapping/methodCallArg04.php", options);
    }

    public void testWrapMethodCallArg05() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        reformatFileContents("testfiles/formatting/wrapping/methodCallArg05.php", options);
    }

    public void testWrapMethodCallArgWithAnonymousClass01() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_CALL_ARGS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/methodCallArgWithAnonymousClass01.php", options);
    }

    public void testWrapMethodCallArgWithAnonymousClass02() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_CALL_ARGS, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/methodCallArgWithAnonymousClass02.php", options);
    }

    public void testWrapMethodParams01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/methodParams01.php", options);
    }

    public void testWrapMethodParams02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/methodParams02.php", options);
    }

    public void testWrapMethodParams03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/methodParams03.php", options);
    }

    public void testWrapMethodParams04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/methodParams04.php", options);
    }

    public void testWrapMethodParams05() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/methodParams05.php", options);
    }

    public void testWrapMethodParams06() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.ALIGN_MULTILINE_METHOD_PARAMS, true);
        reformatFileContents("testfiles/formatting/wrapping/methodParams06.php", options);
    }

    public void testWrapMethodParams07() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/methodParams07.php", options);
    }

    // #270903
    public void testWrapMethodParams08() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/methodParams08.php", options);
    }

    public void testWrapMethodParams09() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/methodParams09.php", options);
    }

    public void testWrapMethodParams10() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/methodParams10.php", options);
    }

    public void testWrapMethodParams11() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/methodParams11.php", options);
    }

    public void testWrapMethodParams12() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/methodParams12.php", options);
    }

    public void testWrapInterfaces01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_EXTENDS_IMPLEMENTS_KEYWORD, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/interfaces01.php", options);
    }

    public void testWrapInterfaces02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_EXTENDS_IMPLEMENTS_KEYWORD, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/interfaces02.php", options);
    }

    public void testWrapInterfaces03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_EXTENDS_IMPLEMENTS_KEYWORD, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/interfaces03.php", options);
    }

    public void testWrapInterfaces04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_EXTENDS_IMPLEMENTS_KEYWORD, CodeStyle.WrapStyle.WRAP_IF_LONG);
        options.put(FmtOptions.WRAP_EXTENDS_IMPLEMENTS_LIST, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/interfaces04.php", options);
    }

    public void testWrapInterfaces05() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_EXTENDS_IMPLEMENTS_KEYWORD, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.WRAP_EXTENDS_IMPLEMENTS_LIST, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.ALIGN_MULTILINE_IMPLEMENTS, true);
        reformatFileContents("testfiles/formatting/wrapping/interfaces05.php", options);
    }

    public void testMethodChainCall01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_CHAINED_METHOD_CALLS, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.SPACE_AROUND_OBJECT_OPS, false);
        reformatFileContents("testfiles/formatting/wrapping/methodChainCall_01.php", options);
    }

    public void testWrappingForStatement01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR_STATEMENT, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/forStatement01.php", options);
    }

    public void testWrappingForStatement02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR_STATEMENT, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/forStatement02.php", options);
    }

    public void testWrappingForStatement03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR_STATEMENT, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.INITIAL_INDENT, 6);
        reformatFileContents("testfiles/formatting/wrapping/forStatement03.php", options);
    }

    public void testWrappingForStatement04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR_STATEMENT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/forStatement04.php", options);
    }

    public void testWrappingForStatement05() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR_STATEMENT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/forStatement05.php", options);
    }

    public void testWrappingForStatement06() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR_STATEMENT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/forStatement06.php", options);
    }

    public void testWrappingForStatement07() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR_STATEMENT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        options.put(FmtOptions.INITIAL_INDENT, 6);
        reformatFileContents("testfiles/formatting/wrapping/forStatement07.php", options);
    }

    public void testWrappingForStatement08() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR_STATEMENT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        options.put(FmtOptions.INITIAL_INDENT, 5);
        reformatFileContents("testfiles/formatting/wrapping/forStatement08.php", options);
    }

    public void testWrappingForStatement09() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR_STATEMENT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/forStatement09.php", options);
    }

    public void testWrappingForStatement10() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR_STATEMENT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/forStatement10.php", options);
    }

    public void testWrappingWhileStatement01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_WHILE_STATEMENT, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/whileStatement01.php", options);
    }

    public void testWrappingWhileStatement02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_WHILE_STATEMENT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/whileStatement02.php", options);
    }

    public void testWrappingWhileStatement03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_WHILE_STATEMENT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        options.put(FmtOptions.INITIAL_INDENT, 5);
        reformatFileContents("testfiles/formatting/wrapping/whileStatement03.php", options);
    }

    public void testWrappingDoWhileStatement01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_DO_WHILE_STATEMENT, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/doStatement01.php", options);
    }

    public void testWrappingDoWhileStatement02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_DO_WHILE_STATEMENT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/doStatement02.php", options);
    }

    public void testWrappingDoWhileStatement03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_DO_WHILE_STATEMENT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/doStatement03.php", options);
    }

    public void testWrappingIfStatement01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/ifStatement01.php", options);
    }

    public void testWrappingIfStatement02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/ifStatement02.php", options);
    }

    public void testWrappingIfStatement03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        options.put(FmtOptions.INITIAL_INDENT, 54);
        reformatFileContents("testfiles/formatting/wrapping/ifStatement03.php", options);
    }

    public void testWrappingFor01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/for01.php", options);
    }

    public void testWrappingFor02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/for02.php", options);
    }

    public void testWrappingBlock01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        reformatFileContents("testfiles/formatting/wrapping/block01.php", options);
    }

    public void testWrappingBlock02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_BLOCK_BRACES, false);
        reformatFileContents("testfiles/formatting/wrapping/block02.php", options);
    }

    public void testWrappingBlock03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_BLOCK_BRACES, true);
        reformatFileContents("testfiles/formatting/wrapping/block03.php", options);
    }

    public void testWrappingBlock04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_BLOCK_BRACES, false);
        reformatFileContents("testfiles/formatting/wrapping/block04.php", options);
    }

    public void testWrappingBlock05() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_BLOCK_BRACES, true);
        options.put(FmtOptions.CLASS_DECL_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.METHOD_DECL_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.IF_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.FOR_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.WHILE_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.SWITCH_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.CATCH_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.OTHER_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        reformatFileContents("testfiles/formatting/wrapping/block05.php", options);
    }

    public void testWrappingBlock06() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_BLOCK_BRACES, false);
        options.put(FmtOptions.CLASS_DECL_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.METHOD_DECL_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.IF_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.FOR_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.WHILE_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.SWITCH_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.CATCH_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        options.put(FmtOptions.OTHER_BRACE_PLACEMENT, CodeStyle.BracePlacement.NEW_LINE);
        reformatFileContents("testfiles/formatting/wrapping/block06.php", options);
    }

    public void testWrappingStatements01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        reformatFileContents("testfiles/formatting/wrapping/statements01.php", options);
    }

    public void testWrappingStatements02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_STATEMENTS_ON_THE_LINE, false);
        options.put(FmtOptions.SPACE_AFTER_SEMI, true);
        reformatFileContents("testfiles/formatting/wrapping/statements02.php", options);
    }

    public void testWrappingStatements03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_STATEMENTS_ON_THE_LINE, false);
        options.put(FmtOptions.SPACE_AFTER_SEMI, false);
        reformatFileContents("testfiles/formatting/wrapping/statements03.php", options);
    }

    public void testWrappingStatements04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        reformatFileContents("testfiles/formatting/wrapping/statements04.php", options);
    }

    public void testWrappingBinaryOps01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        reformatFileContents("testfiles/formatting/wrapping/binaryOps01.php", options);
    }

    public void testWrappingBinaryOps02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_BINARY_OPS, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, false);
        reformatFileContents("testfiles/formatting/wrapping/binaryOps02.php", options);
    }

    public void testWrappingBinaryOps03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_BINARY_OPS, CodeStyle.WrapStyle.WRAP_IF_LONG);
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, false);
        reformatFileContents("testfiles/formatting/wrapping/binaryOps03.php", options);
    }

    public void testWrappingBinaryOps04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_BINARY_OPS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, false);
        reformatFileContents("testfiles/formatting/wrapping/binaryOps04.php", options);
    }

    public void testWrappingBinaryOps05() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_BINARY_OPS, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, true);
        reformatFileContents("testfiles/formatting/wrapping/binaryOps05.php", options);
    }

    public void testWrappingBinaryOps06() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_BINARY_OPS, CodeStyle.WrapStyle.WRAP_IF_LONG);
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, true);
        reformatFileContents("testfiles/formatting/wrapping/binaryOps06.php", options);
    }

    public void testWrappingBinaryOps07() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_BINARY_OPS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, true);
        reformatFileContents("testfiles/formatting/wrapping/binaryOps07.php", options);
    }

    public void testTernaryOp01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_TERNARY_OPS, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/ternaryOp01.php", options);
    }

    public void testTernaryOp02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_TERNARY_OPS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/ternaryOp02.php", options);
    }

    public void testTernaryOp03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_TERNARY_OPS, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/ternaryOp03.php", options);
    }

    public void testTernaryOp04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_TERNARY_OPS, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/ternaryOp04.php", options);
    }

    public void testTernaryOp05() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_TERNARY_OPS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/ternaryOp05.php", options);
    }

    public void testTernaryOp06() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_TERNARY_OPS, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/ternaryOp06.php", options);
    }

    public void testTernaryOp07() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_TERNARY_OPS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/ternaryOp07.php", options);
    }

    public void testIssue189722_01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/issue189722_01.php", options);
    }

    public void testIssue189722_02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/issue189722_02.php", options);
    }

    public void testIssue189722_03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/issue189722_03.php", options);
    }

    public void testIssue189722_04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/issue189722_04.php", options);
    }

    public void testIssue189722_05() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/issue189722_05.php", options);
    }

    public void testIssue189722_06() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_FOR, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/issue189722_06.php", options);
    }

    public void testIssue211933_01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_CALL_ARGS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/issue211933_01.php", options);
    }

    public void testIssue211933_02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_CALL_ARGS, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/issue211933_02.php", options);
    }

    public void testIssue211933_03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_CALL_ARGS, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/issue211933_03.php", options);
    }

    public void testWrappingAssignOps_01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, false);
        reformatFileContents("testfiles/formatting/wrapping/assignOps01.php", options);
    }

    public void testWrappingAssignOps_02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, false);
        reformatFileContents("testfiles/formatting/wrapping/assignOps02.php", options);
    }

    public void testWrappingAssignOps_03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_IF_LONG);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, false);
        reformatFileContents("testfiles/formatting/wrapping/assignOps03.php", options);
    }

    public void testWrappingAssignOps_04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, true);
        reformatFileContents("testfiles/formatting/wrapping/assignOps04.php", options);
    }

    public void testWrappingAssignOps_05() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, true);
        reformatFileContents("testfiles/formatting/wrapping/assignOps05.php", options);
    }

    public void testWrappingAssignOps_06() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_IF_LONG);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, true);
        reformatFileContents("testfiles/formatting/wrapping/assignOps06.php", options);
    }

    public void testWrappingArrayInit_01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ARRAY_INIT, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/arrayInit01.php", options);
    }

    public void testWrappingArrayInit_02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ARRAY_INIT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/arrayInit02.php", options);
    }

    public void testWrappingArrayInit_03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ARRAY_INIT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/arrayInit03.php", options);
    }

    public void testIssue222774_01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, false);
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/issue222774_01.php", options);
    }

    public void testIssue222774_02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, false);
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/issue222774_02.php", options);
    }

    public void testIssue222774_03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, false);
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/issue222774_03.php", options);
    }

    public void testIssue222774_04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, true);
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/issue222774_04.php", options);
    }

    public void testIssue222774_05() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, true);
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/issue222774_05.php", options);
    }

    public void testIssue222774_06() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, true);
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/issue222774_06.php", options);
    }

    public void testIssue222774_07() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, false);
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/issue222774_07.php", options);
    }

    public void testIssue222774_08() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, false);
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/issue222774_08.php", options);
    }

    public void testIssue222774_09() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, false);
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/issue222774_09.php", options);
    }

    public void testIssue222774_10() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, true);
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/issue222774_10.php", options);
    }

    public void testIssue222774_11() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, true);
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/issue222774_11.php", options);
    }

    public void testIssue222774_12() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_AFTER_BIN_OPS, true);
        options.put(FmtOptions.WRAP_IF_STATEMENT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/issue222774_12.php", options);
    }

    public void testIssue225535_01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, false);
        reformatFileContents("testfiles/formatting/wrapping/issue225535_01.php", options);
    }

    public void testIssue225535_02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, false);
        reformatFileContents("testfiles/formatting/wrapping/issue225535_02.php", options);
    }

    public void testIssue225535_03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, true);
        reformatFileContents("testfiles/formatting/wrapping/issue225535_03.php", options);
    }

    public void testIssue225535_04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, true);
        reformatFileContents("testfiles/formatting/wrapping/issue225535_04.php", options);
    }

    public void testIssue225535_05() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, false);
        reformatFileContents("testfiles/formatting/wrapping/issue225535_05.php", options);
    }

    public void testIssue225535_06() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, false);
        reformatFileContents("testfiles/formatting/wrapping/issue225535_06.php", options);
    }

    public void testIssue225535_07() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, true);
        reformatFileContents("testfiles/formatting/wrapping/issue225535_07.php", options);
    }

    public void testIssue225535_08() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ASSIGN_OPS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.WRAP_AFTER_ASSIGN_OPS, true);
        reformatFileContents("testfiles/formatting/wrapping/issue225535_08.php", options);
    }

    public void testIssue228712() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_EXTENDS_IMPLEMENTS_LIST, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/issue228712.php", options);
    }

    public void testIssue230286() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_CALL_ARGS, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/issue230286.php", options);
    }

    public void testIssue233527_01() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ARRAY_INIT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/issue233527_01.php", options);
    }

    public void testIssue233527_02() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ARRAY_INIT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/issue233527_02.php", options);
    }

    public void testIssue233527_03() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ARRAY_INIT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/issue233527_03.php", options);
    }

    public void testIssue233527_04() throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_ARRAY_INIT, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/issue233527_04.php", options);
    }

    public void testWrapGroupUseList_01() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.WRAP_GROUP_USE_BRACES, false);
        reformatFileContents("testfiles/formatting/wrapping/groupUseList01.php", options);
    }

    public void testWrapGroupUseList_02() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.WRAP_GROUP_USE_BRACES, false);
        reformatFileContents("testfiles/formatting/wrapping/groupUseList02.php", options);
    }

    public void testWrapGroupUseList_03() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_IF_LONG);
        options.put(FmtOptions.WRAP_GROUP_USE_BRACES, false);
        reformatFileContents("testfiles/formatting/wrapping/groupUseList03.php", options);
    }

    public void testWrapGroupUseListWithComments_01() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.WRAP_GROUP_USE_BRACES, false);
        reformatFileContents("testfiles/formatting/wrapping/groupUseListWithComments01.php", options);
    }

    public void testWrapGroupUseListWithComments_02() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.WRAP_GROUP_USE_BRACES, false);
        reformatFileContents("testfiles/formatting/wrapping/groupUseListWithComments02.php", options);
    }

    public void testWrapGroupUseListWithComments_03() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_IF_LONG);
        options.put(FmtOptions.WRAP_GROUP_USE_BRACES, false);
        reformatFileContents("testfiles/formatting/wrapping/groupUseListWithComments03.php", options);
    }

    public void testWrapGroupUseBraces_01() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_NEVER);
        options.put(FmtOptions.WRAP_GROUP_USE_BRACES, true);
        reformatFileContents("testfiles/formatting/wrapping/groupUseBraces01.php", options);
    }

    public void testWrapGroupUseBraces_02() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_ALWAYS);
        options.put(FmtOptions.WRAP_GROUP_USE_BRACES, true);
        reformatFileContents("testfiles/formatting/wrapping/groupUseBraces02.php", options);
    }

    public void testWrapGroupUseBraces_03() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_IF_LONG);
        options.put(FmtOptions.WRAP_GROUP_USE_BRACES, true);
        reformatFileContents("testfiles/formatting/wrapping/groupUseBraces03.php", options);
    }

    // #259031
    public void testWrapSelectedGroupUseListNever_01() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListNever01.php", options);
    }

    public void testWrapSelectedGroupUseListNever_02() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListNever02.php", options);
    }

    public void testWrapSelectedGroupUseListNever_03() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListNever03.php", options);
    }

    public void testWrapSelectedGroupUseListNever_04() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListNever04.php", options);
    }

    public void testWrapSelectedGroupUseListNever_05() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListNever05.php", options);
    }

    public void testWrapSelectedGroupUseListNever_06() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListNever06.php", options);
    }

    public void testWrapSelectedGroupUseListNever_07() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListNever07.php", options);
    }

    public void testWrapSelectedGroupUseListIfLong_01() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListIfLong01.php", options);
    }

    public void testWrapSelectedGroupUseListIfLong_02() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListIfLong02.php", options);
    }

    public void testWrapSelectedGroupUseListIfLong_03() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListIfLong03.php", options);
    }

    public void testWrapSelectedGroupUseListIfLong_04() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListIfLong04.php", options);
    }

    public void testWrapSelectedGroupUseListIfLong_05() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListIfLong05.php", options);
    }

    public void testWrapSelectedGroupUseListIfLong_06() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListIfLong06.php", options);
    }

    public void testWrapSelectedGroupUseListIfLong_07() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_IF_LONG);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListIfLong07.php", options);
    }

    public void testWrapSelectedGroupUseListAlways_01() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListAlways01.php", options);
    }

    public void testWrapSelectedGroupUseListAlways_02() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListAlways02.php", options);
    }

    public void testWrapSelectedGroupUseListAlways_03() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListAlways03.php", options);
    }

    public void testWrapSelectedGroupUseListAlways_04() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListAlways04.php", options);
    }

    public void testWrapSelectedGroupUseListAlways_05() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListAlways05.php", options);
    }

    public void testWrapSelectedGroupUseListAlways_06() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListAlways06.php", options);
    }

    public void testWrapSelectedGroupUseListAlways_07() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_GROUP_USE_LIST, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/selectedGroupUseListAlways07.php", options);
    }

    public void testIssue243203() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_CALL_ARGS, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/issue243203.php", options);
    }

    // PHP 7.3
    public void testFunctionCallTrailingCommas_01() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_CALL_ARGS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/functionCallTrailingCommas01.php", options);
    }

    public void testFunctionCallTrailingCommas_02() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_CALL_ARGS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/functionCallTrailingCommas02.php", options);
    }

    public void testFunctionCallTrailingCommas_03() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_CALL_ARGS, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/functionCallTrailingCommas03.php", options);
    }

    public void testFunctionCallTrailingCommas_04() throws Exception {
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_CALL_ARGS, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/functionCallTrailingCommas04.php", options);
    }

    // PHP 8.0
    public void testAllowTrailingCommaInParameterListAlways_01() throws Exception {
        // parameters are on each new line
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/php80/AllowTrailingCommaInParameterList/declarationsAlways01.php", options);
    }

    public void testAllowTrailingCommaInParameterListAlways_02() throws Exception {
        // all parameters are on the same line
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/php80/AllowTrailingCommaInParameterList/declarationsAlways02.php", options);
    }

    public void testAllowTrailingCommaInParameterListWithClosureInArrayAlways_01() throws Exception {
        // parameters are on each new line
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/php80/AllowTrailingCommaInParameterList/closureInArrayAlways01.php", options);
    }

    public void testAllowTrailingCommaInParameterListWithClosureInArrayAlways_02() throws Exception {
        // all parameters are on the same line
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_ALWAYS);
        reformatFileContents("testfiles/formatting/wrapping/php80/AllowTrailingCommaInParameterList/closureInArrayAlways02.php", options);
    }

    public void testAllowTrailingCommaInParameterListNever_01() throws Exception {
        // parameters are on each new line
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/php80/AllowTrailingCommaInParameterList/declarationsNever01.php", options);
    }

    public void testAllowTrailingCommaInParameterListNever_02() throws Exception {
        // all parameters are on each new line
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/php80/AllowTrailingCommaInParameterList/declarationsNever02.php", options);
    }

    public void testAllowTrailingCommaInParameterListWithClosureInArrayNever_01() throws Exception {
        // parameters are on each new line
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/php80/AllowTrailingCommaInParameterList/closureInArrayNever01.php", options);
    }

    public void testAllowTrailingCommaInParameterListWithClosureInArrayNever_02() throws Exception {
        // all parameters are on the same line
        HashMap<String, Object> options = new HashMap<>(FmtOptions.getDefaults());
        options.put(FmtOptions.WRAP_METHOD_PARAMS, CodeStyle.WrapStyle.WRAP_NEVER);
        reformatFileContents("testfiles/formatting/wrapping/php80/AllowTrailingCommaInParameterList/closureInArrayNever02.php", options);
    }

}
