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
import org.netbeans.jemmy.JemmyException;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.Outputable;
import org.netbeans.jemmy.TestOut;
import org.netbeans.jemmy.Timeoutable;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.Timeouts;

import org.netbeans.jemmy.drivers.DriverManager;
import org.netbeans.jemmy.drivers.ScrollDriver;

import org.netbeans.jemmy.drivers.scrolling.ScrollAdjuster;

import java.awt.Adjustable;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.ScrollPane;

import javax.swing.SwingUtilities;

/**
 * <BR><BR>Timeouts used: <BR>
 * ScrollbarOperator.WholeScrollTimeout - time for one scroll click <BR>
 * ComponentOperator.WaitComponentTimeout - time to wait component displayed <BR>
 *
 * @see org.netbeans.jemmy.Timeouts
 *
 * @author Alexandre Iline (alexandre.iline@sun.com)
 *	
 */

public class ScrollPaneOperator extends ContainerOperator
    implements Timeoutable, Outputable {

    private static int X_POINT_RECT_SIZE = 6;
    private static int Y_POINT_RECT_SIZE = 4;
    
    private Timeouts timeouts;
    private TestOut output;
    
    private ScrollDriver driver;

    public ScrollPaneOperator(ScrollPane b) {
	super(b);
	driver = DriverManager.getScrollDriver(getClass());
    }

    /**
     * Constructor.
     * Waits component in container first.
     * Uses cont's timeout and output for waiting and to init operator.
     * @param cont Operator pointing a container to search component in.
     * @param index Ordinal component index.
     * @throws TimeoutExpiredException
     */
    public ScrollPaneOperator(ContainerOperator cont, int index) {
	this((ScrollPane)waitComponent(cont, 
					new ScrollPaneFinder(ComponentSearcher.getTrueChooser("Any container")), 
					index));
	copyEnvironment(cont);
   }

    /**
     * Constructor.
     * Waits component in container first.
     * Uses cont's timeout and output for waiting and to init operator.
     * @param cont Operator pointing a container to search component in.
     * @throws TimeoutExpiredException
     */
    public ScrollPaneOperator(ContainerOperator cont) {
	this(cont, 0);
    }

    /**
     * Searches ScrollPane in container.
     * @param cont Container to search component in.
     * @param chooser org.netbeans.jemmy.ComponentChooser implementation.
     * @param index Ordinal component index.
     * @return ScrollPane instance or null if component was not found.
     */
    public static ScrollPane findScrollPane(Container cont, ComponentChooser chooser, int index) {
	return((ScrollPane)findComponent(cont, new ScrollPaneFinder(chooser), index));
    }
    
    /**
     * Searches 0'th ScrollPane in container.
     * @param cont Container to search component in.
     * @param chooser org.netbeans.jemmy.ComponentChooser implementation.
     * @return ScrollPane instance or null if component was not found.
     */
    public static ScrollPane findScrollPane(Container cont, ComponentChooser chooser) {
	return(findScrollPane(cont, chooser, 0));
    }
    
    /**
     * Searches ScrollPane in container.
     * @param cont Container to search component in.
     * @param index Ordinal component index.
     * @return ScrollPane instance or null if component was not found.
     */
    public static ScrollPane findScrollPane(Container cont, int index) {
	return(findScrollPane(cont, ComponentSearcher.getTrueChooser(Integer.toString(index) + "'th ScrollPane instance"), index));
    }
    
    /**
     * Searches 0'th ScrollPane in container.
     * @param cont Container to search component in.
     * @return ScrollPane instance or null if component was not found.
     */
    public static ScrollPane findScrollPane(Container cont) {
	return(findScrollPane(cont, 0));
    }

    /**
     * Searches ScrollPane object which component lies on.
     * @param comp Component to find ScrollPane under.
     * @param chooser org.netbeans.jemmy.ComponentChooser implementation.
     * @return ScrollPane instance or null if component was not found.
     */
    public static ScrollPane findScrollPaneUnder(Component comp, ComponentChooser chooser) {
	return((ScrollPane)findContainerUnder(comp, new ScrollPaneFinder(chooser)));
    }
    
    /**
     * Searches ScrollPane object which component lies on.
     * @param comp Component to find ScrollPane under.
     * @return ScrollPane instance or null if component was not found.
     */
    public static ScrollPane findScrollPaneUnder(Component comp) {
	return(findScrollPaneUnder(comp, new ScrollPaneFinder(ComponentSearcher.
								getTrueChooser("ScrollPane component"))));
    }
    
    /**
     * Waits ScrollPane in container.
     * @param cont Container to search component in.
     * @param chooser org.netbeans.jemmy.ComponentChooser implementation.
     * @param index Ordinal component index.
     * @return ScrollPane instance or null if component was not displayed.
     * @throws TimeoutExpiredException
     */
    public static ScrollPane waitScrollPane(Container cont, ComponentChooser chooser, int index) {
	return((ScrollPane)waitComponent(cont, new ScrollPaneFinder(chooser), index));
    }
    
    /**
     * Waits 0'th ScrollPane in container.
     * @param cont Container to search component in.
     * @param chooser org.netbeans.jemmy.ComponentChooser implementation.
     * @return ScrollPane instance or null if component was not displayed.
     * @throws TimeoutExpiredException
     */
    public static ScrollPane waitScrollPane(Container cont, ComponentChooser chooser) {
	return(waitScrollPane(cont, chooser, 0));
    }
    
    /**
     * Waits ScrollPane in container.
     * @param cont Container to search component in.
     * @param index Ordinal component index.
     * @return ScrollPane instance or null if component was not displayed.
     * @throws TimeoutExpiredException
     */
    public static ScrollPane waitScrollPane(Container cont, int index) {
	return(waitScrollPane(cont, ComponentSearcher.getTrueChooser(Integer.toString(index) + "'th ScrollPane instance"), index));
    }
    
    /**
     * Waits 0'th ScrollPane in container.
     * @param cont Container to search component in.
     * @return ScrollPane instance or null if component was not displayed.
     * @throws TimeoutExpiredException
     */
    public static ScrollPane waitScrollPane(Container cont) {
	return(waitScrollPane(cont, 0));
    }

    static {
	try {
	    Class.forName("org.netbeans.jemmy.operators.ScrollbarOperator");
	} catch(Exception e) {
	    throw(new JemmyException("Exception", e));
	}
    }
    
    /**
     * Sets operator's timeouts.
     * @param timeouts org.netbeans.jemmy.Timeouts instance.
     */
    public void setTimeouts(Timeouts timeouts) {
	super.setTimeouts(timeouts);
	this.timeouts = timeouts;
    }

    /**
     * Return current timeouts.
     * @return the collection of current timeout assignments.
     * @see org.netbeans.jemmy.Timeoutable
     * @see org.netbeans.jemmy.Timeouts
     */
    public Timeouts getTimeouts() {
	return(timeouts);
    }

    /**
     * Sets operator's output.
     * @param out org.netbeans.jemmy.TestOut instance.
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
	    (ScrollDriver)DriverManager.
	    getDriver(DriverManager.SCROLL_DRIVER_ID,
		      getClass(), 
		      anotherOperator.getProperties());
    }

    public void setValues(int x, int y) {
	getHAdjustable().setValue(x);
	getVAdjustable().setValue(y);
    }

    public void scrollTo(final ScrollAdjuster adj) {
	produceTimeRestricted(new Action() {
		public Object launch(Object obj) {
		    driver.scroll(ScrollPaneOperator.this, adj);
		    return(null);
		}
		public String getDescription() {
		    return("Scrolling");
		}
	    }, getTimeouts().getTimeout("ScrollbarOperator.WholeScrollTimeout"));
    }


    /**
     * Scrolls horizontal scroll bar.
     * @param value Value to scroll horizontal scroll bar to.
     * @throws TimeoutExpiredException
     */
    public void scrollToHorizontalValue(final int value) {
	output.printTrace("Scroll ScrollPane to " + Integer.toString(value) + " horizontal value \n" +
			  getSource().toString());
	output.printGolden("Scroll ScrollPane to " + Integer.toString(value) + " horizontal value");
	scrollTo(new ValueScrollAdjuster(value, 
					 Adjustable.HORIZONTAL, 
					 getHAdjustable()));
    }

    /**
     * Scrolls horizontal scroll bar.
     * @param value Proportional value to scroll horizontal scroll bar to.
     * @throws TimeoutExpiredException
     */
    public void scrollToHorizontalValue(double proportionalValue) {
	output.printTrace("Scroll ScrollPane to " + Double.toString(proportionalValue) + " proportional horizontal value \n" +
			  getSource().toString());
	output.printGolden("Scroll ScrollPane to " + Double.toString(proportionalValue) + " proportional horizontal value");
	Adjustable adj = getHAdjustable();
	scrollTo(new ValueScrollAdjuster((int)(adj.getMinimum() + 
					       (adj.getMaximum() - 
						adj.getVisibleAmount() - 
						adj.getMinimum()) * proportionalValue), 
					 Adjustable.VERTICAL, 
					 getVAdjustable()));
    }

    /**
     * Scrolls vertical scroll bar.
     * @param value Value to scroll vertical scroll bar to.
     * @throws TimeoutExpiredException
     */
    public void scrollToVerticalValue(final int value) {
	output.printTrace("Scroll ScrollPane to " + Integer.toString(value) + " vertical value \n" +
			  getSource().toString());
	output.printGolden("Scroll ScrollPane to " + Integer.toString(value) + " vertical value");
	scrollTo(new ValueScrollAdjuster(value, 
					 Adjustable.VERTICAL, 
					 getVAdjustable()));
    }

    /**
     * Scrolls vertical scroll bar.
     * @param value Value to scroll vertical scroll bar to.
     * @throws TimeoutExpiredException
     */
    public void scrollToVerticalValue(double proportionalValue) {
	output.printTrace("Scroll ScrollPane to " + Double.toString(proportionalValue) + " proportional vertical value \n" +
			  getSource().toString());
	output.printGolden("Scroll ScrollPane to " + Double.toString(proportionalValue) + " proportional vertical value");
	Adjustable adj = getVAdjustable();
	scrollTo(new ValueScrollAdjuster((int)(adj.getMinimum() + 
							  (adj.getMaximum() - 
							   adj.getVisibleAmount() - 
							   adj.getMinimum()) * proportionalValue), 
					 Adjustable.VERTICAL, 
					 getVAdjustable()));
    }

    /**
     * Scrolls both scroll bars.
     * @param valueX Value to scroll horizontal scroll bar to.
     * @param valueY Value to scroll vertical scroll bar to.
     * @throws TimeoutExpiredException
     */
    public void scrollToValues(int valueX, int valueY) {
	scrollToVerticalValue(valueX);
	scrollToHorizontalValue(valueX);
    }

    /**
     * Scrolls both scroll bars.
     * @param proportionalValueX Value to scroll horizontal scroll bar to.
     * @param proportionalValueY Value to scroll vertical scroll bar to.
     * @throws TimeoutExpiredException
     */
    public void scrollToValues(double proportionalValueX, double proportionalValueY) {
	scrollToVerticalValue(proportionalValueX);
	scrollToHorizontalValue(proportionalValueY);
    }

    /**
     * Scrolls pane to top.
     * @throws TimeoutExpiredException
     */
    public void scrollToTop() {
	output.printTrace("Scroll ScrollPane to top\n" +
			  getSource().toString());
	output.printGolden("Scroll ScrollPane to top");
	produceTimeRestricted(new Action() {
		public Object launch(Object obj) {
		    driver.scrollToMinimum(ScrollPaneOperator.this, Adjustable.VERTICAL);
		    return(null);
		}
		public String getDescription() {
		    return("Scrolling");
		}
	    }, getTimeouts().getTimeout("ScrollbarOperator.WholeScrollTimeout"));
    }

    /**
     * Scrolls pane to bottom.
     * @throws TimeoutExpiredException
     */
    public void scrollToBottom() {
	output.printTrace("Scroll ScrollPane to bottom\n" +
			  getSource().toString());
	output.printGolden("Scroll ScrollPane to bottom");
	produceTimeRestricted(new Action() {
		public Object launch(Object obj) {
		    driver.scrollToMaximum(ScrollPaneOperator.this, Adjustable.VERTICAL);
		    return(null);
		}
		public String getDescription() {
		    return("Scrolling");
		}
	    }, getTimeouts().getTimeout("ScrollbarOperator.WholeScrollTimeout"));
    }

    /**
     * Scrolls pane to left.
     * @throws TimeoutExpiredException
     */
    public void scrollToLeft() {
	output.printTrace("Scroll ScrollPane to left\n" +
			  getSource().toString());
	output.printGolden("Scroll ScrollPane to left");
	produceTimeRestricted(new Action() {
		public Object launch(Object obj) {
		    driver.scrollToMinimum(ScrollPaneOperator.this, Adjustable.HORIZONTAL);
		    return(null);
		}
		public String getDescription() {
		    return("Scrolling");
		}
	    }, getTimeouts().getTimeout("ScrollbarOperator.WholeScrollTimeout"));
    }

    /**
     * Scrolls pane to right.
     * @throws TimeoutExpiredException
     */
    public void scrollToRight() {
	output.printTrace("Scroll ScrollPane to right\n" +
			  getSource().toString());
	output.printGolden("Scroll ScrollPane to right");
	produceTimeRestricted(new Action() {
		public Object launch(Object obj) {
		    driver.scrollToMaximum(ScrollPaneOperator.this, Adjustable.HORIZONTAL);
		    return(null);
		}
		public String getDescription() {
		    return("Scrolling");
		}
	    }, getTimeouts().getTimeout("ScrollbarOperator.WholeScrollTimeout"));
    }

    /**
     * Scrolls pane to rectangle..
     * @throws TimeoutExpiredException
     */
    public void scrollToComponentRectangle(Component comp, int x, int y, int width, int height) {
	scrollTo(new ComponentRectChecker(comp, x, y, width, height, Adjustable.HORIZONTAL));
	scrollTo(new ComponentRectChecker(comp, x, y, width, height, Adjustable.VERTICAL));
    }

    /**
     * Scrolls pane to point.
     * @throws TimeoutExpiredException
     */
    public void scrollToComponentPoint(Component comp, int x, int y) {
	scrollToComponentRectangle(comp, 
				   x - X_POINT_RECT_SIZE,
				   y - Y_POINT_RECT_SIZE,
				   2 * X_POINT_RECT_SIZE,
				   2 * Y_POINT_RECT_SIZE);
    }

    /**
     * Scrolls pane to component on this pane.
     * Component should lay on the ScrollPane view.
     * @param comp Component to scroll to.
     * @throws TimeoutExpiredException
     */
    public void scrollToComponent(Component comp) {
	output.printTrace("Scroll ScrollPane " + getSource().toString() + 
			  "\nto component " + comp.toString());
	output.printGolden("Scroll ScrollPane to " + comp.getClass().getName() + " component.");
	scrollToComponentRectangle(comp, 0, 0, comp.getWidth(), comp.getHeight());
    }


    /**
     * Checks if component's rectangle is inside view port (no scrolling necessary).
     * @param component 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public boolean checkInside(Component comp, int x, int y, int width, int height) {
	Point toPoint = SwingUtilities.
	    convertPoint(comp, x, y, getSource());
	if(toPoint.x < getHAdjustable().getValue()) {
	    return(false);
	}
	if(comp.getWidth() > getSource().getWidth()) {
	    if(toPoint.x > 0) {
		return(false);
	    }
	} else {
	    if(toPoint.x + comp.getWidth() > 
	       getHAdjustable().getValue() + getSource().getWidth()) {
		return(false);
	    }
	}
	if(toPoint.y < getVAdjustable().getValue()) {
	    return(false);
	}
	if(comp.getHeight() > getSource().getHeight()) {
	    if(toPoint.y > 0) {
		return(false);
	    }
	} else {
	    if(toPoint.y + comp.getHeight() > 
	       getVAdjustable().getValue() + getSource().getHeight()) {
		return(false);
	    }
	}
	return(true);
    }
    
    /**
     * Checks if component is inside view port (no scrolling necessary).
     * @param component 
     */
    public boolean checkInside(Component comp) {
	return(checkInside(comp, 0, 0, comp.getWidth(), comp.getHeight()));
    }

    public boolean isScrollbarVisible(int orientation) {
	if       (orientation == Adjustable.HORIZONTAL) {
	    return(getViewportSize().getHeight() < getHeight() - getHScrollbarHeight());
	} else if(orientation == Adjustable.VERTICAL) {
	    return(getViewportSize().getWidth() < getWidth() - getVScrollbarWidth());
	} else {
	    return(false);
	}
    }

    ////////////////////////////////////////////////////////
    //Mapping                                             //

    /**Maps <code>ScrollPane.getHAdjustable()</code> through queue*/
    public Adjustable getHAdjustable() {
	return((Adjustable)runMapping(new MapAction("getHAdjustable") {
		public Object map() {
		    return(((ScrollPane)getSource()).getHAdjustable());
		}}));}

    /**Maps <code>ScrollPane.getHScrollbarHeight()</code> through queue*/
    public int getHScrollbarHeight() {
	return(runMapping(new MapIntegerAction("getHScrollbarHeight") {
		public int map() {
		    return(((ScrollPane)getSource()).getHScrollbarHeight());
		}}));}

    /**Maps <code>ScrollPane.getScrollPosition()</code> through queue*/
    public Point getScrollPosition() {
	return((Point)runMapping(new MapAction("getScrollPosition") {
		public Object map() {
		    return(((ScrollPane)getSource()).getScrollPosition());
		}}));}

    /**Maps <code>ScrollPane.getScrollbarDisplayPolicy()</code> through queue*/
    public int getScrollbarDisplayPolicy() {
	return(runMapping(new MapIntegerAction("getScrollbarDisplayPolicy") {
		public int map() {
		    return(((ScrollPane)getSource()).getScrollbarDisplayPolicy());
		}}));}

    /**Maps <code>ScrollPane.getVAdjustable()</code> through queue*/
    public Adjustable getVAdjustable() {
	return((Adjustable)runMapping(new MapAction("getVAdjustable") {
		public Object map() {
		    return(((ScrollPane)getSource()).getVAdjustable());
		}}));}

    /**Maps <code>ScrollPane.getVScrollbarWidth()</code> through queue*/
    public int getVScrollbarWidth() {
	return(runMapping(new MapIntegerAction("getVScrollbarWidth") {
		public int map() {
		    return(((ScrollPane)getSource()).getVScrollbarWidth());
		}}));}

    /**Maps <code>ScrollPane.getViewportSize()</code> through queue*/
    public Dimension getViewportSize() {
	return((Dimension)runMapping(new MapAction("getViewportSize") {
		public Object map() {
		    return(((ScrollPane)getSource()).getViewportSize());
		}}));}

    /**Maps <code>ScrollPane.paramString()</code> through queue*/
    public String paramString() {
	return((String)runMapping(new MapAction("paramString") {
		public Object map() {
		    return(((ScrollPane)getSource()).paramString());
		}}));}

    /**Maps <code>ScrollPane.setScrollPosition(int, int)</code> through queue*/
    public void setScrollPosition(final int i, final int i1) {
	runMapping(new MapVoidAction("setScrollPosition") {
		public void map() {
		    ((ScrollPane)getSource()).setScrollPosition(i, i1);
		}});}

    /**Maps <code>ScrollPane.setScrollPosition(Point)</code> through queue*/
    public void setScrollPosition(final Point point) {
	runMapping(new MapVoidAction("setScrollPosition") {
		public void map() {
		    ((ScrollPane)getSource()).setScrollPosition(point);
		}});}

    //End of mapping                                      //
    ////////////////////////////////////////////////////////

    private class ValueScrollAdjuster implements ScrollAdjuster {
	int value;
	int orientation;
	Adjustable adj;
	public ValueScrollAdjuster(int value, int orientation, Adjustable adj) {
	    this.value = value;
	    this.orientation = orientation;
	    this.adj = adj;
	}
	public int getScrollDirection() {
	    if(adj.getValue() == value) {
		return(ScrollAdjuster.DO_NOT_TOUCH_SCROLL_DIRECTION);
	    } else {
		return((adj.getValue() < value) ?
		       ScrollAdjuster.INCREASE_SCROLL_DIRECTION :
		       ScrollAdjuster.DECREASE_SCROLL_DIRECTION);
	    }
	}
	public int getScrollOrientation() {
	    return(orientation);
	}
	public String getDescription() {
	    return("Scroll to " + Integer.toString(value) + " value");
	}
    }
    private class ComponentRectChecker implements ScrollAdjuster {
	Component comp;
	int x;
	int y;
	int width;
	int height;
	int orientation;
	public ComponentRectChecker(Component comp, int x, int y, int width, int height, int orientation) {
	    this.comp = comp;
	    this.x = x;
	    this.y = y;
	    this.width = width;
	    this.height = height;
	    this.orientation = orientation;
	}
	public int getScrollDirection() {
	    int sp = 
		(orientation == Adjustable.HORIZONTAL) ? 
		(int)getScrollPosition().getX(): 
		(int)getScrollPosition().getY();
	    Point pnt = SwingUtilities.convertPoint(comp, x, y, ((Container)getSource()).getComponents()[0]);
	    int cp = 
		(orientation == Adjustable.HORIZONTAL) ? 
		pnt.x :
		pnt.y;
	    int sl = 
		(orientation == Adjustable.HORIZONTAL) ? 
		(int)getViewportSize().getWidth(): 
		(int)getViewportSize().getHeight();
	    int cl = 
		(orientation == Adjustable.HORIZONTAL) ? 
		width :
		height;
	    if(cp <= sp) {
		return(ScrollAdjuster.DECREASE_SCROLL_DIRECTION);
	    } else if((cp + cl) > (sp + sl) &&
		      cp        > sp) {
		return(ScrollAdjuster.INCREASE_SCROLL_DIRECTION);
	    } else {
		return(ScrollAdjuster.DO_NOT_TOUCH_SCROLL_DIRECTION);
	    }
	}
	public int getScrollOrientation() {
	    return(orientation);
	}
	public String getDescription() {
	    return("");
	}
    }

    static class ScrollPaneFinder implements ComponentChooser {
	ComponentChooser subFinder;
	public ScrollPaneFinder(ComponentChooser sf) {
	    subFinder = sf;
	}
	public boolean checkComponent(Component comp) {
	    if(comp instanceof ScrollPane) {
		return(subFinder.checkComponent(comp));
	    }
	    return(false);
	}
	public String getDescription() {
	    return(subFinder.getDescription());
	}
    }
}
