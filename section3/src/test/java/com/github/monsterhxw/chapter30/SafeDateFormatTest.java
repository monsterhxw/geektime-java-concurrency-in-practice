package com.github.monsterhxw.chapter30;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Xuewei Huang
 * @since 2022-09-20
 */
class SafeDateFormatTest {
    
    @Test
    void testGet() {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(
                    () -> System.out.println(Thread.currentThread().getName() + ": " + SafeDateFormat.get()));
            threadList.add(t);
        }
        threadList.forEach(Thread::start);
    }
}