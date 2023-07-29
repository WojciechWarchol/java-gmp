package com.wojto.producer;

import com.wojto.model.Message;
import com.wojto.model.MessageGenerator;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final BlockingQueue<Message> queue;

    public Producer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        produce();
    }

    private void produce() {
        while (true) {
            Message message = MessageGenerator.generateMessage();
            try {
                Thread.sleep((long) (Math.random() * 1000));
                queue.put(message);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
