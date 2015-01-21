/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2015 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2015 Sun Microsystems, Inc.
 */
package org.netbeans.modules.terminal.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import static javax.swing.Action.ACCELERATOR_KEY;
import javax.swing.KeyStroke;
import org.netbeans.lib.terminalemulator.Term;
import org.netbeans.modules.terminal.api.IOVisibility;
import org.netbeans.modules.terminal.ioprovider.Terminal;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.windows.InputOutput;

/**
 *
 * @author igromov
 */
@ActionID(id = ActionFactory.CLOSE_ACTION_ID, category = ActionFactory.CATEGORY)
@ActionRegistration(displayName = "#CTL_Close", lazy = false) //NOI18N
@ActionReferences({
    @ActionReference(path = ActionFactory.ACTIONS_PATH, name = "CloseAction") //NOI18N
})
public final class CloseAction extends TerminalAction {

    public CloseAction(Lookup context) {
	super(context);
	KeyStroke accelerator = KeyStroke.getKeyStroke(
		KeyEvent.VK_W,
		InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK
	);

	putValue(NAME, getMessage("CTL_Close")); //NOI18N
	putValue(ACCELERATOR_KEY, accelerator);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	Terminal terminal = getTerminal();

	if (!isEnabled()) {
	    return;
	}
	terminal.close();
    }

    @Override
    public boolean isEnabled() {
	return getTerminal().isClosable();
    }

    // --------------------------------------------- 
    public CloseAction() {
	super(null);
    }

    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
	return new CloseAction(actionContext);
    }
}
