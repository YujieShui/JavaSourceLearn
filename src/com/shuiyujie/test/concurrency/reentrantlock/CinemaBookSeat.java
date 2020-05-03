package com.shuiyujie.test.concurrency.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：演示多线程预定电影院座位
 *
 * 模拟选座位的时候，只能一个个依次选座位，
 * 也就是说要获取到 lock 锁的才能够选座位，即串行化选座位
 */
public class CinemaBookSeat {

    private static ReentrantLock lock = new ReentrantLock();

    private static void bookSeat() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始预定座位");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "完成预定座位");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> bookSeat()).start();
        new Thread(() -> bookSeat()).start();
        new Thread(() -> bookSeat()).start();
        new Thread(() -> bookSeat()).start();
    }
}
