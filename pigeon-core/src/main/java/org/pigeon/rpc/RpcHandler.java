package org.pigeon.rpc;

import org.pigeon.callback.PigeonCallback;
import org.pigeon.model.PigeonRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class RpcHandler {

    // 执行服务端逻辑的线程池
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public abstract void bindService(int port);

    public abstract Object sendMessageSync(PigeonRequest request, String serverAddress) throws Exception;

    public abstract void sendMessageAsync(PigeonRequest request, String serverAddress, PigeonCallback callback) throws Exception;

    public static void execute(Runnable task){
        fixedThreadPool.execute(task);
    }

}
