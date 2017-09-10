package org.pigeon.codec;

import org.pigeon.common.enums.SerializerProtocolEnum;

public class SerializerFactory {

    public static Serializer getSerializer(String serializerName) {
        SerializerProtocolEnum serializerProtocolEnum = SerializerProtocolEnum.valueOf(serializerName.toUpperCase());
        switch (serializerProtocolEnum) {
            case PROTOSTUFF:
                return new ProtostuffSerializer();
            default:
                return new ProtostuffSerializer();
        }
    }
}
