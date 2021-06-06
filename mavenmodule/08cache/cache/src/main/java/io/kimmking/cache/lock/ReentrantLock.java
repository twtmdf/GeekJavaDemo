package io.kimmking.cache.lock;

public interface ReentrantLock extends Lock {
    void unlock(int lockNumber);
    boolean lock(String  value);

}