// $ANTLR 3.3 Nov 30, 2010 12:50:56 /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g 2013-03-05 13:55:41

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */
package org.netbeans.modules.css.lib;
    


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.antlr.runtime.debug.*;
import java.io.IOException;
public class Css3Parser extends DebugParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NAMESPACE_SYM", "SEMI", "IDENT", "STRING", "URI", "CHARSET_SYM", "IMPORT_SYM", "COMMA", "MEDIA_SYM", "LBRACE", "RBRACE", "AND", "ONLY", "NOT", "GEN", "LPAREN", "COLON", "RPAREN", "AT_IDENT", "WS", "MOZ_DOCUMENT_SYM", "MOZ_URL_PREFIX", "MOZ_DOMAIN", "MOZ_REGEXP", "WEBKIT_KEYFRAMES_SYM", "PERCENTAGE", "PAGE_SYM", "COUNTER_STYLE_SYM", "FONT_FACE_SYM", "TOPLEFTCORNER_SYM", "TOPLEFT_SYM", "TOPCENTER_SYM", "TOPRIGHT_SYM", "TOPRIGHTCORNER_SYM", "BOTTOMLEFTCORNER_SYM", "BOTTOMLEFT_SYM", "BOTTOMCENTER_SYM", "BOTTOMRIGHT_SYM", "BOTTOMRIGHTCORNER_SYM", "LEFTTOP_SYM", "LEFTMIDDLE_SYM", "LEFTBOTTOM_SYM", "RIGHTTOP_SYM", "RIGHTMIDDLE_SYM", "RIGHTBOTTOM_SYM", "SOLIDUS", "PLUS", "GREATER", "TILDE", "MINUS", "HASH_SYMBOL", "HASH", "DOT", "LBRACKET", "DCOLON", "SASS_EXTEND_ONLY_SELECTOR", "STAR", "PIPE", "NAME", "LESS_AND", "OPEQ", "INCLUDES", "DASHMATCH", "BEGINS", "ENDS", "CONTAINS", "RBRACKET", "IMPORTANT_SYM", "NUMBER", "LENGTH", "EMS", "REM", "EXS", "ANGLE", "TIME", "FREQ", "RESOLUTION", "DIMENSION", "NL", "COMMENT", "SASS_DEFAULT", "SASS_VAR", "SASS_MIXIN", "SASS_INCLUDE", "LESS_DOTS", "LESS_REST", "LESS_WHEN", "GREATER_OR_EQ", "LESS", "LESS_OR_EQ", "SASS_EXTEND", "SASS_OPTIONAL", "SASS_DEBUG", "SASS_WARN", "SASS_IF", "CP_EQ", "SASS_FOR", "SASS_EACH", "SASS_WHILE", "HEXCHAR", "NONASCII", "UNICODE", "ESCAPE", "NMSTART", "NMCHAR", "URL", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "CDO", "CDC", "INVALID", "LINE_COMMENT"
    };
    public static final int EOF=-1;
    public static final int NAMESPACE_SYM=4;
    public static final int SEMI=5;
    public static final int IDENT=6;
    public static final int STRING=7;
    public static final int URI=8;
    public static final int CHARSET_SYM=9;
    public static final int IMPORT_SYM=10;
    public static final int COMMA=11;
    public static final int MEDIA_SYM=12;
    public static final int LBRACE=13;
    public static final int RBRACE=14;
    public static final int AND=15;
    public static final int ONLY=16;
    public static final int NOT=17;
    public static final int GEN=18;
    public static final int LPAREN=19;
    public static final int COLON=20;
    public static final int RPAREN=21;
    public static final int AT_IDENT=22;
    public static final int WS=23;
    public static final int MOZ_DOCUMENT_SYM=24;
    public static final int MOZ_URL_PREFIX=25;
    public static final int MOZ_DOMAIN=26;
    public static final int MOZ_REGEXP=27;
    public static final int WEBKIT_KEYFRAMES_SYM=28;
    public static final int PERCENTAGE=29;
    public static final int PAGE_SYM=30;
    public static final int COUNTER_STYLE_SYM=31;
    public static final int FONT_FACE_SYM=32;
    public static final int TOPLEFTCORNER_SYM=33;
    public static final int TOPLEFT_SYM=34;
    public static final int TOPCENTER_SYM=35;
    public static final int TOPRIGHT_SYM=36;
    public static final int TOPRIGHTCORNER_SYM=37;
    public static final int BOTTOMLEFTCORNER_SYM=38;
    public static final int BOTTOMLEFT_SYM=39;
    public static final int BOTTOMCENTER_SYM=40;
    public static final int BOTTOMRIGHT_SYM=41;
    public static final int BOTTOMRIGHTCORNER_SYM=42;
    public static final int LEFTTOP_SYM=43;
    public static final int LEFTMIDDLE_SYM=44;
    public static final int LEFTBOTTOM_SYM=45;
    public static final int RIGHTTOP_SYM=46;
    public static final int RIGHTMIDDLE_SYM=47;
    public static final int RIGHTBOTTOM_SYM=48;
    public static final int SOLIDUS=49;
    public static final int PLUS=50;
    public static final int GREATER=51;
    public static final int TILDE=52;
    public static final int MINUS=53;
    public static final int HASH_SYMBOL=54;
    public static final int HASH=55;
    public static final int DOT=56;
    public static final int LBRACKET=57;
    public static final int DCOLON=58;
    public static final int SASS_EXTEND_ONLY_SELECTOR=59;
    public static final int STAR=60;
    public static final int PIPE=61;
    public static final int NAME=62;
    public static final int LESS_AND=63;
    public static final int OPEQ=64;
    public static final int INCLUDES=65;
    public static final int DASHMATCH=66;
    public static final int BEGINS=67;
    public static final int ENDS=68;
    public static final int CONTAINS=69;
    public static final int RBRACKET=70;
    public static final int IMPORTANT_SYM=71;
    public static final int NUMBER=72;
    public static final int LENGTH=73;
    public static final int EMS=74;
    public static final int REM=75;
    public static final int EXS=76;
    public static final int ANGLE=77;
    public static final int TIME=78;
    public static final int FREQ=79;
    public static final int RESOLUTION=80;
    public static final int DIMENSION=81;
    public static final int NL=82;
    public static final int COMMENT=83;
    public static final int SASS_DEFAULT=84;
    public static final int SASS_VAR=85;
    public static final int SASS_MIXIN=86;
    public static final int SASS_INCLUDE=87;
    public static final int LESS_DOTS=88;
    public static final int LESS_REST=89;
    public static final int LESS_WHEN=90;
    public static final int GREATER_OR_EQ=91;
    public static final int LESS=92;
    public static final int LESS_OR_EQ=93;
    public static final int SASS_EXTEND=94;
    public static final int SASS_OPTIONAL=95;
    public static final int SASS_DEBUG=96;
    public static final int SASS_WARN=97;
    public static final int SASS_IF=98;
    public static final int CP_EQ=99;
    public static final int SASS_FOR=100;
    public static final int SASS_EACH=101;
    public static final int SASS_WHILE=102;
    public static final int HEXCHAR=103;
    public static final int NONASCII=104;
    public static final int UNICODE=105;
    public static final int ESCAPE=106;
    public static final int NMSTART=107;
    public static final int NMCHAR=108;
    public static final int URL=109;
    public static final int A=110;
    public static final int B=111;
    public static final int C=112;
    public static final int D=113;
    public static final int E=114;
    public static final int F=115;
    public static final int G=116;
    public static final int H=117;
    public static final int I=118;
    public static final int J=119;
    public static final int K=120;
    public static final int L=121;
    public static final int M=122;
    public static final int N=123;
    public static final int O=124;
    public static final int P=125;
    public static final int Q=126;
    public static final int R=127;
    public static final int S=128;
    public static final int T=129;
    public static final int U=130;
    public static final int V=131;
    public static final int W=132;
    public static final int X=133;
    public static final int Y=134;
    public static final int Z=135;
    public static final int CDO=136;
    public static final int CDC=137;
    public static final int INVALID=138;
    public static final int LINE_COMMENT=139;

    // delegates
    // delegators

    public static final String[] ruleNames = new String[] {
        "invalidRule", "less_condition_operator", "synpred16_Css3", "synpred10_Css3", 
        "elementSubsequent", "cp_variable", "synpred11_Css3", "synpred24_Css3", 
        "scss_interpolation_expression_var", "synpred14_Css3", "selectorsGroup", 
        "less_function_in_condition", "synpred7_Css3", "synpred18_Css3", 
        "sass_extend", "synpred4_Css3", "sass_each", "synpred3_Css3", "declaration", 
        "mediaQuery", "slAttributeValue", "cp_mixin_declaration", "cp_atomExp", 
        "synpred21_Css3", "synpred15_Css3", "mediaQueryOperator", "sass_control_block", 
        "syncTo_RBRACE", "scss_declaration_interpolation_expression", "imports", 
        "property", "moz_document", "declarations", "namespaces", "pseudoPage", 
        "charSetValue", "body", "styleSheet", "fnAttributeName", "sass_control", 
        "ws", "scss_mq_interpolation_expression", "rule", "fnAttribute", 
        "synpred12_Css3", "functionName", "importItem", "synpred2_Css3", 
        "margin", "atRuleId", "less_arg", "fontFace", "sass_each_list", 
        "esPred", "counterStyle", "synpred19_Css3", "selector", "charSet", 
        "cp_multiplyExp", "sass_for", "less_args_list", "synpred6_Css3", 
        "function", "resourceIdentifier", "generic_at_rule", "cssClass", 
        "cp_variable_declaration", "unaryOperator", "synpred1_Css3", "scss_nested_properties", 
        "bodyItem", "syncToFollow", "sass_control_expression", "pseudo", 
        "synpred5_Css3", "namespacePrefix", "expressionPredicate", "cp_mixin_call_args", 
        "mediaType", "fnAttributeValue", "sass_extend_only_selector", "combinator", 
        "synpred17_Css3", "term", "namespacePrefixName", "page", "propertyValue", 
        "syncTo_SEMI", "less_condition", "synpred9_Css3", "vendorAtRule", 
        "moz_document_function", "slAttributeName", "less_mixin_guarded", 
        "synpred23_Css3", "cp_expression", "cssId", "cp_mixin_call", "operator", 
        "hexColor", "mediaExpression", "synpred13_Css3", "synpred8_Css3", 
        "mediaQueryList", "webkitKeyframes", "slAttribute", "mediaFeature", 
        "webkitKeyframeSelectors", "sass_debug", "cp_additionExp", "simpleSelectorSequence", 
        "margin_sym", "sass_if", "typeSelector", "cp_term", "media", "expression", 
        "scss_selector_interpolation_expression", "scss_declaration_property_value_interpolation_expression", 
        "sass_while", "synpred20_Css3", "elementName", "prio", "namespace", 
        "syncToDeclarationsRule", "less_fn_name", "webkitKeyframesBlock", 
        "synpred22_Css3", "cp_mixin_name"
    };
    public static final boolean[] decisionCanBacktrack = new boolean[] {
        false, // invalid decision
        false, false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, true, false, false, false, 
            false, false, false, false, false, false, false, true, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, true, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, true, false, false, false, false, true, 
            false, false, true, false, true, false, true, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, true, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            true, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false
    };

     
        public int ruleLevel = 0;
        public int getRuleLevel() { return ruleLevel; }
        public void incRuleLevel() { ruleLevel++; }
        public void decRuleLevel() { ruleLevel--; }
        public Css3Parser(TokenStream input) {
            this(input, DebugEventSocketProxy.DEFAULT_DEBUGGER_PORT, new RecognizerSharedState());
        }
        public Css3Parser(TokenStream input, int port, RecognizerSharedState state) {
            super(input, state);
            DebugEventSocketProxy proxy =
                new DebugEventSocketProxy(this, port, null);
            setDebugListener(proxy);
            try {
                proxy.handshake();
            }
            catch (IOException ioe) {
                reportError(ioe);
            }
        }
    public Css3Parser(TokenStream input, DebugEventListener dbg) {
        super(input, dbg, new RecognizerSharedState());

    }
    protected boolean evalPredicate(boolean result, String predicate) {
        dbg.semanticPredicate(result, predicate);
        return result;
    }


    public String[] getTokenNames() { return Css3Parser.tokenNames; }
    public String getGrammarFileName() { return "/Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g"; }



        protected boolean isLessSource() {
            return false;
        }
        
        protected boolean isScssSource() {
            return false;
        }
        
        private boolean isCssPreprocessorSource() {
            return isLessSource() || isScssSource();
        }
        
    /**
         * Use the current stacked followset to work out the valid tokens that
         * can follow on from the current point in the parse, then recover by
         * eating tokens that are not a member of the follow set we compute.
         *
         * This method is used whenever we wish to force a sync, even though
         * the parser has not yet checked LA(1) for alt selection. This is useful
         * in situations where only a subset of tokens can begin a new construct
         * (such as the start of a new statement in a block) and we want to
         * proactively detect garbage so that the current rule does not exit on
         * on an exception.
         *
         * We could override recover() to make this the default behavior but that
         * is too much like using a sledge hammer to crack a nut. We want finer
         * grained control of the recovery and error mechanisms.
         */
        protected void syncToSet()
        {
            // Compute the followset that is in context wherever we are in the
            // rule chain/stack
            //
             BitSet follow = state.following[state._fsp]; //computeContextSensitiveRuleFOLLOW();

             syncToSet(follow);
        }

        protected void syncToSet(BitSet follow)
        {
            int mark = -1;

            //create error-recovery node
            dbg.enterRule(getGrammarFileName(), "recovery");

            try {

                mark = input.mark();

                // Consume all tokens in the stream until we find a member of the follow
                // set, which means the next production should be guaranteed to be happy.
                //
                while (! follow.member(input.LA(1)) ) {

                    if  (input.LA(1) == Token.EOF) {

                        // Looks like we didn't find anything at all that can help us here
                        // so we need to rewind to where we were and let normal error handling
                        // bail out.
                        //
                        input.rewind();
                        mark = -1;
                        return;
                    }
                    input.consume();

                    // Now here, because you are consuming some tokens, yu will probably want
                    // to raise an error message such as "Spurious elements after the class member were discarded"
                    // using whatever your override of displayRecognitionError() routine does to record
                    // error messages. The exact error my depend on context etc.
                    //
                }
            } catch (Exception e) {

              // Just ignore any errors here, we will just let the recognizer
              // try to resync as normal - something must be very screwed.
              //
            }
            finally {
                dbg.exitRule(getGrammarFileName(), "recovery");

                // Always release the mark we took
                //
                if  (mark != -1) {
                    input.release(mark);
                }
            }
        }
        
        /**
             * synces to next RBRACE "}" taking nesting into account
             */
            protected void syncToRBRACE(int nest)
                {
                    
                    int mark = -1;
                    //create error-recovery node
                    //dbg.enterRule(getGrammarFileName(), "recovery");

                    try {
                        mark = input.mark();
                        for(;;) {
                            //read char
                            int c = input.LA(1);
                            
                            switch(c) {
                                case Token.EOF:
                                    input.rewind();
                                    mark = -1;
                                    return ;
                                case Css3Lexer.LBRACE:
                                    nest++;
                                    break;
                                case Css3Lexer.RBRACE:
                                    nest--;
                                    if(nest == 0) {
                                        //do not eat the final RBRACE
                                        return ;
                                    }
                            }
                            
                            input.consume();
                                                
                        }

                    } catch (Exception e) {

                      // Just ignore any errors here, we will just let the recognizer
                      // try to resync as normal - something must be very screwed.
                      //
                    }
                    finally {
                        if  (mark != -1) {
                            input.release(mark);
                        }
                        //dbg.exitRule(getGrammarFileName(), "recovery");
                    }
                }
        



    // $ANTLR start "styleSheet"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:317:1: styleSheet : ( ws )? ( charSet ( ws )? )? ( imports )? ( namespaces )? ( body )? EOF ;
    public final void styleSheet() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "styleSheet");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(317, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:318:5: ( ( ws )? ( charSet ( ws )? )? ( imports )? ( namespaces )? ( body )? EOF )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:319:6: ( ws )? ( charSet ( ws )? )? ( imports )? ( namespaces )? ( body )? EOF
            {
            dbg.location(319,6);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:319:6: ( ws )?
            int alt1=2;
            try { dbg.enterSubRule(1);
            try { dbg.enterDecision(1, decisionCanBacktrack[1]);

            int LA1_0 = input.LA(1);

            if ( (LA1_0==WS||(LA1_0>=NL && LA1_0<=COMMENT)) ) {
                alt1=1;
            }
            } finally {dbg.exitDecision(1);}

            switch (alt1) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:319:6: ws
                    {
                    dbg.location(319,6);
                    pushFollow(FOLLOW_ws_in_styleSheet125);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(1);}

            dbg.location(320,6);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:320:6: ( charSet ( ws )? )?
            int alt3=2;
            try { dbg.enterSubRule(3);
            try { dbg.enterDecision(3, decisionCanBacktrack[3]);

            int LA3_0 = input.LA(1);

            if ( (LA3_0==CHARSET_SYM) ) {
                alt3=1;
            }
            } finally {dbg.exitDecision(3);}

            switch (alt3) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:320:8: charSet ( ws )?
                    {
                    dbg.location(320,8);
                    pushFollow(FOLLOW_charSet_in_styleSheet135);
                    charSet();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(320,16);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:320:16: ( ws )?
                    int alt2=2;
                    try { dbg.enterSubRule(2);
                    try { dbg.enterDecision(2, decisionCanBacktrack[2]);

                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==WS||(LA2_0>=NL && LA2_0<=COMMENT)) ) {
                        alt2=1;
                    }
                    } finally {dbg.exitDecision(2);}

                    switch (alt2) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:320:16: ws
                            {
                            dbg.location(320,16);
                            pushFollow(FOLLOW_ws_in_styleSheet137);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(2);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(3);}

            dbg.location(321,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:321:9: ( imports )?
            int alt4=2;
            try { dbg.enterSubRule(4);
            try { dbg.enterDecision(4, decisionCanBacktrack[4]);

            int LA4_0 = input.LA(1);

            if ( (LA4_0==IMPORT_SYM) ) {
                alt4=1;
            }
            } finally {dbg.exitDecision(4);}

            switch (alt4) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:321:9: imports
                    {
                    dbg.location(321,9);
                    pushFollow(FOLLOW_imports_in_styleSheet151);
                    imports();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(4);}

            dbg.location(322,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:322:9: ( namespaces )?
            int alt5=2;
            try { dbg.enterSubRule(5);
            try { dbg.enterDecision(5, decisionCanBacktrack[5]);

            int LA5_0 = input.LA(1);

            if ( (LA5_0==NAMESPACE_SYM) ) {
                alt5=1;
            }
            } finally {dbg.exitDecision(5);}

            switch (alt5) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:322:9: namespaces
                    {
                    dbg.location(322,9);
                    pushFollow(FOLLOW_namespaces_in_styleSheet162);
                    namespaces();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(5);}

            dbg.location(323,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:323:9: ( body )?
            int alt6=2;
            try { dbg.enterSubRule(6);
            try { dbg.enterDecision(6, decisionCanBacktrack[6]);

            int LA6_0 = input.LA(1);

            if ( (LA6_0==IDENT||LA6_0==MEDIA_SYM||LA6_0==GEN||LA6_0==COLON||LA6_0==AT_IDENT||LA6_0==MOZ_DOCUMENT_SYM||LA6_0==WEBKIT_KEYFRAMES_SYM||(LA6_0>=PAGE_SYM && LA6_0<=FONT_FACE_SYM)||(LA6_0>=MINUS && LA6_0<=PIPE)||LA6_0==LESS_AND||(LA6_0>=SASS_VAR && LA6_0<=SASS_INCLUDE)||(LA6_0>=SASS_DEBUG && LA6_0<=SASS_IF)||(LA6_0>=SASS_FOR && LA6_0<=SASS_WHILE)) ) {
                alt6=1;
            }
            } finally {dbg.exitDecision(6);}

            switch (alt6) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:323:9: body
                    {
                    dbg.location(323,9);
                    pushFollow(FOLLOW_body_in_styleSheet174);
                    body();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(6);}

            dbg.location(324,6);
            match(input,EOF,FOLLOW_EOF_in_styleSheet182); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(325, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "styleSheet");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "styleSheet"


    // $ANTLR start "namespaces"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:327:1: namespaces : ( namespace ( ws )? )+ ;
    public final void namespaces() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "namespaces");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(327, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:328:2: ( ( namespace ( ws )? )+ )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:329:2: ( namespace ( ws )? )+
            {
            dbg.location(329,2);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:329:2: ( namespace ( ws )? )+
            int cnt8=0;
            try { dbg.enterSubRule(8);

            loop8:
            do {
                int alt8=2;
                try { dbg.enterDecision(8, decisionCanBacktrack[8]);

                int LA8_0 = input.LA(1);

                if ( (LA8_0==NAMESPACE_SYM) ) {
                    alt8=1;
                }


                } finally {dbg.exitDecision(8);}

                switch (alt8) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:329:4: namespace ( ws )?
            	    {
            	    dbg.location(329,4);
            	    pushFollow(FOLLOW_namespace_in_namespaces199);
            	    namespace();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(329,14);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:329:14: ( ws )?
            	    int alt7=2;
            	    try { dbg.enterSubRule(7);
            	    try { dbg.enterDecision(7, decisionCanBacktrack[7]);

            	    int LA7_0 = input.LA(1);

            	    if ( (LA7_0==WS||(LA7_0>=NL && LA7_0<=COMMENT)) ) {
            	        alt7=1;
            	    }
            	    } finally {dbg.exitDecision(7);}

            	    switch (alt7) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:329:14: ws
            	            {
            	            dbg.location(329,14);
            	            pushFollow(FOLLOW_ws_in_namespaces201);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(7);}


            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        dbg.recognitionException(eee);

                        throw eee;
                }
                cnt8++;
            } while (true);
            } finally {dbg.exitSubRule(8);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(330, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "namespaces");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "namespaces"


    // $ANTLR start "namespace"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:332:1: namespace : NAMESPACE_SYM ( ws )? ( namespacePrefixName ( ws )? )? resourceIdentifier ( ws )? SEMI ;
    public final void namespace() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "namespace");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(332, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:333:3: ( NAMESPACE_SYM ( ws )? ( namespacePrefixName ( ws )? )? resourceIdentifier ( ws )? SEMI )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:333:5: NAMESPACE_SYM ( ws )? ( namespacePrefixName ( ws )? )? resourceIdentifier ( ws )? SEMI
            {
            dbg.location(333,5);
            match(input,NAMESPACE_SYM,FOLLOW_NAMESPACE_SYM_in_namespace217); if (state.failed) return ;
            dbg.location(333,19);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:333:19: ( ws )?
            int alt9=2;
            try { dbg.enterSubRule(9);
            try { dbg.enterDecision(9, decisionCanBacktrack[9]);

            int LA9_0 = input.LA(1);

            if ( (LA9_0==WS||(LA9_0>=NL && LA9_0<=COMMENT)) ) {
                alt9=1;
            }
            } finally {dbg.exitDecision(9);}

            switch (alt9) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:333:19: ws
                    {
                    dbg.location(333,19);
                    pushFollow(FOLLOW_ws_in_namespace219);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(9);}

            dbg.location(333,23);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:333:23: ( namespacePrefixName ( ws )? )?
            int alt11=2;
            try { dbg.enterSubRule(11);
            try { dbg.enterDecision(11, decisionCanBacktrack[11]);

            int LA11_0 = input.LA(1);

            if ( (LA11_0==IDENT) ) {
                alt11=1;
            }
            } finally {dbg.exitDecision(11);}

            switch (alt11) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:333:24: namespacePrefixName ( ws )?
                    {
                    dbg.location(333,24);
                    pushFollow(FOLLOW_namespacePrefixName_in_namespace223);
                    namespacePrefixName();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(333,44);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:333:44: ( ws )?
                    int alt10=2;
                    try { dbg.enterSubRule(10);
                    try { dbg.enterDecision(10, decisionCanBacktrack[10]);

                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==WS||(LA10_0>=NL && LA10_0<=COMMENT)) ) {
                        alt10=1;
                    }
                    } finally {dbg.exitDecision(10);}

                    switch (alt10) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:333:44: ws
                            {
                            dbg.location(333,44);
                            pushFollow(FOLLOW_ws_in_namespace225);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(10);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(11);}

            dbg.location(333,50);
            pushFollow(FOLLOW_resourceIdentifier_in_namespace230);
            resourceIdentifier();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(333,69);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:333:69: ( ws )?
            int alt12=2;
            try { dbg.enterSubRule(12);
            try { dbg.enterDecision(12, decisionCanBacktrack[12]);

            int LA12_0 = input.LA(1);

            if ( (LA12_0==WS||(LA12_0>=NL && LA12_0<=COMMENT)) ) {
                alt12=1;
            }
            } finally {dbg.exitDecision(12);}

            switch (alt12) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:333:69: ws
                    {
                    dbg.location(333,69);
                    pushFollow(FOLLOW_ws_in_namespace232);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(12);}

            dbg.location(333,73);
            match(input,SEMI,FOLLOW_SEMI_in_namespace235); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(334, 3);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "namespace");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "namespace"


    // $ANTLR start "namespacePrefixName"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:336:1: namespacePrefixName : IDENT ;
    public final void namespacePrefixName() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "namespacePrefixName");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(336, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:337:3: ( IDENT )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:337:5: IDENT
            {
            dbg.location(337,5);
            match(input,IDENT,FOLLOW_IDENT_in_namespacePrefixName248); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(338, 3);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "namespacePrefixName");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "namespacePrefixName"


    // $ANTLR start "resourceIdentifier"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:340:1: resourceIdentifier : ( STRING | URI );
    public final void resourceIdentifier() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "resourceIdentifier");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(340, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:341:3: ( STRING | URI )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
            {
            dbg.location(341,3);
            if ( (input.LA(1)>=STRING && input.LA(1)<=URI) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(342, 3);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "resourceIdentifier");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "resourceIdentifier"


    // $ANTLR start "charSet"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:344:1: charSet : CHARSET_SYM ( ws )? charSetValue ( ws )? SEMI ;
    public final void charSet() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "charSet");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(344, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:345:5: ( CHARSET_SYM ( ws )? charSetValue ( ws )? SEMI )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:345:9: CHARSET_SYM ( ws )? charSetValue ( ws )? SEMI
            {
            dbg.location(345,9);
            match(input,CHARSET_SYM,FOLLOW_CHARSET_SYM_in_charSet286); if (state.failed) return ;
            dbg.location(345,21);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:345:21: ( ws )?
            int alt13=2;
            try { dbg.enterSubRule(13);
            try { dbg.enterDecision(13, decisionCanBacktrack[13]);

            int LA13_0 = input.LA(1);

            if ( (LA13_0==WS||(LA13_0>=NL && LA13_0<=COMMENT)) ) {
                alt13=1;
            }
            } finally {dbg.exitDecision(13);}

            switch (alt13) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:345:21: ws
                    {
                    dbg.location(345,21);
                    pushFollow(FOLLOW_ws_in_charSet288);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(13);}

            dbg.location(345,25);
            pushFollow(FOLLOW_charSetValue_in_charSet291);
            charSetValue();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(345,38);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:345:38: ( ws )?
            int alt14=2;
            try { dbg.enterSubRule(14);
            try { dbg.enterDecision(14, decisionCanBacktrack[14]);

            int LA14_0 = input.LA(1);

            if ( (LA14_0==WS||(LA14_0>=NL && LA14_0<=COMMENT)) ) {
                alt14=1;
            }
            } finally {dbg.exitDecision(14);}

            switch (alt14) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:345:38: ws
                    {
                    dbg.location(345,38);
                    pushFollow(FOLLOW_ws_in_charSet293);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(14);}

            dbg.location(345,42);
            match(input,SEMI,FOLLOW_SEMI_in_charSet296); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(346, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "charSet");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "charSet"


    // $ANTLR start "charSetValue"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:348:1: charSetValue : STRING ;
    public final void charSetValue() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "charSetValue");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(348, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:349:2: ( STRING )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:349:4: STRING
            {
            dbg.location(349,4);
            match(input,STRING,FOLLOW_STRING_in_charSetValue310); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(350, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "charSetValue");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "charSetValue"


    // $ANTLR start "imports"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:352:1: imports : ( importItem ( ws )? )+ ;
    public final void imports() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "imports");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(352, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:353:2: ( ( importItem ( ws )? )+ )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:354:2: ( importItem ( ws )? )+
            {
            dbg.location(354,2);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:354:2: ( importItem ( ws )? )+
            int cnt16=0;
            try { dbg.enterSubRule(16);

            loop16:
            do {
                int alt16=2;
                try { dbg.enterDecision(16, decisionCanBacktrack[16]);

                int LA16_0 = input.LA(1);

                if ( (LA16_0==IMPORT_SYM) ) {
                    alt16=1;
                }


                } finally {dbg.exitDecision(16);}

                switch (alt16) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:354:4: importItem ( ws )?
            	    {
            	    dbg.location(354,4);
            	    pushFollow(FOLLOW_importItem_in_imports324);
            	    importItem();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(354,15);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:354:15: ( ws )?
            	    int alt15=2;
            	    try { dbg.enterSubRule(15);
            	    try { dbg.enterDecision(15, decisionCanBacktrack[15]);

            	    int LA15_0 = input.LA(1);

            	    if ( (LA15_0==WS||(LA15_0>=NL && LA15_0<=COMMENT)) ) {
            	        alt15=1;
            	    }
            	    } finally {dbg.exitDecision(15);}

            	    switch (alt15) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:354:15: ws
            	            {
            	            dbg.location(354,15);
            	            pushFollow(FOLLOW_ws_in_imports326);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(15);}


            	    }
            	    break;

            	default :
            	    if ( cnt16 >= 1 ) break loop16;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(16, input);
                        dbg.recognitionException(eee);

                        throw eee;
                }
                cnt16++;
            } while (true);
            } finally {dbg.exitSubRule(16);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(355, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "imports");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "imports"


    // $ANTLR start "importItem"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:357:1: importItem : ( IMPORT_SYM ( ws )? resourceIdentifier ( ws )? mediaQueryList SEMI | {...}? IMPORT_SYM ( ws )? resourceIdentifier ( ws )? ( COMMA ( ws )? resourceIdentifier ) mediaQueryList SEMI );
    public final void importItem() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "importItem");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(357, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:358:5: ( IMPORT_SYM ( ws )? resourceIdentifier ( ws )? mediaQueryList SEMI | {...}? IMPORT_SYM ( ws )? resourceIdentifier ( ws )? ( COMMA ( ws )? resourceIdentifier ) mediaQueryList SEMI )
            int alt22=2;
            try { dbg.enterDecision(22, decisionCanBacktrack[22]);

            try {
                isCyclicDecision = true;
                alt22 = dfa22.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(22);}

            switch (alt22) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:359:9: IMPORT_SYM ( ws )? resourceIdentifier ( ws )? mediaQueryList SEMI
                    {
                    dbg.location(359,9);
                    match(input,IMPORT_SYM,FOLLOW_IMPORT_SYM_in_importItem356); if (state.failed) return ;
                    dbg.location(359,20);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:359:20: ( ws )?
                    int alt17=2;
                    try { dbg.enterSubRule(17);
                    try { dbg.enterDecision(17, decisionCanBacktrack[17]);

                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==WS||(LA17_0>=NL && LA17_0<=COMMENT)) ) {
                        alt17=1;
                    }
                    } finally {dbg.exitDecision(17);}

                    switch (alt17) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:359:20: ws
                            {
                            dbg.location(359,20);
                            pushFollow(FOLLOW_ws_in_importItem358);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(17);}

                    dbg.location(359,24);
                    pushFollow(FOLLOW_resourceIdentifier_in_importItem361);
                    resourceIdentifier();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(359,43);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:359:43: ( ws )?
                    int alt18=2;
                    try { dbg.enterSubRule(18);
                    try { dbg.enterDecision(18, decisionCanBacktrack[18]);

                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==WS||(LA18_0>=NL && LA18_0<=COMMENT)) ) {
                        alt18=1;
                    }
                    } finally {dbg.exitDecision(18);}

                    switch (alt18) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:359:43: ws
                            {
                            dbg.location(359,43);
                            pushFollow(FOLLOW_ws_in_importItem363);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(18);}

                    dbg.location(359,47);
                    pushFollow(FOLLOW_mediaQueryList_in_importItem366);
                    mediaQueryList();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(359,62);
                    match(input,SEMI,FOLLOW_SEMI_in_importItem368); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:362:9: {...}? IMPORT_SYM ( ws )? resourceIdentifier ( ws )? ( COMMA ( ws )? resourceIdentifier ) mediaQueryList SEMI
                    {
                    dbg.location(362,9);
                    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "importItem", "isScssSource()");
                    }
                    dbg.location(362,27);
                    match(input,IMPORT_SYM,FOLLOW_IMPORT_SYM_in_importItem399); if (state.failed) return ;
                    dbg.location(362,38);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:362:38: ( ws )?
                    int alt19=2;
                    try { dbg.enterSubRule(19);
                    try { dbg.enterDecision(19, decisionCanBacktrack[19]);

                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==WS||(LA19_0>=NL && LA19_0<=COMMENT)) ) {
                        alt19=1;
                    }
                    } finally {dbg.exitDecision(19);}

                    switch (alt19) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:362:38: ws
                            {
                            dbg.location(362,38);
                            pushFollow(FOLLOW_ws_in_importItem401);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(19);}

                    dbg.location(362,42);
                    pushFollow(FOLLOW_resourceIdentifier_in_importItem404);
                    resourceIdentifier();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(362,61);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:362:61: ( ws )?
                    int alt20=2;
                    try { dbg.enterSubRule(20);
                    try { dbg.enterDecision(20, decisionCanBacktrack[20]);

                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==WS||(LA20_0>=NL && LA20_0<=COMMENT)) ) {
                        alt20=1;
                    }
                    } finally {dbg.exitDecision(20);}

                    switch (alt20) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:362:61: ws
                            {
                            dbg.location(362,61);
                            pushFollow(FOLLOW_ws_in_importItem406);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(20);}

                    dbg.location(362,65);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:362:65: ( COMMA ( ws )? resourceIdentifier )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:362:66: COMMA ( ws )? resourceIdentifier
                    {
                    dbg.location(362,66);
                    match(input,COMMA,FOLLOW_COMMA_in_importItem410); if (state.failed) return ;
                    dbg.location(362,72);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:362:72: ( ws )?
                    int alt21=2;
                    try { dbg.enterSubRule(21);
                    try { dbg.enterDecision(21, decisionCanBacktrack[21]);

                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==WS||(LA21_0>=NL && LA21_0<=COMMENT)) ) {
                        alt21=1;
                    }
                    } finally {dbg.exitDecision(21);}

                    switch (alt21) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:362:72: ws
                            {
                            dbg.location(362,72);
                            pushFollow(FOLLOW_ws_in_importItem412);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(21);}

                    dbg.location(362,76);
                    pushFollow(FOLLOW_resourceIdentifier_in_importItem415);
                    resourceIdentifier();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    dbg.location(362,96);
                    pushFollow(FOLLOW_mediaQueryList_in_importItem418);
                    mediaQueryList();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(362,111);
                    match(input,SEMI,FOLLOW_SEMI_in_importItem420); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(363, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "importItem");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "importItem"


    // $ANTLR start "media"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:364:1: media : MEDIA_SYM ( ws )? ( ( (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE )=> scss_mq_interpolation_expression ( ws )? | ( mediaQueryList )=> mediaQueryList ) LBRACE ( ws )? ( ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )+ COLON (~ ( SEMI | LBRACE | RBRACE ) )+ SEMI | scss_declaration_interpolation_expression COLON )=> declaration SEMI ( ws )? | {...}? sass_extend ( ws )? | {...}? sass_debug ( ws )? | {...}? sass_control ( ws )? | rule ( ws )? | page ( ws )? | fontFace ( ws )? | vendorAtRule ( ws )? | {...}? media ( ws )? )* RBRACE ;
    public final void media() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "media");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(364, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:365:5: ( MEDIA_SYM ( ws )? ( ( (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE )=> scss_mq_interpolation_expression ( ws )? | ( mediaQueryList )=> mediaQueryList ) LBRACE ( ws )? ( ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )+ COLON (~ ( SEMI | LBRACE | RBRACE ) )+ SEMI | scss_declaration_interpolation_expression COLON )=> declaration SEMI ( ws )? | {...}? sass_extend ( ws )? | {...}? sass_debug ( ws )? | {...}? sass_control ( ws )? | rule ( ws )? | page ( ws )? | fontFace ( ws )? | vendorAtRule ( ws )? | {...}? media ( ws )? )* RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:365:7: MEDIA_SYM ( ws )? ( ( (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE )=> scss_mq_interpolation_expression ( ws )? | ( mediaQueryList )=> mediaQueryList ) LBRACE ( ws )? ( ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )+ COLON (~ ( SEMI | LBRACE | RBRACE ) )+ SEMI | scss_declaration_interpolation_expression COLON )=> declaration SEMI ( ws )? | {...}? sass_extend ( ws )? | {...}? sass_debug ( ws )? | {...}? sass_control ( ws )? | rule ( ws )? | page ( ws )? | fontFace ( ws )? | vendorAtRule ( ws )? | {...}? media ( ws )? )* RBRACE
            {
            dbg.location(365,7);
            match(input,MEDIA_SYM,FOLLOW_MEDIA_SYM_in_media436); if (state.failed) return ;
            dbg.location(365,17);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:365:17: ( ws )?
            int alt23=2;
            try { dbg.enterSubRule(23);
            try { dbg.enterDecision(23, decisionCanBacktrack[23]);

            int LA23_0 = input.LA(1);

            if ( (LA23_0==WS||(LA23_0>=NL && LA23_0<=COMMENT)) ) {
                alt23=1;
            }
            } finally {dbg.exitDecision(23);}

            switch (alt23) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:365:17: ws
                    {
                    dbg.location(365,17);
                    pushFollow(FOLLOW_ws_in_media438);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(23);}

            dbg.location(367,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:367:9: ( ( (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE )=> scss_mq_interpolation_expression ( ws )? | ( mediaQueryList )=> mediaQueryList )
            int alt25=2;
            try { dbg.enterSubRule(25);
            try { dbg.enterDecision(25, decisionCanBacktrack[25]);

            try {
                isCyclicDecision = true;
                alt25 = dfa25.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(25);}

            switch (alt25) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:368:13: ( (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE )=> scss_mq_interpolation_expression ( ws )?
                    {
                    dbg.location(368,64);
                    pushFollow(FOLLOW_scss_mq_interpolation_expression_in_media493);
                    scss_mq_interpolation_expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(368,97);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:368:97: ( ws )?
                    int alt24=2;
                    try { dbg.enterSubRule(24);
                    try { dbg.enterDecision(24, decisionCanBacktrack[24]);

                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==WS||(LA24_0>=NL && LA24_0<=COMMENT)) ) {
                        alt24=1;
                    }
                    } finally {dbg.exitDecision(24);}

                    switch (alt24) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:368:97: ws
                            {
                            dbg.location(368,97);
                            pushFollow(FOLLOW_ws_in_media495);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(24);}


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:370:13: ( mediaQueryList )=> mediaQueryList
                    {
                    dbg.location(370,31);
                    pushFollow(FOLLOW_mediaQueryList_in_media529);
                    mediaQueryList();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(25);}

            dbg.location(373,9);
            match(input,LBRACE,FOLLOW_LBRACE_in_media558); if (state.failed) return ;
            dbg.location(373,16);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:373:16: ( ws )?
            int alt26=2;
            try { dbg.enterSubRule(26);
            try { dbg.enterDecision(26, decisionCanBacktrack[26]);

            int LA26_0 = input.LA(1);

            if ( (LA26_0==WS||(LA26_0>=NL && LA26_0<=COMMENT)) ) {
                alt26=1;
            }
            } finally {dbg.exitDecision(26);}

            switch (alt26) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:373:16: ws
                    {
                    dbg.location(373,16);
                    pushFollow(FOLLOW_ws_in_media560);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(26);}

            dbg.location(374,13);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:374:13: ( ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )+ COLON (~ ( SEMI | LBRACE | RBRACE ) )+ SEMI | scss_declaration_interpolation_expression COLON )=> declaration SEMI ( ws )? | {...}? sass_extend ( ws )? | {...}? sass_debug ( ws )? | {...}? sass_control ( ws )? | rule ( ws )? | page ( ws )? | fontFace ( ws )? | vendorAtRule ( ws )? | {...}? media ( ws )? )*
            try { dbg.enterSubRule(36);

            loop36:
            do {
                int alt36=10;
                try { dbg.enterDecision(36, decisionCanBacktrack[36]);

                try {
                    isCyclicDecision = true;
                    alt36 = dfa36.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(36);}

                switch (alt36) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:376:17: ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )+ COLON (~ ( SEMI | LBRACE | RBRACE ) )+ SEMI | scss_declaration_interpolation_expression COLON )=> declaration SEMI ( ws )?
            	    {
            	    dbg.location(376,134);
            	    pushFollow(FOLLOW_declaration_in_media646);
            	    declaration();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(376,146);
            	    match(input,SEMI,FOLLOW_SEMI_in_media648); if (state.failed) return ;
            	    dbg.location(376,151);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:376:151: ( ws )?
            	    int alt27=2;
            	    try { dbg.enterSubRule(27);
            	    try { dbg.enterDecision(27, decisionCanBacktrack[27]);

            	    int LA27_0 = input.LA(1);

            	    if ( (LA27_0==WS||(LA27_0>=NL && LA27_0<=COMMENT)) ) {
            	        alt27=1;
            	    }
            	    } finally {dbg.exitDecision(27);}

            	    switch (alt27) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:376:151: ws
            	            {
            	            dbg.location(376,151);
            	            pushFollow(FOLLOW_ws_in_media650);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(27);}


            	    }
            	    break;
            	case 2 :
            	    dbg.enterAlt(2);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:377:19: {...}? sass_extend ( ws )?
            	    {
            	    dbg.location(377,19);
            	    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        throw new FailedPredicateException(input, "media", "isScssSource()");
            	    }
            	    dbg.location(377,37);
            	    pushFollow(FOLLOW_sass_extend_in_media673);
            	    sass_extend();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(377,49);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:377:49: ( ws )?
            	    int alt28=2;
            	    try { dbg.enterSubRule(28);
            	    try { dbg.enterDecision(28, decisionCanBacktrack[28]);

            	    int LA28_0 = input.LA(1);

            	    if ( (LA28_0==WS||(LA28_0>=NL && LA28_0<=COMMENT)) ) {
            	        alt28=1;
            	    }
            	    } finally {dbg.exitDecision(28);}

            	    switch (alt28) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:377:49: ws
            	            {
            	            dbg.location(377,49);
            	            pushFollow(FOLLOW_ws_in_media675);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(28);}


            	    }
            	    break;
            	case 3 :
            	    dbg.enterAlt(3);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:378:19: {...}? sass_debug ( ws )?
            	    {
            	    dbg.location(378,19);
            	    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        throw new FailedPredicateException(input, "media", "isScssSource()");
            	    }
            	    dbg.location(378,37);
            	    pushFollow(FOLLOW_sass_debug_in_media698);
            	    sass_debug();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(378,48);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:378:48: ( ws )?
            	    int alt29=2;
            	    try { dbg.enterSubRule(29);
            	    try { dbg.enterDecision(29, decisionCanBacktrack[29]);

            	    int LA29_0 = input.LA(1);

            	    if ( (LA29_0==WS||(LA29_0>=NL && LA29_0<=COMMENT)) ) {
            	        alt29=1;
            	    }
            	    } finally {dbg.exitDecision(29);}

            	    switch (alt29) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:378:48: ws
            	            {
            	            dbg.location(378,48);
            	            pushFollow(FOLLOW_ws_in_media700);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(29);}


            	    }
            	    break;
            	case 4 :
            	    dbg.enterAlt(4);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:379:19: {...}? sass_control ( ws )?
            	    {
            	    dbg.location(379,19);
            	    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        throw new FailedPredicateException(input, "media", "isScssSource()");
            	    }
            	    dbg.location(379,37);
            	    pushFollow(FOLLOW_sass_control_in_media723);
            	    sass_control();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(379,50);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:379:50: ( ws )?
            	    int alt30=2;
            	    try { dbg.enterSubRule(30);
            	    try { dbg.enterDecision(30, decisionCanBacktrack[30]);

            	    int LA30_0 = input.LA(1);

            	    if ( (LA30_0==WS||(LA30_0>=NL && LA30_0<=COMMENT)) ) {
            	        alt30=1;
            	    }
            	    } finally {dbg.exitDecision(30);}

            	    switch (alt30) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:379:50: ws
            	            {
            	            dbg.location(379,50);
            	            pushFollow(FOLLOW_ws_in_media725);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(30);}


            	    }
            	    break;
            	case 5 :
            	    dbg.enterAlt(5);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:381:19: rule ( ws )?
            	    {
            	    dbg.location(381,19);
            	    pushFollow(FOLLOW_rule_in_media763);
            	    rule();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(381,25);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:381:25: ( ws )?
            	    int alt31=2;
            	    try { dbg.enterSubRule(31);
            	    try { dbg.enterDecision(31, decisionCanBacktrack[31]);

            	    int LA31_0 = input.LA(1);

            	    if ( (LA31_0==WS||(LA31_0>=NL && LA31_0<=COMMENT)) ) {
            	        alt31=1;
            	    }
            	    } finally {dbg.exitDecision(31);}

            	    switch (alt31) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:381:25: ws
            	            {
            	            dbg.location(381,25);
            	            pushFollow(FOLLOW_ws_in_media766);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(31);}


            	    }
            	    break;
            	case 6 :
            	    dbg.enterAlt(6);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:382:19: page ( ws )?
            	    {
            	    dbg.location(382,19);
            	    pushFollow(FOLLOW_page_in_media787);
            	    page();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(382,25);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:382:25: ( ws )?
            	    int alt32=2;
            	    try { dbg.enterSubRule(32);
            	    try { dbg.enterDecision(32, decisionCanBacktrack[32]);

            	    int LA32_0 = input.LA(1);

            	    if ( (LA32_0==WS||(LA32_0>=NL && LA32_0<=COMMENT)) ) {
            	        alt32=1;
            	    }
            	    } finally {dbg.exitDecision(32);}

            	    switch (alt32) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:382:25: ws
            	            {
            	            dbg.location(382,25);
            	            pushFollow(FOLLOW_ws_in_media790);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(32);}


            	    }
            	    break;
            	case 7 :
            	    dbg.enterAlt(7);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:383:19: fontFace ( ws )?
            	    {
            	    dbg.location(383,19);
            	    pushFollow(FOLLOW_fontFace_in_media811);
            	    fontFace();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(383,29);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:383:29: ( ws )?
            	    int alt33=2;
            	    try { dbg.enterSubRule(33);
            	    try { dbg.enterDecision(33, decisionCanBacktrack[33]);

            	    int LA33_0 = input.LA(1);

            	    if ( (LA33_0==WS||(LA33_0>=NL && LA33_0<=COMMENT)) ) {
            	        alt33=1;
            	    }
            	    } finally {dbg.exitDecision(33);}

            	    switch (alt33) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:383:29: ws
            	            {
            	            dbg.location(383,29);
            	            pushFollow(FOLLOW_ws_in_media814);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(33);}


            	    }
            	    break;
            	case 8 :
            	    dbg.enterAlt(8);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:384:19: vendorAtRule ( ws )?
            	    {
            	    dbg.location(384,19);
            	    pushFollow(FOLLOW_vendorAtRule_in_media835);
            	    vendorAtRule();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(384,33);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:384:33: ( ws )?
            	    int alt34=2;
            	    try { dbg.enterSubRule(34);
            	    try { dbg.enterDecision(34, decisionCanBacktrack[34]);

            	    int LA34_0 = input.LA(1);

            	    if ( (LA34_0==WS||(LA34_0>=NL && LA34_0<=COMMENT)) ) {
            	        alt34=1;
            	    }
            	    } finally {dbg.exitDecision(34);}

            	    switch (alt34) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:384:33: ws
            	            {
            	            dbg.location(384,33);
            	            pushFollow(FOLLOW_ws_in_media838);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(34);}


            	    }
            	    break;
            	case 9 :
            	    dbg.enterAlt(9);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:385:19: {...}? media ( ws )?
            	    {
            	    dbg.location(385,19);
            	    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        throw new FailedPredicateException(input, "media", "isScssSource()");
            	    }
            	    dbg.location(385,37);
            	    pushFollow(FOLLOW_media_in_media861);
            	    media();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(385,43);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:385:43: ( ws )?
            	    int alt35=2;
            	    try { dbg.enterSubRule(35);
            	    try { dbg.enterDecision(35, decisionCanBacktrack[35]);

            	    int LA35_0 = input.LA(1);

            	    if ( (LA35_0==WS||(LA35_0>=NL && LA35_0<=COMMENT)) ) {
            	        alt35=1;
            	    }
            	    } finally {dbg.exitDecision(35);}

            	    switch (alt35) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:385:43: ws
            	            {
            	            dbg.location(385,43);
            	            pushFollow(FOLLOW_ws_in_media863);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(35);}


            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);
            } finally {dbg.exitSubRule(36);}

            dbg.location(388,10);
            match(input,RBRACE,FOLLOW_RBRACE_in_media907); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(389, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "media");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "media"


    // $ANTLR start "mediaQueryList"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:391:1: mediaQueryList : ( mediaQuery ( COMMA ( ws )? mediaQuery )* )? ;
    public final void mediaQueryList() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "mediaQueryList");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(391, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:392:2: ( ( mediaQuery ( COMMA ( ws )? mediaQuery )* )? )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:392:4: ( mediaQuery ( COMMA ( ws )? mediaQuery )* )?
            {
            dbg.location(392,4);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:392:4: ( mediaQuery ( COMMA ( ws )? mediaQuery )* )?
            int alt39=2;
            try { dbg.enterSubRule(39);
            try { dbg.enterDecision(39, decisionCanBacktrack[39]);

            int LA39_0 = input.LA(1);

            if ( (LA39_0==IDENT||(LA39_0>=ONLY && LA39_0<=LPAREN)) ) {
                alt39=1;
            }
            } finally {dbg.exitDecision(39);}

            switch (alt39) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:392:6: mediaQuery ( COMMA ( ws )? mediaQuery )*
                    {
                    dbg.location(392,6);
                    pushFollow(FOLLOW_mediaQuery_in_mediaQueryList923);
                    mediaQuery();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(392,17);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:392:17: ( COMMA ( ws )? mediaQuery )*
                    try { dbg.enterSubRule(38);

                    loop38:
                    do {
                        int alt38=2;
                        try { dbg.enterDecision(38, decisionCanBacktrack[38]);

                        int LA38_0 = input.LA(1);

                        if ( (LA38_0==COMMA) ) {
                            alt38=1;
                        }


                        } finally {dbg.exitDecision(38);}

                        switch (alt38) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:392:19: COMMA ( ws )? mediaQuery
                    	    {
                    	    dbg.location(392,19);
                    	    match(input,COMMA,FOLLOW_COMMA_in_mediaQueryList927); if (state.failed) return ;
                    	    dbg.location(392,25);
                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:392:25: ( ws )?
                    	    int alt37=2;
                    	    try { dbg.enterSubRule(37);
                    	    try { dbg.enterDecision(37, decisionCanBacktrack[37]);

                    	    int LA37_0 = input.LA(1);

                    	    if ( (LA37_0==WS||(LA37_0>=NL && LA37_0<=COMMENT)) ) {
                    	        alt37=1;
                    	    }
                    	    } finally {dbg.exitDecision(37);}

                    	    switch (alt37) {
                    	        case 1 :
                    	            dbg.enterAlt(1);

                    	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:392:25: ws
                    	            {
                    	            dbg.location(392,25);
                    	            pushFollow(FOLLOW_ws_in_mediaQueryList929);
                    	            ws();

                    	            state._fsp--;
                    	            if (state.failed) return ;

                    	            }
                    	            break;

                    	    }
                    	    } finally {dbg.exitSubRule(37);}

                    	    dbg.location(392,29);
                    	    pushFollow(FOLLOW_mediaQuery_in_mediaQueryList932);
                    	    mediaQuery();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop38;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(38);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(39);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(393, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "mediaQueryList");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "mediaQueryList"


    // $ANTLR start "mediaQuery"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:395:1: mediaQuery : ( ( mediaQueryOperator ( ws )? )? mediaType ( ws )? ( AND ( ws )? mediaExpression )* | mediaExpression ( AND ( ws )? mediaExpression )* );
    public final void mediaQuery() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "mediaQuery");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(395, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:396:2: ( ( mediaQueryOperator ( ws )? )? mediaType ( ws )? ( AND ( ws )? mediaExpression )* | mediaExpression ( AND ( ws )? mediaExpression )* )
            int alt47=2;
            try { dbg.enterDecision(47, decisionCanBacktrack[47]);

            int LA47_0 = input.LA(1);

            if ( (LA47_0==IDENT||(LA47_0>=ONLY && LA47_0<=GEN)) ) {
                alt47=1;
            }
            else if ( (LA47_0==LPAREN) ) {
                alt47=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(47);}

            switch (alt47) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:396:4: ( mediaQueryOperator ( ws )? )? mediaType ( ws )? ( AND ( ws )? mediaExpression )*
                    {
                    dbg.location(396,4);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:396:4: ( mediaQueryOperator ( ws )? )?
                    int alt41=2;
                    try { dbg.enterSubRule(41);
                    try { dbg.enterDecision(41, decisionCanBacktrack[41]);

                    int LA41_0 = input.LA(1);

                    if ( ((LA41_0>=ONLY && LA41_0<=NOT)) ) {
                        alt41=1;
                    }
                    } finally {dbg.exitDecision(41);}

                    switch (alt41) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:396:5: mediaQueryOperator ( ws )?
                            {
                            dbg.location(396,5);
                            pushFollow(FOLLOW_mediaQueryOperator_in_mediaQuery951);
                            mediaQueryOperator();

                            state._fsp--;
                            if (state.failed) return ;
                            dbg.location(396,24);
                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:396:24: ( ws )?
                            int alt40=2;
                            try { dbg.enterSubRule(40);
                            try { dbg.enterDecision(40, decisionCanBacktrack[40]);

                            int LA40_0 = input.LA(1);

                            if ( (LA40_0==WS||(LA40_0>=NL && LA40_0<=COMMENT)) ) {
                                alt40=1;
                            }
                            } finally {dbg.exitDecision(40);}

                            switch (alt40) {
                                case 1 :
                                    dbg.enterAlt(1);

                                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:396:24: ws
                                    {
                                    dbg.location(396,24);
                                    pushFollow(FOLLOW_ws_in_mediaQuery953);
                                    ws();

                                    state._fsp--;
                                    if (state.failed) return ;

                                    }
                                    break;

                            }
                            } finally {dbg.exitSubRule(40);}


                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(41);}

                    dbg.location(396,32);
                    pushFollow(FOLLOW_mediaType_in_mediaQuery960);
                    mediaType();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(396,42);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:396:42: ( ws )?
                    int alt42=2;
                    try { dbg.enterSubRule(42);
                    try { dbg.enterDecision(42, decisionCanBacktrack[42]);

                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==WS||(LA42_0>=NL && LA42_0<=COMMENT)) ) {
                        alt42=1;
                    }
                    } finally {dbg.exitDecision(42);}

                    switch (alt42) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:396:42: ws
                            {
                            dbg.location(396,42);
                            pushFollow(FOLLOW_ws_in_mediaQuery962);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(42);}

                    dbg.location(396,46);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:396:46: ( AND ( ws )? mediaExpression )*
                    try { dbg.enterSubRule(44);

                    loop44:
                    do {
                        int alt44=2;
                        try { dbg.enterDecision(44, decisionCanBacktrack[44]);

                        int LA44_0 = input.LA(1);

                        if ( (LA44_0==AND) ) {
                            alt44=1;
                        }


                        } finally {dbg.exitDecision(44);}

                        switch (alt44) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:396:48: AND ( ws )? mediaExpression
                    	    {
                    	    dbg.location(396,48);
                    	    match(input,AND,FOLLOW_AND_in_mediaQuery967); if (state.failed) return ;
                    	    dbg.location(396,52);
                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:396:52: ( ws )?
                    	    int alt43=2;
                    	    try { dbg.enterSubRule(43);
                    	    try { dbg.enterDecision(43, decisionCanBacktrack[43]);

                    	    int LA43_0 = input.LA(1);

                    	    if ( (LA43_0==WS||(LA43_0>=NL && LA43_0<=COMMENT)) ) {
                    	        alt43=1;
                    	    }
                    	    } finally {dbg.exitDecision(43);}

                    	    switch (alt43) {
                    	        case 1 :
                    	            dbg.enterAlt(1);

                    	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:396:52: ws
                    	            {
                    	            dbg.location(396,52);
                    	            pushFollow(FOLLOW_ws_in_mediaQuery969);
                    	            ws();

                    	            state._fsp--;
                    	            if (state.failed) return ;

                    	            }
                    	            break;

                    	    }
                    	    } finally {dbg.exitSubRule(43);}

                    	    dbg.location(396,56);
                    	    pushFollow(FOLLOW_mediaExpression_in_mediaQuery972);
                    	    mediaExpression();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop44;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(44);}


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:397:4: mediaExpression ( AND ( ws )? mediaExpression )*
                    {
                    dbg.location(397,4);
                    pushFollow(FOLLOW_mediaExpression_in_mediaQuery980);
                    mediaExpression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(397,20);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:397:20: ( AND ( ws )? mediaExpression )*
                    try { dbg.enterSubRule(46);

                    loop46:
                    do {
                        int alt46=2;
                        try { dbg.enterDecision(46, decisionCanBacktrack[46]);

                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==AND) ) {
                            alt46=1;
                        }


                        } finally {dbg.exitDecision(46);}

                        switch (alt46) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:397:22: AND ( ws )? mediaExpression
                    	    {
                    	    dbg.location(397,22);
                    	    match(input,AND,FOLLOW_AND_in_mediaQuery984); if (state.failed) return ;
                    	    dbg.location(397,26);
                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:397:26: ( ws )?
                    	    int alt45=2;
                    	    try { dbg.enterSubRule(45);
                    	    try { dbg.enterDecision(45, decisionCanBacktrack[45]);

                    	    int LA45_0 = input.LA(1);

                    	    if ( (LA45_0==WS||(LA45_0>=NL && LA45_0<=COMMENT)) ) {
                    	        alt45=1;
                    	    }
                    	    } finally {dbg.exitDecision(45);}

                    	    switch (alt45) {
                    	        case 1 :
                    	            dbg.enterAlt(1);

                    	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:397:26: ws
                    	            {
                    	            dbg.location(397,26);
                    	            pushFollow(FOLLOW_ws_in_mediaQuery986);
                    	            ws();

                    	            state._fsp--;
                    	            if (state.failed) return ;

                    	            }
                    	            break;

                    	    }
                    	    } finally {dbg.exitSubRule(45);}

                    	    dbg.location(397,30);
                    	    pushFollow(FOLLOW_mediaExpression_in_mediaQuery989);
                    	    mediaExpression();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop46;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(46);}


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(398, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "mediaQuery");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "mediaQuery"


    // $ANTLR start "mediaQueryOperator"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:400:1: mediaQueryOperator : ( ONLY | NOT );
    public final void mediaQueryOperator() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "mediaQueryOperator");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(400, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:401:3: ( ONLY | NOT )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
            {
            dbg.location(401,3);
            if ( (input.LA(1)>=ONLY && input.LA(1)<=NOT) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(402, 3);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "mediaQueryOperator");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "mediaQueryOperator"


    // $ANTLR start "mediaType"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:404:1: mediaType : ( IDENT | GEN );
    public final void mediaType() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "mediaType");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(404, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:405:2: ( IDENT | GEN )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
            {
            dbg.location(405,2);
            if ( input.LA(1)==IDENT||input.LA(1)==GEN ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(406, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "mediaType");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "mediaType"


    // $ANTLR start "mediaExpression"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:408:1: mediaExpression : LPAREN ( ws )? mediaFeature ( ws )? ( COLON ( ws )? expression )? RPAREN ( ws )? ;
    public final void mediaExpression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "mediaExpression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(408, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:409:5: ( LPAREN ( ws )? mediaFeature ( ws )? ( COLON ( ws )? expression )? RPAREN ( ws )? )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:409:7: LPAREN ( ws )? mediaFeature ( ws )? ( COLON ( ws )? expression )? RPAREN ( ws )?
            {
            dbg.location(409,7);
            match(input,LPAREN,FOLLOW_LPAREN_in_mediaExpression1044); if (state.failed) return ;
            dbg.location(409,14);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:409:14: ( ws )?
            int alt48=2;
            try { dbg.enterSubRule(48);
            try { dbg.enterDecision(48, decisionCanBacktrack[48]);

            int LA48_0 = input.LA(1);

            if ( (LA48_0==WS||(LA48_0>=NL && LA48_0<=COMMENT)) ) {
                alt48=1;
            }
            } finally {dbg.exitDecision(48);}

            switch (alt48) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:409:14: ws
                    {
                    dbg.location(409,14);
                    pushFollow(FOLLOW_ws_in_mediaExpression1046);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(48);}

            dbg.location(409,18);
            pushFollow(FOLLOW_mediaFeature_in_mediaExpression1049);
            mediaFeature();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(409,31);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:409:31: ( ws )?
            int alt49=2;
            try { dbg.enterSubRule(49);
            try { dbg.enterDecision(49, decisionCanBacktrack[49]);

            int LA49_0 = input.LA(1);

            if ( (LA49_0==WS||(LA49_0>=NL && LA49_0<=COMMENT)) ) {
                alt49=1;
            }
            } finally {dbg.exitDecision(49);}

            switch (alt49) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:409:31: ws
                    {
                    dbg.location(409,31);
                    pushFollow(FOLLOW_ws_in_mediaExpression1051);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(49);}

            dbg.location(409,35);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:409:35: ( COLON ( ws )? expression )?
            int alt51=2;
            try { dbg.enterSubRule(51);
            try { dbg.enterDecision(51, decisionCanBacktrack[51]);

            int LA51_0 = input.LA(1);

            if ( (LA51_0==COLON) ) {
                alt51=1;
            }
            } finally {dbg.exitDecision(51);}

            switch (alt51) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:409:37: COLON ( ws )? expression
                    {
                    dbg.location(409,37);
                    match(input,COLON,FOLLOW_COLON_in_mediaExpression1056); if (state.failed) return ;
                    dbg.location(409,43);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:409:43: ( ws )?
                    int alt50=2;
                    try { dbg.enterSubRule(50);
                    try { dbg.enterDecision(50, decisionCanBacktrack[50]);

                    int LA50_0 = input.LA(1);

                    if ( (LA50_0==WS||(LA50_0>=NL && LA50_0<=COMMENT)) ) {
                        alt50=1;
                    }
                    } finally {dbg.exitDecision(50);}

                    switch (alt50) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:409:43: ws
                            {
                            dbg.location(409,43);
                            pushFollow(FOLLOW_ws_in_mediaExpression1058);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(50);}

                    dbg.location(409,47);
                    pushFollow(FOLLOW_expression_in_mediaExpression1061);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(51);}

            dbg.location(409,61);
            match(input,RPAREN,FOLLOW_RPAREN_in_mediaExpression1066); if (state.failed) return ;
            dbg.location(409,68);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:409:68: ( ws )?
            int alt52=2;
            try { dbg.enterSubRule(52);
            try { dbg.enterDecision(52, decisionCanBacktrack[52]);

            int LA52_0 = input.LA(1);

            if ( (LA52_0==WS||(LA52_0>=NL && LA52_0<=COMMENT)) ) {
                alt52=1;
            }
            } finally {dbg.exitDecision(52);}

            switch (alt52) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:409:68: ws
                    {
                    dbg.location(409,68);
                    pushFollow(FOLLOW_ws_in_mediaExpression1068);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(52);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(410, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "mediaExpression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "mediaExpression"


    // $ANTLR start "mediaFeature"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:412:1: mediaFeature : IDENT ;
    public final void mediaFeature() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "mediaFeature");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(412, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:413:2: ( IDENT )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:413:4: IDENT
            {
            dbg.location(413,4);
            match(input,IDENT,FOLLOW_IDENT_in_mediaFeature1084); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(414, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "mediaFeature");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "mediaFeature"


    // $ANTLR start "body"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:416:2: body : ( bodyItem ( ws )? )+ ;
    public final void body() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "body");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(416, 2);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:416:7: ( ( bodyItem ( ws )? )+ )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:417:2: ( bodyItem ( ws )? )+
            {
            dbg.location(417,2);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:417:2: ( bodyItem ( ws )? )+
            int cnt54=0;
            try { dbg.enterSubRule(54);

            loop54:
            do {
                int alt54=2;
                try { dbg.enterDecision(54, decisionCanBacktrack[54]);

                int LA54_0 = input.LA(1);

                if ( (LA54_0==IDENT||LA54_0==MEDIA_SYM||LA54_0==GEN||LA54_0==COLON||LA54_0==AT_IDENT||LA54_0==MOZ_DOCUMENT_SYM||LA54_0==WEBKIT_KEYFRAMES_SYM||(LA54_0>=PAGE_SYM && LA54_0<=FONT_FACE_SYM)||(LA54_0>=MINUS && LA54_0<=PIPE)||LA54_0==LESS_AND||(LA54_0>=SASS_VAR && LA54_0<=SASS_INCLUDE)||(LA54_0>=SASS_DEBUG && LA54_0<=SASS_IF)||(LA54_0>=SASS_FOR && LA54_0<=SASS_WHILE)) ) {
                    alt54=1;
                }


                } finally {dbg.exitDecision(54);}

                switch (alt54) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:417:4: bodyItem ( ws )?
            	    {
            	    dbg.location(417,4);
            	    pushFollow(FOLLOW_bodyItem_in_body1100);
            	    bodyItem();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(417,13);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:417:13: ( ws )?
            	    int alt53=2;
            	    try { dbg.enterSubRule(53);
            	    try { dbg.enterDecision(53, decisionCanBacktrack[53]);

            	    int LA53_0 = input.LA(1);

            	    if ( (LA53_0==WS||(LA53_0>=NL && LA53_0<=COMMENT)) ) {
            	        alt53=1;
            	    }
            	    } finally {dbg.exitDecision(53);}

            	    switch (alt53) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:417:13: ws
            	            {
            	            dbg.location(417,13);
            	            pushFollow(FOLLOW_ws_in_body1102);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(53);}


            	    }
            	    break;

            	default :
            	    if ( cnt54 >= 1 ) break loop54;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(54, input);
                        dbg.recognitionException(eee);

                        throw eee;
                }
                cnt54++;
            } while (true);
            } finally {dbg.exitSubRule(54);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(418, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "body");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "body"


    // $ANTLR start "bodyItem"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:420:1: bodyItem : ( rule | media | page | counterStyle | fontFace | vendorAtRule | {...}? cp_variable_declaration | {...}? cp_mixin_call | {...}? sass_debug | {...}? sass_control );
    public final void bodyItem() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "bodyItem");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(420, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:421:5: ( rule | media | page | counterStyle | fontFace | vendorAtRule | {...}? cp_variable_declaration | {...}? cp_mixin_call | {...}? sass_debug | {...}? sass_control )
            int alt55=10;
            try { dbg.enterDecision(55, decisionCanBacktrack[55]);

            try {
                isCyclicDecision = true;
                alt55 = dfa55.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(55);}

            switch (alt55) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:422:6: rule
                    {
                    dbg.location(422,6);
                    pushFollow(FOLLOW_rule_in_bodyItem1127);
                    rule();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:423:11: media
                    {
                    dbg.location(423,11);
                    pushFollow(FOLLOW_media_in_bodyItem1139);
                    media();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:424:11: page
                    {
                    dbg.location(424,11);
                    pushFollow(FOLLOW_page_in_bodyItem1151);
                    page();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:425:11: counterStyle
                    {
                    dbg.location(425,11);
                    pushFollow(FOLLOW_counterStyle_in_bodyItem1163);
                    counterStyle();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 5 :
                    dbg.enterAlt(5);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:426:11: fontFace
                    {
                    dbg.location(426,11);
                    pushFollow(FOLLOW_fontFace_in_bodyItem1175);
                    fontFace();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 6 :
                    dbg.enterAlt(6);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:427:11: vendorAtRule
                    {
                    dbg.location(427,11);
                    pushFollow(FOLLOW_vendorAtRule_in_bodyItem1187);
                    vendorAtRule();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 7 :
                    dbg.enterAlt(7);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:428:11: {...}? cp_variable_declaration
                    {
                    dbg.location(428,11);
                    if ( !(evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "bodyItem", "isCssPreprocessorSource()");
                    }
                    dbg.location(428,40);
                    pushFollow(FOLLOW_cp_variable_declaration_in_bodyItem1201);
                    cp_variable_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 8 :
                    dbg.enterAlt(8);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:429:11: {...}? cp_mixin_call
                    {
                    dbg.location(429,11);
                    if ( !(evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "bodyItem", "isCssPreprocessorSource()");
                    }
                    dbg.location(429,40);
                    pushFollow(FOLLOW_cp_mixin_call_in_bodyItem1215);
                    cp_mixin_call();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 9 :
                    dbg.enterAlt(9);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:430:11: {...}? sass_debug
                    {
                    dbg.location(430,11);
                    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "bodyItem", "isScssSource()");
                    }
                    dbg.location(430,29);
                    pushFollow(FOLLOW_sass_debug_in_bodyItem1229);
                    sass_debug();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 10 :
                    dbg.enterAlt(10);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:431:11: {...}? sass_control
                    {
                    dbg.location(431,11);
                    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "bodyItem", "isScssSource()");
                    }
                    dbg.location(431,29);
                    pushFollow(FOLLOW_sass_control_in_bodyItem1243);
                    sass_control();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(432, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "bodyItem");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "bodyItem"


    // $ANTLR start "vendorAtRule"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:440:1: vendorAtRule : ( moz_document | webkitKeyframes | generic_at_rule );
    public final void vendorAtRule() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "vendorAtRule");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(440, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:441:1: ( moz_document | webkitKeyframes | generic_at_rule )
            int alt56=3;
            try { dbg.enterDecision(56, decisionCanBacktrack[56]);

            switch ( input.LA(1) ) {
            case MOZ_DOCUMENT_SYM:
                {
                alt56=1;
                }
                break;
            case WEBKIT_KEYFRAMES_SYM:
                {
                alt56=2;
                }
                break;
            case AT_IDENT:
                {
                alt56=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(56);}

            switch (alt56) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:441:3: moz_document
                    {
                    dbg.location(441,3);
                    pushFollow(FOLLOW_moz_document_in_vendorAtRule1266);
                    moz_document();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:441:18: webkitKeyframes
                    {
                    dbg.location(441,18);
                    pushFollow(FOLLOW_webkitKeyframes_in_vendorAtRule1270);
                    webkitKeyframes();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:441:36: generic_at_rule
                    {
                    dbg.location(441,36);
                    pushFollow(FOLLOW_generic_at_rule_in_vendorAtRule1274);
                    generic_at_rule();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(441, 51);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "vendorAtRule");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "vendorAtRule"


    // $ANTLR start "atRuleId"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:443:1: atRuleId : ( IDENT | STRING );
    public final void atRuleId() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "atRuleId");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(443, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:444:2: ( IDENT | STRING )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
            {
            dbg.location(444,2);
            if ( (input.LA(1)>=IDENT && input.LA(1)<=STRING) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(446, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "atRuleId");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "atRuleId"


    // $ANTLR start "generic_at_rule"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:448:1: generic_at_rule : AT_IDENT ( WS )* ( atRuleId ( WS )* )? LBRACE syncTo_RBRACE RBRACE ;
    public final void generic_at_rule() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "generic_at_rule");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(448, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:449:5: ( AT_IDENT ( WS )* ( atRuleId ( WS )* )? LBRACE syncTo_RBRACE RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:449:7: AT_IDENT ( WS )* ( atRuleId ( WS )* )? LBRACE syncTo_RBRACE RBRACE
            {
            dbg.location(449,7);
            match(input,AT_IDENT,FOLLOW_AT_IDENT_in_generic_at_rule1310); if (state.failed) return ;
            dbg.location(449,16);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:449:16: ( WS )*
            try { dbg.enterSubRule(57);

            loop57:
            do {
                int alt57=2;
                try { dbg.enterDecision(57, decisionCanBacktrack[57]);

                int LA57_0 = input.LA(1);

                if ( (LA57_0==WS) ) {
                    alt57=1;
                }


                } finally {dbg.exitDecision(57);}

                switch (alt57) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:449:16: WS
            	    {
            	    dbg.location(449,16);
            	    match(input,WS,FOLLOW_WS_in_generic_at_rule1312); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop57;
                }
            } while (true);
            } finally {dbg.exitSubRule(57);}

            dbg.location(449,20);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:449:20: ( atRuleId ( WS )* )?
            int alt59=2;
            try { dbg.enterSubRule(59);
            try { dbg.enterDecision(59, decisionCanBacktrack[59]);

            int LA59_0 = input.LA(1);

            if ( ((LA59_0>=IDENT && LA59_0<=STRING)) ) {
                alt59=1;
            }
            } finally {dbg.exitDecision(59);}

            switch (alt59) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:449:22: atRuleId ( WS )*
                    {
                    dbg.location(449,22);
                    pushFollow(FOLLOW_atRuleId_in_generic_at_rule1317);
                    atRuleId();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(449,31);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:449:31: ( WS )*
                    try { dbg.enterSubRule(58);

                    loop58:
                    do {
                        int alt58=2;
                        try { dbg.enterDecision(58, decisionCanBacktrack[58]);

                        int LA58_0 = input.LA(1);

                        if ( (LA58_0==WS) ) {
                            alt58=1;
                        }


                        } finally {dbg.exitDecision(58);}

                        switch (alt58) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:449:31: WS
                    	    {
                    	    dbg.location(449,31);
                    	    match(input,WS,FOLLOW_WS_in_generic_at_rule1319); if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop58;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(58);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(59);}

            dbg.location(450,9);
            match(input,LBRACE,FOLLOW_LBRACE_in_generic_at_rule1334); if (state.failed) return ;
            dbg.location(451,10);
            pushFollow(FOLLOW_syncTo_RBRACE_in_generic_at_rule1346);
            syncTo_RBRACE();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(452,9);
            match(input,RBRACE,FOLLOW_RBRACE_in_generic_at_rule1356); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(453, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "generic_at_rule");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "generic_at_rule"


    // $ANTLR start "moz_document"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:454:1: moz_document : MOZ_DOCUMENT_SYM ( ws )? ( moz_document_function ( ws )? ) ( COMMA ( ws )? moz_document_function ( ws )? )* LBRACE ( ws )? ( body )? RBRACE ;
    public final void moz_document() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "moz_document");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(454, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:455:2: ( MOZ_DOCUMENT_SYM ( ws )? ( moz_document_function ( ws )? ) ( COMMA ( ws )? moz_document_function ( ws )? )* LBRACE ( ws )? ( body )? RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:2: MOZ_DOCUMENT_SYM ( ws )? ( moz_document_function ( ws )? ) ( COMMA ( ws )? moz_document_function ( ws )? )* LBRACE ( ws )? ( body )? RBRACE
            {
            dbg.location(456,2);
            match(input,MOZ_DOCUMENT_SYM,FOLLOW_MOZ_DOCUMENT_SYM_in_moz_document1372); if (state.failed) return ;
            dbg.location(456,19);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:19: ( ws )?
            int alt60=2;
            try { dbg.enterSubRule(60);
            try { dbg.enterDecision(60, decisionCanBacktrack[60]);

            int LA60_0 = input.LA(1);

            if ( (LA60_0==WS||(LA60_0>=NL && LA60_0<=COMMENT)) ) {
                alt60=1;
            }
            } finally {dbg.exitDecision(60);}

            switch (alt60) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:19: ws
                    {
                    dbg.location(456,19);
                    pushFollow(FOLLOW_ws_in_moz_document1374);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(60);}

            dbg.location(456,23);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:23: ( moz_document_function ( ws )? )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:25: moz_document_function ( ws )?
            {
            dbg.location(456,25);
            pushFollow(FOLLOW_moz_document_function_in_moz_document1379);
            moz_document_function();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(456,47);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:47: ( ws )?
            int alt61=2;
            try { dbg.enterSubRule(61);
            try { dbg.enterDecision(61, decisionCanBacktrack[61]);

            int LA61_0 = input.LA(1);

            if ( (LA61_0==WS||(LA61_0>=NL && LA61_0<=COMMENT)) ) {
                alt61=1;
            }
            } finally {dbg.exitDecision(61);}

            switch (alt61) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:47: ws
                    {
                    dbg.location(456,47);
                    pushFollow(FOLLOW_ws_in_moz_document1381);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(61);}


            }

            dbg.location(456,52);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:52: ( COMMA ( ws )? moz_document_function ( ws )? )*
            try { dbg.enterSubRule(64);

            loop64:
            do {
                int alt64=2;
                try { dbg.enterDecision(64, decisionCanBacktrack[64]);

                int LA64_0 = input.LA(1);

                if ( (LA64_0==COMMA) ) {
                    alt64=1;
                }


                } finally {dbg.exitDecision(64);}

                switch (alt64) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:54: COMMA ( ws )? moz_document_function ( ws )?
            	    {
            	    dbg.location(456,54);
            	    match(input,COMMA,FOLLOW_COMMA_in_moz_document1387); if (state.failed) return ;
            	    dbg.location(456,60);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:60: ( ws )?
            	    int alt62=2;
            	    try { dbg.enterSubRule(62);
            	    try { dbg.enterDecision(62, decisionCanBacktrack[62]);

            	    int LA62_0 = input.LA(1);

            	    if ( (LA62_0==WS||(LA62_0>=NL && LA62_0<=COMMENT)) ) {
            	        alt62=1;
            	    }
            	    } finally {dbg.exitDecision(62);}

            	    switch (alt62) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:60: ws
            	            {
            	            dbg.location(456,60);
            	            pushFollow(FOLLOW_ws_in_moz_document1389);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(62);}

            	    dbg.location(456,64);
            	    pushFollow(FOLLOW_moz_document_function_in_moz_document1392);
            	    moz_document_function();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(456,86);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:86: ( ws )?
            	    int alt63=2;
            	    try { dbg.enterSubRule(63);
            	    try { dbg.enterDecision(63, decisionCanBacktrack[63]);

            	    int LA63_0 = input.LA(1);

            	    if ( (LA63_0==WS||(LA63_0>=NL && LA63_0<=COMMENT)) ) {
            	        alt63=1;
            	    }
            	    } finally {dbg.exitDecision(63);}

            	    switch (alt63) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:456:86: ws
            	            {
            	            dbg.location(456,86);
            	            pushFollow(FOLLOW_ws_in_moz_document1394);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(63);}


            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);
            } finally {dbg.exitSubRule(64);}

            dbg.location(457,2);
            match(input,LBRACE,FOLLOW_LBRACE_in_moz_document1401); if (state.failed) return ;
            dbg.location(457,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:457:9: ( ws )?
            int alt65=2;
            try { dbg.enterSubRule(65);
            try { dbg.enterDecision(65, decisionCanBacktrack[65]);

            int LA65_0 = input.LA(1);

            if ( (LA65_0==WS||(LA65_0>=NL && LA65_0<=COMMENT)) ) {
                alt65=1;
            }
            } finally {dbg.exitDecision(65);}

            switch (alt65) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:457:9: ws
                    {
                    dbg.location(457,9);
                    pushFollow(FOLLOW_ws_in_moz_document1403);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(65);}

            dbg.location(458,3);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:458:3: ( body )?
            int alt66=2;
            try { dbg.enterSubRule(66);
            try { dbg.enterDecision(66, decisionCanBacktrack[66]);

            int LA66_0 = input.LA(1);

            if ( (LA66_0==IDENT||LA66_0==MEDIA_SYM||LA66_0==GEN||LA66_0==COLON||LA66_0==AT_IDENT||LA66_0==MOZ_DOCUMENT_SYM||LA66_0==WEBKIT_KEYFRAMES_SYM||(LA66_0>=PAGE_SYM && LA66_0<=FONT_FACE_SYM)||(LA66_0>=MINUS && LA66_0<=PIPE)||LA66_0==LESS_AND||(LA66_0>=SASS_VAR && LA66_0<=SASS_INCLUDE)||(LA66_0>=SASS_DEBUG && LA66_0<=SASS_IF)||(LA66_0>=SASS_FOR && LA66_0<=SASS_WHILE)) ) {
                alt66=1;
            }
            } finally {dbg.exitDecision(66);}

            switch (alt66) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:458:3: body
                    {
                    dbg.location(458,3);
                    pushFollow(FOLLOW_body_in_moz_document1408);
                    body();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(66);}

            dbg.location(459,2);
            match(input,RBRACE,FOLLOW_RBRACE_in_moz_document1413); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(460, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "moz_document");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "moz_document"


    // $ANTLR start "moz_document_function"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:462:1: moz_document_function : ( URI | MOZ_URL_PREFIX | MOZ_DOMAIN | MOZ_REGEXP );
    public final void moz_document_function() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "moz_document_function");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(462, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:463:2: ( URI | MOZ_URL_PREFIX | MOZ_DOMAIN | MOZ_REGEXP )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
            {
            dbg.location(463,2);
            if ( input.LA(1)==URI||(input.LA(1)>=MOZ_URL_PREFIX && input.LA(1)<=MOZ_REGEXP) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(465, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "moz_document_function");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "moz_document_function"


    // $ANTLR start "webkitKeyframes"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:468:1: webkitKeyframes : WEBKIT_KEYFRAMES_SYM ( ws )? atRuleId ( ws )? LBRACE ( ws )? ( webkitKeyframesBlock ( ws )? )* RBRACE ;
    public final void webkitKeyframes() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "webkitKeyframes");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(468, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:469:2: ( WEBKIT_KEYFRAMES_SYM ( ws )? atRuleId ( ws )? LBRACE ( ws )? ( webkitKeyframesBlock ( ws )? )* RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:470:2: WEBKIT_KEYFRAMES_SYM ( ws )? atRuleId ( ws )? LBRACE ( ws )? ( webkitKeyframesBlock ( ws )? )* RBRACE
            {
            dbg.location(470,2);
            match(input,WEBKIT_KEYFRAMES_SYM,FOLLOW_WEBKIT_KEYFRAMES_SYM_in_webkitKeyframes1454); if (state.failed) return ;
            dbg.location(470,23);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:470:23: ( ws )?
            int alt67=2;
            try { dbg.enterSubRule(67);
            try { dbg.enterDecision(67, decisionCanBacktrack[67]);

            int LA67_0 = input.LA(1);

            if ( (LA67_0==WS||(LA67_0>=NL && LA67_0<=COMMENT)) ) {
                alt67=1;
            }
            } finally {dbg.exitDecision(67);}

            switch (alt67) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:470:23: ws
                    {
                    dbg.location(470,23);
                    pushFollow(FOLLOW_ws_in_webkitKeyframes1456);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(67);}

            dbg.location(470,27);
            pushFollow(FOLLOW_atRuleId_in_webkitKeyframes1459);
            atRuleId();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(470,36);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:470:36: ( ws )?
            int alt68=2;
            try { dbg.enterSubRule(68);
            try { dbg.enterDecision(68, decisionCanBacktrack[68]);

            int LA68_0 = input.LA(1);

            if ( (LA68_0==WS||(LA68_0>=NL && LA68_0<=COMMENT)) ) {
                alt68=1;
            }
            } finally {dbg.exitDecision(68);}

            switch (alt68) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:470:36: ws
                    {
                    dbg.location(470,36);
                    pushFollow(FOLLOW_ws_in_webkitKeyframes1461);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(68);}

            dbg.location(471,2);
            match(input,LBRACE,FOLLOW_LBRACE_in_webkitKeyframes1466); if (state.failed) return ;
            dbg.location(471,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:471:9: ( ws )?
            int alt69=2;
            try { dbg.enterSubRule(69);
            try { dbg.enterDecision(69, decisionCanBacktrack[69]);

            int LA69_0 = input.LA(1);

            if ( (LA69_0==WS||(LA69_0>=NL && LA69_0<=COMMENT)) ) {
                alt69=1;
            }
            } finally {dbg.exitDecision(69);}

            switch (alt69) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:471:9: ws
                    {
                    dbg.location(471,9);
                    pushFollow(FOLLOW_ws_in_webkitKeyframes1468);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(69);}

            dbg.location(472,3);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:472:3: ( webkitKeyframesBlock ( ws )? )*
            try { dbg.enterSubRule(71);

            loop71:
            do {
                int alt71=2;
                try { dbg.enterDecision(71, decisionCanBacktrack[71]);

                int LA71_0 = input.LA(1);

                if ( (LA71_0==IDENT||LA71_0==PERCENTAGE) ) {
                    alt71=1;
                }


                } finally {dbg.exitDecision(71);}

                switch (alt71) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:472:5: webkitKeyframesBlock ( ws )?
            	    {
            	    dbg.location(472,5);
            	    pushFollow(FOLLOW_webkitKeyframesBlock_in_webkitKeyframes1475);
            	    webkitKeyframesBlock();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(472,26);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:472:26: ( ws )?
            	    int alt70=2;
            	    try { dbg.enterSubRule(70);
            	    try { dbg.enterDecision(70, decisionCanBacktrack[70]);

            	    int LA70_0 = input.LA(1);

            	    if ( (LA70_0==WS||(LA70_0>=NL && LA70_0<=COMMENT)) ) {
            	        alt70=1;
            	    }
            	    } finally {dbg.exitDecision(70);}

            	    switch (alt70) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:472:26: ws
            	            {
            	            dbg.location(472,26);
            	            pushFollow(FOLLOW_ws_in_webkitKeyframes1477);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(70);}


            	    }
            	    break;

            	default :
            	    break loop71;
                }
            } while (true);
            } finally {dbg.exitSubRule(71);}

            dbg.location(473,2);
            match(input,RBRACE,FOLLOW_RBRACE_in_webkitKeyframes1484); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(474, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "webkitKeyframes");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "webkitKeyframes"


    // $ANTLR start "webkitKeyframesBlock"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:476:1: webkitKeyframesBlock : webkitKeyframeSelectors ( ws )? LBRACE ( ws )? syncToFollow declarations RBRACE ;
    public final void webkitKeyframesBlock() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "webkitKeyframesBlock");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(476, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:477:2: ( webkitKeyframeSelectors ( ws )? LBRACE ( ws )? syncToFollow declarations RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:478:2: webkitKeyframeSelectors ( ws )? LBRACE ( ws )? syncToFollow declarations RBRACE
            {
            dbg.location(478,2);
            pushFollow(FOLLOW_webkitKeyframeSelectors_in_webkitKeyframesBlock1497);
            webkitKeyframeSelectors();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(478,26);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:478:26: ( ws )?
            int alt72=2;
            try { dbg.enterSubRule(72);
            try { dbg.enterDecision(72, decisionCanBacktrack[72]);

            int LA72_0 = input.LA(1);

            if ( (LA72_0==WS||(LA72_0>=NL && LA72_0<=COMMENT)) ) {
                alt72=1;
            }
            } finally {dbg.exitDecision(72);}

            switch (alt72) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:478:26: ws
                    {
                    dbg.location(478,26);
                    pushFollow(FOLLOW_ws_in_webkitKeyframesBlock1499);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(72);}

            dbg.location(480,2);
            match(input,LBRACE,FOLLOW_LBRACE_in_webkitKeyframesBlock1504); if (state.failed) return ;
            dbg.location(480,10);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:480:10: ( ws )?
            int alt73=2;
            try { dbg.enterSubRule(73);
            try { dbg.enterDecision(73, decisionCanBacktrack[73]);

            int LA73_0 = input.LA(1);

            if ( (LA73_0==WS||(LA73_0>=NL && LA73_0<=COMMENT)) ) {
                alt73=1;
            }
            } finally {dbg.exitDecision(73);}

            switch (alt73) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:480:10: ws
                    {
                    dbg.location(480,10);
                    pushFollow(FOLLOW_ws_in_webkitKeyframesBlock1507);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(73);}

            dbg.location(480,14);
            pushFollow(FOLLOW_syncToFollow_in_webkitKeyframesBlock1510);
            syncToFollow();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(481,3);
            pushFollow(FOLLOW_declarations_in_webkitKeyframesBlock1514);
            declarations();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(482,2);
            match(input,RBRACE,FOLLOW_RBRACE_in_webkitKeyframesBlock1517); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(483, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "webkitKeyframesBlock");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "webkitKeyframesBlock"


    // $ANTLR start "webkitKeyframeSelectors"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:485:1: webkitKeyframeSelectors : ( IDENT | PERCENTAGE ) ( ( ws )? COMMA ( ws )? ( IDENT | PERCENTAGE ) )* ;
    public final void webkitKeyframeSelectors() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "webkitKeyframeSelectors");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(485, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:486:2: ( ( IDENT | PERCENTAGE ) ( ( ws )? COMMA ( ws )? ( IDENT | PERCENTAGE ) )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:487:2: ( IDENT | PERCENTAGE ) ( ( ws )? COMMA ( ws )? ( IDENT | PERCENTAGE ) )*
            {
            dbg.location(487,2);
            if ( input.LA(1)==IDENT||input.LA(1)==PERCENTAGE ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }

            dbg.location(487,25);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:487:25: ( ( ws )? COMMA ( ws )? ( IDENT | PERCENTAGE ) )*
            try { dbg.enterSubRule(76);

            loop76:
            do {
                int alt76=2;
                try { dbg.enterDecision(76, decisionCanBacktrack[76]);

                try {
                    isCyclicDecision = true;
                    alt76 = dfa76.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(76);}

                switch (alt76) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:487:27: ( ws )? COMMA ( ws )? ( IDENT | PERCENTAGE )
            	    {
            	    dbg.location(487,27);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:487:27: ( ws )?
            	    int alt74=2;
            	    try { dbg.enterSubRule(74);
            	    try { dbg.enterDecision(74, decisionCanBacktrack[74]);

            	    int LA74_0 = input.LA(1);

            	    if ( (LA74_0==WS||(LA74_0>=NL && LA74_0<=COMMENT)) ) {
            	        alt74=1;
            	    }
            	    } finally {dbg.exitDecision(74);}

            	    switch (alt74) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:487:27: ws
            	            {
            	            dbg.location(487,27);
            	            pushFollow(FOLLOW_ws_in_webkitKeyframeSelectors1544);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(74);}

            	    dbg.location(487,31);
            	    match(input,COMMA,FOLLOW_COMMA_in_webkitKeyframeSelectors1547); if (state.failed) return ;
            	    dbg.location(487,37);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:487:37: ( ws )?
            	    int alt75=2;
            	    try { dbg.enterSubRule(75);
            	    try { dbg.enterDecision(75, decisionCanBacktrack[75]);

            	    int LA75_0 = input.LA(1);

            	    if ( (LA75_0==WS||(LA75_0>=NL && LA75_0<=COMMENT)) ) {
            	        alt75=1;
            	    }
            	    } finally {dbg.exitDecision(75);}

            	    switch (alt75) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:487:37: ws
            	            {
            	            dbg.location(487,37);
            	            pushFollow(FOLLOW_ws_in_webkitKeyframeSelectors1549);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(75);}

            	    dbg.location(487,41);
            	    if ( input.LA(1)==IDENT||input.LA(1)==PERCENTAGE ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        dbg.recognitionException(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop76;
                }
            } while (true);
            } finally {dbg.exitSubRule(76);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(488, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "webkitKeyframeSelectors");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "webkitKeyframeSelectors"


    // $ANTLR start "page"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:490:1: page : PAGE_SYM ( ws )? ( IDENT ( ws )? )? ( pseudoPage ( ws )? )? LBRACE ( ws )? ( declaration | margin ( ws )? )? ( SEMI ( ws )? ( declaration | margin ( ws )? )? )* RBRACE ;
    public final void page() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "page");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(490, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:491:5: ( PAGE_SYM ( ws )? ( IDENT ( ws )? )? ( pseudoPage ( ws )? )? LBRACE ( ws )? ( declaration | margin ( ws )? )? ( SEMI ( ws )? ( declaration | margin ( ws )? )? )* RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:491:7: PAGE_SYM ( ws )? ( IDENT ( ws )? )? ( pseudoPage ( ws )? )? LBRACE ( ws )? ( declaration | margin ( ws )? )? ( SEMI ( ws )? ( declaration | margin ( ws )? )? )* RBRACE
            {
            dbg.location(491,7);
            match(input,PAGE_SYM,FOLLOW_PAGE_SYM_in_page1581); if (state.failed) return ;
            dbg.location(491,16);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:491:16: ( ws )?
            int alt77=2;
            try { dbg.enterSubRule(77);
            try { dbg.enterDecision(77, decisionCanBacktrack[77]);

            int LA77_0 = input.LA(1);

            if ( (LA77_0==WS||(LA77_0>=NL && LA77_0<=COMMENT)) ) {
                alt77=1;
            }
            } finally {dbg.exitDecision(77);}

            switch (alt77) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:491:16: ws
                    {
                    dbg.location(491,16);
                    pushFollow(FOLLOW_ws_in_page1583);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(77);}

            dbg.location(491,20);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:491:20: ( IDENT ( ws )? )?
            int alt79=2;
            try { dbg.enterSubRule(79);
            try { dbg.enterDecision(79, decisionCanBacktrack[79]);

            int LA79_0 = input.LA(1);

            if ( (LA79_0==IDENT) ) {
                alt79=1;
            }
            } finally {dbg.exitDecision(79);}

            switch (alt79) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:491:22: IDENT ( ws )?
                    {
                    dbg.location(491,22);
                    match(input,IDENT,FOLLOW_IDENT_in_page1588); if (state.failed) return ;
                    dbg.location(491,28);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:491:28: ( ws )?
                    int alt78=2;
                    try { dbg.enterSubRule(78);
                    try { dbg.enterDecision(78, decisionCanBacktrack[78]);

                    int LA78_0 = input.LA(1);

                    if ( (LA78_0==WS||(LA78_0>=NL && LA78_0<=COMMENT)) ) {
                        alt78=1;
                    }
                    } finally {dbg.exitDecision(78);}

                    switch (alt78) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:491:28: ws
                            {
                            dbg.location(491,28);
                            pushFollow(FOLLOW_ws_in_page1590);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(78);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(79);}

            dbg.location(491,35);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:491:35: ( pseudoPage ( ws )? )?
            int alt81=2;
            try { dbg.enterSubRule(81);
            try { dbg.enterDecision(81, decisionCanBacktrack[81]);

            int LA81_0 = input.LA(1);

            if ( (LA81_0==COLON) ) {
                alt81=1;
            }
            } finally {dbg.exitDecision(81);}

            switch (alt81) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:491:36: pseudoPage ( ws )?
                    {
                    dbg.location(491,36);
                    pushFollow(FOLLOW_pseudoPage_in_page1597);
                    pseudoPage();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(491,47);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:491:47: ( ws )?
                    int alt80=2;
                    try { dbg.enterSubRule(80);
                    try { dbg.enterDecision(80, decisionCanBacktrack[80]);

                    int LA80_0 = input.LA(1);

                    if ( (LA80_0==WS||(LA80_0>=NL && LA80_0<=COMMENT)) ) {
                        alt80=1;
                    }
                    } finally {dbg.exitDecision(80);}

                    switch (alt80) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:491:47: ws
                            {
                            dbg.location(491,47);
                            pushFollow(FOLLOW_ws_in_page1599);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(80);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(81);}

            dbg.location(492,9);
            match(input,LBRACE,FOLLOW_LBRACE_in_page1612); if (state.failed) return ;
            dbg.location(492,16);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:492:16: ( ws )?
            int alt82=2;
            try { dbg.enterSubRule(82);
            try { dbg.enterDecision(82, decisionCanBacktrack[82]);

            int LA82_0 = input.LA(1);

            if ( (LA82_0==WS||(LA82_0>=NL && LA82_0<=COMMENT)) ) {
                alt82=1;
            }
            } finally {dbg.exitDecision(82);}

            switch (alt82) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:492:16: ws
                    {
                    dbg.location(492,16);
                    pushFollow(FOLLOW_ws_in_page1614);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(82);}

            dbg.location(496,13);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:13: ( declaration | margin ( ws )? )?
            int alt84=3;
            try { dbg.enterSubRule(84);
            try { dbg.enterDecision(84, decisionCanBacktrack[84]);

            int LA84_0 = input.LA(1);

            if ( (LA84_0==IDENT||LA84_0==MEDIA_SYM||LA84_0==GEN||LA84_0==AT_IDENT||(LA84_0>=MINUS && LA84_0<=DOT)||LA84_0==STAR||LA84_0==SASS_VAR) ) {
                alt84=1;
            }
            else if ( ((LA84_0>=TOPLEFTCORNER_SYM && LA84_0<=RIGHTBOTTOM_SYM)) ) {
                alt84=2;
            }
            } finally {dbg.exitDecision(84);}

            switch (alt84) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:14: declaration
                    {
                    dbg.location(496,14);
                    pushFollow(FOLLOW_declaration_in_page1669);
                    declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:26: margin ( ws )?
                    {
                    dbg.location(496,26);
                    pushFollow(FOLLOW_margin_in_page1671);
                    margin();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(496,33);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:33: ( ws )?
                    int alt83=2;
                    try { dbg.enterSubRule(83);
                    try { dbg.enterDecision(83, decisionCanBacktrack[83]);

                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==WS||(LA83_0>=NL && LA83_0<=COMMENT)) ) {
                        alt83=1;
                    }
                    } finally {dbg.exitDecision(83);}

                    switch (alt83) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:33: ws
                            {
                            dbg.location(496,33);
                            pushFollow(FOLLOW_ws_in_page1673);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(83);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(84);}

            dbg.location(496,39);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:39: ( SEMI ( ws )? ( declaration | margin ( ws )? )? )*
            try { dbg.enterSubRule(88);

            loop88:
            do {
                int alt88=2;
                try { dbg.enterDecision(88, decisionCanBacktrack[88]);

                int LA88_0 = input.LA(1);

                if ( (LA88_0==SEMI) ) {
                    alt88=1;
                }


                } finally {dbg.exitDecision(88);}

                switch (alt88) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:40: SEMI ( ws )? ( declaration | margin ( ws )? )?
            	    {
            	    dbg.location(496,40);
            	    match(input,SEMI,FOLLOW_SEMI_in_page1679); if (state.failed) return ;
            	    dbg.location(496,45);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:45: ( ws )?
            	    int alt85=2;
            	    try { dbg.enterSubRule(85);
            	    try { dbg.enterDecision(85, decisionCanBacktrack[85]);

            	    int LA85_0 = input.LA(1);

            	    if ( (LA85_0==WS||(LA85_0>=NL && LA85_0<=COMMENT)) ) {
            	        alt85=1;
            	    }
            	    } finally {dbg.exitDecision(85);}

            	    switch (alt85) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:45: ws
            	            {
            	            dbg.location(496,45);
            	            pushFollow(FOLLOW_ws_in_page1681);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(85);}

            	    dbg.location(496,49);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:49: ( declaration | margin ( ws )? )?
            	    int alt87=3;
            	    try { dbg.enterSubRule(87);
            	    try { dbg.enterDecision(87, decisionCanBacktrack[87]);

            	    int LA87_0 = input.LA(1);

            	    if ( (LA87_0==IDENT||LA87_0==MEDIA_SYM||LA87_0==GEN||LA87_0==AT_IDENT||(LA87_0>=MINUS && LA87_0<=DOT)||LA87_0==STAR||LA87_0==SASS_VAR) ) {
            	        alt87=1;
            	    }
            	    else if ( ((LA87_0>=TOPLEFTCORNER_SYM && LA87_0<=RIGHTBOTTOM_SYM)) ) {
            	        alt87=2;
            	    }
            	    } finally {dbg.exitDecision(87);}

            	    switch (alt87) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:50: declaration
            	            {
            	            dbg.location(496,50);
            	            pushFollow(FOLLOW_declaration_in_page1685);
            	            declaration();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;
            	        case 2 :
            	            dbg.enterAlt(2);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:62: margin ( ws )?
            	            {
            	            dbg.location(496,62);
            	            pushFollow(FOLLOW_margin_in_page1687);
            	            margin();

            	            state._fsp--;
            	            if (state.failed) return ;
            	            dbg.location(496,69);
            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:69: ( ws )?
            	            int alt86=2;
            	            try { dbg.enterSubRule(86);
            	            try { dbg.enterDecision(86, decisionCanBacktrack[86]);

            	            int LA86_0 = input.LA(1);

            	            if ( (LA86_0==WS||(LA86_0>=NL && LA86_0<=COMMENT)) ) {
            	                alt86=1;
            	            }
            	            } finally {dbg.exitDecision(86);}

            	            switch (alt86) {
            	                case 1 :
            	                    dbg.enterAlt(1);

            	                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:496:69: ws
            	                    {
            	                    dbg.location(496,69);
            	                    pushFollow(FOLLOW_ws_in_page1689);
            	                    ws();

            	                    state._fsp--;
            	                    if (state.failed) return ;

            	                    }
            	                    break;

            	            }
            	            } finally {dbg.exitSubRule(86);}


            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(87);}


            	    }
            	    break;

            	default :
            	    break loop88;
                }
            } while (true);
            } finally {dbg.exitSubRule(88);}

            dbg.location(497,9);
            match(input,RBRACE,FOLLOW_RBRACE_in_page1704); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(498, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "page");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "page"


    // $ANTLR start "counterStyle"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:500:1: counterStyle : COUNTER_STYLE_SYM ( ws )? IDENT ( ws )? LBRACE ( ws )? syncToDeclarationsRule declarations RBRACE ;
    public final void counterStyle() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "counterStyle");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(500, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:501:5: ( COUNTER_STYLE_SYM ( ws )? IDENT ( ws )? LBRACE ( ws )? syncToDeclarationsRule declarations RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:501:7: COUNTER_STYLE_SYM ( ws )? IDENT ( ws )? LBRACE ( ws )? syncToDeclarationsRule declarations RBRACE
            {
            dbg.location(501,7);
            match(input,COUNTER_STYLE_SYM,FOLLOW_COUNTER_STYLE_SYM_in_counterStyle1725); if (state.failed) return ;
            dbg.location(501,25);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:501:25: ( ws )?
            int alt89=2;
            try { dbg.enterSubRule(89);
            try { dbg.enterDecision(89, decisionCanBacktrack[89]);

            int LA89_0 = input.LA(1);

            if ( (LA89_0==WS||(LA89_0>=NL && LA89_0<=COMMENT)) ) {
                alt89=1;
            }
            } finally {dbg.exitDecision(89);}

            switch (alt89) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:501:25: ws
                    {
                    dbg.location(501,25);
                    pushFollow(FOLLOW_ws_in_counterStyle1727);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(89);}

            dbg.location(501,29);
            match(input,IDENT,FOLLOW_IDENT_in_counterStyle1730); if (state.failed) return ;
            dbg.location(501,35);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:501:35: ( ws )?
            int alt90=2;
            try { dbg.enterSubRule(90);
            try { dbg.enterDecision(90, decisionCanBacktrack[90]);

            int LA90_0 = input.LA(1);

            if ( (LA90_0==WS||(LA90_0>=NL && LA90_0<=COMMENT)) ) {
                alt90=1;
            }
            } finally {dbg.exitDecision(90);}

            switch (alt90) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:501:35: ws
                    {
                    dbg.location(501,35);
                    pushFollow(FOLLOW_ws_in_counterStyle1732);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(90);}

            dbg.location(502,9);
            match(input,LBRACE,FOLLOW_LBRACE_in_counterStyle1743); if (state.failed) return ;
            dbg.location(502,16);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:502:16: ( ws )?
            int alt91=2;
            try { dbg.enterSubRule(91);
            try { dbg.enterDecision(91, decisionCanBacktrack[91]);

            int LA91_0 = input.LA(1);

            if ( (LA91_0==WS||(LA91_0>=NL && LA91_0<=COMMENT)) ) {
                alt91=1;
            }
            } finally {dbg.exitDecision(91);}

            switch (alt91) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:502:16: ws
                    {
                    dbg.location(502,16);
                    pushFollow(FOLLOW_ws_in_counterStyle1745);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(91);}

            dbg.location(502,20);
            pushFollow(FOLLOW_syncToDeclarationsRule_in_counterStyle1748);
            syncToDeclarationsRule();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(503,3);
            pushFollow(FOLLOW_declarations_in_counterStyle1752);
            declarations();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(504,9);
            match(input,RBRACE,FOLLOW_RBRACE_in_counterStyle1762); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(505, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "counterStyle");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "counterStyle"


    // $ANTLR start "fontFace"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:507:1: fontFace : FONT_FACE_SYM ( ws )? LBRACE ( ws )? syncToDeclarationsRule declarations RBRACE ;
    public final void fontFace() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "fontFace");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(507, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:508:5: ( FONT_FACE_SYM ( ws )? LBRACE ( ws )? syncToDeclarationsRule declarations RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:508:7: FONT_FACE_SYM ( ws )? LBRACE ( ws )? syncToDeclarationsRule declarations RBRACE
            {
            dbg.location(508,7);
            match(input,FONT_FACE_SYM,FOLLOW_FONT_FACE_SYM_in_fontFace1783); if (state.failed) return ;
            dbg.location(508,21);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:508:21: ( ws )?
            int alt92=2;
            try { dbg.enterSubRule(92);
            try { dbg.enterDecision(92, decisionCanBacktrack[92]);

            int LA92_0 = input.LA(1);

            if ( (LA92_0==WS||(LA92_0>=NL && LA92_0<=COMMENT)) ) {
                alt92=1;
            }
            } finally {dbg.exitDecision(92);}

            switch (alt92) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:508:21: ws
                    {
                    dbg.location(508,21);
                    pushFollow(FOLLOW_ws_in_fontFace1785);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(92);}

            dbg.location(509,9);
            match(input,LBRACE,FOLLOW_LBRACE_in_fontFace1796); if (state.failed) return ;
            dbg.location(509,16);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:509:16: ( ws )?
            int alt93=2;
            try { dbg.enterSubRule(93);
            try { dbg.enterDecision(93, decisionCanBacktrack[93]);

            int LA93_0 = input.LA(1);

            if ( (LA93_0==WS||(LA93_0>=NL && LA93_0<=COMMENT)) ) {
                alt93=1;
            }
            } finally {dbg.exitDecision(93);}

            switch (alt93) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:509:16: ws
                    {
                    dbg.location(509,16);
                    pushFollow(FOLLOW_ws_in_fontFace1798);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(93);}

            dbg.location(509,20);
            pushFollow(FOLLOW_syncToDeclarationsRule_in_fontFace1801);
            syncToDeclarationsRule();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(510,3);
            pushFollow(FOLLOW_declarations_in_fontFace1805);
            declarations();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(511,9);
            match(input,RBRACE,FOLLOW_RBRACE_in_fontFace1815); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(512, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "fontFace");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "fontFace"


    // $ANTLR start "margin"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:514:1: margin : margin_sym ( ws )? LBRACE ( ws )? syncToDeclarationsRule declarations RBRACE ;
    public final void margin() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "margin");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(514, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:515:2: ( margin_sym ( ws )? LBRACE ( ws )? syncToDeclarationsRule declarations RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:515:4: margin_sym ( ws )? LBRACE ( ws )? syncToDeclarationsRule declarations RBRACE
            {
            dbg.location(515,4);
            pushFollow(FOLLOW_margin_sym_in_margin1830);
            margin_sym();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(515,15);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:515:15: ( ws )?
            int alt94=2;
            try { dbg.enterSubRule(94);
            try { dbg.enterDecision(94, decisionCanBacktrack[94]);

            int LA94_0 = input.LA(1);

            if ( (LA94_0==WS||(LA94_0>=NL && LA94_0<=COMMENT)) ) {
                alt94=1;
            }
            } finally {dbg.exitDecision(94);}

            switch (alt94) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:515:15: ws
                    {
                    dbg.location(515,15);
                    pushFollow(FOLLOW_ws_in_margin1832);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(94);}

            dbg.location(515,19);
            match(input,LBRACE,FOLLOW_LBRACE_in_margin1835); if (state.failed) return ;
            dbg.location(515,26);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:515:26: ( ws )?
            int alt95=2;
            try { dbg.enterSubRule(95);
            try { dbg.enterDecision(95, decisionCanBacktrack[95]);

            int LA95_0 = input.LA(1);

            if ( (LA95_0==WS||(LA95_0>=NL && LA95_0<=COMMENT)) ) {
                alt95=1;
            }
            } finally {dbg.exitDecision(95);}

            switch (alt95) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:515:26: ws
                    {
                    dbg.location(515,26);
                    pushFollow(FOLLOW_ws_in_margin1837);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(95);}

            dbg.location(515,30);
            pushFollow(FOLLOW_syncToDeclarationsRule_in_margin1840);
            syncToDeclarationsRule();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(515,53);
            pushFollow(FOLLOW_declarations_in_margin1842);
            declarations();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(515,66);
            match(input,RBRACE,FOLLOW_RBRACE_in_margin1844); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(516, 8);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "margin");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "margin"


    // $ANTLR start "margin_sym"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:518:1: margin_sym : ( TOPLEFTCORNER_SYM | TOPLEFT_SYM | TOPCENTER_SYM | TOPRIGHT_SYM | TOPRIGHTCORNER_SYM | BOTTOMLEFTCORNER_SYM | BOTTOMLEFT_SYM | BOTTOMCENTER_SYM | BOTTOMRIGHT_SYM | BOTTOMRIGHTCORNER_SYM | LEFTTOP_SYM | LEFTMIDDLE_SYM | LEFTBOTTOM_SYM | RIGHTTOP_SYM | RIGHTMIDDLE_SYM | RIGHTBOTTOM_SYM );
    public final void margin_sym() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "margin_sym");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(518, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:519:2: ( TOPLEFTCORNER_SYM | TOPLEFT_SYM | TOPCENTER_SYM | TOPRIGHT_SYM | TOPRIGHTCORNER_SYM | BOTTOMLEFTCORNER_SYM | BOTTOMLEFT_SYM | BOTTOMCENTER_SYM | BOTTOMRIGHT_SYM | BOTTOMRIGHTCORNER_SYM | LEFTTOP_SYM | LEFTMIDDLE_SYM | LEFTBOTTOM_SYM | RIGHTTOP_SYM | RIGHTMIDDLE_SYM | RIGHTBOTTOM_SYM )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
            {
            dbg.location(519,2);
            if ( (input.LA(1)>=TOPLEFTCORNER_SYM && input.LA(1)<=RIGHTBOTTOM_SYM) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(536, 8);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "margin_sym");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "margin_sym"


    // $ANTLR start "pseudoPage"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:538:1: pseudoPage : COLON IDENT ;
    public final void pseudoPage() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "pseudoPage");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(538, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:539:5: ( COLON IDENT )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:539:7: COLON IDENT
            {
            dbg.location(539,7);
            match(input,COLON,FOLLOW_COLON_in_pseudoPage2073); if (state.failed) return ;
            dbg.location(539,13);
            match(input,IDENT,FOLLOW_IDENT_in_pseudoPage2075); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(540, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "pseudoPage");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "pseudoPage"


    // $ANTLR start "operator"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:542:1: operator : ( SOLIDUS | COMMA );
    public final void operator() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "operator");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(542, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:543:5: ( SOLIDUS | COMMA )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
            {
            dbg.location(543,5);
            if ( input.LA(1)==COMMA||input.LA(1)==SOLIDUS ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(545, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "operator");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "operator"


    // $ANTLR start "combinator"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:547:1: combinator : ( PLUS ( ws )? | GREATER ( ws )? | TILDE ( ws )? | );
    public final void combinator() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "combinator");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(547, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:548:5: ( PLUS ( ws )? | GREATER ( ws )? | TILDE ( ws )? | )
            int alt99=4;
            try { dbg.enterDecision(99, decisionCanBacktrack[99]);

            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt99=1;
                }
                break;
            case GREATER:
                {
                alt99=2;
                }
                break;
            case TILDE:
                {
                alt99=3;
                }
                break;
            case IDENT:
            case GEN:
            case COLON:
            case HASH_SYMBOL:
            case HASH:
            case DOT:
            case LBRACKET:
            case DCOLON:
            case SASS_EXTEND_ONLY_SELECTOR:
            case STAR:
            case PIPE:
            case LESS_AND:
                {
                alt99=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(99);}

            switch (alt99) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:548:7: PLUS ( ws )?
                    {
                    dbg.location(548,7);
                    match(input,PLUS,FOLLOW_PLUS_in_combinator2125); if (state.failed) return ;
                    dbg.location(548,12);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:548:12: ( ws )?
                    int alt96=2;
                    try { dbg.enterSubRule(96);
                    try { dbg.enterDecision(96, decisionCanBacktrack[96]);

                    int LA96_0 = input.LA(1);

                    if ( (LA96_0==WS||(LA96_0>=NL && LA96_0<=COMMENT)) ) {
                        alt96=1;
                    }
                    } finally {dbg.exitDecision(96);}

                    switch (alt96) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:548:12: ws
                            {
                            dbg.location(548,12);
                            pushFollow(FOLLOW_ws_in_combinator2127);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(96);}


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:549:7: GREATER ( ws )?
                    {
                    dbg.location(549,7);
                    match(input,GREATER,FOLLOW_GREATER_in_combinator2136); if (state.failed) return ;
                    dbg.location(549,15);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:549:15: ( ws )?
                    int alt97=2;
                    try { dbg.enterSubRule(97);
                    try { dbg.enterDecision(97, decisionCanBacktrack[97]);

                    int LA97_0 = input.LA(1);

                    if ( (LA97_0==WS||(LA97_0>=NL && LA97_0<=COMMENT)) ) {
                        alt97=1;
                    }
                    } finally {dbg.exitDecision(97);}

                    switch (alt97) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:549:15: ws
                            {
                            dbg.location(549,15);
                            pushFollow(FOLLOW_ws_in_combinator2138);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(97);}


                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:550:7: TILDE ( ws )?
                    {
                    dbg.location(550,7);
                    match(input,TILDE,FOLLOW_TILDE_in_combinator2147); if (state.failed) return ;
                    dbg.location(550,13);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:550:13: ( ws )?
                    int alt98=2;
                    try { dbg.enterSubRule(98);
                    try { dbg.enterDecision(98, decisionCanBacktrack[98]);

                    int LA98_0 = input.LA(1);

                    if ( (LA98_0==WS||(LA98_0>=NL && LA98_0<=COMMENT)) ) {
                        alt98=1;
                    }
                    } finally {dbg.exitDecision(98);}

                    switch (alt98) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:550:13: ws
                            {
                            dbg.location(550,13);
                            pushFollow(FOLLOW_ws_in_combinator2149);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(98);}


                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:552:5: 
                    {
                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(552, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "combinator");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "combinator"


    // $ANTLR start "unaryOperator"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:554:1: unaryOperator : ( MINUS | PLUS );
    public final void unaryOperator() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "unaryOperator");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(554, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:555:5: ( MINUS | PLUS )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
            {
            dbg.location(555,5);
            if ( input.LA(1)==PLUS||input.LA(1)==MINUS ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(557, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "unaryOperator");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "unaryOperator"


    // $ANTLR start "property"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:559:1: property : ( ( (~ ( HASH_SYMBOL | COLON ) )* HASH_SYMBOL LBRACE )=> scss_declaration_interpolation_expression | IDENT | GEN | {...}? cp_variable ) ( ws )? ;
    public final void property() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "property");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(559, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:560:5: ( ( ( (~ ( HASH_SYMBOL | COLON ) )* HASH_SYMBOL LBRACE )=> scss_declaration_interpolation_expression | IDENT | GEN | {...}? cp_variable ) ( ws )? )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:561:5: ( ( (~ ( HASH_SYMBOL | COLON ) )* HASH_SYMBOL LBRACE )=> scss_declaration_interpolation_expression | IDENT | GEN | {...}? cp_variable ) ( ws )?
            {
            dbg.location(561,5);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:561:5: ( ( (~ ( HASH_SYMBOL | COLON ) )* HASH_SYMBOL LBRACE )=> scss_declaration_interpolation_expression | IDENT | GEN | {...}? cp_variable )
            int alt100=4;
            try { dbg.enterSubRule(100);
            try { dbg.enterDecision(100, decisionCanBacktrack[100]);

            int LA100_0 = input.LA(1);

            if ( (LA100_0==HASH_SYMBOL) && (synpred4_Css3())) {
                alt100=1;
            }
            else if ( (LA100_0==IDENT) ) {
                int LA100_2 = input.LA(2);

                if ( (synpred4_Css3()) ) {
                    alt100=1;
                }
                else if ( (true) ) {
                    alt100=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 100, 2, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }
            }
            else if ( (LA100_0==MINUS||(LA100_0>=HASH && LA100_0<=DOT)) && (synpred4_Css3())) {
                alt100=1;
            }
            else if ( (LA100_0==GEN) ) {
                alt100=3;
            }
            else if ( (LA100_0==MEDIA_SYM||LA100_0==AT_IDENT||LA100_0==SASS_VAR) ) {
                alt100=4;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(100);}

            switch (alt100) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:564:9: ( (~ ( HASH_SYMBOL | COLON ) )* HASH_SYMBOL LBRACE )=> scss_declaration_interpolation_expression
                    {
                    dbg.location(564,53);
                    pushFollow(FOLLOW_scss_declaration_interpolation_expression_in_property2256);
                    scss_declaration_interpolation_expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:565:11: IDENT
                    {
                    dbg.location(565,11);
                    match(input,IDENT,FOLLOW_IDENT_in_property2268); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:566:11: GEN
                    {
                    dbg.location(566,11);
                    match(input,GEN,FOLLOW_GEN_in_property2281); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:567:11: {...}? cp_variable
                    {
                    dbg.location(567,11);
                    if ( !(evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "property", "isCssPreprocessorSource()");
                    }
                    dbg.location(567,40);
                    pushFollow(FOLLOW_cp_variable_in_property2296);
                    cp_variable();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(100);}

            dbg.location(568,7);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:568:7: ( ws )?
            int alt101=2;
            try { dbg.enterSubRule(101);
            try { dbg.enterDecision(101, decisionCanBacktrack[101]);

            int LA101_0 = input.LA(1);

            if ( (LA101_0==WS||(LA101_0>=NL && LA101_0<=COMMENT)) ) {
                alt101=1;
            }
            } finally {dbg.exitDecision(101);}

            switch (alt101) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:568:7: ws
                    {
                    dbg.location(568,7);
                    pushFollow(FOLLOW_ws_in_property2304);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(101);}


            }

        }
        catch ( RecognitionException rce) {

                    reportError(rce);
                    consumeUntil(input, BitSet.of(COLON)); 
                
        }
        finally {
        }
        dbg.location(569, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "property");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "property"


    // $ANTLR start "rule"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:574:1: rule : ( ({...}? cp_mixin_declaration ) | ( selectorsGroup ) ) LBRACE ( ws )? syncToFollow declarations RBRACE ;
    public final void rule() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "rule");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(574, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:575:5: ( ( ({...}? cp_mixin_declaration ) | ( selectorsGroup ) ) LBRACE ( ws )? syncToFollow declarations RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:575:9: ( ({...}? cp_mixin_declaration ) | ( selectorsGroup ) ) LBRACE ( ws )? syncToFollow declarations RBRACE
            {
            dbg.location(575,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:575:9: ( ({...}? cp_mixin_declaration ) | ( selectorsGroup ) )
            int alt102=2;
            try { dbg.enterSubRule(102);
            try { dbg.enterDecision(102, decisionCanBacktrack[102]);

            try {
                isCyclicDecision = true;
                alt102 = dfa102.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(102);}

            switch (alt102) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:576:13: ({...}? cp_mixin_declaration )
                    {
                    dbg.location(576,13);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:576:13: ({...}? cp_mixin_declaration )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:576:15: {...}? cp_mixin_declaration
                    {
                    dbg.location(576,15);
                    if ( !(evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule", "isCssPreprocessorSource()");
                    }
                    dbg.location(576,44);
                    pushFollow(FOLLOW_cp_mixin_declaration_in_rule2353);
                    cp_mixin_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:578:13: ( selectorsGroup )
                    {
                    dbg.location(578,13);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:578:13: ( selectorsGroup )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:578:15: selectorsGroup
                    {
                    dbg.location(578,15);
                    pushFollow(FOLLOW_selectorsGroup_in_rule2386);
                    selectorsGroup();

                    state._fsp--;
                    if (state.failed) return ;

                    }


                    }
                    break;

            }
            } finally {dbg.exitSubRule(102);}

            dbg.location(581,9);
            match(input,LBRACE,FOLLOW_LBRACE_in_rule2409); if (state.failed) return ;
            dbg.location(581,16);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:581:16: ( ws )?
            int alt103=2;
            try { dbg.enterSubRule(103);
            try { dbg.enterDecision(103, decisionCanBacktrack[103]);

            int LA103_0 = input.LA(1);

            if ( (LA103_0==WS||(LA103_0>=NL && LA103_0<=COMMENT)) ) {
                alt103=1;
            }
            } finally {dbg.exitDecision(103);}

            switch (alt103) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:581:16: ws
                    {
                    dbg.location(581,16);
                    pushFollow(FOLLOW_ws_in_rule2411);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(103);}

            dbg.location(581,20);
            pushFollow(FOLLOW_syncToFollow_in_rule2414);
            syncToFollow();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(582,13);
            pushFollow(FOLLOW_declarations_in_rule2428);
            declarations();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(583,9);
            match(input,RBRACE,FOLLOW_RBRACE_in_rule2438); if (state.failed) return ;

            }

        }
        catch ( RecognitionException rce) {

                    reportError(rce);
                    consumeUntil(input, BitSet.of(RBRACE));
                    input.consume(); //consume the RBRACE as well   
                    
        }
        finally {
        }
        dbg.location(584, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "rule");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "rule"


    // $ANTLR start "declarations"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:592:1: declarations : ( ( declaration SEMI )=> declaration SEMI ( ws )? | ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )* COLON (~ ( SEMI | LBRACE | RBRACE ) )* SEMI )=> declaration SEMI ( ws )? | ( scss_nested_properties )=> scss_nested_properties ( ws )? | ( rule )=> rule ( ws )? | {...}? sass_extend ( ws )? | {...}? sass_debug ( ws )? | {...}? sass_control ( ws )? | {...}? media ( ws )? | {...}? cp_mixin_call ( ws )? | ( (~ SEMI )* SEMI )=> syncTo_SEMI )* ( declaration )? ;
    public final void declarations() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "declarations");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(592, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:593:5: ( ( ( declaration SEMI )=> declaration SEMI ( ws )? | ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )* COLON (~ ( SEMI | LBRACE | RBRACE ) )* SEMI )=> declaration SEMI ( ws )? | ( scss_nested_properties )=> scss_nested_properties ( ws )? | ( rule )=> rule ( ws )? | {...}? sass_extend ( ws )? | {...}? sass_debug ( ws )? | {...}? sass_control ( ws )? | {...}? media ( ws )? | {...}? cp_mixin_call ( ws )? | ( (~ SEMI )* SEMI )=> syncTo_SEMI )* ( declaration )? )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:594:13: ( ( declaration SEMI )=> declaration SEMI ( ws )? | ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )* COLON (~ ( SEMI | LBRACE | RBRACE ) )* SEMI )=> declaration SEMI ( ws )? | ( scss_nested_properties )=> scss_nested_properties ( ws )? | ( rule )=> rule ( ws )? | {...}? sass_extend ( ws )? | {...}? sass_debug ( ws )? | {...}? sass_control ( ws )? | {...}? media ( ws )? | {...}? cp_mixin_call ( ws )? | ( (~ SEMI )* SEMI )=> syncTo_SEMI )* ( declaration )?
            {
            dbg.location(594,13);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:594:13: ( ( declaration SEMI )=> declaration SEMI ( ws )? | ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )* COLON (~ ( SEMI | LBRACE | RBRACE ) )* SEMI )=> declaration SEMI ( ws )? | ( scss_nested_properties )=> scss_nested_properties ( ws )? | ( rule )=> rule ( ws )? | {...}? sass_extend ( ws )? | {...}? sass_debug ( ws )? | {...}? sass_control ( ws )? | {...}? media ( ws )? | {...}? cp_mixin_call ( ws )? | ( (~ SEMI )* SEMI )=> syncTo_SEMI )*
            try { dbg.enterSubRule(113);

            loop113:
            do {
                int alt113=11;
                try { dbg.enterDecision(113, decisionCanBacktrack[113]);

                try {
                    isCyclicDecision = true;
                    alt113 = dfa113.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(113);}

                switch (alt113) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:599:3: ( declaration SEMI )=> declaration SEMI ( ws )?
            	    {
            	    dbg.location(599,23);
            	    pushFollow(FOLLOW_declaration_in_declarations2544);
            	    declaration();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(599,35);
            	    match(input,SEMI,FOLLOW_SEMI_in_declarations2546); if (state.failed) return ;
            	    dbg.location(599,40);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:599:40: ( ws )?
            	    int alt104=2;
            	    try { dbg.enterSubRule(104);
            	    try { dbg.enterDecision(104, decisionCanBacktrack[104]);

            	    int LA104_0 = input.LA(1);

            	    if ( (LA104_0==WS||(LA104_0>=NL && LA104_0<=COMMENT)) ) {
            	        alt104=1;
            	    }
            	    } finally {dbg.exitDecision(104);}

            	    switch (alt104) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:599:40: ws
            	            {
            	            dbg.location(599,40);
            	            pushFollow(FOLLOW_ws_in_declarations2548);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(104);}


            	    }
            	    break;
            	case 2 :
            	    dbg.enterAlt(2);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:603:3: ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )* COLON (~ ( SEMI | LBRACE | RBRACE ) )* SEMI )=> declaration SEMI ( ws )?
            	    {
            	    dbg.location(603,69);
            	    pushFollow(FOLLOW_declaration_in_declarations2633);
            	    declaration();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(603,81);
            	    match(input,SEMI,FOLLOW_SEMI_in_declarations2635); if (state.failed) return ;
            	    dbg.location(603,86);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:603:86: ( ws )?
            	    int alt105=2;
            	    try { dbg.enterSubRule(105);
            	    try { dbg.enterDecision(105, decisionCanBacktrack[105]);

            	    int LA105_0 = input.LA(1);

            	    if ( (LA105_0==WS||(LA105_0>=NL && LA105_0<=COMMENT)) ) {
            	        alt105=1;
            	    }
            	    } finally {dbg.exitDecision(105);}

            	    switch (alt105) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:603:86: ws
            	            {
            	            dbg.location(603,86);
            	            pushFollow(FOLLOW_ws_in_declarations2637);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(105);}


            	    }
            	    break;
            	case 3 :
            	    dbg.enterAlt(3);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:605:3: ( scss_nested_properties )=> scss_nested_properties ( ws )?
            	    {
            	    dbg.location(605,29);
            	    pushFollow(FOLLOW_scss_nested_properties_in_declarations2650);
            	    scss_nested_properties();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(605,52);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:605:52: ( ws )?
            	    int alt106=2;
            	    try { dbg.enterSubRule(106);
            	    try { dbg.enterDecision(106, decisionCanBacktrack[106]);

            	    int LA106_0 = input.LA(1);

            	    if ( (LA106_0==WS||(LA106_0>=NL && LA106_0<=COMMENT)) ) {
            	        alt106=1;
            	    }
            	    } finally {dbg.exitDecision(106);}

            	    switch (alt106) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:605:52: ws
            	            {
            	            dbg.location(605,52);
            	            pushFollow(FOLLOW_ws_in_declarations2652);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(106);}


            	    }
            	    break;
            	case 4 :
            	    dbg.enterAlt(4);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:607:17: ( rule )=> rule ( ws )?
            	    {
            	    dbg.location(607,25);
            	    pushFollow(FOLLOW_rule_in_declarations2679);
            	    rule();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(607,30);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:607:30: ( ws )?
            	    int alt107=2;
            	    try { dbg.enterSubRule(107);
            	    try { dbg.enterDecision(107, decisionCanBacktrack[107]);

            	    int LA107_0 = input.LA(1);

            	    if ( (LA107_0==WS||(LA107_0>=NL && LA107_0<=COMMENT)) ) {
            	        alt107=1;
            	    }
            	    } finally {dbg.exitDecision(107);}

            	    switch (alt107) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:607:30: ws
            	            {
            	            dbg.location(607,30);
            	            pushFollow(FOLLOW_ws_in_declarations2681);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(107);}


            	    }
            	    break;
            	case 5 :
            	    dbg.enterAlt(5);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:609:17: {...}? sass_extend ( ws )?
            	    {
            	    dbg.location(609,17);
            	    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        throw new FailedPredicateException(input, "declarations", "isScssSource()");
            	    }
            	    dbg.location(609,35);
            	    pushFollow(FOLLOW_sass_extend_in_declarations2720);
            	    sass_extend();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(609,47);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:609:47: ( ws )?
            	    int alt108=2;
            	    try { dbg.enterSubRule(108);
            	    try { dbg.enterDecision(108, decisionCanBacktrack[108]);

            	    int LA108_0 = input.LA(1);

            	    if ( (LA108_0==WS||(LA108_0>=NL && LA108_0<=COMMENT)) ) {
            	        alt108=1;
            	    }
            	    } finally {dbg.exitDecision(108);}

            	    switch (alt108) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:609:47: ws
            	            {
            	            dbg.location(609,47);
            	            pushFollow(FOLLOW_ws_in_declarations2722);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(108);}


            	    }
            	    break;
            	case 6 :
            	    dbg.enterAlt(6);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:611:17: {...}? sass_debug ( ws )?
            	    {
            	    dbg.location(611,17);
            	    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        throw new FailedPredicateException(input, "declarations", "isScssSource()");
            	    }
            	    dbg.location(611,35);
            	    pushFollow(FOLLOW_sass_debug_in_declarations2761);
            	    sass_debug();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(611,46);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:611:46: ( ws )?
            	    int alt109=2;
            	    try { dbg.enterSubRule(109);
            	    try { dbg.enterDecision(109, decisionCanBacktrack[109]);

            	    int LA109_0 = input.LA(1);

            	    if ( (LA109_0==WS||(LA109_0>=NL && LA109_0<=COMMENT)) ) {
            	        alt109=1;
            	    }
            	    } finally {dbg.exitDecision(109);}

            	    switch (alt109) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:611:46: ws
            	            {
            	            dbg.location(611,46);
            	            pushFollow(FOLLOW_ws_in_declarations2763);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(109);}


            	    }
            	    break;
            	case 7 :
            	    dbg.enterAlt(7);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:613:17: {...}? sass_control ( ws )?
            	    {
            	    dbg.location(613,17);
            	    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        throw new FailedPredicateException(input, "declarations", "isScssSource()");
            	    }
            	    dbg.location(613,35);
            	    pushFollow(FOLLOW_sass_control_in_declarations2802);
            	    sass_control();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(613,48);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:613:48: ( ws )?
            	    int alt110=2;
            	    try { dbg.enterSubRule(110);
            	    try { dbg.enterDecision(110, decisionCanBacktrack[110]);

            	    int LA110_0 = input.LA(1);

            	    if ( (LA110_0==WS||(LA110_0>=NL && LA110_0<=COMMENT)) ) {
            	        alt110=1;
            	    }
            	    } finally {dbg.exitDecision(110);}

            	    switch (alt110) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:613:48: ws
            	            {
            	            dbg.location(613,48);
            	            pushFollow(FOLLOW_ws_in_declarations2804);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(110);}


            	    }
            	    break;
            	case 8 :
            	    dbg.enterAlt(8);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:615:17: {...}? media ( ws )?
            	    {
            	    dbg.location(615,17);
            	    if ( !(evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        throw new FailedPredicateException(input, "declarations", "isCssPreprocessorSource()");
            	    }
            	    dbg.location(615,46);
            	    pushFollow(FOLLOW_media_in_declarations2843);
            	    media();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(615,52);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:615:52: ( ws )?
            	    int alt111=2;
            	    try { dbg.enterSubRule(111);
            	    try { dbg.enterDecision(111, decisionCanBacktrack[111]);

            	    int LA111_0 = input.LA(1);

            	    if ( (LA111_0==WS||(LA111_0>=NL && LA111_0<=COMMENT)) ) {
            	        alt111=1;
            	    }
            	    } finally {dbg.exitDecision(111);}

            	    switch (alt111) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:615:52: ws
            	            {
            	            dbg.location(615,52);
            	            pushFollow(FOLLOW_ws_in_declarations2845);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(111);}


            	    }
            	    break;
            	case 9 :
            	    dbg.enterAlt(9);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:617:17: {...}? cp_mixin_call ( ws )?
            	    {
            	    dbg.location(617,17);
            	    if ( !(evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        throw new FailedPredicateException(input, "declarations", "isCssPreprocessorSource()");
            	    }
            	    dbg.location(617,46);
            	    pushFollow(FOLLOW_cp_mixin_call_in_declarations2884);
            	    cp_mixin_call();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(617,60);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:617:60: ( ws )?
            	    int alt112=2;
            	    try { dbg.enterSubRule(112);
            	    try { dbg.enterDecision(112, decisionCanBacktrack[112]);

            	    int LA112_0 = input.LA(1);

            	    if ( (LA112_0==WS||(LA112_0>=NL && LA112_0<=COMMENT)) ) {
            	        alt112=1;
            	    }
            	    } finally {dbg.exitDecision(112);}

            	    switch (alt112) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:617:60: ws
            	            {
            	            dbg.location(617,60);
            	            pushFollow(FOLLOW_ws_in_declarations2886);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(112);}


            	    }
            	    break;
            	case 10 :
            	    dbg.enterAlt(10);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:619:17: ( (~ SEMI )* SEMI )=> syncTo_SEMI
            	    {
            	    dbg.location(619,32);
            	    pushFollow(FOLLOW_syncTo_SEMI_in_declarations2931);
            	    syncTo_SEMI();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop113;
                }
            } while (true);
            } finally {dbg.exitSubRule(113);}

            dbg.location(621,13);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:621:13: ( declaration )?
            int alt114=2;
            try { dbg.enterSubRule(114);
            try { dbg.enterDecision(114, decisionCanBacktrack[114]);

            int LA114_0 = input.LA(1);

            if ( (LA114_0==IDENT||LA114_0==MEDIA_SYM||LA114_0==GEN||LA114_0==AT_IDENT||(LA114_0>=MINUS && LA114_0<=DOT)||LA114_0==STAR||LA114_0==SASS_VAR) ) {
                alt114=1;
            }
            } finally {dbg.exitDecision(114);}

            switch (alt114) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:621:13: declaration
                    {
                    dbg.location(621,13);
                    pushFollow(FOLLOW_declaration_in_declarations2961);
                    declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(114);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(622, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "declarations");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "declarations"


    // $ANTLR start "selectorsGroup"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:624:1: selectorsGroup : ( ( (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE )=> scss_selector_interpolation_expression ( ws )? | selector ( COMMA ( ws )? selector )* );
    public final void selectorsGroup() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "selectorsGroup");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(624, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:625:5: ( ( (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE )=> scss_selector_interpolation_expression ( ws )? | selector ( COMMA ( ws )? selector )* )
            int alt118=2;
            try { dbg.enterDecision(118, decisionCanBacktrack[118]);

            try {
                isCyclicDecision = true;
                alt118 = dfa118.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(118);}

            switch (alt118) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:627:9: ( (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE )=> scss_selector_interpolation_expression ( ws )?
                    {
                    dbg.location(627,60);
                    pushFollow(FOLLOW_scss_selector_interpolation_expression_in_selectorsGroup3021);
                    scss_selector_interpolation_expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(627,99);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:627:99: ( ws )?
                    int alt115=2;
                    try { dbg.enterSubRule(115);
                    try { dbg.enterDecision(115, decisionCanBacktrack[115]);

                    int LA115_0 = input.LA(1);

                    if ( (LA115_0==WS||(LA115_0>=NL && LA115_0<=COMMENT)) ) {
                        alt115=1;
                    }
                    } finally {dbg.exitDecision(115);}

                    switch (alt115) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:627:99: ws
                            {
                            dbg.location(627,99);
                            pushFollow(FOLLOW_ws_in_selectorsGroup3023);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(115);}


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:629:9: selector ( COMMA ( ws )? selector )*
                    {
                    dbg.location(629,9);
                    pushFollow(FOLLOW_selector_in_selectorsGroup3038);
                    selector();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(629,18);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:629:18: ( COMMA ( ws )? selector )*
                    try { dbg.enterSubRule(117);

                    loop117:
                    do {
                        int alt117=2;
                        try { dbg.enterDecision(117, decisionCanBacktrack[117]);

                        int LA117_0 = input.LA(1);

                        if ( (LA117_0==COMMA) ) {
                            alt117=1;
                        }


                        } finally {dbg.exitDecision(117);}

                        switch (alt117) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:629:19: COMMA ( ws )? selector
                    	    {
                    	    dbg.location(629,19);
                    	    match(input,COMMA,FOLLOW_COMMA_in_selectorsGroup3041); if (state.failed) return ;
                    	    dbg.location(629,25);
                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:629:25: ( ws )?
                    	    int alt116=2;
                    	    try { dbg.enterSubRule(116);
                    	    try { dbg.enterDecision(116, decisionCanBacktrack[116]);

                    	    int LA116_0 = input.LA(1);

                    	    if ( (LA116_0==WS||(LA116_0>=NL && LA116_0<=COMMENT)) ) {
                    	        alt116=1;
                    	    }
                    	    } finally {dbg.exitDecision(116);}

                    	    switch (alt116) {
                    	        case 1 :
                    	            dbg.enterAlt(1);

                    	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:629:25: ws
                    	            {
                    	            dbg.location(629,25);
                    	            pushFollow(FOLLOW_ws_in_selectorsGroup3043);
                    	            ws();

                    	            state._fsp--;
                    	            if (state.failed) return ;

                    	            }
                    	            break;

                    	    }
                    	    } finally {dbg.exitSubRule(116);}

                    	    dbg.location(629,29);
                    	    pushFollow(FOLLOW_selector_in_selectorsGroup3046);
                    	    selector();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop117;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(117);}


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(630, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "selectorsGroup");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "selectorsGroup"


    // $ANTLR start "selector"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:632:1: selector : simpleSelectorSequence ( combinator simpleSelectorSequence )* ;
    public final void selector() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "selector");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(632, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:633:5: ( simpleSelectorSequence ( combinator simpleSelectorSequence )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:633:7: simpleSelectorSequence ( combinator simpleSelectorSequence )*
            {
            dbg.location(633,7);
            pushFollow(FOLLOW_simpleSelectorSequence_in_selector3073);
            simpleSelectorSequence();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(633,30);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:633:30: ( combinator simpleSelectorSequence )*
            try { dbg.enterSubRule(119);

            loop119:
            do {
                int alt119=2;
                try { dbg.enterDecision(119, decisionCanBacktrack[119]);

                int LA119_0 = input.LA(1);

                if ( (LA119_0==IDENT||LA119_0==GEN||LA119_0==COLON||(LA119_0>=PLUS && LA119_0<=TILDE)||(LA119_0>=HASH_SYMBOL && LA119_0<=PIPE)||LA119_0==LESS_AND) ) {
                    alt119=1;
                }


                } finally {dbg.exitDecision(119);}

                switch (alt119) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:633:31: combinator simpleSelectorSequence
            	    {
            	    dbg.location(633,31);
            	    pushFollow(FOLLOW_combinator_in_selector3076);
            	    combinator();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(633,42);
            	    pushFollow(FOLLOW_simpleSelectorSequence_in_selector3078);
            	    simpleSelectorSequence();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop119;
                }
            } while (true);
            } finally {dbg.exitSubRule(119);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(634, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "selector");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "selector"


    // $ANTLR start "simpleSelectorSequence"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:637:1: simpleSelectorSequence : ( ( typeSelector ( ( esPred )=> elementSubsequent ( ws )? )* ) | ( ( ( esPred )=> elementSubsequent ( ws )? )+ ) );
    public final void simpleSelectorSequence() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "simpleSelectorSequence");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(637, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:638:2: ( ( typeSelector ( ( esPred )=> elementSubsequent ( ws )? )* ) | ( ( ( esPred )=> elementSubsequent ( ws )? )+ ) )
            int alt124=2;
            try { dbg.enterDecision(124, decisionCanBacktrack[124]);

            int LA124_0 = input.LA(1);

            if ( (LA124_0==IDENT||LA124_0==GEN||(LA124_0>=STAR && LA124_0<=PIPE)||LA124_0==LESS_AND) ) {
                alt124=1;
            }
            else if ( (LA124_0==COLON||(LA124_0>=HASH_SYMBOL && LA124_0<=SASS_EXTEND_ONLY_SELECTOR)) ) {
                alt124=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 124, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(124);}

            switch (alt124) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:640:2: ( typeSelector ( ( esPred )=> elementSubsequent ( ws )? )* )
                    {
                    dbg.location(640,2);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:640:2: ( typeSelector ( ( esPred )=> elementSubsequent ( ws )? )* )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:640:4: typeSelector ( ( esPred )=> elementSubsequent ( ws )? )*
                    {
                    dbg.location(640,4);
                    pushFollow(FOLLOW_typeSelector_in_simpleSelectorSequence3111);
                    typeSelector();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(640,17);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:640:17: ( ( esPred )=> elementSubsequent ( ws )? )*
                    try { dbg.enterSubRule(121);

                    loop121:
                    do {
                        int alt121=2;
                        try { dbg.enterDecision(121, decisionCanBacktrack[121]);

                        try {
                            isCyclicDecision = true;
                            alt121 = dfa121.predict(input);
                        }
                        catch (NoViableAltException nvae) {
                            dbg.recognitionException(nvae);
                            throw nvae;
                        }
                        } finally {dbg.exitDecision(121);}

                        switch (alt121) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:640:18: ( esPred )=> elementSubsequent ( ws )?
                    	    {
                    	    dbg.location(640,28);
                    	    pushFollow(FOLLOW_elementSubsequent_in_simpleSelectorSequence3118);
                    	    elementSubsequent();

                    	    state._fsp--;
                    	    if (state.failed) return ;
                    	    dbg.location(640,46);
                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:640:46: ( ws )?
                    	    int alt120=2;
                    	    try { dbg.enterSubRule(120);
                    	    try { dbg.enterDecision(120, decisionCanBacktrack[120]);

                    	    int LA120_0 = input.LA(1);

                    	    if ( (LA120_0==WS||(LA120_0>=NL && LA120_0<=COMMENT)) ) {
                    	        alt120=1;
                    	    }
                    	    } finally {dbg.exitDecision(120);}

                    	    switch (alt120) {
                    	        case 1 :
                    	            dbg.enterAlt(1);

                    	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:640:46: ws
                    	            {
                    	            dbg.location(640,46);
                    	            pushFollow(FOLLOW_ws_in_simpleSelectorSequence3120);
                    	            ws();

                    	            state._fsp--;
                    	            if (state.failed) return ;

                    	            }
                    	            break;

                    	    }
                    	    } finally {dbg.exitSubRule(120);}


                    	    }
                    	    break;

                    	default :
                    	    break loop121;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(121);}


                    }


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:642:2: ( ( ( esPred )=> elementSubsequent ( ws )? )+ )
                    {
                    dbg.location(642,2);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:642:2: ( ( ( esPred )=> elementSubsequent ( ws )? )+ )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:642:4: ( ( esPred )=> elementSubsequent ( ws )? )+
                    {
                    dbg.location(642,4);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:642:4: ( ( esPred )=> elementSubsequent ( ws )? )+
                    int cnt123=0;
                    try { dbg.enterSubRule(123);

                    loop123:
                    do {
                        int alt123=2;
                        try { dbg.enterDecision(123, decisionCanBacktrack[123]);

                        switch ( input.LA(1) ) {
                        case SASS_EXTEND_ONLY_SELECTOR:
                            {
                            int LA123_2 = input.LA(2);

                            if ( ((synpred12_Css3()&&evalPredicate(isScssSource(),"isScssSource()"))) ) {
                                alt123=1;
                            }


                            }
                            break;
                        case HASH:
                            {
                            int LA123_3 = input.LA(2);

                            if ( (synpred12_Css3()) ) {
                                alt123=1;
                            }


                            }
                            break;
                        case HASH_SYMBOL:
                            {
                            int LA123_4 = input.LA(2);

                            if ( (synpred12_Css3()) ) {
                                alt123=1;
                            }


                            }
                            break;
                        case DOT:
                            {
                            int LA123_5 = input.LA(2);

                            if ( (synpred12_Css3()) ) {
                                alt123=1;
                            }


                            }
                            break;
                        case LBRACKET:
                            {
                            int LA123_6 = input.LA(2);

                            if ( (synpred12_Css3()) ) {
                                alt123=1;
                            }


                            }
                            break;
                        case COLON:
                        case DCOLON:
                            {
                            int LA123_7 = input.LA(2);

                            if ( (synpred12_Css3()) ) {
                                alt123=1;
                            }


                            }
                            break;

                        }

                        } finally {dbg.exitDecision(123);}

                        switch (alt123) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:642:5: ( esPred )=> elementSubsequent ( ws )?
                    	    {
                    	    dbg.location(642,15);
                    	    pushFollow(FOLLOW_elementSubsequent_in_simpleSelectorSequence3139);
                    	    elementSubsequent();

                    	    state._fsp--;
                    	    if (state.failed) return ;
                    	    dbg.location(642,33);
                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:642:33: ( ws )?
                    	    int alt122=2;
                    	    try { dbg.enterSubRule(122);
                    	    try { dbg.enterDecision(122, decisionCanBacktrack[122]);

                    	    int LA122_0 = input.LA(1);

                    	    if ( (LA122_0==WS||(LA122_0>=NL && LA122_0<=COMMENT)) ) {
                    	        alt122=1;
                    	    }
                    	    } finally {dbg.exitDecision(122);}

                    	    switch (alt122) {
                    	        case 1 :
                    	            dbg.enterAlt(1);

                    	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:642:33: ws
                    	            {
                    	            dbg.location(642,33);
                    	            pushFollow(FOLLOW_ws_in_simpleSelectorSequence3141);
                    	            ws();

                    	            state._fsp--;
                    	            if (state.failed) return ;

                    	            }
                    	            break;

                    	    }
                    	    } finally {dbg.exitSubRule(122);}


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt123 >= 1 ) break loop123;
                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(123, input);
                                dbg.recognitionException(eee);

                                throw eee;
                        }
                        cnt123++;
                    } while (true);
                    } finally {dbg.exitSubRule(123);}


                    }


                    }
                    break;

            }
        }
        catch ( RecognitionException rce) {

                        reportError(rce);
                        consumeUntil(input, BitSet.of(LBRACE)); 
                    
        }
        finally {
        }
        dbg.location(643, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "simpleSelectorSequence");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "simpleSelectorSequence"


    // $ANTLR start "esPred"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:650:1: esPred : ( HASH_SYMBOL | HASH | DOT | LBRACKET | COLON | DCOLON | SASS_EXTEND_ONLY_SELECTOR );
    public final void esPred() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "esPred");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(650, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:651:5: ( HASH_SYMBOL | HASH | DOT | LBRACKET | COLON | DCOLON | SASS_EXTEND_ONLY_SELECTOR )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
            {
            dbg.location(651,5);
            if ( input.LA(1)==COLON||(input.LA(1)>=HASH_SYMBOL && input.LA(1)<=SASS_EXTEND_ONLY_SELECTOR) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(652, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "esPred");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "esPred"


    // $ANTLR start "typeSelector"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:654:1: typeSelector options {k=2; } : ( ( ( IDENT | STAR )? PIPE )=> namespacePrefix )? ( elementName ( ws )? ) ;
    public final void typeSelector() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "typeSelector");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(654, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:656:3: ( ( ( ( IDENT | STAR )? PIPE )=> namespacePrefix )? ( elementName ( ws )? ) )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:656:6: ( ( ( IDENT | STAR )? PIPE )=> namespacePrefix )? ( elementName ( ws )? )
            {
            dbg.location(656,6);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:656:6: ( ( ( IDENT | STAR )? PIPE )=> namespacePrefix )?
            int alt125=2;
            try { dbg.enterSubRule(125);
            try { dbg.enterDecision(125, decisionCanBacktrack[125]);

            int LA125_0 = input.LA(1);

            if ( (LA125_0==IDENT) ) {
                int LA125_1 = input.LA(2);

                if ( (synpred13_Css3()) ) {
                    alt125=1;
                }
            }
            else if ( (LA125_0==STAR) ) {
                int LA125_2 = input.LA(2);

                if ( (synpred13_Css3()) ) {
                    alt125=1;
                }
            }
            else if ( (LA125_0==PIPE) && (synpred13_Css3())) {
                alt125=1;
            }
            } finally {dbg.exitDecision(125);}

            switch (alt125) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:656:7: ( ( IDENT | STAR )? PIPE )=> namespacePrefix
                    {
                    dbg.location(656,31);
                    pushFollow(FOLLOW_namespacePrefix_in_typeSelector3257);
                    namespacePrefix();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(125);}

            dbg.location(656,49);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:656:49: ( elementName ( ws )? )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:656:51: elementName ( ws )?
            {
            dbg.location(656,51);
            pushFollow(FOLLOW_elementName_in_typeSelector3263);
            elementName();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(656,63);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:656:63: ( ws )?
            int alt126=2;
            try { dbg.enterSubRule(126);
            try { dbg.enterDecision(126, decisionCanBacktrack[126]);

            int LA126_0 = input.LA(1);

            if ( (LA126_0==WS||(LA126_0>=NL && LA126_0<=COMMENT)) ) {
                alt126=1;
            }
            } finally {dbg.exitDecision(126);}

            switch (alt126) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:656:63: ws
                    {
                    dbg.location(656,63);
                    pushFollow(FOLLOW_ws_in_typeSelector3265);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(126);}


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(657, 3);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "typeSelector");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "typeSelector"


    // $ANTLR start "namespacePrefix"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:659:1: namespacePrefix : ( namespacePrefixName | STAR )? PIPE ;
    public final void namespacePrefix() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "namespacePrefix");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(659, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:660:3: ( ( namespacePrefixName | STAR )? PIPE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:660:5: ( namespacePrefixName | STAR )? PIPE
            {
            dbg.location(660,5);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:660:5: ( namespacePrefixName | STAR )?
            int alt127=3;
            try { dbg.enterSubRule(127);
            try { dbg.enterDecision(127, decisionCanBacktrack[127]);

            int LA127_0 = input.LA(1);

            if ( (LA127_0==IDENT) ) {
                alt127=1;
            }
            else if ( (LA127_0==STAR) ) {
                alt127=2;
            }
            } finally {dbg.exitDecision(127);}

            switch (alt127) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:660:7: namespacePrefixName
                    {
                    dbg.location(660,7);
                    pushFollow(FOLLOW_namespacePrefixName_in_namespacePrefix3283);
                    namespacePrefixName();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:660:29: STAR
                    {
                    dbg.location(660,29);
                    match(input,STAR,FOLLOW_STAR_in_namespacePrefix3287); if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(127);}

            dbg.location(660,36);
            match(input,PIPE,FOLLOW_PIPE_in_namespacePrefix3291); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(661, 3);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "namespacePrefix");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "namespacePrefix"


    // $ANTLR start "elementSubsequent"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:664:1: elementSubsequent : ({...}? sass_extend_only_selector | cssId | cssClass | slAttribute | pseudo ) ;
    public final void elementSubsequent() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "elementSubsequent");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(664, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:665:5: ( ({...}? sass_extend_only_selector | cssId | cssClass | slAttribute | pseudo ) )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:666:5: ({...}? sass_extend_only_selector | cssId | cssClass | slAttribute | pseudo )
            {
            dbg.location(666,5);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:666:5: ({...}? sass_extend_only_selector | cssId | cssClass | slAttribute | pseudo )
            int alt128=5;
            try { dbg.enterSubRule(128);
            try { dbg.enterDecision(128, decisionCanBacktrack[128]);

            switch ( input.LA(1) ) {
            case SASS_EXTEND_ONLY_SELECTOR:
                {
                alt128=1;
                }
                break;
            case HASH_SYMBOL:
            case HASH:
                {
                alt128=2;
                }
                break;
            case DOT:
                {
                alt128=3;
                }
                break;
            case LBRACKET:
                {
                alt128=4;
                }
                break;
            case COLON:
            case DCOLON:
                {
                alt128=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 128, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(128);}

            switch (alt128) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:667:9: {...}? sass_extend_only_selector
                    {
                    dbg.location(667,9);
                    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "elementSubsequent", "isScssSource()");
                    }
                    dbg.location(667,27);
                    pushFollow(FOLLOW_sass_extend_only_selector_in_elementSubsequent3330);
                    sass_extend_only_selector();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:668:8: cssId
                    {
                    dbg.location(668,8);
                    pushFollow(FOLLOW_cssId_in_elementSubsequent3339);
                    cssId();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:669:8: cssClass
                    {
                    dbg.location(669,8);
                    pushFollow(FOLLOW_cssClass_in_elementSubsequent3348);
                    cssClass();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:670:11: slAttribute
                    {
                    dbg.location(670,11);
                    pushFollow(FOLLOW_slAttribute_in_elementSubsequent3360);
                    slAttribute();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 5 :
                    dbg.enterAlt(5);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:671:11: pseudo
                    {
                    dbg.location(671,11);
                    pushFollow(FOLLOW_pseudo_in_elementSubsequent3372);
                    pseudo();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(128);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(673, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "elementSubsequent");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "elementSubsequent"


    // $ANTLR start "cssId"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:676:1: cssId : ( HASH | ( HASH_SYMBOL NAME ) );
    public final void cssId() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cssId");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(676, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:677:5: ( HASH | ( HASH_SYMBOL NAME ) )
            int alt129=2;
            try { dbg.enterDecision(129, decisionCanBacktrack[129]);

            int LA129_0 = input.LA(1);

            if ( (LA129_0==HASH) ) {
                alt129=1;
            }
            else if ( (LA129_0==HASH_SYMBOL) ) {
                alt129=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 129, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(129);}

            switch (alt129) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:677:7: HASH
                    {
                    dbg.location(677,7);
                    match(input,HASH,FOLLOW_HASH_in_cssId3400); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:677:14: ( HASH_SYMBOL NAME )
                    {
                    dbg.location(677,14);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:677:14: ( HASH_SYMBOL NAME )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:677:16: HASH_SYMBOL NAME
                    {
                    dbg.location(677,16);
                    match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_cssId3406); if (state.failed) return ;
                    dbg.location(677,28);
                    match(input,NAME,FOLLOW_NAME_in_cssId3408); if (state.failed) return ;

                    }


                    }
                    break;

            }
        }
        catch ( RecognitionException rce) {

                    reportError(rce);
                    consumeUntil(input, BitSet.of(WS, IDENT, LBRACE)); 
                
        }
        finally {
        }
        dbg.location(678, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cssId");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cssId"


    // $ANTLR start "cssClass"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:684:1: cssClass : DOT ( IDENT | GEN ) ;
    public final void cssClass() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cssClass");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(684, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:685:5: ( DOT ( IDENT | GEN ) )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:685:7: DOT ( IDENT | GEN )
            {
            dbg.location(685,7);
            match(input,DOT,FOLLOW_DOT_in_cssClass3436); if (state.failed) return ;
            dbg.location(685,11);
            if ( input.LA(1)==IDENT||input.LA(1)==GEN ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch ( RecognitionException rce) {

                    reportError(rce);
                    consumeUntil(input, BitSet.of(WS, IDENT, LBRACE)); 
                
        }
        finally {
        }
        dbg.location(686, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cssClass");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cssClass"


    // $ANTLR start "elementName"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:693:1: elementName : ( ( IDENT | GEN | LESS_AND ) | STAR );
    public final void elementName() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "elementName");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(693, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:694:5: ( ( IDENT | GEN | LESS_AND ) | STAR )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
            {
            dbg.location(694,5);
            if ( input.LA(1)==IDENT||input.LA(1)==GEN||input.LA(1)==STAR||input.LA(1)==LESS_AND ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(695, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "elementName");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "elementName"


    // $ANTLR start "slAttribute"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:697:1: slAttribute : LBRACKET ( namespacePrefix )? ( ws )? slAttributeName ( ws )? ( ( OPEQ | INCLUDES | DASHMATCH | BEGINS | ENDS | CONTAINS ) ( ws )? slAttributeValue ( ws )? )? RBRACKET ;
    public final void slAttribute() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "slAttribute");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(697, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:698:5: ( LBRACKET ( namespacePrefix )? ( ws )? slAttributeName ( ws )? ( ( OPEQ | INCLUDES | DASHMATCH | BEGINS | ENDS | CONTAINS ) ( ws )? slAttributeValue ( ws )? )? RBRACKET )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:698:7: LBRACKET ( namespacePrefix )? ( ws )? slAttributeName ( ws )? ( ( OPEQ | INCLUDES | DASHMATCH | BEGINS | ENDS | CONTAINS ) ( ws )? slAttributeValue ( ws )? )? RBRACKET
            {
            dbg.location(698,7);
            match(input,LBRACKET,FOLLOW_LBRACKET_in_slAttribute3510); if (state.failed) return ;
            dbg.location(699,6);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:699:6: ( namespacePrefix )?
            int alt130=2;
            try { dbg.enterSubRule(130);
            try { dbg.enterDecision(130, decisionCanBacktrack[130]);

            int LA130_0 = input.LA(1);

            if ( (LA130_0==IDENT) ) {
                int LA130_1 = input.LA(2);

                if ( (LA130_1==PIPE) ) {
                    alt130=1;
                }
            }
            else if ( ((LA130_0>=STAR && LA130_0<=PIPE)) ) {
                alt130=1;
            }
            } finally {dbg.exitDecision(130);}

            switch (alt130) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:699:6: namespacePrefix
                    {
                    dbg.location(699,6);
                    pushFollow(FOLLOW_namespacePrefix_in_slAttribute3517);
                    namespacePrefix();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(130);}

            dbg.location(699,23);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:699:23: ( ws )?
            int alt131=2;
            try { dbg.enterSubRule(131);
            try { dbg.enterDecision(131, decisionCanBacktrack[131]);

            int LA131_0 = input.LA(1);

            if ( (LA131_0==WS||(LA131_0>=NL && LA131_0<=COMMENT)) ) {
                alt131=1;
            }
            } finally {dbg.exitDecision(131);}

            switch (alt131) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:699:23: ws
                    {
                    dbg.location(699,23);
                    pushFollow(FOLLOW_ws_in_slAttribute3520);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(131);}

            dbg.location(700,9);
            pushFollow(FOLLOW_slAttributeName_in_slAttribute3531);
            slAttributeName();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(700,25);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:700:25: ( ws )?
            int alt132=2;
            try { dbg.enterSubRule(132);
            try { dbg.enterDecision(132, decisionCanBacktrack[132]);

            int LA132_0 = input.LA(1);

            if ( (LA132_0==WS||(LA132_0>=NL && LA132_0<=COMMENT)) ) {
                alt132=1;
            }
            } finally {dbg.exitDecision(132);}

            switch (alt132) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:700:25: ws
                    {
                    dbg.location(700,25);
                    pushFollow(FOLLOW_ws_in_slAttribute3533);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(132);}

            dbg.location(702,13);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:702:13: ( ( OPEQ | INCLUDES | DASHMATCH | BEGINS | ENDS | CONTAINS ) ( ws )? slAttributeValue ( ws )? )?
            int alt135=2;
            try { dbg.enterSubRule(135);
            try { dbg.enterDecision(135, decisionCanBacktrack[135]);

            int LA135_0 = input.LA(1);

            if ( ((LA135_0>=OPEQ && LA135_0<=CONTAINS)) ) {
                alt135=1;
            }
            } finally {dbg.exitDecision(135);}

            switch (alt135) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:703:17: ( OPEQ | INCLUDES | DASHMATCH | BEGINS | ENDS | CONTAINS ) ( ws )? slAttributeValue ( ws )?
                    {
                    dbg.location(703,17);
                    if ( (input.LA(1)>=OPEQ && input.LA(1)<=CONTAINS) ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        dbg.recognitionException(mse);
                        throw mse;
                    }

                    dbg.location(711,17);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:711:17: ( ws )?
                    int alt133=2;
                    try { dbg.enterSubRule(133);
                    try { dbg.enterDecision(133, decisionCanBacktrack[133]);

                    int LA133_0 = input.LA(1);

                    if ( (LA133_0==WS||(LA133_0>=NL && LA133_0<=COMMENT)) ) {
                        alt133=1;
                    }
                    } finally {dbg.exitDecision(133);}

                    switch (alt133) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:711:17: ws
                            {
                            dbg.location(711,17);
                            pushFollow(FOLLOW_ws_in_slAttribute3755);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(133);}

                    dbg.location(712,17);
                    pushFollow(FOLLOW_slAttributeValue_in_slAttribute3774);
                    slAttributeValue();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(713,17);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:713:17: ( ws )?
                    int alt134=2;
                    try { dbg.enterSubRule(134);
                    try { dbg.enterDecision(134, decisionCanBacktrack[134]);

                    int LA134_0 = input.LA(1);

                    if ( (LA134_0==WS||(LA134_0>=NL && LA134_0<=COMMENT)) ) {
                        alt134=1;
                    }
                    } finally {dbg.exitDecision(134);}

                    switch (alt134) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:713:17: ws
                            {
                            dbg.location(713,17);
                            pushFollow(FOLLOW_ws_in_slAttribute3792);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(134);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(135);}

            dbg.location(716,7);
            match(input,RBRACKET,FOLLOW_RBRACKET_in_slAttribute3821); if (state.failed) return ;

            }

        }
        catch ( RecognitionException rce) {

                    reportError(rce);
                    consumeUntil(input, BitSet.of(IDENT, LBRACE)); 
                
        }
        finally {
        }
        dbg.location(717, 1);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "slAttribute");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "slAttribute"


    // $ANTLR start "slAttributeName"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:724:1: slAttributeName : IDENT ;
    public final void slAttributeName() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "slAttributeName");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(724, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:725:2: ( IDENT )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:725:4: IDENT
            {
            dbg.location(725,4);
            match(input,IDENT,FOLLOW_IDENT_in_slAttributeName3837); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(726, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "slAttributeName");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "slAttributeName"


    // $ANTLR start "slAttributeValue"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:728:1: slAttributeValue : ( IDENT | STRING ) ;
    public final void slAttributeValue() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "slAttributeValue");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(728, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:729:2: ( ( IDENT | STRING ) )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:730:2: ( IDENT | STRING )
            {
            dbg.location(730,2);
            if ( (input.LA(1)>=IDENT && input.LA(1)<=STRING) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(734, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "slAttributeValue");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "slAttributeValue"


    // $ANTLR start "pseudo"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:736:1: pseudo : ( COLON | DCOLON ) ( ( ( IDENT | GEN ) ( ( ws )? LPAREN ( ws )? ( expression | STAR )? RPAREN )? ) | ( NOT ( ws )? LPAREN ( ws )? ( simpleSelectorSequence )? RPAREN ) ) ;
    public final void pseudo() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "pseudo");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(736, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:737:5: ( ( COLON | DCOLON ) ( ( ( IDENT | GEN ) ( ( ws )? LPAREN ( ws )? ( expression | STAR )? RPAREN )? ) | ( NOT ( ws )? LPAREN ( ws )? ( simpleSelectorSequence )? RPAREN ) ) )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:737:7: ( COLON | DCOLON ) ( ( ( IDENT | GEN ) ( ( ws )? LPAREN ( ws )? ( expression | STAR )? RPAREN )? ) | ( NOT ( ws )? LPAREN ( ws )? ( simpleSelectorSequence )? RPAREN ) )
            {
            dbg.location(737,7);
            if ( input.LA(1)==COLON||input.LA(1)==DCOLON ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }

            dbg.location(738,14);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:738:14: ( ( ( IDENT | GEN ) ( ( ws )? LPAREN ( ws )? ( expression | STAR )? RPAREN )? ) | ( NOT ( ws )? LPAREN ( ws )? ( simpleSelectorSequence )? RPAREN ) )
            int alt143=2;
            try { dbg.enterSubRule(143);
            try { dbg.enterDecision(143, decisionCanBacktrack[143]);

            int LA143_0 = input.LA(1);

            if ( (LA143_0==IDENT||LA143_0==GEN) ) {
                alt143=1;
            }
            else if ( (LA143_0==NOT) ) {
                alt143=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 143, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(143);}

            switch (alt143) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:739:17: ( ( IDENT | GEN ) ( ( ws )? LPAREN ( ws )? ( expression | STAR )? RPAREN )? )
                    {
                    dbg.location(739,17);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:739:17: ( ( IDENT | GEN ) ( ( ws )? LPAREN ( ws )? ( expression | STAR )? RPAREN )? )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:740:21: ( IDENT | GEN ) ( ( ws )? LPAREN ( ws )? ( expression | STAR )? RPAREN )?
                    {
                    dbg.location(740,21);
                    if ( input.LA(1)==IDENT||input.LA(1)==GEN ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        dbg.recognitionException(mse);
                        throw mse;
                    }

                    dbg.location(741,21);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:741:21: ( ( ws )? LPAREN ( ws )? ( expression | STAR )? RPAREN )?
                    int alt139=2;
                    try { dbg.enterSubRule(139);
                    try { dbg.enterDecision(139, decisionCanBacktrack[139]);

                    try {
                        isCyclicDecision = true;
                        alt139 = dfa139.predict(input);
                    }
                    catch (NoViableAltException nvae) {
                        dbg.recognitionException(nvae);
                        throw nvae;
                    }
                    } finally {dbg.exitDecision(139);}

                    switch (alt139) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:742:25: ( ws )? LPAREN ( ws )? ( expression | STAR )? RPAREN
                            {
                            dbg.location(742,25);
                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:742:25: ( ws )?
                            int alt136=2;
                            try { dbg.enterSubRule(136);
                            try { dbg.enterDecision(136, decisionCanBacktrack[136]);

                            int LA136_0 = input.LA(1);

                            if ( (LA136_0==WS||(LA136_0>=NL && LA136_0<=COMMENT)) ) {
                                alt136=1;
                            }
                            } finally {dbg.exitDecision(136);}

                            switch (alt136) {
                                case 1 :
                                    dbg.enterAlt(1);

                                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:742:25: ws
                                    {
                                    dbg.location(742,25);
                                    pushFollow(FOLLOW_ws_in_pseudo4032);
                                    ws();

                                    state._fsp--;
                                    if (state.failed) return ;

                                    }
                                    break;

                            }
                            } finally {dbg.exitSubRule(136);}

                            dbg.location(742,29);
                            match(input,LPAREN,FOLLOW_LPAREN_in_pseudo4035); if (state.failed) return ;
                            dbg.location(742,36);
                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:742:36: ( ws )?
                            int alt137=2;
                            try { dbg.enterSubRule(137);
                            try { dbg.enterDecision(137, decisionCanBacktrack[137]);

                            int LA137_0 = input.LA(1);

                            if ( (LA137_0==WS||(LA137_0>=NL && LA137_0<=COMMENT)) ) {
                                alt137=1;
                            }
                            } finally {dbg.exitDecision(137);}

                            switch (alt137) {
                                case 1 :
                                    dbg.enterAlt(1);

                                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:742:36: ws
                                    {
                                    dbg.location(742,36);
                                    pushFollow(FOLLOW_ws_in_pseudo4037);
                                    ws();

                                    state._fsp--;
                                    if (state.failed) return ;

                                    }
                                    break;

                            }
                            } finally {dbg.exitSubRule(137);}

                            dbg.location(742,40);
                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:742:40: ( expression | STAR )?
                            int alt138=3;
                            try { dbg.enterSubRule(138);
                            try { dbg.enterDecision(138, decisionCanBacktrack[138]);

                            int LA138_0 = input.LA(1);

                            if ( ((LA138_0>=IDENT && LA138_0<=URI)||LA138_0==MEDIA_SYM||LA138_0==GEN||LA138_0==AT_IDENT||LA138_0==PERCENTAGE||LA138_0==PLUS||LA138_0==MINUS||LA138_0==HASH||(LA138_0>=NUMBER && LA138_0<=DIMENSION)||LA138_0==SASS_VAR) ) {
                                alt138=1;
                            }
                            else if ( (LA138_0==STAR) ) {
                                alt138=2;
                            }
                            } finally {dbg.exitDecision(138);}

                            switch (alt138) {
                                case 1 :
                                    dbg.enterAlt(1);

                                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:742:42: expression
                                    {
                                    dbg.location(742,42);
                                    pushFollow(FOLLOW_expression_in_pseudo4042);
                                    expression();

                                    state._fsp--;
                                    if (state.failed) return ;

                                    }
                                    break;
                                case 2 :
                                    dbg.enterAlt(2);

                                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:742:55: STAR
                                    {
                                    dbg.location(742,55);
                                    match(input,STAR,FOLLOW_STAR_in_pseudo4046); if (state.failed) return ;

                                    }
                                    break;

                            }
                            } finally {dbg.exitSubRule(138);}

                            dbg.location(742,63);
                            match(input,RPAREN,FOLLOW_RPAREN_in_pseudo4051); if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(139);}


                    }


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:746:17: ( NOT ( ws )? LPAREN ( ws )? ( simpleSelectorSequence )? RPAREN )
                    {
                    dbg.location(746,17);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:746:17: ( NOT ( ws )? LPAREN ( ws )? ( simpleSelectorSequence )? RPAREN )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:746:19: NOT ( ws )? LPAREN ( ws )? ( simpleSelectorSequence )? RPAREN
                    {
                    dbg.location(746,19);
                    match(input,NOT,FOLLOW_NOT_in_pseudo4130); if (state.failed) return ;
                    dbg.location(746,23);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:746:23: ( ws )?
                    int alt140=2;
                    try { dbg.enterSubRule(140);
                    try { dbg.enterDecision(140, decisionCanBacktrack[140]);

                    int LA140_0 = input.LA(1);

                    if ( (LA140_0==WS||(LA140_0>=NL && LA140_0<=COMMENT)) ) {
                        alt140=1;
                    }
                    } finally {dbg.exitDecision(140);}

                    switch (alt140) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:746:23: ws
                            {
                            dbg.location(746,23);
                            pushFollow(FOLLOW_ws_in_pseudo4132);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(140);}

                    dbg.location(746,27);
                    match(input,LPAREN,FOLLOW_LPAREN_in_pseudo4135); if (state.failed) return ;
                    dbg.location(746,34);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:746:34: ( ws )?
                    int alt141=2;
                    try { dbg.enterSubRule(141);
                    try { dbg.enterDecision(141, decisionCanBacktrack[141]);

                    int LA141_0 = input.LA(1);

                    if ( (LA141_0==WS||(LA141_0>=NL && LA141_0<=COMMENT)) ) {
                        alt141=1;
                    }
                    } finally {dbg.exitDecision(141);}

                    switch (alt141) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:746:34: ws
                            {
                            dbg.location(746,34);
                            pushFollow(FOLLOW_ws_in_pseudo4137);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(141);}

                    dbg.location(746,38);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:746:38: ( simpleSelectorSequence )?
                    int alt142=2;
                    try { dbg.enterSubRule(142);
                    try { dbg.enterDecision(142, decisionCanBacktrack[142]);

                    int LA142_0 = input.LA(1);

                    if ( (LA142_0==IDENT||LA142_0==GEN||LA142_0==COLON||(LA142_0>=HASH_SYMBOL && LA142_0<=PIPE)||LA142_0==LESS_AND) ) {
                        alt142=1;
                    }
                    } finally {dbg.exitDecision(142);}

                    switch (alt142) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:746:38: simpleSelectorSequence
                            {
                            dbg.location(746,38);
                            pushFollow(FOLLOW_simpleSelectorSequence_in_pseudo4140);
                            simpleSelectorSequence();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(142);}

                    dbg.location(746,62);
                    match(input,RPAREN,FOLLOW_RPAREN_in_pseudo4143); if (state.failed) return ;

                    }


                    }
                    break;

            }
            } finally {dbg.exitSubRule(143);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(748, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "pseudo");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "pseudo"


    // $ANTLR start "declaration"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:750:1: declaration : ( STAR )? property COLON ( ws )? propertyValue ( prio ( ws )? )? ;
    public final void declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(750, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:751:5: ( ( STAR )? property COLON ( ws )? propertyValue ( prio ( ws )? )? )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:752:5: ( STAR )? property COLON ( ws )? propertyValue ( prio ( ws )? )?
            {
            dbg.location(752,5);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:752:5: ( STAR )?
            int alt144=2;
            try { dbg.enterSubRule(144);
            try { dbg.enterDecision(144, decisionCanBacktrack[144]);

            int LA144_0 = input.LA(1);

            if ( (LA144_0==STAR) ) {
                alt144=1;
            }
            } finally {dbg.exitDecision(144);}

            switch (alt144) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:752:5: STAR
                    {
                    dbg.location(752,5);
                    match(input,STAR,FOLLOW_STAR_in_declaration4182); if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(144);}

            dbg.location(752,11);
            pushFollow(FOLLOW_property_in_declaration4185);
            property();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(752,20);
            match(input,COLON,FOLLOW_COLON_in_declaration4187); if (state.failed) return ;
            dbg.location(752,26);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:752:26: ( ws )?
            int alt145=2;
            try { dbg.enterSubRule(145);
            try { dbg.enterDecision(145, decisionCanBacktrack[145]);

            int LA145_0 = input.LA(1);

            if ( (LA145_0==WS||(LA145_0>=NL && LA145_0<=COMMENT)) ) {
                alt145=1;
            }
            } finally {dbg.exitDecision(145);}

            switch (alt145) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:752:26: ws
                    {
                    dbg.location(752,26);
                    pushFollow(FOLLOW_ws_in_declaration4189);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(145);}

            dbg.location(752,30);
            pushFollow(FOLLOW_propertyValue_in_declaration4192);
            propertyValue();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(752,44);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:752:44: ( prio ( ws )? )?
            int alt147=2;
            try { dbg.enterSubRule(147);
            try { dbg.enterDecision(147, decisionCanBacktrack[147]);

            int LA147_0 = input.LA(1);

            if ( (LA147_0==IMPORTANT_SYM) ) {
                alt147=1;
            }
            } finally {dbg.exitDecision(147);}

            switch (alt147) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:752:45: prio ( ws )?
                    {
                    dbg.location(752,45);
                    pushFollow(FOLLOW_prio_in_declaration4195);
                    prio();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(752,50);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:752:50: ( ws )?
                    int alt146=2;
                    try { dbg.enterSubRule(146);
                    try { dbg.enterDecision(146, decisionCanBacktrack[146]);

                    int LA146_0 = input.LA(1);

                    if ( (LA146_0==WS||(LA146_0>=NL && LA146_0<=COMMENT)) ) {
                        alt146=1;
                    }
                    } finally {dbg.exitDecision(146);}

                    switch (alt146) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:752:50: ws
                            {
                            dbg.location(752,50);
                            pushFollow(FOLLOW_ws_in_declaration4197);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(146);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(147);}


            }

        }
        catch ( RecognitionException rce) {

                    reportError(rce);
                    //recovery: if an mismatched token occures inside a declaration is found,
                    //then skip all tokens until an end of the rule is found represented by right curly brace
                    consumeUntil(input, BitSet.of(SEMI, RBRACE)); 
                
        }
        finally {
        }
        dbg.location(753, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "declaration"


    // $ANTLR start "propertyValue"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:761:1: propertyValue : ( ( (~ ( HASH_SYMBOL | SEMI ) )* HASH_SYMBOL LBRACE )=> scss_declaration_property_value_interpolation_expression | ( expressionPredicate )=> expression | ({...}? cp_expression ) );
    public final void propertyValue() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "propertyValue");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(761, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:762:2: ( ( (~ ( HASH_SYMBOL | SEMI ) )* HASH_SYMBOL LBRACE )=> scss_declaration_property_value_interpolation_expression | ( expressionPredicate )=> expression | ({...}? cp_expression ) )
            int alt148=3;
            try { dbg.enterDecision(148, decisionCanBacktrack[148]);

            try {
                isCyclicDecision = true;
                alt148 = dfa148.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(148);}

            switch (alt148) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:765:9: ( (~ ( HASH_SYMBOL | SEMI ) )* HASH_SYMBOL LBRACE )=> scss_declaration_property_value_interpolation_expression
                    {
                    dbg.location(765,52);
                    pushFollow(FOLLOW_scss_declaration_property_value_interpolation_expression_in_propertyValue4263);
                    scss_declaration_property_value_interpolation_expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:766:11: ( expressionPredicate )=> expression
                    {
                    dbg.location(766,34);
                    pushFollow(FOLLOW_expression_in_propertyValue4279);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:776:9: ({...}? cp_expression )
                    {
                    dbg.location(776,9);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:776:9: ({...}? cp_expression )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:776:11: {...}? cp_expression
                    {
                    dbg.location(776,11);
                    if ( !(evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "propertyValue", "isCssPreprocessorSource()");
                    }
                    dbg.location(776,40);
                    pushFollow(FOLLOW_cp_expression_in_propertyValue4320);
                    cp_expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(777, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "propertyValue");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "propertyValue"


    // $ANTLR start "expressionPredicate"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:780:1: expressionPredicate options {k=1; } : (~ ( AT_IDENT | STAR | SOLIDUS | LBRACE | SEMI | RBRACE ) )+ ( SEMI | RBRACE ) ;
    public final void expressionPredicate() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "expressionPredicate");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(780, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:782:5: ( (~ ( AT_IDENT | STAR | SOLIDUS | LBRACE | SEMI | RBRACE ) )+ ( SEMI | RBRACE ) )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:783:5: (~ ( AT_IDENT | STAR | SOLIDUS | LBRACE | SEMI | RBRACE ) )+ ( SEMI | RBRACE )
            {
            dbg.location(783,5);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:783:5: (~ ( AT_IDENT | STAR | SOLIDUS | LBRACE | SEMI | RBRACE ) )+
            int cnt149=0;
            try { dbg.enterSubRule(149);

            loop149:
            do {
                int alt149=2;
                try { dbg.enterDecision(149, decisionCanBacktrack[149]);

                int LA149_0 = input.LA(1);

                if ( (LA149_0==NAMESPACE_SYM||(LA149_0>=IDENT && LA149_0<=MEDIA_SYM)||(LA149_0>=AND && LA149_0<=RPAREN)||(LA149_0>=WS && LA149_0<=RIGHTBOTTOM_SYM)||(LA149_0>=PLUS && LA149_0<=SASS_EXTEND_ONLY_SELECTOR)||(LA149_0>=PIPE && LA149_0<=LINE_COMMENT)) ) {
                    alt149=1;
                }


                } finally {dbg.exitDecision(149);}

                switch (alt149) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:783:7: ~ ( AT_IDENT | STAR | SOLIDUS | LBRACE | SEMI | RBRACE )
            	    {
            	    dbg.location(783,7);
            	    if ( input.LA(1)==NAMESPACE_SYM||(input.LA(1)>=IDENT && input.LA(1)<=MEDIA_SYM)||(input.LA(1)>=AND && input.LA(1)<=RPAREN)||(input.LA(1)>=WS && input.LA(1)<=RIGHTBOTTOM_SYM)||(input.LA(1)>=PLUS && input.LA(1)<=SASS_EXTEND_ONLY_SELECTOR)||(input.LA(1)>=PIPE && input.LA(1)<=LINE_COMMENT) ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        dbg.recognitionException(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt149 >= 1 ) break loop149;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(149, input);
                        dbg.recognitionException(eee);

                        throw eee;
                }
                cnt149++;
            } while (true);
            } finally {dbg.exitSubRule(149);}

            dbg.location(783,65);
            if ( input.LA(1)==SEMI||input.LA(1)==RBRACE ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(784, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "expressionPredicate");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "expressionPredicate"


    // $ANTLR start "syncToDeclarationsRule"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:788:1: syncToDeclarationsRule : ;
    public final void syncToDeclarationsRule() throws RecognitionException {

                //why sync to DOT? - LESS allows class rules nested
                syncToSet(BitSet.of(IDENT, RBRACE, STAR, DOT)); 
            
        try { dbg.enterRule(getGrammarFileName(), "syncToDeclarationsRule");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(788, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:793:6: ()
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:794:6: 
            {
            }

        }
        finally {
        }
        dbg.location(794, 6);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "syncToDeclarationsRule");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "syncToDeclarationsRule"


    // $ANTLR start "syncTo_RBRACE"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:796:1: syncTo_RBRACE : ;
    public final void syncTo_RBRACE() throws RecognitionException {

                syncToRBRACE(1); //initial nest == 1
            
        try { dbg.enterRule(getGrammarFileName(), "syncTo_RBRACE");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(796, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:800:6: ()
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:801:6: 
            {
            }

        }
        finally {
        }
        dbg.location(801, 6);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "syncTo_RBRACE");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "syncTo_RBRACE"


    // $ANTLR start "syncTo_SEMI"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:803:1: syncTo_SEMI : SEMI ;
    public final void syncTo_SEMI() throws RecognitionException {

                syncToSet(BitSet.of(SEMI)); 
            
        try { dbg.enterRule(getGrammarFileName(), "syncTo_SEMI");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(803, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:807:6: ( SEMI )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:808:13: SEMI
            {
            dbg.location(808,13);
            match(input,SEMI,FOLLOW_SEMI_in_syncTo_SEMI4505); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(809, 6);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "syncTo_SEMI");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "syncTo_SEMI"


    // $ANTLR start "syncToFollow"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:812:1: syncToFollow : ;
    public final void syncToFollow() throws RecognitionException {

                syncToSet();
            
        try { dbg.enterRule(getGrammarFileName(), "syncToFollow");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(812, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:816:6: ()
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:817:6: 
            {
            }

        }
        finally {
        }
        dbg.location(817, 6);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "syncToFollow");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "syncToFollow"


    // $ANTLR start "prio"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:819:1: prio : IMPORTANT_SYM ;
    public final void prio() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "prio");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(819, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:820:5: ( IMPORTANT_SYM )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:820:7: IMPORTANT_SYM
            {
            dbg.location(820,7);
            match(input,IMPORTANT_SYM,FOLLOW_IMPORTANT_SYM_in_prio4560); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(821, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "prio");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "prio"


    // $ANTLR start "expression"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:823:1: expression : term ( ( operator ( ws )? )? term )* ;
    public final void expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(823, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:824:5: ( term ( ( operator ( ws )? )? term )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:824:7: term ( ( operator ( ws )? )? term )*
            {
            dbg.location(824,7);
            pushFollow(FOLLOW_term_in_expression4581);
            term();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(824,12);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:824:12: ( ( operator ( ws )? )? term )*
            try { dbg.enterSubRule(152);

            loop152:
            do {
                int alt152=2;
                try { dbg.enterDecision(152, decisionCanBacktrack[152]);

                try {
                    isCyclicDecision = true;
                    alt152 = dfa152.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(152);}

                switch (alt152) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:824:14: ( operator ( ws )? )? term
            	    {
            	    dbg.location(824,14);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:824:14: ( operator ( ws )? )?
            	    int alt151=2;
            	    try { dbg.enterSubRule(151);
            	    try { dbg.enterDecision(151, decisionCanBacktrack[151]);

            	    int LA151_0 = input.LA(1);

            	    if ( (LA151_0==COMMA||LA151_0==SOLIDUS) ) {
            	        alt151=1;
            	    }
            	    } finally {dbg.exitDecision(151);}

            	    switch (alt151) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:824:15: operator ( ws )?
            	            {
            	            dbg.location(824,15);
            	            pushFollow(FOLLOW_operator_in_expression4586);
            	            operator();

            	            state._fsp--;
            	            if (state.failed) return ;
            	            dbg.location(824,24);
            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:824:24: ( ws )?
            	            int alt150=2;
            	            try { dbg.enterSubRule(150);
            	            try { dbg.enterDecision(150, decisionCanBacktrack[150]);

            	            int LA150_0 = input.LA(1);

            	            if ( (LA150_0==WS||(LA150_0>=NL && LA150_0<=COMMENT)) ) {
            	                alt150=1;
            	            }
            	            } finally {dbg.exitDecision(150);}

            	            switch (alt150) {
            	                case 1 :
            	                    dbg.enterAlt(1);

            	                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:824:24: ws
            	                    {
            	                    dbg.location(824,24);
            	                    pushFollow(FOLLOW_ws_in_expression4588);
            	                    ws();

            	                    state._fsp--;
            	                    if (state.failed) return ;

            	                    }
            	                    break;

            	            }
            	            } finally {dbg.exitSubRule(150);}


            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(151);}

            	    dbg.location(824,30);
            	    pushFollow(FOLLOW_term_in_expression4593);
            	    term();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop152;
                }
            } while (true);
            } finally {dbg.exitSubRule(152);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(825, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "expression"


    // $ANTLR start "term"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:827:1: term : ( unaryOperator ( ws )? )? ( ( NUMBER | PERCENTAGE | LENGTH | EMS | REM | EXS | ANGLE | TIME | FREQ | RESOLUTION | DIMENSION ) | STRING | IDENT | GEN | URI | hexColor | function | {...}? cp_variable ) ( ws )? ;
    public final void term() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "term");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(827, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:828:5: ( ( unaryOperator ( ws )? )? ( ( NUMBER | PERCENTAGE | LENGTH | EMS | REM | EXS | ANGLE | TIME | FREQ | RESOLUTION | DIMENSION ) | STRING | IDENT | GEN | URI | hexColor | function | {...}? cp_variable ) ( ws )? )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:828:7: ( unaryOperator ( ws )? )? ( ( NUMBER | PERCENTAGE | LENGTH | EMS | REM | EXS | ANGLE | TIME | FREQ | RESOLUTION | DIMENSION ) | STRING | IDENT | GEN | URI | hexColor | function | {...}? cp_variable ) ( ws )?
            {
            dbg.location(828,7);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:828:7: ( unaryOperator ( ws )? )?
            int alt154=2;
            try { dbg.enterSubRule(154);
            try { dbg.enterDecision(154, decisionCanBacktrack[154]);

            int LA154_0 = input.LA(1);

            if ( (LA154_0==PLUS||LA154_0==MINUS) ) {
                alt154=1;
            }
            } finally {dbg.exitDecision(154);}

            switch (alt154) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:828:9: unaryOperator ( ws )?
                    {
                    dbg.location(828,9);
                    pushFollow(FOLLOW_unaryOperator_in_term4618);
                    unaryOperator();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(828,23);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:828:23: ( ws )?
                    int alt153=2;
                    try { dbg.enterSubRule(153);
                    try { dbg.enterDecision(153, decisionCanBacktrack[153]);

                    int LA153_0 = input.LA(1);

                    if ( (LA153_0==WS||(LA153_0>=NL && LA153_0<=COMMENT)) ) {
                        alt153=1;
                    }
                    } finally {dbg.exitDecision(153);}

                    switch (alt153) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:828:23: ws
                            {
                            dbg.location(828,23);
                            pushFollow(FOLLOW_ws_in_term4620);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(153);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(154);}

            dbg.location(829,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:829:9: ( ( NUMBER | PERCENTAGE | LENGTH | EMS | REM | EXS | ANGLE | TIME | FREQ | RESOLUTION | DIMENSION ) | STRING | IDENT | GEN | URI | hexColor | function | {...}? cp_variable )
            int alt155=8;
            try { dbg.enterSubRule(155);
            try { dbg.enterDecision(155, decisionCanBacktrack[155]);

            try {
                isCyclicDecision = true;
                alt155 = dfa155.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(155);}

            switch (alt155) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:830:9: ( NUMBER | PERCENTAGE | LENGTH | EMS | REM | EXS | ANGLE | TIME | FREQ | RESOLUTION | DIMENSION )
                    {
                    dbg.location(830,9);
                    if ( input.LA(1)==PERCENTAGE||(input.LA(1)>=NUMBER && input.LA(1)<=DIMENSION) ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        dbg.recognitionException(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:843:7: STRING
                    {
                    dbg.location(843,7);
                    match(input,STRING,FOLLOW_STRING_in_term4844); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:844:7: IDENT
                    {
                    dbg.location(844,7);
                    match(input,IDENT,FOLLOW_IDENT_in_term4852); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:845:7: GEN
                    {
                    dbg.location(845,7);
                    match(input,GEN,FOLLOW_GEN_in_term4860); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    dbg.enterAlt(5);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:846:7: URI
                    {
                    dbg.location(846,7);
                    match(input,URI,FOLLOW_URI_in_term4868); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    dbg.enterAlt(6);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:847:7: hexColor
                    {
                    dbg.location(847,7);
                    pushFollow(FOLLOW_hexColor_in_term4876);
                    hexColor();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 7 :
                    dbg.enterAlt(7);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:848:7: function
                    {
                    dbg.location(848,7);
                    pushFollow(FOLLOW_function_in_term4884);
                    function();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 8 :
                    dbg.enterAlt(8);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:849:7: {...}? cp_variable
                    {
                    dbg.location(849,7);
                    if ( !(evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "term", "isCssPreprocessorSource()");
                    }
                    dbg.location(849,36);
                    pushFollow(FOLLOW_cp_variable_in_term4894);
                    cp_variable();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(155);}

            dbg.location(851,5);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:851:5: ( ws )?
            int alt156=2;
            try { dbg.enterSubRule(156);
            try { dbg.enterDecision(156, decisionCanBacktrack[156]);

            int LA156_0 = input.LA(1);

            if ( (LA156_0==WS||(LA156_0>=NL && LA156_0<=COMMENT)) ) {
                alt156=1;
            }
            } finally {dbg.exitDecision(156);}

            switch (alt156) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:851:5: ws
                    {
                    dbg.location(851,5);
                    pushFollow(FOLLOW_ws_in_term4906);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(156);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(852, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "term");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "term"


    // $ANTLR start "function"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:854:1: function : functionName ( ws )? LPAREN ( ws )? ( expression | ( fnAttribute ( COMMA ( ws )? fnAttribute )* ) ) RPAREN ;
    public final void function() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "function");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(854, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:855:2: ( functionName ( ws )? LPAREN ( ws )? ( expression | ( fnAttribute ( COMMA ( ws )? fnAttribute )* ) ) RPAREN )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:855:5: functionName ( ws )? LPAREN ( ws )? ( expression | ( fnAttribute ( COMMA ( ws )? fnAttribute )* ) ) RPAREN
            {
            dbg.location(855,5);
            pushFollow(FOLLOW_functionName_in_function4922);
            functionName();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(855,18);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:855:18: ( ws )?
            int alt157=2;
            try { dbg.enterSubRule(157);
            try { dbg.enterDecision(157, decisionCanBacktrack[157]);

            int LA157_0 = input.LA(1);

            if ( (LA157_0==WS||(LA157_0>=NL && LA157_0<=COMMENT)) ) {
                alt157=1;
            }
            } finally {dbg.exitDecision(157);}

            switch (alt157) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:855:18: ws
                    {
                    dbg.location(855,18);
                    pushFollow(FOLLOW_ws_in_function4924);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(157);}

            dbg.location(856,3);
            match(input,LPAREN,FOLLOW_LPAREN_in_function4929); if (state.failed) return ;
            dbg.location(856,10);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:856:10: ( ws )?
            int alt158=2;
            try { dbg.enterSubRule(158);
            try { dbg.enterDecision(158, decisionCanBacktrack[158]);

            int LA158_0 = input.LA(1);

            if ( (LA158_0==WS||(LA158_0>=NL && LA158_0<=COMMENT)) ) {
                alt158=1;
            }
            } finally {dbg.exitDecision(158);}

            switch (alt158) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:856:10: ws
                    {
                    dbg.location(856,10);
                    pushFollow(FOLLOW_ws_in_function4931);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(158);}

            dbg.location(857,3);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:857:3: ( expression | ( fnAttribute ( COMMA ( ws )? fnAttribute )* ) )
            int alt161=2;
            try { dbg.enterSubRule(161);
            try { dbg.enterDecision(161, decisionCanBacktrack[161]);

            try {
                isCyclicDecision = true;
                alt161 = dfa161.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(161);}

            switch (alt161) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:858:4: expression
                    {
                    dbg.location(858,4);
                    pushFollow(FOLLOW_expression_in_function4941);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:860:6: ( fnAttribute ( COMMA ( ws )? fnAttribute )* )
                    {
                    dbg.location(860,6);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:860:6: ( fnAttribute ( COMMA ( ws )? fnAttribute )* )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:861:5: fnAttribute ( COMMA ( ws )? fnAttribute )*
                    {
                    dbg.location(861,5);
                    pushFollow(FOLLOW_fnAttribute_in_function4959);
                    fnAttribute();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(861,17);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:861:17: ( COMMA ( ws )? fnAttribute )*
                    try { dbg.enterSubRule(160);

                    loop160:
                    do {
                        int alt160=2;
                        try { dbg.enterDecision(160, decisionCanBacktrack[160]);

                        int LA160_0 = input.LA(1);

                        if ( (LA160_0==COMMA) ) {
                            alt160=1;
                        }


                        } finally {dbg.exitDecision(160);}

                        switch (alt160) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:861:18: COMMA ( ws )? fnAttribute
                    	    {
                    	    dbg.location(861,18);
                    	    match(input,COMMA,FOLLOW_COMMA_in_function4962); if (state.failed) return ;
                    	    dbg.location(861,24);
                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:861:24: ( ws )?
                    	    int alt159=2;
                    	    try { dbg.enterSubRule(159);
                    	    try { dbg.enterDecision(159, decisionCanBacktrack[159]);

                    	    int LA159_0 = input.LA(1);

                    	    if ( (LA159_0==WS||(LA159_0>=NL && LA159_0<=COMMENT)) ) {
                    	        alt159=1;
                    	    }
                    	    } finally {dbg.exitDecision(159);}

                    	    switch (alt159) {
                    	        case 1 :
                    	            dbg.enterAlt(1);

                    	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:861:24: ws
                    	            {
                    	            dbg.location(861,24);
                    	            pushFollow(FOLLOW_ws_in_function4964);
                    	            ws();

                    	            state._fsp--;
                    	            if (state.failed) return ;

                    	            }
                    	            break;

                    	    }
                    	    } finally {dbg.exitSubRule(159);}

                    	    dbg.location(861,28);
                    	    pushFollow(FOLLOW_fnAttribute_in_function4967);
                    	    fnAttribute();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop160;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(160);}


                    }


                    }
                    break;

            }
            } finally {dbg.exitSubRule(161);}

            dbg.location(864,3);
            match(input,RPAREN,FOLLOW_RPAREN_in_function4988); if (state.failed) return ;

            }

        }
        catch ( RecognitionException rce) {

                    reportError(rce);
                    consumeUntil(input, BitSet.of(RPAREN, SEMI, RBRACE)); 

        }
        finally {
        }
        dbg.location(865, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "function");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "function"


    // $ANTLR start "functionName"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:871:1: functionName : ( IDENT COLON )? IDENT ( DOT IDENT )* ;
    public final void functionName() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "functionName");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(871, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:875:2: ( ( IDENT COLON )? IDENT ( DOT IDENT )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:875:4: ( IDENT COLON )? IDENT ( DOT IDENT )*
            {
            dbg.location(875,4);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:875:4: ( IDENT COLON )?
            int alt162=2;
            try { dbg.enterSubRule(162);
            try { dbg.enterDecision(162, decisionCanBacktrack[162]);

            int LA162_0 = input.LA(1);

            if ( (LA162_0==IDENT) ) {
                int LA162_1 = input.LA(2);

                if ( (LA162_1==COLON) ) {
                    alt162=1;
                }
            }
            } finally {dbg.exitDecision(162);}

            switch (alt162) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:875:5: IDENT COLON
                    {
                    dbg.location(875,5);
                    match(input,IDENT,FOLLOW_IDENT_in_functionName5036); if (state.failed) return ;
                    dbg.location(875,11);
                    match(input,COLON,FOLLOW_COLON_in_functionName5038); if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(162);}

            dbg.location(875,19);
            match(input,IDENT,FOLLOW_IDENT_in_functionName5042); if (state.failed) return ;
            dbg.location(875,25);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:875:25: ( DOT IDENT )*
            try { dbg.enterSubRule(163);

            loop163:
            do {
                int alt163=2;
                try { dbg.enterDecision(163, decisionCanBacktrack[163]);

                int LA163_0 = input.LA(1);

                if ( (LA163_0==DOT) ) {
                    alt163=1;
                }


                } finally {dbg.exitDecision(163);}

                switch (alt163) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:875:26: DOT IDENT
            	    {
            	    dbg.location(875,26);
            	    match(input,DOT,FOLLOW_DOT_in_functionName5045); if (state.failed) return ;
            	    dbg.location(875,30);
            	    match(input,IDENT,FOLLOW_IDENT_in_functionName5047); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop163;
                }
            } while (true);
            } finally {dbg.exitSubRule(163);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(877, 6);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "functionName");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "functionName"


    // $ANTLR start "fnAttribute"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:879:1: fnAttribute : fnAttributeName ( ws )? OPEQ ( ws )? fnAttributeValue ;
    public final void fnAttribute() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "fnAttribute");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(879, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:880:2: ( fnAttributeName ( ws )? OPEQ ( ws )? fnAttributeValue )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:880:4: fnAttributeName ( ws )? OPEQ ( ws )? fnAttributeValue
            {
            dbg.location(880,4);
            pushFollow(FOLLOW_fnAttributeName_in_fnAttribute5070);
            fnAttributeName();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(880,20);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:880:20: ( ws )?
            int alt164=2;
            try { dbg.enterSubRule(164);
            try { dbg.enterDecision(164, decisionCanBacktrack[164]);

            int LA164_0 = input.LA(1);

            if ( (LA164_0==WS||(LA164_0>=NL && LA164_0<=COMMENT)) ) {
                alt164=1;
            }
            } finally {dbg.exitDecision(164);}

            switch (alt164) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:880:20: ws
                    {
                    dbg.location(880,20);
                    pushFollow(FOLLOW_ws_in_fnAttribute5072);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(164);}

            dbg.location(880,24);
            match(input,OPEQ,FOLLOW_OPEQ_in_fnAttribute5075); if (state.failed) return ;
            dbg.location(880,29);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:880:29: ( ws )?
            int alt165=2;
            try { dbg.enterSubRule(165);
            try { dbg.enterDecision(165, decisionCanBacktrack[165]);

            int LA165_0 = input.LA(1);

            if ( (LA165_0==WS||(LA165_0>=NL && LA165_0<=COMMENT)) ) {
                alt165=1;
            }
            } finally {dbg.exitDecision(165);}

            switch (alt165) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:880:29: ws
                    {
                    dbg.location(880,29);
                    pushFollow(FOLLOW_ws_in_fnAttribute5077);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(165);}

            dbg.location(880,33);
            pushFollow(FOLLOW_fnAttributeValue_in_fnAttribute5080);
            fnAttributeValue();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(881, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "fnAttribute");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "fnAttribute"


    // $ANTLR start "fnAttributeName"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:883:1: fnAttributeName : IDENT ( DOT IDENT )* ;
    public final void fnAttributeName() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "fnAttributeName");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(883, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:884:2: ( IDENT ( DOT IDENT )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:884:4: IDENT ( DOT IDENT )*
            {
            dbg.location(884,4);
            match(input,IDENT,FOLLOW_IDENT_in_fnAttributeName5095); if (state.failed) return ;
            dbg.location(884,10);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:884:10: ( DOT IDENT )*
            try { dbg.enterSubRule(166);

            loop166:
            do {
                int alt166=2;
                try { dbg.enterDecision(166, decisionCanBacktrack[166]);

                int LA166_0 = input.LA(1);

                if ( (LA166_0==DOT) ) {
                    alt166=1;
                }


                } finally {dbg.exitDecision(166);}

                switch (alt166) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:884:11: DOT IDENT
            	    {
            	    dbg.location(884,11);
            	    match(input,DOT,FOLLOW_DOT_in_fnAttributeName5098); if (state.failed) return ;
            	    dbg.location(884,15);
            	    match(input,IDENT,FOLLOW_IDENT_in_fnAttributeName5100); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop166;
                }
            } while (true);
            } finally {dbg.exitSubRule(166);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(885, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "fnAttributeName");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "fnAttributeName"


    // $ANTLR start "fnAttributeValue"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:887:1: fnAttributeValue : expression ;
    public final void fnAttributeValue() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "fnAttributeValue");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(887, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:888:2: ( expression )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:888:4: expression
            {
            dbg.location(888,4);
            pushFollow(FOLLOW_expression_in_fnAttributeValue5114);
            expression();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(889, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "fnAttributeValue");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "fnAttributeValue"


    // $ANTLR start "hexColor"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:891:1: hexColor : HASH ;
    public final void hexColor() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "hexColor");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(891, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:892:5: ( HASH )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:892:7: HASH
            {
            dbg.location(892,7);
            match(input,HASH,FOLLOW_HASH_in_hexColor5132); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(893, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "hexColor");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "hexColor"


    // $ANTLR start "ws"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:895:1: ws : ( WS | NL | COMMENT )+ ;
    public final void ws() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "ws");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(895, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:896:5: ( ( WS | NL | COMMENT )+ )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:896:7: ( WS | NL | COMMENT )+
            {
            dbg.location(896,7);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:896:7: ( WS | NL | COMMENT )+
            int cnt167=0;
            try { dbg.enterSubRule(167);

            loop167:
            do {
                int alt167=2;
                try { dbg.enterDecision(167, decisionCanBacktrack[167]);

                int LA167_0 = input.LA(1);

                if ( (LA167_0==WS||(LA167_0>=NL && LA167_0<=COMMENT)) ) {
                    alt167=1;
                }


                } finally {dbg.exitDecision(167);}

                switch (alt167) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
            	    {
            	    dbg.location(896,7);
            	    if ( input.LA(1)==WS||(input.LA(1)>=NL && input.LA(1)<=COMMENT) ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        dbg.recognitionException(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt167 >= 1 ) break loop167;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(167, input);
                        dbg.recognitionException(eee);

                        throw eee;
                }
                cnt167++;
            } while (true);
            } finally {dbg.exitSubRule(167);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(897, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "ws");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "ws"


    // $ANTLR start "cp_variable_declaration"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:902:1: cp_variable_declaration : ({...}? cp_variable ( ws )? COLON ( ws )? cp_expression SEMI | {...}? cp_variable ( ws )? COLON ( ws )? cp_expression ( SASS_DEFAULT ( ws )? )? SEMI );
    public final void cp_variable_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cp_variable_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(902, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:903:5: ({...}? cp_variable ( ws )? COLON ( ws )? cp_expression SEMI | {...}? cp_variable ( ws )? COLON ( ws )? cp_expression ( SASS_DEFAULT ( ws )? )? SEMI )
            int alt174=2;
            try { dbg.enterDecision(174, decisionCanBacktrack[174]);

            int LA174_0 = input.LA(1);

            if ( (LA174_0==MEDIA_SYM||LA174_0==AT_IDENT) ) {
                int LA174_1 = input.LA(2);

                if ( (evalPredicate(isLessSource(),"isLessSource()")) ) {
                    alt174=1;
                }
                else if ( ((evalPredicate(isScssSource(),"isScssSource()")&&evalPredicate(isLessSource(),"isLessSource()"))) ) {
                    alt174=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 174, 1, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }
            }
            else if ( (LA174_0==SASS_VAR) ) {
                int LA174_2 = input.LA(2);

                if ( ((evalPredicate(isLessSource(),"isLessSource()")&&evalPredicate(isScssSource(),"isScssSource()"))) ) {
                    alt174=1;
                }
                else if ( (evalPredicate(isScssSource(),"isScssSource()")) ) {
                    alt174=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 174, 2, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 174, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(174);}

            switch (alt174) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:904:9: {...}? cp_variable ( ws )? COLON ( ws )? cp_expression SEMI
                    {
                    dbg.location(904,9);
                    if ( !(evalPredicate(isLessSource(),"isLessSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "cp_variable_declaration", "isLessSource()");
                    }
                    dbg.location(904,27);
                    pushFollow(FOLLOW_cp_variable_in_cp_variable_declaration5201);
                    cp_variable();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(904,39);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:904:39: ( ws )?
                    int alt168=2;
                    try { dbg.enterSubRule(168);
                    try { dbg.enterDecision(168, decisionCanBacktrack[168]);

                    int LA168_0 = input.LA(1);

                    if ( (LA168_0==WS||(LA168_0>=NL && LA168_0<=COMMENT)) ) {
                        alt168=1;
                    }
                    } finally {dbg.exitDecision(168);}

                    switch (alt168) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:904:39: ws
                            {
                            dbg.location(904,39);
                            pushFollow(FOLLOW_ws_in_cp_variable_declaration5203);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(168);}

                    dbg.location(904,43);
                    match(input,COLON,FOLLOW_COLON_in_cp_variable_declaration5206); if (state.failed) return ;
                    dbg.location(904,49);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:904:49: ( ws )?
                    int alt169=2;
                    try { dbg.enterSubRule(169);
                    try { dbg.enterDecision(169, decisionCanBacktrack[169]);

                    int LA169_0 = input.LA(1);

                    if ( (LA169_0==WS||(LA169_0>=NL && LA169_0<=COMMENT)) ) {
                        alt169=1;
                    }
                    } finally {dbg.exitDecision(169);}

                    switch (alt169) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:904:49: ws
                            {
                            dbg.location(904,49);
                            pushFollow(FOLLOW_ws_in_cp_variable_declaration5208);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(169);}

                    dbg.location(904,53);
                    pushFollow(FOLLOW_cp_expression_in_cp_variable_declaration5211);
                    cp_expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(904,67);
                    match(input,SEMI,FOLLOW_SEMI_in_cp_variable_declaration5213); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:906:9: {...}? cp_variable ( ws )? COLON ( ws )? cp_expression ( SASS_DEFAULT ( ws )? )? SEMI
                    {
                    dbg.location(906,9);
                    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "cp_variable_declaration", "isScssSource()");
                    }
                    dbg.location(906,27);
                    pushFollow(FOLLOW_cp_variable_in_cp_variable_declaration5240);
                    cp_variable();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(906,39);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:906:39: ( ws )?
                    int alt170=2;
                    try { dbg.enterSubRule(170);
                    try { dbg.enterDecision(170, decisionCanBacktrack[170]);

                    int LA170_0 = input.LA(1);

                    if ( (LA170_0==WS||(LA170_0>=NL && LA170_0<=COMMENT)) ) {
                        alt170=1;
                    }
                    } finally {dbg.exitDecision(170);}

                    switch (alt170) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:906:39: ws
                            {
                            dbg.location(906,39);
                            pushFollow(FOLLOW_ws_in_cp_variable_declaration5242);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(170);}

                    dbg.location(906,43);
                    match(input,COLON,FOLLOW_COLON_in_cp_variable_declaration5245); if (state.failed) return ;
                    dbg.location(906,49);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:906:49: ( ws )?
                    int alt171=2;
                    try { dbg.enterSubRule(171);
                    try { dbg.enterDecision(171, decisionCanBacktrack[171]);

                    int LA171_0 = input.LA(1);

                    if ( (LA171_0==WS||(LA171_0>=NL && LA171_0<=COMMENT)) ) {
                        alt171=1;
                    }
                    } finally {dbg.exitDecision(171);}

                    switch (alt171) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:906:49: ws
                            {
                            dbg.location(906,49);
                            pushFollow(FOLLOW_ws_in_cp_variable_declaration5247);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(171);}

                    dbg.location(906,53);
                    pushFollow(FOLLOW_cp_expression_in_cp_variable_declaration5250);
                    cp_expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(906,67);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:906:67: ( SASS_DEFAULT ( ws )? )?
                    int alt173=2;
                    try { dbg.enterSubRule(173);
                    try { dbg.enterDecision(173, decisionCanBacktrack[173]);

                    int LA173_0 = input.LA(1);

                    if ( (LA173_0==SASS_DEFAULT) ) {
                        alt173=1;
                    }
                    } finally {dbg.exitDecision(173);}

                    switch (alt173) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:906:68: SASS_DEFAULT ( ws )?
                            {
                            dbg.location(906,68);
                            match(input,SASS_DEFAULT,FOLLOW_SASS_DEFAULT_in_cp_variable_declaration5253); if (state.failed) return ;
                            dbg.location(906,81);
                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:906:81: ( ws )?
                            int alt172=2;
                            try { dbg.enterSubRule(172);
                            try { dbg.enterDecision(172, decisionCanBacktrack[172]);

                            int LA172_0 = input.LA(1);

                            if ( (LA172_0==WS||(LA172_0>=NL && LA172_0<=COMMENT)) ) {
                                alt172=1;
                            }
                            } finally {dbg.exitDecision(172);}

                            switch (alt172) {
                                case 1 :
                                    dbg.enterAlt(1);

                                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:906:81: ws
                                    {
                                    dbg.location(906,81);
                                    pushFollow(FOLLOW_ws_in_cp_variable_declaration5255);
                                    ws();

                                    state._fsp--;
                                    if (state.failed) return ;

                                    }
                                    break;

                            }
                            } finally {dbg.exitSubRule(172);}


                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(173);}

                    dbg.location(906,87);
                    match(input,SEMI,FOLLOW_SEMI_in_cp_variable_declaration5260); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(907, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cp_variable_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cp_variable_declaration"


    // $ANTLR start "cp_variable"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:910:1: cp_variable : ({...}? ( AT_IDENT | MEDIA_SYM ) | {...}? ( SASS_VAR ) );
    public final void cp_variable() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cp_variable");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(910, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:911:5: ({...}? ( AT_IDENT | MEDIA_SYM ) | {...}? ( SASS_VAR ) )
            int alt175=2;
            try { dbg.enterDecision(175, decisionCanBacktrack[175]);

            int LA175_0 = input.LA(1);

            if ( (LA175_0==MEDIA_SYM||LA175_0==AT_IDENT) ) {
                alt175=1;
            }
            else if ( (LA175_0==SASS_VAR) ) {
                alt175=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 175, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(175);}

            switch (alt175) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:912:9: {...}? ( AT_IDENT | MEDIA_SYM )
                    {
                    dbg.location(912,9);
                    if ( !(evalPredicate(isLessSource(),"isLessSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "cp_variable", "isLessSource()");
                    }
                    dbg.location(912,27);
                    if ( input.LA(1)==MEDIA_SYM||input.LA(1)==AT_IDENT ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        dbg.recognitionException(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:914:9: {...}? ( SASS_VAR )
                    {
                    dbg.location(914,9);
                    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "cp_variable", "isScssSource()");
                    }
                    dbg.location(914,27);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:914:27: ( SASS_VAR )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:914:29: SASS_VAR
                    {
                    dbg.location(914,29);
                    match(input,SASS_VAR,FOLLOW_SASS_VAR_in_cp_variable5325); if (state.failed) return ;

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(916, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cp_variable");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cp_variable"


    // $ANTLR start "cp_expression"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:919:1: cp_expression : cp_additionExp ;
    public final void cp_expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cp_expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(919, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:920:5: ( cp_additionExp )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:920:10: cp_additionExp
            {
            dbg.location(920,10);
            pushFollow(FOLLOW_cp_additionExp_in_cp_expression5349);
            cp_additionExp();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(921, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cp_expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cp_expression"


    // $ANTLR start "cp_additionExp"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:923:1: cp_additionExp : cp_multiplyExp ( PLUS ( ws )? cp_multiplyExp | MINUS ( ws )? cp_multiplyExp )* ;
    public final void cp_additionExp() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cp_additionExp");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(923, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:924:5: ( cp_multiplyExp ( PLUS ( ws )? cp_multiplyExp | MINUS ( ws )? cp_multiplyExp )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:924:10: cp_multiplyExp ( PLUS ( ws )? cp_multiplyExp | MINUS ( ws )? cp_multiplyExp )*
            {
            dbg.location(924,10);
            pushFollow(FOLLOW_cp_multiplyExp_in_cp_additionExp5369);
            cp_multiplyExp();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(925,10);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:925:10: ( PLUS ( ws )? cp_multiplyExp | MINUS ( ws )? cp_multiplyExp )*
            try { dbg.enterSubRule(178);

            loop178:
            do {
                int alt178=3;
                try { dbg.enterDecision(178, decisionCanBacktrack[178]);

                int LA178_0 = input.LA(1);

                if ( (LA178_0==PLUS) ) {
                    alt178=1;
                }
                else if ( (LA178_0==MINUS) ) {
                    alt178=2;
                }


                } finally {dbg.exitDecision(178);}

                switch (alt178) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:925:12: PLUS ( ws )? cp_multiplyExp
            	    {
            	    dbg.location(925,12);
            	    match(input,PLUS,FOLLOW_PLUS_in_cp_additionExp5383); if (state.failed) return ;
            	    dbg.location(925,17);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:925:17: ( ws )?
            	    int alt176=2;
            	    try { dbg.enterSubRule(176);
            	    try { dbg.enterDecision(176, decisionCanBacktrack[176]);

            	    int LA176_0 = input.LA(1);

            	    if ( (LA176_0==WS||(LA176_0>=NL && LA176_0<=COMMENT)) ) {
            	        alt176=1;
            	    }
            	    } finally {dbg.exitDecision(176);}

            	    switch (alt176) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:925:17: ws
            	            {
            	            dbg.location(925,17);
            	            pushFollow(FOLLOW_ws_in_cp_additionExp5385);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(176);}

            	    dbg.location(925,21);
            	    pushFollow(FOLLOW_cp_multiplyExp_in_cp_additionExp5388);
            	    cp_multiplyExp();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    dbg.enterAlt(2);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:926:12: MINUS ( ws )? cp_multiplyExp
            	    {
            	    dbg.location(926,12);
            	    match(input,MINUS,FOLLOW_MINUS_in_cp_additionExp5401); if (state.failed) return ;
            	    dbg.location(926,18);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:926:18: ( ws )?
            	    int alt177=2;
            	    try { dbg.enterSubRule(177);
            	    try { dbg.enterDecision(177, decisionCanBacktrack[177]);

            	    int LA177_0 = input.LA(1);

            	    if ( (LA177_0==WS||(LA177_0>=NL && LA177_0<=COMMENT)) ) {
            	        alt177=1;
            	    }
            	    } finally {dbg.exitDecision(177);}

            	    switch (alt177) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:926:18: ws
            	            {
            	            dbg.location(926,18);
            	            pushFollow(FOLLOW_ws_in_cp_additionExp5403);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(177);}

            	    dbg.location(926,22);
            	    pushFollow(FOLLOW_cp_multiplyExp_in_cp_additionExp5406);
            	    cp_multiplyExp();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop178;
                }
            } while (true);
            } finally {dbg.exitSubRule(178);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(928, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cp_additionExp");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cp_additionExp"


    // $ANTLR start "cp_multiplyExp"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:930:1: cp_multiplyExp : cp_atomExp ( STAR ( ws )? cp_atomExp | SOLIDUS ( ws )? cp_atomExp )* ;
    public final void cp_multiplyExp() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cp_multiplyExp");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(930, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:931:5: ( cp_atomExp ( STAR ( ws )? cp_atomExp | SOLIDUS ( ws )? cp_atomExp )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:931:10: cp_atomExp ( STAR ( ws )? cp_atomExp | SOLIDUS ( ws )? cp_atomExp )*
            {
            dbg.location(931,10);
            pushFollow(FOLLOW_cp_atomExp_in_cp_multiplyExp5439);
            cp_atomExp();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(932,10);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:932:10: ( STAR ( ws )? cp_atomExp | SOLIDUS ( ws )? cp_atomExp )*
            try { dbg.enterSubRule(181);

            loop181:
            do {
                int alt181=3;
                try { dbg.enterDecision(181, decisionCanBacktrack[181]);

                int LA181_0 = input.LA(1);

                if ( (LA181_0==STAR) ) {
                    alt181=1;
                }
                else if ( (LA181_0==SOLIDUS) ) {
                    alt181=2;
                }


                } finally {dbg.exitDecision(181);}

                switch (alt181) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:932:12: STAR ( ws )? cp_atomExp
            	    {
            	    dbg.location(932,12);
            	    match(input,STAR,FOLLOW_STAR_in_cp_multiplyExp5452); if (state.failed) return ;
            	    dbg.location(932,17);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:932:17: ( ws )?
            	    int alt179=2;
            	    try { dbg.enterSubRule(179);
            	    try { dbg.enterDecision(179, decisionCanBacktrack[179]);

            	    int LA179_0 = input.LA(1);

            	    if ( (LA179_0==WS||(LA179_0>=NL && LA179_0<=COMMENT)) ) {
            	        alt179=1;
            	    }
            	    } finally {dbg.exitDecision(179);}

            	    switch (alt179) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:932:17: ws
            	            {
            	            dbg.location(932,17);
            	            pushFollow(FOLLOW_ws_in_cp_multiplyExp5454);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(179);}

            	    dbg.location(932,21);
            	    pushFollow(FOLLOW_cp_atomExp_in_cp_multiplyExp5457);
            	    cp_atomExp();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    dbg.enterAlt(2);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:933:12: SOLIDUS ( ws )? cp_atomExp
            	    {
            	    dbg.location(933,12);
            	    match(input,SOLIDUS,FOLLOW_SOLIDUS_in_cp_multiplyExp5471); if (state.failed) return ;
            	    dbg.location(933,20);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:933:20: ( ws )?
            	    int alt180=2;
            	    try { dbg.enterSubRule(180);
            	    try { dbg.enterDecision(180, decisionCanBacktrack[180]);

            	    int LA180_0 = input.LA(1);

            	    if ( (LA180_0==WS||(LA180_0>=NL && LA180_0<=COMMENT)) ) {
            	        alt180=1;
            	    }
            	    } finally {dbg.exitDecision(180);}

            	    switch (alt180) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:933:20: ws
            	            {
            	            dbg.location(933,20);
            	            pushFollow(FOLLOW_ws_in_cp_multiplyExp5473);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(180);}

            	    dbg.location(933,24);
            	    pushFollow(FOLLOW_cp_atomExp_in_cp_multiplyExp5476);
            	    cp_atomExp();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop181;
                }
            } while (true);
            } finally {dbg.exitSubRule(181);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(935, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cp_multiplyExp");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cp_multiplyExp"


    // $ANTLR start "cp_atomExp"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:937:1: cp_atomExp : ( term ( ( term )=> term )* | LPAREN ( ws )? cp_additionExp RPAREN ( ws )? );
    public final void cp_atomExp() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cp_atomExp");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(937, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:938:5: ( term ( ( term )=> term )* | LPAREN ( ws )? cp_additionExp RPAREN ( ws )? )
            int alt185=2;
            try { dbg.enterDecision(185, decisionCanBacktrack[185]);

            int LA185_0 = input.LA(1);

            if ( ((LA185_0>=IDENT && LA185_0<=URI)||LA185_0==MEDIA_SYM||LA185_0==GEN||LA185_0==AT_IDENT||LA185_0==PERCENTAGE||LA185_0==PLUS||LA185_0==MINUS||LA185_0==HASH||(LA185_0>=NUMBER && LA185_0<=DIMENSION)||LA185_0==SASS_VAR) ) {
                alt185=1;
            }
            else if ( (LA185_0==LPAREN) ) {
                alt185=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 185, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(185);}

            switch (alt185) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:938:10: term ( ( term )=> term )*
                    {
                    dbg.location(938,10);
                    pushFollow(FOLLOW_term_in_cp_atomExp5509);
                    term();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(938,15);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:938:15: ( ( term )=> term )*
                    try { dbg.enterSubRule(182);

                    loop182:
                    do {
                        int alt182=2;
                        try { dbg.enterDecision(182, decisionCanBacktrack[182]);

                        try {
                            isCyclicDecision = true;
                            alt182 = dfa182.predict(input);
                        }
                        catch (NoViableAltException nvae) {
                            dbg.recognitionException(nvae);
                            throw nvae;
                        }
                        } finally {dbg.exitDecision(182);}

                        switch (alt182) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:938:16: ( term )=> term
                    	    {
                    	    dbg.location(938,24);
                    	    pushFollow(FOLLOW_term_in_cp_atomExp5516);
                    	    term();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop182;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(182);}


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:939:10: LPAREN ( ws )? cp_additionExp RPAREN ( ws )?
                    {
                    dbg.location(939,10);
                    match(input,LPAREN,FOLLOW_LPAREN_in_cp_atomExp5530); if (state.failed) return ;
                    dbg.location(939,17);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:939:17: ( ws )?
                    int alt183=2;
                    try { dbg.enterSubRule(183);
                    try { dbg.enterDecision(183, decisionCanBacktrack[183]);

                    int LA183_0 = input.LA(1);

                    if ( (LA183_0==WS||(LA183_0>=NL && LA183_0<=COMMENT)) ) {
                        alt183=1;
                    }
                    } finally {dbg.exitDecision(183);}

                    switch (alt183) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:939:17: ws
                            {
                            dbg.location(939,17);
                            pushFollow(FOLLOW_ws_in_cp_atomExp5532);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(183);}

                    dbg.location(939,21);
                    pushFollow(FOLLOW_cp_additionExp_in_cp_atomExp5535);
                    cp_additionExp();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(939,36);
                    match(input,RPAREN,FOLLOW_RPAREN_in_cp_atomExp5537); if (state.failed) return ;
                    dbg.location(939,43);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:939:43: ( ws )?
                    int alt184=2;
                    try { dbg.enterSubRule(184);
                    try { dbg.enterDecision(184, decisionCanBacktrack[184]);

                    int LA184_0 = input.LA(1);

                    if ( (LA184_0==WS||(LA184_0>=NL && LA184_0<=COMMENT)) ) {
                        alt184=1;
                    }
                    } finally {dbg.exitDecision(184);}

                    switch (alt184) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:939:43: ws
                            {
                            dbg.location(939,43);
                            pushFollow(FOLLOW_ws_in_cp_atomExp5539);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(184);}


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(940, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cp_atomExp");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cp_atomExp"


    // $ANTLR start "cp_term"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:943:1: cp_term : ( ( NUMBER | PERCENTAGE | LENGTH | EMS | REM | EXS | ANGLE | TIME | FREQ | RESOLUTION | DIMENSION ) | STRING | IDENT | GEN | URI | hexColor | function | cp_variable ) ( ws )? ;
    public final void cp_term() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cp_term");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(943, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:944:5: ( ( ( NUMBER | PERCENTAGE | LENGTH | EMS | REM | EXS | ANGLE | TIME | FREQ | RESOLUTION | DIMENSION ) | STRING | IDENT | GEN | URI | hexColor | function | cp_variable ) ( ws )? )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:945:9: ( ( NUMBER | PERCENTAGE | LENGTH | EMS | REM | EXS | ANGLE | TIME | FREQ | RESOLUTION | DIMENSION ) | STRING | IDENT | GEN | URI | hexColor | function | cp_variable ) ( ws )?
            {
            dbg.location(945,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:945:9: ( ( NUMBER | PERCENTAGE | LENGTH | EMS | REM | EXS | ANGLE | TIME | FREQ | RESOLUTION | DIMENSION ) | STRING | IDENT | GEN | URI | hexColor | function | cp_variable )
            int alt186=8;
            try { dbg.enterSubRule(186);
            try { dbg.enterDecision(186, decisionCanBacktrack[186]);

            try {
                isCyclicDecision = true;
                alt186 = dfa186.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(186);}

            switch (alt186) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:946:9: ( NUMBER | PERCENTAGE | LENGTH | EMS | REM | EXS | ANGLE | TIME | FREQ | RESOLUTION | DIMENSION )
                    {
                    dbg.location(946,9);
                    if ( input.LA(1)==PERCENTAGE||(input.LA(1)>=NUMBER && input.LA(1)<=DIMENSION) ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        dbg.recognitionException(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:959:7: STRING
                    {
                    dbg.location(959,7);
                    match(input,STRING,FOLLOW_STRING_in_cp_term5777); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:960:7: IDENT
                    {
                    dbg.location(960,7);
                    match(input,IDENT,FOLLOW_IDENT_in_cp_term5785); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:961:7: GEN
                    {
                    dbg.location(961,7);
                    match(input,GEN,FOLLOW_GEN_in_cp_term5793); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    dbg.enterAlt(5);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:962:7: URI
                    {
                    dbg.location(962,7);
                    match(input,URI,FOLLOW_URI_in_cp_term5801); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    dbg.enterAlt(6);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:963:7: hexColor
                    {
                    dbg.location(963,7);
                    pushFollow(FOLLOW_hexColor_in_cp_term5809);
                    hexColor();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 7 :
                    dbg.enterAlt(7);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:964:7: function
                    {
                    dbg.location(964,7);
                    pushFollow(FOLLOW_function_in_cp_term5817);
                    function();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 8 :
                    dbg.enterAlt(8);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:965:7: cp_variable
                    {
                    dbg.location(965,7);
                    pushFollow(FOLLOW_cp_variable_in_cp_term5825);
                    cp_variable();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(186);}

            dbg.location(967,5);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:967:5: ( ws )?
            int alt187=2;
            try { dbg.enterSubRule(187);
            try { dbg.enterDecision(187, decisionCanBacktrack[187]);

            int LA187_0 = input.LA(1);

            if ( (LA187_0==WS||(LA187_0>=NL && LA187_0<=COMMENT)) ) {
                alt187=1;
            }
            } finally {dbg.exitDecision(187);}

            switch (alt187) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:967:5: ws
                    {
                    dbg.location(967,5);
                    pushFollow(FOLLOW_ws_in_cp_term5837);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(187);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(968, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cp_term");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cp_term"


    // $ANTLR start "cp_mixin_declaration"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:977:1: cp_mixin_declaration : ({...}? DOT cp_mixin_name ( ws )? LPAREN ( less_args_list )? RPAREN ( ws )? ( less_mixin_guarded ( ws )? )? | {...}? SASS_MIXIN ws cp_mixin_name ( ws )? ( LPAREN ( less_args_list )? RPAREN ( ws )? )? );
    public final void cp_mixin_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cp_mixin_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(977, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:978:5: ({...}? DOT cp_mixin_name ( ws )? LPAREN ( less_args_list )? RPAREN ( ws )? ( less_mixin_guarded ( ws )? )? | {...}? SASS_MIXIN ws cp_mixin_name ( ws )? ( LPAREN ( less_args_list )? RPAREN ( ws )? )? )
            int alt197=2;
            try { dbg.enterDecision(197, decisionCanBacktrack[197]);

            int LA197_0 = input.LA(1);

            if ( (LA197_0==DOT) ) {
                alt197=1;
            }
            else if ( (LA197_0==SASS_MIXIN) ) {
                alt197=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 197, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(197);}

            switch (alt197) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:979:5: {...}? DOT cp_mixin_name ( ws )? LPAREN ( less_args_list )? RPAREN ( ws )? ( less_mixin_guarded ( ws )? )?
                    {
                    dbg.location(979,5);
                    if ( !(evalPredicate(isLessSource(),"isLessSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "cp_mixin_declaration", "isLessSource()");
                    }
                    dbg.location(979,23);
                    match(input,DOT,FOLLOW_DOT_in_cp_mixin_declaration5868); if (state.failed) return ;
                    dbg.location(979,27);
                    pushFollow(FOLLOW_cp_mixin_name_in_cp_mixin_declaration5870);
                    cp_mixin_name();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(979,41);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:979:41: ( ws )?
                    int alt188=2;
                    try { dbg.enterSubRule(188);
                    try { dbg.enterDecision(188, decisionCanBacktrack[188]);

                    int LA188_0 = input.LA(1);

                    if ( (LA188_0==WS||(LA188_0>=NL && LA188_0<=COMMENT)) ) {
                        alt188=1;
                    }
                    } finally {dbg.exitDecision(188);}

                    switch (alt188) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:979:41: ws
                            {
                            dbg.location(979,41);
                            pushFollow(FOLLOW_ws_in_cp_mixin_declaration5872);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(188);}

                    dbg.location(979,45);
                    match(input,LPAREN,FOLLOW_LPAREN_in_cp_mixin_declaration5875); if (state.failed) return ;
                    dbg.location(979,52);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:979:52: ( less_args_list )?
                    int alt189=2;
                    try { dbg.enterSubRule(189);
                    try { dbg.enterDecision(189, decisionCanBacktrack[189]);

                    int LA189_0 = input.LA(1);

                    if ( (LA189_0==MEDIA_SYM||LA189_0==AT_IDENT||LA189_0==SASS_VAR||(LA189_0>=LESS_DOTS && LA189_0<=LESS_REST)) ) {
                        alt189=1;
                    }
                    } finally {dbg.exitDecision(189);}

                    switch (alt189) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:979:52: less_args_list
                            {
                            dbg.location(979,52);
                            pushFollow(FOLLOW_less_args_list_in_cp_mixin_declaration5877);
                            less_args_list();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(189);}

                    dbg.location(979,68);
                    match(input,RPAREN,FOLLOW_RPAREN_in_cp_mixin_declaration5880); if (state.failed) return ;
                    dbg.location(979,75);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:979:75: ( ws )?
                    int alt190=2;
                    try { dbg.enterSubRule(190);
                    try { dbg.enterDecision(190, decisionCanBacktrack[190]);

                    int LA190_0 = input.LA(1);

                    if ( (LA190_0==WS||(LA190_0>=NL && LA190_0<=COMMENT)) ) {
                        alt190=1;
                    }
                    } finally {dbg.exitDecision(190);}

                    switch (alt190) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:979:75: ws
                            {
                            dbg.location(979,75);
                            pushFollow(FOLLOW_ws_in_cp_mixin_declaration5882);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(190);}

                    dbg.location(979,79);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:979:79: ( less_mixin_guarded ( ws )? )?
                    int alt192=2;
                    try { dbg.enterSubRule(192);
                    try { dbg.enterDecision(192, decisionCanBacktrack[192]);

                    int LA192_0 = input.LA(1);

                    if ( (LA192_0==LESS_WHEN) ) {
                        alt192=1;
                    }
                    } finally {dbg.exitDecision(192);}

                    switch (alt192) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:979:80: less_mixin_guarded ( ws )?
                            {
                            dbg.location(979,80);
                            pushFollow(FOLLOW_less_mixin_guarded_in_cp_mixin_declaration5886);
                            less_mixin_guarded();

                            state._fsp--;
                            if (state.failed) return ;
                            dbg.location(979,99);
                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:979:99: ( ws )?
                            int alt191=2;
                            try { dbg.enterSubRule(191);
                            try { dbg.enterDecision(191, decisionCanBacktrack[191]);

                            int LA191_0 = input.LA(1);

                            if ( (LA191_0==WS||(LA191_0>=NL && LA191_0<=COMMENT)) ) {
                                alt191=1;
                            }
                            } finally {dbg.exitDecision(191);}

                            switch (alt191) {
                                case 1 :
                                    dbg.enterAlt(1);

                                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:979:99: ws
                                    {
                                    dbg.location(979,99);
                                    pushFollow(FOLLOW_ws_in_cp_mixin_declaration5888);
                                    ws();

                                    state._fsp--;
                                    if (state.failed) return ;

                                    }
                                    break;

                            }
                            } finally {dbg.exitSubRule(191);}


                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(192);}


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:981:5: {...}? SASS_MIXIN ws cp_mixin_name ( ws )? ( LPAREN ( less_args_list )? RPAREN ( ws )? )?
                    {
                    dbg.location(981,5);
                    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "cp_mixin_declaration", "isScssSource()");
                    }
                    dbg.location(981,23);
                    match(input,SASS_MIXIN,FOLLOW_SASS_MIXIN_in_cp_mixin_declaration5905); if (state.failed) return ;
                    dbg.location(981,34);
                    pushFollow(FOLLOW_ws_in_cp_mixin_declaration5907);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(981,37);
                    pushFollow(FOLLOW_cp_mixin_name_in_cp_mixin_declaration5909);
                    cp_mixin_name();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(981,51);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:981:51: ( ws )?
                    int alt193=2;
                    try { dbg.enterSubRule(193);
                    try { dbg.enterDecision(193, decisionCanBacktrack[193]);

                    int LA193_0 = input.LA(1);

                    if ( (LA193_0==WS||(LA193_0>=NL && LA193_0<=COMMENT)) ) {
                        alt193=1;
                    }
                    } finally {dbg.exitDecision(193);}

                    switch (alt193) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:981:51: ws
                            {
                            dbg.location(981,51);
                            pushFollow(FOLLOW_ws_in_cp_mixin_declaration5911);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(193);}

                    dbg.location(981,55);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:981:55: ( LPAREN ( less_args_list )? RPAREN ( ws )? )?
                    int alt196=2;
                    try { dbg.enterSubRule(196);
                    try { dbg.enterDecision(196, decisionCanBacktrack[196]);

                    int LA196_0 = input.LA(1);

                    if ( (LA196_0==LPAREN) ) {
                        alt196=1;
                    }
                    } finally {dbg.exitDecision(196);}

                    switch (alt196) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:981:56: LPAREN ( less_args_list )? RPAREN ( ws )?
                            {
                            dbg.location(981,56);
                            match(input,LPAREN,FOLLOW_LPAREN_in_cp_mixin_declaration5915); if (state.failed) return ;
                            dbg.location(981,63);
                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:981:63: ( less_args_list )?
                            int alt194=2;
                            try { dbg.enterSubRule(194);
                            try { dbg.enterDecision(194, decisionCanBacktrack[194]);

                            int LA194_0 = input.LA(1);

                            if ( (LA194_0==MEDIA_SYM||LA194_0==AT_IDENT||LA194_0==SASS_VAR||(LA194_0>=LESS_DOTS && LA194_0<=LESS_REST)) ) {
                                alt194=1;
                            }
                            } finally {dbg.exitDecision(194);}

                            switch (alt194) {
                                case 1 :
                                    dbg.enterAlt(1);

                                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:981:63: less_args_list
                                    {
                                    dbg.location(981,63);
                                    pushFollow(FOLLOW_less_args_list_in_cp_mixin_declaration5917);
                                    less_args_list();

                                    state._fsp--;
                                    if (state.failed) return ;

                                    }
                                    break;

                            }
                            } finally {dbg.exitSubRule(194);}

                            dbg.location(981,79);
                            match(input,RPAREN,FOLLOW_RPAREN_in_cp_mixin_declaration5920); if (state.failed) return ;
                            dbg.location(981,86);
                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:981:86: ( ws )?
                            int alt195=2;
                            try { dbg.enterSubRule(195);
                            try { dbg.enterDecision(195, decisionCanBacktrack[195]);

                            int LA195_0 = input.LA(1);

                            if ( (LA195_0==WS||(LA195_0>=NL && LA195_0<=COMMENT)) ) {
                                alt195=1;
                            }
                            } finally {dbg.exitDecision(195);}

                            switch (alt195) {
                                case 1 :
                                    dbg.enterAlt(1);

                                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:981:86: ws
                                    {
                                    dbg.location(981,86);
                                    pushFollow(FOLLOW_ws_in_cp_mixin_declaration5922);
                                    ws();

                                    state._fsp--;
                                    if (state.failed) return ;

                                    }
                                    break;

                            }
                            } finally {dbg.exitSubRule(195);}


                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(196);}


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(982, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cp_mixin_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cp_mixin_declaration"


    // $ANTLR start "cp_mixin_call"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:986:1: cp_mixin_call : ({...}? DOT cp_mixin_name | {...}? SASS_INCLUDE ws cp_mixin_name ) ( ( ws )? LPAREN ( cp_mixin_call_args )? RPAREN )? ( ws )? SEMI ;
    public final void cp_mixin_call() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cp_mixin_call");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(986, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:987:5: ( ({...}? DOT cp_mixin_name | {...}? SASS_INCLUDE ws cp_mixin_name ) ( ( ws )? LPAREN ( cp_mixin_call_args )? RPAREN )? ( ws )? SEMI )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:988:5: ({...}? DOT cp_mixin_name | {...}? SASS_INCLUDE ws cp_mixin_name ) ( ( ws )? LPAREN ( cp_mixin_call_args )? RPAREN )? ( ws )? SEMI
            {
            dbg.location(988,5);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:988:5: ({...}? DOT cp_mixin_name | {...}? SASS_INCLUDE ws cp_mixin_name )
            int alt198=2;
            try { dbg.enterSubRule(198);
            try { dbg.enterDecision(198, decisionCanBacktrack[198]);

            int LA198_0 = input.LA(1);

            if ( (LA198_0==DOT) ) {
                alt198=1;
            }
            else if ( (LA198_0==SASS_INCLUDE) ) {
                alt198=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 198, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(198);}

            switch (alt198) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:989:9: {...}? DOT cp_mixin_name
                    {
                    dbg.location(989,9);
                    if ( !(evalPredicate(isLessSource(),"isLessSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "cp_mixin_call", "isLessSource()");
                    }
                    dbg.location(989,27);
                    match(input,DOT,FOLLOW_DOT_in_cp_mixin_call5964); if (state.failed) return ;
                    dbg.location(989,31);
                    pushFollow(FOLLOW_cp_mixin_name_in_cp_mixin_call5966);
                    cp_mixin_name();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:991:9: {...}? SASS_INCLUDE ws cp_mixin_name
                    {
                    dbg.location(991,9);
                    if ( !(evalPredicate(isScssSource(),"isScssSource()")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "cp_mixin_call", "isScssSource()");
                    }
                    dbg.location(991,27);
                    match(input,SASS_INCLUDE,FOLLOW_SASS_INCLUDE_in_cp_mixin_call5988); if (state.failed) return ;
                    dbg.location(991,40);
                    pushFollow(FOLLOW_ws_in_cp_mixin_call5990);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(991,43);
                    pushFollow(FOLLOW_cp_mixin_name_in_cp_mixin_call5992);
                    cp_mixin_name();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(198);}

            dbg.location(993,5);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:993:5: ( ( ws )? LPAREN ( cp_mixin_call_args )? RPAREN )?
            int alt201=2;
            try { dbg.enterSubRule(201);
            try { dbg.enterDecision(201, decisionCanBacktrack[201]);

            try {
                isCyclicDecision = true;
                alt201 = dfa201.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(201);}

            switch (alt201) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:993:6: ( ws )? LPAREN ( cp_mixin_call_args )? RPAREN
                    {
                    dbg.location(993,6);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:993:6: ( ws )?
                    int alt199=2;
                    try { dbg.enterSubRule(199);
                    try { dbg.enterDecision(199, decisionCanBacktrack[199]);

                    int LA199_0 = input.LA(1);

                    if ( (LA199_0==WS||(LA199_0>=NL && LA199_0<=COMMENT)) ) {
                        alt199=1;
                    }
                    } finally {dbg.exitDecision(199);}

                    switch (alt199) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:993:6: ws
                            {
                            dbg.location(993,6);
                            pushFollow(FOLLOW_ws_in_cp_mixin_call6005);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(199);}

                    dbg.location(993,10);
                    match(input,LPAREN,FOLLOW_LPAREN_in_cp_mixin_call6008); if (state.failed) return ;
                    dbg.location(993,17);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:993:17: ( cp_mixin_call_args )?
                    int alt200=2;
                    try { dbg.enterSubRule(200);
                    try { dbg.enterDecision(200, decisionCanBacktrack[200]);

                    int LA200_0 = input.LA(1);

                    if ( ((LA200_0>=IDENT && LA200_0<=URI)||LA200_0==MEDIA_SYM||LA200_0==GEN||LA200_0==AT_IDENT||LA200_0==PERCENTAGE||LA200_0==PLUS||LA200_0==MINUS||LA200_0==HASH||(LA200_0>=NUMBER && LA200_0<=DIMENSION)||LA200_0==SASS_VAR) ) {
                        alt200=1;
                    }
                    } finally {dbg.exitDecision(200);}

                    switch (alt200) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:993:17: cp_mixin_call_args
                            {
                            dbg.location(993,17);
                            pushFollow(FOLLOW_cp_mixin_call_args_in_cp_mixin_call6010);
                            cp_mixin_call_args();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(200);}

                    dbg.location(993,37);
                    match(input,RPAREN,FOLLOW_RPAREN_in_cp_mixin_call6013); if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(201);}

            dbg.location(993,46);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:993:46: ( ws )?
            int alt202=2;
            try { dbg.enterSubRule(202);
            try { dbg.enterDecision(202, decisionCanBacktrack[202]);

            int LA202_0 = input.LA(1);

            if ( (LA202_0==WS||(LA202_0>=NL && LA202_0<=COMMENT)) ) {
                alt202=1;
            }
            } finally {dbg.exitDecision(202);}

            switch (alt202) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:993:46: ws
                    {
                    dbg.location(993,46);
                    pushFollow(FOLLOW_ws_in_cp_mixin_call6017);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(202);}

            dbg.location(993,50);
            match(input,SEMI,FOLLOW_SEMI_in_cp_mixin_call6020); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(994, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cp_mixin_call");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cp_mixin_call"


    // $ANTLR start "cp_mixin_name"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:996:1: cp_mixin_name : IDENT ;
    public final void cp_mixin_name() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cp_mixin_name");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(996, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:997:5: ( IDENT )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:998:5: IDENT
            {
            dbg.location(998,5);
            match(input,IDENT,FOLLOW_IDENT_in_cp_mixin_name6049); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(999, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cp_mixin_name");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cp_mixin_name"


    // $ANTLR start "cp_mixin_call_args"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1001:1: cp_mixin_call_args : term ( ( COMMA | SEMI ) ( ws )? term )* ;
    public final void cp_mixin_call_args() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "cp_mixin_call_args");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1001, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1002:5: ( term ( ( COMMA | SEMI ) ( ws )? term )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1005:5: term ( ( COMMA | SEMI ) ( ws )? term )*
            {
            dbg.location(1005,5);
            pushFollow(FOLLOW_term_in_cp_mixin_call_args6085);
            term();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1005,10);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1005:10: ( ( COMMA | SEMI ) ( ws )? term )*
            try { dbg.enterSubRule(204);

            loop204:
            do {
                int alt204=2;
                try { dbg.enterDecision(204, decisionCanBacktrack[204]);

                int LA204_0 = input.LA(1);

                if ( (LA204_0==SEMI||LA204_0==COMMA) ) {
                    alt204=1;
                }


                } finally {dbg.exitDecision(204);}

                switch (alt204) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1005:12: ( COMMA | SEMI ) ( ws )? term
            	    {
            	    dbg.location(1005,12);
            	    if ( input.LA(1)==SEMI||input.LA(1)==COMMA ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        dbg.recognitionException(mse);
            	        throw mse;
            	    }

            	    dbg.location(1005,27);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1005:27: ( ws )?
            	    int alt203=2;
            	    try { dbg.enterSubRule(203);
            	    try { dbg.enterDecision(203, decisionCanBacktrack[203]);

            	    int LA203_0 = input.LA(1);

            	    if ( (LA203_0==WS||(LA203_0>=NL && LA203_0<=COMMENT)) ) {
            	        alt203=1;
            	    }
            	    } finally {dbg.exitDecision(203);}

            	    switch (alt203) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1005:27: ws
            	            {
            	            dbg.location(1005,27);
            	            pushFollow(FOLLOW_ws_in_cp_mixin_call_args6097);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(203);}

            	    dbg.location(1005,31);
            	    pushFollow(FOLLOW_term_in_cp_mixin_call_args6100);
            	    term();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop204;
                }
            } while (true);
            } finally {dbg.exitSubRule(204);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1006, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "cp_mixin_call_args");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "cp_mixin_call_args"


    // $ANTLR start "less_args_list"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1009:1: less_args_list : ( ( less_arg ( ( COMMA | SEMI ) ( ws )? less_arg )* ( ( COMMA | SEMI ) ( ws )? ( LESS_DOTS | LESS_REST ) )? ) | ( LESS_DOTS | LESS_REST ) );
    public final void less_args_list() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "less_args_list");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1009, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1010:5: ( ( less_arg ( ( COMMA | SEMI ) ( ws )? less_arg )* ( ( COMMA | SEMI ) ( ws )? ( LESS_DOTS | LESS_REST ) )? ) | ( LESS_DOTS | LESS_REST ) )
            int alt209=2;
            try { dbg.enterDecision(209, decisionCanBacktrack[209]);

            int LA209_0 = input.LA(1);

            if ( (LA209_0==MEDIA_SYM||LA209_0==AT_IDENT||LA209_0==SASS_VAR) ) {
                alt209=1;
            }
            else if ( ((LA209_0>=LESS_DOTS && LA209_0<=LESS_REST)) ) {
                alt209=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 209, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(209);}

            switch (alt209) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1013:5: ( less_arg ( ( COMMA | SEMI ) ( ws )? less_arg )* ( ( COMMA | SEMI ) ( ws )? ( LESS_DOTS | LESS_REST ) )? )
                    {
                    dbg.location(1013,5);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1013:5: ( less_arg ( ( COMMA | SEMI ) ( ws )? less_arg )* ( ( COMMA | SEMI ) ( ws )? ( LESS_DOTS | LESS_REST ) )? )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1013:7: less_arg ( ( COMMA | SEMI ) ( ws )? less_arg )* ( ( COMMA | SEMI ) ( ws )? ( LESS_DOTS | LESS_REST ) )?
                    {
                    dbg.location(1013,7);
                    pushFollow(FOLLOW_less_arg_in_less_args_list6142);
                    less_arg();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(1013,16);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1013:16: ( ( COMMA | SEMI ) ( ws )? less_arg )*
                    try { dbg.enterSubRule(206);

                    loop206:
                    do {
                        int alt206=2;
                        try { dbg.enterDecision(206, decisionCanBacktrack[206]);

                        try {
                            isCyclicDecision = true;
                            alt206 = dfa206.predict(input);
                        }
                        catch (NoViableAltException nvae) {
                            dbg.recognitionException(nvae);
                            throw nvae;
                        }
                        } finally {dbg.exitDecision(206);}

                        switch (alt206) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1013:18: ( COMMA | SEMI ) ( ws )? less_arg
                    	    {
                    	    dbg.location(1013,18);
                    	    if ( input.LA(1)==SEMI||input.LA(1)==COMMA ) {
                    	        input.consume();
                    	        state.errorRecovery=false;state.failed=false;
                    	    }
                    	    else {
                    	        if (state.backtracking>0) {state.failed=true; return ;}
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        dbg.recognitionException(mse);
                    	        throw mse;
                    	    }

                    	    dbg.location(1013,35);
                    	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1013:35: ( ws )?
                    	    int alt205=2;
                    	    try { dbg.enterSubRule(205);
                    	    try { dbg.enterDecision(205, decisionCanBacktrack[205]);

                    	    int LA205_0 = input.LA(1);

                    	    if ( (LA205_0==WS||(LA205_0>=NL && LA205_0<=COMMENT)) ) {
                    	        alt205=1;
                    	    }
                    	    } finally {dbg.exitDecision(205);}

                    	    switch (alt205) {
                    	        case 1 :
                    	            dbg.enterAlt(1);

                    	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1013:35: ws
                    	            {
                    	            dbg.location(1013,35);
                    	            pushFollow(FOLLOW_ws_in_less_args_list6156);
                    	            ws();

                    	            state._fsp--;
                    	            if (state.failed) return ;

                    	            }
                    	            break;

                    	    }
                    	    } finally {dbg.exitSubRule(205);}

                    	    dbg.location(1013,39);
                    	    pushFollow(FOLLOW_less_arg_in_less_args_list6159);
                    	    less_arg();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop206;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(206);}

                    dbg.location(1013,50);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1013:50: ( ( COMMA | SEMI ) ( ws )? ( LESS_DOTS | LESS_REST ) )?
                    int alt208=2;
                    try { dbg.enterSubRule(208);
                    try { dbg.enterDecision(208, decisionCanBacktrack[208]);

                    int LA208_0 = input.LA(1);

                    if ( (LA208_0==SEMI||LA208_0==COMMA) ) {
                        alt208=1;
                    }
                    } finally {dbg.exitDecision(208);}

                    switch (alt208) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1013:52: ( COMMA | SEMI ) ( ws )? ( LESS_DOTS | LESS_REST )
                            {
                            dbg.location(1013,52);
                            if ( input.LA(1)==SEMI||input.LA(1)==COMMA ) {
                                input.consume();
                                state.errorRecovery=false;state.failed=false;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return ;}
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                dbg.recognitionException(mse);
                                throw mse;
                            }

                            dbg.location(1013,69);
                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1013:69: ( ws )?
                            int alt207=2;
                            try { dbg.enterSubRule(207);
                            try { dbg.enterDecision(207, decisionCanBacktrack[207]);

                            int LA207_0 = input.LA(1);

                            if ( (LA207_0==WS||(LA207_0>=NL && LA207_0<=COMMENT)) ) {
                                alt207=1;
                            }
                            } finally {dbg.exitDecision(207);}

                            switch (alt207) {
                                case 1 :
                                    dbg.enterAlt(1);

                                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1013:69: ws
                                    {
                                    dbg.location(1013,69);
                                    pushFollow(FOLLOW_ws_in_less_args_list6175);
                                    ws();

                                    state._fsp--;
                                    if (state.failed) return ;

                                    }
                                    break;

                            }
                            } finally {dbg.exitSubRule(207);}

                            dbg.location(1013,73);
                            if ( (input.LA(1)>=LESS_DOTS && input.LA(1)<=LESS_REST) ) {
                                input.consume();
                                state.errorRecovery=false;state.failed=false;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return ;}
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                dbg.recognitionException(mse);
                                throw mse;
                            }


                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(208);}


                    }


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1015:5: ( LESS_DOTS | LESS_REST )
                    {
                    dbg.location(1015,5);
                    if ( (input.LA(1)>=LESS_DOTS && input.LA(1)<=LESS_REST) ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        dbg.recognitionException(mse);
                        throw mse;
                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1016, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "less_args_list");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "less_args_list"


    // $ANTLR start "less_arg"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1019:1: less_arg : cp_variable ( COLON ( ws )? cp_expression )? ;
    public final void less_arg() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "less_arg");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1019, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1020:5: ( cp_variable ( COLON ( ws )? cp_expression )? )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1021:5: cp_variable ( COLON ( ws )? cp_expression )?
            {
            dbg.location(1021,5);
            pushFollow(FOLLOW_cp_variable_in_less_arg6232);
            cp_variable();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1021,17);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1021:17: ( COLON ( ws )? cp_expression )?
            int alt211=2;
            try { dbg.enterSubRule(211);
            try { dbg.enterDecision(211, decisionCanBacktrack[211]);

            int LA211_0 = input.LA(1);

            if ( (LA211_0==COLON) ) {
                alt211=1;
            }
            } finally {dbg.exitDecision(211);}

            switch (alt211) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1021:19: COLON ( ws )? cp_expression
                    {
                    dbg.location(1021,19);
                    match(input,COLON,FOLLOW_COLON_in_less_arg6236); if (state.failed) return ;
                    dbg.location(1021,25);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1021:25: ( ws )?
                    int alt210=2;
                    try { dbg.enterSubRule(210);
                    try { dbg.enterDecision(210, decisionCanBacktrack[210]);

                    int LA210_0 = input.LA(1);

                    if ( (LA210_0==WS||(LA210_0>=NL && LA210_0<=COMMENT)) ) {
                        alt210=1;
                    }
                    } finally {dbg.exitDecision(210);}

                    switch (alt210) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1021:25: ws
                            {
                            dbg.location(1021,25);
                            pushFollow(FOLLOW_ws_in_less_arg6238);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(210);}

                    dbg.location(1021,29);
                    pushFollow(FOLLOW_cp_expression_in_less_arg6241);
                    cp_expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(211);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1022, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "less_arg");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "less_arg"


    // $ANTLR start "less_mixin_guarded"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1026:1: less_mixin_guarded : LESS_WHEN ( ws )? less_condition ( ( COMMA | AND ) ( ws )? less_condition )* ;
    public final void less_mixin_guarded() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "less_mixin_guarded");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1026, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1027:5: ( LESS_WHEN ( ws )? less_condition ( ( COMMA | AND ) ( ws )? less_condition )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1028:5: LESS_WHEN ( ws )? less_condition ( ( COMMA | AND ) ( ws )? less_condition )*
            {
            dbg.location(1028,5);
            match(input,LESS_WHEN,FOLLOW_LESS_WHEN_in_less_mixin_guarded6267); if (state.failed) return ;
            dbg.location(1028,15);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1028:15: ( ws )?
            int alt212=2;
            try { dbg.enterSubRule(212);
            try { dbg.enterDecision(212, decisionCanBacktrack[212]);

            int LA212_0 = input.LA(1);

            if ( (LA212_0==WS||(LA212_0>=NL && LA212_0<=COMMENT)) ) {
                alt212=1;
            }
            } finally {dbg.exitDecision(212);}

            switch (alt212) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1028:15: ws
                    {
                    dbg.location(1028,15);
                    pushFollow(FOLLOW_ws_in_less_mixin_guarded6269);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(212);}

            dbg.location(1028,19);
            pushFollow(FOLLOW_less_condition_in_less_mixin_guarded6272);
            less_condition();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1028,34);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1028:34: ( ( COMMA | AND ) ( ws )? less_condition )*
            try { dbg.enterSubRule(214);

            loop214:
            do {
                int alt214=2;
                try { dbg.enterDecision(214, decisionCanBacktrack[214]);

                int LA214_0 = input.LA(1);

                if ( (LA214_0==COMMA||LA214_0==AND) ) {
                    alt214=1;
                }


                } finally {dbg.exitDecision(214);}

                switch (alt214) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1028:36: ( COMMA | AND ) ( ws )? less_condition
            	    {
            	    dbg.location(1028,36);
            	    if ( input.LA(1)==COMMA||input.LA(1)==AND ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        dbg.recognitionException(mse);
            	        throw mse;
            	    }

            	    dbg.location(1028,50);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1028:50: ( ws )?
            	    int alt213=2;
            	    try { dbg.enterSubRule(213);
            	    try { dbg.enterDecision(213, decisionCanBacktrack[213]);

            	    int LA213_0 = input.LA(1);

            	    if ( (LA213_0==WS||(LA213_0>=NL && LA213_0<=COMMENT)) ) {
            	        alt213=1;
            	    }
            	    } finally {dbg.exitDecision(213);}

            	    switch (alt213) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1028:50: ws
            	            {
            	            dbg.location(1028,50);
            	            pushFollow(FOLLOW_ws_in_less_mixin_guarded6284);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(213);}

            	    dbg.location(1028,54);
            	    pushFollow(FOLLOW_less_condition_in_less_mixin_guarded6287);
            	    less_condition();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop214;
                }
            } while (true);
            } finally {dbg.exitSubRule(214);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1029, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "less_mixin_guarded");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "less_mixin_guarded"


    // $ANTLR start "less_condition"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1033:1: less_condition : ( NOT ( ws )? )? LPAREN ( ws )? ( less_function_in_condition ( ws )? | ( cp_variable ( ( ws )? less_condition_operator ( ws )? cp_expression )? ) ) RPAREN ;
    public final void less_condition() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "less_condition");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1033, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1034:5: ( ( NOT ( ws )? )? LPAREN ( ws )? ( less_function_in_condition ( ws )? | ( cp_variable ( ( ws )? less_condition_operator ( ws )? cp_expression )? ) ) RPAREN )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1035:5: ( NOT ( ws )? )? LPAREN ( ws )? ( less_function_in_condition ( ws )? | ( cp_variable ( ( ws )? less_condition_operator ( ws )? cp_expression )? ) ) RPAREN
            {
            dbg.location(1035,5);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1035:5: ( NOT ( ws )? )?
            int alt216=2;
            try { dbg.enterSubRule(216);
            try { dbg.enterDecision(216, decisionCanBacktrack[216]);

            int LA216_0 = input.LA(1);

            if ( (LA216_0==NOT) ) {
                alt216=1;
            }
            } finally {dbg.exitDecision(216);}

            switch (alt216) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1035:6: NOT ( ws )?
                    {
                    dbg.location(1035,6);
                    match(input,NOT,FOLLOW_NOT_in_less_condition6317); if (state.failed) return ;
                    dbg.location(1035,10);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1035:10: ( ws )?
                    int alt215=2;
                    try { dbg.enterSubRule(215);
                    try { dbg.enterDecision(215, decisionCanBacktrack[215]);

                    int LA215_0 = input.LA(1);

                    if ( (LA215_0==WS||(LA215_0>=NL && LA215_0<=COMMENT)) ) {
                        alt215=1;
                    }
                    } finally {dbg.exitDecision(215);}

                    switch (alt215) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1035:10: ws
                            {
                            dbg.location(1035,10);
                            pushFollow(FOLLOW_ws_in_less_condition6319);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(215);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(216);}

            dbg.location(1036,5);
            match(input,LPAREN,FOLLOW_LPAREN_in_less_condition6328); if (state.failed) return ;
            dbg.location(1036,12);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1036:12: ( ws )?
            int alt217=2;
            try { dbg.enterSubRule(217);
            try { dbg.enterDecision(217, decisionCanBacktrack[217]);

            int LA217_0 = input.LA(1);

            if ( (LA217_0==WS||(LA217_0>=NL && LA217_0<=COMMENT)) ) {
                alt217=1;
            }
            } finally {dbg.exitDecision(217);}

            switch (alt217) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1036:12: ws
                    {
                    dbg.location(1036,12);
                    pushFollow(FOLLOW_ws_in_less_condition6330);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(217);}

            dbg.location(1037,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1037:9: ( less_function_in_condition ( ws )? | ( cp_variable ( ( ws )? less_condition_operator ( ws )? cp_expression )? ) )
            int alt222=2;
            try { dbg.enterSubRule(222);
            try { dbg.enterDecision(222, decisionCanBacktrack[222]);

            int LA222_0 = input.LA(1);

            if ( (LA222_0==IDENT) ) {
                alt222=1;
            }
            else if ( (LA222_0==MEDIA_SYM||LA222_0==AT_IDENT||LA222_0==SASS_VAR) ) {
                alt222=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 222, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(222);}

            switch (alt222) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1038:13: less_function_in_condition ( ws )?
                    {
                    dbg.location(1038,13);
                    pushFollow(FOLLOW_less_function_in_condition_in_less_condition6356);
                    less_function_in_condition();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(1038,40);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1038:40: ( ws )?
                    int alt218=2;
                    try { dbg.enterSubRule(218);
                    try { dbg.enterDecision(218, decisionCanBacktrack[218]);

                    int LA218_0 = input.LA(1);

                    if ( (LA218_0==WS||(LA218_0>=NL && LA218_0<=COMMENT)) ) {
                        alt218=1;
                    }
                    } finally {dbg.exitDecision(218);}

                    switch (alt218) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1038:40: ws
                            {
                            dbg.location(1038,40);
                            pushFollow(FOLLOW_ws_in_less_condition6358);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(218);}


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1040:13: ( cp_variable ( ( ws )? less_condition_operator ( ws )? cp_expression )? )
                    {
                    dbg.location(1040,13);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1040:13: ( cp_variable ( ( ws )? less_condition_operator ( ws )? cp_expression )? )
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1040:15: cp_variable ( ( ws )? less_condition_operator ( ws )? cp_expression )?
                    {
                    dbg.location(1040,15);
                    pushFollow(FOLLOW_cp_variable_in_less_condition6389);
                    cp_variable();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(1040,27);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1040:27: ( ( ws )? less_condition_operator ( ws )? cp_expression )?
                    int alt221=2;
                    try { dbg.enterSubRule(221);
                    try { dbg.enterDecision(221, decisionCanBacktrack[221]);

                    int LA221_0 = input.LA(1);

                    if ( (LA221_0==WS||LA221_0==GREATER||LA221_0==OPEQ||(LA221_0>=NL && LA221_0<=COMMENT)||(LA221_0>=GREATER_OR_EQ && LA221_0<=LESS_OR_EQ)) ) {
                        alt221=1;
                    }
                    } finally {dbg.exitDecision(221);}

                    switch (alt221) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1040:28: ( ws )? less_condition_operator ( ws )? cp_expression
                            {
                            dbg.location(1040,28);
                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1040:28: ( ws )?
                            int alt219=2;
                            try { dbg.enterSubRule(219);
                            try { dbg.enterDecision(219, decisionCanBacktrack[219]);

                            int LA219_0 = input.LA(1);

                            if ( (LA219_0==WS||(LA219_0>=NL && LA219_0<=COMMENT)) ) {
                                alt219=1;
                            }
                            } finally {dbg.exitDecision(219);}

                            switch (alt219) {
                                case 1 :
                                    dbg.enterAlt(1);

                                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1040:28: ws
                                    {
                                    dbg.location(1040,28);
                                    pushFollow(FOLLOW_ws_in_less_condition6392);
                                    ws();

                                    state._fsp--;
                                    if (state.failed) return ;

                                    }
                                    break;

                            }
                            } finally {dbg.exitSubRule(219);}

                            dbg.location(1040,32);
                            pushFollow(FOLLOW_less_condition_operator_in_less_condition6395);
                            less_condition_operator();

                            state._fsp--;
                            if (state.failed) return ;
                            dbg.location(1040,56);
                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1040:56: ( ws )?
                            int alt220=2;
                            try { dbg.enterSubRule(220);
                            try { dbg.enterDecision(220, decisionCanBacktrack[220]);

                            int LA220_0 = input.LA(1);

                            if ( (LA220_0==WS||(LA220_0>=NL && LA220_0<=COMMENT)) ) {
                                alt220=1;
                            }
                            } finally {dbg.exitDecision(220);}

                            switch (alt220) {
                                case 1 :
                                    dbg.enterAlt(1);

                                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1040:56: ws
                                    {
                                    dbg.location(1040,56);
                                    pushFollow(FOLLOW_ws_in_less_condition6397);
                                    ws();

                                    state._fsp--;
                                    if (state.failed) return ;

                                    }
                                    break;

                            }
                            } finally {dbg.exitSubRule(220);}

                            dbg.location(1040,60);
                            pushFollow(FOLLOW_cp_expression_in_less_condition6400);
                            cp_expression();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(221);}


                    }


                    }
                    break;

            }
            } finally {dbg.exitSubRule(222);}

            dbg.location(1042,5);
            match(input,RPAREN,FOLLOW_RPAREN_in_less_condition6429); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1043, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "less_condition");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "less_condition"


    // $ANTLR start "less_function_in_condition"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1046:1: less_function_in_condition : less_fn_name ( ws )? LPAREN ( ws )? cp_variable ( ws )? RPAREN ;
    public final void less_function_in_condition() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "less_function_in_condition");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1046, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1047:5: ( less_fn_name ( ws )? LPAREN ( ws )? cp_variable ( ws )? RPAREN )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1048:5: less_fn_name ( ws )? LPAREN ( ws )? cp_variable ( ws )? RPAREN
            {
            dbg.location(1048,5);
            pushFollow(FOLLOW_less_fn_name_in_less_function_in_condition6455);
            less_fn_name();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1048,18);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1048:18: ( ws )?
            int alt223=2;
            try { dbg.enterSubRule(223);
            try { dbg.enterDecision(223, decisionCanBacktrack[223]);

            int LA223_0 = input.LA(1);

            if ( (LA223_0==WS||(LA223_0>=NL && LA223_0<=COMMENT)) ) {
                alt223=1;
            }
            } finally {dbg.exitDecision(223);}

            switch (alt223) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1048:18: ws
                    {
                    dbg.location(1048,18);
                    pushFollow(FOLLOW_ws_in_less_function_in_condition6457);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(223);}

            dbg.location(1048,22);
            match(input,LPAREN,FOLLOW_LPAREN_in_less_function_in_condition6460); if (state.failed) return ;
            dbg.location(1048,29);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1048:29: ( ws )?
            int alt224=2;
            try { dbg.enterSubRule(224);
            try { dbg.enterDecision(224, decisionCanBacktrack[224]);

            int LA224_0 = input.LA(1);

            if ( (LA224_0==WS||(LA224_0>=NL && LA224_0<=COMMENT)) ) {
                alt224=1;
            }
            } finally {dbg.exitDecision(224);}

            switch (alt224) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1048:29: ws
                    {
                    dbg.location(1048,29);
                    pushFollow(FOLLOW_ws_in_less_function_in_condition6462);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(224);}

            dbg.location(1048,33);
            pushFollow(FOLLOW_cp_variable_in_less_function_in_condition6465);
            cp_variable();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1048,45);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1048:45: ( ws )?
            int alt225=2;
            try { dbg.enterSubRule(225);
            try { dbg.enterDecision(225, decisionCanBacktrack[225]);

            int LA225_0 = input.LA(1);

            if ( (LA225_0==WS||(LA225_0>=NL && LA225_0<=COMMENT)) ) {
                alt225=1;
            }
            } finally {dbg.exitDecision(225);}

            switch (alt225) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1048:45: ws
                    {
                    dbg.location(1048,45);
                    pushFollow(FOLLOW_ws_in_less_function_in_condition6467);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(225);}

            dbg.location(1048,49);
            match(input,RPAREN,FOLLOW_RPAREN_in_less_function_in_condition6470); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1049, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "less_function_in_condition");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "less_function_in_condition"


    // $ANTLR start "less_fn_name"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1052:1: less_fn_name : IDENT ;
    public final void less_fn_name() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "less_fn_name");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1052, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1053:5: ( IDENT )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1054:5: IDENT
            {
            dbg.location(1054,5);
            match(input,IDENT,FOLLOW_IDENT_in_less_fn_name6492); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1055, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "less_fn_name");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "less_fn_name"


    // $ANTLR start "less_condition_operator"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1057:1: less_condition_operator : ( GREATER | GREATER_OR_EQ | OPEQ | LESS | LESS_OR_EQ );
    public final void less_condition_operator() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "less_condition_operator");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1057, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1058:5: ( GREATER | GREATER_OR_EQ | OPEQ | LESS | LESS_OR_EQ )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
            {
            dbg.location(1058,5);
            if ( input.LA(1)==GREATER||input.LA(1)==OPEQ||(input.LA(1)>=GREATER_OR_EQ && input.LA(1)<=LESS_OR_EQ) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1060, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "less_condition_operator");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "less_condition_operator"


    // $ANTLR start "scss_selector_interpolation_expression"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1078:1: scss_selector_interpolation_expression : ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) ) ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) ) )* ;
    public final void scss_selector_interpolation_expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "scss_selector_interpolation_expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1078, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1079:5: ( ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) ) ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) ) )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1080:9: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) ) ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) ) )*
            {
            dbg.location(1080,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1080:9: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) )
            int alt226=2;
            try { dbg.enterSubRule(226);
            try { dbg.enterDecision(226, decisionCanBacktrack[226]);

            try {
                isCyclicDecision = true;
                alt226 = dfa226.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(226);}

            switch (alt226) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1081:13: ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var
                    {
                    dbg.location(1081,35);
                    pushFollow(FOLLOW_scss_interpolation_expression_var_in_scss_selector_interpolation_expression6591);
                    scss_interpolation_expression_var();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1083:13: ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON )
                    {
                    dbg.location(1083,13);
                    if ( input.LA(1)==IDENT||input.LA(1)==COLON||(input.LA(1)>=MINUS && input.LA(1)<=DOT) ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        dbg.recognitionException(mse);
                        throw mse;
                    }


                    }
                    break;

            }
            } finally {dbg.exitSubRule(226);}

            dbg.location(1085,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1085:9: ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) ) )*
            try { dbg.enterSubRule(229);

            loop229:
            do {
                int alt229=2;
                try { dbg.enterDecision(229, decisionCanBacktrack[229]);

                try {
                    isCyclicDecision = true;
                    alt229 = dfa229.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(229);}

                switch (alt229) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1086:13: ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) )
            	    {
            	    dbg.location(1086,13);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1086:13: ( ws )?
            	    int alt227=2;
            	    try { dbg.enterSubRule(227);
            	    try { dbg.enterDecision(227, decisionCanBacktrack[227]);

            	    int LA227_0 = input.LA(1);

            	    if ( (LA227_0==WS||(LA227_0>=NL && LA227_0<=COMMENT)) ) {
            	        alt227=1;
            	    }
            	    } finally {dbg.exitDecision(227);}

            	    switch (alt227) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1086:13: ws
            	            {
            	            dbg.location(1086,13);
            	            pushFollow(FOLLOW_ws_in_scss_selector_interpolation_expression6676);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(227);}

            	    dbg.location(1087,13);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1087:13: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) )
            	    int alt228=2;
            	    try { dbg.enterSubRule(228);
            	    try { dbg.enterDecision(228, decisionCanBacktrack[228]);

            	    try {
            	        isCyclicDecision = true;
            	        alt228 = dfa228.predict(input);
            	    }
            	    catch (NoViableAltException nvae) {
            	        dbg.recognitionException(nvae);
            	        throw nvae;
            	    }
            	    } finally {dbg.exitDecision(228);}

            	    switch (alt228) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1088:17: ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var
            	            {
            	            dbg.location(1088,39);
            	            pushFollow(FOLLOW_scss_interpolation_expression_var_in_scss_selector_interpolation_expression6715);
            	            scss_interpolation_expression_var();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;
            	        case 2 :
            	            dbg.enterAlt(2);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1090:17: ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON )
            	            {
            	            dbg.location(1090,17);
            	            if ( input.LA(1)==IDENT||input.LA(1)==COLON||(input.LA(1)>=MINUS && input.LA(1)<=DOT) ) {
            	                input.consume();
            	                state.errorRecovery=false;state.failed=false;
            	            }
            	            else {
            	                if (state.backtracking>0) {state.failed=true; return ;}
            	                MismatchedSetException mse = new MismatchedSetException(null,input);
            	                dbg.recognitionException(mse);
            	                throw mse;
            	            }


            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(228);}


            	    }
            	    break;

            	default :
            	    break loop229;
                }
            } while (true);
            } finally {dbg.exitSubRule(229);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1094, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "scss_selector_interpolation_expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "scss_selector_interpolation_expression"


    // $ANTLR start "scss_declaration_interpolation_expression"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1096:1: scss_declaration_interpolation_expression : ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH ) ) ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH ) ) )* ;
    public final void scss_declaration_interpolation_expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "scss_declaration_interpolation_expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1096, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1097:5: ( ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH ) ) ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH ) ) )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1098:9: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH ) ) ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH ) ) )*
            {
            dbg.location(1098,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1098:9: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH ) )
            int alt230=2;
            try { dbg.enterSubRule(230);
            try { dbg.enterDecision(230, decisionCanBacktrack[230]);

            int LA230_0 = input.LA(1);

            if ( (LA230_0==HASH_SYMBOL) ) {
                int LA230_1 = input.LA(2);

                if ( (LA230_1==LBRACE) && (synpred19_Css3())) {
                    alt230=1;
                }
                else if ( (LA230_1==IDENT||LA230_1==COLON||LA230_1==WS||(LA230_1>=MINUS && LA230_1<=DOT)||(LA230_1>=NL && LA230_1<=COMMENT)) ) {
                    alt230=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 230, 1, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }
            }
            else if ( (LA230_0==IDENT||LA230_0==MINUS||(LA230_0>=HASH && LA230_0<=DOT)) ) {
                alt230=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 230, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(230);}

            switch (alt230) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1099:13: ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var
                    {
                    dbg.location(1099,35);
                    pushFollow(FOLLOW_scss_interpolation_expression_var_in_scss_declaration_interpolation_expression6849);
                    scss_interpolation_expression_var();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1101:13: ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH )
                    {
                    dbg.location(1101,13);
                    if ( input.LA(1)==IDENT||(input.LA(1)>=MINUS && input.LA(1)<=DOT) ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        dbg.recognitionException(mse);
                        throw mse;
                    }


                    }
                    break;

            }
            } finally {dbg.exitSubRule(230);}

            dbg.location(1103,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1103:9: ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH ) ) )*
            try { dbg.enterSubRule(233);

            loop233:
            do {
                int alt233=2;
                try { dbg.enterDecision(233, decisionCanBacktrack[233]);

                try {
                    isCyclicDecision = true;
                    alt233 = dfa233.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(233);}

                switch (alt233) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1104:13: ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH ) )
            	    {
            	    dbg.location(1104,13);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1104:13: ( ws )?
            	    int alt231=2;
            	    try { dbg.enterSubRule(231);
            	    try { dbg.enterDecision(231, decisionCanBacktrack[231]);

            	    int LA231_0 = input.LA(1);

            	    if ( (LA231_0==WS||(LA231_0>=NL && LA231_0<=COMMENT)) ) {
            	        alt231=1;
            	    }
            	    } finally {dbg.exitDecision(231);}

            	    switch (alt231) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1104:13: ws
            	            {
            	            dbg.location(1104,13);
            	            pushFollow(FOLLOW_ws_in_scss_declaration_interpolation_expression6930);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(231);}

            	    dbg.location(1105,13);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1105:13: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH ) )
            	    int alt232=2;
            	    try { dbg.enterSubRule(232);
            	    try { dbg.enterDecision(232, decisionCanBacktrack[232]);

            	    int LA232_0 = input.LA(1);

            	    if ( (LA232_0==HASH_SYMBOL) ) {
            	        int LA232_1 = input.LA(2);

            	        if ( (LA232_1==LBRACE) && (synpred20_Css3())) {
            	            alt232=1;
            	        }
            	        else if ( (LA232_1==IDENT||LA232_1==COLON||LA232_1==WS||(LA232_1>=MINUS && LA232_1<=DOT)||(LA232_1>=NL && LA232_1<=COMMENT)) ) {
            	            alt232=2;
            	        }
            	        else {
            	            if (state.backtracking>0) {state.failed=true; return ;}
            	            NoViableAltException nvae =
            	                new NoViableAltException("", 232, 1, input);

            	            dbg.recognitionException(nvae);
            	            throw nvae;
            	        }
            	    }
            	    else if ( (LA232_0==IDENT||LA232_0==MINUS||(LA232_0>=HASH && LA232_0<=DOT)) ) {
            	        alt232=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 232, 0, input);

            	        dbg.recognitionException(nvae);
            	        throw nvae;
            	    }
            	    } finally {dbg.exitDecision(232);}

            	    switch (alt232) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1106:17: ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var
            	            {
            	            dbg.location(1106,39);
            	            pushFollow(FOLLOW_scss_interpolation_expression_var_in_scss_declaration_interpolation_expression6969);
            	            scss_interpolation_expression_var();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;
            	        case 2 :
            	            dbg.enterAlt(2);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1108:17: ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH )
            	            {
            	            dbg.location(1108,17);
            	            if ( input.LA(1)==IDENT||(input.LA(1)>=MINUS && input.LA(1)<=DOT) ) {
            	                input.consume();
            	                state.errorRecovery=false;state.failed=false;
            	            }
            	            else {
            	                if (state.backtracking>0) {state.failed=true; return ;}
            	                MismatchedSetException mse = new MismatchedSetException(null,input);
            	                dbg.recognitionException(mse);
            	                throw mse;
            	            }


            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(232);}


            	    }
            	    break;

            	default :
            	    break loop233;
                }
            } while (true);
            } finally {dbg.exitSubRule(233);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1112, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "scss_declaration_interpolation_expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "scss_declaration_interpolation_expression"


    // $ANTLR start "scss_declaration_property_value_interpolation_expression"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1114:1: scss_declaration_property_value_interpolation_expression : ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS ) ) ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS ) ) )* ;
    public final void scss_declaration_property_value_interpolation_expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "scss_declaration_property_value_interpolation_expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1114, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1115:5: ( ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS ) ) ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS ) ) )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1116:9: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS ) ) ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS ) ) )*
            {
            dbg.location(1116,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1116:9: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS ) )
            int alt234=2;
            try { dbg.enterSubRule(234);
            try { dbg.enterDecision(234, decisionCanBacktrack[234]);

            try {
                isCyclicDecision = true;
                alt234 = dfa234.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(234);}

            switch (alt234) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1117:13: ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var
                    {
                    dbg.location(1117,35);
                    pushFollow(FOLLOW_scss_interpolation_expression_var_in_scss_declaration_property_value_interpolation_expression7095);
                    scss_interpolation_expression_var();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1119:13: ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS )
                    {
                    dbg.location(1119,13);
                    if ( input.LA(1)==IDENT||input.LA(1)==SOLIDUS||(input.LA(1)>=MINUS && input.LA(1)<=DOT) ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        dbg.recognitionException(mse);
                        throw mse;
                    }


                    }
                    break;

            }
            } finally {dbg.exitSubRule(234);}

            dbg.location(1121,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1121:9: ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS ) ) )*
            try { dbg.enterSubRule(237);

            loop237:
            do {
                int alt237=2;
                try { dbg.enterDecision(237, decisionCanBacktrack[237]);

                int LA237_0 = input.LA(1);

                if ( (LA237_0==IDENT||LA237_0==WS||LA237_0==SOLIDUS||(LA237_0>=MINUS && LA237_0<=DOT)||(LA237_0>=NL && LA237_0<=COMMENT)) ) {
                    alt237=1;
                }


                } finally {dbg.exitDecision(237);}

                switch (alt237) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1122:13: ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS ) )
            	    {
            	    dbg.location(1122,13);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1122:13: ( ws )?
            	    int alt235=2;
            	    try { dbg.enterSubRule(235);
            	    try { dbg.enterDecision(235, decisionCanBacktrack[235]);

            	    int LA235_0 = input.LA(1);

            	    if ( (LA235_0==WS||(LA235_0>=NL && LA235_0<=COMMENT)) ) {
            	        alt235=1;
            	    }
            	    } finally {dbg.exitDecision(235);}

            	    switch (alt235) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1122:13: ws
            	            {
            	            dbg.location(1122,13);
            	            pushFollow(FOLLOW_ws_in_scss_declaration_property_value_interpolation_expression7180);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(235);}

            	    dbg.location(1123,13);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1123:13: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS ) )
            	    int alt236=2;
            	    try { dbg.enterSubRule(236);
            	    try { dbg.enterDecision(236, decisionCanBacktrack[236]);

            	    try {
            	        isCyclicDecision = true;
            	        alt236 = dfa236.predict(input);
            	    }
            	    catch (NoViableAltException nvae) {
            	        dbg.recognitionException(nvae);
            	        throw nvae;
            	    }
            	    } finally {dbg.exitDecision(236);}

            	    switch (alt236) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1124:17: ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var
            	            {
            	            dbg.location(1124,39);
            	            pushFollow(FOLLOW_scss_interpolation_expression_var_in_scss_declaration_property_value_interpolation_expression7219);
            	            scss_interpolation_expression_var();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;
            	        case 2 :
            	            dbg.enterAlt(2);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1126:17: ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS )
            	            {
            	            dbg.location(1126,17);
            	            if ( input.LA(1)==IDENT||input.LA(1)==SOLIDUS||(input.LA(1)>=MINUS && input.LA(1)<=DOT) ) {
            	                input.consume();
            	                state.errorRecovery=false;state.failed=false;
            	            }
            	            else {
            	                if (state.backtracking>0) {state.failed=true; return ;}
            	                MismatchedSetException mse = new MismatchedSetException(null,input);
            	                dbg.recognitionException(mse);
            	                throw mse;
            	            }


            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(236);}


            	    }
            	    break;

            	default :
            	    break loop237;
                }
            } while (true);
            } finally {dbg.exitSubRule(237);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1130, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "scss_declaration_property_value_interpolation_expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "scss_declaration_property_value_interpolation_expression"


    // $ANTLR start "scss_mq_interpolation_expression"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1132:1: scss_mq_interpolation_expression : ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) ) ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) ) )* ;
    public final void scss_mq_interpolation_expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "scss_mq_interpolation_expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1132, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1133:5: ( ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) ) ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) ) )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1134:9: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) ) ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) ) )*
            {
            dbg.location(1134,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1134:9: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) )
            int alt238=2;
            try { dbg.enterSubRule(238);
            try { dbg.enterDecision(238, decisionCanBacktrack[238]);

            try {
                isCyclicDecision = true;
                alt238 = dfa238.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(238);}

            switch (alt238) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1135:13: ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var
                    {
                    dbg.location(1135,35);
                    pushFollow(FOLLOW_scss_interpolation_expression_var_in_scss_mq_interpolation_expression7353);
                    scss_interpolation_expression_var();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1137:13: ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT )
                    {
                    dbg.location(1137,13);
                    if ( input.LA(1)==IDENT||input.LA(1)==AND||input.LA(1)==NOT||input.LA(1)==COLON||(input.LA(1)>=MINUS && input.LA(1)<=DOT) ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        dbg.recognitionException(mse);
                        throw mse;
                    }


                    }
                    break;

            }
            } finally {dbg.exitSubRule(238);}

            dbg.location(1139,9);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1139:9: ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) ) )*
            try { dbg.enterSubRule(241);

            loop241:
            do {
                int alt241=2;
                try { dbg.enterDecision(241, decisionCanBacktrack[241]);

                try {
                    isCyclicDecision = true;
                    alt241 = dfa241.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(241);}

                switch (alt241) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1140:13: ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) )
            	    {
            	    dbg.location(1140,13);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1140:13: ( ws )?
            	    int alt239=2;
            	    try { dbg.enterSubRule(239);
            	    try { dbg.enterDecision(239, decisionCanBacktrack[239]);

            	    int LA239_0 = input.LA(1);

            	    if ( (LA239_0==WS||(LA239_0>=NL && LA239_0<=COMMENT)) ) {
            	        alt239=1;
            	    }
            	    } finally {dbg.exitDecision(239);}

            	    switch (alt239) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1140:13: ws
            	            {
            	            dbg.location(1140,13);
            	            pushFollow(FOLLOW_ws_in_scss_mq_interpolation_expression7446);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(239);}

            	    dbg.location(1141,13);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1141:13: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) )
            	    int alt240=2;
            	    try { dbg.enterSubRule(240);
            	    try { dbg.enterDecision(240, decisionCanBacktrack[240]);

            	    try {
            	        isCyclicDecision = true;
            	        alt240 = dfa240.predict(input);
            	    }
            	    catch (NoViableAltException nvae) {
            	        dbg.recognitionException(nvae);
            	        throw nvae;
            	    }
            	    } finally {dbg.exitDecision(240);}

            	    switch (alt240) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1142:17: ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var
            	            {
            	            dbg.location(1142,39);
            	            pushFollow(FOLLOW_scss_interpolation_expression_var_in_scss_mq_interpolation_expression7485);
            	            scss_interpolation_expression_var();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;
            	        case 2 :
            	            dbg.enterAlt(2);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1144:17: ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT )
            	            {
            	            dbg.location(1144,17);
            	            if ( input.LA(1)==IDENT||input.LA(1)==AND||input.LA(1)==NOT||input.LA(1)==COLON||(input.LA(1)>=MINUS && input.LA(1)<=DOT) ) {
            	                input.consume();
            	                state.errorRecovery=false;state.failed=false;
            	            }
            	            else {
            	                if (state.backtracking>0) {state.failed=true; return ;}
            	                MismatchedSetException mse = new MismatchedSetException(null,input);
            	                dbg.recognitionException(mse);
            	                throw mse;
            	            }


            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(240);}


            	    }
            	    break;

            	default :
            	    break loop241;
                }
            } while (true);
            } finally {dbg.exitSubRule(241);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1148, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "scss_mq_interpolation_expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "scss_mq_interpolation_expression"


    // $ANTLR start "scss_interpolation_expression_var"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1150:1: scss_interpolation_expression_var : HASH_SYMBOL LBRACE ( ws )? ( cp_variable | less_function_in_condition ) ( ws )? RBRACE ;
    public final void scss_interpolation_expression_var() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "scss_interpolation_expression_var");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1150, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1151:5: ( HASH_SYMBOL LBRACE ( ws )? ( cp_variable | less_function_in_condition ) ( ws )? RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1152:9: HASH_SYMBOL LBRACE ( ws )? ( cp_variable | less_function_in_condition ) ( ws )? RBRACE
            {
            dbg.location(1152,9);
            match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_scss_interpolation_expression_var7606); if (state.failed) return ;
            dbg.location(1152,21);
            match(input,LBRACE,FOLLOW_LBRACE_in_scss_interpolation_expression_var7608); if (state.failed) return ;
            dbg.location(1152,28);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1152:28: ( ws )?
            int alt242=2;
            try { dbg.enterSubRule(242);
            try { dbg.enterDecision(242, decisionCanBacktrack[242]);

            int LA242_0 = input.LA(1);

            if ( (LA242_0==WS||(LA242_0>=NL && LA242_0<=COMMENT)) ) {
                alt242=1;
            }
            } finally {dbg.exitDecision(242);}

            switch (alt242) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1152:28: ws
                    {
                    dbg.location(1152,28);
                    pushFollow(FOLLOW_ws_in_scss_interpolation_expression_var7610);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(242);}

            dbg.location(1152,32);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1152:32: ( cp_variable | less_function_in_condition )
            int alt243=2;
            try { dbg.enterSubRule(243);
            try { dbg.enterDecision(243, decisionCanBacktrack[243]);

            int LA243_0 = input.LA(1);

            if ( (LA243_0==MEDIA_SYM||LA243_0==AT_IDENT||LA243_0==SASS_VAR) ) {
                alt243=1;
            }
            else if ( (LA243_0==IDENT) ) {
                alt243=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 243, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(243);}

            switch (alt243) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1152:34: cp_variable
                    {
                    dbg.location(1152,34);
                    pushFollow(FOLLOW_cp_variable_in_scss_interpolation_expression_var7615);
                    cp_variable();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1152:48: less_function_in_condition
                    {
                    dbg.location(1152,48);
                    pushFollow(FOLLOW_less_function_in_condition_in_scss_interpolation_expression_var7619);
                    less_function_in_condition();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(243);}

            dbg.location(1152,77);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1152:77: ( ws )?
            int alt244=2;
            try { dbg.enterSubRule(244);
            try { dbg.enterDecision(244, decisionCanBacktrack[244]);

            int LA244_0 = input.LA(1);

            if ( (LA244_0==WS||(LA244_0>=NL && LA244_0<=COMMENT)) ) {
                alt244=1;
            }
            } finally {dbg.exitDecision(244);}

            switch (alt244) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1152:77: ws
                    {
                    dbg.location(1152,77);
                    pushFollow(FOLLOW_ws_in_scss_interpolation_expression_var7623);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(244);}

            dbg.location(1152,81);
            match(input,RBRACE,FOLLOW_RBRACE_in_scss_interpolation_expression_var7626); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1153, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "scss_interpolation_expression_var");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "scss_interpolation_expression_var"


    // $ANTLR start "scss_nested_properties"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1173:1: scss_nested_properties : property COLON ( ws )? ( propertyValue )? LBRACE ( ws )? syncToFollow declarations RBRACE ;
    public final void scss_nested_properties() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "scss_nested_properties");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1173, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1174:5: ( property COLON ( ws )? ( propertyValue )? LBRACE ( ws )? syncToFollow declarations RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1175:5: property COLON ( ws )? ( propertyValue )? LBRACE ( ws )? syncToFollow declarations RBRACE
            {
            dbg.location(1175,5);
            pushFollow(FOLLOW_property_in_scss_nested_properties7670);
            property();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1175,14);
            match(input,COLON,FOLLOW_COLON_in_scss_nested_properties7672); if (state.failed) return ;
            dbg.location(1175,20);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1175:20: ( ws )?
            int alt245=2;
            try { dbg.enterSubRule(245);
            try { dbg.enterDecision(245, decisionCanBacktrack[245]);

            int LA245_0 = input.LA(1);

            if ( (LA245_0==WS||(LA245_0>=NL && LA245_0<=COMMENT)) ) {
                alt245=1;
            }
            } finally {dbg.exitDecision(245);}

            switch (alt245) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1175:20: ws
                    {
                    dbg.location(1175,20);
                    pushFollow(FOLLOW_ws_in_scss_nested_properties7674);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(245);}

            dbg.location(1175,24);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1175:24: ( propertyValue )?
            int alt246=2;
            try { dbg.enterSubRule(246);
            try { dbg.enterDecision(246, decisionCanBacktrack[246]);

            int LA246_0 = input.LA(1);

            if ( ((LA246_0>=IDENT && LA246_0<=URI)||LA246_0==MEDIA_SYM||(LA246_0>=GEN && LA246_0<=LPAREN)||LA246_0==AT_IDENT||LA246_0==PERCENTAGE||(LA246_0>=SOLIDUS && LA246_0<=PLUS)||(LA246_0>=MINUS && LA246_0<=DOT)||(LA246_0>=NUMBER && LA246_0<=DIMENSION)||LA246_0==SASS_VAR) ) {
                alt246=1;
            }
            } finally {dbg.exitDecision(246);}

            switch (alt246) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1175:24: propertyValue
                    {
                    dbg.location(1175,24);
                    pushFollow(FOLLOW_propertyValue_in_scss_nested_properties7677);
                    propertyValue();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(246);}

            dbg.location(1175,39);
            match(input,LBRACE,FOLLOW_LBRACE_in_scss_nested_properties7680); if (state.failed) return ;
            dbg.location(1175,46);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1175:46: ( ws )?
            int alt247=2;
            try { dbg.enterSubRule(247);
            try { dbg.enterDecision(247, decisionCanBacktrack[247]);

            int LA247_0 = input.LA(1);

            if ( (LA247_0==WS||(LA247_0>=NL && LA247_0<=COMMENT)) ) {
                alt247=1;
            }
            } finally {dbg.exitDecision(247);}

            switch (alt247) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1175:46: ws
                    {
                    dbg.location(1175,46);
                    pushFollow(FOLLOW_ws_in_scss_nested_properties7682);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(247);}

            dbg.location(1175,50);
            pushFollow(FOLLOW_syncToFollow_in_scss_nested_properties7685);
            syncToFollow();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1175,63);
            pushFollow(FOLLOW_declarations_in_scss_nested_properties7687);
            declarations();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1175,76);
            match(input,RBRACE,FOLLOW_RBRACE_in_scss_nested_properties7689); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1176, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "scss_nested_properties");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "scss_nested_properties"


    // $ANTLR start "sass_extend"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1178:1: sass_extend : SASS_EXTEND ws simpleSelectorSequence ( SASS_OPTIONAL ( ws )? )? SEMI ;
    public final void sass_extend() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sass_extend");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1178, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1179:5: ( SASS_EXTEND ws simpleSelectorSequence ( SASS_OPTIONAL ( ws )? )? SEMI )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1180:5: SASS_EXTEND ws simpleSelectorSequence ( SASS_OPTIONAL ( ws )? )? SEMI
            {
            dbg.location(1180,5);
            match(input,SASS_EXTEND,FOLLOW_SASS_EXTEND_in_sass_extend7710); if (state.failed) return ;
            dbg.location(1180,17);
            pushFollow(FOLLOW_ws_in_sass_extend7712);
            ws();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1180,20);
            pushFollow(FOLLOW_simpleSelectorSequence_in_sass_extend7714);
            simpleSelectorSequence();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1180,43);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1180:43: ( SASS_OPTIONAL ( ws )? )?
            int alt249=2;
            try { dbg.enterSubRule(249);
            try { dbg.enterDecision(249, decisionCanBacktrack[249]);

            int LA249_0 = input.LA(1);

            if ( (LA249_0==SASS_OPTIONAL) ) {
                alt249=1;
            }
            } finally {dbg.exitDecision(249);}

            switch (alt249) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1180:44: SASS_OPTIONAL ( ws )?
                    {
                    dbg.location(1180,44);
                    match(input,SASS_OPTIONAL,FOLLOW_SASS_OPTIONAL_in_sass_extend7717); if (state.failed) return ;
                    dbg.location(1180,58);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1180:58: ( ws )?
                    int alt248=2;
                    try { dbg.enterSubRule(248);
                    try { dbg.enterDecision(248, decisionCanBacktrack[248]);

                    int LA248_0 = input.LA(1);

                    if ( (LA248_0==WS||(LA248_0>=NL && LA248_0<=COMMENT)) ) {
                        alt248=1;
                    }
                    } finally {dbg.exitDecision(248);}

                    switch (alt248) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1180:58: ws
                            {
                            dbg.location(1180,58);
                            pushFollow(FOLLOW_ws_in_sass_extend7719);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(248);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(249);}

            dbg.location(1180,64);
            match(input,SEMI,FOLLOW_SEMI_in_sass_extend7724); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1181, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sass_extend");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sass_extend"


    // $ANTLR start "sass_extend_only_selector"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1183:1: sass_extend_only_selector : SASS_EXTEND_ONLY_SELECTOR ;
    public final void sass_extend_only_selector() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sass_extend_only_selector");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1183, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1184:5: ( SASS_EXTEND_ONLY_SELECTOR )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1185:5: SASS_EXTEND_ONLY_SELECTOR
            {
            dbg.location(1185,5);
            match(input,SASS_EXTEND_ONLY_SELECTOR,FOLLOW_SASS_EXTEND_ONLY_SELECTOR_in_sass_extend_only_selector7749); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1186, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sass_extend_only_selector");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sass_extend_only_selector"


    // $ANTLR start "sass_debug"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1188:1: sass_debug : ( SASS_DEBUG | SASS_WARN ) ws cp_expression SEMI ;
    public final void sass_debug() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sass_debug");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1188, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1189:5: ( ( SASS_DEBUG | SASS_WARN ) ws cp_expression SEMI )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1190:5: ( SASS_DEBUG | SASS_WARN ) ws cp_expression SEMI
            {
            dbg.location(1190,5);
            if ( (input.LA(1)>=SASS_DEBUG && input.LA(1)<=SASS_WARN) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }

            dbg.location(1190,32);
            pushFollow(FOLLOW_ws_in_sass_debug7780);
            ws();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1190,35);
            pushFollow(FOLLOW_cp_expression_in_sass_debug7782);
            cp_expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1190,49);
            match(input,SEMI,FOLLOW_SEMI_in_sass_debug7784); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1191, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sass_debug");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sass_debug"


    // $ANTLR start "sass_control"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1193:1: sass_control : ( sass_if | sass_for | sass_each | sass_while );
    public final void sass_control() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sass_control");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1193, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1194:5: ( sass_if | sass_for | sass_each | sass_while )
            int alt250=4;
            try { dbg.enterDecision(250, decisionCanBacktrack[250]);

            switch ( input.LA(1) ) {
            case SASS_IF:
                {
                alt250=1;
                }
                break;
            case SASS_FOR:
                {
                alt250=2;
                }
                break;
            case SASS_EACH:
                {
                alt250=3;
                }
                break;
            case SASS_WHILE:
                {
                alt250=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 250, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(250);}

            switch (alt250) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1195:5: sass_if
                    {
                    dbg.location(1195,5);
                    pushFollow(FOLLOW_sass_if_in_sass_control7809);
                    sass_if();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1195:15: sass_for
                    {
                    dbg.location(1195,15);
                    pushFollow(FOLLOW_sass_for_in_sass_control7813);
                    sass_for();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1195:26: sass_each
                    {
                    dbg.location(1195,26);
                    pushFollow(FOLLOW_sass_each_in_sass_control7817);
                    sass_each();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1195:38: sass_while
                    {
                    dbg.location(1195,38);
                    pushFollow(FOLLOW_sass_while_in_sass_control7821);
                    sass_while();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1196, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sass_control");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sass_control"


    // $ANTLR start "sass_if"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1198:1: sass_if : SASS_IF ws sass_control_expression sass_control_block ;
    public final void sass_if() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sass_if");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1198, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1199:5: ( SASS_IF ws sass_control_expression sass_control_block )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1200:5: SASS_IF ws sass_control_expression sass_control_block
            {
            dbg.location(1200,5);
            match(input,SASS_IF,FOLLOW_SASS_IF_in_sass_if7842); if (state.failed) return ;
            dbg.location(1200,13);
            pushFollow(FOLLOW_ws_in_sass_if7844);
            ws();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1200,16);
            pushFollow(FOLLOW_sass_control_expression_in_sass_if7846);
            sass_control_expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1200,40);
            pushFollow(FOLLOW_sass_control_block_in_sass_if7848);
            sass_control_block();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1201, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sass_if");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sass_if"


    // $ANTLR start "sass_control_expression"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1203:1: sass_control_expression : cp_expression ( ( CP_EQ | LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) ( ws )? cp_expression )? ;
    public final void sass_control_expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sass_control_expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1203, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1204:5: ( cp_expression ( ( CP_EQ | LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) ( ws )? cp_expression )? )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1205:5: cp_expression ( ( CP_EQ | LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) ( ws )? cp_expression )?
            {
            dbg.location(1205,5);
            pushFollow(FOLLOW_cp_expression_in_sass_control_expression7873);
            cp_expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1205,19);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1205:19: ( ( CP_EQ | LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) ( ws )? cp_expression )?
            int alt252=2;
            try { dbg.enterSubRule(252);
            try { dbg.enterDecision(252, decisionCanBacktrack[252]);

            int LA252_0 = input.LA(1);

            if ( (LA252_0==GREATER||(LA252_0>=GREATER_OR_EQ && LA252_0<=LESS_OR_EQ)||LA252_0==CP_EQ) ) {
                alt252=1;
            }
            } finally {dbg.exitDecision(252);}

            switch (alt252) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1205:20: ( CP_EQ | LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) ( ws )? cp_expression
                    {
                    dbg.location(1205,20);
                    if ( input.LA(1)==GREATER||(input.LA(1)>=GREATER_OR_EQ && input.LA(1)<=LESS_OR_EQ)||input.LA(1)==CP_EQ ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        dbg.recognitionException(mse);
                        throw mse;
                    }

                    dbg.location(1205,75);
                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1205:75: ( ws )?
                    int alt251=2;
                    try { dbg.enterSubRule(251);
                    try { dbg.enterDecision(251, decisionCanBacktrack[251]);

                    int LA251_0 = input.LA(1);

                    if ( (LA251_0==WS||(LA251_0>=NL && LA251_0<=COMMENT)) ) {
                        alt251=1;
                    }
                    } finally {dbg.exitDecision(251);}

                    switch (alt251) {
                        case 1 :
                            dbg.enterAlt(1);

                            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1205:75: ws
                            {
                            dbg.location(1205,75);
                            pushFollow(FOLLOW_ws_in_sass_control_expression7897);
                            ws();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(251);}

                    dbg.location(1205,79);
                    pushFollow(FOLLOW_cp_expression_in_sass_control_expression7900);
                    cp_expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(252);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1206, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sass_control_expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sass_control_expression"


    // $ANTLR start "sass_for"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1208:1: sass_for : SASS_FOR ws cp_variable ws IDENT ws cp_term IDENT ws cp_term sass_control_block ;
    public final void sass_for() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sass_for");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1208, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1209:5: ( SASS_FOR ws cp_variable ws IDENT ws cp_term IDENT ws cp_term sass_control_block )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1210:5: SASS_FOR ws cp_variable ws IDENT ws cp_term IDENT ws cp_term sass_control_block
            {
            dbg.location(1210,5);
            match(input,SASS_FOR,FOLLOW_SASS_FOR_in_sass_for7923); if (state.failed) return ;
            dbg.location(1210,14);
            pushFollow(FOLLOW_ws_in_sass_for7925);
            ws();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1210,17);
            pushFollow(FOLLOW_cp_variable_in_sass_for7927);
            cp_variable();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1210,29);
            pushFollow(FOLLOW_ws_in_sass_for7929);
            ws();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1210,32);
            match(input,IDENT,FOLLOW_IDENT_in_sass_for7931); if (state.failed) return ;
            dbg.location(1210,47);
            pushFollow(FOLLOW_ws_in_sass_for7935);
            ws();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1210,50);
            pushFollow(FOLLOW_cp_term_in_sass_for7937);
            cp_term();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1210,58);
            match(input,IDENT,FOLLOW_IDENT_in_sass_for7939); if (state.failed) return ;
            dbg.location(1210,71);
            pushFollow(FOLLOW_ws_in_sass_for7943);
            ws();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1210,74);
            pushFollow(FOLLOW_cp_term_in_sass_for7945);
            cp_term();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1210,82);
            pushFollow(FOLLOW_sass_control_block_in_sass_for7947);
            sass_control_block();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1211, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sass_for");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sass_for"


    // $ANTLR start "sass_each"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1213:1: sass_each : SASS_EACH ws cp_variable ws IDENT ws sass_each_list sass_control_block ;
    public final void sass_each() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sass_each");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1213, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1214:5: ( SASS_EACH ws cp_variable ws IDENT ws sass_each_list sass_control_block )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1215:5: SASS_EACH ws cp_variable ws IDENT ws sass_each_list sass_control_block
            {
            dbg.location(1215,5);
            match(input,SASS_EACH,FOLLOW_SASS_EACH_in_sass_each7968); if (state.failed) return ;
            dbg.location(1215,15);
            pushFollow(FOLLOW_ws_in_sass_each7970);
            ws();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1215,18);
            pushFollow(FOLLOW_cp_variable_in_sass_each7972);
            cp_variable();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1215,30);
            pushFollow(FOLLOW_ws_in_sass_each7974);
            ws();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1215,33);
            match(input,IDENT,FOLLOW_IDENT_in_sass_each7976); if (state.failed) return ;
            dbg.location(1215,46);
            pushFollow(FOLLOW_ws_in_sass_each7980);
            ws();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1215,49);
            pushFollow(FOLLOW_sass_each_list_in_sass_each7982);
            sass_each_list();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1215,64);
            pushFollow(FOLLOW_sass_control_block_in_sass_each7984);
            sass_control_block();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1216, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sass_each");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sass_each"


    // $ANTLR start "sass_each_list"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1218:1: sass_each_list : cp_term ( COMMA ( ws )? cp_term )* ;
    public final void sass_each_list() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sass_each_list");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1218, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1219:5: ( cp_term ( COMMA ( ws )? cp_term )* )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1220:5: cp_term ( COMMA ( ws )? cp_term )*
            {
            dbg.location(1220,5);
            pushFollow(FOLLOW_cp_term_in_sass_each_list8009);
            cp_term();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1220,13);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1220:13: ( COMMA ( ws )? cp_term )*
            try { dbg.enterSubRule(254);

            loop254:
            do {
                int alt254=2;
                try { dbg.enterDecision(254, decisionCanBacktrack[254]);

                int LA254_0 = input.LA(1);

                if ( (LA254_0==COMMA) ) {
                    alt254=1;
                }


                } finally {dbg.exitDecision(254);}

                switch (alt254) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1220:14: COMMA ( ws )? cp_term
            	    {
            	    dbg.location(1220,14);
            	    match(input,COMMA,FOLLOW_COMMA_in_sass_each_list8012); if (state.failed) return ;
            	    dbg.location(1220,20);
            	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1220:20: ( ws )?
            	    int alt253=2;
            	    try { dbg.enterSubRule(253);
            	    try { dbg.enterDecision(253, decisionCanBacktrack[253]);

            	    int LA253_0 = input.LA(1);

            	    if ( (LA253_0==WS||(LA253_0>=NL && LA253_0<=COMMENT)) ) {
            	        alt253=1;
            	    }
            	    } finally {dbg.exitDecision(253);}

            	    switch (alt253) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1220:20: ws
            	            {
            	            dbg.location(1220,20);
            	            pushFollow(FOLLOW_ws_in_sass_each_list8014);
            	            ws();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(253);}

            	    dbg.location(1220,24);
            	    pushFollow(FOLLOW_cp_term_in_sass_each_list8017);
            	    cp_term();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop254;
                }
            } while (true);
            } finally {dbg.exitSubRule(254);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1221, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sass_each_list");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sass_each_list"


    // $ANTLR start "sass_while"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1223:1: sass_while : SASS_WHILE ws sass_control_expression sass_control_block ;
    public final void sass_while() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sass_while");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1223, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1224:5: ( SASS_WHILE ws sass_control_expression sass_control_block )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1225:5: SASS_WHILE ws sass_control_expression sass_control_block
            {
            dbg.location(1225,5);
            match(input,SASS_WHILE,FOLLOW_SASS_WHILE_in_sass_while8044); if (state.failed) return ;
            dbg.location(1225,16);
            pushFollow(FOLLOW_ws_in_sass_while8046);
            ws();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1225,19);
            pushFollow(FOLLOW_sass_control_expression_in_sass_while8048);
            sass_control_expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1225,43);
            pushFollow(FOLLOW_sass_control_block_in_sass_while8050);
            sass_control_block();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1226, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sass_while");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sass_while"


    // $ANTLR start "sass_control_block"
    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1228:1: sass_control_block : LBRACE ( ws )? declarations RBRACE ;
    public final void sass_control_block() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sass_control_block");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1228, 1);

        try {
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1229:5: ( LBRACE ( ws )? declarations RBRACE )
            dbg.enterAlt(1);

            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1230:5: LBRACE ( ws )? declarations RBRACE
            {
            dbg.location(1230,5);
            match(input,LBRACE,FOLLOW_LBRACE_in_sass_control_block8071); if (state.failed) return ;
            dbg.location(1230,12);
            // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1230:12: ( ws )?
            int alt255=2;
            try { dbg.enterSubRule(255);
            try { dbg.enterDecision(255, decisionCanBacktrack[255]);

            int LA255_0 = input.LA(1);

            if ( (LA255_0==WS||(LA255_0>=NL && LA255_0<=COMMENT)) ) {
                alt255=1;
            }
            } finally {dbg.exitDecision(255);}

            switch (alt255) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1230:12: ws
                    {
                    dbg.location(1230,12);
                    pushFollow(FOLLOW_ws_in_sass_control_block8073);
                    ws();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(255);}

            dbg.location(1230,16);
            pushFollow(FOLLOW_declarations_in_sass_control_block8076);
            declarations();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(1230,29);
            match(input,RBRACE,FOLLOW_RBRACE_in_sass_control_block8078); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1231, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sass_control_block");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sass_control_block"

    // $ANTLR start synpred1_Css3
    public final void synpred1_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:368:13: ( (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:368:15: (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE
        {
        dbg.location(368,15);
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:368:15: (~ ( HASH_SYMBOL | LBRACE ) )*
        try { dbg.enterSubRule(256);

        loop256:
        do {
            int alt256=2;
            try { dbg.enterDecision(256, decisionCanBacktrack[256]);

            int LA256_0 = input.LA(1);

            if ( ((LA256_0>=NAMESPACE_SYM && LA256_0<=MEDIA_SYM)||(LA256_0>=RBRACE && LA256_0<=MINUS)||(LA256_0>=HASH && LA256_0<=LINE_COMMENT)) ) {
                alt256=1;
            }


            } finally {dbg.exitDecision(256);}

            switch (alt256) {
        	case 1 :
        	    dbg.enterAlt(1);

        	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:368:15: ~ ( HASH_SYMBOL | LBRACE )
        	    {
        	    dbg.location(368,15);
        	    if ( (input.LA(1)>=NAMESPACE_SYM && input.LA(1)<=MEDIA_SYM)||(input.LA(1)>=RBRACE && input.LA(1)<=MINUS)||(input.LA(1)>=HASH && input.LA(1)<=LINE_COMMENT) ) {
        	        input.consume();
        	        state.errorRecovery=false;state.failed=false;
        	    }
        	    else {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        MismatchedSetException mse = new MismatchedSetException(null,input);
        	        dbg.recognitionException(mse);
        	        throw mse;
        	    }


        	    }
        	    break;

        	default :
        	    break loop256;
            }
        } while (true);
        } finally {dbg.exitSubRule(256);}

        dbg.location(368,42);
        match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_synpred1_Css3487); if (state.failed) return ;
        dbg.location(368,54);
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred1_Css3489); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Css3

    // $ANTLR start synpred2_Css3
    public final void synpred2_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:370:13: ( mediaQueryList )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:370:14: mediaQueryList
        {
        dbg.location(370,14);
        pushFollow(FOLLOW_mediaQueryList_in_synpred2_Css3526);
        mediaQueryList();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_Css3

    // $ANTLR start synpred3_Css3
    public final void synpred3_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:376:17: ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )+ COLON (~ ( SEMI | LBRACE | RBRACE ) )+ SEMI | scss_declaration_interpolation_expression COLON )
        int alt259=2;
        try { dbg.enterDecision(259, decisionCanBacktrack[259]);

        try {
            isCyclicDecision = true;
            alt259 = dfa259.predict(input);
        }
        catch (NoViableAltException nvae) {
            dbg.recognitionException(nvae);
            throw nvae;
        }
        } finally {dbg.exitDecision(259);}

        switch (alt259) {
            case 1 :
                dbg.enterAlt(1);

                // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:376:18: (~ ( LBRACE | SEMI | RBRACE | COLON ) )+ COLON (~ ( SEMI | LBRACE | RBRACE ) )+ SEMI
                {
                dbg.location(376,18);
                // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:376:18: (~ ( LBRACE | SEMI | RBRACE | COLON ) )+
                int cnt257=0;
                try { dbg.enterSubRule(257);

                loop257:
                do {
                    int alt257=2;
                    try { dbg.enterDecision(257, decisionCanBacktrack[257]);

                    int LA257_0 = input.LA(1);

                    if ( (LA257_0==NAMESPACE_SYM||(LA257_0>=IDENT && LA257_0<=MEDIA_SYM)||(LA257_0>=AND && LA257_0<=LPAREN)||(LA257_0>=RPAREN && LA257_0<=LINE_COMMENT)) ) {
                        alt257=1;
                    }


                    } finally {dbg.exitDecision(257);}

                    switch (alt257) {
                	case 1 :
                	    dbg.enterAlt(1);

                	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:376:18: ~ ( LBRACE | SEMI | RBRACE | COLON )
                	    {
                	    dbg.location(376,18);
                	    if ( input.LA(1)==NAMESPACE_SYM||(input.LA(1)>=IDENT && input.LA(1)<=MEDIA_SYM)||(input.LA(1)>=AND && input.LA(1)<=LPAREN)||(input.LA(1)>=RPAREN && input.LA(1)<=LINE_COMMENT) ) {
                	        input.consume();
                	        state.errorRecovery=false;state.failed=false;
                	    }
                	    else {
                	        if (state.backtracking>0) {state.failed=true; return ;}
                	        MismatchedSetException mse = new MismatchedSetException(null,input);
                	        dbg.recognitionException(mse);
                	        throw mse;
                	    }


                	    }
                	    break;

                	default :
                	    if ( cnt257 >= 1 ) break loop257;
                	    if (state.backtracking>0) {state.failed=true; return ;}
                            EarlyExitException eee =
                                new EarlyExitException(257, input);
                            dbg.recognitionException(eee);

                            throw eee;
                    }
                    cnt257++;
                } while (true);
                } finally {dbg.exitSubRule(257);}

                dbg.location(376,47);
                match(input,COLON,FOLLOW_COLON_in_synpred3_Css3624); if (state.failed) return ;
                dbg.location(376,53);
                // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:376:53: (~ ( SEMI | LBRACE | RBRACE ) )+
                int cnt258=0;
                try { dbg.enterSubRule(258);

                loop258:
                do {
                    int alt258=2;
                    try { dbg.enterDecision(258, decisionCanBacktrack[258]);

                    int LA258_0 = input.LA(1);

                    if ( (LA258_0==NAMESPACE_SYM||(LA258_0>=IDENT && LA258_0<=MEDIA_SYM)||(LA258_0>=AND && LA258_0<=LINE_COMMENT)) ) {
                        alt258=1;
                    }


                    } finally {dbg.exitDecision(258);}

                    switch (alt258) {
                	case 1 :
                	    dbg.enterAlt(1);

                	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:376:53: ~ ( SEMI | LBRACE | RBRACE )
                	    {
                	    dbg.location(376,53);
                	    if ( input.LA(1)==NAMESPACE_SYM||(input.LA(1)>=IDENT && input.LA(1)<=MEDIA_SYM)||(input.LA(1)>=AND && input.LA(1)<=LINE_COMMENT) ) {
                	        input.consume();
                	        state.errorRecovery=false;state.failed=false;
                	    }
                	    else {
                	        if (state.backtracking>0) {state.failed=true; return ;}
                	        MismatchedSetException mse = new MismatchedSetException(null,input);
                	        dbg.recognitionException(mse);
                	        throw mse;
                	    }


                	    }
                	    break;

                	default :
                	    if ( cnt258 >= 1 ) break loop258;
                	    if (state.backtracking>0) {state.failed=true; return ;}
                            EarlyExitException eee =
                                new EarlyExitException(258, input);
                            dbg.recognitionException(eee);

                            throw eee;
                    }
                    cnt258++;
                } while (true);
                } finally {dbg.exitSubRule(258);}

                dbg.location(376,76);
                match(input,SEMI,FOLLOW_SEMI_in_synpred3_Css3636); if (state.failed) return ;

                }
                break;
            case 2 :
                dbg.enterAlt(2);

                // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:376:83: scss_declaration_interpolation_expression COLON
                {
                dbg.location(376,83);
                pushFollow(FOLLOW_scss_declaration_interpolation_expression_in_synpred3_Css3640);
                scss_declaration_interpolation_expression();

                state._fsp--;
                if (state.failed) return ;
                dbg.location(376,125);
                match(input,COLON,FOLLOW_COLON_in_synpred3_Css3642); if (state.failed) return ;

                }
                break;

        }}
    // $ANTLR end synpred3_Css3

    // $ANTLR start synpred4_Css3
    public final void synpred4_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:564:9: ( (~ ( HASH_SYMBOL | COLON ) )* HASH_SYMBOL LBRACE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:564:10: (~ ( HASH_SYMBOL | COLON ) )* HASH_SYMBOL LBRACE
        {
        dbg.location(564,10);
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:564:10: (~ ( HASH_SYMBOL | COLON ) )*
        try { dbg.enterSubRule(260);

        loop260:
        do {
            int alt260=2;
            try { dbg.enterDecision(260, decisionCanBacktrack[260]);

            int LA260_0 = input.LA(1);

            if ( ((LA260_0>=NAMESPACE_SYM && LA260_0<=LPAREN)||(LA260_0>=RPAREN && LA260_0<=MINUS)||(LA260_0>=HASH && LA260_0<=LINE_COMMENT)) ) {
                alt260=1;
            }


            } finally {dbg.exitDecision(260);}

            switch (alt260) {
        	case 1 :
        	    dbg.enterAlt(1);

        	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:564:10: ~ ( HASH_SYMBOL | COLON )
        	    {
        	    dbg.location(564,10);
        	    if ( (input.LA(1)>=NAMESPACE_SYM && input.LA(1)<=LPAREN)||(input.LA(1)>=RPAREN && input.LA(1)<=MINUS)||(input.LA(1)>=HASH && input.LA(1)<=LINE_COMMENT) ) {
        	        input.consume();
        	        state.errorRecovery=false;state.failed=false;
        	    }
        	    else {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        MismatchedSetException mse = new MismatchedSetException(null,input);
        	        dbg.recognitionException(mse);
        	        throw mse;
        	    }


        	    }
        	    break;

        	default :
        	    break loop260;
            }
        } while (true);
        } finally {dbg.exitSubRule(260);}

        dbg.location(564,32);
        match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_synpred4_Css32251); if (state.failed) return ;
        dbg.location(564,44);
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred4_Css32253); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_Css3

    // $ANTLR start synpred5_Css3
    public final void synpred5_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:599:3: ( declaration SEMI )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:599:4: declaration SEMI
        {
        dbg.location(599,4);
        pushFollow(FOLLOW_declaration_in_synpred5_Css32539);
        declaration();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(599,16);
        match(input,SEMI,FOLLOW_SEMI_in_synpred5_Css32541); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_Css3

    // $ANTLR start synpred6_Css3
    public final void synpred6_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:603:3: ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )* COLON (~ ( SEMI | LBRACE | RBRACE ) )* SEMI )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:603:4: (~ ( LBRACE | SEMI | RBRACE | COLON ) )* COLON (~ ( SEMI | LBRACE | RBRACE ) )* SEMI
        {
        dbg.location(603,4);
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:603:4: (~ ( LBRACE | SEMI | RBRACE | COLON ) )*
        try { dbg.enterSubRule(261);

        loop261:
        do {
            int alt261=2;
            try { dbg.enterDecision(261, decisionCanBacktrack[261]);

            int LA261_0 = input.LA(1);

            if ( (LA261_0==NAMESPACE_SYM||(LA261_0>=IDENT && LA261_0<=MEDIA_SYM)||(LA261_0>=AND && LA261_0<=LPAREN)||(LA261_0>=RPAREN && LA261_0<=LINE_COMMENT)) ) {
                alt261=1;
            }


            } finally {dbg.exitDecision(261);}

            switch (alt261) {
        	case 1 :
        	    dbg.enterAlt(1);

        	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:603:4: ~ ( LBRACE | SEMI | RBRACE | COLON )
        	    {
        	    dbg.location(603,4);
        	    if ( input.LA(1)==NAMESPACE_SYM||(input.LA(1)>=IDENT && input.LA(1)<=MEDIA_SYM)||(input.LA(1)>=AND && input.LA(1)<=LPAREN)||(input.LA(1)>=RPAREN && input.LA(1)<=LINE_COMMENT) ) {
        	        input.consume();
        	        state.errorRecovery=false;state.failed=false;
        	    }
        	    else {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        MismatchedSetException mse = new MismatchedSetException(null,input);
        	        dbg.recognitionException(mse);
        	        throw mse;
        	    }


        	    }
        	    break;

        	default :
        	    break loop261;
            }
        } while (true);
        } finally {dbg.exitSubRule(261);}

        dbg.location(603,33);
        match(input,COLON,FOLLOW_COLON_in_synpred6_Css32618); if (state.failed) return ;
        dbg.location(603,39);
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:603:39: (~ ( SEMI | LBRACE | RBRACE ) )*
        try { dbg.enterSubRule(262);

        loop262:
        do {
            int alt262=2;
            try { dbg.enterDecision(262, decisionCanBacktrack[262]);

            int LA262_0 = input.LA(1);

            if ( (LA262_0==NAMESPACE_SYM||(LA262_0>=IDENT && LA262_0<=MEDIA_SYM)||(LA262_0>=AND && LA262_0<=LINE_COMMENT)) ) {
                alt262=1;
            }


            } finally {dbg.exitDecision(262);}

            switch (alt262) {
        	case 1 :
        	    dbg.enterAlt(1);

        	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:603:39: ~ ( SEMI | LBRACE | RBRACE )
        	    {
        	    dbg.location(603,39);
        	    if ( input.LA(1)==NAMESPACE_SYM||(input.LA(1)>=IDENT && input.LA(1)<=MEDIA_SYM)||(input.LA(1)>=AND && input.LA(1)<=LINE_COMMENT) ) {
        	        input.consume();
        	        state.errorRecovery=false;state.failed=false;
        	    }
        	    else {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        MismatchedSetException mse = new MismatchedSetException(null,input);
        	        dbg.recognitionException(mse);
        	        throw mse;
        	    }


        	    }
        	    break;

        	default :
        	    break loop262;
            }
        } while (true);
        } finally {dbg.exitSubRule(262);}

        dbg.location(603,62);
        match(input,SEMI,FOLLOW_SEMI_in_synpred6_Css32630); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_Css3

    // $ANTLR start synpred7_Css3
    public final void synpred7_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:605:3: ( scss_nested_properties )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:605:4: scss_nested_properties
        {
        dbg.location(605,4);
        pushFollow(FOLLOW_scss_nested_properties_in_synpred7_Css32647);
        scss_nested_properties();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_Css3

    // $ANTLR start synpred8_Css3
    public final void synpred8_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:607:17: ( rule )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:607:18: rule
        {
        dbg.location(607,18);
        pushFollow(FOLLOW_rule_in_synpred8_Css32676);
        rule();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_Css3

    // $ANTLR start synpred9_Css3
    public final void synpred9_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:619:17: ( (~ SEMI )* SEMI )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:619:18: (~ SEMI )* SEMI
        {
        dbg.location(619,18);
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:619:18: (~ SEMI )*
        try { dbg.enterSubRule(263);

        loop263:
        do {
            int alt263=2;
            try { dbg.enterDecision(263, decisionCanBacktrack[263]);

            int LA263_0 = input.LA(1);

            if ( (LA263_0==NAMESPACE_SYM||(LA263_0>=IDENT && LA263_0<=LINE_COMMENT)) ) {
                alt263=1;
            }


            } finally {dbg.exitDecision(263);}

            switch (alt263) {
        	case 1 :
        	    dbg.enterAlt(1);

        	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:619:18: ~ SEMI
        	    {
        	    dbg.location(619,18);
        	    if ( input.LA(1)==NAMESPACE_SYM||(input.LA(1)>=IDENT && input.LA(1)<=LINE_COMMENT) ) {
        	        input.consume();
        	        state.errorRecovery=false;state.failed=false;
        	    }
        	    else {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        MismatchedSetException mse = new MismatchedSetException(null,input);
        	        dbg.recognitionException(mse);
        	        throw mse;
        	    }


        	    }
        	    break;

        	default :
        	    break loop263;
            }
        } while (true);
        } finally {dbg.exitSubRule(263);}

        dbg.location(619,25);
        match(input,SEMI,FOLLOW_SEMI_in_synpred9_Css32928); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Css3

    // $ANTLR start synpred10_Css3
    public final void synpred10_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:627:9: ( (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:627:11: (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE
        {
        dbg.location(627,11);
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:627:11: (~ ( HASH_SYMBOL | LBRACE ) )*
        try { dbg.enterSubRule(264);

        loop264:
        do {
            int alt264=2;
            try { dbg.enterDecision(264, decisionCanBacktrack[264]);

            int LA264_0 = input.LA(1);

            if ( ((LA264_0>=NAMESPACE_SYM && LA264_0<=MEDIA_SYM)||(LA264_0>=RBRACE && LA264_0<=MINUS)||(LA264_0>=HASH && LA264_0<=LINE_COMMENT)) ) {
                alt264=1;
            }


            } finally {dbg.exitDecision(264);}

            switch (alt264) {
        	case 1 :
        	    dbg.enterAlt(1);

        	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:627:11: ~ ( HASH_SYMBOL | LBRACE )
        	    {
        	    dbg.location(627,11);
        	    if ( (input.LA(1)>=NAMESPACE_SYM && input.LA(1)<=MEDIA_SYM)||(input.LA(1)>=RBRACE && input.LA(1)<=MINUS)||(input.LA(1)>=HASH && input.LA(1)<=LINE_COMMENT) ) {
        	        input.consume();
        	        state.errorRecovery=false;state.failed=false;
        	    }
        	    else {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        MismatchedSetException mse = new MismatchedSetException(null,input);
        	        dbg.recognitionException(mse);
        	        throw mse;
        	    }


        	    }
        	    break;

        	default :
        	    break loop264;
            }
        } while (true);
        } finally {dbg.exitSubRule(264);}

        dbg.location(627,38);
        match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_synpred10_Css33015); if (state.failed) return ;
        dbg.location(627,50);
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred10_Css33017); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_Css3

    // $ANTLR start synpred11_Css3
    public final void synpred11_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:640:18: ( esPred )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:640:19: esPred
        {
        dbg.location(640,19);
        pushFollow(FOLLOW_esPred_in_synpred11_Css33115);
        esPred();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_Css3

    // $ANTLR start synpred12_Css3
    public final void synpred12_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:642:5: ( esPred )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:642:6: esPred
        {
        dbg.location(642,6);
        pushFollow(FOLLOW_esPred_in_synpred12_Css33136);
        esPred();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_Css3

    // $ANTLR start synpred13_Css3
    public final void synpred13_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:656:7: ( ( IDENT | STAR )? PIPE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:656:8: ( IDENT | STAR )? PIPE
        {
        dbg.location(656,8);
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:656:8: ( IDENT | STAR )?
        int alt265=2;
        try { dbg.enterSubRule(265);
        try { dbg.enterDecision(265, decisionCanBacktrack[265]);

        int LA265_0 = input.LA(1);

        if ( (LA265_0==IDENT||LA265_0==STAR) ) {
            alt265=1;
        }
        } finally {dbg.exitDecision(265);}

        switch (alt265) {
            case 1 :
                dbg.enterAlt(1);

                // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:
                {
                dbg.location(656,8);
                if ( input.LA(1)==IDENT||input.LA(1)==STAR ) {
                    input.consume();
                    state.errorRecovery=false;state.failed=false;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    MismatchedSetException mse = new MismatchedSetException(null,input);
                    dbg.recognitionException(mse);
                    throw mse;
                }


                }
                break;

        }
        } finally {dbg.exitSubRule(265);}

        dbg.location(656,24);
        match(input,PIPE,FOLLOW_PIPE_in_synpred13_Css33254); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_Css3

    // $ANTLR start synpred14_Css3
    public final void synpred14_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:765:9: ( (~ ( HASH_SYMBOL | SEMI ) )* HASH_SYMBOL LBRACE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:765:10: (~ ( HASH_SYMBOL | SEMI ) )* HASH_SYMBOL LBRACE
        {
        dbg.location(765,10);
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:765:10: (~ ( HASH_SYMBOL | SEMI ) )*
        try { dbg.enterSubRule(266);

        loop266:
        do {
            int alt266=2;
            try { dbg.enterDecision(266, decisionCanBacktrack[266]);

            int LA266_0 = input.LA(1);

            if ( (LA266_0==NAMESPACE_SYM||(LA266_0>=IDENT && LA266_0<=MINUS)||(LA266_0>=HASH && LA266_0<=LINE_COMMENT)) ) {
                alt266=1;
            }


            } finally {dbg.exitDecision(266);}

            switch (alt266) {
        	case 1 :
        	    dbg.enterAlt(1);

        	    // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:765:10: ~ ( HASH_SYMBOL | SEMI )
        	    {
        	    dbg.location(765,10);
        	    if ( input.LA(1)==NAMESPACE_SYM||(input.LA(1)>=IDENT && input.LA(1)<=MINUS)||(input.LA(1)>=HASH && input.LA(1)<=LINE_COMMENT) ) {
        	        input.consume();
        	        state.errorRecovery=false;state.failed=false;
        	    }
        	    else {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        MismatchedSetException mse = new MismatchedSetException(null,input);
        	        dbg.recognitionException(mse);
        	        throw mse;
        	    }


        	    }
        	    break;

        	default :
        	    break loop266;
            }
        } while (true);
        } finally {dbg.exitSubRule(266);}

        dbg.location(765,31);
        match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_synpred14_Css34258); if (state.failed) return ;
        dbg.location(765,43);
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred14_Css34260); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_Css3

    // $ANTLR start synpred15_Css3
    public final void synpred15_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:766:11: ( expressionPredicate )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:766:12: expressionPredicate
        {
        dbg.location(766,12);
        pushFollow(FOLLOW_expressionPredicate_in_synpred15_Css34276);
        expressionPredicate();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_Css3

    // $ANTLR start synpred16_Css3
    public final void synpred16_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:938:16: ( term )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:938:17: term
        {
        dbg.location(938,17);
        pushFollow(FOLLOW_term_in_synpred16_Css35513);
        term();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_Css3

    // $ANTLR start synpred17_Css3
    public final void synpred17_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1081:13: ( HASH_SYMBOL LBRACE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1081:14: HASH_SYMBOL LBRACE
        {
        dbg.location(1081,14);
        match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_synpred17_Css36586); if (state.failed) return ;
        dbg.location(1081,26);
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred17_Css36588); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred17_Css3

    // $ANTLR start synpred18_Css3
    public final void synpred18_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1088:17: ( HASH_SYMBOL LBRACE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1088:18: HASH_SYMBOL LBRACE
        {
        dbg.location(1088,18);
        match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_synpred18_Css36710); if (state.failed) return ;
        dbg.location(1088,30);
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred18_Css36712); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_Css3

    // $ANTLR start synpred19_Css3
    public final void synpred19_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1099:13: ( HASH_SYMBOL LBRACE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1099:14: HASH_SYMBOL LBRACE
        {
        dbg.location(1099,14);
        match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_synpred19_Css36844); if (state.failed) return ;
        dbg.location(1099,26);
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred19_Css36846); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_Css3

    // $ANTLR start synpred20_Css3
    public final void synpred20_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1106:17: ( HASH_SYMBOL LBRACE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1106:18: HASH_SYMBOL LBRACE
        {
        dbg.location(1106,18);
        match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_synpred20_Css36964); if (state.failed) return ;
        dbg.location(1106,30);
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred20_Css36966); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_Css3

    // $ANTLR start synpred21_Css3
    public final void synpred21_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1117:13: ( HASH_SYMBOL LBRACE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1117:14: HASH_SYMBOL LBRACE
        {
        dbg.location(1117,14);
        match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_synpred21_Css37090); if (state.failed) return ;
        dbg.location(1117,26);
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred21_Css37092); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_Css3

    // $ANTLR start synpred22_Css3
    public final void synpred22_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1124:17: ( HASH_SYMBOL LBRACE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1124:18: HASH_SYMBOL LBRACE
        {
        dbg.location(1124,18);
        match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_synpred22_Css37214); if (state.failed) return ;
        dbg.location(1124,30);
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred22_Css37216); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred22_Css3

    // $ANTLR start synpred23_Css3
    public final void synpred23_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1135:13: ( HASH_SYMBOL LBRACE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1135:14: HASH_SYMBOL LBRACE
        {
        dbg.location(1135,14);
        match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_synpred23_Css37348); if (state.failed) return ;
        dbg.location(1135,26);
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred23_Css37350); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred23_Css3

    // $ANTLR start synpred24_Css3
    public final void synpred24_Css3_fragment() throws RecognitionException {   
        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1142:17: ( HASH_SYMBOL LBRACE )
        dbg.enterAlt(1);

        // /Volumes/Mercurial/web-main/css.lib/src/org/netbeans/modules/css/lib/Css3.g:1142:18: HASH_SYMBOL LBRACE
        {
        dbg.location(1142,18);
        match(input,HASH_SYMBOL,FOLLOW_HASH_SYMBOL_in_synpred24_Css37480); if (state.failed) return ;
        dbg.location(1142,30);
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred24_Css37482); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred24_Css3

    // Delegated rules

    public final boolean synpred5_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred5_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred10_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred11_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred20_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred16_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred18_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred17_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred17_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred14_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred7_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred15_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred8_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred23_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred23_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred24_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred24_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred6_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred3_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred2_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred21_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred13_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred1_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred9_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred12_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred4_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred22_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred22_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_Css3() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred19_Css3_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA22 dfa22 = new DFA22(this);
    protected DFA25 dfa25 = new DFA25(this);
    protected DFA36 dfa36 = new DFA36(this);
    protected DFA55 dfa55 = new DFA55(this);
    protected DFA76 dfa76 = new DFA76(this);
    protected DFA102 dfa102 = new DFA102(this);
    protected DFA113 dfa113 = new DFA113(this);
    protected DFA118 dfa118 = new DFA118(this);
    protected DFA121 dfa121 = new DFA121(this);
    protected DFA139 dfa139 = new DFA139(this);
    protected DFA148 dfa148 = new DFA148(this);
    protected DFA152 dfa152 = new DFA152(this);
    protected DFA155 dfa155 = new DFA155(this);
    protected DFA161 dfa161 = new DFA161(this);
    protected DFA182 dfa182 = new DFA182(this);
    protected DFA186 dfa186 = new DFA186(this);
    protected DFA201 dfa201 = new DFA201(this);
    protected DFA206 dfa206 = new DFA206(this);
    protected DFA226 dfa226 = new DFA226(this);
    protected DFA229 dfa229 = new DFA229(this);
    protected DFA228 dfa228 = new DFA228(this);
    protected DFA233 dfa233 = new DFA233(this);
    protected DFA234 dfa234 = new DFA234(this);
    protected DFA236 dfa236 = new DFA236(this);
    protected DFA238 dfa238 = new DFA238(this);
    protected DFA241 dfa241 = new DFA241(this);
    protected DFA240 dfa240 = new DFA240(this);
    protected DFA259 dfa259 = new DFA259(this);
    static final String DFA22_eotS =
        "\7\uffff";
    static final String DFA22_eofS =
        "\7\uffff";
    static final String DFA22_minS =
        "\1\12\2\7\2\5\2\uffff";
    static final String DFA22_maxS =
        "\1\12\4\123\2\uffff";
    static final String DFA22_acceptS =
        "\5\uffff\1\1\1\2";
    static final String DFA22_specialS =
        "\7\uffff}>";
    static final String[] DFA22_transitionS = {
            "\1\1",
            "\2\3\16\uffff\1\2\72\uffff\2\2",
            "\2\3\16\uffff\1\2\72\uffff\2\2",
            "\2\5\4\uffff\1\6\4\uffff\4\5\3\uffff\1\4\72\uffff\2\4",
            "\2\5\4\uffff\1\6\4\uffff\4\5\3\uffff\1\4\72\uffff\2\4",
            "",
            ""
    };

    static final short[] DFA22_eot = DFA.unpackEncodedString(DFA22_eotS);
    static final short[] DFA22_eof = DFA.unpackEncodedString(DFA22_eofS);
    static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars(DFA22_minS);
    static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars(DFA22_maxS);
    static final short[] DFA22_accept = DFA.unpackEncodedString(DFA22_acceptS);
    static final short[] DFA22_special = DFA.unpackEncodedString(DFA22_specialS);
    static final short[][] DFA22_transition;

    static {
        int numStates = DFA22_transitionS.length;
        DFA22_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
        }
    }

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = DFA22_eot;
            this.eof = DFA22_eof;
            this.min = DFA22_min;
            this.max = DFA22_max;
            this.accept = DFA22_accept;
            this.special = DFA22_special;
            this.transition = DFA22_transition;
        }
        public String getDescription() {
            return "357:1: importItem : ( IMPORT_SYM ( ws )? resourceIdentifier ( ws )? mediaQueryList SEMI | {...}? IMPORT_SYM ( ws )? resourceIdentifier ( ws )? ( COMMA ( ws )? resourceIdentifier ) mediaQueryList SEMI );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA25_eotS =
        "\16\uffff";
    static final String DFA25_eofS =
        "\16\uffff";
    static final String DFA25_minS =
        "\1\6\1\uffff\1\6\1\0\5\uffff\1\6\1\uffff\1\0\2\uffff";
    static final String DFA25_maxS =
        "\1\70\1\uffff\1\123\1\0\5\uffff\1\123\1\uffff\1\0\2\uffff";
    static final String DFA25_acceptS =
        "\1\uffff\1\1\2\uffff\1\2\1\1\3\2\1\uffff\1\1\1\uffff\2\1";
    static final String DFA25_specialS =
        "\1\0\1\uffff\1\4\1\2\5\uffff\1\1\1\uffff\1\3\2\uffff}>";
    static final String[] DFA25_transitionS = {
            "\1\3\6\uffff\1\10\1\uffff\1\5\1\4\1\2\1\6\1\7\1\5\40\uffff\1"+
            "\5\1\1\2\5",
            "",
            "\1\13\6\uffff\1\14\1\uffff\1\15\1\uffff\1\15\1\6\1\uffff\1"+
            "\15\2\uffff\1\11\35\uffff\1\15\1\12\2\15\31\uffff\2\11",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "\1\13\6\uffff\1\14\1\uffff\1\15\1\uffff\1\15\1\6\1\uffff\1"+
            "\15\2\uffff\1\11\35\uffff\1\15\1\12\2\15\31\uffff\2\11",
            "",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA25_eot = DFA.unpackEncodedString(DFA25_eotS);
    static final short[] DFA25_eof = DFA.unpackEncodedString(DFA25_eofS);
    static final char[] DFA25_min = DFA.unpackEncodedStringToUnsignedChars(DFA25_minS);
    static final char[] DFA25_max = DFA.unpackEncodedStringToUnsignedChars(DFA25_maxS);
    static final short[] DFA25_accept = DFA.unpackEncodedString(DFA25_acceptS);
    static final short[] DFA25_special = DFA.unpackEncodedString(DFA25_specialS);
    static final short[][] DFA25_transition;

    static {
        int numStates = DFA25_transitionS.length;
        DFA25_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA25_transition[i] = DFA.unpackEncodedString(DFA25_transitionS[i]);
        }
    }

    class DFA25 extends DFA {

        public DFA25(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 25;
            this.eot = DFA25_eot;
            this.eof = DFA25_eof;
            this.min = DFA25_min;
            this.max = DFA25_max;
            this.accept = DFA25_accept;
            this.special = DFA25_special;
            this.transition = DFA25_transition;
        }
        public String getDescription() {
            return "367:9: ( ( (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE )=> scss_mq_interpolation_expression ( ws )? | ( mediaQueryList )=> mediaQueryList )";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA25_0 = input.LA(1);

                         
                        int index25_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA25_0==HASH_SYMBOL) && (synpred1_Css3())) {s = 1;}

                        else if ( (LA25_0==NOT) ) {s = 2;}

                        else if ( (LA25_0==IDENT) ) {s = 3;}

                        else if ( (LA25_0==ONLY) && (synpred2_Css3())) {s = 4;}

                        else if ( (LA25_0==AND||LA25_0==COLON||LA25_0==MINUS||(LA25_0>=HASH && LA25_0<=DOT)) && (synpred1_Css3())) {s = 5;}

                        else if ( (LA25_0==GEN) && (synpred2_Css3())) {s = 6;}

                        else if ( (LA25_0==LPAREN) && (synpred2_Css3())) {s = 7;}

                        else if ( (LA25_0==LBRACE) && (synpred2_Css3())) {s = 8;}

                         
                        input.seek(index25_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA25_9 = input.LA(1);

                         
                        int index25_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA25_9==HASH_SYMBOL) && (synpred1_Css3())) {s = 10;}

                        else if ( (LA25_9==IDENT) ) {s = 11;}

                        else if ( (LA25_9==WS||(LA25_9>=NL && LA25_9<=COMMENT)) ) {s = 9;}

                        else if ( (LA25_9==LBRACE) && (synpred1_Css3())) {s = 12;}

                        else if ( (LA25_9==AND||LA25_9==NOT||LA25_9==COLON||LA25_9==MINUS||(LA25_9>=HASH && LA25_9<=DOT)) && (synpred1_Css3())) {s = 13;}

                        else if ( (LA25_9==GEN) && (synpred2_Css3())) {s = 6;}

                         
                        input.seek(index25_9);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA25_3 = input.LA(1);

                         
                        int index25_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Css3()) ) {s = 13;}

                        else if ( (synpred2_Css3()) ) {s = 8;}

                         
                        input.seek(index25_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA25_11 = input.LA(1);

                         
                        int index25_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Css3()) ) {s = 13;}

                        else if ( (synpred2_Css3()) ) {s = 8;}

                         
                        input.seek(index25_11);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA25_2 = input.LA(1);

                         
                        int index25_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA25_2==WS||(LA25_2>=NL && LA25_2<=COMMENT)) ) {s = 9;}

                        else if ( (LA25_2==HASH_SYMBOL) && (synpred1_Css3())) {s = 10;}

                        else if ( (LA25_2==IDENT) ) {s = 11;}

                        else if ( (LA25_2==LBRACE) && (synpred1_Css3())) {s = 12;}

                        else if ( (LA25_2==AND||LA25_2==NOT||LA25_2==COLON||LA25_2==MINUS||(LA25_2>=HASH && LA25_2<=DOT)) && (synpred1_Css3())) {s = 13;}

                        else if ( (LA25_2==GEN) && (synpred2_Css3())) {s = 6;}

                         
                        input.seek(index25_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 25, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA36_eotS =
        "\36\uffff";
    static final String DFA36_eofS =
        "\36\uffff";
    static final String DFA36_minS =
        "\1\6\1\uffff\6\0\7\uffff\1\0\5\uffff\1\0\6\uffff\1\0\1\uffff";
    static final String DFA36_maxS =
        "\1\146\1\uffff\6\0\7\uffff\1\0\5\uffff\1\0\6\uffff\1\0\1\uffff";
    static final String DFA36_acceptS =
        "\1\uffff\1\12\6\uffff\1\1\1\2\1\3\1\4\4\uffff\1\5\7\uffff\1\6\1"+
        "\7\1\10\2\uffff\1\11";
    static final String DFA36_specialS =
        "\1\0\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\7\uffff\1\7\5\uffff\1\10\6"+
        "\uffff\1\11\1\uffff}>";
    static final String[] DFA36_transitionS = {
            "\1\4\5\uffff\1\34\1\uffff\1\1\3\uffff\1\6\1\uffff\1\20\1\uffff"+
            "\1\7\1\uffff\1\32\3\uffff\1\32\1\uffff\1\30\1\uffff\1\31\24"+
            "\uffff\1\25\1\3\1\17\1\5\3\20\1\2\1\20\1\uffff\1\20\25\uffff"+
            "\1\10\1\20\7\uffff\1\11\1\uffff\2\12\1\13\1\uffff\3\13",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            ""
    };

    static final short[] DFA36_eot = DFA.unpackEncodedString(DFA36_eotS);
    static final short[] DFA36_eof = DFA.unpackEncodedString(DFA36_eofS);
    static final char[] DFA36_min = DFA.unpackEncodedStringToUnsignedChars(DFA36_minS);
    static final char[] DFA36_max = DFA.unpackEncodedStringToUnsignedChars(DFA36_maxS);
    static final short[] DFA36_accept = DFA.unpackEncodedString(DFA36_acceptS);
    static final short[] DFA36_special = DFA.unpackEncodedString(DFA36_specialS);
    static final short[][] DFA36_transition;

    static {
        int numStates = DFA36_transitionS.length;
        DFA36_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA36_transition[i] = DFA.unpackEncodedString(DFA36_transitionS[i]);
        }
    }

    class DFA36 extends DFA {

        public DFA36(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 36;
            this.eot = DFA36_eot;
            this.eof = DFA36_eof;
            this.min = DFA36_min;
            this.max = DFA36_max;
            this.accept = DFA36_accept;
            this.special = DFA36_special;
            this.transition = DFA36_transition;
        }
        public String getDescription() {
            return "()* loopback of 374:13: ( ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )+ COLON (~ ( SEMI | LBRACE | RBRACE ) )+ SEMI | scss_declaration_interpolation_expression COLON )=> declaration SEMI ( ws )? | {...}? sass_extend ( ws )? | {...}? sass_debug ( ws )? | {...}? sass_control ( ws )? | rule ( ws )? | page ( ws )? | fontFace ( ws )? | vendorAtRule ( ws )? | {...}? media ( ws )? )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA36_0 = input.LA(1);

                         
                        int index36_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA36_0==RBRACE) ) {s = 1;}

                        else if ( (LA36_0==STAR) ) {s = 2;}

                        else if ( (LA36_0==HASH_SYMBOL) ) {s = 3;}

                        else if ( (LA36_0==IDENT) ) {s = 4;}

                        else if ( (LA36_0==DOT) ) {s = 5;}

                        else if ( (LA36_0==GEN) ) {s = 6;}

                        else if ( (LA36_0==AT_IDENT) ) {s = 7;}

                        else if ( (LA36_0==SASS_VAR) && (synpred3_Css3())) {s = 8;}

                        else if ( (LA36_0==SASS_EXTEND) ) {s = 9;}

                        else if ( ((LA36_0>=SASS_DEBUG && LA36_0<=SASS_WARN)) ) {s = 10;}

                        else if ( (LA36_0==SASS_IF||(LA36_0>=SASS_FOR && LA36_0<=SASS_WHILE)) ) {s = 11;}

                        else if ( (LA36_0==HASH) ) {s = 15;}

                        else if ( (LA36_0==COLON||(LA36_0>=LBRACKET && LA36_0<=SASS_EXTEND_ONLY_SELECTOR)||LA36_0==PIPE||LA36_0==LESS_AND||LA36_0==SASS_MIXIN) ) {s = 16;}

                        else if ( (LA36_0==MINUS) ) {s = 21;}

                        else if ( (LA36_0==PAGE_SYM) ) {s = 24;}

                        else if ( (LA36_0==FONT_FACE_SYM) ) {s = 25;}

                        else if ( (LA36_0==MOZ_DOCUMENT_SYM||LA36_0==WEBKIT_KEYFRAMES_SYM) ) {s = 26;}

                        else if ( (LA36_0==MEDIA_SYM) ) {s = 28;}

                         
                        input.seek(index36_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA36_2 = input.LA(1);

                         
                        int index36_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Css3()) ) {s = 8;}

                        else if ( (true) ) {s = 16;}

                         
                        input.seek(index36_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA36_3 = input.LA(1);

                         
                        int index36_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Css3()) ) {s = 8;}

                        else if ( (true) ) {s = 16;}

                         
                        input.seek(index36_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA36_4 = input.LA(1);

                         
                        int index36_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Css3()) ) {s = 8;}

                        else if ( (true) ) {s = 16;}

                         
                        input.seek(index36_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA36_5 = input.LA(1);

                         
                        int index36_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Css3()) ) {s = 8;}

                        else if ( (true) ) {s = 16;}

                         
                        input.seek(index36_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA36_6 = input.LA(1);

                         
                        int index36_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Css3()) ) {s = 8;}

                        else if ( (true) ) {s = 16;}

                         
                        input.seek(index36_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA36_7 = input.LA(1);

                         
                        int index36_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (((synpred3_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 8;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index36_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA36_15 = input.LA(1);

                         
                        int index36_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Css3()) ) {s = 8;}

                        else if ( (true) ) {s = 16;}

                         
                        input.seek(index36_15);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA36_21 = input.LA(1);

                         
                        int index36_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Css3()) ) {s = 8;}

                        else if ( (true) ) {s = 16;}

                         
                        input.seek(index36_21);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA36_28 = input.LA(1);

                         
                        int index36_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (((synpred3_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 8;}

                        else if ( (evalPredicate(isScssSource(),"isScssSource()")) ) {s = 29;}

                         
                        input.seek(index36_28);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 36, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA55_eotS =
        "\157\uffff";
    static final String DFA55_eofS =
        "\157\uffff";
    static final String DFA55_minS =
        "\2\6\1\uffff\1\6\4\uffff\1\6\4\uffff\1\5\1\6\1\uffff\2\6\1\5\3\6"+
        "\5\5\2\6\1\5\1\6\2\5\1\6\1\5\1\6\1\5\2\6\2\5\2\6\1\5\1\6\1\5\2\6"+
        "\2\5\2\6\1\5\1\6\2\5\1\6\1\5\1\6\2\5\4\6\2\5\1\6\1\5\1\6\1\5\2\6"+
        "\1\5\1\6\2\5\1\6\1\5\3\6\1\5\1\6\1\5\4\6\2\5\1\6\1\5\1\6\1\5\6\6"+
        "\1\5\1\6\1\5\7\6";
    static final String DFA55_maxS =
        "\1\146\1\123\1\uffff\1\123\4\uffff\1\123\4\uffff\2\123\1\uffff\1"+
        "\125\2\123\1\131\4\125\2\123\1\132\1\125\1\123\4\125\1\123\1\125"+
        "\1\131\1\132\1\123\4\125\1\123\1\125\1\123\1\125\1\123\1\131\4\123"+
        "\4\125\1\123\4\125\3\123\3\125\1\123\1\125\1\123\1\125\2\123\4\125"+
        "\1\123\1\125\3\123\1\125\1\123\1\125\3\123\3\125\1\123\1\125\1\123"+
        "\1\125\6\123\1\125\1\123\1\125\7\123";
    static final String DFA55_acceptS =
        "\2\uffff\1\1\1\uffff\1\3\1\4\1\5\1\6\1\uffff\1\7\1\10\1\11\1\12"+
        "\2\uffff\1\2\137\uffff";
    static final String DFA55_specialS =
        "\157\uffff}>";
    static final String[] DFA55_transitionS = {
            "\1\2\5\uffff\1\3\5\uffff\1\2\1\uffff\1\2\1\uffff\1\10\1\uffff"+
            "\1\7\3\uffff\1\7\1\uffff\1\4\1\5\1\6\24\uffff\3\2\1\1\5\2\1"+
            "\uffff\1\2\25\uffff\1\11\1\2\1\12\10\uffff\2\13\1\14\1\uffff"+
            "\3\14",
            "\1\15\6\uffff\1\2\4\uffff\1\2\1\uffff\1\2\2\uffff\1\2\35\uffff"+
            "\4\2\31\uffff\2\2",
            "",
            "\1\17\6\uffff\1\17\1\uffff\5\17\1\20\2\uffff\1\16\35\uffff"+
            "\4\17\31\uffff\2\16",
            "",
            "",
            "",
            "",
            "\2\7\5\uffff\1\7\6\uffff\1\11\2\uffff\1\21\72\uffff\2\11",
            "",
            "",
            "",
            "",
            "\1\12\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\23\1\2\2\uffff"+
            "\1\22\32\uffff\14\2\1\uffff\1\2\22\uffff\2\22",
            "\1\17\6\uffff\1\17\1\uffff\5\17\1\20\2\uffff\1\16\35\uffff"+
            "\4\17\31\uffff\2\16",
            "",
            "\1\26\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17\2\11"+
            "\1\17\1\uffff\1\11\1\24\5\uffff\1\11\24\uffff\1\11\2\uffff\1"+
            "\25\1\17\1\27\1\17\17\uffff\12\11\2\24\1\uffff\1\11",
            "\2\7\5\uffff\1\7\6\uffff\1\11\2\uffff\1\21\72\uffff\2\11",
            "\1\12\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\23\1\2\2\uffff"+
            "\1\22\32\uffff\14\2\1\uffff\1\2\22\uffff\2\22",
            "\3\12\3\uffff\1\30\5\uffff\1\12\2\uffff\1\32\1\30\6\uffff\1"+
            "\12\24\uffff\1\12\2\uffff\1\12\1\uffff\1\12\20\uffff\12\12\3"+
            "\uffff\1\31\2\uffff\2\2",
            "\1\26\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17\2\11"+
            "\1\17\1\uffff\1\11\1\24\5\uffff\1\11\24\uffff\1\11\2\uffff\1"+
            "\25\1\17\1\27\1\17\17\uffff\12\11\2\24\1\uffff\1\11",
            "\1\26\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17\1\11"+
            "\1\uffff\1\17\1\uffff\1\11\1\33\5\uffff\1\11\27\uffff\2\17\1"+
            "\27\1\17\17\uffff\12\11\2\33\1\uffff\1\11",
            "\1\11\1\37\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\34\1\uffff\1\11\1\35\5\uffff\1\11\23\uffff\2\11\2\uffff"+
            "\1\36\1\17\1\40\1\41\3\uffff\1\11\13\uffff\12\11\2\35\2\11",
            "\1\11\1\37\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\42\5\uffff\1\11\23\uffff\2"+
            "\11\2\uffff\1\36\1\17\1\40\1\17\3\uffff\1\11\13\uffff\12\11"+
            "\2\42\2\11",
            "\1\43\5\uffff\1\43\10\uffff\1\2\1\32\1\uffff\1\12\72\uffff"+
            "\2\12",
            "\1\43\5\uffff\1\43\10\uffff\1\2\1\32\1\uffff\1\12\72\uffff"+
            "\2\12",
            "\1\12\7\uffff\1\2\11\uffff\1\44\72\uffff\2\44\6\uffff\1\2",
            "\1\26\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17\1\11"+
            "\1\uffff\1\17\1\uffff\1\11\1\33\5\uffff\1\11\27\uffff\2\17\1"+
            "\27\1\17\17\uffff\12\11\2\33\1\uffff\1\11",
            "\1\45\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17\2"+
            "\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\37\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\17\1\uffff\1\11\1\35\5\uffff\1\11\23\uffff\2\11\2\uffff"+
            "\1\36\1\17\1\40\1\17\3\uffff\1\11\13\uffff\12\11\2\35\2\11",
            "\1\47\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17\2\11"+
            "\1\17\1\uffff\1\11\1\46\5\uffff\1\11\24\uffff\1\11\2\uffff\1"+
            "\51\1\17\1\50\1\17\17\uffff\12\11\2\46\1\uffff\1\11",
            "\1\11\1\37\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\52\1\uffff\1\11\1\53\5\uffff\1\11\23\uffff\2\11\2\uffff"+
            "\1\36\1\17\1\40\1\54\3\uffff\1\11\13\uffff\12\11\2\53\2\11",
            "\1\11\1\37\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\55\5\uffff\1\11\23\uffff\2"+
            "\11\2\uffff\1\36\1\17\1\40\1\17\3\uffff\1\11\13\uffff\12\11"+
            "\2\55\2\11",
            "\1\56\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17\2"+
            "\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\37\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\42\5\uffff\1\11\23\uffff\2"+
            "\11\2\uffff\1\36\1\17\1\40\1\17\3\uffff\1\11\13\uffff\12\11"+
            "\2\42\2\11",
            "\3\12\3\uffff\1\60\5\uffff\1\12\3\uffff\1\60\1\57\5\uffff\1"+
            "\12\24\uffff\1\12\2\uffff\1\12\1\uffff\1\12\20\uffff\12\12\2"+
            "\57\1\uffff\1\61\2\uffff\2\2",
            "\1\12\7\uffff\1\2\11\uffff\1\44\72\uffff\2\44\6\uffff\1\2",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\62\35\uffff\3\17\1\41\31\uffff\2\62",
            "\1\47\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17\2\11"+
            "\1\17\1\uffff\1\11\1\46\5\uffff\1\11\24\uffff\1\11\2\uffff\1"+
            "\51\1\17\1\50\1\17\17\uffff\12\11\2\46\1\uffff\1\11",
            "\1\11\1\66\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\63\1\uffff\1\11\1\64\5\uffff\1\11\23\uffff\2\11\2\uffff"+
            "\1\65\1\17\1\67\1\70\3\uffff\1\11\13\uffff\12\11\2\64\2\11",
            "\1\11\1\66\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\71\5\uffff\1\11\23\uffff\2"+
            "\11\2\uffff\1\65\1\17\1\67\1\17\3\uffff\1\11\13\uffff\12\11"+
            "\2\71\2\11",
            "\1\73\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17\1\11"+
            "\1\uffff\1\17\1\uffff\1\11\1\72\5\uffff\1\11\27\uffff\2\17\1"+
            "\74\1\17\17\uffff\12\11\2\72\1\uffff\1\11",
            "\1\75\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17\2"+
            "\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\37\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\17\1\uffff\1\11\1\53\5\uffff\1\11\23\uffff\2\11\2\uffff"+
            "\1\36\1\17\1\40\1\17\3\uffff\1\11\13\uffff\12\11\2\53\2\11",
            "\1\76\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17\2"+
            "\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\37\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\55\5\uffff\1\11\23\uffff\2"+
            "\11\2\uffff\1\36\1\17\1\40\1\17\3\uffff\1\11\13\uffff\12\11"+
            "\2\55\2\11",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\62\35\uffff\3\17\1\41\31\uffff\2\62",
            "\3\12\3\uffff\1\60\5\uffff\1\12\3\uffff\1\60\1\57\5\uffff\1"+
            "\12\24\uffff\1\12\2\uffff\1\12\1\uffff\1\12\20\uffff\12\12\2"+
            "\57\1\uffff\1\61\2\uffff\2\2",
            "\1\43\5\uffff\1\43\10\uffff\1\2\1\32\1\uffff\1\12\72\uffff"+
            "\2\12",
            "\1\43\5\uffff\1\43\10\uffff\1\2\1\32\1\uffff\1\12\72\uffff"+
            "\2\12",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\62\35\uffff\4\17\31\uffff\2\62",
            "\1\77\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17\2"+
            "\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\66\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\17\1\uffff\1\11\1\64\5\uffff\1\11\23\uffff\2\11\2\uffff"+
            "\1\65\1\17\1\67\1\17\3\uffff\1\11\13\uffff\12\11\2\64\2\11",
            "\1\101\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17\2\11"+
            "\1\17\1\uffff\1\11\1\100\5\uffff\1\11\24\uffff\1\11\2\uffff"+
            "\1\51\1\17\1\102\1\17\17\uffff\12\11\2\100\1\uffff\1\11",
            "\1\11\1\66\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\103\1\uffff\1\11\1\104\5\uffff\1\11\23\uffff\2\11\2"+
            "\uffff\1\65\1\17\1\67\1\105\3\uffff\1\11\13\uffff\12\11\2\104"+
            "\2\11",
            "\1\11\1\66\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\106\5\uffff\1\11\23\uffff"+
            "\2\11\2\uffff\1\65\1\17\1\67\1\17\3\uffff\1\11\13\uffff\12\11"+
            "\2\106\2\11",
            "\1\107\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17"+
            "\2\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\66\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\71\5\uffff\1\11\23\uffff\2"+
            "\11\2\uffff\1\65\1\17\1\67\1\17\3\uffff\1\11\13\uffff\12\11"+
            "\2\71\2\11",
            "\1\73\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17\1\11"+
            "\1\uffff\1\17\1\uffff\1\11\1\72\5\uffff\1\11\27\uffff\2\17\1"+
            "\74\1\17\17\uffff\12\11\2\72\1\uffff\1\11",
            "\1\11\1\113\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\110\1\uffff\1\11\1\111\5\uffff\1\11\23\uffff\2\11\2"+
            "\uffff\1\112\1\17\1\114\1\115\3\uffff\1\11\13\uffff\12\11\2"+
            "\111\2\11",
            "\1\11\1\113\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\116\5\uffff\1\11\23\uffff"+
            "\2\11\2\uffff\1\112\1\17\1\114\1\17\3\uffff\1\11\13\uffff\12"+
            "\11\2\116\2\11",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\117\35\uffff\3\17\1\54\31\uffff\2\117",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\117\35\uffff\3\17\1\54\31\uffff\2\117",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\120\35\uffff\3\17\1\70\31\uffff\2\120",
            "\1\101\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17\2\11"+
            "\1\17\1\uffff\1\11\1\100\5\uffff\1\11\24\uffff\1\11\2\uffff"+
            "\1\51\1\17\1\102\1\17\17\uffff\12\11\2\100\1\uffff\1\11",
            "\1\11\1\66\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\121\1\uffff\1\11\1\122\5\uffff\1\11\23\uffff\2\11\2"+
            "\uffff\1\65\1\17\1\67\1\123\3\uffff\1\11\13\uffff\12\11\2\122"+
            "\2\11",
            "\1\11\1\66\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\124\5\uffff\1\11\23\uffff"+
            "\2\11\2\uffff\1\65\1\17\1\67\1\17\3\uffff\1\11\13\uffff\12\11"+
            "\2\124\2\11",
            "\1\125\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17"+
            "\2\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\66\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\17\1\uffff\1\11\1\104\5\uffff\1\11\23\uffff\2\11\2\uffff"+
            "\1\65\1\17\1\67\1\17\3\uffff\1\11\13\uffff\12\11\2\104\2\11",
            "\1\126\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17"+
            "\2\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\66\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\106\5\uffff\1\11\23\uffff"+
            "\2\11\2\uffff\1\65\1\17\1\67\1\17\3\uffff\1\11\13\uffff\12\11"+
            "\2\106\2\11",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\120\35\uffff\3\17\1\70\31\uffff\2\120",
            "\1\127\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17"+
            "\2\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\113\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\17\1\uffff\1\11\1\111\5\uffff\1\11\23\uffff\2\11\2\uffff"+
            "\1\112\1\17\1\114\1\17\3\uffff\1\11\13\uffff\12\11\2\111\2\11",
            "\1\131\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17\2\11"+
            "\1\17\1\uffff\1\11\1\130\5\uffff\1\11\24\uffff\1\11\2\uffff"+
            "\1\51\1\17\1\132\1\17\17\uffff\12\11\2\130\1\uffff\1\11",
            "\1\11\1\113\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\133\1\uffff\1\11\1\134\5\uffff\1\11\23\uffff\2\11\2"+
            "\uffff\1\112\1\17\1\114\1\135\3\uffff\1\11\13\uffff\12\11\2"+
            "\134\2\11",
            "\1\11\1\113\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\136\5\uffff\1\11\23\uffff"+
            "\2\11\2\uffff\1\112\1\17\1\114\1\17\3\uffff\1\11\13\uffff\12"+
            "\11\2\136\2\11",
            "\1\137\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17"+
            "\2\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\113\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\116\5\uffff\1\11\23\uffff"+
            "\2\11\2\uffff\1\112\1\17\1\114\1\17\3\uffff\1\11\13\uffff\12"+
            "\11\2\116\2\11",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\117\35\uffff\4\17\31\uffff\2\117",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\120\35\uffff\4\17\31\uffff\2\120",
            "\1\140\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17"+
            "\2\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\66\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\17\1\uffff\1\11\1\122\5\uffff\1\11\23\uffff\2\11\2\uffff"+
            "\1\65\1\17\1\67\1\17\3\uffff\1\11\13\uffff\12\11\2\122\2\11",
            "\1\141\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17"+
            "\2\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\66\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\124\5\uffff\1\11\23\uffff"+
            "\2\11\2\uffff\1\65\1\17\1\67\1\17\3\uffff\1\11\13\uffff\12\11"+
            "\2\124\2\11",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\142\35\uffff\3\17\1\105\31\uffff\2\142",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\142\35\uffff\3\17\1\105\31\uffff\2\142",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\143\35\uffff\3\17\1\115\31\uffff\2\143",
            "\1\131\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17\2\11"+
            "\1\17\1\uffff\1\11\1\130\5\uffff\1\11\24\uffff\1\11\2\uffff"+
            "\1\51\1\17\1\132\1\17\17\uffff\12\11\2\130\1\uffff\1\11",
            "\1\11\1\113\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\144\1\uffff\1\11\1\145\5\uffff\1\11\23\uffff\2\11\2"+
            "\uffff\1\112\1\17\1\114\1\146\3\uffff\1\11\13\uffff\12\11\2"+
            "\145\2\11",
            "\1\11\1\113\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\147\5\uffff\1\11\23\uffff"+
            "\2\11\2\uffff\1\112\1\17\1\114\1\17\3\uffff\1\11\13\uffff\12"+
            "\11\2\147\2\11",
            "\1\150\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17"+
            "\2\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\113\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\17\1\uffff\1\11\1\134\5\uffff\1\11\23\uffff\2\11\2\uffff"+
            "\1\112\1\17\1\114\1\17\3\uffff\1\11\13\uffff\12\11\2\134\2\11",
            "\1\151\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17"+
            "\2\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\113\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\136\5\uffff\1\11\23\uffff"+
            "\2\11\2\uffff\1\112\1\17\1\114\1\17\3\uffff\1\11\13\uffff\12"+
            "\11\2\136\2\11",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\143\35\uffff\3\17\1\115\31\uffff\2\143",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\152\35\uffff\3\17\1\123\31\uffff\2\152",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\152\35\uffff\3\17\1\123\31\uffff\2\152",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\142\35\uffff\4\17\31\uffff\2\142",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\143\35\uffff\4\17\31\uffff\2\143",
            "\1\153\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17"+
            "\2\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\113\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\2\11\1\17\1\uffff\1\11\1\145\5\uffff\1\11\23\uffff\2\11\2\uffff"+
            "\1\112\1\17\1\114\1\17\3\uffff\1\11\13\uffff\12\11\2\145\2\11",
            "\1\154\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\2\uffff\1\17"+
            "\2\uffff\1\17\35\uffff\4\17\31\uffff\2\17",
            "\1\11\1\113\2\11\3\uffff\1\11\1\17\1\uffff\1\17\1\uffff\1\17"+
            "\1\11\1\uffff\1\17\1\uffff\1\11\1\147\5\uffff\1\11\23\uffff"+
            "\2\11\2\uffff\1\112\1\17\1\114\1\17\3\uffff\1\11\13\uffff\12"+
            "\11\2\147\2\11",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\155\35\uffff\3\17\1\135\31\uffff\2\155",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\155\35\uffff\3\17\1\135\31\uffff\2\155",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\152\35\uffff\4\17\31\uffff\2\152",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\156\35\uffff\3\17\1\146\31\uffff\2\156",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\156\35\uffff\3\17\1\146\31\uffff\2\156",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\155\35\uffff\4\17\31\uffff\2\155",
            "\1\17\6\uffff\1\17\1\uffff\1\17\1\uffff\1\17\1\uffff\1\11\1"+
            "\17\2\uffff\1\156\35\uffff\4\17\31\uffff\2\156"
    };

    static final short[] DFA55_eot = DFA.unpackEncodedString(DFA55_eotS);
    static final short[] DFA55_eof = DFA.unpackEncodedString(DFA55_eofS);
    static final char[] DFA55_min = DFA.unpackEncodedStringToUnsignedChars(DFA55_minS);
    static final char[] DFA55_max = DFA.unpackEncodedStringToUnsignedChars(DFA55_maxS);
    static final short[] DFA55_accept = DFA.unpackEncodedString(DFA55_acceptS);
    static final short[] DFA55_special = DFA.unpackEncodedString(DFA55_specialS);
    static final short[][] DFA55_transition;

    static {
        int numStates = DFA55_transitionS.length;
        DFA55_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA55_transition[i] = DFA.unpackEncodedString(DFA55_transitionS[i]);
        }
    }

    class DFA55 extends DFA {

        public DFA55(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 55;
            this.eot = DFA55_eot;
            this.eof = DFA55_eof;
            this.min = DFA55_min;
            this.max = DFA55_max;
            this.accept = DFA55_accept;
            this.special = DFA55_special;
            this.transition = DFA55_transition;
        }
        public String getDescription() {
            return "420:1: bodyItem : ( rule | media | page | counterStyle | fontFace | vendorAtRule | {...}? cp_variable_declaration | {...}? cp_mixin_call | {...}? sass_debug | {...}? sass_control );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA76_eotS =
        "\4\uffff";
    static final String DFA76_eofS =
        "\4\uffff";
    static final String DFA76_minS =
        "\2\13\2\uffff";
    static final String DFA76_maxS =
        "\2\123\2\uffff";
    static final String DFA76_acceptS =
        "\2\uffff\1\2\1\1";
    static final String DFA76_specialS =
        "\4\uffff}>";
    static final String[] DFA76_transitionS = {
            "\1\3\1\uffff\1\2\11\uffff\1\1\72\uffff\2\1",
            "\1\3\1\uffff\1\2\11\uffff\1\1\72\uffff\2\1",
            "",
            ""
    };

    static final short[] DFA76_eot = DFA.unpackEncodedString(DFA76_eotS);
    static final short[] DFA76_eof = DFA.unpackEncodedString(DFA76_eofS);
    static final char[] DFA76_min = DFA.unpackEncodedStringToUnsignedChars(DFA76_minS);
    static final char[] DFA76_max = DFA.unpackEncodedStringToUnsignedChars(DFA76_maxS);
    static final short[] DFA76_accept = DFA.unpackEncodedString(DFA76_acceptS);
    static final short[] DFA76_special = DFA.unpackEncodedString(DFA76_specialS);
    static final short[][] DFA76_transition;

    static {
        int numStates = DFA76_transitionS.length;
        DFA76_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA76_transition[i] = DFA.unpackEncodedString(DFA76_transitionS[i]);
        }
    }

    class DFA76 extends DFA {

        public DFA76(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 76;
            this.eot = DFA76_eot;
            this.eof = DFA76_eof;
            this.min = DFA76_min;
            this.max = DFA76_max;
            this.accept = DFA76_accept;
            this.special = DFA76_special;
            this.transition = DFA76_transition;
        }
        public String getDescription() {
            return "()* loopback of 487:25: ( ( ws )? COMMA ( ws )? ( IDENT | PERCENTAGE ) )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA102_eotS =
        "\6\uffff";
    static final String DFA102_eofS =
        "\6\uffff";
    static final String DFA102_minS =
        "\2\6\2\uffff\2\6";
    static final String DFA102_maxS =
        "\1\126\1\123\2\uffff\2\123";
    static final String DFA102_acceptS =
        "\2\uffff\1\1\1\2\2\uffff";
    static final String DFA102_specialS =
        "\6\uffff}>";
    static final String[] DFA102_transitionS = {
            "\1\3\13\uffff\1\3\1\uffff\1\3\40\uffff\3\3\1\1\5\3\1\uffff\1"+
            "\3\26\uffff\1\2",
            "\1\4\6\uffff\1\3\4\uffff\1\3\1\uffff\1\3\2\uffff\1\3\35\uffff"+
            "\4\3\31\uffff\2\3",
            "",
            "",
            "\1\3\4\uffff\1\3\1\uffff\1\3\4\uffff\1\3\1\2\1\3\2\uffff\1"+
            "\5\32\uffff\14\3\1\uffff\1\3\22\uffff\2\5",
            "\1\3\4\uffff\1\3\1\uffff\1\3\4\uffff\1\3\1\2\1\3\2\uffff\1"+
            "\5\32\uffff\14\3\1\uffff\1\3\22\uffff\2\5"
    };

    static final short[] DFA102_eot = DFA.unpackEncodedString(DFA102_eotS);
    static final short[] DFA102_eof = DFA.unpackEncodedString(DFA102_eofS);
    static final char[] DFA102_min = DFA.unpackEncodedStringToUnsignedChars(DFA102_minS);
    static final char[] DFA102_max = DFA.unpackEncodedStringToUnsignedChars(DFA102_maxS);
    static final short[] DFA102_accept = DFA.unpackEncodedString(DFA102_acceptS);
    static final short[] DFA102_special = DFA.unpackEncodedString(DFA102_specialS);
    static final short[][] DFA102_transition;

    static {
        int numStates = DFA102_transitionS.length;
        DFA102_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA102_transition[i] = DFA.unpackEncodedString(DFA102_transitionS[i]);
        }
    }

    class DFA102 extends DFA {

        public DFA102(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 102;
            this.eot = DFA102_eot;
            this.eof = DFA102_eof;
            this.min = DFA102_min;
            this.max = DFA102_max;
            this.accept = DFA102_accept;
            this.special = DFA102_special;
            this.transition = DFA102_transition;
        }
        public String getDescription() {
            return "575:9: ( ({...}? cp_mixin_declaration ) | ( selectorsGroup ) )";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA113_eotS =
        "\37\uffff";
    static final String DFA113_eofS =
        "\37\uffff";
    static final String DFA113_minS =
        "\1\5\7\0\1\uffff\1\0\5\uffff\1\0\10\uffff\1\0\6\uffff";
    static final String DFA113_maxS =
        "\1\146\7\0\1\uffff\1\0\5\uffff\1\0\10\uffff\1\0\6\uffff";
    static final String DFA113_acceptS =
        "\10\uffff\1\13\1\uffff\5\4\1\uffff\2\4\1\5\1\6\1\7\4\uffff\1\11"+
        "\1\12\1\1\1\2\1\3\1\10";
    static final String DFA113_specialS =
        "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\uffff\1\10\5\uffff\1\11\10\uffff"+
        "\1\12\6\uffff}>";
    static final String[] DFA113_transitionS = {
            "\1\32\1\3\5\uffff\1\6\1\uffff\1\10\3\uffff\1\5\1\uffff\1\13"+
            "\1\uffff\1\30\36\uffff\1\17\1\2\1\11\1\4\1\20\1\21\1\16\1\1"+
            "\1\14\1\uffff\1\15\25\uffff\1\7\1\12\1\31\6\uffff\1\22\1\uffff"+
            "\2\23\1\24\1\uffff\3\24",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA113_eot = DFA.unpackEncodedString(DFA113_eotS);
    static final short[] DFA113_eof = DFA.unpackEncodedString(DFA113_eofS);
    static final char[] DFA113_min = DFA.unpackEncodedStringToUnsignedChars(DFA113_minS);
    static final char[] DFA113_max = DFA.unpackEncodedStringToUnsignedChars(DFA113_maxS);
    static final short[] DFA113_accept = DFA.unpackEncodedString(DFA113_acceptS);
    static final short[] DFA113_special = DFA.unpackEncodedString(DFA113_specialS);
    static final short[][] DFA113_transition;

    static {
        int numStates = DFA113_transitionS.length;
        DFA113_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA113_transition[i] = DFA.unpackEncodedString(DFA113_transitionS[i]);
        }
    }

    class DFA113 extends DFA {

        public DFA113(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 113;
            this.eot = DFA113_eot;
            this.eof = DFA113_eof;
            this.min = DFA113_min;
            this.max = DFA113_max;
            this.accept = DFA113_accept;
            this.special = DFA113_special;
            this.transition = DFA113_transition;
        }
        public String getDescription() {
            return "()* loopback of 594:13: ( ( declaration SEMI )=> declaration SEMI ( ws )? | ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )* COLON (~ ( SEMI | LBRACE | RBRACE ) )* SEMI )=> declaration SEMI ( ws )? | ( scss_nested_properties )=> scss_nested_properties ( ws )? | ( rule )=> rule ( ws )? | {...}? sass_extend ( ws )? | {...}? sass_debug ( ws )? | {...}? sass_control ( ws )? | {...}? media ( ws )? | {...}? cp_mixin_call ( ws )? | ( (~ SEMI )* SEMI )=> syncTo_SEMI )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA113_0 = input.LA(1);

                         
                        int index113_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA113_0==STAR) ) {s = 1;}

                        else if ( (LA113_0==HASH_SYMBOL) ) {s = 2;}

                        else if ( (LA113_0==IDENT) ) {s = 3;}

                        else if ( (LA113_0==DOT) ) {s = 4;}

                        else if ( (LA113_0==GEN) ) {s = 5;}

                        else if ( (LA113_0==MEDIA_SYM) ) {s = 6;}

                        else if ( (LA113_0==SASS_VAR) ) {s = 7;}

                        else if ( (LA113_0==RBRACE) ) {s = 8;}

                        else if ( (LA113_0==HASH) ) {s = 9;}

                        else if ( (LA113_0==SASS_MIXIN) && (synpred8_Css3())) {s = 10;}

                        else if ( (LA113_0==COLON) && (synpred8_Css3())) {s = 11;}

                        else if ( (LA113_0==PIPE) && (synpred8_Css3())) {s = 12;}

                        else if ( (LA113_0==LESS_AND) && (synpred8_Css3())) {s = 13;}

                        else if ( (LA113_0==SASS_EXTEND_ONLY_SELECTOR) && (synpred8_Css3())) {s = 14;}

                        else if ( (LA113_0==MINUS) ) {s = 15;}

                        else if ( (LA113_0==LBRACKET) && (synpred8_Css3())) {s = 16;}

                        else if ( (LA113_0==DCOLON) && (synpred8_Css3())) {s = 17;}

                        else if ( (LA113_0==SASS_EXTEND) ) {s = 18;}

                        else if ( ((LA113_0>=SASS_DEBUG && LA113_0<=SASS_WARN)) ) {s = 19;}

                        else if ( (LA113_0==SASS_IF||(LA113_0>=SASS_FOR && LA113_0<=SASS_WHILE)) ) {s = 20;}

                        else if ( (LA113_0==AT_IDENT) ) {s = 24;}

                        else if ( (LA113_0==SASS_INCLUDE) ) {s = 25;}

                        else if ( (LA113_0==SEMI) && (synpred9_Css3())) {s = 26;}

                         
                        input.seek(index113_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA113_1 = input.LA(1);

                         
                        int index113_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Css3()) ) {s = 27;}

                        else if ( (synpred6_Css3()) ) {s = 28;}

                        else if ( (synpred8_Css3()) ) {s = 17;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index113_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA113_2 = input.LA(1);

                         
                        int index113_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Css3()) ) {s = 27;}

                        else if ( (synpred6_Css3()) ) {s = 28;}

                        else if ( (synpred7_Css3()) ) {s = 29;}

                        else if ( (synpred8_Css3()) ) {s = 17;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index113_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA113_3 = input.LA(1);

                         
                        int index113_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Css3()) ) {s = 27;}

                        else if ( (synpred6_Css3()) ) {s = 28;}

                        else if ( (synpred7_Css3()) ) {s = 29;}

                        else if ( (synpred8_Css3()) ) {s = 17;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index113_3);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA113_4 = input.LA(1);

                         
                        int index113_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Css3()) ) {s = 27;}

                        else if ( (synpred6_Css3()) ) {s = 28;}

                        else if ( (synpred7_Css3()) ) {s = 29;}

                        else if ( ((((synpred8_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isLessSource(),"isLessSource()"))||synpred8_Css3())) ) {s = 17;}

                        else if ( ((evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 25;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index113_4);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA113_5 = input.LA(1);

                         
                        int index113_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Css3()) ) {s = 27;}

                        else if ( (synpred6_Css3()) ) {s = 28;}

                        else if ( (synpred7_Css3()) ) {s = 29;}

                        else if ( (synpred8_Css3()) ) {s = 17;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index113_5);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA113_6 = input.LA(1);

                         
                        int index113_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (((synpred5_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 27;}

                        else if ( (((synpred6_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 28;}

                        else if ( (((synpred7_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 29;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 30;}

                        else if ( ((evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 8;}

                         
                        input.seek(index113_6);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA113_7 = input.LA(1);

                         
                        int index113_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (((synpred5_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isScssSource(),"isScssSource()"))) ) {s = 27;}

                        else if ( (((synpred6_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isScssSource(),"isScssSource()"))) ) {s = 28;}

                        else if ( (((synpred7_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isScssSource(),"isScssSource()"))) ) {s = 29;}

                        else if ( ((evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")&&evalPredicate(isScssSource(),"isScssSource()"))) ) {s = 8;}

                         
                        input.seek(index113_7);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA113_9 = input.LA(1);

                         
                        int index113_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Css3()) ) {s = 27;}

                        else if ( (synpred6_Css3()) ) {s = 28;}

                        else if ( (synpred7_Css3()) ) {s = 29;}

                        else if ( (synpred8_Css3()) ) {s = 17;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index113_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA113_15 = input.LA(1);

                         
                        int index113_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Css3()) ) {s = 27;}

                        else if ( (synpred6_Css3()) ) {s = 28;}

                        else if ( (synpred7_Css3()) ) {s = 29;}

                        else if ( (synpred8_Css3()) ) {s = 17;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index113_15);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA113_24 = input.LA(1);

                         
                        int index113_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (((synpred5_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 27;}

                        else if ( (((synpred6_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 28;}

                        else if ( (((synpred7_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 29;}

                        else if ( ((evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 8;}

                         
                        input.seek(index113_24);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 113, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA118_eotS =
        "\17\uffff";
    static final String DFA118_eofS =
        "\17\uffff";
    static final String DFA118_minS =
        "\2\6\2\0\1\uffff\2\6\5\uffff\1\0\1\uffff\1\0";
    static final String DFA118_maxS =
        "\1\77\1\123\2\0\1\uffff\2\123\5\uffff\1\0\1\uffff\1\0";
    static final String DFA118_acceptS =
        "\4\uffff\1\2\2\uffff\5\1\1\uffff\1\1\1\uffff";
    static final String DFA118_specialS =
        "\1\2\1\4\1\0\1\1\1\uffff\1\6\1\3\5\uffff\1\7\1\uffff\1\5}>";
    static final String[] DFA118_transitionS = {
            "\1\2\13\uffff\1\4\1\uffff\1\6\40\uffff\1\7\1\1\1\3\1\5\5\4\1"+
            "\uffff\1\4",
            "\1\13\6\uffff\1\10\6\uffff\1\13\2\uffff\1\11\35\uffff\1\13"+
            "\1\12\2\13\5\uffff\1\4\23\uffff\2\11",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\14\6\uffff\1\15\4\uffff\1\4\1\uffff\1\13\2\uffff\1\11\35"+
            "\uffff\1\13\1\12\2\13\31\uffff\2\11",
            "\1\16\6\uffff\1\15\3\uffff\2\4\1\uffff\1\13\2\uffff\1\11\35"+
            "\uffff\1\13\1\12\2\13\31\uffff\2\11",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "\1\uffff"
    };

    static final short[] DFA118_eot = DFA.unpackEncodedString(DFA118_eotS);
    static final short[] DFA118_eof = DFA.unpackEncodedString(DFA118_eofS);
    static final char[] DFA118_min = DFA.unpackEncodedStringToUnsignedChars(DFA118_minS);
    static final char[] DFA118_max = DFA.unpackEncodedStringToUnsignedChars(DFA118_maxS);
    static final short[] DFA118_accept = DFA.unpackEncodedString(DFA118_acceptS);
    static final short[] DFA118_special = DFA.unpackEncodedString(DFA118_specialS);
    static final short[][] DFA118_transition;

    static {
        int numStates = DFA118_transitionS.length;
        DFA118_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA118_transition[i] = DFA.unpackEncodedString(DFA118_transitionS[i]);
        }
    }

    class DFA118 extends DFA {

        public DFA118(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 118;
            this.eot = DFA118_eot;
            this.eof = DFA118_eof;
            this.min = DFA118_min;
            this.max = DFA118_max;
            this.accept = DFA118_accept;
            this.special = DFA118_special;
            this.transition = DFA118_transition;
        }
        public String getDescription() {
            return "624:1: selectorsGroup : ( ( (~ ( HASH_SYMBOL | LBRACE ) )* HASH_SYMBOL LBRACE )=> scss_selector_interpolation_expression ( ws )? | selector ( COMMA ( ws )? selector )* );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA118_2 = input.LA(1);

                         
                        int index118_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index118_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA118_3 = input.LA(1);

                         
                        int index118_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index118_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA118_0 = input.LA(1);

                         
                        int index118_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA118_0==HASH_SYMBOL) ) {s = 1;}

                        else if ( (LA118_0==IDENT) ) {s = 2;}

                        else if ( (LA118_0==HASH) ) {s = 3;}

                        else if ( (LA118_0==GEN||(LA118_0>=LBRACKET && LA118_0<=PIPE)||LA118_0==LESS_AND) ) {s = 4;}

                        else if ( (LA118_0==DOT) ) {s = 5;}

                        else if ( (LA118_0==COLON) ) {s = 6;}

                        else if ( (LA118_0==MINUS) && (synpred10_Css3())) {s = 7;}

                         
                        input.seek(index118_0);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA118_6 = input.LA(1);

                         
                        int index118_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA118_6==WS||(LA118_6>=NL && LA118_6<=COMMENT)) && (synpred10_Css3())) {s = 9;}

                        else if ( (LA118_6==HASH_SYMBOL) && (synpred10_Css3())) {s = 10;}

                        else if ( (LA118_6==IDENT) ) {s = 14;}

                        else if ( (LA118_6==LBRACE) && (synpred10_Css3())) {s = 13;}

                        else if ( (LA118_6==COLON||LA118_6==MINUS||(LA118_6>=HASH && LA118_6<=DOT)) && (synpred10_Css3())) {s = 11;}

                        else if ( ((LA118_6>=NOT && LA118_6<=GEN)) ) {s = 4;}

                         
                        input.seek(index118_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA118_1 = input.LA(1);

                         
                        int index118_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA118_1==LBRACE) && (synpred10_Css3())) {s = 8;}

                        else if ( (LA118_1==NAME) ) {s = 4;}

                        else if ( (LA118_1==WS||(LA118_1>=NL && LA118_1<=COMMENT)) && (synpred10_Css3())) {s = 9;}

                        else if ( (LA118_1==HASH_SYMBOL) && (synpred10_Css3())) {s = 10;}

                        else if ( (LA118_1==IDENT||LA118_1==COLON||LA118_1==MINUS||(LA118_1>=HASH && LA118_1<=DOT)) && (synpred10_Css3())) {s = 11;}

                         
                        input.seek(index118_1);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA118_14 = input.LA(1);

                         
                        int index118_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Css3()) ) {s = 13;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index118_14);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA118_5 = input.LA(1);

                         
                        int index118_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA118_5==IDENT) ) {s = 12;}

                        else if ( (LA118_5==WS||(LA118_5>=NL && LA118_5<=COMMENT)) && (synpred10_Css3())) {s = 9;}

                        else if ( (LA118_5==HASH_SYMBOL) && (synpred10_Css3())) {s = 10;}

                        else if ( (LA118_5==GEN) ) {s = 4;}

                        else if ( (LA118_5==COLON||LA118_5==MINUS||(LA118_5>=HASH && LA118_5<=DOT)) && (synpred10_Css3())) {s = 11;}

                        else if ( (LA118_5==LBRACE) && (synpred10_Css3())) {s = 13;}

                         
                        input.seek(index118_5);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA118_12 = input.LA(1);

                         
                        int index118_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Css3()) ) {s = 13;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index118_12);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 118, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA121_eotS =
        "\24\uffff";
    static final String DFA121_eofS =
        "\24\uffff";
    static final String DFA121_minS =
        "\1\5\7\uffff\6\0\6\uffff";
    static final String DFA121_maxS =
        "\1\137\7\uffff\6\0\6\uffff";
    static final String DFA121_acceptS =
        "\1\uffff\1\2\21\uffff\1\1";
    static final String DFA121_specialS =
        "\10\uffff\1\0\1\1\1\2\1\3\1\4\1\5\6\uffff}>";
    static final String[] DFA121_transitionS = {
            "\2\1\4\uffff\1\1\1\uffff\1\1\4\uffff\1\1\1\uffff\1\15\1\1\34"+
            "\uffff\3\1\1\uffff\1\12\1\11\1\13\1\14\1\15\1\10\2\1\1\uffff"+
            "\1\1\37\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA121_eot = DFA.unpackEncodedString(DFA121_eotS);
    static final short[] DFA121_eof = DFA.unpackEncodedString(DFA121_eofS);
    static final char[] DFA121_min = DFA.unpackEncodedStringToUnsignedChars(DFA121_minS);
    static final char[] DFA121_max = DFA.unpackEncodedStringToUnsignedChars(DFA121_maxS);
    static final short[] DFA121_accept = DFA.unpackEncodedString(DFA121_acceptS);
    static final short[] DFA121_special = DFA.unpackEncodedString(DFA121_specialS);
    static final short[][] DFA121_transition;

    static {
        int numStates = DFA121_transitionS.length;
        DFA121_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA121_transition[i] = DFA.unpackEncodedString(DFA121_transitionS[i]);
        }
    }

    class DFA121 extends DFA {

        public DFA121(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 121;
            this.eot = DFA121_eot;
            this.eof = DFA121_eof;
            this.min = DFA121_min;
            this.max = DFA121_max;
            this.accept = DFA121_accept;
            this.special = DFA121_special;
            this.transition = DFA121_transition;
        }
        public String getDescription() {
            return "()* loopback of 640:17: ( ( esPred )=> elementSubsequent ( ws )? )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA121_8 = input.LA(1);

                         
                        int index121_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((synpred11_Css3()&&evalPredicate(isScssSource(),"isScssSource()"))) ) {s = 19;}

                        else if ( (evalPredicate(isScssSource(),"isScssSource()")) ) {s = 1;}

                         
                        input.seek(index121_8);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA121_9 = input.LA(1);

                         
                        int index121_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Css3()) ) {s = 19;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index121_9);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA121_10 = input.LA(1);

                         
                        int index121_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Css3()) ) {s = 19;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index121_10);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA121_11 = input.LA(1);

                         
                        int index121_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Css3()) ) {s = 19;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index121_11);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA121_12 = input.LA(1);

                         
                        int index121_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Css3()) ) {s = 19;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index121_12);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA121_13 = input.LA(1);

                         
                        int index121_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Css3()) ) {s = 19;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index121_13);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 121, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA139_eotS =
        "\4\uffff";
    static final String DFA139_eofS =
        "\4\uffff";
    static final String DFA139_minS =
        "\2\5\2\uffff";
    static final String DFA139_maxS =
        "\2\137\2\uffff";
    static final String DFA139_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA139_specialS =
        "\4\uffff}>";
    static final String[] DFA139_transitionS = {
            "\2\3\4\uffff\1\3\1\uffff\1\3\4\uffff\1\3\1\2\2\3\1\uffff\1\1"+
            "\32\uffff\3\3\1\uffff\10\3\1\uffff\1\3\22\uffff\2\1\13\uffff"+
            "\1\3",
            "\2\3\4\uffff\1\3\1\uffff\1\3\4\uffff\1\3\1\2\2\3\1\uffff\1"+
            "\1\32\uffff\3\3\1\uffff\10\3\1\uffff\1\3\22\uffff\2\1\13\uffff"+
            "\1\3",
            "",
            ""
    };

    static final short[] DFA139_eot = DFA.unpackEncodedString(DFA139_eotS);
    static final short[] DFA139_eof = DFA.unpackEncodedString(DFA139_eofS);
    static final char[] DFA139_min = DFA.unpackEncodedStringToUnsignedChars(DFA139_minS);
    static final char[] DFA139_max = DFA.unpackEncodedStringToUnsignedChars(DFA139_maxS);
    static final short[] DFA139_accept = DFA.unpackEncodedString(DFA139_acceptS);
    static final short[] DFA139_special = DFA.unpackEncodedString(DFA139_specialS);
    static final short[][] DFA139_transition;

    static {
        int numStates = DFA139_transitionS.length;
        DFA139_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA139_transition[i] = DFA.unpackEncodedString(DFA139_transitionS[i]);
        }
    }

    class DFA139 extends DFA {

        public DFA139(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 139;
            this.eot = DFA139_eot;
            this.eof = DFA139_eof;
            this.min = DFA139_min;
            this.max = DFA139_max;
            this.accept = DFA139_accept;
            this.special = DFA139_special;
            this.transition = DFA139_transition;
        }
        public String getDescription() {
            return "741:21: ( ( ws )? LPAREN ( ws )? ( expression | STAR )? RPAREN )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA148_eotS =
        "\35\uffff";
    static final String DFA148_eofS =
        "\35\uffff";
    static final String DFA148_minS =
        "\1\6\1\uffff\1\5\1\0\1\6\5\0\1\uffff\2\0\1\uffff\1\6\1\uffff\1\0"+
        "\4\uffff\1\0\1\uffff\2\0\1\uffff\1\6\2\0";
    static final String DFA148_maxS =
        "\1\125\1\uffff\1\125\1\0\1\125\5\0\1\uffff\2\0\1\uffff\1\125\1\uffff"+
        "\1\0\4\uffff\1\0\1\uffff\2\0\1\uffff\1\125\2\0";
    static final String DFA148_acceptS =
        "\1\uffff\1\1\10\uffff\1\1\2\uffff\1\3\1\uffff\1\1\1\uffff\4\1\1"+
        "\uffff\1\1\2\uffff\1\2\3\uffff";
    static final String DFA148_specialS =
        "\1\13\1\uffff\1\7\1\4\1\uffff\1\0\1\3\1\10\1\5\1\6\1\uffff\1\11"+
        "\1\14\1\uffff\1\1\1\uffff\1\20\4\uffff\1\16\1\uffff\1\12\1\15\2"+
        "\uffff\1\17\1\2}>";
    static final String[] DFA148_transitionS = {
            "\1\3\1\6\1\11\3\uffff\1\13\5\uffff\1\10\1\15\2\uffff\1\13\6"+
            "\uffff\1\5\23\uffff\1\12\1\4\2\uffff\1\2\1\1\1\7\1\12\17\uffff"+
            "\12\5\3\uffff\1\14",
            "",
            "\1\22\1\20\1\6\1\11\3\uffff\1\27\1\24\1\23\3\uffff\1\10\3\uffff"+
            "\1\27\1\16\5\uffff\1\5\23\uffff\1\26\3\uffff\1\26\1\17\1\25"+
            "\1\26\16\uffff\1\21\12\5\2\16\1\uffff\1\30",
            "\1\uffff",
            "\1\33\1\6\1\11\3\uffff\1\27\5\uffff\1\10\3\uffff\1\27\1\32"+
            "\5\uffff\1\5\31\uffff\1\34\20\uffff\12\5\2\32\1\uffff\1\30",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\20\1\6\1\11\3\uffff\1\27\5\uffff\1\10\3\uffff\1\27\1\16"+
            "\5\uffff\1\5\23\uffff\1\26\3\uffff\1\26\1\17\1\25\1\26\17\uffff"+
            "\12\5\2\16\1\uffff\1\30",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\33\1\6\1\11\3\uffff\1\27\5\uffff\1\10\3\uffff\1\27\1\32"+
            "\5\uffff\1\5\31\uffff\1\34\20\uffff\12\5\2\32\1\uffff\1\30",
            "\1\uffff",
            "\1\uffff"
    };

    static final short[] DFA148_eot = DFA.unpackEncodedString(DFA148_eotS);
    static final short[] DFA148_eof = DFA.unpackEncodedString(DFA148_eofS);
    static final char[] DFA148_min = DFA.unpackEncodedStringToUnsignedChars(DFA148_minS);
    static final char[] DFA148_max = DFA.unpackEncodedStringToUnsignedChars(DFA148_maxS);
    static final short[] DFA148_accept = DFA.unpackEncodedString(DFA148_acceptS);
    static final short[] DFA148_special = DFA.unpackEncodedString(DFA148_specialS);
    static final short[][] DFA148_transition;

    static {
        int numStates = DFA148_transitionS.length;
        DFA148_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA148_transition[i] = DFA.unpackEncodedString(DFA148_transitionS[i]);
        }
    }

    class DFA148 extends DFA {

        public DFA148(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 148;
            this.eot = DFA148_eot;
            this.eof = DFA148_eof;
            this.min = DFA148_min;
            this.max = DFA148_max;
            this.accept = DFA148_accept;
            this.special = DFA148_special;
            this.transition = DFA148_transition;
        }
        public String getDescription() {
            return "761:1: propertyValue : ( ( (~ ( HASH_SYMBOL | SEMI ) )* HASH_SYMBOL LBRACE )=> scss_declaration_property_value_interpolation_expression | ( expressionPredicate )=> expression | ({...}? cp_expression ) );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA148_5 = input.LA(1);

                         
                        int index148_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Css3()) ) {s = 25;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 13;}

                         
                        input.seek(index148_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA148_14 = input.LA(1);

                         
                        int index148_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA148_14==HASH_SYMBOL) && (synpred14_Css3())) {s = 15;}

                        else if ( (LA148_14==IDENT) ) {s = 16;}

                        else if ( (LA148_14==WS||(LA148_14>=NL && LA148_14<=COMMENT)) ) {s = 14;}

                        else if ( (LA148_14==PERCENTAGE||(LA148_14>=NUMBER && LA148_14<=DIMENSION)) ) {s = 5;}

                        else if ( (LA148_14==STRING) ) {s = 6;}

                        else if ( (LA148_14==HASH) ) {s = 21;}

                        else if ( (LA148_14==GEN) ) {s = 8;}

                        else if ( (LA148_14==URI) ) {s = 9;}

                        else if ( (LA148_14==SOLIDUS||LA148_14==MINUS||LA148_14==DOT) && (synpred14_Css3())) {s = 22;}

                        else if ( (LA148_14==MEDIA_SYM||LA148_14==AT_IDENT) ) {s = 23;}

                        else if ( (LA148_14==SASS_VAR) ) {s = 24;}

                         
                        input.seek(index148_14);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA148_28 = input.LA(1);

                         
                        int index148_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Css3()) ) {s = 25;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 13;}

                         
                        input.seek(index148_28);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA148_6 = input.LA(1);

                         
                        int index148_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Css3()) ) {s = 25;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 13;}

                         
                        input.seek(index148_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA148_3 = input.LA(1);

                         
                        int index148_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_Css3()) ) {s = 22;}

                        else if ( (synpred15_Css3()) ) {s = 25;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 13;}

                         
                        input.seek(index148_3);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA148_8 = input.LA(1);

                         
                        int index148_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Css3()) ) {s = 25;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 13;}

                         
                        input.seek(index148_8);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA148_9 = input.LA(1);

                         
                        int index148_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Css3()) ) {s = 25;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 13;}

                         
                        input.seek(index148_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA148_2 = input.LA(1);

                         
                        int index148_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA148_2==WS||(LA148_2>=NL && LA148_2<=COMMENT)) ) {s = 14;}

                        else if ( (LA148_2==HASH_SYMBOL) && (synpred14_Css3())) {s = 15;}

                        else if ( (LA148_2==IDENT) ) {s = 16;}

                        else if ( (LA148_2==IMPORTANT_SYM) && (synpred14_Css3())) {s = 17;}

                        else if ( (LA148_2==SEMI) && (synpred14_Css3())) {s = 18;}

                        else if ( (LA148_2==RBRACE) && (synpred14_Css3())) {s = 19;}

                        else if ( (LA148_2==LBRACE) && (synpred14_Css3())) {s = 20;}

                        else if ( (LA148_2==PERCENTAGE||(LA148_2>=NUMBER && LA148_2<=DIMENSION)) ) {s = 5;}

                        else if ( (LA148_2==STRING) ) {s = 6;}

                        else if ( (LA148_2==HASH) ) {s = 21;}

                        else if ( (LA148_2==GEN) ) {s = 8;}

                        else if ( (LA148_2==URI) ) {s = 9;}

                        else if ( (LA148_2==SOLIDUS||LA148_2==MINUS||LA148_2==DOT) && (synpred14_Css3())) {s = 22;}

                        else if ( (LA148_2==MEDIA_SYM||LA148_2==AT_IDENT) ) {s = 23;}

                        else if ( (LA148_2==SASS_VAR) ) {s = 24;}

                         
                        input.seek(index148_2);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA148_7 = input.LA(1);

                         
                        int index148_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_Css3()) ) {s = 22;}

                        else if ( (synpred15_Css3()) ) {s = 25;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 13;}

                         
                        input.seek(index148_7);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA148_11 = input.LA(1);

                         
                        int index148_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (((synpred15_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 25;}

                        else if ( ((evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")&&evalPredicate(isLessSource(),"isLessSource()"))) ) {s = 13;}

                         
                        input.seek(index148_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA148_23 = input.LA(1);

                         
                        int index148_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Css3()) ) {s = 25;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 13;}

                         
                        input.seek(index148_23);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA148_0 = input.LA(1);

                         
                        int index148_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA148_0==HASH_SYMBOL) && (synpred14_Css3())) {s = 1;}

                        else if ( (LA148_0==MINUS) ) {s = 2;}

                        else if ( (LA148_0==IDENT) ) {s = 3;}

                        else if ( (LA148_0==PLUS) ) {s = 4;}

                        else if ( (LA148_0==PERCENTAGE||(LA148_0>=NUMBER && LA148_0<=DIMENSION)) ) {s = 5;}

                        else if ( (LA148_0==STRING) ) {s = 6;}

                        else if ( (LA148_0==HASH) ) {s = 7;}

                        else if ( (LA148_0==GEN) ) {s = 8;}

                        else if ( (LA148_0==URI) ) {s = 9;}

                        else if ( (LA148_0==SOLIDUS||LA148_0==DOT) && (synpred14_Css3())) {s = 10;}

                        else if ( (LA148_0==MEDIA_SYM||LA148_0==AT_IDENT) ) {s = 11;}

                        else if ( (LA148_0==SASS_VAR) ) {s = 12;}

                        else if ( (LA148_0==LPAREN) ) {s = 13;}

                         
                        input.seek(index148_0);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA148_12 = input.LA(1);

                         
                        int index148_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (((synpred15_Css3()&&evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()"))&&evalPredicate(isScssSource(),"isScssSource()"))) ) {s = 25;}

                        else if ( ((evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")&&evalPredicate(isScssSource(),"isScssSource()"))) ) {s = 13;}

                         
                        input.seek(index148_12);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA148_24 = input.LA(1);

                         
                        int index148_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Css3()) ) {s = 25;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 13;}

                         
                        input.seek(index148_24);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA148_21 = input.LA(1);

                         
                        int index148_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_Css3()) ) {s = 22;}

                        else if ( (synpred15_Css3()) ) {s = 25;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 13;}

                         
                        input.seek(index148_21);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA148_27 = input.LA(1);

                         
                        int index148_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Css3()) ) {s = 25;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 13;}

                         
                        input.seek(index148_27);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA148_16 = input.LA(1);

                         
                        int index148_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_Css3()) ) {s = 22;}

                        else if ( (synpred15_Css3()) ) {s = 25;}

                        else if ( (evalPredicate(isCssPreprocessorSource(),"isCssPreprocessorSource()")) ) {s = 13;}

                         
                        input.seek(index148_16);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 148, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA152_eotS =
        "\12\uffff";
    static final String DFA152_eofS =
        "\12\uffff";
    static final String DFA152_minS =
        "\1\5\1\uffff\1\6\1\uffff\1\6\1\5\1\6\1\5\2\23";
    static final String DFA152_maxS =
        "\1\125\1\uffff\1\125\1\uffff\2\125\1\6\1\125\2\123";
    static final String DFA152_acceptS =
        "\1\uffff\1\2\1\uffff\1\1\6\uffff";
    static final String DFA152_specialS =
        "\12\uffff}>";
    static final String[] DFA152_transitionS = {
            "\1\1\3\3\2\uffff\1\2\1\3\2\1\3\uffff\1\3\2\uffff\1\1\1\3\6\uffff"+
            "\1\3\23\uffff\2\3\2\uffff\1\3\1\uffff\1\3\17\uffff\1\1\12\3"+
            "\3\uffff\1\3",
            "",
            "\1\5\2\3\3\uffff\1\3\5\uffff\1\3\3\uffff\1\3\1\4\5\uffff\1"+
            "\3\24\uffff\1\3\2\uffff\1\3\1\uffff\1\3\20\uffff\12\3\2\4\1"+
            "\uffff\1\3",
            "",
            "\1\5\2\3\3\uffff\1\3\5\uffff\1\3\3\uffff\1\3\1\4\5\uffff\1"+
            "\3\24\uffff\1\3\2\uffff\1\3\1\uffff\1\3\20\uffff\12\3\2\4\1"+
            "\uffff\1\3",
            "\4\3\2\uffff\4\3\3\uffff\5\3\1\7\5\uffff\1\3\23\uffff\2\3\2"+
            "\uffff\1\3\1\uffff\1\3\1\6\7\uffff\1\1\6\uffff\13\3\2\7\1\uffff"+
            "\1\3",
            "\1\10",
            "\4\3\2\uffff\4\3\3\uffff\2\3\1\uffff\2\3\1\7\5\uffff\1\3\23"+
            "\uffff\2\3\2\uffff\1\3\1\uffff\1\3\10\uffff\1\1\6\uffff\13\3"+
            "\2\7\1\uffff\1\3",
            "\1\3\3\uffff\1\11\40\uffff\1\6\7\uffff\1\1\21\uffff\2\11",
            "\1\3\3\uffff\1\11\50\uffff\1\1\21\uffff\2\11"
    };

    static final short[] DFA152_eot = DFA.unpackEncodedString(DFA152_eotS);
    static final short[] DFA152_eof = DFA.unpackEncodedString(DFA152_eofS);
    static final char[] DFA152_min = DFA.unpackEncodedStringToUnsignedChars(DFA152_minS);
    static final char[] DFA152_max = DFA.unpackEncodedStringToUnsignedChars(DFA152_maxS);
    static final short[] DFA152_accept = DFA.unpackEncodedString(DFA152_acceptS);
    static final short[] DFA152_special = DFA.unpackEncodedString(DFA152_specialS);
    static final short[][] DFA152_transition;

    static {
        int numStates = DFA152_transitionS.length;
        DFA152_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA152_transition[i] = DFA.unpackEncodedString(DFA152_transitionS[i]);
        }
    }

    class DFA152 extends DFA {

        public DFA152(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 152;
            this.eot = DFA152_eot;
            this.eof = DFA152_eof;
            this.min = DFA152_min;
            this.max = DFA152_max;
            this.accept = DFA152_accept;
            this.special = DFA152_special;
            this.transition = DFA152_transition;
        }
        public String getDescription() {
            return "()* loopback of 824:12: ( ( operator ( ws )? )? term )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA155_eotS =
        "\13\uffff";
    static final String DFA155_eofS =
        "\3\uffff\1\12\5\uffff\1\12\1\uffff";
    static final String DFA155_minS =
        "\1\6\2\uffff\1\5\5\uffff\1\5\1\uffff";
    static final String DFA155_maxS =
        "\1\125\2\uffff\1\143\5\uffff\1\143\1\uffff";
    static final String DFA155_acceptS =
        "\1\uffff\1\1\1\2\1\uffff\1\4\1\5\1\6\1\10\1\7\1\uffff\1\3";
    static final String DFA155_specialS =
        "\13\uffff}>";
    static final String[] DFA155_transitionS = {
            "\1\3\1\2\1\5\3\uffff\1\7\5\uffff\1\4\3\uffff\1\7\6\uffff\1\1"+
            "\31\uffff\1\6\20\uffff\12\1\3\uffff\1\7",
            "",
            "",
            "\4\12\2\uffff\4\12\3\uffff\1\12\2\10\2\12\1\11\5\uffff\1\12"+
            "\23\uffff\3\12\1\uffff\1\12\1\uffff\1\12\1\10\3\uffff\1\12\12"+
            "\uffff\13\12\2\11\2\12\5\uffff\3\12\5\uffff\1\12",
            "",
            "",
            "",
            "",
            "",
            "\4\12\2\uffff\4\12\3\uffff\1\12\1\10\1\uffff\2\12\1\11\5\uffff"+
            "\1\12\23\uffff\3\12\1\uffff\1\12\1\uffff\1\12\4\uffff\1\12\12"+
            "\uffff\13\12\2\11\2\12\5\uffff\3\12\5\uffff\1\12",
            ""
    };

    static final short[] DFA155_eot = DFA.unpackEncodedString(DFA155_eotS);
    static final short[] DFA155_eof = DFA.unpackEncodedString(DFA155_eofS);
    static final char[] DFA155_min = DFA.unpackEncodedStringToUnsignedChars(DFA155_minS);
    static final char[] DFA155_max = DFA.unpackEncodedStringToUnsignedChars(DFA155_maxS);
    static final short[] DFA155_accept = DFA.unpackEncodedString(DFA155_acceptS);
    static final short[] DFA155_special = DFA.unpackEncodedString(DFA155_specialS);
    static final short[][] DFA155_transition;

    static {
        int numStates = DFA155_transitionS.length;
        DFA155_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA155_transition[i] = DFA.unpackEncodedString(DFA155_transitionS[i]);
        }
    }

    class DFA155 extends DFA {

        public DFA155(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 155;
            this.eot = DFA155_eot;
            this.eof = DFA155_eof;
            this.min = DFA155_min;
            this.max = DFA155_max;
            this.accept = DFA155_accept;
            this.special = DFA155_special;
            this.transition = DFA155_transition;
        }
        public String getDescription() {
            return "829:9: ( ( NUMBER | PERCENTAGE | LENGTH | EMS | REM | EXS | ANGLE | TIME | FREQ | RESOLUTION | DIMENSION ) | STRING | IDENT | GEN | URI | hexColor | function | {...}? cp_variable )";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA161_eotS =
        "\10\uffff";
    static final String DFA161_eofS =
        "\10\uffff";
    static final String DFA161_minS =
        "\1\6\1\uffff\3\6\1\uffff\2\23";
    static final String DFA161_maxS =
        "\1\125\1\uffff\2\125\1\6\1\uffff\2\123";
    static final String DFA161_acceptS =
        "\1\uffff\1\1\3\uffff\1\2\2\uffff";
    static final String DFA161_specialS =
        "\10\uffff}>";
    static final String[] DFA161_transitionS = {
            "\1\2\2\1\3\uffff\1\1\5\uffff\1\1\3\uffff\1\1\6\uffff\1\1\24"+
            "\uffff\1\1\2\uffff\1\1\1\uffff\1\1\20\uffff\12\1\3\uffff\1\1",
            "",
            "\3\1\2\uffff\2\1\5\uffff\5\1\1\3\5\uffff\1\1\23\uffff\2\1\2"+
            "\uffff\1\1\1\uffff\1\1\1\4\7\uffff\1\5\7\uffff\12\1\2\3\1\uffff"+
            "\1\1",
            "\3\1\2\uffff\2\1\5\uffff\2\1\1\uffff\2\1\1\3\5\uffff\1\1\23"+
            "\uffff\2\1\2\uffff\1\1\1\uffff\1\1\10\uffff\1\5\7\uffff\12\1"+
            "\2\3\1\uffff\1\1",
            "\1\6",
            "",
            "\1\1\3\uffff\1\7\40\uffff\1\4\7\uffff\1\5\21\uffff\2\7",
            "\1\1\3\uffff\1\7\50\uffff\1\5\21\uffff\2\7"
    };

    static final short[] DFA161_eot = DFA.unpackEncodedString(DFA161_eotS);
    static final short[] DFA161_eof = DFA.unpackEncodedString(DFA161_eofS);
    static final char[] DFA161_min = DFA.unpackEncodedStringToUnsignedChars(DFA161_minS);
    static final char[] DFA161_max = DFA.unpackEncodedStringToUnsignedChars(DFA161_maxS);
    static final short[] DFA161_accept = DFA.unpackEncodedString(DFA161_acceptS);
    static final short[] DFA161_special = DFA.unpackEncodedString(DFA161_specialS);
    static final short[][] DFA161_transition;

    static {
        int numStates = DFA161_transitionS.length;
        DFA161_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA161_transition[i] = DFA.unpackEncodedString(DFA161_transitionS[i]);
        }
    }

    class DFA161 extends DFA {

        public DFA161(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 161;
            this.eot = DFA161_eot;
            this.eof = DFA161_eof;
            this.min = DFA161_min;
            this.max = DFA161_max;
            this.accept = DFA161_accept;
            this.special = DFA161_special;
            this.transition = DFA161_transition;
        }
        public String getDescription() {
            return "857:3: ( expression | ( fnAttribute ( COMMA ( ws )? fnAttribute )* ) )";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA182_eotS =
        "\36\uffff";
    static final String DFA182_eofS =
        "\36\uffff";
    static final String DFA182_minS =
        "\1\5\1\uffff\2\6\10\uffff\1\6\10\0\1\6\10\0";
    static final String DFA182_maxS =
        "\1\143\1\uffff\2\125\10\uffff\1\125\10\0\1\125\10\0";
    static final String DFA182_acceptS =
        "\1\uffff\1\2\2\uffff\10\1\22\uffff";
    static final String DFA182_specialS =
        "\1\14\14\uffff\1\0\1\2\1\17\1\3\1\6\1\1\1\12\1\15\1\uffff\1\5\1"+
        "\7\1\20\1\11\1\13\1\10\1\16\1\4}>";
    static final String[] DFA182_transitionS = {
            "\1\1\1\6\1\5\1\10\2\uffff\1\1\1\12\2\1\3\uffff\1\7\2\uffff\1"+
            "\1\1\12\6\uffff\1\4\23\uffff\1\1\1\2\1\1\1\uffff\1\3\1\uffff"+
            "\1\11\4\uffff\1\1\12\uffff\1\1\12\4\2\uffff\1\1\1\13\5\uffff"+
            "\3\1\5\uffff\1\1",
            "",
            "\1\17\1\16\1\21\3\uffff\1\23\5\uffff\1\20\1\1\2\uffff\1\23"+
            "\1\14\5\uffff\1\15\24\uffff\1\1\2\uffff\1\1\1\uffff\1\22\20"+
            "\uffff\12\15\2\14\1\uffff\1\24",
            "\1\30\1\27\1\32\3\uffff\1\34\5\uffff\1\31\1\1\2\uffff\1\34"+
            "\1\25\5\uffff\1\26\24\uffff\1\1\2\uffff\1\1\1\uffff\1\33\20"+
            "\uffff\12\26\2\25\1\uffff\1\35",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\17\1\16\1\21\3\uffff\1\23\5\uffff\1\20\1\1\2\uffff\1\23"+
            "\1\14\5\uffff\1\15\24\uffff\1\1\2\uffff\1\1\1\uffff\1\22\20"+
            "\uffff\12\15\2\14\1\uffff\1\24",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\30\1\27\1\32\3\uffff\1\34\5\uffff\1\31\1\1\2\uffff\1\34"+
            "\1\25\5\uffff\1\26\24\uffff\1\1\2\uffff\1\1\1\uffff\1\33\20"+
            "\uffff\12\26\2\25\1\uffff\1\35",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff"
    };

    static final short[] DFA182_eot = DFA.unpackEncodedString(DFA182_eotS);
    static final short[] DFA182_eof = DFA.unpackEncodedString(DFA182_eofS);
    static final char[] DFA182_min = DFA.unpackEncodedStringToUnsignedChars(DFA182_minS);
    static final char[] DFA182_max = DFA.unpackEncodedStringToUnsignedChars(DFA182_maxS);
    static final short[] DFA182_accept = DFA.unpackEncodedString(DFA182_acceptS);
    static final short[] DFA182_special = DFA.unpackEncodedString(DFA182_specialS);
    static final short[][] DFA182_transition;

    static {
        int numStates = DFA182_transitionS.length;
        DFA182_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA182_transition[i] = DFA.unpackEncodedString(DFA182_transitionS[i]);
        }
    }

    class DFA182 extends DFA {

        public DFA182(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 182;
            this.eot = DFA182_eot;
            this.eof = DFA182_eof;
            this.min = DFA182_min;
            this.max = DFA182_max;
            this.accept = DFA182_accept;
            this.special = DFA182_special;
            this.transition = DFA182_transition;
        }
        public String getDescription() {
            return "()* loopback of 938:15: ( ( term )=> term )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA182_13 = input.LA(1);

                         
                        int index182_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_13);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA182_18 = input.LA(1);

                         
                        int index182_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_18);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA182_14 = input.LA(1);

                         
                        int index182_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_14);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA182_16 = input.LA(1);

                         
                        int index182_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_16);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA182_29 = input.LA(1);

                         
                        int index182_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_29);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA182_22 = input.LA(1);

                         
                        int index182_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_22);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA182_17 = input.LA(1);

                         
                        int index182_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_17);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA182_23 = input.LA(1);

                         
                        int index182_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_23);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA182_27 = input.LA(1);

                         
                        int index182_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_27);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA182_25 = input.LA(1);

                         
                        int index182_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_25);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA182_19 = input.LA(1);

                         
                        int index182_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_19);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA182_26 = input.LA(1);

                         
                        int index182_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_26);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA182_0 = input.LA(1);

                         
                        int index182_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA182_0==SEMI||LA182_0==COMMA||(LA182_0>=LBRACE && LA182_0<=RBRACE)||LA182_0==RPAREN||LA182_0==SOLIDUS||LA182_0==GREATER||LA182_0==STAR||LA182_0==IMPORTANT_SYM||LA182_0==SASS_DEFAULT||(LA182_0>=GREATER_OR_EQ && LA182_0<=LESS_OR_EQ)||LA182_0==CP_EQ) ) {s = 1;}

                        else if ( (LA182_0==PLUS) ) {s = 2;}

                        else if ( (LA182_0==MINUS) ) {s = 3;}

                        else if ( (LA182_0==PERCENTAGE||(LA182_0>=NUMBER && LA182_0<=DIMENSION)) && (synpred16_Css3())) {s = 4;}

                        else if ( (LA182_0==STRING) && (synpred16_Css3())) {s = 5;}

                        else if ( (LA182_0==IDENT) && (synpred16_Css3())) {s = 6;}

                        else if ( (LA182_0==GEN) && (synpred16_Css3())) {s = 7;}

                        else if ( (LA182_0==URI) && (synpred16_Css3())) {s = 8;}

                        else if ( (LA182_0==HASH) && (synpred16_Css3())) {s = 9;}

                        else if ( (LA182_0==MEDIA_SYM||LA182_0==AT_IDENT) && (synpred16_Css3())) {s = 10;}

                        else if ( (LA182_0==SASS_VAR) && (synpred16_Css3())) {s = 11;}

                         
                        input.seek(index182_0);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA182_20 = input.LA(1);

                         
                        int index182_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_20);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA182_28 = input.LA(1);

                         
                        int index182_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_28);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA182_15 = input.LA(1);

                         
                        int index182_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_15);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA182_24 = input.LA(1);

                         
                        int index182_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Css3()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index182_24);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 182, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA186_eotS =
        "\13\uffff";
    static final String DFA186_eofS =
        "\13\uffff";
    static final String DFA186_minS =
        "\1\6\2\uffff\1\6\5\uffff\1\6\1\uffff";
    static final String DFA186_maxS =
        "\1\125\2\uffff\1\123\5\uffff\1\123\1\uffff";
    static final String DFA186_acceptS =
        "\1\uffff\1\1\1\2\1\uffff\1\4\1\5\1\6\1\10\1\7\1\uffff\1\3";
    static final String DFA186_specialS =
        "\13\uffff}>";
    static final String[] DFA186_transitionS = {
            "\1\3\1\2\1\5\3\uffff\1\7\5\uffff\1\4\3\uffff\1\7\6\uffff\1\1"+
            "\31\uffff\1\6\20\uffff\12\1\3\uffff\1\7",
            "",
            "",
            "\1\12\4\uffff\1\12\1\uffff\1\12\5\uffff\2\10\2\uffff\1\11\40"+
            "\uffff\1\10\31\uffff\2\11",
            "",
            "",
            "",
            "",
            "",
            "\1\12\4\uffff\1\12\1\uffff\1\12\5\uffff\1\10\3\uffff\1\11\72"+
            "\uffff\2\11",
            ""
    };

    static final short[] DFA186_eot = DFA.unpackEncodedString(DFA186_eotS);
    static final short[] DFA186_eof = DFA.unpackEncodedString(DFA186_eofS);
    static final char[] DFA186_min = DFA.unpackEncodedStringToUnsignedChars(DFA186_minS);
    static final char[] DFA186_max = DFA.unpackEncodedStringToUnsignedChars(DFA186_maxS);
    static final short[] DFA186_accept = DFA.unpackEncodedString(DFA186_acceptS);
    static final short[] DFA186_special = DFA.unpackEncodedString(DFA186_specialS);
    static final short[][] DFA186_transition;

    static {
        int numStates = DFA186_transitionS.length;
        DFA186_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA186_transition[i] = DFA.unpackEncodedString(DFA186_transitionS[i]);
        }
    }

    class DFA186 extends DFA {

        public DFA186(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 186;
            this.eot = DFA186_eot;
            this.eof = DFA186_eof;
            this.min = DFA186_min;
            this.max = DFA186_max;
            this.accept = DFA186_accept;
            this.special = DFA186_special;
            this.transition = DFA186_transition;
        }
        public String getDescription() {
            return "945:9: ( ( NUMBER | PERCENTAGE | LENGTH | EMS | REM | EXS | ANGLE | TIME | FREQ | RESOLUTION | DIMENSION ) | STRING | IDENT | GEN | URI | hexColor | function | cp_variable )";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA201_eotS =
        "\4\uffff";
    static final String DFA201_eofS =
        "\4\uffff";
    static final String DFA201_minS =
        "\2\5\2\uffff";
    static final String DFA201_maxS =
        "\2\123\2\uffff";
    static final String DFA201_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA201_specialS =
        "\4\uffff}>";
    static final String[] DFA201_transitionS = {
            "\1\3\15\uffff\1\2\3\uffff\1\1\72\uffff\2\1",
            "\1\3\15\uffff\1\2\3\uffff\1\1\72\uffff\2\1",
            "",
            ""
    };

    static final short[] DFA201_eot = DFA.unpackEncodedString(DFA201_eotS);
    static final short[] DFA201_eof = DFA.unpackEncodedString(DFA201_eofS);
    static final char[] DFA201_min = DFA.unpackEncodedStringToUnsignedChars(DFA201_minS);
    static final char[] DFA201_max = DFA.unpackEncodedStringToUnsignedChars(DFA201_maxS);
    static final short[] DFA201_accept = DFA.unpackEncodedString(DFA201_acceptS);
    static final short[] DFA201_special = DFA.unpackEncodedString(DFA201_specialS);
    static final short[][] DFA201_transition;

    static {
        int numStates = DFA201_transitionS.length;
        DFA201_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA201_transition[i] = DFA.unpackEncodedString(DFA201_transitionS[i]);
        }
    }

    class DFA201 extends DFA {

        public DFA201(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 201;
            this.eot = DFA201_eot;
            this.eof = DFA201_eof;
            this.min = DFA201_min;
            this.max = DFA201_max;
            this.accept = DFA201_accept;
            this.special = DFA201_special;
            this.transition = DFA201_transition;
        }
        public String getDescription() {
            return "993:5: ( ( ws )? LPAREN ( cp_mixin_call_args )? RPAREN )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA206_eotS =
        "\5\uffff";
    static final String DFA206_eofS =
        "\5\uffff";
    static final String DFA206_minS =
        "\1\5\1\14\1\uffff\1\14\1\uffff";
    static final String DFA206_maxS =
        "\1\25\1\131\1\uffff\1\131\1\uffff";
    static final String DFA206_acceptS =
        "\2\uffff\1\2\1\uffff\1\1";
    static final String DFA206_specialS =
        "\5\uffff}>";
    static final String[] DFA206_transitionS = {
            "\1\1\5\uffff\1\1\11\uffff\1\2",
            "\1\4\11\uffff\1\4\1\3\72\uffff\2\3\1\uffff\1\4\2\uffff\2\2",
            "",
            "\1\4\11\uffff\1\4\1\3\72\uffff\2\3\1\uffff\1\4\2\uffff\2\2",
            ""
    };

    static final short[] DFA206_eot = DFA.unpackEncodedString(DFA206_eotS);
    static final short[] DFA206_eof = DFA.unpackEncodedString(DFA206_eofS);
    static final char[] DFA206_min = DFA.unpackEncodedStringToUnsignedChars(DFA206_minS);
    static final char[] DFA206_max = DFA.unpackEncodedStringToUnsignedChars(DFA206_maxS);
    static final short[] DFA206_accept = DFA.unpackEncodedString(DFA206_acceptS);
    static final short[] DFA206_special = DFA.unpackEncodedString(DFA206_specialS);
    static final short[][] DFA206_transition;

    static {
        int numStates = DFA206_transitionS.length;
        DFA206_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA206_transition[i] = DFA.unpackEncodedString(DFA206_transitionS[i]);
        }
    }

    class DFA206 extends DFA {

        public DFA206(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 206;
            this.eot = DFA206_eot;
            this.eof = DFA206_eof;
            this.min = DFA206_min;
            this.max = DFA206_max;
            this.accept = DFA206_accept;
            this.special = DFA206_special;
            this.transition = DFA206_transition;
        }
        public String getDescription() {
            return "()* loopback of 1013:16: ( ( COMMA | SEMI ) ( ws )? less_arg )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA226_eotS =
        "\16\uffff";
    static final String DFA226_eofS =
        "\16\uffff";
    static final String DFA226_minS =
        "\2\6\1\uffff\2\5\1\6\1\16\1\6\1\16\1\6\1\uffff\1\16\1\6\1\uffff";
    static final String DFA226_maxS =
        "\1\70\1\123\1\uffff\2\146\5\123\1\uffff\2\123\1\uffff";
    static final String DFA226_acceptS =
        "\2\uffff\1\2\7\uffff\1\1\2\uffff\1\1";
    static final String DFA226_specialS =
        "\5\uffff\1\6\1\3\1\2\1\0\1\4\1\uffff\1\1\1\5\1\uffff}>";
    static final String[] DFA226_transitionS = {
            "\1\2\15\uffff\1\2\40\uffff\1\2\1\1\2\2",
            "\1\2\6\uffff\1\3\6\uffff\1\2\2\uffff\1\2\35\uffff\4\2\31\uffff"+
            "\2\2",
            "",
            "\1\2\1\7\5\uffff\1\5\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1"+
            "\uffff\1\10\1\4\35\uffff\11\2\1\uffff\1\2\22\uffff\2\4\1\uffff"+
            "\1\6\2\2\6\uffff\1\2\1\uffff\3\2\1\uffff\3\2",
            "\1\2\1\7\5\uffff\1\5\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1"+
            "\uffff\1\10\1\4\35\uffff\11\2\1\uffff\1\2\22\uffff\2\4\1\uffff"+
            "\1\6\2\2\6\uffff\1\2\1\uffff\3\2\1\uffff\3\2",
            "\1\2\6\uffff\1\2\1\12\6\2\2\uffff\1\11\35\uffff\4\2\31\uffff"+
            "\2\11",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\15\1\2\2\uffff\1"+
            "\14\32\uffff\14\2\1\uffff\1\2\22\uffff\2\14",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\6\uffff\1\2\1\12\6\2\2\uffff\1\11\35\uffff\4\2\31\uffff"+
            "\2\11",
            "",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\15\1\2\2\uffff\1"+
            "\14\32\uffff\14\2\1\uffff\1\2\22\uffff\2\14",
            ""
    };

    static final short[] DFA226_eot = DFA.unpackEncodedString(DFA226_eotS);
    static final short[] DFA226_eof = DFA.unpackEncodedString(DFA226_eofS);
    static final char[] DFA226_min = DFA.unpackEncodedStringToUnsignedChars(DFA226_minS);
    static final char[] DFA226_max = DFA.unpackEncodedStringToUnsignedChars(DFA226_maxS);
    static final short[] DFA226_accept = DFA.unpackEncodedString(DFA226_acceptS);
    static final short[] DFA226_special = DFA.unpackEncodedString(DFA226_specialS);
    static final short[][] DFA226_transition;

    static {
        int numStates = DFA226_transitionS.length;
        DFA226_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA226_transition[i] = DFA.unpackEncodedString(DFA226_transitionS[i]);
        }
    }

    class DFA226 extends DFA {

        public DFA226(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 226;
            this.eot = DFA226_eot;
            this.eof = DFA226_eof;
            this.min = DFA226_min;
            this.max = DFA226_max;
            this.accept = DFA226_accept;
            this.special = DFA226_special;
            this.transition = DFA226_transition;
        }
        public String getDescription() {
            return "1080:9: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) )";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA226_8 = input.LA(1);

                         
                        int index226_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA226_8==WS||(LA226_8>=NL && LA226_8<=COMMENT)) ) {s = 11;}

                        else if ( (LA226_8==RBRACE) && (synpred17_Css3())) {s = 10;}

                        else if ( (LA226_8==COLON) ) {s = 2;}

                         
                        input.seek(index226_8);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA226_11 = input.LA(1);

                         
                        int index226_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA226_11==RBRACE) && (synpred17_Css3())) {s = 10;}

                        else if ( (LA226_11==WS||(LA226_11>=NL && LA226_11<=COMMENT)) ) {s = 11;}

                        else if ( (LA226_11==COLON) ) {s = 2;}

                         
                        input.seek(index226_11);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA226_7 = input.LA(1);

                         
                        int index226_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA226_7==WS||(LA226_7>=NL && LA226_7<=COMMENT)) ) {s = 12;}

                        else if ( (LA226_7==LPAREN) && (synpred17_Css3())) {s = 13;}

                        else if ( (LA226_7==IDENT||LA226_7==COMMA||LA226_7==LBRACE||LA226_7==GEN||LA226_7==COLON||(LA226_7>=PLUS && LA226_7<=PIPE)||LA226_7==LESS_AND) ) {s = 2;}

                         
                        input.seek(index226_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA226_6 = input.LA(1);

                         
                        int index226_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA226_6==WS||(LA226_6>=NL && LA226_6<=COMMENT)) ) {s = 11;}

                        else if ( (LA226_6==RBRACE) && (synpred17_Css3())) {s = 10;}

                        else if ( (LA226_6==COLON) ) {s = 2;}

                         
                        input.seek(index226_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA226_9 = input.LA(1);

                         
                        int index226_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA226_9==RBRACE) && (synpred17_Css3())) {s = 10;}

                        else if ( (LA226_9==WS||(LA226_9>=NL && LA226_9<=COMMENT)) ) {s = 9;}

                        else if ( (LA226_9==IDENT||LA226_9==LBRACE||(LA226_9>=AND && LA226_9<=COLON)||(LA226_9>=MINUS && LA226_9<=DOT)) ) {s = 2;}

                         
                        input.seek(index226_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA226_12 = input.LA(1);

                         
                        int index226_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA226_12==LPAREN) && (synpred17_Css3())) {s = 13;}

                        else if ( (LA226_12==WS||(LA226_12>=NL && LA226_12<=COMMENT)) ) {s = 12;}

                        else if ( (LA226_12==IDENT||LA226_12==COMMA||LA226_12==LBRACE||LA226_12==GEN||LA226_12==COLON||(LA226_12>=PLUS && LA226_12<=PIPE)||LA226_12==LESS_AND) ) {s = 2;}

                         
                        input.seek(index226_12);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA226_5 = input.LA(1);

                         
                        int index226_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA226_5==WS||(LA226_5>=NL && LA226_5<=COMMENT)) ) {s = 9;}

                        else if ( (LA226_5==RBRACE) && (synpred17_Css3())) {s = 10;}

                        else if ( (LA226_5==IDENT||LA226_5==LBRACE||(LA226_5>=AND && LA226_5<=COLON)||(LA226_5>=MINUS && LA226_5<=DOT)) ) {s = 2;}

                         
                        input.seek(index226_5);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 226, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA229_eotS =
        "\4\uffff";
    static final String DFA229_eofS =
        "\4\uffff";
    static final String DFA229_minS =
        "\2\6\2\uffff";
    static final String DFA229_maxS =
        "\2\123\2\uffff";
    static final String DFA229_acceptS =
        "\2\uffff\1\2\1\1";
    static final String DFA229_specialS =
        "\4\uffff}>";
    static final String[] DFA229_transitionS = {
            "\1\3\6\uffff\1\2\6\uffff\1\3\2\uffff\1\1\35\uffff\4\3\31\uffff"+
            "\2\1",
            "\1\3\6\uffff\1\2\6\uffff\1\3\2\uffff\1\1\35\uffff\4\3\31\uffff"+
            "\2\1",
            "",
            ""
    };

    static final short[] DFA229_eot = DFA.unpackEncodedString(DFA229_eotS);
    static final short[] DFA229_eof = DFA.unpackEncodedString(DFA229_eofS);
    static final char[] DFA229_min = DFA.unpackEncodedStringToUnsignedChars(DFA229_minS);
    static final char[] DFA229_max = DFA.unpackEncodedStringToUnsignedChars(DFA229_maxS);
    static final short[] DFA229_accept = DFA.unpackEncodedString(DFA229_acceptS);
    static final short[] DFA229_special = DFA.unpackEncodedString(DFA229_specialS);
    static final short[][] DFA229_transition;

    static {
        int numStates = DFA229_transitionS.length;
        DFA229_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA229_transition[i] = DFA.unpackEncodedString(DFA229_transitionS[i]);
        }
    }

    class DFA229 extends DFA {

        public DFA229(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 229;
            this.eot = DFA229_eot;
            this.eof = DFA229_eof;
            this.min = DFA229_min;
            this.max = DFA229_max;
            this.accept = DFA229_accept;
            this.special = DFA229_special;
            this.transition = DFA229_transition;
        }
        public String getDescription() {
            return "()* loopback of 1085:9: ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) ) )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA228_eotS =
        "\16\uffff";
    static final String DFA228_eofS =
        "\16\uffff";
    static final String DFA228_minS =
        "\2\6\1\uffff\2\5\1\6\1\16\1\6\1\16\1\6\1\uffff\1\16\1\6\1\uffff";
    static final String DFA228_maxS =
        "\1\70\1\123\1\uffff\2\146\5\123\1\uffff\2\123\1\uffff";
    static final String DFA228_acceptS =
        "\2\uffff\1\2\7\uffff\1\1\2\uffff\1\1";
    static final String DFA228_specialS =
        "\5\uffff\1\6\1\3\1\2\1\0\1\4\1\uffff\1\1\1\5\1\uffff}>";
    static final String[] DFA228_transitionS = {
            "\1\2\15\uffff\1\2\40\uffff\1\2\1\1\2\2",
            "\1\2\6\uffff\1\3\6\uffff\1\2\2\uffff\1\2\35\uffff\4\2\31\uffff"+
            "\2\2",
            "",
            "\1\2\1\7\5\uffff\1\5\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1"+
            "\uffff\1\10\1\4\35\uffff\11\2\1\uffff\1\2\22\uffff\2\4\1\uffff"+
            "\1\6\2\2\6\uffff\1\2\1\uffff\3\2\1\uffff\3\2",
            "\1\2\1\7\5\uffff\1\5\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1"+
            "\uffff\1\10\1\4\35\uffff\11\2\1\uffff\1\2\22\uffff\2\4\1\uffff"+
            "\1\6\2\2\6\uffff\1\2\1\uffff\3\2\1\uffff\3\2",
            "\1\2\6\uffff\1\2\1\12\6\2\2\uffff\1\11\35\uffff\4\2\31\uffff"+
            "\2\11",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\15\1\2\2\uffff\1"+
            "\14\32\uffff\14\2\1\uffff\1\2\22\uffff\2\14",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\6\uffff\1\2\1\12\6\2\2\uffff\1\11\35\uffff\4\2\31\uffff"+
            "\2\11",
            "",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\15\1\2\2\uffff\1"+
            "\14\32\uffff\14\2\1\uffff\1\2\22\uffff\2\14",
            ""
    };

    static final short[] DFA228_eot = DFA.unpackEncodedString(DFA228_eotS);
    static final short[] DFA228_eof = DFA.unpackEncodedString(DFA228_eofS);
    static final char[] DFA228_min = DFA.unpackEncodedStringToUnsignedChars(DFA228_minS);
    static final char[] DFA228_max = DFA.unpackEncodedStringToUnsignedChars(DFA228_maxS);
    static final short[] DFA228_accept = DFA.unpackEncodedString(DFA228_acceptS);
    static final short[] DFA228_special = DFA.unpackEncodedString(DFA228_specialS);
    static final short[][] DFA228_transition;

    static {
        int numStates = DFA228_transitionS.length;
        DFA228_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA228_transition[i] = DFA.unpackEncodedString(DFA228_transitionS[i]);
        }
    }

    class DFA228 extends DFA {

        public DFA228(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 228;
            this.eot = DFA228_eot;
            this.eof = DFA228_eof;
            this.min = DFA228_min;
            this.max = DFA228_max;
            this.accept = DFA228_accept;
            this.special = DFA228_special;
            this.transition = DFA228_transition;
        }
        public String getDescription() {
            return "1087:13: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON ) )";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA228_8 = input.LA(1);

                         
                        int index228_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA228_8==WS||(LA228_8>=NL && LA228_8<=COMMENT)) ) {s = 11;}

                        else if ( (LA228_8==RBRACE) && (synpred18_Css3())) {s = 10;}

                        else if ( (LA228_8==COLON) ) {s = 2;}

                         
                        input.seek(index228_8);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA228_11 = input.LA(1);

                         
                        int index228_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA228_11==RBRACE) && (synpred18_Css3())) {s = 10;}

                        else if ( (LA228_11==WS||(LA228_11>=NL && LA228_11<=COMMENT)) ) {s = 11;}

                        else if ( (LA228_11==COLON) ) {s = 2;}

                         
                        input.seek(index228_11);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA228_7 = input.LA(1);

                         
                        int index228_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA228_7==WS||(LA228_7>=NL && LA228_7<=COMMENT)) ) {s = 12;}

                        else if ( (LA228_7==LPAREN) && (synpred18_Css3())) {s = 13;}

                        else if ( (LA228_7==IDENT||LA228_7==COMMA||LA228_7==LBRACE||LA228_7==GEN||LA228_7==COLON||(LA228_7>=PLUS && LA228_7<=PIPE)||LA228_7==LESS_AND) ) {s = 2;}

                         
                        input.seek(index228_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA228_6 = input.LA(1);

                         
                        int index228_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA228_6==WS||(LA228_6>=NL && LA228_6<=COMMENT)) ) {s = 11;}

                        else if ( (LA228_6==RBRACE) && (synpred18_Css3())) {s = 10;}

                        else if ( (LA228_6==COLON) ) {s = 2;}

                         
                        input.seek(index228_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA228_9 = input.LA(1);

                         
                        int index228_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA228_9==RBRACE) && (synpred18_Css3())) {s = 10;}

                        else if ( (LA228_9==WS||(LA228_9>=NL && LA228_9<=COMMENT)) ) {s = 9;}

                        else if ( (LA228_9==IDENT||LA228_9==LBRACE||(LA228_9>=AND && LA228_9<=COLON)||(LA228_9>=MINUS && LA228_9<=DOT)) ) {s = 2;}

                         
                        input.seek(index228_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA228_12 = input.LA(1);

                         
                        int index228_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA228_12==LPAREN) && (synpred18_Css3())) {s = 13;}

                        else if ( (LA228_12==WS||(LA228_12>=NL && LA228_12<=COMMENT)) ) {s = 12;}

                        else if ( (LA228_12==IDENT||LA228_12==COMMA||LA228_12==LBRACE||LA228_12==GEN||LA228_12==COLON||(LA228_12>=PLUS && LA228_12<=PIPE)||LA228_12==LESS_AND) ) {s = 2;}

                         
                        input.seek(index228_12);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA228_5 = input.LA(1);

                         
                        int index228_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA228_5==WS||(LA228_5>=NL && LA228_5<=COMMENT)) ) {s = 9;}

                        else if ( (LA228_5==RBRACE) && (synpred18_Css3())) {s = 10;}

                        else if ( (LA228_5==IDENT||LA228_5==LBRACE||(LA228_5>=AND && LA228_5<=COLON)||(LA228_5>=MINUS && LA228_5<=DOT)) ) {s = 2;}

                         
                        input.seek(index228_5);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 228, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA233_eotS =
        "\4\uffff";
    static final String DFA233_eofS =
        "\4\uffff";
    static final String DFA233_minS =
        "\2\6\2\uffff";
    static final String DFA233_maxS =
        "\2\123\2\uffff";
    static final String DFA233_acceptS =
        "\2\uffff\1\2\1\1";
    static final String DFA233_specialS =
        "\4\uffff}>";
    static final String[] DFA233_transitionS = {
            "\1\3\15\uffff\1\2\2\uffff\1\1\35\uffff\4\3\31\uffff\2\1",
            "\1\3\15\uffff\1\2\2\uffff\1\1\35\uffff\4\3\31\uffff\2\1",
            "",
            ""
    };

    static final short[] DFA233_eot = DFA.unpackEncodedString(DFA233_eotS);
    static final short[] DFA233_eof = DFA.unpackEncodedString(DFA233_eofS);
    static final char[] DFA233_min = DFA.unpackEncodedStringToUnsignedChars(DFA233_minS);
    static final char[] DFA233_max = DFA.unpackEncodedStringToUnsignedChars(DFA233_maxS);
    static final short[] DFA233_accept = DFA.unpackEncodedString(DFA233_acceptS);
    static final short[] DFA233_special = DFA.unpackEncodedString(DFA233_specialS);
    static final short[][] DFA233_transition;

    static {
        int numStates = DFA233_transitionS.length;
        DFA233_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA233_transition[i] = DFA.unpackEncodedString(DFA233_transitionS[i]);
        }
    }

    class DFA233 extends DFA {

        public DFA233(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 233;
            this.eot = DFA233_eot;
            this.eof = DFA233_eof;
            this.min = DFA233_min;
            this.max = DFA233_max;
            this.accept = DFA233_accept;
            this.special = DFA233_special;
            this.transition = DFA233_transition;
        }
        public String getDescription() {
            return "()* loopback of 1103:9: ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH ) ) )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA234_eotS =
        "\16\uffff";
    static final String DFA234_eofS =
        "\16\uffff";
    static final String DFA234_minS =
        "\1\6\1\5\1\uffff\2\5\1\6\1\16\1\6\1\16\1\6\1\uffff\1\16\1\6\1\uffff";
    static final String DFA234_maxS =
        "\1\70\1\123\1\uffff\2\146\5\123\1\uffff\2\123\1\uffff";
    static final String DFA234_acceptS =
        "\2\uffff\1\2\7\uffff\1\1\2\uffff\1\1";
    static final String DFA234_specialS =
        "\5\uffff\1\6\1\3\1\2\1\0\1\4\1\uffff\1\1\1\5\1\uffff}>";
    static final String[] DFA234_transitionS = {
            "\1\2\52\uffff\1\2\3\uffff\1\2\1\1\2\2",
            "\2\2\6\uffff\1\3\1\2\10\uffff\1\2\31\uffff\1\2\3\uffff\4\2"+
            "\16\uffff\1\2\12\uffff\2\2",
            "",
            "\1\2\1\7\5\uffff\1\5\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1"+
            "\uffff\1\10\1\4\35\uffff\11\2\1\uffff\1\2\22\uffff\2\4\1\uffff"+
            "\1\6\2\2\6\uffff\1\2\1\uffff\3\2\1\uffff\3\2",
            "\1\2\1\7\5\uffff\1\5\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1"+
            "\uffff\1\10\1\4\35\uffff\11\2\1\uffff\1\2\22\uffff\2\4\1\uffff"+
            "\1\6\2\2\6\uffff\1\2\1\uffff\3\2\1\uffff\3\2",
            "\1\2\6\uffff\1\2\1\12\6\2\2\uffff\1\11\35\uffff\4\2\31\uffff"+
            "\2\11",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\15\1\2\2\uffff\1"+
            "\14\32\uffff\14\2\1\uffff\1\2\22\uffff\2\14",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\6\uffff\1\2\1\12\6\2\2\uffff\1\11\35\uffff\4\2\31\uffff"+
            "\2\11",
            "",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\15\1\2\2\uffff\1"+
            "\14\32\uffff\14\2\1\uffff\1\2\22\uffff\2\14",
            ""
    };

    static final short[] DFA234_eot = DFA.unpackEncodedString(DFA234_eotS);
    static final short[] DFA234_eof = DFA.unpackEncodedString(DFA234_eofS);
    static final char[] DFA234_min = DFA.unpackEncodedStringToUnsignedChars(DFA234_minS);
    static final char[] DFA234_max = DFA.unpackEncodedStringToUnsignedChars(DFA234_maxS);
    static final short[] DFA234_accept = DFA.unpackEncodedString(DFA234_acceptS);
    static final short[] DFA234_special = DFA.unpackEncodedString(DFA234_specialS);
    static final short[][] DFA234_transition;

    static {
        int numStates = DFA234_transitionS.length;
        DFA234_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA234_transition[i] = DFA.unpackEncodedString(DFA234_transitionS[i]);
        }
    }

    class DFA234 extends DFA {

        public DFA234(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 234;
            this.eot = DFA234_eot;
            this.eof = DFA234_eof;
            this.min = DFA234_min;
            this.max = DFA234_max;
            this.accept = DFA234_accept;
            this.special = DFA234_special;
            this.transition = DFA234_transition;
        }
        public String getDescription() {
            return "1116:9: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS ) )";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA234_8 = input.LA(1);

                         
                        int index234_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA234_8==WS||(LA234_8>=NL && LA234_8<=COMMENT)) ) {s = 11;}

                        else if ( (LA234_8==RBRACE) && (synpred21_Css3())) {s = 10;}

                        else if ( (LA234_8==COLON) ) {s = 2;}

                         
                        input.seek(index234_8);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA234_11 = input.LA(1);

                         
                        int index234_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA234_11==RBRACE) && (synpred21_Css3())) {s = 10;}

                        else if ( (LA234_11==WS||(LA234_11>=NL && LA234_11<=COMMENT)) ) {s = 11;}

                        else if ( (LA234_11==COLON) ) {s = 2;}

                         
                        input.seek(index234_11);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA234_7 = input.LA(1);

                         
                        int index234_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA234_7==WS||(LA234_7>=NL && LA234_7<=COMMENT)) ) {s = 12;}

                        else if ( (LA234_7==LPAREN) && (synpred21_Css3())) {s = 13;}

                        else if ( (LA234_7==IDENT||LA234_7==COMMA||LA234_7==LBRACE||LA234_7==GEN||LA234_7==COLON||(LA234_7>=PLUS && LA234_7<=PIPE)||LA234_7==LESS_AND) ) {s = 2;}

                         
                        input.seek(index234_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA234_6 = input.LA(1);

                         
                        int index234_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA234_6==WS||(LA234_6>=NL && LA234_6<=COMMENT)) ) {s = 11;}

                        else if ( (LA234_6==RBRACE) && (synpred21_Css3())) {s = 10;}

                        else if ( (LA234_6==COLON) ) {s = 2;}

                         
                        input.seek(index234_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA234_9 = input.LA(1);

                         
                        int index234_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA234_9==RBRACE) && (synpred21_Css3())) {s = 10;}

                        else if ( (LA234_9==WS||(LA234_9>=NL && LA234_9<=COMMENT)) ) {s = 9;}

                        else if ( (LA234_9==IDENT||LA234_9==LBRACE||(LA234_9>=AND && LA234_9<=COLON)||(LA234_9>=MINUS && LA234_9<=DOT)) ) {s = 2;}

                         
                        input.seek(index234_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA234_12 = input.LA(1);

                         
                        int index234_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA234_12==LPAREN) && (synpred21_Css3())) {s = 13;}

                        else if ( (LA234_12==WS||(LA234_12>=NL && LA234_12<=COMMENT)) ) {s = 12;}

                        else if ( (LA234_12==IDENT||LA234_12==COMMA||LA234_12==LBRACE||LA234_12==GEN||LA234_12==COLON||(LA234_12>=PLUS && LA234_12<=PIPE)||LA234_12==LESS_AND) ) {s = 2;}

                         
                        input.seek(index234_12);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA234_5 = input.LA(1);

                         
                        int index234_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA234_5==WS||(LA234_5>=NL && LA234_5<=COMMENT)) ) {s = 9;}

                        else if ( (LA234_5==RBRACE) && (synpred21_Css3())) {s = 10;}

                        else if ( (LA234_5==IDENT||LA234_5==LBRACE||(LA234_5>=AND && LA234_5<=COLON)||(LA234_5>=MINUS && LA234_5<=DOT)) ) {s = 2;}

                         
                        input.seek(index234_5);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 234, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA236_eotS =
        "\16\uffff";
    static final String DFA236_eofS =
        "\16\uffff";
    static final String DFA236_minS =
        "\1\6\1\5\1\uffff\2\5\1\6\1\16\1\6\1\16\1\6\1\uffff\1\16\1\6\1\uffff";
    static final String DFA236_maxS =
        "\1\70\1\123\1\uffff\2\146\5\123\1\uffff\2\123\1\uffff";
    static final String DFA236_acceptS =
        "\2\uffff\1\2\7\uffff\1\1\2\uffff\1\1";
    static final String DFA236_specialS =
        "\5\uffff\1\6\1\3\1\2\1\0\1\4\1\uffff\1\1\1\5\1\uffff}>";
    static final String[] DFA236_transitionS = {
            "\1\2\52\uffff\1\2\3\uffff\1\2\1\1\2\2",
            "\2\2\6\uffff\1\3\1\2\10\uffff\1\2\31\uffff\1\2\3\uffff\4\2"+
            "\16\uffff\1\2\12\uffff\2\2",
            "",
            "\1\2\1\7\5\uffff\1\5\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1"+
            "\uffff\1\10\1\4\35\uffff\11\2\1\uffff\1\2\22\uffff\2\4\1\uffff"+
            "\1\6\2\2\6\uffff\1\2\1\uffff\3\2\1\uffff\3\2",
            "\1\2\1\7\5\uffff\1\5\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1"+
            "\uffff\1\10\1\4\35\uffff\11\2\1\uffff\1\2\22\uffff\2\4\1\uffff"+
            "\1\6\2\2\6\uffff\1\2\1\uffff\3\2\1\uffff\3\2",
            "\1\2\6\uffff\1\2\1\12\6\2\2\uffff\1\11\35\uffff\4\2\31\uffff"+
            "\2\11",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\15\1\2\2\uffff\1"+
            "\14\32\uffff\14\2\1\uffff\1\2\22\uffff\2\14",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\6\uffff\1\2\1\12\6\2\2\uffff\1\11\35\uffff\4\2\31\uffff"+
            "\2\11",
            "",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\15\1\2\2\uffff\1"+
            "\14\32\uffff\14\2\1\uffff\1\2\22\uffff\2\14",
            ""
    };

    static final short[] DFA236_eot = DFA.unpackEncodedString(DFA236_eotS);
    static final short[] DFA236_eof = DFA.unpackEncodedString(DFA236_eofS);
    static final char[] DFA236_min = DFA.unpackEncodedStringToUnsignedChars(DFA236_minS);
    static final char[] DFA236_max = DFA.unpackEncodedStringToUnsignedChars(DFA236_maxS);
    static final short[] DFA236_accept = DFA.unpackEncodedString(DFA236_acceptS);
    static final short[] DFA236_special = DFA.unpackEncodedString(DFA236_specialS);
    static final short[][] DFA236_transition;

    static {
        int numStates = DFA236_transitionS.length;
        DFA236_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA236_transition[i] = DFA.unpackEncodedString(DFA236_transitionS[i]);
        }
    }

    class DFA236 extends DFA {

        public DFA236(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 236;
            this.eot = DFA236_eot;
            this.eof = DFA236_eof;
            this.min = DFA236_min;
            this.max = DFA236_max;
            this.accept = DFA236_accept;
            this.special = DFA236_special;
            this.transition = DFA236_transition;
        }
        public String getDescription() {
            return "1123:13: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | SOLIDUS ) )";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA236_8 = input.LA(1);

                         
                        int index236_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA236_8==WS||(LA236_8>=NL && LA236_8<=COMMENT)) ) {s = 11;}

                        else if ( (LA236_8==RBRACE) && (synpred22_Css3())) {s = 10;}

                        else if ( (LA236_8==COLON) ) {s = 2;}

                         
                        input.seek(index236_8);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA236_11 = input.LA(1);

                         
                        int index236_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA236_11==RBRACE) && (synpred22_Css3())) {s = 10;}

                        else if ( (LA236_11==WS||(LA236_11>=NL && LA236_11<=COMMENT)) ) {s = 11;}

                        else if ( (LA236_11==COLON) ) {s = 2;}

                         
                        input.seek(index236_11);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA236_7 = input.LA(1);

                         
                        int index236_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA236_7==WS||(LA236_7>=NL && LA236_7<=COMMENT)) ) {s = 12;}

                        else if ( (LA236_7==LPAREN) && (synpred22_Css3())) {s = 13;}

                        else if ( (LA236_7==IDENT||LA236_7==COMMA||LA236_7==LBRACE||LA236_7==GEN||LA236_7==COLON||(LA236_7>=PLUS && LA236_7<=PIPE)||LA236_7==LESS_AND) ) {s = 2;}

                         
                        input.seek(index236_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA236_6 = input.LA(1);

                         
                        int index236_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA236_6==WS||(LA236_6>=NL && LA236_6<=COMMENT)) ) {s = 11;}

                        else if ( (LA236_6==RBRACE) && (synpred22_Css3())) {s = 10;}

                        else if ( (LA236_6==COLON) ) {s = 2;}

                         
                        input.seek(index236_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA236_9 = input.LA(1);

                         
                        int index236_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA236_9==RBRACE) && (synpred22_Css3())) {s = 10;}

                        else if ( (LA236_9==WS||(LA236_9>=NL && LA236_9<=COMMENT)) ) {s = 9;}

                        else if ( (LA236_9==IDENT||LA236_9==LBRACE||(LA236_9>=AND && LA236_9<=COLON)||(LA236_9>=MINUS && LA236_9<=DOT)) ) {s = 2;}

                         
                        input.seek(index236_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA236_12 = input.LA(1);

                         
                        int index236_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA236_12==LPAREN) && (synpred22_Css3())) {s = 13;}

                        else if ( (LA236_12==WS||(LA236_12>=NL && LA236_12<=COMMENT)) ) {s = 12;}

                        else if ( (LA236_12==IDENT||LA236_12==COMMA||LA236_12==LBRACE||LA236_12==GEN||LA236_12==COLON||(LA236_12>=PLUS && LA236_12<=PIPE)||LA236_12==LESS_AND) ) {s = 2;}

                         
                        input.seek(index236_12);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA236_5 = input.LA(1);

                         
                        int index236_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA236_5==WS||(LA236_5>=NL && LA236_5<=COMMENT)) ) {s = 9;}

                        else if ( (LA236_5==RBRACE) && (synpred22_Css3())) {s = 10;}

                        else if ( (LA236_5==IDENT||LA236_5==LBRACE||(LA236_5>=AND && LA236_5<=COLON)||(LA236_5>=MINUS && LA236_5<=DOT)) ) {s = 2;}

                         
                        input.seek(index236_5);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 236, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA238_eotS =
        "\17\uffff";
    static final String DFA238_eofS =
        "\17\uffff";
    static final String DFA238_minS =
        "\2\6\1\uffff\3\6\1\16\3\6\1\uffff\1\16\1\6\1\uffff\1\6";
    static final String DFA238_maxS =
        "\1\70\1\123\1\uffff\2\146\5\123\1\uffff\2\123\1\uffff\1\123";
    static final String DFA238_acceptS =
        "\2\uffff\1\2\7\uffff\1\1\2\uffff\1\1\1\uffff";
    static final String DFA238_specialS =
        "\5\uffff\1\1\1\6\1\0\1\2\1\3\1\uffff\1\4\1\7\1\uffff\1\5}>";
    static final String[] DFA238_transitionS = {
            "\1\2\10\uffff\1\2\1\uffff\1\2\2\uffff\1\2\40\uffff\1\2\1\1\2"+
            "\2",
            "\1\2\6\uffff\1\3\1\uffff\1\2\1\uffff\1\2\2\uffff\1\2\2\uffff"+
            "\1\2\35\uffff\4\2\31\uffff\2\2",
            "",
            "\1\7\5\uffff\1\10\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff"+
            "\1\5\1\4\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff\1\2\24\uffff\11"+
            "\2\1\uffff\1\2\22\uffff\2\4\1\uffff\1\6\1\2\7\uffff\1\2\1\uffff"+
            "\3\2\1\uffff\3\2",
            "\1\7\5\uffff\1\10\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff"+
            "\1\5\1\4\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff\1\2\24\uffff\11"+
            "\2\1\uffff\1\2\22\uffff\2\4\1\uffff\1\6\1\2\7\uffff\1\2\1\uffff"+
            "\3\2\1\uffff\3\2",
            "\2\2\5\uffff\1\2\1\12\5\uffff\1\2\2\uffff\1\11\72\uffff\2\13",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\15\1\2\2\uffff\1"+
            "\14\32\uffff\14\2\1\uffff\1\2\22\uffff\2\14",
            "\1\2\6\uffff\1\2\1\12\6\2\2\uffff\1\16\35\uffff\4\2\31\uffff"+
            "\2\16",
            "\2\2\5\uffff\1\2\1\12\5\uffff\1\2\2\uffff\1\11\72\uffff\2\13",
            "",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\15\1\2\2\uffff\1"+
            "\14\32\uffff\14\2\1\uffff\1\2\22\uffff\2\14",
            "",
            "\1\2\6\uffff\1\2\1\12\6\2\2\uffff\1\16\35\uffff\4\2\31\uffff"+
            "\2\16"
    };

    static final short[] DFA238_eot = DFA.unpackEncodedString(DFA238_eotS);
    static final short[] DFA238_eof = DFA.unpackEncodedString(DFA238_eofS);
    static final char[] DFA238_min = DFA.unpackEncodedStringToUnsignedChars(DFA238_minS);
    static final char[] DFA238_max = DFA.unpackEncodedStringToUnsignedChars(DFA238_maxS);
    static final short[] DFA238_accept = DFA.unpackEncodedString(DFA238_acceptS);
    static final short[] DFA238_special = DFA.unpackEncodedString(DFA238_specialS);
    static final short[][] DFA238_transition;

    static {
        int numStates = DFA238_transitionS.length;
        DFA238_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA238_transition[i] = DFA.unpackEncodedString(DFA238_transitionS[i]);
        }
    }

    class DFA238 extends DFA {

        public DFA238(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 238;
            this.eot = DFA238_eot;
            this.eof = DFA238_eof;
            this.min = DFA238_min;
            this.max = DFA238_max;
            this.accept = DFA238_accept;
            this.special = DFA238_special;
            this.transition = DFA238_transition;
        }
        public String getDescription() {
            return "1134:9: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) )";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA238_7 = input.LA(1);

                         
                        int index238_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA238_7==WS||(LA238_7>=NL && LA238_7<=COMMENT)) ) {s = 12;}

                        else if ( (LA238_7==LPAREN) && (synpred23_Css3())) {s = 13;}

                        else if ( (LA238_7==IDENT||LA238_7==COMMA||LA238_7==LBRACE||LA238_7==GEN||LA238_7==COLON||(LA238_7>=PLUS && LA238_7<=PIPE)||LA238_7==LESS_AND) ) {s = 2;}

                         
                        input.seek(index238_7);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA238_5 = input.LA(1);

                         
                        int index238_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA238_5==WS) ) {s = 9;}

                        else if ( (LA238_5==RBRACE) && (synpred23_Css3())) {s = 10;}

                        else if ( ((LA238_5>=IDENT && LA238_5<=STRING)||LA238_5==LBRACE||LA238_5==COLON) ) {s = 2;}

                        else if ( ((LA238_5>=NL && LA238_5<=COMMENT)) ) {s = 11;}

                         
                        input.seek(index238_5);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA238_8 = input.LA(1);

                         
                        int index238_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA238_8==WS||(LA238_8>=NL && LA238_8<=COMMENT)) ) {s = 14;}

                        else if ( (LA238_8==RBRACE) && (synpred23_Css3())) {s = 10;}

                        else if ( (LA238_8==IDENT||LA238_8==LBRACE||(LA238_8>=AND && LA238_8<=COLON)||(LA238_8>=MINUS && LA238_8<=DOT)) ) {s = 2;}

                         
                        input.seek(index238_8);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA238_9 = input.LA(1);

                         
                        int index238_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA238_9==RBRACE) && (synpred23_Css3())) {s = 10;}

                        else if ( (LA238_9==WS) ) {s = 9;}

                        else if ( ((LA238_9>=IDENT && LA238_9<=STRING)||LA238_9==LBRACE||LA238_9==COLON) ) {s = 2;}

                        else if ( ((LA238_9>=NL && LA238_9<=COMMENT)) ) {s = 11;}

                         
                        input.seek(index238_9);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA238_11 = input.LA(1);

                         
                        int index238_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA238_11==RBRACE) && (synpred23_Css3())) {s = 10;}

                        else if ( (LA238_11==WS||(LA238_11>=NL && LA238_11<=COMMENT)) ) {s = 11;}

                        else if ( (LA238_11==COLON) ) {s = 2;}

                         
                        input.seek(index238_11);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA238_14 = input.LA(1);

                         
                        int index238_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA238_14==RBRACE) && (synpred23_Css3())) {s = 10;}

                        else if ( (LA238_14==WS||(LA238_14>=NL && LA238_14<=COMMENT)) ) {s = 14;}

                        else if ( (LA238_14==IDENT||LA238_14==LBRACE||(LA238_14>=AND && LA238_14<=COLON)||(LA238_14>=MINUS && LA238_14<=DOT)) ) {s = 2;}

                         
                        input.seek(index238_14);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA238_6 = input.LA(1);

                         
                        int index238_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA238_6==WS||(LA238_6>=NL && LA238_6<=COMMENT)) ) {s = 11;}

                        else if ( (LA238_6==RBRACE) && (synpred23_Css3())) {s = 10;}

                        else if ( (LA238_6==COLON) ) {s = 2;}

                         
                        input.seek(index238_6);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA238_12 = input.LA(1);

                         
                        int index238_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA238_12==LPAREN) && (synpred23_Css3())) {s = 13;}

                        else if ( (LA238_12==WS||(LA238_12>=NL && LA238_12<=COMMENT)) ) {s = 12;}

                        else if ( (LA238_12==IDENT||LA238_12==COMMA||LA238_12==LBRACE||LA238_12==GEN||LA238_12==COLON||(LA238_12>=PLUS && LA238_12<=PIPE)||LA238_12==LESS_AND) ) {s = 2;}

                         
                        input.seek(index238_12);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 238, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA241_eotS =
        "\4\uffff";
    static final String DFA241_eofS =
        "\4\uffff";
    static final String DFA241_minS =
        "\2\6\2\uffff";
    static final String DFA241_maxS =
        "\2\123\2\uffff";
    static final String DFA241_acceptS =
        "\2\uffff\1\2\1\1";
    static final String DFA241_specialS =
        "\4\uffff}>";
    static final String[] DFA241_transitionS = {
            "\1\3\6\uffff\1\2\1\uffff\1\3\1\uffff\1\3\2\uffff\1\3\2\uffff"+
            "\1\1\35\uffff\4\3\31\uffff\2\1",
            "\1\3\6\uffff\1\2\1\uffff\1\3\1\uffff\1\3\2\uffff\1\3\2\uffff"+
            "\1\1\35\uffff\4\3\31\uffff\2\1",
            "",
            ""
    };

    static final short[] DFA241_eot = DFA.unpackEncodedString(DFA241_eotS);
    static final short[] DFA241_eof = DFA.unpackEncodedString(DFA241_eofS);
    static final char[] DFA241_min = DFA.unpackEncodedStringToUnsignedChars(DFA241_minS);
    static final char[] DFA241_max = DFA.unpackEncodedStringToUnsignedChars(DFA241_maxS);
    static final short[] DFA241_accept = DFA.unpackEncodedString(DFA241_acceptS);
    static final short[] DFA241_special = DFA.unpackEncodedString(DFA241_specialS);
    static final short[][] DFA241_transition;

    static {
        int numStates = DFA241_transitionS.length;
        DFA241_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA241_transition[i] = DFA.unpackEncodedString(DFA241_transitionS[i]);
        }
    }

    class DFA241 extends DFA {

        public DFA241(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 241;
            this.eot = DFA241_eot;
            this.eof = DFA241_eof;
            this.min = DFA241_min;
            this.max = DFA241_max;
            this.accept = DFA241_accept;
            this.special = DFA241_special;
            this.transition = DFA241_transition;
        }
        public String getDescription() {
            return "()* loopback of 1139:9: ( ( ws )? ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) ) )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA240_eotS =
        "\17\uffff";
    static final String DFA240_eofS =
        "\17\uffff";
    static final String DFA240_minS =
        "\2\6\1\uffff\3\6\1\16\3\6\1\uffff\1\16\1\6\1\uffff\1\6";
    static final String DFA240_maxS =
        "\1\70\1\123\1\uffff\2\146\5\123\1\uffff\2\123\1\uffff\1\123";
    static final String DFA240_acceptS =
        "\2\uffff\1\2\7\uffff\1\1\2\uffff\1\1\1\uffff";
    static final String DFA240_specialS =
        "\5\uffff\1\1\1\6\1\0\1\2\1\3\1\uffff\1\4\1\7\1\uffff\1\5}>";
    static final String[] DFA240_transitionS = {
            "\1\2\10\uffff\1\2\1\uffff\1\2\2\uffff\1\2\40\uffff\1\2\1\1\2"+
            "\2",
            "\1\2\6\uffff\1\3\1\uffff\1\2\1\uffff\1\2\2\uffff\1\2\2\uffff"+
            "\1\2\35\uffff\4\2\31\uffff\2\2",
            "",
            "\1\7\5\uffff\1\10\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff"+
            "\1\5\1\4\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff\1\2\24\uffff\11"+
            "\2\1\uffff\1\2\22\uffff\2\4\1\uffff\1\6\1\2\7\uffff\1\2\1\uffff"+
            "\3\2\1\uffff\3\2",
            "\1\7\5\uffff\1\10\1\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff"+
            "\1\5\1\4\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff\1\2\24\uffff\11"+
            "\2\1\uffff\1\2\22\uffff\2\4\1\uffff\1\6\1\2\7\uffff\1\2\1\uffff"+
            "\3\2\1\uffff\3\2",
            "\2\2\5\uffff\1\2\1\12\5\uffff\1\2\2\uffff\1\11\72\uffff\2\13",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\15\1\2\2\uffff\1"+
            "\14\32\uffff\14\2\1\uffff\1\2\22\uffff\2\14",
            "\1\2\6\uffff\1\2\1\12\6\2\2\uffff\1\16\35\uffff\4\2\31\uffff"+
            "\2\16",
            "\2\2\5\uffff\1\2\1\12\5\uffff\1\2\2\uffff\1\11\72\uffff\2\13",
            "",
            "\1\12\5\uffff\1\2\2\uffff\1\13\72\uffff\2\13",
            "\1\2\4\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\15\1\2\2\uffff\1"+
            "\14\32\uffff\14\2\1\uffff\1\2\22\uffff\2\14",
            "",
            "\1\2\6\uffff\1\2\1\12\6\2\2\uffff\1\16\35\uffff\4\2\31\uffff"+
            "\2\16"
    };

    static final short[] DFA240_eot = DFA.unpackEncodedString(DFA240_eotS);
    static final short[] DFA240_eof = DFA.unpackEncodedString(DFA240_eofS);
    static final char[] DFA240_min = DFA.unpackEncodedStringToUnsignedChars(DFA240_minS);
    static final char[] DFA240_max = DFA.unpackEncodedStringToUnsignedChars(DFA240_maxS);
    static final short[] DFA240_accept = DFA.unpackEncodedString(DFA240_acceptS);
    static final short[] DFA240_special = DFA.unpackEncodedString(DFA240_specialS);
    static final short[][] DFA240_transition;

    static {
        int numStates = DFA240_transitionS.length;
        DFA240_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA240_transition[i] = DFA.unpackEncodedString(DFA240_transitionS[i]);
        }
    }

    class DFA240 extends DFA {

        public DFA240(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 240;
            this.eot = DFA240_eot;
            this.eof = DFA240_eof;
            this.min = DFA240_min;
            this.max = DFA240_max;
            this.accept = DFA240_accept;
            this.special = DFA240_special;
            this.transition = DFA240_transition;
        }
        public String getDescription() {
            return "1141:13: ( ( HASH_SYMBOL LBRACE )=> scss_interpolation_expression_var | ( IDENT | MINUS | DOT | HASH_SYMBOL | HASH | COLON | AND | NOT ) )";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA240_7 = input.LA(1);

                         
                        int index240_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA240_7==WS||(LA240_7>=NL && LA240_7<=COMMENT)) ) {s = 12;}

                        else if ( (LA240_7==LPAREN) && (synpred24_Css3())) {s = 13;}

                        else if ( (LA240_7==IDENT||LA240_7==COMMA||LA240_7==LBRACE||LA240_7==GEN||LA240_7==COLON||(LA240_7>=PLUS && LA240_7<=PIPE)||LA240_7==LESS_AND) ) {s = 2;}

                         
                        input.seek(index240_7);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA240_5 = input.LA(1);

                         
                        int index240_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA240_5==WS) ) {s = 9;}

                        else if ( (LA240_5==RBRACE) && (synpred24_Css3())) {s = 10;}

                        else if ( ((LA240_5>=IDENT && LA240_5<=STRING)||LA240_5==LBRACE||LA240_5==COLON) ) {s = 2;}

                        else if ( ((LA240_5>=NL && LA240_5<=COMMENT)) ) {s = 11;}

                         
                        input.seek(index240_5);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA240_8 = input.LA(1);

                         
                        int index240_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA240_8==WS||(LA240_8>=NL && LA240_8<=COMMENT)) ) {s = 14;}

                        else if ( (LA240_8==RBRACE) && (synpred24_Css3())) {s = 10;}

                        else if ( (LA240_8==IDENT||LA240_8==LBRACE||(LA240_8>=AND && LA240_8<=COLON)||(LA240_8>=MINUS && LA240_8<=DOT)) ) {s = 2;}

                         
                        input.seek(index240_8);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA240_9 = input.LA(1);

                         
                        int index240_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA240_9==RBRACE) && (synpred24_Css3())) {s = 10;}

                        else if ( (LA240_9==WS) ) {s = 9;}

                        else if ( ((LA240_9>=IDENT && LA240_9<=STRING)||LA240_9==LBRACE||LA240_9==COLON) ) {s = 2;}

                        else if ( ((LA240_9>=NL && LA240_9<=COMMENT)) ) {s = 11;}

                         
                        input.seek(index240_9);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA240_11 = input.LA(1);

                         
                        int index240_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA240_11==RBRACE) && (synpred24_Css3())) {s = 10;}

                        else if ( (LA240_11==WS||(LA240_11>=NL && LA240_11<=COMMENT)) ) {s = 11;}

                        else if ( (LA240_11==COLON) ) {s = 2;}

                         
                        input.seek(index240_11);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA240_14 = input.LA(1);

                         
                        int index240_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA240_14==RBRACE) && (synpred24_Css3())) {s = 10;}

                        else if ( (LA240_14==WS||(LA240_14>=NL && LA240_14<=COMMENT)) ) {s = 14;}

                        else if ( (LA240_14==IDENT||LA240_14==LBRACE||(LA240_14>=AND && LA240_14<=COLON)||(LA240_14>=MINUS && LA240_14<=DOT)) ) {s = 2;}

                         
                        input.seek(index240_14);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA240_6 = input.LA(1);

                         
                        int index240_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA240_6==WS||(LA240_6>=NL && LA240_6<=COMMENT)) ) {s = 11;}

                        else if ( (LA240_6==RBRACE) && (synpred24_Css3())) {s = 10;}

                        else if ( (LA240_6==COLON) ) {s = 2;}

                         
                        input.seek(index240_6);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA240_12 = input.LA(1);

                         
                        int index240_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA240_12==LPAREN) && (synpred24_Css3())) {s = 13;}

                        else if ( (LA240_12==WS||(LA240_12>=NL && LA240_12<=COMMENT)) ) {s = 12;}

                        else if ( (LA240_12==IDENT||LA240_12==COMMA||LA240_12==LBRACE||LA240_12==GEN||LA240_12==COLON||(LA240_12>=PLUS && LA240_12<=PIPE)||LA240_12==LESS_AND) ) {s = 2;}

                         
                        input.seek(index240_12);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 240, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA259_eotS =
        "\11\uffff";
    static final String DFA259_eofS =
        "\5\uffff\1\4\3\uffff";
    static final String DFA259_minS =
        "\3\4\2\uffff\4\4";
    static final String DFA259_maxS =
        "\3\u008b\2\uffff\4\u008b";
    static final String DFA259_acceptS =
        "\3\uffff\1\1\1\2\4\uffff";
    static final String DFA259_specialS =
        "\11\uffff}>";
    static final String[] DFA259_transitionS = {
            "\1\3\1\uffff\1\2\6\3\2\uffff\5\3\1\uffff\40\3\1\2\1\1\2\2\123"+
            "\3",
            "\1\3\1\uffff\1\10\6\3\1\4\1\uffff\5\3\1\5\2\3\1\6\35\3\1\10"+
            "\1\7\2\10\31\3\2\6\70\3",
            "\1\3\1\uffff\1\10\6\3\2\uffff\5\3\1\5\2\3\1\6\35\3\1\10\1\7"+
            "\2\10\31\3\2\6\70\3",
            "",
            "",
            "\1\3\1\uffff\7\3\2\uffff\175\3",
            "\1\3\1\uffff\1\10\6\3\2\uffff\10\3\1\6\35\3\1\10\1\7\2\10\31"+
            "\3\2\6\70\3",
            "\1\3\1\uffff\1\10\6\3\1\4\1\uffff\5\3\1\5\2\3\1\6\35\3\1\10"+
            "\1\7\2\10\31\3\2\6\70\3",
            "\1\3\1\uffff\1\10\6\3\2\uffff\5\3\1\5\2\3\1\6\35\3\1\10\1\7"+
            "\2\10\31\3\2\6\70\3"
    };

    static final short[] DFA259_eot = DFA.unpackEncodedString(DFA259_eotS);
    static final short[] DFA259_eof = DFA.unpackEncodedString(DFA259_eofS);
    static final char[] DFA259_min = DFA.unpackEncodedStringToUnsignedChars(DFA259_minS);
    static final char[] DFA259_max = DFA.unpackEncodedStringToUnsignedChars(DFA259_maxS);
    static final short[] DFA259_accept = DFA.unpackEncodedString(DFA259_acceptS);
    static final short[] DFA259_special = DFA.unpackEncodedString(DFA259_specialS);
    static final short[][] DFA259_transition;

    static {
        int numStates = DFA259_transitionS.length;
        DFA259_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA259_transition[i] = DFA.unpackEncodedString(DFA259_transitionS[i]);
        }
    }

    class DFA259 extends DFA {

        public DFA259(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 259;
            this.eot = DFA259_eot;
            this.eof = DFA259_eof;
            this.min = DFA259_min;
            this.max = DFA259_max;
            this.accept = DFA259_accept;
            this.special = DFA259_special;
            this.transition = DFA259_transition;
        }
        public String getDescription() {
            return "376:17: synpred3_Css3 : ( (~ ( LBRACE | SEMI | RBRACE | COLON ) )+ COLON (~ ( SEMI | LBRACE | RBRACE ) )+ SEMI | scss_declaration_interpolation_expression COLON );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
 

    public static final BitSet FOLLOW_ws_in_styleSheet125 = new BitSet(new long[]{0xBFE00001D1541650L,0x0000007700E00000L});
    public static final BitSet FOLLOW_charSet_in_styleSheet135 = new BitSet(new long[]{0xBFE00001D1D41450L,0x0000007700EC0000L});
    public static final BitSet FOLLOW_ws_in_styleSheet137 = new BitSet(new long[]{0xBFE00001D1541450L,0x0000007700E00000L});
    public static final BitSet FOLLOW_imports_in_styleSheet151 = new BitSet(new long[]{0xBFE00001D1541050L,0x0000007700E00000L});
    public static final BitSet FOLLOW_namespaces_in_styleSheet162 = new BitSet(new long[]{0xBFE00001D1541040L,0x0000007700E00000L});
    public static final BitSet FOLLOW_body_in_styleSheet174 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_styleSheet182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namespace_in_namespaces199 = new BitSet(new long[]{0x0000000000800012L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_namespaces201 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_NAMESPACE_SYM_in_namespace217 = new BitSet(new long[]{0x00000000008001C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_namespace219 = new BitSet(new long[]{0x00000000008001C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_namespacePrefixName_in_namespace223 = new BitSet(new long[]{0x00000000008001C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_namespace225 = new BitSet(new long[]{0x00000000008001C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_resourceIdentifier_in_namespace230 = new BitSet(new long[]{0x0000000000800020L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_namespace232 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_namespace235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_namespacePrefixName248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_resourceIdentifier0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CHARSET_SYM_in_charSet286 = new BitSet(new long[]{0x0000000000800080L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_charSet288 = new BitSet(new long[]{0x0000000000800080L,0x00000000000C0000L});
    public static final BitSet FOLLOW_charSetValue_in_charSet291 = new BitSet(new long[]{0x0000000000800020L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_charSet293 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_charSet296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_charSetValue310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_importItem_in_imports324 = new BitSet(new long[]{0x0000000000800402L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_imports326 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_IMPORT_SYM_in_importItem356 = new BitSet(new long[]{0x00000000008001C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_importItem358 = new BitSet(new long[]{0x00000000008001C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_resourceIdentifier_in_importItem361 = new BitSet(new long[]{0x00000000008F0060L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_importItem363 = new BitSet(new long[]{0x00000000000F0060L});
    public static final BitSet FOLLOW_mediaQueryList_in_importItem366 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_importItem368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IMPORT_SYM_in_importItem399 = new BitSet(new long[]{0x00000000008001C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_importItem401 = new BitSet(new long[]{0x00000000008001C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_resourceIdentifier_in_importItem404 = new BitSet(new long[]{0x0000000000800800L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_importItem406 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COMMA_in_importItem410 = new BitSet(new long[]{0x00000000008001C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_importItem412 = new BitSet(new long[]{0x00000000008001C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_resourceIdentifier_in_importItem415 = new BitSet(new long[]{0x00000000000F0060L});
    public static final BitSet FOLLOW_mediaQueryList_in_importItem418 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_importItem420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MEDIA_SYM_in_media436 = new BitSet(new long[]{0x01E00000009FA040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_media438 = new BitSet(new long[]{0x01E00000001FA040L});
    public static final BitSet FOLLOW_scss_mq_interpolation_expression_in_media493 = new BitSet(new long[]{0x0000000000802000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_media495 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_mediaQueryList_in_media529 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_media558 = new BitSet(new long[]{0xBFE00001D1D45040L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_media560 = new BitSet(new long[]{0xBFE00001D1545040L,0x0000007740E00000L});
    public static final BitSet FOLLOW_declaration_in_media646 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_media648 = new BitSet(new long[]{0xBFE00001D1D45040L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_media650 = new BitSet(new long[]{0xBFE00001D1545040L,0x0000007740E00000L});
    public static final BitSet FOLLOW_sass_extend_in_media673 = new BitSet(new long[]{0xBFE00001D1D45040L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_media675 = new BitSet(new long[]{0xBFE00001D1545040L,0x0000007740E00000L});
    public static final BitSet FOLLOW_sass_debug_in_media698 = new BitSet(new long[]{0xBFE00001D1D45040L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_media700 = new BitSet(new long[]{0xBFE00001D1545040L,0x0000007740E00000L});
    public static final BitSet FOLLOW_sass_control_in_media723 = new BitSet(new long[]{0xBFE00001D1D45040L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_media725 = new BitSet(new long[]{0xBFE00001D1545040L,0x0000007740E00000L});
    public static final BitSet FOLLOW_rule_in_media763 = new BitSet(new long[]{0xBFE00001D1D45040L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_media766 = new BitSet(new long[]{0xBFE00001D1545040L,0x0000007740E00000L});
    public static final BitSet FOLLOW_page_in_media787 = new BitSet(new long[]{0xBFE00001D1D45040L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_media790 = new BitSet(new long[]{0xBFE00001D1545040L,0x0000007740E00000L});
    public static final BitSet FOLLOW_fontFace_in_media811 = new BitSet(new long[]{0xBFE00001D1D45040L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_media814 = new BitSet(new long[]{0xBFE00001D1545040L,0x0000007740E00000L});
    public static final BitSet FOLLOW_vendorAtRule_in_media835 = new BitSet(new long[]{0xBFE00001D1D45040L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_media838 = new BitSet(new long[]{0xBFE00001D1545040L,0x0000007740E00000L});
    public static final BitSet FOLLOW_media_in_media861 = new BitSet(new long[]{0xBFE00001D1D45040L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_media863 = new BitSet(new long[]{0xBFE00001D1545040L,0x0000007740E00000L});
    public static final BitSet FOLLOW_RBRACE_in_media907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mediaQuery_in_mediaQueryList923 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COMMA_in_mediaQueryList927 = new BitSet(new long[]{0x00000000008F0040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_mediaQueryList929 = new BitSet(new long[]{0x00000000000F0040L});
    public static final BitSet FOLLOW_mediaQuery_in_mediaQueryList932 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_mediaQueryOperator_in_mediaQuery951 = new BitSet(new long[]{0x0000000000870040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_mediaQuery953 = new BitSet(new long[]{0x0000000000070040L});
    public static final BitSet FOLLOW_mediaType_in_mediaQuery960 = new BitSet(new long[]{0x0000000000808002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_mediaQuery962 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_AND_in_mediaQuery967 = new BitSet(new long[]{0x00000000008F0040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_mediaQuery969 = new BitSet(new long[]{0x00000000000F0040L});
    public static final BitSet FOLLOW_mediaExpression_in_mediaQuery972 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_mediaExpression_in_mediaQuery980 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_AND_in_mediaQuery984 = new BitSet(new long[]{0x00000000008F0040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_mediaQuery986 = new BitSet(new long[]{0x00000000000F0040L});
    public static final BitSet FOLLOW_mediaExpression_in_mediaQuery989 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_set_in_mediaQueryOperator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_mediaType0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_mediaExpression1044 = new BitSet(new long[]{0x0000000000800040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_mediaExpression1046 = new BitSet(new long[]{0x0000000000800040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_mediaFeature_in_mediaExpression1049 = new BitSet(new long[]{0x0000000000B00000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_mediaExpression1051 = new BitSet(new long[]{0x0000000000300000L});
    public static final BitSet FOLLOW_COLON_in_mediaExpression1056 = new BitSet(new long[]{0x00A4000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_mediaExpression1058 = new BitSet(new long[]{0x00A4000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_expression_in_mediaExpression1061 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RPAREN_in_mediaExpression1066 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_mediaExpression1068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_mediaFeature1084 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bodyItem_in_body1100 = new BitSet(new long[]{0xBFE00001D1D41042L,0x0000007700EC0000L});
    public static final BitSet FOLLOW_ws_in_body1102 = new BitSet(new long[]{0xBFE00001D1541042L,0x0000007700E00000L});
    public static final BitSet FOLLOW_rule_in_bodyItem1127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_media_in_bodyItem1139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_page_in_bodyItem1151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_counterStyle_in_bodyItem1163 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fontFace_in_bodyItem1175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_vendorAtRule_in_bodyItem1187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cp_variable_declaration_in_bodyItem1201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cp_mixin_call_in_bodyItem1215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sass_debug_in_bodyItem1229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sass_control_in_bodyItem1243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_moz_document_in_vendorAtRule1266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_webkitKeyframes_in_vendorAtRule1270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_generic_at_rule_in_vendorAtRule1274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_atRuleId0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_IDENT_in_generic_at_rule1310 = new BitSet(new long[]{0x00000000008020C0L});
    public static final BitSet FOLLOW_WS_in_generic_at_rule1312 = new BitSet(new long[]{0x00000000008020C0L});
    public static final BitSet FOLLOW_atRuleId_in_generic_at_rule1317 = new BitSet(new long[]{0x0000000000802000L});
    public static final BitSet FOLLOW_WS_in_generic_at_rule1319 = new BitSet(new long[]{0x0000000000802000L});
    public static final BitSet FOLLOW_LBRACE_in_generic_at_rule1334 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_syncTo_RBRACE_in_generic_at_rule1346 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_generic_at_rule1356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MOZ_DOCUMENT_SYM_in_moz_document1372 = new BitSet(new long[]{0x000000000E800100L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_moz_document1374 = new BitSet(new long[]{0x000000000E800100L,0x00000000000C0000L});
    public static final BitSet FOLLOW_moz_document_function_in_moz_document1379 = new BitSet(new long[]{0x0000000000802800L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_moz_document1381 = new BitSet(new long[]{0x0000000000002800L});
    public static final BitSet FOLLOW_COMMA_in_moz_document1387 = new BitSet(new long[]{0x000000000E800100L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_moz_document1389 = new BitSet(new long[]{0x000000000E800100L,0x00000000000C0000L});
    public static final BitSet FOLLOW_moz_document_function_in_moz_document1392 = new BitSet(new long[]{0x0000000000802800L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_moz_document1394 = new BitSet(new long[]{0x0000000000002800L});
    public static final BitSet FOLLOW_LBRACE_in_moz_document1401 = new BitSet(new long[]{0xBFE00001D1D45040L,0x0000007700EC0000L});
    public static final BitSet FOLLOW_ws_in_moz_document1403 = new BitSet(new long[]{0xBFE00001D1545040L,0x0000007700E00000L});
    public static final BitSet FOLLOW_body_in_moz_document1408 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_moz_document1413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_moz_document_function0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEBKIT_KEYFRAMES_SYM_in_webkitKeyframes1454 = new BitSet(new long[]{0x00000000008000C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_webkitKeyframes1456 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_atRuleId_in_webkitKeyframes1459 = new BitSet(new long[]{0x0000000000802000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_webkitKeyframes1461 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_webkitKeyframes1466 = new BitSet(new long[]{0x0000000020804040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_webkitKeyframes1468 = new BitSet(new long[]{0x0000000020004040L});
    public static final BitSet FOLLOW_webkitKeyframesBlock_in_webkitKeyframes1475 = new BitSet(new long[]{0x0000000020804040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_webkitKeyframes1477 = new BitSet(new long[]{0x0000000020004040L});
    public static final BitSet FOLLOW_RBRACE_in_webkitKeyframes1484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_webkitKeyframeSelectors_in_webkitKeyframesBlock1497 = new BitSet(new long[]{0x0000000000802000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_webkitKeyframesBlock1499 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_webkitKeyframesBlock1504 = new BitSet(new long[]{0xBFE00001D1D45060L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_webkitKeyframesBlock1507 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_syncToFollow_in_webkitKeyframesBlock1510 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_declarations_in_webkitKeyframesBlock1514 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_webkitKeyframesBlock1517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_webkitKeyframeSelectors1532 = new BitSet(new long[]{0x0000000000800802L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_webkitKeyframeSelectors1544 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COMMA_in_webkitKeyframeSelectors1547 = new BitSet(new long[]{0x0000000020800040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_webkitKeyframeSelectors1549 = new BitSet(new long[]{0x0000000020000040L});
    public static final BitSet FOLLOW_set_in_webkitKeyframeSelectors1552 = new BitSet(new long[]{0x0000000000800802L,0x00000000000C0000L});
    public static final BitSet FOLLOW_PAGE_SYM_in_page1581 = new BitSet(new long[]{0x0000000000902040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_page1583 = new BitSet(new long[]{0x0000000000102040L});
    public static final BitSet FOLLOW_IDENT_in_page1588 = new BitSet(new long[]{0x0000000000902000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_page1590 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_pseudoPage_in_page1597 = new BitSet(new long[]{0x0000000000802000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_page1599 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_page1612 = new BitSet(new long[]{0x11E1FFFE00C45060L,0x00000000002C0000L});
    public static final BitSet FOLLOW_ws_in_page1614 = new BitSet(new long[]{0x11E1FFFE00445060L,0x0000000000200000L});
    public static final BitSet FOLLOW_declaration_in_page1669 = new BitSet(new long[]{0x0000000000004020L});
    public static final BitSet FOLLOW_margin_in_page1671 = new BitSet(new long[]{0x0000000000804020L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_page1673 = new BitSet(new long[]{0x0000000000004020L});
    public static final BitSet FOLLOW_SEMI_in_page1679 = new BitSet(new long[]{0x11E1FFFE00C45060L,0x00000000002C0000L});
    public static final BitSet FOLLOW_ws_in_page1681 = new BitSet(new long[]{0x11E1FFFE00445060L,0x0000000000200000L});
    public static final BitSet FOLLOW_declaration_in_page1685 = new BitSet(new long[]{0x0000000000004020L});
    public static final BitSet FOLLOW_margin_in_page1687 = new BitSet(new long[]{0x0000000000804020L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_page1689 = new BitSet(new long[]{0x0000000000004020L});
    public static final BitSet FOLLOW_RBRACE_in_page1704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COUNTER_STYLE_SYM_in_counterStyle1725 = new BitSet(new long[]{0x0000000000800040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_counterStyle1727 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_counterStyle1730 = new BitSet(new long[]{0x0000000000802000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_counterStyle1732 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_counterStyle1743 = new BitSet(new long[]{0xBFE00001D1D45060L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_counterStyle1745 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_syncToDeclarationsRule_in_counterStyle1748 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_declarations_in_counterStyle1752 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_counterStyle1762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FONT_FACE_SYM_in_fontFace1783 = new BitSet(new long[]{0x0000000000802000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_fontFace1785 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_fontFace1796 = new BitSet(new long[]{0xBFE00001D1D45060L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_fontFace1798 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_syncToDeclarationsRule_in_fontFace1801 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_declarations_in_fontFace1805 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_fontFace1815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_margin_sym_in_margin1830 = new BitSet(new long[]{0x0000000000802000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_margin1832 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_margin1835 = new BitSet(new long[]{0xBFE00001D1D45060L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_margin1837 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_syncToDeclarationsRule_in_margin1840 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_declarations_in_margin1842 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_margin1844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_margin_sym0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_pseudoPage2073 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_pseudoPage2075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_operator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_in_combinator2125 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_combinator2127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATER_in_combinator2136 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_combinator2138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TILDE_in_combinator2147 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_combinator2149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_unaryOperator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scss_declaration_interpolation_expression_in_property2256 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_IDENT_in_property2268 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_GEN_in_property2281 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_cp_variable_in_property2296 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_property2304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cp_mixin_declaration_in_rule2353 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_selectorsGroup_in_rule2386 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_rule2409 = new BitSet(new long[]{0xBFE00001D1D45060L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_rule2411 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_syncToFollow_in_rule2414 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_declarations_in_rule2428 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_rule2438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declaration_in_declarations2544 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_declarations2546 = new BitSet(new long[]{0xBFE00001D1D41062L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_declarations2548 = new BitSet(new long[]{0xBFE00001D1541062L,0x0000007740E00000L});
    public static final BitSet FOLLOW_declaration_in_declarations2633 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_declarations2635 = new BitSet(new long[]{0xBFE00001D1D41062L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_declarations2637 = new BitSet(new long[]{0xBFE00001D1541062L,0x0000007740E00000L});
    public static final BitSet FOLLOW_scss_nested_properties_in_declarations2650 = new BitSet(new long[]{0xBFE00001D1D41062L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_declarations2652 = new BitSet(new long[]{0xBFE00001D1541062L,0x0000007740E00000L});
    public static final BitSet FOLLOW_rule_in_declarations2679 = new BitSet(new long[]{0xBFE00001D1D41062L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_declarations2681 = new BitSet(new long[]{0xBFE00001D1541062L,0x0000007740E00000L});
    public static final BitSet FOLLOW_sass_extend_in_declarations2720 = new BitSet(new long[]{0xBFE00001D1D41062L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_declarations2722 = new BitSet(new long[]{0xBFE00001D1541062L,0x0000007740E00000L});
    public static final BitSet FOLLOW_sass_debug_in_declarations2761 = new BitSet(new long[]{0xBFE00001D1D41062L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_declarations2763 = new BitSet(new long[]{0xBFE00001D1541062L,0x0000007740E00000L});
    public static final BitSet FOLLOW_sass_control_in_declarations2802 = new BitSet(new long[]{0xBFE00001D1D41062L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_declarations2804 = new BitSet(new long[]{0xBFE00001D1541062L,0x0000007740E00000L});
    public static final BitSet FOLLOW_media_in_declarations2843 = new BitSet(new long[]{0xBFE00001D1D41062L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_declarations2845 = new BitSet(new long[]{0xBFE00001D1541062L,0x0000007740E00000L});
    public static final BitSet FOLLOW_cp_mixin_call_in_declarations2884 = new BitSet(new long[]{0xBFE00001D1D41062L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_declarations2886 = new BitSet(new long[]{0xBFE00001D1541062L,0x0000007740E00000L});
    public static final BitSet FOLLOW_syncTo_SEMI_in_declarations2931 = new BitSet(new long[]{0xBFE00001D1541062L,0x0000007740E00000L});
    public static final BitSet FOLLOW_declaration_in_declarations2961 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scss_selector_interpolation_expression_in_selectorsGroup3021 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_selectorsGroup3023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_selector_in_selectorsGroup3038 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COMMA_in_selectorsGroup3041 = new BitSet(new long[]{0xBFE0000000940040L,0x00000000004C0000L});
    public static final BitSet FOLLOW_ws_in_selectorsGroup3043 = new BitSet(new long[]{0xBFE0000000140040L,0x0000000000400000L});
    public static final BitSet FOLLOW_selector_in_selectorsGroup3046 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_simpleSelectorSequence_in_selector3073 = new BitSet(new long[]{0xBFFC000000140042L,0x0000000000400000L});
    public static final BitSet FOLLOW_combinator_in_selector3076 = new BitSet(new long[]{0xBFE0000000140040L,0x0000000000400000L});
    public static final BitSet FOLLOW_simpleSelectorSequence_in_selector3078 = new BitSet(new long[]{0xBFFC000000140042L,0x0000000000400000L});
    public static final BitSet FOLLOW_typeSelector_in_simpleSelectorSequence3111 = new BitSet(new long[]{0xBFE0000000140042L,0x0000000000400000L});
    public static final BitSet FOLLOW_elementSubsequent_in_simpleSelectorSequence3118 = new BitSet(new long[]{0xBFE0000000940042L,0x00000000004C0000L});
    public static final BitSet FOLLOW_ws_in_simpleSelectorSequence3120 = new BitSet(new long[]{0xBFE0000000140042L,0x0000000000400000L});
    public static final BitSet FOLLOW_elementSubsequent_in_simpleSelectorSequence3139 = new BitSet(new long[]{0xBFE0000000940042L,0x00000000004C0000L});
    public static final BitSet FOLLOW_ws_in_simpleSelectorSequence3141 = new BitSet(new long[]{0xBFE0000000140042L,0x0000000000400000L});
    public static final BitSet FOLLOW_set_in_esPred0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namespacePrefix_in_typeSelector3257 = new BitSet(new long[]{0xB000000000040040L});
    public static final BitSet FOLLOW_elementName_in_typeSelector3263 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_typeSelector3265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namespacePrefixName_in_namespacePrefix3283 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_STAR_in_namespacePrefix3287 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_PIPE_in_namespacePrefix3291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sass_extend_only_selector_in_elementSubsequent3330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cssId_in_elementSubsequent3339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cssClass_in_elementSubsequent3348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_slAttribute_in_elementSubsequent3360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pseudo_in_elementSubsequent3372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_cssId3400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_cssId3406 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_NAME_in_cssId3408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_cssClass3436 = new BitSet(new long[]{0x0000000000040040L});
    public static final BitSet FOLLOW_set_in_cssClass3438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_elementName0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACKET_in_slAttribute3510 = new BitSet(new long[]{0x3000000000800040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_namespacePrefix_in_slAttribute3517 = new BitSet(new long[]{0x3000000000800040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_slAttribute3520 = new BitSet(new long[]{0x3000000000800040L,0x00000000000C0000L});
    public static final BitSet FOLLOW_slAttributeName_in_slAttribute3531 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C007FL});
    public static final BitSet FOLLOW_ws_in_slAttribute3533 = new BitSet(new long[]{0x0000000000000000L,0x000000000000007FL});
    public static final BitSet FOLLOW_set_in_slAttribute3575 = new BitSet(new long[]{0x00000000008000C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_slAttribute3755 = new BitSet(new long[]{0x00000000008000C0L,0x00000000000C0000L});
    public static final BitSet FOLLOW_slAttributeValue_in_slAttribute3774 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0040L});
    public static final BitSet FOLLOW_ws_in_slAttribute3792 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_RBRACKET_in_slAttribute3821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_slAttributeName3837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_slAttributeValue3851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_pseudo3911 = new BitSet(new long[]{0x0000000000060040L});
    public static final BitSet FOLLOW_set_in_pseudo3975 = new BitSet(new long[]{0x0000000000880002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_pseudo4032 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_pseudo4035 = new BitSet(new long[]{0x10A4000020E411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_pseudo4037 = new BitSet(new long[]{0x10A4000020E411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_expression_in_pseudo4042 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_STAR_in_pseudo4046 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RPAREN_in_pseudo4051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_pseudo4130 = new BitSet(new long[]{0x0000000000880000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_pseudo4132 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_pseudo4135 = new BitSet(new long[]{0xBFE0000000B40040L,0x00000000004C0000L});
    public static final BitSet FOLLOW_ws_in_pseudo4137 = new BitSet(new long[]{0xBFE0000000340040L,0x0000000000400000L});
    public static final BitSet FOLLOW_simpleSelectorSequence_in_pseudo4140 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RPAREN_in_pseudo4143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_declaration4182 = new BitSet(new long[]{0x11E0000000441040L,0x0000000000200000L});
    public static final BitSet FOLLOW_property_in_declaration4185 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_COLON_in_declaration4187 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_declaration4189 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_propertyValue_in_declaration4192 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_prio_in_declaration4195 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_declaration4197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scss_declaration_property_value_interpolation_expression_in_propertyValue4263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_propertyValue4279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cp_expression_in_propertyValue4320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_expressionPredicate4358 = new BitSet(new long[]{0xEFFDFFFFFFBFDFF0L,0xFFFFFFFFFFFFFFFFL,0x0000000000000FFFL});
    public static final BitSet FOLLOW_set_in_expressionPredicate4387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMI_in_syncTo_SEMI4505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IMPORTANT_SYM_in_prio4560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_expression4581 = new BitSet(new long[]{0x00A6000020C419C2L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_operator_in_expression4586 = new BitSet(new long[]{0x00A4000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_expression4588 = new BitSet(new long[]{0x00A4000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_term_in_expression4593 = new BitSet(new long[]{0x00A6000020C419C2L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_unaryOperator_in_term4618 = new BitSet(new long[]{0x0080000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_term4620 = new BitSet(new long[]{0x00800000204411C0L,0x000000000023FF00L});
    public static final BitSet FOLLOW_set_in_term4644 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_STRING_in_term4844 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_IDENT_in_term4852 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_GEN_in_term4860 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_URI_in_term4868 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_hexColor_in_term4876 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_function_in_term4884 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_cp_variable_in_term4894 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_term4906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionName_in_function4922 = new BitSet(new long[]{0x0000000000880000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_function4924 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_function4929 = new BitSet(new long[]{0x00A4000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_function4931 = new BitSet(new long[]{0x00A4000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_expression_in_function4941 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_fnAttribute_in_function4959 = new BitSet(new long[]{0x0000000000200800L});
    public static final BitSet FOLLOW_COMMA_in_function4962 = new BitSet(new long[]{0x00A4000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_function4964 = new BitSet(new long[]{0x00A4000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_fnAttribute_in_function4967 = new BitSet(new long[]{0x0000000000200800L});
    public static final BitSet FOLLOW_RPAREN_in_function4988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_functionName5036 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_COLON_in_functionName5038 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_functionName5042 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_DOT_in_functionName5045 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_functionName5047 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_fnAttributeName_in_fnAttribute5070 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0001L});
    public static final BitSet FOLLOW_ws_in_fnAttribute5072 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_OPEQ_in_fnAttribute5075 = new BitSet(new long[]{0x00A4000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_fnAttribute5077 = new BitSet(new long[]{0x00A4000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_fnAttributeValue_in_fnAttribute5080 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_fnAttributeName5095 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_DOT_in_fnAttributeName5098 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_fnAttributeName5100 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_expression_in_fnAttributeValue5114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_hexColor5132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_ws5153 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_cp_variable_in_cp_variable_declaration5201 = new BitSet(new long[]{0x0000000000900000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_variable_declaration5203 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_COLON_in_cp_variable_declaration5206 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_cp_variable_declaration5208 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_cp_expression_in_cp_variable_declaration5211 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_cp_variable_declaration5213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cp_variable_in_cp_variable_declaration5240 = new BitSet(new long[]{0x0000000000900000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_variable_declaration5242 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_COLON_in_cp_variable_declaration5245 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_cp_variable_declaration5247 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_cp_expression_in_cp_variable_declaration5250 = new BitSet(new long[]{0x0000000000000020L,0x0000000000100000L});
    public static final BitSet FOLLOW_SASS_DEFAULT_in_cp_variable_declaration5253 = new BitSet(new long[]{0x0000000000800020L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_variable_declaration5255 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_cp_variable_declaration5260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_cp_variable5293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SASS_VAR_in_cp_variable5325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cp_additionExp_in_cp_expression5349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cp_multiplyExp_in_cp_additionExp5369 = new BitSet(new long[]{0x0024000000000002L});
    public static final BitSet FOLLOW_PLUS_in_cp_additionExp5383 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_cp_additionExp5385 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_cp_multiplyExp_in_cp_additionExp5388 = new BitSet(new long[]{0x0024000000000002L});
    public static final BitSet FOLLOW_MINUS_in_cp_additionExp5401 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_cp_additionExp5403 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_cp_multiplyExp_in_cp_additionExp5406 = new BitSet(new long[]{0x0024000000000002L});
    public static final BitSet FOLLOW_cp_atomExp_in_cp_multiplyExp5439 = new BitSet(new long[]{0x1002000000000002L});
    public static final BitSet FOLLOW_STAR_in_cp_multiplyExp5452 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_cp_multiplyExp5454 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_cp_atomExp_in_cp_multiplyExp5457 = new BitSet(new long[]{0x1002000000000002L});
    public static final BitSet FOLLOW_SOLIDUS_in_cp_multiplyExp5471 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_cp_multiplyExp5473 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_cp_atomExp_in_cp_multiplyExp5476 = new BitSet(new long[]{0x1002000000000002L});
    public static final BitSet FOLLOW_term_in_cp_atomExp5509 = new BitSet(new long[]{0x00A4000020C411C2L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_term_in_cp_atomExp5516 = new BitSet(new long[]{0x00A4000020C411C2L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_LPAREN_in_cp_atomExp5530 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_cp_atomExp5532 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_cp_additionExp_in_cp_atomExp5535 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RPAREN_in_cp_atomExp5537 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_atomExp5539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_cp_term5577 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_STRING_in_cp_term5777 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_IDENT_in_cp_term5785 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_GEN_in_cp_term5793 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_URI_in_cp_term5801 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_hexColor_in_cp_term5809 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_function_in_cp_term5817 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_cp_variable_in_cp_term5825 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_term5837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_cp_mixin_declaration5868 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_cp_mixin_name_in_cp_mixin_declaration5870 = new BitSet(new long[]{0x0000000000880000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_mixin_declaration5872 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_cp_mixin_declaration5875 = new BitSet(new long[]{0x0000000000601000L,0x0000000003200000L});
    public static final BitSet FOLLOW_less_args_list_in_cp_mixin_declaration5877 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RPAREN_in_cp_mixin_declaration5880 = new BitSet(new long[]{0x0000000000800002L,0x00000000040C0000L});
    public static final BitSet FOLLOW_ws_in_cp_mixin_declaration5882 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_less_mixin_guarded_in_cp_mixin_declaration5886 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_mixin_declaration5888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SASS_MIXIN_in_cp_mixin_declaration5905 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_mixin_declaration5907 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_cp_mixin_name_in_cp_mixin_declaration5909 = new BitSet(new long[]{0x0000000000880002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_mixin_declaration5911 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_LPAREN_in_cp_mixin_declaration5915 = new BitSet(new long[]{0x0000000000601000L,0x0000000003200000L});
    public static final BitSet FOLLOW_less_args_list_in_cp_mixin_declaration5917 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RPAREN_in_cp_mixin_declaration5920 = new BitSet(new long[]{0x0000000000800002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_mixin_declaration5922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_cp_mixin_call5964 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_cp_mixin_name_in_cp_mixin_call5966 = new BitSet(new long[]{0x0000000000880020L,0x00000000000C0000L});
    public static final BitSet FOLLOW_SASS_INCLUDE_in_cp_mixin_call5988 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_mixin_call5990 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_cp_mixin_name_in_cp_mixin_call5992 = new BitSet(new long[]{0x0000000000880020L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_mixin_call6005 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_cp_mixin_call6008 = new BitSet(new long[]{0x00A4000020E411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_cp_mixin_call_args_in_cp_mixin_call6010 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RPAREN_in_cp_mixin_call6013 = new BitSet(new long[]{0x0000000000800020L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_cp_mixin_call6017 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_cp_mixin_call6020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_cp_mixin_name6049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_cp_mixin_call_args6085 = new BitSet(new long[]{0x0000000000000822L});
    public static final BitSet FOLLOW_set_in_cp_mixin_call_args6089 = new BitSet(new long[]{0x00A4000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_cp_mixin_call_args6097 = new BitSet(new long[]{0x00A4000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_term_in_cp_mixin_call_args6100 = new BitSet(new long[]{0x0000000000000822L});
    public static final BitSet FOLLOW_less_arg_in_less_args_list6142 = new BitSet(new long[]{0x0000000000000822L});
    public static final BitSet FOLLOW_set_in_less_args_list6146 = new BitSet(new long[]{0x0000000000C01000L,0x00000000002C0000L});
    public static final BitSet FOLLOW_ws_in_less_args_list6156 = new BitSet(new long[]{0x0000000000401000L,0x0000000000200000L});
    public static final BitSet FOLLOW_less_arg_in_less_args_list6159 = new BitSet(new long[]{0x0000000000000822L});
    public static final BitSet FOLLOW_set_in_less_args_list6165 = new BitSet(new long[]{0x0000000000800000L,0x00000000030C0000L});
    public static final BitSet FOLLOW_ws_in_less_args_list6175 = new BitSet(new long[]{0x0000000000000000L,0x0000000003000000L});
    public static final BitSet FOLLOW_set_in_less_args_list6178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_less_args_list6200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cp_variable_in_less_arg6232 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_COLON_in_less_arg6236 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_less_arg6238 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_cp_expression_in_less_arg6241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LESS_WHEN_in_less_mixin_guarded6267 = new BitSet(new long[]{0x00000000008A0000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_less_mixin_guarded6269 = new BitSet(new long[]{0x00000000008A0000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_less_condition_in_less_mixin_guarded6272 = new BitSet(new long[]{0x0000000000008802L});
    public static final BitSet FOLLOW_set_in_less_mixin_guarded6276 = new BitSet(new long[]{0x00000000008A0000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_less_mixin_guarded6284 = new BitSet(new long[]{0x00000000008A0000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_less_condition_in_less_mixin_guarded6287 = new BitSet(new long[]{0x0000000000008802L});
    public static final BitSet FOLLOW_NOT_in_less_condition6317 = new BitSet(new long[]{0x0000000000880000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_less_condition6319 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_less_condition6328 = new BitSet(new long[]{0x0000000000C01040L,0x00000000002C0000L});
    public static final BitSet FOLLOW_ws_in_less_condition6330 = new BitSet(new long[]{0x0000000000401040L,0x0000000000200000L});
    public static final BitSet FOLLOW_less_function_in_condition_in_less_condition6356 = new BitSet(new long[]{0x0000000000A00000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_less_condition6358 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_cp_variable_in_less_condition6389 = new BitSet(new long[]{0x0008000000A00000L,0x00000000380C0001L});
    public static final BitSet FOLLOW_ws_in_less_condition6392 = new BitSet(new long[]{0x0008000000800000L,0x00000000380C0001L});
    public static final BitSet FOLLOW_less_condition_operator_in_less_condition6395 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_less_condition6397 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_cp_expression_in_less_condition6400 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RPAREN_in_less_condition6429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_less_fn_name_in_less_function_in_condition6455 = new BitSet(new long[]{0x0000000000880000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_less_function_in_condition6457 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_less_function_in_condition6460 = new BitSet(new long[]{0x0000000000C01000L,0x00000000002C0000L});
    public static final BitSet FOLLOW_ws_in_less_function_in_condition6462 = new BitSet(new long[]{0x0000000000401000L,0x0000000000200000L});
    public static final BitSet FOLLOW_cp_variable_in_less_function_in_condition6465 = new BitSet(new long[]{0x0000000000A00000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_less_function_in_condition6467 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RPAREN_in_less_function_in_condition6470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_less_fn_name6492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_less_condition_operator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scss_interpolation_expression_var_in_scss_selector_interpolation_expression6591 = new BitSet(new long[]{0x01E0000000900042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_set_in_scss_selector_interpolation_expression6619 = new BitSet(new long[]{0x01E0000000900042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_scss_selector_interpolation_expression6676 = new BitSet(new long[]{0x01E0000000100040L});
    public static final BitSet FOLLOW_scss_interpolation_expression_var_in_scss_selector_interpolation_expression6715 = new BitSet(new long[]{0x01E0000000900042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_set_in_scss_selector_interpolation_expression6751 = new BitSet(new long[]{0x01E0000000900042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_scss_interpolation_expression_var_in_scss_declaration_interpolation_expression6849 = new BitSet(new long[]{0x01E0000000800042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_set_in_scss_declaration_interpolation_expression6877 = new BitSet(new long[]{0x01E0000000800042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_scss_declaration_interpolation_expression6930 = new BitSet(new long[]{0x01E0000000000040L});
    public static final BitSet FOLLOW_scss_interpolation_expression_var_in_scss_declaration_interpolation_expression6969 = new BitSet(new long[]{0x01E0000000800042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_set_in_scss_declaration_interpolation_expression7005 = new BitSet(new long[]{0x01E0000000800042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_scss_interpolation_expression_var_in_scss_declaration_property_value_interpolation_expression7095 = new BitSet(new long[]{0x01E2000000800042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_set_in_scss_declaration_property_value_interpolation_expression7123 = new BitSet(new long[]{0x01E2000000800042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_scss_declaration_property_value_interpolation_expression7180 = new BitSet(new long[]{0x01E2000000000040L});
    public static final BitSet FOLLOW_scss_interpolation_expression_var_in_scss_declaration_property_value_interpolation_expression7219 = new BitSet(new long[]{0x01E2000000800042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_set_in_scss_declaration_property_value_interpolation_expression7255 = new BitSet(new long[]{0x01E2000000800042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_scss_interpolation_expression_var_in_scss_mq_interpolation_expression7353 = new BitSet(new long[]{0x01E0000000928042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_set_in_scss_mq_interpolation_expression7381 = new BitSet(new long[]{0x01E0000000928042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_scss_mq_interpolation_expression7446 = new BitSet(new long[]{0x01E0000000128040L});
    public static final BitSet FOLLOW_scss_interpolation_expression_var_in_scss_mq_interpolation_expression7485 = new BitSet(new long[]{0x01E0000000928042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_set_in_scss_mq_interpolation_expression7521 = new BitSet(new long[]{0x01E0000000928042L,0x00000000000C0000L});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_scss_interpolation_expression_var7606 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_scss_interpolation_expression_var7608 = new BitSet(new long[]{0x0000000000C01040L,0x00000000002C0000L});
    public static final BitSet FOLLOW_ws_in_scss_interpolation_expression_var7610 = new BitSet(new long[]{0x0000000000401040L,0x0000000000200000L});
    public static final BitSet FOLLOW_cp_variable_in_scss_interpolation_expression_var7615 = new BitSet(new long[]{0x0000000000804000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_less_function_in_condition_in_scss_interpolation_expression_var7619 = new BitSet(new long[]{0x0000000000804000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_scss_interpolation_expression_var7623 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_scss_interpolation_expression_var7626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_property_in_scss_nested_properties7670 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_COLON_in_scss_nested_properties7672 = new BitSet(new long[]{0x01E6000020CC31C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_scss_nested_properties7674 = new BitSet(new long[]{0x01E6000020CC31C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_propertyValue_in_scss_nested_properties7677 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_scss_nested_properties7680 = new BitSet(new long[]{0xBFE00001D1D45060L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_scss_nested_properties7682 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_syncToFollow_in_scss_nested_properties7685 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_declarations_in_scss_nested_properties7687 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_scss_nested_properties7689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SASS_EXTEND_in_sass_extend7710 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_sass_extend7712 = new BitSet(new long[]{0xBFE0000000140040L,0x0000000000400000L});
    public static final BitSet FOLLOW_simpleSelectorSequence_in_sass_extend7714 = new BitSet(new long[]{0x0000000000000020L,0x0000000080000000L});
    public static final BitSet FOLLOW_SASS_OPTIONAL_in_sass_extend7717 = new BitSet(new long[]{0x0000000000800020L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_sass_extend7719 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_sass_extend7724 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SASS_EXTEND_ONLY_SELECTOR_in_sass_extend_only_selector7749 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_sass_debug7770 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_sass_debug7780 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_cp_expression_in_sass_debug7782 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_sass_debug7784 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sass_if_in_sass_control7809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sass_for_in_sass_control7813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sass_each_in_sass_control7817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sass_while_in_sass_control7821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SASS_IF_in_sass_if7842 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_sass_if7844 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_sass_control_expression_in_sass_if7846 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_sass_control_block_in_sass_if7848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cp_expression_in_sass_control_expression7873 = new BitSet(new long[]{0x0008000000000002L,0x0000000838000000L});
    public static final BitSet FOLLOW_set_in_sass_control_expression7876 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_sass_control_expression7897 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_cp_expression_in_sass_control_expression7900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SASS_FOR_in_sass_for7923 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_sass_for7925 = new BitSet(new long[]{0x0000000000401000L,0x0000000000200000L});
    public static final BitSet FOLLOW_cp_variable_in_sass_for7927 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_sass_for7929 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_sass_for7931 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_sass_for7935 = new BitSet(new long[]{0x00800000204411C0L,0x000000000023FF00L});
    public static final BitSet FOLLOW_cp_term_in_sass_for7937 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_sass_for7939 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_sass_for7943 = new BitSet(new long[]{0x00800000204411C0L,0x000000000023FF00L});
    public static final BitSet FOLLOW_cp_term_in_sass_for7945 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_sass_control_block_in_sass_for7947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SASS_EACH_in_sass_each7968 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_sass_each7970 = new BitSet(new long[]{0x0000000000401000L,0x0000000000200000L});
    public static final BitSet FOLLOW_cp_variable_in_sass_each7972 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_sass_each7974 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_sass_each7976 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_sass_each7980 = new BitSet(new long[]{0x00800000204411C0L,0x000000000023FF00L});
    public static final BitSet FOLLOW_sass_each_list_in_sass_each7982 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_sass_control_block_in_sass_each7984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cp_term_in_sass_each_list8009 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COMMA_in_sass_each_list8012 = new BitSet(new long[]{0x0080000020C411C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_ws_in_sass_each_list8014 = new BitSet(new long[]{0x00800000204411C0L,0x000000000023FF00L});
    public static final BitSet FOLLOW_cp_term_in_sass_each_list8017 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_SASS_WHILE_in_sass_while8044 = new BitSet(new long[]{0x0000000000800000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_ws_in_sass_while8046 = new BitSet(new long[]{0x01E6000020CC11C0L,0x00000000002FFF00L});
    public static final BitSet FOLLOW_sass_control_expression_in_sass_while8048 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_sass_control_block_in_sass_while8050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_sass_control_block8071 = new BitSet(new long[]{0xBFE00001D1D45060L,0x0000007740EC0000L});
    public static final BitSet FOLLOW_ws_in_sass_control_block8073 = new BitSet(new long[]{0xBFE00001D1545060L,0x0000007740E00000L});
    public static final BitSet FOLLOW_declarations_in_sass_control_block8076 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_sass_control_block8078 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_synpred1_Css3475 = new BitSet(new long[]{0xFFFFFFFFFFFFDFF0L,0xFFFFFFFFFFFFFFFFL,0x0000000000000FFFL});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_synpred1_Css3487 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred1_Css3489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mediaQueryList_in_synpred2_Css3526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_synpred3_Css3612 = new BitSet(new long[]{0xFFFFFFFFFFFF9FD0L,0xFFFFFFFFFFFFFFFFL,0x0000000000000FFFL});
    public static final BitSet FOLLOW_COLON_in_synpred3_Css3624 = new BitSet(new long[]{0xFFFFFFFFFFFF9FD0L,0xFFFFFFFFFFFFFFFFL,0x0000000000000FFFL});
    public static final BitSet FOLLOW_set_in_synpred3_Css3626 = new BitSet(new long[]{0xFFFFFFFFFFFF9FF0L,0xFFFFFFFFFFFFFFFFL,0x0000000000000FFFL});
    public static final BitSet FOLLOW_SEMI_in_synpred3_Css3636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scss_declaration_interpolation_expression_in_synpred3_Css3640 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_COLON_in_synpred3_Css3642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_synpred4_Css32243 = new BitSet(new long[]{0xFFFFFFFFFFEFFFF0L,0xFFFFFFFFFFFFFFFFL,0x0000000000000FFFL});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_synpred4_Css32251 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred4_Css32253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declaration_in_synpred5_Css32539 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SEMI_in_synpred5_Css32541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_synpred6_Css32606 = new BitSet(new long[]{0xFFFFFFFFFFFF9FD0L,0xFFFFFFFFFFFFFFFFL,0x0000000000000FFFL});
    public static final BitSet FOLLOW_COLON_in_synpred6_Css32618 = new BitSet(new long[]{0xFFFFFFFFFFFF9FF0L,0xFFFFFFFFFFFFFFFFL,0x0000000000000FFFL});
    public static final BitSet FOLLOW_set_in_synpred6_Css32620 = new BitSet(new long[]{0xFFFFFFFFFFFF9FF0L,0xFFFFFFFFFFFFFFFFL,0x0000000000000FFFL});
    public static final BitSet FOLLOW_SEMI_in_synpred6_Css32630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scss_nested_properties_in_synpred7_Css32647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_in_synpred8_Css32676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_synpred9_Css32924 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0xFFFFFFFFFFFFFFFFL,0x0000000000000FFFL});
    public static final BitSet FOLLOW_SEMI_in_synpred9_Css32928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_synpred10_Css33003 = new BitSet(new long[]{0xFFFFFFFFFFFFDFF0L,0xFFFFFFFFFFFFFFFFL,0x0000000000000FFFL});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_synpred10_Css33015 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred10_Css33017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_esPred_in_synpred11_Css33115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_esPred_in_synpred12_Css33136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_synpred13_Css33245 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_PIPE_in_synpred13_Css33254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_synpred14_Css34250 = new BitSet(new long[]{0xFFFFFFFFFFFFFFD0L,0xFFFFFFFFFFFFFFFFL,0x0000000000000FFFL});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_synpred14_Css34258 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred14_Css34260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionPredicate_in_synpred15_Css34276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_synpred16_Css35513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_synpred17_Css36586 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred17_Css36588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_synpred18_Css36710 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred18_Css36712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_synpred19_Css36844 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred19_Css36846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_synpred20_Css36964 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred20_Css36966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_synpred21_Css37090 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred21_Css37092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_synpred22_Css37214 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred22_Css37216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_synpred23_Css37348 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred23_Css37350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_SYMBOL_in_synpred24_Css37480 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred24_Css37482 = new BitSet(new long[]{0x0000000000000002L});

}