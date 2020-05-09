package com.shuiyujie.test.concurrency.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 使用 Lambda 表达式来创建 Callable 对象
 *
 * @author shui
 * @create 2020-05-07
 **/
public class FutureDemo2 {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(3000);
                return new Random().nextInt();
            }
        };

        Future future = threadPool.submit(callable);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
