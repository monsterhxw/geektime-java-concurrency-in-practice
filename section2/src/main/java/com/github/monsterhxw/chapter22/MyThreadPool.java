package com.github.monsterhxw.chapter22;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 简化的线程池，仅用来说明工作原理
 *
 * @author Xuewei Huang
 * @since 2022-09-14
 */
public class MyThreadPool {
    
    /**
     * 利用阻塞队列实现 {生产者-消费者模式}
     */
    private BlockingQueue<Runnable> workQueue;
    
    /**
     * 保存内部工作线程
     */
    private final List<WorkerThread> workerThreads = new ArrayList<>();
    
    MyThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        // 创建工作线程
        for (int i = 0; i < poolSize; i++) {
            workerThreads.add(new WorkerThread());
        }
    }
    
    public void execute(Runnable command) {
        try {
            workQueue.put(command);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 工作线程负责消费任务并执行任务
     */
    private class WorkerThread extends Thread {
        
        @Override
        public void run() {
            // 循环取任务并执行
            while (true) {
                try {
                    Runnable task = workQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
