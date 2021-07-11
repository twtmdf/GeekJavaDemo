package io.kimmking.kmq.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SecondQueue {
    private final AtomicInteger count = new AtomicInteger();
    private final int capacity = 10000;
    private final ReentrantLock putLock = new ReentrantLock();
    private final Condition notFull = putLock.newCondition();
    private final ReentrantLock takeLock = new ReentrantLock();
    private final Condition notEmpty = takeLock.newCondition();

    private KmqMessage[] queues = new KmqMessage[10001];
    private AtomicInteger read = new AtomicInteger(0);
    private AtomicInteger write = new AtomicInteger(0);

    public boolean offer(KmqMessage message) {
        if (message == null) throw new NullPointerException();
        final AtomicInteger count = this.count;
        if (count.get() == capacity)
            return false;
        int c = -1;
//        LinkedBlockingQueue.Node<KmqMessage> node = new LinkedBlockingQueue.Node<E>(e);
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            if (count.get() < capacity) {
                enqueue(message);
                c = count.getAndIncrement();
                if (c + 1 < capacity)
                    notFull.signal();
            }
        } finally {
            putLock.unlock();
        }
        if (c == 0)
            signalNotEmpty();
        return c >= 0;
    }

    private void enqueue(KmqMessage message) {
        // assert putLock.isHeldByCurrentThread();
        // assert last.next == null;
//        last = last.next = node;
        queues[write.getAndIncrement()] = message;
        System.out.println("write == "+write);
    }
    private void signalNotEmpty() {
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }
    private void signalNotFull() {
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }


    public KmqMessage poll(long timeout, TimeUnit unit) throws InterruptedException {
        KmqMessage x = null;
        int c = -1;
        long nanos = unit.toNanos(timeout);
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                if (nanos <= 0)
                    return null;
                nanos = notEmpty.awaitNanos(nanos);
            }
            x = dequeue();
            c = count.getAndDecrement();
            if (c > 1)
                notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
        if (c == capacity)
            signalNotFull();
        return x;
    }
    private KmqMessage dequeue() {
        // assert takeLock.isHeldByCurrentThread();
        // assert head.item == null;
//        LinkedBlockingQueue.Node<E> h = head;
//        LinkedBlockingQueue.Node<E> first = h.next;
//        h.next = h; // help GC
//        head = first;
//        E x = first.item;
//        first.item = null;
        KmqMessage x = queues[read.getAndIncrement()];
        return x;
    }

    public boolean offset(int offset) {
        if (count.intValue() < offset) {
            return Boolean.FALSE;
        }
        read.addAndGet(offset);
        if (read.intValue()>=capacity) {
            read.addAndGet(-1*offset);
        }else {
            count.addAndGet(-1 * offset);
        }
        return Boolean.TRUE;
    }

}
