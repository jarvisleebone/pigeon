/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package com.example.demo.controller;

import org.pigeon.demo.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixiang
 * @version $Id TestController.java, v 0.1 2017-09-19 19:28 lixiang Exp $$
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getUser")
    @ResponseBody
    public String getUser(String userid) {
        return userService.getUser(userid).toString();
    }
}