package com.shuiyujie.test.concurrency.flowcontrol.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 使用信号量进行限流，效果是：
 * 一个线程池里面有多个线程在运行，但是只有获取到信号量的线程才能执行
 * <p>
 * 信号量能起到限流的作用，比如说我一个接口本身有访问的上限，
 * 不能太多的线程同时去访问这个接口，否则系统就奔溃了，
 * 那么就可以使用信号量来做这个限制
 * <p>
 * 所以信号量就像是一个许可证，只有拿到许可证的才能进入
 *
 * @author shui
 * @create 2020-05-05
 **/
public class SemaphoreDemo {

    public static void main(String[] args) {

        // 两个参数，第一个表示信号量的数量，第二个表示是否是公平的（一般都用公平的）
        Semaphore semaphore = new Semaphore(3, true);
        ExecutorService threadPool = Executors.newFixedThreadPool(50);

        for (int i = 0; i < 100; i++) {
            threadPool.submit(new Task(semaphore));
        }
        threadPool.shutdown();
    }

}

class Task implements Runnable {

    private Semaphore semaphore;

    public Task(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {

        try {
            // 获取到信号量的就能继续执行
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + "获得信号量，正在运行...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 获取之后要释放
            semaphore.release();
            System.out.println(Thread.currentThread().getName() + "运行结束...");
        }

    }
}
