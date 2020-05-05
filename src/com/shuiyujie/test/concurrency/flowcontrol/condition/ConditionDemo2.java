package com.shuiyujie.test.concurrency.flowcontrol.condition;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过 Condition 来实现一个生产者、消费者的案例
 * 1. 一个队列，两个条件变量（分别表示"非空"和"非满"）
 * 2. 生产者和消费者的类，不断产生和消费
 * 2.1 生产者：队满时不再生产，等待"非满"的条件来唤醒
 * 2.2 消费者：队空时不再消费，等待"非空"的条件来唤醒
 *
 * @author shui
 * @create 2020-05-05
 **/
public class ConditionDemo2 {

    private int queueSize = 10;
    private PriorityQueue <Integer> queue = new PriorityQueue <>(queueSize);
    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public static void main(String[] args) {
        ConditionDemo2 conditionDemo2 = new ConditionDemo2();
        Producer producer = conditionDemo2.new Producer();
        Consumer consumer = conditionDemo2.new Consumer();

        producer.start();
        consumer.start();
    }

    class Producer extends Thread {

        @Override
        public void run() {

            while (true) {
                lock.lock();
                try {
                    while (queue.size() == queueSize) {
                        try {
                            // 此时队列是满的，我无法继续生产
                            // 不满足队列不空这个条件，那就用 await() 等着
                            // 当满足队列不满这个条件的时候，就会被唤醒，继续生产
                            System.out.println("队列已满...");
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);
                    System.out.println("生产者继续生产，当前队列大小为: " + queue.size());
                    notEmpty.signalAll();
                } finally {
                    lock.unlock();
                }
            }

        }
    }

    class Consumer extends Thread {

        @Override
        public void run() {

            while (true) {
                lock.lock();
                try {
                    while (queue.size() == 0) {
                        // 队列是空的时候，我就死等
                        try {
                            System.out.println("队列已空...");
                            // 此时队列是空的，不满足队列不空这个条件
                            // 那么我就 await()，等待被唤醒
                            // 唤醒之后继续消费
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    System.out.println("消费者继续生产，当前队列大小为: " + queue.size());
                    notFull.signalAll();
                } finally {
                    lock.unlock();
                }
            }


        }
    }


}
