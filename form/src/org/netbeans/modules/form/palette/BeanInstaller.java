/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is Forte for Java, Community Edition. The Initial
 * Developer of the Original Code is Sun Microsystems, Inc. Portions
 * Copyright 1997-2000 Sun Microsystems, Inc. All Rights Reserved.
 */

package com.netbeans.developer.modules.loaders.form.palette;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.jar.*;
import java.beans.PropertyVetoException;
import java.text.MessageFormat;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import org.openide.*;
import org.openide.cookies.InstanceCookie;
import org.openide.filesystems.*;
import org.openide.loaders.DataObject;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import com.netbeans.developer.modules.loaders.form.FormLoaderSettings;

/** Bean Installer
*
* @author Petr Hamernik
*/
public final class BeanInstaller extends Object {

  /** Text resources */
  static final ResourceBundle bundle = NbBundle.getBundle(BeanInstaller.class);

  /** Last opened directory */
  private static File lastDirectory;
  
  /** Borders used in dialogs */
  static final Border hasFocusBorder;
  static final Border noFocusBorder;

  /** Initialize static fields */
  static {
    hasFocusBorder = new LineBorder(UIManager.getColor("List.focusCellHighlight"));
    noFocusBorder = new EmptyBorder(1, 1, 1, 1);
    lastDirectory = null;
  }

  /** Extension of jar archive where to find module */
  static String JAR_EXT = ".jar";

  //==============================================================================
  // Installing new beans - user action.
  //==============================================================================
  
  /** Open the FileOpenDialog for the selection of Jar file and
  * install the selected module into the system.
  */
  public static void installBean() {
    File jarFile = selectJarModule();

    if (jarFile != null) {
      JarFileSystem jar = createJarForFile(jarFile);
      if (jar == null) {
        TopManager.getDefault().notify(new NotifyDescriptor.Message(
          bundle.getString("MSG_ErrorInFile"), NotifyDescriptor.ERROR_MESSAGE)
        );
      }
      else {
        LinkedList list = findJavaBeans(jar);
        
        if (list.size() == 0) {
          TopManager.getDefault().notify(new NotifyDescriptor.Message(
            bundle.getString("MSG_noBeansInJar"), NotifyDescriptor.INFORMATION_MESSAGE)
          );
          return;
        }

        BeanSelector sel = new BeanSelector(list);
        DialogDescriptor desc = new DialogDescriptor (
          sel, 
          bundle.getString("CTL_SelectJB"),
          true,
          null
        );
        desc.setHelpCtx (new HelpCtx (BeanInstaller.class.getName () + ".installBean"));

        TopManager.getDefault ().createDialog (desc).show ();
        if (desc.getValue () == NotifyDescriptor.OK_OPTION) {
        
          String pal = selectPaletteCategory();
          if (pal != null) {
            finishInstall(jar, sel.getSelectedBeans(), pal);
            java.awt.EventQueue.invokeLater (
              new Runnable () {
                public void run () {
                  ComponentPalette.getDefault ().updatePalette ();
                }
              }
            );
          }
        }
      }
    }
  }

  /** Open the palette category selector and if user confirms some category, installs specified beans into it.
  */
  public static void installBeans(InstanceCookie[] cookies) {
    String pal = selectPaletteCategory();

    if (pal == null) return; // installation cancelled

    ArrayList list = new ArrayList (cookies.length);
    for (int i = 0; i < cookies.length; i++) {
      list.add (cookies[i]);
    }

    if (pal != null) {
      finishInstall(null, list, pal);
    }
  }

  /** Scan all files with attributes in the given jar.
  * @return LinkedList of founded beans.
  */
  private static LinkedList findJavaBeans(JarFileSystem jar) {
    LinkedList foundJB = new LinkedList();

    // Looking for the beans
    Manifest manifest = jar.getManifest();
    Map entries = manifest.getEntries();

    Iterator it = entries.keySet().iterator();
    while (it.hasNext()) {
      String key = (String) it.next();
      String value = ((Attributes) entries.get(key)).getValue("Java-Bean");
      if ((value != null) && value.equalsIgnoreCase("True")) {
        if (key.endsWith(".class")) {
          String wholeName = key.substring(0, key.length() - 6).replace('/', '.').replace('\\', '.');
          int lastDot = wholeName.lastIndexOf('.');
          String pack;
          String name;
          if (lastDot == -1) {
            pack = "";
            name = wholeName;
          }
          else {
            pack = wholeName.substring(0, lastDot);
            name = wholeName.substring(lastDot + 1);
          }
          FileObject fo = jar.find(pack, name, "class");
          if (fo != null) {
            foundJB.add(fo);
          }
        }
      }
    }
    return foundJB;
  }
  
  /** Finishing the instalation of the java beans.
  * @param jar JarFileSystem - the source of JBs to be mounted, or null if not necessary
  * @param list Collection of FileObjects - selected JBs
  * @param pal palettecategory where to place beans.
  */
  private static void finishInstall(JarFileSystem jar, final Collection list, String pal) {
    addJarFileSystem (jar);

    if (pal == null) {
      pal = "Beans"; // default palette category
    }

    FileObject root = TopManager.getDefault().getRepository().getDefaultFileSystem().getRoot();
    FileObject paletteFolder = root.getFileObject ("Palette");
    if (paletteFolder == null) {
      return;
    }

    FileObject category = paletteFolder.getFileObject(pal);
    if (category == null) {
      try {
        category = paletteFolder.createFolder (pal);
      } catch (IOException e) {
        if (System.getProperty ("netbeans.debug.exceptions") != null) e.printStackTrace ();
        /* ignore */
        return;
      }
    }

    ClassLoader loader = TopManager.getDefault().currentClassLoader();
    Iterator it = list.iterator();
    LinkedList paletteNodes = new LinkedList();

    while (it.hasNext()) {
      Object obj = it.next();
      String name = null;
      if (obj instanceof FileObject) {
        name = ((FileObject)obj).getPackageName('.');
      } else if (obj instanceof InstanceCookie) {
        name = ((InstanceCookie)obj).instanceName ();
      } 
      if (name != null) createInstance(category, name, null);
    }
  }


  private static void addJarFileSystem (JarFileSystem jar) {
    boolean alreadyInstalled = false;
    if (jar != null) {
      Repository rep = TopManager.getDefault().getRepository();
      JarFileSystem jar2 = (JarFileSystem) rep.findFileSystem(jar.getSystemName());
      if (jar2 != null) {
        alreadyInstalled = true;
        jar = jar2;
      }
          
      if (!alreadyInstalled) {
        jar.setHidden(true);
        rep.addFileSystem(jar);
      }
    }
  }

  private static void createInstance(FileObject folder, String className, String iconName) {
    String fileName = formatName(className);
    FileLock lock = null;
    try {
      if (folder.getFileObject(fileName+".instance") == null) {
        FileObject fo = folder.createData(fileName, "instance");
        if (iconName != null) {
          lock = fo.lock ();
          java.io.OutputStream os = fo.getOutputStream (lock);
          String ic = "icon="+iconName;
          os.write (ic.getBytes ());
        }
        DataObject dobj = DataObject.find (fo);
        if (dobj != null) {
          // enforce creation of node so that it is displayed
          dobj.getNodeDelegate ();
        }
      }
    }
    catch (java.io.IOException e) {
      if (System.getProperty ("netbeans.debug.exceptions") != null) e.printStackTrace ();
      /* ignore */
    }
    finally {
      if (lock != null) {
        lock.releaseLock ();
      }
    }
  }

  private static String formatName (String className) {
    String ret = className.substring(className.lastIndexOf (".") + 1) + "[" + className.replace ('.', '-') + "]";
    return ret;
  }

  /** Opens dialog and lets user select category, where beans should be installed
  */
  private static String selectPaletteCategory() {
    PaletteSelector sel = new PaletteSelector();
    DialogDescriptor desc = new DialogDescriptor (
      sel, 
      bundle.getString("CTL_SelectPalette"),
      true,
      null
    );
    desc.setHelpCtx (new HelpCtx (BeanInstaller.class.getName () + ".selectPaletteCategory"));

    TopManager.getDefault ().createDialog (desc).show ();
    if (desc.getValue () == NotifyDescriptor.OK_OPTION) {
      return sel.getSelectedCategory();
    } else {
      return null;
    }
  }

  /** This method open FileChooser and selects
  * the jar file with the module.
  *
  * @return filename or null if operation was cancelled.
  */
  private static File selectJarModule() {
    JFileChooser chooser = new JFileChooser();

    chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
      public boolean accept(File f) {
        return (f.isDirectory() || f.getName().endsWith(JAR_EXT));
      }
      public String getDescription() {
        return bundle.getString ("CTL_JarArchivesMask");
      }
    });

    if (lastDirectory != null) {
      chooser.setCurrentDirectory(lastDirectory);
    }

    chooser.setDialogTitle(bundle.getString("CTL_SelectJar"));
    if (chooser.showDialog(TopManager.getDefault ().getWindowManager ().getMainWindow (),
                           bundle.getString("CTL_Select_Approve_Button"))
        == JFileChooser.APPROVE_OPTION) 
    {
      File f = chooser.getSelectedFile();
      lastDirectory = chooser.getCurrentDirectory();
      if ((f != null) && (f.isFile()) && f.getName ().endsWith(JAR_EXT)) {
        return f;
      }
    }
    return null;
  }

  /**
  * @return jar FS for the given name or null if some problems occured
  */
  private static JarFileSystem createJarForFile(File jarFile) {
    try {
      JarFileSystem jar = new JarFileSystem();
      jar.setJarFile(jarFile);
      return jar;
    }
    catch (PropertyVetoException e) {
      if (System.getProperty ("netbeans.debug.exceptions") != null) e.printStackTrace ();
      return null;
    }
    catch (IOException e) {
      if (System.getProperty ("netbeans.debug.exceptions") != null) e.printStackTrace ();
      /* ignore */
      return null;
    }
  }

  //==============================================================================
  // Auto loading beans on startup
  //==============================================================================

  /** Auto loading all jars - beans */
  public static void autoLoadBeans() {
    FormLoaderSettings settings = new FormLoaderSettings();

    File globalFolder = new File(System.getProperty("netbeans.home") + File.separator + "beans");
    try {
      globalFolder = new File(globalFolder.getCanonicalPath());
    } catch (IOException e) { 
      if (System.getProperty ("netbeans.debug.exceptions") != null) e.printStackTrace ();
      /* ignore */ 
    }

    File localFolder = new File(System.getProperty("netbeans.user") + File.separator + "beans");
    try {
      localFolder = new File(localFolder.getCanonicalPath());
    } catch (IOException e) { 
      if (System.getProperty ("netbeans.debug.exceptions") != null) e.printStackTrace ();
      /* ignore */ 
    }
    
    autoLoadFolder(globalFolder);
    if (!globalFolder.equals(localFolder))
      autoLoadFolder(localFolder);

    java.awt.EventQueue.invokeLater (
      new Runnable () {
        public void run () {
          ComponentPalette.getDefault ().updatePalette ();
        }
      }
    );
  }

  /** Loads the beans stored in the given folder.
  * @param folder - where to find jars
  */
  private static void autoLoadFolder(File folder) {
    if (!folder.exists())
      return;
    
    final String[] list = folder.list();
    final String base = folder.getAbsolutePath() + File.separator;
    Properties details = new Properties();
    FileInputStream fis = null;
    try {
      details.load(fis = new FileInputStream(base + "beans.properties"));
    } catch (IOException e) {
      if (System.getProperty ("netbeans.debug.exceptions") != null) e.printStackTrace ();
      // ignore in this case
    } finally {
      if (fis != null) try { fis.close (); } catch (IOException e) { /* ignore */ };
    }

    FileInputStream fis2 = null;
    Properties alreadyInstalled = new Properties();
    try {
      alreadyInstalled.load(fis2 = new FileInputStream(base + "installed.properties"));
    } catch (IOException e) {
      /* ignore - the file just does not exist */
    } finally {
      if (fis2 != null) try { fis2.close (); } catch (IOException e) { /* ignore */ };
    }

    String[] categories = ComponentPalette.getDefault ().getPaletteCategories();
    for (int i = 0; i < list.length; i++) {
      if (list[i].endsWith(JAR_EXT)) {
        if (alreadyInstalled.get(list[i]) == null) {
          String withoutExt = list[i].substring(0, list[i].length() - JAR_EXT.length());
          String categoryName = details.getProperty(withoutExt, withoutExt);
          if (autoLoadJar(new File (base + list[i]), categoryName, details.getProperty(withoutExt + ".beans"))) {
            alreadyInstalled.put(list[i], "true");
          }
        } else {
          // ensure, that the filesystems are present
          addJarFileSystem (createJarForFile(new File (base + list[i])));
        }
      }
    }

    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(base + "installed.properties");
      alreadyInstalled.store(fos, "Installed Archives");
    } catch (IOException e) {
      if (System.getProperty ("netbeans.debug.exceptions") != null) e.printStackTrace ();
      // ignore 
    } finally {
      if (fos != null) try { fos.close (); } catch (IOException e) { /* ignore */ };
    }

  }

  /** Loaded beans from the jar.
  * @param jarFile the jar File
  * @param palette category where to place the beans
  * @param selection the selection of beans which should be installed. If null, all beans are loaded.
  */
  private static boolean autoLoadJar(File jarFile, String paletteCategory, String selection) {
    JarFileSystem jar = createJarForFile(jarFile);
    if (jar == null) {
      TopManager.getDefault().notify(
        new NotifyDescriptor.Message(bundle.getString("MSG_ErrorInFile"),
                                     NotifyDescriptor.ERROR_MESSAGE)
        ); 
      return false;
    }
    else {
      JarFileSystem jar2 = (JarFileSystem) TopManager.getDefault ().getRepository ().findFileSystem (jar.getSystemName());
      if (jar2 != null)
        jar = jar2;

      LinkedList list = findJavaBeans(jar);
      if (selection != null) {
        Vector dest = new Vector();
        StringTokenizer tok = new StringTokenizer(selection, ", ", false);
        while (tok.hasMoreTokens()) {
          String token = tok.nextToken();
          String clName = token;
          String clPack = "";
          
          int lastDot = token.lastIndexOf('.');
          if ((lastDot != -1) && (!token.endsWith("."))) {
            clName = token.substring(lastDot + 1);
            clPack = token.substring(0, lastDot);
          }
          FileObject fo = jar.find(clPack, clName, "class");
          if (fo != null) {
            Iterator it = list.iterator ();
            while (it.hasNext()) {
              FileObject fo2 = (FileObject) it.next();
              if (fo.equals(fo2)) {
                dest.addElement(fo);
              }
            }
          }
        }
        finishInstall(jar, dest, paletteCategory);
      }
      return true;
    }
  }
  
  
  //==============================================================================
  // Inner classes
  //==============================================================================

  
  static class PaletteSelector extends JPanel {
    private JList list;

    static final long serialVersionUID =936459317386043582L;
    /** Creates a new ExceptionBox for given exception descriptor. */
    public PaletteSelector() {
      super(null);
      String[] categories = ComponentPalette.getDefault ().getPaletteCategories();

      list = new JList(categories);
      list.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

/*      list.addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent evt) {
          okButton.setEnabled(!list.isSelectionEmpty());
        }
      });
*/
/*      list.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
          if (e.getClickCount() == 2) {
            closeDlg(true);
          }
        }
      });
[PENDING - doubleclick]
*/
      BorderLayout layout = new BorderLayout();
      layout.setVgap(5);
      layout.setHgap(5);
      setLayout(layout);
      add(new JLabel(bundle.getString("CTL_PaletteCategories")), "North");
      add(new JScrollPane (list), "Center");
      setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    public String getSelectedCategory() {
      return (String) list.getSelectedValue ();
    }

    public Dimension getPreferredSize() {
      Dimension ret = super.getPreferredSize();
      ret.width = Math.max(ret.width, 350);
      ret.height = Math.max(ret.height, 250);
      return ret;
    }
  }  

  /** dialog which allows to select found beans */
  public static class BeanSelector extends JPanel {
    private JList list;
static final long serialVersionUID =-6038414545631774041L;
    /** Creates a new ExceptionBox for given exception descriptor. */
    public BeanSelector(LinkedList fileObjects) {
      super(null);
      Object[] arr = new Object[fileObjects.size ()];
      fileObjects.toArray (arr);

      list = new JList(arr);
      list.setCellRenderer(new FileObjectRenderer());

/*      list.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
          if (e.getClickCount() == 2) {
            closeDlg(true);
          }
        }
      });
[PENDING - doubleclick]
*/
// [PENDING - OK/Cancel enabled accorning to selection
      BorderLayout layout = new BorderLayout();
      layout.setVgap(5);
      layout.setHgap(5);
      setLayout(layout);
      add(new JLabel(bundle.getString("CTL_SelectBeans")), "North");
      add(new JScrollPane (list), "Center");
      setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    Collection getSelectedBeans() {
      Object[] sel = list.getSelectedValues ();
      ArrayList al = new ArrayList (sel.length);
      for (int i = 0; i < sel.length; i++) { 
        al.add (sel[i]);
      }
      return al;
    }

    public Dimension getPreferredSize() {
      Dimension ret = super.getPreferredSize();
      ret.width = Math.max(ret.width, 350);
      ret.height = Math.max(ret.height, 250);
      return ret;
    }
  } 
  
  static class FileObjectRenderer extends JLabel implements ListCellRenderer {
    static final long serialVersionUID =832555965217675765L;
    /** Creates a new NetbeansListCellRenderer */
    public FileObjectRenderer() {
      setOpaque(true);
      setBorder(noFocusBorder);
    }

    public Component getListCellRendererComponent(JList list,
                                                  Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
      if (!(value instanceof FileObject))
        return this;

      FileObject fo = (FileObject) value;

      setText(fo.getName());
      if (isSelected){
        setBackground(UIManager.getColor("List.selectionBackground"));
        setForeground(UIManager.getColor("List.selectionForeground"));
      }
      else {
        setBackground(list.getBackground());
        setForeground(list.getForeground());
      }
      setBorder(cellHasFocus ? hasFocusBorder : noFocusBorder);
      return this;
    }
  }
}

/*
 * Log
 *  17   Gandalf   1.16        9/23/99  Ian Formanek    Better notification in 
 *       case that the JAR Archive does not contain any JavaBeans.
 *  16   Gandalf   1.15        9/9/99   Ian Formanek    Exceptions notification
 *  15   Gandalf   1.14        8/10/99  Ian Formanek    Generated Serial Version
 *       UID
 *  14   Gandalf   1.13        8/9/99   Ian Formanek    Fixed bug which cused 
 *       newly installed beans not to be visible in the palette
 *  13   Gandalf   1.12        7/25/99  Ian Formanek    Fixed bug 2582 - Beans 
 *       tab is empty although the COmponent Palette node shows that there is 
 *       the Timer bean
 *  12   Gandalf   1.11        7/18/99  Ian Formanek    InstallToPaletteAction
 *  11   Gandalf   1.10        7/8/99   Jesse Glick     Context help.
 *  10   Gandalf   1.9         6/22/99  Ian Formanek    Fixed bug 2004 - The 
 *       dialog for selecting JavaBean isn't dialog commonly used in IDE but 
 *       classical MS Windows filedialog. Also after installing bean IDE must be
 *       restarted. 
 *  9    Gandalf   1.8         6/10/99  Ian Formanek    loadedBeans -> 
 *       properties rather than FormSettings
 *  8    Gandalf   1.7         6/9/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  7    Gandalf   1.6         6/7/99   Ian Formanek    
 *  6    Gandalf   1.5         6/7/99   Ian Formanek    Fixed problem with 
 *       beans.properties not containing item for JAR archive
 *  5    Gandalf   1.4         6/7/99   Ian Formanek    PaletteCategory is 
 *       created if it does not exist yet
 *  4    Gandalf   1.3         6/7/99   Ian Formanek    Allows to select beans 
 *       and category
 *  3    Gandalf   1.2         6/4/99   Ian Formanek    First cut of 
 *       autoLoadBeans
 *  2    Gandalf   1.1         5/17/99  Petr Hamernik   very simple version of 
 *       Beans installer
 *  1    Gandalf   1.0         5/17/99  Petr Hamernik   
 * $
 */
