package com.shuiyujie.test.concurrency.flowcontrol.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 基本用法：
 * 一个线程在等待，然后另一个线程唤醒它
 *
 * @author shui
 * @create 2020-05-05
 **/
public class ConditionDemo1 {

    /***Lock 与 Condition 配置使用，实现多条件变量*/
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public static void main(String[] args) {

        ConditionDemo1 condition = new ConditionDemo1();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                condition.method2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        condition.menthod1();

    }


    void menthod1() {
        lock.lock();
        try {
            System.out.println("条件不满足，开始等待....");
            condition.await();
            System.out.println("条件满足，执行后续代码....");
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }

    }

    void method2() {
        lock.lock();
        try {
            System.out.println("唤醒等待的线程");
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }
}
