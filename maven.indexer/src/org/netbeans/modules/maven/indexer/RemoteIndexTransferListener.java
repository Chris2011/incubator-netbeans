/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s): theanuradha@netbeans.org
 *
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */
package org.netbeans.modules.maven.indexer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.wagon.events.TransferEvent;
import org.apache.maven.wagon.events.TransferListener;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.modules.maven.indexer.api.RepositoryInfo;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.Cancellable;
import static org.netbeans.modules.maven.indexer.Bundle.*;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Anuradha G
 */
public class RemoteIndexTransferListener implements TransferListener, Cancellable {

    private final @NonNull ProgressHandle handle;
    private final RepositoryInfo info;
    private int lastunit;/*last work unit*/
    private int units;

    private final AtomicBoolean canceled = new AtomicBoolean();
    private final AtomicBoolean unpacking = new AtomicBoolean();

    private static Map<Thread, Integer> transfers = new HashMap<Thread, Integer>();
    private static final Object TRANSFERS_LOCK = new Object();

    @SuppressWarnings("LeakingThisInConstructor")
    @Messages({"# {0} - repo name", "LBL_Transfer=Transferring Maven repository index: {0}"})
    public RemoteIndexTransferListener(RepositoryInfo info) {
        this.info = info;
        Cancellation.register(this);
        handle = ProgressHandleFactory.createHandle(LBL_Transfer(info.getName()), this);
        handle.start();
    }

    public @Override void transferInitiated(TransferEvent arg0) {
        checkCancel();
    }

    public @Override void transferStarted(TransferEvent arg0) {
        checkCancel();
        long contentLength = arg0.getResource().getContentLength();
        // #189806: could be resumed due to FNFE in DefaultIndexUpdater (*.gz -> *.zip)
        this.units = (int) contentLength / 1024;
        handle.switchToDeterminate(units);
    }

    public @Override boolean cancel() {
        return canceled.compareAndSet(false, true);
    }

    private void checkCancel() throws Cancellation {
        if (canceled.get()) {
            throw new Cancellation();
        }
    }

    public @Override void transferProgress(TransferEvent arg0, byte[] arg1, int arg2) {
        checkCancel();
        int work = arg2 / 1024;
        handle.progress(arg0.getResource().getName(), Math.min(units, lastunit += work));
    }

    public @Override void transferCompleted(TransferEvent arg0) {
        handle.switchToIndeterminate();
    }

    public @Override void transferError(TransferEvent e) {
        Logger.getLogger(RemoteIndexTransferListener.class.getName()).log(Level.FINE, "error transferring " + info.getIndexUpdateUrl(), e.getException());
        handle.switchToIndeterminate();
    }

    public @Override void debug(String arg0) {
        checkCancel();
    }

    static void addToActive (Thread t) {
        synchronized (TRANSFERS_LOCK) {
            Integer count = transfers.get(t);
            if (count == null) {
                count = Integer.valueOf(1);
            } else {
                count = Integer.valueOf(count + 1);
            }
            transfers.put(t, count);
        }
    }

    static void removeFromActive (Thread t) {
        synchronized (TRANSFERS_LOCK) {
            Integer count = transfers.get(t);
            if (count == null) {
                return;
            }
            if (count <= 1) {
                transfers.remove(t);
            } else {
                count = Integer.valueOf(count - 1);
                transfers.put(t, count);
            }
        }
    }

    static Set<Thread> getActiveTransfersOrScans () {
        synchronized (TRANSFERS_LOCK) {
            return transfers.keySet();
        }
    }

    @Messages({"# {0} - repo name", "LBL_unpacking=Unpacking index for {0}"})
    void unpackingProgress(String label) {
        checkCancel();
        if (unpacking.compareAndSet(false, true)) {
            handle.setDisplayName(LBL_unpacking(info.getName()));
        }
        handle.progress(label);
    }

    void close() {
        handle.finish();
    }

}
