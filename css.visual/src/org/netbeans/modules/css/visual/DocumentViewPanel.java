/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 * Contributor(s):
 *
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.css.visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import org.netbeans.modules.css.lib.api.CssParserResult;
import org.netbeans.modules.css.model.api.Model;
import org.netbeans.modules.css.model.api.ModelUtils;
import org.netbeans.modules.css.model.api.Rule;
import org.netbeans.modules.css.model.api.StyleSheet;
import org.netbeans.modules.css.visual.api.RuleEditorController;
import org.netbeans.modules.parsing.api.ParserManager;
import org.netbeans.modules.parsing.api.ResultIterator;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.modules.parsing.api.UserTask;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.web.common.api.WebUtils;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.filesystems.FileObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 *
 * @author marekfukala
 */
@NbBundle.Messages({})
public class DocumentViewPanel extends javax.swing.JPanel implements ExplorerManager.Provider {

    private static RequestProcessor RP = new RequestProcessor();
    /**
     * Tree view showing the style sheet information.
     */
    private BeanTreeView treeView;
    /**
     * Explorer manager provided by this panel.
     */
    private ExplorerManager manager = new ExplorerManager();
    /**
     * Lookup of this panel.
     */
    private final Lookup lookup;
    private final Lookup cssStylesLookup;
    /**
     * Filter for the tree displayed in this panel.
     */
    private Filter filter = new Filter();
    private DocumentViewModel documentModel;

    private final ChangeListener DOCUMENT_VIEW_MODEL_LISTENER = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent ce) {
            updateContent();
        }
    };
    
    /**
     * Creates new form DocumentViewPanel
     */
    public DocumentViewPanel(Lookup cssStylesLookup) {
        this.cssStylesLookup = cssStylesLookup;
        Result<FileObject> result = cssStylesLookup.lookupResult(FileObject.class);
        result.addLookupListener(new LookupListener() {
            @Override
            public void resultChanged(LookupEvent ev) {
                contextChanged();
            }
        });

        lookup = ExplorerUtils.createLookup(getExplorerManager(), getActionMap());
        Result<Node> lookupResult = lookup.lookupResult(Node.class);
        lookupResult.addLookupListener(new LookupListener() {
            @Override
            public void resultChanged(LookupEvent ev) {
                Node[] selectedNodes = manager.getSelectedNodes();
                Node selected = selectedNodes.length > 0 ? selectedNodes[0] : null;
                if (selected != null) {
                    RuleHandle ruleHandle = selected.getLookup().lookup(RuleHandle.class);
                    if (ruleHandle != null) {
                        selectRuleInRuleEditor(ruleHandle);
                    }
                }
            }
        });

        initComponents();

        initTreeView();
        initFilter();

        contextChanged();

    }

    private void selectRuleInRuleEditor(RuleHandle handle) {
        RuleEditorController rec = cssStylesLookup.lookup(RuleEditorController.class);
        if (rec == null) {
            return;
        }
        final Rule rule = handle.getRule();
        final AtomicReference<Rule> matched_rule_ref = new AtomicReference<Rule>();
        
        FileObject file = handle.getFile();
        Source source = Source.create(file);
        try {
            ParserManager.parse(Collections.singleton(source), new UserTask() {
                @Override
                public void run(ResultIterator resultIterator) throws Exception {
                    ResultIterator ri = WebUtils.getResultIterator(resultIterator, "text/css"); //NOI18N
                    if (ri != null) {
                        final CssParserResult result = (CssParserResult) ri.getParserResult();
                        final Model model = Model.getModel(result);

                        model.runReadTask(new Model.ModelTask() {
                            @Override
                            public void run(StyleSheet styleSheet) {
                                ModelUtils utils = new ModelUtils(model);
                                Rule match = utils.findMatchingRule(rule.getModel(), rule);
                                matched_rule_ref.set(match);
                            }
                        });
                    }
                }
            });
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

        Rule match = matched_rule_ref.get();
        if(match != null) {
            rec.setModel(match.getModel());
            rec.setRule(match);
        }
    }

    @Override
    public final ExplorerManager getExplorerManager() {
        return manager;
    }

    private FileObject getContext() {
        return cssStylesLookup.lookup(FileObject.class);
    }

    /**
     * Called when the CssStylesPanel is activated for different file.
     */
    private void contextChanged() {
        RP.post(new Runnable() {
            @Override
            public void run() {
                final FileObject context = getContext();

                //dispose old model
                if (documentModel != null) {
                    documentModel.removeChangeListener(DOCUMENT_VIEW_MODEL_LISTENER);
                    documentModel.dispose();
                }

                if (context == null) {
                    documentModel = null;
                } else {
                    documentModel = new DocumentViewModel(context);
                    documentModel.addChangeListener(DOCUMENT_VIEW_MODEL_LISTENER);
                }

                updateContent();
            }
        });

    }

    /**
     * Initializes the tree view.
     */
    private void initTreeView() {
        treeView = new BeanTreeView() {
            {
                MouseAdapter listener = createTreeMouseListener();
                tree.addMouseListener(listener);
                tree.addMouseMotionListener(listener);
                tree.setCellRenderer(createTreeCellRenderer(tree.getCellRenderer()));
            }

            @Override
            public void expandAll() {
                // The original expandAll() doesn't work for us as it doesn't
                // seem to wait for the calculation of sub-nodes.
                Node root = manager.getRootContext();
                expandAll(root);
                // The view attempts to scroll to the expanded node
                // and it does it with a delay. Hence, simple calls like
                // tree.scrollRowToVisible(0) have no effect (are overriden
                // later) => the dummy collapse and expansion attempts
                // to work around that and keep the root node visible.
                collapseNode(root);
                expandNode(root);
            }

            /**
             * Expands the whole sub-tree under the specified node.
             *
             * @param node root node of the sub-tree that should be expanded.
             */
            private void expandAll(Node node) {
                treeView.expandNode(node);
                for (Node subNode : node.getChildren().getNodes(true)) {
                    if (!subNode.isLeaf()) {
                        expandAll(subNode);
                    }
                }
            }
        };
        treeView.setAllowedDragActions(DnDConstants.ACTION_NONE);
        treeView.setAllowedDropActions(DnDConstants.ACTION_NONE);
        treeView.setRootVisible(false);
        add(treeView, BorderLayout.CENTER);
    }

    /**
     * Initializes the filter section of this panel.
     */
    private void initFilter() {
        JPanel panel = new JPanel();
        Color background = treeView.getViewport().getView().getBackground();
        panel.setBackground(background);

        // "Find" label
        JLabel label = new JLabel(ImageUtilities.loadImageIcon(
                "org/netbeans/modules/css/visual/resources/find.png", true)); // NOI18N
        label.setVerticalAlignment(SwingConstants.CENTER);

        // Pattern text field
        final JTextField field = new JTextField();
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filter.setPattern(field.getText());
            }
        });

        // Clear pattern button
        JButton button = new JButton(ImageUtilities.loadImageIcon(
                "org/netbeans/modules/css/visual/resources/cancel.png", true)); // NOI18N
        button.setBackground(background);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.setText(""); // NOI18N
            }
        });

        // Layout
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(2)
                .addComponent(label)
                .addComponent(field)
                .addComponent(button)
                .addGap(2));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(label)
                .addComponent(field)
                .addComponent(button)));
        add(panel, BorderLayout.PAGE_START);
    }

    final void updateContent() {
        final Node root;
        if (documentModel == null) {
            // Using dummy node as the root to release the old root
            root = new AbstractNode(Children.LEAF);
        } else {
            filter.removePropertyChangeListeners();
            DocumentNode documentNode = new DocumentNode(documentModel, filter);
            root = new FakeRootNode<DocumentNode>(documentNode,
                    new Action[]{});
        }
        final Node[] oldSelection = manager.getSelectedNodes();
        manager.setRootContext(root);
        treeView.expandAll();

        //keep selection
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                List<Node> selection = new ArrayList<Node>(oldSelection.length);
                for (Node oldSelected : oldSelection) {
                    Location location = oldSelected.getLookup().lookup(Location.class);
                    if (location != null) {
                        Node newSelected = findRule(root, location);
                        if (newSelected != null) {
                            selection.add(newSelected);
                        }
                    }
                }
                try {
                    manager.setSelectedNodes(selection.toArray(new Node[selection.size()]));
                } catch (PropertyVetoException pvex) {
                    //no-op
                }
            }
        });

    }

    /**
     * Finds a node that represents the specified location in a tree represented
     * by the given root node.
     *
     * @param root root of a tree to search.
     * @param rule rule to find.
     * @return node that represents the rule or {@code null}.
     */
    public static Node findRule(Node root, Location location) {
        Location candidate = root.getLookup().lookup(Location.class);
        if (candidate != null && location.equals(candidate)) {
            return root;
        }
        for (Node node : root.getChildren().getNodes()) {
            Node result = findRule(node, location);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
    // The last node we were hovering over.
    Object lastHover = null;

    /**
     * Creates a mouse listener for the tree view.
     *
     * @return mouse listener for the tree view.
     */
    public MouseAdapter createTreeMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                processEvent(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                processEvent(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                processEvent(null);
                // Make sure that lastHover != <any potential value>
                // i.e., make sure that change in hover is triggered when
                // mouse returns into this component
                lastHover = new Object();
            }

            /**
             * Processes the specified mouse event.
             *
             * @param e mouse event to process.
             */
            private void processEvent(MouseEvent e) {
//                Object hover = null;
//                if (e != null) {
//                    JTree tree = (JTree)e.getSource();
//                    TreePath path = tree.getPathForLocation(e.getX(), e.getY());
//                    if (path != null) {
//                        hover = path.getLastPathComponent();
//                    }
//                }
//                if (hover != lastHover) {
//                    lastHover = hover;
//                    final String selector;
//                    if (hover != null) {
//                        Node node = Visualizer.findNode(hover);
//                        Rule rule = node.getLookup().lookup(Rule.class);
//                        if (rule != null) {
//                            selector = rule.getSelector();
//                        } else {
//                            selector = null;
//                        }
//                    } else {
//                        selector = null;
//                    }
//                    treeView.repaint();
//                    final PageModel pageModel = currentPageModel();
//                    if (pageModel != null) {
//                        RP.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                pageModel.setHighlightedSelector(selector);
//                            }
//                        });
//                    }
//                }
            }
        };
    }

    /**
     * Creates a cell renderer for the tree view.
     *
     * @param delegate delegating/original tree renderer.
     * @return call renderer for the tree view.
     */
    private TreeCellRenderer createTreeCellRenderer(final TreeCellRenderer delegate) {
        Color origColor = UIManager.getColor("Tree.selectionBackground"); // NOI18N
        Color color = origColor.brighter().brighter();
        if (color.equals(Color.WHITE)) { // Issue 217127
            color = origColor.darker();
        }
        // Color used for hovering highlight
        final Color hoverColor = color;
        return new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                JLabel component;
                if (!selected && (value == lastHover)) {
                    component = (JLabel) delegate.getTreeCellRendererComponent(tree, value, true, expanded, leaf, row, hasFocus);
                    component.setBackground(hoverColor);
                    component.setOpaque(true);
                } else {
                    component = (JLabel) delegate.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                }
                return component;
            }
        };
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
