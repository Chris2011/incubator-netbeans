/*
 * Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License Version
 * 1.0 (the "License"). You may not use this file except in compliance with
 * the License. A copy of the License is available at http://www.sun.com/
 * 
 * The Original Code is the Jemmy library.
 * The Initial Developer of the Original Code is Alexandre Iline.
 * All Rights Reserved.
 * 
 * Contributor(s): Alexandre Iline.
 * 
 * $Id$ $Revision$ $Date$
 * 
 */

package org.netbeans.jemmy.operators;

import org.netbeans.jemmy.Action;
import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.ComponentSearcher;
import org.netbeans.jemmy.JemmyException;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.Outputable;
import org.netbeans.jemmy.TestOut;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.Timeouts;

import org.netbeans.jemmy.drivers.ListDriver;
import org.netbeans.jemmy.drivers.DriverManager;

import java.awt.Choice;
import java.awt.Component;
import java.awt.Container;

import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import java.util.Hashtable;

/**
 *
 * <BR><BR>Timeouts used: <BR>
 * ButtonOperator.PushButtonTimeout - time between choice pressing and releasing<BR>
 * ComponentOperator.WaitComponentTimeout - time to wait choice displayed <BR>
 * ComponentOperator.WaitComponentEnabledTimeout - time to wait choice enabled <BR>
 *
 * @see org.netbeans.jemmy.Timeouts
 *	
 * @author Alexandre Iline (alexandre.iline@sun.com)
 *	
 */

public class ChoiceOperator extends ComponentOperator implements Outputable{

    private TestOut output;
    private ListDriver driver;

    /**
     * Constructor.
     */
    public ChoiceOperator(Choice b) {
	super(b);
	driver = DriverManager.getListDriver(getClass());
    }

    /**
     * Constructor.
     * Waits component in container first.
     * Uses cont's timeout and output for waiting and to init operator.
     * @param text Choice text. 
     * @param index Ordinal component index.
     * @see ComponentOperator#isCaptionEqual(String, String, boolean, boolean)
     * @throws TimeoutExpiredException
     */
    public ChoiceOperator(ContainerOperator cont, String text, int index) {
	this((Choice)
	     waitComponent(cont, 
			   new ChoiceBySelectedItemFinder(text,
                                              cont.getComparator()),
			   index));
	copyEnvironment(cont);
    }
    
    /**
     * Constructor.
     * Waits component in container first.
     * Uses cont's timeout and output for waiting and to init operator.
     * @param text Choice text. 
     * @see ComponentOperator#isCaptionEqual(String, String, boolean, boolean)
     * @throws TimeoutExpiredException
     */
    public ChoiceOperator(ContainerOperator cont, String text) {
	this(cont, text, 0);
    }

    /**
     * Constructor.
     * Waits component in container first.
     * Uses cont's timeout and output for waiting and to init operator.
     * @param index Ordinal component index.
     * @throws TimeoutExpiredException
     */
    public ChoiceOperator(ContainerOperator cont, int index) {
	this((Choice)
	     waitComponent(cont, 
			   new ChoiceFinder(ComponentSearcher.
					       getTrueChooser("Any Choice")),
			   index));
	copyEnvironment(cont);
    }
    
    /**
     * Constructor.
     * Waits component in container first.
     * Uses cont's timeout and output for waiting and to init operator.
     * @throws TimeoutExpiredException
     */
    public ChoiceOperator(ContainerOperator cont) {
	this(cont, 0);
    }

    /**
     * Searches Choice in container.
     * @param cont Container to search component in.
     * @param chooser org.netbeans.jemmy.ComponentChooser implementation.
     * @param index Ordinal component index.
     * @return Choice instance or null if component was not found.
     */
    public static Choice findChoice(Container cont, ComponentChooser chooser, int index) {
	return((Choice)findComponent(cont, new ChoiceFinder(chooser), index));
    }

    /**
     * Searches 0'th Choice in container.
     * @param cont Container to search component in.
     * @param chooser org.netbeans.jemmy.ComponentChooser implementation.
     * @return Choice instance or null if component was not found.
     */
    public static Choice findChoice(Container cont, ComponentChooser chooser) {
	return(findChoice(cont, chooser, 0));
    }

    /**
     * Searches Choice by text.
     * @param cont Container to search component in.
     * @param text Choice text. If null, contents is not checked.
     * @param ce Compare text exactly.
     * @param ccs Compare text case sensitively.
     * @param index Ordinal component index.
     * @return Choice instance or null if component was not found.
     * @see ComponentOperator#isCaptionEqual(String, String, boolean, boolean)
     */
    public static Choice findChoice(Container cont, String text, boolean ce, boolean ccs, int index) {
	return(findChoice(cont, 
			     new ChoiceFinder(new ChoiceOperator.
					ChoiceBySelectedItemFinder(text, 
					new DefaultStringComparator(ce, ccs))), 
			     index));
    }

    /**
     * Searches Choice by text.
     * @param cont Container to search component in.
     * @param text Choice text. If null, contents is not checked.
     * @param ce Compare text exactly.
     * @param ccs Compare text case sensitively.
     * @return Choice instance or null if component was not found.
     * @see ComponentOperator#isCaptionEqual(String, String, boolean, boolean)
     */
    public static Choice findChoice(Container cont, String text, boolean ce, boolean ccs) {
	return(findChoice(cont, text, ce, ccs, 0));
    }

    /**
     * Waits Choice in container.
     * @param cont Container to search component in.
     * @param chooser org.netbeans.jemmy.ComponentChooser implementation.
     * @param index Ordinal component index.
     * @return Choice instance.
     * @throws TimeoutExpiredException
     */
    public static Choice waitChoice(Container cont, ComponentChooser chooser, int index) {
	return((Choice)waitComponent(cont, new ChoiceFinder(chooser), index));
    }

    /**
     * Waits 0'th Choice in container.
     * @param cont Container to search component in.
     * @param chooser org.netbeans.jemmy.ComponentChooser implementation.
     * @return Choice instance.
     * @throws TimeoutExpiredException
     */
    public static Choice waitChoice(Container cont, ComponentChooser chooser) {
	return(waitChoice(cont, chooser, 0));
    }

    /**
     * Waits Choice by text.
     * @param cont Container to search component in.
     * @param text Choice text. If null, contents is not checked.
     * @param ce Compare text exactly.
     * @param ccs Compare text case sensitively.
     * @param index Ordinal component index.
     * @return Choice instance.
     * @see ComponentOperator#isCaptionEqual(String, String, boolean, boolean)
     * @throws TimeoutExpiredException
     */
    public static Choice waitChoice(Container cont, String text, boolean ce, boolean ccs, int index) {
	return(waitChoice(cont,  
			     new ChoiceFinder(new ChoiceOperator.
						 ChoiceBySelectedItemFinder(text, 
	                                         new DefaultStringComparator(ce, ccs))), 
			     index));
    }

    /**
     * Waits Choice by text.
     * @param cont Container to search component in.
     * @param text Choice text. If null, contents is not checked.
     * @param ce Compare text exactly.
     * @param ccs Compare text case sensitively.
     * @return Choice instance.
     * @see ComponentOperator#isCaptionEqual(String, String, boolean, boolean)
     * @throws TimeoutExpiredException
     */
    public static Choice waitChoice(Container cont, String text, boolean ce, boolean ccs) {
	return(waitChoice(cont, text, ce, ccs, 0));
    }

    /**
     * Defines print output streams or writers.
     * @param out Identify the streams or writers used for print output.
     * @see org.netbeans.jemmy.Outputable
     * @see org.netbeans.jemmy.TestOut
     */
    public void setOutput(TestOut out) {
	output = out;
	super.setOutput(output.createErrorOutput());
    }

    /**
     * Returns print output streams or writers.
     * @return an object that contains references to objects for
     * printing to output and err streams.
     * @see org.netbeans.jemmy.Outputable
     * @see org.netbeans.jemmy.TestOut
     */
    public TestOut getOutput() {
	return(output);
    }

    public void copyEnvironment(Operator anotherOperator) {
	super.copyEnvironment(anotherOperator);
	driver = 
	    (ListDriver)DriverManager.
	    getDriver(DriverManager.LIST_DRIVER_ID,
		      getClass(), 
		      anotherOperator.getProperties());
    }

    private int findItemIndex(String item, StringComparator comparator, int index){
	int count = 0;
	for(int i = 0; i < getItemCount(); i++) {
	    if(comparator.equals(getItem(i), item)) {
		if(count == index) {
		    return(i);
		} else {
		    count++;
		}
	    }
	}
	return(-1);
    }

    public int findItemIndex(String item, int index){
	return(findItemIndex(item, getComparator(), index));
    }
    
    public int findItemIndex(String item){
	return(findItemIndex(item, 0));
    }

    private void selectItem(String item, StringComparator comparator, int index) {
	selectItem(findItemIndex(item, comparator, index));
    }

    public void selectItem(String item, int index) {
	selectItem(item, getComparator(), index);
    }

    public void selectItem(String item) {
	selectItem(item, 0);
    }

    public void selectItem(int index) {
	output.printLine("Select " + Integer.toString(index) + "`th item in combobox\n    : " +
			 getSource().toString());
	output.printGolden("Select " + Integer.toString(index) + "`th item in combobox");
	makeComponentVisible();
	try {
	    waitComponentEnabled();
	} catch(InterruptedException e) {
	    throw(new JemmyException("Interrupted!", e));
	}
	driver.selectItem(this, index);
    }

    /**
     * Returns information about component.
     */
    public Hashtable getDump() {
	Hashtable result = super.getDump();
	result.put("SelectedItem", ((Choice)getSource()).getSelectedItem());
        String[] items = new String[((Choice)getSource()).getItemCount()];
        for (int i=0; i<((Choice)getSource()).getItemCount(); i++) {
            items[i] = ((Choice)getSource()).getItem(i);
        }
        addToDump(result,"Item", items);
	return(result);
    }

    ////////////////////////////////////////////////////////
    //Mapping                                             //

    /**Maps <code>Choice.add(String)</code> through queue*/
    public void add(final String item) {
	runMapping(new MapVoidAction("add") {
		public void map() {
		    ((Choice)getSource()).add(item);
		}});}

    /**Maps <code>Choice.addItemListener(ItemListener)</code> through queue*/
    public void addItemListener(final ItemListener itemListener) {
	runMapping(new MapVoidAction("addItemListener") {
		public void map() {
		    ((Choice)getSource()).addItemListener(itemListener);
		}});}

    /**Maps <code>Choice.addNotify()</code> through queue*/
    public void addNotify() {
	    runMapping(new MapVoidAction("addNotify") {
		public void map() {
		    ((Choice)getSource()).addNotify();
		}});}

    /**Maps <code>Choice.getItem(int)</code> through queue*/
    public String getItem(final int index) {
	return((String)runMapping(new MapAction("getItem") {
		public Object map() {
		    return(((Choice)getSource()).getItem(index));
		}}));}

    /**Maps <code>Choice.getItemCount()</code> through queue*/
    public int getItemCount() {
	return(runMapping(new MapIntegerAction("getItemCount") {
		public int map() {
		    return(((Choice)getSource()).getItemCount());
		}}));}

    /**Maps <code>Choice.getSelectedIndex()</code> through queue*/
    public int getSelectedIndex() {
	return(runMapping(new MapIntegerAction("getSelectedIndex") {
		public int map() {
		    return(((Choice)getSource()).getSelectedIndex());
		}}));}

    /**Maps <code>Choice.getSelectedItem()</code> through queue*/
    public String getSelectedItem() {
	return((String)runMapping(new MapAction("getSelectedItem") {
		public Object map() {
		    return(((Choice)getSource()).getSelectedItem());
		}}));}

    /**Maps <code>Choice.insert(String)</code> through queue*/
    public void insert(final String item,final int index) {
	    runMapping(new MapVoidAction("insert") {
		public void map() {
		    ((Choice)getSource()).insert(item,index);
		}});}

    /**Maps <code>Choice.remove(int)</code> through queue*/
    public void remove(final int position) {
	    runMapping(new MapVoidAction("remove") {
		public void map() {
		    ((Choice)getSource()).remove(position);
		}});}

    /**Maps <code>Choice.remove(String)</code> through queue*/
    public void remove(final String item) {
	    runMapping(new MapVoidAction("remove") {
		public void map() {
		    ((Choice)getSource()).remove(item);
		}});}

    /**Maps <code>Choice.removeAll()</code> through queue*/
    public void removeAll() {
	    runMapping(new MapVoidAction("removeAll") {
		public void map() {
		    ((Choice)getSource()).removeAll();
		}});}

    /**Maps <code>Choice.removeItemListener(ItemListener)</code> through queue*/
    public void removeItemListener(final ItemListener itemListener) {
	runMapping(new MapVoidAction("removeItemListener") {
		public void map() {
		    ((Choice)getSource()).removeItemListener(itemListener);
		}});}

    /**Maps <code>Choice.select(int)</code> through queue*/
    public void select(final int pos) {
	runMapping(new MapVoidAction("select") {
		public void map() {
		    ((Choice)getSource()).select(pos);
		}});}

    /**Maps <code>Choice.select(String)</code> through queue*/
    public void setState(final String str) {
	runMapping(new MapVoidAction("select") {
		public void map() {
		    ((Choice)getSource()).select(str);
		}});}

    //End of mapping                                      //
    ////////////////////////////////////////////////////////

    protected static class ChoiceBySelectedItemFinder implements ComponentChooser {
	String label;
	StringComparator comparator;
	public ChoiceBySelectedItemFinder(String lb, StringComparator comparator) {
	    label = lb;
	    this.comparator = comparator;
	}
	public boolean checkComponent(Component comp) {
	    if(comp instanceof Choice) {
		if(((Choice)comp).getSelectedItem() != null) {
		    return(comparator.equals(((Choice)comp).getSelectedItem(),
					     label));
		}
	    }
	    return(false);
	}
	public String getDescription() {
	    return("Choice with label \"" + label + "\"");
	}
    }

    private static class ChoiceFinder implements ComponentChooser {
	ComponentChooser subFinder;
	public ChoiceFinder(ComponentChooser sf) {
	    subFinder = sf;
	}
	public boolean checkComponent(Component comp) {
	    if(comp instanceof Choice) {
		return(subFinder.checkComponent(comp));
	    }
	    return(false);
	}
	public String getDescription() {
	    return(subFinder.getDescription());
	}
    }
}
