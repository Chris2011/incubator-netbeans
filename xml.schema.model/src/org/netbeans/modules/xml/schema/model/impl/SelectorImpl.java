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


package org.netbeans.modules.xml.schema.model.impl;

import org.netbeans.modules.xml.schema.model.*;
import org.netbeans.modules.xml.schema.model.visitor.SchemaVisitor;
import org.w3c.dom.Element;

/**
 * Provide implementation for selector.
 * @author Chris Webster
 */
public class SelectorImpl extends SchemaComponentImpl 
implements Selector {
    public SelectorImpl(SchemaModelImpl model) {
        this(model,createNewComponent(SchemaElements.SELECTOR, model));
    }
    
    public SelectorImpl(SchemaModelImpl model, Element e) {
        super(model,e);
    }

	/**
	 *
	 *
	 */
	public Class<? extends SchemaComponent> getComponentType() {
		return Selector.class;
	}

    public void setXPath(String xPath) {
        setAttribute(XPATH_PROPERTY, SchemaAttributes.XPATH, xPath);
    }

    public void accept(SchemaVisitor v) {
        v.visit(this);
    }

    public String getXPath() {
        return getAttribute(SchemaAttributes.XPATH);
    }
}
