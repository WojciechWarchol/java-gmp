package com.wojto.thread;

import com.google.common.cache.Cache;
import com.wojto.cache.CachingService;
import com.wojto.model.Message;

import java.util.Random;

public class MessagePusher implements Runnable {

    private CachingService cache;
    private final int messagesPerSecond;

    public MessagePusher(CachingService cache, int messagesPerSecond) {
        this.cache = cache;
        this.messagesPerSecond = messagesPerSecond;
    }

    @Override
    public void run() {
        Random random = new Random();
        int count = 0;

        while (true) {
            int randomNumber = random.nextInt(100000) + 1;
            String message = "message " + randomNumber;
            cache.postMessage(Message.of(message));

            try {
                Thread.sleep(1000 / messagesPerSecond);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}