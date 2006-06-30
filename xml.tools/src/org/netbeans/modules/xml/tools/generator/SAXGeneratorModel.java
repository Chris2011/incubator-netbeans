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
package org.netbeans.modules.xml.tools.generator;

import java.util.*;

/**
 * Holder of generator settings.
 *
 * @author  Petr Kuzel
 * @version 1.0
 */
public class SAXGeneratorModel implements java.io.Serializable {

    /** Serial Version UID */
    private static final long serialVersionUID =-3982410888926831459L;

    public static final int JAXP_1_0 = 1;

    public static final int JAXP_1_1 = 2;

    public static final int SAX_1_0 = 1;

    public static final int SAX_2_0 = 2;

    /** Holds value of property handler. */
    private String handler;
    
    /** Holds value of property stub. */
    private String stub;
    
    /** Holds value of property parslet. */
    private String parslet;
    
    /** Holds value of property version. */
    private int SAXversion;
    
    /** Holds value of property JAXPversion. */
    private int JAXPversion;
    
    /** Holds value of property parsletImpl. */
    private String parsletImpl;
    
    /** Holds value of property handlerImpl. */
    private String handlerImpl;
    
    /** Holds value of property elementBindings. */
    private ElementBindings elementBindings;
    
    /** Holds value of property parsletBindings. */
    private ParsletBindings parsletBindings;
    
    /** Holds value of property elementDeclarations. */
    private ElementDeclarations elementDeclarations;
    
    /** Holds value of property propagateSAX. */
    private boolean propagateSAX;
    
    private String bindings;
   
    public SAXGeneratorModel() {
        this(""); // NOI18N
    }
   
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<SAXGeneratorModel propagateSax=" + propagateSAX + "sax/jaxp=" + SAXversion + "/" + JAXPversion + "\n"); // NOI18N
        sb.append(elementBindings.toString());
        sb.append("  --"); // NOI18N
        sb.append(parsletBindings.toString());
        sb.append("/>\n"); // NOI18N
        return sb.toString();
    }
 
    /** Creates new SAXGeneratorModel */
    public SAXGeneratorModel(String prefix) {
        this(prefix, new ElementDeclarations(null), new ElementBindings(), new ParsletBindings());
    }

    public SAXGeneratorModel(String prefix, ElementDeclarations eld, ElementBindings elb, ParsletBindings pab) {
        handler = prefix + "Handler"; // NOI18N
        stub = prefix + "Parser"; // NOI18N
        parslet = prefix + "Parslet"; // NOI18N
        parsletImpl = prefix + "ParsletImpl"; // NOI18N
        handlerImpl = prefix + "HandlerImpl"; // NOI18N
        SAXversion = SAX_1_0;
        JAXPversion = JAXP_1_1;
        elementBindings = elb;
        parsletBindings = pab;
        elementDeclarations = eld;
        bindings = prefix + "SAXBindings"; // NOI18N
    }
    
    /** Getter for property handler.
     * @return Value of property handler.
     */
    public String getHandler() {
        return handler;
    }
    
    /** Setter for property handler.
     * @param handler New value of property handler.
     */
    public void setHandler(String handler) {
        this.handler = handler;
    }
    
    /** Getter for property stub.
     * @return Value of property stub.
     */
    public String getStub() {
        return stub;
    }
    
    /** Setter for property stub.
     * @param stub New value of property stub.
     */
    public void setStub(String stub) {
        this.stub = stub;
    }
    
    /** Getter for property parslet.
     * @return Value of property parslet.
     */
    public String getParslet() {
        return parslet;
    }
    
    /** Setter for property parslet.
     * @param parslet New value of property parslet.
     */
    public void setParslet(String parslet) {
        this.parslet = parslet;
    }
    
    /** Getter for property version.
     * @return Value of property version.
     */
    public int getSAXversion() {
        return SAXversion;
    }
    
    /** Setter for property version.
     * @param version New value of property version.
     */
    public void setSAXversion(int version) {
        this.SAXversion = version;
    }
    
    /** Getter for property JAXPversion.
     * @return Value of property JAXPversion (1 for JaXP !.0; 2 for JaXP 1.1).
     */
    public int getJAXPversion() {
        return JAXPversion;
    }
    
    /** Setter for property JAXPversion.
     * @param JAXPversion New value of property JAXPversion.
     */
    public void setJAXPversion(int JAXPversion) {
        this.JAXPversion = JAXPversion;
    }
    
    /** Getter for property parsletImpl.
     * @return Value of property parsletImpl.
     */
    public String getParsletImpl() {
        return parsletImpl;
    }
    
    /** Setter for property parsletImpl.
     * @param parsletImpl New value of property parsletImpl.
     */
    public void setParsletImpl(String parsletImpl) {
        this.parsletImpl = parsletImpl;
    }
    
    /** Getter for property handlerImpl.
     * @return Value of property handlerImpl.
     */
    public String getHandlerImpl() {
        return handlerImpl;
    }
    
    /** Setter for property handlerImpl.
     * @param handlerImpl New value of property handlerImpl.
     */
    public void setHandlerImpl(String handlerImpl) {
        this.handlerImpl = handlerImpl;
    }
    
    /** Getter for property elementBindings.
     * @return Value of property elementBindings.
     */
    public ElementBindings getElementBindings() {
        return elementBindings;
    }
    
    /** Setter for property elementBindings.
     * @param elementBindings New value of property elementBindings.
     */
    public void setElementBindings(ElementBindings elementBindings) {
        this.elementBindings = elementBindings;
    }
    
    /** Getter for property parsletBindings.
     * @return Value of property parsletBindings.
     */
    public ParsletBindings getParsletBindings() {
        return parsletBindings;
    }
    
    /** Setter for property parsletBindings.
     * @param parsletBindings New value of property parsletBindings.
     */
    public void setParsletBindings(ParsletBindings parsletBindings) {
        this.parsletBindings = parsletBindings;
    }
    
    /** Getter for property elementDeclarations.
     * @return Value of property elementDeclarations.
     */
    public ElementDeclarations getElementDeclarations() {
        return elementDeclarations;
    }
    
    /** Setter for property elementDeclarations.
     * @param elementDeclarations New value of property elementDeclarations.
     */
    public void setElementDeclarations(ElementDeclarations elementDeclarations) {
        this.elementDeclarations = elementDeclarations;
    }
    
    /** Getter for property propagateSAX.
     * @return Value of property propagateSAX.
     */
    public boolean isPropagateSAX() {
        return propagateSAX;
    }
    
    /** Setter for property propagateSAX.
     * @param propagateSAX New value of property propagateSAX.
     */
    public void setPropagateSAX(boolean propagateSAX) {
        this.propagateSAX = propagateSAX;
    }

    
    public String getBindings() {
        return bindings;
    }
    
    public void setBindnings(String val) {
        bindings = val;
    }
    
    /**
     * @return true is some parslet mapping exists
     */
    public boolean hasParslets() {
        return parsletBindings.keySet().isEmpty() == false;
    }
    
    /*
     * Load passed bindings into this model for all declared elements.
     */
    public void loadElementBindings(ElementBindings bindings) {
        if (bindings == null) return;
        
        Iterator it = bindings.values().iterator();
        while (it.hasNext()) {
            ElementBindings.Entry next = (ElementBindings.Entry) it.next();
            
            if (elementDeclarations.getEntry(next.getElement()) != null) {
                elementBindings.put(next.getElement(), next);
            }
        }
    }
    
    /*
     * Load passed parslets into this model if a parslet is used.
     * Should be called after loadElementBindings().
     */
    public void loadParsletBindings(ParsletBindings parslets) {
        if (parslets == null) return;
        
        Iterator it = parslets.values().iterator();
        while (it.hasNext()) {
            ParsletBindings.Entry next = (ParsletBindings.Entry) it.next();
            
            if (elementBindings.containsParslet(next.getId())) {
                parsletBindings.put(next.getId(), next);
            }
        }
    }
}
