/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 */

package org.netbeans.modules.debugger.jpda.models;

import com.sun.jdi.ArrayReference;
import com.sun.jdi.BooleanType;
import com.sun.jdi.ByteType;
import com.sun.jdi.CharType;
import com.sun.jdi.CharValue;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.ClassObjectReference;
import com.sun.jdi.ClassType;
import com.sun.jdi.DoubleType;
import com.sun.jdi.Field;
import com.sun.jdi.FloatType;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.IntegerType;
import com.sun.jdi.InternalException;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.InvocationException;
import com.sun.jdi.LongType;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.PrimitiveType;
import com.sun.jdi.PrimitiveValue;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ShortType;
import com.sun.jdi.StringReference;
import com.sun.jdi.Type;
import com.sun.jdi.Value;
import com.sun.jdi.VoidValue;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import java.beans.Customizer;
import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import org.netbeans.api.debugger.jpda.InvalidExpressionException;
import org.netbeans.api.debugger.jpda.JPDAClassType;
import org.netbeans.api.debugger.jpda.JPDAThread;
import org.netbeans.api.debugger.jpda.Variable;
import org.netbeans.modules.debugger.jpda.JPDADebuggerImpl;
import org.netbeans.modules.debugger.jpda.expr.EvaluatorVisitor;
import org.netbeans.modules.debugger.jpda.expr.JDIVariable;
import org.netbeans.modules.debugger.jpda.jdi.ArrayReferenceWrapper;
import org.netbeans.modules.debugger.jpda.jdi.ClassNotPreparedExceptionWrapper;
import org.netbeans.modules.debugger.jpda.jdi.ClassObjectReferenceWrapper;
import org.netbeans.modules.debugger.jpda.jdi.ClassTypeWrapper;
import org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper;
import org.netbeans.modules.debugger.jpda.jdi.MirrorWrapper;
import org.netbeans.modules.debugger.jpda.jdi.ObjectCollectedExceptionWrapper;
import org.netbeans.modules.debugger.jpda.jdi.ObjectReferenceWrapper;
import org.netbeans.modules.debugger.jpda.jdi.PrimitiveValueWrapper;
import org.netbeans.modules.debugger.jpda.jdi.ReferenceTypeWrapper;
import org.netbeans.modules.debugger.jpda.jdi.StringReferenceWrapper;
import org.netbeans.modules.debugger.jpda.jdi.TypeWrapper;
import org.netbeans.modules.debugger.jpda.jdi.UnsupportedOperationExceptionWrapper;
import org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper;
import org.netbeans.modules.debugger.jpda.jdi.ValueWrapper;
import org.netbeans.modules.debugger.jpda.jdi.VirtualMachineWrapper;
import org.openide.util.NbBundle;


/**
 * @author   Jan Jancura
 */
public class AbstractVariable implements JDIVariable, Customizer, Cloneable {
    // Customized for add/removePropertyChangeListener
    // Cloneable for fixed watches
    
    private Value   value;
    private JPDADebuggerImpl debugger;
    private String          id;
    
    private final Set<PropertyChangeListener> listeners = new HashSet<PropertyChangeListener>();

    
    public AbstractVariable (
        JPDADebuggerImpl debugger,
        Value value,
        String id
    ) {
        this.debugger = debugger;
        this.value = value;
        this.id = id;
        if (this.id == null) {
            this.id = Integer.toString(super.hashCode());
        }
    }

    
    // public interface ........................................................
    
    /**
    * Returns string representation of type of this variable.
    *
    * @return string representation of type of this variable.
    */
    @Override
    public String getValue () {
        Value v = getInnerValue ();
        return getValue(v);
    }
    
    static String getValue (Value v) {
        if (v == null) {
            return "null";
        }
        if (v instanceof VoidValue) {
            return "void";
        }
        if (v instanceof CharValue) {
            return "\'" + v.toString () + "\'";
        }
        if (v instanceof PrimitiveValue) {
            return v.toString ();
        }
        try {
            if (v instanceof StringReference) {
                String str = StringReferenceWrapper.value((StringReference) v);
                if (str.length() > AbstractObjectVariable.MAX_STRING_LENGTH) {
                    str = str.substring(0, AbstractObjectVariable.MAX_STRING_LENGTH) + "..."; // NOI18N
                }
                return "\"" + str + "\"";
            }
            if (v instanceof ClassObjectReference) {
                return "class " + ReferenceTypeWrapper.name(ClassObjectReferenceWrapper.reflectedType((ClassObjectReference) v));
            }
            if (v instanceof ArrayReference) {
                return "#" + ObjectReferenceWrapper.uniqueID((ArrayReference) v) +
                    "(length=" + ArrayReferenceWrapper.length((ArrayReference) v) + ")";
            }
            return "#" + ObjectReferenceWrapper.uniqueID((ObjectReference) v);
        } catch (InternalExceptionWrapper iex) {
            return "";
        } catch (ObjectCollectedExceptionWrapper oex) {
            return "";
        } catch (VMDisconnectedExceptionWrapper dex) {
            return "";
        }
    }

    /**
    * Sets string representation of value of this variable.
    *
    * @param value string representation of value of this variable.
    */
    public void setValue (String expression) throws InvalidExpressionException {
        String oldValue = getValue();
        if (expression.equals(oldValue)) {
            return ; // Do nothing, since the values are identical
        }
        Value value;
        Value oldV = getInnerValue();
        //ObjectReference valueToEnableCollectionOn = null;
        //try {
        try {
            if (oldV instanceof CharValue && expression.startsWith("'") && expression.endsWith("'") && expression.length() > 1) {
                value = VirtualMachineWrapper.mirrorOf(MirrorWrapper.virtualMachine(oldV), expression.charAt(1));
            } else if ((oldV instanceof StringReference || oldV == null) &&
                       expression.startsWith("\"") && expression.endsWith("\"") && expression.length() > 1) {
                value = VirtualMachineWrapper.mirrorOf(
                        debugger.getVirtualMachine(),
                        expression.substring(1, expression.length() - 1));
            } else if (oldV instanceof ObjectReference &&
                       ObjectReferenceWrapper.referenceType((ObjectReference) oldV) instanceof ClassType &&
                       ClassTypeWrapper.isEnum((ClassType) ObjectReferenceWrapper.referenceType((ObjectReference) oldV))) {
                ClassType enumType = (ClassType) ObjectReferenceWrapper.referenceType((ObjectReference) oldV);
                Field enumValue = ReferenceTypeWrapper.fieldByName(enumType, expression);
                if (enumValue != null) {
                    value = ReferenceTypeWrapper.getValue(enumType, enumValue);
                } else {
                    throw new InvalidExpressionException(expression);
                }
            } else if ("null".equals(expression)) {
                value = null;
            } else {
                // evaluate expression to Value
                Value evaluatedValue = debugger.evaluateIn (expression);
                /* Uncomment if evaluator returns a variable with disabled collection.
                   When not used any more, it's collection must be enabled again.
                if (evaluatedValue instanceof ObjectReference) {
                    valueToEnableCollectionOn = (ObjectReference) evaluatedValue;
                }*/
                if (oldV != null && evaluatedValue != null) {
                    Type type = ValueWrapper.type(oldV);
                    if (!type.equals(ValueWrapper.type(evaluatedValue))) {
                        evaluatedValue = convertValue(evaluatedValue, type);
                    }
                }
                value = evaluatedValue;
            }
        } catch (InternalExceptionWrapper e) {
            throw new InvalidExpressionException(e);
        } catch (ClassNotPreparedExceptionWrapper e) {
            throw new InvalidExpressionException(e);
        } catch (ObjectCollectedExceptionWrapper e) {
            throw new InvalidExpressionException(e);
        } catch (VMDisconnectedExceptionWrapper e) {
            return ;
        } catch (UnsupportedOperationExceptionWrapper e) {
            throw new InvalidExpressionException(e);
        }
        // set new value to remote veriable
        setValue (value);
        // set new value to this model
        setInnerValue (value);
        /*} finally {
            if (valueToEnableCollectionOn != null) {
                try {
                    ObjectReferenceWrapper.enableCollection(valueToEnableCollectionOn);
                } catch (Exception ex) {}
            }
        }*/
    }
    
    private Value convertValue(Value value, Type type) {
        if (type instanceof PrimitiveType) {
            if (value instanceof ObjectReference) {
                JPDAThread ct = getDebugger().getCurrentThread();
                if (ct != null) {
                    try {
                        value = EvaluatorVisitor.unbox((ObjectReference) value,
                                                       (PrimitiveType) type,
                                                       ((JPDAThreadImpl) ct).getThreadReference(),
                                                       null);
                    } catch (InvalidTypeException ex) {
                    } catch (ClassNotLoadedException ex) {
                    } catch (IncompatibleThreadStateException ex) {
                    } catch (InvocationException ex) {
                    }
                }
                boolean equalsType;
                try {
                    equalsType = ValueWrapper.type(value).equals(type);
                } catch (InternalExceptionWrapper ex) {
                    equalsType = true;
                } catch (VMDisconnectedExceptionWrapper ex) {
                    equalsType = true;
                } catch (ObjectCollectedExceptionWrapper ex) {
                    equalsType = true;
                }
                if (equalsType) {
                    return value;
                }
            }
            if (value instanceof PrimitiveValue) {
                PrimitiveValue pv = (PrimitiveValue) value;
                try {
                    VirtualMachine vm = MirrorWrapper.virtualMachine(pv);
                    if (type instanceof BooleanType) {
                        return VirtualMachineWrapper.mirrorOf(vm, PrimitiveValueWrapper.booleanValue(pv));
                    }
                    if (type instanceof ByteType) {
                        return VirtualMachineWrapper.mirrorOf(vm, PrimitiveValueWrapper.byteValue(pv));
                    }
                    if (type instanceof CharType) {
                        return VirtualMachineWrapper.mirrorOf(vm, PrimitiveValueWrapper.charValue(pv));
                    }
                    if (type instanceof ShortType) {
                        return VirtualMachineWrapper.mirrorOf(vm, PrimitiveValueWrapper.shortValue(pv));
                    }
                    if (type instanceof IntegerType) {
                        return VirtualMachineWrapper.mirrorOf(vm, PrimitiveValueWrapper.intValue(pv));
                    }
                    if (type instanceof LongType) {
                        return VirtualMachineWrapper.mirrorOf(vm, PrimitiveValueWrapper.longValue(pv));
                    }
                    if (type instanceof FloatType) {
                        return VirtualMachineWrapper.mirrorOf(vm, PrimitiveValueWrapper.floatValue(pv));
                    }
                    if (type instanceof DoubleType) {
                        return VirtualMachineWrapper.mirrorOf(vm, PrimitiveValueWrapper.doubleValue(pv));
                    }
                } catch (InternalExceptionWrapper e) {
                } catch (VMDisconnectedExceptionWrapper e) {
                }
            }
        }
        if (type instanceof ClassType && value instanceof PrimitiveValue) {
            JPDAThread ct = getDebugger().getCurrentThread();
            if (ct != null) {
                PrimitiveValue pv = (PrimitiveValue) value;
                try {
                    value = EvaluatorVisitor.box(pv,
                                                 (ReferenceType) type,
                                                 ((JPDAThreadImpl) ct).getThreadReference(),
                                                 null);
                } catch (InvalidTypeException ex) {
                } catch (ClassNotLoadedException ex) {
                } catch (IncompatibleThreadStateException ex) {
                } catch (InvocationException ex) {
                } catch (InternalException ex) {
                } catch (VMDisconnectedException ex) {
                }
            }
        }
        return value;
    }
    
    /**
     * Override, but do not call directly!
     */
    protected void setValue (Value value) throws InvalidExpressionException {
        throw new InternalError ();
    }
    
    @Override
    public void setObject(Object bean) {
        try {
            if (bean instanceof String) {
                setValue((String) bean);
            //} else if (bean instanceof Value) {
            //    setValue((Value) bean); -- do not call directly
            } else {
                throw new IllegalArgumentException(""+bean);
            }
        } catch (InvalidExpressionException ieex) {
            throw new IllegalArgumentException(ieex);
        }
    }

    /**
     * Declared type of this local.
     *
     * @return declared type of this local
     */
    @Override
    public String getType () {
        if (getInnerValue () == null) {
            return "";
        }
        try {
            return TypeWrapper.name(ValueWrapper.type(this.getInnerValue()));
        } catch (InternalExceptionWrapper ex) {
            return ex.getLocalizedMessage();
        } catch (VMDisconnectedExceptionWrapper vmdex) {
            // The session is gone.
            return NbBundle.getMessage(AbstractVariable.class, "MSG_Disconnected");
        } catch (ObjectCollectedExceptionWrapper ocex) {
            // The object is gone.
            return NbBundle.getMessage(AbstractVariable.class, "MSG_ObjCollected");
        }
    }
    
    public JPDAClassType getClassType() {
        Value value = getInnerValue();
        if (value == null) {
            return null;
        }
        com.sun.jdi.Type type;
        try {
            type = ValueWrapper.type(value);
        } catch (InternalExceptionWrapper ex) {
            return null;
        } catch (VMDisconnectedExceptionWrapper ex) {
            return null;
        } catch (ObjectCollectedExceptionWrapper ex) {
            return null;
        }
        if (type instanceof ReferenceType) {
            return new JPDAClassTypeImpl(debugger, (ReferenceType) type);
        } else {
            return null;
        }
    }
    
    @Override
    public boolean equals (Object o) {
        return  (o instanceof AbstractVariable) &&
                (id.equals (((AbstractVariable) o).id));
    }
    
    @Override
    public int hashCode () {
        return id.hashCode ();
    }

    
    // other methods............................................................
    
    protected Value getInnerValue () {
        return value;
    }
    
    protected void setInnerValue (Value v) {
        value = v;
        // refresh tree
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "value", null, v);
        Object[] ls;
        synchronized (listeners) {
            ls = listeners.toArray();
        }
        for (int i = 0; i < ls.length; i++) {
            ((PropertyChangeListener) ls[i]).propertyChange(evt);
        }
        debugger.varChangeSupport.firePropertyChange(evt);
        //pchs.firePropertyChange("value", null, value);
        //getModel ().fireTableValueChangedChanged (this, null);
    }
    
    @Override
    public Value getJDIValue() {
        return getInnerValue();
    }
    
    public final JPDADebuggerImpl getDebugger() {
        return debugger;
    }
    
    protected final String getID () {
        return id;
    }
    
    private int cloneNumber = 1;
    
    @Override
    public Variable clone() {
        AbstractVariable clon = new AbstractVariable(debugger, value, id + "_clone"+(cloneNumber++));
        return clon;
    }
    
    @Override
    public final void addPropertyChangeListener(PropertyChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }
    
    @Override
    public final void removePropertyChangeListener(PropertyChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }
    
    @Override
    public String toString () {
        return "Variable ";
    }
    
    /* Uncomment when needed. Was used to create "readable" String and Char values.
    private static String convertToStringInitializer (String s) {
        StringBuffer sb = new StringBuffer ();
        int i, k = s.length ();
        for (i = 0; i < k; i++)
            switch (s.charAt (i)) {
                case '\b':
                    sb.append ("\\b");
                    break;
                case '\f':
                    sb.append ("\\f");
                    break;
                case '\\':
                    sb.append ("\\\\");
                    break;
                case '\t':
                    sb.append ("\\t");
                    break;
                case '\r':
                    sb.append ("\\r");
                    break;
                case '\n':
                    sb.append ("\\n");
                    break;
                case '\"':
                    sb.append ("\\\"");
                    break;
                default:
                    sb.append (s.charAt (i));
            }
        return sb.toString();
    }
    
    private static String convertToCharInitializer (String s) {
        StringBuffer sb = new StringBuffer ();
        int i, k = s.length ();
        for (i = 0; i < k; i++)
            switch (s.charAt (i)) {
                case '\b':
                    sb.append ("\\b");
                    break;
                case '\f':
                    sb.append ("\\f");
                    break;
                case '\\':
                    sb.append ("\\\\");
                    break;
                case '\t':
                    sb.append ("\\t");
                    break;
                case '\r':
                    sb.append ("\\r");
                    break;
                case '\n':
                    sb.append ("\\n");
                    break;
                case '\'':
                    sb.append ("\\\'");
                    break;
                default:
                    sb.append (s.charAt (i));
            }
        return sb.toString();
    }
     */
    
}

