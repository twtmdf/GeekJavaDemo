package io.kimmking.cache.pubsub;

import org.springframework.stereotype.Service;

@Service
public class Message {
    private MyRedisPubSubListener redisPubSubListener;
    public void putMessage() {
        redisPubSubListener.message("key","message");
    }
    public void subMessage() {
        redisPubSubListener.subscribed("key",1);
    }
}
