package com.github.monsterhxw.chapter24;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Xuewei Huang
 * @since 2022-09-17
 */
public class CompletableFuturePractice {
    
    public static void main(String[] args) {
        // 任务 1: 洗水壶 -> 烧开水
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            System.out.println("Task 1: 洗水壶...");
            sleep(1, TimeUnit.SECONDS);
            
            System.out.println("Task 1: 烧开水...");
            sleep(15, TimeUnit.SECONDS);
        });
        
        // 任务 2: 洗茶壶 -> 洗茶杯 -> 拿茶叶
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 2: 洗茶壶...");
            sleep(1, TimeUnit.SECONDS);
            
            System.out.println("Task 2: 洗茶杯...");
            sleep(2, TimeUnit.SECONDS);
            
            System.out.println("Task 2: 拿茶叶...");
            sleep(1, TimeUnit.SECONDS);
            
            return "龙井";
        });
        
        // 任务 3: 任务 1 和任务 2 完成后执行: 泡茶
        CompletableFuture<String> task3 = task1.thenCombine(task2, (__, task2Result) -> {
            System.out.println("Task 3: 拿到茶叶: " + task2Result);
            System.out.println("Task 3: 泡茶...");
            return "上茶: " + task2Result;
        });
        
        System.out.println(task3.join());
    }
    
    private static void sleep(int time, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException ignore) {
        }
    }
}
