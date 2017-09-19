/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package com.example.demo;

import org.pigeon.demo.server.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixiang
 * @version $Id TestController.java, v 0.1 2017-09-19 19:28 lixiang Exp $$
 */
@RestController
public class TestController {

    @Autowired
    private HelloService helloService;

    @GetMapping("test")
    @ResponseBody
    public String test() {
        return helloService.hello("test");
    }

}