/*
 *                 Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2004 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package gui.window;

import org.netbeans.jellytools.properties.Property;
import org.netbeans.jellytools.properties.editors.ColorCustomEditorOperator;

import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Test of Color Property Editor.
 *
 * @author  mmirilovic@netbeans.org
 */
public class PropertyEditorColor extends PropertyEditors {
    
    /** Creates a new instance of PropertyEditorColor */
    public PropertyEditorColor(String testName) {
        super(testName);
    }
    
    /** Creates a new instance of PropertyEditorColor */
    public PropertyEditorColor(String testName, String performanceDataName) {
        super(testName,performanceDataName);
    }
    
    private Property property;
    
    public void prepare(){
        property = findProperty("Color", propertiesWindow);
    }
    
    public ComponentOperator open(){
        openPropertyEditor();
        return new ColorCustomEditorOperator("Color");
    }

}
