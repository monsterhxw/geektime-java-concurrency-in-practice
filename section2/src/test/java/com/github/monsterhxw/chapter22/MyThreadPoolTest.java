package com.github.monsterhxw.chapter22;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Xuewei Huang
 * @since 2022-09-15
 */
class MyThreadPoolTest {
    
    @Test
    void testUsingMyThreadPool() {
        // 创建有界阻塞队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2);
        // 线程池大小
        int poolSize = 10;
        // 创建线程池
        MyThreadPool threadPool = new MyThreadPool(10, workQueue);
        threadPool.execute(() -> System.out.println("Hello" + Thread.currentThread().getName()));
    }
}