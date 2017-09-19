/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.proxy;

import org.pigeon.common.util.ReflectUtil;
import org.pigeon.config.MethodConfig;
import org.pigeon.config.PigeonConfig;
import org.pigeon.config.handler.ConfigHandler;
import org.pigeon.model.PigeonRequest;
import org.pigeon.registry.RegisterHandler;
import org.pigeon.router.Router;
import org.pigeon.rpc.RpcHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author lixiang
 * @version $Id ClientInvocationHandler.java, v 0.1 2017-09-11 9:52 lixiang Exp $$
 */
public class ClientInvocationHandler<T> implements InvocationHandler {

    private Class<T> clazz;
    private RpcHandler rpcHandler;

    public ClientInvocationHandler(Class<T> clazz) {
        this.clazz = clazz;
        this.rpcHandler = ConfigHandler.rpcHandler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO 本地方法调用


        // 封装请求
        PigeonRequest request = new PigeonRequest();
        String methodSign = ReflectUtil.getMethodSign(clazz, method);
        request.setInterfaceName(clazz.getName());
        request.setMethodSign(methodSign);
        request.setReturnType(method.getReturnType().getName());
        if (null != args && 0 != args.length)
            request.setParameters(args);
        MethodConfig methodConfig = PigeonConfig.methodConfigs.get(methodSign);
        request.setSync(null == methodConfig ? true : methodConfig.isSync());

        // 服务选举
        List<String> servers = RegisterHandler.services.get(request.getInterfaceName());
        String serverAddress = ConfigHandler.router.elect(servers, request.getInterfaceName());

        // 发送请求
        if (request.isSync()) {
            return rpcHandler.sendMessageSync(request, serverAddress);
        } else {
            rpcHandler.sendMessageAsync(request, serverAddress);
            return null;
        }
    }
}