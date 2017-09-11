package org.pigeon.rpc.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.stream.StreamIoHandler;
import org.pigeon.codec.Serializer;
import org.pigeon.model.PigeonRequest;

import java.io.*;


public class MinaServerHandler extends StreamIoHandler {

    private static final Logger LOGGER = Logger.getLogger(MinaServerHandler.class);

    private Serializer serializer;

    public MinaServerHandler(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        LOGGER.info("session created : " + session.getId());
    }

    @Override
    protected void processStreamIo(IoSession session, InputStream in, OutputStream out) {
        try {
            ObjectInput objectInput = new ObjectInputStream(in);
            PigeonRequest request = serializer.deserialize((byte[]) objectInput.readObject(), PigeonRequest.class);

            // TODO 接收客户端请求，反射执行该接口实现，返回响应


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
