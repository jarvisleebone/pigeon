package org.pigeon.config;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class RegistryConfig implements ApplicationListener {

    private String id;
    private String address;
    private int port;
    private String protocol;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (0 == PigeonConfig.interfaceNames.size()) return;




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

}
