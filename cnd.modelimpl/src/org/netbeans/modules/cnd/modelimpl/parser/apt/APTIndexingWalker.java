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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
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

package org.netbeans.modules.cnd.modelimpl.parser.apt;

import org.netbeans.modules.cnd.antlr.TokenStream;
import org.netbeans.modules.cnd.antlr.TokenStreamException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.netbeans.modules.cnd.api.model.CsmFile;
import org.netbeans.modules.cnd.apt.structure.APT;
import org.netbeans.modules.cnd.apt.structure.APTDefine;
import org.netbeans.modules.cnd.apt.structure.APTElif;
import org.netbeans.modules.cnd.apt.structure.APTFile;
import org.netbeans.modules.cnd.apt.structure.APTIf;
import org.netbeans.modules.cnd.apt.structure.APTIfdef;
import org.netbeans.modules.cnd.apt.structure.APTIfndef;
import org.netbeans.modules.cnd.apt.structure.APTInclude;
import org.netbeans.modules.cnd.apt.structure.APTIncludeNext;
import org.netbeans.modules.cnd.apt.structure.APTUndefine;
import org.netbeans.modules.cnd.apt.support.APTFileCacheEntry;
import org.netbeans.modules.cnd.apt.support.APTPreprocHandler;
import org.netbeans.modules.cnd.apt.support.APTToken;
import org.netbeans.modules.cnd.apt.support.APTTokenTypes;
import org.netbeans.modules.cnd.apt.utils.APTUtils;
import org.netbeans.modules.cnd.modelimpl.debug.DiagnosticExceptoins;


/**
 * Walker to find all Identifiers in the file
 *
 * @author Egor Ushakov
 */
public final class APTIndexingWalker extends APTSelfWalker {
    private final Set<String> ids = new HashSet<String>();
    
    public APTIndexingWalker(APTFile apt, CsmFile csmFile, APTPreprocHandler preprocHandler, APTFileCacheEntry cacheEntry) {
        super(apt, preprocHandler, cacheEntry);
    }

    @Override
    protected void onDefine(APT apt) {
        APTDefine defineNode = (APTDefine) apt;
        analyzeList(defineNode.getBody());
        super.onDefine(apt);
    }

    @Override
    protected boolean onIf(APT apt) {
        analyzeStream(((APTIf) apt).getCondition(), false);
        return super.onIf(apt);
    }

    @Override
    protected boolean onElif(APT apt, boolean wasInPrevBranch) {
        analyzeStream(((APTElif) apt).getCondition(), false);
        return super.onElif(apt, wasInPrevBranch);
    }

    @Override
    protected boolean onIfndef(APT apt) {
        analyzeToken(((APTIfndef) apt).getMacroName(), false);
        return super.onIfndef(apt);
    }

    @Override
    protected boolean onIfdef(APT apt) {
        analyzeToken(((APTIfdef) apt).getMacroName(), false);
        return super.onIfdef(apt);
    }

    @Override
    protected void onUndef(APT apt) {
        analyzeToken(((APTUndefine) apt).getName(), false);
        super.onUndef(apt);
    }

    @Override
    protected void onInclude(APT apt) {
        analyzeStream(((APTInclude)apt).getInclude(), true);
        super.onInclude(apt);
    }

    @Override
    protected void onIncludeNext(APT apt) {
        analyzeStream(((APTIncludeNext)apt).getInclude(), true);
        super.onIncludeNext(apt);
    }

    public Set<String> collectIds() {
        TokenStream ts = super.getTokenStream();
        analyzeStream(ts, true);
        return ids;
    }

    private boolean analyzeToken(APTToken token, boolean addOnlyIfNotFunLikeMacro) {
        if (token != null && !APTUtils.isEOF(token)) {
            if (APTUtils.isID(token) || token.getType() == APTTokenTypes.ID_DEFINED) {
                ids.add(token.getText());
                return true;
            }
        }
        return false;
    }

    private void analyzeList(List<APTToken> tokens) {
        if (tokens != null) {
            for (APTToken token : tokens) {
                analyzeToken(token, false);
            }
        }
    }

    private void analyzeStream(TokenStream ts, boolean checkFunLikeMacro) {
        if (ts != null) {
            try {
                for (APTToken token = (APTToken) ts.nextToken(); !APTUtils.isEOF(token); ) {
                    analyzeToken(token, checkFunLikeMacro);
                }
            } catch (TokenStreamException ex) {
		DiagnosticExceptoins.register(ex);
            }
        }
    }
}
