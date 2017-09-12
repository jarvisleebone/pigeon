package org.pigeon.config.handler;

import org.pigeon.codec.Serializer;
import org.pigeon.registry.RegisterHandler;
import org.pigeon.router.Router;
import org.pigeon.rpc.RpcHandler;

public class ConfigHandler {

    public static RegisterHandler registerHandler;
    public static RpcHandler rpcHandler;
    public static Serializer serializer;
    public static Router router;

}
