package com.github.monsterhxw.chapter25;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Xuewei Huang
 * @since 2022-09-18
 */
public class CompletionServicePractice {
    
    /**
     * 创建线程池
     */
    private final static ExecutorService EXECUTOR = Executors.newFixedThreadPool(3);
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 创建 CompletionService
        CompletionService<Integer> cs = new ExecutorCompletionService<>(EXECUTOR);
        
        // 异步向电商 S1 询价
        cs.submit(CompletionServicePractice::getPriceByS1);
        
        // 异步向电商 S2 询价
        cs.submit(CompletionServicePractice::getPriceByS2);
        
        // 异步向电商 S3 询价
        cs.submit(CompletionServicePractice::getPriceByS3);
        
        // 将询价结果异步保存到数据库
        for (int i = 0; i < 3; i++) {
            Integer res = cs.take().get();
            EXECUTOR.execute(() -> save(res));
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