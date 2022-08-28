package com.github.monsterhxw.chapter07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Xuewei Huang
 * @since 2022-08-28
 */
class Add10KTest {
    
    @Test
    void testV1() throws InterruptedException {
        final Add10K instance = new Add10K();
        
        Thread t1 = new Thread(instance::add10kV1);
        Thread t2 = new Thread(instance::add10kV1);
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        assertNotEquals(20_000, instance.getCountV1());
        
        System.out.println("Version1 count : " + instance.getCountV1());
    }
    
    @Test
    void testV2() throws InterruptedException {
        
        final Add10K instance = new Add10K();
        
        Thread t1 = new Thread(instance::add10kV2);
        Thread t2 = new Thread(instance::add10kV2);
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        assertNotEquals(20_000, instance.getCountV2());
        
        System.out.println("Version2 count : " + instance.getCountV2());
    }
    
    @Test
    void testV3() throws InterruptedException {
        
        final Add10K instance = new Add10K();
        
        Thread t1 = new Thread(instance::add10kV3);
        Thread t2 = new Thread(instance::add10kV3);
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        assertEquals(20_000, instance.getCountV3());
        
        System.out.println("Version3 count : " + instance.getCountV3());
    }
    
    @Test
    void testV4() throws InterruptedException {
        
        final Add10K instance = new Add10K();
        
        Thread t1 = new Thread(instance::add10kV4);
        Thread t2 = new Thread(instance::add10kV4);
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        assertEquals(20_000, instance.getCountV4());
        
        System.out.println("Version4 count : " + instance.getCountV4());
    }
}