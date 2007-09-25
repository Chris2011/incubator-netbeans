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

package org.netbeans.modules.web.core.palette.items;

import java.util.ResourceBundle;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import org.netbeans.modules.web.core.palette.JSPPaletteUtilities;
import org.openide.text.ActiveEditorDrop;
import org.openide.util.NbBundle;


/**
 *
 * @author Libor Kotouc
 */
public class Query implements ActiveEditorDrop {

    public static String QUERY_DEFAULT = "SELECT column_name(s) FROM table_name"; // NOI18N
    SQLStmt stmt = null;
    private String variable = "";
    private int scopeIndex = SQLStmt.SCOPE_DEFAULT;
    private String dataSource = "";
    private String query = QUERY_DEFAULT;
    private String displayName;
    private String stmtLabel = "";
    private String stmtACSN = "";
    private String stmtACSD = "";

    public Query() {

        try {
            displayName = NbBundle.getBundle("org.netbeans.modules.web.core.palette.items.resources.Bundle").getString("NAME_jsp-Query"); // NOI18N
        } catch (Exception e) {
        }

        ResourceBundle bundle = NbBundle.getBundle("org.netbeans.modules.web.core.palette.items.Bundle"); // NOI18N
        try {
            stmtLabel = bundle.getString("LBL_Query_Stmt"); // NOI18N
        } catch (Exception e) {
        }
        try {
            stmtACSN = bundle.getString("ACSN_Query_Stmt"); // NOI18N
        } catch (Exception e) {
        }
        try {
            stmtACSD = bundle.getString("ACSD_Query_Stmt"); // NOI18N
        } catch (Exception e) {
        }

        stmt = new SQLStmt(variable, scopeIndex, dataSource, query, "QueryStmtCustomizer"); // NOI18N
    }

    public boolean handleTransfer(JTextComponent targetComponent) {

        boolean accept = stmt.customize(targetComponent, displayName, stmtLabel, stmtACSN, stmtACSD);
        if (accept) {
            String body = createBody();
            try {
                JSPPaletteUtilities.insert(body, targetComponent);
            } catch (BadLocationException ble) {
                accept = false;
            }
        }

        return accept;
    }

    private String createBody() {

        variable = stmt.getVariable();
        dataSource = stmt.getDataSource();

        if (variable.equals("")) {// NOI18N
            variable = JSPPaletteUtilities.CARET;
        } else if (dataSource.equals("")) {// NOI18N
            dataSource = JSPPaletteUtilities.CARET;
        }
        String strVariable = " var=\"\""; // NOI18N
        if (variable.length() > 0) {
            strVariable = " var=\"" + variable + "\""; // NOI18N
        }
        scopeIndex = stmt.getScopeIndex();
        String strScope = "";
        if (scopeIndex != SQLStmt.SCOPE_DEFAULT) {
            strScope = " scope=\"" + SQLStmt.scopes[scopeIndex] + "\""; // NOI18N
        }
        String strDS = " dataSource=\"\""; // NOI18N
        if (strDS.length() > 0) {
            strDS = " dataSource=\"" + dataSource + "\""; // NOI18N
        }
        query = stmt.getStmt();
        String strQuery = query;
        if (query.length() > 0) {
            strQuery += "\n"; // NOI18N
        }
        String queryBody = "<sql:query" + strVariable + strScope + strDS + ">\n" + strQuery + "</sql:query>";
        ; // NOI18N
        return queryBody;
    }
}