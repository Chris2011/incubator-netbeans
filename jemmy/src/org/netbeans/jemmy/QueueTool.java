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

package org.netbeans.jemmy;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;

import java.awt.event.InvocationEvent;

import java.lang.reflect.InvocationTargetException;

/**
 * 
 * Provides functionality to work with java.awt.EventQueue empty. 
 *
 * <BR><BR>Timeouts used: <BR>
 * QueueTool.WaitQueueEmptyTimeout - timeout to wait queue emptied<BR>
 * QueueTool.QueueCheckingDelta - time delta to check result<BR>
 * QueueTool.LockTimeout - time to wait queue locked after lock action has been put there<BR>
 * QueueTool.InvocationTimeout - time for action was put into queue to be started<BR>
 * QueueTool.MaximumLockingTime - maximum time to lock queue.
 *
 * @see Timeouts
 *
 * @author Alexandre Iline (alexandre.iline@sun.com)
 *
 */
public class QueueTool implements Outputable, Timeoutable {

    private final static long WAIT_QUEUE_EMPTY_TIMEOUT = 180000;
    private final static long QUEUE_CHECKING_DELTA = 10;
    private final static long LOCK_TIMEOUT = 180000;
    private final static long MAXIMUM_LOCKING_TIME = 180000;
    private final static long INVOCATION_TIMEOUT = 180000;

    private final static JemmyQueue jemmyQueue;

    private TestOut output;
    private Timeouts timeouts;
    private Locker locker;
    private Waiter lockWaiter;

    /**
     * Constructor.
     */
    public QueueTool() {
	locker = new Locker();
	lockWaiter = new Waiter(new Waitable() {
		public Object actionProduced(Object obj) {
		    return(locker.isLocked() ? "" : null);
		}
		public String getDescription() {
		    return("Event queue to be locked");
		}
	    });
	setOutput(JemmyProperties.getProperties().getOutput());
	setTimeouts(JemmyProperties.getProperties().getTimeouts());
    }

    /**
     * Returns system EventQueue.
     */
    public static EventQueue getQueue() {
        return(jemmyQueue);
    }

    /**
     * Map to <code>EventQueue.isDispatchThread()</code>
     */
    public static boolean isDispatchThread() {
	return(getQueue().isDispatchThread());
    }

    /**
     * Checks if system event queue is empty.
     */
    public static boolean checkEmpty() {
	return(getQueue().peekEvent() == null);
    }

    static {
        jemmyQueue = new JemmyQueue();
        Toolkit.getDefaultToolkit().getSystemEventQueue().push(jemmyQueue);
	Timeouts.initDefault("QueueTool.WaitQueueEmptyTimeout", WAIT_QUEUE_EMPTY_TIMEOUT);
	Timeouts.initDefault("QueueTool.QueueCheckingDelta", QUEUE_CHECKING_DELTA);
	Timeouts.initDefault("QueueTool.LockTimeout", LOCK_TIMEOUT);
	Timeouts.initDefault("QueueTool.InvocationTimeout", INVOCATION_TIMEOUT);
	Timeouts.initDefault("QueueTool.MaximumLockingTime", MAXIMUM_LOCKING_TIME);
    }

    /**
     * Defines current timeouts.
     * @param t A collection of timeout assignments.
     * @see org.netbeans.jemmy.Timeouts
     * @see org.netbeans.jemmy.Timeoutable
     */
    public void setTimeouts(Timeouts ts) {
	timeouts = ts;
	lockWaiter.setTimeouts(getTimeouts().cloneThis());
    }

    /**
     * Return current timeouts.
     * @return the collection of current timeout assignments.
     * @see org.netbeans.jemmy.Timeouts
     * @see org.netbeans.jemmy.Timeoutable
     */
    public Timeouts getTimeouts() {
	return(timeouts);
    }

    /**
     * Defines print output streams or writers.
     * @param out Identify the streams or writers used for print output.
     * @see org.netbeans.jemmy.Outputable
     * @see org.netbeans.jemmy.TestOut
     */
    public void setOutput(TestOut out) {
	output = out;
	lockWaiter.setOutput(output.createErrorOutput());
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

    /**
     * Waits for system event queue empty.
     * Uses "QueueTool.WaitQueueEmptyTimeout" milliseconds to wait.
     * @throws TimeoutExpiredException
     */
    public void waitEmpty() {
	Waiter waiter = new Waiter(new Waitable() {
	    public Object actionProduced(Object obj) {
		if(checkEmpty()) {
		    return("Empty");
		}
		return(null);
	    }
	    public String getDescription() {
		return("Wait event queue empty");
	    }
	});
	waiter.setTimeouts(timeouts.cloneThis());
	waiter.getTimeouts().setTimeout("Waiter.WaitingTime",
					timeouts.getTimeout("QueueTool.WaitQueueEmptyTimeout"));
	waiter.setOutput(output);
	try {
	    waiter.waitAction(null);
	} catch(TimeoutExpiredException e) {
            AWTEvent event = getQueue().peekEvent();
            getOutput().printErrLine("Event at the top of stack: " + 
                                     ((event == null) ? "null" : event.toString()));
            throw(e);
	} catch(InterruptedException e) {
	    output.printStackTrace(e);
	}
    }

    /**
     * Waits for system event queue be empty for emptyTime milliseconds.
     * Uses "QueueTool.WaitQueueEmptyTimeout" milliseconds to wait.
     * @throws TimeoutExpiredException
     */
    public void waitEmpty(long emptyTime) {

	StayingEmptyWaiter waiter = new StayingEmptyWaiter(emptyTime);
	waiter.setTimeouts(timeouts.cloneThis());
	waiter.getTimeouts().setTimeout("Waiter.WaitingTime",
					timeouts.getTimeout("QueueTool.WaitQueueEmptyTimeout"));
	waiter.setOutput(output);
	try {
	    waiter.waitAction(null);
	} catch(TimeoutExpiredException e) {
            AWTEvent event = getQueue().peekEvent();
            getOutput().printErrLine("Event at the top of stack: " + 
                                     ((event == null) ? "null" : event.toString()));
            throw(e);
	} catch(InterruptedException e) {
	    output.printStackTrace(e);
	}
    }

    /**
     * Dispathced events going from Jemmy code.
     * If method is executed from event dispatch thread
     * (<code>EventQueue.isDispatchThread()</code>) events
     * is "shortcuted" (passed directly into
     * <code>EventQueue.dispatchEvent(AWTEvent)<code> method.
     * Otherwise, event id dispatched regular way: 
     * by EventQueue.postEvent(AWTEvent) method.
     * @param event Event to dispatch.
     */
    public void dispatchEvent(AWTEvent event) {
        if(isDispatchThread()) {
            jemmyQueue.shortcutEvent(event);
        } else {
            getQueue().postEvent(event);
        }
    }

    /**
     * Invokes action through EventQueue.
     * Does not wait for it execution.
     * @param action
     */
    public void invoke(QueueAction action) {
	output.printTrace("Invoking \"" + action.getDescription() + "\" action through event queue");
	EventQueue.invokeLater(action);
    }

    /**
     * Invokes runnable through EventQueue.
     * Does not wait for it execution.
     * @param runable
     * @return QueueAction instance which can be use for execution monitoring.
     * @see QueueTool.QueueAction
     */
    public QueueAction invoke(Runnable runnable) {
	QueueAction result = new RunnableRunnable(runnable);
	invoke(result);
	return(result);
    }

    /**
     * Invokes action through EventQueue.
     * Does not wait for it execution.
     * @param action
     * @param param <code>action.launch(Object)</code> method parameter.
     * @return QueueAction instance which can be use for execution monitoring.
     * @see QueueTool.QueueAction
     */
    public QueueAction invoke(Action action, Object param) {
	QueueAction result = new ActionRunnable(action, param);
	invoke(result);
	return(result);
    }

    public Object invokeSmoothly(QueueAction action) {
        if(!getQueue().isDispatchThread()) {
            return(invokeAndWait(action));
        } else {
            try {
                return(action.launch());
            } catch(Exception e) {
                throw(new JemmyException("Exception in " + action.getDescription(), e));
            }
        }
    }

    public void invokeSmoothly(Runnable runnable) {
        if(!getQueue().isDispatchThread()) {
            invokeAndWait(runnable);
        } else {
            runnable.run();
        }
    }

    public Object invokeSmoothly(Action action, Object param) {
        if(!getQueue().isDispatchThread()) {
            return(invokeAndWait(action, param));
        } else {
            return(action.launch(param));
        }
    }

    /**
     * Invokes action through EventQueue.
     * Waits for it execution.
     * @param action
     * @throws TimeoutExpiredException if action
     * was not executed in "QueueTool.InvocationTimeout" milliseconds.
     */
    public Object invokeAndWait(QueueAction action) {
	class JemmyInvocationLock {}
        Object lock = new JemmyInvocationLock();
        InvocationEvent event = 
            new InvocationEvent(Toolkit.getDefaultToolkit(), 
                                action, 
                                lock,
				true);
	try {
            synchronized (lock) {
                getQueue().postEvent(event);
                lock.wait();
            }
	} catch(InterruptedException e) {
	    throw(new JemmyException("InterruptedException during " + 
				     action.getDescription() + 
				     " execution", e));
	}
	if(action.getException() != null) {
	    throw(new JemmyException("Exception in " + action.getDescription(), 
				     action.getException()));
	}
	if(event.getException() != null) {
	    throw(new JemmyException("Exception in " + action.getDescription(), 
				     event.getException()));
	}
	return(action.getResult());
    }

    /**
     * Invokes runnable through EventQueue.
     * Waits for it execution.
     * @param runable
     * @throws TimeoutExpiredException if runnable
     * was not executed in "QueueTool.InvocationTimeout" milliseconds.

     */
    public void invokeAndWait(Runnable runnable) {
	invokeAndWait(new RunnableRunnable(runnable));
    }

    /**
     * Invokes action through EventQueue.
     * Waits for it execution.
     * May throw TimeoutExpiredException if action
     * was not executed in "QueueTool.InvocationTimeout" milliseconds.
     * @param runable
     * @param param action.launch(Object method parameter.
     * @throws TimeoutExpiredException if action
     * was not executed in "QueueTool.InvocationTimeout" milliseconds.
     */
    public Object invokeAndWait(Action action, Object param) {
	return(invokeAndWait(new ActionRunnable(action, param)));
    }

    /**
     * Locks EventQueue.
     * Locking will be automatically aborted after 
     * "QueueTool.MaximumLockingTime" milliseconds.
     * @see #unlock()
     * @throws TimeoutExpiredException
     */
    public void lock() {
	output.printTrace("Locking queue.");
	invoke(locker);
	try {
	    lockWaiter.
		getTimeouts().
		setTimeout("Waiter.WaitingTime",
			   timeouts.
			   getTimeout("QueueTool.LockTimeout"));
	    lockWaiter.
		getTimeouts().
		setTimeout("Waiter.TimeDelta",
			   timeouts.
			   getTimeout("QueueTool.QueueCheckingDelta"));
	    lockWaiter.waitAction(null);
	} catch(InterruptedException e) {
	    output.printStackTrace(e);
	}
    }

    /**
     * Unlocks EventQueue.
     * @see #lock()
     */
    public void unlock() {
	output.printTrace("Unlocking queue.");
	locker.setLocked(false);
    }

    /**
     * Locks event queue for "time" milliseconds.
     * Returns immediately after locking.
     */
    public void lock(long time) {
	output.printTrace("Locking queue for " + Long.toString(time) + " milliseconds");
	lock();
	invoke(new UnlockPostponer(time));
    }

    /**
     * Sais if last locking was expired.
     */
    public boolean wasLockingExpired() {
	return(locker.expired);
    }

    /**
     * Action to be excuted through event queue.
     * Even if it was executed without waiting by <code>invoke(QueueAction)</code>
     * execution process can be monitored by <code>getResult()</code>,
     * <code>getException()</code>, <code>getFinished()</code> methods.
     */
    public static abstract class QueueAction implements Runnable {
	private boolean finished;
	private Exception exception;
	private Object result;
	private String description;
	/**
	 * Constructor.
	 */
	public QueueAction(String description) {
	    this.description = description;
	    finished = false;
	    exception = null;
	    result = null;
	}
	/**
	 * Method to implement action functionality.
	 */
	public abstract Object launch() 
	    throws Exception;

	/**
	 */
	public final void run() {
	    finished = false;
	    exception = null;
	    result = null;
	    try {
		result = launch();
	    } catch(Exception e) {
		exception = e;
	    }
	    finished = true;
	}
	/**
	 * Action description.
	 */
	public String getDescription() {
	    return(description);
	}
	/**
	 * Returns action result if action has already been finished,
	 * null otherwise.
	 */
	public Object getResult() {
	    return(result);
	}
	/**
	 * Returns exception occured during action execution (if any).
	 */
	public Exception getException() {
	    return(exception);
	}
	/**
	 * Informs whether action has been finished or not.
	 */
	public boolean getFinished() {
	    return(finished);
	}
    }

    private static class JemmyQueue extends EventQueue {
        public void shortcutEvent(AWTEvent event) {
            super.dispatchEvent(event);
        }
    }

    private class EventWaiter implements Runnable {
	boolean empty = true;
	long emptyTime;
	public EventWaiter(long emptyTime) {
	    this.emptyTime = emptyTime;
	}
	public void run() {
	    long startTime = System.currentTimeMillis();
	    while((empty = checkEmpty()) && 
		  (System.currentTimeMillis() - startTime) < emptyTime) {
		timeouts.sleep("QueueTool.QueueCheckingDelta");
	    }
	}
    }

    private class StayingEmptyWaiter extends Waiter {
	long emptyTime;

	public StayingEmptyWaiter(long emptyTime) {
	    this.emptyTime = emptyTime;
	}

	public Object actionProduced(Object obj) {
	    try {
		EventWaiter eventWaiter = new EventWaiter(emptyTime);
		EventQueue.invokeAndWait(eventWaiter);
		if(eventWaiter.empty &&
		   timeFromStart() <= super.getTimeouts().getTimeout("Waiter.WaitingTime")) {
		    return("Reached");
		}
	    } catch(InterruptedException e) {
		output.printStackTrace(e);
	    } catch(InvocationTargetException e) {
		output.printStackTrace(e);
	    }
	    return(null);
	}
	public String getDescription() {
	    return("Wait event queue staying empty for " + 
		   Long.toString(emptyTime));
	}
    }

    private class ActionRunnable extends QueueAction {
	Action action;
	Object param;
	public ActionRunnable(Action action, Object param) {
	    super(action.getDescription());
	    this.action = action;
	    this.param = param;
	}
	public Object launch() throws Exception {
	    return(action.launch(param)); 
	}
    }

    private class RunnableRunnable extends QueueAction {
	Runnable action;
	public RunnableRunnable(Runnable action) {
	    super("Runnable");
	    this.action = action;
	}
	public Object launch() throws Exception {
	    action.run();
	    return(null);
	}
    }

    private class Locker extends QueueAction {
	boolean locked = false;
	long wholeTime, deltaTime;
	boolean expired;
	public Locker() {
	    super("Event queue locking");
	}
	public Object launch() {
	    wholeTime = timeouts.getTimeout("QueueTool.MaximumLockingTime");
	    deltaTime = timeouts.getTimeout("QueueTool.QueueCheckingDelta");
	    setLocked(true);
	    expired = false;
	    long startTime = System.currentTimeMillis();
	    while(isLocked()) {
		try {
		    Thread.sleep(deltaTime);
		} catch(InterruptedException e) {
		    getOutput().printStackTrace(e);
		}
		if(System.currentTimeMillis() - startTime > wholeTime) {
		    getOutput().printLine("Locking has been expired!");
		    expired = true;
		    break;
		}
	    }
	    return(null);
	}
	public void setLocked(boolean locked) {
	    this.locked = locked;
	}
	public boolean isLocked() {
	    return(locked);
	}
    }

    private class UnlockPostponer implements Runnable {
	long time;
	public UnlockPostponer(long time) {
	    this.time = time;
	}
	public void run() {
	    new Timeout("", time).sleep();
	    unlock();
	}
    }

}
