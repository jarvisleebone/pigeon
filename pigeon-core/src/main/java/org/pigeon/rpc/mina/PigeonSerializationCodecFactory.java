package org.pigeon.rpc.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class PigeonSerializationCodecFactory implements ProtocolCodecFactory {

    private PigeonSerializationEncoder encoder;
    private PigeonSerializationDecoder decoder;


    public PigeonSerializationCodecFactory() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public PigeonSerializationCodecFactory(ClassLoader classLoader) {
        encoder = new PigeonSerializationEncoder();
        decoder = new PigeonSerializationDecoder(classLoader);
    }


    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }
}
