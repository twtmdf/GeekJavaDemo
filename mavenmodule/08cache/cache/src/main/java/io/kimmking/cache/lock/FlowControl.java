package io.kimmking.cache.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class FlowControl {
    @Autowired
    private RedisTemplate redisTemplate;
//    可以先获取锁后再扣减
    public boolean flowControl(String key,int max,long secondTime){
        long total = 1L;
        String redisKey = "stock-number:"+key;
        try {
            ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
            if (valueOperations.get(redisKey) == null) {
                //如果redis目前没有这个key，创建并赋予0，有效时间为60s，应由业务决定有效期
                valueOperations.set(redisKey, 1L,secondTime, TimeUnit.SECONDS);
            } else {
                //获取加1后的值
                total = valueOperations.increment(redisKey,1);
            }
        } catch (Exception e) {
            log.error("流量控制组件:执行计数操作失败,无法执行计数");
        }
        //判断是否已超过最大值，超过则返回false
        if (total > max) {
            return false;
        }
        return true;
    }
}
