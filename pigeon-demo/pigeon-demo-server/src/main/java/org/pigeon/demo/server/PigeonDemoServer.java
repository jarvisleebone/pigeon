package org.pigeon.demo.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PigeonDemoServer {

    public static void main(String[] args) {
        try {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {"spring/spring.xml"});
            System.out.println("server start");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
