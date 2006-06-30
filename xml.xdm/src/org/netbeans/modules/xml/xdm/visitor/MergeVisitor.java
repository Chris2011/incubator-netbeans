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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */


package org.netbeans.modules.xml.xdm.visitor;
import java.util.List;
import org.netbeans.modules.xml.xdm.nodes.Document;
import org.netbeans.modules.xml.xdm.nodes.Node;
import org.netbeans.modules.xml.xdm.XDMModel;
import org.netbeans.modules.xml.xdm.nodes.Attribute;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * This class provides a way to merge two trees. A merge is defined as taking a
 * tree and muting this tree (firing events) to cause the tree to be the same
 * as the target tree. This allows the original tree to retain nodes which have
 * not been changed. A change is defined as having both the same syntax and
 * semantics. The current tree model preserves spacing where appropriate, so
 * this requires comparing spacing as well as semantics.
 * @author Chris Webster
 * @author Vidhya Narayanan
 */
public class MergeVisitor extends DefaultVisitor {
    /**
     * This method merges the currentModel and the given newDocument. Events
     * will be fired on currentModel.
     * @param model the model to merge the changes required to transform
     * the current document to something equivalent to newDocument.
     * @param newDoc to replicate in the current model.
     */
    public void merge(XDMModel model, Document newDoc) {
        xmlModel = model;
        oldtree = xmlModel.getDocument();
        target = newDoc;
        oldtree.accept(this);
    }
    
    protected void visitNode(Node node) {
        CompareVisitor comparer = new CompareVisitor();
        boolean result = comparer.compare(node, target);
        if (!result) {
            Node newNode = (Node)target.clone(false,false,false);
            List<Node> path = pathVisitor.findPath(oldtree, node);
            assert !path.isEmpty();
            Node oldNode = path.get(0);
            int offset = 0;
            NodeList children = ((Node)path.get(1)).getChildNodes();
            for (;offset<children.getLength();offset++) {
                if (oldNode.equals(children.item(offset))) {
                    break;
                }
            }
            xmlModel.delete(oldNode);
            xmlModel.add((Node)path.get(1), newNode, offset);
        } else {
            compareByIndex((Node)target, node);
            compareAttrsByIndex((Node)target, node);
        }
    }
    
    private void compareByIndex(Node newNode, Node current) {
        NodeList newnodes = newNode.getChildNodes();
        NodeList children = current.getChildNodes();
        int oldTreesize = children.getLength();
        int newTreesize = newnodes.getLength();
        
        int lastEqualIndex = Math.min(oldTreesize, newTreesize);
        // these nodes are comparable
        for (int i = 0; i < lastEqualIndex; i++) {
            target = (Node)newnodes.item(i);
            Node n = (Node)children.item(i);
            n.accept(this);
        }
        // reset target as the rest of the tree will need to be walked
        target = newNode;
        
        // delete removed nodes from oldTree
        for (int i = oldTreesize-1; i >= lastEqualIndex; i--) {
            xmlModel.delete((Node)children.item(i));
        }
        
        // add nodes from newTree
        for (int i = lastEqualIndex; i < newTreesize; i++) {
            Node n = (Node)newnodes.item(i);
            xmlModel.add(current, (Node)n.clone(false,false,false), i);
        }
    }
    
    private void compareAttrsByIndex(Node newNode, Node current) {
        NamedNodeMap newAttributes = newNode.getAttributes();
        NamedNodeMap attributes = current.getAttributes();
        int oldTreesize = attributes.getLength();
        int newTreesize = newAttributes.getLength();
        
        int lastEqualIndex = Math.min(oldTreesize, newTreesize);
        // these nodes are comparable
        for (int i = 0; i < lastEqualIndex; i++) {
            target = (Node)newAttributes.item(i);
            Node n = (Node)attributes.item(i);
//            System.out.println(n.getNodeName());
            n.accept(this);
        }
        // reset target as the rest of the tree will need to be walked
        target = newNode;
        
        // delete removed nodes from oldTree
        for (int i = oldTreesize-1; i >= lastEqualIndex; i--) {
            xmlModel.delete((Node)attributes.item(i));
        }
        
        // add nodes from newTree
        for (int i = lastEqualIndex; i < newTreesize; i++) {
            Node n = (Node)newAttributes.item(i);
            xmlModel.add(current, (Node)n.cloneNode(false), i);
        }
    }
    
    private XDMModel xmlModel;
    private Document oldtree;
    private Node target;
    private PathFromRootVisitor pathVisitor = new PathFromRootVisitor();
}
