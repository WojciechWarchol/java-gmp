package com.wojto;

import com.wojto.consumer.Consumer;
import com.wojto.producer.Producer;
import com.wojto.queue.SemaphoreQueue;



public class Main {
    public static void main(String[] args) {

        SemaphoreQueue semaphoreQueue = new SemaphoreQueue();

        new Consumer(semaphoreQueue);

        new Producer(semaphoreQueue);

    }
}