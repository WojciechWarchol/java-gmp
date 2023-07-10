package com.wojto;

import com.wojto.consumer.Consumer;
import com.wojto.model.Message;
import com.wojto.producer.Producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
    public static void main(String[] args) {

        BlockingQueue<Message> queue = new LinkedBlockingDeque<>(5);

        for (int i = 0; i < 20; i++) {
            Thread producerThread = new Thread(new Producer(queue));
            producerThread.start();
        }

        for (int i = 0; i < 30; i++) {
            Thread consumerThread = new Thread(new Consumer(queue));
            consumerThread.start();
        }
    }
}