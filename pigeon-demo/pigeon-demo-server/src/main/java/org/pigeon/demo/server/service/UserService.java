/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.demo.server.service;

import org.pigeon.demo.server.model.User;

/**
 * @author lixiang
 * @version $Id UserService.java, v 0.1 2017-09-22 13:05 lixiang Exp $$
 */
public interface UserService {
    User getUser(String userId);
}