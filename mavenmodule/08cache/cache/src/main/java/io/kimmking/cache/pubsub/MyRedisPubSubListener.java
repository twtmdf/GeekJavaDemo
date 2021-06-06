package io.kimmking.cache.pubsub;

import io.lettuce.core.pubsub.RedisPubSubListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyRedisPubSubListener implements RedisPubSubListener<String, String> {
    @Override
    public void message(String channel, String message) {
        log.info("msg1={} on channel {}",  message, channel);
    }

    @Override
    public void message(String pattern, String channel, String message) {
        log.info("msg2={} in channel={}",  message, channel);
    }

    @Override
    public void subscribed(String channel, long count) {
        log.info("sub channel={}, count={}",  channel, count);
    }

    @Override
    public void psubscribed(String pattern, long count) {
        log.info("psub pattern={}, count={}", pattern, count);
    }

    @Override
    public void unsubscribed(String channel, long count) {
        log.info("unsub channel={}, count={}",  channel, count);
    }

    @Override
    public void punsubscribed(String pattern, long count) {
        log.info("punsub channel={}, count={}",  pattern, count);
    }

}
