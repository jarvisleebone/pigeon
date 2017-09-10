package org.pigeon.config.spring;

import org.pigeon.config.ClientConfig;
import org.pigeon.config.PigeonConfig;
import org.pigeon.config.RegistryConfig;
import org.pigeon.config.ServiceConfig;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class PigeonNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("client", new PigeonBeanDefinitionParser(ClientConfig.class));
        registerBeanDefinitionParser("registry", new PigeonBeanDefinitionParser(RegistryConfig.class));
        registerBeanDefinitionParser("config", new PigeonBeanDefinitionParser(PigeonConfig.class));
        registerBeanDefinitionParser("service", new PigeonBeanDefinitionParser(ServiceConfig.class));
    }
}
