package org.pigeon.demo.client;

import org.pigeon.demo.server.model.Person;
import org.pigeon.demo.server.service.HelloService;
import org.pigeon.demo.server.service.TestService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PigeonDemoClient {

    private static ExecutorService fixedThreadPool;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring.xml"});
        System.out.println("client start");

        TestService testService = applicationContext.getBean(TestService.class);
        HelloService helloService = applicationContext.getBean(HelloService.class);

        Person p = new Person("person", 50);
        // sync
        System.out.println(testService.test("test", 20));
        System.out.println(testService.test(50));
        testService.test();
        System.out.println(testService.test(p));
//         async
        testService.testAsync();
        testService.testAsync(p);
        testService.testAsync("test async");
        testService.testAsync("test async", 33, 26.58);
        testService.testAsync2("test async2");
        testService.testAsync2("test async2", 70);
        testService.testAsync2("test async2", 90, 25.64);

        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("循环次数：");
            String line = s.nextLine();
            int count = Integer.parseInt(line);
            System.out.println("-------------------------");
            test(testService, count);
            System.out.println("-------------------------");
        }
    }

    private static void test(TestService testService, int count) {
        CountDownLatch countDown = new CountDownLatch(count);

        fixedThreadPool = Executors.newFixedThreadPool(100);

        long beginTime = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            fixedThreadPool.execute(() -> {
//                String str = helloService.hello("test");
                String str = testService.testSync("[INFO ][2017-09-19 13:46:37,526][NotifyFacadeImpl$2:336] 短信同步发送日志记录成功：mobile=17681802566,template=SMS_78415031:APP开户初审不通过,args={prodclient=布林证券交易端, telephone=4008-8888-8888} path:/root/logs/westockcore/westockcore-baseservice.log @timestamp:September 19th 2017, 13:46:38.756 @version:1 host:10.0.1.92 type:westockcore-baseservice _id:AV6YrI022E46rrktWRGF _type:westockcore-baseservice _index:logstash-2017.09.19 _score:");
//                testService.testAsync("test async", 20);
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
