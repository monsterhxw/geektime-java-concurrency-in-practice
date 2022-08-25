package com.github.monsterhxw.chapter05;

/**
 * @author Xuewei Huang
 * @since 2022-08-25
 */
public class Account {
    
    /**
     * actr 应该为单例
     */
    private final Allocator allocator;
    
    private int balance;
    
    Account() {
        this.allocator = Allocator.getInstance();
        this.balance = 0;
    }
    
    /**
     * 转账
     *
     * @param target 转出账户
     * @param amt    金额
     */
    void transfer(Account target, int amt) {
        // 一次性申请转出账户和转入账户，直到成功
        while (!allocator.apply(this, target)) {
        }
        try {
            // 锁定转出账户
            synchronized (this) {
                // 锁定转入账户
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            allocator.free(this, target);
        }
    }
}
