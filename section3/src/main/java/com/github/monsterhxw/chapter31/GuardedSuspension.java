package com.github.monsterhxw.chapter31;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @author Xuewei Huang
 * @since 2022-09-20
 */
public class GuardedSuspension<T> {
    
    /**
     * 受保护的对象
     */
    private T guardedObject;
    
    private final Lock lock = new ReentrantLock();
    
    private final Condition done = lock.newCondition();
    
    private final int timeout;
    
    public GuardedSuspension(T guardedObject, int timeout) {
        this.guardedObject = guardedObject;
        this.timeout = timeout;
    }
    
    public GuardedSuspension(T guardedObject) {
        this(guardedObject, 1);
    }
    
    /**
     * 获取受保护对象
     *
     * @param predicate 谓词
     * @return 受保护对象
     */
    T get(Predicate<T> predicate) {
        lock.lock();
        try {
            // MESA 管程推荐写法
            while (!predicate.test(guardedObject)) {
                done.await(timeout, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        // 返回非空的受保护对象
        return guardedObject;
    }
    
    /**
     * 事件通知方法
     *
     * @param guardedObject 受保护对象
     */
    void onChanged(T guardedObject) {
        lock.lock();
        try {
            this.guardedObject = guardedObject;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
