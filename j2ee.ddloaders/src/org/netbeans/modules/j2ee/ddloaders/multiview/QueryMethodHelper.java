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

package org.netbeans.modules.j2ee.ddloaders.multiview;

import org.netbeans.modules.j2ee.dd.api.ejb.MethodParams;
import org.netbeans.modules.j2ee.dd.api.ejb.Query;
import org.netbeans.modules.j2ee.dd.api.ejb.QueryMethod;
import org.netbeans.modules.j2ee.common.JMIUtils;
import org.netbeans.jmi.javamodel.Method;
import org.netbeans.jmi.javamodel.JavaClass;
import org.netbeans.jmi.javamodel.Type;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * @author pfiala
 */
public class QueryMethodHelper {

    final Query query;
    private final EntityHelper entityHelper;
    private boolean isSelectMethod;
    private Method implementationMethod;
    public Method remoteMethod;
    public Method localMethod;

    public QueryMethodHelper(EntityHelper helper, Query query) {
        this.query = query;
        this.entityHelper = helper;
        isSelectMethod = query.getQueryMethod().getMethodName().startsWith(EntityHelper.Queries.SELECT_PREFIX);
        init();
    }

    protected void init() {
        QueryMethod queryMethod = query.getQueryMethod();
        List parameters = getQueryMethodParams(queryMethod);
        String methodName = queryMethod.getMethodName();
        implementationMethod = entityHelper.getBeanClass().getMethod(methodName, parameters, false);
        remoteMethod = getMethod(entityHelper.getHomeInterfaceClass(), methodName, parameters);
        localMethod = getMethod(entityHelper.getLocalHomeInterfaceClass(), methodName, parameters);
    }

    private static Method getMethod(JavaClass javaClass, String methodName, List parameters) {
        return javaClass == null ? null : javaClass.getMethod(methodName, parameters, false);
    }

    public String getMethodName() {
        return query.getQueryMethod().getMethodName();
    }

    public String getReturnType() {
        JMIUtils.beginJmiTransaction();
        try {
            Method method = getImplementationMethod();
            if (method != null) {
                Type type = method.getType();
                if (type == null) {
                    return null;
                } else {
                    return type.getName();
                }
            }
            return null;
        } finally {
            JMIUtils.endJmiTransaction();
        }
    }

    public boolean returnsCollection() {
        return "java.util.Collection".equals(getReturnType());//NOI18N
    }

    public String getResultInterface() {
        JavaClass localHomeClass = entityHelper.getLocalHomeInterfaceClass();
        JavaClass homeClass = entityHelper.getHomeInterfaceClass();

        QueryMethod queryMethod = query.getQueryMethod();
        List parameters = getQueryMethodParams(queryMethod);
        String methodName = queryMethod.getMethodName();
        boolean hasLocal = localHomeClass == null ?
                false : localHomeClass.getMethod(methodName, parameters, false) != null;
        boolean hasRemote = homeClass == null ?
                false : homeClass.getMethod(methodName, parameters, false) != null;
        String remote = "remote"; //NOI18N;
        String local = "local"; //NOI18N;
        if (hasLocal) {
            if (hasRemote) {
                return local + "+" + remote; //NOI18N;
            } else {
                return local;
            }
        } else {
            if (hasRemote) {
                return remote;
            } else {
                return "-"; //NOI18N;
            }
        }
    }

    private List getQueryMethodParams(QueryMethod queryMethod) {
        String[] methodParam = queryMethod.getMethodParams().getMethodParam();
        List params = new LinkedList();
        for (int i = 0; i < methodParam.length; i++) {
            params.add(JMIUtils.resolveType(methodParam[i]));
        }
        return params;
    }

    public void removeQuery() {
        init();
        Utils.removeMethod(entityHelper.getBeanClass(), implementationMethod);
        Utils.removeMethod(entityHelper.getLocalHomeInterfaceClass(), localMethod);
        Utils.removeMethod(entityHelper.getHomeInterfaceClass(), remoteMethod);
        entityHelper.removeQuery(query);
    }

    public Method getPrototypeMethod() {
        Method implementationMethod = getImplementationMethod();
        if (implementationMethod == null) {
            Method prototypeMethod = JMIUtils.createMethod(null);
            QueryMethod queryMethod = query.getQueryMethod();
            prototypeMethod.setName(queryMethod.getMethodName());
            MethodParams queryParams = queryMethod.getMethodParams();
            List parameters = prototypeMethod.getParameters();
            for (int i = 0, n = queryParams.sizeMethodParam(); i < n; i++) {
                parameters.add(JMIUtils.createParameter(prototypeMethod, "p" + i,
                        JMIUtils.resolveType(queryParams.getMethodParam(i)), false));
            }
            return prototypeMethod;
        } else {
            return (Method) implementationMethod.duplicate();

        }
    }

    public Method getImplementationMethod() {
        Method prototypeMethod = null;
        if (isSelectMethod) {
            //select method
            prototypeMethod = implementationMethod;
        } else {
            //finder method
            if (localMethod != null) {
                prototypeMethod = localMethod;
            } else if (remoteMethod != null) {
                prototypeMethod = remoteMethod;
            }
        }
        return prototypeMethod;
    }

    public void updateFinderMethod(Method prototype, Query query, boolean singleReturn, boolean publishToLocal,
            boolean publishToRemote) {
        //todo: validation
        prototype.setModifiers(0);
        if (publishToLocal) {
            localMethod = setMethod(localMethod, prototype, singleReturn, false);
        } else {
            localMethod = removeMethod(localMethod, false);
        }
        if (publishToRemote) {
            remoteMethod = setMethod(remoteMethod, prototype, singleReturn, true);
        } else {
            remoteMethod = removeMethod(remoteMethod, true);
        }
        updateQuery(query);
    }

    private void updateQuery(Query query) {
        this.query.setQueryMethod(query.getQueryMethod());
        this.query.setDescription(query.getDefaultDescription());
        this.query.setEjbQl(query.getEjbQl());
        entityHelper.modelUpdatedFromUI();
    }

    private Method setMethod(Method method, Method prototype, boolean singleReturn,
            boolean remote) {
        JavaClass interfaceClass = getHomeClass(remote);
        setReturn(prototype, singleReturn, remote);
        if (method == null) {
            Utils.addMethod(interfaceClass, (Method) prototype.duplicate(), remote);
            method = Utils.getMethod(interfaceClass, prototype);
        } else {
            updateMethod(method, prototype);
        }
        return method;
    }

    private Method removeMethod(Method method, boolean remote) {
        Utils.removeMethod(getHomeClass(remote), method);
        return null;
    }

    private JavaClass getHomeClass(boolean remote) {
        JavaClass interfaceClass = remote ?
                entityHelper.getHomeInterfaceClass() : entityHelper.getLocalHomeInterfaceClass();
        return interfaceClass;
    }

    private void setReturn(Method prototype, boolean singleReturn, boolean remote) {
        String interfaceName = remote ? entityHelper.getRemote() : entityHelper.getLocal();
        String typeName = singleReturn ? interfaceName : Collection.class.getName();
        prototype.setType(JMIUtils.resolveType(typeName));
    }

    public void updateSelectMethod(Method prototype, Query query) {
        //todo: validation
        prototype.setModifiers(Modifier.PUBLIC | Modifier.ABSTRACT);
        if (implementationMethod == null) {
            Utils.addMethod(entityHelper.getBeanClass(), prototype);
            implementationMethod = prototype;
        } else {
            updateMethod(implementationMethod, prototype);
        }
        updateQuery(query);
    }

    private static void updateMethod(Method method, Method prototype) {
        if (method != null) {
            method.setName(prototype.getName());
            method.setType(prototype.getType());
            final List newParameters = prototype.getParameters();
            JMIUtils.replaceParameters(method, newParameters);
            for (Iterator it = prototype.getExceptions().iterator(); it.hasNext();) {
                JMIUtils.addException(method, ((JavaClass) it.next()).getName());
            }
            method.setModifiers(prototype.getModifiers());
        }
    }

    public QueryMethod getQueryMethod() {
        return query.getQueryMethod();
    }

    public String getEjbQl() {
        return query.getEjbQl();
    }

    public String getDefaultDescription() {
        return query.getDefaultDescription();
    }
}
