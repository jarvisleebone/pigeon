package org.pigeon.demo.client;

import org.pigeon.demo.server.service.HelloService;

import java.lang.reflect.Method;

public class main {

    public static void main(String[] args) {

        Class<HelloService> clazz = HelloService.class;

        for (Method m : clazz.getMethods()) {
            System.out.println(clazz.getName() + ":" + m.getName());
            for (Class c : m.getParameterTypes()) {
                System.out.println("    " + c.getName());
            }
        }


    }
}
