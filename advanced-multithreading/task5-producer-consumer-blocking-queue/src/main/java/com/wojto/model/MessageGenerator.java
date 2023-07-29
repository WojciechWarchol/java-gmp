package com.wojto.model;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageGenerator {

    private static final AtomicInteger messageCount = new AtomicInteger(0);

    private static final List<String> messageContents = List.of(
            "User logged in",
            "User clicked on button X",
            "New item added to shopping cart",
            "Order placed",
            "Item shipped",
            "Payment received",
            "New message received",
            "New task assigned",
            "Task completed",
            "File uploaded",
            "File downloaded",
            "System error occurred",
            "Server restarted",
            "Backup completed",
            "Database migrated",
            "API rate limit exceeded",
            "New user registered",
            "User updated profile",
            "User deleted account",
            "Access denied"
    );

    private static Random random = new Random();

    public static Message generateMessage() {
        return new Message(messageCount.getAndIncrement(), getMessageContents());
    }

    private static String getMessageContents() {
        return messageContents.get(random.nextInt(messageContents.size()));
    }
}
