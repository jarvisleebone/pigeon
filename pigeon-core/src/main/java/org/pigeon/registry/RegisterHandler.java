/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.registry;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiang
 * @version $Id RegisterHandler.java, v 0.1 2017-09-11 11:28 lixiang Exp $$
 */
public interface RegisterHandler {

    /**
     * key：客户端需要调用的服务接口
     * value：提供该服务接口的所有服务端信息（ip:port）
     */
    Map<String, List<String>> services = new ConcurrentHashMap<>();

    void registerService(String serviceAddress, Set<String> interfaceNames);

    void loadServices(Set<String> interfaceNames);

}