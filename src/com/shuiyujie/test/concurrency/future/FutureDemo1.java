package com.shuiyujie.test.concurrency.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Future 的基本使用方法：
 * 1. 创建一个类实现 Callable 接口，重写它的 call 方法
 * 2. 线程池中 submit 这个类，返回一个 Future 对象
 * 3. 这里的 Future 对象是一个占位符，其中还没有返回值
 * 4. 调用 get() 方法，在任务执行完之后，就会填充 Future 获取返回值
 *
 * @author shui
 * @create 2020-05-06
 **/
public class FutureDemo1 {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        Future future = threadPool.submit(new CallableTask());
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class CallableTask implements Callable {
        @Override
        public Object call() throws Exception {
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }

}
