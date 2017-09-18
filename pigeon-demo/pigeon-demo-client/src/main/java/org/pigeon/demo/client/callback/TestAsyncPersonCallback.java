/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.demo.client.callback;

import org.pigeon.callback.PigeonCallback;
import org.pigeon.demo.server.model.Person;

/**
 * @author lixiang
 * @version $Id TestAsyncPersonCallback.java, v 0.1 2017-09-18 11:20 lixiang Exp $$
 */
public class TestAsyncPersonCallback implements PigeonCallback {

    @Override
    public void callback(Object o) {
        Person person = (Person) o;
        System.out.println("callback name:" + person.getName());
        System.out.println("callback age:" + person.getAge());
    }
}