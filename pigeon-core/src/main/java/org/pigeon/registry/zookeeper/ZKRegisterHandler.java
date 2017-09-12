/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.registry.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.pigeon.common.constant.Constant;
import org.pigeon.registry.RegisterHandler;

import java.util.List;
import java.util.Set;

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
    public void loadServices(Set<String> interfaceNames) {
        interfaceNames.stream().forEach((interfaceName) -> {
            // 从注册中心加载出提供该接口的所有服务端信息
            List<String> servers = zkClient.getChildren(Constant.ZKRootPath + Constant.ZKServicePath + Constant.PATH_SEPARATOR + interfaceName);
            RegisterHandler.services.put(interfaceName, servers);
        });
    }

    @Override
    public void registerService(String serviceAddress, Set<String> interfaceNames) {
        interfaceNames.stream().forEach((interfaceName) -> {
            String path = ZKUtil.toServicePath(interfaceName + Constant.PATH_SEPARATOR + serviceAddress);
            zkClient.delete(path);
            zkClient.createPersistent(path, true);
        });
    }
}