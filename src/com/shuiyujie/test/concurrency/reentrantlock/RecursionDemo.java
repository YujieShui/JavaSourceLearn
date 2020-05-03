package com.shuiyujie.test.concurrency.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：演示 ReentrantLock 重入特性
 *
 * 通过 lock.getHoldCount()<5 来判断重入次数
 * 让其重入 5 次，演示重入特性
 */
public class RecursionDemo {

    private static ReentrantLock lock = new ReentrantLock();

    private static void accessResource() {
        lock.lock();
        try {
            System.out.println("已经对资源进行了处理");
            if (lock.getHoldCount()<5) {
                System.out.println(lock.getHoldCount());
                accessResource();
                System.out.println(lock.getHoldCount());
            }
        } finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        accessResource();
    }
}
