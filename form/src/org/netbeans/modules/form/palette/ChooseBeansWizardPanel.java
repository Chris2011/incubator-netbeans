/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2004 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.form.palette;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.*;
import java.io.File;
import java.util.*;
import javax.swing.event.*;
import java.awt.event.*;

import org.openide.WizardDescriptor;
import org.openide.ErrorManager;

/**
 * The second panel in the wizard for adding new components to the palette.
 * Lets the user choose components from a list of all available components
 * in selected source.
 *
 * @author Tomas Pavek
 */

class ChooseBeansWizardPanel implements WizardDescriptor.Panel {

    private File[] currentFiles; // roots (typically JAR files) chosen by the user
    private Map libraryNameMap; // map from root file names to library names

    private java.util.List markedBeans; // beans marked in JAR manifest
    private java.util.List allBeans; // all bean classes under given roots

    private BeanSelector beanSelector;

    private EventListenerList listenerList;

    // ----------
    // WizardDescriptor.Panel implementation

    public Component getComponent() {
        if (beanSelector == null) { // create the UI component for the wizard step
            beanSelector = new BeanSelector();

            // wizard API: set the caption and index of this panel
            beanSelector.setName(PaletteUtils.getBundleString("CTL_SelectBeans_Caption")); // NOI18N
            beanSelector.putClientProperty("WizardPanel_contentSelectedIndex", // NOI18N
                                           new Integer(1));
            if (markedBeans != null || allBeans != null)
                beanSelector.setBeans(markedBeans, allBeans);

            Listener listener = new Listener();
            beanSelector.list.addListSelectionListener(listener);
            beanSelector.radio1.addActionListener(listener);
            beanSelector.radio2.addActionListener(listener);
        }

        return beanSelector;
    }

    public org.openide.util.HelpCtx getHelp() {
        // PENDING
        return new org.openide.util.HelpCtx("beans.adding"); // NOI18N
    }

    public boolean isValid() {
        return beanSelector != null && beanSelector.getSelectedBeans().size() > 0;
    }

    public void readSettings(Object settings) {
        AddToPaletteWizard wizard = (AddToPaletteWizard) settings;
        File[] jarFiles = wizard.getJARFiles();

        if (currentFiles != null && currentFiles.length == jarFiles.length)
            for (int i=0; i < jarFiles.length; i++)
                if (jarFiles[i].equals(currentFiles[i])) {
                    if (i+1 == jarFiles.length)
                        return;  // no change from the last time
                }
                else break;

        currentFiles = jarFiles;
        libraryNameMap = wizard.libraryNameMap;

        allBeans = null; // don't read all the beans until needed
        markedBeans = BeanInstaller.findJavaBeansInJar(jarFiles);
        if (markedBeans != null) {
            if (libraryNameMap != null) // need to change root file names to library names
                remapLibraryNames(markedBeans, libraryNameMap);
            Collections.sort(markedBeans);
        }
        else {
            allBeans = BeanInstaller.findJavaBeans(jarFiles);
            if (libraryNameMap != null) // need to change root file names to library names
                remapLibraryNames(allBeans, libraryNameMap);
            Collections.sort(allBeans);
        }

        if (beanSelector != null)
            beanSelector.setBeans(markedBeans, allBeans);
    }

    public void storeSettings(Object settings) {
        if (beanSelector != null) {
            java.util.List itemList = beanSelector.getSelectedBeans();
            BeanInstaller.ItemInfo[] itemArray =
                new BeanInstaller.ItemInfo[itemList.size()];
            itemList.toArray(itemArray);
            ((AddToPaletteWizard)settings).setSelectedBeans(itemArray);
        }
    }

    public void addChangeListener(ChangeListener listener) {
        if (listenerList == null)
            listenerList = new EventListenerList();
        listenerList.add(ChangeListener.class, listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        if (listenerList != null)
            listenerList.remove(ChangeListener.class, listener);
    }

    // -----

    void fireStateChanged() {
        if (listenerList == null)
            return;

        ChangeEvent e = null;
        Object[] listeners = listenerList.getListenerList();
        for (int i=listeners.length-2; i >= 0; i-=2) {
            if (listeners[i] == ChangeListener.class) {
                if (e == null)
                    e = new ChangeEvent(this);
                ((ChangeListener)listeners[i+1]).stateChanged(e);
            }
        }
    }

    private static void remapLibraryNames(List beans, Map map) {
        for (int i=0, n=beans.size(); i < n; i++) {
            BeanInstaller.ItemInfo ii = (BeanInstaller.ItemInfo) beans.get(i);
            ii.source = (String) map.get(ii.source);
        }
    }

    // -------

    static class BeanSelector extends JPanel {

        JList list;
        JRadioButton radio1, radio2;

        BeanSelector() {
            setLayout(new java.awt.GridBagLayout());
            java.awt.GridBagConstraints gridBagConstraints;

            JLabel label1 = new JLabel();
            org.openide.awt.Mnemonics.setLocalizedText(
                label1, PaletteUtils.getBundleString("CTL_SelectBeans")); // NOI18N
            label1.setLabelFor(list);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.insets = new java.awt.Insets(0, 0, 6, 0);
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            add(label1, gridBagConstraints);

            list = new JList();
            list.setLayoutOrientation(JList.VERTICAL_WRAP);
            list.setVisibleRowCount(0);
            list.setCellRenderer(new ItemInfoRenderer());
            list.getAccessibleContext().setAccessibleDescription(
                PaletteUtils.getBundleString("ACSD_CTL_SelectBeans")); // NOI18N

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            scrollPane.setViewportView(list);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            add(scrollPane, gridBagConstraints);

            radio1 = new JRadioButton();
            radio1.setActionCommand("SHOW MARKED"); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(
                radio1, PaletteUtils.getBundleString("CTL_ShowMarked")); // NOI18N
            radio1.setToolTipText(PaletteUtils.getBundleString("HINT_ShowMarked")); // NOI18N
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
            add(radio1, gridBagConstraints);
            // PENDING A11Y

            radio2 = new JRadioButton();
            radio2.setActionCommand("SHOW ALL"); // NOI18N
            org.openide.awt.Mnemonics.setLocalizedText(
                radio2, PaletteUtils.getBundleString("CTL_ShowAllClasses")); // NOI18N
            radio2.setToolTipText(PaletteUtils.getBundleString("HINT_ShowAllClasses")); // NOI18N
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
            add(radio2, gridBagConstraints);
            // PENDING A11Y

            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(radio1);
            buttonGroup.add(radio2);

            getAccessibleContext().setAccessibleDescription(
                PaletteUtils.getBundleString("ACSD_SelectBeansDialog")); // NOI18N
        }

        void setBeans(List markedBeans, List allBeans) {
            if (markedBeans == null) {
                radio1.setEnabled(false);
            }
            else {
                radio1.setEnabled(true);
                radio1.setSelected(true);
                setDisplayedBeans(markedBeans);
            }
            if (allBeans != null && markedBeans == null) {
                radio2.setSelected(true);
                setDisplayedBeans(allBeans);
            }
        }

        /** @param list of BeanInstaller.ItemInfo  */
        void setDisplayedBeans(final List beans) {
            list.setModel(new AbstractListModel() {
                public int getSize() { return beans.size(); }
                public Object getElementAt(int i) { return beans.get(i); }
            });
        }

        /** @return list of BeanInstaller.ItemInfo */
        List getSelectedBeans() {
            Object[] sel = list.getSelectedValues();
            List al = new ArrayList(sel.length);
            for (int i = 0; i < sel.length; i++)
                al.add(sel[i]);
            return al;
        }

        public Dimension getPreferredSize() {
            return new Dimension(400, 300);
        }
    }

    // --------

    private static class ItemInfoRenderer extends JLabel
                                               implements ListCellRenderer
    {
        private static final Border hasFocusBorder =
            new LineBorder(UIManager.getColor("List.focusCellHighlight")); // NOI18N
        private static final Border noFocusBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);

        public ItemInfoRenderer() {
            setOpaque(true);
            setBorder(noFocusBorder);
        }

        public Component getListCellRendererComponent(JList list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus)
        {
            String name = ((BeanInstaller.ItemInfo)value).classname;
            setToolTipText(name); // full class name as tooltip

            int i = name.lastIndexOf('.');
            if (i >= 0)
                name = name.substring(i+1);

            setText(name); // short class name as the label text

            if (isSelected){
                setBackground(UIManager.getColor("List.selectionBackground")); // NOI18N
                setForeground(UIManager.getColor("List.selectionForeground")); // NOI18N
            }
            else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setBorder(cellHasFocus ? hasFocusBorder : noFocusBorder);
            return this;
        }
    }

    // -------

    class Listener implements ListSelectionListener, ActionListener {

        public void valueChanged(ListSelectionEvent e) {
            fireStateChanged();
        }

        public void actionPerformed(ActionEvent ev) {
            if ("SHOW MARKED".equals(ev.getActionCommand())) { // NOI18N
                beanSelector.setDisplayedBeans(markedBeans);
            }
            else if ("SHOW ALL".equals(ev.getActionCommand())) { // NOI18N
                if (allBeans == null) { // not read yet
                    // PENDING wait cursor
                    allBeans = BeanInstaller.findJavaBeans(currentFiles);
                    if (libraryNameMap != null) { // need to change root file names to library names
                        remapLibraryNames(allBeans, libraryNameMap);
                        libraryNameMap = null;
                    }
                    Collections.sort(allBeans);
                }
                beanSelector.setDisplayedBeans(allBeans);
            }
        }
    }
}
