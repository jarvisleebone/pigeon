package org.pigeon.demo.server.service.impl;

import org.pigeon.demo.server.service.WorldService;

public class WorldServiceImpl implements WorldService {
    @Override
    public String world(String name) {
        return "World " + name;
    }
}
