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

package org.netbeans.modules.form.palette;

import java.util.ArrayList;
import java.text.MessageFormat;
import java.io.File;

import org.openide.nodes.Node;
import org.openide.loaders.DataFolder;
import org.openide.filesystems.*;
import org.openide.ErrorManager;
import org.openide.util.NbBundle;
import org.netbeans.api.project.libraries.*;
import org.netbeans.api.project.*;

/**
 * Class providing various useful methods for palette classes.
 *
 * @author Tomas Pavek
 */

public final class PaletteUtils {

    private static FileObject paletteFolder;
    private static DataFolder paletteDataFolder;

    private PaletteUtils() {
    }

    // -----------

    static Node[] getItemNodes(Node categoryNode, boolean mustBeValid) {
        Node[] nodes = categoryNode.getChildren().getNodes(true);
        if (mustBeValid) {
            java.util.List list = null; // don't create until needed
            for (int i=0; i < nodes.length; i++) {
                if (nodes[i].getCookie(PaletteItem.class) != null) {
                    if (list != null)
                        list.add(nodes[i]);
                }
                else if (list == null) {
                    list = new ArrayList(nodes.length);
                    for (int j=0; j < i; j++)
                        list.add(nodes[j]);
                }
            }
            if (list != null) {
                nodes = new Node[list.size()];
                list.toArray(nodes);
            }
        }
        return nodes;
    }

    static Node[] getCategoryNodes(Node paletteNode, boolean mustBeVisible) {
        Node[] nodes = paletteNode.getChildren().getNodes(true);
        if (mustBeVisible) {
            java.util.List list = null; // don't create until needed
            for (int i=0; i < nodes.length; i++) {
                if (isValidCategoryNode(nodes[i], mustBeVisible)) {
                    if (list != null)
                        list.add(nodes[i]);
                }
                else if (list == null) {
                    list = new ArrayList(nodes.length);
                    for (int j=0; j < i; j++)
                        list.add(nodes[j]);
                }
            }
            if (list != null) {
                nodes = new Node[list.size()];
                list.toArray(nodes);
            }
        }
        return nodes;
    }

    static boolean isValidCategoryNode(Node node, boolean visible) {
        DataFolder df = (DataFolder) node.getCookie(DataFolder.class);
        return df != null
               && (!visible || !Boolean.TRUE.equals(df.getPrimaryFile()
                                       .getAttribute(PaletteNode.CAT_HIDDEN))); // NOI18N
    }

    static String getItemComponentDescription(PaletteItem item) {
        String[] classpath_raw = item.classpath_raw;
        if (classpath_raw == null || classpath_raw.length < 2) {
            String componentClassName = item.getComponentClassName();
            if (componentClassName != null) {
                if (componentClassName.startsWith("javax.") // NOI18N
                        || componentClassName.startsWith("java.")) // NOI18N
                    return getBundleString("MSG_StandardJDKComponent"); // NOI18N
                if (componentClassName.startsWith("org.netbeans."))
                    return getBundleString("MSG_NetBeansComponent"); // NOI18N
            }
        }
        else if (PaletteItem.JAR_SOURCE.equals(classpath_raw[0])) {
            return MessageFormat.format(
                getBundleString("FMT_ComponentFromJar"), // NOI18N
                new Object[] { classpath_raw[1] });
        }
        else if (PaletteItem.LIBRARY_SOURCE.equals(classpath_raw[0])) {
            Library lib = LibraryManager.getDefault().getLibrary(classpath_raw[1]);
            return MessageFormat.format(
                getBundleString("FMT_ComponentFromLibrary"), // NOI18N
                new Object[] { lib.getDisplayName() });
        }
        else if (PaletteItem.PROJECT_SOURCE.equals(classpath_raw[0])) {
            try {
                Project project = FileOwnerQuery.getOwner(new File(classpath_raw[1]).toURI());
                return MessageFormat.format(
                      getBundleString("FMT_ComponentFromProject"), // NOI18N
                      new Object[] { project.getProjectDirectory().getPath() })
                    .replace('/', File.separatorChar);
            }
            catch (Exception ex) {} // ignore
        }
        return getBundleString("MSG_UnspecifiedComponent"); // NOI18N
    }

    public static String getItemSourceDescription(PaletteItem item) {
        String[] classpath_raw = item.classpath_raw;
        if (classpath_raw == null || classpath_raw.length < 2) {
            String componentClassName = item.getComponentClassName();
            if (componentClassName != null) {
                if (componentClassName.startsWith("javax.") // NOI18N
                        || componentClassName.startsWith("java.")) // NOI18N
                    return getBundleString("MSG_StandardJDKSource"); // NOI18N
                if (componentClassName.startsWith("org.netbeans."))
                    return getBundleString("MSG_NetBeansSource"); // NOI18N
            }
        }
        else if (PaletteItem.JAR_SOURCE.equals(classpath_raw[0])) {
            return MessageFormat.format(
                getBundleString("FMT_JarSource"), // NOI18N
                new Object[] { classpath_raw[1] });
        }
        else if (PaletteItem.LIBRARY_SOURCE.equals(classpath_raw[0])) {
            Library lib = LibraryManager.getDefault().getLibrary(classpath_raw[1]);
            return MessageFormat.format(
                getBundleString("FMT_LibrarySource"), // NOI18N
                new Object[] { lib.getDisplayName() });
        }
        else if (PaletteItem.PROJECT_SOURCE.equals(classpath_raw[0])) {
            try {
                Project project = FileOwnerQuery.getOwner(new File(classpath_raw[1]).toURI());
                return MessageFormat.format(
                      getBundleString("FMT_ProjectSource"), // NOI18N
                      new Object[] { project.getProjectDirectory().getPath() })
                    .replace('/', File.separatorChar);
            }
            catch (Exception ex) {} // ignore
        }
        return getBundleString("MSG_UnspecifiedSource"); // NOI18N
    }

    public static FileObject getPaletteFolder() {
        if (paletteFolder != null)
            return paletteFolder;

        try {
            paletteFolder = Repository.getDefault().getDefaultFileSystem()
                                                     .findResource("Palette"); // NOI18N
            if (paletteFolder == null) // not found, create new folder
                paletteFolder = Repository.getDefault().getDefaultFileSystem()
                                  .getRoot().createFolder("Palette"); // NOI18N
        }
        catch (java.io.IOException ex) {
            throw new IllegalStateException("Palette folder not found and cannot be created."); // NOI18N
        }
        return paletteFolder;
    }

    static DataFolder getPaletteDataFolder() {
        if (paletteDataFolder == null)
            paletteDataFolder = DataFolder.findFolder(getPaletteFolder());
        return paletteDataFolder;
    }

    static String getBundleString(String key) {
        return NbBundle.getBundle(PaletteUtils.class).getString(key);
    }

}
