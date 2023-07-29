package com.wojto;

import com.wojto.cache.CachingService;
import com.wojto.cache.GuavaCachingService;
import com.wojto.model.Message;
import com.wojto.thread.MessagePusher;
import com.wojto.thread.MessageRequester;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing application");

        CachingService cache = GuavaCachingService.getGuavaCache();

        int numberOfPushThreads = 2;
        int numberOfGetThreads = 2;

        ExecutorService pushMessageExecutor = Executors.newFixedThreadPool(numberOfPushThreads);
        for (int i = 0; i < numberOfPushThreads; i++) {
            pushMessageExecutor.execute(new MessagePusher(cache, 2000));
        }

        ExecutorService getMessageExecutor = Executors.newFixedThreadPool(numberOfGetThreads);
        for (int i = 0; i < numberOfGetThreads; i++) {
            getMessageExecutor.execute(new MessageRequester(cache, 100000 ));
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down application...");
            pushMessageExecutor.shutdown();
            getMessageExecutor.shutdown();
            cache.printCacheStats();
            System.out.println("Application shutdown complete.");
        }));

    }
}