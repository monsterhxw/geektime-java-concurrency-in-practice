package com.github.monsterhxw.chapter25;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Xuewei Huang
 * @since 2022-09-18
 */
public class ForkingCluster {
    
    private final ExecutorService executor;
    
    private final CompletionService<Integer> cs;
    
    public ForkingCluster(ExecutorService executor) {
        this.executor = executor;
        this.cs = new ExecutorCompletionService<>(executor);
    }
    
    public Integer geocoder() throws InterruptedException, ExecutionException {
        // 用于保存 Future 对象
        List<Future<Integer>> futures = new ArrayList<>(3);
        
        // 提交异步任务，并保存 future 到 futures
        Future<Integer> f1 = cs.submit(this::geocoderByS1);
        futures.add(f1);
        
        Future<Integer> f2 = cs.submit(this::geocoderByS2);
        futures.add(f2);
        
        Future<Integer> f3 = cs.submit(this::geocoderByS3);
        futures.add(f3);
        
        // 获取最快返回的任务执行结果
        Integer res = 0;
        
        try {
            // 只要有一个成功返回，则 break
            for (int i = 0; i < 3; i++) {
                res = cs.take().get();
                // 简单通过判空来检查是否成功返回
                if (Objects.nonNull(res)) {
                    break;
                }
            }
        } finally {
            // 取消所有任务
            for (Future<Integer> future : futures) {
                future.cancel(true);
            }
        }
        
        return res;
    }
    
    private Integer geocoderByS1() {
        return getRandom(1, 10);
    }
    
    private Integer geocoderByS2() {
        return getRandom(21, 30);
    }
    
    private Integer geocoderByS3() {
        return getRandom(31, 40);
    }
    
    public static int getRandom(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}
