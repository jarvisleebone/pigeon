/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.router;

import org.apache.commons.lang3.RandomUtils;
import org.pigeon.callback.PigeonCallback;
import org.pigeon.common.enums.RouteProtocolEnum;
import org.pigeon.exception.PigeonException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiang
 * @version $Id RouterFactory.java, v 0.1 2017-09-12 9:34 lixiang Exp $$
 */
public class RouterFactory {

    private static Router router;

    private static Map<String, Integer> currentServerIndex = new ConcurrentHashMap<>();

    public static Router getRouter(String route) {
        RouteProtocolEnum routeProtocolEnum = RouteProtocolEnum.valueOf(route.toUpperCase());
        switch (routeProtocolEnum) {
            case DIRECT: // 连第一个
                router = RouterFactory::direct;
                break;
            case RANDOM: // 随机
                router = RouterFactory::random;
                break;
            case RR: // 轮询
                router = RouterFactory::rr;
                break;
            case WRR: // 加权轮询
                router = RouterFactory::wrr;
                break;
            default:
                router = RouterFactory::rr;
                break;
        }
        return router;
    }

    private static String direct(List<String> servers, String interfaceName) throws PigeonException {
        if (null == servers || 0 == servers.size())
            throw new PigeonException(interfaceName + "：该接口无可用服务端");
        return servers.get(0);
    }

    private static String random(List<String> servers, String interfaceName) throws PigeonException {
        if (null == servers || 0 == servers.size())
            throw new PigeonException(interfaceName + "：该接口无可用服务端");
        return servers.get(RandomUtils.nextInt(0, servers.size()));
    }

    private static String rr(List<String> servers, String interfaceName) throws PigeonException {
        if (null == servers || 0 == servers.size())
            throw new PigeonException(interfaceName + "：该接口无可用服务端");
        Integer index = currentServerIndex.get(interfaceName);
        if (null == index || ++index >= servers.size()) {
            currentServerIndex.put(interfaceName, 0);
            return servers.get(0);
        }
        currentServerIndex.put(interfaceName, index);
        return servers.get(index);
    }

    private static String wrr(List<String> servers, String interfaceName) throws PigeonException {
        // TODO 加权轮询
        return null;
    }
}