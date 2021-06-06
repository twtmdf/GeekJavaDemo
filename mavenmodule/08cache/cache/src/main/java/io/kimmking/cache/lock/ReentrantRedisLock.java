package io.kimmking.cache.lock;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Redis distributed lock implementation.
 */
@Slf4j
public class ReentrantRedisLock implements ReentrantLock {

    private static String LOCK_SCRIPT = "local key_ttl = redis.call('TTL',KEYS[1])\n" +
            "if(key_ttl<=0) then\n" +
            "    key_ttl=1\n" +
            "end\n" +
            "local key_value = redis.call('GET', KEYS[1])\n" +
            "if(key_value) then\n" +
            "    local start = string.find(key_value,\"&\")\n" +
            "    local value = string.sub(key_value,1,start-1)\n" +
            "    local entrant_numbers = string.sub(key_value,start+1,string.len(key_value))\n" +
            "    if(value == ARGV[1]) then\n" +
            "        return redis.call('SET', KEYS[1], ARGV[1]..'&'..(entrant_numbers+1),'PX',key_ttl*1000)\n" +
            "    end\n" +
            "else\n" +
            "    return redis.call('SET', KEYS[1], ARGV[1]..'&1','PX', ARGV[2])\n" +
            "end";
    private static String RELEASE_LOCK_SCRIPT = "local key_ttl = redis.call('TTL',KEYS[1])\n" +
            "if(key_ttl<=0) then\n" +
            "    key_ttl=1\n" +
            "end\n" +
            "local key_value = redis.call('GET', KEYS[1])\n" +
            "if(key_value) then\n" +
            "    local start = string.find(key_value,\"&\")\n" +
            "    local value = string.sub(key_value,1,start-1);\n" +
            "    local entrant_numbers = string.sub(key_value,start+1,string.len(key_value))\n" +
            "    if(value == ARGV[1]) then\n" +
            "        if(entrant_numbers-ARGV[2]>0) then \n" +
            "            return redis.call('SET', KEYS[1], ARGV[1]..'&'..(entrant_numbers-ARGV[2]),'PX',key_ttl)\n" +
            "        else\n" +
            "            return  redis.call('DEL', KEYS[1])\n" +
            "        end\n" +
            "    end\n" +
            "end";

    public static final String OK = "OK";

    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    /**
     * Lock key path.
     */
    private String lockKey;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 5 * 60 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 1000;

    private volatile boolean locked = false;

    public ReentrantRedisLock(String lockKey) {
        this.lockKey = lockKey + "_lock";
    }


    public ReentrantRedisLock(String lockKey, int timeoutMsecs) {
        this(lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    public ReentrantRedisLock(String lockKey, int timeoutMsecs, int expireMsecs) {
        this(lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    @Override
    public String getLockKey() {
        return lockKey;
    }

    @Override
    public boolean lock() {
        return lock(getLockValue());
    }

    private String getLockValue() {
        return MDC.get("traceId");
    }

    @Override
    public boolean lock(String value) {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            log.debug("lock args value:" + Arrays.asList(value, String.valueOf(expireMsecs)).toString());
            Object obj = getStringRedisTemplate().execute(new DefaultRedisScript(LOCK_SCRIPT, String.class), Arrays.asList(getLockKey()), value, Objects.toString(expireMsecs));
            log.debug("lock Object value:" + obj);
            if (obj != null && OK.equalsIgnoreCase(obj.toString())) {
                locked = true;
                return true;
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;

            /*
                延迟100 毫秒,  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
                只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
                使用随机的等待时间可以一定程度上保证公平性
             */
            int random = (int) (Math.random() * 20) - 10;
            try {
                Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS + random);
            } catch (InterruptedException exception) {
                throw new RuntimeException(" Redis lock InterruptedException", exception);
            }
        }
        return false;
    }

    @Override
    public void unlock() {
        unlock(1);
    }

    @Override
    public void unlock(int lockNum) {
        if (locked) {
            log.debug(" release lock args value:" + Arrays.asList(getLockValue(), String.valueOf(lockNum)).toString());
            getStringRedisTemplate().execute(new DefaultRedisScript(RELEASE_LOCK_SCRIPT, Long.class), Arrays.asList(getLockKey()), getLockValue(), Objects.toString(lockNum));
            locked = false;
        }
    }

    @Override
    public void close() {
        unlock();
    }
}