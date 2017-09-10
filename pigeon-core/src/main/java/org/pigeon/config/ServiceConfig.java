package org.pigeon.config;

public class ServiceConfig<T>{

    private String id;
    private T ref;
    private Class<T> interfaceClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public Class<T> getInterface() {
        return interfaceClass;
    }

    public void setInterface(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

}
