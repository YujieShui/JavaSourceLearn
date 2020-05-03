package com.shuiyujie.test.concurrency.lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用 tryLock 来避免死锁
 * 1. 模拟死锁可能发生的情况，即用一个标志位，使线程执行不同的逻辑
 * 2. 创建两把锁，启动两个线程，分别持有对方需要的资源
 * <p>
 * 如果使用的是 lock() 就会直接发生死锁，
 * 但是使用 tryLock() 就有一个等待的时间，能够避免死锁
 * <p>
 * PS：使用 tryLock() 时，应该遵循 tryLock() 注释中给出的使用规范，
 *
 * @author shui
 * @create 2020-05-03
 **/
public class TryLockDeadLock implements Runnable {

    int flag = 0;
    private static Lock lock1 = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        TryLockDeadLock r1 = new TryLockDeadLock();
        TryLockDeadLock r2 = new TryLockDeadLock();
        r1.flag = 0;
        r2.flag = 1;
        new Thread(r1).start();
        new Thread(r2).start();
    }

    @Override
    public void run() {


        for (int i = 0; i < 100; i++) {
            // for 循环中不断重试，尝试同时获取到两把锁

            if (flag == 0) {
                try {
                    // 使用 tryLock() 的时候遵循其注释上的规范
                    if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                        // 如果成功获取锁，那就执行业务逻辑
                        try {
                            System.out.println("线程1获取到了锁1");
                            Thread.sleep(new Random().nextInt(1000));

                            // 获取到锁 1 之后，继续尝试获取锁 2
                            if (lock2.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("线程1获取到了锁2");
                                    System.out.println("线程1成功获取到了两把锁");
                                    // 成功获取到两把锁之后，就可以处理业务逻辑了
                                    // 处理完之后，跳出循环
                                    break;
                                } finally {
                                    lock2.unlock();
                                }
                            } else {
                                System.out.println("线程1获取锁2失败，已重试");
                            }

                        } finally {
                            lock1.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        // 如果没有获取锁，表示获取锁失败
                        // 可以通过 for 循环，或者 while 循环继续重试
                        System.out.println("线程1获取锁1失败，已重试");
                    }
                } catch (InterruptedException e) {
                    // tryLock() 的时候可能发生中断
                    // 发生中断就不需要再尝试了，直接走 InterruptedException 异常
                    e.printStackTrace();
                }

            }

            if (flag == 1) {
                try {
                    // 使用 tryLock() 的时候遵循其注释上的规范
                    if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                        // 如果成功获取锁，那就执行业务逻辑
                        try {
                            System.out.println("线程2获取到了锁1");
                            Thread.sleep(new Random().nextInt(1000));

                            // 获取到锁 1 之后，继续尝试获取锁 2
                            if (lock2.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("线程2获取到了锁2");
                                    System.out.println("线程2成功获取到了两把锁");
                                    break;
                                } finally {
                                    lock2.unlock();
                                }
                            } else {
                                System.out.println("线程2获取锁2失败，已重试");
                            }

                        } finally {
                            lock1.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        // 如果没有获取锁，表示获取锁失败
                        // 可以通过 for 循环，或者 while 循环继续重试
                        System.out.println("线程2获取锁1失败，已重试");
                    }
                } catch (InterruptedException e) {
                    // tryLock() 的时候可能发生中断
                    // 发生中断就不需要再尝试了，直接走 InterruptedException 异常
                    e.printStackTrace();
                }

            }

        }
    }
}
