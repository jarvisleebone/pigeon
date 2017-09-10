package org.pigeon.demo.server.service.impl;

import org.pigeon.demo.server.service.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
