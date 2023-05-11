package com.wojto.producer;

import com.wojto.queue.SemaphoreQueue;

public class Producer implements Runnable {

    SemaphoreQueue semaphoreQueue;

    public Producer(SemaphoreQueue semaphoreQueue) {
        this.semaphoreQueue = semaphoreQueue;
        new Thread(this, "Producer").start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            semaphoreQueue.put(i);
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
