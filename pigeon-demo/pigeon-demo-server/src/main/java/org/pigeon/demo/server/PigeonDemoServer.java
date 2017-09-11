package org.pigeon.demo.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

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
