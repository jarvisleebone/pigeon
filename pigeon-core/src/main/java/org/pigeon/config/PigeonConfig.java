package org.pigeon.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.util.ConcurrentHashSet;
import org.pigeon.rpc.RpcHandler;
import org.pigeon.rpc.RpcHandlerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.Set;

public class PigeonConfig implements ApplicationListener, BeanFactoryAware{

    public static final Set<String> interfaceNames = new ConcurrentHashSet<>();
    private String id;
    private String address;
    private int port;
    private String protocol;
    private String serializer;
    private RpcHandler rpcHandler;
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (StringUtils.isEmpty(address) || 0 == port) return;

        rpcHandler = RpcHandlerFactory.getRpcHandler(protocol);
        // 绑定端口
        rpcHandler.bindService(this);
        // 注册服务
        RegistryConfig registryConfig = beanFactory.getBean(RegistryConfig.class);
        rpcHandler.register(this, registryConfig, interfaceNames);
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
