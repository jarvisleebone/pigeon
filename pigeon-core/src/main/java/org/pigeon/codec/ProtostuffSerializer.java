package org.pigeon.codec;

import java.io.IOException;

public class ProtostuffSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) throws IOException {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        return null;
    }
}
