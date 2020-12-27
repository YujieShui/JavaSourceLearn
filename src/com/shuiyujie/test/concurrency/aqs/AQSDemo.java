package com.shuiyujie.test.concurrency.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * @author shui
 * @create 2020-05-06
 **/
public class AQSDemo {

    /**
     * 里面都有一个 Sync extends AbstractQueuedSynchronizer（AQS）
     *
     * AQS 的比喻——群面、单面：
     * 安排就坐、叫好、先来后到等，这些是 HR 的职责，也是 AQS 的职责
     * 面试官不管面试者是否号码冲突，面试者是否需要一个地方休息，这些都交给 HR 来做
     *
     * Semaphore：一个人面试完了以后，后一个人才能继续面试
     * CountDownLatch：群面，等待10人到齐，一起面试
     *
     */
    private Semaphore semaphore = new Semaphore(5, true);
    private CountDownLatch latch = new CountDownLatch(3);

}
