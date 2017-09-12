/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.router;

import java.util.List;

/**
 * @author lixiang
 * @version $Id RandomRouter.java, v 0.1 2017-09-12 9:36 lixiang Exp $$
 */
public class RandomRouter implements Router {

    @Override
    public String elect(List<String> servers) {
        // TODO 随机路由选举
        return servers.get(0);
    }
}