package com.github.monsterhxw.chapter05;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xuewei Huang
 * @since 2022-08-25
 */
public class Allocator {
    
    private static volatile Allocator instance;
    
    
    private final List<Object> als = new ArrayList<>();
    
    /**
     * 一次性申请所有资源
     *
     * @param from 转出
     * @param to   转入
     */
    synchronized boolean apply(Object from, Object to) {
        if (als.contains(from) || als.contains(to)) {
            return false;
        }
        als.add(from);
        return true;
    }
    
    /**
     * 归还资源
     *
     * @param from 转出
     * @param to   转入
     */
    synchronized void free(Object from, Object to) {
        als.remove(from);
        als.remove(to);
    }
    
    private Allocator() {
    
    }
    
    public static Allocator getInstance() {
        if (instance == null) {
            synchronized (Allocator.class) {
                if (instance == null) {
                    instance = new Allocator();
                }
            }
        }
        return instance;
    }
}
