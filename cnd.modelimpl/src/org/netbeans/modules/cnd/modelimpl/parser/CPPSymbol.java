/*
 * PUBLIC DOMAIN PCCTS-BASED C++ GRAMMAR (cplusplus.g, stat.g, expr.g)
 *
 * Authors: Sumana Srinivasan, NeXT Inc.;            sumana_srinivasan@next.com
 *          Terence Parr, Parr Research Corporation; parrt@parr-research.com
 *          Russell Quong, Purdue University;        quong@ecn.purdue.edu
 *
 * SOFTWARE RIGHTS
 *
 * This file is a part of the ANTLR-based C++ grammar and is free
 * software.  We do not reserve any LEGAL rights to its use or
 * distribution, but you may NOT claim ownership or authorship of this
 * grammar or support code.  An individual or company may otherwise do
 * whatever they wish with the grammar distributed herewith including the
 * incorporation of the grammar or the output generated by ANTLR into
 * commerical software.  You may redistribute in source or binary form
 * without payment of royalties to us as long as this header remains
 * in all source distributions.
 *
 * We encourage users to develop parsers/tools using this grammar.
 * In return, we ask that credit is given to us for developing this
 * grammar.  By "credit", we mean that if you incorporate our grammar or
 * the generated code into one of your programs (commercial product,
 * research project, or otherwise) that you acknowledge this fact in the
 * documentation, research report, etc....  In addition, you should say nice
 * things about us at every opportunity.
 *
 * As long as these guidelines are kept, we expect to continue enhancing
 * this grammar.  Feel free to send us enhancements, fixes, bug reports,
 * suggestions, or general words of encouragement at parrt@parr-research.com.
 * 
 * NeXT Computer Inc.
 * 900 Chesapeake Dr.
 * Redwood City, CA 94555
 * 12/02/1994
 * 
 * Restructured for public consumption by Terence Parr late February, 1995.
 *
 * Requires PCCTS 1.32b4 or higher to get past ANTLR. 
 * 
 * DISCLAIMER: we make no guarantees that this grammar works, makes sense,
 *             or can be used to do anything useful.
 */
/* 1999-2004 Version 3.0 July 2004
 * Modified by David Wigg at London South Bank University for CPP_parser.g
 *
 * See MyReadMe.txt for further information
 *
 * This file is best viewed in courier font with tabs set to 4 spaces
 */
/* 2005
 * Some modifications were made by Gordon Prieur (Gordon.Prieur@sun.com);
 * after that the grammar was ported to Java by Vladimir Kvashin (Vladimir.Kvashin@sun.com)
 *
 * NOCDDL
 */
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2016 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2016 Sun Microsystems, Inc.
 */

package org.netbeans.modules.cnd.modelimpl.parser;

public class CPPSymbol {

    public static class ObjectType extends Enum {
        public ObjectType(String id) {
            super(id);
        }
    }
    
    public static final ObjectType otInvalid = new ObjectType("otInvalid"); // NOI18N
    public static final ObjectType otFunction = new ObjectType("otFunction"); // NOI18N
    public static final ObjectType otVariable = new ObjectType("otVariable"); // NOI18N
    public static final ObjectType otTypedef = new ObjectType("otTypedef"); // NOI18N
    public static final ObjectType otStruct = new ObjectType("otStruct"); // NOI18N
    public static final ObjectType otUnion = new ObjectType("otUnion"); // NOI18N
    public static final ObjectType otEnum = new ObjectType("otEnum"); // NOI18N
    public static final ObjectType otClass = new ObjectType("otClass"); // NOI18N
    public static final ObjectType otEnumElement = new ObjectType("otEnumElement"); // NOI18N

    
    public static class ObjectFunction extends Enum {
        public ObjectFunction(String id) {
            super(id);
        }
    }
    public static final ObjectFunction ofNormal = new ObjectFunction("ofNormal"); // NOI18N
    public static final ObjectFunction ofAddress = new ObjectFunction("ofAddress"); // NOI18N
    public static final ObjectFunction ofPointer = new ObjectFunction("ofPointer"); // NOI18N
    
    private ObjectType type;
    private ObjectFunction function;	// Not fully used yet

//    public CPPSymbol() {
//    }
    
    public CPPSymbol(String name) { 
        this(name, otInvalid);
    }

    public CPPSymbol(String name, ObjectType ot/*=otInvalid*/) { 
        this(name, ot, ofNormal);
    }
    
    public CPPSymbol(String name, ObjectType ot/*=otInvalid*/, ObjectFunction of/*=ofNormal*/) { 
        type = ot; 
        function = of;
    }
	    
    public void setType(ObjectType t)	{
        type = t;
    }
    
    public ObjectType getType() {
        return type;
    }
	    
    public void setFunction(ObjectFunction f)	{
        function = f;
    }
    
    public ObjectFunction getFunction() {
        return function;
    }
    
}
