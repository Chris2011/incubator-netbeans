/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.netbeans.modules.html.knockout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.modules.html.editor.api.completion.HtmlCompletionItem;
import org.netbeans.modules.html.editor.lib.api.SyntaxAnalyzer;
import org.netbeans.modules.html.knockout.model.Binding;
import org.openide.util.NbBundle;
import org.netbeans.modules.html.editor.lib.api.HtmlSource;
import org.netbeans.modules.html.editor.lib.api.elements.Element;
import org.netbeans.modules.html.editor.lib.api.elements.OpenTag;
import org.netbeans.modules.web.common.api.LexerUtils;

/**
 *
 * todo: possibly distribute the external help w/ the module.
 * Now it's loaded from the external knockout website.
 *
 * @author marekfukala
 */
public class KOBindingCompletionItem extends HtmlCompletionItem {
    
    private static final Map<Binding, String> HELP_CACHE = new WeakHashMap<>();

    private Binding binding;
    private static final String CANNOT_LOAD_HELP = Bundle.cannot_load_help();

    public KOBindingCompletionItem(Binding binding, int substituteOffset) {
        super(binding.getName(), substituteOffset);
        this.binding = binding;
    }

    @Override
    protected String getSubstituteText() {
        return new StringBuilder().append(binding.getName()).append(": ").toString();
    }
    
    @Override
    public URL getHelpURL() {
        try {
            return new URL(binding.getExternalDocumentationURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(KOBindingCompletionItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getHelp() {
        String helpContent = HELP_CACHE.get(binding);
        if(helpContent != null) {
            return helpContent;
        }
        try {
            URL url = getHelpURL();
            if(url == null) {
                return CANNOT_LOAD_HELP;
            } else {
                helpContent = HelpSupport.getKnockoutDocumentationContent(HelpSupport.loadURLContent(url));
                HELP_CACHE.put(binding, helpContent);
                return helpContent;
            }
        } catch (IOException ex) {
            Logger.getLogger(KOBindingCompletionItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return CANNOT_LOAD_HELP;
    }

    @Override
    public boolean hasHelp() {
        return true;
    }

  
}
