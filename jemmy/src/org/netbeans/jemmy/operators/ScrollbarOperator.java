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
import org.netbeans.jemmy.ComponentSearcher;
import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.Outputable;
import org.netbeans.jemmy.TestOut;
import org.netbeans.jemmy.Timeoutable;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.Timeouts;
import org.netbeans.jemmy.Waitable;
import org.netbeans.jemmy.Waiter;

import org.netbeans.jemmy.drivers.DriverManager;
import org.netbeans.jemmy.drivers.ScrollDriver;

import org.netbeans.jemmy.drivers.scrolling.ScrollAdjuster;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Scrollbar;

import java.awt.event.AdjustmentListener;

import java.util.Hashtable;

public class ScrollbarOperator extends ComponentOperator
    implements Timeoutable, Outputable{

    private final static long ONE_SCROLL_CLICK_TIMEOUT = 0;
    private final static long WHOLE_SCROLL_TIMEOUT = 60000;
    private final static long BEFORE_DROP_TIMEOUT = 0;
    private final static long DRAG_AND_DROP_SCROLLING_DELTA = 0;

    private static final int MINIMAL_PAD_SIZE = 10;

    private static final int MINIMAL_DRAGGER_SIZE = 5;

    private Timeouts timeouts;
    private TestOut output;

    private ScrollDriver driver;

    public ScrollbarOperator(Scrollbar b) {
	super(b);
	driver = DriverManager.getScrollDriver(getClass());
    }
    public ScrollbarOperator(ContainerOperator cont, int index) {
	this((Scrollbar)waitComponent(cont, 
				       new ScrollbarFinder(ComponentSearcher.getTrueChooser("Any container")), 
				       index));
	copyEnvironment(cont);
    }
    public ScrollbarOperator(ContainerOperator cont) {
	this(cont, 0);
    }
    public static Scrollbar findScrollbar(Container cont, ComponentChooser chooser, int index) {
	return((Scrollbar)findComponent(cont, new ScrollbarFinder(chooser), index));
    }
    public static Scrollbar findScrollbar(Container cont, ComponentChooser chooser) {
	return(findScrollbar(cont, chooser, 0));
    }
    public static Scrollbar findScrollbar(Container cont, int index) {
	return(findScrollbar(cont, ComponentSearcher.getTrueChooser(Integer.toString(index) + "'th Scrollbar instance"), index));
    }
    public static Scrollbar findScrollbar(Container cont) {
	return(findScrollbar(cont, 0));
    }
    public static Scrollbar waitScrollbar(Container cont, ComponentChooser chooser, int index)  {
	return((Scrollbar)waitComponent(cont, new ScrollbarFinder(chooser), index));
    }
    public static Scrollbar waitScrollbar(Container cont, ComponentChooser chooser) {
	return(waitScrollbar(cont, chooser, 0));
    }
    public static Scrollbar waitScrollbar(Container cont, int index)  {
	return(waitScrollbar(cont, ComponentSearcher.getTrueChooser(Integer.toString(index) + "'th Scrollbar instance"), index));
    }
    public static Scrollbar waitScrollbar(Container cont) {
	return(waitScrollbar(cont, 0));
    }

    static {
	Timeouts.initDefault("ScrollbarOperator.OneScrollClickTimeout", ONE_SCROLL_CLICK_TIMEOUT);
	Timeouts.initDefault("ScrollbarOperator.WholeScrollTimeout", WHOLE_SCROLL_TIMEOUT);
	Timeouts.initDefault("ScrollbarOperator.BeforeDropTimeout", BEFORE_DROP_TIMEOUT);
	Timeouts.initDefault("ScrollbarOperator.DragAndDropScrollingDelta", DRAG_AND_DROP_SCROLLING_DELTA);
    }
    public void setOutput(TestOut out) {
	output = out;
	super.setOutput(output.createErrorOutput());
    }
    public TestOut getOutput() {
	return(output);
    }
    public void setTimeouts(Timeouts timeouts) {
	this.timeouts = timeouts;
	super.setTimeouts(timeouts);
    }
    public Timeouts getTimeouts() {
	return(timeouts);
    }

    public void copyEnvironment(Operator anotherOperator) {
	super.copyEnvironment(anotherOperator);
	driver = 
	    (ScrollDriver)DriverManager.
	    getDriver(DriverManager.SCROLL_DRIVER_ID,
		      getClass(), 
		      anotherOperator.getProperties());
    }

    public void scrollTo(Waitable w, Object waiterParam, boolean increase) {
	scrollTo(new WaitableChecker(w, waiterParam, increase, this));
    }
    public void scrollTo(final ScrollAdjuster adj) {
	produceTimeRestricted(new Action() {
		public Object launch(Object obj) {
		    driver.scroll(ScrollbarOperator.this, adj);
		    return(null);
		}
		public String getDescription() {
		    return("Scrolling");
		}
	    }, getTimeouts().getTimeout("ScrollbarOperator.WholeScrollTimeout"));
    }
    public void scrollToValue(int value) {
	output.printTrace("Scroll Scrollbar to " + Integer.toString(value) +
			  " value\n" + getSource().toString());
	output.printGolden("Scroll Scrollbar to " + Integer.toString(value) + " value");
	scrollTo(new ValueScrollAdjuster(value));
    }
    public void scrollToValue(double proportionalValue) {
	output.printTrace("Scroll Scrollbar to " + Double.toString(proportionalValue) +
			  " proportional value\n" + getSource().toString());
	output.printGolden("Scroll Scrollbar to " + Double.toString(proportionalValue) + " proportional value");
	scrollTo(new ValueScrollAdjuster((int)(getMinimum() + 
					       (getMaximum() - 
						getVisibleAmount() - 
						getMinimum()) * proportionalValue)));
    }
    public void scrollToMinimum() {
	output.printTrace("Scroll Scrollbar to minimum value\n" +
			  getSource().toString());
	output.printGolden("Scroll Scrollbar to minimum value");
	produceTimeRestricted(new Action() {
		public Object launch(Object obj) {
		    driver.scrollToMinimum(ScrollbarOperator.this, getOrientation());
		    return(null);
		}
		public String getDescription() {
		    return("Scrolling");
		}
	    }, getTimeouts().getTimeout("ScrollbarOperator.WholeScrollTimeout"));
    }
    public void scrollToMaximum() {
	output.printTrace("Scroll Scrollbar to maximum value\n" +
			  getSource().toString());
	output.printGolden("Scroll Scrollbar to maximum value");
	produceTimeRestricted(new Action() {
		public Object launch(Object obj) {
		    driver.scrollToMaximum(ScrollbarOperator.this, getOrientation());
		    return(null);
		}
		public String getDescription() {
		    return("Scrolling");
		}
	    }, getTimeouts().getTimeout("ScrollbarOperator.WholeScrollTimeout"));
    }
    ////////////////////////////////////////////////////////
    //Mapping                                             //

    /**Maps <code>Scrollbar.addAdjustmentListener(AdjustmentListener)</code> through queue*/
    public void addAdjustmentListener(final AdjustmentListener adjustmentListener) {
	runMapping(new MapVoidAction("addAdjustmentListener") {
		public void map() {
		    ((Scrollbar)getSource()).addAdjustmentListener(adjustmentListener);
		}});}

    /**Maps <code>Scrollbar.getBlockIncrement()</code> through queue*/
    public int getBlockIncrement() {
	return(runMapping(new MapIntegerAction("getBlockIncrement") {
		public int map() {
		    return(((Scrollbar)getSource()).getBlockIncrement());
		}}));}

    /**Maps <code>Scrollbar.getMaximum()</code> through queue*/
    public int getMaximum() {
	return(runMapping(new MapIntegerAction("getMaximum") {
		public int map() {
		    return(((Scrollbar)getSource()).getMaximum());
		}}));}

    /**Maps <code>Scrollbar.getMinimum()</code> through queue*/
    public int getMinimum() {
	return(runMapping(new MapIntegerAction("getMinimum") {
		public int map() {
		    return(((Scrollbar)getSource()).getMinimum());
		}}));}

    /**Maps <code>Scrollbar.getOrientation()</code> through queue*/
    public int getOrientation() {
	return(runMapping(new MapIntegerAction("getOrientation") {
		public int map() {
		    return(((Scrollbar)getSource()).getOrientation());
		}}));}

    /**Maps <code>Scrollbar.getUnitIncrement()</code> through queue*/
    public int getUnitIncrement() {
	return(runMapping(new MapIntegerAction("getUnitIncrement") {
		public int map() {
		    return(((Scrollbar)getSource()).getUnitIncrement());
		}}));}

    /**Maps <code>Scrollbar.getValue()</code> through queue*/
    public int getValue() {
	return(runMapping(new MapIntegerAction("getValue") {
		public int map() {
		    return(((Scrollbar)getSource()).getValue());
		}}));}

    /**Maps <code>Scrollbar.getVisibleAmount()</code> through queue*/
    public int getVisibleAmount() {
	return(runMapping(new MapIntegerAction("getVisibleAmount") {
		public int map() {
		    return(((Scrollbar)getSource()).getVisibleAmount());
		}}));}

    /**Maps <code>Scrollbar.removeAdjustmentListener(AdjustmentListener)</code> through queue*/
    public void removeAdjustmentListener(final AdjustmentListener adjustmentListener) {
	runMapping(new MapVoidAction("removeAdjustmentListener") {
		public void map() {
		    ((Scrollbar)getSource()).removeAdjustmentListener(adjustmentListener);
		}});}

    /**Maps <code>Scrollbar.setBlockIncrement(int)</code> through queue*/
    public void setBlockIncrement(final int i) {
	runMapping(new MapVoidAction("setBlockIncrement") {
		public void map() {
		    ((Scrollbar)getSource()).setBlockIncrement(i);
		}});}

    /**Maps <code>Scrollbar.setMaximum(int)</code> through queue*/
    public void setMaximum(final int i) {
	runMapping(new MapVoidAction("setMaximum") {
		public void map() {
		    ((Scrollbar)getSource()).setMaximum(i);
		}});}

    /**Maps <code>Scrollbar.setMinimum(int)</code> through queue*/
    public void setMinimum(final int i) {
	runMapping(new MapVoidAction("setMinimum") {
		public void map() {
		    ((Scrollbar)getSource()).setMinimum(i);
		}});}

    /**Maps <code>Scrollbar.setOrientation(int)</code> through queue*/
    public void setOrientation(final int i) {
	runMapping(new MapVoidAction("setOrientation") {
		public void map() {
		    ((Scrollbar)getSource()).setOrientation(i);
		}});}

    /**Maps <code>Scrollbar.setUnitIncrement(int)</code> through queue*/
    public void setUnitIncrement(final int i) {
	runMapping(new MapVoidAction("setUnitIncrement") {
		public void map() {
		    ((Scrollbar)getSource()).setUnitIncrement(i);
		}});}

    /**Maps <code>Scrollbar.setValue(int)</code> through queue*/
    public void setValue(final int i) {
	runMapping(new MapVoidAction("setValue") {
		public void map() {
		    ((Scrollbar)getSource()).setValue(i);
		}});}

    /**Maps <code>Scrollbar.setValues(int, int, int, int)</code> through queue*/
    public void setValues(final int i, final int i1, final int i2, final int i3) {
	runMapping(new MapVoidAction("setValues") {
		public void map() {
		    ((Scrollbar)getSource()).setValues(i, i1, i2, i3);
		}});}

    /**Maps <code>Scrollbar.setVisibleAmount(int)</code> through queue*/
    public void setVisibleAmount(final int i) {
	runMapping(new MapVoidAction("setVisibleAmount") {
		public void map() {
		    ((Scrollbar)getSource()).setVisibleAmount(i);
		}});}

    //End of mapping                                      //
    ////////////////////////////////////////////////////////

    private class ValueScrollAdjuster implements ScrollAdjuster {
	int value;
	public ValueScrollAdjuster(int value) {
	    this.value = value;
	}
	public int getScrollDirection() {
	    if(getValue() == value) {
		return(ScrollAdjuster.DO_NOT_TOUCH_SCROLL_DIRECTION);
	    } else {
		return((getValue() < value) ?
		       ScrollAdjuster.INCREASE_SCROLL_DIRECTION :
		       ScrollAdjuster.DECREASE_SCROLL_DIRECTION);
	    }
	}
	public int getScrollOrientation() {
	    return(getOrientation());
	}
	public String getDescription() {
	    return("Scroll to " + Integer.toString(value) + " value");
	}
    }
    private class WaitableChecker implements ScrollAdjuster {
	Waitable w;
	Object waitParam;
	boolean increase;
	boolean reached = false;
	ScrollbarOperator oper;
	public WaitableChecker(Waitable w, Object waitParam, boolean increase, ScrollbarOperator oper) {
	    this.w = w;
	    this.waitParam = waitParam;
	    this.increase = increase;
	    this.oper = oper;
	}
	public int getScrollDirection() {
	    if(!reached && w.actionProduced(waitParam) != null) {
		reached = true;
	    }
	    if(reached) {
		return(this.DO_NOT_TOUCH_SCROLL_DIRECTION);
	    } else {
		return(increase ? 
		       this.INCREASE_SCROLL_DIRECTION :
		       this.DECREASE_SCROLL_DIRECTION);
	    }
	}
	public int getScrollOrientation() {
	    return(getOrientation());
	}
	public String getDescription() {
	    return(w.getDescription());
	}
    }
    private static class ScrollbarFinder implements ComponentChooser {
	ComponentChooser subFinder;
	public ScrollbarFinder(ComponentChooser sf) {
	    subFinder = sf;
	}
	public boolean checkComponent(Component comp) {
	    if(comp instanceof Scrollbar) {
		return(subFinder.checkComponent(comp));
	    }
	    return(false);
	}
	public String getDescription() {
	    return(subFinder.getDescription());
	}
    }
}
