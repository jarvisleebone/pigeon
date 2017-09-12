/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.rpc.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.stream.StreamIoHandler;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author lixiang
 * @version $Id MinaClientHandler.java, v 0.1 2017-09-12 10:19 lixiang Exp $$
 */
public class MinaClientHandler extends StreamIoHandler {



    @Override
    protected void processStreamIo(IoSession session, InputStream in, OutputStream out) {




    }

    @Override
    public void messageReceived(IoSession session, Object buf) {
        super.messageReceived(session, buf);
    }
}