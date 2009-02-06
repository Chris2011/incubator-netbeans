/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */

package org.netbeans.modules.kenai.ui;

import org.netbeans.modules.kenai.ui.spi.KenaiProjectUIQuery;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.netbeans.modules.kenai.api.KenaiProject;
import org.netbeans.modules.kenai.ui.spi.UIUtils;
import org.netbeans.modules.kenai.ui.spi.KenaiProjectUI.Type;
import org.netbeans.modules.kenai.ui.spi.LinkNode;

/**
 *
 * @author Jan Becicka
 */
public class KenaiProjectWidget extends JPanel implements LinkNode.RefreshCallback {
    private KenaiProject project;

    public KenaiProjectWidget(KenaiProject project) {
        this.project = project;
        initComponents();
    }

    private void initComponents() {
        setLayout(new VerticalLayout());

        final JTextPane title = UIUtils.createHTMLPane();
        title.setBackground(UIManager.getColor("Label.background"));
        title.setText("<html><body>" +
                "<table border=\"0\" borderwith=\"0\" width=\"100%\"><tbody><tr><td align=\"left\">" +
                "My Project" +
                "</td>" +
                "<td align=\"right\"><a href=\"edit\">Edit</a></td></tr></table><body></html>");

        title.setOpaque(true);
        title.addHyperlinkListener(new HyperlinkListener() {

            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    JOptionPane.showMessageDialog(title, e.getDescription());
                }
            }
        });
//        ExpandableWidget widget = new ExpandableWidget(title, KenaiProjectUIQuery.getComponent(Type.BUILDS, project, this), false);
//        add(widget);
        validate();
    }

    public void refresh(LinkNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
