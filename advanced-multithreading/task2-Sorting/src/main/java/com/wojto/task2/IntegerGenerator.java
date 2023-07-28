package com.wojto.task2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class IntegerGenerator {

    private static final int THREAD_POOL_SIZE = 4;
    private static final Random RANDOM = new Random();

    public static List<Integer> generateIntegers(int numberOfIntegers, int maxInteger, boolean integersRepeat) {
        if (!integersRepeat && numberOfIntegers > maxInteger) {
            throw new IllegalArgumentException("Maximal integer must be higher or equal than the number of integers if integers can't repeat.");
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Integer> generatedIntegers = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            executor.execute(() -> {
                int count = 0;
                while (count < numberOfIntegers / THREAD_POOL_SIZE) {
                    int newInt = RANDOM.nextInt(maxInteger);
                    if (integersRepeat || !generatedIntegers.contains(newInt)) {
                        generatedIntegers.add(newInt);
                        count++;
                    }
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return generatedIntegers;
    }
}

