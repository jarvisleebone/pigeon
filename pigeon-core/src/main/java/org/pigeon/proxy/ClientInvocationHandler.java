/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.proxy;

import org.pigeon.model.PigeonRequest;
import org.pigeon.rpc.RpcHandler;
import org.pigeon.rpc.RpcHandlerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author lixiang
 * @version $Id ClientInvocationHandler.java, v 0.1 2017-09-11 9:52 lixiang Exp $$
 */
public class ClientInvocationHandler<T> implements InvocationHandler {

    private Class<T> clazz;

    private RpcHandler rpcHandler;

    public ClientInvocationHandler(Class<T> clazz) {
        this.clazz = clazz;
        // TODO 暂时写死mina
        this.rpcHandler = RpcHandlerFactory.getRpcHandler("mina");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PigeonRequest request = new PigeonRequest();
        request.setInterfaceName(clazz.getName());
        request.setMethodName(method.getName());
        if (null != args && 0 != args.length) {
            request.setParameterTypes(Arrays.stream(args).map((arg) -> arg.getClass()).toArray(Class<?>[]::new));
            request.setParameters(args);
        }
        return rpcHandler.sendMessage(request);
    }
}