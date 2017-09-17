package org.pigeon.rpc.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.pigeon.config.PigeonConfig;
import org.pigeon.config.ServiceConfig;
import org.pigeon.model.PigeonRequest;
import org.pigeon.model.PigeonResponse;
import org.pigeon.rpc.RpcHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MinaServerHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        RpcHandler.execute(() -> {
            try {
                PigeonRequest request = (PigeonRequest) message;
                ServiceConfig serviceConfig = PigeonConfig.serviceConfigs.get(request.getInterfaceName());
                Method method = PigeonConfig.serviceMethods.get(request.getMethodSign());
                if (null == method)
                    throw new NoSuchMethodException();

                if ("void".equals(method.getReturnType().getName())) {
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
