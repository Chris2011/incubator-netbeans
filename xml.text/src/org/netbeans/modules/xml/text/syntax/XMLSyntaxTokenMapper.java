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
package org.netbeans.modules.xml.text.syntax;

import org.netbeans.editor.*;
import org.netbeans.modules.xml.text.syntax.javacc.lib.*;
import org.netbeans.modules.xml.text.syntax.javacc.XMLSyntaxConstants;

/**
 * Factory mapping jjID => TokenID.
 *
 * @author  Petr Kuzel
 * @version 1.0
 */
public class XMLSyntaxTokenMapper implements JJMapperInterface, JJConstants, XMLSyntaxConstants {


    /** Create token for particular ID.  */
    public JJTokenID createToken(int id) {

        switch(id) {

            case JJ_EOL:
                return XMLTokenContext.EOL;
                
            case JJ_EOF:
                throw new Error("guessToken() must be called for such case."); // NOI18N
                
            case JJ_ERR:
                return XMLTokenContext.ERROR;
                
            //    case RCB:
            case RSB:
            case TAG_START:
            case DECL_START:
            case PI_START:
            case CDATA_START:
            case COND_END_IN_DEFAULT:
            case EQ_IN_TAG_ATTLIST:
            case TAG_END:
            case PI_CONTENT_START:
            case PI_END:
            case PI_CONTENT_END:
            case CDATA_END:
            case COND:
            case DECL_END:
            case ENTITY_END:
            case ELEMENT_END:
            case NOTATION_END:
            case COND_END:
            case ATTLIST_END:
            case DOCTYPE_END:
                
                return XMLTokenContext.SYMBOL;
                
            case TAG_NAME:
                
                return XMLTokenContext.TAG;

            case ERR_IN_GREF:
            case ERR_IN_TAG:
            case ERR_IN_TAG_ATTLIST:
            case ERR_IN_PI:
            case ERR_IN_PI_CONTENT:
            case ERR_IN_DECL:
            case ERR_IN_NOTATION:
            case ERR_IN_COND:
            case ERR_IN_ATTLIST:
            case ERR_IN_DOCTYPE:
            case ERR_IN_DEFAULT:
            case ERR_IN_COMMENT:
                
                return XMLTokenContext.ERROR;
                
            case ATT_NAME:
                
                return XMLTokenContext.ATT;
                
            case PI_TARGET:
            case XML_TARGET:
                
                return XMLTokenContext.TARGET;
                
//            case B_IN_CDATA:
            case TEXT_IN_CDATA:
                
                return XMLTokenContext.CDATA;

            case MARKUP_IN_CDATA:

                return XMLTokenContext.CDATA_MARKUP;
                
            case KW_IN_XML_DECL:                
            case ENTITY:
            case ATTLIST:
            case DOCTYPE:
            case ELEMENT:
            case NOTATION:
            case KW_IN_ENTITY:
            case EMPTY:
            case PCDATA:
            case ANY:
            case SYSTEM_IN_NOTATION:
            case INCLUDE:
            case IGNORE:
            case REQUIRED:
            case IMPLIED:
            case FIXED:
            case ID_IN_ATTLIST:
            case CDATA:
            case IDREF:
            case IDREFS:
            case ENTITY_IN_ATTLIST:
            case ENTITIES:
            case NMTOKEN:
            case NMTOKENS:
            case NOTATION_IN_ATTLIST:
            case PUBLIC:
            case SYSTEM:
                
                return XMLTokenContext.KW;
                
                
            case PREF_START:
            case TEXT_IN_PREF:
            case GREF_END:
            case PREF_END:
            case GREF_START:
            case TEXT_IN_GREF:
                
                return XMLTokenContext.REF;
                                
            case CHARS_START:
            case TEXT_IN_CHARS:
            case CHARS_END:
            case GREF_CHARS_START:
            case TEXT_IN_GREF_CHARS:
            case GREF_CHARS_END:
                
            case STRING_START:
            case TEXT_IN_STRING:
            case STRING_END:            
            case GREF_STRING_START:
            case TEXT_IN_GREF_STRING:
            case GREF_STRING_END:            
                
                return XMLTokenContext.STRING;
                
            case COMMENT_START:
            case TEXT_IN_COMMENT:
            case COMMENT_END:
                
                return XMLTokenContext.COMMENT;
                
                default:
                    return XMLTokenContext.PLAIN;
        }
        
    }
    /** @return token guessed for particular state.  */
    public final JJTokenID guessToken(String token,int state,boolean lastBuffer) {
        
        switch (state) {
            case XMLSyntaxConstants.IN_COMMENT:
                if (!("--".equals(token) || "-".equals(token))) { // NOI18N
                    return XMLTokenContext.COMMENT;
                } else {
                    return cannotGuess(lastBuffer);
                }
                
            case XMLSyntaxConstants.IN_GREF:
            case XMLSyntaxConstants.IN_PREF:
                return XMLTokenContext.REF;
                
            case XMLSyntaxConstants.IN_STRING:
            case XMLSyntaxConstants.IN_CHARS:
            case XMLSyntaxConstants.IN_GREF_STRING:
            case XMLSyntaxConstants.IN_GREF_CHARS:                
                return XMLTokenContext.STRING;
                
            default:
                return cannotGuess(lastBuffer);
        }
    }
    
    private JJTokenID cannotGuess(boolean lastBuffer) {
        return cannotGuess(lastBuffer, XMLTokenContext.PLAIN);
    }
    
    private JJTokenID cannotGuess(boolean lastBuffer, JJTokenID supposed) {
        if (lastBuffer) {
            return supposed;
        } else { 
            //ask for next buffer
            return null;
        }
        
    }
    /** @return supposed token for particular id and state.  */
    public JJTokenID supposedToken(String token,int id,int state) {
        switch (state) {
            case IN_TAG:
                return XMLTokenContext.TAG;
                
            case IN_COMMENT:
                return XMLTokenContext.COMMENT;
                
            default:
                return null;                
        }
    }
    
    
}
