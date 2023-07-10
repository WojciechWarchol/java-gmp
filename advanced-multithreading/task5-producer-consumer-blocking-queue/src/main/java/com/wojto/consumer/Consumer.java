package com.wojto.consumer;

import com.wojto.model.Message;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue<Message> queue;

    public Consumer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        consume();
    }

    private void consume() {
        while (true) {
            Message message;
            try {
                Thread.sleep((long) (Math.random() * 500));
                message = queue.take();
            } catch (InterruptedException e) {
                break;
            }
            System.out.println(message.toString());
        }
    }
}
