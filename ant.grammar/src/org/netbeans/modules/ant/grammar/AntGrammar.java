/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2003 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.ant.grammar;

import java.util.*;
import javax.swing.Icon;
import org.apache.tools.ant.module.api.IntrospectedInfo;

import org.w3c.dom.*;

import org.openide.util.enum.*;

import org.netbeans.modules.xml.api.model.*;
import org.netbeans.modules.xml.spi.dom.*;

/**
 * Rather simple query implemetation based on static Ant introspection info.
 * Hints given by this grammar cannot guarantee that valid XML document is created.
 *
 * @author Petr Kuzel, Jesse Glick
 */
class AntGrammar implements GrammarQuery {
        
    /**
     * Allow to get names of <b>parsed general entities</b>.
     * @return list of <code>CompletionResult</code>s (ENTITY_REFERENCE_NODEs)
     */
    public Enumeration queryEntities(String prefix) {
        QueueEnumeration list = new QueueEnumeration();
        
        // add well-know build-in entity names
        
        if ("lt".startsWith(prefix)) list.put(new MyEntityReference("lt"));
        if ("gt".startsWith(prefix)) list.put(new MyEntityReference("gt"));
        if ("apos".startsWith(prefix)) list.put(new MyEntityReference("apos"));
        if ("quot".startsWith(prefix)) list.put(new MyEntityReference("quot"));
        if ("amp".startsWith(prefix)) list.put(new MyEntityReference("amp"));
        
        return list;
    }
    
    private static String getTaskClassFor(String elementName) {
        Map defs = getAntGrammar().getDefs("task");
        return (String) defs.get(elementName);
    }

    private static String getTypeClassFor(String elementName) {
        Map defs = getAntGrammar().getDefs("type");
        return (String) defs.get(elementName);        
    }
    
    private static IntrospectedInfo getAntGrammar() {
        return IntrospectedInfo.getKnownInfo();
    }
    
    /** this element is a special thing, like the root project element */
    private static final String KIND_SPECIAL = "special"; // NOI18N
    /** this element is a task */
    private static final String KIND_TASK = "task"; // NOI18N
    /** this element is a data type */
    private static final String KIND_TYPE = "type"; // NOI18N
    /** this element is part of some other structure (task or type) */
    private static final String KIND_DATA = "data"; // NOI18N
    /** tag for root project element */
    private static final String SPECIAL_PROJECT = "project"; // NOI18N
    /** tag for a target element */
    private static final String SPECIAL_TARGET = "target"; // NOI18N
    /** tag for a project description element */
    private static final String SPECIAL_DESCRIPTION = "description"; // NOI18N
    
    /**
     * Determine what a particular element in a build script represents,
     * based on its name and the names of all of its parents.
     * Returns a pair of the kind of the element (one of the KIND_* constants)
     * and the details (a class name suitable for {@link IntrospectedInfo}, or
     * in the case of {@link KIND_SPECIAL}, one of the SPECIAL_* constants).
     * @param e an element
     * @return a two-element string (kind and details), or null if this element is anomalous
     */
    private static final String[] typeOf(Element e) {
        String name = e.getNodeName();
        Node p = e.getParentNode();
        if (p == null) {
            throw new IllegalArgumentException("Detached node: " + e); // NOI18N
        }
        if (p.getNodeType() == Node.DOCUMENT_NODE) {
            if (name.equals("project")) { // NOI18N
                return new String[] {KIND_SPECIAL, SPECIAL_PROJECT};
            } else {
                // Weird root element? Ignore.
                return null;
            }
        } else if (p.getNodeType() == Node.ELEMENT_NODE) {
            // Find ourselves in context.
            String[] ptype = typeOf((Element)p);
            if (ptype == null) {
                // Unknown parent, therefore this is unknown too.
                return null;
            }
            if (ptype[0] == KIND_SPECIAL) {
                if (ptype[1] == SPECIAL_PROJECT) {
                    // <project> may have <description>, or types, or targets, or tasks
                    if (name.equals("description")) { // NOI18N
                        return new String[] {KIND_SPECIAL, SPECIAL_DESCRIPTION};
                    } else if (name.equals("target")) { // NOI18N
                        return new String[] {KIND_SPECIAL, SPECIAL_TARGET};
                    } else {
                        String taskClazz = (String)getAntGrammar().getDefs("task").get(name); // NOI18N
                        if (taskClazz != null) {
                            return new String[] {KIND_TASK, taskClazz};
                        } else {
                            String typeClazz = (String)getAntGrammar().getDefs("type").get(name); // NOI18N
                            if (typeClazz != null) {
                                return new String[] {KIND_TYPE, typeClazz};
                            } else {
                                return null;
                            }
                        }
                    }
                } else if (ptype[1] == SPECIAL_TARGET) {
                    // <target> may have tasks and types
                    String taskClazz = (String)getAntGrammar().getDefs("task").get(name); // NOI18N
                    if (taskClazz != null) {
                        return new String[] {KIND_TASK, taskClazz};
                    } else {
                        String typeClazz = (String)getAntGrammar().getDefs("type").get(name); // NOI18N
                        if (typeClazz != null) {
                            return new String[] {KIND_TYPE, typeClazz};
                        } else {
                            return null;
                        }
                    }
                } else if (ptype[1] == SPECIAL_DESCRIPTION) {
                    // <description> should have no children!
                    return null;
                } else {
                    throw new IllegalStateException(ptype[1]);
                }
            } else {
                // We must be data.
                String pclazz = ptype[1];
                String clazz = (String)getAntGrammar().getElements(pclazz).get(name);
                if (clazz != null) {
                    return new String[] {KIND_DATA, clazz};
                } else {
                    // Unknown data.
                    return null;
                }
            }
        } else {
            throw new IllegalArgumentException("Bad parent for " + e.toString() + ": " + p); // NOI18N
        }
    }
    
    /**
     * @stereotype query
     * @output list of results that can be queried on name, and attributes
     * @time Performs fast up to 300 ms.
     * @param ctx represents virtual attribute <code>Node</code> to be replaced. Its parent is a element node.
     * @return list of <code>CompletionResult</code>s (ATTRIBUTE_NODEs) that can be queried on name, and attributes.
     *        Every list member represents one possibility.
     */
    public Enumeration queryAttributes(HintContext ctx) {
        
        Element ownerElement = null;
        // Support both versions of GrammarQuery contract
        if (ctx.getNodeType() == Node.ATTRIBUTE_NODE) {
            ownerElement = ((Attr)ctx).getOwnerElement();
        } else if (ctx.getNodeType() == Node.ELEMENT_NODE) {
            ownerElement = (Element) ctx;
        }
        if (ownerElement == null) return EmptyEnumeration.EMPTY;
        
        NamedNodeMap existingAttributes = ownerElement.getAttributes();        
        List possibleAttributes;
        String[] typePair = typeOf(ownerElement);
        if (typePair == null) {
            return EmptyEnumeration.EMPTY;
        }
        String kind = typePair[0];
        String clazz = typePair[1];
        
        if (kind == KIND_SPECIAL && clazz == SPECIAL_PROJECT) {
            possibleAttributes = new LinkedList();
            possibleAttributes.add("default");
            possibleAttributes.add("name");
            possibleAttributes.add("basedir");
        } else if (kind == KIND_SPECIAL && clazz == SPECIAL_TARGET) {
            possibleAttributes = new LinkedList();
            possibleAttributes.add("name");
            possibleAttributes.add("depends");
            possibleAttributes.add("description");
            possibleAttributes.add("if");
            possibleAttributes.add("unless");
        } else if (kind == KIND_SPECIAL && clazz == SPECIAL_DESCRIPTION) {
            return EmptyEnumeration.EMPTY;
        } else {
            // task, type, or data; anyway, we have the defining class
            possibleAttributes = new LinkedList();
            if (kind == KIND_TYPE) {
                possibleAttributes.add("id");
            }
            possibleAttributes.addAll(new TreeSet(getAntGrammar().getAttributes(clazz).keySet()));
            if (kind == KIND_TASK) {
                // Can have an ID too, but less important; leave at end.
                possibleAttributes.add("id");
                // Currently IntrospectedInfo includes this in the props for a type,
                // though it excludes it for tasks. So for now add it explicitly
                // only to tasks.
                possibleAttributes.add("description");
                // Also useful sometimes:
                possibleAttributes.add("taskname");
            }
        }
        
        String prefix = ctx.getCurrentPrefix();
        
        QueueEnumeration list = new QueueEnumeration();
        Iterator it = possibleAttributes.iterator();
        while ( it.hasNext()) {
            String next = (String) it.next();
            if (next.startsWith(prefix)) {
                if (existingAttributes.getNamedItem(next) == null) {
                    list.put(new MyAttr(next));
                }
            }
        }
        
        return list;
    }
    
    /**
     * @semantics Navigates through read-only Node tree to determine context and provide right results.
     * @postconditions Let ctx unchanged
     * @time Performs fast up to 300 ms.
     * @stereotype query
     * @param ctx represents virtual element Node that has to be replaced, its own attributes does not name sense, it can be used just as the navigation start point.
     * @return list of <code>CompletionResult</code>s (ELEMENT_NODEs) that can be queried on name, and attributes
     *        Every list member represents one possibility.
     */
    public Enumeration queryElements(HintContext ctx) {
        
        Node parent = ((Node)ctx).getParentNode();
        if (parent == null) return EmptyEnumeration.EMPTY;
        if (parent.getNodeType() != Node.ELEMENT_NODE) {
            return EmptyEnumeration.EMPTY;
        }
        
        List elements;
        String[] typePair = typeOf((Element)parent);
        if (typePair == null) {
            return EmptyEnumeration.EMPTY;
        }
        String kind = typePair[0];
        String clazz = typePair[1];
        
        if (kind == KIND_SPECIAL && clazz == SPECIAL_PROJECT) {
            elements = new LinkedList();
            elements.add("target");
            elements.add("property");
            elements.add("taskdef");
            elements.add("typedef");
            // Ant 1.6 permits any task here...
            elements.add("description");
            elements.addAll(new TreeSet(getAntGrammar().getDefs("type").keySet()));
        } else if (kind == KIND_SPECIAL && clazz == SPECIAL_TARGET) {
            elements = new ArrayList(new TreeSet(getAntGrammar().getDefs("task").keySet()));
            // targets can have embedded types too, though less common:
            elements.addAll(new TreeSet(getAntGrammar().getDefs("type").keySet())); // NOI18N
        } else if (kind == KIND_SPECIAL && clazz == SPECIAL_DESCRIPTION) {
            return EmptyEnumeration.EMPTY;
        } else {
            // some introspectable class
            elements = new ArrayList(new TreeSet(getAntGrammar().getElements(clazz).keySet()));
        }
                
        String prefix = ctx.getCurrentPrefix();
        
        QueueEnumeration list = new QueueEnumeration();
        Iterator it = elements.iterator();
        while ( it.hasNext()) {
            String next = (String) it.next();
            if (next.startsWith(prefix)) {
                list.put(new MyElement(next));
            }
        }
        
        return list;                        
    }
    
    /**
     * Allow to get names of <b>declared notations</b>.
     * @return list of <code>CompletionResult</code>s (NOTATION_NODEs)
     */
    public Enumeration queryNotations(String prefix) {
        return EmptyEnumeration.EMPTY;
    }
       
    /**
     * @semantics Navigates through read-only Node tree to determine context and provide right results.
     * @postconditions Let ctx unchanged
     * @time Performs fast up to 300 ms.
     * @stereotype query
     * @input ctx represents virtual Node that has to be replaced (parent can be either Attr or Element), its own attributes does not name sense, it can be used just as the navigation start point.
     * @return list of <code>CompletionResult</code>s (TEXT_NODEs) that can be queried on name, and attributes.
     *        Every list member represents one possibility.
     */
    public Enumeration queryValues(HintContext ctx) {
        return EmptyEnumeration.EMPTY;
    }

    // return defaults, no way to query them
    public GrammarResult queryDefault(final HintContext ctx) {
        return null;
    }
    
    // it is not yet implemented
    public boolean isAllowed(Enumeration en) {
        return true;
    }
    
    // customizers section ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    public java.awt.Component getCustomizer(HintContext ctx) {
        return null;
    }
    
    public boolean hasCustomizer(HintContext ctx) {
        return false;
    }

    public org.openide.nodes.Node.Property[] getProperties(HintContext ctx) {
        return null;
    }
    

    /** For debug purposes only. */
    public String toString() {
        return "Ant grammar";                                                   // NOI18N
    }

    /**
     * Lazy evaluated enumeration of previous element siblings.
     */
    private static class PreviousEnumeration implements Enumeration {
        
        private final Node parent;
        private final Node last;
        private Node next;
        
        PreviousEnumeration(Node parent, Node last) {
            this.parent = parent;
            this.last = last;
            
            // init next
            
            next = parent.getFirstChild();
            while (next != null) {                
                if (next.getNodeType() == Node.ELEMENT_NODE) break;
                next = next.getNextSibling();
            }            
            if (next == last) next = null;
        }
        
        public boolean hasMoreElements() {
            return next != null;
        }

        public Object nextElement() {
            if (next == null) throw new NoSuchElementException();
            try {
                return next;
            } finally {
                while (next != null) {
                    next = next.getNextSibling();
                    if (next.getNodeType() == Node.ELEMENT_NODE) break;
                }
                if (next == last) next = null;
            }
        }
    }
    
    // Result classes ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    
    private static abstract class AbstractResultNode extends AbstractNode implements GrammarResult {
        
        public Icon getIcon(int kind) {
            return null;
        }
        
        /**
         * @output provide additional information simplifiing decision
         */
        public String getDescription() {
            return getNodeName() + " desc";
        }
        
        /**
         * @output text representing name of suitable entity
         * //??? is it really needed
         */
        public String getText() {
            return getNodeName();
        }
        
        /**
         * @output name that is presented to user
         */
        public String getDisplayName() {
            return getNodeName() + " disp";
        }
        
    }
    
    private static class MyEntityReference extends AbstractResultNode implements EntityReference {
        
        private String name;
        
        MyEntityReference(String name) {
            this.name = name;
        }
        
        public short getNodeType() {
            return Node.ENTITY_REFERENCE_NODE;
        }
        
        public String getNodeName() {
            return name;
        }
                
    }
    
    private static class MyElement extends AbstractResultNode implements Element {
        
        private String name;
        
        MyElement(String name) {
            this.name = name;
        }
        
        public short getNodeType() {
            return Node.ELEMENT_NODE;
        }
        
        public String getNodeName() {
            return name;
        }
        
        public String getTagName() {
            return name;
        }
        
    }

    private static class MyAttr extends AbstractResultNode implements Attr {
        
        private String name;
        
        MyAttr(String name) {
            this.name = name;
        }
        
        public short getNodeType() {
            return Node.ATTRIBUTE_NODE;
        }
        
        public String getNodeName() {
            return name;
        }
        
        public String getName() {
            return name;                
        }

        public String getValue() {
            return null;  //??? what spec says
        }
        
        
    }

    private static class MyNotation extends AbstractResultNode implements Notation {
        
        private String name;
        
        MyNotation(String name) {
            this.name = name;
        }
        
        public short getNodeType() {
            return Node.NOTATION_NODE;
        }
        
        public String getNodeName() {
            return name;
        }
                        
    }
    
    private static class MyText extends AbstractResultNode implements Text {
        
        private String data;
        
        MyText(String data) {
            this.data = data;
        }
        
        public short getNodeType() {
            return Node.TEXT_NODE;
        }

        public String getNodeValue() {
            return getData();
        }
        
        public String getData() throws DOMException {
            return data;
        }

        public int getLength() {
            return data == null ? -1 : data.length();
        }    
    }
        
}
