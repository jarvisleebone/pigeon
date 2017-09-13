package org.pigeon.demo.client;

import org.pigeon.demo.server.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.*;

public class PigeonDemoClient {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring.xml"});
        System.out.println("client start");

        HelloService helloService = applicationContext.getBean(HelloService.class);
        // 先调用一次，为了初始化连接
        System.out.println(helloService.hello("jarvis"));
        System.out.println("----------------------");

        long count = 1 * 10000;
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            helloService.hello("test");
        }
        long endTime = System.currentTimeMillis();

        System.out.println("sec:" + (endTime - beginTime) / 1000.00);
        System.out.println("tps:" + count / ((endTime - beginTime) / 1000.00));
        System.out.println("平均耗时：" + (endTime - beginTime) / (count*1.00) + "ms");

        System.out.println("----------------------");










        /**
         * 异步发送请求、异步的callback
         *
         * 序列化实现
         *
         * 心跳
         *
         * 路由规则实现
         *
         * netty集成
         */
    }
}
