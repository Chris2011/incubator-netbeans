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

package com.netbeans.developer.modules.loaders.form;

import java.io.IOException;
import java.text.MessageFormat;

import com.netbeans.ide.*;
import com.netbeans.ide.actions.OpenAction;
import com.netbeans.ide.loaders.*;
import com.netbeans.ide.filesystems.*;
import com.netbeans.ide.util.actions.SystemAction;
//import com.netbeans.ide.windows.ComponentRefEvent;
//import com.netbeans.ide.windows.ComponentRefListener;
import com.netbeans.ide.nodes.Node;
import com.netbeans.developer.modules.loaders.java.JavaDataObject;
//import com.netbeans.developer.modules.loaders.form.formeditor.*;
//import com.netbeans.developer.modules.loaders.java.DocumentRef;
//import com.netbeans.developer.modules.loaders.java.JavaEditor;
//import com.netbeans.developer.modules.loaders.java.src.*;

/** The DataObject for forms.
*
* @author Ian Formanek, Petr Hamernik
*/
public class FormDataObject extends JavaDataObject implements FormCookie {
  /** generated Serialized Version UID */
//  static final long serialVersionUID = 7952143476761137063L;

  private static final String URL_ICON_BASE =
    "com/netbeans/developer/modules/resources/formObject";

  /** lock for closing window */
  private static final Object OPEN_FORM_LOCK = new Object ();

  /** The resource bundle for Java Objects */
//  private static java.util.ResourceBundle javaBundle =
//    com.netbeans.ide.util.NbBundle.getBundle("com.netbeans.developer.modules.locales.LoadersJavaBundle");

  public FormDataObject (FileObject ffo, FileObject jfo, FormDataLoader loader) throws DataObjectExistsException {
    super(jfo, loader);
    addSecondaryEntry (formEntry = new FileEntry (ffo) {
        /** saves the DesignForm into the .form file */
        public void save (boolean modified) {
/*          if (modified & !isModified())
            return;
          if (!isLoaded ()) // cannot save to the .form file if there is nothing to save
            return;
          designForm.getFormManager ().cancelSelection ();
          FileLock lock = null;
          java.io.OutputStream os = null;
          try {
            lock = takeLock();
            // we first save into memory to prevent corrupting the form file
            // if something goes wrong
            java.io.ByteArrayOutputStream barros = new java.io.ByteArrayOutputStream (10000);
            NbObjectOutputStream oos = new NbObjectOutputStream(barros);
            oos.writeObject(designForm);
            oos.close ();

            // now it is safely written in memory, so we can save it to the file
            os = getFile().getOutputStream(lock);
            barros.writeTo(os);
          }
          catch (Exception e) {
            String message = MessageFormat.format(FormLoaderSettings.formBundle.getString("FMT_ERR_SavingForm"),
                                                  new Object[] {getName(), e.getClass().getName()});
            TopManager.getDefault().notify(new NotifyDescriptor.Exception(e, message));
          }
          finally {
            if (lock != null)
              lock.releaseLock();
            if (os != null) {
              try {
                os.close();
              }
              catch (IOException e) {
              }
            } 
          } */
        }
      }
    ); 
    init ();
  }

  /** Initalizes the FormDataObject after deserialization */
  private void init() {
    formLoaded = false;
    templateInit = false;
    modifiedInit = false;
    componentRefRegistered = false;
  }

  boolean isLoaded () {
    return formLoaded;
  }

  /** Loads the DesignForm from the .form file */
  protected boolean loadForm () {
//    System.out.println("Loading form");
//    Thread.dumpStack();

/*    java.io.InputStream is = null;
    try {
      is = formEntry.getFile().getInputStream();
    } catch (java.io.FileNotFoundException e) {
      String message = MessageFormat.format(FormLoaderSettings.formBundle.getString("FMT_ERR_LoadingForm"),
                                            new Object[] {getName(), e.getClass().getName()});
      TopManager.getDefault().notify(new NotifyDescriptor.Exception(e, message));
      return false;
    }
    NbObjectInputStream ois = null;
    try {
      ois = new NbObjectInputStream(is);
      designForm = (DesignForm) ois.readObject ();
      FormEditor.displayErrorLog ();

      formLoaded = true;
      designForm.initialize (this);
      if (!modifiedInit) {
        setModified (false);
        if (editorLock != null) {
          editorLock.releaseLock();
          editorLock = null;
        }
        // though we regenerated, it should
        // not be different (AKA modified)
      }
    }
    catch (Throwable e) {
      if (System.getProperty ("netbeans.full.hack") != null) {
        e.printStackTrace ();
        System.out.println ("IOException during opening form: Opening empty form");
        switch (new FormLoaderSettings ().getEmptyFormType ()) {
          default:
          case 0: designForm = new JFrameForm(); break;
          case 1: designForm = new JDialogForm(); break;
          case 2: designForm = new JAppletForm(); break;
          case 3: designForm = new JPanelForm(); break;
          case 4: designForm = new FrameForm(); break;
          case 5: designForm = new DialogForm(); break;
          case 6: designForm = new AppletForm(); break;
          case 7: designForm = new PanelForm(); break;
          case 8: designForm = new JInternalFrameForm(); break;
        }

        formLoaded = true;
        designForm.initialize (this);
        if (!modifiedInit)
          setModified (false); // though we regenerated, it should not be different (AKA modified)
      } else {
        Stringring message = MessageFormat.format(FormLoaderSettings.formBundle.getString("FMT_ERR_LoadingForm"),
                                              new Object[] {getName(), e.getClass().getName()});
        TopManager.getDefault().notify(new NotifyDescriptor.Exception(e, message));
        return false;
      }
    }
    finally {
      if (ois != null) {
        try {
          ois.close();
        }
        catch (IOException e) {
        }
      }
    }

    // enforce recreation of children
    ((FormDataNode)nodeDelegate).updateFormNode ();
*/
    return true;
  }

  /** Help context for this object.
  * @return help context
  */
  public com.netbeans.ide.util.HelpCtx getHelpCtx () {
    return new com.netbeans.ide.util.HelpCtx ("com.netbeans.developer.docs.Users_Guide.usergd-using-div-12", "USERGD-USING-TABLE-2");
  }

  public boolean isOpened () {
    return formLoaded;
  }

  /** Method from OpenCookie */
  public void open() {
/*    TopManager.getDefault ().setStatusText (
      java.text.MessageFormat.format (
        FormLoaderSettings.formBundle.getString ("FMT_OpeningForm"),
        new Object[] { getName () }
      )
    );

    synchronized (OPEN_FORM_LOCK) {
      if (!formLoaded)
        if (!loadForm ()) return;
    }

    // show the ComponentInspector
    FormEditor.getComponentInspector().setVisible(true);

    designForm.getRADWindow().show();
    super.open();
    TopManager.getDefault ().setStatusText (""); */
  }

  /** returns an editor with the document */
/*  public JavaEditor prepareEditor (boolean visibility) {
    final JavaEditor je = super.prepareEditor(visibility);
    if (!componentRefRegistered) {
      componentRefRegistered = true;
      je.addComponentRefListener(new ComponentRefListener() {
          /** This method is called when number of components changes.
          * @param evt Adequate event.
          * /
          public void componentChanged(ComponentRefEvent evt) {
            if (evt.getNewValue () == 0) {
              je.removeComponentRefListener (this);
              designForm.getFormManager ().disposeFormManager ();
              designForm.getRADWindow().setVisible (false);
              designForm.getRADWindow().dispose ();
              com.netbeans.ide.explorer.ExplorerManager em = FormEditor.getExplorerManager ();
              if (em.getRootContext ().equals (designForm.getFormManager (). getComponentsRoot ()))
                FormEditor.formActivated (null);
              designForm = null;
              formLoaded = false;
              templateInit = false;
              componentRefRegistered = false;
            }
          }
        }
      );
    }
    return je;
  }

  public DocumentRef getDocument () {
    return super.getDocument ();
  }

  protected void discard () {
    super.discard ();
    formLoaded = false;
    templateInit = false;
  }
*/
  /** Method from FormCookie */
  public void gotoEditor() {
/*    synchronized (OPEN_FORM_LOCK) {
      if (!formLoaded)
        if (!loadForm ()) return;
    }
    super.open(); */
  }

  /** Method from FormCookie */
  public void gotoForm() {
/*    synchronized (OPEN_FORM_LOCK) {
      if (!formLoaded)
        if (!loadForm ()) return;
    }
    designForm.getRADWindow().show(); */
  }

  /** Method from FormCookie */
  public void gotoInspector() {
    // show the ComponentInspector
//    FormEditor.getComponentInspector().setVisible(true);
  }

  /** @returns the DesignForm of this Form */
/*  public DesignForm getDesignForm() {
    if (!formLoaded)
      loadForm ();
    return designForm;
  } */

  /** @returns the root Node of the nodes representing the AWT hierarchy */
/*  public RADFormNode getComponentsRoot() {
    if (!formLoaded)
      if (!loadForm ()) return null;
    return designForm.getFormManager().getComponentsRoot();
  } */

  /** Handles copy of the data object.
  * @param f target folder
  * @return the new data object
  * @exception IOException if an error occures
  */
  public DataObject handleCopy (DataFolder df) throws IOException {
    String suffix = existInFolder(formEntry.getFile(), df.getPrimaryFile ());
    FileObject ffo = formEntry.copy (df.getPrimaryFile (), suffix);
    FileObject jfo = getPrimaryEntry ().copy (df.getPrimaryFile (), suffix);
    FormDataObject fdo = new FormDataObject (ffo, jfo, (FormDataLoader)getMultiFileLoader ());
//    fdo.instantiated = true;
    return fdo;
  }

  /** Check if in specific folder exists fileobject with the same name.
  * If it exists user is asked for confirmation to rewrite, rename or cancel operation.
  * @param folder destination folder
  * @return the suffix which should be added to the name or null if operation is cancelled
  */
  private static String existInFolder(FileObject fo, FileObject folder) {
    String orig = fo.getName ();
    String name = FileUtil.findFreeFileName(
      folder, orig, fo.getExt ()
    );
    if (name.length () <= orig.length ()) {
      return "";
    } else {
      return name.substring (orig.length ());
    }
  }
  
  /** Handles creation of new data object from template. This method should
  * copy content of the template to destination folder and assign new name
  * to the new object.
  *
  * @param df data folder to create object in
  * @param name name to give to the new object (or <CODE>null</CODE>
  *    if the name is up to the template
  * @return new data object
  * @exception IOException if an error occured
  */
  public DataObject handleCreateFromTemplate (
    DataFolder df, String name
  ) throws IOException {
/*    if ((name != null) && (!com.netbeans.ide.util.Utilities.isJavaIdentifier (name)))
      throw new IOException ();
/*          java.text.MessageFormat.format (
              javaBundle.getString ("FMT_Not_Valid_Class_Name"),
              new Object[] { name }
              )
          ); * /
    FileObject ffo = formEntry.createFromTemplate (df, name);
    FileObject jfo = null;
    try {
      jfo = getPrimaryEntry ().createFromTemplate (df, name);
    } catch (IOException e) { // if the creation of *.java fails, we must remove the created .form
      FileLock lock = null;
      try {
        lock = ffo.lock ();
        ffo.delete(lock);
      } catch (IOException e2) {
        // ignore, what else can we do
      } finally {
        if (lock != null)
          lock.releaseLock();
      }
      throw e;
    }
    FormDataObject fdo = new FormDataObject (ffo, jfo);
    fdo.setTemplate(false);
    fdo.templateInit = true;
    fdo.instantiated = true;
    fdo.modifiedInit = true;
    return fdo; */
    return null;
  }

  /** This method is used by ParseManager to set the parsed information. */
/*  protected void setParsed(JavaFile parsed) {
    super.setParsed (parsed);
    synchronized (OPEN_FORM_LOCK) {
      if (templateInit) {
        templateInit = false;
        if (getDesignForm () != null) {
          getDesignForm ().postCreateInit ();
          try {
            save (true); // save only if modified
          }
          catch (java.io.IOException e) {
            TopManager.getDefault().notifyException(e); // [PENDING - notify different way]
          }
        }
      }
    }

    // update the namespace of global variables in the form
    JavaFile file = getParsed ();
    if ((file != null) && (formLoaded)) {
      ClassElement[] cl = file.getClasses ();
      for (int i = 0; i < cl.length; i++) {
        if (cl[i].getName ().getName ().equals(getName ())) { //main class in the java file
          VarElement[] variables = cl[i].getVariables ();
          String[] names = new String[variables.length];
          for (int j = 0; j < variables.length; j++)
            names[j] = variables[j].getName ().getName ();

          designForm.getFormManager ().getVariablesPool ().updateNameSpace (names);

          break;
        } // if
      } // for
    }

  } */

  /** Allows subclasses to create its own data object for provided
  * primary file. This implementation returns JavaDataObject.
  *
  * @param fo file object to create data object for
  * @exception IOException if something falls
  */
/*  protected DataObject createDataObject (FileObject fo) throws IOException {
    throw new InternalError ("Error creating FormDataObject");
  } */

  /** Provides node that should represent this data object. When a node for representation
  * in a parent is requested by a call to getNode (parent) it is the exact copy of this node
  * with only parent changed. This implementation creates instance
  * <CODE>DataNode</CODE>.
  * <P>
  * This method is called only once.
  *
  * @return the node representation for this data object
  * @see DataNode
  */
  protected Node createNodeDelegate () {
    FormDataNode node = new FormDataNode (this);
    node.setDefaultAction (SystemAction.get (OpenAction.class));
    return node;
  }

  /** @see com.netbeans.developer.modules.loaders.java.JavaDataObject#fileEntryAdded
  * This method only makes it public to this package.
  */
/*  protected void fileEntryAdded(FileEntry fe) {
    super.fileEntryAdded(fe);
  } */


//--------------------------------------------------------------------
// serialization

  private void readObject(java.io.ObjectInputStream is)
  throws java.io.IOException, ClassNotFoundException {
    is.defaultReadObject();
    init();
  }

//--------------------------------------------------------------------
// private variables

  /** True, if the design form has been loaded from the form file */
  transient private boolean formLoaded;
  /** If true, a postInit method is called after reparsing - used after createFromTemplate */
  transient private boolean templateInit;
  /** If true, the form is marked as modified after regeneration - used if created from template */
  transient private boolean modifiedInit;
  /** A flag to prevent multiple registration of ComponentRefListener */
  transient private boolean componentRefRegistered;


  /** The DesignForm of this form */
//  transient private DesignForm designForm;
  /** The entry for the .form file */
  private FileEntry formEntry;
}

/*
 * Log
 *  4    Gandalf   1.3         3/10/99  Ian Formanek    Gandalf updated
 *  3    Gandalf   1.2         2/11/99  Ian Formanek    getXXXPresenter -> 
 *       createXXXPresenter (XXX={Menu, Toolbar})
 *  2    Gandalf   1.1         1/6/99   Ian Formanek    Reflecting change in 
 *       datasystem package
 *  1    Gandalf   1.0         1/5/99   Ian Formanek    
 * $
 */
