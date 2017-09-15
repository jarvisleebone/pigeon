package org.pigeon.config.spring;

import org.apache.commons.lang3.StringUtils;
import org.pigeon.codec.SerializerFactory;
import org.pigeon.common.enums.RegistryProtocolEnum;
import org.pigeon.common.enums.RouteProtocolEnum;
import org.pigeon.common.enums.RpcProtocolEnum;
import org.pigeon.common.enums.SerializerProtocolEnum;
import org.pigeon.config.*;
import org.pigeon.config.handler.ConfigHandler;
import org.pigeon.registry.RegisterHandlerFactory;
import org.pigeon.router.RouterFactory;
import org.pigeon.rpc.RpcHandlerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PigeonBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    public PigeonBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    private BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass, String clientInterface) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String id = element.getAttribute("id");
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        beanDefinition.getPropertyValues().addPropertyValue("id", id);
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
                beanDefinition.getPropertyValues().addPropertyValue("address", element.getAttribute("address"));
                beanDefinition.getPropertyValues().addPropertyValue("port", element.getAttribute("port"));
            }
            beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);
            beanDefinition.getPropertyValues().addPropertyValue("serializer", serializer);
            beanDefinition.getPropertyValues().addPropertyValue("route", route);

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

            beanDefinition.getPropertyValues().addPropertyValue("address", address);
            beanDefinition.getPropertyValues().addPropertyValue("port", port);
            beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);
            ConfigHandler.registerHandler = RegisterHandlerFactory.getRegisterHandler(protocol, address, port);
        }
        if (ServiceConfig.class.equals(beanClass)) {
            String interfaceName = element.getAttribute("interface");
            PigeonConfig.serviceInterfaceNames.add(interfaceName);
            beanDefinition.getPropertyValues().addPropertyValue("interface", interfaceName);
            beanDefinition.getPropertyValues().addPropertyValue("ref", new RuntimeBeanReference(element.getAttribute("ref")));
        }
        if (ClientConfig.class.equals(beanClass)) {
            String interfaceName = element.getAttribute("interface");
            beanDefinition.getPropertyValues().addPropertyValue("interface", interfaceName);
            // 解析method配置
            NodeList methods = element.getChildNodes();
            if (null != methods && 0 != methods.getLength()) {
                for (int i = 0; i < methods.getLength(); i++) {
                    Node node = methods.item(i);
                    if (node instanceof Element) {
                        Element method = (Element) methods.item(i);
                        parse(method, parserContext, MethodConfig.class, interfaceName);
                    }
                }
            }
            PigeonConfig.clientInterfaceNames.add(interfaceName);
        }
        if (MethodConfig.class.equals(beanClass)) {
            beanDefinition.getPropertyValues().add("interfaceName", clientInterface);
            beanDefinition.getPropertyValues().add("name", element.getAttribute("name"));
            beanDefinition.getPropertyValues().add("sync", element.getAttribute("sync"));
            String callback = element.getAttribute("callback");
            if (StringUtils.isNotEmpty(callback))
                beanDefinition.getPropertyValues().add("callback", new RuntimeBeanReference(callback));
        }

        return beanDefinition;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return parse(element, parserContext, beanClass, "");
    }
}
