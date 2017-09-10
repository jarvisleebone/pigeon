package org.pigeon.rpc.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.stream.StreamIoHandler;
import org.pigeon.codec.Serializer;
import org.pigeon.model.PigeonRequest;

import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.IOException;


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
        LOGGER.info("请求");
        try {
            ObjectInput objectInput = new ObjectInputStream(in);
            PigeonRequest request = serializer.deserialize((byte[]) objectInput.readObject(), PigeonRequest.class);

            // TODO 反射执行请求接口，拿到返回值写入out



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
