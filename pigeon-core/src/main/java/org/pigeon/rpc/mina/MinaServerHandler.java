package org.pigeon.rpc.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.pigeon.config.PigeonConfig;
import org.pigeon.config.ServiceConfig;
import org.pigeon.model.PigeonRequest;

import java.lang.reflect.Method;

public class MinaServerHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        PigeonRequest request = (PigeonRequest) message;
        ServiceConfig serviceConfig = PigeonConfig.serviceConfigs.get(request.getInterfaceName());
        Method method = serviceConfig.getRef().getClass().getMethod(request.getMethodName(), request.getParameterTypes());
        session.write(method.invoke(serviceConfig.getRef(), request.getParameters()));
    }
}
