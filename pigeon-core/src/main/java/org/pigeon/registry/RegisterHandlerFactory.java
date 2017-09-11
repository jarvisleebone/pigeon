package org.pigeon.registry;

import org.pigeon.common.enums.RegistryProtocolEnum;
import org.pigeon.registry.zookeeper.ZKRegisterHandler;

public class RegisterHandlerFactory {

    private static RegisterHandler registerHandler;

    public static RegisterHandler getRegisterHandler(String protocol, String address, int port) {

        RegistryProtocolEnum protocolEnum = RegistryProtocolEnum.valueOf(protocol.toUpperCase());
        switch (protocolEnum) {
            case ZOOKEEPER:
                registerHandler = new ZKRegisterHandler(address, port);
                break;
            default:
                registerHandler = new ZKRegisterHandler(address, port);
        }
        return registerHandler;
    }
}
