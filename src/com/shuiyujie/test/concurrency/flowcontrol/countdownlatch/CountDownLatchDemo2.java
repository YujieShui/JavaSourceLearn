package com.shuiyujie.test.concurrency.flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shui
 * @create 2020-05-05
 * @see java.util.concurrent.CountDownLatch 多等一的场景：
 * @see CountDownLatchDemo1 中演示了一等多的场景，
 * CountDownLatch 另一个使用场景是多等一
 * <p>
 * 比如说我们要做压测，就要模拟一批请求，但是这一批请求必须要一次性发出去，
 * 如果模拟一个请求就发送，模拟一个请求就发送，就达不到压测的效果，
 * 这里就可以使用 CountDownLatch 的多等一功能了
 * <p>
 * 这里模拟一个场景，就是运动会上，5名运动员等待裁判发令，
 * 也就是5个线程都准备好了，等一个线程的通知，5个线程就同时开始执行
 **/
public class CountDownLatchDemo2 {

    public static void main(String[] args) {

        // 多等一，运动员等待裁判发令
        CountDownLatch begin = new CountDownLatch(1);
        // 一等多，记录员等运动员都跑完
        CountDownLatch end = new CountDownLatch(5);
        ExecutorService service = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            final int no = i + 1;

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("运动员" + no + "准备完毕....");
                    try {
                        // 等待信号
                        begin.await();
                        System.out.println("运动员" + no + "开始跑步");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("运动员" + no + "跑到终点了");
                    } catch (InterruptedException e) {
                        end.countDown();
                        e.printStackTrace();
                    }
                }
            };
            service.submit(runnable);
        }

        try {
            Thread.sleep(5000);
            System.out.println("发令枪响，比赛开始！");
            begin.countDown();

            end.await();
            System.out.println("比赛结束...");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
