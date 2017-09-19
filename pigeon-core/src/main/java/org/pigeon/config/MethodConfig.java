/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.config;

import org.pigeon.callback.PigeonCallback;
import org.pigeon.common.util.EncryptUtil;
import org.pigeon.common.util.ReflectUtil;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author lixiang
 * @version $Id MethodConfig.java, v 0.1 2017-09-15 11:11 lixiang Exp $$
 */
public class MethodConfig implements ApplicationListener {

    private String id;
    private String interfaceName;
    private String name;
    private boolean sync;
    private PigeonCallback callback;
    private Map<Integer, String> paramterTypes = new TreeMap<>();

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        try {
            if (0 == paramterTypes.size()) {
                Class clazz = Class.forName(interfaceName);
                for (Method method : clazz.getMethods()) {
                    if (name.equals(method.getName())) {
                        String methodSign = ReflectUtil.getMethodSign(clazz, method);
                        PigeonConfig.methodConfigs.put(methodSign, this);
                    }
                }
            } else {
                String methodSign = ReflectUtil.getMethodSign(interfaceName, name, paramterTypes.values().toArray());
                PigeonConfig.methodConfigs.put(methodSign, this);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>callback</tt>.
     *
     * @return property value of callback
     */
    public PigeonCallback getCallback() {
        return callback;
    }

    /**
     * Setter method for property <tt>callback</tt>.
     *
     * @param callback value to be assigned to property callback
     */
    public void setCallback(PigeonCallback callback) {
        this.callback = callback;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    /**
     * Getter method for property <tt>interfaceName</tt>.
     *
     * @return property value of interfaceName
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * Setter method for property <tt>interfaceName</tt>.
     *
     * @param interfaceName value to be assigned to property interfaceName
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * Getter method for property <tt>paramterTypes</tt>.
     *
     * @return property value of paramterTypes
     */
    public Map<Integer, String> getParamterTypes() {
        return paramterTypes;
    }

    /**
     * Setter method for property <tt>paramterTypes</tt>.
     *
     * @param paramterTypes value to be assigned to property paramterTypes
     */
    public void setParamterTypes(Map<Integer, String> paramterTypes) {
        this.paramterTypes = paramterTypes;
    }
}