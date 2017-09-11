/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.registry.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.pigeon.common.constant.Constant;
import org.pigeon.registry.RegisterHandler;

/**
 * @author lixiang
 * @version $Id ZKRegisterHandler.java, v 0.1 2017-09-11 11:30 lixiang Exp $$
 */
public class ZKRegisterHandler implements RegisterHandler {

    private ZkClient zkClient;

    public ZKRegisterHandler(String ipAddress, int port) {
        zkClient = new ZkClient(ipAddress + ":" + port);
    }

    @Override
    public void loadServices() {

    }

    @Override
    public void registerService(String address, String serviceInterface) {
        String path = ZKUtil.toServicePath(serviceInterface + Constant.PATH_SEPARATOR + address);
        zkClient.delete(path);
        zkClient.createPersistent(path, true);
    }
}