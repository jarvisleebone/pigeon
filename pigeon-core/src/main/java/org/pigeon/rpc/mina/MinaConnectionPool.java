/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.rpc.mina;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.util.ConcurrentHashSet;

import java.util.Set;

/**
 * @author lixiang
 * @version $Id MinaConnectionPool.java, v 0.1 2017-09-12 10:34 lixiang Exp $$
 */
public class MinaConnectionPool {

    private int minCount = 2;
    private int maxCount = 5;
    private Set<ConnectFuture> connections = new ConcurrentHashSet<>();


}