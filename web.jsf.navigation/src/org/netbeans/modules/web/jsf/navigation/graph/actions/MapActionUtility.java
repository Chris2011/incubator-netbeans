/*
 * MapActionUtility.java
 *
 * Created on March 19, 2007, 5:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.modules.web.jsf.navigation.graph.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import org.netbeans.api.visual.graph.GraphPinScene;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author joelle
 */
public class MapActionUtility {
    
    /** Creates a new instance of MapActionUtility */
    public MapActionUtility() {
    }
    
    public static ActionMap initActionMap() {
        ActionMap actionMap = new ActionMap();
        //        // Install the actions
        //        actionMap.put("handleTab", handleTab);
        //        actionMap.put("handleEscape", handleEscape);
        //
        //        actionMap.put("handleLinkStart", handleLinkStart);
        //        actionMap.put("handleLinkEnd", handleLinkEnd);
        //
        //        actionMap.put("handleZoomPage", handleZoomPage);
        //        actionMap.put("handleUnZoomPage", handleUnZoomPage);
        //        actionMap.put("handleOpenPage", handleOpenPage);
        //        actionMap.put("handleNewWebForm", handleNewWebForm);
        //
        //        actionMap.put("handleLeftArrowKey", handleLeftArrowKey);
        //        actionMap.put("handleRightArrowKey", handleRightArrowKey);
        //        actionMap.put("handleUpArrowKey", handleUpArrowKey);
        //        actionMap.put("handleDownArrowKey", handleDownArrowKey);
        //
        //        actionMap.put("handleAddCommandButton", handleAddCommandButton);
        //        actionMap.put("handleAddCommandLink", handleAddCommandLink);
        //        actionMap.put("handleAddImageHyperLink", handleAddImageHyperLink);
        //        actionMap.put("handlePopupMenu", handlePopupMenu);
        actionMap.put("handleDeleteKey", handleDeleteKey);
        return actionMap;
    }
    
    
    public static InputMap initInputMap() {
        InputMap inputMap = new InputMap();
        //        // Tab Key
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB,0), "handleTab");
        //        // Esc Key
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0), "handleEscape");
        //
        //        //Lower Case a
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK), "handleNewWebForm");
        //
        //        //Lower Case s,e,z,u
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S,0), "handleLinkStart");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E,0), "handleLinkEnd");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,0), "handleZoomPage");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U,0), "handleUnZoomPage");
        //
        // Keys enter, b,l,i
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "handleOpenPage");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_B,0), "handleAddCommandButton");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_L,0), "handleAddCommandLink");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_I,0), "handleAddImageHyperLink");
        //
        //        // Upper Case S,E,Z,U
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.SHIFT_MASK), "handleLinkStart");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.SHIFT_MASK), "handleLinkEnd");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.SHIFT_MASK), "handleZoomPage");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.SHIFT_MASK), "handleUnZoomPage");
        //
        // Keys B,L, I
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_B,InputEvent.SHIFT_MASK), "handleAddCommandButton");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.SHIFT_MASK), "handleAddCommandLink");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_I,InputEvent.SHIFT_MASK), "handleAddImageHyperLink");
        //
        //        // Non Numeric Key Pad arrow keys
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0), "handleLeftArrowKey");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0), "handleRightArrowKey");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0), "handleUpArrowKey");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0), "handleDownArrowKey");
        //
        //        // Numeric Key Pad arrow keys
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_LEFT,0), "handleLeftArrowKey");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_RIGHT,0), "handleRightArrowKey");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP,0), "handleUpArrowKey");
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_DOWN,0), "handleDownArrowKey");
        //
        //        // SHIFT + F10
        //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F10,InputEvent.SHIFT_MASK), "handlePopupMenu");
        //
        // DELETE
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0), "handleDeleteKey");
        return inputMap;
    }
    
    public static Action handleDeleteKey = new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
//            System.out.println("Action Event: " + event);
            Object obj = event.getSource();
            
            if ( obj instanceof Widget ){
                Widget widget = ((Widget)obj);
                Scene scene = widget.getScene();
                if( scene instanceof ObjectScene ) {
                    ObjectScene objScene = (ObjectScene)scene;
                    Set<Object> selectedObjects = (Set<Object>) objScene.getSelectedObjects();
                    for( Object myObject : selectedObjects ){
                        if( myObject instanceof Node ) {
                            Node node = (Node)myObject;
                            if ( node.canDestroy() ){
                                try                 {
                                    node.destroy();
                                } catch (IOException ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                            }
                        }
                    }
                }
                
            }
            
        }
    };
    
}
