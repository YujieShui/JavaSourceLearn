package com.shuiyujie.test.concurrency.future;

import java.util.concurrent.*;

/**
 * FutureTask 的使用：
 * 1. 创建一个 Task 实现 Callable 接口
 * 2. 创建 FutureTask 传入 Task
 * 3. 可以通过 Thread 或者线程池来执行这个 FutureTask
 * 4. 最后通过 FutureTask.get() 获取结果
 *
 * @author shui
 * @create 2020-05-09
 **/
public class FutureTaskDemo {

    public static void main(String[] args) {

        Task task = new Task();
        FutureTask <Integer> integerFutureTask = new FutureTask <Integer>(task);

        /** 用线程的方法，执行 FutureTask **/
//        new Thread(integerFutureTask).start();
        /**用线程池，执行 FutureTask**/
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(integerFutureTask);

        try {
            System.out.println(integerFutureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    static class Task implements Callable <Integer> {
        @Override
        public Integer call() throws Exception {
            Integer sum = 0;
            for (int i = 0; i < 1000; i++) {
                sum += i;
            }
            return sum;
        }
    }
}