/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2005 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.xml.xsd;
import java.util.Enumeration;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.netbeans.modules.xml.dtd.grammar.DTDParser;
import org.netbeans.api.xml.services.UserCatalog;
import java.io.*;

/** XML Schema Grammar provided code completion for XML Schema file.
 *
 * @author  Milan Kuchtiak
 */
public class SchemaGrammarQueryManager extends org.netbeans.modules.xml.api.model.GrammarQueryManager
{
    // actually code completion works only for xsd: and xs: prefixes
    private static final String SCHEMA="schema"; //NOI18N
    private static final String PUBLIC_JAXB="http://java.sun.com/xml/ns/jaxb"; //NOI18N
    
    private String prefix, ns_jaxb;
       
    public java.util.Enumeration enabled(org.netbeans.modules.xml.api.model.GrammarEnvironment ctx) {
        if (ctx.getFileObject() == null) return null;
        Enumeration en = ctx.getDocumentChildren();
        prefix=null;
        ns_jaxb=null;
        while (en.hasMoreElements()) {
            Node next = (Node) en.nextElement();
            if (next.getNodeType() == next.DOCUMENT_TYPE_NODE) {
                return null; // null for XML Documents specified by DTD
            } else if (next.getNodeType() == next.ELEMENT_NODE) {
                Element element = (Element) next;
                String tagName = element.getTagName();
                if (tagName.endsWith(":"+SCHEMA)) { //NOI18N
                    prefix = tagName.substring(0,tagName.indexOf(":"+SCHEMA));
                } else if (tagName.equals(SCHEMA)) {
                    prefix = "";
                }
                if (prefix==null) return null;
                NamedNodeMap map = element.getAttributes();
                for (int i=0; i<map.getLength();i++) {
                    Attr attr = (Attr)map.item(i);
                    String name = attr.getName();
                    if (PUBLIC_JAXB.equals(attr.getValue())) {
                        if (name.startsWith("xmlns:")) ns_jaxb=name.substring(6); //NOI18N
                    }
                }
                return org.openide.util.Enumerations.singleton (next);
            }
        }
        return null;
    }
    
    public java.beans.FeatureDescriptor getDescriptor() {
        return new java.beans.FeatureDescriptor();
    }
    
    /** Returns DTD for code completion
    */
    public org.netbeans.modules.xml.api.model.GrammarQuery getGrammar(org.netbeans.modules.xml.api.model.GrammarEnvironment ctx) {
        if (prefix==null) return null;
        InputSource inputSource = null;
        StringBuffer buffer=new StringBuffer();
        if (prefix.length()==0) {
            buffer.append("<!ENTITY % p ''><!ENTITY % s ''>"); //NOI18N
        } else {
            buffer.append("<!ENTITY % p '"+prefix+":'><!ENTITY % s ':"+prefix+"'>"); //NOI18N
        }
        java.io.InputStream is = null;
        if (ns_jaxb==null) {
            is = getClass().getResourceAsStream("/org/netbeans/modules/xml/schema/resources/XMLSchema.dtd"); //NOI18N
        } else {
            is = getClass().getResourceAsStream("/org/netbeans/modules/xml/schema/resources/XMLSchema_jaxb.dtd"); //NOI18N
            buffer.append("<!ENTITY % jaxb '"+ns_jaxb+"'>"); //NOI18N
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        try {
            while ((line=br.readLine())!=null) {
                buffer.append(line);
            }
            br.close();
        } catch (IOException ex) {
            return null;
        }
        inputSource = new InputSource(new StringReader(buffer.toString()));
        if (ns_jaxb==null)
            inputSource.setSystemId("nbres:/org/netbeans/modules/xml/schema/resources/XMLSchema.dtd"); //NOI18N
        else
            inputSource.setSystemId("nbres:/org/netbeans/modules/xml/schema/resources/XMLSchema_jaxb.dtd"); //NOI18N
        if (inputSource!=null) {
            DTDParser dtdParser = new DTDParser(true);
            return dtdParser.parse(inputSource);
        }
        return null;
    }    
}
