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

package org.netbeans.core.windows.view.ui.toolbars;

import java.beans.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MenuListener;
import org.netbeans.core.NbPlaces;

import org.openide.util.NbBundle;
import org.openide.NotifyDescriptor;
import org.openide.DialogDescriptor;
import org.openide.ErrorManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileLock;
import org.openide.awt.JPopupMenuPlus;
import org.openide.awt.Toolbar;
import org.openide.awt.ToolbarPool;
import org.openide.loaders.XMLDataObject;
import org.openide.nodes.NodeOperation;
import org.openide.util.Lookup;
import org.openide.util.Utilities;

import org.w3c.dom.*;
import org.xml.sax.*;
import org.openide.windows.WindowManager;

import org.netbeans.core.windows.services.ToolbarFolderNode;
import org.netbeans.core.windows.WindowManagerImpl;

/** Toolbar configuration.
 * It can load configuration from DOM Document, store configuration int XML file. 
 * Toolbar configuration contains list of all correct toolbars (toolbars which are
 * represented int ToolbarPool too), waiting toolbars (toolbars which was described
 * [it's position, visibility] but there is representation int ToolbarPool).
 * There is list of rows (ToolbarRow) and map of invisible toolbars.
 *
 * @author Libor Kramolis
 */
public final class ToolbarConfiguration extends Object 
implements ToolbarPool.Configuration, PropertyChangeListener {
    /** location outside the IDE */
    protected static final String TOOLBAR_DTD_WEB           =
        "http://www.netbeans.org/dtds/toolbar.dtd"; // NOI18N
    /** toolbar dtd public id */
    protected static final String TOOLBAR_DTD_PUBLIC_ID     =
        "-//NetBeans IDE//DTD toolbar//EN"; // NOI18N
    /** toolbar prcessor class */
    protected static final Class  TOOLBAR_PROCESSOR_CLASS   = ToolbarProcessor.class;
    /** toolbar icon base */
    protected static final String TOOLBAR_ICON_BASE         =
        "/org/netbeans/core/windows/toolbars/xmlToolbars"; // NOI18N
    
    /** error manager */
    private static ErrorManager ERR = ErrorManager.getDefault ().getInstance ("org.netbeans.core.windows.toolbars"); // NOI18N

    /** last time the document has been reloaded */
    private volatile long lastReload;
    
    /** xml extension */
    protected static final String EXT_XML                   = "xml"; // NOI18N
//      /** xmlinfo extension */
//      protected static final String EXT_XMLINFO               = "xmlinfo"; // NOI18N

    /** xml element for configuration (root element) */
    protected static final String TAG_CONFIG                = "Configuration"; // NOI18N
    /** xml element for row */
    protected static final String TAG_ROW                   = "Row"; // NOI18N
    /** xml element for toolbar */
    protected static final String TAG_TOOLBAR               = "Toolbar"; // NOI18N
    /** xml attribute for toolbar name */
    protected static final String ATT_TOOLBAR_NAME          = "name"; // NOI18N
    /** xml attribute for toolbar position */
    protected static final String ATT_TOOLBAR_POSITION      = "position"; // NOI18N
    /** xml attribute for toolbar visible */
    protected static final String ATT_TOOLBAR_VISIBLE       = "visible"; // NOI18N

    /** standard panel for all configurations */
    private static ToolbarPanel  toolbarPanel;
    /** mapping from configuration instances to their names */
    private static WeakHashMap confs2Names = new WeakHashMap(10);
    
    /** toolbar layout manager for this configuration */
    private        ToolbarLayout toolbarLayout;
    /** toolbar drag and drop listener */
    private   ToolbarDnDListener toolbarListener;

    /** All toolbars which are represented in ToolbarPool too. */
    private WeakHashMap allToolbars;
    /** List of visible toolbar rows. */
    private Vector      toolbarRows;
    /** All invisible toolbars (visibility==false || tb.isCorrect==false). */
    private HashMap     invisibleToolbars;
    
    private JMenu       toolbarMenu;
    /** Toolbars which was described in DOM Document,
	but which aren't represented in ToolbarPool.
	For exapmle ComponentPalette and first start of IDE. */
    private WeakHashMap waitingToolbars;
    /** Name of configuration. */
    private String      configName;
    /** Display name of configuration. */
    private String      configDisplayName;
    /** Cached preferred width. */
    private int         prefWidth;

   // private static final ResourceBundle bundle = NbBundle.getBundle (ToolbarConfiguration.class);

    /** Creates new empty toolbar configuration for specific name.
     * @param name new configuration name
     */
    public ToolbarConfiguration (String name, String displayName) {
        configName = name;
        configDisplayName = displayName;
        initInstance ();
        // asociate name and configuration instance
        confs2Names.put(this, name);
    }

    /** Creates new toolbar configuration for specific name and from specific XMLDataObject
     * @param name new configuration name
     * @param xml XMLDataObject representing a toolbar configuration
     */
    public ToolbarConfiguration(XMLDataObject xml) throws IOException {
        this(xml.getNodeDelegate().getName(), xml.getNodeDelegate().getDisplayName());
        readConfig(xml);
    }

    private void readConfig(XMLDataObject xml) throws IOException {
        Parser parser = xml.createParser();
        
        parser.setEntityResolver(new EntityResolver() {
            public InputSource resolveEntity(String pubid, String sysid) {
                return new InputSource(new java.io.ByteArrayInputStream(new byte[0]));
            }
        });

        HandlerBase handler = new HandlerBase() {
            private ToolbarRow currentRow = null;
            
            public void startElement(String name, AttributeList amap) throws SAXException {
                if (TAG_ROW.equals(name)) {
                    currentRow = new ToolbarRow(ToolbarConfiguration.this);
                    addRow(currentRow);
                }
                else if (currentRow != null && TAG_TOOLBAR.equals(name)) {
                    String tbname = amap.getValue(ATT_TOOLBAR_NAME);
                    if (tbname == null || tbname.equals("")) // NOI18N
                        return;

                    String  posStr = amap.getValue(ATT_TOOLBAR_POSITION);
                    Integer pos = null;
                    if (posStr != null)
                        pos = new Integer(posStr);
                    
                    String visStr = amap.getValue(ATT_TOOLBAR_VISIBLE);
                    Boolean vis;
                    if (visStr != null)
                        vis = Boolean.valueOf(visStr);
                    else
                        vis = Boolean.TRUE;
                        
                    addToolbar(currentRow, checkToolbarConstraints (tbname, pos, vis));
                }
            }
                    
            public void endElement(String name) throws SAXException {
                if (TAG_ROW.equals(name)) {
                    currentRow = null;
                }
            }
        };

        //parser.setErrorHandler(listener);
        parser.setDocumentHandler(handler);
            
        try {
            parser.parse(new InputSource(xml.getPrimaryFile().getInputStream()));
        } catch (Exception saxe) {
            IOException ex = new IOException (saxe.toString());
            ErrorManager.getDefault().annotate(ex, saxe);
            throw ex;
        }
        checkToolbarRows();
    }
    
    /** Clean all the configuration parameters.
     */
    private void initInstance () {
        allToolbars = new WeakHashMap();
        waitingToolbars = new WeakHashMap();
        toolbarRows = new Vector();
        invisibleToolbars = new HashMap();
        toolbarListener = new ToolbarDnDListener (this);
    }
    
    /** @return returns string from bundle for given string pattern */
    static final String getBundleString (String bundleStr) {
        return NbBundle.getMessage(ToolbarConfiguration.class, bundleStr);
    }
    
    /** Finds toolbar configuration which has given name.
     * @return toolbar configuration instance which ID is given name or null
     * if no such configuration can be found */
    public static final ToolbarConfiguration findConfiguration (String name) {
        Map.Entry curEntry = null;
        for (Iterator iter = confs2Names.entrySet().iterator(); iter.hasNext(); ) {
            curEntry = (Map.Entry)iter.next();
            if (name.equals((String)curEntry.getValue())) {
                return (ToolbarConfiguration)curEntry.getKey();
            }
        }
        // no luck
        return null;
    }

    /** Add toolbar to list of all toolbars.
     * If specified toolbar constraints represents visible component 
     * it is added to specified toolbar row.
     * Othewise toolbar constraints is added to invisible toolbars.
     *
     * @param row toolbar row of new toolbar is part
     * @param tc added toolbar represented by ToolbarConstraints
     */
    void addToolbar (ToolbarRow row, ToolbarConstraints tc) {
        if (tc == null)
            return;

        if (tc.isVisible())
            row.addToolbar (tc);
        else {
            int rI;
            if (row == null)
                rI = toolbarRows.size();
            else
                rI = toolbarRows.indexOf (row);
            invisibleToolbars.put (tc, new Integer (rI));
        }
        allToolbars.put (tc.getName(), tc);
    }

    /** Remove toolbar from list of all toolbars.
     * This could mean that toolbar is represented only in DOM document.
     *
     * @param name name of removed toolbar
     */
    ToolbarConstraints removeToolbar (String name) {
        ToolbarConstraints tc = (ToolbarConstraints)allToolbars.remove (name);
        if (tc.destroy())
            checkToolbarRows();
        return tc;
    }

    /** Add toolbar row as last row.
     * @param row added toolbar row
     */
    void addRow (ToolbarRow row) {
        addRow (row, toolbarRows.size());
    }

    /** Add toolbar row to specific index.
     * @param row added toolbar row
     * @param index specified index of toolbar position
     */
    void addRow (ToolbarRow row, int index) {
	/* It is important to recompute row neighbournhood. */
        ToolbarRow prev = null;
        ToolbarRow next = null;
        try {
            prev = (ToolbarRow)toolbarRows.elementAt (index - 1);
        } catch (ArrayIndexOutOfBoundsException e) { }
        try {
            next = (ToolbarRow)toolbarRows.elementAt (index);
        } catch (ArrayIndexOutOfBoundsException e) { }

        if (prev != null)
            prev.setNextRow (row);
        row.setPrevRow (prev);
        row.setNextRow (next);
        if (next != null)
            next.setPrevRow (row);

        toolbarRows.insertElementAt (row, index);
        updateBounds (row);
    }

    /** Remove toolbar row from list of all rows.
     * @param row removed toolbar row
     */
    void removeRow (ToolbarRow row) {
	/* It is important to recompute row neighbournhood. */
        ToolbarRow prev = row.getPrevRow();
        ToolbarRow next = row.getNextRow();
        if (prev != null) {
            prev.setNextRow (next);
        }
        if (next != null) {
            next.setPrevRow (prev);
        }

        toolbarRows.removeElement (row);
        updateBounds (next);
        revalidateWindow();
    }

    /** Update toolbar row cached bounds.
     * @param row updated row
     */
    void updateBounds (ToolbarRow row) {
        while (row != null) {
            row.updateBounds();
            row = row.getNextRow();
        }
    }
    
    private static final ToolbarPool toolbarPool () {
        return ToolbarPool.getDefault ();
    }

    /** Revalidates toolbar pool window.
     * It is important for change height when number of rows is changed.
     */
    void revalidateWindow () {
        // PENDING
        toolbarPanel().revalidate();
//        // #15930. Always replane even we are in AWT thread already.
//        SwingUtilities.invokeLater(new Runnable () {
//            public void run () {
//                doRevalidateWindow();
//            }
//        });
    }
    
//    /** Performs revalidating work */
//    private void doRevalidateWindow () {
//        toolbarPanel().revalidate();
//        java.awt.Window w = SwingUtilities.windowForComponent (toolbarPool ());
//        if (w != null) {
//            w.validate ();
//        }
//    } // PENDING

    /** 
     * @param row specified toolbar row
     * @return index of toolbar row
     */
    int rowIndex (ToolbarRow row) {
        return toolbarRows.indexOf (row);
    }

    /** Updates cached preferred width of toolbar configuration.
     */
    void updatePrefWidth () {
        Iterator it = toolbarRows.iterator();
        prefWidth = 0;
        int tryPrefWidth;
        while (it.hasNext()) {
            prefWidth = Math.max (prefWidth, ((ToolbarRow)it.next()).getPrefWidth());
        }
    }

    /**
     * @return configuration preferred width
     */
    int getPrefWidth () {
        return prefWidth;
    }

    /**
     * @return configuration preferred height, sum of preferred heights of rows.
     * If there is no row, preferred height is 0.
     */
    int getPrefHeight () {
        if (getRowCount() == 0) return 0;
        ToolbarRow lastRow = (ToolbarRow)toolbarRows.lastElement();
        return getRowVertLocation(lastRow) + lastRow.getPreferredHeight();
    }

    /** Checks toolbar rows. If there is some empty row it is removed.
     */
    void checkToolbarRows () {
        Object[] rows = toolbarRows.toArray();
        ToolbarRow row;

        for (int i = rows.length - 1; i >= 0; i--) {
            row = (ToolbarRow)rows[i];
            if (row.isEmpty())
                removeRow (row);
        }
    }

    /**
     * @return number of rows.
     */
    int getRowCount () {
        return toolbarRows.size();
    }

    /**
     * @param name toolbar constraints name
     * @return toolbar constraints of specified name
     */
    ToolbarConstraints getToolbarConstraints (String name) {
        return (ToolbarConstraints)allToolbars.get (name);
    }

    /** Checks toolbars constraints if there is some of specific name.
     * If isn't then is created new toolbar constraints. Othewise is old
     * toolbar constraints confronted with new values (position, visibility).
     * @param name name of checked toolbar
     * @param position position of toolbar
     * @param visible visibility of toolbar
     * @return toolbar constraints for specifed toolbar name
     */
    ToolbarConstraints checkToolbarConstraints (String name, Integer position, Boolean visible) {
        ToolbarConstraints tc = (ToolbarConstraints)allToolbars.get (name);
        if (tc == null)
            tc = new ToolbarConstraints (this, name, position, visible);
        else
            tc.checkNextPosition (position, visible);
        return tc;
    }

    /** Checks whole toolbar configuration.
     * It confronts list of all toolbars and waiting toolbars
     * with toolbars represented by ToolbarPool.
     *
     * @return true if there is some change and is important another check.
     */
    boolean checkConfigurationOver () {
        boolean change = false;
        String name;
        Object[] waNas = waitingToolbars.keySet().toArray();
        Object[] names = allToolbars.keySet().toArray();

	/* Checks ToolbarPool with waiting list. */
        for (int i = 0; i < waNas.length; i++) {
            name = (String)waNas[i];
            if (toolbarPool ().findToolbar (name) != null) {  /* If there is new toolbar in the pool
							      which was sometimes described ... */
                ToolbarConstraints tc = (ToolbarConstraints)waitingToolbars.remove (name);
		                                           /* ... it's removed from waiting ... */
                allToolbars.put (name, tc);                /* ... so it's added to correct toolbars ... */
                addInvisible (tc);                         /* ... and added to visible toolbars. */
                change = true;
            }
        }

	/* Checks ToolbarPool with list of all toolbars ... reverse process than previous for. */
        for (int i = 0; i < names.length; i++) {
            name = (String)names[i];
            if (toolbarPool ().findToolbar (name) == null) {  /* If there is toolbar which is not represented int pool ... */
                ToolbarConstraints tc = removeToolbar (name);  /* ... so let's remove toolbar from all toolbars ... */
                waitingToolbars.put (name, tc);                /* ... and add to waiting list. */
                invisibleToolbars.put (tc, new Integer (tc.rowIndex()));
                change = true;
            }
        }
        if (change || Utilities.arrayHashCode(toolbarPool().getConfigurations()) != lastConfigurationHash) {
            rebuildMenu();
        }
        return change;
    }

    private int lastConfigurationHash = -1;
    private void rebuildMenu() {
        if (toolbarMenu != null) {
            toolbarMenu.removeAll();
            fillToolbarsMenu(toolbarMenu);
        }
    }
    
    /** Removes toolbar from visible toolbars.
     * @param tc specified toolbar
     */
    void removeVisible (ToolbarConstraints tc) {
        invisibleToolbars.put (tc, new Integer (tc.rowIndex()));
        if (tc.destroy())
            checkToolbarRows();
        tc.setVisible (false);

        //reflectChanges();
    }

    /** Adds toolbar from list of invisible to visible toolbars.
     * @param tc specified toolbar
     */
    void addInvisible (ToolbarConstraints tc) {
        int rC = toolbarRows.size();
        int pos = ((Integer)invisibleToolbars.remove (tc)).intValue();
        tc.setVisible (true);
        for (int i = pos; i < pos + tc.getRowCount(); i++) {
            getRow (i).addToolbar (tc, tc.getPosition());
        }

        if (rC != toolbarRows.size())
            revalidateWindow();
        //reflectChanges();
    }

    /**
     * @param rI index of required row
     * @return toolbar row of specified index.
     * If rI is out of bounds then new row is created.
     */
    ToolbarRow getRow (int rI) {
        ToolbarRow row;
        int s = toolbarRows.size();
        if (rI < 0) {
            row = new ToolbarRow (this);
            addRow (row, 0);
        } else if (rI >= s) {
            row = new ToolbarRow (this);
            addRow (row);
        } else {
            row = (ToolbarRow)toolbarRows.elementAt (rI);
        }
        return row;
    }

    /**
     * @return toolbar row at last row position.
     */
    ToolbarRow createLastRow () {
        return getRow (toolbarRows.size());
    }

    /** Reactivate toolbar panel.
     * All components are removed and again added using ToolbarPool's list of correct toolbars.
     *
     * @param someBarRemoved if some toolbar was previously removed and is important to reflect changes
     * @param writeAtAll if false the content of disk will not be updated at all
     */
    void reactivatePanel (boolean someBarRemoved, boolean writeAtAll) {
        toolbarPanel().removeAll();
        prefWidth = 0;

        Toolbar tbs[] = toolbarPool ().getToolbars();
        Toolbar tb;
        ToolbarConstraints tc;
        String name;
        ToolbarRow newRow = null;
        boolean someBarAdded = false;

        for (int i = 0; i < tbs.length; i++) {
            tb = tbs[i];
            name = tb.getName();
            tc = (ToolbarConstraints)allToolbars.get (name);
            if (tc == null) {                          /* If there is no toolbar constraints description defined yet ... */
                if (newRow == null)
                    newRow = createLastRow();
                tc = new ToolbarConstraints (this, name, null, Boolean.TRUE);  /* ... there is created a new constraints. */
                addToolbar (newRow, tc);
                someBarAdded = true;
            }
            toolbarPanel().add (tb, tc);
        }

        revalidateWindow();

        if (writeAtAll) {
            if (someBarRemoved || someBarAdded)
            {
                //Do not reflect non user change
                //reflectChanges();
            }
        }
    }

    /**
     * @return true if if important reactivate component.
     */
    boolean isImportantActivateComponent () {
        Object[] names = allToolbars.keySet().toArray();
        Toolbar[] toolbars = toolbarPool ().getToolbars();

	/* Is number of toolbars int local list and toolbar pool list different? */
        if (names.length != toolbars.length)
            return true;

	/* Is name of current configuration differrent of last toolbar pool configuration? */
        if (! configName.equals (toolbarPool ().getConfiguration()))
            return true;

        return false;
    }

    /** Reflects configuration changes ... write it to document.
     */
    void reflectChanges () {
        try {
            writeDocument();
        } catch (IOException e) { /* ??? */ }
    }

    /////////////////////////////////
    // from ToolbarPool.Configuration

    /** Activates the configuration and returns right
     * component that can display the configuration.
     * @return representation component
     */
    public Component activate () {
        return activate (isImportantActivateComponent (), true);
    }
        
        
    /** Activate.
     * @param isImportant is the change of structure important
     * @param writeAtAll write changes to disk or not?
     */
    private Component activate (boolean isImportant, boolean writeAtAll) {
        toolbarPool().setToolbarsListener (toolbarListener);

        boolean someBarRemoved = checkConfigurationOver();

        if (isImportant || someBarRemoved) {
            if (toolbarLayout == null) {
                toolbarLayout = new ToolbarLayout (this);
            }
            toolbarPanel().setLayout (toolbarLayout);
            reactivatePanel (someBarRemoved, writeAtAll);
        }

        return toolbarPanel();
    }

    /** Name of the configuration.
     * @return the name
     */
    public String getName () {
        return configName;
    }
    
    public String getDisplayName () {
        return configDisplayName;
    }

    /** Popup menu that should be displayed when the users presses
     * right mouse button on the panel. This menu can contain
     * contains list of possible configurations, additional actions, etc.
     *
     * @return popup menu to be displayed
     */
    public JPopupMenu getContextMenu () {
        JPopupMenu menu = new JPopupMenuPlus();
        fillToolbarsMenu(menu);
        return menu;
    }

    /** Fills given menu with toolbars and configurations items and returns
     * filled menu. */ 
    public JMenu getToolbarsMenu (JMenu menu) {
        fillToolbarsMenu(menu);
        toolbarMenu = menu;
        return menu;
    }

    /** Fills given menu instance with list of toolbars and configurations */
    private void fillToolbarsMenu (JComponent menu) {
        lastConfigurationHash = Utilities.arrayHashCode(ToolbarPool.getDefault().getConfigurations());
        // generate list of available toolbars
        Iterator it = Arrays.asList (ToolbarPool.getDefault ().getToolbars ()).iterator ();
        while (it.hasNext()) {
            final Toolbar tb = (Toolbar)it.next();
            final String tbName = tb.getName();
            ToolbarConstraints tc = 
                (ToolbarConstraints)allToolbars.get (tb.getName());
/*            
            if (tc == null) {
                System.err.println("ToolbarConfiguration.java - error"); // NOI18N
                System.err.println("name: " + name); // NOI18N
                System.err.println(" all: " + allToolbars); // NOI18N
                continue;
            }
*/
            if (tc == null || tb == null) {
                //a toolbar configuration has been renamed (for whatever reason,
                //we permit this - I'm sure it's a popular feature).
                checkConfigurationOver();
            }

            
            if (tc != null && tb != null) {
                //May be null if a toolbar has been renamed
                JCheckBoxMenuItem mi = new JCheckBoxMenuItem (
                    tb.getDisplayName(), tc.isVisible()
                );
                mi.putClientProperty("ToolbarName", tbName); //NOI18N
                mi.addActionListener (new ActionListener () {
                                          public void actionPerformed (ActionEvent ae) {
                                              // #39741 fix
                                              // for some reason (unknown to me - mkleint) the menu gets recreated repeatedly, which 
                                              // can cause the formerly final ToolbarConstraints instance to be obsolete.
                                              // that's why we each time look up the current instance on the allToolbars map.
                                              ToolbarConstraints tc = (ToolbarConstraints)allToolbars.get (tbName );
                                              setToolbarVisible(tb, !tc.isVisible());
                                          }
                                      });
                menu.add (mi);
            }
        }
        menu.add (new JPopupMenu.Separator());
        // generate list of available toolbar configurations
        it = Arrays.asList (ToolbarPool.getDefault ().getConfigurations ()).iterator ();
        ButtonGroup bg = new ButtonGroup ();
        String current = ToolbarPool.getDefault ().getConfiguration ();
        while (it.hasNext()) {
            final String name = (String)it.next ();
            JRadioButtonMenuItem mi = new JRadioButtonMenuItem (
                findConfiguration(name).getDisplayName(), (name != null && name.equals(current))
            );
            mi.addActionListener (new ActionListener () {
                                      public void actionPerformed (ActionEvent e) {
                                          ErrorManager.getDefault().getInstance(getClass().getName()).log("triggered a change in toolbar config.");
                                          WindowManagerImpl.getInstance().setToolbarConfigName (name);
                                      }
                                  });
            bg.add (mi);
            menu.add (mi);
        }
        menu.add (new JPopupMenu.Separator());
        JMenuItem menuItem = new JMenuItem(NbBundle.getMessage(ToolbarConfiguration.class, "CTL_DisplayToolbars"));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                //#38613 fix -start
                ConfigureToolbarPanel.showConfigureDialog(new ToolbarFolderNode());
//                NodeOperation no = (NodeOperation)Lookup.getDefault().lookup (NodeOperation.class);
//                no.explore(new ToolbarFolderNode());
                //#38613 fix - end
            }
        });
        menu.add(menuItem);
        JMenuItem mi = new JMenuItem (getBundleString("PROP_saveAs")); // NOI18N
        mi.addActionListener (new ActionListener () {
                                  public void actionPerformed (ActionEvent e) {
                                      NotifyDescriptor.InputLine il = new NotifyDescriptor.InputLine
                                                                      (getBundleString("PROP_saveLabel"), // NOI18N
                                                                       getBundleString("PROP_saveDialog")); // NOI18N
                                      il.setInputText (getBundleString("PROP_saveName")); // NOI18N

                                      Object ok = org.openide.DialogDisplayer.getDefault ().notify (il);
                                      if (ok == NotifyDescriptor.OK_OPTION) {
                                          String s = il.getInputText();
                                          if (s.length() != 0) {
                                              try {
                                                  String newName = il.getInputText();
                                                  if (tryWriteDocument (newName)) {
                                                      WindowManagerImpl.getInstance().setToolbarConfigName (newName);
                                                  }
                                              } catch (IOException ioe) {
                                                  // Bugfix #10779 04 Sep 2001 by Jiri Rechtacek
                                                  // show a error message
                                                  ErrorManager.getDefault().notify (ioe);
                                              }
                                          }
                                      }
                                  }
                              });
        menu.add (mi);

    } // getContextMenu

    /** Make toolbar visible/invisible in this configuration
     * @param tb toolbar
     * @param b true to make toolbar visible
     */
    public void setToolbarVisible(Toolbar tb, boolean b) {
        ToolbarConstraints tc = getToolbarConstraints(tb.getName());
        if (b) {
            addInvisible(tc);
        } else {
            removeVisible(tc);
        }
        if (toolbarMenu != null) {
            //#39808 - somoewhat bruteforce approach, but works and is simple enough.
            // assumes the toolbar selection is always processed through the setToolbarVisible() method.
            //correct selection of the toolbar checkboxes in the main menu..
            Component[] elements = toolbarMenu.getMenuComponents();
            for (int i = 0; i < elements.length; i++) {
                JComponent component = (JComponent)elements[i];
                String tcmenu  = (String)component.getClientProperty("ToolbarName"); //NOI18N
                if (tcmenu != null && tcmenu.equals(tb.getName())) {
                    ((JCheckBoxMenuItem)component).setSelected(b);
                    break;
                }
            }
        }
        tb.setVisible(b);
        reflectChanges();
        firePropertyChange();
    }
    
    /** Returns true if the toolbar is visible in this configuration
     * @param tb toolbar
     * @return true if the toolbar is visible
     */
    public boolean isToolbarVisible(Toolbar tb) {
        ToolbarConstraints tc = getToolbarConstraints(tb.getName());
        return tc.isVisible();
    }
    
    PropertyChangeSupport pcs;
    
    public void addPropertyChangeListener(PropertyChangeListener l) {
        if (pcs == null) {
            pcs = new PropertyChangeSupport(this);
        }
        pcs.addPropertyChangeListener(l);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener l) {
        if (pcs != null) {
            pcs.removePropertyChangeListener(l);
        }
    }
    
    private void firePropertyChange() {
        if (pcs != null) {
            pcs.firePropertyChange("constraints", null, null); // some constraints have changed
        }
    }

    //// writting

    /** Try write document to file. It is asked for replacing if there is configuration of same name.
     * @param cn configuration file name
     * @return false if don't want replace
     */
    boolean tryWriteDocument (String cn) throws IOException {
        final FileObject tbFO = NbPlaces.getDefault().toolbars().getPrimaryFile();
        FileObject newFO = tbFO.getFileObject(cn, EXT_XML);

        // Bugfix #10779 04 Sep 2001 by Jiri Rechtacek
        // Windows are case-insensitive, find for a file by lower-case name
        if(Utilities.isWindows() && (newFO == null)) {
            FileObject children[] = tbFO.getChildren();
            for(int i=0; i < children.length; i++)
                if(children[i].getExt().equals(EXT_XML)
                    && cn.toLowerCase().equals(children[i].getName().toLowerCase())) {

                        // there is a file with same name by lower-case
                        newFO = children[i];
                        cn = children[i].getName();
                }
                
        }
        // enf of bugfix #10779
        
        if (newFO != null) {
            NotifyDescriptor replaceD = new NotifyDescriptor.Confirmation
                                        (MessageFormat.format (getBundleString("MSG_replaceConfiguration"), // NOI18N
                                                               new String [] { cn }),
                                         NotifyDescriptor.OK_CANCEL_OPTION, NotifyDescriptor.WARNING_MESSAGE);
            org.openide.DialogDisplayer.getDefault().notify (replaceD);
            if (replaceD.getValue() != DialogDescriptor.OK_OPTION) {
                return false;
            }
        }
        writeDocument (cn);
        return true;
    }

    /** Write actual toolbar configuration. */
    public void writeDocument () throws IOException {
        writeDocument (configName);
    }

    /** Write toolbar configuration for specified file name to xml.
     * @param cn configuration file name
     */
    private void writeDocument (final String cn) throws IOException {
        ERR.log ("writeDocument: " + cn); // NOI18N
        WritableToolbarConfiguration wtc = new WritableToolbarConfiguration (toolbarRows, invisibleToolbars);
        final StringBuffer sb = new StringBuffer ("<?xml version=\"1.0\"?>\n\n"); // NOI18N
        sb.append ("<!DOCTYPE ").append (TAG_CONFIG).append (" PUBLIC \""). // NOI18N
        append (TOOLBAR_DTD_PUBLIC_ID).append ("\" \"" + TOOLBAR_DTD_WEB + "\">\n\n").append (wtc.toString()); // NOI18N

        final FileObject tbFO = NbPlaces.getDefault().toolbars().getPrimaryFile();
        final FileSystem tbFS = tbFO.getFileSystem();

        tbFS.runAtomicAction (new FileSystem.AtomicAction () {
		public void run () throws IOException {
		    FileLock lock = null;
		    OutputStream os = null;
		    FileObject xmlFO = tbFO.getFileObject(cn, EXT_XML);
		    if (xmlFO == null)
			xmlFO = tbFO.createData (cn, EXT_XML);
		    try {
			lock = xmlFO.lock ();
			os = xmlFO.getOutputStream (lock);
			
			Writer writer = new OutputStreamWriter(os, "UTF-8"); // NOI18N
			writer.write(sb.toString());
			writer.close();
		    } finally {
                        lastReload = System.currentTimeMillis ();
                        ERR.log ("Setting last reload: " + lastReload); // NOI18N
                        
			if (os != null)
			    os.close ();
			if (lock != null)
			    lock.releaseLock ();
		    }
		}
	    });
        ERR.log ("writeDocument finished"); // NOI18N
    }

    /** lazy init of toolbar panel */
    private static final synchronized ToolbarPanel toolbarPanel () {
        if (toolbarPanel == null) {
            toolbarPanel = new ToolbarPanel();
        }
        return toolbarPanel;
    }

    /** Listens on changes in the document.
     */
    public void propertyChange(PropertyChangeEvent ev) {
        if (!XMLDataObject.PROP_DOCUMENT.equals(ev.getPropertyName ())) {
            // interested only in PROP_DOCUMENT properties
            return;
        }
        
        updateConfiguration((XMLDataObject)ev.getSource());
    }

    /** Updates configuration and also 'configuration over'.
     * @see #readConfig 
     * @see #checkConfigurationOver */
    void updateConfiguration(final XMLDataObject xmlDataObject) {
        long mod = xmlDataObject.getPrimaryFile ().lastModified().getTime ();
        ERR.log ("Checking modified: " + lastReload); // NOI18N
        //Bugfix #10196, this condition commented to make sure that all changes
        //will be applied.
        /*if (lastReload >= mod) {
            // not changed since last refresh
            return;
        }*/
        
        // [dafe] - code below demonstrates data integrity problem that occurs
        // in current toolbar impl. data in toolbar pool and data in toolbar
        // rows are not maintained centrally, and because of this invoke later,
        // they are inconsistent for a while (toolbar rows have older data, while
        // toolbar pool has already new content)
        // needs architecture redesign IMO
        SwingUtilities.invokeLater(new Runnable() {
            public void run () {
                try {
                    initInstance ();
                    readConfig(xmlDataObject);
                    checkConfigurationOver ();


                    if (configName.equals (toolbarPool ().getConfiguration())) {
                        ERR.log ("Activating the configuration"); // NOI18N
                        // 1st argument is true because the change is important
                        // 2nd argument is false, because it should prevent the system
                        // to write anything do
                        activate (true, false);
                    }

                } catch (IOException ex) {
                    ErrorManager.getDefault().notify (
                        ErrorManager.INFORMATIONAL, ex
                    );
                }
            }
        });
    }    
    
    /** @return upper vertical location of specified row
     */
    int getRowVertLocation (ToolbarRow row) {
        int index = rowIndex(row);
        int vertLocation = index * ToolbarLayout.VGAP;
        Iterator iter = toolbarRows.iterator();
        for (int i = 0; i < index; i++) {
            vertLocation += ((ToolbarRow)iter.next()).getPreferredHeight();
        }
        return vertLocation;
    }

    // class WritableToolbarConfiguration
    class WritableToolbarConfiguration {
	/** List of rows. */
        Vector rows;

	/** Create new WritableToolbarConfiguration.
	 * @param rs list of rows
	 * @param iv map of invisible toolbars
	 */
        public WritableToolbarConfiguration (Vector rs, Map iv) {
            initRows (rs);
            initInvisible (iv);
            removeEmptyRows();
        }

        /** Init list of writable rows.
	 * @param rs list of rows
	 */
        void initRows (Vector rs) {
            rows = new Vector();

            Iterator it = rs.iterator();
            while (it.hasNext()) {
                rows.addElement (new ToolbarRow.WritableToolbarRow ((ToolbarRow)it.next()));
            }
        }

	/** Init invisible toolbars in toolbar rows.
	 * @param iv map of invisible toolbars
	 */
        void initInvisible (Map iv) {
            Iterator it = iv.keySet().iterator();
            ToolbarConstraints tc;
            int row;
            while (it.hasNext()) {
                tc = (ToolbarConstraints)it.next();
                row = ((Integer)iv.get (tc)).intValue();
                for (int i = row; i < row + tc.getRowCount(); i++) {
                    getRow (i).addToolbar (tc);
                }
            }
        }

	/** Removes empty rows. */
        void removeEmptyRows () {
            ToolbarRow.WritableToolbarRow row;
            for (int i = rows.size() - 1; i >= 0; i--) {
                row = (ToolbarRow.WritableToolbarRow)rows.elementAt (i);
                if (row.isEmpty())
                    rows.removeElement (row);
            }
        }

	/**
	 * @param r row index
	 * @return WritableToolbarRow for specified row index
	 */
        ToolbarRow.WritableToolbarRow getRow (int r) {
            try {
                return (ToolbarRow.WritableToolbarRow)rows.elementAt (r);
            } catch (ArrayIndexOutOfBoundsException e) {
                rows.addElement (new ToolbarRow.WritableToolbarRow ());
                return getRow (r);
            }
        }

        /** @return ToolbarConfiguration in xml format. */
        public String toString () {
            StringBuffer sb = new StringBuffer();

            sb.append ("<").append (TAG_CONFIG).append (">\n"); // NOI18N
            Iterator it = rows.iterator();
            while (it.hasNext()) {
                sb.append (it.next().toString());
            }
            sb.append ("</").append (TAG_CONFIG).append (">\n"); // NOI18N

            return sb.toString();
        }
    } // end of class WritableToolbarConfiguration
} // end of class Configuration
