package org.pigeon.demo.server.service.impl;

import org.pigeon.demo.server.service.WorldService;

public class WorldServiceImpl implements WorldService {
    @Override
    public String world(String name, int age, double d) {
        return "World " + name + ":" + age + ":" + d;
    }

    @Override
    public String world(int i, float f) {
        return "World2 " + i + ":" + f;
    }
}
