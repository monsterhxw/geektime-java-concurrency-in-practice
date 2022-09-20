package com.github.monsterhxw.chapter30;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Xuewei Huang
 * @since 2022-09-20
 */
public class ThreadId {
    
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    
    private static final ThreadLocal<Long> NEXT_ID_TL = ThreadLocal.withInitial(NEXT_ID::getAndIncrement);
    
    public static long get() {
        return NEXT_ID_TL.get();
    }
}
