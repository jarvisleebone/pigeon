package org.pigeon.demo.client;

import org.pigeon.demo.server.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PigeonDemoClient {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring.xml"});
        System.out.println("client start");

        HelloService helloService = applicationContext.getBean(HelloService.class);
        System.out.println(helloService.hello("jarvis"));
    }
}
