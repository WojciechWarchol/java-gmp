package com.wojto.consumer;

import com.wojto.queue.SemaphoreQueue;

public class Consumer implements Runnable {

    SemaphoreQueue semaphoreQueue;

    public Consumer(SemaphoreQueue semaphoreQueue) {
        this.semaphoreQueue = semaphoreQueue;
        new Thread(this, "Consumer").start();
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            semaphoreQueue.get();
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
