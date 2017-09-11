package org.pigeon.rpc;

import org.pigeon.common.enums.RegistryProtocolEnum;
import org.pigeon.config.PigeonConfig;
import org.pigeon.config.RegistryConfig;
import org.pigeon.model.PigeonRequest;
import org.pigeon.registry.RegisterHandler;
import org.pigeon.registry.zookeeper.ZKRegisterHandler;

import java.util.Set;

public abstract class RpcHandler {

    private RegisterHandler registerHandler;

    public void register(PigeonConfig pigeonConfig, RegistryConfig registryConfig, Set<String> interfaceNames) {
        RegistryProtocolEnum registryProtocol = RegistryProtocolEnum.valueOf(registryConfig.getProtocol().toUpperCase());
        switch (registryProtocol) {
            case ZOOKEEPER:
                registerHandler = new ZKRegisterHandler(registryConfig.getAddress(), registryConfig.getPort());
                break;
            default:
                break;
        }

        String serviceAddress = pigeonConfig.getAddress() + ":" + pigeonConfig.getPort();
        interfaceNames.stream().forEach((interfaceName) -> registerHandler.registerService(serviceAddress, interfaceName));
    }

    public abstract void bindService(PigeonConfig pigeonConfig);

    public abstract Object sendMessage(PigeonRequest request);

}
