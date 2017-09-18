package org.pigeon.demo.client;

import org.pigeon.demo.server.model.Person;
import org.pigeon.demo.server.service.HelloService;
import org.pigeon.demo.server.service.WorldService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PigeonDemoClient {

    private static ExecutorService fixedThreadPool;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring.xml"});
        System.out.println("client start");

        HelloService helloService = applicationContext.getBean(HelloService.class);
        WorldService worldService = applicationContext.getBean(WorldService.class);
        helloService.testVoid("void");
        Person p = helloService.addAge(new Person("name", 20));
        System.out.println(p.getName() + ":" + p.getAge());
        worldService.world("hello", 88, 20.78);
        worldService.world(799, 38.4876f);
        System.out.println(helloService.hello("哈哈哈哈哈哈哈哈"));

        sync(helloService, 100 * 10000);
    }

    private static void sync(HelloService helloService, int count) {
        CountDownLatch countDown = new CountDownLatch(count);

        fixedThreadPool = Executors.newFixedThreadPool(100);

        long beginTime = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            fixedThreadPool.execute(() -> {
                String str = helloService.hello("test");
                countDown.countDown();
            });
        }

        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("sec:" + (endTime - beginTime) / 1000.00);
        System.out.println("qps:" + count / ((endTime - beginTime) / 1000.00));
        System.out.println("平均耗时：" + (endTime - beginTime) / (count * 1.00) + "ms");
    }

}
