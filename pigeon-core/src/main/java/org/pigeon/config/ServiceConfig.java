package org.pigeon.config;

import org.pigeon.common.util.EncryptUtil;
import org.pigeon.common.util.ReflectUtil;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ServiceConfig<T> implements ApplicationListener {

    private String id;
    private T ref;
    private Class<T> interfaceClass;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        PigeonConfig.serviceConfigs.put(interfaceClass.getName(), this);

        for (Method m : interfaceClass.getMethods()) {
            PigeonConfig.serviceMethods.put(ReflectUtil.getMethodSign(interfaceClass, m), m);
        }

    }

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
