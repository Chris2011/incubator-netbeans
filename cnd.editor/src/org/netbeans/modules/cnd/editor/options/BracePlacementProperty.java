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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

package org.netbeans.modules.cnd.editor.options;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import org.netbeans.modules.cnd.editor.api.CodeStyle;
import org.openide.nodes.PropertySupport;
import org.openide.util.NbBundle;

/**
 *
 * @author  Alexander Simon
 */
public class BracePlacementProperty extends PropertySupport.ReadWrite<CodeStyle.BracePlacement> {

    private final CodeStyle.Language language;
    private final String optionID;
    private PreviewPreferences preferences;
    private CodeStyle.BracePlacement state;
    private PropertyEditor editor;

    public BracePlacementProperty(CodeStyle.Language language, PreviewPreferences preferences, String optionID) {
        super(optionID, CodeStyle.BracePlacement.class, getString("LBL_"+optionID), getString("HINT_"+optionID)); // NOI18N
        this.language = language;
        this.optionID = optionID;
        this.preferences = preferences;
        init();
    }
    
    private static String getString(String key) {
        return NbBundle.getMessage(BracePlacementProperty.class, key);
    }

    private void init() {
        state = CodeStyle.BracePlacement.valueOf(getPreferences().get(optionID,  getDefault().name()));
    }

    private CodeStyle.BracePlacement getDefault(){
        return CodeStyle.BracePlacement.valueOf((String) EditorOptions.getDefault(
                getPreferences().getLanguage(), getPreferences().getStyleId(), optionID));
    }

    private PreviewPreferences getPreferences() {
        return preferences;
    }

    @Override
    public String getHtmlDisplayName() {
        if (!isDefaultValue()) {
            return "<b>" + getDisplayName(); // NOI18N
        }
        return null;
    }

    public CodeStyle.BracePlacement getValue() {
        return state;
    }

    public void setValue(CodeStyle.BracePlacement v) {
        state = v;
        getPreferences().put(optionID, state.name());
    }

    @Override
    public void restoreDefaultValue() {
        setValue(getDefault());
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }

    @Override
    public boolean isDefaultValue() {
        return getDefault().equals(getValue());
    }

    @Override
    public PropertyEditor getPropertyEditor() {
        if (editor == null) {
            editor = new BracePlacementEditor();
        }
        return editor;
    }

    private static class BracePlacementEditor extends PropertyEditorSupport {
        @Override
        public String[] getTags() {
            try {
                CodeStyle.BracePlacement[] values = CodeStyle.BracePlacement.values();
                String[] tags = new String[values.length];
                for (int i = 0; i < values.length; i++) {
                    tags[i] = values[i].toString();
                }
                return tags;
            } catch (Exception x) {
                throw new AssertionError(x);
            }
        }

        @Override
        public String getAsText() {
            Object o = getValue();
            return o != null ? o.toString() : ""; // NOI18N
        }

        @Override
        public void setAsText(String text) {
            if (text.length() > 0) {
                CodeStyle.BracePlacement[] values = CodeStyle.BracePlacement.values();
                for (int i = 0; i < values.length; i++) {
                    if (values[i].toString().equals(text)) {
                        setValue(values[i]);
                        return;
                    }
                }
            }
            setValue(null);
        }

        @Override
        public String getJavaInitializationString() {
            CodeStyle.BracePlacement e = (CodeStyle.BracePlacement) getValue();
            return e != null ? CodeStyle.BracePlacement.class.getName().replace('$', '.') + '.' + e.name() : "null"; // NOI18N
        }
    }
}
