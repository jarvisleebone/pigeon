/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.registry;

/**
 * @author lixiang
 * @version $Id RegisterHandler.java, v 0.1 2017-09-11 11:28 lixiang Exp $$
 */
public interface RegisterHandler {

    void registerService(String address, String serviceInterface);

    void loadServices();

}