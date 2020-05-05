package com.shuiyujie.test.concurrency.flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch 一等多的使用场景：
 * 一个产品要发货之前，需要先经过 5 个人的检查
 * 5 个人检查完才可以发货
 *
 * 输出结果：
 * 等待完成检查......
 * 检查员1正在检查中......
 * 检查员2正在检查中......
 * 检查员4正在检查中......
 * 检查员5正在检查中......
 * 检查员3正在检查中......
 * 5 个人都检查完毕，可以发货了......
 *
 * @author shui
 * @create 2020-05-05
 **/
public class CountDownLatchDemo1 {

    public static void main(String[] args) {

        /**
         * 计数器：检查次数
         */
        CountDownLatch latch = new CountDownLatch(5);

        // 由 5 个线程来完成检查任务
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 1; i < 6; i++) {
            final int number = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        // 表示在执行检查操作
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("检查员" + number + "正在检查中......");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 检查完毕，contDownLathc 减少 1
                        latch.countDown();
                    }
                }
            };
            executorService.submit(runnable);
        }

        System.out.println("等待完成检查......");
        try {
            latch.await();
            System.out.println("5 个人都检查完毕，可以发货了......");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
