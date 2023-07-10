package com.wojto.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

public class SemaphoreQueue {

    private static Logger LOG = LoggerFactory.getLogger(SemaphoreQueue.class);

    int item;

    private Semaphore consumerSemaphore = new Semaphore(0);

    private Semaphore producerSempahore = new Semaphore(1);

    public void get() {
        try {
            consumerSemaphore.acquire();
        } catch (InterruptedException e) {
            LOG.error("InterruptedException caught while trying to acquire consumerSemaphore");
        }

        LOG.info("Consumer consumed item: " + item);

        producerSempahore.release();
    }

    public void put(int item) {
        try {
            producerSempahore.acquire();
        } catch (InterruptedException e) {
            LOG.error("InterruptedException caught while trying to acquire producerSemaphore");
        }

        this.item = item;

        LOG.info("Producer produced item: " + item);

        consumerSemaphore.release();
    }
}
