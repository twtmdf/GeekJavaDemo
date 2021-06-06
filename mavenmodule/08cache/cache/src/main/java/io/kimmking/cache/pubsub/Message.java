package io.kimmking.cache.pubsub;

import org.springframework.stereotype.Service;

@Service
public class Message {
    private MyRedisPubSubListener redisPubSubListener;
    public void putMessage() {
//        发下单消息
        redisPubSubListener.message("key","message");
    }
    public void subMessage() {
//        消费消息处理订单
        redisPubSubListener.subscribed("key",1);
    }
}
