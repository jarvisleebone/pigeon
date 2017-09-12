package org.pigeon.rpc;

import org.pigeon.model.PigeonRequest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class RpcHandler {

    private static ThreadPoolExecutor threadPoolExecutor;

    public abstract void bindService(int port);

    public abstract Object sendMessage(PigeonRequest request);

    public static void submit(Runnable task){
        if(threadPoolExecutor == null){
            synchronized (RpcHandler.class) {
                if(threadPoolExecutor == null){
                    threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
                }
            }
        }
        threadPoolExecutor.submit(task);
    }

}
