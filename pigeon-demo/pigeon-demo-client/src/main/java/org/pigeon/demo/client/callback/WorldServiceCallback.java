package org.pigeon.demo.client.callback;

import org.pigeon.callback.PigeonCallback;

public class WorldServiceCallback implements PigeonCallback {
    @Override
    public void callback(Object o) {
        System.out.println(o.toString());
    }
}
