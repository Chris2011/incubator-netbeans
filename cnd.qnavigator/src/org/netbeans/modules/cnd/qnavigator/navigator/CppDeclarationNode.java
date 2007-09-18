/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.cnd.qnavigator.navigator;


import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.Action;
import org.netbeans.modules.cnd.api.model.CsmClass;
import org.netbeans.modules.cnd.api.model.CsmClassifier;
import org.netbeans.modules.cnd.api.model.CsmCompoundClassifier;
import org.netbeans.modules.cnd.api.model.CsmDeclaration;
import org.netbeans.modules.cnd.api.model.CsmEnum;
import org.netbeans.modules.cnd.api.model.CsmEnumerator;
import org.netbeans.modules.cnd.api.model.CsmFile;
import org.netbeans.modules.cnd.api.model.CsmFriend;
import org.netbeans.modules.cnd.api.model.CsmFunction;
import org.netbeans.modules.cnd.api.model.CsmFunctionDefinition;
import org.netbeans.modules.cnd.api.model.CsmInclude;
import org.netbeans.modules.cnd.api.model.CsmMacro;
import org.netbeans.modules.cnd.api.model.CsmMember;
import org.netbeans.modules.cnd.api.model.CsmNamespaceDefinition;
import org.netbeans.modules.cnd.api.model.CsmObject;
import org.netbeans.modules.cnd.api.model.CsmOffsetable;
import org.netbeans.modules.cnd.api.model.CsmOffsetableDeclaration;
import org.netbeans.modules.cnd.api.model.CsmTemplate;
import org.netbeans.modules.cnd.api.model.CsmTypedef;
import org.netbeans.modules.cnd.api.model.util.CsmKindUtilities;
import org.netbeans.modules.cnd.modelutil.AbstractCsmNode;
import org.netbeans.modules.cnd.modelutil.CsmImageLoader;
import org.netbeans.modules.cnd.modelutil.CsmUtilities;
import org.openide.nodes.Children;

/**
 * Navigator Tree node.
 */
public class CppDeclarationNode extends AbstractCsmNode implements Comparable<CppDeclarationNode> {
    private Image icon;
    private CsmObject object;
    private CsmFile file;
    private boolean isFriend;
    private CppDeclarationNode(CsmOffsetableDeclaration element, List<IndexOffsetNode> lineNumberIndex) {
        super(new NavigatorChildren(element, lineNumberIndex));
        object = element;
        file = element.getContainingFile();
    }

    private CppDeclarationNode(CsmOffsetableDeclaration element, List<IndexOffsetNode> lineNumberIndex, CsmCompoundClassifier classifier) {
        super(new NavigatorChildren(element, lineNumberIndex, classifier));
        object = element;
        file = element.getContainingFile();
    }
    
    private CppDeclarationNode(Children children, CsmOffsetableDeclaration element) {
        super(children);
        object = element;
        file = element.getContainingFile();
    }

    private CppDeclarationNode(Children children, CsmOffsetableDeclaration element, boolean isFriend) {
        super(children);
        object = element;
        file = element.getContainingFile();
        this.isFriend = isFriend;
    }

    private CppDeclarationNode(Children children, CsmMacro element) {
        super(children);
        object = element;
    }

    private CppDeclarationNode(Children children, CsmInclude element) {
        super(children);
        object = element;
    }

    public CsmObject getCsmObject() {
        return object;
    }
    
    public int compareTo(CppDeclarationNode o) {
        int res = getDisplayName().compareTo(o.getDisplayName());
        if (res == 0) {
            if (CsmKindUtilities.isFunctionDeclaration(object)){
                res = 1;
            } else if (CsmKindUtilities.isFunctionDeclaration(o.object)) {
                res = -1;
            }
        }
        return res;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }


    @Override
    public String getHtmlDisplayName() {
        if (CsmKindUtilities.isFunctionDefinition(object)) {
            CsmFunction function = ((CsmFunctionDefinition)object).getDeclaration();
            if (function != null && !function.equals(object) &&  CsmKindUtilities.isClassMember(function)){
                CsmClass cls = ((CsmMember)function).getContainingClass();
                if (cls != null && cls.getName().length()>0) {
                    String name = cls.getName().replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"); // NOI18N
                    String displayName = getDisplayName().replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"); // NOI18N
                    return displayName+"<font color='!controlShadow'>  in " + name; // NOI18N
                }
            }
        }
        return super.getHtmlDisplayName();
    }
    
    @Override
    public Image getIcon(int param) {
        if (icon != null){
            return icon;
        }
        if (file != null && !file.isValid()){
            CsmObject obj = object;
            object = null;
            Image aIcon = super.getIcon(param);
            object = obj;
            return aIcon;
        }
        if (isFriend) {
            CsmFriend csmObj = (CsmFriend)object;
            return (csmObj == null) ? super.getIcon(param) : CsmImageLoader.getFriendFunctionImage(csmObj);
        } else {
            return super.getIcon(param);
        }
    }
    
    @Override
    public Image getOpenedIcon(int param) {
        return getIcon(param);
    }
    
    @Override
    public Action getPreferredAction() {
        if (CsmKindUtilities.isOffsetable(object)){
            return new GoToDeclarationAction((CsmOffsetable)object);
        }
        return null;
    }

    @Override
    public Action[] getActions(boolean context) {
        Action action = getPreferredAction();
        if (action != null){
            return new Action[] { action };
        }
        return new Action[0];
    }
    
    public static CppDeclarationNode nodeFactory(CsmObject element, List<IndexOffsetNode> lineNumberIndex, boolean isFriend){
        CppDeclarationNode node = null;
        if (CsmKindUtilities.isTypedef(element)){
            CsmTypedef def = (CsmTypedef) element;
            if (def.isTypeUnnamed()) {
                CsmClassifier cls = def.getType().getClassifier();
                if (cls != null && cls.getName().length()==0 &&
                   (cls instanceof CsmCompoundClassifier)) {
                    node = new CppDeclarationNode((CsmOffsetableDeclaration)element, lineNumberIndex, (CsmCompoundClassifier) cls);
                    node.setName(((CsmDeclaration)element).getName());
                    lineNumberIndex.add(new IndexOffsetNode(node,((CsmOffsetable)element).getStartOffset()));
                    return node;
                }
            }
            node = new CppDeclarationNode(Children.LEAF,(CsmOffsetableDeclaration)element,isFriend);
            node.setName(((CsmDeclaration)element).getName());
            lineNumberIndex.add(new IndexOffsetNode(node,((CsmOffsetable)element).getStartOffset()));
            return node;
        } else if (CsmKindUtilities.isClassifier(element)){
            String name = ((CsmClassifier)element).getName();
            if (name.length()==0 && (element instanceof CsmCompoundClassifier)) {
                Collection list = ((CsmCompoundClassifier)element).getEnclosingTypedefs();
                if (list.size() > 0) {
                    return null;
                }
            }
            node = new CppDeclarationNode((CsmOffsetableDeclaration)element, lineNumberIndex);
            if (CsmKindUtilities.isClass(element)) {
                CsmClass cls = (CsmClass)element;
                node.setName(cls.isTemplate() ? ((CsmTemplate)cls).getDisplayName() : cls.getName());
            } else {
                node.setName(((CsmClassifier)element).getName());
            }
            lineNumberIndex.add(new IndexOffsetNode(node,((CsmOffsetable)element).getStartOffset()));
            return node;
        } else if(CsmKindUtilities.isNamespaceDefinition(element)){
            node = new CppDeclarationNode((CsmNamespaceDefinition)element, lineNumberIndex);
            node.setName(((CsmNamespaceDefinition)element).getName());
            lineNumberIndex.add(new IndexOffsetNode(node,((CsmOffsetable)element).getStartOffset()));
            return node;
        } else if(CsmKindUtilities.isDeclaration(element)){
            node = new CppDeclarationNode(Children.LEAF,(CsmOffsetableDeclaration)element,isFriend);
            node.setName(((CsmDeclaration)element).getName());
            if(CsmKindUtilities.isFunction(element)){
                node.setName(CsmUtilities.getSignature((CsmFunction)element, true));
            } else {
                node.setName(((CsmDeclaration)element).getName());
            }
            lineNumberIndex.add(new IndexOffsetNode(node,((CsmOffsetable)element).getStartOffset()));
            return node;
        } else if(CsmKindUtilities.isEnumerator(element)){
            node = new CppDeclarationNode(Children.LEAF,(CsmEnumerator)element);
            node.setName(((CsmEnumerator)element).getName());
            lineNumberIndex.add(new IndexOffsetNode(node,((CsmOffsetable)element).getStartOffset()));
            return node;
        } else if(CsmKindUtilities.isMacro(element)){
            node = new CppDeclarationNode(Children.LEAF,(CsmMacro)element);
            node.setName(((CsmMacro)element).getName());
            lineNumberIndex.add(new IndexOffsetNode(node,((CsmOffsetable)element).getStartOffset()));
            return node;
        } else if(element instanceof CsmInclude){
            node = new CppDeclarationNode(Children.LEAF,(CsmInclude)element);
            node.setName(((CsmInclude)element).getIncludeName());
            lineNumberIndex.add(new IndexOffsetNode(node,((CsmOffsetable)element).getStartOffset()));
            return node;
        }
        return node;
    }
    
    private static class NavigatorChildren extends Children.SortedArray {
        private CsmOffsetableDeclaration element;
        private CsmCompoundClassifier container;
        private List<IndexOffsetNode> lineNumberIndex;
        public NavigatorChildren(CsmOffsetableDeclaration element, List<IndexOffsetNode> lineNumberIndex){
            this.element = element;
            this.lineNumberIndex = lineNumberIndex;
            this.getNodes();
        }

        public NavigatorChildren(CsmOffsetableDeclaration element, List<IndexOffsetNode> lineNumberIndex, CsmCompoundClassifier container){
            this.element = element;
            this.container = container;
            this.lineNumberIndex = lineNumberIndex;
            this.getNodes();
        }
        
        @Override
        protected Collection initCollection() {
            List<CppDeclarationNode> retValue = new ArrayList<CppDeclarationNode>();
            if (container != null){
                if (CsmKindUtilities.isClass(container)) {
                    initClassifier((CsmClass)container, retValue);
                } else {
                    initEnum((CsmEnum)container, retValue);
                }
            } else if (CsmKindUtilities.isClass(element)){
                initClassifier((CsmClass)element, retValue);
            } else if (CsmKindUtilities.isEnum(element)){
                initEnum((CsmEnum)element, retValue);
            } else if(CsmKindUtilities.isNamespaceDefinition(element)){
                CsmNamespaceDefinition ns = (CsmNamespaceDefinition)element;
                for (CsmDeclaration decl : ns.getDeclarations()){
                    CppDeclarationNode node = nodeFactory(decl, lineNumberIndex, false);
                    if (node != null){
                        retValue.add(node);
                    }
                }
            }
            Collections.<CppDeclarationNode>sort(retValue);
            return retValue;
        }
        
        private void initClassifier(CsmClass cls, List<CppDeclarationNode> retValue){
            for (CsmMember member : cls.getMembers()) {
                CppDeclarationNode node = nodeFactory(member, lineNumberIndex, false);
                if (node != null) {
                    retValue.add(node);
                }
            }
            for (CsmFriend friend : cls.getFriends()) {
                CppDeclarationNode node = nodeFactory(friend, lineNumberIndex, true);
                if (node != null) {
                    retValue.add(node);
                }
            }
        }

        private void initEnum(CsmEnum cls, List<CppDeclarationNode> retValue){
            for (CsmEnumerator en : cls.getEnumerators()){
                CppDeclarationNode node = nodeFactory(en, lineNumberIndex, false);
                if (node != null){
                    retValue.add(node);
                }
            }
        }
    }
}
