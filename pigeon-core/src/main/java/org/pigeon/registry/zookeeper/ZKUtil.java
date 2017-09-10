package org.pigeon.registry.zookeeper;

import org.pigeon.common.constant.Constant;

public class ZKUtil {

    public static String toServicePath(String serviceUrl) {
        return Constant.ZKRootPath + Constant.ZKServicePath + Constant.PATH_SEPARATOR + serviceUrl;
    }

}
