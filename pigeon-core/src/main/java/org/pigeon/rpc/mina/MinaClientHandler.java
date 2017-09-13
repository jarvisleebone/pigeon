/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.rpc.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.pigeon.callback.PigeonCallback;

import java.lang.reflect.Method;

/**
 * @author lixiang
 * @version $Id MinaClientHandler.java, v 0.1 2017-09-12 10:19 lixiang Exp $$
 */
public class MinaClientHandler extends IoHandlerAdapter {

    private PigeonCallback callback;

    public MinaClientHandler(PigeonCallback callback) {
        this.callback = callback;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        Method method = callback.getClass().getMethod("callback", Object.class);
        method.invoke(callback, message);
    }
}