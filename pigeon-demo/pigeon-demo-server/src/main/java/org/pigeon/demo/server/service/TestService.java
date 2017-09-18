/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.demo.server.service;

import org.pigeon.demo.server.model.Person;

/**
 * @author lixiang
 * @version $Id TestService.java, v 0.1 2017-09-18 11:02 lixiang Exp $$
 */
public interface TestService {

    String test(String str, int i);

    String test(int i);

    void test();

    Person test(Person p);

    String testAsync(String str, int i, double d);

    String testAsync(String str);

    void testAsync();

    Person testAsync(Person p);

    String testAsync2(String str);

    String testAsync2(String str, int i);

    String testAsync2(String str, int i, double d);

    String testSync(String str);

    String testAsync(String str, int i);

}