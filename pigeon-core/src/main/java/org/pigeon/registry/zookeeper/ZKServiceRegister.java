package org.pigeon.registry.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.pigeon.common.constant.Constant;
import org.pigeon.registry.ServiceRegister;

public class ZKServiceRegister implements ServiceRegister {

    private ZkClient zkClient;

    public ZKServiceRegister(String ipAddress, int port) {
        zkClient = new ZkClient(ipAddress + ":" + port);
    }

    @Override
    public void register(String address, String serviceInterface){
        String path = ZKUtil.toServicePath(serviceInterface + Constant.PATH_SEPARATOR + address);
        zkClient.delete(path);
        zkClient.createPersistent(path, true);
    }

}
