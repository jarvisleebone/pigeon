/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.demo.server.service.impl;

import org.pigeon.demo.server.model.User;
import org.pigeon.demo.server.service.UserService;

/**
 * @author lixiang
 * @version $Id UserServiceImpl.java, v 0.1 2017-09-22 13:06 lixiang Exp $$
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName("测试");
        return user;
    }
}