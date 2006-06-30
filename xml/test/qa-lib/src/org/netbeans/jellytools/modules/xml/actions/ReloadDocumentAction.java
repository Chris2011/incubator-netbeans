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
package org.netbeans.jellytools.modules.xml.actions;

import java.awt.event.KeyEvent;
import org.netbeans.jellytools.Bundle;
import org.netbeans.jellytools.actions.ActionNoBlock;

/** ReloadDocumentAction class
 * @author <a href="mailto:mschovanek@netbeans.org">Martin Schovanek</a> */
public class ReloadDocumentAction extends ActionNoBlock {

    private static final String popup =
    Bundle.getStringTrimmed("org.netbeans.modules.xml.core.actions.Bundle", "PROP_UpdateDocument");

    /** creates new ReloadDocumentAction instance */
    public ReloadDocumentAction() {
        super(null, popup, "org.netbeans.modules.xml.core.actions.XMLUpdateDocumentAction");
    }
}