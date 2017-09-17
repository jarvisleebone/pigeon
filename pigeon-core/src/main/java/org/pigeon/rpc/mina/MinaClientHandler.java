/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.rpc.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.pigeon.config.MethodConfig;
import org.pigeon.config.PigeonConfig;
import org.pigeon.model.PigeonResponse;

/**
 * @author lixiang
 * @version $Id MinaClientHandler.java, v 0.1 2017-09-12 10:19 lixiang Exp $$
 */
public class MinaClientHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        PigeonResponse response = (PigeonResponse) message;
        MethodConfig methodConfig = PigeonConfig.methodConfigs.get(response.getMethodSign());
        if (null != methodConfig.getCallback()) {
            methodConfig.getCallback().callback(response.getResult());
        }
    }
}