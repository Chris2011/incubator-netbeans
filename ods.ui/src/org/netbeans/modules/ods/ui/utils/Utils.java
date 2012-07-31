/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.ods.ui.utils;

import java.awt.Font;
import java.awt.FontMetrics;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.Icon;
import javax.swing.JComponent;
import org.openide.awt.HtmlBrowser;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;

/**
 *
 * @author jpeska
 */
public class Utils {

    public static final Icon EXPAND_ICON = ImageUtilities.loadImageIcon("org/netbeans/modules/ods/ui/resources/arrow-down.png", true);
    public static final Icon COLLAPSE_ICON = ImageUtilities.loadImageIcon("org/netbeans/modules/ods/ui/resources/arrow-up.png", true);
    private final static int VISIBLE_START_CHARS = 20;
    private static final Logger LOG = Logger.getLogger("ODS ui"); // NOI18N

    public static String computeFitText(JComponent component, int maxWidth, String text, boolean bold) {
        if (text == null) {
            text = ""; // NOI18N
        }
        if (text.length() <= VISIBLE_START_CHARS + 3) {
            return text;
        }
        FontMetrics fm;
        if (bold) {
            fm = component.getFontMetrics(component.getFont().deriveFont(Font.BOLD));
        } else {
            fm = component.getFontMetrics(component.getFont());
        }
        int width = maxWidth;

        String sufix = "..."; // NOI18N
        int sufixLength = fm.stringWidth(sufix + " "); //NOI18N
        int desired = width - sufixLength;
        if (desired <= 0) {
            return text;
        }

        for (int i = 0; i <= text.length() - 1; i++) {
            String prefix = text.substring(0, i);
            int swidth = fm.stringWidth(prefix);
            if (swidth >= desired) {
                if (fm.stringWidth(text.substring(i + 1)) <= fm.stringWidth(sufix)) {
                    return text;
                }
                return prefix.length() > 0 ? prefix + sufix : text;
            }
        }
        return text;
    }

    public static void openBrowser(String url) {
        try {
            HtmlBrowser.URLDisplayer.getDefault().showURL(new URL(url));
        } catch (MalformedURLException ex) {
            getLogger().warning(NbBundle.getMessage(Utils.class, "ERR_OpenBrowser", url));
        }
    }

    public static Logger getLogger() {
        return LOG;
    }

    public static class Settings {

        private static final String SHOW_TASKS = "ods.ui.show_tasks";
        private static final String SHOW_SCM = "ods.ui.show_scm";
        private static final String SHOW_WIKI = "ods.ui.show_wiki";
        private static final String SHOW_BUILDS = "ods.ui.show_builds";

        public static boolean isShowTasks() {
            return getPreferences().getBoolean(SHOW_TASKS, true);
        }

        public static void setShowTasks(boolean showTasks) {
            getPreferences().putBoolean(SHOW_TASKS, showTasks);
        }

        public static boolean isShowScm() {
            return getPreferences().getBoolean(SHOW_SCM, true);
        }

        public static void setShowScm(boolean showScm) {
            getPreferences().putBoolean(SHOW_SCM, showScm);
        }

        public static boolean isShowWiki() {
            return getPreferences().getBoolean(SHOW_WIKI, true);
        }

        public static void setShowWiki(boolean showWiki) {
            getPreferences().putBoolean(SHOW_WIKI, showWiki);
        }

        public static boolean isShowBuilds() {
            return getPreferences().getBoolean(SHOW_BUILDS, true);
        }

        public static void setShowBuilds(boolean showBuilds) {
            getPreferences().putBoolean(SHOW_BUILDS, showBuilds);
        }

        private static Preferences getPreferences() {
            return NbPreferences.forModule(Settings.class);
        }
    }
}
