/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */
package org.netbeans.test.editor.search;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import junit.framework.Test;
import junit.textui.TestRunner;
import org.netbeans.test.editor.lib.EditorTestCase;
import org.netbeans.jellytools.EditorOperator;
import org.netbeans.jellytools.MainWindowOperator;
import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JEditorPaneOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.jellytools.modules.editor.SearchBarOperator;

/**
 *
 * @author Jiri Prox
 */
public class IncrementalSearchTest extends EditorTestCase{
    
    public IncrementalSearchTest(String name) {
        super(name);
    }
        
    public void testSearchForward() {
        openDefaultProject();
        openDefaultSampleFile();
        EditorOperator editor = new EditorOperator("testSearchForward.java");
        editor.setCaretPosition(4, 1);
        SearchBarOperator barOperator = SearchBarOperator.invoke(editor);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        t.clearText();
        new EventTool().waitNoEvent(100);
        t.typeText("p");
        t.pushKey(KeyEvent.VK_ENTER);
        assertSelection(editor, 114, 115);
        editor.setCaretPosition(4, 1);
        t.pushKey(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK);
        t.clearText();
        new EventTool().waitNoEvent(100);
        t.typeText("pu");
        t.pushKey(KeyEvent.VK_ENTER);
        assertSelection(editor, 130, 132);        
        editor.setCaretPosition(4, 1);
        t.pushKey(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK);
        t.clearText();
        new EventTool().waitNoEvent(100);
        t.typeText("pub");
        t.pushKey(KeyEvent.VK_ENTER);
        assertSelection(editor, 143, 146);        
        t.pushKey(KeyEvent.VK_N, KeyEvent.ALT_DOWN_MASK);        
        assertSelection(editor, 187, 190);
        t.pushKey(KeyEvent.VK_N, KeyEvent.ALT_DOWN_MASK);
        assertSelection(editor, 64, 67);
        MainWindowOperator mwo = MainWindowOperator.getDefault();
        assertEquals("'pub' found at 3:1; End of the document reached. Continuing search from beginning.",mwo.getStatusText());
        t.pushKey(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK);
        t.pushKey(KeyEvent.VK_ESCAPE);
        new EventTool().waitNoEvent(100);
        assertFalse("ToolBar not closed",barOperator.isVisible());
        closeFileWithDiscard();
    }
    
    public void testSearchBackwards() {
        openDefaultProject();
        openFile("Source Packages|org.netbeans.test.editor.search.IncrementalSearchTest", "testSearchForward");
        EditorOperator editor = new EditorOperator("testSearchForward");
        editor.setCaretPosition(11, 1);
        SearchBarOperator barOperator = SearchBarOperator.invoke(editor);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        t.clearText();
        new EventTool().waitNoEvent(100);
        t.typeText("pub");
        t.pushKey(KeyEvent.VK_ENTER,KeyEvent.SHIFT_DOWN_MASK);
        assertSelection(editor, 143, 146);
        t.pushKey(KeyEvent.VK_ENTER,KeyEvent.SHIFT_DOWN_MASK);
        assertSelection(editor, 64, 67);
        t.pushKey(KeyEvent.VK_ENTER,KeyEvent.SHIFT_DOWN_MASK);
        assertSelection(editor, 187, 190);        
        MainWindowOperator mwo = MainWindowOperator.getDefault();
        assertEquals("'pub' found at 12:5; Beginning of the document reached. Continuing search from end.",mwo.getStatusText());
        t.pushKey(KeyEvent.VK_ESCAPE);
        new EventTool().waitNoEvent(100);
        assertFalse("ToolBar not closed",barOperator.isVisible());
        editor.closeDiscard();
    }
    
    public void testMatchCase() {
        openDefaultProject();
        openFile("Source Packages|org.netbeans.test.editor.search.IncrementalSearchTest", "match.txt");
        EditorOperator editor = new EditorOperator("match.txt");
        editor.setCaretPosition(1, 1);
        new EventTool().waitNoEvent(100); // needed to compute layout
        SearchBarOperator barOperator = SearchBarOperator.invoke(editor);
        JCheckBoxOperator jcbo = barOperator.matchCaseCheckBox();
        jcbo.setSelected(true);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        t.clearText();
        new EventTool().waitNoEvent(100);
        t.typeText("Abc");
        t.pushKey(KeyEvent.VK_ENTER);
        assertSelection(editor, 8, 11);
        t.pushKey(KeyEvent.VK_ESCAPE);
        new EventTool().waitNoEvent(100);
        assertFalse("ToolBar not closed",barOperator.isVisible());
        SearchBarOperator.invoke(editor);
        assertTrue("Checkbox state is not persisten",jcbo.isSelected());
        assertEquals("Last searched text not persisten",t.getText(),"Abc");
        jcbo.setSelected(false);
        t.pushKey(KeyEvent.VK_ESCAPE);
        new EventTool().waitNoEvent(100);
        assertFalse("ToolBar not closed",barOperator.isVisible());
        editor.closeDiscard();
    }
    
    public void testNextButton() {
        openDefaultProject();
        openFile("Source Packages|org.netbeans.test.editor.search.IncrementalSearchTest", "match.txt");
        EditorOperator editor = new EditorOperator("match.txt");
        editor.setCaretPosition(1, 1);
        SearchBarOperator barOperator = SearchBarOperator.invoke(editor);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        t.clearText();
        new EventTool().waitNoEvent(100);
        t.typeText("abc");
        JButtonOperator b = barOperator.nextButton();
        b.push();
        assertSelection(editor, 0, 3);
        b.push();
        assertSelection(editor, 4, 7);
        t.pushKey(KeyEvent.VK_ESCAPE);
        new EventTool().waitNoEvent(100);
        assertFalse("ToolBar not closed",barOperator.isVisible());
        editor.closeDiscard();
    }
    
    public void testPrevButton() {
        openDefaultProject();
        openFile("Source Packages|org.netbeans.test.editor.search.IncrementalSearchTest", "match.txt");
        EditorOperator editor = new EditorOperator("match.txt");
        editor.setCaretPosition(3, 1);
        SearchBarOperator barOperator = SearchBarOperator.invoke(editor);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        t.clearText();
        new EventTool().waitNoEvent(100);
        t.typeText("abc");
        JButtonOperator b = barOperator.prevButton();
        b.push();
        assertSelection(editor, 4, 7);
        b.push();
        assertSelection(editor, 0, 3);
        t.pushKey(KeyEvent.VK_ESCAPE);
        new EventTool().waitNoEvent(100);
        assertFalse("ToolBar not closed",barOperator.isVisible());
        editor.closeDiscard();        
    }
    
    public void testCloseButton() {
        openDefaultProject();
        openFile("Source Packages|org.netbeans.test.editor.search.IncrementalSearchTest", "match.txt");
        EditorOperator editor = new EditorOperator("match.txt");
        editor.setCaretPosition(3, 1);
        SearchBarOperator barOperator = SearchBarOperator.invoke(editor);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        JButtonOperator b = barOperator.closeButton();
        b.push();
        new EventTool().waitNoEvent(250);        
        assertFalse("ToolBar not closed",barOperator.isVisible());
        editor.closeDiscard();
    }
    
    public void testNotFound() {
        openDefaultProject();
        openFile("Source Packages|org.netbeans.test.editor.search.IncrementalSearchTest", "match.txt");
        EditorOperator editor = new EditorOperator("match.txt");
        editor.setCaretPosition(3, 1);
        SearchBarOperator barOperator = SearchBarOperator.invoke(editor);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        t.clearText();
        new EventTool().waitNoEvent(100);
        t.requestFocus();
        t.typeText("XYZ");
        new EventTool().waitNoEvent(300);        
        JTextField filed = barOperator.findCombo().findJTextField();
        assertEquals(new Color(178, 0, 0), filed.getForeground());
        t.pushKey(KeyEvent.VK_ENTER);
        MainWindowOperator mwo =  MainWindowOperator.getDefault();
        assertEquals("'XYZ' not found",mwo.getStatusText());
        t.pushKey(KeyEvent.VK_ESCAPE);
        new EventTool().waitNoEvent(100);
        assertFalse("ToolBar not closed",barOperator.isVisible());
        editor.closeDiscard();
    }
    
    public void testInvalidRegexp() {
        openDefaultProject();
        openFile("Source Packages|org.netbeans.test.editor.search.IncrementalSearchTest", "match.txt");
        EditorOperator editor = new EditorOperator("match.txt");
        editor.setCaretPosition(3, 1);
        SearchBarOperator barOperator = SearchBarOperator.invoke(editor);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        JCheckBoxOperator ch2 = barOperator.reqularExpressionCheckBox();        
        ch2.setSelected(true);
        t.clearText();
        new EventTool().waitNoEvent(100);
        t.requestFocus();
        t.typeText("(");
        new EventTool().waitNoEvent(300);        
        JTextField filed = barOperator.findCombo().findJTextField();
        assertEquals(new Color(255, 0, 0), filed.getForeground());        
        MainWindowOperator mwo = MainWindowOperator.getDefault();
        assertEquals("Invalid regular expression: 'Unclosed group'",mwo.getStatusText());
        ch2.setSelected(false);
        t.pushKey(KeyEvent.VK_ESCAPE);
        new EventTool().waitNoEvent(100);
        assertFalse("ToolBar not closed",barOperator.isVisible());
        editor.closeDiscard();
    }
    
    public void testSearchForwardBackward() {
        openDefaultProject();
        openFile("Source Packages|org.netbeans.test.editor.search.IncrementalSearchTest", "match.txt");
        EditorOperator editor = new EditorOperator("match.txt");
        editor.setCaretPosition(2, 1);
        SearchBarOperator barOperator = SearchBarOperator.invoke(editor);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        t.clearText();
        new EventTool().waitNoEvent(100);
        t.typeText("abc");
        t.pushKey(KeyEvent.VK_ENTER);
        assertSelection(editor, 4, 7);
        t.pushKey(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK);
        assertSelection(editor, 0, 3);
        editor.txtEditorPane().requestFocus();
        new EventTool().waitNoEvent(100);
        assertTrue("ToolBar closed by focus transfer ",barOperator.isVisible());        
        editor.closeDiscard();
    }
    
    public void testWholeWords() {
        openDefaultProject();        
        openFile("Source Packages|org.netbeans.test.editor.search.IncrementalSearchTest", "match2.txt");
        EditorOperator editor = new EditorOperator("match2.txt");        
        editor.setCaretPosition(1, 1);
        SearchBarOperator barOperator = SearchBarOperator.invoke(editor);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        JCheckBoxOperator ch1 = barOperator.wholeWordsCheckBox();
        ch1.setSelected(true);
        t.clearText();        
        t.typeText("whole");
        t.pushKey(KeyEvent.VK_ENTER);        
        assertSelection(editor, 12, 17);                
        t.pushKey(KeyEvent.VK_ESCAPE);
        new EventTool().waitNoEvent(100);
        assertFalse("ToolBar not closed",barOperator.isVisible());
        closeFileWithDiscard();
    }

    public void testRegularExpression() {
        openDefaultProject();        
        openFile("Source Packages|org.netbeans.test.editor.search.IncrementalSearchTest", "match2.txt");
        EditorOperator editor = new EditorOperator("match2.txt");        
        editor.setCaretPosition(1, 1);
        SearchBarOperator barOperator =SearchBarOperator.invoke(editor);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        JCheckBoxOperator ch1 = barOperator.matchCaseCheckBox();
        JCheckBoxOperator ch2 = barOperator.reqularExpressionCheckBox();
        ch1.setSelected(false);        
        ch2.setSelected(true);
        t.clearText();        
        t.typeText("[aA]b*cd?");
        t.pushKey(KeyEvent.VK_ENTER);
        assertSelection(editor, 19, 23);        
        t.pushKey(KeyEvent.VK_ENTER);
        assertSelection(editor, 36, 39);        
        t.pushKey(KeyEvent.VK_ESCAPE);
        new EventTool().waitNoEvent(100);
        assertFalse("ToolBar not closed",barOperator.isVisible());
        closeFileWithDiscard();
    }

    public void testFindNext() {
        openDefaultProject();        
        openFile("Source Packages|org.netbeans.test.editor.search.IncrementalSearchTest", "match2.txt");
        EditorOperator editor = new EditorOperator("match2.txt");        
        editor.setCaretPosition(1, 1);
        SearchBarOperator barOperator = SearchBarOperator.invoke(editor);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        t.clearText();        
        barOperator.matchCaseCheckBox().setSelected(false);
        barOperator.reqularExpressionCheckBox().setSelected(false);
        barOperator.wholeWordsCheckBox().setSelected(false);        
        t.pushKey(KeyEvent.VK_ESCAPE);
        barOperator = SearchBarOperator.invoke(editor);        
        new EventTool().waitNoEvent(100);
        t.typeText("ab");
        new EventTool().waitNoEvent(1000);
        t.pushKey(KeyEvent.VK_ENTER);
        new EventTool().waitNoEvent(1000);
        assertSelection(editor, 19, 21);        
        t.pushKey(KeyEvent.VK_ESCAPE);
        new EventTool().waitNoEvent(100);
        assertFalse("ToolBar not closed",barOperator.isVisible());
        t.pushKey(KeyEvent.VK_F3);
        new EventTool().waitNoEvent(100);
        assertSelection(editor, 24, 26);                
        closeFileWithDiscard();
    }
    
    public void testFindPrev() {     
        openDefaultProject();        
        openFile("Source Packages|org.netbeans.test.editor.search.IncrementalSearchTest", "match2.txt");
        EditorOperator editor = new EditorOperator("match2.txt");        
        SearchBarOperator barOperator = SearchBarOperator.invoke(editor);
        JTextFieldOperator t = barOperator.findCombo().getTextField();
        barOperator.matchCaseCheckBox().setSelected(false);
        barOperator.reqularExpressionCheckBox().setSelected(false);
        barOperator.wholeWordsCheckBox().setSelected(false);        
        t.pushKey(KeyEvent.VK_ESCAPE);
        editor.setCaretPosition(6, 1);
        barOperator = SearchBarOperator.invoke(editor);
        t.clearText();        
        new EventTool().waitNoEvent(2000);
        t.typeText("ab");
        new EventTool().waitNoEvent(2000);
        t.pushKey(KeyEvent.VK_ENTER);
        new EventTool().waitNoEvent(100);
        assertSelection(editor, 36, 38); 
        t.pushKey(KeyEvent.VK_ESCAPE);
        new EventTool().waitNoEvent(100);
        assertFalse("ToolBar not closed",barOperator.isVisible());
        t.pushKey(KeyEvent.VK_F3, KeyEvent.SHIFT_DOWN_MASK);
        new EventTool().waitNoEvent(100);
        assertSelection(editor, 24, 26);                
        t.pushKey(KeyEvent.VK_F3, KeyEvent.SHIFT_DOWN_MASK);
        new EventTool().waitNoEvent(100);
        assertSelection(editor, 19, 21);                
        closeFileWithDiscard();           
    }


    
    public void assertSelection(EditorOperator editor,int start, int end) {
        JEditorPaneOperator txtEditorPane = editor.txtEditorPane();
        int actStart = txtEditorPane.getSelectionStart();
        int actEnd = txtEditorPane.getSelectionEnd();
//        System.out.println("-----------------------------------------------------");
//        System.out.println("Expected:"+start+" "+end+" Actual:"+actStart+" "+actEnd);
//        System.out.println("-----------------------------------------------------");        
        if(actStart!=start || actEnd!=end) fail("Wrong text selected in editor, actual selection <"+actStart+","+actEnd+">, expected <"+start+","+end+">");
    }
    
    public static void main(String[] args) {        
        TestRunner.run(IncrementalSearchTest.class);        
    }

    public static Test suite() {
      return NbModuleSuite.create(
              NbModuleSuite.createConfiguration(IncrementalSearchTest.class).enableModules(".*").clusters(".*"));
   }
    
}
