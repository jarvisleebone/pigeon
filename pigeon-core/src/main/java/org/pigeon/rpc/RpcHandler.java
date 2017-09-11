package org.pigeon.rpc;

import org.pigeon.model.PigeonRequest;

public abstract class RpcHandler {

    public abstract void bindService(int port);

    public abstract Object sendMessage(PigeonRequest request);

}
