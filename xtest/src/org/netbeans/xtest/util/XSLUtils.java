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

/*
 * XSLUtils.java
 *
 * Created on June 4, 2002, 4:18 PM
 */

package org.netbeans.xtest.util;

//
import java.io.*;

import org.w3c.dom.*;
import javax.xml.parsers.*;
// xsl
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;

/**
 *
 * @author  mb115822
 */
public class XSLUtils {

   // only static utils
   private XSLUtils() {
   }




   public static File getXSLFile(File xtestHome, String xslFilename) throws IOException {
       if (!xtestHome.isDirectory()) {
           throw new IOException("xtest home is not set");
       }
       File xslFile = new File(xtestHome,"lib"+File.separator+"xsl"+File.separator+xslFilename);
       if (!xslFile.isFile()) {
           throw new IOException("cannot find xsl file "+xslFile);
       }
       return xslFile;
   }
   
   
   public static Transformer getTransformer(File xsl) throws TransformerConfigurationException {
       StreamSource xslSource = new StreamSource(xsl);
       Transformer transformer = XMLFactoryUtil.newTransformer(xslSource);
       return transformer;
   }
   
   public static Document transform(Transformer transformer, Document xml) throws TransformerException, ParserConfigurationException {
       DOMSource domSource = new DOMSource(xml);
       DOMResult domResult = new DOMResult();
       transform(transformer,domSource,domResult);
       Node aNode = domResult.getNode();       
       Document resultDoc = XMLFactoryUtil.newDocumentBuilder().newDocument();
       resultDoc.appendChild(aNode);
       return resultDoc;
   }
   
   public static void transform(Transformer transformer, Document xml, File outputXML) throws TransformerException, IOException {
       DOMSource domSource = new DOMSource(xml);
       FileOutputStream outputFileStream = new FileOutputStream(outputXML);
       StreamResult streamResult = new StreamResult(outputFileStream);
       transform(transformer,domSource,streamResult);
       outputFileStream.close();
    }
    
    
    public static void transform(Transformer transformer, Source xmlSource, Result outputTarget) throws TransformerException {
        transformer.transform(xmlSource, outputTarget);
    }
    
    
    public static void transform(Transformer transformer, File inputXML, File outputXML) throws TransformerException, IOException {
        StreamSource xmlSource = new StreamSource(inputXML);
        FileOutputStream outputFileStream = new FileOutputStream(outputXML);
        StreamResult xmlResult = new StreamResult(outputFileStream);
        transform(transformer,xmlSource,xmlResult);
        outputFileStream.close();
    }
    
    public static void transform(File xsl, File inputXML, File outputXML) throws TransformerException, IOException {
        Transformer transformer = getTransformer(xsl);
        transform(transformer, inputXML, outputXML);
    }
    
    // here are also some extension to Xalan transformer --- these are used by the XTest's XSL scripts
    
    // replace 'original' string by 'replacement' string in 'input' string
    public static String replaceString(String input, String original, String replacement) {
        int index=0;
        int newIndex;
        StringBuffer resultBuffer = new StringBuffer(input.length());
        while (((newIndex = input.indexOf(original,index)) > -1)&(index < input.length())) {
            if (index < newIndex) {
                resultBuffer.append(input.substring(index,newIndex));
            }
            resultBuffer.append(replacement);
            index = newIndex + original.length();
        }
        
        if (index < input.length()) {
            resultBuffer.append(input.substring(index));
        }
        
        return resultBuffer.toString();
    }

}
