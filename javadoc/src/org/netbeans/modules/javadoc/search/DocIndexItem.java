/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2000 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.javadoc.search;


import java.net.URL;
import java.util.Comparator;

/** Represents one item found in document index

 @author Petr Hrebejk
*/
class DocIndexItem extends Object {

    /** Standard comparators */
    public static final Comparator REFERENCE_COMPARATOR = new ReferenceComparator();
    public static final Comparator TYPE_COMPARATOR = new TypeComparator();
    public static final Comparator ALPHA_COMPARATOR = new AlphaComparator();

    private String text = null;
    private URL contextURL = null;
    private String spec = null;
    private String remark = null;
    private String pckg = null;
    private String declaringClass = null;    

    private int iconIndex = DocSearchIcons.ICON_NOTRESOLVED;

    public DocIndexItem ( String text, String remark, URL contextURL, String spec ) {
        this.text = text;
        this.remark = remark;
        this.contextURL = contextURL;
        this.spec = spec;
    }

    public URL getURL () throws java.net.MalformedURLException {
        return new URL( contextURL, spec );
    }

    public String toString() {
        if ( remark != null )
            return text + remark;
        else
            return text;
    }

    public int getIconIndex() {
        return iconIndex;
    }

    public void setIconIndex( int iconIndex ) {
        this.iconIndex = iconIndex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark( String remark ) {
        this.remark = remark;
    }

    public String getPackage() {
        return pckg == null ? "" : pckg; // NOI18N
    }

    public void setPackage( String pckg ) {
        this.pckg = pckg;
    }

    /** Getter for property declaringClass.
     * @return Value of property declaringClass.
 */
    public java.lang.String getDeclaringClass() {
        return declaringClass;
    }    

    /** Setter for property declaringClass.
     * @param declaringClass New value of property declaringClass.
 */
    public void setDeclaringClass(java.lang.String declaringClass) {
        this.declaringClass = declaringClass;
    }
    
    /** Getter for property field.
     * @return Value of property field.
 */
    public java.lang.String getField() {
        return text != null ? text : "";    //NOI18N
    }

    /** Setter for property field.
     * @param Value of property field.
    */
    public void setField(String text) {
        this.text = text;
    }

    // COMPARATOR INNER CLASSES ----------------------------------------------------------------

    static class ReferenceComparator implements java.util.Comparator {

        public int compare( Object dii1, Object dii2 ) {
            int res = ((DocIndexItem)dii1).getPackage().compareTo( ((DocIndexItem)dii2).getPackage() );

            return res != 0 ? res : DocIndexItem.ALPHA_COMPARATOR.compare( dii1, dii2 );
        }

        public boolean equals( Object comp ) {
            return ( comp instanceof ReferenceComparator );
        }

    }

    static class TypeComparator implements java.util.Comparator {

        public int compare( Object dii1, Object dii2 ) {
            return ((DocIndexItem)dii1).getIconIndex() - ((DocIndexItem)dii2).getIconIndex();
        }

        public boolean equals( Object comp ) {
            return ( comp instanceof TypeComparator );
        }

    }

    static class AlphaComparator implements java.util.Comparator {

        public int compare( Object dii1, Object dii2 ) {
            return ((DocIndexItem)dii1).toString().compareTo( ((DocIndexItem)dii2).toString() );
        }

        public boolean equals( Object comp ) {
            return ( comp instanceof AlphaComparator );
        }

    }
}
