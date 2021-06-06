package io.kimmking.cache.lock;

import org.springframework.data.redis.core.RedisTemplate;

public interface Lock extends AutoCloseable {

    boolean lock();

    void unlock();

    String getLockKey();

    @Override
    void close();

    default RedisTemplate<String,String> getStringRedisTemplate(){
        RedisTemplate stringRedisTemplate = (RedisTemplate)SpringContextUtil.getBean("stringRedisTemplate");
        if(null == stringRedisTemplate){
            throw  new RuntimeException("get redisTemplate is null");
        }
        return stringRedisTemplate;
    }
}