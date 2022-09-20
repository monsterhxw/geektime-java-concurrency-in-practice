package com.github.monsterhxw.chapter26;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @author Xuewei Huang
 * @since 2022-09-18
 */
public class ForkJoinPractice {
    
    public static void main(String[] args) {
        // 创建分治任务线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        
        // 创建分治任务
        FibonacciTask fibonacciTask = new FibonacciTask(30);
        
        // 启动分治任务
        Integer result = forkJoinPool.invoke(fibonacciTask);
        
        // 输出结果
        System.out.println(result);
    }
    
    static class FibonacciTask extends RecursiveTask<Integer> {
        
        private final int n;
        
        FibonacciTask(int n) {
            this.n = n;
        }
        
        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            FibonacciTask fibonacciTask1 = new FibonacciTask(n - 1);
            // 创建子任务 1
            fibonacciTask1.fork();
            
            FibonacciTask fibonacciTask2 = new FibonacciTask(n - 2);
            
            // 等待子任务结果，并合并结果
            return fibonacciTask2.compute() + fibonacciTask1.join();
        }
    }
}
