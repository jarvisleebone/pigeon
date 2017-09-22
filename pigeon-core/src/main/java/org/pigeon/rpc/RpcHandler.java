package org.pigeon.rpc;

import org.pigeon.model.PigeonRequest;

public abstract class RpcHandler {

    public abstract void bindService(int port);

    public abstract Object sendMessageSync(PigeonRequest request, String serverAddress) throws Exception;

    public abstract void sendMessageAsync(PigeonRequest request, String serverAddress) throws Exception;

}
