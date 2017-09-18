/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.demo.server.service.impl;

import org.pigeon.demo.server.model.Person;
import org.pigeon.demo.server.service.TestService;

/**
 * @author lixiang
 * @version $Id TestServiceImpl.java, v 0.1 2017-09-18 11:02 lixiang Exp $$
 */
public class TestServiceImpl implements TestService {

    @Override
    public String test(String str, int i) {
        System.out.println("test string:" + str + " int:" + i);
        return "test return:" + str + ":" + i;
    }

    @Override
    public String test(int i) {
        System.out.println("test int:" + i);
        return "test return:" + i;
    }

    @Override
    public void test() {
        System.out.println("test");
    }

    @Override
    public Person test(Person p) {
        System.out.println("test Person:" + p.getName() + ":" + p.getAge());
        p.setName(p.getName() + " dead");
        p.setAge(p.getAge() + 100);
        return p;
    }

    @Override
    public String testAsync(String str, int i, double d) {
        System.out.println("testAsync string:" + str + " int:" + i + " double:" + d);
        return "testAsync:" + str + ":" + i + ":" + d;
    }

    @Override
    public String testAsync(String str) {
        System.out.println("testAsync string:" + str);
        return "testAsync:" + str;
    }

    @Override
    public void testAsync() {
        System.out.println("testAsync");
    }

    @Override
    public Person testAsync(Person p) {
        System.out.println("testAsync Person:" + p.getName() + ":" + p.getAge());
        p.setName(p.getName() + " dead");
        p.setAge(p.getAge() + 100);
        return p;
    }

    @Override
    public String testAsync2(String str) {
        System.out.println("testAsync2 string:" + str);
        return "testAsync:" + str;
    }

    @Override
    public String testAsync2(String str, int i) {
        System.out.println("testAsync2 string:" + str + " int:" + i);
        return "testAsync:" + str + ":" + i;
    }

    @Override
    public String testAsync2(String str, int i, double d) {
        System.out.println("testAsync2 string:" + str + " int:" + i + " double:" + d);
        return "testAsync:" + str + ":" + i + ":" + d;
    }

    @Override
    public String testSync(String str) {
        return "hello " + str;
    }
}