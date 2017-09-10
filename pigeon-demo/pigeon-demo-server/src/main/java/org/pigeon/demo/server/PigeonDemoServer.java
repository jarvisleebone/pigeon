package org.pigeon.demo.server;

import org.pigeon.config.ServiceConfig;
import org.pigeon.demo.server.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class PigeonDemoServer {

    public static void main(String[] args) {
        try {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {"spring/spring.xml"});
            System.out.println("server start");

            ServiceConfig<HelloService> serviceConfig = (ServiceConfig<HelloService>) applicationContext.getBean("helloService");
            System.out.println(serviceConfig.getRef().getClass().getName());

            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
