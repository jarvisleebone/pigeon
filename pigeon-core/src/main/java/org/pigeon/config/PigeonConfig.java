package org.pigeon.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.util.ConcurrentHashSet;
import org.pigeon.config.handler.ConfigHandler;
import org.pigeon.registry.RegisterHandler;
import org.pigeon.rpc.RpcHandler;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PigeonConfig implements ApplicationListener{

    // 服务端提供的所有接口名字，会写入注册中心
    public static final Set<String> serverInterfaceNames = new ConcurrentHashSet<>();
    // 客户端需要的所有接口名字
    public static final Set<String> clientInterfaceNames = new ConcurrentHashSet<>();
    private String id;
    private String address;
    private int port;
    private String protocol;
    private String serializer;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        RpcHandler rpcHandler = ConfigHandler.rpcHandler;
        RegisterHandler registerHandler = ConfigHandler.registerHandler;

        if (StringUtils.isNotEmpty(address) && 0 != port && 0 != serverInterfaceNames.size()) {
            // 绑定端口
            rpcHandler.bindService(port);
            // 注册服务
            registerHandler.registerService(address + ":" + port, serverInterfaceNames);
        }
        if (0 != clientInterfaceNames.size()) {
            registerHandler.loadServices(clientInterfaceNames);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }
}
