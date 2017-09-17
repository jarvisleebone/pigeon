package org.pigeon.rpc.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.pigeon.config.handler.ConfigHandler;

import java.io.NotSerializableException;
import java.io.Serializable;

public class PigeonSerializationEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        if (!(message instanceof Serializable)) {
            throw new NotSerializableException();
        }

        byte[] bytes = ConfigHandler.serializer.serialize(message);
        System.out.println("encoder : " + bytes.length);

        IoBuffer buf = IoBuffer.allocate(64);
        buf.setAutoExpand(true);
        buf.putInt(bytes.length);
        buf.put(bytes);

        buf.flip();
        out.write(buf);
    }
}
