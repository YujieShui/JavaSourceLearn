package com.shuiyujie.test.concurrency.future;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 多个任务用Future数组来获取结果
 *
 * @author shui
 * @create 2020-05-07
 **/
public class MultiFuture {


    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        ArrayList <Future> futureList = new ArrayList <>();
        for (int i = 0; i < 20; i++) {
            Future future = threadPool.submit(new CallableTask());
            futureList.add(future);
        }

        for (Future <Integer> f : futureList) {
            try {
                Integer integer = f.get();
                System.out.println(integer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
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
