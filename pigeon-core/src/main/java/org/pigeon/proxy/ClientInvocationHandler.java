/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.proxy;

import org.pigeon.config.handler.ConfigHandler;
import org.pigeon.model.PigeonRequest;
import org.pigeon.rpc.RpcHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author lixiang
 * @version $Id ClientInvocationHandler.java, v 0.1 2017-09-11 9:52 lixiang Exp $$
 */
public class ClientInvocationHandler<T> implements InvocationHandler {

    private Class<T> clazz;
    private boolean sync;
    private RpcHandler rpcHandler;

    public ClientInvocationHandler(Class<T> clazz, boolean sync) {
        this.clazz = clazz;
        this.sync = sync;
        this.rpcHandler = ConfigHandler.rpcHandler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PigeonRequest request = new PigeonRequest();
        request.setInterfaceName(clazz.getName());
        request.setMethodName(method.getName());
        request.setSync(sync);
        if (null != args && 0 != args.length) {
            request.setParameterTypes(Arrays.stream(args).map((arg) -> arg.getClass()).toArray(Class<?>[]::new));
            request.setParameters(args);
        }
        if (sync) {
            return rpcHandler.sendMessageSync(request);
        } else {
            rpcHandler.sendMessageAsync(request);
            return null;
        }
    }
}