package com.wojto;

import com.wojto.recursive.Applyer;
import com.wojto.util.DoubleGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Started program");
        double[] array = DoubleGenerator.generateDoubles(500_000_000, 1_000_000_000);
        System.out.println("Created the double array.");

        System.out.println("\nTesting the speed of summing squares concurrently");
        Instant startTime = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
//        int n = array.length;
        Applyer a = new Applyer(array, 0, array.length, null);
        pool.invoke(a);
        System.out.println("The sum of concurrent task is:");
        System.out.println(a.getResult());
        Instant endTime = Instant.now();
//        Thread.sleep(1000);
        long concurrentTaskTime = Duration.between(startTime, endTime).toMillis();
        System.out.println("Concurrent task finished in: " + concurrentTaskTime + " milliseconds.");
        pool.shutdown();

        System.out.println("\nTesting the speed of summing squares sequentially");
        startTime = Instant.now();
        double sequentialSum = Arrays.stream(array).map(d -> d * d).sum();
        System.out.println("The sum of sequential task is:");
        System.out.println(sequentialSum);
        endTime = Instant.now();
        long sequentialTaskTime = Duration.between(startTime, endTime).toMillis();
        System.out.println("Sequential task finished in: " + sequentialTaskTime + " milliseconds.");

        double difference = (double) sequentialTaskTime/concurrentTaskTime;
        System.out.println("\nThe Concurrent task was x" + String.format("%.2f", difference) + " times faster.");
    }
}