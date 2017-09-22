/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.common;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lixiang
 * @version $Id ServerRejectedExecutorHandler.java, v 0.1 2017-09-22 14:58 lixiang Exp $$
 */
public class ServerRejectedExecutorHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("拒绝");
    }
}