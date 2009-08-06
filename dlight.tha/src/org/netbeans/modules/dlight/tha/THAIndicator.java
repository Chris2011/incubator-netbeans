/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009 Sun Microsystems, Inc. All rights reserved.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
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
 * Contributor(s):
 *
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */
package org.netbeans.modules.dlight.tha;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import org.netbeans.modules.dlight.api.execution.DLightTarget;
import org.netbeans.modules.dlight.api.storage.DataRow;
import org.netbeans.modules.dlight.api.storage.DataUtil;
import org.netbeans.modules.dlight.api.support.NativeExecutableTarget;
import org.netbeans.modules.dlight.spi.indicator.Indicator;
import org.netbeans.modules.dlight.util.UIThread;
import org.netbeans.modules.nativeexecution.api.util.CommonTasksSupport;

public class THAIndicator extends Indicator<THAIndicatorConfiguration> {

    private final THAControlPanel controlPanel;
    private final String dataracesColumnName;
    private final String deadlocksColumnName;
    private int dataraces;
    private int deadlocks;

    public THAIndicator(final THAIndicatorConfiguration configuration) {
        super(configuration);
        controlPanel = new THAControlPanel(false, new ToggleCollectorAction(), getDefaultAction());
        dataracesColumnName = getMetadataColumnName(0);
        deadlocksColumnName = getMetadataColumnName(1);
    }

    @Override
    protected void repairNeeded(boolean needed) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected synchronized void tick() {
        UIThread.invoke(new Runnable() {
            public void run() {
                controlPanel.setDataRaces(dataraces);
                controlPanel.setDeadlocks(deadlocks);
            }
        });
    }

    @Override
    public synchronized void updated(List<DataRow> data) {
        for (DataRow row : data) {
            Object dataracesObj = row.getData(dataracesColumnName);
            dataraces = Math.max(dataraces, DataUtil.toInt(dataracesObj));
            Object deadlocksObj = row.getData(deadlocksColumnName);
            deadlocks = Math.max(deadlocks, DataUtil.toInt(deadlocksObj));
        }
    }

    @Override
    public void reset() {
        controlPanel.reset();
    }

    @Override
    public JComponent getComponent() {
        return controlPanel;
    }

    private class ToggleCollectorAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            DLightTarget target = getTarget();
            if (target instanceof NativeExecutableTarget) {
                int pid = ((NativeExecutableTarget)target).getPID();
                if (0 < pid) {
                    CommonTasksSupport.sendSignal(target.getExecEnv(), pid, "USR1", null); // NOI18N
                }
            }
        }
    }
}
