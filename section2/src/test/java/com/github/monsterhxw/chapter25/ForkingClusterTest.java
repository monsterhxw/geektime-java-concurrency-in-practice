package com.github.monsterhxw.chapter25;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Xuewei Huang
 * @since 2022-09-18
 */
class ForkingClusterTest {
    
    @Test
    void testGeocoder() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ForkingCluster forkingCluster = new ForkingCluster(executor);
        Integer geocoder = forkingCluster.geocoder();
        assertNotNull(geocoder);
        System.out.println("Forking Cluster geo coder: " + geocoder);
        executor.shutdown();
    }
}