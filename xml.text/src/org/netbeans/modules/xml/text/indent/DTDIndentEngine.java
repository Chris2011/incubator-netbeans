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
package org.netbeans.modules.xml.text.indent;

import java.io.*;
import javax.swing.text.Document;

import org.netbeans.editor.ext.ExtFormatter;
import org.netbeans.editor.BaseKit;
import org.netbeans.modules.editor.EditorModule;
import org.netbeans.modules.editor.FormatterIndentEngine;
import org.openide.text.IndentEngine;
import org.openide.util.HelpCtx;

import org.netbeans.modules.xml.text.syntax.UniKit;

/**
 * @author  Libor Kramolis
 * @version 0.1
 */
public class DTDIndentEngine extends FormatterIndentEngine {

    /**
     */
    protected ExtFormatter createFormatter () {
        return new DTDFormatter (UniKit.class);
    }

    /**
     */
    public HelpCtx getHelpCtx () {
        return new HelpCtx (DTDIndentEngine.class);
    }

    /**
     */
    protected boolean acceptMimeType (String mimeType) {
        return true;
    }

}
