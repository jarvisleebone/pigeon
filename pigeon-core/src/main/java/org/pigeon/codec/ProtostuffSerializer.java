package org.pigeon.codec;

import java.io.IOException;

public class ProtostuffSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) throws IOException {
        // TODO 序列化

        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        // TODO 反序列化


        return null;
    }
}
