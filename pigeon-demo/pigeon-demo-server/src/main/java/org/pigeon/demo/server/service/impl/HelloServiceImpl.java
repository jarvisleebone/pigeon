/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.demo.server.service.impl;

import org.pigeon.demo.server.service.HelloService;

/**
 * @author lixiang
 * @version $Id HelloServiceImpl.java, v 0.1 2017-09-18 17:29 lixiang Exp $$
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String str) {
        return "hello " + str;
    }
}