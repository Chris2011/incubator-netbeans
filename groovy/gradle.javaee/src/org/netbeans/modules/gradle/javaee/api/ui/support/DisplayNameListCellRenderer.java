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

package org.netbeans.modules.gradle.javaee.api.ui.support;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Laszlo Kishalmi
 */
public class DisplayNameListCellRenderer<T> implements ListCellRenderer<T>{
    final ListCellRenderer<T> delegate;

    public DisplayNameListCellRenderer(ListCellRenderer<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus) {
        Component comp = delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value != null && comp instanceof JLabel) {
            JLabel label = (JLabel) comp;
            try {
                Method getDisplayName = value.getClass().getMethod("getDisplayName"); //NOI18N
                Object dn = getDisplayName.invoke(value);
                if (dn != null) {
                    label.setText(dn.toString());
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
        return comp;
    }

}
