package com.github.monsterhxw.chapter25;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Xuewei Huang
 * @since 2022-09-18
 */
public class AsyncGettingPrice {
    
    /**
     * 创建线程池
     */
    private final static ExecutorService EXECUTOR = Executors.newFixedThreadPool(3);
    
    /**
     * 创建阻塞队列
     */
    private final static BlockingQueue<Integer> BLOCKING_QUEUE = new LinkedBlockingQueue<>();
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 异步向电商 S1 询价
        Future<Integer> f1 = EXECUTOR.submit(() -> getPriceByS1());
        
        // 异步向电商 S2 询价
        Future<Integer> f2 = EXECUTOR.submit(() -> getPriceByS2());
        
        // 异步向电商 S3 询价
        Future<Integer> f3 = EXECUTOR.submit(() -> getPriceByS3());
        
        //        // 获取电商 S1 报价并保存
        //        Integer r1 = f1.get();
        //        EXECUTOR.execute(() -> save(r1));
        //
        //        // 获取电商 S2 报价并保存
        //        Integer r2 = f2.get();
        //        EXECUTOR.execute(() -> save(r2));
        //
        //        // 获取电商 S3 报价并保存
        //        Integer r3 = f3.get();
        //        EXECUTOR.execute(() -> save(r3));
        
        // 电商 S1 报价异步进入阻塞队列
        EXECUTOR.execute(() -> {
            try {
                BLOCKING_QUEUE.put(f1.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        
        // 电商 S2 报价异步进入阻塞队列
        EXECUTOR.execute(() -> {
            try {
                BLOCKING_QUEUE.put(f2.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        
        // 电商 S3 报价异步进入阻塞队列
        EXECUTOR.execute(() -> {
            try {
                BLOCKING_QUEUE.put(f3.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        
        // 异步保存所有报价
        for (int i = 0; i < 3; i++) {
            Integer r = BLOCKING_QUEUE.take();
            EXECUTOR.execute(() -> save(r));
        }
        
        EXECUTOR.shutdown();
    }
    
    private static Integer getPriceByS1() {
        return 10;
    }
    
    private static Integer getPriceByS2() {
        return 20;
    }
    
    private static Integer getPriceByS3() {
        return 30;
    }
    
    private static void save(Integer price) {
        System.out.println("Save price: " + price);
    }
}
