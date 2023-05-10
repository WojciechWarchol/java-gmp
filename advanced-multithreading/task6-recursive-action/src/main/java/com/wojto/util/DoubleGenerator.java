package com.wojto.util;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DoubleGenerator {

    private static final int THREAD_POOL_SIZE = 4;
    private static final Random RANDOM = new Random();

    public static double[] generateDoubles(int numberOfDoubles, double maxDouble) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        double[] generatedDoubles = new double[numberOfDoubles];

        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            final int startIndex = i * (numberOfDoubles / THREAD_POOL_SIZE);
            final int endIndex = (i + 1) * (numberOfDoubles / THREAD_POOL_SIZE);

            executor.execute(() -> {
                for (int j = startIndex; j < endIndex; j++) {
                    generatedDoubles[j] = RANDOM.nextDouble(maxDouble);
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return generatedDoubles;
    }
}
