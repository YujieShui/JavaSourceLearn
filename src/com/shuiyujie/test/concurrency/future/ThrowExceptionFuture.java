package com.shuiyujie.test.concurrency.future;

import java.util.concurrent.*;

/**
 * 任务执行过程中抛出 Exception 的情况
 *
 * 1. Callable 任务执行过程中抛出异常，future.get(); 能收到 ExecutionException
 * 2. Callable 任务执行过程中抛出异常，只要没有执行 future.get(); 就不会抛出异常
 * 3. future.isDone() 可以判断任务是否执行完成，抛出异常也是执行完成了
 *
 * @author shui
 * @create 2020-05-08
 **/
public class ThrowExceptionFuture {


    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        Future <Integer> future = threadPool.submit(new CallableTask());

        try {

            for (int i = 0; i < 5; i++) {
                System.out.println(i);
                Thread.sleep(1000);
            }
            System.out.println(future.isDone());
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            // Callable 方法执行过程中发生异常，就会在这里抛出异常
            e.printStackTrace();
        }

    }

}


class CallableTask implements Callable <Integer> {

    @Override
    public Integer call() throws Exception {
        throw new IllegalArgumentException("CallableTask Exception");
    }
}
