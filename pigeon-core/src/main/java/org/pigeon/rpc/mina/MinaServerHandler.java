package org.pigeon.rpc.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.pigeon.model.PigeonRequest;


public class MinaServerHandler extends IoHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(MinaServerHandler.class);


    @Override
    public void sessionCreated(IoSession session) throws Exception {
        LOGGER.info("session created : " + session.getId());
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        PigeonRequest request = (PigeonRequest) message;

        System.out.println(request.getInterfaceName());
        System.out.println(request.getMethodName());

        session.write("result");
    }
}
