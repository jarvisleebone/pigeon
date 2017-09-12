package org.pigeon.rpc;

import org.pigeon.model.PigeonRequest;

public interface RpcHandler {

    void bindService(int port);

    Object sendMessage(PigeonRequest request);

}
