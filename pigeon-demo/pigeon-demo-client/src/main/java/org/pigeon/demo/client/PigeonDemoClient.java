package org.pigeon.demo.client;

import org.pigeon.demo.server.model.Person;
import org.pigeon.demo.server.service.HelloService;
import org.pigeon.demo.server.service.WorldService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PigeonDemoClient {

    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring.xml"});
        System.out.println("client start");

        HelloService helloService = applicationContext.getBean(HelloService.class);
        WorldService worldService = applicationContext.getBean(WorldService.class);

//        sync(helloService);
        async(worldService);

        /**
         * 序列化实现
         *
         * 路由规则实现
         *
         * 心跳
         *
         * netty集成
         */
    }


    private static void sync(HelloService helloService) {
        // 先调用一次，为了初始化连接
//        System.out.println(helloService.hello("world"));
        System.out.println("----------------------");

        long count = 1;
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
//            String str = helloService.hello("test");
//            System.out.println(str);
//            helloService.testVoid("void");

//            fixedThreadPool.execute(() -> {
//                String str = helloService.hello("test");
//                System.out.println(str);
//            });

            Person p = helloService.addAge(new Person("mike", 35));
            System.out.println(p.getName() + ":" + p.getAge());
        }
        long endTime = System.currentTimeMillis();

        System.out.println("sec:" + (endTime - beginTime) / 1000.00);
        System.out.println("tps:" + count / ((endTime - beginTime) / 1000.00));
        System.out.println("平均耗时：" + (endTime - beginTime) / (count * 1.00) + "ms");

        System.out.println("----------------------");
    }

    private static void async(WorldService worldService) {
        worldService.world("hello");
    }

}
