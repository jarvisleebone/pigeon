package org.pigeon.rpc.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.pigeon.config.PigeonConfig;
import org.pigeon.config.ServiceConfig;
import org.pigeon.model.PigeonRequest;
import org.pigeon.rpc.RpcHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ThreadPoolExecutor;

public class MinaServerHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        RpcHandler.execute(() -> {
            try {
                PigeonRequest request = (PigeonRequest) message;
                ServiceConfig serviceConfig = PigeonConfig.serviceConfigs.get(request.getInterfaceName());
                Method method = serviceConfig.getRef().getClass().getMethod(request.getMethodName(), request.getParameterTypes());

                if ("void".equals(method.getReturnType().getName()))
                    method.invoke(serviceConfig.getRef(), request.getParameters());
                else
                    session.write(method.invoke(serviceConfig.getRef(), request.getParameters()));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
}
