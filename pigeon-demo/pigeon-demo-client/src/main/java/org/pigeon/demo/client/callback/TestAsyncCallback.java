/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.demo.client.callback;

import org.pigeon.callback.PigeonCallback;

/**
 * @author lixiang
 * @version $Id TestAsyncCallback.java, v 0.1 2017-09-18 11:15 lixiang Exp $$
 */
public class TestAsyncCallback implements PigeonCallback {

    @Override
    public void callback(Object o) {
        System.out.println("testAsync callback:" + o.toString());
    }
}