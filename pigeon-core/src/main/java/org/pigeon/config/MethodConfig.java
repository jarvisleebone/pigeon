/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.config;

import org.pigeon.callback.PigeonCallback;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

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

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        PigeonConfig.methodConfigs.put(interfaceName + name, this);
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
}