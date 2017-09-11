package org.pigeon.rpc;

import org.pigeon.common.enums.RegistryProtocolEnum;
import org.pigeon.config.PigeonConfig;
import org.pigeon.config.RegistryConfig;
import org.pigeon.registry.ServiceRegister;
import org.pigeon.registry.zookeeper.ZKServiceRegister;

import java.util.Set;

public abstract class RpcHandler<T> {

    private ServiceRegister serviceRegister;

    public void register(PigeonConfig pigeonConfig, RegistryConfig registryConfig, Set<String> interfaceNames) {
        RegistryProtocolEnum registryProtocol = RegistryProtocolEnum.valueOf(registryConfig.getProtocol().toUpperCase());
        switch (registryProtocol) {
            case ZOOKEEPER:
                serviceRegister = new ZKServiceRegister(registryConfig.getAddress(), registryConfig.getPort());
                break;
            default:
                break;
        }

        String serviceAddress = pigeonConfig.getAddress() + ":" + pigeonConfig.getPort();
        interfaceNames.stream().forEach((interfaceName) -> serviceRegister.register(serviceAddress, interfaceName));
    }

    public abstract void bindService(PigeonConfig pigeonConfig);

}
