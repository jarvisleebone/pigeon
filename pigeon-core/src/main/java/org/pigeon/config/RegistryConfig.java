package org.pigeon.config;

import java.io.Serializable;

public class RegistryConfig implements Serializable {

    private static final long serialVersionUID = 470591081823416394L;
    private String id;
    private String address;
    private int port;
    private String protocol;

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
