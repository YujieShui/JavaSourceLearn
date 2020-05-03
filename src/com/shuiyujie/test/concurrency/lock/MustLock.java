package com.shuiyujie.test.concurrency.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock 接口不会像synchronized一样，在发生异常时主动释放锁
 * 最佳实践是使用 try..catch..finally..，并在 finally 中主动释放锁
 * 保证发生异常的时候，锁一定会被释放
 *
 * @author shui
 * @create 2020-05-03
 **/
public class MustLock {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
            // 执行业务逻辑
            System.out.println(Thread.currentThread().getName() + "开始执行任务....");
        }finally {
            // 使用 lock，先把这个 finally 写上
            lock.unlock();
        }
    }

}
