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
package org.openide.text;


import java.awt.Image;
import java.beans.*;
import org.openide.util.*;


/** BeanInfo for PrintSettings.
*
* @author Ales Novak
*/
public class PrintSettingsBeanInfo extends SimpleBeanInfo {
    /** Returns the PrintSettings' icon */
    public Image getIcon(int type) {
        return Utilities.loadImage("org/openide/text/printSettings.gif"); // NOI18N
    }

    /** Descriptor of valid properties
    * @return array of properties
    */
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor[] desc = new PropertyDescriptor[] {
                    new PropertyDescriptor(PrintSettings.PROP_WRAP, PrintSettings.class), // 0
                    new PropertyDescriptor(PrintSettings.PROP_HEADER_FORMAT, PrintSettings.class), // 1
                    new PropertyDescriptor(PrintSettings.PROP_FOOTER_FORMAT, PrintSettings.class), // 2
                    new PropertyDescriptor(PrintSettings.PROP_HEADER_FONT, PrintSettings.class), // 3
                    new PropertyDescriptor(PrintSettings.PROP_FOOTER_FONT, PrintSettings.class), // 4
                    new PropertyDescriptor(PrintSettings.PROP_HEADER_ALIGNMENT, PrintSettings.class), // 5
                    new PropertyDescriptor(PrintSettings.PROP_FOOTER_ALIGNMENT, PrintSettings.class), // 6

                    //        new PropertyDescriptor(PrintSettings.PROP_PAGE_FORMAT, PrintSettings.class), // 7
                    new PropertyDescriptor(PrintSettings.PROP_LINE_ASCENT_CORRECTION, PrintSettings.class) // 8
                };
            desc[0].setDisplayName(NbBundle.getMessage(PrintSettings.class, "PROP_WRAP"));
            desc[0].setShortDescription(NbBundle.getMessage(PrintSettings.class, "HINT_WRAP"));
            desc[1].setDisplayName(NbBundle.getMessage(PrintSettings.class, "PROP_HEADER_FORMAT"));
            desc[1].setShortDescription(NbBundle.getMessage(PrintSettings.class, "HINT_HEADER_FORMAT"));
            desc[2].setDisplayName(NbBundle.getMessage(PrintSettings.class, "PROP_FOOTER_FORMAT"));
            desc[2].setShortDescription(NbBundle.getMessage(PrintSettings.class, "HINT_FOOTER_FORMAT"));
            desc[3].setDisplayName(NbBundle.getMessage(PrintSettings.class, "PROP_HEADER_FONT"));
            desc[3].setShortDescription(NbBundle.getMessage(PrintSettings.class, "HINT_HEADER_FONT"));
            desc[4].setDisplayName(NbBundle.getMessage(PrintSettings.class, "PROP_FOOTER_FONT"));
            desc[4].setShortDescription(NbBundle.getMessage(PrintSettings.class, "HINT_FOOTER_FONT"));
            desc[5].setDisplayName(NbBundle.getMessage(PrintSettings.class, "PROP_HEADER_ALIGNMENT"));
            desc[5].setShortDescription(NbBundle.getMessage(PrintSettings.class, "HINT_HEADER_ALIGNMENT"));
            desc[5].setPropertyEditorClass(PrintSettings.AlignmentEditor.class);
            desc[6].setDisplayName(NbBundle.getMessage(PrintSettings.class, "PROP_FOOTER_ALIGNMENT"));
            desc[6].setShortDescription(NbBundle.getMessage(PrintSettings.class, "HINT_FOOTER_ALIGNMENT"));
            desc[6].setPropertyEditorClass(PrintSettings.AlignmentEditor.class);

            /*
            desc[7].setDisplayName(PrintSettings.getString("PROP_PAGE_FORMAT"));
            desc[7].setShortDescription(PrintSettings.getString("HINT_PAGE_FORMAT"));
            desc[7].setPropertyEditorClass(PrintSettings.PageFormatEditor.class);
            */
            desc[7].setDisplayName(NbBundle.getMessage(PrintSettings.class, "PROP_LINE_ASCENT_CORRECTION"));
            desc[7].setShortDescription(NbBundle.getMessage(PrintSettings.class, "HINT_LINE_ASCENT_CORRECTION"));

            return desc;
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);

            return null;
        }
    }
}
