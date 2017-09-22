package org.pigeon.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
    private AtomicInteger id = new AtomicInteger(0);
    private final String namePrefix;
    private final boolean deamon;

    public NamedThreadFactory(String namePrefix, boolean deamon) {
        this.namePrefix = namePrefix;
        this.deamon = deamon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r,namePrefix + "_" + id.getAndSet(1));
        thread.setDaemon(deamon);
        return thread;
    }
}
