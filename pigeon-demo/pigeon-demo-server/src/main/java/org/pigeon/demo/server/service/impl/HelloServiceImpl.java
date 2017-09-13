package org.pigeon.demo.server.service.impl;

import org.pigeon.demo.server.model.Person;
import org.pigeon.demo.server.service.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

    @Override
    public void testVoid(String name) {
        System.out.println("test void " + name);
    }

    @Override
    public Person addAge(Person p) {
        p.setAge(p.getAge() + 100);
        p.setName(p.getName() + " dead");
        return p;
    }
}
