package org.pigeon.rpc.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.pigeon.config.handler.ConfigHandler;

public class PigeonSerializationDecoder extends CumulativeProtocolDecoder {

    private final ClassLoader classLoader;

    public PigeonSerializationDecoder(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {

        try {
            int length = in.getInt();

            System.out.println("decoder" + length);

            byte[] bytes = new byte[length];
            in.get(bytes);




            Object o = ConfigHandler.serializer.deserialize(bytes, Object.class);

            System.out.println(o.toString());


            out.write(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
