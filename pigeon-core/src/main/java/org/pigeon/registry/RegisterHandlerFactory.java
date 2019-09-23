package org.pigeon.registry;

import org.pigeon.common.enums.RegistryProtocolEnum;
import org.pigeon.registry.zookeeper.ZKRegisterHandler;

public class RegisterHandlerFactory {

    private static RegisterHandler registerHandler;

    public static RegisterHandler getRegisterHandler(String protocol, String address, int port) {

        RegistryProtocolEnum protocolEnum = RegistryProtocolEnum.valueOf(protocol.toUpperCase());
        if (protocolEnum == RegistryProtocolEnum.ZOOKEEPER) {
            registerHandler = new ZKRegisterHandler(address, port);
        } else {
            registerHandler = new ZKRegisterHandler(address, port);
        }
        return registerHandler;
    }
}
