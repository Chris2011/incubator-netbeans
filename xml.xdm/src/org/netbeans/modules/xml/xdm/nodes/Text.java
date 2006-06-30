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

/*
 * Text.java
 *
 * Created on August 3, 2005, 5:51 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.netbeans.modules.xml.xdm.nodes;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.modules.xml.xdm.visitor.XMLNodeVisitor;

/**
 *
 * @author Ajit
 */
public class Text extends NodeImpl implements Node, org.w3c.dom.Text {
    
    public void accept(XMLNodeVisitor visitor) {
        visitor.visit(this);
    }
    
    Text() {
        super();
    }

    Text(String text) {
        this();
        List<Token> tokens = getTokensForWrite();
        tokens.add(new Token(text, TokenType.TOKEN_CHARACTER_DATA));
    }

    @Override
    public String getNodeValue() {
        return getText();
    }

    public short getNodeType() {
        return Node.TEXT_NODE;
    }

    public String getNodeName() {
        return "#text"; //NOI18N
    }

    @Override
    public String getNamespaceURI() {
        return null;
    }

    public String getText() {
        StringBuffer txtBuf = new StringBuffer();
        for (Token token: getTokens()) {
            if(token.getType()==TokenType.TOKEN_CHARACTER_DATA ||
			   token.getType()==TokenType.TOKEN_COMMENT) 
                txtBuf.append(token.getValue());
        }
        return removeEntityReference(txtBuf.toString());
    }
    
    public void setText(String text) {
        setText(text, TokenType.TOKEN_CHARACTER_DATA);
    }
    
    public void setText(String text, TokenType type) {
        if (type == TokenType.TOKEN_CHARACTER_DATA ||
                type == TokenType.TOKEN_PRETTY_PRINT) {
            checkNotInTree();
            List<Token> tokens = getTokensForWrite();
            if(!tokens.isEmpty())tokens.clear();
            text = insertEntityReference(text);
            tokens.add(new Token(text, type));
        }
    }
    
    /**
     * Returns whether this text node contains <a href='http://www.w3.org/TR/2004/REC-xml-infoset-20040204#infoitem.character'>
     * element content whitespace</a>, often abusively called "ignorable whitespace". The text node is 
     * determined to contain whitespace in element content during the load 
     * of the document or if validation occurs while using 
     * <code>Document.normalizeDocument()</code>.
     * @since DOM Level 3
     */
    public boolean isElementContentWhitespace() {
        //TODO Implement later
        return false;
    }

    /**
     * The number of 16-bit units that are available through <code>data</code> 
     * and the <code>substringData</code> method below. This may have the 
     * value zero, i.e., <code>CharacterData</code> nodes may be empty.
     */
    public int getLength() {
        //TODO Implement later
        return -1;
    }

    /**
     * Replaces the text of the current node and all logically-adjacent text 
     * nodes with the specified text. All logically-adjacent text nodes are 
     * removed including the current node unless it was the recipient of the 
     * replacement text.
     * <br>This method returns the node which received the replacement text. 
     * The returned node is: 
     * <ul>
     * <li><code>null</code>, when the replacement text is 
     * the empty string;
     * </li>
     * <li>the current node, except when the current node is 
     * read-only;
     * </li>
     * <li> a new <code>Text</code> node of the same type (
     * <code>Text</code> or <code>CDATASection</code>) as the current node 
     * inserted at the location of the replacement.
     * </li>
     * </ul>
     * <br>For instance, in the above example calling 
     * <code>replaceWholeText</code> on the <code>Text</code> node that 
     * contains "bar" with "yo" in argument results in the following: 
     * <br>Where the nodes to be removed are read-only descendants of an 
     * <code>EntityReference</code>, the <code>EntityReference</code> must 
     * be removed instead of the read-only nodes. If any 
     * <code>EntityReference</code> to be removed has descendants that are 
     * not <code>EntityReference</code>, <code>Text</code>, or 
     * <code>CDATASection</code> nodes, the <code>replaceWholeText</code> 
     * method must fail before performing any modification of the document, 
     * raising a <code>DOMException</code> with the code 
     * <code>NO_MODIFICATION_ALLOWED_ERR</code>.
     * <br>For instance, in the example below calling 
     * <code>replaceWholeText</code> on the <code>Text</code> node that 
     * contains "bar" fails, because the <code>EntityReference</code> node 
     * "ent" contains an <code>Element</code> node which cannot be removed.
     * @param content The content of the replacing <code>Text</code> node.
     * @return The <code>Text</code> node created with the specified content.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if one of the <code>Text</code> 
     *   nodes being replaced is readonly.
     * @since DOM Level 3
     */
    public org.w3c.dom.Text replaceWholeText(String content) {
        //TODO Implement later
        return null;
    }

    /**
     * Breaks this node into two nodes at the specified <code>offset</code>, 
     * keeping both in the tree as siblings. After being split, this node 
     * will contain all the content up to the <code>offset</code> point. A 
     * new node of the same type, which contains all the content at and 
     * after the <code>offset</code> point, is returned. If the original 
     * node had a parent node, the new node is inserted as the next sibling 
     * of the original node. When the <code>offset</code> is equal to the 
     * length of this node, the new node has no data.
     * @param offset The 16-bit unit offset at which to split, starting from 
     *   <code>0</code>.
     * @return The new node, of the same type as this node.
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if the specified offset is negative or greater 
     *   than the number of 16-bit units in <code>data</code>.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public org.w3c.dom.Text splitText(int offset) {
        //TODO Implement later
        return null;
    }

    /**
     * Returns all text of <code>Text</code> nodes logically-adjacent text 
     * nodes to this node, concatenated in document order.
     * <br>For instance, in the example below <code>wholeText</code> on the 
     * <code>Text</code> node that contains "bar" returns "barfoo", while on 
     * the <code>Text</code> node that contains "foo" it returns "barfoo". 
     * @since DOM Level 3
     */
    public String getWholeText() {
        //TODO Implement later
        return null;
    }

    /**
     * The character data of the node that implements this interface. The DOM 
     * implementation may not put arbitrary limits on the amount of data 
     * that may be stored in a <code>CharacterData</code> node. However, 
     * implementation limits may mean that the entirety of a node's data may 
     * not fit into a single <code>DOMString</code>. In such cases, the user 
     * may call <code>substringData</code> to retrieve the data in 
     * appropriately sized pieces.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
     */
    public void setData(String data) {
        //TODO Implement later
    }

    /**
     * Append the string to the end of the character data of the node. Upon 
     * success, <code>data</code> provides access to the concatenation of 
     * <code>data</code> and the <code>DOMString</code> specified.
     * @param arg The <code>DOMString</code> to append.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public void appendData(String arg) {
        //TODO Implement later
    }

    /**
     * Replace the characters starting at the specified 16-bit unit offset 
     * with the specified string.
     * @param offset The offset from which to start replacing.
     * @param count The number of 16-bit units to replace. If the sum of 
     *   <code>offset</code> and <code>count</code> exceeds 
     *   <code>length</code>, then all 16-bit units to the end of the data 
     *   are replaced; (i.e., the effect is the same as a <code>remove</code>
     *    method call with the same range, followed by an <code>append</code>
     *    method invocation).
     * @param arg The <code>DOMString</code> with which the range must be 
     *   replaced.
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if the specified <code>offset</code> is 
     *   negative or greater than the number of 16-bit units in 
     *   <code>data</code>, or if the specified <code>count</code> is 
     *   negative.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public void replaceData(int offset, int count, String arg) {
        //TODO Implement later
    }

    /**
     * Insert a string at the specified 16-bit unit offset.
     * @param offset The character offset at which to insert.
     * @param arg The <code>DOMString</code> to insert.
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if the specified <code>offset</code> is 
     *   negative or greater than the number of 16-bit units in 
     *   <code>data</code>.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public void insertData(int offset, String arg) {
        //TODO Implement later
    }

    /**
     * Extracts a range of data from the node.
     * @param offset Start offset of substring to extract.
     * @param count The number of 16-bit units to extract.
     * @return The specified substring. If the sum of <code>offset</code> and 
     *   <code>count</code> exceeds the <code>length</code>, then all 16-bit 
     *   units to the end of the data are returned.
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if the specified <code>offset</code> is 
     *   negative or greater than the number of 16-bit units in 
     *   <code>data</code>, or if the specified <code>count</code> is 
     *   negative.
     *   <br>DOMSTRING_SIZE_ERR: Raised if the specified range of text does 
     *   not fit into a <code>DOMString</code>.
     */
    public String substringData(int offset, int count) {
        //TODO Implement later
        return null;
    }

    /**
     * The character data of the node that implements this interface. The DOM 
     * implementation may not put arbitrary limits on the amount of data 
     * that may be stored in a <code>CharacterData</code> node. However, 
     * implementation limits may mean that the entirety of a node's data may 
     * not fit into a single <code>DOMString</code>. In such cases, the user 
     * may call <code>substringData</code> to retrieve the data in 
     * appropriately sized pieces.
     * @exception DOMException
     *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than 
     *   fit in a <code>DOMString</code> variable on the implementation 
     *   platform.
     */
    public String getData() {
        //TODO Implement later
        return null;
    }

    /**
     * Remove a range of 16-bit units from the node. Upon success, 
     * <code>data</code> and <code>length</code> reflect the change.
     * @param offset The offset from which to start removing.
     * @param count The number of 16-bit units to delete. If the sum of 
     *   <code>offset</code> and <code>count</code> exceeds 
     *   <code>length</code> then all 16-bit units from <code>offset</code> 
     *   to the end of the data are deleted.
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if the specified <code>offset</code> is 
     *   negative or greater than the number of 16-bit units in 
     *   <code>data</code>, or if the specified <code>count</code> is 
     *   negative.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public void deleteData(int offset, int count) {
        //TODO Implement later
    }
    
    private String insertEntityReference(String text) {
        // just make sure we replace & with &amp; and not &amp; with &&amp;amp; and so on
        String result = removeEntityReference(text);
        result = result.replaceAll("&","&amp;");   //replace &
        result = result.replaceAll("<","&lt;");    //replace <
//        result = result.replaceAll(">","&gt;");    //replace >
//        result = result.replaceAll("'","&apos;");  //replace '
//        result = result.replaceAll("\"","&quot;"); //replace "
        return result;
    }

    private String removeEntityReference(String text) {
        String result = text;
        result = result.replaceAll("&amp;","&");   //replace with &
        result = result.replaceAll("&lt;","<");    //replace with <
//        result = result.replaceAll("&gt;",">");    //replace with >
//        result = result.replaceAll("&apos;","'");  //replace with '
//        result = result.replaceAll("&quot;","\""); //replace with "
        return result;
    }  

}
