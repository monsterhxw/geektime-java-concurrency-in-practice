package com.github.monsterhxw.chapter07;

/**
 * @author Xuewei Huang
 * @since 2022-08-28
 */
public class Add10K {
    
    private long countV1 = 0L;
    
    private long countV2 = 0L;
    
    private long countV3 = 0L;
    
    void add10kV1() {
        int idx = 0;
        while (idx++ < 10_000) {
            countV1 += 1L;
        }
    }
    
    void add10kV2() {
        int idx = 0;
        while (idx++ < 10_000) {
            setV2(getV2() + 1L);
        }
    }
    
    synchronized void add10kV3() {
        int idx = 0;
        while (idx++ < 10_000) {
            countV3 += 1L;
        }
    }
    
    synchronized long getV2() {
        return countV2;
    }
    
    synchronized void setV2(long v) {
        countV2 = v;
    }
    
    public long getCountV1() {
        return countV1;
    }
    
    public long getCountV2() {
        return countV2;
    }
    
    public long getCountV3() {
        return countV3;
    }
}
