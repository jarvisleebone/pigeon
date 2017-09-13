package org.pigeon.demo.server.service;

import org.pigeon.demo.server.model.Person;

public interface HelloService {

    String hello(String name);

    void testVoid(String name);

    Person addAge(Person p);

}
