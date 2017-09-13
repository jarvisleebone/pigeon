/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.proxy;

import org.pigeon.callback.PigeonCallback;
import org.pigeon.config.handler.ConfigHandler;
import org.pigeon.model.PigeonRequest;
import org.pigeon.registry.RegisterHandler;
import org.pigeon.router.Router;
import org.pigeon.rpc.RpcHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author lixiang
 * @version $Id ClientInvocationHandler.java, v 0.1 2017-09-11 9:52 lixiang Exp $$
 */
public class ClientInvocationHandler<T> implements InvocationHandler {

    private Class<T> clazz;
    private boolean sync;
    private PigeonCallback callback;
    private RpcHandler rpcHandler;

    public ClientInvocationHandler(Class<T> clazz, boolean sync, PigeonCallback callback) {
        this.clazz = clazz;
        this.sync = sync;
        this.rpcHandler = ConfigHandler.rpcHandler;
        this.callback = callback;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 封装请求
        PigeonRequest request = new PigeonRequest();
        request.setInterfaceName(clazz.getName());
        request.setMethodName(method.getName());
        request.setSync(sync);
        request.setReturnType(method.getReturnType());
        if (null != args && 0 != args.length) {
            request.setParameterTypes(Arrays.stream(args).map((arg) -> arg.getClass()).toArray(Class<?>[]::new));
            request.setParameters(args);
        }
        // 服务选举
        Router router = ConfigHandler.router;
        List<String> servers = RegisterHandler.services.get(request.getInterfaceName());
        String serverAddress = router.elect(servers);
        // 发送请求
        if (sync) {
            return rpcHandler.sendMessageSync(request, serverAddress);
        } else {
            rpcHandler.sendMessageAsync(request, serverAddress, callback);
            return null;
        }
    }
}