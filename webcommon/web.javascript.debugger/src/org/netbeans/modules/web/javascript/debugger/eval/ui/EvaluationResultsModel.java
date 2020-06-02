/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.web.javascript.debugger.eval.ui;

import java.util.List;
import org.netbeans.api.debugger.DebuggerEngine;
import org.netbeans.modules.web.javascript.debugger.locals.VariablesModel;
import org.netbeans.spi.debugger.ContextProvider;
import org.netbeans.spi.debugger.DebuggerServiceRegistration;
import org.netbeans.spi.debugger.ui.CodeEvaluator;
import org.netbeans.spi.debugger.ui.CodeEvaluator.Result.DefaultHistoryItem;
import org.netbeans.spi.debugger.ui.Constants;
import org.netbeans.spi.viewmodel.ExtendedNodeModel;
import org.netbeans.spi.viewmodel.ModelEvent;
import org.netbeans.spi.viewmodel.ModelListener;
import org.netbeans.spi.viewmodel.TableModel;
import org.netbeans.spi.viewmodel.TreeModel;
import org.netbeans.spi.viewmodel.UnknownTypeException;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Martin Entlicher
 */
@DebuggerServiceRegistration(path="javascript-debuggerengine/ResultsView", types={ TreeModel.class, ExtendedNodeModel.class, TableModel.class })
public class EvaluationResultsModel extends VariablesModel {
    
    private static final String ICON_HISTORY_NODE =
        "org/netbeans/modules/debugger/resources/evaluator/history_node_16.png"; // NOI18N

    private static final String ICON_HISTORY_ITEM =
        "org/netbeans/modules/debugger/resources/evaluator/eval_history_item.png"; // NOI18N
    
    private static final String ICON_EVAL_RESULT =
        "org/netbeans/modules/debugger/resources/evaluator/evaluator_result_16.png"; // NOI18N

    private final static RequestProcessor RP = new RequestProcessor(EvaluationResultsModel.class);
    private final CodeEvaluator.Result result;
    private EvaluatorListener evalListener = new EvaluatorListener();
    
    public EvaluationResultsModel(ContextProvider contextProvider) {
        super(contextProvider);
        //CodeEvaluator.addResultListener(evalListener);
        result = CodeEvaluator.Result.get(contextProvider.lookupFirst(null, DebuggerEngine.class));
        result.addListener(evalListener);
    }

    @Override
    public int getChildrenCount(Object parent) throws UnknownTypeException {
        if (parent instanceof HistoryNode) {
            return ((HistoryNode) parent).getItems().size();
        }
        if (parent == ROOT) {
            return 2;
        } else {
            return super.getChildrenCount(parent);
        }
    }

    @Override
    public Object[] getChildren(Object parent, int from, int to) throws UnknownTypeException {
        if (parent instanceof HistoryNode) {
            List<?> l = ((HistoryNode) parent).getItems();
            for (Object o : l) {
                if (!(o instanceof DefaultHistoryItem)) {
                    return new Object[]{};
                }
            }
            return l.toArray();
        }
        if (parent == ROOT) {
            if (this.result != null) {
                Object result = this.result.getResult();
                List items = this.result.getHistoryItems();
                int count = 0;
                if (result != null) {
                    count++;
                }
                if (items.size() > 0) {
                    count++;
                }
                Object[] children = new Object[count];
                int index = 0;
                if (result != null) {
                    children[index++] = result;
                }
                if (items.size() > 0) {
                    children[index] = new HistoryNode(items);
                }
                return children;
            } else {
                return new Object[] {};
            }
        } else {
            return super.getChildren(parent, from, to);
        }
    }
    
    @Override
    public boolean isLeaf(Object node) throws UnknownTypeException {
        if (TreeModel.ROOT.equals(node)) {
            return false;
        } else if (node instanceof HistoryNode) {
            return false;
        } else if (node instanceof DefaultHistoryItem) {
            return true;
        }
        return super.isLeaf(node);
    }

    @NbBundle.Messages("CTL_EvaluatorHistoryNode=History")
    @Override
    public String getDisplayName(Object node) throws UnknownTypeException {
        if (node instanceof DefaultHistoryItem) {
            return ((DefaultHistoryItem) node).getExpression();
        }
        if (node instanceof EvaluationResultsModel.HistoryNode) {
            return Bundle.CTL_EvaluatorHistoryNode();
        }
        return super.getDisplayName(node);
    }

    @NbBundle.Messages("CTL_EvaluatorHistoryNodeDescr=Evaluation History")
    @Override
    public String getShortDescription(Object node) throws UnknownTypeException {
        if (node == result.getResult()) {
            String str = result.getExpression();
            if (str != null) {
                StringBuffer buf = new StringBuffer();
                buf.append("<html>");
                str = str.replaceAll ("&", "&amp;");
                str = str.replaceAll ("<", "&lt;");
                str = str.replaceAll (">", "&gt;");
                str = str.replaceAll ("\n", "<br/>");
                str = str.replaceAll ("\r", "");
                buf.append(str);
                buf.append("</html>");
                return buf.toString();
            }
        }
        if (node instanceof DefaultHistoryItem) {
            return ((DefaultHistoryItem) node).getTooltip();
        }
        if (node instanceof EvaluationResultsModel.HistoryNode) {
            return Bundle.CTL_EvaluatorHistoryNodeDescr();
        }
        return super.getShortDescription(node);
    }
    
    @Override
    public String getIconBaseWithExtension(Object node) throws UnknownTypeException {
        if (node == result.getResult()) {
            return ICON_EVAL_RESULT;
        }
        if (node instanceof DefaultHistoryItem) {
            return ICON_HISTORY_ITEM;
        }
        if (node instanceof EvaluationResultsModel.HistoryNode) {
            return ICON_HISTORY_NODE;
        }
        return super.getIconBaseWithExtension(node);
    }

    @Override
    public Object getValueAt(Object node, String columnID) throws UnknownTypeException {
        if (node instanceof DefaultHistoryItem) {
            DefaultHistoryItem item = (DefaultHistoryItem) node;
            if (Constants.LOCALS_TO_STRING_COLUMN_ID.equals(columnID)) {
                return item.getToStringValue();
            } else if (Constants.LOCALS_TYPE_COLUMN_ID.equals(columnID)) {
                return item.getType();
            } else if (Constants.LOCALS_VALUE_COLUMN_ID.equals(columnID)) {
                return item.getValue();
            }
            return ""; // NOI18N
        }
        if (node instanceof EvaluationResultsModel.HistoryNode) {
            return "";
        }
        return super.getValueAt(node, columnID);
    }

    @Override
    public boolean isReadOnly(Object node, String columnID) throws UnknownTypeException {
        if (node instanceof DefaultHistoryItem || node instanceof EvaluationResultsModel.HistoryNode) {
            return true;
        }
        return super.isReadOnly(node, columnID);
    }

    @Override
    public void setValueAt(Object node, String columnID, Object value) throws UnknownTypeException {
        if (node instanceof DefaultHistoryItem || node instanceof EvaluationResultsModel.HistoryNode) {
            return ;
        }
        super.setValueAt(node, columnID, value);
    }
    
    public void fireNodeChanged (Object node) {
        fireChangeEvent(new ModelEvent.NodeChanged(this, node));
    }
    
    private void fireSelectionChanged(final Object result) {
        final ModelListener[] ls;
        synchronized (listeners) {
            ls = listeners.toArray(new ModelListener[0]);
        }
        // Unselect
        ModelEvent ev = new ModelEvent.SelectionChanged(this);
        for (int i = 0; i < ls.length; i++) {
            ls[i].modelChanged (ev);
        }
        // Select
        RP.post(new Runnable() {
            public void run() {
                ModelEvent ev = new ModelEvent.SelectionChanged(EvaluationResultsModel.this, result);
                for (int i = 0; i < ls.length; i++) {
                    ls[i].modelChanged (ev);
                }
            }
        }, 500);
    }

    private static class HistoryNode {
        
        private final List<DefaultHistoryItem> items;

        HistoryNode(List<DefaultHistoryItem> items) {
            this.items = items;
        }
        
        List<DefaultHistoryItem> getItems() {
            return items;
        }
    }

    private class EvaluatorListener implements CodeEvaluator.Result.Listener<ScopedRemoteObject> {

        @Override
        public void resultChanged(ScopedRemoteObject o) {
            fireNodeChanged(TreeModel.ROOT);
            fireSelectionChanged(o);
        }

    }

}
