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
package org.openide.util;


/** Queue of objects. When there is no object in the queue the process
* is suspended till some arrives.
*
* @author Jaroslav Tulach
* @version 0.10, Feb 06, 1998
*/
public class Queue extends Object {
    /** Queue enumeration */
    private java.util.LinkedList queue = new java.util.LinkedList();

    /** Adds new item.
    * @param o object to add
    */
    public synchronized void put(Object o) {
        queue.add(o);
        notify();
    }

    /** Gets an object from the queue. If there is no such object the
    * thread is suspended until some object arrives
    *
    * @return object from the queue
    */
    public synchronized Object get() {
        for (;;) {
            if (queue.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                }
            } else {
                break;
            }
        }

        return queue.removeFirst();
    }
}
