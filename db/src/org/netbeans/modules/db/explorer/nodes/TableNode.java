/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2001 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package com.netbeans.enterprise.modules.db.explorer.nodes;

import java.util.*;
import com.netbeans.ddl.*;
import com.netbeans.ddl.impl.*;
import com.netbeans.enterprise.modules.db.*;
import com.netbeans.enterprise.modules.db.explorer.*;
import com.netbeans.enterprise.modules.db.explorer.infos.*;

// Node for Table/View/Procedure things.

public class TableNode extends DatabaseNode
{
	public void setName(String newname)
	{
		try {
			DatabaseNodeInfo info = getInfo();
			Specification spec = (Specification)info.getSpecification();
			AbstractCommand cmd = spec.createCommandRenameTable(info.getName(), newname);
			cmd.execute();
			super.setName(newname);
			info.put(DatabaseNode.TABLE, newname);
		} catch (Exception e) {
			System.out.println("Unable to change the name: "+e);
		}
	}
}