/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.demo.client.callback;

import org.pigeon.callback.PigeonCallback;

/**
 * @author lixiang
 * @version $Id World2ServiceCallback.java, v 0.1 2017-09-18 10:29 lixiang Exp $$
 */
public class World2ServiceCallback implements PigeonCallback {

    @Override
    public void callback(Object o) {
        System.out.println("world2 service callback: " + o.toString());
    }
}