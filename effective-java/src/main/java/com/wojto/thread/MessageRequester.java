package com.wojto.thread;

import com.wojto.cache.CachingService;
import com.wojto.model.Message;

import java.util.Optional;
import java.util.Random;

public class MessageRequester implements Runnable {

    private final CachingService cachingService;
    private final int messagePoolSize;


    public MessageRequester(CachingService cachingService, int messagePoolSize) {
        this.cachingService = cachingService;
        this.messagePoolSize = messagePoolSize;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Random random = new Random();
            int messageNumber = random.nextInt(messagePoolSize) + 1;
            try {
                Optional<Message> message = cachingService.getMessage("message " + messageNumber);
                if (message.isPresent()) {
                    System.out.println("Received message: " + message.get().getContent());
                } else {
                    System.out.println("Message not found for ID: " + messageNumber);
                }

                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
