package org.pigeon.config.spring;

import org.apache.commons.lang3.StringUtils;
import org.pigeon.codec.SerializerFactory;
import org.pigeon.common.enums.RegistryProtocolEnum;
import org.pigeon.common.enums.RouteProtocolEnum;
import org.pigeon.common.enums.RpcProtocolEnum;
import org.pigeon.common.enums.SerializerProtocolEnum;
import org.pigeon.config.ClientConfig;
import org.pigeon.config.PigeonConfig;
import org.pigeon.config.RegistryConfig;
import org.pigeon.config.ServiceConfig;
import org.pigeon.config.handler.ConfigHandler;
import org.pigeon.registry.RegisterHandlerFactory;
import org.pigeon.router.RouterFactory;
import org.pigeon.rpc.RpcHandlerFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
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
            String address = element.getAttribute("address");
            String port = element.getAttribute("port");
            String protocol = element.getAttribute("protocol");
            String serializer = element.getAttribute("serializer");
            String route = element.getAttribute("route");

            if (StringUtils.isEmpty(protocol))
                protocol = RpcProtocolEnum.MINA.toString();
            if (StringUtils.isEmpty(serializer))
                serializer = SerializerProtocolEnum.PROTOSTUFF.toString();
            if (StringUtils.isEmpty(route))
                route = RouteProtocolEnum.RANDOM.toString();

            if (StringUtils.isNotEmpty(address) && StringUtils.isNotEmpty(port)) {
                builder.addPropertyValue("address", element.getAttribute("address"));
                builder.addPropertyValue("port", element.getAttribute("port"));
            }
            builder.addPropertyValue("protocol", protocol);
            builder.addPropertyValue("serializer", serializer);
            builder.addPropertyValue("route", route);

            ConfigHandler.rpcHandler = RpcHandlerFactory.getRpcHandler(protocol);
            ConfigHandler.serializer = SerializerFactory.getSerializer(serializer);
            ConfigHandler.router = RouterFactory.getRouter(route);
        }
        if (RegistryConfig.class.equals(beanClass)) {
            String address = element.getAttribute("address");
            int port = Integer.parseInt(element.getAttribute("port"));
            String protocol = element.getAttribute("protocol");
            if (StringUtils.isEmpty(protocol))
                protocol = RegistryProtocolEnum.ZOOKEEPER.toString();

            builder.addPropertyValue("address", address);
            builder.addPropertyValue("port", port);
            builder.addPropertyValue("protocol", protocol);
            ConfigHandler.registerHandler = RegisterHandlerFactory.getRegisterHandler(protocol, address, port);
        }
        if (ServiceConfig.class.equals(beanClass)) {
            String interfaceName = element.getAttribute("interface");
            PigeonConfig.serviceInterfaceNames.add(interfaceName);
            builder.addPropertyValue("interface", interfaceName);
            builder.addPropertyValue("ref", new RuntimeBeanReference(element.getAttribute("ref")));
        }
        if (ClientConfig.class.equals(beanClass)) {
            String interfaceName = element.getAttribute("interface");
            String sync = element.getAttribute("sync");

            PigeonConfig.clientInterfaceNames.add(interfaceName);
            builder.addPropertyValue("interface", interfaceName);
            builder.addPropertyValue("sync", sync);

            if ("false".equals(sync))
                builder.addPropertyValue("callback", new RuntimeBeanReference(element.getAttribute("callback")));
        }
    }

    @Override
    protected Class<?> getBeanClass(Element element) {
        return beanClass;
    }

}
