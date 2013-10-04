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
package org.netbeans.test.java.editor.formatting.operators;

import java.awt.Component;
import java.util.*;
import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import org.netbeans.jellytools.NbDialogOperator;
import org.netbeans.jellytools.OptionsOperator;
import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.*;
import org.netbeans.modules.editor.settings.storage.api.EditorSettings;
import org.netbeans.modules.options.editor.spi.PreferencesCustomizer;
import org.netbeans.modules.options.indentation.FormattingPanel;

/**
 *
 * @author jprox
 */
public class FormattingOptionsOperator extends NbDialogOperator {

    private static OptionsOperator optionsOperator = null;
    private static ContainerOperator<JScrollPane> formattingPanel;

    private FormattingOptionsOperator(JDialog dialog) {
        super(dialog);
    }

    /**
     * @param openOptions
     * @return
     */
    public static FormattingOptionsOperator invoke(boolean openOptions) {
        if (openOptions) {
            optionsOperator = OptionsOperator.invoke();
        } else {
            optionsOperator = new OptionsOperator();
        }
        switchToFormatting();
        return new FormattingOptionsOperator((JDialog) optionsOperator.getSource());
    }

    private static void switchToFormatting() {
        optionsOperator.selectEditor();
        Component findComponent = optionsOperator.findSubComponent(new JTabbedPaneOperator.JTabbedPaneFinder());
        JTabbedPaneOperator tabbedPane = new JTabbedPaneOperator((JTabbedPane) findComponent);
        tabbedPane.selectPage("Formatting");
        formattingPanel = new ContainerOperator<>((JScrollPane) tabbedPane.getSelectedComponent());
    }

    public void selectLanguage(String language) {
        JComboBoxOperator comboBoxOperator = getComboBoxByLabel("Language:");
        if (getComparator().equals(getLanguageAsText((String) comboBoxOperator.getSelectedItem()), language)) {
            return;
        }
        for (int i = 0; i < comboBoxOperator.getItemCount(); i++) {
            String value = getLanguageAsText((String) comboBoxOperator.getItemAt(i));
            if (getComparator().equals(value, language)) {
                comboBoxOperator.selectItem(i);
                return;
            }
        }
        throw new IllegalArgumentException("Language " + language + " is not found");
    }

    private String getLanguageAsText(String text) throws MissingResourceException {
        String value = text.length() > 0
                ? EditorSettings.getDefault().getLanguageName(text)
                : org.openide.util.NbBundle.getMessage(FormattingPanel.class, "LBL_AllLanguages"); //NOI18N
        return value;
    }

    public void selectCategory(String category) {
        JComboBoxOperator comboBoxOperator = getComboBoxByLabel("Category:");
        StringComparator c = this.getComparator();
        for (int i = 0; i < comboBoxOperator.getItemCount(); i++) {
            final Object item = comboBoxOperator.getItemAt(i);
            if (item instanceof PreferencesCustomizer) {
                String displayName = ((PreferencesCustomizer) item).getDisplayName();
                if (c.equals(displayName, category)) {
                    comboBoxOperator.selectItem(i);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Category " + category + " is not found");
    }

    public JComboBoxOperator getComboBoxByLabel(String labeledBy) throws IllegalStateException {
        return new JComboBoxOperator((JComboBox) getComponentByLabel(labeledBy));
    }

    public JSpinnerOperator getSpinnerOperatorByLabel(String labeledBy) {
        return new JSpinnerOperator((JSpinner) getComponentByLabel(labeledBy));
    }

    public JTextFieldOperator getTextFieldByLabel(String labeledBy) {
        return new JTextFieldOperator((JTextField) getComponentByLabel(labeledBy));
    }

    public JCheckBoxOperator getCheckboxOperatorByLabel(String labeledBy) {
        return new JCheckBoxOperator(formattingPanel, labeledBy);
    }

    public Component getComponentByLabel(String labeledBy) throws IllegalStateException {
        Component findSubComponent = formattingPanel.findSubComponent(new ComponentChooserByLabel(labeledBy));
        if (findSubComponent == null) {
            throw new IllegalStateException("Component labeled by JLabel(" + labeledBy + ") is not found");
        }
        return findSubComponent;
    }

    static class ComponentChooserByLabel implements ComponentChooser {

        private final String label;

        public ComponentChooserByLabel(String label) {
            this.label = label;
        }

        @Override
        public boolean checkComponent(Component comp) {
            if (comp instanceof JComponent) {
                Object labeledBy = ((JComponent) comp).getClientProperty("labeledBy");                
                if (labeledBy != null && (labeledBy instanceof JLabel)) {                    
                    String labelText = ((JLabel) labeledBy).getText();                    
                    return getDefaultStringComparator().equals(labelText, this.label);
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return "Componet labeled by " + label;
        }
    }

    private AllLanguageTabsAndIndentsOperator allLanguageTabsAndIndentsOperator;
    private JavaTabsAndIndentsOperator javaTabsAndIndentsOperator;
    private AlignmentOperator alignmentOperator;

    public AllLanguageTabsAndIndentsOperator getAllLanguageTabsAndIndentsOperator() {
        if (allLanguageTabsAndIndentsOperator == null) {
            allLanguageTabsAndIndentsOperator = new AllLanguageTabsAndIndentsOperator(this);
        }
        return allLanguageTabsAndIndentsOperator;
    }

    public JavaTabsAndIndentsOperator getJavaTabsAndIndentsOperator() {
        if (javaTabsAndIndentsOperator == null) {
            javaTabsAndIndentsOperator = new JavaTabsAndIndentsOperator(this);
        }
        return javaTabsAndIndentsOperator;
    }

    public AlignmentOperator getAlignmentOperator() {
        if (alignmentOperator == null) {
            alignmentOperator = new AlignmentOperator(this);
        }
        return alignmentOperator;
    }

    public static void restoreDefaultValues() {
        FormattingOptionsOperator formattingOperator = FormattingOptionsOperator.invoke(true);
        formattingOperator.getAllLanguageTabsAndIndentsOperator().restoreDefaultsValues();
        formattingOperator.getJavaTabsAndIndentsOperator().restoreDefaultsValues();
        formattingOperator.getAlignmentOperator().restoreDefaultsValues();
        formattingOperator.ok();
    }
}
