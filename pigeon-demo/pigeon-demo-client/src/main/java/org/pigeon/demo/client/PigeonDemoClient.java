package org.pigeon.demo.client;

import org.pigeon.demo.server.model.Person;
import org.pigeon.demo.server.service.TestService;
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

        TestService testService = applicationContext.getBean(TestService.class);
        Person p = new Person("person", 50);
        // sync
        System.out.println(testService.test("test", 20));
        System.out.println(testService.test(50));
        testService.test();
        System.out.println(testService.test(p));
        // async
        testService.testAsync();
        testService.testAsync(p);
        testService.testAsync("test async");
        testService.testAsync("test async", 33, 26.58);
        testService.testAsync2("test async2");
        testService.testAsync2("test async2", 70);
        testService.testAsync2("test async2", 90, 25.64);


        test(testService, 100 * 10000);
    }

    private static void test(TestService testService, int count) {
        CountDownLatch countDown = new CountDownLatch(count);

        fixedThreadPool = Executors.newFixedThreadPool(100);

        long beginTime = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            fixedThreadPool.execute(() -> {
                String str = testService.testSync("test");
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
