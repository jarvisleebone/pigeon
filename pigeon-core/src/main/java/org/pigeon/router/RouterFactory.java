/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.router;

import org.pigeon.common.enums.RouteProtocolEnum;

/**
 * @author lixiang
 * @version $Id RouterFactory.java, v 0.1 2017-09-12 9:34 lixiang Exp $$
 */
public class RouterFactory {

    private static Router router;

    public static Router getRouter(String route){

        RouteProtocolEnum routeProtocolEnum = RouteProtocolEnum.valueOf(route.toUpperCase());
        switch (routeProtocolEnum) {
            case RANDOM:
                router = new RandomRouter();
                break;
            default:
                router = new RandomRouter();
                break;
        }
        return router;
    }
}