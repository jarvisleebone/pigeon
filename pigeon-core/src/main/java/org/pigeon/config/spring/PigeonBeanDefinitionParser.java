package org.pigeon.config.spring;

import org.pigeon.config.ClientConfig;
import org.pigeon.config.PigeonConfig;
import org.pigeon.config.RegistryConfig;
import org.pigeon.config.ServiceConfig;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class PigeonBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    private final Class<?> beanClass;

    public PigeonBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        builder.addPropertyValue("id", element.getAttribute("id"));

        if (PigeonConfig.class.equals(beanClass)) {
            builder.addPropertyValue("address", element.getAttribute("address"));
            builder.addPropertyValue("port", element.getAttribute("port"));
            builder.addPropertyValue("protocol", element.getAttribute("protocol"));
            builder.addPropertyValue("serializer", element.getAttribute("serializer"));
        }
        if (RegistryConfig.class.equals(beanClass)) {
            builder.addPropertyValue("address", element.getAttribute("address"));
            builder.addPropertyValue("port", element.getAttribute("port"));
            builder.addPropertyValue("protocol", element.getAttribute("protocol"));
        }
        if (ServiceConfig.class.equals(beanClass)) {
            String interfaceName = element.getAttribute("interface");
            PigeonConfig.interfaceNames.add(interfaceName);
            builder.addPropertyValue("interface", interfaceName);
            builder.addPropertyValue("ref", new RuntimeBeanReference(element.getAttribute("ref")));
        }
        if (ClientConfig.class.equals(beanClass)) {
            builder.addPropertyValue("interface", element.getAttribute("interface"));
        }
    }

    @Override
    protected Class<?> getBeanClass(Element element) {
        return beanClass;
    }

}
