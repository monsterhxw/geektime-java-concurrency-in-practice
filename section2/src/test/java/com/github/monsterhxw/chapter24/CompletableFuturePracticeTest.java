package com.github.monsterhxw.chapter24;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Xuewei Huang
 * @since 2022-09-17
 */
class CompletableFuturePracticeTest {
    
    @Test
    void testThenApply() {
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> "Hello World")
                .thenApply(s -> s.concat(" ").concat("QQ")).thenApply(String::toUpperCase);
        
        assertEquals("HELLO WORLD QQ", f0.join());
        
        System.out.println(f0.join());
    }
    
    @Test
    void testApplyToEither() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            int second = CompletableFuturePractice.getRandom(5, 10);
            CompletableFuturePractice.sleep(second, TimeUnit.SECONDS);
            return String.valueOf(second);
        });
        
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            int second = CompletableFuturePractice.getRandom(5, 10);
            CompletableFuturePractice.sleep(second, TimeUnit.SECONDS);
            return String.valueOf(second);
        });
        
        CompletableFuture<String> f3 = f1.applyToEither(f2, s -> s);
        
        System.out.println(f3.join());
    }
    
    @Test
    void testExceptionally() {
        CompletableFuture<Integer> f0 = CompletableFuture.supplyAsync(() -> 7 / 0).thenApply(r -> r * 10)
                .exceptionally(e -> 0).handle((r, e) -> r + 1);
        assertEquals(1, f0.join());
        System.out.println(f0.join());
    }
}