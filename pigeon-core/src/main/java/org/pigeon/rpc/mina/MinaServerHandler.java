package org.pigeon.rpc.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.pigeon.config.PigeonConfig;
import org.pigeon.config.ServiceConfig;
import org.pigeon.model.PigeonRequest;
import org.pigeon.model.PigeonResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

public class MinaServerHandler extends IoHandlerAdapter {

    private Executor executor;

    public MinaServerHandler(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        executor.execute(() -> {
            try {
                PigeonRequest request = (PigeonRequest) message;
                ServiceConfig serviceConfig = PigeonConfig.serviceConfigs.get(request.getInterfaceName());
                Method method = PigeonConfig.serviceMethods.get(request.getMethodSign());
                if (null == method) {
                    throw new NoSuchMethodException();
                }

                if ("void".equals(request.getReturnType())) {
                    method.invoke(serviceConfig.getRef(), request.getParameters());
                } else {
                    if (request.isSync()) {
                        session.write(method.invoke(serviceConfig.getRef(), request.getParameters()));
                    } else {
                        PigeonResponse response = new PigeonResponse();
                        response.setMethodSign(request.getMethodSign());
                        response.setResult(method.invoke(serviceConfig.getRef(), request.getParameters()));
                        session.write(response);
                    }
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
