/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
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


package org.netbeans.api.gsf;

import org.netbeans.api.gsf.annotations.NonNull;

/**
 * A PositionManager is responsible for mapping ComObjects provided by a Parser
 * to an offset in the source buffer used by that parser.
 * This service can often be provided by the parser itself (which would in that case
 * implement this interface). Offsets are 0-based.
 *
 * @author Tor Norbye
 */
public interface PositionManager {
    /**
     * Return the offset range of the given ComObject in the buffer, or OffsetRange.NONE
     * if the ComObject is not found
     */
    @NonNull
    OffsetRange getOffsetRange(Element file, Element object);

    /**
     * Return true if this position manager translates source positions
     */
    boolean isTranslatingSource();
    
    /**
     * Return the lexical offset corresponding to the given ast offset.
     * For most source files, this is the same (so it just returns the parameter)
     * but in languages like RHTML where the lexical input file is translated
     * into Ruby before being parsed, the offsets may differ.
     * 
     * @param result The result associated with this parser job
     * @param astOffset The offset in the source processed by the parser
     * @return The lexical offset corresponding to the ast offset, or -1
     *   if the corresponding position couldn't be determined
     */
    int getLexicalOffset(ParserResult result, int astOffset);

    /**
     * Reverse mapping from lexical offset to ast offset. See {@link getLexicalOffset}
     * for an explanation.
     * 
     * @param result The result associated with this parser job
     * @param lexicalOffset The lexical offset in the source buffer
     * @return The corresponding offset in the AST, or -1 if the position couldn't be
     *  determined
     */
    int getAstOffset(ParserResult result, int lexicalOffset);
}
