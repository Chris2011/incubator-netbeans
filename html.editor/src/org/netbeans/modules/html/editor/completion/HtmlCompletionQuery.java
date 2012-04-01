/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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
package org.netbeans.modules.html.editor.completion;

import org.netbeans.modules.html.editor.lib.api.elements.Node;
import org.netbeans.modules.html.editor.lib.api.elements.FeaturedNode;
import org.netbeans.modules.html.editor.lib.api.elements.Element;
import org.netbeans.modules.html.editor.lib.api.elements.ElementType;
import org.netbeans.modules.html.editor.lib.api.elements.Attribute;
import org.netbeans.modules.html.editor.lib.api.elements.ElementUtils;
import java.util.Map.Entry;
import java.util.*;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.api.html.lexer.HTMLTokenId;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.lib.editor.util.CharSequenceUtilities;
import org.netbeans.modules.csl.api.DataLoadersBridge;
import org.netbeans.modules.html.editor.HtmlPreferences;
import org.netbeans.modules.html.editor.api.Utils;
import org.netbeans.modules.html.editor.api.completion.HtmlCompletionItem;
import org.netbeans.modules.html.editor.api.gsf.HtmlExtension;
import org.netbeans.modules.html.editor.api.gsf.HtmlParserResult;
import org.netbeans.modules.html.editor.lib.api.HtmlParseResult;
import org.netbeans.modules.html.editor.lib.api.HtmlVersion;
import org.netbeans.modules.html.editor.lib.api.ParseResult;
import org.netbeans.modules.html.editor.lib.api.ProblemDescription;
import org.netbeans.modules.html.editor.lib.api.elements.*;
import org.netbeans.modules.html.editor.lib.api.model.HtmlModel;
import org.netbeans.modules.html.editor.lib.api.model.HtmlTag;
import org.netbeans.modules.html.editor.lib.api.model.HtmlTagAttribute;
import org.netbeans.modules.html.editor.lib.api.model.NamedCharRef;
import org.netbeans.modules.parsing.api.*;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.web.common.api.LexerUtils;
import org.netbeans.modules.web.common.api.ValueCompletion;
import org.netbeans.spi.editor.completion.CompletionItem;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

/**
 * Html completion results finder
 *
 * @author Marek Fukala
 * @author Petr Nejedly
 *
 * @version 2.0
 */
public class HtmlCompletionQuery extends UserTask {

    private static final String SCRIPT_TAG_NAME = "script"; //NOI18N
    private static final String STYLE_TAG_NAME = "style"; //NOI18N
    private static boolean lowerCase;
    private static boolean isXHtml = false;
    private Document document;
    private FileObject file;
    private int offset;
    private CompletionResult completionResult;
    private boolean triggeredByAutocompletion; //unused so far

    public HtmlCompletionQuery(Document document, int offset, boolean triggeredByAutocompletion) {
        this.document = document;
        this.offset = offset;
        this.file = DataLoadersBridge.getDefault().getFileObject(document);
        this.triggeredByAutocompletion = triggeredByAutocompletion;
    }

    public CompletionResult query() throws ParseException {
        Source source = Source.create(document);
        ParserManager.parse(Collections.singleton(source), this);

        return this.completionResult;
    }

    @Override
    public void run(ResultIterator resultIterator) throws Exception {
        final Parser.Result parserResult = resultIterator.getParserResult(offset);
        if (parserResult == null) {
            return;
        }
        final Snapshot snapshot = parserResult.getSnapshot();
        final Document doc = snapshot.getSource().getDocument(true);
        if (doc == null) {
            return; //this still may happen under some circumstances (deleted file, UserQuestionException etc.)
        }

        doc.render(new Runnable() {
            @Override
            public void run() {
                int embeddedOffset = snapshot.getEmbeddedOffset(offset);
                String resultMimeType = parserResult.getSnapshot().getMimeType();
                if (resultMimeType.equals("text/html")) {
                    //proceed only on html content
                    completionResult = query((HtmlParserResult) parserResult);
                } else if (resultMimeType.equals("text/javascript")) {
                    //complete the </script> end tag
                    completionResult = queryHtmlEndTagInEmbeddedCode(snapshot, doc, embeddedOffset, SCRIPT_TAG_NAME);
                } else if (resultMimeType.equals("text/x-css")) {
                    //complete the </style> end tag
                    completionResult = queryHtmlEndTagInEmbeddedCode(snapshot, doc, embeddedOffset, STYLE_TAG_NAME);
                }
            }
        });

    }

    private CompletionResult queryHtmlEndTagInEmbeddedCode(Snapshot snapshot, Document doc, final int embeddedOffset, final String endTagName) {
        // End tag autocompletion support
        // We want the end tag autocompletion to appear just after <style> and <script> tags.
        // Since there is css language as leaf languge, this needs to be treated separately.
        int documentItemOffset = snapshot.getOriginalOffset(embeddedOffset);
        TokenSequence ts = Utils.getJoinedHtmlSequence(doc, documentItemOffset - 1);
        if (ts != null) {
            if (ts.token().id() == HTMLTokenId.TAG_CLOSE_SYMBOL && CharSequenceUtilities.equals(ts.token().text(), ">")) {
                Token openTagToken = Utils.findTagOpenToken(ts);
                if (openTagToken != null && CharSequenceUtilities.equals(openTagToken.text(), endTagName)) {

                    List<? extends CompletionItem> items = Collections.singletonList(
                            HtmlCompletionItem.createAutocompleteEndTag(endTagName, documentItemOffset));
                    return new CompletionResult(items, offset);
                }
            }
        }


        String expectedCode = "</" + endTagName;
        // Common end tag completion

        //get searched area before caret size
        int patternSize = Math.max(embeddedOffset, embeddedOffset - expectedCode.length());

        CharSequence pattern = snapshot.getText().subSequence(embeddedOffset - patternSize, embeddedOffset);

        //find < in the pattern
        int ltIndex = CharSequenceUtilities.lastIndexOf(pattern, '<');
        if (ltIndex == -1) {
            //no acceptable prefix
            return null;
        }

        boolean match = true;
        //now compare the pattern with the expected text
        for (int i = ltIndex; i < pattern.length(); i++) {
            if (pattern.charAt(i) != expectedCode.charAt(i - ltIndex)) {
                match = false;
                break;
            }
        }

        if (match) {
            int itemOffset = embeddedOffset - patternSize + ltIndex;

            //convert back to document offsets
            documentItemOffset = snapshot.getOriginalOffset(itemOffset);

            List<? extends CompletionItem> items = Collections.singletonList(HtmlCompletionItem.createEndTag(endTagName, documentItemOffset, null, -1, HtmlCompletionItem.EndTag.Type.DEFAULT));
            return new CompletionResult(items, offset);
        }

        return null;
    }

    CompletionResult query(HtmlParserResult parserResult) {
        HtmlParseResult htmlResult;
        try {
            htmlResult = parserResult.getSyntaxAnalyzerResult().parseHtml();
        } catch (org.netbeans.modules.html.editor.lib.api.ParseException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }

        HtmlModel model = htmlResult.model();

        Snapshot snapshot = parserResult.getSnapshot();
        String sourceMimetype = snapshot.getSource().getMimeType();
        int astOffset = snapshot.getEmbeddedOffset(offset);

        //in some cases the embedded offset cannot be mapped, then we can do very less
        if (astOffset == -1) {
            return null;
        }

        lowerCase = usesLowerCase(parserResult, astOffset);
        HtmlVersion version = parserResult.getHtmlVersion();
        isXHtml = version.isXhtml();

        TokenHierarchy<?> hi = snapshot.getTokenHierarchy();
        TokenSequence<HTMLTokenId> ts = hi.tokenSequence(HTMLTokenId.language());
        assert ts != null; //should be ensured by the parsing.api that we always get html token sequence from the snapshot

        int diff = ts.move(astOffset);
        boolean backward = false;
        if (ts.moveNext()) {
            if (diff == 0 && (ts.token().id() == HTMLTokenId.TEXT || ts.token().id() == HTMLTokenId.WS
                    || ts.token().id() == HTMLTokenId.TAG_CLOSE_SYMBOL || ts.token().id() == HTMLTokenId.TAG_OPEN_SYMBOL)) {
                //looks like we are on a boundary of a text or whitespace, need the previous token
                //or we are just before tag closing symbol
                backward = true;
                if (!ts.movePrevious()) {
                    //we cannot get previous token
                    return null;
                }
            }
        } else {
            backward = true;
            if (!ts.movePrevious()) {
                //can't get previous token
                return null;
            }
        }

        int anchor = -1;

        //get text before cursor
        Token<HTMLTokenId> item = ts.token();
        int itemOffset = ts.offset();
        int documentItemOffset = snapshot.getOriginalOffset(itemOffset);
        String preText = item.text().toString();
        String itemText = preText;

        // Bug 182267 -  StringIndexOutOfBoundsException: String index out of range: -1
        // debug>>>
        if ((astOffset - itemOffset) < 0 || preText.length() < (astOffset - itemOffset)) {
            StringBuilder b = new StringBuilder();
            b.append("Inconsistency in the snapshot! Detailed info:"); //NOI18N
            b.append("\n------------------------------------------------");
            b.append("\ndocument.getText():");
            try {
                b.append(document.getText(0, document.getLength()));
            } catch (BadLocationException ex) {
                b.append(ex.getMessage());
            }
            b.append("\n------------------------------------------------");
            b.append("\ntoken hierarchy:\n").append(hi.toString());
            b.append("\n------------------------------------------------");
            b.append("\ntoken sequence:\n").append(ts.toString());
            b.append("\n------------------------------------------------");
            b.append("\nsnapshot.getText():").append(snapshot.getText().toString());
            b.append("\n------------------------------------------------");
            b.append("\nsnapshot.toString():").append(snapshot).toString();
            b.append("\nsource:").append(snapshot.getSource()).toString();
            b.append(String.format("\nastOffset = %1$s, itemOffset = %2$s", astOffset, itemOffset)); //NOI18N
            b.append(String.format("\npreText=%s; len=%s", preText, preText.length()));

            Logger.getAnonymousLogger().warning(b.toString());
            //and let the original exception to be thrown so the item is properly bound to the original report
        }
        //<<<debug

        if (diff < preText.length()) {
            preText = preText.substring(0, astOffset - itemOffset);
        }
        TokenId id = item.id();
        boolean inside = ts.offset() < astOffset; // are we inside token or between tokens?

        Collection<CompletionItem> result = null;
        int len = 1;

        //adjust the astOffset if at the end of the file
        int searchAstOffset = astOffset == snapshot.getText().length() ? astOffset - 1 : astOffset;

        //finds a leaf node for all the declared namespaces content including the default html content
        Element node = null;
        Node root = null;
        //html5 parse tree broken workaround:
        //In most cases when user edits the file the resulting parse tree
        //from the html5 parser is broken to such extent, that it is not possible
        //to resolve the real context node for given completion offset.
        //So if the edited source is html5 && there is a parser error, use
        //different approach - simply build a nesting tree of tag from the
        //actual position to the root by using the lexical syntax elements
        boolean useHtmlParseResult = true;
        if (version == HtmlVersion.HTML5
                || version == HtmlVersion.XHTML5
                || version == HtmlVersion.XHTML10_FRAMESET
                || version == HtmlVersion.XHTML10_STICT
                || version == HtmlVersion.XHTML10_TRANSATIONAL
                || version == HtmlVersion.HTML41_FRAMESET
                || version == HtmlVersion.HTML41_STRICT
                || version == HtmlVersion.HTML41_TRANSATIONAL) {
            for (ProblemDescription pd : htmlResult.getProblems()) {
                if (pd.getType() > ProblemDescription.WARNING) {
                    useHtmlParseResult = false;
                    break;
                }
            }
        }
        if (useHtmlParseResult) {
            //use the standart mechanism
            node = parserResult.findBySemanticRange(searchAstOffset, !backward);
            if (node == null || node.equals(parserResult.root())) {
                //fallback to the default simple xml parse tree (mlSyntaxTreeBuilder.makeUncheckedTree() )
                //if no leaf node found or just the root seems to be the leaf. This situation is likely
                //caused by an erroneous parse tree
                useHtmlParseResult = false;
            } else {
                root = ElementUtils.getRoot(node);
            }
        }

        if (!useHtmlParseResult) {
            //html5 && errors in the source => likely broken parse tree
            //force use the legacy tree builder, even if the tree is quite inaccurate,
            //it is not broken to such extent as the html5 parser one.
//            System.err.println("Broken HTML5 parse tree, using the legacy SyntaxTreeBuilder!");
//            root = SyntaxTreeBuilder.makeTree(htmlResult.source(), HtmlVersion.HTML40_TRANSATIONAL, parserResult.getSyntaxAnalyzerResult().getElements().items());
            ParseResult plain = parserResult.getSyntaxAnalyzerResult().parsePlain();
            root = plain.root();
            node = ElementUtils.findBySemanticRange(root, searchAstOffset, !backward);
            if (node == null) {
                node = root;
            }
        }

        assert node != null;
        assert root != null;

        //find a leaf node for the xml stuff
        Node xmlLeafNode = findLeafTag(parserResult, searchAstOffset, !backward);

        assert xmlLeafNode != null;

        //namespace is null for html content
        String namespace = null;
        if (root instanceof FeaturedNode) {
            namespace = (String) ((FeaturedNode) root).getProperty("namespace");
        }

        boolean queryHtmlContent = namespace == null || namespace.equals(parserResult.getHtmlVersion().getDefaultNamespace());

        /* Character reference finder */
        int ampIndex = preText.lastIndexOf('&'); //NOI18N
        if ((id == HTMLTokenId.TEXT || id == HTMLTokenId.VALUE) && ampIndex > -1) {
            //complete character references
            anchor = offset;
            result = translateCharRefs(offset - len, model.getNamedCharacterReferences(), preText.substring(ampIndex + 1));

        } else if (id == HTMLTokenId.CHARACTER) {
            //complete character references
            if (inside || !preText.endsWith(";")) { // NOI18N
                anchor = documentItemOffset + 1; //plus "&" length
                result = translateCharRefs(documentItemOffset, model.getNamedCharacterReferences(), preText.length() > 0 ? preText.substring(1) : "");
            }
        } else if (id == HTMLTokenId.TAG_OPEN) { // NOI18N

            //an element being typed is parsed as normal element end then
            //returned as a leaf node for the position, which is clearly wrong
            //since we need its parent to be able to complete the typed element
            OpenTag tag = (OpenTag) node;
            if (LexerUtils.equals(tag.unqualifiedName(), preText, false, false)) {
                node = node.parent();
                tag = (OpenTag) node;
            }

            //complete open tags with prefix
            anchor = documentItemOffset;
            //we are inside a tagname, the real content is the position before the tag
            astOffset -= (preText.length() + 1); // +"<" len

            result = new ArrayList<CompletionItem>();

            if (queryHtmlContent) {
                Collection<HtmlTag> possibleOpenTags = htmlResult.getPossibleOpenTags(tag);
                Collection<HtmlTag> allTags = filterHtmlElements(model.getAllTags(), preText);
                Collection<HtmlTag> filteredByPrefix = filterHtmlElements(possibleOpenTags, preText);
                result.addAll(translateHtmlTags(documentItemOffset - 1, filteredByPrefix, allTags));
            }

            //extensions
            HtmlExtension.CompletionContext context = new HtmlExtension.CompletionContext(parserResult, itemOffset, astOffset, documentItemOffset - 1, preText, itemText, node);
            for (HtmlExtension e : HtmlExtension.getRegisteredExtensions(sourceMimetype)) {
                result.addAll(e.completeOpenTags(context));
            }


        } else if ((id != HTMLTokenId.BLOCK_COMMENT && preText.endsWith("<"))
                || (id == HTMLTokenId.TAG_OPEN_SYMBOL && "<".equals(item.text().toString()))) { // NOI18N

            OpenTag tag = (OpenTag) node;
            //an element being typed is parsed as normal element end then
            //returned as a leaf node for the position, which is clearly wrong
            //since we need its parent to be able to complete the typed element
            if (LexerUtils.equals(tag.unqualifiedName(), preText, false, false)) {
                node = node.parent();
            }

            //complete open tags with no prefix
            anchor = offset;
            result = new ArrayList<CompletionItem>();

            if (queryHtmlContent) {
                Collection<HtmlTag> possibleOpenTags = htmlResult.getPossibleOpenTags(tag);
                Collection<HtmlTag> allTags = model.getAllTags();
                result.addAll(translateHtmlTags(offset - 1, possibleOpenTags, allTags));

                if (HtmlPreferences.completionOffersEndTagAfterLt()) {
                    //the end tag completion expects the item to be invoked after </ prefix
                    //which is not true in this case, we need to adjust it by one char
                    int endTagOffset = offset + 1;
                    result.addAll(getPossibleEndTags(htmlResult, node, xmlLeafNode, endTagOffset, "", model));
                }
            }

            //extensions
            HtmlExtension.CompletionContext context = new HtmlExtension.CompletionContext(parserResult, itemOffset, astOffset, offset - 1, "", "", node);
            for (HtmlExtension e : HtmlExtension.getRegisteredExtensions(sourceMimetype)) {
                Collection<CompletionItem> items = e.completeOpenTags(context);
                result.addAll(items);
            }


        } else if ((id == HTMLTokenId.TEXT && preText.endsWith("</"))
                || (id == HTMLTokenId.TAG_OPEN_SYMBOL && preText.endsWith("</"))) { // NOI18N
            //complete end tags without prefix
            anchor = offset;
            result = getPossibleEndTags(htmlResult, node, xmlLeafNode, offset, "", model);

        } else if (id == HTMLTokenId.TAG_CLOSE) { // NOI18N
            //complete end tags with prefix
            anchor = documentItemOffset;
            result = getPossibleEndTags(htmlResult, node, xmlLeafNode, offset, preText, model);

        } else if (id == HTMLTokenId.TAG_CLOSE_SYMBOL) {
            anchor = offset;
            result = getAutocompletedEndTag(node, xmlLeafNode, astOffset, offset, model);
        } else if (id == HTMLTokenId.WS || id == HTMLTokenId.ARGUMENT) {
            /*Argument finder */
            String prefix = (id == HTMLTokenId.ARGUMENT) ? preText : "";
            len = prefix.length();
            anchor = offset - len;

            result = new ArrayList<CompletionItem>();

            //extensions
            Collection<CompletionItem> items = new ArrayList<CompletionItem>();
            HtmlExtension.CompletionContext context = new HtmlExtension.CompletionContext(parserResult, itemOffset, astOffset, anchor, prefix, itemText, node);
            for (HtmlExtension e : HtmlExtension.getRegisteredExtensions(sourceMimetype)) {
                items.addAll(e.completeAttributes(context));
            }
            result.addAll(items);

            if (queryHtmlContent) {
                if (node.type() == ElementType.OPEN_TAG) {

                    OpenTag tnode = (OpenTag) node;
                    HtmlTag tag = model.getTag(tnode.name().toString());
                    if (tag != null) {

                        Collection<HtmlTagAttribute> possible = filterAttributes(tag.getAttributes(), prefix);
                        Collection<Attribute> existingAttrs = tnode.attributes();
                        Collection<String> existingAttrsNames = new ArrayList<String>();
                        for (Attribute attr : existingAttrs) {
                            existingAttrsNames.add(attr.name().toString());
                        }

                        String wordAtCursor = (item == null) ? null : item.text().toString();
                        // #BUGFIX 25261 because of being at the end of document the
                        // wordAtCursor must be checked for null to prevent NPE
                        // below
                        if (wordAtCursor == null) {
                            wordAtCursor = "";
                        }

                        Collection<HtmlTagAttribute> complete = new ArrayList<HtmlTagAttribute>();
                        for (HtmlTagAttribute attr : possible) {
                            String aName = attr.getName();
                            if (aName.equals(prefix)
                                    || (!existingAttrsNames.contains(isXHtml ? aName : aName.toUpperCase(Locale.ENGLISH))
                                    && !existingAttrsNames.contains(isXHtml ? aName : aName.toLowerCase(Locale.ENGLISH))) || (wordAtCursor.equals(aName) && prefix.length() > 0)) {
                                complete.add(attr);
                            }
                        }

                        result.addAll(translateAttribs(anchor, complete, tag));
                    }
                }
            }


        } else if (id == HTMLTokenId.VALUE || id == HTMLTokenId.OPERATOR || id == HTMLTokenId.WS) {
            /* Value finder */
            if (id == HTMLTokenId.WS) {
                //is the token before an operator? '<div color= |red>'
                ts.move(itemOffset);
                ts.movePrevious();
                Token t = ts.token();
                if (t.id() != HTMLTokenId.OPERATOR) {
                    return null;
                }
            }

            if (node.type() == ElementType.OPEN_TAG) {
                OpenTag tnode = (OpenTag) node;

                ts.move(itemOffset);
                ts.moveNext();
                Token argItem = ts.token();
                while (argItem.id() != HTMLTokenId.ARGUMENT && ts.movePrevious()) {
                    argItem = ts.token();
                }

                if (argItem.id() != HTMLTokenId.ARGUMENT) {
                    return null; // no ArgItem
                }
                String argName = argItem.text().toString();
                if (!isXHtml) {
                    argName = argName.toLowerCase(Locale.ENGLISH);
                }

                HtmlTag tag = model.getTag(tnode.name().toString());
                HtmlTagAttribute attribute = tag != null ? tag.getAttribute(argName) : null;

                //use set instead of list since the AttrValuesCompletion may return identical values as
                //HtmlTagAttribute.getPossibleValues()
                result = new LinkedHashSet<CompletionItem>();

                if (id != HTMLTokenId.VALUE) {
                    //after the equal sign
                    anchor = offset;
                    if (attribute != null) {
                        result.addAll(translateValues(anchor, attribute.getPossibleValues()));
                        ValueCompletion<HtmlCompletionItem> valuesCompletion = AttrValuesCompletion.getSupport(tnode.name().toString(), argName);
                        if (valuesCompletion != null) {
                            result.addAll(valuesCompletion.getItems(file, anchor, ""));
                        }
                    }

                    HtmlExtension.CompletionContext context = new HtmlExtension.CompletionContext(parserResult, itemOffset, astOffset, anchor, "", itemText, node, argName, false);
                    for (HtmlExtension e : HtmlExtension.getRegisteredExtensions(sourceMimetype)) {
                        result.addAll(e.completeAttributeValue(context));
                    }

                } else {
                    //inside the attribute value
                    String quotationChar = null;
                    if (preText != null && preText.length() > 0) {
                        if (preText.substring(0, 1).equals("'")) {
                            quotationChar = "'"; // NOI18N
                        }
                        if (preText.substring(0, 1).equals("\"")) {
                            quotationChar = "\""; // NOI18N
                        }
                    }
                    String prefix = quotationChar == null ? preText : preText.substring(1);

                    anchor = documentItemOffset + (quotationChar != null ? 1 : 0);

                    if (attribute != null) {
                        result.addAll(translateValues(documentItemOffset, filter(attribute.getPossibleValues(), prefix), quotationChar));
                        ValueCompletion<HtmlCompletionItem> valuesCompletion = AttrValuesCompletion.getSupport(tnode.name().toString(), argName);
                        if (valuesCompletion != null) {
                            result.addAll(valuesCompletion.getItems(file, anchor, prefix));
                        }
                    }

                    HtmlExtension.CompletionContext context = new HtmlExtension.CompletionContext(parserResult, itemOffset, astOffset, anchor, prefix, itemText, node, argName, quotationChar != null);
                    for (HtmlExtension e : HtmlExtension.getRegisteredExtensions(sourceMimetype)) {
                        result.addAll(e.completeAttributeValue(context));
                    }

                }
            }
        }

        return result == null ? null : new CompletionResult(result, anchor);

    }

    private boolean usesLowerCase(HtmlParserResult result, int astOffset) {
        //finds tag name case for the first document tag
        for (Element e : result.getSyntaxAnalyzerResult().getElements().items()) {
            switch (e.type()) {
                case OPEN_TAG:
                case CLOSE_TAG:
                    Named te = (Named) e;
                    char first = te.name().charAt(0);
                    return Character.isLowerCase(first);
            }
        }
        return true; //default
    }

    public List<CompletionItem> getAutocompletedEndTag(Element node, Node undeclaredTagsLeafNode, int astOffset, int documentOffset, HtmlModel model) {
        List<CompletionItem> result = getAutocompletedEndTag(node, astOffset, documentOffset, model);
        if (result == null) {
            result = getAutocompletedEndTag(undeclaredTagsLeafNode, astOffset, documentOffset, model);
        }
        return result == null ? Collections.<CompletionItem>emptyList() : result;
    }

    public List<CompletionItem> getAutocompletedEndTag(Element node, int astOffset, int documentOffset, HtmlModel model) {
        //check for open tags only
        //the test node.endOffset() == astOffset is required since the given node
        //is the most leaf OPEN TAG node for the position. But if there is some
        //unresolved (no-DTD) node at the position it would autocomplete the open
        //tag: <div> <bla>| + ACC would complete </div>
        if (node.type() == ElementType.OPEN_TAG && node.to() == astOffset) {
            OpenTag tnode = (OpenTag) node;
            //I do not check if the tag is closed already since
            //when more tags of the same type are nested,
            //the matches can be created so the current node
            //appear to be matched even if the user just typed it

            //test if the tag is an empty tag <div/> and whether the open tag has forbidden end tag
            HtmlTag tag = model.getTag(tnode.name().toString());
            boolean hasForbiddenEndTag = tag != null && tag.isEmpty();
            if (!tnode.isEmpty() && !hasForbiddenEndTag) {
                return Collections.singletonList((CompletionItem) HtmlCompletionItem.createAutocompleteEndTag(tnode.name().toString(), documentOffset));
            }
        }
        return null;
    }

    private List<CompletionItem> translateCharRefs(int offset, Collection<? extends NamedCharRef> refs, String prefix) {
        List<CompletionItem> result = new ArrayList<CompletionItem>(refs.size());
        for (NamedCharRef ref : refs) {
            String name = ref.getName();
            if (name.startsWith(prefix)) {
                result.add(HtmlCompletionItem.createCharacterReference(name, ref.getValue(), offset, name));
            }
        }
        return result;
    }

    private List<CompletionItem> getPossibleEndTags(HtmlParseResult htmlResult, Element leaf, Node undeclaredTagsLeafNode, int offset, String prefix, HtmlModel model) {
        List<CompletionItem> items = new ArrayList<CompletionItem>();
        items.addAll(getPossibleEndTags(htmlResult, leaf, offset, prefix, model));
        items.addAll(getPossibleHtmlEndTagsForUndeclaredComponents(undeclaredTagsLeafNode, offset, prefix, model));

        return items;
    }

    private Collection<CompletionItem> getPossibleEndTags(HtmlParseResult htmlResult, Element leaf, int offset, String prefix, HtmlModel model) {
        Map<HtmlTag, OpenTag> possible = htmlResult.getPossibleCloseTags(leaf);
        Collection<CompletionItem> items = new ArrayList<CompletionItem>();
        for (Entry<HtmlTag, OpenTag> entry : possible.entrySet()) {
            HtmlTag tag = entry.getKey();
            OpenTag node = entry.getValue();

            //distance from the caret position - lower number, higher precedence
            //this will ensure the two end tags list from html and undeclared content being properly ordered
            int order = offset - (node != null ? node.from() : 0);

            String tagName = isXHtml ? tag.getName() : (lowerCase ? tag.getName().toLowerCase(Locale.ENGLISH) : tag.getName().toUpperCase(Locale.ENGLISH));
            if (LexerUtils.startsWith(tagName, prefix, true, false)) {
                items.add(HtmlCompletionItem.createEndTag(tag, tagName, offset - 2 - prefix.length(), tagName, order, getEndTagType(leaf, model)));
            }
        }
        return items;
    }

    private List<CompletionItem> getPossibleHtmlEndTagsForUndeclaredComponents(Node leaf, int offset, String prefix, HtmlModel model) {
        List<CompletionItem> items = new ArrayList<CompletionItem>();

        for (;;) {
            if (leaf.type() == ElementType.ROOT) {
                break;
            }

            if (leaf.type() == ElementType.OPEN_TAG) {
                OpenTag tleaf = (OpenTag) leaf;
                String tagName = isXHtml ? tleaf.name().toString() : (lowerCase ? tleaf.name().toString().toLowerCase(Locale.ENGLISH) : tleaf.name().toString().toUpperCase(Locale.ENGLISH));
                if (tagName.startsWith(prefix.toLowerCase(Locale.ENGLISH))) {
                    //TODO - distinguish unmatched and matched tags in the completion!!!
                    //TODO - mark required and optional end tags somehow

                    //distance from the caret position - lower number, higher precedence
                    //this will ensure the two end tags list from html and undeclared content being properly ordered
                    int order = offset - leaf.from();
                    items.add(HtmlCompletionItem.createEndTag(tagName, offset - 2 - prefix.length(), tagName, order++, getEndTagType(leaf, model)));
                }

                //check if the tag needs to have a matching tag and if is matched already
                if (tleaf.matchingCloseTag() == null) {
                    //if not, any of its parent cannot be closed here
                    break;
                }
            }

            leaf = leaf.parent();
            assert leaf != null;
        }
        return items;
    }

    private HtmlCompletionItem.EndTag.Type getEndTagType(Element leaf, HtmlModel model) {
        switch (leaf.type()) {
            case OPEN_TAG:
                break;
            default:
                return HtmlCompletionItem.EndTag.Type.REQUIRED_EXISTING; //??????

        }

        OpenTag tleaf = (OpenTag) leaf;
        String tagName = tleaf.name().toString();
        HtmlTag htmlTag = model.getTag(tagName);

        if (htmlTag == null) {
            return HtmlCompletionItem.EndTag.Type.REQUIRED_EXISTING;
        }

        boolean needsMatchingTag = !htmlTag.hasOptionalEndTag();

        if (tleaf.matchingCloseTag() != null) {
            //matched
            return needsMatchingTag ? HtmlCompletionItem.EndTag.Type.REQUIRED_EXISTING : HtmlCompletionItem.EndTag.Type.OPTIONAL_EXISTING;
        } else {
            //unmatched
            return needsMatchingTag ? HtmlCompletionItem.EndTag.Type.REQUIRED_MISSING : HtmlCompletionItem.EndTag.Type.OPTIONAL_MISSING;
        }

    }

    private Collection<String> filter(Collection<?> col, String prefix) {
        Collection<String> filtered = new ArrayList<String>();
        for (Object o : col) {
            String s = o.toString();
            if (s.startsWith(prefix)) {
                filtered.add(s);
            }
        }
        return filtered;
    }

    private Collection<HtmlTagAttribute> filterAttributes(Collection<HtmlTagAttribute> attrs, String prefix) {
        Collection<HtmlTagAttribute> filtered = new ArrayList<HtmlTagAttribute>();
        for (HtmlTagAttribute ta : attrs) {
            if (ta.getName().startsWith(prefix)) {
                filtered.add(ta);
            }
        }
        return filtered;
    }

    private Collection<HtmlTag> filterHtmlElements(Collection<HtmlTag> elements, String elementNamePrefix) {
        List<HtmlTag> filtered = new ArrayList<HtmlTag>();
        elementNamePrefix = elementNamePrefix.toLowerCase(Locale.ENGLISH);
        for (HtmlTag e : elements) {
            if (e.getName().toLowerCase(Locale.ENGLISH).startsWith(elementNamePrefix)) {
                filtered.add(e);
            }
        }
        return filtered;
    }

    List<CompletionItem> translateHtmlTags(int offset, Collection<HtmlTag> possible, Collection<HtmlTag> all) {
        List<CompletionItem> result = new ArrayList<CompletionItem>(possible.size());
        Set<HtmlTag> allmodifiable = new HashSet<HtmlTag>(all);
        allmodifiable.removeAll(possible); //remove possible elements
        for (HtmlTag e : possible) {
            result.add(item4HtmlTag(e, offset, true));
        }
        for (HtmlTag e : allmodifiable) {
            result.add(item4HtmlTag(e, offset, false));
        }
        return result;
    }

    private HtmlCompletionItem item4HtmlTag(HtmlTag e, int offset, boolean possible) {
        String name = e.getName();
        name = isXHtml ? name : (lowerCase ? name.toLowerCase(Locale.ENGLISH) : name.toUpperCase(Locale.ENGLISH));
        return HtmlCompletionItem.createTag(e, name, offset, name, possible);
    }

    Collection<CompletionItem> translateAttribs(int offset, Collection<HtmlTagAttribute> attribs, HtmlTag tag) {
        List<CompletionItem> result = new ArrayList<CompletionItem>(attribs.size());
        String tagName = tag.getName() + "#"; // NOI18N
        for (HtmlTagAttribute attrib : attribs) {
            String name = attrib.getName();
            switch (attrib.getType()) {
                case BOOLEAN:
                    result.add(HtmlCompletionItem.createBooleanAttribute(name, offset, attrib.isRequired(), tagName + name));
                    break;
                default:
                    result.add(HtmlCompletionItem.createAttribute(attrib, name, offset, attrib.isRequired(), tagName + name));
                    break;
            }
        }
        return result;
    }

    Collection<HtmlCompletionItem> translateValues(int offset, Collection<String> values) {
        return translateValues(offset, values, null);
    }

    Collection<HtmlCompletionItem> translateValues(int offset, Collection<String> values, String quotationChar) {
        if (values == null) {
            return Collections.emptyList();
        }
        List<HtmlCompletionItem> result = new ArrayList<HtmlCompletionItem>(values.size());
        if (quotationChar != null) {
            offset++; //shift the offset after the quotation
        }
        for (String value : values) {
            result.add(HtmlCompletionItem.createAttributeValue(value, offset));
        }
        return result;
    }

    public static class CompletionResult {

        private Collection<? extends CompletionItem> items;
        int anchor;

        CompletionResult(Collection<? extends CompletionItem> items, int anchor) {
            this.items = items;
            this.anchor = anchor;
        }

        public int getAnchor() {
            return anchor;
        }

        public Collection<? extends CompletionItem> getItems() {
            return items;
        }
    }

    private Node findLeafTag(HtmlParserResult result, int offset, boolean forward) {
        //first try to find the in the undeclared component tree
        Node mostLeaf = ElementUtils.findBySemanticRange(result.rootOfUndeclaredTagsParseTree(), offset, forward);
        //now search the non html trees
        for (String uri : result.getNamespaces().keySet()) {
            Node root = result.root(uri);
            Node leaf = ElementUtils.findBySemanticRange(root, offset, forward);
            if (mostLeaf == null) {
                mostLeaf = leaf;
            } else {
                //they cannot overlap, just be nested, at least I think
                if (leaf.from() > mostLeaf.from()) {
                    mostLeaf = leaf;
                }
            }
        }
        return (OpenTag) mostLeaf;
    }
}
