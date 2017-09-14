package org.pigeon.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.util.ConcurrentHashSet;
import org.pigeon.callback.PigeonCallback;
import org.pigeon.config.handler.ConfigHandler;
import org.pigeon.registry.RegisterHandler;
import org.pigeon.rpc.RpcHandler;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PigeonConfig implements ApplicationListener{

    // 服务端提供的接口配置
    public static final Map<String, ServiceConfig> serviceConfigs = new ConcurrentHashMap<>();
    // 服务端提供的接口名字集合
    public static final Set<String> serviceInterfaceNames = new ConcurrentHashSet<>();
    // 客户端使用的接口集合
    public static final Map<String, String> clientInterfaces = new ConcurrentHashMap<>();
    private String id;
    private String address;
    private int port;
    private String protocol;
    private String serializer;
    private String route;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        RpcHandler rpcHandler = ConfigHandler.rpcHandler;
        RegisterHandler registerHandler = ConfigHandler.registerHandler;

        if (StringUtils.isNotEmpty(address) && 0 != port && 0 != serviceInterfaceNames.size()) {
            // 绑定端口
            rpcHandler.bindService(port);
            // 注册服务
            registerHandler.registerService(address + ":" + port, serviceInterfaceNames);
        }
        if (0 != clientInterfaces.size()) {
            registerHandler.loadServices(clientInterfaces.keySet());
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

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
