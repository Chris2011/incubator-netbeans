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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */
package org.netbeans.modules.css.editor.csl;

import javax.swing.text.Document;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.csl.spi.CommentHandler;
import org.netbeans.modules.css.lib.api.CssTokenId;

/**
 *
 * @author mfukala@netbeans.org
 */
public class CssCommentHandler extends CommentHandler.DefaultCommentHandler {

    private static final String COMMENT_START_DELIMITER = "/*"; //NOI18N
    private static final String COMMENT_END_DELIMITER = "*/"; //NOI18N

    @Override
    public String getCommentStartDelimiter() {
        return COMMENT_START_DELIMITER;
    }

    @Override
    public String getCommentEndDelimiter() {
        return COMMENT_END_DELIMITER;
    }

    @Override
    public int[] getAdjustedBlocks(Document doc, int from, int to) {
        //limit the search just to one embedded css section
        TokenSequence<CssTokenId> ts = getCssTokenSequence(doc, from);
        if(ts == null) {
            return super.getAdjustedBlocks(doc, from, to);
        }
        ts.moveStart();
        int limitedFrom = ts.moveNext() ? ts.offset() : from;
        ts.moveEnd();
        int limitedTo = ts.moveNext() ? ts.offset() + ts.token().length() : to;
        
        return new int[]{limitedFrom, limitedTo};
    }
 
    
    private TokenSequence<CssTokenId> getCssTokenSequence(Document doc, int offset) {
        TokenHierarchy th = TokenHierarchy.get(doc);
        TokenSequence ts = th.tokenSequence();
        if(ts == null) {
            return null;
        }
        ts.move(offset);

        while(ts.moveNext() || ts.movePrevious()) {
            if(ts.language() == CssTokenId.language()) {
                return ts;
            }

            ts = ts.embedded();

            if(ts == null) {
                break;
            }

            //position the embedded ts so we can search deeper
            ts.move(offset);
        }

        return null;

    }
}
