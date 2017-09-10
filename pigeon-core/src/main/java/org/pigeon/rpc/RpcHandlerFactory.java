package org.pigeon.rpc;

import org.pigeon.common.enums.RpcProtocolEnum;
import org.pigeon.rpc.mina.MinaRpcHandler;

public class RpcHandlerFactory {

    private static RpcHandler rpcHandler;

    public static RpcHandler getRpcHandler(String protocol) {
        RpcProtocolEnum rpcProtocolEnum = RpcProtocolEnum.valueOf(protocol.toUpperCase());
        switch (rpcProtocolEnum) {
            case MINA:
                rpcHandler = new MinaRpcHandler();
            case NETTY:
                rpcHandler = new MinaRpcHandler();
            default:
                rpcHandler = new MinaRpcHandler();
        }
        return rpcHandler;
    }
}
